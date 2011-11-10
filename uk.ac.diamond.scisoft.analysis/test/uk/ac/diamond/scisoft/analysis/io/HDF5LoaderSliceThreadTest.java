/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
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
