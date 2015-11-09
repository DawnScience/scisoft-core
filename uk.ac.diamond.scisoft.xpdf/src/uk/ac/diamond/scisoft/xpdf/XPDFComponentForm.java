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

/**
 * 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 *
 */
// public because it needs to be visible in the uk...xpdf.operations package
public class XPDFComponentForm {

	private XPDFSubstance substance;
	private XPDFComponentGeometry geometry;
	
	/**
	 * Empty constructor.
	 */
	public XPDFComponentForm() {
		substance = new XPDFSubstance();
		geometry = null;
	}

	/**
	 * Copy constructor.
	 * @param inForm
	 * 			Form to be copied.
	 */
	public XPDFComponentForm(XPDFComponentForm inForm) {
		substance = new XPDFSubstance(inForm.substance);
		// Must be a better way to do this
		if (inForm.getGeom().getShape() == "cylinder") {
			this.geometry = new XPDFComponentCylinder(inForm.getGeom());
		} else if (inForm.getGeom().getShape() == "plate") {
			this.geometry = new XPDFComponentPlate(inForm.getGeom());
		}
	}

	/**
	 * Getter for the name of the material.
	 * @return the name of the material.
	 */
	public String getMaterialName() {
		return this.substance.getMaterialName();
	}

	/**
	 * Setter for the name of the material.
	 * <p>
	 * The material name here is the chemical formula. Both the name and the 
	 * account of number of atoms are set by this method.
	 * @param matName
	 * 				chemical formula of the material, in plain ASCII format, 
	 * 				that is water would be H2O. 
	 */
	public void setMatName(String matName) {
		this.substance.setMaterialName(matName);
		this.substance.setMaterialComposition(matName);
	}

	/**
	 * Getter for the density.
	 * @return the crystallographic density of the material.
	 */
	public double getDensity() {
		return this.substance.getMassDensity();
	}

	/**
	 * Setter for the density.
	 * @param density
	 * 				the crystallographic density of the material
	 */
	public void setDensity(double density) {
		this.substance.setMassDensity(density);
	}

	/**
	 * Getter for the packing fraction.
	 * @return the packing fraction of the solid making up the material. 
	 */
	public double getPackingFraction() {
		return this.substance.getPackingFraction();
	}

	/**
	 * Setter for the packing fraction.
	 * @param packingFraction
	 * 						the packing fraction of the solid making up the material.
	 */
	public void setPackingFraction(double packingFraction) {
		this.substance.setPackingFraction(packingFraction);
	}

	/**
	 * Getter for the form geometry.
	 * @return the geometry object defining the shape of the form.
	 */
	public XPDFComponentGeometry getGeom() {
		return geometry;
	}

	/**
	 * Setter for the form geometry.
	 * @param geom
	 * 			the geometry object defining the shape of the form.
	 */
	public void setGeom(XPDFComponentGeometry geom) {
		this.geometry = geom;
	}
	
	/**
	 * The number of atoms illuminated by the X-ray beam.
	 * <p>
	 * Returns the number of atoms that the given beam illuminates in the sample.
	 * @param beamData
	 * 				definition of the illuminating beam.
	 * @return the number of atoms illuminated.
	 */
	public double getIlluminatedAtoms(XPDFBeamData beamData){
		double packingFactorUsed;
		// Don't know why this is used on one, and not the other
		packingFactorUsed = (getGeom().getShape().equals("cylinder")) ? 1.0 : getPackingFraction();
		final double cubicAngstromsPerCubicMillimetre = 1e21; 
		return substance.getNumberDensity() * cubicAngstromsPerCubicMillimetre * packingFactorUsed*geometry.getIlluminatedVolume(beamData);		
	}

	/**
	 * Pass through for the substance Krogh-Moe sum.
	 * @return the Krogh-Moe sum of the substance.
	 */
	public double getKroghMoeSum() {
		return substance.getKroghMoeSum();
	}

	/**
	 * Pass through the self-scattering, except that is done elsewhere.
	 * @param twoTheta angles
	 * @return null
	 */
	public Dataset getSelfScattering(Dataset twoTheta) {
		return null;
	}

	/**
	 * Pass-through for the substance attenuation coefficient. 
	 * @param beamEnergy
	 * 					definition of the attenuated beam
	 * @return the substance attenuation coefficient.
	 */
	public double getAttenuationCoefficient(double beamEnergy) {
		return substance.getAttenuationCoefficient(beamEnergy);
	}

	public boolean isEqualToForAbsorption(XPDFComponentForm inForm) {
		return (inForm != null) &&
				substance.isEqualToForAbsorption(inForm.substance) &&
				geometry.isEqualToForAbsorption(inForm.geometry);
	}

	public XPDFSubstance getSubstance() {
		return substance;
	}
	
}
