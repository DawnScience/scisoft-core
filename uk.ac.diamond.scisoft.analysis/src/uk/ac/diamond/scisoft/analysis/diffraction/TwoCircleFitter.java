/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Vector3d;

import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class TwoCircleFitter {
	private static Logger logger = LoggerFactory.getLogger(TwoCircleFitter.class);

	/**
	 * 
	 * @param prop
	 * @param init
	 * @param gamma angles (in degrees) for gamma circle
	 * @param delta angles (in degrees) for delta circle
	 * @param x x-coordinates of beam centre in pixels
	 * @param y y-coordinates of beam centre in pixels
	 * @return fitted detector
	 */
	public static TwoCircleDetector fitDetector(DetectorProperties prop, TwoCircleDetector init, double[] gamma, double[] delta, double[] x, double[] y) {
		int pts = gamma.length;
		if (delta.length != pts || x.length != pts || y.length != pts) {
			throw new IllegalArgumentException("Input angles and coordinates must be arrays of the same length");
		}

		TwoCircleFitFunction f = new TwoCircleFitFunction(prop, init, gamma, delta, x, y);
		/* 18-parameter fit function: beam pos (x,y,z), beam dir (t,p), gamma offset, delta pos, delta dir, delta offset,
		 * detector pos, detector normal, detector fast axis angle from horizontal
		 */
		f.setInitial(0, 0, 950, 0, 0, 0, -970, 950, 0, 90, 180, 0,
				33.54, 0, 535, 180-35, 0, 90);
		MultivariateOptimizer opt = FittingUtils.createOptimizer(FittingUtils.Optimizer.CMAES, f.getN());
		double res = FittingUtils.optimize(f, opt, Double.POSITIVE_INFINITY);

		logger.debug("Parameters: p {} (min {})", new Object[] { f.getParameters(), res });
		logger.debug("Residual value: {}", f.value(f.getParameters()));
		return f.getTwoCircle();
	}

	static Vector3d createDirection(double a, double b) {
		double theta = Math.toRadians(a);
		double phi = Math.toRadians(b);
		double st = Math.sin(theta);
		return new Vector3d(st*Math.cos(phi), st*Math.sin(phi), Math.cos(theta));
	}

	/**
	 * Setup two circle detector
	 * @param two
	 * @param p
	 */
	public static void setupTwoCircle(TwoCircleDetector two, double... p) {
		int i = 0;
		two.setBeam(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]));
		two.setGamma(p[i++]);
		two.setDelta(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
		two.setDetector(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
	}

	/**
	 * 18-parameter fit function: beam pos (x,y,z), beam dir (t,p), gamma offset, delta pos, delta dir, delta offset,
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 */
	static class TwoCircleFitFunction implements FittingUtils.FitFunction {
		private double[] initial;
		protected double[] parameters;
		protected SimpleBounds bounds;
		protected double[] sigma;
		private DetectorProperties dp;
		private TwoCircleDetector two;
		private double[] gamma;
		private double[] delta;
		private double[] x;
		private double[] y;
		private int pts;

		protected static final double SIGMA_POSN = 1./16; // (in mm)
		protected static final double SIGMA_FANG = 1./8; // (in degrees)
		protected static final double SIGMA_ANG  = 2; // (in degrees)

		public TwoCircleFitFunction(DetectorProperties prop, TwoCircleDetector detector, double[] gamma, double[] delta, double[] x, double[] y) {
			initial = null;
			parameters = null;
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
					SIGMA_POSN, SIGMA_POSN, SIGMA_POSN, SIGMA_ANG, SIGMA_ANG, SIGMA_FANG,
					SIGMA_POSN, SIGMA_POSN, SIGMA_POSN, SIGMA_FANG, SIGMA_FANG, SIGMA_FANG
			};

			dp = prop;
			two = detector.clone();
			this.gamma = gamma;
			this.delta = delta;
			this.x = x;
			this.y = y;
			pts = x.length;
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

		void setDetector(double[] p) {
			setupTwoCircle(two, p);
		}

		TwoCircleDetector getTwoCircle() {
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
				two.getDetectorProperties(dp, gamma[i], delta[i]);
				double[] bc = dp.getBeamCentreCoords();
				t = bc[0] - x[i];
				diff += t * t;
				t = bc[1] - y[i];
				diff += t * t;
			}
			return diff;
		}

	}
}
