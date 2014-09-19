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
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.hdf5.HDF5Dataset;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5File;
import uk.ac.diamond.scisoft.analysis.hdf5.HDF5NodeLink;

public class HDF5LoaderSliceThreadTest extends LoaderThreadTestBase {
	
	private static String filename = System.getProperty("GDALargeTestFilesLocation")+"/NexusUITest/DCT_201006-good.h5";

	private ILazyDataset dataset;

	@Before
	public void createLazyDataset() {
		Assert.assertTrue(new File(filename).canRead());
		HDF5Loader l = new HDF5Loader(filename);
		HDF5File t;
		try {
			t = l.loadTree();
		} catch (ScanFileHolderException e) {
			throw new IllegalArgumentException("Could not load tree");
		}
		HDF5NodeLink n = t.findNodeLink("/RawDCT/data");
		Assert.assertTrue(n.isDestinationADataset());
		HDF5Dataset d = (HDF5Dataset) n.getDestination();

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
