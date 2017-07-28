/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BirchMurnaghanTest {
	
	class Moduluses {
		public double k0, k0p;
	}
	
	Moduluses ceria, nickel;
	
	@Before
	public void setUp() throws Exception {
		nickel = new Moduluses();
		nickel.k0 = 181.0;
		nickel.k0p = 5.2;
		ceria = new Moduluses();
		ceria.k0 = 3.8;
		ceria.k0p = 5.4;
	}

	@Test
	public void testBirchMurnaghanLinear() {
		fail("Not yet implemented");
	}

	@Test
	public void testBirchMurnaghanVolume() {
		fail("Not yet implemented");
	}

	@Test
	public void testBirchMurnaghanPressure() {
		fail("Not yet implemented");
	}

}
