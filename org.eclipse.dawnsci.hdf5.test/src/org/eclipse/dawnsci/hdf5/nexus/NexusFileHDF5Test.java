/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5.nexus;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.net.URI;

import org.eclipse.dawnsci.hdf5.HDF5DatasetResource;
import org.eclipse.dawnsci.hdf5.HDF5DatatypeResource;
import org.eclipse.dawnsci.hdf5.HDF5FileResource;
import org.eclipse.dawnsci.hdf5.HDF5Resource;
import org.eclipse.dawnsci.hdf5.TestBase;
import org.eclipse.dawnsci.nexus.NexusException;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyWriteableDataset;
import org.eclipse.january.dataset.LazyWriteableDataset;
import org.junit.Test;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;

public class NexusFileHDF5Test extends TestBase {

	private final static String FILE_NAME = "test-scratch/test.nxs";

	@Test
	public void testStringsSwmrFixedLength() throws Exception {
		IDataset ods = DatasetFactory.createFromObject(new String[] {"String 1", "String 2", "String 3"});
		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.createAndOpenToWrite();
			ILazyWriteableDataset lds = new LazyWriteableDataset("data",
					String.class,
					new int[] {0},
					new int[] {ILazyWriteableDataset.UNLIMITED},
					new int[] {3}, null);
			nf.createData("/test", lds, true);
			nf.flush();
		}
		try (HDF5Resource fRes = new HDF5FileResource(H5.H5Fopen(FILE_NAME,
					HDF5Constants.H5F_ACC_RDONLY,
					HDF5Constants.H5P_DEFAULT));
				HDF5Resource dRes = new HDF5DatasetResource(H5.H5Dopen(fRes.getResource(),
						"/test/data",
						HDF5Constants.H5P_DEFAULT));
				HDF5Resource tRes = new HDF5DatatypeResource(H5.H5Dget_type(dRes.getResource()))) {
			assertFalse(H5.H5Tis_variable_str(tRes.getResource()));
		}
		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.openToWrite(true);
			ILazyWriteableDataset lds = nf.getData("/test/data").getWriteableDataset();
			lds.setSlice(null, ods, new int[] {0}, new int[] {3}, null);
		}
	}

	@Test
	public void testEditingDisabledWithSwmr() throws Exception {
		try (NexusFileHDF5 nf = new NexusFileHDF5(FILE_NAME, true)) {
			nf.createAndOpenToWrite();
			nf.getGroup("/a/b", true);
			nf.getGroup("/a/c", true);
			IDataset ds = DatasetFactory.createFromObject(new int[] {1, 2, 3, 4, 5, 6, 7, 8});
			ds.setName("data");
			ILazyWriteableDataset lds = new LazyWriteableDataset("data",
					Integer.class,
					new int[] {0},
					new int[] {ILazyWriteableDataset.UNLIMITED},
					new int[] {64}, null);
			nf.createData("/a/b/", lds, true);
			nf.link("/a/b", "/a/d");

			nf.activateSwmrMode();

			nf.getGroup("/a/b", true);
			nf.getData("/a/b/data");
			lds.setSlice(null, ds, new int[] {0}, new int[] {8}, null);
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
}
