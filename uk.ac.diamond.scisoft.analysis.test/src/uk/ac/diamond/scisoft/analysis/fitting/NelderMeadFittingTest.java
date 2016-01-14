/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import java.util.HashMap;

import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.NelderMead;


public class NelderMeadFittingTest extends AbstractFittingTestBase {
	static {
		deltaFactor = new HashMap<String, int[]>();
		deltaFactor.put(GAUSSIAN, new int[] {1, 1});
		deltaFactor.put(LORENTZIAN, new int[] {4, 6});
		deltaFactor.put(PEARSON_VII, new int[] {11, 24}); // FIXME
		deltaFactor.put(PSEUDO_VOIGT, new int[] {11, 28}); // FIXME
	}

	@Override
	public IOptimizer createOptimizer() {
		return new NelderMead(accuracy);
	}
}
