/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.metadata.IMetadata;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

/**
 * RubyLoaderTest Class
 */
public class CrysalisLoaderTest {
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		TestFileFolder += "CrysalisLoaderTest/";
	}

	/**
	 * Testing loading a file into a sfh
	 * 
	 * @throws Exception if the file couldn't be loaded
	 */
	@Test
	public void testLoadFile() throws Exception {
		DataHolder dh = new CrysalisLoader(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img").loadFile();
		assertTrue(dh.getNames().length >= 1);

	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoaderFactory() throws Exception {
		IDataHolder dh = LoaderFactory.getData(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img", null);
		assertNotNull(dh);
		assertTrue(dh.getNames().length >= 1);
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new CrysalisLoader(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img").loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getFirstMetadata(IMetadata.class));
	}
}
