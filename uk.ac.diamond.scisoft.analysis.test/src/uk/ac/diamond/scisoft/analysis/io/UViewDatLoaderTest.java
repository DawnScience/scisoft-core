/*
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Test;

public class UViewDatLoaderTest {

	@Test
	public void testMovie_22() throws Exception {
		final String testfile = "testfiles/gda/analysis/io/UViewDatLoaderTest/movie_22_000001.dat";
 		final IDataHolder dh = new UViewDatLoader(testfile).loadFile();

		// Test some of the data
		if (dh.getDataset("image-01").getShape()[0]!=512) throw new Exception("The width value of the dataset should be 512!");
		if (dh.getDataset("image-01").getShape()[1]!=512) throw new Exception("The height value of the dataset should be 512!");
		if (dh.getDataset("image-01").getDouble(new int[] {0, 0})!=202.0d) throw new Exception("The first value of the dataset should be 202!");
		if (dh.getDataset("image-01").getDouble(new int[] {511, 510})!=236.0d) throw new Exception("The one before the last value of the dataset should be 236!");

	}

	@Test
	public void testMetadata() throws Exception {
		final String testfile = "testfiles/gda/analysis/io/UViewDatLoaderTest/movie_22_000001.dat";
		DataHolder loader = (DataHolder) new UViewDatLoader(testfile).loadFile();
		Dataset data = loader.getDataset(0);
		IMetadata metadata = data.getFirstMetadata(IMetadata.class);
		assertEquals(8, metadata.getMetaValue(UViewDatLoader.BinaryKey.VERSION.toString()));
		assertEquals(1657, metadata.getMetaValue(UViewDatLoader.BinaryKey.TOTALHEADERSIZE.toString()));
		assertEquals(true, metadata.getMetaValue(UViewDatLoader.BinaryKey.HASMARKUP.toString()));
	}

	@Test
	public void testSerializability() throws Exception {
		final String testfile = "testfiles/gda/analysis/io/UViewDatLoaderTest/movie_22_000001.dat";
		DataHolder loader = (DataHolder) new UViewDatLoader(testfile).loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getFirstMetadata(IMetadata.class));
	}

	@Test
	public void testLazyLoading() throws Exception {
		List<String> paths = new ArrayList<String>(3);
		for (int i = 0; i < 3; i++) {
			paths.add(i, "testfiles/gda/analysis/io/UViewDatLoaderTest/movie_22_00000" + (i+1) + ".dat");
		}
		ImageStackLoader loader = new ImageStackLoader(paths, null);
		ILazyDataset lazy = loader.createLazyDataset("Image stack");

		assertArrayEquals("Shapes are the same", loader.getShape(), lazy.getShape());
		IDataset slice = lazy.getSlice(new Slice(1)).squeeze();
		if (slice.getShape()[0]!=512) throw new Exception("The width value of the dataset should be 512!");
		if (slice.getShape()[1]!=512) throw new Exception("The height value of the dataset should be 512!");
		if (slice.getDouble(new int[] {0, 0})!=202.0d) throw new Exception("The first value of the dataset should be 202!");
		if (slice.getDouble(new int[] {511, 510})!=236.0d) throw new Exception("The one before the last value of the dataset should be 236!");
	}
}
