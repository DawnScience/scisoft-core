/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
//import org.junit.Before;
import org.junit.Test;

public class JCPDSLoaderTest {

//	@Before
//	public void setUp() throws Exception {
//	}

	@Test
	public void testLoadFile() {
		// TODO: Move the test file out of my home directory
		JCPDSLoader loadOTron = new JCPDSLoader("testfiles/diamond.jcpds");
		
		DataHolder dh = null;
		try {
			dh = loadOTron.loadFile();
		} catch (ScanFileHolderException sFHE) {
			fail("Exception in reading file: " + sFHE);
			return;
		}
		
		Dataset d = dh.getDataset("d");
		
		assertEquals(5, d.getSize());
		
		assertEquals(2.0600, d.getDouble(0), 5e-5);
		assertEquals(27., dh.getDataset("i").getDouble(1), 5e-1);
		assertEquals(3., dh.getDataset("h").getDouble(2), 5e-1);
		assertEquals(0., dh.getDataset("k").getDouble(3), 5e-1);
		assertEquals(1., dh.getDataset("l").getDouble(4), 5e-1);
		
	}

}
