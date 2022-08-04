/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.hdf5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.dawnsci.hdf5.nexus.NexusFileHDF5;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.junit.Test;

public class HDF5UtilsTest extends TestBase {

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

		// can we detect empty file???
//		HDF5Utils.writeDataset(f.getAbsolutePath(), "/entry");

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

	@Test
	public void testExternalLink() throws Throwable {
		File sf = File.createTempFile("src", ".h5");
		String src = sf.getAbsolutePath();

		int[] shape0 = new int[] {3,2};
		HDF5Utils.createDataset(src, "/group0", "data0", shape0, shape0, shape0, ByteDataset.class, new short[] {130}, true);
		int[] shape1 = new int[] {4,3};
		HDF5Utils.createDataset(src, "/group1", "data1", shape1, shape1, shape1, FloatDataset.class, new float[] {-1.0f}, true);
		HDF5FileFactory.releaseFile(src, true);

		File df = File.createTempFile("dst", ".h5");
		String dst = df.getAbsolutePath();
		HDF5Utils.createDataset(dst, "/group0", "data0", shape0, shape0, shape0, ByteDataset.class, new byte[] {30}, true);
		HDF5Utils.createExternalLink(dst, "/data0", src, "/group0/data0");
		HDF5Utils.createExternalLink(dst, "/group1", src, "/group1");
		HDF5FileFactory.releaseFile(dst, true);

		System.err.printf("Created source %s and destination %s", src, dst);
		HDF5File f = HDF5FileFactory.acquireFile(dst, false);
		Dataset d = HDF5Utils.readDataset(f, "/group0/data0", new int[] {0, 0}, new int[] {3, 2}, new int[] {1,1}, 1, ByteDataset.class, true);
		TestUtils.assertDatasetEquals(DatasetFactory.zeros(ByteDataset.class, shape0).fill(30), d);

		d = HDF5Utils.readDataset(f, "/data0", new int[] {0, 0}, new int[] {3, 2}, new int[] {1,1}, 1, ByteDataset.class, true);
		TestUtils.assertDatasetEquals(DatasetFactory.zeros(ShortDataset.class, shape0).fill(130), d);

		d = HDF5Utils.readDataset(f, "/group1/data1", new int[] {0, 0}, new int[] {4, 3}, new int[] {1,1}, 1, FloatDataset.class, true);
		TestUtils.assertDatasetEquals(DatasetFactory.zeros(FloatDataset.class, shape1).fill(-1.0), d);
		HDF5FileFactory.releaseFile(dst, true);
	}
}
