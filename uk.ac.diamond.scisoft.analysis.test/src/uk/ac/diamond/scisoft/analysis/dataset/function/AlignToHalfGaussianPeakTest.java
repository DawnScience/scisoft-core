/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import static org.eclipse.january.asserts.TestUtils.assertDatasetEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Random;
import org.eclipse.january.dataset.Slice;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class AlignToHalfGaussianPeakTest {
	private static IDataHolder holder;
	private static double[] pos;

	@BeforeClass
	public static void load() throws Exception {
		String elasticFile = "testfiles/i21-elastic.dat";
		holder = LoaderFactory.getData(elasticFile);
		pos = new double[] {958.4854088985544, 958.555139199572, 958.5259421253912};
	}

	@Test
	public void testAlignIndex() {
		Dataset index = DatasetFactory.createRange(holder.getDataset(0).getSize());

		List<? extends IDataset> result = testAlign(index, 940, 985, false, false);
		double[] expected = new double[pos.length];
		for (int i = 0; i < pos.length; i++) {
			expected[i] = pos[i] - pos[0];
		}
		assertArrayEquals(expected, calcOffset(index, result), 1e-5);
		result = testAlign(index, 940, 985, true, false);
		assertArrayEquals(pos, calcOffset(index, result), 1e-5);
	}

	@Test
	public void testAlign() {
		Dataset energy = DatasetUtils.convertToDataset(holder.getDataset(0));

		List<? extends IDataset> result = testAlign(energy, 5.155, 7.445, false, false);
		double edelta = energy.getDouble(1) - energy.getDouble(0);
		double[] expected = new double[pos.length];
		for (int i = 0; i < pos.length; i++) {
			expected[i] = edelta * (pos[i] - pos[0]);
		}
		assertArrayEquals(expected, calcOffset(energy, result), 1e-6);
		result = testAlign(energy, 5.155, 7.445, true, false);
		double first = pos[0];
		first = Maths.interpolate(energy, first);
		for (int i = 0; i < pos.length; i++) {
			expected[i] = expected[i] + first;
		}
		assertArrayEquals(expected, calcOffset(energy, result), 1e-6);
	}

	private List<? extends IDataset> testAlign(IDataset x, double lo, double hi, boolean force, boolean resample) {
		int imax = 2 * (holder.size() - 1);
		IDataset[] in = new IDataset[imax];
		for (int i = 0; i < imax; i += 2) {
			in[i] = x;
			in[i + 1] = holder.getDataset(i/2 + 1);
		}

		return testAlign(in, lo, hi, force, resample);
	}

	private List<? extends IDataset> testAlign(IDataset[] in, double lo, double hi, boolean force, boolean resample) {
		AlignToHalfGaussianPeak align = new AlignToHalfGaussianPeak(false);

		align.setPeakZone(lo, hi);
		align.setForceToPosition(force);
		align.setResample(resample);
		return align.value(in);
	}

	private double[] calcOffset(IDataset energy, List<? extends IDataset> result) {
		int imax = holder.size() - 1;
		double[] out = new double[imax];
		double e = energy.getDouble(0);

		for (int i = 0; i < imax; i++) {
			out[i] = e - result.get(2 * i).getDouble(0);
		}
		return out;
	}

	@Test
	public void testAlignAll() {
		double pos = 24.33;
		Gaussian g = new Gaussian(pos, 0.125, 1);
		IParameter p = g.getParameter(0);
		Dataset x = DatasetFactory.createRange(20, 30, 0.05);
		Random.seed(1239712356787l);

		double off = 10;
		double am = 0.02;
		Dataset ya = g.calculateValues(x).iadd(off).iadd(Random.randn(x.getShapeRef()).imultiply(am));

		double dx = 0.13;
		p.setValue(pos + dx);
		g.setDirty(true);
		Dataset yb = g.calculateValues(x).imultiply(1.2).iadd(off).iadd(Random.randn(x.getShapeRef()).imultiply(am));

		IDataset[] in = new IDataset[] {x, ya, x, yb};
		List<? extends IDataset> results;
		IDataset nx;
		double err = 2.5e-3;

		// align to 1st peak and shift x coords
		results = testAlign(in, 24, 25, false, false);
		assertTrue(results.get(0) == in[0]);
		assertTrue(results.get(1) == in[1]);
		assertTrue(results.get(3) == in[3]);
		nx = results.get(2);
		assertEquals(x.getDouble(0), nx.getDouble(0) + dx, err);

		// align to zero and shift x coords
		results = testAlign(in, 24, 25, true, false);
		assertTrue(results.get(1) == in[1]);
		assertTrue(results.get(3) == in[3]);
		nx = results.get(0);
		assertEquals(x.getDouble(0), nx.getDouble(0) + pos, err);
		nx = results.get(2);
		assertEquals(x.getDouble(0), nx.getDouble(0) + pos + dx, err);

		// align to 1st peak and resample y values
		results = testAlign(in, 24, 25, false, true);
		assertTrue(results.get(0) == in[0]);
		assertTrue(results.get(1) == in[1]);
		Slice s = new Slice(x.getSize()/2);
		assertDatasetEquals(DatasetUtils.convertToDataset(results.get(2)).getSliceView(s), DatasetUtils.convertToDataset(in[2]).getSliceView(s));
		assertArrayEquals(in[1].maxPos(true), results.get(3).maxPos(true));

		// align to zero and resample y values
		results = testAlign(in, 24, 25, true, true);
		assertTrue(results.get(1) == in[1]);
		nx = results.get(0);
		assertEquals(x.getDouble(0), nx.getDouble(0) + pos, err);
		assertDatasetEquals(DatasetUtils.convertToDataset(nx).getSliceView(s), DatasetUtils.convertToDataset(results.get(2)).getSliceView(s));
		assertArrayEquals(in[1].maxPos(true), results.get(3).maxPos(true));
	}
}
