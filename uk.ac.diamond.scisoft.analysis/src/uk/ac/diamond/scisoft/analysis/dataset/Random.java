/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset;


import org.apache.commons.math.random.MersenneTwister;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.math.random.RandomGenerator;

/**
 * Class to hold methods to create random datasets
 * 
 * Emulates numpy.random
 */
public class Random {
	private final static RandomGenerator generator = new MersenneTwister();
	private final static RandomDataImpl prng = new RandomDataImpl(generator);

	/**
	 * @param seed
	 */
	public static void seed(final int seed) {
		generator.setSeed(seed);
	}

	/**
	 * @param seed
	 */
	public static void seed(final int[] seed) {
		generator.setSeed(seed);
	}

	/**
	 * @param seed
	 */
	public static void seed(final long seed) {
		generator.setSeed(seed);
	}

	/**
	 * @param shape
	 * @return an array of values sampled from a uniform distribution between 0 (inclusive) and 1 (exclusive) 
	 */
	public static DoubleDataset rand(final int... shape) {
		DoubleDataset data = new DoubleDataset(shape);
		double[] buf = data.getData();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = generator.nextDouble();
		}

		return data;
	}

	/**
	 * @param low
	 * @param high
	 * @param shape
	 * @return an array of values sampled from a uniform distribution between low and high (both exclusive) 
	 */
	public static DoubleDataset rand(double low, double high, final int... shape) {
		DoubleDataset data = new DoubleDataset(shape);
		double[] buf = data.getData();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextUniform(low, high);
		}

		return data;
	}

	/**
	 * @param shape
	 * @return an array of values sampled from a Gaussian distribution with mean 0 and variance 1 
	 */
	public static DoubleDataset randn(final int... shape) {
		DoubleDataset data = new DoubleDataset(shape);
		double[] buf = data.getData();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = generator.nextGaussian();
		}

		return data;
	}

	/**
	 * @param mean
	 * @param std standard deviation
	 * @param shape
	 * @return an array of values sampled from a Gaussian distribution with given mean and standard deviation 
	 */
	public static DoubleDataset randn(double mean, double std, final int... shape) {
		DoubleDataset data = new DoubleDataset(shape);
		double[] buf = data.getData();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextGaussian(mean, std);
		}

		return data;
	}

	/**
	 * @param low 
	 * @param high
	 * @param shape
	 * @return an array of values sampled from a discrete uniform distribution in range [low, high)
	 */
	public static IntegerDataset randint(final int low, final int high, final int[] shape) {
		return random_integers(low, high-1, shape);
	}

	/**
	 * @param low 
	 * @param high 
	 * @param shape
	 * @return an array of values sampled from a discrete uniform distribution in range [low, high]
	 */
	public static IntegerDataset random_integers(final int low, final int high, final int[] shape) {
		IntegerDataset data = new IntegerDataset(shape);
		int[] buf = data.getData();

		if (low == high) {
			for (int i = 0; i < buf.length; i++) {
				buf[i] = low;
			}			
		} else {
			for (int i = 0; i < buf.length; i++) {
				buf[i] = prng.nextInt(low, high);
			}
		}

		return data;
	}

	/**
	 * @param beta 
	 * @param shape
	 * @return an array of values sampled from an exponential distribution with mean beta
	 */
	public static DoubleDataset exponential(final double beta, final int... shape) {
		DoubleDataset data = new DoubleDataset(shape);
		double[] buf = data.getData();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = prng.nextExponential(beta);
		}

		return data;
	}

	/**
	 * @param lam 
	 * @param shape
	 * @return an array of values sampled from an exponential distribution with mean lambda
	 */
	public static IntegerDataset poisson(final double lam, final int... shape) {
		IntegerDataset data = new IntegerDataset(shape);
		int[] buf = data.getData();

		for (int i = 0; i < buf.length; i++) {
			buf[i] = (int) prng.nextPoisson(lam);
		}

		return data;
	}
}
