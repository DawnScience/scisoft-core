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

/**
 * add transmission and exp time 
 */
public class DiffractionCrystalEnvironment {
	private double wavelength;
	private double phiStart;
	private double phiRange;
	private double exposureTime;
	
	
	/**
	 * @param wavelength in Angstroms
	 */
	public DiffractionCrystalEnvironment(double wavelength){
		this.wavelength = wavelength;
		this.phiStart = Double.NaN;
		this.phiRange = Double.NaN;
		this.exposureTime = Double.NaN;
	}
	 
	public DiffractionCrystalEnvironment(double wavelength,double phiStart, double phiRange, double exposureTime){
		this.wavelength = wavelength;
		this.phiStart = phiStart;
		this.phiRange = phiRange;
		this.exposureTime = exposureTime;
	}
	/**
	 * null constructor
	 */
	public DiffractionCrystalEnvironment(){
	}

	@Override
	public DiffractionCrystalEnvironment clone(){
		return new DiffractionCrystalEnvironment(wavelength,phiStart,phiRange,exposureTime);
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiffractionCrystalEnvironment other = (DiffractionCrystalEnvironment) obj;
		if (Double.doubleToLongBits(exposureTime) != Double.doubleToLongBits(other.exposureTime))
			return false;
		if (Double.doubleToLongBits(phiRange) != Double.doubleToLongBits(other.phiRange))
			return false;
		if (Double.doubleToLongBits(phiStart) != Double.doubleToLongBits(other.phiStart))
			return false;
		if (Double.doubleToLongBits(wavelength) != Double.doubleToLongBits(other.wavelength))
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
	}

	/**
	 * Set wavelength in Angstroms
	 * @param wavelength
	 */
	public void setWavelength(double wavelength) {
		this.wavelength = wavelength;
	}
	
	/**
	 * 
	 * @return phi value for the start of the image in degrees
	 */
	public double getPhiStart() {
		return phiStart;
	}

	/**
	 * 
	 * @param phiStart phi at start of the diffraction image in degrees
	 */
	public void setPhiStart(double phiStart) {
		this.phiStart = phiStart;
	}

	/**
	 * 
	 * @return the phi range of the image in degrees
	 */
	public double getPhiRange() {
		return phiRange;
	}

	/**
	 * Set the 
	 * @param phiRange 
	 */
	public void setPhiRange(double phiRange) {
		this.phiRange = phiRange;
	}
	
	/**
	 * 
	 * @return Exposure time in seconds
	 */
	public double getExposureTime() {
		return exposureTime;
	}

	/**
	 * 
	 * @param exposureTime in seconds
	 */
	public void setExposureTime(double exposureTime) {
		this.exposureTime = exposureTime;
	}

}
