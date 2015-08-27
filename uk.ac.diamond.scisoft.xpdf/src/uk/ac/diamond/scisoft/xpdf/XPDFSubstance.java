/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

public class XPDFSubstance {

	private String materialName;
	private XPDFComposition materialComposition;
	private double microMassDensity;
	private double packingFraction;
	
	public XPDFSubstance() {
		materialName="";
		materialComposition = null;
		microMassDensity = 0.0;
		packingFraction = 0.0;
	}
	
	public XPDFSubstance(XPDFSubstance substance) {
		this.materialName = substance.materialName;
		this.materialComposition = substance.materialComposition;
		this.microMassDensity = substance.microMassDensity;
		this.packingFraction = substance.packingFraction;
	}
	
	public XPDFSubstance(String materialName, String materialFormula, double microMassDensity, double packingFraction) {
		this.materialName = materialName;
		this.materialComposition = new XPDFComposition(materialFormula);
		this.microMassDensity = microMassDensity;
		this.packingFraction = packingFraction;
	}
	
	public String getMaterialName() {
		return materialName;
	}
	
	public void setMaterialName(String matName) {
		this.materialName = matName;
	}
	
//	public XPDFComposition getMaterialComposition() {
//		return materialComposition;
//	}

	public void setMaterialComposition(String materialFormula) {
		this.materialComposition = new XPDFComposition(materialFormula);
	}

	public void setMassDensity(double microMassDensity) {
		this.microMassDensity = microMassDensity;
	}

	public void setPackingFraction(double packingFraction) {
		this.packingFraction = packingFraction;
	}

	// microscopic density; the density of individual crystals/grains of the substance
	public double getMassDensity() {
		return microMassDensity;
	}
	public double getNumberDensity() {
		return microMassDensity/materialComposition.getMeanAtomicMass();
	}
	// macroscopic density packing fraction of the substance
	public double getPackingFraction() {
		return packingFraction;
	}
	
	// Krogh-Moe sum from XPDFNormalisation
	public double getKroghMoeSum() {
			
		return 2*Math.PI*Math.PI*getNumberDensity()*materialComposition.getKroghMoeSummand();
	}
	
	// mass attenuation coefficient of the substance at the given energy
	public double getMassAttenuation(double beamEnergy) {
		// TODO: Implement this
		return 1.0;
	}
	
	public XPDFComposition getComposition() {
		return materialComposition;
	}
	
}
