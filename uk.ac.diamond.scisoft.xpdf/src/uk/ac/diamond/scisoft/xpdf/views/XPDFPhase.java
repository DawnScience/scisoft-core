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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.exception.OutOfRangeException;

import uk.ac.diamond.scisoft.xpdf.XPDFComposition;

/**
 * Holds the information for a phase.
 * <p>
 * The specifics of a phase can be stored here. The information is heavily
 * skewed towards the needs of the XPDF project.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class XPDFPhase {

	private String name;
	private int iDCode;
	private String comment;
	private XPDFPhaseForm form;
	private CrystalSystem system;
	private XPDFSpaceGroup spaceGroup;
	private double[] unitCellLengths;
	private double[] unitCellDegrees;
	private List<XPDFAtom> atoms;
	
	private static final int nDim = 3;

	
	/**
	 * Default constructor
	 */
	public XPDFPhase() {
		unitCellLengths = new double[] {0, 0, 0}; // zero is meaningless, used as a default value
		unitCellDegrees = new double[] {0, 0, 0}; // ditto
		form = XPDFPhaseForm.get(XPDFPhaseForm.Forms.AMORPHOUS);
		atoms = new ArrayList<XPDFAtom>();
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
	public void addAtom(XPDFAtom atom) {
		atoms.add(atom);
	}

	/**
	 * Adds all the atoms in a {@link Collection}.
	 * @param atoms
	 * 				the new atoms to be added.
	 */
	public void addAllAtoms(Collection<? extends XPDFAtom> atoms) {
		this.atoms.addAll(atoms);
	}
	
	/**
	 * Empties the phase of all its previously defined atoms.
	 */
	public void clearAtoms() {
		atoms.clear();
	}
	
	/**
	 * Gets the first atom with the given label
	 * @param label
	 * 				label of the atom to be got
	 * @return the first atom with that label
	 */
	public XPDFAtom getAtom(String label) {
		for (XPDFAtom atom : atoms)
			if (atom.getLabel().equals(label))
				return atom;
		return null;
	}
	/**
	 * Returns all atoms defined in the phase
	 * @return a Collection of all the atoms in the phase
	 */
	public Collection<XPDFAtom> getAllAtoms() {
		return atoms;
	}
	
	/**
	 * Sets the form of the phase.
	 * <p>
	 * Sets the form of the phase, from a choice of:
	 * <ul>
	 * <li> Amorphous </li>
	 * <li> Glassy </li>
	 * <li> Liquid </li>
	 * <li> Crystalline. </li>
	 * </ul>
	 * @param formIn
	 */
	public void setForm(XPDFPhaseForm formIn) {
		form = formIn;
	}
	
	/**
	 * Gets the form of the phase.
	 * <p>
	 * Gets the form of the phase, from a choice of:
	 * <ul>
	 * <li> Amorphous </li>
	 * <li> Glassy </li>
	 * <li> Liquid </li>
	 * <li> Crystalline. </li>
	 * </ul>
	 * @return form of the phase.
	 */
	public XPDFPhaseForm getForm() {
		return form;
	}
	
	/**
	 * Returns whether the phase is crystalline
	 * @return whether the phase is crystalline.
	 */
	public boolean isCrystalline() {
		return (form != null) ? form.isCrystalline() : false;
	}
	
	/**
	 * Sets the crystal system of the phase.
	 * @param inSystem
	 * 				Crystal system of the phase, as represented by a
	 * 				{@link CrystalSystem}.
	 */
	public void setCrystalSystem(CrystalSystem inSystem) {
		if (system != inSystem) {
			system = inSystem;
			// Check if the space group is a rhombohedral pseudo-space group,
			// and check that we are changing to the hexagonal crystal system,
			// that is, the crystal system of its alternative basis.
			if (spaceGroup.isRhombohedral() && spaceGroup.asHexagonal().getSystem() == inSystem) {
				unitCelltoHexagonal();
			} else
				// Check if the space group has an alternative rhombohedral
				// basis, and that we are changing the crystal system to the
				// rhombohedral lattice, that is the same system as the
				// alternative representation
				if (spaceGroup.hasRhombohedral() && spaceGroup.asRhombohedral().getSystem() == inSystem) {
					unitCelltoRhombohedral();
			} else {
					setSpaceGroup(CrystalSystem.lowestGroups[system.getOrdinal()]);
			}
		}
	}
	
	/**
	 * Gets the crystal system of the phase.
	 * @return Crystal system of the phase.
	 */
	public CrystalSystem getCrystalSystem() {
		return system;
	}
	
	/**
	 * Sets the space group symmetry of the unit cell
	 * @param spaceGroupIndex
	 * 					the space group to set by index
	 */
	public void setSpaceGroup(int spaceGroupIndex) {
		this.setSpaceGroup(XPDFSpaceGroup.get(spaceGroupIndex));
	}
	
	/**
	 * Sets the space group of the phase.
	 * @param spaceGroup
	 * 					The space group of the phase, as represented by an
	 * {@link XPDFSpaceGroup}.
	 */
	public void setSpaceGroup(XPDFSpaceGroup spaceGroup) {
		// If we set a space group, the phase must be crystalline
		this.setForm(XPDFPhaseForm.get(XPDFPhaseForm.Forms.CRYSTALLINE));

		this.spaceGroup = spaceGroup;
		this.system = spaceGroup.getSystem();
	}
	
	/**
	 * Gets the space group of the phase
	 * @return The space group of the phase, as represented by an
	 * {@link XPDFSpaceGroup}.
	 */
	public XPDFSpaceGroup getSpaceGroup() {
		return spaceGroup;
	}

	/**
	 * Sets the unit cell edge lengths.
	 * @param length
	 * 				a 3-element array of the edge lengths
	 */
	public void setUnitCellLengths(double[] lengths) {
		for (int i = 0; i < nDim; i++)
			unitCellLengths[i] = lengths[i];
	}
	
	/**
	 * Sets the unit cell edge lengths.
	 * @param a
	 * @param b
	 * @param c Edge cell lengths
	 */
	public void setUnitCellLengths(double a, double b, double c) {
		unitCellLengths[0] = a;
		unitCellLengths[1] = b;
		unitCellLengths[2] = c;
	}
	
	/**
	 * Sets a single unit cell edge length
	 * @param dimension
	 * 				zero-based index of the dimension to alter (unit cell edge
	 * 				a is equivalent to index 0, &c.)
	 * @param l
	 * 			Length to set.
	 */
	public void setUnitCellLength(int dimension, double l) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		unitCellLengths[dimension] = l;
	}
	
	/**
	 * Gets the unit cell edge lengths of the phase.
	 * @return unit cell edge lengths of the phase.
	 */
	public double[] getUnitCellLengths() {
		return unitCellLengths;
	}
	
	/**
	 * Gets one unit cell edge length of the phase
	 * @param dimension
	 * 				zero-based index of the dimension to alter (unit cell edge
	 * 				a is equivalent to index 0, &c.)
	 * @return length of the unit cell edge
	 */
	public double getUnitCellLength(int dimension) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		return unitCellLengths[dimension];
	}
	
	/**
	 * Sets the unit cell edge angles.
	 * @param length
	 * 				a 3-element array of the edge angles in degrees.
	 */
	public void setUnitCellAngles(double[] angles) {
		for (int i = 0; i < nDim; i++)
			unitCellDegrees[i] = angles[i];
	}
	
	/**
	 * Sets the unit cell edge angles.
	 * @param a
	 * @param b
	 * @param c Edge cell angles in degrees.
	 */
	public void setUnitCellAngles(double a, double b, double c) {
		unitCellDegrees[0] = a;
		unitCellDegrees[1] = b;
		unitCellDegrees[2] = c;
	}
	
	/**
	 * Sets a single unit cell edge angle.
	 * @param dimension
	 * 				zero-based index of the dimension to alter (unit cell edge
	 * 				angle α is equivalent to index 0, &c.)
	 * @param a
	 * 			angle to be set.
	 */
	public void setUnitCellAngle(int dimension, double a) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		unitCellDegrees[dimension] = a;
	}
	
	/**
	 * Gets the unit cell edge angles of the phase.
	 * @return unit cell edge angles of the phase in degrees.
	 */
	public double[] getUnitCellAngle() {
		return unitCellDegrees;
	}
	
	/**
	 * Gets one unit cell edge angle of the phase
	 * @param dimension
	 * 				zero-based index of the dimension to alter (unit cell edge
	 * 				angle α is equivalent to index 0, &c.)
	 * @return length of the unit cell edge
	 */
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
	
	/**
	 * Gets the volume of the unit cell
	 * @return
	 * 		the volume of the unit cell
	 */
	// TODO: Fix this for unit cell without 90° angles
	public double getUnitCellVolume() {
		return unitCellLengths[0] * unitCellLengths[1] * unitCellLengths[2];
	}

	/**
	 * Adds a comment to the phase.
	 * @param inComment
	 * 					the comment to be added.
	 */
	public void addComment(String inComment) {
		if (comment == null) comment = new String();
		if (inComment != null)
			comment += (comment.length() > 0) ? " " + inComment : inComment;
	}
	
	/**
	 * Clears the comment from this.
	 */
	public void clearComment() {
		comment = new String();
	}
	
	/**
	 * Gets the comment for this.
	 * @return the comment.
	 */
	public String getComment() {
		return (comment != null) ? comment : "";
	}

	/**
	 * Changes the present unit cell from hexagonal to rhombohedral.
	 * <p> 
	 * Changes the unit cell parameters from a hexagonal basis
	 * (aac 90,90,120)to a rhombohedral basis (aaa, ααα), and sets the space
	 * group to the rhombohedral equivalent.
	 */
	private void unitCelltoRhombohedral() {
		setSpaceGroup(spaceGroup.asRhombohedral());

		double ah = unitCellLengths[0], c = unitCellLengths[2];
		// Convert them to the squared lengths
		ah *= ah;
		c *= c;
		// Squared rhombohedral unit cell edge length 
		double ar = ah/3 + c/9;
		double alpha = Math.toDegrees( Math.acos((-ah/6 + c/9)/ar) );
		// Make ar the length
		ar = Math.sqrt(ar);
		unitCellLengths[0] = unitCellLengths[1] = unitCellLengths[2] = ar;
		unitCellDegrees[0] = unitCellDegrees[1] = unitCellDegrees[2] = alpha;
	}
	/**
	 * Changes the present unit cell from rhombohedral to hexagonal.
	 * <p> 
	 * Changes the unit cell parameters from a rhombohedral basis (aaa, ααα)
	 * to a hexagonal basis (aac 90,90,120), and sets the space
	 * group to the hexagonal equivalent.
	 */
	private void unitCelltoHexagonal() {
		setSpaceGroup(spaceGroup.asHexagonal());

		double ar = unitCellLengths[0];
		double cosAlpha = Math.cos(Math.toRadians(unitCellDegrees[0]));
		// Convert to the squared length
		ar *= ar;
		// Calculate the squared unit cell lengths
		double ah = 2*ar*(1-cosAlpha);
		double c = 3*ar*(1+2*cosAlpha);
		ah = Math.sqrt(ah);
		c = Math.sqrt(c);
		unitCellLengths[0] = unitCellLengths[1] = ah;
		unitCellLengths[2] = c;
		unitCellDegrees[0] = unitCellDegrees[1] = 90.0;
		unitCellDegrees[2] = 120;
	}

	/**
	 * Returns the chemical formula of this phase.
	 * <p>
	 * Returns the chemical formula of this phase in Hall notation.
	 * @return chemical formula in Hall notation.
	 */
	public String getHallNotation(boolean reduceToPrimitive) {
		
		final int nElements = 100; // Up to and including fermium
		
		final String[] elementSymbol = { "n",
		"H","He","Li","Be","B","C","N","O","F","Ne",
		"Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca",
		"Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn",
		"Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr",
        "Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn",
        "Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd",
        "Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb",
        "Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au","Hg",
        "Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th",
        "Pa","U","Np","Pu","Am","Cm","Bk","Cf","Es","Fm"};
		
		final int[] alphabeticElements = {
				89, 47, 13, 95, 18, 33, 85, 79, 5, 56,
				4, 83, 97, 35, 6, 20, 48, 58, 98, 17,
				96, 27, 24, 55, 29, 66, 68, 99, 63, 9,
				26, 100, 87, 31, 64, 32, 1, 2, 72, 80,
				67, 53, 49, 77, 19, 36, 57, 3, 71, 12,
				25, 42, 7, 11, 41, 60, 10, 28, 93, 8,
				76, 15, 91, 82, 46, 61, 84, 59, 78, 94,
				88, 37, 75, 45, 86, 44, 16, 51, 21, 34,
				14, 62, 50, 38, 73, 65, 43, 52, 90, 22,
				81, 69, 92, 23, 74, 54, 39, 70, 30, 40
		};

		
		String hall = "";
		
		Map<Integer, Double> atomCount = new HashMap<Integer, Double>();
		// Add up all the occupancies by element
		for (XPDFAtom atom : atoms) {
			int z = atom.getAtomicNumber();
			double n = atom.getOccupancy();
			if (atomCount.containsKey(z)) {
				atomCount.put(z, atomCount.get(z) + n);
			} else {
				atomCount.put(z, n);
			}
		}
		
		if (reduceToPrimitive) {
		}
		
		
		// Do the special case for organic compounds
		if (atomCount.containsKey(6)) {
			hall += elementSymbol[6] + prettifyDouble(atomCount.get(6));
			atomCount.remove(6);
			if (atomCount.containsKey(1)) {
				hall += elementSymbol[1] + prettifyDouble(atomCount.get(1));
				atomCount.remove(1);
			}
		}
		
		// Do all the remaining elements in order
		for (int i = 0; i < nElements; i++) { 
			int z = alphabeticElements[i];
			if (atomCount.containsKey(z))
				hall += elementSymbol[z] + prettifyDouble(atomCount.get(z));
		}
		return (hall.length() != 0) ? hall : "-";
	}
	
	// Format the atom multiplicity as nicely as we can
	private String prettifyDouble(double n) {
		if (n == 1.0)
			return "";
		
		String number = (n == Math.floor(n)) ? Integer.toString((int) n) :  Double.toString(n);
		String normalNumbers = "0123456789.";
		String subscriptNumbers = "₀₁₂₃₄₅₆₇₈₉.";
		
		for (int i = 0; i < normalNumbers.length(); i++ )
			number = number.replace(normalNumbers.charAt(i), subscriptNumbers.charAt(i));
		
		return number;
	}
	
}				