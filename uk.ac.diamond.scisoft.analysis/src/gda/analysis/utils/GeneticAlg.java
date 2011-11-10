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
import gda.analysis.functions.IFunction;
import gda.analysis.functions.Parameter;

import org.apache.commons.math.random.JDKRandomGenerator;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.math.random.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class uses the Differential evolution genetic algorithm as an optimizer.
 */
public class GeneticAlg implements IOptimizer {
	private final static RandomGenerator generator = new JDKRandomGenerator();
	private final static RandomDataImpl prng = new RandomDataImpl(generator);
	
	transient protected static final Logger DepricatedGAlogger = LoggerFactory.getLogger(GeneticAlg.class);
	private static final int MaxNumberOfStaticBestValue = 50;

	private double qualityFactor = 0.0;

	/**
	 * Constructor which takes the quality of the fit as an input.
	 * 
	 * @param quality
	 *            A generic quality term, try 0.1 to start then increase the
	 *            number for a quicker time, and decrease it for more accuracy
	 */
	public GeneticAlg(double quality) {
		qualityFactor = quality;
	}

	/**
	 * Constructor which takes quality of fit and seed for random number generator
	 * @param quality
	 * @param seed
	 */
	public GeneticAlg(double quality, Long seed) {
		qualityFactor = quality;
		if (seed != null)
			generator.setSeed(seed);
	}

	@Override
	public void Optimize(final DataSet[] coords, final DataSet yAxis, final IFunction function) {
		try {
			Optimize(coords, yAxis, function, 10000);
		} catch (Exception e) {	
			DepricatedGAlogger.warn("Maximum number of itterations has been exceeded.  This solution may be suboptimal");
		}
		
	}
		
	public void Optimize(final DataSet[] coords, final DataSet yAxis, final IFunction function, int maxItterations) throws Exception {

		// set some factors
		final double mutantProportion = 0.5;
		final double mutantScaling = 0.5;

		final int nparams = function.getNoOfParameters();
		// epoch size is normally the number of dimensions multiplied by 20.
		int epochSize = nparams * 20;
		if (epochSize < 100)
			epochSize = 100;
		// for the time being these are the same size
		final int nfuncs = function.getNoOfFunctions();

		// generate the first epoch, each member will be a random initial
		// position picked from the maximum parameters

		double epoch[][] = new double[epochSize][nparams + 1];
		double nextepoch[][] = new double[epochSize][nparams + 1];

		// first one should be the original, just in case its a good solution
		for (int j = 0; j < nparams; j++) {
			epoch[0][j] = function.getParameter(j).getValue();
		}

		// the others explore space in the bounded regions
		for (int i = 1; i < epochSize; i++) {
			for (int j = 0; j < nparams; j++) {
				final Parameter p = function.getParameter(j);
				epoch[i][j] = prng.nextUniform(p.getLowerLimit(), p.getUpperLimit());
			}
		}

		// now the first epoch has been created and calculate the fitness
		for (int i = 0; i < epochSize; i++) {
			function.setParameterValues(epoch[i]);
			epoch[i][nparams] = function.residual(true, yAxis, coords);
			if (((Double) (epoch[i][nparams])).isNaN()) {
				epoch[i][nparams] = Double.MAX_VALUE;
			}
		}

		// now do the epochs
		double minval = Double.MAX_VALUE;
		double previousMinval = minval;
		int numberOfTimesMinvalTheSame = 0;
		
		int iterationCount = 0;

		while (minval > qualityFactor) {

			double mean = 0;

			// the first member of the new epoch, should be the best member of the last
			double minvalue = epoch[0][nparams];
			int minposition = 0;

			for (int i = 1; i < epochSize; i++) {
				if (epoch[i][nparams] < minvalue) {
					minvalue = epoch[i][nparams];
					minposition = i;
				}
			}

			for (int m = 0; m < nparams; m++) {
				nextepoch[0][m] = epoch[minposition][m];
			}

			function.setParameterValues(nextepoch[0]);
			nextepoch[0][nparams] = function.residual(true, yAxis, coords);

			// now go on and get the rest of the population
			for (int j = 1; j < epochSize; j++) {

				// get mum and dad
				int mum = 0;
				int dad = 0;

				int c1 = prng.nextInt(0, epochSize-1);
				int c2 = prng.nextInt(0, epochSize-1);
				int c3 = prng.nextInt(0, epochSize-1);
				int c4 = prng.nextInt(0, epochSize-1);

				while (((Double) epoch[c1][nparams]).isNaN()) {
					c1 = prng.nextInt(0, epochSize-1);
				}
				while (((Double) epoch[c2][nparams]).isNaN()) {
					c2 = prng.nextInt(0, epochSize-1);
				}
				while (((Double) epoch[c3][nparams]).isNaN()) {
					c3 = prng.nextInt(0, epochSize-1);
				}
				while (((Double) epoch[c4][nparams]).isNaN()) {
					c4 = prng.nextInt(0, epochSize-1);
				}

				if (epoch[c1][nparams] < epoch[c2][nparams]) {
					mum = c1;
				} else {
					mum = c2;
				}

				if (epoch[c3][nparams] < epoch[c4][nparams]) {
					dad = c3;
				} else {
					dad = c4;
				}

				// cross-breed at a point, between 2 different functions.
				int point = nfuncs > 1 ? prng.nextInt(0, nfuncs - 1) : prng.nextInt(0, nparams - 1);
				int parent = mum;
				int count = 0;

				for (int i = 0; i < nfuncs; i++) {
					if (i >= point) {
						parent = dad;
					}

					final int fnparams = function.getFunction(i).getNoOfParameters();
					for (int l = 0; l < fnparams; l++) {
						nextepoch[j][count] = epoch[parent][count];
						count++;
					}
				}

				// add in random mutation
				if (generator.nextDouble() > mutantProportion) {

					c1 = prng.nextInt(0, epochSize-1);
					c2 = prng.nextInt(0, epochSize-1);

					for (int i = 0; i < nparams; i++) {
						nextepoch[j][i] = nextepoch[j][i]
								+ (epoch[c1][i] - epoch[c2][i]) * mutantScaling;
					}
				}
			}

			// at the end of the epoch, flush the next epoch to the epoch
			for (int i = 0; i < epochSize; i++) {
				for (int j = 0; j < nparams; j++) {
					epoch[i][j] = nextepoch[i][j];

					// then clip it
					final Parameter p = function.getParameter(j);

					if (epoch[i][j] > p.getUpperLimit()) {
						epoch[i][j] = 2. * p.getUpperLimit() - epoch[i][j];
					}
					if (epoch[i][j] < p.getLowerLimit()) {
						epoch[i][j] = 2. * p.getLowerLimit() - epoch[i][j];
					}
				}

				// finally calculate the fitness and put it in the last digit
				function.setParameterValues(epoch[i]);
				epoch[i][nparams] = function.residual(true, yAxis, coords);
				
			    double delta = epoch[i][nparams] - mean;
			    mean = mean + delta/(i+1);
			}

			// at the end find the best solution, and evaluate it, to fix the
			// values into the model

			minval = epoch[0][nparams];

			for (int i = 1; i < epochSize; i++) {
				if (epoch[i][nparams] < minval) {
					minval = epoch[0][nparams];
				}
			}

			// Exit if the number of iterations has been exceeded
			iterationCount++;
			if (iterationCount > maxItterations) {
				throw new Exception("Too many itterations have been proformed, best available soulution has been provided");
			}			
			
			// Exit if the minval has not changed in a number of itterations
			if (previousMinval == minval) {
				numberOfTimesMinvalTheSame += 1;
				if (numberOfTimesMinvalTheSame > MaxNumberOfStaticBestValue) {
					// the solution is probably good enough, 
					break;
				}
			} else {
				previousMinval = minval;
				numberOfTimesMinvalTheSame = 0;
			}
			
			minval = Math.abs(mean - minval)/minval;
		}

		// at the end find the best solution, and evaluate it, to fix the values
		// into the model

		minval = epoch[0][nparams];
		int minpos = 0;

		for (int i = 1; i < epochSize; i++) {
			if (epoch[i][nparams] < minval) {
				minval = epoch[0][nparams];
				minpos = i;
			}
		}

		function.setParameterValues(epoch[minpos]);

	}

}
