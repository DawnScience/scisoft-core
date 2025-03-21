/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Peter Chang - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.analysis.api.diffraction;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironmentEvent.EventType;

/**
 * Container for parameters that a crystal is subject to in a diffraction experiment
 */
public class DiffractionCrystalEnvironment implements Serializable, Cloneable {
	/**
	 * Update this when there are any serious changes to API
	 */
	static final long serialVersionUID = 4306363319254548387L;
	//  TODO add transmission

	private double wavelength;   // in Angstroms
	private double phiStart;     // in degrees
	private double phiRange;     // in degrees
	private double exposureTime; // in seconds
	private double oscGap;       // in degrees
	private boolean fire = true;
	private Matrix3d orientation;

	// TODO move controller away from model?
	private transient Set<IDiffractionCrystalEnvironmentListener> diffCrystEnvListeners;
	private final static Vector3d DEFAULT_DIR = new Vector3d(0, 0, 1);

	private Vector3d dirn = new Vector3d(DEFAULT_DIR);
	private Vector3d referenceNormal = new Vector3d(0, 1, 0); // horizontal plane in DLS frame has normal in vertical direction
	private Vector4d stokesVector = new Vector4d(1, 1, 0, 0); // fully polarized horizontally

	/**
	 * @param wavelength in Angstroms
	 */
	public DiffractionCrystalEnvironment(double wavelength) {
		this(wavelength, Double.NaN, Double.NaN, Double.NaN, Double.NaN);
	}

	/**
	 * @param wavelength in Angstroms
	 * @param phiStart in degrees
	 * @param phiRange in degrees
	 * @param exposureTime in seconds
	 */
	public DiffractionCrystalEnvironment(double wavelength, double phiStart, double phiRange, double exposureTime) {
		this(wavelength, phiStart, phiRange, exposureTime, Double.NaN);
	}

	/**
	 * @param wavelength in Angstroms
	 * @param phiStart in degrees
	 * @param phiRange in degrees
	 * @param exposureTime in seconds
	 * @param oscGap in degrees
	 */
	public DiffractionCrystalEnvironment(double wavelength, double phiStart, double phiRange, double exposureTime, double oscGap) {
		this.wavelength = wavelength;
		this.phiStart = phiStart;
		this.phiRange = phiRange;
		this.exposureTime = exposureTime;
		this.oscGap = oscGap;
	}

	/**
	 * null constructor
	 */
	public DiffractionCrystalEnvironment() {
	}

	/*
	 *  @param diffenv The DiffractionCrystalEnvironment to copy
	 */
	public DiffractionCrystalEnvironment(DiffractionCrystalEnvironment diffenv) {
		wavelength = diffenv.getWavelength();
		phiStart = diffenv.getPhiStart();
		phiRange = diffenv.getPhiRange();
		exposureTime = diffenv.getExposureTime();
	}

	public static DiffractionCrystalEnvironment getDefaultDiffractionCrystalEnvironment() {
		double lambda = 0.9;
		double startOmega = 0.0;
		double rangeOmega = 1.0;
		double exposureTime = 1.0;
		double oscGap = 0;
		
		return new DiffractionCrystalEnvironment(lambda, startOmega, rangeOmega, exposureTime, oscGap);
	}
	
	@Override
	public DiffractionCrystalEnvironment clone() {
		return new DiffractionCrystalEnvironment(wavelength, phiStart, phiRange, exposureTime);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(exposureTime);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(phiRange);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(phiStart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(wavelength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(oscGap);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	private boolean equalOrBothNaNs(double a, double b) {
		if (Double.isNaN(a) && Double.isNaN(b))
			return true;
		return a == b;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiffractionCrystalEnvironment other = (DiffractionCrystalEnvironment) obj;
		if (!equalOrBothNaNs(exposureTime, other.exposureTime))
			return false;
		if (!equalOrBothNaNs(phiRange, other.phiRange))
			return false;
		if (!equalOrBothNaNs(phiStart, other.phiStart))
			return false;
		if (!equalOrBothNaNs(wavelength, other.wavelength))
			return false;
		if (!equalOrBothNaNs(oscGap, other.oscGap))
			return false;
		return true;
	}

	/**
	 * @return wavelength in Angstroms
	 */
	public double getWavelength() {
		return wavelength;
	}

	/**
	 * Energy equivalent (1eV)/(h c) [m^-1] from NIST CODATA 2018
	 */
	private static final double CODATA_FACTOR = 8.065543937e5;

	/**
	 * Calculate photon energy from wavelength
	 * @param wavelength in Angstroms
	 * @return energy in keV
	 */
	public static double calculateEnergy(double wavelength) {
		// energy(keV) = 10^7 * (h * c / e) / lambda(A) 
		// NB, 1e7 is factor from 10^-3 * 10^10 to convert eV to keV and m to A
		return 1e7 / (CODATA_FACTOR * wavelength);
	}

	/**
	 * Calculate wavelength from photon energy
	 * @param energy in keV
	 * @return wavelength in Angstroms
	 */
	public static double calculateWavelength(double energy) {
		// lambda(A) = 10^7 * (h * c / e) / energy(keV)
		return calculateEnergy(energy);
	}

	/**
	 * @return keV energy
	 */
	public double getEnergy() {
		return calculateEnergy(wavelength);
	}

	/**
	 * Sets the wavelength from energy in keV
	 * @param keV energy
	 */
	public void setWavelengthFromEnergykeV(double keV) {
		this.wavelength = calculateWavelength(keV);
		// Tell listeners
		fireDiffractionCrystalEnvironmentListeners(new DiffractionCrystalEnvironmentEvent(this, EventType.WAVELENGTH));
	}

	/**
	 * Set wavelength in Angstroms
	 * @param wavelength
	 */
	public void setWavelength(double wavelength) {
		this.wavelength = wavelength;
		fireDiffractionCrystalEnvironmentListeners(new DiffractionCrystalEnvironmentEvent(this, EventType.WAVELENGTH));
	}
	
	/**
	 * @return the phi value for the start of the image in degrees
	 */
	public double getPhiStart() {
		return phiStart;
	}

	/**
	 * Set the phi value at start of the diffraction image in degrees
	 * @param phiStart 
	 */
	public void setPhiStart(double phiStart) {
		this.phiStart = phiStart;
	}

	/**
	 * @return the phi range of the image in degrees
	 */
	public double getPhiRange() {
		return phiRange;
	}

	/**
	 * Set the phi range in degrees
	 * @param phiRange 
	 */
	public void setPhiRange(double phiRange) {
		this.phiRange = phiRange;
	}
	
	/**
	 * @return Exposure time in seconds
	 */
	public double getExposureTime() {
		return exposureTime;
	}

	/**
	 * Set the exposure time in seconds
	 * @param exposureTime
	 */
	public void setExposureTime(double exposureTime) {
		this.exposureTime = exposureTime;
	}
	
	/**
	 * @return the oscillation gap value of diffraction image in degrees
	 */
	public double getOscGap() {
		return oscGap;
	}

	/**
	 * Set the oscillation gap value of diffraction image in degrees
	 * @param oscGap
	 */
	public void setOscGap(double oscGap) {
		this.oscGap = oscGap;
	}

	/**
	 * Set the orientation of the crystal
	 * @param orientation
	 */
	public void setOrientation(Matrix3d orientation) {
		this.orientation = orientation;
	}

	/**
	 * Get the orientation of the crystal
	 * @return orientation
	 */
	public Matrix3d getOrientation() {
		return orientation;
	}

	public void addDiffractionCrystalEnvironmentListener(IDiffractionCrystalEnvironmentListener l) {
		if (diffCrystEnvListeners==null) 
			diffCrystEnvListeners = new HashSet<IDiffractionCrystalEnvironmentListener>(5);
		diffCrystEnvListeners.add(l);
	}

	/**
	 * Call from dispose of part listening to diffraction crystal environment changing
	 * @param l
	 */
	public void removeDiffractionCrystalEnvironmentListener(IDiffractionCrystalEnvironmentListener l) {
		if (diffCrystEnvListeners==null) 
			return;
		diffCrystEnvListeners.remove(l);
	}
	
	protected void fireDiffractionCrystalEnvironmentListeners(DiffractionCrystalEnvironmentEvent evt) {
		if (diffCrystEnvListeners==null || !fire) 
			return;
		for (IDiffractionCrystalEnvironmentListener l : diffCrystEnvListeners) {
			l.diffractionCrystalEnvironmentChanged(evt);
		}
	}

	public void restore(DiffractionCrystalEnvironment original) {
		fire = false;
		setExposureTime(original.getExposureTime());
		setOscGap(original.getOscGap());
		setPhiRange(original.getPhiRange());
		setPhiStart(original.getPhiStart());
		setWavelength(original.getWavelength());
		fire = true;
	}

	@Override
	public String toString() {
		return "CE: " + wavelength;
	}

	/**
	 * Set beam direction
	 * @param direction (can be null to reset)
	 */
	public void setBeamDirection(Vector3d direction) {
		if (direction == null) {
			dirn.set(DEFAULT_DIR);
		} else {
			double l = direction.lengthSquared();
			if (l == 0) {
				throw new IllegalArgumentException("Must be non-zero in length");
			}
			if (l == 1) {
				dirn.set(direction);
			} else {
				dirn.normalize(direction);
			}
		}
	}

	/**
	 * @return direction along beam
	 */
	public Vector3d getBeamVector() {
		return dirn;
	}

	/**
	 * @return normal to reference place for polarization 
	 */
	public Vector3d getReferenceNormal() {
		return referenceNormal;
	}

	public void setReferenceNormal(Vector3d referenceNormal) {
		referenceNormal.normalize();
		this.referenceNormal = referenceNormal;
	}

	/**
	 * @return Stokes parameters as a vector to describe polarization state of beam
	 */
	public Vector4d getStokesVector() {
		return stokesVector;
	}

	public void setStokesVector(Vector4d stokesVector) {
		double i = stokesVector.getX();
		if (i <= 0 || stokesVector.lengthSquared() > 2*i*i) {
			throw new IllegalArgumentException("First component must be positive and its square >= the sum of squares of the rest: " + stokesVector);
		}
		this.stokesVector = stokesVector;
	}
}
