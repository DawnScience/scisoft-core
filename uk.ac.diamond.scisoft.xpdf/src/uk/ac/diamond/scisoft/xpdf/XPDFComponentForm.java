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

// public because it needs to be visible in the uk...xpdf.operations package
public class XPDFComponentForm {

	XPDFSubstance substance;
	XPDFComponentGeometry geometry;
	
	public XPDFComponentForm() {
		substance = new XPDFSubstance();
		geometry = null;
	}

	public XPDFComponentForm(XPDFComponentForm inForm) {
		substance = new XPDFSubstance(inForm.substance);
		// Must be a better way to do this
		if (inForm.getGeom().getShape() == "cylinder") {
			this.geometry = new XPDFComponentCylinder(inForm.getGeom());
		} else if (inForm.getGeom().getShape() == "plate") {
			this.geometry = new XPDFComponentPlate(inForm.getGeom());
		}
	}


	public String getMaterialName() {
		return this.substance.getMaterialName();
	}

	public void setMatName(String matName) {
		this.substance.setMaterialName(matName);
		this.substance.setMaterialComposition(matName);
	}

	public double getDensity() {
		return this.substance.getMassDensity();
	}

	public void setDensity(double density) {
		this.substance.setMassDensity(density);
	}

	public double getPackingFraction() {
		return this.substance.getPackingFraction();
	}

	public void setPackingFraction(double packingFraction) {
		this.substance.setPackingFraction(packingFraction);
	}

	public XPDFComponentGeometry getGeom() {
		return geometry;
	}

	public void setGeom(XPDFComponentGeometry geom) {
		this.geometry = geom;
	}
	
	public double getIlluminatedAtoms(XPDFBeamData beamdata){
		double packingFactorUsed;
		// Don't know why this is used on one, and not the other
		packingFactorUsed = (getGeom().getShape().equals("cylinder")) ? 1.0 : getPackingFraction();
		final double cubicAngstromsPerCubicMillimetre = 1e21; 
		return substance.getNumberDensity() * cubicAngstromsPerCubicMillimetre * packingFactorUsed;		
	}

	public double getKroghMoeSum() {
		return substance.getKroghMoeSum();
	}

	public Dataset getSelfScattering(Dataset twoTheta) {
		return null;
	}

	public double getAttenuationCoefficient(double beamEnergy) {
		return substance.getAttenuationCoefficient(beamEnergy);
	}
	
}
