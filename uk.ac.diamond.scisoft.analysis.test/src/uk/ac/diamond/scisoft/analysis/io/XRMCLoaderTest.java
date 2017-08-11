/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.IDataset;
import org.junit.Before;
import org.junit.Test;

public class XRMCLoaderTest {

	static final String testFileName = "testfiles/images/image.xrmc";
	XRMCLoader loader;
	@Before
	public void setUp() throws Exception {
		loader = new XRMCLoader(testFileName);
	}

	@Test
	public void testLoadFile() {
		
		IDataHolder dh;
		
		try {
			dh = loader.loadFile(); 
		} catch (Exception e) {
			fail("Exception loading file:" + e.toString());
			return;
		}
		
		IDataset data = dh.getDataset(0);
		assertArrayEquals("Error in array shape", new int[]{2,  1, 40, 40}, data.getShape());
		assertEquals("Maximum values differ", 720000., (double) data.max(), 10000.);
	}

}
