/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class ApacheConjugateGradientFittingTest extends AbstractFittingTestBase {

	@Override
	public IOptimizer createOptimizer() {
		return new ApacheOptimizer(Optimizer.CONJUGATE_GRADIENT);
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testNumberOfPeaksFoundGaussian() {
		Assert.assertEquals(1, fittedGaussian.size());
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testNumberOfPeaksFoundLorentzian() {
		Assert.assertEquals(1, fittedLorentzian.size());
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testPeakPosGaussian() {
		Assert.assertEquals(pos, fittedGaussian.get(0).getPeak(0).getPosition(), delta);
	}

	@Override
	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testPeakPosLorentzian() {
		Assert.assertEquals(pos, fittedLorentzian.get(0).getPeak(0).getPosition(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMGaussian() {
		checkClose("Gaussian fwhm", fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMLorentzian() {
		checkClose("Lorentzian fwhm", fwhm, fittedLorentzian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test failing. 17 Dec 15")
	@Test
	public void testFWHMPearsonVII() {
		checkClose("Pearson7 fwhm", fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), 20*delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testFWHMPseudoVoigt() {
		checkClose("PseudoVoigt fwhm", fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaGaussian() {
		checkClose("Gaussian area", area, fittedGaussian.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaLorentzian() {
		checkClose("Lorentzian area", area, fittedLorentzian.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaPearsonVII() {
		checkClose("Pearson7 area", area, fittedPearsonVII.get(0).getPeak(0).getArea(), delta);
	}

	@Ignore("Test not finished and is failing. 9 Nov 11")
	@Test
	public void testAreaPseudoVoigt() {
		checkClose("PseudoVoigt area", area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), delta);
	}
}
