/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;
//TODO: Move back to uk.ac.diamond.scisoft.xpdf once the NPEs are solved


import org.eclipse.dawnsci.analysis.api.metadata.XPDFBeamMetadata;

/**
 * Beam data for the XPDFProcessor class
 * 
 * @author Timothy Spain
 *
 */
class BeamData {

	double beamEnergy;
	double beamWidth;
	double beamHeight;
	BeamTrace beamBGTrace;
	
	public BeamData() {
//		Zero beam data values
		this.beamEnergy = 0.0;
		this.beamHeight = 0.0;
		this.beamWidth = 0.0;
		this.beamBGTrace = null;
	}

	public BeamData(BeamData inBeam) {
		this.beamEnergy = inBeam.beamEnergy;
		this.beamHeight = inBeam.beamHeight;
		this.beamWidth = inBeam.beamWidth;
		this.beamBGTrace = (BeamTrace) inBeam.beamBGTrace.clone();
	}

	public BeamData(XPDFBeamMetadata inBeam) {
		this.beamEnergy = inBeam.getBeamEnergy();
		this.beamHeight = inBeam.getBeamHeight();
		this.beamWidth = inBeam.getBeamWidth();
		this.beamBGTrace = new BeamTrace(inBeam.getTrace());
	}
	
	@Override
	protected BeamData clone() {
		return new BeamData(this);
	}

	public double getBeamEnergy() {
		return beamEnergy;
	}

	public void setBeamEnergy(double beamEnergy) {
		this.beamEnergy = beamEnergy;
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

	public BeamTrace getBeamBGTrace() {
		return beamBGTrace;
	}

	public void setBeamBGTrace(BeamTrace beamBGTrace) {
		this.beamBGTrace = beamBGTrace;
	}
}
