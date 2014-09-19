/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class GeneticAlgFittingTest extends AbstractFittingTestBase {

	static final long SEED = 12357L;

	@Override
	public IOptimizer createOptimizer() {
		return new GeneticAlg(accuracy, SEED);
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
		checkClose("PseudoVoigt area", area, fittedPseudoVoigt.get(0).getPeak(0).getArea(), 6*delta);
	}
}
