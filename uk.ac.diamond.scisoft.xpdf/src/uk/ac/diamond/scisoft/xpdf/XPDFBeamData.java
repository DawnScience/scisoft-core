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

//TODO: Move back to uk.ac.diamond.scisoft.xpdf once the NPEs are solved

/**
 * Beam data for the XPDFProcessor class
 * 
 * @author Timothy Spain
 *
 */
public class XPDFBeamData {

	double beamEnergy;
	double beamWidth;
	double beamHeight;
	XPDFBeamTrace trace;
	private static final double hckeVAA = 12.39841974;//(17)

	
	public XPDFBeamData() {
//		Zero beam data values
		this.beamEnergy = 0.0;
		this.beamHeight = 0.0;
		this.beamWidth = 0.0;
		this.trace = null;
	}

	public XPDFBeamData(XPDFBeamData inBeam) {
		this.beamEnergy = inBeam.beamEnergy;
		this.beamHeight = inBeam.beamHeight;
		this.beamWidth = inBeam.beamWidth;
		this.trace = (XPDFBeamTrace) inBeam.trace.clone();
	}

	@Override
	protected XPDFBeamData clone() {
		return new XPDFBeamData(this);
	}

	public double getBeamEnergy() {
		return beamEnergy;
	}

	public void setBeamEnergy(double beamEnergy) {
		this.beamEnergy = beamEnergy;
	}
	
	public void setBeamWavelength(double beamWavelength) {
		this.beamEnergy = hckeVAA/beamWavelength;
	}

	public double getBeamWavelength() {
		return hckeVAA/this.beamEnergy;
	}
	
	public double getBeamWidth() {
		return beamWidth;
	}

	public void setBeamWidth(double beamWidth) {
		this.beamWidth = beamWidth;
	}

	public double getBeamHeight() {
		return beamHeight;
	}

	public void setBeamHeight(double beamHeight) {
		this.beamHeight = beamHeight;
	}

	public XPDFBeamTrace getTrace() {
		return trace;
	}

	public void setTrace(XPDFBeamTrace trace) {
		this.trace = trace;
	}

	// The q(2θ) calculation is in this class because of the energy dependence.
	public Dataset getQFromTwoTheta(Dataset twoTheta) {
		Dataset x = Maths.divide(Maths.sin(Maths.divide(twoTheta, 2)), this.getBeamWavelength());
		Dataset q = Maths.multiply(4*Math.PI, x);
		return q;
	}
		
}
