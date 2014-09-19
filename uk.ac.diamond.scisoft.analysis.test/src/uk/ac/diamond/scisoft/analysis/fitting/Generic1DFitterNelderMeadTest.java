/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.NelderMead;

public class Generic1DFitterNelderMeadTest extends Generic1DFitterTestBase {

	static {
		name = "Nelder Mead simplex";
	}

	@Override
	public IOptimizer createOptimizer() {
		return new NelderMead(0.00001);
	}
}
