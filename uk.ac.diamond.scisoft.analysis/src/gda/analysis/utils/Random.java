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

package gda.analysis.utils;

import gda.analysis.DataSet;

import org.apache.commons.math.random.MersenneTwister;
import org.apache.commons.math.random.RandomDataImpl;

/**
 * Class to hold methods to create random datasets
 * 
 * Emulates numpy.random
 * 
 * @deprecated Use {@link uk.ac.diamond.scisoft.analysis.dataset.Random} with new generic datasets
 */
@Deprecated
public class Random {
	private final static MersenneTwister mtGenerator = new MersenneTwister();
	private final static RandomDataImpl prng = new RandomDataImpl(mtGenerator);

	/**
	 * @param seed
	 */
	public static void seed(int seed) {
		mtGenerator.setSeed(seed);
	}

	/**
	 * @param seed
	 */
	public static void seed(int[] seed) {
		mtGenerator.setSeed(seed);
	}

	/**
	 * @param seed
	 */
	public static void seed(long seed) {
		mtGenerator.setSeed(seed);
	}

	/**
	 * @param size
	 * @return an array of values sampled from a uniform distribution between 0 and 1 (exclusive) 
	 */
	public static DataSet rand(int... size) {
		DataSet data = new DataSet(size);
		double[] buf = data.getBuffer();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextUniform(0, 1);
		}

		return data;
	}

	/**
	 * @param size
	 * @return an array of values sampled from a Gaussian distribution with mean 0 and variance 1 
	 */
	public static DataSet randn(int... size) {
		DataSet data = new DataSet(size);
		double[] buf = data.getBuffer();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextGaussian(0, 1);
		}

		return data;
	}

	/**
	 * @param high
	 * @param size
	 * @return an array of values sampled from a discrete uniform distribution in range [0, high)
	 * @deprecated To be removed in v8.8. Use {@link #randint(int, int, int[])}
	 */
	@Deprecated
	public static DataSet randint(int high, int[] size) {
		return random_integers(0, high-1, size);
	}

	/**
	 * @param low 
	 * @param high
	 * @param size
	 * @return an array of values sampled from a discrete uniform distribution in range [low, high)
	 */
	public static DataSet randint(int low, int high, int[] size) {
		return random_integers(low, high-1, size);
	}

	/**
	 * @param high
	 * @param size
	 * @return an array of values sampled from a discrete uniform distribution in range [1, high]
	 * @deprecated To be removed in v8.8. Use {@link Random#random_integers(int, int, int[])}
	 */
	@Deprecated
	public static DataSet random_integers(int high, int[] size) {
		return random_integers(1, high, size);
	}

	/**
	 * @param low 
	 * @param high 
	 * @param size
	 * @return an array of values sampled from a discrete uniform distribution in range [low, high]
	 */
	public static DataSet random_integers(int low, int high, int[] size) {
		DataSet data = new DataSet(size);
		double[] buf = data.getBuffer();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextInt(low, high);
		}

		return data;
	}

	/**
	 * @param beta 
	 * @param size
	 * @return an array of values sampled from an exponential distribution with mean beta
	 */
	public static DataSet exponential(double beta, int... size) {
		DataSet data = new DataSet(size);
		double[] buf = data.getBuffer();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextExponential(beta);
		}

		return data;
	}

	/**
	 * @param lam 
	 * @param size
	 * @return an array of values sampled from an exponential distribution with mean lambda
	 */
	public static DataSet poisson(double lam, int... size) {
		DataSet data = new DataSet(size);
		double[] buf = data.getBuffer();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextPoisson(lam);
		}

		return data;
	}
}
