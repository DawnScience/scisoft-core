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

import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetFormMetadata;

class ComponentForm {

	String matName;
	double density;
	double packingFraction;
	ComponentGeometry geom;
	
	public ComponentForm() {
		matName = "";
		density = 0.0;
		packingFraction = 1.0;
		geom = null;
	}

	public ComponentForm(ComponentForm inForm) {
		this.matName = inForm.matName;
		this.density = inForm.density;
		this.packingFraction = inForm.packingFraction;
		this.geom = (ComponentGeometry) inForm.geom.clone();
	}

	public ComponentForm(XPDFTargetFormMetadata inForm) {
		this.matName = inForm.getMaterialName();
		this.density = inForm.getDensity();
		this.packingFraction = inForm.getPackingFraction();
		// Must be a better way to do this
		if (inForm.getGeometry().getShape() == "cylinder") {
			this.geom = new ComponentCylinder(inForm.getGeometry());
		} else if (inForm.getGeometry().getShape() == "plate") {
			this.geom = new ComponentPlate(inForm.getGeometry());
		}
	}

	public String getMatName() {
		return matName;
	}

	public void setMatName(String matName) {
		this.matName = matName;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getPackingFraction() {
		return packingFraction;
	}

	public void setPackingFraction(double packingFraction) {
		this.packingFraction = packingFraction;
	}

	public ComponentGeometry getGeom() {
		return geom;
	}

	public void setGeom(ComponentGeometry geom) {
		this.geom = geom;
	}
	
}
