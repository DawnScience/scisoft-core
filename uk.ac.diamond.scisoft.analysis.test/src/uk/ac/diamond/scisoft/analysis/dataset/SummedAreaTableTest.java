/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.impl.SummedAreaTable;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.DoubleUtils;

public class SummedAreaTableTest {

	
	@Test
	public void testSmallDiagonal() throws Exception {
		
		final IDataset image = Random.rand(new int[]{10,10});
		final IDataset sum = SummedAreaTable.getSummedTable(image);
		testDiagonal(image, sum);
	}
	
	@Test
	public void testLargeDiagonal() throws Exception {
		
		long start = System.currentTimeMillis();
		final IDataset image = Random.rand(new int[]{1024,1024});
		final IDataset sum = SummedAreaTable.getSummedTable(image);
		long end   = System.currentTimeMillis();
		
		// Check time
		long delta = end-start;
		if (delta>1000) throw new Exception("Unexpected long sum table generation! As a guide, it should take less than 400ms on I7 but took longer than 1000ms");
		
		// Long time, no caching done!
		testDiagonal(image, sum);
	}

	private void testDiagonal(IDataset image, IDataset sum) throws Exception {
		
		if (!Arrays.equals(sum.getShape(), image.getShape())) throw new Exception("Shape not the same! sum is "+Arrays.toString(sum.getShape()));
		
		int x=0, y=0;
		while(x<image.getShape()[0] && y<image.getShape()[1]) {
			
			double isum = sum.getDouble(x,y);
			double fsum = getSum(image, x, y);
			
			if (!DoubleUtils.equalsWithinTolerance(isum, fsum, 0.000001)) {
				throw new Exception(isum+" does not equal "+fsum+" for x,y="+x+","+y);
			}
			x++; y++;
		}
	}

	/**
	 * The summed area table is just the sum of all the pixels above and to the left of (x, y)
	 * @param image
	 * @param x
	 * @param y
	 * @return The summed area table is just the sum of all the pixels above and to the left of (x, y)
	 */
	private double getSum(IDataset image, int x, int y) {
		
		double sum = 0d;
		for (int ix = 0; ix <=x; ix++) {
			for (int iy = 0; iy <=y; iy++) {
				sum+=image.getDouble(ix,iy);
			}
		}
		return sum;
	}
}
