/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.SliceObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class NexusLoaderSliceThreadTest extends LoaderThreadTestBase {

	static String TestFileFolder;
	private static String filename;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if (TestFileFolder == null) {
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		filename =  TestFileFolder + "NexusUITest/DCT_201006-good.h5";
	}

	private SliceObject sliceObject;

	@Before
	public void createSliceObject() {
		sliceObject = new SliceObject();
		sliceObject.setName("/RawDCT/NXdata/data");
		sliceObject.setPath(filename);
		sliceObject.setSliceStart(new int[] { 0, 0, 1 });
		sliceObject.setSliceStop(new int[] { 61, 171, 2 });
	}
	
	@Test
	public void testNoThread() throws Exception{
		doTestOfDataSet(1);
	}


	@Override
	@Test
	public void testInTestThread() throws Exception {
		super.testInTestThread();
	}

	@Override
	@Test
	public void testWithTenThreads() throws Exception {
		super.testWithTenThreads();
	}

	@Test
	public void testWithNThreads() throws Exception {
		super.testWithNThreads(60);
	}

	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {

		Assert.assertTrue(new File(filename).canRead());

		final IDataHolder  dh = LoaderFactory.getData(sliceObject.getPath(), false, null);
		final ILazyDataset ld = dh.getLazyDataset(sliceObject.getName());
		IDataset  slice = ld.getSlice(new int[] { 0, 0, threadIndex + 10 }, 
				                      new int[] { 61, 171, threadIndex + 11 }, 
				                      new int[]{1,1,1});
		ILazyDataset squeeze = slice.squeeze();
		Assert.assertTrue(squeeze.getSize() == (61 * 171));
	}
}
