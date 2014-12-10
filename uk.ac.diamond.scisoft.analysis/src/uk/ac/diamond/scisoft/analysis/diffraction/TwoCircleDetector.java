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
 * A two-circle detector - the detector is mounted on one arm can rotated about a point on a second
 * arm which also can rotate about a fixed base.
 */
public class TwoCircleDetector implements Cloneable {

	// all dimensions in millimetres, angles and offsets in degrees

	// laboratory frame is sited on base of gamma rotation axis that is defined as (0, 0, 1)

	Vector3d beamDir;
	Vector3d beamPos; // relative to lab frame
	double gammaOff;
	double deltaOff;
	Vector3d deltaDir; // relative to gamma frame
	Vector3d deltaPos; // base of delta rotation axis relative to lab frame
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
		beamDir = new Vector3d(0, 0, 1);
		beamPos = new Vector3d(0, circle, 0);
		gammaOff = 0;
		deltaOff = 0;
		deltaDir = new Vector3d(-1, 0, 0);
		deltaPos = new Vector3d(-circle, circle, 0);
		detectorPos = new Vector3d(circle, 0, circle);
		detectorOri = computeOrientation(new Vector3d(0, -Math.sin(detectorOffset), -Math.cos(detectorOffset)),
				new Vector3d(1, 0, 0));
	}

	TwoCircleDetector(TwoCircleDetector other) {
		beamDir = (Vector3d) other.beamDir.clone();
		beamPos = (Vector3d) other.beamPos.clone();
		gammaOff = other.gammaOff;
		deltaOff = other.deltaOff;
		deltaDir = (Vector3d) other.deltaDir.clone();
		deltaPos = (Vector3d) other.deltaPos.clone();
		detectorPos = (Vector3d) other.detectorPos.clone();
		detectorOri = (Matrix3d) other.detectorOri.clone();
	}

	@Override
	protected TwoCircleDetector clone() {
		return new TwoCircleDetector(this);
	}

	/**
	 * Set beam with a position and direction
	 * @param position (in mm)
	 * @param direction
	 */
	public void setBeam(Vector3d position, Vector3d direction) {
		beamPos = position;
		beamDir = direction;
	}

	/**
	 * Set gamma circle with angular offset
	 * @param offset (in degrees)
	 */
	public void setGamma(double offset) {
		gammaOff = offset;
	}

	/**
	 * Set delta circle with axis position and direction, and angular offset
	 * @param position (in mm)
	 * @param direction
	 * @param offset (in degrees)
	 */
	public void setDelta(Vector3d position, Vector3d direction, double offset) {
		deltaPos = position;
		deltaDir = direction;
		deltaOff = offset;
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
		detectorOri = computeOrientation(normal, fast);
	}

	/**
	 * Set detector face with position of its origin [(0,0) point], normal direction,
	 * and angle of fast axis (up from horizontal)
	 * @param position (in mm)
	 * @param normal
	 * @param angle (in degrees)
	 */
	public void setDetector(Vector3d position, Vector3d normal, double angle) {
		detectorPos = position;
		detectorOri = computeOrientation(normal, computeIntersection(normal, angle));
	}

	/**
	 * Compute intersection between plane and cone (whose apex is on the plane) for a unit vector
	 * @param normal
	 * @param angle (in degrees)
	 * @return unit vector
	 */
	private static Vector3d computeIntersection(Vector3d normal, double angle) {
		double n0s = normal.getX();
		double n1s = normal.getY();
		double n0n1 = n0s * n1s;
		n0s *= n0s;
		n1s *= n1s;
		double ang = Math.toRadians(angle);
		double ca = Math.cos(ang);
		double d = ca * ca - n1s;
		if (d <= 0) {
			throw new IllegalArgumentException("No intersection possible for given normal and angle");
		}
		double n2 = normal.getZ();
		d = Math.sqrt(d) * Math.abs(n2);
		
		double a = 1 - n1s;
		double sa = Math.sin(ang);
		double b = -n0n1 * sa;

		double x = (b - d) / a;
		// choose one where x < 0 as we need fast axis to be anti-parallel...
		if (x > 0) {
			throw new IllegalArgumentException("No solution!");
		}

		Vector3d dirn = new Vector3d(x, sa, -(x * normal.getX() + sa * normal.getY()) / n2);
		
		return dirn;
	}

	/**
	 * Compute transform to go from detector frame given normal direction and fast axis 
	 * @param normal
	 * @param fast (or component of it if it is not perpendicular to the normal)
	 * @return orientation transform from detector frame
	 */
	public static Matrix3d computeOrientation(Vector3d normal, Vector3d fast) {
		Matrix3d ori = new Matrix3d();
		Vector3d lz = new Vector3d();
		lz.negate(normal); // as normal is anti-parallel to detector frame
		lz.normalize();
		Vector3d lx = new Vector3d();
		lx.negate(fast); // as fast is anti-parallel to detector frame
		double d = lx.dot(lz);
		if (d != 0) { // ensure x-dir is perpendicular to z-dir
			if (Math.abs(Math.abs(d) - 1) < Math.ulp(1)) {
				throw new IllegalArgumentException("fast axis must not be parallel or anti-parallel to normal");
			}
			lx.scaleAdd(-d, lz, lx);
		}
		lx.normalize();
		Vector3d ly = new Vector3d();
		ly.cross(lz, lx);
		ori.setColumn(0, lx);
		ori.setColumn(1, ly);
		ori.setColumn(2, lz);
		santise(ori);
		return ori;
	}

	/**
	 * Calculate detector setup
	 */
	private void calc(double gamma, double delta) {
		if (beamDir == null || beamPos == null || deltaDir == null || deltaPos == null || detectorOri== null || detectorPos == null) {
			throw new IllegalStateException("Some vectors have not been defined");
		}

		// start at detector which is defined relative to delta frame
		Matrix3d rDelta = new Matrix3d();
		rDelta.set(new AxisAngle4d(deltaDir, Math.toRadians(delta + deltaOff)));

		origin = (Vector3d) detectorPos.clone();
		rDelta.transform(origin);

		Matrix3d rGamma = new Matrix3d();
		rGamma.set(new AxisAngle4d(new double[] {0, 1, 0, Math.toRadians(gamma + gammaOff)}));
		origin.add(deltaPos);
		rGamma.transform(origin);
		origin.sub(beamPos); // as detector properties has its frame based at the sample-beam intersection

		orientation = new Matrix3d();
		orientation.mul(rDelta, detectorOri);
		orientation.mul(rGamma, orientation);
		orientation.normalize();
		santise(orientation);
		orientation.transpose(); // make inverse as that's what detector properties needs
	}

	/**
	 * Reset entries that are less than or equal to 1 unit of least precision of
	 * the matrix's scale
	 * @param m
	 */
	private static void santise(Matrix3d m) {
		double min = Math.ulp(m.getScale());
		for (int i = 0; i < 3; i++) {
			double t;
			t = Math.abs(m.getElement(i, 0));
			if (t <= min) {
				m.setElement(i, 0, 0);
			}
			t = Math.abs(m.getElement(i, 1));
			if (t <= min) {
				m.setElement(i, 1, 0);
			}
			t = Math.abs(m.getElement(i, 2));
			if (t <= min) {
				m.setElement(i, 2, 0);
			}
		}
	}

	/**
	 * @param old
	 * @param gamma (in mm)
	 * @param delta (in mm)
	 * @return detector properties (modified in-place)
	 */
	public DetectorProperties getDetectorProperties(DetectorProperties old, double gamma, double delta) {
		calc(gamma, delta);
		old.setOrigin(origin);
		old.setOrientation(orientation);
		old.setBeamVector(beamDir);
		return old;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("beam d: ");
		out.append(beamDir);
		out.append(", p: ");
		out.append(beamPos);
		out.append(", delta d: ");
		out.append(deltaDir);
		out.append(", a: ");
		out.append(deltaOff);
		out.append(", p: ");
		out.append(deltaPos);
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
}
