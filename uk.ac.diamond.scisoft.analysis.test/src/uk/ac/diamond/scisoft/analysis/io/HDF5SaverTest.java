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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyWriteableDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.dataset.SliceND;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.api.tree.DataNode;
import org.eclipse.dawnsci.analysis.api.tree.NodeLink;
import org.eclipse.dawnsci.analysis.api.tree.TreeFile;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.hdf5.HDF5Utils;
import org.junit.Test;

public class HDF5SaverTest {
	final static String TestFileFolder = "testfiles/gda/analysis/io/NexusLoaderTest/";

	@Test
	public void testSaving() throws Exception {
		String file = TestFileFolder + "save.h5";
		String path = "/e/a/b/";
		String name = "d";
		int[] shape = new int[] {2, 34};
		int[] mshape = new int[] {ILazyWriteableDataset.UNLIMITED, 34};
		int dtype = Dataset.INT16;

		File f = new File(file);
		if (f.exists())
			f.delete();

		HDF5Utils.createDataset(file, path, name, shape, mshape, new int[] {1, 34}, dtype, new int[] {-1}, false);

		assertEquals(-1, checkOutput(file, path, name, shape).getShort(0, 0));

		SliceND slice = new SliceND(shape, mshape, new Slice(1, 3), new Slice(null, null, 2));
		System.out.println(slice);
		IDataset data = DatasetFactory.createRange(1, AbstractDataset.calcSize(slice.getShape()) + 1, 1, dtype);
		System.out.println(data);
		data.setShape(slice.getShape());
		HDF5Utils.setDatasetSlice(file, path, name, slice, data);

		data = checkOutput(file, path, name, slice.getSourceShape());
		assertEquals(-1, data.getShort(0, 0));
		assertEquals(1, data.getShort(1, 0));
		assertEquals(-1, data.getShort(1, 1));
		assertEquals(2, data.getShort(1, 2));
	}

	private IDataset checkOutput(String file, String path, String name, int[] shape) throws ScanFileHolderException {
		TreeFile tree = new HDF5Loader(file).loadTree();
		NodeLink link = tree.findNodeLink(path + name);
		assertEquals(path, link.getPath());
		assertEquals(name, link.getName());
		assertTrue(link.isDestinationData());

		DataNode d = (DataNode) link.getDestination();
		ILazyDataset ds = d.getDataset();
		assertArrayEquals(shape, ds.getShape());
		assertEquals(Short.class, ds.elementClass());
		return ds.getSlice();
	}
}
