/*-
 * Copyright (c) 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey


package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

public class GaussianNDTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testParameterSetting() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5, -0.5);

		// First we need to ensure that the fitting object has
		// been created properly
		Assert.assertEquals(6, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0., 1., 1, 1, 1.5, -0.5}, f.getParameterValues(), ABS_TOL);
		
		DoubleDataset xCoord = DatasetFactory.createRange(7);
		DoubleDataset yCoord = DatasetFactory.createRange(8);
		FunctionTestUtils.checkValuesND(f, xCoord, yCoord);
	}
	
	@Test
	public void testMinMaxSet() {
		double[] mins = {0.5, 1.2};
		double[] maxs = {1.2, 2};
		GaussianND f = new GaussianND(1., mins, maxs, 1);

		// First we need to ensure that the fitting object has
		// been created properly
		Assert.assertEquals(6, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0.85, 1.6, 0.5, 0.01, 0.01, 0}, f.getParameterValues(), ABS_TOL);

		DoubleDataset xCoord = DatasetFactory.createRange(7);
		DoubleDataset yCoord = DatasetFactory.createRange(8);
		FunctionTestUtils.checkValuesND(f, xCoord, yCoord);
	}
	
	// Test for the setPeakPosition function
	@Test
	public void testSetPeakPosition() {
		double[] mins = {0.5, 1.2};
		double[] maxs = {1.2, 2};
		GaussianND f = new GaussianND(1., mins, maxs, 1);

		// First we need to ensure that the fitting object has
		// been created properly
		Assert.assertEquals(6, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0.85, 1.6, 0.5, 0.01, 0.01, 0}, f.getParameterValues(), ABS_TOL);

		f.setPeakPosition(new double[] {0.7, 1.7});
		Assert.assertArrayEquals(new double[] {0.7, 1.7, 0.5, 0.01, 0.01, 0}, f.getParameterValues(), ABS_TOL);

	}
	
	// Test setVolume function
	@Test
	public void testSetVolume() {
		double[] mins = {0.5, 1.2};
		double[] maxs = {1.2, 2};
		GaussianND f = new GaussianND(1., mins, maxs, 1);

		// First we need to ensure that the fitting object has
		// been created properly
		Assert.assertEquals(6, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0.85, 1.6, 0.5, 0.01, 0.01, 0}, f.getParameterValues(), ABS_TOL);

		f.setVolume(0.7);
		Assert.assertArrayEquals(new double[] {0.85, 1.6, 0.7, 0.01, 0.01, 0}, f.getParameterValues(), ABS_TOL);
	}
	
	// Test getPeakValue function, when the function is dirty
	// Compared against the python available at https://github.com/arm61/2d_gaussian
	@Test 
	public void testGetPeakValue() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5, -0.5);

		// First we need to ensure that the fitting object has
		// been created properly
		Assert.assertEquals(6, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0., 1., 1, 1, 1.5, -0.5}, f.getParameterValues(), ABS_TOL);
				
		Assert.assertEquals(0.1500527193595177, f.getPeakValue(), ABS_TOL);
	}
	
	// Test getPeakValue function when the function is clean
	// Compared against the python available at https://github.com/arm61/2d_gaussian
	@Test 
	public void testGetPeakValueDirty() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5, -0.5);

		// First we need to ensure that the fitting object has
		// been created properly
		Assert.assertEquals(6, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0., 1., 1, 1, 1.5, -0.5}, f.getParameterValues(), ABS_TOL);
				
		Assert.assertEquals(0.1500527193595177, f.getPeakValue(), ABS_TOL);
		
		Assert.assertEquals(0.1500527193595177, f.getPeakValue(), ABS_TOL);
	}
	
	// Test getPeakValue, and therefore calcCachedParameters, function where is rank is 0
	@Test 
	public void testPosNull() {
		GaussianND f = new GaussianND();

		Assert.assertEquals(0, f.getPeakValue(), ABS_TOL);
	}
	
	// Test getPeakValue, and therefore calcCachedParameters, function where pos is not null
	@Test 
	public void testPosNotNull() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5, -0.5);

		Assert.assertEquals(0.1500527193595177, f.getPeakValue(), ABS_TOL);
		
		f.setVolume(4);
		Assert.assertEquals(0.6002108774380708, f.getPeakValue(), ABS_TOL);
	}
	
	// Test vals when isDirty
	@Test 
	public void testDirtyVal() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5, -0.5);

		Assert.assertEquals(0.1500527193595177, f.val(0., 1.), ABS_TOL);
		
		f.setVolume(4);
		Assert.assertEquals(0.6002108774380708, f.val(0., 1.), ABS_TOL);
	}
	
	// Test fillwithvalues when dirty
	@Test
	public void testFillwithValuesDirty() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5, -0.5);
		IDataset coords = DatasetFactory.createRange(0,  1,  0.1);
		CoordinatesIterator it = CoordinatesIterator.createIterator(null, coords, coords);
		DoubleDataset current = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		f.setVolume(4);
		f.fillWithValues(current, it);
	}
	
	// Test fillwithvalues when clean
	@Test
	public void testFillwithValuesClean() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5, -0.5);
		double peak = f.getPeakValue();
		IDataset coords = DatasetFactory.createRange(0,  1,  0.1);
		CoordinatesIterator it = CoordinatesIterator.createIterator(null, coords, coords);
		DoubleDataset current = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		f.fillWithValues(current, it);
	}
	
	// Test fitting
	@Test
	public void testFit() {
		GaussianND f = new GaussianND(0.5, 0.5, 1, 1, 1.5, -0.5);
		DoubleDataset coords = DatasetFactory.createRange(0,  1,  0.1);
		CoordinatesIterator it = CoordinatesIterator.createIterator(null, coords, coords);
		DoubleDataset current = DatasetFactory.zeros(DoubleDataset.class, it.getShape());
		f.fillWithValues(current, it);	
		
		double mV = 1.1;
		double[] mins = {0.4, 0.4};
		double[] maxs = {0.6, 0.6};
		
		GaussianND gaussFit = new GaussianND(mV, mins, maxs, 1.6);
			
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.SIMPLEX_MD);
			opt.optimize(new Dataset[] {coords, coords}, current, gaussFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing 2D Gaussian fit in GaussianBackgroundSubtractionOperation(): " + fittingError.toString());
		}
		Assert.assertArrayEquals(new double[] {0.5, 0.5, 1, 1, 1.5, -0.5}, gaussFit.getParameterValues(), 1e-4);
	}
	
	// Test an invalid number of parameters setting
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidParameterSetting() {
		GaussianND f = new GaussianND(0., 1., 1, 1, 1.5);
	}
	
	// Test an invalid valid of parameters setting
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidValueParameterSetting() {
		GaussianND f = new GaussianND(0., 1., 1, 0, 1.5, -1.5);
	}

	// This will test that the double array for the maxPeakPosition should
	// throw an error if it not the same length as the minPeakPosition array
	@Test(expected = IllegalArgumentException.class)
	public void testErrorInArrayLength() {	
		double[] mins = {0.5, 1.2};
		double[] maxs = {1.2};
		GaussianND f = new GaussianND(1., mins, maxs, 1);
	} 
	
	
	// Test for an error in setting the peak position
	@Test(expected = IllegalArgumentException.class)
	public void testErrorInSetPeakPosition() {	
		double[] mins = {0.5, 1.2};
		double[] maxs = {1.2, 2};
		GaussianND f = new GaussianND(1., mins, maxs, 1);

		// First we need to ensure that the fitting object has
		// been created properly
		Assert.assertEquals(6, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {0.85, 1.6, 0.5, 0.01, 0.01, 0}, f.getParameterValues(), ABS_TOL);

		f.setPeakPosition(new double[] {0.7, 1.7, 1.5});
	} 
}