/*
 * Copyright (c) 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

/**
 * Test for RGBText Loader
 */
public class RGBTextLoaderTest {

	private static String testScratchDirectoryName;

	/**
	 * Creates an empty directory for use by test code.
	 * 
	 * @throws Exception
	 *             if the directory is not created
	 */
	@BeforeClass
	static public void setUpClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(RGBTextLoaderTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
	}

	/**
	 * Test Loader
	 */
	public RGBTextLoaderTest() {

	}

	@Test
	public void testRGBTextLoaderLoop()  {
		boolean success = false;
		try {
			DataHolder dh = new SRSLoader("testfiles/images/56884_Baier_norm_red_1.rgb").loadFile();
			success = dh.size() != 0;
		} catch (Exception expected) {
			// do nothing
		}
		if (!success)
			fail("Test file 56884_Baier_norm_red_1.rgb should be parsed!");
	}

	@Test
	public void testNormalFileLoading() throws Exception {
		// clear state left over from any previous tests
		LoaderFactory.clear();
		IDataHolder dh = LoaderFactory.getData("testfiles/images/56884_Baier_norm_red_1.rgb", null);
		if (dh == null || dh.getNames().length < 1)
			throw new Exception();
		assertEquals("There is not the correct number of axis in the file", 7, dh.size());
		assertNotSame("The file does not contain NANs", Double.NaN, dh.getDataset(6).getDouble(1));
		assertEquals("The file does not contain data as well", 0.1, dh.getDataset(0).getDouble(1), 1.);
	}

	@Test
	public void testFileWithNans() throws Exception {
		// clear state left over from any previous tests
		LoaderFactory.clear();
		IDataHolder dh = LoaderFactory.getData("testfiles/images/56420_map-test_xsp3_1.rgb", null);
		if (dh == null || dh.getNames().length < 1)
			throw new Exception();
		assertEquals("There is not the correct number of axis in the file", 8, dh.size());
		assertEquals("The file does not contain NANs", Double.NaN, dh.getDataset(7).getDouble(6,18), 1.);
		assertEquals("The file does not contain data at the expected position", 20923.0, dh.getDataset(7).getDouble(6,17), 1.);

	}

	@Test
	public void testFileWithExtraHeaders() throws Exception {
		// clear state left over from any previous tests
		LoaderFactory.clear();
		IDataHolder dh = LoaderFactory.getData("testfiles/images/55475_As_1.rgb", null);
		if (dh == null || dh.getNames().length < 1)
			throw new Exception();
		assertEquals("There is not the correct number of axis in the file", 5, dh.size());
		assertEquals("The extra header does not only contain NANs", Double.NaN, dh.getDataset(4).getDouble(0, 0), 1.);
		assertEquals("The extra header does not only contain NANs", Double.NaN, dh.getDataset(4).getDouble(2, 100), 1.);
		assertEquals("The file does not contain data at the expected position", 68.0, dh.getDataset(2).getDouble(0, 25), 1.);
	}
}
