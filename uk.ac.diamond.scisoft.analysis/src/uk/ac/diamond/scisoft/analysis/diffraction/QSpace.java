/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;


/**
 * Q-space calculations
 * 
 * q = k_f - k_i where |k| = 2 pi / lambda and |q| = 4 pi sin(theta)/lambda where
 * lambda is wavelength (in Angstroms) and 2*theta is the scattering angle
 */
public class QSpace {
	private static final double QSCALE_DEFAULT = 2. * Math.PI;
	private DetectorProperties detProps;
	private double kmod; // wave number
	private Vector3d ki; // initial wave vector
	private Vector3d mki; // minus initial wave vector
	private double qScale;
	private double residual; // fitting mean of squared residuals
	private Vector3d reference;
	private Vector4d stokes;

	public QSpace(DetectorProperties detprops, DiffractionCrystalEnvironment diffexp, double scale) {
		detProps = detprops;
		qScale=scale;
		setDiffractionCrystalEnvironment(diffexp);
	}

	public QSpace(DetectorProperties detprops, DiffractionCrystalEnvironment diffexp) {
		this(detprops, diffexp, QSCALE_DEFAULT);
	}

	private void calculateInitalWavevector() {
		ki = new Vector3d(detProps.getBeamVector());
		ki.scale(kmod);
		mki = new Vector3d(ki);
		mki.negate();
	}

	public void setDiffractionCrystalEnvironment(DiffractionCrystalEnvironment diffexp) {
		kmod = qScale/diffexp.getWavelength();
		calculateInitalWavevector();
		reference = diffexp.getReferenceNormal();
		stokes = diffexp.getStokesVector();
	}

	/**
	 * @return wavelength in Angstroms
	 */
	public double getWavelength() {
		return qScale/kmod;
	}

	public void setDetectorProperties(DetectorProperties detprops) {
		detProps = detprops;
		calculateInitalWavevector();
	}

	public DetectorProperties getDetectorProperties() {
		return detProps;
	}

	public Vector3d getInitialWavevector() {
		return ki;
	}

	/**
	 * Work out q from pixel coordinates 
	 * @param x
	 * @param y
	 * @param q
	 */
	public void qFromPixelPosition(final double x, final double y, Vector3d q) {
		detProps.pixelPosition(x, y, q);
		convertToQ(q);
	}

	/**
	 * Convert position vector into q
	 * @param q position vector
	 */
	public void convertToQ(Vector3d q) {
		double l = q.length();
		if (l > 0) {
			q.scaleAdd(kmod/l, mki);
		} else {
			q.add(mki);
		}
	}

	/**
	 * Work out q from pixel coordinates 
	 * @param x
	 * @param y
	 * @param q
	 */
	public void qFromPixelPosition(final int x, final int y, Vector3d q) {
		qFromPixelPosition((double) x, (double) y, q);
	}

	/**
	 * @return q vector in inverse Angstroms
	 */
	public Vector3d qFromPixelPosition(final double x, final double y) {
		Vector3d q = new Vector3d();
		qFromPixelPosition(x, y, q);
		return q;
	}

	/**
	 * @return q vector in inverse Angstroms
	 */
	public Vector3d qFromPixelPosition(final int x, final int y) {
		return qFromPixelPosition((double) x, (double) y);
	}

	/**
	 * @return max |q| that detector can see
	 */
	public double maxModQ() {
		double twotheta = detProps.getMaxScatteringAngle();
		return 2.*kmod*Math.sin(0.5*twotheta);
	}

	/**
	 * Calculate pixel position from given q
	 * @param q
	 * @param p output position vector in reference frame
	 * @param t output vector (x and y components are pixel coordinates)
	 */
	public void pixelPosition(final Vector3d q, Vector3d p, Vector3d t) {
		t.set(q);
		t.add(ki);
		t.normalize();
		detProps.intersect(t, p);
		detProps.pixelCoords(p, t);
	}

	/**
	 * Calculate pixel position from given q
	 * @param q
	 * @param t output vector (x and y components are pixel coordinates)
	 */
	public void pixelPosition(final Vector3d q, Vector3d t) {
		Vector3d p = new Vector3d();
		pixelPosition(q, p, t);
	}

	/**
	 * Calculate pixel position from given q
	 * @param q
	 * @return position of pixel
	 */
	public int[] pixelPosition(final Vector3d q) {
		Vector3d t = new Vector3d(q);
		t.add(ki);
		t.normalize();
		return detProps.pixelCoords(detProps.intersect(t));
	}

	/**
	 * Calculate scattering angle corresponding to given q vector
	 * @param q
	 * @return scattering angle (two-theta) in radians
	 */
	public double scatteringAngle(final Vector3d q) {
		return 2.*Math.asin(0.5*q.length()/kmod);
	}

	/**
	 * Calculate solid angle subtended by pixel
	 * @param x
	 * @param y
	 * @return solid angle
	 */
	public double calculateSolidAngle(final int x, final int y) {
		return detProps.calculateSolidAngle(x, y);
	}

	@Override
	public String toString() {
		return detProps + "; " + getWavelength();
	}

	/**
	 * @return mean of squared residuals
	 */
	public double getResidual() {
		return residual;
	}

	/**
	 * Set mean of squared residuals
	 * @param residual
	 */
	public void setResidual(double residual) {
		this.residual = residual;
	}

	/**
	 * @return normal to reference place for polarization 
	 */
	public Vector3d getReferenceNormal() {
		return reference;
	}

	/**
	 * @return Stokes parameters as a vector to describe polarization state of beam
	 */
	public Vector4d getStokesVector() {
		return stokes;
	}
}
