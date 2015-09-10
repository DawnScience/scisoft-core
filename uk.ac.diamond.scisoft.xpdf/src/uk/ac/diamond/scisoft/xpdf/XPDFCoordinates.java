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
public class XPDFCoordinates {

	double wavelength;
	Dataset twoTheta;
	Dataset q;
	Dataset x;
	private static final double hckeVAA = 12.39841974;//(17)

	public XPDFCoordinates() {
		wavelength = 0.0;
		twoTheta = null;
		q = null;
		x = null;
	}
	
	public XPDFCoordinates(XPDFCoordinates inCoords) {
		this.wavelength = inCoords.wavelength;
		this.twoTheta = new DoubleDataset(inCoords.twoTheta);
		this.q = new DoubleDataset(inCoords.q);
		this.x = new DoubleDataset(inCoords.x);
	}
	
	// Energy in keV
	public void setEnergy(double inEnergy) {
		this.wavelength = hckeVAA/inEnergy;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}
	
	// Wavelength in angstroms
	public void setWavelength(double inLambda) {
		this.wavelength = inLambda;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}
	
	public void setBeamData(XPDFBeamData inBeam) {
		this.setEnergy(inBeam.getBeamEnergy());
	}
	
	public void setTwoTheta(Dataset twoTheta) {
		this.twoTheta = twoTheta;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}

	// Set twoTheta based on horizontal and vertical scattering angles
	public void setGammaDelta(Dataset gamma, Dataset delta) {
		// TODO: Fix this up when we have 2D data
		this.twoTheta = delta;
		// Invalidate all dependent variables
		q = null;
		x = null;
	}
	
	public Dataset getTwoTheta() {
		return this.twoTheta;
	}
	
	public Dataset getX() {
		if (this.x == null)
			x = Maths.divide(Maths.sin(Maths.multiply(0.5, twoTheta)), this.wavelength);
		return this.x;
	}
	
	public Dataset getQ() {
		if (this.q == null) 
			this.q = Maths.multiply(this.getX(), 4*Math.PI);
		return this.q;
	}
	
	public double getEnergy() {
		return hckeVAA/this.wavelength;
	}
}
