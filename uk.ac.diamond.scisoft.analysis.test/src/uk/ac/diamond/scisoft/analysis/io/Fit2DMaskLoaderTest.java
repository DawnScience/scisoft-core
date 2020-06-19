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

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.IDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

public class Fit2DMaskLoaderTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/Fit2dLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = IOTestUtils.setUpTestClass(Fit2DMaskLoaderTest.class, true);
	}
	
	@Test
	public void testReadMask1()  throws Exception {
		
		final String path = testFileFolder+"fit2d.msk";
		IDataHolder dataHolder = LoaderFactory.getData(path, null);
		
		IDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(3072,shape[0], 0.0);
		assertEquals(3072,shape[1], 0.0);
		assertEquals(true,data.getBoolean(0,0));
		assertEquals(true, data.getBoolean(1023, 1023));
		assertEquals(false, data.getBoolean(2047, 2047));
	}
	
	@Test
	public void testReadMask2()  throws Exception {
		
		final String path = testFileFolder+"fit2d2.msk";
		IDataHolder dataHolder = LoaderFactory.getData(path, null);
		
		IDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(2048,shape[0], 0.0);
		assertEquals(2048,shape[1], 0.0);
		assertEquals(true,data.getBoolean(0,0));
		assertEquals(false, data.getBoolean(80, 80));
		assertEquals(true, data.getBoolean(2047, 2047));
	}

}
