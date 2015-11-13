/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.xpdf.XPDFSubstance;

public class XPDFSampleParameters {
	private String name;
	private int id;
	private List<String> phases;
	private XPDFSubstance substance;
	private double suggestedEnergy;
	private double suggestedCapDiameter;
	private String beamState;
	private String container;

	/**
	 * default ctor
	 */
	public XPDFSampleParameters() {
		this.substance = new XPDFSubstance();
	}

	/**
	 * Copy constructor
	 */
	public XPDFSampleParameters(XPDFSampleParameters inSamp) {
		this.name = inSamp.name;
		this.phases = new ArrayList<String>(inSamp.phases);
		this.substance = new XPDFSubstance(inSamp.substance);
		this.suggestedEnergy = inSamp.suggestedEnergy;
		this.suggestedCapDiameter = inSamp.suggestedCapDiameter;
		this.beamState = inSamp.beamState;
		this.container = inSamp.container;
	}
	
	/**
	 * @return the name of the sample
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 * 			 the name of the sample to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the phases present in the sample
	 */
	public List<String> getPhases() {
		return phases;
	}
	/**
	 * @param phases sets the phases present in the sample
	 */
	public void setPhases(List<String> phases) {
		this.phases = phases;
	}

	/**
	 * @return the substance
	 */
	public XPDFSubstance getSubstance() {
		return substance;
	}
	/**
	 * @param substance the substance to set
	 */
	public void setSubstance(XPDFSubstance substance) {
		this.substance = substance;
	}
	
	// Getters and setters for the properties of the substance
	/**
	 * @return the ASCII formula of the substance
	 */
	public String getComposition() {
		return substance.getMaterialName();
	}
	/**
	 * @param compoString
	 * 					the compostion to be set
	 */
	public void setComposition(String compoString) {
		substance.setMaterialComposition(compoString);
		substance.setMaterialName(compoString);
		// set the typical powder packing fraction
		substance.setPackingFraction(0.6);
	}
	/**
	 * @return the crystallographic density of the material in g/cm³.
	 */
	public double getDensity() {
		return substance.getMassDensity();
	}
	/**
	 * @param density
	 * 				the crystallographic density of the material in g/cm³.
	 */
	public void setDensity(double density) {
		substance.setMassDensity(density);
	}
	/**
	 * @return the powder packing fraction (probably 0.6).
	 */
	public double getPackingFraction() {
		return substance.getPackingFraction();
	}
	/**
	 * @param fraction
	 * 				the powder packing fraction to set.
	 */
	public void setPackingFraction(double fraction) {
		substance.setPackingFraction(fraction);
	}
	
	
	/**
	 * @return the suggested beam energy in keV.
	 */
	public double getSuggestedEnergy() {
		return suggestedEnergy;
	}
	/**
	 * @param suggestedEnergy
	 * 						the suggested beam energy  in keV.
	 */
	public void setSuggestedEnergy(double suggestedEnergy) {
		this.suggestedEnergy = suggestedEnergy;
	}
	/**
	 * @return the attenuation coefficient at the suggested beam energy.
	 */
	public double getMu() {
		return substance.getAttenuationCoefficient(suggestedEnergy);
	}
	/**
	 * @return the suggested capillary diameter in mm.
	 */
	public double getSuggestedCapDiameter() {
		return suggestedCapDiameter;
	}
	/**
	 * @param suggestedCapDiameter
	 * 							the suggested diameter of the capillary in mm.
	 */
	public void setSuggestedCapDiameter(double suggestedCapDiameter) {
		this.suggestedCapDiameter = suggestedCapDiameter;
	}
	/**
	 * @return the chosen beam state (keV and flux)
	 */
	public String getBeamState() {
		return beamState;
	}
	/**
	 * @param beamState
	 * 				the beam state to set (keV and flux)
	 */
	public void setBeamState(String beamState) {
		this.beamState = beamState;
	}
	/**
	 * @return the chosen container description.
	 */
	public String getContainer() {
		return container;
	}
	/**
	 * @param container
	 * 				the container description to set.
	 */
	public void setContainer(String container) {
		this.container = container;
	}
	
}
