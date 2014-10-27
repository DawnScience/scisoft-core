/*
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.metadata;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.junit.Test;

public class AxesMetadataTest {

	ILazyDataset createRandomLazyDataset(String name, final int[] shape, final int dtype) {
		LazyDataset ld = new LazyDataset(name, dtype, shape, new ILazyLoader() {
			final Dataset d = Random.randn(shape).cast(dtype);
			@Override
			public boolean isFileReadable() {
				return true;
			}
			@Override
			public Dataset getDataset(IMonitor mon, int[] shape, int[] start, int[] stop, int[] step)
					throws Exception {
				return d.getSlice(mon, start, stop, step);
			}
		});
		return ld;
	}

	@Test
	public void testAxesMetadata() {
		final int[] shape = new int[] {1, 1, 2, 3, 4, 1, 1};

		int r = shape.length;
		AxesMetadataImpl amd = new AxesMetadataImpl(r);
		for (int i = 0; i < r; i++) {
			DoubleDataset[] array = new DoubleDataset[i + 1];
			for (int j = 0; j < (i + 1) ; j++) {
				array[j] = Random.randn(shape);
			}			
			amd.setAxis(i, array);
		}


		ILazyDataset dataset = createRandomLazyDataset("Main", shape, Dataset.INT32);
		dataset.addMetadata(amd);

		try {
			AxesMetadata tmd = dataset.getMetadata(AxesMetadata.class).get(0);
			assertEquals(amd, tmd);
			assertEquals(r, tmd.getAxes().length);
			for (int i = 0; i < r; i++) {
				assertEquals(i + 1, tmd.getAxis(i).length);
			}
			assertEquals(r, tmd.getAxis(0)[0].getRank());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		dataset.squeeze();
		r = dataset.getRank();
		try {
			AxesMetadata tmd = dataset.getMetadata(AxesMetadata.class).get(0);
			assertEquals(amd, tmd);
			assertEquals(r, tmd.getAxes().length);
			for (int i = 0; i < r; i++) {
				assertEquals(i + 3, tmd.getAxis(i).length);
			}
			assertEquals(r, tmd.getAxis(0)[0].getRank());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		Slice[] slice = new Slice[] {new Slice(1), null, new Slice(null, null, 2)};
		ILazyDataset sliced = dataset.getSliceView(slice);
		int[] nshape = new int[] {1, 3, 2};
		assertArrayEquals(nshape, sliced.getShape());
		try {
			AxesMetadata tmd = sliced.getMetadata(AxesMetadata.class).get(0);
			assertEquals(sliced.getRank(), tmd.getAxes().length);
			assertEquals(3, tmd.getAxis(0).length);
			assertArrayEquals(nshape, tmd.getAxis(0)[0].getShape());
			assertEquals(4, tmd.getAxis(1).length);
			assertArrayEquals(nshape, tmd.getAxis(1)[0].getShape());
			assertEquals(5, tmd.getAxis(2).length);
			assertArrayEquals(nshape, tmd.getAxis(2)[0].getShape());
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		nshape = new int[] {3, 2, 1, 1};
		sliced.setShape(nshape);
		try {
			AxesMetadata tmd = sliced.getMetadata(AxesMetadata.class).get(0);
			assertEquals(sliced.getRank(), tmd.getAxes().length);
			assertArrayEquals(nshape, tmd.getAxis(0)[0].getShape());
			assertArrayEquals(null, tmd.getAxis(2));
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}

		nshape = new int[] {1, 1, 3, 2};
		sliced.setShape(nshape);
		try {
			AxesMetadata tmd = sliced.getMetadata(AxesMetadata.class).get(0);
			assertEquals(sliced.getRank(), tmd.getAxes().length);
			assertArrayEquals(nshape, tmd.getAxis(2)[0].getShape());
			assertArrayEquals(null, tmd.getAxis(0));
		} catch (Exception e) {
			fail("Should not fail: " + e);
		}
	}

	@Test
	public void testAxesMetadataReshape() {
		final int[] shape = new int[] { 1, 2, 3, 1 };
		final int[] reshape = new int[] { 1, 1, 2, 3, 1 };

		int r = shape.length;
		int[] nShape = new int[r];
		AxesMetadataImpl amd = new AxesMetadataImpl(r);
		for (int i = 0; i < r; i++) {
			Arrays.fill(nShape, 1);
			nShape[i] = shape[i];
			DoubleDataset array = Random.randn(nShape);
			amd.setAxis(i, new ILazyDataset[] { array });
		}
		ILazyDataset dataset = createRandomLazyDataset("Main", shape, Dataset.INT32);
		dataset.addMetadata(amd);
		dataset.setShape(reshape);
	}
}
