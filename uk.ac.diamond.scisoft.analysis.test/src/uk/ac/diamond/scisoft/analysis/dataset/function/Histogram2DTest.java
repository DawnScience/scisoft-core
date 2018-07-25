/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.eclipse.january.asserts.TestUtils.assertDatasetEquals;

import java.util.List;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Random;
import org.junit.BeforeClass;
import org.junit.Test;

public class Histogram2DTest {

	private static DoubleDataset x;
	private static DoubleDataset y;

	@BeforeClass
	public static void setUp() {
		Random.seed(1238129l);
		x = Random.randn(1024).imultiply(50);
		y = Random.randn(1024);
	}

	@Test
	public void testHistogram2D() {
		Histogram2D h2d = new Histogram2D(32, 10);
		List<Dataset> r = h2d.value(x, y);

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {32, 10}, h.getShapeRef());
		assertArrayEquals(new int[] {32 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {10 + 1}, q.getShapeRef());

		assertEquals(x.min(true).doubleValue(), p.getDouble(), 1e-8);
		assertEquals(x.max(true).doubleValue(), p.getDouble(-1), 1e-8);
		assertEquals(y.min(true).doubleValue(), q.getDouble(), 1e-8);
		assertEquals(y.max(true).doubleValue(), q.getDouble(-1), 1e-8);

		assertEquals(x.getSize(), h.sum(true));
		assertEquals(32 / 2, h.sum(1, true).argMax(true));
		assertEquals(10 / 2 - 1, h.sum(0, true).argMax(true));
	}

	@Test
	public void testHistogram2DEdges() {
		Histogram2D h2d = new Histogram2D(2);
		Dataset nx = DatasetFactory.createFromObject(new int[] {0, 1, 2, 1});
		Dataset ny = DatasetFactory.createFromObject(new int[] {2, 3, 1, 3});
		List<Dataset> r = h2d.value(nx, ny);

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {2, 2}, h.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, q.getShapeRef());

		assertEquals(nx.min(true).doubleValue(), p.getDouble(), 1e-8);
		assertEquals(nx.max(true).doubleValue(), p.getDouble(-1), 1e-8);
		assertEquals(ny.min(true).doubleValue(), q.getDouble(), 1e-8);
		assertEquals(ny.max(true).doubleValue(), q.getDouble(-1), 1e-8);

		assertEquals(nx.getSize(), h.sum(true));
		assertDatasetEquals(DatasetFactory.createFromObject(new int[] {0, 1, 1, 2}).reshape(2, 2), h);
	}

	@Test
	public void testHistogram2DUnequalEdgeSpans() {
		Histogram2D h2d = new Histogram2D(2);
		h2d.setBinEdges(DatasetFactory.createFromObject(new double[] {0, 1, 3}),
				DatasetFactory.createFromObject(new double[] {1, 3, 4}));
		Dataset nx = DatasetFactory.createFromObject(new int[] {0, 1, 2, 1});
		Dataset ny = DatasetFactory.createFromObject(new int[] {2, 3, 1, 3});
		List<Dataset> r = h2d.value(nx, ny);

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {2, 2}, h.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {2 + 1}, q.getShapeRef());

		assertEquals(nx.getSize(), h.sum(true));
		assertDatasetEquals(DatasetFactory.createFromObject(new int[] {1, 0, 1, 2}).reshape(2, 2), h);
	}

	@Test
	public void testHistogram2DWeights() {
		Histogram2D h2d = new Histogram2D(32, 10);
		DoubleDataset w = DatasetFactory.zeros(x);
		w.fill(0.25);
		h2d.setWeights(w);
		List<Dataset> r = h2d.value(x, y);

		assertEquals(3, r.size());

		Dataset h = r.get(0);
		Dataset p = r.get(1);
		Dataset q = r.get(2);
		assertArrayEquals(new int[] {32, 10}, h.getShapeRef());
		assertArrayEquals(new int[] {32 + 1}, p.getShapeRef());
		assertArrayEquals(new int[] {10 + 1}, q.getShapeRef());

		assertEquals(x.min(true).doubleValue(), p.getDouble(), 1e-8);
		assertEquals(x.max(true).doubleValue(), p.getDouble(-1), 1e-8);
		assertEquals(y.min(true).doubleValue(), q.getDouble(), 1e-8);
		assertEquals(y.max(true).doubleValue(), q.getDouble(-1), 1e-8);

		assertEquals(w.sum(true), h.sum(true));
		assertEquals(32 / 2, h.sum(1, true).argMax(true));
		assertEquals(10 / 2 - 1, h.sum(0, true).argMax(true));
	}
}
