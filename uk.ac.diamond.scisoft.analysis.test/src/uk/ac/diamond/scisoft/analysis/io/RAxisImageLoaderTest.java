/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

public class RAxisImageLoaderTest {

	static String TestFileFolder;
	static String filename;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		filename = TestFileFolder + "RigakuLoaderTest/4_05_screen_0001.osc";
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoadFile() throws Exception {
		new RAxisImageLoader(filename).loadFile();
	}
	
	@Test
	public void testLoaderFactory() throws Exception {
		final IDataHolder dh = LoaderFactory.getData(filename, null);
		Assert.assertEquals("RAxis Image", dh.getNames()[0]);
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new RAxisImageLoader(filename).loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getFirstMetadata(IMetadata.class));
	}
}

