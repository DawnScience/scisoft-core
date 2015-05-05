/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.impl.Random;
import org.eclipse.dawnsci.analysis.dataset.impl.Stats;
import org.junit.Test;

/**
 * Basic tests of Stats class
 */
public class StatsTest {

	private final static String msg = "This test is statistical in nature and can fail. Try re-running.";

	/**
	 * Uniform distribution
	 */
	@SuppressWarnings("null")
	@Test
	public void testRand() {
		Random.seed(103);
		DoubleDataset ta = Random.rand(100000);

		assertEquals(msg, 0.5, ((Number) ta.mean()).doubleValue(), 4e-2);
		assertEquals(msg, 0.5/Math.sqrt(3), ta.stdDeviation().doubleValue(), 4e-2);
		assertEquals(msg, 0.0, ((Number) Stats.skewness(ta)).doubleValue(), 1.5e-2);
		assertEquals(msg, -1.2, ((Number) Stats.kurtosis(ta)).doubleValue(), 1e-3);
		assertEquals(msg, 0.5, ((Number) Stats.median(ta)).doubleValue(), 4e-3);

		DoubleDataset tb = Random.rand(100000);
		assertEquals(msg, 0.5, ((Number) tb.mean()).doubleValue(), 4e-2);
		assertEquals(msg, 0.5/Math.sqrt(3), tb.stdDeviation().doubleValue(), 4e-2);

		double res = 0;
		long start;
		start = -System.nanoTime();
		for (int i = 0; i < 5; i++)
			res = Stats.residual(ta, tb);
		start += System.nanoTime();
		System.out.printf("New residual takes %.3fms\n", start*1e-6);

		DoubleDataset tw = new DoubleDataset(ta.getSize());
		double wv = 2.5;
		tw.fill(wv);
		double wres = Stats.weightedResidual(ta, tb, tw);
		assertEquals(msg, wv*res, wres, 1e-14*res);

		Number ores = null;
		start = -System.nanoTime();
		for (int i = 0; i < 5; i++)
			ores = (Number) Maths.square(Maths.subtract(ta, tb)).sum();
		start += System.nanoTime();
		System.out.printf("Old residual takes %.3fms\n", start*1e-6);
		assertEquals(msg, res, ores.doubleValue(), 1e-14*res);

		Number owres = (Number) Maths.multiply(Maths.square(Maths.subtract(ta, tb)), tw).sum();
		assertEquals(msg, wv * ores.doubleValue(), owres.doubleValue(), 1e-13*owres.doubleValue());

		assertEquals(msg, wres, owres.doubleValue(), 1e-14*wres);
	}

	/**
	 * Normal distribution
	 */
	@Test
	public void testRandn() {
		Random.seed(103);
		DoubleDataset ta = Random.randn(100000);

		assertEquals(msg, 0, ((Number) ta.mean()).doubleValue(), 4e-2);
		assertEquals(msg, 1.0, ta.stdDeviation().doubleValue(), 4e-2);
		assertEquals(msg, 0.0, ((Number) Stats.skewness(ta)).doubleValue(), 1.5e-2);
		assertEquals(msg, 0.0, ((Number) Stats.kurtosis(ta)).doubleValue(), 6e-2);
		assertEquals(msg, 0.0, ((Number) Stats.median(ta)).doubleValue(), 4e-2);
		assertEquals("0-percentile not min!", ta.min().doubleValue(), Stats.quantile(ta, 0), 1e-5);
		assertEquals("100-percentile not max!", ta.max().doubleValue(), Stats.quantile(ta, 1), 1e-5);
	}

	/**
	 * Exponential distribution
	 */
	@Test
	public void testRandExp() {
		Random.seed(103);
		double beta = 0.3;
		DoubleDataset ta = Random.exponential(beta, 100000);

		assertEquals(msg, beta, ((Number) ta.mean()).doubleValue(), 4e-2);
		assertEquals(msg, beta, ta.stdDeviation().doubleValue(), 4e-2);
		assertEquals(msg, 2.0, ((Number) Stats.skewness(ta)).doubleValue(), 4e-2);
		assertEquals(msg, 6.0, ((Number) Stats.kurtosis(ta)).doubleValue(), 3e-1);
		assertEquals(msg, Math.log(2.)*beta, ((Number) Stats.median(ta)).doubleValue(), 4e-3);
		assertEquals("0-percentile not min!", ta.min().doubleValue(), Stats.quantile(ta, 0), 1e-5);
		assertEquals("100-percentile not max!", ta.max().doubleValue(), Stats.quantile(ta, 1), 1e-5);
	}

	/**
	 * Poisson distribution
	 */
	@Test
	public void testRandPois() {
		Random.seed(103);
		double lam = 0.3;
		IntegerDataset ta = Random.poisson(lam, 100000);

		assertEquals(msg, lam, ((Number) ta.mean()).doubleValue(), 4e-2);
		assertEquals(msg, Math.sqrt(lam), ta.stdDeviation().doubleValue(), 4e-2);
		assertEquals(msg, 1./Math.sqrt(lam), ((Number) Stats.skewness(ta)).doubleValue(), 5e-2);
		assertEquals(msg, 1./lam, ((Number) Stats.kurtosis(ta)).doubleValue(), 3.5e-1);
		assertEquals(msg, Math.floor(lam + 1./3 - 0.02/lam), ((Number) Stats.median(ta)).doubleValue(), 4e-3);
		assertEquals("0-percentile not min!", ta.min().doubleValue(), Stats.quantile(ta, 0), 1e-5);
		assertEquals("100-percentile not max!", ta.max().doubleValue(), Stats.quantile(ta, 1), 1e-5);
	}

	@Test
	public void testNaNs() {
		Dataset a = DatasetFactory.createRange(1, 7, 1, Dataset.FLOAT64);

		assertEquals("Sum", 21, ((Number) a.sum()).doubleValue(), 1e-6);
		assertEquals("Product", 720, (Double) Stats.product(a), 1e-6);
		a.set(Double.NaN, 0);
		assertTrue("Sum", Double.isNaN(((Number) a.sum()).doubleValue()));
		assertTrue("Product", Double.isNaN((Double) Stats.product(a)));
		assertEquals("Sum", 20, ((Number) a.sum(true)).doubleValue(), 1e-6);
		assertEquals("Product", 720, (Double) Stats.product(a, true), 1e-6);
	}

	@Test
	public void testQuantileSpeed() {
		int REPEAT = 5;
		int LENGTH = 1024*1024*4;
		double LOW = 0.01;
		double HIGH = 0.99;
		Random.seed(12371);
//		Dataset a = Random.randn(LENGTH);
//		Dataset a = Random.rand(LENGTH);
		Dataset a = Random.exponential(3.75, LENGTH);
//		Dataset a = Random.poisson(3.9, LENGTH);
		long[] times = new long[REPEAT]; // in nanoseconds
		double[] vs;

		System.out.printf("Dataset: mean = %g (%g, %g)\n", a.mean(), a.min().doubleValue(), a.max().doubleValue());
		vs = Stats.quantile(a, LOW, HIGH);
		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			Stats.quantile(a, LOW, HIGH);
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Low/High (%g/%g) took %.2fms\n", vs[0], vs[1], times[0]/1e6);

		double s = a.getSize();
		double lx = s*LOW;
		double hx = s*(1-HIGH);

		int[] ls;
		ls = new int[] {256, 640};
		for (int l : ls) {
			vs = Stats.outlierValuesList(a, l, l);
			for (int i = 0; i < REPEAT; i++) {
				times[i] = -System.nanoTime();
				Stats.outlierValuesList(a, l, l);
				times[i] += System.nanoTime();
			}
			Arrays.sort(times);
			System.out.printf("%4d: Low/High (%g/%g - %.4f/%.4f) took %.2fms\n", l, vs[0], vs[1], vs[2]/s, 1-vs[3]/s, times[0]/1e6);
		}

		ls = new int[] {640, 1024, 8192};
		for (int l : ls) {
			vs = Stats.outlierValuesMap(a, l, l);
			for (int i = 0; i < REPEAT; i++) {
				times[i] = -System.nanoTime();
				Stats.outlierValuesMap(a, l, l);
				times[i] += System.nanoTime();
			}
			Arrays.sort(times);
			System.out.printf("%4d: Low/High (%g/%g - %.4f/%.4f) took %.2fms\n", l, vs[0], vs[1], vs[2]/s, 1-vs[3]/s, times[0]/1e6);
		}

		vs = Stats.outlierValuesMap(a, (int) lx, (int) hx);
		for (int i = 0; i < REPEAT; i++) {
			times[i] = -System.nanoTime();
			Stats.outlierValuesMap(a, (int) lx, (int) hx);
			times[i] += System.nanoTime();
		}
		Arrays.sort(times);
		System.out.printf("Low/High (%g/%g - %.4f/%.4f) took %.2fms\n", vs[0], vs[1], vs[2]/s, 1-vs[3]/s, times[0]/1e6);

		double[] qs = Stats.quantile(a, LOW, HIGH);
		assertEquals("Lower quantile", qs[0], vs[0], 1e-4*qs[0]);
		assertEquals("Upper quantile", qs[1], vs[1], 1e-4*qs[1]);
	}

	@Test
	public void testOutlierValues() {
		Dataset a = DatasetFactory.zeros(new int[] {20}, Dataset.FLOAT64);

		double[] o = Stats.outlierValues(a, 0.01, 99.9, 10);
		assertEquals(0, o[0], 1e-4);
		assertEquals(0, o[1], 1e-4);
		assertEquals(0, o[2], 1e-4);
		assertEquals(100, o[3], 1e-4);
	}
	
	@Test
	public void testCovarianceRanks() {
		Dataset a = new DoubleDataset(new double[]{-3.5, 6., 8., 14., -2.2, 1.6, 4.0, 7});
		Dataset cova = Stats.covariance(a);
		assertEquals(32.62839, cova.getDouble(), 1e-4);
		
		Dataset b = new DoubleDataset(new double[]{0., 1., 2., 2., 1., 0.}, 2, 3);
		Dataset covb = Stats.covariance(b);
		Dataset bexpect = new DoubleDataset(new double[]{1., -1., -1., 1.}, 2, 2);
		assertEquals(bexpect, covb);
		
		Dataset c = b.transpose();
		Dataset covc = Stats.covariance(c);
		Dataset cexpect = new DoubleDataset(new double[]{2., 0., -2., 0., 0., 0., -2., 0., 2.}, 3, 3);
		assertEquals(cexpect, covc);
		
		Dataset d = new DoubleDataset(new double[]{0., 2., 4., 8., 16., 32., 64., 128.}, 2, 2, 2);
		Dataset covd = Stats.covariance(d);
		Dataset dexpect = new DoubleDataset(new double[]{-2., -24., -3., -48., 2., 24., 3., 48., -48., -576., -72., -1152., 48., 576., 72., 1152.}, 2, 2, 2, 2);
		assertEquals(dexpect, covd);
	}
	
	@Test
	public void testCovarianceArgs() {
		//Tests adding a second dataset to the first.
		Dataset a = new DoubleDataset(new double[]{-3.5, 6., 8., 14., -2.2, 1.6, 4.0, 7});
		Dataset b = new DoubleDataset(new double[]{8.5, 9., 13., -2.3, 1.6, 7.2, 3., -2.9});
		DoubleDataset covab = (DoubleDataset)Stats.covariance(a, b);
		DoubleDataset abexpect = new DoubleDataset(new double[]{32.628392857142856, -9.44267857142857, -9.44267857142857, 32.47125}, 2, 2);
		assertArrayEquals(abexpect.getData(), covab.getData(), 1E-6);
	}
}
