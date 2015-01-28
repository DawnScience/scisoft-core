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

		TwoCircleFitFunctionBase f = new TwoCircleFitFunction10(prop, init, gamma, delta, x, y);
//		f.setInitial(I16_NOMINAL_PARAMS);
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

	static double[] getAngles(Vector3d v) {
		return new double[] {Math.toDegrees(Math.acos(v.z)), Math.toDegrees(Math.atan2(v.y, v.z))};
	}

	static abstract class TwoCircleFitFunctionBase implements FittingUtils.FitFunction {
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

		public TwoCircleFitFunctionBase(DetectorProperties prop, TwoCircleDetector detector, double[] gamma, double[] delta, double[] x, double[] y) {
			initial = null;
			parameters = null;
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

		abstract public double[] getFromDetector();

		void setDetector(double[] p) {
			switch (p.length) {
			case 8:
				setupTwoCircle8(two, p);
				break;
			case 10:
				setupTwoCircle10(two, p);
				break;
			case 18:
				setupTwoCircle18(two, p);
				break;
			default:
				throw new IllegalArgumentException("Number of parameters not specified correctly");
			}
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

	/**
	 * Setup two circle detector
	 * @param two
	 * @param p
	 */
	public static void setupTwoCircle18(TwoCircleDetector two, double... p) {
		int i = 0;
		two.setBeam(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]));
		two.setGamma(p[i++]);
		two.setDelta(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
		two.setDetector(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
	}

	/**
	 * Get 18 parameters from two circle detector
	 * @param two
	 */
	public static double[] getTwoCircle18Parameters(TwoCircleDetector two) {
		double[] params = new double[18];
		int i = 0;
		params[i++] = two.beamPos.x;
		params[i++] = two.beamPos.y;
		params[i++] = two.beamPos.z;
		double[] a = getAngles(two.beamDir);
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
		Vector3d v = new Vector3d(0, 0, -1);
		two.detectorOri.transform(v);
		a = getAngles(v);
		params[i++] = a[0];
		params[i++] = a[1];
		v.set(-1, 0, 0);
		two.detectorOri.transform(v);
		params[i++] = Math.toDegrees(Math.acos(v.y));
		return params;
	}

	/**
	 * 18-parameter fit function: beam pos (x,y,z), beam dir (t,p), gamma offset, delta pos, delta dir, delta offset,
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 */
	static class TwoCircleFitFunction18 extends TwoCircleFitFunctionBase {

		public TwoCircleFitFunction18(DetectorProperties prop, TwoCircleDetector detector, double[] gamma, double[] delta, double[] x, double[] y) {
			super(prop, detector, gamma, delta, x, y);
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
		}

		@Override
		public double[] getFromDetector() {
			return getTwoCircle18Parameters(getTwoCircle());
		}
	}

	/**
	 * Setup two circle detector
	 * @param two
	 * @param p
	 */
	public static void setupTwoCircle10(TwoCircleDetector two, double... p) {
		int i = 0;
		two.setBeam(two.beamPos, createDirection(p[i++], p[i++]));
		two.setGamma(two.gammaOff);
		two.setDelta(two.deltaPos, createDirection(p[i++], p[i++]), two.deltaOff);
		two.setDetector(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
	}

	/**
	 * Get 10 parameters from two circle detector
	 * @param two
	 */
	public static double[] getTwoCircle10Parameters(TwoCircleDetector two) {
		double[] params = new double[18];
		int i = 0;
		double[] a = getAngles(two.beamDir);
		params[i++] = a[0];
		params[i++] = a[1];
		a = getAngles(two.deltaDir);
		params[i++] = a[0];
		params[i++] = a[1];
		params[i++] = two.detectorPos.x;
		params[i++] = two.detectorPos.y;
		params[i++] = two.detectorPos.z;
		Vector3d v = new Vector3d(0, 0, -1);
		two.detectorOri.transform(v);
		a = getAngles(v);
		params[i++] = a[0];
		params[i++] = a[1];
		v.set(-1, 0, 0);
		two.detectorOri.transform(v);
		params[i++] = Math.toDegrees(Math.acos(v.y));
		return params;
	}

	/**
	 * 10-parameter fit function: beam dir (t,p), delta dir,
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 */
	static class TwoCircleFitFunction10 extends TwoCircleFitFunctionBase {

		public TwoCircleFitFunction10(DetectorProperties prop, TwoCircleDetector detector, double[] gamma, double[] delta, double[] x, double[] y) {
			super(prop, detector, gamma, delta, x, y);
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
		}

		@Override
		public double[] getFromDetector() {
			return getTwoCircle10Parameters(getTwoCircle());
		}
	}

	/**
	 * Setup two circle detector
	 * @param two
	 * @param p
	 */
	public static void setupTwoCircle8(TwoCircleDetector two, double... p) {
		int i = 0;
		two.setBeam(two.beamPos, createDirection(p[i++], p[i++]));
		two.setGamma(two.gammaOff);
		two.setDelta(two.deltaPos, two.deltaDir, two.deltaOff);
		two.setDetector(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
	}

	/**
	 * Get 8 parameters from two circle detector
	 * @param two
	 */
	public static double[] getTwoCircle8Parameters(TwoCircleDetector two) {
		double[] params = new double[18];
		int i = 0;
		double[] a = getAngles(two.beamDir);
		params[i++] = a[0];
		params[i++] = a[1];
		params[i++] = two.detectorPos.x;
		params[i++] = two.detectorPos.y;
		params[i++] = two.detectorPos.z;
		Vector3d v = new Vector3d(0, 0, -1);
		two.detectorOri.transform(v);
		a = getAngles(v);
		params[i++] = a[0];
		params[i++] = a[1];
		v.set(-1, 0, 0);
		two.detectorOri.transform(v);
		params[i++] = Math.toDegrees(Math.acos(v.y));
		return params;
	}

	/**
	 * 8-parameter fit function: beam dir (t,p),
	 *  detector pos, detector normal, detector fast axis angle from horizontal
	 */
	static class TwoCircleFitFunction8 extends TwoCircleFitFunctionBase {

		public TwoCircleFitFunction8(DetectorProperties prop, TwoCircleDetector detector, double[] gamma, double[] delta, double[] x, double[] y) {
			super(prop, detector, gamma, delta, x, y);
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
		}

		@Override
		public double[] getFromDetector() {
			return getTwoCircle8Parameters(getTwoCircle());
		}
	}
}
