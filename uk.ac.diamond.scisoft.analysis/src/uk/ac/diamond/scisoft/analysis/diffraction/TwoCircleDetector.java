/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;

/**
 * A two-circle detector - the detector is mounted on one arm (delta) can rotated about a point on a second
 * arm (gamma) which also can rotate about a fixed base.
 *
 * The laboratory frame is sited with its origin at the sample centre with gamma rotation axis that is defined
 * as (0, 1, 0) [vertical] and delta rotation axis that is defined relative to the gamma as (-1, 0, 0) [horizontal].
 * The beam is assumed to pass through the sample at the origin and has direction (0, 0, 1) [horizontal].
 * All diffractometer circles are assumed to be centred on the origin.
 */
public class TwoCircleDetector implements Cloneable {

	// all dimensions in millimetres, angles and offsets in degrees

	double gammaOff;
	double deltaOff;
	Vector3d detectorPos; // relative to delta frame
	Matrix3d detectorOri; // relative to delta frame

	Matrix3d orientation; // detector orientation relative to lab frame
	Vector3d origin; // position of detector origin relative to beam

	private static final double detectorOffset = Math.toRadians(9);
	private static final double circle = 1000;

	/**
	 * create forward transformation for a two-circle mounting of a detector
	 * 
	 */
	public TwoCircleDetector() {
		gammaOff = 0;
		deltaOff = 0;
		detectorPos = new Vector3d(circle, 0, circle);
		detectorOri = MatrixUtils.computeOrientation(new Vector3d(0, -Math.sin(detectorOffset), -Math.cos(detectorOffset)),
				new Vector3d(1, 0, 0));
	}

	TwoCircleDetector(TwoCircleDetector other) {
		gammaOff = other.gammaOff;
		deltaOff = other.deltaOff;
		detectorPos = (Vector3d) other.detectorPos.clone();
		detectorOri = (Matrix3d) other.detectorOri.clone();
	}

	@Override
	protected TwoCircleDetector clone() {
		return new TwoCircleDetector(this);
	}

	/**
	 * Set gamma circle with angular offset
	 * @param offset (in degrees)
	 */
	public void setGamma(double offset) {
		gammaOff = offset;
	}

	/**
	 * Set detector face with position of its origin [(0,0) point], normal direction,
	 * and fast axis direction
	 * @param position (in mm)
	 * @param normal
	 * @param fast (or component of it if it is not perpendicular to the normal)
	 */
	public void setDetector(Vector3d position, Vector3d normal, Vector3d fast) {
		detectorPos = position;
		detectorOri = MatrixUtils.computeOrientation(normal, fast);
	}

	/**
	 * Set detector face with position of its origin [(0,0) point], normal direction,
	 * and angle of fast axis (anti-clockwise from meridian)
	 * @param position (in mm)
	 * @param normal
	 * @param angle (in degrees)
	 */
	public void setDetector(Vector3d position, Vector3d normal, double angle) {
		detectorPos = position;
		detectorOri = MatrixUtils.computeOrientation(normal, MatrixUtils.computeTangent(normal, angle));
	}

	/**
	 * Calculate detector setup
	 */
	private void calc(double gamma, double delta) {
		if (detectorOri== null || detectorPos == null) {
			throw new IllegalStateException("Some vectors have not been defined");
		}

		// start at detector which is defined relative to delta frame
		Matrix3d rDelta = new Matrix3d();
		rDelta.set(new AxisAngle4d(-1, 0, 0, Math.toRadians(delta + deltaOff)));

		origin = (Vector3d) detectorPos.clone();
		rDelta.transform(origin);

		Matrix3d rGamma = new Matrix3d();
		rGamma.set(new AxisAngle4d(0, 1, 0, Math.toRadians(gamma + gammaOff)));
		rGamma.transform(origin);

		orientation = new Matrix3d();
		orientation.mul(rDelta, detectorOri);
		orientation.mul(rGamma, orientation);
		orientation.normalize();
		MatrixUtils.santise(orientation);
		orientation.transpose(); // make inverse as that's what detector properties needs
	}

	/**
	 * @param old
	 * @param gamma (in degrees)
	 * @param delta (in degrees)
	 */
	public void updateDetectorProperties(DetectorProperties old, double gamma, double delta) {
		calc(gamma, delta);
		old.setOrigin(origin);
		old.setOrientation(orientation);
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(",\ndelta a: ");
		out.append(deltaOff);
		out.append("\ndet d: ");
		out.append(detectorOri);
		out.append("p: ");
		out.append(detectorPos);
		out.append("\no: ");
		out.append(origin);
		out.append("\n");
		out.append(orientation);

		return out.toString();
	}

	public boolean isClose(TwoCircleDetector other, final double rel, final double abs) {
		return MatrixUtils.isClose(gammaOff, other.gammaOff, rel, abs) && MatrixUtils.isClose(deltaOff, other.deltaOff, rel, abs)
				&& MatrixUtils.isClose(detectorPos, other.detectorPos, rel, abs) && MatrixUtils.isClose(detectorOri, other.detectorOri, rel, abs);
	}

	static Vector3d createDirection(double a, double b) {
		double theta = Math.toRadians(a);
		double phi = Math.toRadians(b);
		double st = Math.sin(theta);
		return new Vector3d(st*Math.cos(phi), st*Math.sin(phi), Math.cos(theta));
	}

	/**
	 * Setup two circle detector in a variety of ways (0 <= t <= 90, -180 < p <= 180, -90 < axis angle <= 90)
	 * <dl>
	 * <dt>6-parameter</dt>
	 * <dd>detector pos, detector normal, detector fast axis angle from meridian</dd>
	 * </dl>
	 * @param two
	 * @param p only array lengths of 8, 10, 18 are supported
	 */
	public static void setupTwoCircle(TwoCircleDetector two, double... p) {
		if (p.length != 6)
			throw new IllegalArgumentException("Number of parameters not specified correctly");
		int i = 0;
		two.setDetector(new Vector3d(p[i++], p[i++], p[i++]), createDirection(p[i++], p[i++]), p[i++]);
	}

	private static double[] getAngles(Vector3d v) {
		return new double[] {Math.toDegrees(Math.acos(v.z)), Math.toDegrees(Math.atan2(v.y, v.x))};
	}

	/**
	 * Get parameters from two circle detector (0 <= t <= 90, -180 < p <= 180, -90 < axis angle <= 90) 
	 * <dl>
	 * <dt>6-parameter</dt>
	 * <dd>detector pos, detector normal, detector fast axis angle from horizontal</dd>
	 * </dl>
	 * @param two
	 * @param n only value of 6
	 * @return parameters
	 */
	public static double[] getTwoCircleParameters(TwoCircleDetector two, int n) {
		double[] params = new double[n];
		int i = 0;
		double[] a;
		Vector3d v;
		if (n != 6) {
			throw new IllegalArgumentException("Number of parameters not specified correctly");
		}
	
		params[i++] = two.detectorPos.x;
		params[i++] = two.detectorPos.y;
		params[i++] = two.detectorPos.z;
		v = new Vector3d(0, 0, -1);
		two.detectorOri.transform(v);
		Vector3d t = MatrixUtils.computeMeridionalTangent(v);
		a = getAngles(v);
		params[i++] = a[0];
		params[i++] = a[1];
		v.set(-1, 0, 0);
		two.detectorOri.transform(v);
		Vector3d u = new Vector3d(0, -1, 0);
		two.detectorOri.transform(u);
		params[i++] = Math.toDegrees(Math.atan2(t.dot(u), t.dot(v)));
//		System.err.println("Params: " + Arrays.toString(params));
		return params;
	}
}
