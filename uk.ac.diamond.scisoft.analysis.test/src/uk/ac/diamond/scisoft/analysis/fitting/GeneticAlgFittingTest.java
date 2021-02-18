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
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class GeneticAlgFittingTest extends AbstractFittingTestBase {

	static final long SEED = 12357L;
	static {
		deltaFactor = new HashMap<PeakType, int[]>();
		deltaFactor.put(PeakType.GAUSSIAN, new int[] {1, 1});
		deltaFactor.put(PeakType.LORENTZIAN, new int[] {4, 6});
		deltaFactor.put(PeakType.PEARSON_VII, new int[] {1, 2});
		deltaFactor.put(PeakType.PSEUDO_VOIGT, new int[] {1, 7});
		deltaFactor.put(PeakType.VOIGT, new int[] {25, 11});
	}

	@Override
	public IOptimizer createOptimizer() {
		return new GeneticAlg(accuracy, SEED);
	}
}
