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

// public because it needs to be visible in the uk...xpdf.operations package
public class XPDFComponentForm {

	String matName;
	double density;
	double packingFraction;
	XPDFComponentGeometry geom;
	
	public XPDFComponentForm() {
		matName = "";
		density = 0.0;
		packingFraction = 1.0;
		geom = null;
	}

	public XPDFComponentForm(XPDFComponentForm inForm) {
		this.matName = inForm.matName;
		this.density = inForm.density;
		this.packingFraction = inForm.packingFraction;
		// Must be a better way to do this
		if (inForm.getGeom().getShape() == "cylinder") {
			this.geom = new XPDFComponentCylinder(inForm.getGeom());
		} else if (inForm.getGeom().getShape() == "plate") {
			this.geom = new XPDFComponentPlate(inForm.getGeom());
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

	public XPDFComponentGeometry getGeom() {
		return geom;
	}

	public void setGeom(XPDFComponentGeometry geom) {
		this.geom = geom;
	}
	
}
