/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import uk.ac.diamond.scisoft.xpdf.XPDFComposition;

public class XPDFPhase {

	
	String name;
	int iDCode;
	String comment;
	XPDFSpaceGroup spaceGroup;
	
	/**
	 * Default constructor
	 */
	public XPDFPhase() {
		
	}
	
	/**
	 * Copy constructor
	 * @param inPhase
	 */
	public XPDFPhase(XPDFPhase inPhase) {
		
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
	
	/**
	 * Sets the space group symmetry of the unit cell
	 * @param spaceGroupIndex
	 * 					the space group structure to set
	 */
	public void setSpaceGroup(int spaceGroupIndex) {
		if (spaceGroupIndex > 0 && spaceGroupIndex <= XPDFSpaceGroup.nGroups)
			this.spaceGroup = XPDFSpaceGroup.get(spaceGroupIndex);
	}
	
	public XPDFSpaceGroup getSpaceGroup() {
		return spaceGroup;
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
		return 0.0;
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
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				