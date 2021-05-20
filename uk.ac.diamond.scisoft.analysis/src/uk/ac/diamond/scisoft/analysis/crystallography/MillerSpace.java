/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;


/**
 * Crystallographic reciprocal space as defined by Miller indices
 */
public class MillerSpace {
	private Matrix3d ub; // UB
	private Matrix3d rotate; // additional rotation on top of U
	private Matrix3d toQ;
	private Matrix3d toMiller;
	private Matrix3d ta;
	private Matrix3d tb;

	/**
	 * Miller space from a UB matrix
	 * @param ub
	 */
	public MillerSpace(final Matrix3d ub) {
		this.ub = ub;
		rotate = new Matrix3d();
		rotate.setIdentity();
		toQ = new Matrix3d();
		toMiller = new Matrix3d();
		ta = new Matrix3d();
		tb = new Matrix3d();
		calcNetTransforms();
	}

	/**
	 * Miller space from a reciprocal cell and an orientation matrix
	 * @param rc
	 * @param orientation
	 */
	public MillerSpace(final ReciprocalCell rc, final Matrix3d orientation) {
		Matrix3d ortho = rc.orthogonalization();
		Matrix3d orient;
		if (orientation == null) {
			orient = new Matrix3d();
			orient.setIdentity();
		} else {
			orient = orientation;
		}
		ub = new Matrix3d();
		ub.mul(orient, ortho);
		rotate = new Matrix3d();
		rotate.setIdentity();
		toQ = new Matrix3d();
		toMiller = new Matrix3d();
		calcNetTransforms();
	}

	/**
	 * Miller space from a unit cell and an orientation matrix
	 * @param uc
	 * @param orientation
	 */
	public MillerSpace(final UnitCell uc, Matrix3d orientation) {
		this(new ReciprocalCell(uc), orientation);
	}

	private void calcNetTransforms() {
		toQ.mul(rotate, ub);
		toQ.mul(2.*Math.PI);
		toMiller.invert(toQ);
	}

	/**
	 * Set sample rotation
	 * @param rotation
	 */
	public void setRotation(final Matrix3d rotation) {
		rotate.set(rotation);
		calcNetTransforms();
	}

	/**
	 * Set sample rotation using a set of Euler angles in ZXZ order
	 * @param alpha angle about horizontal axis (parallel to beam)
	 * @param beta  angle about horizontal axis (perpendicular to beam, outward from ring))
	 * @param gamma angle about horizontal axis (parallel to beam)
	 */
	public void setRotationEulerZXZ(final double alpha, final double beta, final double gamma) {
		ta.rotZ(alpha);
		tb.rotX(beta);
		tb.mul(ta);
		rotate.rotZ(gamma);
		rotate.mul(tb);
		calcNetTransforms();
	}

	/**
	 * Set sample rotation using a set of Euler angles in ZYZ order
	 * @param alpha angle about horizontal axis (parallel to beam)
	 * @param beta  angle about vertical axis (upward)
	 * @param gamma angle about horizontal axis (parallel to beam)
	 */
	public void setRotationEulerZYZ(final double alpha, final double beta, final double gamma) {
		ta.rotZ(alpha);
		tb.rotY(beta);
		tb.mul(ta);
		rotate.rotZ(gamma);
		rotate.mul(tb);
		calcNetTransforms();
	}

	/**
	 * Set sample rotation using a set of angles in H.You's convention where
	 * at zeros the sample mount normal is pointing horizontally outward from ring.
	 * The arguments are given in order from ground to sample mount.
	 * 
	 * <p>
	 * H.You, "Angle calculations for a '4S+2D' six-circle diffractometer',
	 * J.Appl.Cryst., v32, pp614-23 (1999)
	 * 
	 * @param mu rotation angle about vertical axis (from laboratory frame)
	 * @param eta rotation angle about horizontal axis (perpendicular to beam, into ring)
	 * @param chi rotation angle about horizontal axis (parallel to beam)
	 * @param phi rotation angle about horizontal axis (perpendicular to beam, into ring)
	 */
	public void setRotationYouConvention(final double mu, final double eta, final double chi, final double phi) {
		ta.rotY(mu);
		tb.rotX(-eta);
		tb.mul(ta);
		ta.rotZ(chi);
		tb.mul(ta);
		rotate.rotX(-phi);
		rotate.mul(tb);
		calcNetTransforms();
	}

	/**
	 * Set scattering vector q for given reflection defined by Miller indices.
	 * This scattering vector will only be valid if the reflection lies on the
	 * Ewald sphere
	 * @param h
	 * @param q
	 */
	public void q(final Vector3d h, Vector3d q) {
		q.set(h);
		toQ.transform(q);
	}

	/**
	 * @param h
	 * @param k
	 * @param l
	 * @return q for given reflection defined by Miller indices
	 */
	public Vector3d q(final double h, final double k, final double l) {
		Vector3d q = new Vector3d(h, k, l);
		toQ.transform(q);
		return q;
	}

	/**
	 * @param h
	 * @param k
	 * @param l
	 * @return q for given reflection defined by Miller indices
	 */
	public Vector3d q(final int h, final int k, final int l) {
		return q((double) h, (double) k, (double) l);
	}

	/**
	 * Calculate Miller indices from a q vector and store in given h vector
	 * @param q
	 * @param rotation can be null but overrides internal rotation state
	 * @param h
	 */
	public void h(final Vector3d q, final Matrix3d rotation, final Vector3d h) {
		h.set(q);
		if (rotation != null)
			setRotation(rotation);
		toMiller.transform(h);
	}

	/**
	 * @return matrix to transform to Miller space
	 */
	public Matrix3d getMillerTransform( ) {
		return new Matrix3d(toMiller);
	}

	/**
	 * Calculate Miller indices from a q vector
	 * @param q
	 * @param rotation can be null but overrides internal rotation state
	 */
	public void h(final Vector3d q, final Matrix3d rotation, final double[] h) {
		Vector3d H = new Vector3d();
		h(q, rotation, H);

		H.get(h);
	}

	/**
	 * Calculate Miller indices from a q vector
	 * @param q
	 * @param rotation can be null but overrides internal rotation state
	 * @return Miller indices
	 */
	public double[] h(final Vector3d q, final Matrix3d rotation) {
		double[] h = new double[3];
		h(q, rotation, h);
		return h;
	}

	/**
	 * Calculate (floored not rounded) integer Miller indices from a q vector
	 * @param q
	 * @param rotation can be null but overrides internal rotation state
	 * @return integer Miller indices
	 */
	public int[] integerh(final Vector3d q, final Matrix3d rotation) {
		Vector3d H = new Vector3d();
		h(q, rotation, H);

		int[]h = new int[3];
		h[0] = (int) Math.floor(H.x);
		h[1] = (int) Math.floor(H.y);
		h[2] = (int) Math.floor(H.z);

		return h;
	}
}
