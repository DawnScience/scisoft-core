/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5.nexus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Set;

import org.eclipse.dawnsci.analysis.api.tree.Attribute;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.GroupNode;
import org.eclipse.dawnsci.hdf5.HDF5DatasetResource;
import org.eclipse.dawnsci.hdf5.HDF5DatatypeResource;
import org.eclipse.dawnsci.hdf5.HDF5FileResource;
import org.eclipse.dawnsci.hdf5.HDF5Resource;
import org.eclipse.dawnsci.hdf5.TestBase;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.dawnsci.nexus.NexusFile;
import org.eclipse.dawnsci.nexus.NexusUtils;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.eclipse.january.dataset.SliceND;
import org.junit.Test;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;

public class NexusFileHDF5Test extends TestBase {

	private final static String FILE_NAME = "test-scratch/test.nxs";

	@Test
	public void testStringsSwmrFixedLength() throws Exception {
		IDataset ods = DatasetFactory.createFromObject(new String[] { "String 1", "String 2", "String 3" });
		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.createAndOpenToWrite();
			ILazyWriteableDataset lds = new LazyWriteableDataset("data", String.class, new int[] { 0 },
					new int[] { ILazyWriteableDataset.UNLIMITED }, new int[] { 3 }, null);
			nf.createData("/test", lds, true);
			nf.flush();
		}
		try (HDF5Resource fRes = new HDF5FileResource(
				H5.H5Fopen(FILE_NAME, HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT));
				HDF5Resource dRes = new HDF5DatasetResource(
						H5.H5Dopen(fRes.getResource(), "/test/data", HDF5Constants.H5P_DEFAULT));
				HDF5Resource tRes = new HDF5DatatypeResource(H5.H5Dget_type(dRes.getResource()))) {
			assertFalse(H5.H5Tis_variable_str(tRes.getResource()));
		}
		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.openToWrite(true);
			ILazyWriteableDataset lds = nf.getData("/test/data").getWriteableDataset();
			lds.setSlice(null, ods, new int[] { 0 }, new int[] { 3 }, null);
		}
	}

	@Test
	public void testEditingDisabledWithSwmr() throws Exception {
		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.createAndOpenToWrite();
			nf.getGroup("/a/b", true);
			nf.getGroup("/a/c", true);
			IDataset ds = DatasetFactory.createFromObject(new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
			ds.setName("data");
			ILazyWriteableDataset lds = new LazyWriteableDataset("data", Integer.class, new int[] { 0 },
					new int[] { ILazyWriteableDataset.UNLIMITED }, new int[] { 64 }, null);
			nf.createData("/a/b/", lds, true);
			nf.link("/a/b", "/a/d");

			nf.activateSwmrMode();

			nf.getGroup("/a/b", true);
			nf.getData("/a/b/data");
			lds.setSlice(null, ds, new int[] { 0 }, new int[] { 8 }, null);
			try {
				nf.getGroup("/a/e", true);
				fail("Should not be able to create group in SWMR mode");
			} catch (NexusException e) {
				// pass
			}
			try {
				nf.createData("/a/c", ds, true);
				fail("Should not be able to create dataset in SWMR mode");
			} catch (NexusException e) {
				// pass
			}
			try {
				nf.link("/a/f", "/a/b");
				fail("Should not be able to create links in SWMR mode");
			} catch (NexusException e) {
				// pass
			}
			try {
				nf.linkExternal(new URI("nxfile:///tmp/file/that/does/not/exist#/x/y"), "/a/g", true);
				fail("Should not be able to create external links in SWMR mode");
			} catch (NexusException e) {
				// pass
			}
		}
	}

	@Test
	public void testChunkEstimation() throws Exception {
		checkChunkEstimation(null, null);

		checkChunkEstimation(new int[] { ILazyWriteableDataset.UNLIMITED, 2, 4 }, 64, 2, 4);

		assertThrows(IllegalArgumentException.class, () -> checkChunkEstimation(null, 16, 4, 1));
	}

	private void checkChunkEstimation(int[] maxShape, int... chunk) throws Exception {
		Dataset ds = DatasetFactory.createRange(8).reshape(4, 2, 1);
		if (maxShape == null) {
			maxShape = new int[] { ILazyWriteableDataset.UNLIMITED, 2, 1 };
		}
		try (NexusFile nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.createAndOpenToWrite();
			nf.getGroup("/a/b", true);
			ILazyWriteableDataset lds = new LazyWriteableDataset("data", Double.class, new int[] { 1, 1, 1 }, maxShape,
					chunk, null);
			nf.createData("/a/b/", lds, false);

			nf.activateSwmrMode();
			lds.setSlice(null, ds, new int[] { 0, 0, 0 }, new int[] { 4, 2, 1 }, null);
		}

		try (NexusFile nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.openToRead();
			DataNode ads = nf.getData("/a/b/data");
			TestUtils.assertDatasetEquals(ds, DatasetUtils.sliceAndConvertLazyDataset(ads.getDataset()));
		}
	}

	@Test
	public void testBooleanDataset() throws Exception {
		final boolean[] boolData = new boolean[] { true, false, false, true };
		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.createAndOpenToWrite();
			final GroupNode group = nf.getGroup("/a", true);
			final Dataset ds = DatasetFactory.createFromObject(boolData);
			assertEquals(Boolean.class, ds.getElementClass());
			assertEquals(BooleanDataset.class, ds.getClass());
			nf.createData(group, "b", ds);
			ILazyWriteableDataset lazy = NexusUtils.createLazyWriteableDataset("l", Boolean.class, new int[] {0,4}, new int[] {ILazyWriteableDataset.UNLIMITED, 4}, new int[] {1, 2});
			nf.createData(group, "l", lazy);
			lazy.setSlice(null, ds, new SliceND(new int[] {1,4}));
		}

		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.openToRead();
			final DataNode data = nf.getData("/a/b");
			assertNotNull(data);
			final ILazyDataset ds = data.getDataset();
			assertNotNull(ds);
			
			assertEquals(data.getAttributeNames(), Set.of("DLS_read_datatype"));
			final Attribute isBooleanAttr = data.getAttribute("DLS_read_datatype");
			assertNotNull(isBooleanAttr);
			Dataset isBoolean = DatasetUtils.convertToDataset(isBooleanAttr.getValue());
			assertEquals(1, isBoolean.getSize());
			assertEquals("boolean", isBoolean.getString());
			Dataset bd = DatasetUtils.sliceAndConvertLazyDataset(ds);
			assertEquals(Boolean.class, bd.getElementClass());
			assertEquals(BooleanDataset.class, bd.getClass());
			assertEquals(DatasetFactory.createFromObject(boolData), bd);
			
			final DataNode lazy = nf.getData("/a/l");
			bd = DatasetUtils.sliceAndConvertLazyDataset(lazy.getDataset());
			assertEquals(Boolean.class, bd.getElementClass());
			assertEquals(BooleanDataset.class, bd.getClass());
			assertEquals(DatasetFactory.createFromObject(boolData, 1, 4), bd);
		}
	}
}
