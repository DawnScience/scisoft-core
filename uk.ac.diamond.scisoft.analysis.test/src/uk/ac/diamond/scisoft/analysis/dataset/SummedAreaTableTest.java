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
		final SummedAreaTable sum = new SummedAreaTable(image);
		testDiagonal(image, sum);
	}
	
	@Test
	public void testLargeDiagonal() throws Exception {
		
		long start = System.currentTimeMillis();
		final IDataset image = Random.rand(new int[]{1024,1024});
		final SummedAreaTable sum = new SummedAreaTable(image);
		long end   = System.currentTimeMillis();
		
		// Check time
		long delta = end-start;
		if (delta>1000) throw new Exception("Unexpected long sum table generation! As a guide, it should take less than 400ms on I7 but took longer than 1000ms");
		
		// Long time, no caching done!
		testDiagonal(image, sum);
	}
	

	@Test
	public void testSmallMean() throws Exception {
		
		final IDataset image = Random.rand(new int[]{10,10});
		final SummedAreaTable sum = new SummedAreaTable(image);
		testDiagonal(image, sum, 3, 3);
	}

	@Test
	public void testLargeMean() throws Exception {
		
		long start = System.currentTimeMillis();
		final IDataset image = Random.rand(new int[]{1024,1024});
		final SummedAreaTable sum = new SummedAreaTable(image);
		long end   = System.currentTimeMillis();
		
		// Check time
		long delta = end-start;
		if (delta>1000) throw new Exception("Unexpected long sum table generation! As a guide, it should take less than 400ms on I7 but took longer than 1000ms");
		
		// Long time, no caching done!
		testDiagonal(image, sum, 5, 5);
	}



	private void testDiagonal(IDataset image, SummedAreaTable sum, int... box) throws Exception {
		
		if (!Arrays.equals(sum.getShape(), image.getShape())) throw new Exception("Shape not the same! sum is "+Arrays.toString(sum.getShape()));
		
		int x=0, y=0;
		while(x<image.getShape()[0] && y<image.getShape()[1]) {
			
			double a=0d, b=0d;
			if (box==null || box.length<1) {
				a = sum.getDouble(x,y);
				b = getSum(image, x, y);
				
			} else {				
				int w = box[0];
				int h = box[1];
				double csum = getBoxSum(image, new int[]{x,y}, box);
				double isum = sum.getBoxSum(new int[]{x,y}, box);
                a = isum / (w*h);
				b = csum / (w*h);
			}
			if (!DoubleUtils.equalsWithinTolerance(a, b, 0.000001)) {
				throw new Exception(a+" does not equal "+b+" for x,y="+x+","+y);
			}
			x++; y++;
		}
	}


	private double getBoxSum(IDataset image, int[] point, int... box) {
			
		int x = point[0];
		int y = point[1];
		int r1 = (int)Math.floor(box[0]/2d); // for instance 3->1, 5->2, 7->3 
		int r2 = (int)Math.floor(box[1]/2d); // for instance 3->1, 5->2, 7->3 
		
		int minx = x-r1;
		if (minx<0) minx=0;		
		int maxx = x+r1;
		if (maxx>=image.getShape()[0]) maxx = image.getShape()[0]-1;
		
		int miny = y-r2;
		if (miny<0) miny=0;		
		int maxy = y+r2;
		if (maxy>=image.getShape()[1]) maxy = image.getShape()[1]-1;

		double sum = 0d;
		for (int ix = minx; ix <=maxx; ix++) {
			for (int iy = miny; iy <=maxy; iy++) {
				sum+=image.getDouble(ix,iy);
			}
		}
		return sum;
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
