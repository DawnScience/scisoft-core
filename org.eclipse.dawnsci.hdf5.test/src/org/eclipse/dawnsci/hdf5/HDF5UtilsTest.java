/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.january.dataset.DatasetFactory;
import org.junit.Test;

public class HDF5UtilsTest {

	@Test
	public void testHasDataset() throws Throwable {
		File f = File.createTempFile("hdf5", ".h5");
		NexusFileHDF5 h5f = new NexusFileHDF5(f.getAbsolutePath());
		h5f.createAndOpenToWrite();
		h5f.createData("/entry/data", "data", DatasetFactory.createFromObject("test"), true);
		h5f.close();

		assertFalse(HDF5Utils.hasDataset(f.getAbsolutePath(), "/entry"));
		assertFalse(HDF5Utils.hasDataset(f.getAbsolutePath(), "/entry/data"));
		assertTrue(HDF5Utils.hasDataset(f.getAbsolutePath(), "/entry/data/data"));
		assertFalse(HDF5Utils.hasDataset(f.getAbsolutePath(), "/not/in/file"));

	}

	@Test
	public void testAttributesRequiresGroup() throws Throwable {
		File f = File.createTempFile("hdf5", ".h5");

		Dataset a = DatasetFactory.createFromObject("NXnote");
		a.setName("NX_class");

		Dataset b = DatasetFactory.createFromObject("NXdata");
		b.setName("NX_class");

		HDF5Utils.writeAttributes(f.getAbsolutePath(), "/entry/data", true, a);

		Dataset test = DatasetFactory.createFromObject("test");
		test.setName("test");
		HDF5Utils.writeDataset(f.getAbsolutePath(), "/entry/data", test);
		HDF5Utils.writeAttributes(f.getAbsolutePath(), "/entry/data/test", false, b);
		Dataset[] groupAttributes = HDF5Utils.readAttributes(f.getAbsolutePath(), "/entry/data");
		Dataset[] datasetAttributes = HDF5Utils.readAttributes(f.getAbsolutePath(), "/entry/data/test");
		assertTrue(HDF5Utils.hasDataset(f.getAbsolutePath(), "/entry/data/test"));

		assertEquals(a, groupAttributes[0]);
		assertEquals(1, groupAttributes.length);
		assertEquals(b, datasetAttributes[0]);
		assertEquals(1, datasetAttributes.length);
	}
}
