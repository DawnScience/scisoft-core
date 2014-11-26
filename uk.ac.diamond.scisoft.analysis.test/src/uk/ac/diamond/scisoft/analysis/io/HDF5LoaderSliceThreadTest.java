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
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.Tree;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class HDF5LoaderSliceThreadTest extends LoaderThreadTestBase {
	
	private static String filename;
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if (TestFileFolder == null) {
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		filename =  TestFileFolder + "NexusUITest/DCT_201006-good.h5";
	}

	private ILazyDataset dataset;

	@Before
	public void createLazyDataset() {
		Assert.assertTrue(new File(filename).canRead());
		HDF5Loader l = new HDF5Loader(filename);
		Tree t;
		try {
			t = l.loadTree();
		} catch (ScanFileHolderException e) {
			throw new IllegalArgumentException("Could not load tree");
		}
		NodeLink n = t.findNodeLink("/RawDCT/data");
		Assert.assertTrue(n.isDestinationData());
		DataNode d = (DataNode) n.getDestination();

		dataset = d.getDataset();
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
		final Slice[] nSlice = new Slice[] { new Slice(threadIndex, threadIndex+1), null, null };
		final IDataset s = dataset.getSlice(nSlice);
		Assert.assertEquals("Thd " + threadIndex + " is not correct size", 171*1692, s.getSize());
	}
}
