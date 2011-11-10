/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset;

import static org.junit.Assert.assertEquals;

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

}
