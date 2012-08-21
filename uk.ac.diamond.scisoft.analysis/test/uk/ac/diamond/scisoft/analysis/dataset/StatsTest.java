/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	public void TestRand() {
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

		Number ores = null;
		start = -System.nanoTime();
		for (int i = 0; i < 5; i++)
			ores = (Number) Maths.square(Maths.subtract(ta, tb)).sum();
		start += System.nanoTime();
		System.out.printf("Old residual takes %.3fms\n", start*1e-6);

		assertEquals(msg, res, ores.doubleValue(), 1e-14*res);
	}

	/**
	 * Normal distribution
	 */
	@Test
	public void TestRandn() {
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
	public void TestRandExp() {
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
	public void TestRandPois() {
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
	public void TestNaNs() {
		AbstractDataset a = AbstractDataset.arange(1, 7, 1, AbstractDataset.FLOAT64);

		assertEquals("Sum", 21, ((Number) a.sum()).doubleValue(), 1e-6);
		assertEquals("Product", 720, (Double) Stats.product(a), 1e-6);
		a.set(Double.NaN, 0);
		assertTrue("Sum", Double.isNaN(((Number) a.sum()).doubleValue()));
		assertTrue("Product", Double.isNaN((Double) Stats.product(a)));
		assertEquals("Sum", 20, ((Number) a.sum(true)).doubleValue(), 1e-6);
		assertEquals("Product", 720, (Double) Stats.product(a, true), 1e-6);
	}
}
