/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.plotting.api.trace;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.dawnsci.plotting.api.IPlottingSystem;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;

/**
 * Class containing utility methods for regions to avoid duplication 
 * @author Matthew Gerring
 *
 */
public class TraceUtils {
	private TraceUtils() {
	}
	

	/**
	 * Call to get a unique region name 
	 * @param nameStub
	 * @param system
	 * @return
	 */
	public static String getUniqueTrace(final String nameStub, final IPlottingSystem<?> system, final String... usedNames) {
		int i = 1;
		@SuppressWarnings("unchecked")
		final List<String> used = (List<String>) (usedNames!=null ? Arrays.asList(usedNames) : Collections.emptyList());
		while(system.getTrace(nameStub+" "+i)!=null || used.contains(nameStub+" "+i)) {
			++i;
			if (i>10000) break; // something went wrong!
		}
		return nameStub+" "+i;
	}

	/**
	 * Removes a trace of this name if it is already there.
	 * @param plottingSystem
	 * @param string
	 * @return
	 */
	public static final ILineTrace replaceCreateLineTrace(IPlottingSystem<?> system, String name) {
		if (system.getTrace(name)!=null) {
			system.removeTrace(system.getTrace(name));
		}
		return system.createLineTrace(name);
	}

	/**
	 * Determine if given IImageTrace has any custom axes
	 * @param trace
	 * @return true if it does have custom axes
	 */
	public static boolean isCustomAxes(ICoordinateSystemTrace trace) {
		if (trace == null) {
			return false;
		}

		List<? extends IDataset> axes = trace.getAxes();
		if (axes == null || axes.isEmpty()) {
			return false;
		}

		int[] shape = trace.getData().getShape();
		if (shape.length != axes.size()) {
			throw new IllegalArgumentException("Trace has strange number of axes: they should be equal to rank of data");
		}
		int d = shape.length - 1;
		for (IDataset a : axes) {
			if (isAxisCustom(a, shape[d--]))
				return true;
		}
		return false;
	}

	/**
	 * Determine if axis is custom
	 * @param axis dataset of axis values
	 * @param length number of axis values
	 * @return true if it is custom
	 */
	public static boolean isAxisCustom(IDataset axis, int length) {
		if (axis == null || axis.getSize() == 0) {
			return false;
		}

		final Class<?> clazz = axis.getElementClass();
		if (clazz != Integer.class) {
			return true;
		}

		if (axis.getSize() != length) {
			return true;
		}

		length--;
		return axis.getDouble() != 0 && (length == 0 || axis.getDouble(length) != length);
	}

	public static final void transform(boolean interpolate, Dataset label, int index, double[] point, double[] newPoint) {
		if (label != null) {
			internalTransform(interpolate, label, index, point, newPoint);
		}
	}

	public static final void transform(boolean interpolate, Dataset label, int index, double[] pointA, double[] pointB, double[] newPointA, double[] newPointB) {
		if (label != null) {
			internalTransform(interpolate, label, index, pointA, newPointA);
			internalTransform(interpolate, label, index, pointB, newPointB);
		}
	}

	public static final void transform(boolean interpolate, Dataset label, int index, double[]... points) {
		if (label != null) {
			int pairs = points.length;
			if (pairs % 2 == 1) {
				throw new IllegalArgumentException("Need an even number of points arrays");
			}
			pairs /= 2;
			for (int p = 0; p < pairs; p++) {
				internalTransform(interpolate, label, index, points[p], points[p+pairs]);
			}
		}
	}

	private static final void internalTransform(boolean interpolate, Dataset label, int index, double[] oldPoint, double[] newPoint) {
		if (label.getRank() == 1) {
			newPoint[index] = getDouble(interpolate, label, oldPoint[index]);
		} else {
			newPoint[index] = getDoubleND(interpolate, label, oldPoint[1], oldPoint[0]);
		}
	}

	private static double getDouble(boolean interpolate, Dataset label, double pt) {
		if (interpolate) {
			double floor  = Math.floor(pt);
			double frac   = pt - floor;
			int    iFloor = (int) floor;
			int length = label.getSize();
			double lo     = iFloor >= length ? 0 : label.getDouble(iFloor++);
			if (iFloor >= length) {
				return Double.NaN;
			}
			return frac == 0 ? lo : (1 - frac) * lo + frac * label.getDouble(iFloor);
		}

		return label.getDouble(Math.clamp((int) Math.floor(pt), 0, label.getSize() - 1));
	}

	private static double getDoubleND(boolean interpolate, Dataset label, double... pt) {
		if (interpolate) {
			return Maths.interpolate(label, pt[1], pt[0]);
		}
		int[] shape = label.getShapeRef();
		int[] point = new int[shape.length];
		for (int i = 0; i < Math.min(pt.length, shape.length); i++) {
			point[i] = Math.clamp((int) Math.floor(pt[i]), 0, shape[i] - 1);
		}
		return label.getDouble(point);
	}
}
