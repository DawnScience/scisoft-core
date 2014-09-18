/*-
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

package uk.ac.diamond.scisoft.analysis.optimize;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IOperator;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class uses the Differential evolution genetic algorithm as an optimizer.
 */
public class GeneticAlg extends AbstractOptimizer {
	
	transient protected static final Logger GAlogger = LoggerFactory.getLogger(GeneticAlg.class);
	
	private final RandomGenerator generator = new MersenneTwister();
	private final RandomDataGenerator prng = new RandomDataGenerator(generator);

	private static final int MaxNumberOfStaticBestValue = 50;

	/**
	 * Setup the logging facilities
	 */
//	private static final Logger logger = LoggerFactory.getLogger(GeneticAlg.class);

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
	void internalOptimize() {
		try {
			optimize(10000);
		} catch (IterationLimitException e) {	
			GAlogger.warn("Maximum number of itterations has been exceeded.  This solution may be suboptimal");
		}
	}

	public void optimize(int maxItterations) throws IterationLimitException {
		IOperator operator = (function instanceof IOperator) ? (IOperator) function : null;
		// set some factors
		final double mutantProportion = 0.5;
		final double mutantScaling = 0.5;

		// top epoch is normally the number of dimensions multiplied by 20 minus 1.
		int topEpoch = n * 20 - 1;
		if (topEpoch < 100)
			topEpoch = 99;


		// for the time being these are the same size
		final int nfuncs = operator != null ? operator.getNoOfFunctions() : 1;

		// generate the first epoch, each member will be a random initial
		// position picked from the maximum parameters

		double epoch[][] = new double[topEpoch + 1][];
		double[] results = new double[topEpoch + 1];
		double nextepoch[][] = new double[topEpoch + 1][n];

		// first one should be the original, just in case its a good solution
		epoch[0] = getParameterValues();

		// the others explore space in the bounded regions
		for (int i = 1; i <= topEpoch; i++) {
			double[] e = new double[n];
			epoch[i] = e;
			for (int j = 0; j < n; j++) {
				final IParameter p = params.get(j);
				e[j] = prng.nextUniform(p.getLowerLimit(), p.getUpperLimit());
			}
		}

		// now the first epoch has been created and calculate the fitness
		for (int i = 0; i <= topEpoch; i++) {
			double r = calculateResidual(epoch[i]);
			results[i] = Double.isNaN(r) ? Double.MAX_VALUE : r;
		}

		// now do the epochs
		double minval = Double.MAX_VALUE;
		double previousMinval = minval;
		int numberOfTimesMinvalTheSame = 0;

		int iterationCount = 0;
		
		while (minval > qualityFactor) {
			
			double mean = 0;

			// the first member of the new epoch, should be the best member of the last
			double minvalue = results[0];
			int minposition = 0;

			for (int i = 1; i <= topEpoch; i++) {
				if (results[i] < minvalue) {
					minvalue = results[i];
					minposition = i;
				}
			}

			for (int m = 0; m < n; m++) {
				nextepoch[0][m] = epoch[minposition][m];
			}

			// now go on and get the rest of the population
			for (int j = 1; j <= topEpoch; j++) {

				// get mum and dad
				int c1 = prng.nextInt(0, topEpoch);
				int c2 = prng.nextInt(0, topEpoch);
				int c3 = prng.nextInt(0, topEpoch);
				int c4 = prng.nextInt(0, topEpoch);

				while (Double.isNaN(results[c1])) {
					c1 = prng.nextInt(0, topEpoch);
				}
				while (Double.isNaN(results[c2])) {
					c2 = prng.nextInt(0, topEpoch);
				}
				while (Double.isNaN(results[c3])) {
					c3 = prng.nextInt(0, topEpoch);
				}
				while (Double.isNaN(results[c4])) {
					c4 = prng.nextInt(0, topEpoch);
				}

				int mum = results[c1] < results[c2] ? c1 : c2;
				int dad = results[c3] < results[c4] ? c3 : c4;

				// cross-breed at a point, between 2 different functions.
				int point = nfuncs > 1 ? prng.nextInt(0, nfuncs - 1) : prng.nextInt(0, n-1);
				double[] parentepoch = epoch[mum];
				int count = 0;

				double[] ne = nextepoch[j];
				if (operator != null) {
					for (int i = 0; i < nfuncs; i++) {
						if (i >= point) {
							parentepoch = epoch[dad];
						}

						IFunction of = operator.getFunction(i);
						for (IParameter pf : of.getParameters()) {
							if (params.get(count) == pf) {
								ne[count] = parentepoch[count];
								count++;
							}
						}
					}
				} else {
					if (0 >= point) {
						parentepoch = epoch[dad];
					}
	
					for (int l = 0; l < n; l++) {
						ne[count] = parentepoch[count];
						count++;
					}
				}

				// add in random mutation
				if (generator.nextDouble() > mutantProportion) {
					c1 = prng.nextInt(0, topEpoch);
					c2 = prng.nextInt(0, topEpoch);

					for (int i = 0; i < n; i++) {
						ne[i] += (epoch[c1][i] - epoch[c2][i]) * mutantScaling;
					}
				}
			}

			// at the end of the epoch, flush the next epoch to the epoch
			for (int i = 0; i <= topEpoch; i++) {
				double[] e = epoch[i];
				double[] ne = nextepoch[i];
				for (int j = 0; j < n; j++) {
					e[j] = ne[j];

					// then clip it
					final IParameter p = params.get(j);

					if (e[j] > p.getUpperLimit()) {
						e[j] = 2. * p.getUpperLimit() - e[j];
					}
					if (e[j] < p.getLowerLimit()) {
						e[j] = 2. * p.getLowerLimit() - e[j];
					}
				}

				// finally calculate the fitness and put it in the last digit
				results[i] = calculateResidual(e);

			    double delta = results[i] - mean;
			    mean = mean + delta/(i+1);
			}
			
			//mean = mean;

			// at the end find the best solution, and evaluate it, to fix the
			// values into the model

			minval = results[0];

			for (int i = 1; i <= topEpoch; i++) {
				if (results[i] < minval) {
					minval = results[0];
				}
			}

			// Exit if the number of iterations has been exceeded
			iterationCount++;
			if (iterationCount > maxItterations) {
				throw new IterationLimitException("Too many iterations have been performed, best available soulution has been provided");
			}
			
			// Exit if the minval has not changed in a number of iterations
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
			
			if (minval == 0.0)
				break;
			minval = Math.abs(mean - minval)/minval;
		}

		// at the end find the best solution, and evaluate it, to fix the values
		// into the model

		minval = results[0];
		int minpos = 0;

		for (int i = 1; i <= topEpoch; i++) {
			if (results[n] < minval) {
				minval = results[0];
				minpos = i;
			}
		}

		setParameterValues(epoch[minpos]);
	}
}
