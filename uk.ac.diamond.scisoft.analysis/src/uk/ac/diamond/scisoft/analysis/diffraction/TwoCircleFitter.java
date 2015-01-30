/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class supports a number of ways to fit a detector using the direct beam method:
 * <dl>
 * <dt>8-parameter</dt>
 * <dd>beam dir (t,p),
 *  detector pos, detector normal, detector fast axis angle from horizontal</dd>
 * <dt>10-parameter</dt>
 * <dd>beam dir (t,p), delta dir,
 *  detector pos, detector normal, detector fast axis angle from horizontal</dd>
 * <dt>18-parameter</dt>
 * <dd>	beam pos (x,y,z), beam dir (t,p), gamma offset,
 *  delta pos, delta dir, delta offset,
 *  detector pos, detector normal, detector fast axis angle from horizontal</dd>
 * </dl>
 * where 0 <= t <= 90, -180 < p <= 180, -90 < axis angle <= 90
 */
public class TwoCircleFitter {
	private static Logger logger = LoggerFactory.getLogger(TwoCircleFitter.class);

	/**
	 * Nominal 18 parameters for diffractometer on I16
	 * beam pos (x,y,z), beam dir (t,p), gamma offset,
	 * delta pos, delta dir, delta offset,
	 * detector pos, detector normal, detector fast axis angle from horizontal
	 */
	public static double[] I16_NOMINAL_PARAMS = new double[] {0, 789, 0, 0, 0, 0,
		-938.5, 789, 0, 90, 180, 0,
		637+271-73+83.764*0.5, (30.5+33.54)*Math.sqrt(0.5), 500 - (30.5+33.54)*Math.sqrt(0.5), 180-45, 0, 90
		};

	/**
	 * 
	 * @param prop
	 * @param init detector
	 * @param n
	 * @param gamma angles (in degrees) for gamma circle
	 * @param delta angles (in degrees) for delta circle
	 * @param x x-coordinates of beam centre in pixels
	 * @param y y-coordinates of beam centre in pixels
	 * @return fitted detector
	 */
	public static TwoCircleDetector fitDetector(DetectorProperties prop, TwoCircleDetector init, int n, double[] gamma, double[] delta, double[] x, double[] y) {
		int pts = gamma.length;
		if (delta.length != pts || x.length != pts || y.length != pts) {
			throw new IllegalArgumentException("Input angles and coordinates must be arrays of the same length");
		}

		TwoCircleFitFunction f = new TwoCircleFitFunction(prop, init, n, gamma, delta, x, y);
		MultivariateOptimizer opt = FittingUtils.createOptimizer(FittingUtils.Optimizer.CMAES, f.getN());
		double res = FittingUtils.optimize(f, opt, Double.POSITIVE_INFINITY);

		logger.debug("Parameters: p {} (min {})", new Object[] { f.getParameters(), res });
		logger.debug("Residual value: {}", f.value(f.getParameters()));
		return f.getTwoCircle();
	}

	static class TwoCircleFitFunction implements FittingUtils.FitFunction {
		private double[] initial;
		private double[] parameters;
		final private SimpleBounds bounds;
		final private double[] sigma;
		private DetectorProperties dp;
		private TwoCircleDetector two;
		private double[] gamma;
		private double[] delta;
		private double[] x;
		private double[] y;
		private int pts;

		private static final double SIGMA_POSN = 1./16; // (in mm)
		private static final double SIGMA_FANG = 1./8; // (in degrees)
//		private static final double SIGMA_ANG  = 2; // (in degrees)

		/**
		 * @param prop
		 * @param detector
		 * @param n
		 * @param gamma angles (in degrees) for gamma circle
		 * @param delta angles (in degrees) for delta circle
		 * @param x x-coordinates of beam centre in pixels
		 * @param y y-coordinates of beam centre in pixels
		 */
		public TwoCircleFitFunction(DetectorProperties prop, TwoCircleDetector detector, int n, double[] gamma, double[] delta, double[] x, double[] y) {
			initial = null;
			parameters = null;
			dp = prop;
			two = detector.clone();
			this.gamma = gamma;
			this.delta = delta;
			this.x = x;
			this.y = y;
			pts = x.length;
			switch (n) {
			case 8:
				bounds = new SimpleBounds(new double[] {
						0, -180,
						Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0, -180, -90
						}, new double[] {
						180, 180,
						Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 180, 180, 90
				});
				sigma = new double[] {
						SIGMA_FANG, SIGMA_FANG,
						SIGMA_POSN, SIGMA_POSN, SIGMA_POSN, SIGMA_FANG, SIGMA_FANG, SIGMA_FANG
				};
				break;
			case 10:
				bounds = new SimpleBounds(new double[] {
						0, -180, 0, -180,
						Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0, -180, -90
						}, new double[] {
						180, 180, 180, 180,
						Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 180, 180, 90
				});
				sigma = new double[] {
						SIGMA_FANG, SIGMA_FANG, SIGMA_FANG, SIGMA_FANG,
						SIGMA_POSN, SIGMA_POSN, SIGMA_POSN, SIGMA_FANG, SIGMA_FANG, SIGMA_FANG
				};
				break;
			case 18:
				bounds = new SimpleBounds(new double[] {
						Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0, -180, -180,
						Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0, -180, -180,
						Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 0, -180, -90
						}, new double[] {
						Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 180, 180, 180,
						Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 180, 180, 180,
						Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 180, 180, 90
				});
				sigma = new double[] {
						SIGMA_POSN, SIGMA_POSN, SIGMA_POSN, SIGMA_FANG, SIGMA_FANG, SIGMA_FANG,
						SIGMA_POSN, SIGMA_POSN, SIGMA_POSN, SIGMA_FANG, SIGMA_FANG, SIGMA_FANG,
						SIGMA_POSN, SIGMA_POSN, SIGMA_POSN, SIGMA_FANG, SIGMA_FANG, SIGMA_FANG
				};
				break;
			default:
				throw new IllegalArgumentException("Number of parameters not specified correctly");
			}
		}

		@Override
		public void setParameters(double[] point) {
			parameters = point.clone();
			setDetector(parameters);
		}

		@Override
		public int getN() {
			return sigma.length;
		}

		@Override
		public double[] getSigma() {
			return sigma;
		}

		@Override
		public SimpleBounds getBounds() {
			return bounds;
		}

		@Override
		public double[] getInitial() {
			if (initial == null) {
				initial = getFromDetector();
			}
			return initial;
		}

		@Override
		public void setInitial(double... init) {
			initial = init.clone();
			setDetector(initial);
		}

		@Override
		public double[] getParameters() {
			return parameters;
		}

		/**
		 * @return parameters from two-circle detector
		 */
		public double[] getFromDetector() {
			return TwoCircleDetector.getTwoCircleParameters(two, getN());
		}

		/**
		 * Set two-circle detector according to parameters
		 * @param p
		 */
		public void setDetector(double[] p) {
			TwoCircleDetector.setupTwoCircle(two, p);
		}

		/**
		 * @return two-circle detector
		 */
		public TwoCircleDetector getTwoCircle() {
			return two;
		}

		@Override
		public double value(double[] point) {
			try {
				setDetector(point);
			} catch (Exception e) {
				return Double.MAX_VALUE;
			}
			double diff = 0;
			double t;
			for (int i = 0; i < pts; i++) {
				two.updateDetectorProperties(dp, gamma[i], delta[i]);
				double[] bc = dp.getBeamCentreCoords();
				t = bc[0] - x[i];
				diff += t * t;
				t = bc[1] - y[i];
				diff += t * t;
			}
//			System.err.println("V @ " + Arrays.toString(point) + " = " + diff);
			return diff;
		}
	}
}
