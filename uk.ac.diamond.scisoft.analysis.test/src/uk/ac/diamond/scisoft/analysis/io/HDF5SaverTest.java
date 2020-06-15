/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.hdf5.HDF5FileFactory;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IDynamicDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.dataset.SliceNDIterator;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

public class HDF5SaverTest {
	final static String TestFileFolder = "test-scratch/";

	@BeforeClass
	public static void setUpClass() throws Exception {
		IOTestUtils.makeScratchDirectory(TestFileFolder);
	}

	@Test
	public void testSaving() throws Exception {
		String file = TestFileFolder + "save.h5";
		String path = "/e/a/b/";
		String name = "d";
		int[] shape = new int[] {2, 34};
		int[] mshape = new int[] {IDynamicDataset.UNLIMITED, 34};
		Class<? extends Dataset> clazz = ShortDataset.class;

		File f = new File(file);
		if (f.exists())
			f.delete();

		int value = -1;
		HDF5Utils.createDataset(file, path, name, shape, mshape, new int[] {1, 34}, clazz, new int[] {value}, false);

		assertEquals(value, checkOutput(file, path, name, shape).getShort(0, 0));

		SliceND slice = new SliceND(shape, mshape, new Slice(1, 3), new Slice(null, null, 2));
		System.out.println(slice);
		IDataset data = DatasetFactory.createRange(clazz, 1, ShapeUtils.calcSize(slice.getShape()) + 1, 1);
		System.out.println(data);
		data.setShape(slice.getShape());
		HDF5Utils.setDatasetSlice(file, path, name, slice, data);

		data = checkOutput(file, path, name, slice.getSourceShape());
		assertEquals(value, data.getShort(0, 0));
		assertEquals(value, data.getShort(1, 1));
		assertEquals(1, data.getShort(1, 0));
		assertEquals(2, data.getShort(1, 2));
	}

	private Dataset checkOutput(String file, String path, String name, int[] shape) throws ScanFileHolderException, DatasetException {
		TreeFile tree = new HDF5Loader(file).loadTree();
		NodeLink link = tree.findNodeLink(path + name);
		assertEquals(name, link.getName());
		assertTrue(link.isDestinationData());

		DataNode d = (DataNode) link.getDestination();
		ILazyDataset ds = d.getDataset();
		assertArrayEquals(shape, ds.getShape());
		assertEquals(Short.class, ds.getElementClass());
		return DatasetUtils.sliceAndConvertLazyDataset(ds);
	}

	@Test
	public void testSavingAll() throws Exception {
		test(false);
		test(true);
	}

	private void test(boolean initialize) throws Exception {
		String file = TestFileFolder + "all.h5";
		String path = "/e/a/";
		String name = "f";
		int[] shape = new int[] {1, 34};
		int[] mshape = new int[] {20, 34};
		Class<? extends Dataset> clazz = ShortDataset.class;

		HDF5FileFactory.deleteFile(file);

		int value = initialize ? -1 : 0;
		if (initialize) {
			HDF5Utils.createDataset(file, path, name, shape, mshape, shape, clazz, new int[] {value}, false);

			assertEquals(value, checkOutput(file, path, name, shape).getShort(0, 0));
		}

		SliceND slice = new SliceND(mshape, new Slice(null, null, 2));
		SliceNDIterator it = new SliceNDIterator(slice, 1);

		Dataset data = DatasetFactory.createRange(clazz, 1, shape[1]+1, 1);
		data.setShape(shape);
		slice = it.getCurrentSlice();
		while (it.hasNext()) {
			System.err.println(slice);
			HDF5Utils.setDatasetSlice(file, path, name, slice, data);
		}

		data = checkOutput(file, path, name, mshape);

		assertEquals(2, data.getInt(2, 1));
		assertEquals(value, data.getInt(1, 0));

		assertEquals(10*34*35/2, ((Number) data.getSlice(new Slice(null, null, 2)).sum()).longValue());
		assertEquals(value*data.getSize()/2, ((Number) data.getSlice(new Slice(1, null, 2)).sum()).longValue());
	}
}
