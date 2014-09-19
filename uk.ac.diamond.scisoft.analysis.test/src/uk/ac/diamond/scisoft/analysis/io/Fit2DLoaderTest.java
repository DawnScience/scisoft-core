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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class Fit2DLoaderTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/Fit2dLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(Fit2DLoaderTest.class.getCanonicalName());

	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
		
	}
	
	@Test
	public void testF2dMetaLoader()  throws Exception {
		
		IMetadata meta = LoaderFactory.getMetadata(testFileFolder+"test1.f2d", null);
 		
		assertEquals(meta.getMetaValue("Dim_1"), "2048");
		assertEquals(meta.getMetaValue("Dim_2"), "2048");
	}
	
	@Test
	public void testF2dDataLoader()  throws Exception {
		
		final String path = testFileFolder+"/test1.f2d";
		IDataHolder dataHolder = LoaderFactory.getData(path, null);
 		
		IDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(2048,shape[0], 0.0);
		assertEquals(2048,shape[1], 0.0);
		assertEquals(0.0,data.getDouble(0,0), 0.0);
		assertEquals(2572.0, data.getDouble(1023, 1023), 0.0);
		assertEquals(0.0, data.getDouble(2047, 2047), 0.0);
	}

}
