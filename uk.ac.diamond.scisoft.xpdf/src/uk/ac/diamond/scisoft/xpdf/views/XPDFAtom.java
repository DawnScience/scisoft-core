/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.Arrays;

/**
 * A class to hold the parameters on an individual atom within a unit cell.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class XPDFAtom {

	private static final int nDim = 3;
	private String label;
	private double[] position;
	private double occupancy;
	private int atomicNumber;
	// atomic displacement parameters, a leading isotropic value, and 9
	// anisotropic parameters
	private double[] atomicDisplacement;

	/**
	 * Constructs a new atom with the given atomic number.
	 * @param atomicNumber
	 * 					atomic number of the atom
	 */
	public XPDFAtom() {
		atomicNumber = 0; // a new atom is a neutron?
		label = "";
		position = new double[nDim];
		atomicDisplacement = new double[nDim*nDim+1];
	}
	
	/**
	 * Constructs an atom without a label or displacement information.
	 * @param atomicNumber
	 * @param occupancy
	 * @param position
	 */
	public XPDFAtom(int atomicNumber, double occupancy, double[] position) {
		this();
		this.setAtomicNumber(atomicNumber);
		this.setOccupancy(occupancy);
		this.setPosition(position);
	}
	
	/**
	 * Constructors an atom without displacement information.
	 * @param label
	 * @param atomicNumber
	 * @param occupancy
	 * @param position
	 */
	public XPDFAtom(String label, int atomicNumber, double occupancy, double[] position) {
		this(atomicNumber, occupancy, position);
		this.label = label;
	}
	
	/**
	 * Copy constructor.
	 * @param inAtom
	 * 				XPDFAtom to be copied
	 */
	public XPDFAtom(XPDFAtom inAtom) {
		this(inAtom.getLabel(), inAtom.getAtomicNumber(), inAtom.getOccupancy(), inAtom.getPosition());
	}
	
	/**
	 * Copy constructor, giving the new atom a new label.
	 * @param inAtom
	 * 				atom to be copied.
	 * @param newLabel
	 * 				label to be applied to the new atom.
	 */
	public XPDFAtom(XPDFAtom inAtom, String newLabel) {
		this(newLabel, inAtom.getAtomicNumber(), inAtom.getOccupancy(), inAtom.getPosition());
	}
	
	/**
	 * Sets the label of the atom.
	 * @param label
	 * 				label to give the atom
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Gets the label applied to the atom.
	 * @return the atom's label.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets the atomic number of the atom.
	 * @param atomicNumber
	 * 					the atomic number of the atom, such that hydrogen is 1.
	 */
	public void setAtomicNumber(int atomicNumber) {
		this.atomicNumber = atomicNumber;
	}
	
	/**
	 * Sets the position of the atom within the unit cell.
	 * <p>
	 * Sets the position of the atom within the unit cell in units of ??? along ??? axes
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setPosition(double x, double y, double z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}
	
	/**
	 * Sets the atomic position as a primitive array
	 * @param r
	 * 			array specifying the position of the atom 
	 */
	public void setPosition(double[] r) {
		this.setPosition(r[0], r[1], r[2]);
	}
	
	/**
	 * Sets the occupancy of the atom within the unit cell
	 * @param occupancy
	 * 				How many times the atom should be counted within the unit cell
	 */
	public void setOccupancy(double occupancy) {
		this.occupancy = occupancy;
	}
	
	/**
	 * Sets the isotropic atomic displacement
	 * @param b
	 * 			isotropic atomic displacement 
	 */
	public void setIsotropicDisplacement(double b) {
		this.atomicDisplacement[0] = b;
	}
	
	/**
	 * Sets the anisotropic atomic displacement 
	 * @param b
	 * 			9 element array of the anisotropic displacement parameters 
	 */
	public void setAnisotropicDisplacement(double[] b) {
		for (int i = 0; i < nDim*nDim; i++) {
			atomicDisplacement[i+1] = b[i];
		}
	}
	
	/**
	 * Gets the atomic position
	 * @return atomic position as an array of doubles
	 */
	public double[] getPosition() {
		return this.position;
	}
	
	/**
	 * Gets the occupancy of the atom
	 * @return occupancy of the atom
	 */
	public double getOccupancy() {
		return this.occupancy;
	}
	
	/**
	 * Gets the atomic number of the atom
	 * @return atomic number of the atom
	 */
	public int getAtomicNumber() {
		return this.atomicNumber;
	}
	
	/**
	 * Gets the isotropic atomic displacement
	 * @return the isotropic atomic displacement
	 */
	public double getIsotropicDisplacement() {
		return atomicDisplacement[0];
	}
	
	/**
	 * Gets the anisotropic atomic displacement
	 * @return the anisotropic atomic displacement as a 9 element array
	 */
	public double[] getAnisotropicDisplacement() {
		return Arrays.copyOfRange(atomicDisplacement, 1, nDim*nDim+1);
	}

	/**
	 * Set the position in a specific direction
	 * @param axisIndex
	 * 					axis of the coordinate to change. 0 is x, 2 is z
	 * @param value
	 * 				new value of the coordinate.
	 */
	public void setPosition(int axisIndex, Double value) {
		position[axisIndex % 3] = value % 1.0;
	}
}
