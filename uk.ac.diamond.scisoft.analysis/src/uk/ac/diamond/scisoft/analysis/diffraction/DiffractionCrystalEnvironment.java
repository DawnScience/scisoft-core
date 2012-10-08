/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.HashSet;

/**
 * Container for parameters that a crystal is subject to in a diffraction experiment
 */
public class DiffractionCrystalEnvironment {
	//  TODO add transmission

	private double wavelength;
	private double phiStart;
	private double phiRange;
	private double exposureTime;
	private HashSet<IDiffractionCrystalEnvironmentListener> diffCrystEnvListeners; 

	/**
	 * @param wavelength in Angstroms
	 */
	public DiffractionCrystalEnvironment(double wavelength){
		this.wavelength = wavelength;
		this.phiStart = Double.NaN;
		this.phiRange = Double.NaN;
		this.exposureTime = Double.NaN;
	}

	/**
	 * 
	 * @param wavelength in Angstroms
	 * @param phiStart in degrees
	 * @param phiRange in degrees
	 * @param exposureTime in seconds
	 */
	public DiffractionCrystalEnvironment(double wavelength, double phiStart, double phiRange, double exposureTime) {
		this.wavelength = wavelength;
		this.phiStart = phiStart;
		this.phiRange = phiRange;
		this.exposureTime = exposureTime;
	}

	/**
	 * null constructor
	 */
	public DiffractionCrystalEnvironment() {
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
		return true;
	}

	/**
	 * @return wavelength in Angstroms
	 */
	public double getWavelength() {
		return wavelength;
	}
	
	/**
	 * Sets the wavelength from energy in keV
	 * @param keV energy
	 */
	public void setWavelengthFromEnergykeV(double keV) {
		// lambda(A) = 10^7 * (h*c/e) / energy(keV)
		this.wavelength = 1./(0.0806554465*keV); // constant from NIST CODATA 2006
		// Tell listeners
		fireDiffractionCrystalEnvironmentListeners(new DiffractionCrystalEnvironmentEvent(this, "Wavelength"));
	}

	/**
	 * Set wavelength in Angstroms
	 * @param wavelength
	 */
	public void setWavelength(double wavelength) {
		this.wavelength = wavelength;
		fireDiffractionCrystalEnvironmentListeners(new DiffractionCrystalEnvironmentEvent(this, "Wavelength"));
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
		if (diffCrystEnvListeners==null) 
			return;
		for (IDiffractionCrystalEnvironmentListener l : diffCrystEnvListeners) {
			l.diffractionCrystalEnvironmentChanged(evt);
		}
	}

}
