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

import java.io.File;

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
}
