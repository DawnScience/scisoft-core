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

import uk.ac.diamond.scisoft.analysis.fitting.functions.PeakType;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.NelderMead;


public class NelderMeadFittingTest extends AbstractFittingTestBase {
	static {
		deltaFactor = new HashMap<PeakType, int[]>();
		deltaFactor.put(PeakType.GAUSSIAN, new int[] {1, 1});
		deltaFactor.put(PeakType.LORENTZIAN, new int[] {4, 6});
		deltaFactor.put(PeakType.PEARSON_VII, new int[] {11, 24}); // FIXME
		deltaFactor.put(PeakType.PSEUDO_VOIGT, new int[] {11, 28}); // FIXME
		deltaFactor.put(PeakType.VOIGT, new int[] {8, 15}); // FIXME
	}

	@Override
	public IOptimizer createOptimizer() {
		return new NelderMead(accuracy);
	}
}
