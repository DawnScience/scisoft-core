/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;

public class Interpolation1D {

	public static IDataset splineInterpolation(IDataset x, IDataset y, IDataset xnew) {
		
		return interpolate(x,y, xnew, new SplineInterpolator());
		
	}
	
	public static IDataset linearInterpolation(IDataset x, IDataset y, IDataset xnew) {
		
		return interpolate(x,y, xnew, new LinearInterpolator());
		
	}
	
	public static IDataset interpolate(IDataset oldx, IDataset oldy, IDataset newx, UnivariateInterpolator interpolator) {
		
		//TODO more sanity checks on inputs
		
		DoubleDataset dx = (DoubleDataset)DatasetUtils.cast(oldx,Dataset.FLOAT64);
		DoubleDataset dy = (DoubleDataset)DatasetUtils.cast(oldy,Dataset.FLOAT64);
		
		boolean sorted = true;
		double maxtest = oldx.getDouble(0);
		for (int i = 1; i < ((Dataset)oldx).count(); i++) {
			if (maxtest > oldx.getDouble(i))  {
				sorted = false;
				break;
			}
			maxtest = dx.getDouble(i);
		}
		
		double[] sortedx = null;
		double[] sortedy = null;
		
		if (!sorted) {
			IntegerDataset sIdx = getIndiciesOfSorted(dx);
			sortedx = new double[dx.getData().length];
			sortedy = new double[dy.getData().length];
			
			for (int i = 0 ; i < sIdx.getSize(); i++) {
				sortedx[i] = dx.getDouble(sIdx.get(i));
				sortedy[i] = dy.getDouble(sIdx.get(i));
			}
		} else {
			sortedx = dx.getData();
			sortedy = dy.getData();
		}
		
		UnivariateFunction func = interpolator.interpolate(sortedx,sortedy);
		
		IDataset newy = newx.clone();
		newy.setName(oldy.getName()+"_interpolated");
		
		double val = 0;
		for (int i = 0; i < ((Dataset)newx).count(); i++) {
			
			try {
				val = func.value(newx.getDouble(i));
			} catch (OutOfRangeException e) {
				val = 0;
			}
			
			newy.set(val,i);
		}
		
		return newy;
	}
	
	private static IntegerDataset getIndiciesOfSorted(final IDataset d) {

		Integer[] dBox = new Integer[d.getSize()];

		for (int i = 0; i < dBox.length; i++) dBox[i] = i;

		Arrays.sort(dBox, new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				double dif = d.getDouble(o1)-d.getDouble(o2);
				return dif > 0 ? (int)Math.ceil(dif) : (int)Math.floor(dif);
			}
		});

		int[] dint = new int[d.getSize()];
		for (int i = 0; i < dBox.length; i++) dint[i] = dBox[i];

		return new IntegerDataset(dint, new int[]{dint.length});
	}
	
	private static int[] getCommonRangeIndicies(IDataset x1, IDataset x2) {
		//TODO checks for no overlap etc
		double max1 = x1.max().doubleValue();
		double min1 = x1.min().doubleValue();
		
		double max2 = x2.max().doubleValue();
		double min2 = x2.min().doubleValue();
		
		double max = max1 < max2 ? max1 : max2;
		double min = min1 > min2 ? min1 : min2;
		//TODO max needs to be 1 lower, min needs to be 1 higher
		int maxpos = ROISliceUtils.findPositionOfClosestValueInAxis(x1, max);
		int minpos = ROISliceUtils.findPositionOfClosestValueInAxis(x1, min);
		
		return new int[]{minpos+1, maxpos-1};
	}
	
}
