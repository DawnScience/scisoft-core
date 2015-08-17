/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.metadata;

import org.eclipse.dawnsci.analysis.api.metadata.XPDFBeamMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTrace;

public class XPDFBeamMetadataImpl implements XPDFBeamMetadata {

	double beamEnergy;
	double beamWidth;
	double beamHeight;
	XPDFTrace beamBGTrace;
	
	public XPDFBeamMetadataImpl() {
//		Zero beam data values
		this.beamEnergy = 0.0;
		this.beamHeight = 0.0;
		this.beamWidth = 0.0;
		this.beamBGTrace = null;
	}
	
	public XPDFBeamMetadataImpl(XPDFBeamMetadataImpl inBeam) {
		this.beamEnergy = inBeam.beamEnergy;
		this.beamHeight = inBeam.beamHeight;
		this.beamWidth = inBeam.beamWidth;
		this.beamBGTrace = (XPDFTraceMetadataImpl) inBeam.beamBGTrace.clone();
	}

	@Override
	public MetadataType clone() {
		return new XPDFBeamMetadataImpl(this);
	}

	// Setters
	public void setBeamEnergy(double beamEnergy) {
		this.beamEnergy = beamEnergy;
	}

	public void setBeamWidth(double beamWidth) {
		this.beamWidth = beamWidth;
	}

	public void setBeamHeight(double beamHeight) {
		this.beamHeight = beamHeight;
	}

	public void setTrace(XPDFTrace inTrace) {
		this.beamBGTrace = (XPDFTraceMetadataImpl) inTrace.clone();
	}
	
	// Getters implemented for the interface
	@Override
	public double getBeamEnergy() {
		return beamEnergy;
	}

	@Override
	public double getBeamHeight() {
		return beamHeight;
	}

	@Override
	public double getBeamWidth() {
		return beamWidth;
	}

	@Override
	public XPDFTrace getTrace() {
		return (XPDFTraceMetadataImpl) beamBGTrace.clone();
	}

}
