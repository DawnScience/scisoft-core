/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.apache.commons.lang.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class RAxisImageLoaderTest {

	static String TestFileFolder;
	static String filename;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		filename = TestFileFolder+"/RigakuLoaderTest/4_05_screen_0001.osc";
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
		if (!dh.getNames()[0].equals("RAxis Image")) throw new Exception();
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new RAxisImageLoader(filename).loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
	}
}

