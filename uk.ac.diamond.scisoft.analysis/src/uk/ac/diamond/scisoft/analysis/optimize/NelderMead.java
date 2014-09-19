/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implementation of the Nelder-Mead optimisation for the fitting routines.
 * 
 * It is variously known as the down-hill simplex or amoeba method
 */
public class NelderMead extends AbstractOptimizer {

	double accuracy = 0.1;

	public NelderMead() {
	}

	/**
	 * @param accuracy
	 */
	public NelderMead(double accuracy) {
		this.accuracy = accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(NelderMead.class);

	private double alpha = 1.0;
	private double gamma = 2.0;
	private double phi = 0.5;
	private double sigma = 0.5;
	private double startingSpread = 1.0;
	private int    maxIterations = 2000;

	private Random random = new Random(0);

	/**
	 * @return Returns the alpha.
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha
	 *            The alpha to set.
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return Returns the gamma.
	 */
	public double getGamma() {
		return gamma;
	}

	/**
	 * @param gamma
	 *            The gamma to set.
	 */
	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	/**
	 * @return Returns the phi.
	 */
	public double getPhi() {
		return phi;
	}

	/**
	 * @param phi
	 *            The phi to set.
	 */
	public void setPhi(double phi) {
		this.phi = phi;
	}

	/**
	 * @return Returns the sigma.
	 */
	public double getSigma() {
		return sigma;
	}

	/**
	 * @param sigma
	 *            The sigma to set.
	 */
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	/**
	 * @return Returns the startingSpread.
	 */
	public double getStartingSpread() {
		return startingSpread;
	}

	/**
	 * @param startingSpread
	 *            The startingSpread to set.
	 */
	public void setStartingSpread(double startingSpread) {
		this.startingSpread = startingSpread;
	}

	@Override
	void internalOptimize() {
		double[] best = optimise(getParameterValues(), accuracy);
		setParameterValues(best);
	}

	/**
	 * The main optimisation method
	 * 
	 * @param parameters
	 * @param finishCriteria
	 * @return a double array of the parameters for the optimisation
	 */
	private double[] optimise(double[] parameters, double finishCriteria) {

		double[] solution = parameters;
		
		// TODO make this more adaptive than just running 5 times, should work to a tolerance
		// However 5 times gives a reasonable solution for the time being without too much 
		// overhead.
		for (int p = 0; p < 5; p++) {
			boolean ok = true;
			
			int collapseCount = 0;
			
			while (ok && collapseCount < 5) {
				// this outer loop handles collapsing simplexes
				Simplex simplex = new Simplex(solution, startingSpread);
	
				simplex.generateStartingFitnesses();
	
				//for(int i = 0; i < 100*problemDefinition.getNumberOfParameters(); i++) {
				//	simplex.iterate(problemDefinition);
				//}
				
				int iterationCount = 0;
				
				do {
					simplex.iterate();
				} while (simplex.improving(finishCriteria) && ++iterationCount < maxIterations );
	
				solution = simplex.getBestSolution();
	
				ok = simplex.hasCollapsed();
				collapseCount += 1;
			}
			
			if (collapseCount > 0) {
				logger.info("Error, the NelderMead simplex has collapsed, this minimisation may be flawed");
			}
			
		}

		return solution;
	}

	private class Simplex {
		private boolean collapsed = false;
		double[][] points;

		double[] fitnesses;

		double oldBest = 0;

		double oldSpread = 0;
		
		int reduceCount = 0;
		int contractCount = 0;
		int extendedCount = 0;
		int reflectedCount = 0;

		@SuppressWarnings("unused")
		public int getReduceCount() {
			return reduceCount;
		}

		@SuppressWarnings("unused")
		public int getContractCount() {
			return contractCount;
		}

		@SuppressWarnings("unused")
		public int getExtendedCount() {
			return extendedCount;
		}

		@SuppressWarnings("unused")
		public int getReflectedCount() {
			return reflectedCount;
		}

		public Simplex(double[] parameters, double startingSpread) {

			points = new double[parameters.length + 1][parameters.length];

			initialise(parameters, startingSpread);

		}

		public boolean hasCollapsed() {
			return collapsed;
		}

		public boolean improving(double finishCriteria) {

			int[] sortedValues = sortFitnesses();
			double best = fitnesses[sortedValues[0]];
			double spread = Math.abs(fitnesses[sortedValues[0]]
					- fitnesses[sortedValues[sortedValues.length - 1]]);

			if (spread < finishCriteria) {
				oldBest = best;
				oldSpread = spread;
				return false;
			}

			if ((best == oldBest) && (spread == oldSpread)) {
				collapsed = true; // this collapse is fixed by externally restarting search with bigger spread
				return false;
			}

			oldBest = best;
			oldSpread = spread;

			return true;
		}

		public double[] getBestSolution() {
			int[] orderedValues = sortFitnesses();
			return points[orderedValues[0]];
		}

		public void iterate() {

			int[] orderedValues = sortFitnesses();

			double[] cog = calculateCentreOfGravityIgnoringWorstValue(orderedValues);

			double[] reflectedPoint = calculateReflectedPoint(cog,
					orderedValues);

			double reflectedPointFitness = calculateResidual(reflectedPoint);

			// if the reflected point is better than the best point

			if (reflectedPointFitness < fitnesses[orderedValues[0]]) {

				double[] extendedPoint = calculateExtendedPoint(cog,
						orderedValues);

				double extendedPointFitness = calculateResidual(extendedPoint);

				// if the extended point is better than the reflected point

				if (extendedPointFitness < reflectedPointFitness) {
					replaceWorstPointWith(extendedPoint, extendedPointFitness,
							orderedValues);
					return;
				}

				replaceWorstPointWith(reflectedPoint, reflectedPointFitness,
						orderedValues);
				return;

			}

			// if the value is worse than the next worst
			double nextWorstFitness = fitnesses[orderedValues[orderedValues.length - 2]];
			if (reflectedPointFitness > nextWorstFitness) {

				double[] contractedPoint = calculateContractedPoint(cog,
						orderedValues);

				double contractedPointFitness = calculateResidual(contractedPoint);

				// if this is better than the worst point
				if (contractedPointFitness < fitnesses[orderedValues[orderedValues.length - 1]]) {
					replaceWorstPointWith(contractedPoint,
							contractedPointFitness, orderedValues);
					return;

				}

				reducePoints(orderedValues);
				return;

			}

			// if the point is amongst the other points, then simple replace the
			// worst point with it
			replaceWorstPointWith(reflectedPoint, reflectedPointFitness,
					orderedValues);

			return;

		}

		private void reducePoints(int[] orderedValues) {

			reduceCount++;
			for (int i = 1; i < orderedValues.length; i++) {

				for (int j = 0; j < points[i].length; j++) {

					points[orderedValues[i]][j] = points[orderedValues[0]][j]
					        + sigma * (points[orderedValues[i]][j] - points[orderedValues[0]][j]);
				}

				fitnesses[orderedValues[i]] = calculateResidual(points[orderedValues[i]]);
			}

		}

		private double[] calculateContractedPoint(double[] cog,
				int[] orderedValues) {

			contractCount++;
			double[] contractedPoint = new double[cog.length];

			int worstPoint = orderedValues[orderedValues.length - 1];
			for (int i = 0; i < contractedPoint.length; i++) {
				contractedPoint[i] = cog[i]	- (phi * (cog[i] - points[worstPoint][i]));
			}

			return contractedPoint;
		}

		private void replaceWorstPointWith(double[] replacingPoint,
				double replacingPointFitness, int[] orderedValues) {

			int worstPoint = orderedValues[orderedValues.length - 1];
			for (int i = 0; i < replacingPoint.length; i++) {
				points[worstPoint][i] = replacingPoint[i];
			}

			fitnesses[worstPoint] = replacingPointFitness;

		}

		private double[] calculateExtendedPoint(double[] cog,
				int[] orderedValues) {

			extendedCount++;
			double[] extendedPoint = new double[cog.length];

			for (int i = 0; i < extendedPoint.length; i++) {
				extendedPoint[i] = cog[i]
						+ gamma * (cog[i] - points[orderedValues[orderedValues.length - 1]][i]);
			}

			return extendedPoint;
		}

		private double[] calculateReflectedPoint(double[] cog,
				int[] orderedValues) {

			reflectedCount++;
			double[] reflectedPoint = new double[cog.length];

			int worstPoint = orderedValues[orderedValues.length - 1];
			for (int i = 0; i < reflectedPoint.length; i++) {
				reflectedPoint[i] = cog[i] + (alpha * (cog[i] - points[worstPoint][i]));
			}

			return reflectedPoint;
		}

		private double[] calculateCentreOfGravityIgnoringWorstValue(int[] orderedValues) {

			double[] cog = new double[points[0].length];

			for (int i = 0; i < cog.length; i++) {
				cog[i] = 0;
			}

			for (int i = 0; i < orderedValues.length - 1; i++) {
				for (int j = 0; j < cog.length; j++) {
					cog[j] += points[orderedValues[i]][j];
				}
			}

			for (int i = 0; i < cog.length; i++) {
				cog[i] = cog[i] / (orderedValues.length - 1);
			}

			return cog;
		}

		private int[] sortFitnesses() {
			int[] sortedPositions = new int[fitnesses.length];

			bubbleSort(sortedPositions);

			return sortedPositions;
		}

		private void bubbleSort(int[] sortedPositions) {

			boolean done = false;

			for (int i = 0; i < sortedPositions.length; i++) {
				sortedPositions[i] = i;
			}

			while (!done) {
				done = true;
				for (int i = 0; i < sortedPositions.length - 1; i++) {
					if (fitnesses[sortedPositions[i]] > fitnesses[sortedPositions[i + 1]]) {
						int temp = sortedPositions[i];
						sortedPositions[i] = sortedPositions[i + 1];
						sortedPositions[i + 1] = temp;
						done = false;
					}
				}
			}
		}

		public void generateStartingFitnesses() {
// TODO replace fitnesses with tree map <Double,Integer>???
// fitnesses.put(eval(points[i]), i)
			fitnesses = new double[points.length];

			for (int i = 0; i < fitnesses.length; i++) {
				fitnesses[i] = calculateResidual(points[i]);
			}

			// initialise stored best values
			int[] sortedValues = sortFitnesses();
			oldBest = fitnesses[sortedValues[0]];
			oldSpread = Math.abs(fitnesses[sortedValues[0]]
			                               - fitnesses[sortedValues[sortedValues.length - 1]]);
		}

		private void initialise(double[] parameters, double startingSpread) {

			for (int i = 0; i < points.length; i++) {

				double[] randomUnitVector = createRandomUnitVector(parameters.length);
				
				for (int j = 0; j < parameters.length; j++) {
					points[i][j] = parameters[j] + randomUnitVector[j]
							* startingSpread;
				}

			}

		}

		private double[] createRandomUnitVector(int size) {

			double[] unitVector = createRandomVector(size);

			double length = getVectorlength(unitVector);

			for (int i = 0; i < unitVector.length; i++) {
				unitVector[i] = unitVector[i] / length;
			}

			return unitVector;
		}

		private double getVectorlength(double[] vector) {

			double sum = 0.0;

			for (int i = 0; i < vector.length; i++) {
				sum += vector.length * vector.length;
			}

			return Math.sqrt(sum);
		}

		private double[] createRandomVector(int size) {

			double[] result = new double[size];

			for (int i = 0; i < size; i++) {
				result[i] = random.nextDouble() - 0.5;
			}

			return result;
		}

	}

}
