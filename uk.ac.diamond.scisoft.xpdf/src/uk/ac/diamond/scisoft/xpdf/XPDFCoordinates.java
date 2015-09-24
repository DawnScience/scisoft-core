/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.api.metadata.AxesMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

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

	private double wavelength;
	private Dataset twoTheta;
	private Dataset q;
	private Dataset x;
	private boolean isAngleAuthorative;
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
		isAngleAuthorative = true;
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
		this.isAngleAuthorative = inCoords.isAngleAuthorative;
	}
	
	public XPDFCoordinates(Dataset input) {
		XPDFMetadata theXPDFMetadata = input.getFirstMetadata(XPDFMetadata.class);
		this.wavelength = theXPDFMetadata.getBeam().getBeamWavelength();
		AxesMetadata axes = input.getFirstMetadata(AxesMetadata.class);
		if (theXPDFMetadata.getSample().getTrace().isAxisAngle()) {
			this.setTwoTheta(Maths.toRadians(DatasetUtils.convertToDataset(axes.getAxis(0)[0].getSlice())));
			q = null;
			x = null;
		} else {
			this.setQ(DatasetUtils.convertToDataset(axes.getAxis(0)[0].getSlice()));
			twoTheta = null;
		}		
	}
	
	/**
	 * Set the energy of the photons.
	 * @param inEnergy
	 * 				beam photon energy in keV.
	 */
	public void setEnergy(double inEnergy) {
		this.wavelength = hckeVAA/inEnergy;
		invalidateData();
	}
	
	/**
	 * Set the wavelength of the photons.
	 * @param inLambda
	 * 				beam photon wavelength in Angstroms.
	 */
	public void setWavelength(double inLambda) {
		this.wavelength = inLambda;
		invalidateData();
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
		this.isAngleAuthorative = true;
		invalidateData();
	}

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
		this.isAngleAuthorative = true;
		invalidateData();
	}
	
	public void setQ(Dataset q) {
		this.q = q;
		this.x = Maths.divide(this.q, 4*Math.PI);
		this.isAngleAuthorative = false;
	}
	
	public void setX(Dataset x) {
		this.x = x;
		this.q = Maths.multiply(this.x, 4*Math.PI);
		this.isAngleAuthorative = false;
	}
	
	/**
	 * Returns the total scattering angle.
	 * @return the total scattering angle in radians.
	 */
	public Dataset getTwoTheta() {
		if (this.twoTheta == null)
			this.twoTheta = Maths.multiply(2, Maths.arcsin(Maths.multiply(this.x, this.wavelength)));
		return this.twoTheta;
	}
	
	/**
	 * Calculates and returns sin 2θ/λ
	 * @return the value x = sin 2θ/λ
	 */
	public Dataset getX() {
		if (this.x == null)
			this.x = Maths.divide(Maths.sin(Maths.multiply(0.5, this.twoTheta)), this.wavelength);
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

	private void invalidateData() {
		if (isAngleAuthorative) {
			q = null;
			x = null;
		} else {
			twoTheta = null;
		}
	}

}
