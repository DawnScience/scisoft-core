/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.metadata;

import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFBeamMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetFormMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetGeometryMetadata;

public class XPDFTargetFormMetadataImpl implements XPDFTargetFormMetadata {
	
	String matName;
	double massDensity;
	double numberDensity;
	double packingFraction;
	XPDFTargetGeometryMetadata geom;
	
	public XPDFTargetFormMetadataImpl() {
		matName = "";
		massDensity = 0.0;
		numberDensity = 0.0;
		packingFraction = 1.0;
		geom = null;
	}

	public XPDFTargetFormMetadataImpl(XPDFTargetFormMetadataImpl inform){
		this.matName = inform.matName;
		this.massDensity = inform.massDensity;
		this.numberDensity = inform.numberDensity;
		this.packingFraction = inform.packingFraction;
		this.geom = (XPDFTargetGeometryMetadata) inform.geom.clone();
	}
	
	@Override
	public MetadataType clone() {
		return new XPDFTargetFormMetadataImpl(this);
	}

	public void setMaterialName(String matName) {
		this.matName = matName;
	}

	public XPDFTargetGeometryMetadata getGeom() {
		return geom;
	}

	public void setGeometry(XPDFTargetGeometryMetadata geom) {
		this.geom = (XPDFTargetGeometryMetadata) geom.clone();
	}

	public void setMassDensity(double massDensity) {
		this.massDensity = massDensity;
	}

	public void setPackingFraction(double packingFraction) {
		this.packingFraction = packingFraction;
	}

	@Override
	public String getMaterialName() {
		return this.matName;
	}

	@Override
	public double getMassDensity() {
		return this.massDensity;
	}

	@Override
	public double getPackingFraction() {
		return this.packingFraction;
	}

	@Override
	public XPDFTargetGeometryMetadata getGeometry() {
		return (XPDFTargetGeometryMetadata) this.geom.clone();
	}

	@Override
	public double getNumberOfAtomsIlluminated(XPDFBeamMetadata beam) {
		 double atomicNumberDensitymm3 = 1.0e21 * this.packingFraction * numberDensity;
//		 double volumeIlluminated = geom.getVolumeIlluminated(beam);
		 return 0;
	}

	@Override
	public double getNumberDensity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
