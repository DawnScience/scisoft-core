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

package gda.analysis.utils.optimisation;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class performs the Nelder-Mead of simplex minimisation
 */
public class Neldermead {

	/**
	 * Setup the logging facilities
	 */
	private static final Logger logger = LoggerFactory.getLogger(Neldermead.class);

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

	/**
	 * The main optimisation method
	 * 
	 * @param parameters
	 * @param problemDefinition
	 * @param finishCriteria
	 * @return a double array of the parameters for the optimisation
	 * @throws Exception 
	 */
	public double[] optimise(double[] parameters,
			ProblemDefinition problemDefinition, double finishCriteria) throws Exception {

		double[] solution = parameters;
		
		// TODO make this more adaptive than just running 5 times, should work to a tolerance
		// However 5 times gives a reasonable solution for the time being without too much 
		// overhead.
		for (int p = 0; p < 5; p++) {
			boolean ok = true;
			
			while (ok) {
				// this outer loop handles collapsing simplexes
				Simplex simplex = new Simplex(solution, startingSpread);
	
				simplex.generateStartingFitnesses(problemDefinition);
	
				//for(int i = 0; i < 100*problemDefinition.getNumberOfParameters(); i++) {
				//	simplex.iterate(problemDefinition);
				//}
				
				int iterationCount = 0;
				
				do {
					simplex.iterate(problemDefinition);
				} while (simplex.improving(finishCriteria) && ++iterationCount < maxIterations );
	
				solution = simplex.getBestSolution();
	
				ok = simplex.hasCollapsed();
				
				//System.out.println("Extend count "+simplex.getExtendedCount());
				//System.out.println("Contract count "+simplex.getContractCount());
				//System.out.println("Reduce count "+simplex.getReduceCount());
				//System.out.println("Reflect count "+simplex.getReflectedCount());
				
				
			}
			//System.out.println("Pass "+p+" solution is "+ problemDefinition.eval(solution));
		}

		//System.out.println(problemDefinition.eval(solution));
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
				logger.info("Error, the NelderMead simplex has collapsed, this minimisation may be flawed");
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

		public void iterate(ProblemDefinition problemDefinition) throws Exception {

			int[] orderedValues = sortFitnesses();

			double[] cog = calculateCentreOfGravityIgnoringWorstValue(orderedValues);

			double[] reflectedPoint = calculateReflectedPoint(cog,
					orderedValues);

			double reflectedPointFitness = problemDefinition
					.eval(reflectedPoint);

			// if the reflected point is better than the best point

			if (reflectedPointFitness < fitnesses[orderedValues[0]]) {

				double[] extendedPoint = calculateExtendedPoint(cog,
						orderedValues);

				double extendedPointFitness = problemDefinition
						.eval(extendedPoint);

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

				double contractedPointFitness = problemDefinition
						.eval(contractedPoint);

				// if this is better than the worst point
				if (contractedPointFitness < fitnesses[orderedValues[orderedValues.length - 1]]) {
					replaceWorstPointWith(contractedPoint,
							contractedPointFitness, orderedValues);
					return;

				}

				reducePoints(orderedValues, problemDefinition);
				return;

			}

			// if the point is amongst the other points, then simple replace the
			// worst point with it
			replaceWorstPointWith(reflectedPoint, reflectedPointFitness,
					orderedValues);

			return;

		}

		private void reducePoints(int[] orderedValues,
				ProblemDefinition problemDefinition) throws Exception {

			reduceCount++;
			for (int i = 1; i < orderedValues.length; i++) {

				for (int j = 0; j < points[i].length; j++) {

					points[orderedValues[i]][j] = points[orderedValues[0]][j]
					        + sigma * (points[orderedValues[i]][j] - points[orderedValues[0]][j]);
				}

				double newFitness = problemDefinition.eval(points[orderedValues[i]]);

				fitnesses[orderedValues[i]] = newFitness;
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

		public void generateStartingFitnesses(ProblemDefinition problemDefinition) throws Exception {
// TODO replace fitnesses with tree map <Double,Integer>???
// fitnesses.put(eval(points[i]), i)
			fitnesses = new double[points.length];

			for (int i = 0; i < fitnesses.length; i++) {
				fitnesses[i] = problemDefinition.eval(points[i]);
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
