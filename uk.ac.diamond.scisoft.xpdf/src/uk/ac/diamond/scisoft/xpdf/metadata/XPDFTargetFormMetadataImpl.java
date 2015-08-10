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
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetFormMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetGeometryMetadata;

public class XPDFTargetFormMetadataImpl implements XPDFTargetFormMetadata {
	
	String matName;
	double density;
	double packingFraction;
	XPDFTargetGeometryMetadata geom;
	
	public XPDFTargetFormMetadataImpl() {
		matName = "";
		density = 0.0;
		packingFraction = 1.0;
		geom = null;
	}

	public XPDFTargetFormMetadataImpl(XPDFTargetFormMetadataImpl inform){
		this.matName = inform.matName;
		this.density = inform.density;
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

	public void setDensity(double density) {
		this.density = density;
	}

	public void setPackingFraction(double packingFraction) {
		this.packingFraction = packingFraction;
	}

	@Override
	public String getMaterialName() {
		return this.matName;
	}

	@Override
	public double getDensity() {
		return this.density;
	}

	@Override
	public double getPackingFraction() {
		return this.packingFraction;
	}

	@Override
	public XPDFTargetGeometryMetadata getGeometry() {
		return (XPDFTargetGeometryMetadata) this.geom.clone();
	}

}
