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
		pos = new double[] {958.4642122452913, 958.5349371709174, 958.5091583268133};
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
		double edelta = energy.getDouble(1) - energy.getDouble();
		double[] expected = new double[pos.length];
		for (int i = 0; i < pos.length; i++) {
			expected[i] = edelta * (pos[i] - pos[0]);
		}
		assertArrayEquals(expected, calcOffset(energy, result), 1e-3);
		result = testAlign(energy, 5.155, 7.445, true, false);
		double first = pos[0];
		first = Maths.interpolate(energy, first);
		for (int i = 0; i < pos.length; i++) {
			expected[i] = expected[i] + first;
		}
		assertArrayEquals(expected, calcOffset(energy, result), 2e-3);
	}

	private Dataset[] testAlign(Dataset x, double lo, double hi, boolean force, boolean resample) {
		int imax = holder.size() - 1;
		Dataset[] in = new Dataset[2*imax];
		for (int i = 1, j = 0; i <= imax; i++) {
			in[j++] = x;
			in[j++] = DatasetUtils.convertToDataset(holder.getDataset(i));
		}

		return testAlign(in, lo, hi, force, resample);
	}

	private static Dataset[] testAlign(Dataset[] in, double lo, double hi, boolean force, boolean resample) {
		AlignToHalfGaussianPeak align = new AlignToHalfGaussianPeak(true);

		align.setPeakZone(lo, hi);
		List<Double> posn = align.value(in);
		return AlignToHalfGaussianPeak.alignToPositions(resample, force || (lo <= 0 && hi >= 0) ? 0 : Double.NaN, posn, in);
	}

	private double[] calcOffset(Dataset energy, Dataset[] result) {
		int imax = holder.size() - 1;
		double[] out = new double[imax];
		double e = energy.getDouble();

		for (int i = 0; i < imax; i++) {
			out[i] = e - result[2 * i].getDouble();
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

		double dx = 0.133;
		double my = 1.2;
		p.setValue(pos + dx); // shift peak
		g.setDirty(true);
		double sx = 0.73;
		Dataset xb = DatasetFactory.createRange(21.15 + sx, 30, 0.06);
		Dataset yb = g.calculateValues(xb).imultiply(my).iadd(off).iadd(Random.randn(xb.getShapeRef()).imultiply(am));

		Dataset[] in = new Dataset[] {xa, ya, xb, yb};
		Dataset[] results;

		// align to 1st peak and shift x coords
		results = testAlign(in, 24, 25, false, false);
		assertSame(in[0], results[0]);
		assertSame(in[1], results[1]);
		assertSame(in[3], results[3]);
		assertEquals(xb.getDouble(), results[2].getDouble() + dx, 0.01);

		// align to zero and shift x coords
		results = testAlign(in, 24, 25, true, false);
		assertSame(in[1], results[1]);
		assertSame(in[3], results[3]);
		assertEquals(xa.getDouble(), results[0].getDouble() + pos, 2.5e-3);
		assertEquals(xb.getDouble(), results[2].getDouble() + pos + dx, 0.01);
		int pa = results[0].argMax();
		int pb = results[2].argMax();
		assertEquals(results[0].getDouble(pa), results[2].getDouble(pb) + dx, 0.05);

		// align to 1st peak and resample y values
		results = testAlign(in, 24, 25, false, true);
		pa = results[1].argMax();
		pb = results[3].argMax();
		int w = 5;
		assertDatasetEquals(results[0].getSliceView(new Slice(pa - w, pa + w)), results[2].getSliceView(new Slice(pb - w, pb + w)));
		assertEquals(in[1].max().doubleValue(), results[1].getDouble(pa), 0.3);
		assertEquals(in[3].max().doubleValue(), results[3].getDouble(pb), 0.3);

		// align to zero and resample y values
		results = testAlign(in, 24, 25, true, true);
		assertDatasetEquals(in[1].getSliceView(new Slice(0, -2)), results[1], 2.5e-1, 0.05);
		pa = results[1].argMax();
		pb = results[3].argMax();
		assertDatasetEquals(results[0].getSliceView(new Slice(pa - w, pa + w)), results[2].getSliceView(new Slice(pb - w, pb + w)));
		assertEquals(0, results[0].getDouble(pa), 1e-8);
		assertEquals(0, results[2].getDouble(pb), 1e-8);
		assertEquals(in[1].max().doubleValue(), results[1].getDouble(pa), 0.3);
		assertEquals(in[3].max().doubleValue(), results[3].getDouble(pb), 0.3);
	}
}
