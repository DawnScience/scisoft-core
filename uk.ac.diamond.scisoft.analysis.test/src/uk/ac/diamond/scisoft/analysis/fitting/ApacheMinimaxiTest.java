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

import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

/**
 * Test minimizer and maximizer
 */
@RunWith(Parameterized.class)
public class ApacheMinimaxiTest extends MinimaxiTestBase {
	@Parameters(name = "{index}: {0}")
	public static Collection<Object> data() {
		return Arrays.asList(ApacheOptimizer.Optimizer.SIMPLEX_MD,
				ApacheOptimizer.Optimizer.SIMPLEX_NM,
				ApacheOptimizer.Optimizer.POWELL,
				ApacheOptimizer.Optimizer.CMAES,
//				ApacheOptimizer.Optimizer.BOBYQA, // does not work!!!
				ApacheOptimizer.Optimizer.CONJUGATE_GRADIENT);
	}

	@Parameter
	public Optimizer opt;

	@Override
	public void setup() {
		optimizer = new ApacheOptimizer(opt, 1237L);
		delta = opt == Optimizer.POWELL ? 7e-3 : DELTA; // poor accuracy
		globalMin = opt == Optimizer.CMAES;
		omit = opt == Optimizer.CONJUGATE_GRADIENT;
	}

	@Test
	public void testMiniMaxi() throws Exception {
		internalTest();
	}
}
