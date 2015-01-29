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
	 * @param init
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
//		f.setInitial(I16_NOMINAL_PARAMS);
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
		 * 8-parameter fit function: beam dir (t,p),
		 *  detector pos, detector normal, detector fast axis angle from horizontal
		 * 10-parameter fit function: beam dir (t,p), delta dir,
		 *  detector pos, detector normal, detector fast axis angle from horizontal
		 * 18-parameter fit function: beam pos (x,y,z), beam dir (t,p), gamma offset,
		 *  delta pos, delta dir, delta offset,
		 *  detector pos, detector normal, detector fast axis angle from horizontal
		 * @param prop
		 * @param detector
		 * @param n
		 * @param gamma
		 * @param delta
		 * @param x
		 * @param y
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
			return getTwoCircleParameters(two, getN());
		}

		/**
		 * Set two-circle detector according to parameters
		 * @param p
		 */
		public void setDetector(double[] p) {
			setupTwoCircle(two, p);
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
			return diff;
		}
	}

	static Vector3d createDirection(double a, double b) {
		double theta = Math.toRadians(a);
		double phi = Math.toRadians(b);
		double st = Math.sin(theta);
		return new Vector3d(st*Math.cos(phi), st*Math.sin(phi), Math.cos(theta));
	}

	/**
	 * Setup two circle detector
	 * 8-parameter fit function: beam dir (t,p),
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 * 10-parameter fit function: beam dir (t,p), delta dir,
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 * 18-parameter fit function: beam pos (x,y,z), beam dir (t,p), gamma offset,
	 *  delta pos, delta dir, delta offset,
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 * @param two
	 * @param p
	 */
	public static void setupTwoCircle(TwoCircleDetector two, double... p) {
		int i = 0;
		switch (p.length) {
		case 8:
			two.setBeam(two.beamPos, createDirection(p[i++], p[i++]));
			two.setGamma(two.gammaOff);
			two.setDelta(two.deltaPos, two.deltaDir, two.deltaOff);
			break;
		case 10:
			two.setBeam(two.beamPos, createDirection(p[i++], p[i++]));
			two.setGamma(two.gammaOff);
			two.setDelta(two.deltaPos, createDirection(p[i++], p[i++]), two.deltaOff);
			break;
		case 18:
			two.setBeam(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]));
			two.setGamma(p[i++]);
			two.setDelta(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
			break;
		default:
			throw new IllegalArgumentException("Number of parameters not specified correctly");
		}
		two.setDetector(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
	}

	private static double[] getAngles(Vector3d v) {
		return new double[] {Math.toDegrees(Math.acos(v.z)), Math.toDegrees(Math.atan2(v.y, v.z))};
	}

	/**
	 * Get parameters from two circle detector
	 * 8-parameter fit function: beam dir (t,p),
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 * 10-parameter fit function: beam dir (t,p), delta dir,
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 * 18-parameter fit function: beam pos (x,y,z), beam dir (t,p), gamma offset,
	 *  delta pos, delta dir, delta offset,
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 * @param two
	 * @param n
	 * @return parameters
	 */
	public static double[] getTwoCircleParameters(TwoCircleDetector two, int n) {
		double[] params = new double[n];
		int i = 0;
		double[] a;
		Vector3d v;
		switch (n) {
		case 8:
			a = getAngles(two.beamDir);
			params[i++] = a[0];
			params[i++] = a[1];

			params[i++] = two.detectorPos.x;
			params[i++] = two.detectorPos.y;
			params[i++] = two.detectorPos.z;
			v = new Vector3d(0, 0, -1);
			two.detectorOri.transform(v);
			a = getAngles(v);
			params[i++] = a[0];
			params[i++] = a[1];
			v.set(-1, 0, 0);
			two.detectorOri.transform(v);
			params[i++] = Math.toDegrees(Math.acos(v.y));
			break;
		case 10:
			a = getAngles(two.beamDir);
			params[i++] = a[0];
			params[i++] = a[1];
			a = getAngles(two.deltaDir);
			params[i++] = a[0];
			params[i++] = a[1];

			params[i++] = two.detectorPos.x;
			params[i++] = two.detectorPos.y;
			params[i++] = two.detectorPos.z;
			v = new Vector3d(0, 0, -1);
			two.detectorOri.transform(v);
			a = getAngles(v);
			params[i++] = a[0];
			params[i++] = a[1];
			v.set(-1, 0, 0);
			two.detectorOri.transform(v);
			params[i++] = Math.toDegrees(Math.acos(v.y));
			break;
		case 18:
			params[i++] = two.beamPos.x;
			params[i++] = two.beamPos.y;
			params[i++] = two.beamPos.z;
			a = getAngles(two.beamDir);
			params[i++] = a[0];
			params[i++] = a[1];
			params[i++] = two.gammaOff;
			params[i++] = two.deltaPos.x;
			params[i++] = two.deltaPos.y;
			params[i++] = two.deltaPos.z;
			a = getAngles(two.deltaDir);
			params[i++] = a[0];
			params[i++] = a[1];
			params[i++] = two.deltaOff;
		
			params[i++] = two.detectorPos.x;
			params[i++] = two.detectorPos.y;
			params[i++] = two.detectorPos.z;
			v = new Vector3d(0, 0, -1);
			two.detectorOri.transform(v);
			a = getAngles(v);
			params[i++] = a[0];
			params[i++] = a[1];
			v.set(-1, 0, 0);
			two.detectorOri.transform(v);
			params[i++] = Math.toDegrees(Math.acos(v.y));
			break;
		default:
			throw new IllegalArgumentException("Number of parameters not specified correctly");
		}

		return params;
	}
}
