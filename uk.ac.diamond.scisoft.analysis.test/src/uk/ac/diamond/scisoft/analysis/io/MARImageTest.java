/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.apache.commons.lang.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

/**
 */
public class MARImageTest {
	static String TestFileFolder;

	static String testfile1, testfile2, testfile3, testfile4;

	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if (TestFileFolder == null) {
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		TestFileFolder += "MARImageTest/";
		testfile1 = TestFileFolder + "in1187_sample1.mccd";
		testfile2 = TestFileFolder + "mar225_001.mccd";
		testfile3 = TestFileFolder + "mar165_001.mccd";
		testfile4 = TestFileFolder + "ins2-foc_MS_2_001.mccd";
	}

	@Test
	public void testLoaderFactory() throws Exception {
		IDataHolder dh = LoaderFactory.getData(testfile1, null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
        		
        dh = LoaderFactory.getData(testfile2, null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
        
        dh = LoaderFactory.getData(testfile3, null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
        
        dh = LoaderFactory.getData(testfile4, null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
        
		assertTrue(dh.getName(0).contains(AbstractFileLoader.DEF_IMAGE_NAME));
		
		IDataset data = dh.getDataset(0);
		assertEquals(299, data.getDouble(1500, 1514), 1);
		assertEquals(89, data.getDouble(3009, 2168), 1);
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception
	 *             if the test fails
	 */
	@Test
	public void testLoadFile() throws Exception {
		new MARLoader(testfile1).loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception
	 *             if the test fails
	 */
	@Test
	public void testLoadFile2() throws Exception {
		new MARLoader(testfile2).loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception
	 *             if the test fails
	 */
	@Test
	public void testLoadFile3() throws Exception {
		new MARLoader(testfile3).loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception
	 *             if the test fails
	 */
	@Test
	public void testLoadFile4() throws Exception {
		new MARLoader(testfile4).loadFile();
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new MARLoader(testfile2).loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
	}
}
