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

import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

public class AlignToHalfGaussianPeakTest {
	private static IDataHolder holder;
	private static double[] pos;

	@BeforeClass
	public static void load() throws Exception {
		String elasticFile = "testfiles/i21-elastic.dat";
		holder = LoaderFactory.getData(elasticFile);
		pos = new double[] {957.9744719343317, 958.0293316165976, 958.0592181750769};
	}

	@Test
	public void testAlignIndex() {
		Dataset index = DatasetFactory.createRange(holder.getDataset(0).getSize());

		List<? extends IDataset> result = testAlign(index, 940, 985, false);
		double[] expected = new double[pos.length];
		for (int i = 0; i < pos.length; i++) {
			expected[i] = pos[i] - pos[0];
		}
		assertArrayEquals(expected, calcOffset(index, result), 1e-5);
		result = testAlign(index, 940, 985, true);
		assertArrayEquals(pos, calcOffset(index, result), 1e-5);
	}

	@Test
	public void testAlign() {
		Dataset energy = DatasetUtils.convertToDataset(holder.getDataset(0));

		List<? extends IDataset> result = testAlign(energy, 5.155, 7.445, false);
		double edelta = energy.getDouble(1) - energy.getDouble(0);
		double[] expected = new double[pos.length];
		for (int i = 0; i < pos.length; i++) {
			expected[i] = edelta * (pos[i] - pos[0]);
		}
		assertArrayEquals(expected, calcOffset(energy, result), 1e-6);
		result = testAlign(energy, 5.155, 7.445, true);
		double first = 15.974471934331792 + 942;
		first = Maths.interpolate(energy, first);
		for (int i = 0; i < pos.length; i++) {
			expected[i] = expected[i] + first;
		}
		assertArrayEquals(expected, calcOffset(energy, result), 1e-6);
	}

	private List<? extends IDataset> testAlign(IDataset x, double lo, double hi, boolean force) {
		int imax = 2 * (holder.size() - 1);
		IDataset[] in = new IDataset[imax];
		for (int i = 0; i < imax; i += 2) {
			in[i] = x;
			in[i + 1] = holder.getDataset(i/2 + 1);
		}

		AlignToHalfGaussianPeak align = new AlignToHalfGaussianPeak(false);

		align.setPeakZone(lo, hi);
		align.setForceToPosition(force);
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
}
