/*-
 * Copyright 2018 Diamond Light Source Ltd.
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
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.GradientDescent;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.LeastSquares;
import uk.ac.diamond.scisoft.analysis.optimize.NelderMead;

/**
 * Test minimizer and maximizer
 */
@RunWith(Parameterized.class)
public class LegacyMinimaxiTest extends MinimaxiTestBase {
	@Parameter(value = 0)
	public Class<IOptimizer> opt;

	@Parameter(value = 1)
	public double lDelta;

	@Parameter(value = 2)
	public boolean lGlobalMin;

	@SuppressWarnings("rawtypes")
	@Parameters(name = "{index}: {0}")
	public static Collection data() {
		return Arrays.asList(new Object[][] {
			{GeneticAlg.class, 0.4, true},
//			{GradientDescent.class, 1e-5, false},
//			{LeastSquares.class, 1e-6, false},
//			{NelderMead.class, 3e-3, false}
		});
	}

	@Override
	public void setup() throws Exception {
		optimizer = opt.getConstructor(double.class).newInstance(1e-2);
		if (optimizer instanceof GeneticAlg) {
			((GeneticAlg) optimizer).setSeed(1237L);
		}
		delta = lDelta; // poor accuracy
		globalMin = lGlobalMin;
		omit = opt.equals(GradientDescent.class);
		doMinimize = false;
	}

	@Test
	public void testMiniMaxi() throws Exception {
		internalTest();
	}
}
