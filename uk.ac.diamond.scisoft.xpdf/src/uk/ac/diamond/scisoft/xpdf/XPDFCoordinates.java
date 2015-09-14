/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/*
 * Build from energy or wavelength, and angles, and able to return those values, or x or q.
 */
/**
 * Centralized calculation of the momentum transfer coordinate.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFCoordinates {

	double wavelength;
	Dataset twoTheta;
	Dataset q;
	Dataset x;
	// Energy-wavelength conversion in keV Angstroms
	private static final double hckeVAA = 12.39841974;//(17)

	/**
	 * Empty constructor.
	 */
	public XPDFCoordinates() {
		wavelength = 0.0;
		twoTheta = null;
		q = null;
		x = null;
	}
	
	/**
	 * Copy constructor.
	 * @param inCoords
	 * 				Coordinate set to be copied
	 */
	public XPDFCoordinates(XPDFCoordinates inCoords) {
		this.wavelength = inCoords.wavelength;
		this.twoTheta = new DoubleDataset(inCoords.twoTheta);
		this.q = new DoubleDataset(inCoords.q);
		this.x = new DoubleDataset(inCoords.x);
	}
	
	/**
	 * Set the energy of the photons.
	 * @param inEnergy
	 * 				beam photon energy in keV.
	 */
	public void setEnergy(double inEnergy) {
		this.wavelength = hckeVAA/inEnergy;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}
	
	/**
	 * Set the wavelength of the photons.
	 * @param inLambda
	 * 				beam photon wavelength in Angstroms.
	 */
	public void setWavelength(double inLambda) {
		this.wavelength = inLambda;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}

	/**
	 * Set the beam wavelength from a XPDFBeamData object.
	 * @param inBeam
	 * 				beam data to be used.
	 */
	public void setBeamData(XPDFBeamData inBeam) {
		this.setEnergy(inBeam.getBeamEnergy());
	}
	
	public void setTwoTheta(Dataset twoTheta) {
		this.twoTheta = twoTheta;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}

	// 
	/**
	 * Set the total scattering angle based on horizontal and vertical scattering angles.
	 * @param gamma
	 * 			Horizontal scattering angle
	 * @param delta
	 * 			Vertical scattering angle
	 */
	public void setGammaDelta(Dataset gamma, Dataset delta) {
		// TODO: Fix this up when we have 2D data
		this.twoTheta = delta;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}
	
	/**
	 * Returns the total scattering angle.
	 * @return the total scattering angle in radians.
	 */
	public Dataset getTwoTheta() {
		return this.twoTheta;
	}
	
	/**
	 * Calculates and returns sin 2θ/λ
	 * @return the value x = sin 2θ/λ
	 */
	public Dataset getX() {
		if (this.x == null)
			x = Maths.divide(Maths.sin(Maths.multiply(0.5, twoTheta)), this.wavelength);
		return this.x;
	}
	
	/**
	 * Calculates and returns the momentum transfer, q 
	 * @return the momentum transfer of a beam photon at each detector angle. q = 4π sin 2θ/λ.
	 */
	public Dataset getQ() {
		if (this.q == null) 
			this.q = Maths.multiply(this.getX(), 4*Math.PI);
		return this.q;
	}
	
	/**
	 * Returns the energy.
	 * <p>
	 * Returns the energy as a help to functions where we have the coordinates,
	 * but do not want to pass in the beam data as well. 
	 * @return beam energy in keV.
	 */
	public double getEnergy() {
		return hckeVAA/this.wavelength;
	}
}
