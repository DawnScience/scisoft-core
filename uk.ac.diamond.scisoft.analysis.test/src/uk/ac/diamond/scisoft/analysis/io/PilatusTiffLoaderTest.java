/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;


import static org.junit.Assert.assertTrue;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

/**
 *
 */
public class PilatusTiffLoaderTest {
	static String testfile1 = null;
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		testfile1 = TestFileFolder + "PilatusTiffLoaderTest/p678450.tif"; // 100k
	}

	/**
	 * Test Loading
	 * 
	 * @throws ScanFileHolderException if the file cannot be loaded
	 */
	@Test
	public void testLoadFile() throws ScanFileHolderException {
		DataHolder dh = new PilatusTiffLoader(testfile1).loadFile();
		assertTrue(dh.getNames().length >= 1);
	}
}

