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

import uk.ac.diamond.scisoft.xpdf.XPDFComposition;

public class XPDFSampleParameters {
	private String name;
	private int id;
	private List<String> phases;
	private XPDFComposition composition;
	private double density;
	private double packingFraction;
	private double suggestedEnergy;
	private double mu;
	private double suggestedCapDiameter;
	private String beamState;
	private String container;

	/**
	 * default ctor
	 */
	public XPDFSampleParameters() {
	}

	/**
	 * Copy constructor
	 */
	public XPDFSampleParameters(XPDFSampleParameters inSamp) {
		this.name = inSamp.name;
		this.phases = new ArrayList<String>();
		this.phases.addAll(inSamp.phases);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
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
	 * @return the phases
	 */
	public List<String> getPhases() {
		return phases;
	}
	/**
	 * @param phases the phases to set
	 */
	public void setPhases(List<String> phases) {
		this.phases = phases;
	}
	/**
	 * @return the composition
	 */
	public XPDFComposition getComposition() {
		return composition;
	}
	/**
	 * @param composition the composition to set
	 */
	public void setComposition(XPDFComposition composition) {
		this.composition = composition;
	}
	/**
	 * @return the density
	 */
	public double getDensity() {
		return density;
	}
	/**
	 * @param density the density to set
	 */
	public void setDensity(double density) {
		this.density = density;
	}
	/**
	 * @return the packingFraction
	 */
	public double getPackingFraction() {
		return packingFraction;
	}
	/**
	 * @param packingFraction the packingFraction to set
	 */
	public void setPackingFraction(double packingFraction) {
		this.packingFraction = packingFraction;
	}
	/**
	 * @return the suggestedEnergy
	 */
	public double getSuggestedEnergy() {
		return suggestedEnergy;
	}
	/**
	 * @param suggestedEnergy the suggestedEnergy to set
	 */
	public void setSuggestedEnergy(double suggestedEnergy) {
		this.suggestedEnergy = suggestedEnergy;
	}
	/**
	 * @return the mu
	 */
	public double getMu() {
		return mu;
	}
	/**
	 * @param mu the mu to set
	 */
	public void setMu(double mu) {
		this.mu = mu;
	}
	/**
	 * @return the suggestedCapDiameter
	 */
	public double getSuggestedCapDiameter() {
		return suggestedCapDiameter;
	}
	/**
	 * @param suggestedCapDiameter the suggestedCapDiameter to set
	 */
	public void setSuggestedCapDiameter(double suggestedCapDiameter) {
		this.suggestedCapDiameter = suggestedCapDiameter;
	}
	/**
	 * @return the beamState
	 */
	public String getBeamState() {
		return beamState;
	}
	/**
	 * @param beamState the beamState to set
	 */
	public void setBeamState(String beamState) {
		this.beamState = beamState;
	}
	/**
	 * @return the container
	 */
	public String getContainer() {
		return container;
	}
	/**
	 * @param container the container to set
	 */
	public void setContainer(String container) {
		this.container = container;
	}
	
}
