/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

/**
 * Holds the details of a material, both chemical and physical.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFSubstance {

	private String materialName;
	private XPDFComposition materialComposition;
	private double microMassDensity;
	private double packingFraction;
	
	/**
	 * Empty constructor.
	 */
	public XPDFSubstance() {
		materialName="";
		materialComposition = null;
		microMassDensity = 0.0;
		packingFraction = 0.0;
	}
	
	/**
	 * Copy constructor.
	 * @param substance
	 * 					object to be copied.
	 */
	public XPDFSubstance(XPDFSubstance substance) {
		this.materialName = substance.materialName;
		this.materialComposition = substance.materialComposition;
		this.microMassDensity = substance.microMassDensity;
		this.packingFraction = substance.packingFraction;
	}
	
	/**
	 * Constructor from properties.
	 * @param materialName
	 * 					An arbitrary name.
	 * @param materialFormula
	 * 						Chemical formula.
	 * @param microMassDensity
	 * 						Crystallographic density.
	 * @param packingFraction
	 * 						Packing fraction of the solid material.
	 */
	public XPDFSubstance(String materialName, String materialFormula, double microMassDensity, double packingFraction) {
		this.materialName = materialName;
		this.materialComposition = new XPDFComposition(materialFormula);
		this.microMassDensity = microMassDensity;
		this.packingFraction = packingFraction;
	}
	
	/**
	 * Getter for the name.
	 * @return the material name.
	 */
	public String getMaterialName() {
		return materialName;
	}
	
	/**
	 * Setter for the material name.
	 * @param matName
	 * 				Name to be set.
	 */
	public void setMaterialName(String matName) {
		this.materialName = matName;
	}
	
//	public XPDFComposition getMaterialComposition() {
//		return materialComposition;
//	}

	/**
	 * Setter for the chemical formula.
	 * @param materialFormula
	 * 						ASCII version of the chemical formula for the substance.
	 */
	public void setMaterialComposition(String materialFormula) {
		this.materialComposition = new XPDFComposition(materialFormula);
	}

	/**
	 * Set the crystallographic density of the material.
	 * @param microMassDensity
	 * 						crystallographic density in g/cm³
	 */
	public void setMassDensity(double microMassDensity) {
		this.microMassDensity = microMassDensity;
	}

	/**
	 * Setter for the packing fraction.
	 * @param packingFraction
	 * 						volume fraction that the material takes up.
	 */
	public void setPackingFraction(double packingFraction) {
		this.packingFraction = packingFraction;
	}

	// microscopic density; the density of individual crystals/grains of the substance
	/**
	 * Getter for the crystallographic mass density.	
	 * @return the crystallographic mass density in g/cm³.
	 */
	public double getMassDensity() {
		return microMassDensity;
	}
	
	/**
	 * Calculates and returns the crystallographic number density
	 * @return the crystallographic number density in 1/Å³ 
	 */
	public double getNumberDensity() {
		final double nAvogadro = 6.022140857e23;//(74)
		final double cubicCentimetresPerCubicAngstrom = 1e-24;
		return microMassDensity/materialComposition.getMeanAtomicMass()*nAvogadro*cubicCentimetresPerCubicAngstrom;
	}

	/**
	 * Getter for the packing fraction
	 * @return the fraction of total volume that the solid material occupies.
	 */
	public double getPackingFraction() {
		return packingFraction;
	}
	
	// Krogh-Moe sum from XPDFNormalisation
	/**
	 * Calculates the Krogh-Moe sum.
	 * @return the Krogh-Moe sum for this state of the material.
	 */
	public double getKroghMoeSum() {
		return 2*Math.PI*Math.PI*getNumberDensity()*materialComposition.getKroghMoeSummand();
	}
	
	// mass attenuation coefficient of the substance at the given energy
	/**
	 * Calculates the attenuation coefficient of the substance.
	 * @param beamEnergy
	 * 					the energy of the photons being attenuated.
	 * @return the attenuation coefficient of the substance in 1/cm.
	 */
	public double getAttenuationCoefficient(double beamEnergy) {
		return 0.1 * getMassDensity()*getPackingFraction() * materialComposition.getMassAttenuation(beamEnergy);
	}
	
	/**
	 * Returns the chemical formula.
	 * @return the ASCII chemical formula of the substance.
	 */
	public XPDFComposition getComposition() {
		return materialComposition;
	}
	
	/**
	 * Returns the g0-1 value of the material.
	 * @return g0-1
	 */
	public double getG0Minus1() {
		return materialComposition.getG0Minus1();
	}

	public boolean isEqualToForAbsorption(XPDFSubstance inSubstance) {
		return (inSubstance != null) &&
				materialComposition.isEqualToForAbsorption(inSubstance.materialComposition) &&
				microMassDensity == inSubstance.microMassDensity &&
				packingFraction == inSubstance.packingFraction;
	}

	/**
	 * Returns the number density of the element with atomic number z.
	 * <p>
	 * Given an atomic number, <code>z</code>, this method returns the number
	 * density of that element within this component. If the component contains
	 * none of that element, then the number density therein is necessarily
	 * zero.
	 * @param z
	 * 			the atomic number to query the density of.
	 * @return the number density in 1/Å³
	 */
	public double getNumberDensity(Integer z) {
		return getNumberDensity()*this.materialComposition.atomFraction(z);
	}
}
