/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

@RunWith(Parameterized.class)
public class ApacheFittingTest extends AbstractFittingTestBase {

	static final long SEED = 12357L;
	private Optimizer o;


	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{Optimizer.SIMPLEX_MD},
				{Optimizer.SIMPLEX_NM},
//				{Optimizer.POWELL}, // bad fit
				{Optimizer.BOBYQA},
//				{Optimizer.CMAES},  // bad fit
//				{Optimizer.CONJUGATE_GRADIENT}, // does not work for Gaussian and PVII
				};
		return Arrays.asList(data);
	}

	public ApacheFittingTest(Optimizer optimizer) {
		o = optimizer;
	}

	@Override
	public IOptimizer createOptimizer() {
		ApacheOptimizer opt = new ApacheOptimizer(o);
		opt.seed = SEED;
		return opt;
	}

	@Test
	public void testFWHMGaussian() {
		checkClose("Gaussian fwhm", fwhm, fittedGaussian.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMLorentzian() {
		checkClose("Lorentzian fwhm", fwhm, fittedLorentzian.get(0).getPeak(0).getFWHM(), 4*delta);
	}

	@Test
	public void testFWHMPearsonVII() {
		checkClose("Pearson7 fwhm", fwhm, fittedPearsonVII.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testFWHMPseudoVoigt() {
		checkClose("PseudoVoigt fwhm", fwhm, fittedPseudoVoigt.get(0).getPeak(0).getFWHM(), delta);
	}

	@Test
	public void testAreaGaussian() {
		checkClose("Gaussian area", area, fittedGaussian.get(0).getPeak(0).getArea(), delta);
	}

	@Test
	public void testAreaLorentzian() {
		checkClose("Lorentzian area", area, fittedLorentzian.get(0).getPeak(0).getArea(), 6*delta);
	}

	@Test
	public void testAreaPearsonVII() {
		checkClose("Pearson7 area", area, fittedPearsonVII.get(0).getPeak(0).getArea(), 2*delta);
	}

	@Test
	public void testAreaPseudoVoigt() {
		checkClose("PseudoVoigt area", area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), 7*delta);
	}
}
