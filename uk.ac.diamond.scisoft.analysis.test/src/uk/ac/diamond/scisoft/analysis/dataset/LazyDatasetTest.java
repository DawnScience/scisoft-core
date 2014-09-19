/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.ILazyLoader;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.LazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.junit.Assert;
import org.junit.Test;

public class LazyDatasetTest {

	private void setShape(String msg, boolean well, LazyDataset l, int... shape) {
		try {
			l.setShape(shape);
			if (well)
				System.out.println("Succeeded setting shape for " + msg);
			else
				Assert.fail("Should have thrown exception for " + msg);
		} catch (IllegalArgumentException iae) {
			// do nothing
			if (well)
				Assert.fail("Unexpected exception for " + msg);
			else
				System.out.println("Correctly failed setting shape for " + msg);
		} catch (Exception e) {
			msg += ": " + e.getMessage();
			if (well)
				Assert.fail("Unexpected exception for " + msg);
			else
				Assert.fail("Thrown wrong exception for " + msg);
		}
	}

	@Test
	public void testSetShape() {
		LazyDataset ld = new LazyDataset("", Dataset.INT, new int[] {1, 2, 3, 4}, null);

		setShape("check on same rank", true, ld, 1, 2, 3, 4);
		setShape("check on same rank", false, ld, 1, 2, 3, 5);

		setShape("check on greater rank", true, ld, 1, 1, 1, 2, 3, 4);
		setShape("check on greater rank", false, ld, 1, 2, 2, 3, 5);
		setShape("check on greater rank", false, ld, 2, 1, 2, 3, 4);

		setShape("check on greater rank", true, ld, 2, 3, 4, 1, 1, 1);
		setShape("check on greater rank", true, ld, 1, 1, 2, 3, 4, 1, 1, 1);

		setShape("check on lesser rank", true, ld, 2, 3, 4);
		setShape("check on lesser rank", false, ld, 3, 4);
		setShape("check on lesser rank", false, ld, 2, 3);

		ld = new LazyDataset("", Dataset.INT, new int[] {2, 3, 4, 1}, null);
		setShape("check on lesser rank", true, ld, 2, 3, 4);

		ld = new LazyDataset("", Dataset.INT, new int[] {1, 2, 3, 4, 1}, null);
		setShape("check on lesser rank", true, ld, 2, 3, 4);
	}

	@Test
	public void testGetSlice() {
		final int[] shape = new int[] {1, 2, 3, 4};
		final Dataset d = Random.randn(shape);
		LazyDataset ld = new LazyDataset("", Dataset.INT, shape, new ILazyLoader() {
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

		Slice[] slice;
		slice = new Slice[]{null, new Slice(1), null, new Slice(1, 3)};
		Assert.assertEquals("Full slice", d, ld.getSlice());
		Assert.assertEquals("Full slice", d, ld.getSlice((Slice) null));
		Assert.assertEquals("Full slice", d, ld.getSlice((Slice) null, null));
		Assert.assertEquals("Full slice", d, ld.getSlice(null, null, null));
		Assert.assertEquals("Full slice", d, ld.getSlice(null, null, new int[] {1, 1, 1, 1}));
		Assert.assertEquals("Full slice", d, ld.getSlice(new int[4], null, new int[] {1, 1, 1, 1}));
		Assert.assertEquals("Full slice", d, ld.getSlice(new int[4], new int[] { 1, 2, 3, 4 }, new int[] { 1, 1, 1, 1 }));
		Assert.assertEquals("Part slice", d.getSlice(slice), ld.getSlice(slice));

		Dataset nd;
		ld.setShape(1, 1, 1, 2, 3, 4);
		nd = d.getView();
		nd.setShape(1, 1, 1, 2, 3, 4);
		Assert.assertEquals("Full slice", nd, ld.getSlice());
		slice = new Slice[]{null, null, null, new Slice(1), null, new Slice(1, 3)};
		Assert.assertEquals("Part slice", nd.getSlice(slice), ld.getSlice(slice));

		ld.setShape(2, 3, 4);
		nd = d.getView();
		nd.setShape(2, 3, 4);
		Assert.assertEquals("Full slice", nd, ld.getSlice());
		slice = new Slice[]{new Slice(1), null, new Slice(1, 3)};
		Assert.assertEquals("Part slice", nd.getSlice(slice), ld.getSlice(slice));

		ld.setShape(2, 3, 4, 1, 1, 1);
		nd = d.getView();
		nd.setShape(2, 3, 4, 1, 1, 1);
		Assert.assertEquals("Full slice", nd, ld.getSlice());
		slice = new Slice[]{new Slice(1), null, new Slice(1, 3), null, null, null};
		Assert.assertEquals("Part slice", nd.getSlice(slice), ld.getSlice(slice));

		ld.setShape(1, 2, 3, 4, 1, 1, 1);
		nd = d.getView();
		nd.setShape(1, 2, 3, 4, 1, 1, 1);
		Assert.assertEquals("Full slice", nd, ld.getSlice());
		slice = new Slice[]{null, new Slice(1), null, new Slice(1, 3), null, null, null};
		Assert.assertEquals("Part slice", nd.getSlice(slice), ld.getSlice(slice));
	}

	@Test
	public void testGetSliceView() {
		final int[] shape = new int[] {6, 2, 3, 4};
		final Dataset d = Random.randn(shape);
		LazyDataset ld = new LazyDataset("", Dataset.INT, shape, new ILazyLoader() {
			@Override
			public boolean isFileReadable() {
				return true;
			}
			
			@Override
			public Dataset getDataset(IMonitor mon, int[] shape, int[] start, int[] stop, int[] step)
					throws Exception {
				return d.getSliceView(start, stop, step);
			}
		});

		Slice[] slice;
		slice = new Slice[]{new Slice(1, null, 3), new Slice(1), null, new Slice(1, 3)};
		ILazyDataset l = ld.getSliceView(null, shape, null);
		System.out.println(l);
		Assert.assertEquals("Full slice", d, l.getSlice());
		l = ld.getSliceView(slice);
		System.out.println(l);
		Assert.assertEquals("Part slice", d.getSlice(slice), l.getSlice());

		l = ld.getSliceView();
		System.out.println(l);
		Assert.assertEquals("Full slice", d, l.getSlice());
		l = ld.getSliceView(slice);
		System.out.println(l);
		Assert.assertEquals("Part slice", d.getSlice(slice), l.getSlice());
	}
}
