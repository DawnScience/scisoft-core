/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import org.apache.commons.math3.exception.OutOfRangeException;

import uk.ac.diamond.scisoft.xpdf.XPDFComposition;

public class XPDFPhase {

	private String name;
	private int iDCode;
	private String comment;
	private XPDFPhaseForm form;
	private CrystalSystem system;
	private XPDFSpaceGroup spaceGroup;
	private double[] unitCellLengths;
	private double[] unitCellDegrees;
	private static final int nDim = 3;
	
	/**
	 * Default constructor
	 */
	public XPDFPhase() {
		unitCellLengths = new double[] {0, 0, 0}; // zero is meaningless, used as a default value
		unitCellDegrees = new double[] {0, 0, 0}; // ditto
		form = XPDFPhaseForm.get(XPDFPhaseForm.Forms.AMORPHOUS);
	}
	
	/**
	 * Copy constructor
	 * @param inPhase
	 */
	public XPDFPhase(XPDFPhase inPhase) {
		//TODO copy some things
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
	 * @return the iDCode
	 */
	public int getId() {
		return iDCode;
	}

	/**
	 * @param iD the iDCode to set
	 */
	public void setId(int iD) {
		this.iDCode = iD;
	}

	/**
	 * Adds an atom to a phase unit cell
	 */
	// TODO: Add the argument(s)
	public void addAtom() {
		
	}
	
	public void setForm(XPDFPhaseForm formIn) {
		form = formIn;
	}
	
	public XPDFPhaseForm getForm() {
		return form;
	}
	
	public boolean isCrystalline() {
		return (form != null) ? form.isCrystalline() : false;
	}
	
	public void setCrystalSystem(CrystalSystem inSystem) {
		if (system != inSystem) {
			system = inSystem;
			setSpaceGroup(CrystalSystem.lowestGroups[system.getOrdinal()]);
		}
	}
	
	public CrystalSystem getCrystalSystem() {
		return system;
	}
	
	/**
	 * Sets the space group symmetry of the unit cell
	 * @param spaceGroupIndex
	 * 					the space group structure to set
	 */
	public void setSpaceGroup(int spaceGroupIndex) {
		// If we set a space group, the phase must be crystalline
		this.setForm(XPDFPhaseForm.get(XPDFPhaseForm.Forms.CRYSTALLINE));

		if (spaceGroupIndex >= 0 && spaceGroupIndex <= XPDFSpaceGroup.nGroups) {
			this.spaceGroup = XPDFSpaceGroup.get(spaceGroupIndex);

		if (spaceGroupIndex >= 1 && spaceGroupIndex <= XPDFSpaceGroup.nGroups)
			// Find and set the crystal system, too
			for (int i = 0; i < CrystalSystem.nSystems; i++)
				if (CrystalSystem.get(i).getGroups().contains(this.spaceGroup))
					this.setCrystalSystem(CrystalSystem.get(i));
		}
	}
	
	public XPDFSpaceGroup getSpaceGroup() {
		return spaceGroup;
	}

	public void setUnitCellLengths(double[] lengths) {
		for (int i = 0; i < nDim; i++)
			unitCellLengths[i] = lengths[i];
	}
	
	public void setUnitCellLengths(double a, double b, double c) {
		unitCellLengths[0] = a;
		unitCellLengths[1] = b;
		unitCellLengths[2] = c;
	}
	
	public void setUnitCellLength(int dimension, double l) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		unitCellLengths[dimension] = l;
	}
	
	public double[] getUnitCellLengths() {
		return unitCellLengths;
	}
	
	public double getUnitCellLength(int dimension) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		return unitCellLengths[dimension];
	}
	
	public void setUnitCellAngles(double[] angles) {
		for (int i = 0; i < nDim; i++)
			unitCellDegrees[i] = angles[i];
	}
	
	public void setUnitCellAngles(double a, double b, double c) {
		unitCellDegrees[0] = a;
		unitCellDegrees[1] = b;
		unitCellDegrees[2] = c;
	}
	
	public void setUnitCellAngle(int dimension, double a) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		unitCellDegrees[dimension] = a;
	}
	
	public double[] getUnitCellAngle() {
		return unitCellDegrees;
	}
	
	public double getUnitCellAngle(int dimension) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		return unitCellDegrees[dimension];
	}
	/**
	 * Returns the density.
	 * @return the crystallographic density of the phase in g cm⁻³.
	 */
	public double getDensity() {
	
		return 0.0;
	}
	
	/**
	 * Returns the composition of the unit cell of the phase.
	 * @return the XPDFComposition object describing the contents of the unit cell
	 */
	public XPDFComposition getComposition() {
		
		return new XPDFComposition("");
	}
	
	public double getUnitCellVolume() {
		return unitCellLengths[0] * unitCellLengths[1] * unitCellLengths[2];
	}

	public void addComment(String inComment) {
		if (comment == null) comment = new String();
		if (inComment != null)
			comment += (comment.length() > 0) ? " " + inComment : inComment;
	}
	
	public void clearComment() {
		comment = new String();
	}
	
	public String getComment() {
		return (comment != null) ? comment : "";
	}

}				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				