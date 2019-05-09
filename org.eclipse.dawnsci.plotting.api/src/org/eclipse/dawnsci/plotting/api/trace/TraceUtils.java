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
import org.eclipse.january.dataset.IDataset;

/**
 * Class containing utility methods for regions to avoid duplication 
 * @author Matthew Gerring
 *
 */
public class TraceUtils {
	
	

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

		List<IDataset> axes = trace.getAxes();
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
		if (axis == null)
			return false;

		final Class<?> clazz = axis.getElementClass();
		if (clazz != Integer.class) {
			return true;
		}

		if (axis.getSize() != length)
			return true;

		length--;
		return axis.getDouble(0) != 0 && axis.getDouble(length) != length;
	}

	public final static void transform(IDataset label, int index, double[] point) {
		if (label!=null) {
			internalTransform(label, index, point);
		}
	}

	public final static void transform(IDataset label, int index, double[] pointA, double[] pointB) {
		if (label!=null) {
			internalTransform(label, index, pointA);
			internalTransform(label, index, pointB);
		}
	}

	public final static void transform(IDataset label, int index, double[]... points) {
		if (label!=null) {
			for (double[] ds : points) {
				internalTransform(label, index, ds);
			}
		}
	}

	private final static void internalTransform(IDataset label, int index, double[] point) {
		double lIndex = point[index];
		double floor  = Math.floor(lIndex);
		double frac   = lIndex - floor;
		int    iFloor = (int) floor;
		double lo     = label.getDouble(iFloor);

		point[index] = frac == 0 ? lo : (1 - frac) * lo + frac * label.getDouble(iFloor + 1);
	}
}
