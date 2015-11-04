/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

class XPDFFluorescentLine {
	private double energy; // keV
	private double crossSection; // barns?
	private int fluorescentZ; // atomic number
	
	/**
	 * Default constructor
	 */
	public XPDFFluorescentLine() {
	}
	
	/**
	 * Copy constructor
	 */
	public XPDFFluorescentLine(XPDFFluorescentLine inLine) {
		this.energy = inLine.energy;
		this.crossSection = inLine.crossSection;
		this.fluorescentZ = inLine.fluorescentZ;
	}
	
	/**
	 * Constructor to actually use 
	 */
	public XPDFFluorescentLine(double energy, double crossSection, int fluorescentZ) {
		this.energy = energy;
		this.crossSection = crossSection;
		this.fluorescentZ = fluorescentZ;
	}

	/**
	 * @return the energy in keV
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * @return the cross section in barns
	 */
	public double getCrossSection() {
		return crossSection;
	}

	/**
	 * @return the fluorescent atomic number
	 */
	public int getFluorescentZ() {
		return fluorescentZ;
	}
	
}
