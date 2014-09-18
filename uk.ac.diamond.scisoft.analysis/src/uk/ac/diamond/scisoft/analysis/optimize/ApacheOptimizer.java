/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.MultivariateVectorFunction;
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
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunctionGradient;
import org.apache.commons.math3.optim.nonlinear.scalar.gradient.NonLinearConjugateGradientOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.gradient.NonLinearConjugateGradientOptimizer.Formula;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.MultiDirectionalSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.PowellOptimizer;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;
import org.apache.commons.math3.random.Well19937c;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheOptimizer extends AbstractOptimizer {
	private static Logger logger = LoggerFactory.getLogger(ApacheOptimizer.class);

	public MultivariateFunction createFunction() {
		// provide the fitting function which wrappers all the normal fitting functionality
		MultivariateFunction f = new MultivariateFunction() {

			@Override
			public double value(double[] parameters) {
				return calculateResidual(parameters);
			}
		};

		return f;
	}

	public MultivariateVectorFunction createGradientFunction() {
		MultivariateVectorFunction f = new MultivariateVectorFunction() {
			
			@Override
			public double[] value(double[] parameters) throws IllegalArgumentException {
				double[] result = new double[n];
				
				for (int i = 0; i < n; i++) {
					result[i] = calculateResidualDerivative(params.get(i), parameters);
				}
				return result;
			}
		};

		return f;
	}

	public Long seed = null;

	private static final int MAX_ITER = 10000;
	private static final int MAX_EVAL = 1000000;
	private static final double REL_TOL = 1e-7;
	private static final double ABS_TOL = 1e-15;

	public enum Optimizer { SIMPLEX_MD, SIMPLEX_NM, POWELL, CMAES, BOBYQA, CONJUGATE_GRADIENT }

	Optimizer optimizer;

	public ApacheOptimizer(Optimizer opt) {
		optimizer = opt;
	}

	private MultivariateOptimizer createOptimizer() {
		SimplePointChecker<PointValuePair> checker = new SimplePointChecker<PointValuePair>(REL_TOL, ABS_TOL);
		switch (optimizer) {
		case CONJUGATE_GRADIENT:
			return new NonLinearConjugateGradientOptimizer(Formula.POLAK_RIBIERE, checker);
		case BOBYQA:
			return new BOBYQAOptimizer(n + 2);
		case CMAES:
			return new CMAESOptimizer(MAX_ITER, 0., true, 0, 10, seed == null ? new Well19937c() : new Well19937c(seed),
					false, new SimplePointChecker<PointValuePair>(REL_TOL, ABS_TOL));
		case POWELL:
			return new PowellOptimizer(REL_TOL, ABS_TOL, checker);
		case SIMPLEX_MD:
		case SIMPLEX_NM:
		default:
			return new SimplexOptimizer(checker);
		}
	}

	private SimpleBounds createBounds() {
		double[] lb = new double[n];
		double[] ub = new double[n];
		for (int i = 0; i < n; i++) {
			IParameter p = params.get(i);
			lb[i] = p.getLowerLimit();
			ub[i] = p.getUpperLimit();
		}

		return new SimpleBounds(lb, ub);
	}

	@Override
	void internalOptimize() throws Exception {
		MultivariateOptimizer opt = createOptimizer();
		MultivariateFunction fn = createFunction();
		ObjectiveFunction of = new ObjectiveFunction(fn);
		InitialGuess ig = new InitialGuess(getParameterValues());
		SimpleBounds bd = createBounds();
		MaxEval me = new MaxEval(MAX_EVAL);
		double min = Double.POSITIVE_INFINITY;
		double res = Double.NaN;
		double offset = 1e12;
		double[] scale = new double[n];
		for (int i = 0; i < n; i++) {
			scale[i] = offset*0.25;
		}
		MultivariateFunctionPenaltyAdapter af;

		try {
			PointValuePair result;

			switch (optimizer) {
			case CONJUGATE_GRADIENT:
//				af = new MultivariateFunctionPenaltyAdapter(fn, bd.getLower(), bd.getUpper(), offset, scale);
				result = opt.optimize(ig, GoalType.MINIMIZE, new ObjectiveFunction(fn), me,
						new ObjectiveFunctionGradient(createGradientFunction()));
				break;
			case BOBYQA:
				result = opt.optimize(ig, GoalType.MINIMIZE, of, me, bd);
				break;
			case CMAES:
				double[] sigma = new double[n];
				for (int i = 0; i < n; i++) {
					IParameter p = params.get(i);
					double v = p.getValue();
					double r = Math.max(p.getUpperLimit()-v, v - p.getLowerLimit());
					sigma[i] = r*0.05; // 5% of range
				}
				int p = (int) Math.ceil(4 + Math.log(n)) + 1;
				logger.trace("Population size: {}", p);
				result = opt.optimize(ig, GoalType.MINIMIZE, of, me, bd,
						new CMAESOptimizer.Sigma(sigma), new CMAESOptimizer.PopulationSize(p));
				break;
			case SIMPLEX_MD:
			default:
				af = new MultivariateFunctionPenaltyAdapter(fn, bd.getLower(), bd.getUpper(), offset, scale);
				result = opt.optimize(ig, GoalType.MINIMIZE, new ObjectiveFunction(af), me,
						new MultiDirectionalSimplex(n));
				break;
			case SIMPLEX_NM:
				af = new MultivariateFunctionPenaltyAdapter(fn, bd.getLower(), bd.getUpper(), offset, scale);
				result = opt.optimize(ig, GoalType.MINIMIZE, new ObjectiveFunction(af), me,
						new NelderMeadSimplex(n));
				break;
			}

			// logger.info("Q-space fit: rms = {}, x^2 = {}", opt.getRMS(), opt.getChiSquare());
			double ires = calculateResidual(opt.getStartPoint());
			logger.trace("Residual: {} from {}", result.getValue(), ires);
			res = result.getValue();
			if (res < min)
				setParameterValues(result.getPoint());
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
	}
}
