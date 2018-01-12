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
import java.util.HashMap;

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
//				{Optimizer.GAUSS_NEWTON},
				{Optimizer.SIMPLEX_MD},
				{Optimizer.SIMPLEX_NM},
//				{Optimizer.POWELL}, // bad fit
				{Optimizer.BOBYQA},
//				{Optimizer.CMAES},  // bad fit
//				{Optimizer.CONJUGATE_GRADIENT}, // does not work for PseudoVoigt and PearsonVII
				{Optimizer.LEVENBERG_MARQUARDT},
//				{Optimizer.GAUSS_NEWTON},
				};
		return Arrays.asList(data);
	}

	static {
		deltaFactor = new HashMap<String, int[]>();
		deltaFactor.put(GAUSSIAN, new int[] {1, 1});
		deltaFactor.put(LORENTZIAN, new int[] {4, 6});
		deltaFactor.put(PEARSON_VII, new int[] {1, 2});
		deltaFactor.put(PSEUDO_VOIGT, new int[] {1, 7});
	}

	public ApacheFittingTest(Optimizer optimizer) {
		o = optimizer;
	}

	@Override
	public IOptimizer createOptimizer() {
		ApacheOptimizer opt = new ApacheOptimizer(o, SEED);
		return opt;
	}

}
