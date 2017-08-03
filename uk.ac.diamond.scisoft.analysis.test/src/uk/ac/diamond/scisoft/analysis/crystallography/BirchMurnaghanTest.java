/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BirchMurnaghanTest {
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				// Nickel
				{ 181.0, 5.2, 1.000, 1e-3, 0.0, 1e-12 }, { 181.0, 5.2, 1.010, 1e-3, 1.848, 1e-3 },
				{ 181.0, 5.2, 1.100, 1e-3, 22.1, 1e-1 }, { 181.0, 5.2, 2.000, 1e-3, 774., 1 },
				// FCC solid krypton
				{ 3.78, 5.71, 1.000, 1e-3, 0.0, 1e-12 }, { 3.78, 5.71, 1.010, 1e-3, 0.0387, 1e-4 },
				{ 3.78, 5.71, 1.100, 1e-3, 0.473, 1e-3 }, { 3.78, 5.71, 2.000, 1e-3, 18.54, 1e-2 },
				{ 3.78, 5.71, 5.520, 1e-3, 773.0, 1 }

		});
	}

	private double k0, k0p, pressure, pError, volume, vError;

	public BirchMurnaghanTest(double k0, double k0p, double v0v, double vError, double pressure, double pError) {
		this.k0 = k0;
		this.k0p = k0p;
		this.volume = 1./v0v;
		this.vError = vError/v0v/v0v;
		this.pressure = pressure;
		this.pError = pError;
	}

	@Test
	public void pressureTest() {
		assertEquals(pressure, BirchMurnaghanSolver.birchMurnaghanPressure(volume, k0, k0p), pError);
	}

	@Test
	public void volumeTest() {
		assertEquals(volume, BirchMurnaghanSolver.birchMurnaghanVolume(pressure, k0, k0p), vError);
	}
	
	
}
