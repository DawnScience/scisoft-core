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
import java.util.Arrays;
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
	 * Copy constructor.
	 * <p>
	 * Does not generate an new id code
	 * @param inPhase
	 */
	public XPDFPhase(XPDFPhase inPhase) {
		this.name = new String(inPhase.name);
//		this.iDCode do not copy iDCode
		this.comment = (inPhase.comment != null) ? new String(inPhase.comment) : null;
		this.form = inPhase.form; // phase form...
		this.system = inPhase.system; // ...crystal system ...
		this.spaceGroup = inPhase.spaceGroup; // ... and space group are static
		this.unitCellLengths = Arrays.copyOf(inPhase.unitCellLengths, nDim);
		this.unitCellDegrees = Arrays.copyOf(inPhase.unitCellDegrees, nDim);
		this.atoms = new ArrayList<XPDFAtom>();
		for (XPDFAtom atom : inPhase.atoms)
			this.atoms.add(new XPDFAtom(atom));
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
			if (spaceGroup != null) {
			
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
						// Changing arbitrary crystal systems
						setSpaceGroup(CrystalSystem.lowestGroups[system.getOrdinal()]);
						// Move all atoms to the 'general' Wyckoff position (the last defined)
						// TODO: Recalculate Wyckoff symbol based on position, once this is possible.
						for (XPDFAtom atom : atoms) {
							atom.setWyckoffLetter(Character.toString(XPDFSpaceGroup.allWyckoffLetters.charAt(getSpaceGroup().getNWyckoffLetters()-1)));
						}
					}
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
			setUnitCellLength(i, lengths[i]);
	}
	
	/**
	 * Sets the unit cell edge lengths.
	 * @param a
	 * @param b
	 * @param c Edge cell lengths
	 */
	public void setUnitCellLengths(double a, double b, double c) {
		setUnitCellLength(0, a);
		setUnitCellLength(1, b);
		setUnitCellLength(2, c);
	}
	
	/**
	 * Sets a single unit cell edge length.
	 * <p>
	 * Sets the cell edge lengths, ensuring whatever consistency is required by
	 * the crystal system.
	 * @param dimension
	 * 				zero-based index of the dimension to alter (unit cell edge
	 * 				a is equivalent to index 0, &c.)
	 * @param l
	 * 			Length to set.
	 */
	public void setUnitCellLength(int dimension, double l) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		int[] axisIndices = this.getCrystalSystem().getAxisIndices();
		// get the principal cell edge dimension for this dimension
		int principleEdge = axisIndices[dimension];
		// get the list of the dimensions defined by this principle dimension
		List<Integer> definedDimensions = new ArrayList<Integer>();
		for (int i = 0; i < nDim; i++)
			if (axisIndices[i] == principleEdge)
				definedDimensions.add(i);

		for (int edge : definedDimensions)
			unitCellLengths[edge] = l;
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
			setUnitCellAngle(i, angles[i]);
	}
	
	/**
	 * Sets the unit cell edge angles.
	 * @param a
	 * @param b
	 * @param c Edge cell angles in degrees.
	 */
	public void setUnitCellAngles(double a, double b, double c) {
		setUnitCellAngle(0, a);
		setUnitCellAngle(1, b);
		setUnitCellAngle(2, c);
	}
	
	/**
	 * Sets a single unit cell edge angle.
	 * <p>
	 * Sets the unit cell angles, guaranteeing consistency as defined by the
	 * crystal system.
	 * @param dimension
	 * 				zero-based index of the dimension to alter (unit cell edge
	 * 				angle α is equivalent to index 0, &c.)
	 * @param a
	 * 			angle to be set.
	 */
	public void setUnitCellAngle(int dimension, double a) {
		if (dimension < 0 || dimension >= nDim)
			throw new OutOfRangeException(dimension, 0, nDim);
		int[] fixedAngles = Arrays.copyOf(this.getCrystalSystem().getFixedAngles(), nDim);
		// Check if the angle trying to be set is fixed: a non-negative integer
		if (fixedAngles[dimension] >= 0)
			return;
		// Unmunge the axes to revert to the dimension index that defines this
		// dimension
		for (int dim = 0; dim < nDim; dim++)
			if (fixedAngles[dim] > 0)
				fixedAngles[dim] = dim;
			else
				fixedAngles[dim] = 1-fixedAngles[dim];
		
		// The index of the angle defining this one
		int principleAngle = fixedAngles[dimension];
		
		// get the list of dimensions defined by this principle dimension
		List<Integer> definedDimensions = new ArrayList<Integer>();
		for (int i = 0; i < nDim; i++)
			if (fixedAngles[i] == principleAngle)
				definedDimensions.add(i);
		
		for (int angle : definedDimensions)
			unitCellDegrees[angle] = a;
	
	}
	
	/**
	 * Gets the unit cell edge angles of the phase.
	 * @return unit cell edge angles of the phase in degrees.
	 */
	public double[] getUnitCellAngle() {
		double[] assignedAngles = Arrays.copyOf(unitCellDegrees, nDim);
		// Account for fixed angles from the space group.
		int[] fixedAngles = (spaceGroup != null) ? spaceGroup.getSystem().getFixedAngles() : null;
		

		if (fixedAngles != null) {
			for (int i = 0; i < nDim; i++) {
				int fixedAngle = fixedAngles[i];
				if (fixedAngle > 0)
					assignedAngles[i] = fixedAngle;
			}
		}		
		return assignedAngles;
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

		// From Pure Appl. Chem., 2013, 85, pp.1047-1078, via http://www.chem.qmul.ac.uk/iupac/AtWt/
		// With the neutron as the zero-th element

		double[] atomicWeights = { 1.009,
				1.008, 4.002602, 6.94, 9.0121831, 10.81, 12.011, 14.007, 15.999, 18.998403163, 20.1797,
				22.98976928, 24.305, 26.9815385, 28.085, 30.973761998, 32.06, 35.45, 39.948, 39.0983, 40.078,
				44.955908, 47.867, 50.9415, 51.9961, 54.938044, 55.845, 58.933194, 58.6934, 63.546, 65.38,
				69.723, 72.630, 74.921595, 78.971, 79.904, 83.798, 85.4678, 87.62, 88.90584, 91.224,
				92.90637, 95.95, 97, 101.07, 102.90550, 106.42, 107.8682, 112.414, 114.818, 118.710,
				121.760, 127.60, 126.90447, 131.293, 132.90545196, 137.327, 138.90547, 140.116, 140.90766, 144.242,
				145, 150.36, 151.964, 157.25, 158.92535, 162.500, 164.93033, 167.259, 168.93422, 173.045,
				174.9668, 178.49, 180.94788, 183.84, 186.207, 190.23, 192.217, 195.084, 196.966569, 200.592,
				204.38, 207.2, 208.98040, 209, 210, 222, 223, 226, 227, 232.0377,
				231.03588, 238.02891, 237, 244, 243, 247, 247, 251, 252, 257
		};
		double unitCellAMU = 0.0;
		for (XPDFAtom atom : atoms) {
			unitCellAMU += atomicWeights[atom.getAtomicNumber()] * atom.getOccupancy() * spaceGroup.getSiteMultiplicity(atom.getWyckoffLetter());
		}
		double unitCellkg = unitCellAMU * 1.660539040e-27;
		double unitCellm3 = getUnitCellVolume() * 1e-30;
		
		double unitCellSIDensity = unitCellkg/unitCellm3;
		double unitCellCGSDensity = unitCellSIDensity * 1e3 / 1e6;

		return (!atoms.isEmpty()) ? unitCellCGSDensity : 0.0;
	}
	
	/**
	 * Returns the composition of the unit cell of the phase.
	 * @return the XPDFComposition object describing the contents of the unit cell
	 */
	public XPDFComposition getComposition() {
		
		return new XPDFComposition(getHallNotation(true, false));
	}
	
	/**
	 * Gets the volume of the unit cell
	 * @return
	 * 		the volume of the unit cell
	 */
	public double getUnitCellVolume() {
		// Volume if all angles were 90°
		double[] trueLengths = this.getUnitCellLengths(),
				trueAngles = this.getUnitCellAngle();
		double orthoVolume = trueLengths[0] * trueLengths[1] * trueLengths[2];
		// Shape coefficient; angular dependency
		double ca = Math.cos(Math.toRadians(trueAngles[0])),
				cb = Math.cos(Math.toRadians(trueAngles[1])),
				cg = Math.cos(Math.toRadians(trueAngles[2]));
		double shapeCoefficient = Math.sqrt(1 - ca*ca - cb*cb - cg*cg + 2*ca*cb*cg);
		return orthoVolume*shapeCoefficient;
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
	public String getHallNotation(boolean reduceToPrimitive, boolean useSubscripts) {
		
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
		List<Integer> multiplicityCount = new ArrayList<Integer>();
		// Add up all the occupancies by element
		for (XPDFAtom atom : atoms) {
			int z = atom.getAtomicNumber();
			double n = atom.getOccupancy();
			// atoms are only defined for the Wyckoff positions
			int multiplicity = spaceGroup.getSiteMultiplicity(atom.getWyckoffLetter()); 
			multiplicityCount.add(multiplicity);
			n *= multiplicity;
			if (atomCount.containsKey(z)) {
				atomCount.put(z, atomCount.get(z) + n);
			} else {
				atomCount.put(z, n);
			}
		}
		
		if (reduceToPrimitive) {
			// The multiplicity list simply an array of the multiplicities of
			// each position. Find the GCD of all the numbers.
			int gcd = greatestCommonDivisor(multiplicityCount);
			// Run through the map, dividing all data by GCD
			for (Map.Entry<Integer, Double> entry : atomCount.entrySet())
				atomCount.put(entry.getKey(), entry.getValue()/gcd);
		}
		
		
		// Do the special case for organic compounds
		if (atomCount.containsKey(6)) {
			hall += elementSymbol[6] + prettifyDouble(atomCount.get(6), useSubscripts);
			atomCount.remove(6);
			if (atomCount.containsKey(1)) {
				hall += elementSymbol[1] + prettifyDouble(atomCount.get(1), useSubscripts);
				atomCount.remove(1);
			}
		}
		
		// Do all the remaining elements in order
		for (int i = 0; i < nElements; i++) { 
			int z = alphabeticElements[i];
			if (atomCount.containsKey(z))
				hall += elementSymbol[z] + prettifyDouble(atomCount.get(z), useSubscripts);
		}
		if (hall.length() != 0)
			return hall;
		else
			return (useSubscripts) ? "-" : "";
	}
	
	// Format the atom multiplicity as nicely as we can
	private String prettifyDouble(double n, boolean useSubscripts) {
		if (n == 1.0)
			return "";
		
		String number = (n == Math.floor(n)) ? Integer.toString((int) n) :  Double.toString(n);
		String normalNumbers = "0123456789.";
		String subscriptNumbers = "₀₁₂₃₄₅₆₇₈₉.";
		
		if (useSubscripts)
			for (int i = 0; i < normalNumbers.length(); i++ )
				number = number.replace(normalNumbers.charAt(i), subscriptNumbers.charAt(i));
		
		return number;
	}
	
	// Euclid's algorithm for GCD, recursively applied to a List of ints 
	private int greatestCommonDivisor(List<Integer> values) {
		if (values.size() < 1) return 1;
		while (values.size() > 1) {
			int currentSize = values.size(); // is at least 2
			int a = values.remove(currentSize - 1);
			int b = values.remove(currentSize - 2);
			values.add(greatestCommonDivisor(a, b));
		}
		return values.get(0); // Return the one element
	}
	
	// Euclid's algorithm for GCD
	private int greatestCommonDivisor(int a, int b) {
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}
}				