/*-
 * Copyright 2015 Diamond Light Source Ltd.
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

public class MatrixUtils {

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
		MatrixUtils.santise(ori);
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
		MatrixUtils.santise(ori);
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
	 * Test to see if expected value is close to actual value within given tolerances
	 * @param e expected value
	 * @param a actual value
	 * @param rel relative tolerance
	 * @param abs absolute tolerance
	 * @return true if close
	 */
	public static boolean isClose(double e, double a, final double rel, final double abs) {
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
	public static boolean isClose(Vector3d e, Vector3d a, final double rel, final double abs) {
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
	public static boolean isClose(Matrix3d e, Matrix3d a, final double rel, final double abs) {
		double tt = rel * e.getScale() + abs;
		if (!e.epsilonEquals(a, tt)) {
			throw new AssertionError(e + " != " + a);
		}
		return true;
	}
}
