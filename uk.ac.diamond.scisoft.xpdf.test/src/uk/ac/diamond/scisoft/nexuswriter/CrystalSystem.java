/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.nexuswriter;

import java.util.ArrayList;
import java.util.List;

/**
 * A class providing the seven crystal systems
 * <p>
 * This class provides information on the 7 crystal systems, plus an eighth
 * pseudo-system to cover the rhombohedral basis of trigonal space groups. 
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class CrystalSystem {

	private int number;
	private static CrystalSystem[] systems;
	static final int nSystems = 8; // 7 crystal systems, plus rhombohedral space groups with a rhombohedral basis
	
	/**
	 * Gets the crystal system with a given number.
	 * <p>
	 * A static function that retuns one of the 8 singleton objects
	 * representing the crystal systems. Follows the usual IUCr ordering
	 * (tricilinic, monoclinic, tetragonal, trigonal (hexagonal basis),
	 * hexagonal, cubic), followed by the pseudo-system for trigonal space
	 * groups on a rhombohedral basis.
	 * @param systemOrdinal
	 * 					ordinal of the system to get (zero-based)
	 * @return the object representing that crystal system.
	 */
	public static CrystalSystem get(int systemOrdinal) {
		if (systems == null || systems.length < nSystems) {
			generateSystems();
		}
		return systems[systemOrdinal];
	}
		
	/**
	 * Gets the name of the crystal system.
	 * <p>
	 * The names of the systems are "Triclinic", "Monoclinic", "Orthorhombic",
	 * "Tetragonal", "Trigonal ⬡", "Hexagonal", "Cubic", "Trigonal ⋄". The
	 * symbols after the trigonal names are intended to show the basis on
	 * which the unit cell is defined.
	 * @return the usual name of the crystal system, with a suffix to denote
	 * the basis of trigonal space groups.
	 */
	public String getName() {
		return (number >= 0 && number < nSystems) ? names[number] : "Unknown";
	}
	
	/**
	 * Gets the ordinal number associated with the crystal system.
	 * @return ordinal assigned to the crystal system.
	 */
	public int getOrdinal() {
		return number;
	}
	
	/**
	 * Gets the names of all the crystal systems.
	 * @return 
	 */
	public static String[] getNames() {
		return names;
	}
	
	/**
	 * Returns a List of all the space groups with this crystal system.
	 * @return a List of all the objects representing the space groups with 
	 * this crystal system.
	 */
	public List<XPDFSpaceGroup> getGroups() {
		List<XPDFSpaceGroup> groupList = new ArrayList<XPDFSpaceGroup>();
		for (int group = lowestGroups[number]; group <= highestGroups[number]; group++)
			groupList.add(XPDFSpaceGroup.get(group));
		return groupList;
	}
	
	/**
	 * Returns the indices of the axis that defines the length of this side of the unit cell.
	 * <p>
	 * For unit cells of the tetragonal, trigonal, hexagonal and cubic systems,
	 * some edges of the unit cell are defined to be equal to others. The
	 * return value of this method allows this to be determined. The triclinic
	 * system returns {0,1,2}, as each axis defines itself. The cubic system
	 * returns {0,0,0} as all three axes are defined by the first. The trigonal
	 * system and pseudo-system return the correct values for their bases. 
	 * @return indices of the lements used to define each unit cell axis.
	 */
	public int[] getAxisIndices() {
		switch (nFreeAxes[number]) {
		case 3:
			return new int[]{0,1,2};
		case 2:
			return new int[]{0,0,2};
		case 1:
			return new int[]{0,0,0};
		default:
			return new int[]{-1,-1,-1};
		}		
	}
	
	/**
	 * Gets the angles between the planes of the unit cell.
	 * <p>
	 * For crystal systems with fixed angles (all but triclinic and trigonal
	 * with a rhombohedral basis), the entry in the array gives the angle in
	 * degrees. For free angles, the value is negative, and the absolute value
	 * gives 1 more than the dimension which defines this angle.
	 * @return The angle of the vertex of the unit cell, or a negative number
	 * defining the index which defines the angle.
	 */
	public int[] getFixedAngles() {
		return fixedAngles[number];
	}
	
	private static void generateSystems() {
		systems = new CrystalSystem[nSystems]; // 7 crystal systems plus rhombohedral bases
		for (int iSystem = 0; iSystem < nSystems ; iSystem++) {
			CrystalSystem system = new CrystalSystem();
			system.number = iSystem;
			systems[iSystem] = system;
		}
	}
	
	public final static int[] lowestGroups = {1, 3, 16, 75, 143, 168, 195, 231};
	public final static int[] highestGroups = {2, 15, 74, 142, 167, 194, 230, 237};
	private final static String[] names = {"Triclinic", "Monoclinic", "Orthorhombic",
		"Tetragonal", "Trigonal ⬡", "Hexagonal", "Cubic", "Trigonal ⋄"};
	private final static int[] nFreeAxes = {3, 3, 3, 2, 2, 2, 1, 1};
	private final static int[][] fixedAngles = {
		new int[] {-1, -2, -3},
		new int[] {90, -2, 90},
		new int[] {90, 90, 90},
		new int[] {90, 90, 90},
		new int[] {90, 90, 120},
		new int[] {90, 90, 120},
		new int[] {90, 90, 90},
		new int[] {-1, -1, -1}};
		
}
