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
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
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

	@Test
	public void testSmallVariance() throws Exception {
		
		final IDataset image = Maths.multiply(Random.rand(new int[]{10,10}), 100);
		final SummedAreaTable sum = new SummedAreaTable(image);
		testDiagonal(image, sum, true, 3, 3);
	}

	@Test
	public void testLargeVariance() throws Exception {
		
		long start = System.currentTimeMillis();
		final IDataset image = Maths.multiply(Random.rand(new int[]{1024,1024}), 100);
		final SummedAreaTable sum = new SummedAreaTable(image);
		long end   = System.currentTimeMillis();
		
		// Check time
		long delta = end-start;
		if (delta>1000) throw new Exception("Unexpected long sum table generation! As a guide, it should take less than 400ms on I7 but took longer than 1000ms");
		
		// Long time, no caching done!
		testDiagonal(image, sum, true, 5, 5);
	}
	
	@Test
	public void testSmallFano() throws Exception {	
        typeLoop(new int[]{10,10});
	}
	
	@Test
	public void testFunnyFano() throws Exception {	
        typeLoop(new int[]{10,10});
	}
	
	@Test
	public void testMediumFano() throws Exception {
		testFano(DatasetFactory.zeros(new int[]{10, 10}, Dataset.FLOAT32), 3, 3);
		testFano(DatasetFactory.ones(new int[]{10, 10}, Dataset.FLOAT32), 3, 3);
	}
	
	@Test
	public void test6MillTableTime() throws Exception {
		
		long start = System.currentTimeMillis();
		final IDataset image = Random.rand(new int[]{2000,3000});
		final SummedAreaTable sum = new SummedAreaTable(image, true);
		long end  = System.currentTimeMillis();
		System.out.println("Calculated summed area table of size "+Arrays.toString(new int[]{2000,3000})+" in "+(end-start)+"ms");
		
	}
	@Test
	public void test6MillFanoTableTime() throws Exception {
		long start = System.currentTimeMillis();
		final IDataset image = Random.rand(new int[]{2000,3000});
		final SummedAreaTable sum = new SummedAreaTable(image, true);
		System.out.println("Calculated summed area table of size "+Arrays.toString(new int[]{2000,3000})+" in "+(System.currentTimeMillis()-start)+"ms");
		final IDataset fano = sum.getFanoImage(new int[]{5,5});
		long end  = System.currentTimeMillis();
		System.out.println("Calculated fano image of size "+Arrays.toString(new int[]{2000,3000})+" in "+(end-start)+"ms");

	}

    //@Test
	public void testMediumLargeFano() throws Exception {
        typeLoop(new int[]{3000,2000});
	}
	
	//@Test
	public void testLargeFano() throws Exception {
        typeLoop(new int[]{4096,4096});
	}

	private void typeLoop(int[] size)  throws Exception {
		for (int i : new int[]{1,3,5,7,9}) {	
			testFano(DatasetUtils.cast(Maths.multiply(Random.rand(size), 100), Dataset.INT16),   i, i);
			testFano(DatasetUtils.cast(Maths.multiply(Random.rand(size), 100), Dataset.INT32),   i, i);
			testFano(DatasetUtils.cast(Maths.multiply(Random.rand(size), 100), Dataset.INT64),   i, i);
			testFano(DatasetUtils.cast(Maths.multiply(Random.rand(size), 100), Dataset.FLOAT32), i, i);
			testFano(DatasetUtils.cast(Maths.multiply(Random.rand(size), 100), Dataset.FLOAT64), i, i);
		}
	}

	
	private void testFano(Dataset image, int... box) throws Exception {
		
		long start = System.currentTimeMillis();
		final SummedAreaTable sum = new SummedAreaTable(image);
		final Dataset fano   = (Dataset)sum.getFanoImage(box);
		long end  = System.currentTimeMillis();
		
		if (fano.getDtype() != image.getDtype()) throw new Exception("Fano image changed dType!");
		if (!Arrays.equals(fano.getShape(), image.getShape())) throw new Exception("Fano image changed shape!");
		
		System.out.println("Calculated fano of size "+Arrays.toString(fano.getShape())+" with box "+Arrays.toString(box)+" in "+(end-start)+"ms");
		
	}

	private void testDiagonal(IDataset image, SummedAreaTable sum, int... box) throws Exception {
		testDiagonal(image, sum, false, box);
	}

	private void testDiagonal(IDataset image, SummedAreaTable sum, boolean variance, int... box) throws Exception {
		
		if (!Arrays.equals(sum.getShape(), image.getShape())) throw new Exception("Shape not the same! sum is "+Arrays.toString(sum.getShape()));
		
		String lastFail = null;
		int x=0, y=0;
		while(x<image.getShape()[0] && y<image.getShape()[1]) {
			
			double a=0d, b=0d;
			if (box==null || box.length<1) {
				a = sum.getDouble(x,y);
				b = getSum(image, x, y);
				
			} else {				
				
				if (variance) { // variance
					a = sum.getBoxVariance(new int[]{x,y}, box);
					b = getBoxVariance(image, new int[]{x,y}, box);
					
				} else { // mean
					a = sum.getBoxMean(new int[]{x,y}, box);				
					b = getBoxMean(image, new int[]{x,y}, box);
				}
			}
			if (!DoubleUtils.equalsWithinTolerance(a, b, 0.000001)) {
				lastFail = a+" does not equal "+b+" for x,y="+x+","+y;
				System.out.println(lastFail);
			}
			x++; y++;
		}
		if (lastFail!=null) throw new Exception(lastFail);
	}
	
	/**
	 * At one point during development the dataset variance was wrong.
	 * @throws Exception
	 */
	@Test
	public void testDatasetVariance() throws Exception {
		
		final Dataset image = Maths.multiply(Random.rand(new int[]{10,10}), 100);
	    double mean = ((Number)image.mean()).doubleValue();
	    Dataset minus  = Maths.subtract(image, mean);
	    Dataset square = Maths.power(minus, 2);
	    double var = ((Number)square.mean()).doubleValue();

		if (DoubleUtils.equalsWithinTolerance(var, image.variance().doubleValue(), 0.000001d)) {
			throw new Exception("Variance not equal : "+var+" to "+image.variance().doubleValue());
		}
	}

	private double getBoxVariance(IDataset image, int[] point, int[] box) {
	    Dataset subsetNoSlice = createDataset(image, point, box);
	    return subsetNoSlice.variance(true).doubleValue();
	}

	private double getBoxMean(IDataset image, int[] point, int... box) {
	    Dataset subsetNoSlice = createDataset(image, point, box);
		return ((Number)subsetNoSlice.mean()).doubleValue();
	}

	private Dataset createDataset(IDataset image, int[] point, int[] box) {
        int[] coords = createCoords(image, point, box);
        
        double[] subset = new double[box[0]*box[1]];
        
        int count = 0;
		for (int ix = coords[0]; ix <=coords[2]; ix++) {
			for (int iy = coords[1]; iy <=coords[3]; iy++) {
				subset[count] = image.getDouble(ix,iy);
				++count;
			}
		}
		
		return new DoubleDataset(subset, box);
	}

	private int[] createCoords(IDataset image, int[] point, int[] box) {
		
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
		
		return new int[]{minx, miny, maxx, maxy};
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
