/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.SimplePointChecker;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateFunctionPenaltyAdapter;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.MultiDirectionalSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.random.Well19937c;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for fitting
 */
public class FittingUtils {
	private static Logger logger = LoggerFactory.getLogger(TwoCircleFitter.class);

	public static Long seed = null;

	private static final int MAX_ITER = 10000;
	private static final int MAX_EVAL = 1000000;
	private static final double REL_TOL = 1e-7;
	private static final double ABS_TOL = 1e-15;
	enum Optimizer { Simplex, CMAES, BOBYQA}
	static Optimizer optimizer = Optimizer.CMAES;

	/**
	 * @param n
	 * @return optimizer
	 */
	public static MultivariateOptimizer createOptimizer(int n) {
		switch (optimizer) {
		case BOBYQA:
			return new BOBYQAOptimizer(n + 2);
		case CMAES:
			return new CMAESOptimizer(MAX_ITER, 0., true, 0, 10, seed == null ? new Well19937c() : new Well19937c(seed),
					false, new SimplePointChecker<PointValuePair>(REL_TOL, ABS_TOL));
		case Simplex:
		default:
			return new SimplexOptimizer(new SimplePointChecker<PointValuePair>(REL_TOL*1e3, ABS_TOL*1e3));
		}
	}

	/**
	 * Optimize given function
	 * @param f
	 * @param opt
	 * @param min
	 * @return residual
	 */
	public static double optimize(FitFunction f, MultivariateOptimizer opt, double min) {
		double res = Double.NaN;
		try {
			PointValuePair result;

			switch (optimizer) {
			case BOBYQA:
				result = opt.optimize(new InitialGuess(f.getInit()), GoalType.MINIMIZE, new ObjectiveFunction(f),
						new MaxEval(MAX_EVAL), f.getBounds());
				break;
			case CMAES:
				int p = (int) Math.ceil(4 + Math.log(f.getN())) + 1;
				logger.trace("Population size: {}", p);
				result = opt.optimize(new InitialGuess(f.getInit()), GoalType.MINIMIZE, new ObjectiveFunction(f),
						new CMAESOptimizer.Sigma(f.getSigma()), new CMAESOptimizer.PopulationSize(p),
						new MaxEval(MAX_EVAL), f.getBounds());
				break;
			case Simplex:
			default:
				int n = f.getN();
				double offset = 1e12;
				double[] scale = new double[n];
				for (int i = 0; i < n; i++) {
					scale[i] = offset*0.25;
				}
				SimpleBounds bnds = f.getBounds();
				MultivariateFunctionPenaltyAdapter of = new MultivariateFunctionPenaltyAdapter(f, bnds.getLower(), bnds.getUpper(), offset, scale);
				result = opt.optimize(new InitialGuess(f.getInit()), GoalType.MINIMIZE,
						new ObjectiveFunction(of), new MaxEval(MAX_EVAL),
						new MultiDirectionalSimplex(n));
//				new NelderMeadSimplex(n));
				break;
			}

			// logger.info("Q-space fit: rms = {}, x^2 = {}", opt.getRMS(), opt.getChiSquare());
			double ires = f.value(opt.getStartPoint());
			logger.trace("Residual: {} from {}", result.getValue(), ires);
			res = result.getValue();
			if (res < min)
				f.setParameters(result.getPoint());
			logger.trace("Used {} evals and {} iters", opt.getEvaluations(), opt.getIterations());
//			System.err.printf("Used %d evals and %d iters\n", opt.getEvaluations(), opt.getIterations());
			// logger.info("Q-space fit: rms = {}, x^2 = {}", opt.getRMS(), opt.getChiSquare());
		} catch (IllegalArgumentException e) {
			logger.error("Start point has wrong dimension", e);
			// should not happen!
		} catch (TooManyEvaluationsException e) {
			throw new IllegalArgumentException("Could not fit as optimizer did not converge");
//				logger.error("Convergence problem: max iterations ({}) exceeded", opt.getMaxIterations());
		}

		return res;
	}

	/**
	 * Basic fit function interface
	 */
	public interface FitFunction extends MultivariateFunction {
		public void setParameters(double[] arg);
		public double[] getParameters();

		public double[] getSigma();
		public SimpleBounds getBounds();

		public double[] getInit();
		public void setInit(double[] init);

		/**
		 * @return number of parameters
		 */
		public int getN();
	}

}
