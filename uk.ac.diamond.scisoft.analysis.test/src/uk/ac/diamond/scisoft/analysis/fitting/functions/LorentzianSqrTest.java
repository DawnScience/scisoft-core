/*-
 * Copyright (c) 2015 Gero Flucke, DESY.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Assert;
import org.junit.Test;

public class LorentzianSqrTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() 
	{
 		testLorentzianSqr(23., 2., 1.5); // as Lorentzian
		testLorentzianSqr(1, 0.1, 10.); // area >> fwhm
		testLorentzianSqr(999., .9, 1.1);// area ~ fwhm
		testLorentzianSqr(-10., 10., 0.1);// area << fwhm
	}

	public void testLorentzianSqr(double pos, double fwhm, double area)
	{
		APeak f = new LorentzianSqr();
		f.setParameterValues(pos, fwhm, area);
		// test number of parameters and whether parameters are correctly stored
		Assert.assertEquals(3, f.getNoOfParameters());
		Assert.assertArrayEquals(new double[] {pos, fwhm, area}, f.getParameterValues(), ABS_TOL);

		FunctionTestUtils.checkValues(f);

		// test function values at centre and centre +/1 0.5*fwhm
		double widthPar = fwhm / Math.sqrt(Math.sqrt(2.) - 1.);	
		double height = area / (0.25 * Math.PI * widthPar);
		Assert.assertEquals(height, f.val(pos), ABS_TOL);
		Assert.assertEquals(height, f.getHeight(), ABS_TOL);
		Assert.assertEquals(0.5 * height, f.val(pos - 0.5 * fwhm), ABS_TOL);
		Assert.assertEquals(0.5 * height, f.val(pos + 0.5 * fwhm), ABS_TOL);

		// test that equals(f2) works even if f2 is still "dirty" 
		LorentzianSqr f2 = new LorentzianSqr(new double[] {pos, fwhm, area});
		Assert.assertTrue(f.equals(f2));
		
		// integral from -inf to inf should be area,
		// actual range related to fwhm, bin width as well
		int nBins = (int) Math.ceil(1402./fwhm);
		if ((nBins % 2) == 0) {
			nBins += 1; // odd number of bins to get peak
		}
		Dataset x = DatasetFactory.createLinearSpace(DoubleDataset.class, -50.*fwhm+pos, 50.*fwhm+pos, nBins);
		Dataset v = f2.calculateValues(x);
		double s = ((Number) v.sum()).doubleValue() * Math.abs(x.getDouble(0) - x.getDouble(1));
		Assert.assertEquals(area, s, 1e-2); // relaxed tolerance: poor man's integration only...
		// test that calculateValues(dataset) gives same as f.val(double)
		Assert.assertEquals(v.getDouble(0), f.val(x.getDouble(0)), ABS_TOL);
		
	}

	@Test
	public void testFunctionDerivative() {
		APeak f = new LorentzianSqr();
		f.setParameterValues(23., 2., 1.5);

		FunctionTestUtils.checkPartialDerivatives(f);
	}
}
