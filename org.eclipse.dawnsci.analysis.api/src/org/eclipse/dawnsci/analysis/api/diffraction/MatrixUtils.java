/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.api.diffraction;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

public class MatrixUtils {
	private MatrixUtils() {
	}

	/**
	 * Compute tangent to direction vector that lies along meridian (from pole)
	 * <p>
	 * In the case where the direction is polar, the prime meridian is chosen
	 * @param direction
	 * @return unit vector
	 */
	public static Vector3d computeMeridionalTangent(Vector3d direction) {
		double dz = direction.z;
		Vector3d t;
		if (Math.abs(dz) == 1) {
			t = new Vector3d(dz, 0, 0);
			t.normalize();
		} else if (dz == 0) {
			t = new Vector3d(0, 0, -1);
		} else {
			t = new Vector3d(0, 0, -1/dz);
			t.add(direction);
			t.normalize();
		}
		return t;
	}


	/**
	 * Compute tangent to direction vector that makes an angle with meridian (from pole)
	 * <p>
	 * In the case where the direction is polar, the prime meridian is chosen
	 * @param direction
	 * @param angle (in degrees)
	 * @return unit vector
	 */
	public static Vector3d computeTangent(Vector3d direction, double angle) {
		Vector3d t = computeMeridionalTangent(direction);
		Matrix3d r = new Matrix3d();
		r.set(new AxisAngle4d(direction, Math.toRadians(angle)));
		r.transform(t);
		return t;
	}

	/**
	 * Compute intersection between plane and cone (whose apex is on the plane) for a unit vector
	 * @param normal
	 * @param angle (in degrees)
	 * @return unit vector
	 */
	public static Vector3d computeIntersection(Vector3d normal, double angle) {
		double n0s = normal.getX();
		double n1s = normal.getY();
		double n0n1 = n0s * n1s;
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
	
		return new Vector3d(x, sa, -(x * normal.getX() + sa * normal.getY()) / n2);
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
	 * Compute transform to go from detector frame given fast and slow axes 
	 * @param fast direction of fastest varying pixels (i.e. row direction)
	 * @param slow direction of slowest varying pixels (i.e. column direction)
	 * (or component of it if it is not perpendicular to the fast)
	 * @return orientation transform from detector frame
	 */
	public static Matrix3d computeFSOrientation(Vector3d fast, Vector3d slow) {
		Matrix3d ori = new Matrix3d();
		Vector3d lx = new Vector3d();
		lx.negate(fast); // as fast is anti-parallel to detector frame
		Vector3d ly = new Vector3d();
		ly.negate(slow); // as slow is anti-parallel to detector frame
	
		double d = lx.dot(ly);
		if (d != 0) { // ensure x-dir is perpendicular to z-dir
			if (Math.abs(Math.abs(d) - 1) < Math.ulp(1)) {
				throw new IllegalArgumentException("fast axis must not be parallel or anti-parallel to normal");
			}
			ly.scaleAdd(-d, lx, ly);
		}
	
		Vector3d lz = new Vector3d();
		lx.normalize();
		ly.normalize();
		lz.cross(lx, ly);
		ori.setColumn(0, lx);
		ori.setColumn(1, ly);
		ori.setColumn(2, lz);
		santise(ori);
		return ori;
	}

	/**
	 * Reset entries that are less than or equal to 1 unit of least precision of
	 * the matrix's scale
	 * @param m
	 */
	public static void santise(Matrix3d m) {
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
	 * Reset entries that are less than or equal to 1 unit of least precision of
	 * the vector's magnitude
	 * @param v
	 */
	public static void santise(Vector3d v) {
		double min = Math.ulp(Math.hypot(v.x, Math.hypot(v.y, v.z)));
		double t;
		t = Math.abs(v.x);
		if (t <= min) {
			v.setX(0);
		}
		t = Math.abs(v.y);
		if (t <= min) {
			v.setY(0);
		}
		t = Math.abs(v.z);
		if (t <= min) {
			v.setZ(0);
		}
	}

	/**
	 * Test to see if expected value is close to actual value within given tolerances
	 * @param e expected value
	 * @param a actual value
	 * @param rel relative tolerance
	 * @param abs absolute tolerance
	 * @return true if close
	 */
	public static boolean isClose(double e, double a, final double rel, final double abs) throws AssertionError {
		double tt = rel * Math.max(Math.abs(e), Math.abs(a)) + abs;
		if (Math.abs(e - a) > tt) {
			throw new AssertionError(e + " != " + a);
		}
		return true;
	}

	/**
	 * Test to see if expected value is close to actual value within given tolerances
	 * @param e expected value
	 * @param a actual value
	 * @param rel relative tolerance
	 * @param abs absolute tolerance
	 * @return true if close
	 */
	public static boolean isClose(Vector3d e, Vector3d a, final double rel, final double abs) throws AssertionError {
		double tt = rel * Math.max(e.length(), a.length()) + abs;
		if (!e.epsilonEquals(a, tt)) {
			throw new AssertionError(e + " != " + a);
		}
		return true;
	}

	/**
	 * Test to see if expected value is close to actual value within given tolerances
	 * @param e expected value
	 * @param a actual value
	 * @param rel relative tolerance
	 * @param abs absolute tolerance
	 * @return true if close
	 */
	public static boolean isClose(Matrix3d e, Matrix3d a, final double rel, final double abs) throws AssertionError {
		double tt = rel * e.getScale() + abs;
		if (!e.epsilonEquals(a, tt)) {
			throw new AssertionError(e + " != " + a);
		}
		return true;
	}


	/**
	 * Create rotation matrix with given axis and angle
	 * @param axis
	 * @param angle in degrees
	 * @return matrix
	 */
	public static Matrix3d createRotationMatrix(Vector3d axis, double angle) {
		Matrix3d r = new Matrix3d();
		r.set(new AxisAngle4d(axis, Math.toRadians(angle)));
		return r;
	}

	private static final double FIFTY_DEGREES = Math.toRadians(50);
	private static final double COS_50 = Math.cos(FIFTY_DEGREES);
	private static final double SIN_50 = Math.sin(FIFTY_DEGREES);

	/**
	 * Create matrix to rotate from sample on end of phi arm to CBF laboratory frame for a kappa goniometer.
	 * All angles in degrees
	 * @param phi (horizontal, +ve y)
	 * @param kappa (50 degrees from horizontal, away from sample, +ve y, -ve z) 
	 * @param theta (horizontal, +ve y)
	 * @param mu (vertical, +ve z)
	 * @return rotation matrix
	 */
	public static Matrix3d createI16KappaRotation(double phi, double kappa, double theta, double mu) {
		Matrix3d rotn = createRotationMatrix(new Vector3d(1, 0, 0), mu);
		rotn.mul(createRotationMatrix(new Vector3d(0, 1, 0), theta));
		rotn.mul(createRotationMatrix(new Vector3d(0, COS_50, -SIN_50), kappa));
		rotn.mul(createRotationMatrix(new Vector3d(0, 1, 0), phi));
		return rotn;
	}

	/**
	 * Create matrix to rotate from sample on end of phi arm to CBF laboratory frame for an Euler goniometer.
	 * All angles in degrees
	 * @param phi (horizontal, +ve y)
	 * @param chi (horizontal, +ve z)
	 * @param eta (horizontal, +ve y)
	 * @param mu (vertical, +ve z)
	 * @return rotation matrix
	 */
	public static Matrix3d createI16EulerRotation(double phi, double chi, double eta, double mu) {
		// Rx(mu) * Ry(eta) * Rz(chi) * Ry(phi)
		Matrix3d rotn = new Matrix3d();
		Matrix3d t = new Matrix3d();
		rotn.rotX(Math.toRadians(mu));

		t.rotY(Math.toRadians(eta));
		rotn.mul(t);

		t.rotZ(Math.toRadians(chi));
		rotn.mul(t);

		t.rotY(Math.toRadians(phi));
		rotn.mul(t);
		return rotn;
	}

	/**
	 * Create matrix to orient from local frame to laboratory frame.
	 * Lab coordinate frame is +ve y upwards, +ve z forwards and the three
	 * rotations are applied in order of the arguments
	 * All angles in degrees
	 * @param alpha (+ve z)
	 * @param beta (+ve y)
	 * @param gamma (+ve z)
	 * @return active orientation matrix
	 */
	public static Matrix3d createOrientationFromEulerZYZ(double alpha, double beta, double gamma) {
		return createOrientationFromEulerZYZRadians(Math.toRadians(alpha), Math.toRadians(beta), Math.toRadians(gamma));
	}

	static Matrix3d createOrientationFromEulerZYZRadians(double alpha, double beta, double gamma) {
		Matrix3d tz = new Matrix3d();
		Matrix3d t = new Matrix3d();

		// the active (extrinsic) transformation becomes Rz(gamma) Ry(beta) Rz(alpha)
		tz.rotZ(gamma);

		t.rotY(beta);
		tz.mul(t);

		t.rotZ(alpha);
		tz.mul(t);

		santise(tz);
		return tz;
	}

	/**
	 * Calculate Euler ZYZ angles from given orientation matrix
	 * @param orient active transformation from local to laboratory frame
	 * @return angles in degrees
	 */
	public static double[] calculateEulerZYZ(Matrix3d orient) {
		double alpha;
		double gamma;
		double cb = orient.getM22();

		if (Math.abs(cb) < 1) {
			alpha = Math.atan2(orient.getM21(), -orient.getM20());
			gamma = Math.atan2(orient.getM12(), orient.getM02());
		} else { // gimbal lock case
			alpha = Math.atan2(orient.getM10(), orient.getM11());
			gamma = 0;
		}

		return new double[] {Math.toDegrees(alpha), Math.toDegrees(Math.acos(cb)), Math.toDegrees(gamma)};
	}

	/**
	 * Calculate yaw, pitch and roll angles from given orientation
	 * @param orient
	 * @return array of angles (in radians)
	 */
	public static double[] calculateYawPitchRoll(Matrix3d orient) {
		double roll;
		double yaw;
		double sp = orient.getM12();

		if (Math.abs(sp) < 1) {
			yaw  = Math.atan2(-orient.getM02(), orient.getM22());
			roll = Math.atan2(-orient.getM10(), orient.getM11());
		} else {
			// gimbal lock case
			yaw  = Math.atan2(sp*orient.getM01(), orient.getM00());
			roll = 0;
		}
	
		return new double[] {yaw, Math.asin(sp), roll};
	}

	/**
	 * Calculate transformation for orienting an object with given angles (in radians)
	 * 
	 * @see MatrixUtils#createOrientationFromYawPitchRoll(double, double, double)
	 * 
	 * @param angles
	 * @return active transformation
	 */
	public static Matrix3d createOrientationFromYawPitchRoll(double... angles) {
		int n = angles == null ? 0 : angles.length;
		double yaw = n == 0 ? 0 : angles[0];
		double pitch = n < 2 ? 0 : angles[1];
		double roll = n < 3 ? 0 : angles[2];

		return createOrientationFromYawPitchRoll(yaw, pitch, roll);
	}

	/**
	 * Calculate transformation for orienting an object with given angles (in radians)
	 * 
	 * Lab coordinate frame is +ve y upwards, +ve z forwards and the three
	 * rotations are applied in reverse order of the arguments
	 * 
	 * @param yaw rotate about vertical axis ((-pi, pi] with positive is to the right, east or clockwise looking down)
	 * @param pitch rotate about horizontal axis ([-pi/2, pi/2] with positive is upwards)
	 * @param roll rotate about +ve z ((-pi, pi] with positive is clockwise looking forward)
	 * @return active transformation
	 */
	public static Matrix3d createOrientationFromYawPitchRoll(final double yaw, final double pitch, final double roll) {
		Matrix3d ty = new Matrix3d();
		Matrix3d t = new Matrix3d();

		// the active (extrinsic) transformation becomes Ry(-yaw) Rx(-pitch) Rz(-roll)
		ty.rotY(-yaw);
		t.rotX(-pitch);
		ty.mul(t);
		t.rotZ(-roll);
		ty.mul(t);
		santise(ty);
		return ty;
	}
}
