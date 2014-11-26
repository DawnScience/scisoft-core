/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import junit.framework.Assert;

import org.apache.commons.lang.SerializationUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

/**
 * RubyLoaderTest Class
 */
public class CrysalisLoaderTest {
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if (TestFileFolder == null) {
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		TestFileFolder += "CrysalisLoaderTest/";
	}

	/**
	 * Testing loading a file into a sfh
	 * 
	 * @throws Exception if the file couldn't be loaded
	 */
	@Test
	public void testLoadFile() throws Exception {
		new CrysalisLoader(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img").loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoaderFactory() throws Exception {
		if (LoaderFactory.getData(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img", null) == null) throw new Exception();
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new CrysalisLoader(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img").loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
	}
}
