/*-
 * Copyright 2025 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.eclipse.january.asserts.TestUtils.assertDatasetEquals;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Random;
import org.eclipse.january.dataset.Slice;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.fitting.OverlapMerge.NormOption;
import uk.ac.diamond.scisoft.analysis.io.DatLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

public class OverlapMergeTest {
	private static DataHolder testData;

	private void checkOverlap(Dataset a, Dataset b, int[] expected, int[] actual) {
		assertArrayEquals(expected, actual);
		System.err.printf("Start: %g cf %g%n", a.getDouble(actual[0]), b.getDouble(actual[1]));
		System.err.printf("End  : %g cf %g%n", a.getDouble(actual[2]), b.getDouble(actual[3]));
	}

	@BeforeClass
	public static void loadData() throws ScanFileHolderException {
		final String testfile1 = "testfiles/iqe.dat";
		testData = new DatLoader(testfile1).loadFile();
	}

	@Test
	public void testFindOvelap() {
		// 2, 3, 4, 5, 6,  7,  8,  9, 10, 11
		DoubleDataset a = DatasetFactory.createRange(2., 12., 1.);
		// 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
		DoubleDataset b = DatasetFactory.createRange(5., 15., 1.);

		int[] overlap = OverlapMerge.findOverlap(a, b);
		checkOverlap(a, b, new int[] {3, 0, 9, 6}, overlap);

		overlap = OverlapMerge.findOverlap(b, a);
		checkOverlap(b, a, new int[] {0, 3, 6, 9}, overlap);

		a.imultiply(1.5).iadd(0.5); // 3.5, 5, 6.5, 8,  9.5, 11, 12.5, 14, 15.5, 17
		b.imultiply(2).iadd(-4.);   // 6.,  8,10., 12, 14,   16, 18,   20, 22,   24
		overlap = OverlapMerge.findOverlap(a, b);
		checkOverlap(a, b, new int[] {2, 0, 9, 6}, overlap);

		Dataset ar = a.getSlice(new Slice(null, null, -1));
		overlap = OverlapMerge.findOverlap(ar, b);
		checkOverlap(ar, b, new int[] {7, 0, 0, 6}, overlap);

		assertThrows(IllegalArgumentException.class, () -> {
			OverlapMerge.findOverlap(a, Random.randint(0, 15, new int[] {15}));
		});

		Dataset q1 = testData.getDataset("q1");
		Dataset q2 = testData.getDataset("q2");
		overlap = OverlapMerge.findOverlap(q1, q2);
		checkOverlap(q1, q2, new int[] {29, 0, 499, 156}, overlap);
	}

	private Dataset[] getResults(Dataset q1, Dataset i1, Dataset q2, Dataset i2, NormOption normOpt) throws DatasetException {
		OverlapMerge om = new OverlapMerge(q1, q2);
		return om.mergeOverlap(i1, i2, normOpt);
	}

	@Test
	public void testMerge() throws DatasetException {
		Dataset q1 = testData.getDataset("q1");
		Dataset q2 = testData.getDataset("q2");
		Dataset i1 = testData.getDataset("I1");
		Dataset i2 = testData.getDataset("I2");
		Dataset[] result = getResults(q1, i1, q2, i2, NormOption.FIRST);

		assertEquals(Monotonicity.STRICTLY_INCREASING, Comparisons.findMonotonicity(result[0]));

		// swap input order
		Dataset[] other = getResults(q2, i2, q1, i1, NormOption.FIRST);
		assertEquals(Monotonicity.STRICTLY_INCREASING, Comparisons.findMonotonicity(other[0]));
		assertDatasetEquals(result[0], other[0]);
		assertDatasetEquals(result[1], other[1]);

		// reverse input1
		Slice rSlice = new Slice(null, null, -1);
		Dataset j1 = i1.getSliceView(rSlice);
		Dataset r1 = q1.getSliceView(rSlice);
		other = getResults(r1, j1, q2, i2, NormOption.FIRST);
		assertEquals(Monotonicity.STRICTLY_INCREASING, Comparisons.findMonotonicity(other[0]));
		assertDatasetEquals(result[0], other[0]);
		assertDatasetEquals(result[1], other[1]);

		Dataset j2 = i2.getSliceView(rSlice);
		Dataset r2 = q2.getSliceView(rSlice);
		other = getResults(r1, j1, r2, j2, NormOption.FIRST);
		assertEquals(Monotonicity.STRICTLY_INCREASING, Comparisons.findMonotonicity(other[0]));
		assertDatasetEquals(result[0], other[0]);
		assertDatasetEquals(result[1], other[1]);

		other = getResults(q1, i1, q2, i2, NormOption.SECOND);
		assertEquals(Monotonicity.STRICTLY_INCREASING, Comparisons.findMonotonicity(other[0]));

		other = getResults(q1, i1, q2, i2, NormOption.NEITHER);
		assertEquals(Monotonicity.STRICTLY_INCREASING, Comparisons.findMonotonicity(other[0]));
	}

	private void checkDataset(Dataset actual, double... expected) {
		assertDatasetEquals(DatasetFactory.createFromObject(expected), actual);
	}

	@Test
	public void testInterpolate() throws DatasetException {
		DoubleDataset a = DatasetFactory.createRange(2., 12., 1.);
		DoubleDataset b = DatasetFactory.createRange(5., 15., 1.);

		a.imultiply(1.5).iadd(0.5); // 3.5, 5, 6.5, 8,  9.5, 11, 12.5, 14, 15.5, 17
		b.imultiply(2).iadd(-4.);   // 6.,  8,10., 12, 14,   16, 18,   20, 22,   24

		DoubleDataset e = a.clone();
		b.setErrors(e);

		Dataset x = DatasetFactory.createFromObject(new double[] {1.2, 2.7, 8, 12.13, 18.2, 19.6});
		Dataset y = OverlapMerge.interpolate(a, b, x);
		Dataset f = y.getErrors();
		checkDataset(y, 0, 6*0.7/1.5, 12, 16*0.37/1.5 + 18*1.13/1.5, 0.3/1.5*24, 0);
		checkDataset(f, 0, 3.5*0.7/1.5, 8, Math.hypot(11*0.37, 12.5*1.13)/1.5, 0.3/1.5*17, 0);
	}
}
