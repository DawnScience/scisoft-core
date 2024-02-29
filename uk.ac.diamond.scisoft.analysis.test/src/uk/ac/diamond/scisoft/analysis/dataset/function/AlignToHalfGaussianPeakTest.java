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
import static org.junit.Assert.assertSame;

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

		Dataset[] result = testAlign(index, 940, 985, false, false);
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

		Dataset[] result = testAlign(energy, 5.155, 7.445, false, false);
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

	private Dataset[] testAlign(Dataset x, double lo, double hi, boolean force, boolean resample) {
		int imax = 2 * (holder.size() - 1);
		Dataset[] in = new Dataset[imax];
		for (int i = 0; i < imax; i += 2) {
			in[i] = x;
			in[i + 1] = DatasetUtils.convertToDataset(holder.getDataset(i/2 + 1));
		}

		return testAlign(in, lo, hi, force, resample);
	}

	private Dataset[] testAlign(Dataset[] in, double lo, double hi, boolean force, boolean resample) {
		AlignToHalfGaussianPeak align = new AlignToHalfGaussianPeak(false);

		align.setPeakZone(lo, hi);
		List<Double> posn = align.value(in);
		return AlignToHalfGaussianPeak.alignToPositions(resample, force || (lo <= 0 && hi >= 0), 0, posn, in);
	}

	private double[] calcOffset(IDataset energy, Dataset[] result) {
		int imax = holder.size() - 1;
		double[] out = new double[imax];
		double e = energy.getDouble(0);

		for (int i = 0; i < imax; i++) {
			out[i] = e - result[2 * i].getDouble(0);
		}
		return out;
	}

	@Test
	public void testAlignAll() {
		double pos = 24.33;
		Gaussian g = new Gaussian(pos, 0.125, 1);
		IParameter p = g.getParameter(0);
		Dataset xa = DatasetFactory.createRange(20, 30, 0.05);
		Random.seed(1239712356787l);

		double off = 10;
		double am = 0.02;
		Dataset ya = g.calculateValues(xa).iadd(off).iadd(Random.randn(xa.getShapeRef()).imultiply(am));

		double dx = 0.13;
		p.setValue(pos + dx); // shift peak
		g.setDirty(true);
		double sx = 0.73;
		Dataset xb = Maths.add(xa, sx); // shift evaluation locations
		Dataset yb = g.calculateValues(xb).imultiply(1.2).iadd(off).iadd(Random.randn(xb.getShapeRef()).imultiply(am));

		Dataset[] in = new Dataset[] {xa, ya, xb, yb};
		Dataset[] results;
		double err = 2.5e-3;

		// align to 1st peak and shift x coords
		results = testAlign(in, 24, 25, false, false);
		assertSame(in[0], results[0]);
		assertSame(in[1], results[1]);
		assertSame(in[3], results[3]);
		assertEquals(xb.getDouble(0), results[2].getDouble(0) + dx, err);

		// align to zero and shift x coords
		results = testAlign(in, 24, 25, true, false);
		assertSame(in[1], results[1]);
		assertSame(in[3], results[3]);
		assertEquals(xa.getDouble(0), results[0].getDouble(0) + pos, err);
		assertEquals(xb.getDouble(0), results[2].getDouble(0) + pos + dx, err);
		assertEquals(results[0].getDouble(0), results[2].getDouble(0) - sx + dx, err);

		// align to 1st peak and resample y values
		results = testAlign(in, 24, 25, false, true);
		assertSame(in[0], results[0]);
		assertSame(in[1], results[1]);
		Slice s = new Slice(xb.getSize()/2);
		assertDatasetEquals(results[2].getSliceView(s), in[2].getSliceView(s));
		assertArrayEquals(in[1].maxPos(true), results[3].maxPos(true));

		// align to zero and resample y values
		results = testAlign(in, 24, 25, true, true);
		assertSame(in[1], results[1]);
		assertEquals(xa.getDouble(0), results[0].getDouble(0) + pos, err);
		assertArrayEquals(in[1].maxPos(true), results[3].maxPos(true));
		assertEquals(xb.getDouble(0), results[2].getDouble(0) + pos, err);
		assertEquals(results[0].getDouble(0), results[2].getDouble(0) - sx, err);
	}
}
