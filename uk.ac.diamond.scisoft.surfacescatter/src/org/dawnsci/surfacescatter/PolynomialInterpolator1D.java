/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dawnsci.surfacescatter;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IntegerDataset;

public class PolynomialInterpolator1D {

	public static IDataset interpolate(IDataset oldx, IDataset oldy, IDataset newx) {
		
		//TODO more sanity checks on inputs
		
		DoubleDataset dx = (DoubleDataset)DatasetUtils.cast(oldx,Dataset.FLOAT64);
		DoubleDataset dy = (DoubleDataset)DatasetUtils.cast(oldy,Dataset.FLOAT64);
		
		boolean sorted = true;
		double maxtest = dx.getDouble(0);
		int count = dx.getSize();
		for (int i = 1; i < count; i++) {
			if (maxtest > dx.getDouble(i))  {
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
		
		SplineInterpolator si = new SplineInterpolator();
		PolynomialSplineFunction poly = si.interpolate(sortedx,sortedy);
		
		Dataset newy = DatasetFactory.zeros(newx.getShape(), Dataset.FLOAT64);
		count = newy.getSize();
		newy.setName(oldy.getName()+"_interpolated");
		
		for (int i = 0; i < count; i++) {
			newy.set(poly.value(newx.getDouble(i)),i);
		}
		
		return newy;
	}
	
	public static IntegerDataset getIndiciesOfSorted(final IDataset d) {

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

		return DatasetFactory.createFromObject(IntegerDataset.class, dint, null);
	}
	
	public static int[] getCommonRangeIndicies(IDataset x1, IDataset x2) {
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
