/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.junit.Test;

public class DiffractionCrystalEnvironmentTest {

	@Test
	public void testConversion() {
		// from CODATA 2018, (1eV)/(h c)
		double f = 1.602176634e-19 / (6.62607015e-34 * 2.99792458e8);
		double w = 620e-9;
		double e = 1/(f * w);
		assertEquals(2, e, 3e-4); // 620nm => 2eV

		assertEquals(e, 1e3*DiffractionCrystalEnvironment.calculateEnergy(w * 1e10), 1e-10);
		assertEquals(w*1e7, 1e-3*DiffractionCrystalEnvironment.calculateWavelength(e * 1e-3), 3e-10);
	}
}
