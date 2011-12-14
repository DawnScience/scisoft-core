/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;


import javax.vecmath.Vector3d;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.io.IMetaData;


/**
 * Q-space calculations
 * 
 * q = k_f - k_i where |k| = 2 pi / lambda and |q| = 4 pi sin(theta)/lambda where
 * lambda is wavelength (in Angstroms) and 2*theta is the scattering angle
 */
public class QSpace {
	private DetectorProperties detProps;
	private double kmod; // wave number
	private Vector3d ki; // initial wave vector


	public QSpace(DetectorProperties detprops, DiffractionCrystalEnvironment diffexp) {
		detProps = detprops;
		setDiffractionCrystalEnvironment(diffexp);
	}

	private void calculateInitalWavevector() {
		ki = new Vector3d(detProps.getBeamVector());
		ki.scale(kmod);
	}

	public void setDiffractionCrystalEnvironment(DiffractionCrystalEnvironment diffexp) {
		kmod = 2.*Math.PI/diffexp.getWavelength();
		calculateInitalWavevector();
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
		q.normalize();
		q.scale(kmod);
		q.sub(ki);
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
	 * @param pos
	 */
	public void pixelPosition(final Vector3d q, Vector3d p, Vector3d t, int[] pos) {
		t.set(q);
		t.add(ki);
		t.normalize();
		detProps.intersect(t, p);
		if (pos == null)
			detProps.pixelCoords(p, t);
		else
			detProps.pixelCoords(p, t, pos);
	}

	/**
	 * Calculate pixel position from given q
	 * @param q
	 * @param p output position vector in reference frame
	 * @param t output vector (x and y components are pixel coordinates)
	 */
	public void pixelPosition(final Vector3d q, Vector3d p, Vector3d t) {
		pixelPosition(q, p, t, null);
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
}
