/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.nexuswriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Parameterizes the properties of crystal space groups.
 * <p>
 * Parameterizes the properties of the 230 space groups, and 7 additional
 * pseudo-groups parameterizing the rhombohedral basis for those trigonal space
 * groups.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
// TODO: give the rhombohedral based pseudo-groups their actual number at the interface.
class XPDFSpaceGroup {

	private int number;
	private static XPDFSpaceGroup[] groups;
	static final Integer[] rhombGroups = {146, 148, 155, 160, 161, 166, 167};
	static final int nGroupsFedorov = 230;
	static final int nGroups = nGroupsFedorov + rhombGroups.length;
	private static Map<Integer, Integer> hexagonalRhombohedralGroupMap = null;
	private WyckoffData wyckoffData;
	
	public static final String allWyckoffLetters = "abcdefghijklmnopqrstuvwxyzα"; 
	
	private static Pattern spacePattern = null;
	final static int base10 = 10;
	private static final String normalNumbers = "0123456789.";
	private static final String subscriptNumbers = "₀₁₂₃₄₅₆₇₈₉.";
	private static Pattern[] subscriptPatterns = null;
	private static Pattern overbarPattern = null;
	private static Pattern trueMinusPattern = null;
	private static Pattern hyphenMinusPattern = null;
	

	
	private XPDFSpaceGroup() {
	}
	
	/**
	 * Gets the space group with a given number.
	 * @param groupNumber
	 * 					number of the space group following the IUCr.
	 * @return the object representing that space group
	 */
	public static XPDFSpaceGroup get(int groupNumber) {
		if (groups == null) {
			generateGroups();
		}
		// Watch out for off by 1 errors
		return groups[groupNumber];
	}
	
	public static XPDFSpaceGroup get(String name) {
		// search long names, stripping spaces, converting subscripts to ASCII numerals, and overbars to leading hyphen-minuses
		String normalizedName = simplifyLongName(name);
		for (int iLongName = 1; iLongName < names.length; iLongName ++) {
			String normalizedLongName = simplifyLongName(names[iLongName]);
			if (normalizedLongName.equals(normalizedName))
				return get(iLongName);
		}
		
		normalizedName = simplifyShortName(name);
		for (int iShortName = 1; iShortName < shortNames.length; iShortName++) {
			String normalizedShortName = simplifyShortName(shortNames[iShortName]);
			if (normalizedShortName.equals(normalizedName))
				return get(iShortName);
		}
		// we have got here without finding a valid group. Has the writer of
		// the file forgotten to include a minus sign? Let's see. Only check
		// the short names in this case.
		String deminusedName = simplifyShortName(stripMinuses(name));
		for (int iShortName = 1; iShortName < shortNames.length; iShortName++) {
			String normalizedShortName = simplifyShortName(stripMinuses(shortNames[iShortName]));
			if (normalizedShortName.equals(deminusedName))
				return get(iShortName);
		}
		
		// return the null group
		return get(0);
	}
	
	/**
	 * Gets the number of a space group.
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Gets the symbolic name of the space group.
	 * @return the Hermann-Mauguin symbol of the space group.
	 */
	public String getName() {
		return names[number];
	}
	
	/**
	 * Gets the typical short form of the space group.
	 * @return the short-form Hermann-Maugin symbol of the space group.
	 */
	public String getShortName() {
		return shortNames[number];
	}
	
	/**
	 * Gets the object representing the crystal system of which this space
	 * group is a member 
	 * @return the {@link CrystalSystem} of the space group.
	 */
	public CrystalSystem getSystem() {
		if (number < 1)
			return null; // 0 is undefined
		else
			for (int iGroup = 0; iGroup < CrystalSystem.nSystems; iGroup++) {
				if (number <= CrystalSystem.highestGroups[iGroup])
					return CrystalSystem.get(iGroup);
			}
			return null; // system not found
	}
	
	/**
	 * Gets the symmetry operations of the space group.
	 * <p>
	 * Returns a list of Strings in the IUCr format describing all the
	 * transformations for an atom in the general position for the space group.
	 * The list is ordered such that the identity operation is always first.
	 * @return a list of Strings describing the symmetry operations
	 */
	public String[] getSymmetryOperations() {
		return allSymOps[number];
	}
	
	// Simplify the long form of a Hermann-Maugin name. Strip out spaces,
	// convert subscripts to ASCII numerals, convert overbars to leading
	// hyphen-minuses, convert Unicode minuses to hyphen-minuses
	private static String simplifyLongName(String name) {

		if (spacePattern == null)
			compilePatterns();

		// strip out all spaces
		String stripSpaces = simplifyShortName(name);
		// translate subscripts to ASCII
		String desubscript = stripSpaces;
		for (int i = 0; i < base10; i++)
			desubscript = subscriptPatterns[i].matcher(desubscript).replaceAll(Integer.toString(i));
		// convert overbars to hyphen-minuses
		String deoverbar = overbarPattern.matcher(desubscript).replaceAll("-$1");
		// convert real minuses to (ugh) hyphen-minuses
		String hyphenate = trueMinusPattern.matcher(deoverbar).replaceAll("-");
		
		return hyphenate;
	}
	
	// Simplify the short form of a Hermann-Maugin name. Strip out spaces
	private static String simplifyShortName(String name) {
		if (spacePattern == null) 
			compilePatterns();
		// strip out all spaces
		String stripSpaces = spacePattern.matcher(name).replaceAll("");
		
		return stripSpaces;
	}

	private static String stripMinuses(String name) {
		if (spacePattern == null)
			compilePatterns();
		return hyphenMinusPattern.matcher(name).replaceAll("");
	}
	
	private static void compilePatterns() {
		spacePattern = Pattern.compile(" +");
		subscriptPatterns = new Pattern[base10];
		for (int i = 0; i < base10; i++)
			subscriptPatterns[i] = Pattern.compile(Character.toString(subscriptNumbers.charAt(i)));
		overbarPattern = Pattern.compile("(\\p{Digit})\u0305");
		trueMinusPattern = Pattern.compile("\u2212");
		hyphenMinusPattern = Pattern.compile("-");
	}
	
	private static void generateGroups() {
		groups = new XPDFSpaceGroup[nGroups+1];
		for (int newGroup = 0; newGroup <= nGroups; newGroup++) {
			XPDFSpaceGroup group = new XPDFSpaceGroup();
			group.number = newGroup;
			group.wyckoffData = new WyckoffData(newGroup);
			groups[newGroup] = group;
		}
		
		// Also generate the mapping between rhombohedral and hexagonal groups
		hexagonalRhombohedralGroupMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < nGroups-nGroupsFedorov; i++) {
			hexagonalRhombohedralGroupMap.put(rhombGroups[i], nGroupsFedorov+1+i);
			hexagonalRhombohedralGroupMap.put(nGroupsFedorov+1+i, rhombGroups[i]);
		}
	}
	
	/**
	 * Returns the rhombohedral equivalent space group.
	 * <p>
	 * If this is the space group has a rhombohedral lattice, then return the
	 * pseudo-space group representing its rhombohedral form. Otherwise, return this.
	 * @return
	 * 		the rhombohedral equivalent pseudo-space group, if it exists, else this.
	 */
	public XPDFSpaceGroup asRhombohedral() {
		if (this.hasRhombohedral()) {
			return groups[hexagonalRhombohedralGroupMap.get(number)];
		}
		return this;
	}
	
	/**
	 * Returns the hexagonal equivalent space group.
	 * <p>
	 * If this is a pseudo-space group of a rhombohedral lattice, then return the
	 * space group representing its hexagonal form. Otherwise, return this.
	 * @return
	 * 		the hexagonal equivalent space group, if it applicable, else this.
	 */
	public XPDFSpaceGroup asHexagonal() {
		if (this.isRhombohedral()) {
			return groups[hexagonalRhombohedralGroupMap.get(number)];
		}
		return this;
	}
	
	/**
	 * Returns whether the space group has an alternative rhombohedral basis
	 * @return
	 * 		does this space group have a rhombohedral basis, and is it itself in the hexagonal basis
	 */
	public boolean hasRhombohedral() {
		return Arrays.asList(rhombGroups).contains(this.number); 
	}
	
	/**
	 * Returns whether the space group is in a rhombohedral basis
	 * @return
	 * 		Returns whether the space group is a trigonal group represented in a rhombohedral basis 
	 */
	public boolean isRhombohedral() {
		return this.number > nGroupsFedorov;
	}
	
	public int getNWyckoffLetters() {
		return wyckoffData.getNLetters();
	}
	
	public int getSiteMultiplicity(String wyckoffLetter) {
		return wyckoffData.getMultiplicity(wyckoffLetter);
	}
	
	private static class WyckoffData {
		private final int nLetters;
		private int[] multiplicities;
		
		public WyckoffData(int spaceGroup) {
			nLetters = getNWyckoff(spaceGroup);
			multiplicities = getMultiplicity(spaceGroup);
		}
		
		public int getMultiplicity(String letter) {
			int letterIndex = indexFromLetter(letter);
			return multiplicities[letterIndex-1];
		}
		
		public int getNLetters() {
			return nLetters;
		}
		
		private static int indexFromLetter(String letter) {
			return ("?"+allWyckoffLetters).indexOf(letter.charAt(0));
		}
		
		private static String letterFromIndex(int index) {
			return Character.toString(("?"+allWyckoffLetters).charAt(index));
		}
		
		private static int getNWyckoff(int spaceGroup) {
			int[] nSpecial= {0, // null space group, for indexing
					 0, 8, 4, 0, 2, 2, 0, 1, 0, 14,
					 5, 9, 6, 4, 5, 20, 4, 2, 0, 2,
					 11, 10, 10, 3, 8, 2, 4, 3, 0, 2,
					 1, 2, 0, 2, 5, 1, 3, 5, 3, 2,
					 1, 4, 1, 4, 2, 2, 26, 12, 17, 12,
					 11, 4, 8, 5, 8, 4, 4, 7, 6, 3,
					 2, 3, 7, 6, 17, 12, 14, 8, 15, 7,
					 14, 10, 5, 9, 3, 0, 3, 0, 2, 1,
					 7, 6, 11, 10, 6, 6, 8, 5, 15, 6,
					 3, 1, 15, 6, 3, 1, 10, 6, 6, 3,
					 4, 3, 3, 2, 5, 2, 4, 3, 2, 1,
					 14, 13, 5, 4, 11, 9, 8, 8, 9, 8,
					 9, 4, 20, 13, 13, 10, 11, 8, 10, 6,
					 17, 15, 10, 13, 8, 10, 7, 9, 14, 12,
					 8, 6, 3, 0, 0, 1, 6, 5, 11, 6,
					 2, 2, 2, 2, 5, 4, 3, 3, 2, 2,
					 1, 11, 8, 9, 6, 8, 5, 3, 0, 0,
					 2, 2, 2, 11, 11, 8, 13, 2, 2, 10,
					 10, 8, 5, 3, 3, 3, 14, 11, 11, 8,
					 17, 12, 11, 11, 9, 7, 5, 1, 2, 11,
					 7, 8, 6, 7, 3, 4, 10, 12, 9, 7,
					 9, 4, 4, 8, 9, 8, 7, 8, 7, 4,
					 13, 8, 11, 11, 11, 9, 8, 7, 11, 7, // 230 real space groups
					 1, 5, 5, 2, 1, 8, 5}; // The rhombohedral pseudo-groups have the same number of special positions as the hexagonal representation
			return nSpecial[spaceGroup] + 1;
		}
		
		private static int[] getMultiplicity(int spaceGroup) {
			int[][] multiplicities = {
					{0}, // null group for indexing
					// 0-10
					{1},
					{1,1,1,1,1,1,1,1,2},
					{1,1,1,1,2},
					{2},
					{2,2,4},
					{1,1,2},
					{2},
					{2, 4},
					{4},
					{1,1,1,1,1,1,1,1,2,2,2,2,2,2,4},
					//11-20
					{2,2,2,2,2,4},
					{2,2,2,2,4,4,4,4,4,8},
					{2,2,2,2,2,2,4},
					{2,2,2,2,4},
					{4,4,4,4,4,8},
					{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,4},
					{2,2,2,2,4},
					{2,2,4},
					{4},
					{4,4,8},
					//21-30
					{2,2,2,2,4,4,4,4,4,4,4,8},
					{4,4,4,4,8,8,8,8,8,8,16},
					{2,2,2,2,4,4,4,4,4,4,8},
					{4,4,4,8},
					{1,1,1,1,2,2,2,2,4},
					{2,2,4},
					{2,2,2,2,4},
					{2,2,2,4},
					{4},
					{2,2,4},
					//31-40
					{2,4},
					{2,2,4},
					{4},
					{2,2,4},
					{2,2,4,4,4,8},
					{4,8},
					{4,4,4,8},
					{2,2,4,4,4,8},
					{4,4,4,8},
					{4,4,8},
					//41-50
					{4,8},
					{4,8,8,8,16},
					{8,16},
					{2,2,4,4,8},
					{4,4,8},
					{4,4,8},
					{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,4,4,4,4,4,4,8},
					{2,2,2,2,4,4,4,4,4,4,4,4,8},
					{2,2,2,2,2,2,2,2,4,4,4,4,4,4,4,4,4,8},
					{2,2,2,2,4,4,4,4,4,4,4,4,8},
					//51-60
					{2,2,2,2,2,2,4,4,4,4,4,8},
					{4,4,4,4,8},
					{2,2,2,2,4,4,4,4,8},
					{4,4,4,4,4,8},
					{2,2,2,2,4,4,4,4,8},
					{4,4,4,4,8},
					{4,4,4,4,8},
					{2,2,2,2,4,4,4,8},
					{2,2,4,4,4,4,8},
					{4,4,4,8},
					//61-70
					{4,4,8},
					{4,4,4,8},
					{4,4,4,8,8,8,8,16},
					{4,4,8,8,8,8,16},
					{2,2,2,2,4,4,4,4,4,4,4,4,8,8,8,8,8,16},
					{4,4,4,4,4,4,4,8,8,8,8,8,8,16},
					{4,4,4,4,4,4,4,8,8,8,8,8,8,8,16},
					{4,4,8,8,8,8,8,8,16},
					{4,4,8,8,8,8,8,8,8,16,16,16,16,16,16,32},
					{8,8,16,16,16,16,16,32},
					//71-80
					{2,2,2,2,4,4,4,4,4,4,8,8,8,8,16},
					{4,4,4,4,8,8,8,8,8,8,16},
					{8,8,8,8,8,16},
					{4,4,4,4,4,8,8,8,8,16},
					{1,1,2,4},
					{4},
					{2,2,2,4},
					{4},
					{2,4,8},
					{4,8},
					//81-90
					{1,1,1,1,2,2,2,4},
					{2,2,2,2,4,4,8},
					{1,1,1,1,2,2,2,2,4,4,4,8},
					{2,2,2,2,2,2,4,4,4,4,8},
					{2,2,2,4,4,4,8},
					{2,2,4,4,4,4,8},
					{2,2,4,4,4,8,8,8,16},
					{4,4,8,8,8,16},
					{1,1,1,1,2,2,2,2,4,4,4,4,4,4,4,8},
					{2,2,2,4,4,4,8},
					//91-100
					{4,4,4,8},
					{4,8},
					{2,2,2,2,2,2,4,4,4,4,4,4,4,4,4,8},
					{2,2,4,4,4,4,8},
					{4,4,4,8},
					{4,8},
					{2,2,4,4,4,8,8,8,8,8,16},
					{4,4,8,8,8,16},
					{1,1,2,4,4,4,8},
					{2,2,4,8},
					//101-110
					{2,2,4,4,8},
					{2,4,4,8},
					{2,2,4,8},
					{2,4,8},
					{2,2,2,4,4,8},
					{4,4,8},
					{2,4,8,8,16},
					{4,4,8,16},
					{4,8,16},
					{8,16},
					//111-120
					{1,1,1,1,2,2,2,2,4,4,4,4,4,4,8},
					{2,2,2,2,2,2,4,4,4,4,4,4,4,8},
					{2,2,2,4,4,8},
					{2,2,4,4,8},
					{1,1,1,1,2,2,2,4,4,4,4,8},
					{2,2,2,2,4,4,4,4,4,8},
					{2,2,2,2,4,4,4,4,8},
					{2,2,2,2,4,4,4,4,8},
					{2,2,2,2,4,4,8,8,8,16},
					{4,4,4,4,8,8,8,8,16},
					//121-130
					{2,2,4,4,4,8,8,8,8,16},
					{4,4,8,8,16},
					{1,1,1,1,2,2,2,2,4,4,4,4,4,4,4,8,8,8,8,8,16},
					{2,2,2,2,4,4,4,4,8,8,8,8,8,16},
					{2,2,2,2,4,4,4,4,8,8,8,8,8,16},
					{2,2,4,4,4,8,8,8,8,8,16},
					{2,2,2,2,4,4,4,4,8,8,8,16},
					{2,2,4,4,4,8,8,8,16},
					{2,2,2,4,4,4,8,8,8,8,16},
					{4,4,4,8,8,8,16},
					//131-140
					{2,2,2,2,2,2,4,4,4,4,4,4,4,8,8,8,8,16},
					{2,2,2,2,4,4,4,4,4,4,8,8,8,8,8,16},
					{4,4,4,4,8,8,8,8,8,8,16},
					{2,2,4,4,4,4,4,8,8,8,8,8,8,16},
					{4,4,4,4,8,8,8,8,16},
					{2,2,4,4,4,4,4,8,8,8,16},
					{2,2,4,4,8,8,8,16},
					{4,4,4,4,4,8,8,8,8,16},
					{2,2,4,4,4,8,8,8,8,8,16,16,16,16,32},
					{4,4,4,4,8,8,8,8,16,16,16,16,32},
					//141-150
					{4,4,8,8,8,16,16,16,32},
					{8,8,16,16,16,16,32},
					{1,1,1,3},
					{3},
					{3},
					{3,9},
					{1,1,2,2,3,3,6},
					{3,3,6,9,9,18},
					{1,1,1,1,1,1,2,2,2,3,3,6},
					{1,1,2,2,3,3,6},
					//151-160
					{3,3,6},
					{3,3,6},
					{3,3,6},
					{3,3,6},
					{3,3,6,9,9,18},
					{1,1,1,3,6},
					{1,2,3,6},
					{2,2,2,6},
					{2,2,6},
					{3,9,18},
					//161-170
					{6,18},
					{1,1,2,2,2,3,3,4,6,6,6,12},
					{2,2,2,2,4,4,6,6,12},
					{1,1,2,2,3,3,6,6,6,12},
					{2,2,4,4,6,6,12},
					{3,3,6,9,9,18,18,18,36},
					{6,6,12,18,18,36},
					{1,2,3,6},
					{6},
					{6},
					//171-180
					{3,3,6},
					{3,3,6},
					{2,2,6},
					{1,1,1,1,1,1,2,2,2,3,3,6},
					{1,1,2,2,2,3,3,4,6,6,6,12},
					{2,2,2,2,4,4,6,6,12},
					{1,1,2,2,2,3,3,4,6,6,6,6,6,12},
					{6,6,12},
					{6,6,12},
					{3,3,3,3,6,6,6,6,6,6,12},
					//181-190
					{3,3,3,3,6,6,6,6,6,6,12},
					{2,2,2,2,4,4,6,6,12},
					{1,2,3,6,6,12},
					{2,4,6,12},
					{2,4,6,12},
					{2,2,6,12},
					{1,1,1,1,1,1,2,2,2,3,3,6,6,6,12},
					{2,2,2,2,2,2,4,4,4,6,6,12},
					{1,1,2,2,2,3,3,4,6,6,6,12},
					{2,2,2,2,4,4,6,6,12},
					//191-200
					{1,1,2,2,2,3,3,4,6,6,6,6,6,12,12,12,12,24},
					{2,2,4,4,4,6,6,8,12,12,12,12,24},
					{2,2,4,4,4,6,6,8,12,12,12,24},
					{2,2,2,2,4,4,6,6,12,12,12,24},
					{1,1,3,3,4,6,6,6,6,12},
					{4,4,4,4,16,24,24,48},
					{2,6,8,12,12,24},
					{4,12},
					{8,12,24},
					{1,1,3,3,6,6,6,6,8,12,12,24},
					//201-210
					{2,4,4,6,8,12,12,24},
					{4,4,8,24,24,32,48,48,96},
					{8,8,16,32,48},
					{2,6,8,12,12,16,24},
					{4,4,8,24},
					{8,8,16,24,48},
					{1,1,3,3,6,6,8,12,12,12,24},
					{2,4,4,6,6,6,8,12,12,12,12,12,24},
					{4,4,8,24,24,32,48,48,48,96},
					{8,8,16,16,32,48,48,96},
					//211-220
					{2,6,8,12,12,16,24,24,24,48},
					{4,4,8,12,24},
					{4,4,8,12,24},
					{8,8,12,12,16,24,24,24,48},
					{1,1,3,3,4,6,6,12,12,24},
					{4,4,4,4,16,24,24,48,96},
					{2,6,8,12,12,24,24,48},
					{2,6,6,6,8,12,12,12,24},
					{8,8,24,24,32,48,48,96},
					{12,12,16,24,48},
					//221-230
					{1,1,3,3,6,6,8,12,12,12,24,24,24,48},
					{2,6,8,12,12,16,24,24,48},
					{2,6,6,6,8,12,12,12,16,24,24,48},
					{2,4,4,6,8,12,12,24,24,24,24,48},
					{4,4,8,24,24,32,48,48,48,96,96,192},
					{8,8,24,24,48,48,64,96,96,192},
					{8,8,16,16,32,48,96,96,192},
					{16,32,32,48,64,96,96,192},
					{2,6,8,12,12,16,24,24,48,48,48,96},
					{16,16,24,24,32,48,48,96},
					// Rhombohedral pseudo-groups (231-237)
					{1,3},
					{1,1,2,3,3,6},
					{1,1,2,3,3,6},
					{1,3,6},
					{2,6},
					{1,1,2,3,3,6,6,6,12},
					{2,2,4,6,6,12}
					};
			return multiplicities[spaceGroup];
		}
	}
	
	private static final String[] names = {
		"-",
		"P 1", // Triclinic 1-2
		"P 1̅",
		"P 1 2 1", // Monoclinic 3-15
		"P 1 2₁ 1",
		"C 1 2 1",
		"P 1 m 1",
		"P 1 c 1",
		"C 1 m 1",
		"C 1 c 1",
		"P 1 2/m 1",
		"P 1 2₁/m 1",
		"C 1 2/m 1",
		"P 1 2/c 1",
		"P 1 2₁/c 1",
		"C 1 2/c 1",
		"P 2 2 2", // Orthorhombic 16-74
		"P 2 2 2₁",
		"P 2₁ 2₁ 2",
		"P 2₁ 2₁ 2₁",
		"C 2 2 2₁",
		"C 2 2 2",
		"F 2 2 2",
		"I 2 2 2",
		"I 2₁ 2₁ 2₁",
		"P m m 2",
		"P m c 2₁",
		"P c c 2",
		"P m a 2",
		"P c a 2₁",
		"P n c 2",
		"P m n 2₁",
		"P b a 2",
		"P n a 2₁",
		"P n n 2",
		"C m m 2",
		"C m c 2₁",
		"C c c 2",
		"A m m 2",
		"A b m 2",
		"A m a 2",
		"A b a 2",
		"F m m 2",
		"F d d 2",
		"I m m 2",
		"I b a 2",
		"I m a 2",
		"P 2/m 2/m 2/m",
		"P 2/n 2/n 2/n",
		"P 2/c 2/c 2/m",
		"P 2/b 2/a 2/n",
		"P 2₁/m 2/m 2/a",
		"P 2/n 2₁/n 2/a",
		"P 2/m 2/n 2₁/a",
		"P 2₁/c 2/c 2/a",
		"P 2₁/b 2₁/a 2/m",
		"P 2₁/c 2₁/c 2/n",
		"P 2/b 2₁/c 2₁/m",
		"P 2₁/n 2₁/n 2/m",
		"P 2₁/m 2₁/m 2/n",
		"P 2₁/b 2/c 2₁/n",
		"P 2₁/b 2₁/c 2₁/a",
		"P 2₁/n 2₁/m 2₁/a",
		"C 2/m 2/c 2₁/m",
		"C 2/m 2/c 2₁/a",
		"C 2/m 2/m 2/m",
		"C 2/c 2/c 2/m",
		"C 2/m 2/m 2/e",
		"C 2/c 2/c 2/e",
		"F 2/m 2/m 2/m",
		"F 2/d 2/d 2/d",
		"I 2/m 2/m 2/m",
		"I 2/b 2/a 2/m",
		"I 2/b 2/c 2/a",
		"I 2/m, 2/m 2/a",
		"P 4", // Tetragonal 75-142
		"P 4₁",
		"P 4₂",
		"P 4₃",
		"I 4",
		"I 4₁",
		"P 4̅",
		"I 4̅",
		"P 4/m",
		"P 4₂/m",
		"P 4/n",
		"P 4₂/n",
		"I 4/m",
		"I 4₁/a",
		"P 4 2 2",
		"P 4 2₁ 2",
		"P 4₁ 2 2",
		"P 4₁ 2₁ 2",
		"P 4₂ 2 2",
		"P 4₂ 2₁ 2",
		"P 4₃ 2 2",
		"P 4₃ 2₁ 2",
		"I 4 2 2",
		"I 4₁ 2 2",
		"P 4 m m",
		"P 4 b m",
		"P 4₂ c m",
		"P 4₂ n m",
		"P 4 c c",
		"P 4 n c",
		"P 4₂ m c",
		"P 4₂ b c",
		"I 4 m m",
		"I 4 c m",
		"I 4₁ m d",
		"I 4₁ c d",
		"P 4̅ 2 m",
		"P 4̅ 2 c",
		"P 4̅ 2₁ m",
		"P 4̅ 2₁ c",
		"P 4̅ m 2",
		"P 4̅ c 2",
		"P 4̅ b 2",
		"P 4̅ n 2",
		"I 4̅ m 2",
		"I 4̅ c 2",
		"I 4̅ 2 m",
		"I 4̅ 2 d",
		"P 4/m 2/m 2/m",
		"P 4/m 2/c 2/c",
		"P 4/n 2/b 2/m",
		"P 4/n 2/n 2/c",
		"P 4/m 2₁/b 2/m",
		"P 4/m 2₁/n 2/c",
		"P 4/n 2₁/m 2/m",
		"P 4/n 2₁/c 2/c",
		"P 4₂/m 2/m 2/c",
		"P 4₂/m 2/c 2/m",
		"P 4₂/m 2/b 2/c",
		"P 4₂/n 2/n 2/m",
		"P 4₂/m 2₁/b 2/c",
		"P 4₂/m 2₁/n 2/m",
		"P 4₂/n 2₁/m 2/c",
		"P 4₂/n 2₁/c 2/m",
		"I 4/m 2/m 2/m",
		"I 4/m 2/c 2/m",
		"I 4₁/a 2/m 2/d",
		"I 4₁/a 2/c 2/d",
		"P 3", // Trigonal 143-167
		"P 3₁",
		"P 3₂",
		"R 3",
		"P 3̅",
		"R 3̅",
		"P 3 1 2",
		"P 3 2 1",
		"P 3₁ 1 2",
		"P 3₁ 2 1",
		"P 3₂ 1 2",
		"P 3₂ 2 1",
		"R 3 2",
		"P 3 m 1",
		"P 3 1 m",
		"P 3 c 1",
		"P 3 1 c",
		"R 3 m",
		"R c 3",
		"P 3̅ 1 2/m",
		"P 3̅ 1 2/c",
		"P 3̅ 2/m 1",
		"P 3̅ 2/c 1",
		"R 3̅ 2/m",
		"R 3̅ 2/c",
		"P 6", // Hexagonal 168-194
		"P 6₁",
		"P 6₅",
		"P 6₂",
		"P 6₄",
		"P 6₃",
		"P 6̅",
		"P 6/m",
		"P 6₃/m",
		"P 6 2 2",
		"P 6₁ 2 2",
		"P 6₅ 2 2",
		"P 6₂ 2 2",
		"P 6₄ 2 2",
		"P 6₃ 2 2",
		"P 6 m m",
		"P 6 c c",
		"P 6₃ c m",
		"P 6₃ m c",
		"P 6̅ m 2",
		"P 6̅ c 2",
		"P 6̅ 2 m",
		"P 6̅ 2 c",
		"P 6/m 2/m 2/m",
		"P 6/m 2/c 2/c",
		"P 6₃/m 2/c 2/m",
		"P 6₃/m 2/m 2/c",
		"P 2 3", // Cubic 195-230
		"F 2 3",
		"I 2 3",
		"P 2₁ 3",
		"I 2₁ 3",
		"P 2/m 3̅",
		"P 2/n 3̅",
		"F 2/m 3̅",
		"F 2/d 3̅",
		"I 2/m 3̅",
		"P 2₁/a 3̅",
		"I 2₁/a 3̅",
		"P 4 3 2",
		"P 4₂ 3 2",
		"F 4 3 2",
		"F 4₁ 3 2",
		"I 4 3 2",
		"P 4₃ 3 2",
		"P 4₁ 3 2",
		"I 4₁ 3 2",
		"P 4̅ 3 m",
		"F 4̅ 3 m",
		"I 4̅ 3 m",
		"P 4̅ 3 n",
		"F 4̅ 3 c",
		"I 4̅ 3 d",
		"P 4/m 3̅ 2/m",
		"P 4/n 3̅ 2/n",
		"P 4₂/m 3̅ 2/n",
		"P 4₂/n 3̅ 2/m",
		"F 4/m 3̅ 2/m",
		"F 4/m 3̅ 2/c",
		"F 4₁/d 3̅ 2/m",
		"F 4₁/d 3̅ 2/c",
		"I 4/m 3̅ 2/m",
		"I 4₁/a 3̅ 2/d",
		// Trigonal, rhombohedral basis 146, 148, 155, 160, 161, 166, 167
		"R 3:R",
		"R 3̅:R",
		"R 3 2:R",
		"R 3 m:R",
		"R c 3:R",
		"R 3̅ 2/m:R",
		"R 3̅ 2/c:R"
	};

	private static final String[] shortNames = {
		"-",
		"P1", 
		"P-1",
		"P2",
		"P21",
		"C2",
		"Pm",
		"Pc",
		"Cm",
		"Cc",
		"P2/m",
		"P21/m",
		"C2/m",
		"P2/c",
		"P21/c",
		"C2/c",
		"P222",
		"P2221",
		"P21212",
		"P212121",
		"C2221",
		"C222",
		"F222",
		"I222",
		"I212121",
		"Pmm2",
		"Pmc21",
		"Pcc2",
		"Pma2",
		"Pca21",
		"Pnc2",
		"Pmn21",
		"Pba2",
		"Pna21",
		"Pnn2",
		"Cmm2",
		"Cmc21",
		"Ccc2",
		"Amm2",
		"Abm2",
		"Ama2",
		"Aba2",
		"Fmm2",
		"Fdd2",
		"Imm2",
		"Iba2",
		"Ima2",
		"Pmmm",
		"Pnnn",
		"Pccm",
		"Pban",
		"Pmma",
		"Pnna",
		"Pmna",
		"Pcca",
		"Pbam",
		"Pccn",
		"Pbcm",
		"Pnnm",
		"Pmmn",
		"Pbcn",
		"Pbca",
		"Pnma",
		"Cmcm",
		"Cmca",
		"Cmmm",
		"Cccm",
		"Cmme",
		"Ccce",
		"Fmmm",
		"Fddd",
		"Immm",
		"Ibam",
		"Ibca",
		"Imma",
		"P4",
		"P41",
		"P42",
		"P43",
		"I4",
		"I41",
		"P-4",
		"I-4",
		"P4/m",
		"P42/m",
		"P4/n",
		"P42/n",
		"I4/m",
		"I41/a",
		"P422",
		"P4212",
		"P4122",
		"P41212",
		"P4222",
		"P42212",
		"P4322",
		"P43212",
		"I422",
		"I4122",
		"P4mm",
		"P4bm",
		"P42cm",
		"P42nm",
		"P4cc",
		"P4nc",
		"P42mc",
		"P42bc",
		"I4mm",
		"I4cm",
		"I41md",
		"I41cd",
		"P-42m",
		"P-42c",
		"P-421m",
		"P-421c",
		"P-4m2",
		"P-4c2",
		"P-4b2",
		"P-4n2",
		"I-4m2",
		"I-4c2",
		"I-42m",
		"I-42d",
		"P4/mmm",
		"P4/mcc",
		"P4/nbm",
		"P4/nnc",
		"P4/mbm",
		"P4/mnc",
		"P4/nmm",
		"P4/ncc",
		"P42/mmc",
		"P42/mcm",
		"P42/nbc",
		"P42/nnm",
		"P42/mbc",
		"P42/mnm",
		"P42/nmc",
		"P42/ncm",
		"I4/mmm",
		"I4/mcm",
		"I41/amd",
		"I41/acd",
		"P3",
		"P31",
		"P32",
		"R3",
		"P-3",
		"R-3",
		"P312",
		"P321",
		"P3112",
		"P3121",
		"P3212",
		"P3221",
		"R32",
		"P3m1",
		"P31m",
		"P3c1",
		"P31c",
		"R3m",
		"R3c",
		"P-31m",
		"P-31c",
		"P-3m1",
		"P-3c1",
		"R-3m",
		"R-3c",
		"P6",
		"P61",
		"P65",
		"P62",
		"P64",
		"P63",
		"P-6",
		"P6/m",
		"P63/m",
		"P622",
		"P6122",
		"P6522",
		"P6222",
		"P6422",
		"P6322",
		"P6mm",
		"P6cc",
		"P63cm",
		"P63mc",
		"P-6m2",
		"P-6c2",
		"P-62m",
		"P-62c",
		"P6/mmm",
		"P6/mcc",
		"P63/mcm",
		"P63/mmc",
		"P23",
		"F23",
		"I23",
		"P213",
		"I213",
		"Pm-3",
		"Pn-3",
		"Fm-3",
		"Fd-3",
		"Im-3",
		"Pa-3",
		"Ia-3",
		"P432",
		"P4232",
		"F432",
		"F4132",
		"I432",
		"P4332",
		"P4132",
		"I4132",
		"P-43m",
		"F-43m",
		"I-43m",
		"P-43n",
		"F-43c",
		"I-43d",
		"Pm-3m",
		"Pn-3n",
		"Pm-3n",
		"Pn-3m",
		"Fm-3m",
		"Fm-3c",
		"Fd-3m",
		"Fd-3c",
		"Im-3m",
		"Ia-3d",
		"R3:R",
		"R-3:R",
		"R32:R",
		"R3m:R",
		"R3c:R",
		"R-3m:R",
		"R-3c:R",

	};

	private static String[][] allSymOps = {
			{"x,y,z"}, // the non-group still has the identity, I guess
			{"x,y,z"},
			{"x,y,z", "-x,-y,-z"},
			{"x,y,z", "-x,y,-z"},
			{"x,y,z", "-x,y+1/2,-z"},
			{"x,y,z", "-x,y,-z", "x+1/2,y+1/2,z", "-x+1/2,y+1/2,-z"},
			{"x,y,z", "x,-y,z"},
			{"x,y,z", "x,-y,z+1/2"},
			{"x,y,z", "x,-y,z", "x+1/2,y+1/2,z", "x+1/2,-y+1/2,z"},
			{"x,y,z", "x,-y,z+1/2", "x+1/2,y+1/2,z", "x+1/2,-y+1/2,z+1/2"},
			{"x,y,z", "-x,y,-z", "-x,-y,-z", "x,-y,z"},
			{"x,y,z", "-x,y+1/2,-z", "-x,-y,-z", "x,-y+1/2,z"},
			{"x,y,z", "-x,y,-z", "-x,-y,-z", "x,-y,z", "x+1/2,y+1/2,z", "-x+1/2,y+1/2,-z", "-x+1/2,-y+1/2,-z", "x+1/2,-y+1/2,z"},
			{"x,y,z", "-x,y,-z+1/2", "-x,-y,-z", "x,-y,z+1/2"},
			{"x,y,z", "-x,y+1/2,-z+1/2", "-x,-y,-z", "x,-y+1/2,z+1/2"},
			{"x,y,z", "-x,y,-z+1/2", "-x,-y,-z", "x,-y,z+1/2", "x+1/2,y+1/2,z", "-x+1/2,y+1/2,-z+1/2", "-x+1/2,-y+1/2,-z", "x+1/2,-y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z"},
			{"x,y,z", "-x,-y,z+1/2", "-x,y,-z+1/2", "x,-y,-z"},
			{"x,y,z", "-x,-y,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z"},
			{"x,y,z", "-x,-y,z+1/2", "-x,y,-z+1/2", "x,-y,-z", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-x+1/2,y,-z", "x,-y,-z+1/2"},
			{"x,y,z", "-x,-y,z", "x,-y,z", "-x,y,z"},
			{"x,y,z", "-x,-y,z+1/2", "x,-y,z+1/2", "-x,y,z"},
			{"x,y,z", "-x,-y,z", "x,-y,z+1/2", "-x,y,z+1/2"},
			{"x,y,z", "-x,-y,z", "x+1/2,-y,z", "-x+1/2,y,z"},
			{"x,y,z", "-x,-y,z+1/2", "x+1/2,-y,z", "-x+1/2,y,z+1/2"},
			{"x,y,z", "-x,-y,z", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "x+1/2,-y,z+1/2", "-x,y,z"},
			{"x,y,z", "-x,-y,z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x,-y,z+1/2", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "x,-y,z", "-x,y,z", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x,-y,z+1/2", "x,-y,z+1/2", "-x,y,z", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x,-y,z", "x,-y,z+1/2", "-x,y,z+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "x,-y,z", "-x,y,z", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "x,-y+1/2,z", "-x,y+1/2,z", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "x,-y,z+1/2", "-x,y,z+1/2"},
			{"x,y,z", "-x,-y,z", "x+1/2,-y,z", "-x+1/2,y,z", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y,z+1/2"},
			{"x,y,z", "-x,-y,z", "x,-y,z", "-x,y,z", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y,z+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x,-y,z", "x+1/4,-y+1/4,z+1/4", "-x+1/4,y+1/4,z+1/4", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "x+1/4,-y+3/4,z+3/4", "-x+1/4,y+3/4,z+3/4", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "x+3/4,-y+1/4,z+3/4", "-x+3/4,y+1/4,z+3/4", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "x+3/4,-y+3/4,z+1/4", "-x+3/4,y+3/4,z+1/4"},
			{"x,y,z", "-x,-y,z", "x,-y,z", "-x,y,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "x,-y,z+1/2", "-x,y,z+1/2"},
			{"x,y,z", "-x,-y,z", "x+1/2,-y,z", "-x+1/2,y,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-x+1/2,y,-z+1/2", "x,-y+1/2,-z+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "x+1/2,-y,z+1/2", "-x,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z+1/2", "x,-y,-z+1/2", "-x,-y,-z", "x,y,-z", "x,-y,z+1/2", "-x,y,z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-x+1/2,y,-z", "x,-y+1/2,-z", "-x,-y,-z", "x+1/2,y+1/2,-z", "x+1/2,-y,z", "-x,y+1/2,z"},
			{"x,y,z", "-x+1/2,-y,z", "-x,y,-z", "x+1/2,-y,-z", "-x,-y,-z", "x+1/2,y,-z", "x,-y,z", "-x+1/2,y,z"},
			{"x,y,z", "-x+1/2,-y,z", "-x+1/2,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "-x,-y,-z", "x+1/2,y,-z", "x+1/2,-y+1/2,z+1/2", "-x,y+1/2,z+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x,-y,-z", "-x,-y,-z", "x+1/2,y,-z+1/2", "x+1/2,-y,z+1/2", "-x,y,z"},
			{"x,y,z", "-x+1/2,-y,z", "-x,y,-z+1/2", "x+1/2,-y,-z+1/2", "-x,-y,-z", "x+1/2,y,-z", "x,-y,z+1/2", "-x+1/2,y,z+1/2"},
			{"x,y,z", "-x,-y,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "-x,-y,-z", "x,y,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-x,y+1/2,-z+1/2", "x+1/2,-y,-z+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "x,-y+1/2,z+1/2", "-x+1/2,y,z+1/2"},
			{"x,y,z", "-x,-y,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z", "-x,-y,-z", "x,y,-z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z"},
			{"x,y,z", "-x,-y,z", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "-x,-y,-z", "x,y,-z", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-x,y+1/2,-z", "x+1/2,-y,-z", "-x,-y,-z", "x+1/2,y+1/2,-z", "x,-y+1/2,z", "-x+1/2,y,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z+1/2", "-x,y,-z+1/2", "x+1/2,-y+1/2,-z", "-x,-y,-z", "x+1/2,y+1/2,-z+1/2", "x,-y,z+1/2", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "-x,-y,-z", "x+1/2,y,-z+1/2", "x,-y+1/2,z+1/2", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z", "x+1/2,-y+1/2,-z+1/2", "-x,-y,-z", "x+1/2,y,-z+1/2", "x,-y+1/2,z", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z+1/2", "-x,y,-z+1/2", "x,-y,-z", "-x,-y,-z", "x,y,-z+1/2", "x,-y,z+1/2", "-x,y,z", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "-x+1/2,-y+1/2,-z", "x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y,-z", "-x,-y,-z", "x,y+1/2,-z+1/2", "x,-y+1/2,z+1/2", "-x,y,z", "x+1/2,y+1/2,z", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y+1/2,-z", "-x+1/2,-y+1/2,-z", "x+1/2,y,-z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "-x+1/2,-y+1/2,-z", "x+1/2,y+1/2,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x,-y,z", "-x,y,-z+1/2", "x,-y,-z+1/2", "-x,-y,-z", "x,y,-z", "x,-y,z+1/2", "-x,y,z+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "-x+1/2,-y+1/2,-z", "x+1/2,y+1/2,-z", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y+1/2,z", "-x,y+1/2,-z", "x,-y,-z", "-x,-y,-z", "x,y+1/2,-z", "x,-y+1/2,z", "-x,y,z", "x+1/2,y+1/2,z", "-x+1/2,-y,z", "-x+1/2,y,-z", "x+1/2,-y+1/2,-z", "-x+1/2,-y+1/2,-z", "x+1/2,y,-z", "x+1/2,-y,z", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x+1/2,-y,z", "-x,y,-z+1/2", "x+1/2,-y,-z+1/2", "-x,-y,-z", "x+1/2,y,-z", "x,-y,z+1/2", "-x+1/2,y,z+1/2", "x+1/2,y+1/2,z", "-x,-y+1/2,z", "-x+1/2,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "-x+1/2,-y+1/2,-z", "x,y+1/2,-z", "x+1/2,-y+1/2,z+1/2", "-x,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "-x,-y+1/2,-z+1/2", "x,y+1/2,-z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "-x+1/2,-y,-z+1/2", "x+1/2,y,-z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y,z+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "-x+1/2,-y+1/2,-z", "x+1/2,y+1/2,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z"},
			{"x,y,z", "-x+3/4,-y+3/4,z", "-x+3/4,y,-z+3/4", "x,-y+3/4,-z+3/4", "-x,-y,-z", "x+1/4,y+1/4,-z", "x+1/4,-y,z+1/4", "-x,y+1/4,z+1/4", "x,y+1/2,z+1/2", "-x+3/4,-y+1/4,z+1/2", "-x+3/4,y+1/2,-z+1/4", "x,-y+1/4,-z+1/4", "-x,-y+1/2,-z+1/2", "x+1/4,y+3/4,-z+1/2", "x+1/4,-y+1/2,z+3/4", "-x,y+3/4,z+3/4", "x+1/2,y,z+1/2", "-x+1/4,-y+3/4,z+1/2", "-x+1/4,y,-z+1/4", "x+1/2,-y+3/4,-z+1/4", "-x+1/2,-y,-z+1/2", "x+3/4,y+1/4,-z+1/2", "x+3/4,-y,z+3/4", "-x+1/2,y+1/4,z+3/4", "x+1/2,y+1/2,z", "-x+1/4,-y+1/4,z", "-x+1/4,y+1/2,-z+3/4", "x+1/2,-y+1/4,-z+3/4", "-x+1/2,-y+1/2,-z", "x+3/4,y+3/4,-z", "x+3/4,-y+1/2,z+1/4", "-x+1/2,y+3/4,z+1/4"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "-x,-y,-z", "x,y,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x,y,-z+1/2", "x,-y,-z+1/2", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y+1/2,-z+1/2", "x,-y,z+1/2", "-x,y,z+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "-x,-y,-z", "x+1/2,y,-z+1/2", "x,-y+1/2,z+1/2", "-x+1/2,y+1/2,z", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-x+1/2,y,-z", "x,-y,-z+1/2", "-x+1/2,-y+1/2,-z+1/2", "x,y+1/2,-z", "x+1/2,-y,z", "-x,y,z+1/2"},
			{"x,y,z", "-x,-y+1/2,z", "-x,y+1/2,-z", "x,-y,-z", "-x,-y,-z", "x,y+1/2,-z", "x,-y+1/2,z", "-x,y,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y,-z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z"},
			{"x,y,z", "-x,-y,z+1/2", "-y,x,z+1/4", "y,-x,z+3/4"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2"},
			{"x,y,z", "-x,-y,z+1/2", "-y,x,z+3/4", "y,-x,z+1/4"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z+1/2", "-y,x+1/2,z+1/4", "y+1/2,-x,z+3/4", "x+1/2,y+1/2,z+1/2", "-x,-y,z", "-y+1/2,x,z+3/4", "y,-x+1/2,z+1/4"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "-x,-y,-z", "x,y,-z", "y,-x,-z+1/2", "-y,x,-z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z", "y,-x+1/2,z", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z", "-y,x+1/2,-z"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y,x+1/2,z+1/2", "y+1/2,-x,z+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "y,-x+1/2,-z+1/2", "-y+1/2,x,-z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y+1/2,-z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-y+3/4,x+1/4,z+1/4", "y+3/4,-x+3/4,z+3/4", "-x,-y,-z", "x+1/2,y,-z+1/2", "y+1/4,-x+3/4,-z+3/4", "-y+1/4,x+1/4,-z+1/4", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-y+1/4,x+3/4,z+3/4", "y+1/4,-x+1/4,z+1/4", "-x+1/2,-y+1/2,-z+1/2", "x,y+1/2,-z", "y+3/4,-x+1/4,-z+1/4", "-y+3/4,x+3/4,-z+3/4"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,y,-z", "x,-y,-z", "y,x,-z", "-y,-x,-z"},
			{"x,y,z", "-x,-y,z", "-y+1/2,x+1/2,z", "y+1/2,-x+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "y,x,-z", "-y,-x,-z"},
			{"x,y,z", "-x,-y,z+1/2", "-y,x,z+1/4", "y,-x,z+3/4", "-x,y,-z", "x,-y,-z+1/2", "y,x,-z+3/4", "-y,-x,-z+1/4"},
			{"x,y,z", "-x,-y,z+1/2", "-y+1/2,x+1/2,z+1/4", "y+1/2,-x+1/2,z+3/4", "-x+1/2,y+1/2,-z+1/4", "x+1/2,-y+1/2,-z+3/4", "y,x,-z", "-y,-x,-z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "-x,y,-z", "x,-y,-z", "y,x,-z+1/2", "-y,-x,-z+1/2"},
			{"x,y,z", "-x,-y,z", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "y,x,-z", "-y,-x,-z"},
			{"x,y,z", "-x,-y,z+1/2", "-y,x,z+3/4", "y,-x,z+1/4", "-x,y,-z", "x,-y,-z+1/2", "y,x,-z+1/4", "-y,-x,-z+3/4"},
			{"x,y,z", "-x,-y,z+1/2", "-y+1/2,x+1/2,z+3/4", "y+1/2,-x+1/2,z+1/4", "-x+1/2,y+1/2,-z+3/4", "x+1/2,-y+1/2,-z+1/4", "y,x,-z", "-y,-x,-z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,y,-z", "x,-y,-z", "y,x,-z", "-y,-x,-z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z+1/2", "-y,x+1/2,z+1/4", "y+1/2,-x,z+3/4", "-x+1/2,y,-z+3/4", "x,-y+1/2,-z+1/4", "y+1/2,x+1/2,-z+1/2", "-y,-x,-z", "x+1/2,y+1/2,z+1/2", "-x,-y,z", "-y+1/2,x,z+3/4", "y,-x+1/2,z+1/4", "-x,y+1/2,-z+1/4", "x+1/2,-y,-z+3/4", "y,x,-z", "-y+1/2,-x+1/2,-z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "x,-y,z", "-x,y,z", "-y,-x,z", "y,x,z"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-y+1/2,-x+1/2,z", "y+1/2,x+1/2,z"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "x,-y,z+1/2", "-x,y,z+1/2", "-y,-x,z", "y,x,z"},
			{"x,y,z", "-x,-y,z", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-y,-x,z", "y,x,z"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "x,-y,z+1/2", "-x,y,z+1/2", "-y,-x,z+1/2", "y,x,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "x,-y,z", "-x,y,z", "-y,-x,z+1/2", "y,x,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "x,-y,z", "-x,y,z", "-y,-x,z", "y,x,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "x,-y,z+1/2", "-x,y,z+1/2", "-y,-x,z+1/2", "y,x,z+1/2", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-y+1/2,-x+1/2,z", "y+1/2,x+1/2,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z+1/2", "-y,x+1/2,z+1/4", "y+1/2,-x,z+3/4", "x,-y,z", "-x+1/2,y+1/2,z+1/2", "-y,-x+1/2,z+1/4", "y+1/2,x,z+3/4", "x+1/2,y+1/2,z+1/2", "-x,-y,z", "-y+1/2,x,z+3/4", "y,-x+1/2,z+1/4", "x+1/2,-y+1/2,z+1/2", "-x,y,z", "-y+1/2,-x,z+3/4", "y,x+1/2,z+1/4"},
			{"x,y,z", "-x+1/2,-y+1/2,z+1/2", "-y,x+1/2,z+1/4", "y+1/2,-x,z+3/4", "x,-y,z+1/2", "-x+1/2,y+1/2,z", "-y,-x+1/2,z+3/4", "y+1/2,x,z+1/4", "x+1/2,y+1/2,z+1/2", "-x,-y,z", "-y+1/2,x,z+3/4", "y,-x+1/2,z+1/4", "x+1/2,-y+1/2,z", "-x,y,z+1/2", "-y+1/2,-x,z+1/4", "y,x+1/2,z+3/4"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "-x,y,-z", "x,-y,-z", "-y,-x,z", "y,x,z"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "-x,y,-z+1/2", "x,-y,-z+1/2", "-y,-x,z+1/2", "y,x,z+1/2"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "-y+1/2,-x+1/2,z", "y+1/2,x+1/2,z"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "x,-y,z", "-x,y,z", "y,x,-z", "-y,-x,-z"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "x,-y,z+1/2", "-x,y,z+1/2", "y,x,-z+1/2", "-y,-x,-z+1/2"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "y+1/2,x+1/2,-z", "-y+1/2,-x+1/2,-z"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "x,-y,z", "-x,y,z", "y,x,-z", "-y,-x,-z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "x,-y,z+1/2", "-x,y,z+1/2", "y,x,-z+1/2", "-y,-x,-z+1/2", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "y+1/2,x+1/2,-z", "-y+1/2,-x+1/2,-z"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "-x,y,-z", "x,-y,-z", "-y,-x,z", "y,x,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "y,-x,-z", "-y,x,-z", "-x+1/2,y,-z+3/4", "x+1/2,-y,-z+3/4", "-y+1/2,-x,z+3/4", "y+1/2,x,z+3/4", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "-x,y+1/2,-z+1/4", "x,-y+1/2,-z+1/4", "-y,-x+1/2,z+1/4", "y,x+1/2,z+1/4"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,y,-z", "x,-y,-z", "y,x,-z", "-y,-x,-z", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z", "x,-y,z", "-x,y,z", "-y,-x,z", "y,x,z"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,y,-z+1/2", "x,-y,-z+1/2", "y,x,-z+1/2", "-y,-x,-z+1/2", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z", "x,-y,z+1/2", "-x,y,z+1/2", "-y,-x,z+1/2", "y,x,z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z", "y,-x+1/2,z", "-x+1/2,y,-z", "x,-y+1/2,-z", "y,x,-z", "-y+1/2,-x+1/2,-z", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z", "-y,x+1/2,-z", "x+1/2,-y,z", "-x,y+1/2,z", "-y,-x,z", "y+1/2,x+1/2,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z", "y,-x+1/2,z", "-x+1/2,y,-z+1/2", "x,-y+1/2,-z+1/2", "y,x,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z", "-y,x+1/2,-z", "x+1/2,-y,z+1/2", "-x,y+1/2,z+1/2", "-y,-x,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "y+1/2,x+1/2,-z", "-y+1/2,-x+1/2,-z", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-y+1/2,-x+1/2,z", "y+1/2,x+1/2,z"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z", "y,-x+1/2,z", "-x,y+1/2,-z", "x+1/2,-y,-z", "y+1/2,x+1/2,-z", "-y,-x,-z", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z", "-y,x+1/2,-z", "x,-y+1/2,z", "-x+1/2,y,z", "-y+1/2,-x+1/2,z", "y,x,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z", "y,-x+1/2,z", "-x,y+1/2,-z+1/2", "x+1/2,-y,-z+1/2", "y+1/2,x+1/2,-z+1/2", "-y,-x,-z+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z", "-y,x+1/2,-z", "x,-y+1/2,z+1/2", "-x+1/2,y,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y,x,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "-x,y,-z", "x,-y,-z", "y,x,-z+1/2", "-y,-x,-z+1/2", "-x,-y,-z", "x,y,-z", "y,-x,-z+1/2", "-y,x,-z+1/2", "x,-y,z", "-x,y,z", "-y,-x,z+1/2", "y,x,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "-x,y,-z+1/2", "x,-y,-z+1/2", "y,x,-z", "-y,-x,-z", "-x,-y,-z", "x,y,-z", "y,-x,-z+1/2", "-y,x,-z+1/2", "x,-y,z+1/2", "-x,y,z+1/2", "-y,-x,z", "y,x,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z+1/2", "y,-x+1/2,z+1/2", "-x+1/2,y,-z", "x,-y+1/2,-z", "y,x,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z+1/2", "-y,x+1/2,-z+1/2", "x+1/2,-y,z", "-x,y+1/2,z", "-y,-x,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z+1/2", "y,-x+1/2,z+1/2", "-x+1/2,y,-z+1/2", "x,-y+1/2,-z+1/2", "y,x,-z", "-y+1/2,-x+1/2,-z", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z+1/2", "-y,x+1/2,-z+1/2", "x+1/2,-y,z+1/2", "-x,y+1/2,z+1/2", "-y,-x,z", "y+1/2,x+1/2,z"},
			{"x,y,z", "-x,-y,z", "-y,x,z+1/2", "y,-x,z+1/2", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "-x,-y,-z", "x,y,-z", "y,-x,-z+1/2", "-y,x,-z+1/2", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "y,x,-z", "-y,-x,-z", "-x,-y,-z", "x,y,-z", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-y,-x,z", "y,x,z"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z+1/2", "y,-x+1/2,z+1/2", "-x,y+1/2,-z", "x+1/2,-y,-z", "y+1/2,x+1/2,-z+1/2", "-y,-x,-z+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z+1/2", "-y,x+1/2,-z+1/2", "x,-y+1/2,z", "-x+1/2,y,z", "-y+1/2,-x+1/2,z+1/2", "y,x,z+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-y+1/2,x,z+1/2", "y,-x+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y,-z+1/2", "y+1/2,x+1/2,-z", "-y,-x,-z", "-x,-y,-z", "x+1/2,y+1/2,-z", "y+1/2,-x,-z+1/2", "-y,x+1/2,-z+1/2", "x,-y+1/2,z+1/2", "-x+1/2,y,z+1/2", "-y+1/2,-x+1/2,z", "y,x,z"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,y,-z", "x,-y,-z", "y,x,-z", "-y,-x,-z", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z", "x,-y,z", "-x,y,z", "-y,-x,z", "y,x,z", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y+1/2,-z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2"},
			{"x,y,z", "-x,-y,z", "-y,x,z", "y,-x,z", "-x,y,-z+1/2", "x,-y,-z+1/2", "y,x,-z+1/2", "-y,-x,-z+1/2", "-x,-y,-z", "x,y,-z", "y,-x,-z", "-y,x,-z", "x,-y,z+1/2", "-x,y,z+1/2", "-y,-x,z+1/2", "y,x,z+1/2", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "y+1/2,-x+1/2,z+1/2", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "y+1/2,x+1/2,-z", "-y+1/2,-x+1/2,-z", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y+1/2,-z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-y+1/2,-x+1/2,z", "y+1/2,x+1/2,z"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-y+1/4,x+3/4,z+1/4", "y+1/4,-x+1/4,z+3/4", "-x+1/2,y,-z+1/2", "x,-y,-z", "y+1/4,x+3/4,-z+1/4", "-y+1/4,-x+1/4,-z+3/4", "-x,-y,-z", "x+1/2,y,-z+1/2", "y+3/4,-x+1/4,-z+3/4", "-y+3/4,x+3/4,-z+1/4", "x+1/2,-y,z+1/2", "-x,y,z", "-y+3/4,-x+1/4,z+3/4", "y+3/4,x+3/4,z+1/4", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-y+3/4,x+1/4,z+3/4", "y+3/4,-x+3/4,z+1/4", "-x,y+1/2,-z", "x+1/2,-y+1/2,-z+1/2", "y+3/4,x+1/4,-z+3/4", "-y+3/4,-x+3/4,-z+1/4", "-x+1/2,-y+1/2,-z+1/2", "x,y+1/2,-z", "y+1/4,-x+3/4,-z+1/4", "-y+1/4,x+1/4,-z+3/4", "x,-y+1/2,z", "-x+1/2,y+1/2,z+1/2", "-y+1/4,-x+3/4,z+1/4", "y+1/4,x+1/4,z+3/4"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-y+1/4,x+3/4,z+1/4", "y+1/4,-x+1/4,z+3/4", "-x+1/2,y,-z", "x,-y,-z+1/2", "y+1/4,x+3/4,-z+3/4", "-y+1/4,-x+1/4,-z+1/4", "-x,-y,-z", "x+1/2,y,-z+1/2", "y+3/4,-x+1/4,-z+3/4", "-y+3/4,x+3/4,-z+1/4", "x+1/2,-y,z", "-x,y,z+1/2", "-y+3/4,-x+1/4,z+1/4", "y+3/4,x+3/4,z+3/4", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-y+3/4,x+1/4,z+3/4", "y+3/4,-x+3/4,z+1/4", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "y+3/4,x+1/4,-z+1/4", "-y+3/4,-x+3/4,-z+3/4", "-x+1/2,-y+1/2,-z+1/2", "x,y+1/2,-z", "y+1/4,-x+3/4,-z+1/4", "-y+1/4,x+1/4,-z+3/4", "x,-y+1/2,z+1/2", "-x+1/2,y+1/2,z", "-y+1/4,-x+3/4,z+3/4", "y+1/4,x+1/4,z+1/4"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z"},
			{"x,y,z", "-y,x-y,z+1/3", "-x+y,-x,z+2/3"},
			{"x,y,z", "-y,x-y,z+2/3", "-x+y,-x,z+1/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "-x+2/3,-y+1/3,-z+1/3", "y+2/3,-x+y+1/3,-z+1/3", "x-y+2/3,x+1/3,-z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "-x+1/3,-y+2/3,-z+2/3", "y+1/3,-x+y+2/3,-z+2/3", "x-y+1/3,x+2/3,-z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,-z", "-x+y,y,-z", "x,x-y,-z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z"},
			{"x,y,z", "-y,x-y,z+1/3", "-x+y,-x,z+2/3", "-y,-x,-z+2/3", "-x+y,y,-z+1/3", "x,x-y,-z"},
			{"x,y,z", "-y,x-y,z+1/3", "-x+y,-x,z+2/3", "y,x,-z", "x-y,-y,-z+2/3", "-x,-x+y,-z+1/3"},
			{"x,y,z", "-y,x-y,z+2/3", "-x+y,-x,z+1/3", "-y,-x,-z+1/3", "-x+y,y,-z+2/3", "x,x-y,-z"},
			{"x,y,z", "-y,x-y,z+2/3", "-x+y,-x,z+1/3", "y,x,-z", "x-y,-y,-z+1/3", "-x,-x+y,-z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "y+2/3,x+1/3,-z+1/3", "x-y+2/3,-y+1/3,-z+1/3", "-x+2/3,-x+y+1/3,-z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "y+1/3,x+2/3,-z+2/3", "x-y+1/3,-y+2/3,-z+2/3", "-x+1/3,-x+y+2/3,-z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,z", "-x+y,y,z", "x,x-y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,z", "x-y,-y,z", "-x,-x+y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,z+1/2", "x-y,-y,z+1/2", "-x,-x+y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "-y+2/3,-x+1/3,z+1/3", "-x+y+2/3,y+1/3,z+1/3", "x+2/3,x-y+1/3,z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "-y+1/3,-x+2/3,z+2/3", "-x+y+1/3,y+2/3,z+2/3", "x+1/3,x-y+2/3,z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "-y+2/3,-x+1/3,z+5/6", "-x+y+2/3,y+1/3,z+5/6", "x+2/3,x-y+1/3,z+5/6", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "-y+1/3,-x+2/3,z+1/6", "-x+y+1/3,y+2/3,z+1/6", "x+1/3,x-y+2/3,z+1/6"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,-z", "-x+y,y,-z", "x,x-y,-z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "y,x,z", "x-y,-y,z", "-x,-x+y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,-z+1/2", "-x+y,y,-z+1/2", "x,x-y,-z+1/2", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "y,x,z+1/2", "x-y,-y,z+1/2", "-x,-x+y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "-y,-x,z", "-x+y,y,z", "x,x-y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z+1/2", "x-y,-y,-z+1/2", "-x,-x+y,-z+1/2", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "y+2/3,x+1/3,-z+1/3", "x-y+2/3,-y+1/3,-z+1/3", "-x+2/3,-x+y+1/3,-z+1/3", "-x+2/3,-y+1/3,-z+1/3", "y+2/3,-x+y+1/3,-z+1/3", "x-y+2/3,x+1/3,-z+1/3", "-y+2/3,-x+1/3,z+1/3", "-x+y+2/3,y+1/3,z+1/3", "x+2/3,x-y+1/3,z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "y+1/3,x+2/3,-z+2/3", "x-y+1/3,-y+2/3,-z+2/3", "-x+1/3,-x+y+2/3,-z+2/3", "-x+1/3,-y+2/3,-z+2/3", "y+1/3,-x+y+2/3,-z+2/3", "x-y+1/3,x+2/3,-z+2/3", "-y+1/3,-x+2/3,z+2/3", "-x+y+1/3,y+2/3,z+2/3", "x+1/3,x-y+2/3,z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z+1/2", "x-y,-y,-z+1/2", "-x,-x+y,-z+1/2", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "y+2/3,x+1/3,-z+5/6", "x-y+2/3,-y+1/3,-z+5/6", "-x+2/3,-x+y+1/3,-z+5/6", "-x+2/3,-y+1/3,-z+1/3", "y+2/3,-x+y+1/3,-z+1/3", "x-y+2/3,x+1/3,-z+1/3", "-y+2/3,-x+1/3,z+5/6", "-x+y+2/3,y+1/3,z+5/6", "x+2/3,x-y+1/3,z+5/6", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "y+1/3,x+2/3,-z+1/6", "x-y+1/3,-y+2/3,-z+1/6", "-x+1/3,-x+y+2/3,-z+1/6", "-x+1/3,-y+2/3,-z+2/3", "y+1/3,-x+y+2/3,-z+2/3", "x-y+1/3,x+2/3,-z+2/3", "-y+1/3,-x+2/3,z+1/6", "-x+y+1/3,y+2/3,z+1/6", "x+1/3,x-y+2/3,z+1/6"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z"},
			{"x,y,z", "-y,x-y,z+1/3", "-x+y,-x,z+2/3", "-x,-y,z+1/2", "y,-x+y,z+5/6", "x-y,x,z+1/6"},
			{"x,y,z", "-y,x-y,z+2/3", "-x+y,-x,z+1/3", "-x,-y,z+1/2", "y,-x+y,z+1/6", "x-y,x,z+5/6"},
			{"x,y,z", "-y,x-y,z+2/3", "-x+y,-x,z+1/3", "-x,-y,z", "y,-x+y,z+2/3", "x-y,x,z+1/3"},
			{"x,y,z", "-y,x-y,z+1/3", "-x+y,-x,z+2/3", "-x,-y,z", "y,-x+y,z+1/3", "x-y,x,z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z+1/2", "y,-x+y,z+1/2", "x-y,x,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "x,y,-z", "-y,x-y,-z", "-x+y,-x,-z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x,y,-z", "-y,x-y,-z", "-x+y,-x,-z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z+1/2", "y,-x+y,z+1/2", "x-y,x,z+1/2", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x,y,-z+1/2", "-y,x-y,-z+1/2", "-x+y,-x,-z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "-y,-x,-z", "-x+y,y,-z", "x,x-y,-z"},
			{"x,y,z", "-y,x-y,z+1/3", "-x+y,-x,z+2/3", "-x,-y,z+1/2", "y,-x+y,z+5/6", "x-y,x,z+1/6", "y,x,-z+1/3", "x-y,-y,-z", "-x,-x+y,-z+2/3", "-y,-x,-z+5/6", "-x+y,y,-z+1/2", "x,x-y,-z+1/6"},
			{"x,y,z", "-y,x-y,z+2/3", "-x+y,-x,z+1/3", "-x,-y,z+1/2", "y,-x+y,z+1/6", "x-y,x,z+5/6", "y,x,-z+2/3", "x-y,-y,-z", "-x,-x+y,-z+1/3", "-y,-x,-z+1/6", "-x+y,y,-z+1/2", "x,x-y,-z+5/6"},
			{"x,y,z", "-y,x-y,z+2/3", "-x+y,-x,z+1/3", "-x,-y,z", "y,-x+y,z+2/3", "x-y,x,z+1/3", "y,x,-z+2/3", "x-y,-y,-z", "-x,-x+y,-z+1/3", "-y,-x,-z+2/3", "-x+y,y,-z", "x,x-y,-z+1/3"},
			{"x,y,z", "-y,x-y,z+1/3", "-x+y,-x,z+2/3", "-x,-y,z", "y,-x+y,z+1/3", "x-y,x,z+2/3", "y,x,-z+1/3", "x-y,-y,-z", "-x,-x+y,-z+2/3", "-y,-x,-z+1/3", "-x+y,y,-z", "x,x-y,-z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z+1/2", "y,-x+y,z+1/2", "x-y,x,z+1/2", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "-y,-x,-z+1/2", "-x+y,y,-z+1/2", "x,x-y,-z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "y,x,z", "x-y,-y,z", "-x,-x+y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "y,x,z+1/2", "x-y,-y,z+1/2", "-x,-x+y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z+1/2", "y,-x+y,z+1/2", "x-y,x,z+1/2", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "y,x,z", "x-y,-y,z", "-x,-x+y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z+1/2", "y,-x+y,z+1/2", "x-y,x,z+1/2", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "y,x,z+1/2", "x-y,-y,z+1/2", "-x,-x+y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "x,y,-z", "-y,x-y,-z", "-x+y,-x,-z", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "-y,-x,-z", "-x+y,y,-z", "x,x-y,-z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "x,y,-z+1/2", "-y,x-y,-z+1/2", "-x+y,-x,-z+1/2", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "-y,-x,-z", "-x+y,y,-z", "x,x-y,-z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "x,y,-z", "-y,x-y,-z", "-x+y,-x,-z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "y,x,z", "x-y,-y,z", "-x,-x+y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "x,y,-z+1/2", "-y,x-y,-z+1/2", "-x+y,-x,-z+1/2", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "y,x,z+1/2", "x-y,-y,z+1/2", "-x,-x+y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "-y,-x,-z", "-x+y,y,-z", "x,x-y,-z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x,y,-z", "-y,x-y,-z", "-x+y,-x,-z", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "y,x,z", "x-y,-y,z", "-x,-x+y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z", "y,x,-z+1/2", "x-y,-y,-z+1/2", "-x,-x+y,-z+1/2", "-y,-x,-z+1/2", "-x+y,y,-z+1/2", "x,x-y,-z+1/2", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x,y,-z", "-y,x-y,-z", "-x+y,-x,-z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "y,x,z+1/2", "x-y,-y,z+1/2", "-x,-x+y,z+1/2"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z+1/2", "y,-x+y,z+1/2", "x-y,x,z+1/2", "y,x,-z+1/2", "x-y,-y,-z+1/2", "-x,-x+y,-z+1/2", "-y,-x,-z", "-x+y,y,-z", "x,x-y,-z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x,y,-z+1/2", "-y,x-y,-z+1/2", "-x+y,-x,-z+1/2", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "y,x,z", "x-y,-y,z", "-x,-x+y,z"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z+1/2", "y,-x+y,z+1/2", "x-y,x,z+1/2", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "-y,-x,-z+1/2", "-x+y,y,-z+1/2", "x,x-y,-z+1/2", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x,y,-z+1/2", "-y,x-y,-z+1/2", "-x+y,-x,-z+1/2", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "y,x,z+1/2", "x-y,-y,z+1/2", "-x,-x+y,z+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "z,x+1/2,y+1/2", "z,-x+1/2,-y+1/2", "-z,-x+1/2,y+1/2", "-z,x+1/2,-y+1/2", "y,z+1/2,x+1/2", "-y,z+1/2,-x+1/2", "y,-z+1/2,-x+1/2", "-y,-z+1/2,x+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "z+1/2,x,y+1/2", "z+1/2,-x,-y+1/2", "-z+1/2,-x,y+1/2", "-z+1/2,x,-y+1/2", "y+1/2,z,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z,-x+1/2", "-y+1/2,-z,x+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "z+1/2,x+1/2,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x+1/2,y", "-z+1/2,x+1/2,-y", "y+1/2,z+1/2,x", "-y+1/2,z+1/2,-x", "y+1/2,-z+1/2,-x", "-y+1/2,-z+1/2,x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "z+1/2,x+1/2,y+1/2", "z+1/2,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y+1/2", "-z+1/2,x+1/2,-y+1/2", "y+1/2,z+1/2,x+1/2", "-y+1/2,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-x+1/2,y,-z", "x,-y,-z+1/2", "z+1/2,x+1/2,y+1/2", "z,-x,-y+1/2", "-z,-x+1/2,y", "-z+1/2,x,-y", "y+1/2,z+1/2,x+1/2", "-y+1/2,z,-x", "y,-z,-x+1/2", "-y,-z+1/2,x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-x+1/2,y,-z+1/2", "x,-y+1/2,-z+1/2", "z,x,y", "z,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y", "-z+1/2,x,-y+1/2", "y,z,x", "-y+1/2,z,-x+1/2", "y,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x", "-x,-y,-z", "x+1/2,y+1/2,-z", "x+1/2,-y,z+1/2", "-x,y+1/2,z+1/2", "-z,-x,-y", "-z,x+1/2,y+1/2", "z+1/2,x+1/2,-y", "z+1/2,-x,y+1/2", "-y,-z,-x", "y+1/2,-z,x+1/2", "-y,z+1/2,x+1/2", "y+1/2,z+1/2,-x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "z,x+1/2,y+1/2", "z,-x+1/2,-y+1/2", "-z,-x+1/2,y+1/2", "-z,x+1/2,-y+1/2", "y,z+1/2,x+1/2", "-y,z+1/2,-x+1/2", "y,-z+1/2,-x+1/2", "-y,-z+1/2,x+1/2", "-x,-y+1/2,-z+1/2", "x,y+1/2,-z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2", "-z,-x+1/2,-y+1/2", "-z,x+1/2,y+1/2", "z,x+1/2,-y+1/2", "z,-x+1/2,y+1/2", "-y,-z+1/2,-x+1/2", "y,-z+1/2,x+1/2", "-y,z+1/2,x+1/2", "y,z+1/2,-x+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "z+1/2,x,y+1/2", "z+1/2,-x,-y+1/2", "-z+1/2,-x,y+1/2", "-z+1/2,x,-y+1/2", "y+1/2,z,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z,-x+1/2", "-y+1/2,-z,x+1/2", "-x+1/2,-y,-z+1/2", "x+1/2,y,-z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y,z+1/2", "-z+1/2,-x,-y+1/2", "-z+1/2,x,y+1/2", "z+1/2,x,-y+1/2", "z+1/2,-x,y+1/2", "-y+1/2,-z,-x+1/2", "y+1/2,-z,x+1/2", "-y+1/2,z,x+1/2", "y+1/2,z,-x+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "z+1/2,x+1/2,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x+1/2,y", "-z+1/2,x+1/2,-y", "y+1/2,z+1/2,x", "-y+1/2,z+1/2,-x", "y+1/2,-z+1/2,-x", "-y+1/2,-z+1/2,x", "-x+1/2,-y+1/2,-z", "x+1/2,y+1/2,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-z+1/2,-x+1/2,-y", "-z+1/2,x+1/2,y", "z+1/2,x+1/2,-y", "z+1/2,-x+1/2,y", "-y+1/2,-z+1/2,-x", "y+1/2,-z+1/2,x", "-y+1/2,z+1/2,x", "y+1/2,z+1/2,-x"},
			{"x,y,z", "-x+3/4,-y+3/4,z", "-x+3/4,y,-z+3/4", "x,-y+3/4,-z+3/4", "z,x,y", "z,-x+3/4,-y+3/4", "-z+3/4,-x+3/4,y", "-z+3/4,x,-y+3/4", "y,z,x", "-y+3/4,z,-x+3/4", "y,-z+3/4,-x+3/4", "-y+3/4,-z+3/4,x", "-x,-y,-z", "x+1/4,y+1/4,-z", "x+1/4,-y,z+1/4", "-x,y+1/4,z+1/4", "-z,-x,-y", "-z,x+1/4,y+1/4", "z+1/4,x+1/4,-y", "z+1/4,-x,y+1/4", "-y,-z,-x", "y+1/4,-z,x+1/4", "-y,z+1/4,x+1/4", "y+1/4,z+1/4,-x", "x,y+1/2,z+1/2", "-x+3/4,-y+1/4,z+1/2", "-x+3/4,y+1/2,-z+1/4", "x,-y+1/4,-z+1/4", "z,x+1/2,y+1/2", "z,-x+1/4,-y+1/4", "-z+3/4,-x+1/4,y+1/2", "-z+3/4,x+1/2,-y+1/4", "y,z+1/2,x+1/2", "-y+3/4,z+1/2,-x+1/4", "y,-z+1/4,-x+1/4", "-y+3/4,-z+1/4,x+1/2", "-x,-y+1/2,-z+1/2", "x+1/4,y+3/4,-z+1/2", "x+1/4,-y+1/2,z+3/4", "-x,y+3/4,z+3/4", "-z,-x+1/2,-y+1/2", "-z,x+3/4,y+3/4", "z+1/4,x+3/4,-y+1/2", "z+1/4,-x+1/2,y+3/4", "-y,-z+1/2,-x+1/2", "y+1/4,-z+1/2,x+3/4", "-y,z+3/4,x+3/4", "y+1/4,z+3/4,-x+1/2", "x+1/2,y,z+1/2", "-x+1/4,-y+3/4,z+1/2", "-x+1/4,y,-z+1/4", "x+1/2,-y+3/4,-z+1/4", "z+1/2,x,y+1/2", "z+1/2,-x+3/4,-y+1/4", "-z+1/4,-x+3/4,y+1/2", "-z+1/4,x,-y+1/4", "y+1/2,z,x+1/2", "-y+1/4,z,-x+1/4", "y+1/2,-z+3/4,-x+1/4", "-y+1/4,-z+3/4,x+1/2", "-x+1/2,-y,-z+1/2", "x+3/4,y+1/4,-z+1/2", "x+3/4,-y,z+3/4", "-x+1/2,y+1/4,z+3/4", "-z+1/2,-x,-y+1/2", "-z+1/2,x+1/4,y+3/4", "z+3/4,x+1/4,-y+1/2", "z+3/4,-x,y+3/4", "-y+1/2,-z,-x+1/2", "y+3/4,-z,x+3/4", "-y+1/2,z+1/4,x+3/4", "y+3/4,z+1/4,-x+1/2", "x+1/2,y+1/2,z", "-x+1/4,-y+1/4,z", "-x+1/4,y+1/2,-z+3/4", "x+1/2,-y+1/4,-z+3/4", "z+1/2,x+1/2,y", "z+1/2,-x+1/4,-y+3/4", "-z+1/4,-x+1/4,y", "-z+1/4,x+1/2,-y+3/4", "y+1/2,z+1/2,x", "-y+1/4,z+1/2,-x+3/4", "y+1/2,-z+1/4,-x+3/4", "-y+1/4,-z+1/4,x", "-x+1/2,-y+1/2,-z", "x+3/4,y+3/4,-z", "x+3/4,-y+1/2,z+1/4", "-x+1/2,y+3/4,z+1/4", "-z+1/2,-x+1/2,-y", "-z+1/2,x+3/4,y+1/4", "z+3/4,x+3/4,-y", "z+3/4,-x+1/2,y+1/4", "-y+1/2,-z+1/2,-x", "y+3/4,-z+1/2,x+1/4", "-y+1/2,z+3/4,x+1/4", "y+3/4,z+3/4,-x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "z+1/2,x+1/2,y+1/2", "z+1/2,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y+1/2", "-z+1/2,x+1/2,-y+1/2", "y+1/2,z+1/2,x+1/2", "-y+1/2,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x+1/2", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-z+1/2,-x+1/2,-y+1/2", "-z+1/2,x+1/2,y+1/2", "z+1/2,x+1/2,-y+1/2", "z+1/2,-x+1/2,y+1/2", "-y+1/2,-z+1/2,-x+1/2", "y+1/2,-z+1/2,x+1/2", "-y+1/2,z+1/2,x+1/2", "y+1/2,z+1/2,-x+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "-x,-y,-z", "x+1/2,y,-z+1/2", "x,-y+1/2,z+1/2", "-x+1/2,y+1/2,z", "-z,-x,-y", "-z+1/2,x+1/2,y", "z+1/2,x,-y+1/2", "z,-x+1/2,y+1/2", "-y,-z,-x", "y,-z+1/2,x+1/2", "-y+1/2,z+1/2,x", "y+1/2,z,-x+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "-x,-y,-z", "x+1/2,y,-z+1/2", "x,-y+1/2,z+1/2", "-x+1/2,y+1/2,z", "-z,-x,-y", "-z+1/2,x+1/2,y", "z+1/2,x,-y+1/2", "z,-x+1/2,y+1/2", "-y,-z,-x", "y,-z+1/2,x+1/2", "-y+1/2,z+1/2,x", "y+1/2,z,-x+1/2", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-x+1/2,y,-z", "x,-y,-z+1/2", "z+1/2,x+1/2,y+1/2", "z,-x,-y+1/2", "-z,-x+1/2,y", "-z+1/2,x,-y", "y+1/2,z+1/2,x+1/2", "-y+1/2,z,-x", "y,-z,-x+1/2", "-y,-z+1/2,x", "-x+1/2,-y+1/2,-z+1/2", "x,y+1/2,-z", "x+1/2,-y,z", "-x,y,z+1/2", "-z+1/2,-x+1/2,-y+1/2", "-z,x,y+1/2", "z,x+1/2,-y", "z+1/2,-x,y", "-y+1/2,-z+1/2,-x+1/2", "y+1/2,-z,x", "-y,z,x+1/2", "y,z+1/2,-x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,-z", "-y,-x,-z", "y,-x,z", "-y,x,z", "x,z,-y", "-x,z,y", "-x,-z,-y", "x,-z,y", "z,y,-x", "z,-y,x", "-z,y,x", "-z,-y,-x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "y+1/2,-x+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "x+1/2,z+1/2,-y+1/2", "-x+1/2,z+1/2,y+1/2", "-x+1/2,-z+1/2,-y+1/2", "x+1/2,-z+1/2,y+1/2", "z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,x+1/2", "-z+1/2,-y+1/2,-x+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,-z", "-y,-x,-z", "y,-x,z", "-y,x,z", "x,z,-y", "-x,z,y", "-x,-z,-y", "x,-z,y", "z,y,-x", "z,-y,x", "-z,y,x", "-z,-y,-x", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "z,x+1/2,y+1/2", "z,-x+1/2,-y+1/2", "-z,-x+1/2,y+1/2", "-z,x+1/2,-y+1/2", "y,z+1/2,x+1/2", "-y,z+1/2,-x+1/2", "y,-z+1/2,-x+1/2", "-y,-z+1/2,x+1/2", "y,x+1/2,-z+1/2", "-y,-x+1/2,-z+1/2", "y,-x+1/2,z+1/2", "-y,x+1/2,z+1/2", "x,z+1/2,-y+1/2", "-x,z+1/2,y+1/2", "-x,-z+1/2,-y+1/2", "x,-z+1/2,y+1/2", "z,y+1/2,-x+1/2", "z,-y+1/2,x+1/2", "-z,y+1/2,x+1/2", "-z,-y+1/2,-x+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "z+1/2,x,y+1/2", "z+1/2,-x,-y+1/2", "-z+1/2,-x,y+1/2", "-z+1/2,x,-y+1/2", "y+1/2,z,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z,-x+1/2", "-y+1/2,-z,x+1/2", "y+1/2,x,-z+1/2", "-y+1/2,-x,-z+1/2", "y+1/2,-x,z+1/2", "-y+1/2,x,z+1/2", "x+1/2,z,-y+1/2", "-x+1/2,z,y+1/2", "-x+1/2,-z,-y+1/2", "x+1/2,-z,y+1/2", "z+1/2,y,-x+1/2", "z+1/2,-y,x+1/2", "-z+1/2,y,x+1/2", "-z+1/2,-y,-x+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "z+1/2,x+1/2,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x+1/2,y", "-z+1/2,x+1/2,-y", "y+1/2,z+1/2,x", "-y+1/2,z+1/2,-x", "y+1/2,-z+1/2,-x", "-y+1/2,-z+1/2,x", "y+1/2,x+1/2,-z", "-y+1/2,-x+1/2,-z", "y+1/2,-x+1/2,z", "-y+1/2,x+1/2,z", "x+1/2,z+1/2,-y", "-x+1/2,z+1/2,y", "-x+1/2,-z+1/2,-y", "x+1/2,-z+1/2,y", "z+1/2,y+1/2,-x", "z+1/2,-y+1/2,x", "-z+1/2,y+1/2,x", "-z+1/2,-y+1/2,-x"},
			{"x,y,z", "-x,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z", "x+1/2,-y,-z+1/2", "z,x,y", "z+1/2,-x,-y+1/2", "-z,-x+1/2,y+1/2", "-z+1/2,x+1/2,-y", "y,z,x", "-y+1/2,z+1/2,-x", "y+1/2,-z,-x+1/2", "-y,-z+1/2,x+1/2", "y+3/4,x+1/4,-z+3/4", "-y+1/4,-x+1/4,-z+1/4", "y+1/4,-x+3/4,z+3/4", "-y+3/4,x+3/4,z+1/4", "x+3/4,z+1/4,-y+3/4", "-x+3/4,z+3/4,y+1/4", "-x+1/4,-z+1/4,-y+1/4", "x+1/4,-z+3/4,y+3/4", "z+3/4,y+1/4,-x+3/4", "z+1/4,-y+3/4,x+3/4", "-z+3/4,y+3/4,x+1/4", "-z+1/4,-y+1/4,-x+1/4", "x,y+1/2,z+1/2", "-x,-y,z", "-x+1/2,y,-z+1/2", "x+1/2,-y+1/2,-z", "z,x+1/2,y+1/2", "z+1/2,-x+1/2,-y", "-z,-x,y", "-z+1/2,x,-y+1/2", "y,z+1/2,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z+1/2,-x", "-y,-z,x", "y+3/4,x+3/4,-z+1/4", "-y+1/4,-x+3/4,-z+3/4", "y+1/4,-x+1/4,z+1/4", "-y+3/4,x+1/4,z+3/4", "x+3/4,z+3/4,-y+1/4", "-x+3/4,z+1/4,y+3/4", "-x+1/4,-z+3/4,-y+3/4", "x+1/4,-z+1/4,y+1/4", "z+3/4,y+3/4,-x+1/4", "z+1/4,-y+1/4,x+1/4", "-z+3/4,y+1/4,x+3/4", "-z+1/4,-y+3/4,-x+3/4", "x+1/2,y,z+1/2", "-x+1/2,-y+1/2,z", "-x,y+1/2,-z+1/2", "x,-y,-z", "z+1/2,x,y+1/2", "z,-x,-y", "-z+1/2,-x+1/2,y", "-z,x+1/2,-y+1/2", "y+1/2,z,x+1/2", "-y,z+1/2,-x+1/2", "y,-z,-x", "-y+1/2,-z+1/2,x", "y+1/4,x+1/4,-z+1/4", "-y+3/4,-x+1/4,-z+3/4", "y+3/4,-x+3/4,z+1/4", "-y+1/4,x+3/4,z+3/4", "x+1/4,z+1/4,-y+1/4", "-x+1/4,z+3/4,y+3/4", "-x+3/4,-z+1/4,-y+3/4", "x+3/4,-z+3/4,y+1/4", "z+1/4,y+1/4,-x+1/4", "z+3/4,-y+3/4,x+1/4", "-z+1/4,y+3/4,x+3/4", "-z+3/4,-y+1/4,-x+3/4", "x+1/2,y+1/2,z", "-x+1/2,-y,z+1/2", "-x,y,-z", "x,-y+1/2,-z+1/2", "z+1/2,x+1/2,y", "z,-x+1/2,-y+1/2", "-z+1/2,-x,y+1/2", "-z,x,-y", "y+1/2,z+1/2,x", "-y,z,-x", "y,-z+1/2,-x+1/2", "-y+1/2,-z,x+1/2", "y+1/4,x+3/4,-z+3/4", "-y+3/4,-x+3/4,-z+1/4", "y+3/4,-x+1/4,z+3/4", "-y+1/4,x+1/4,z+1/4", "x+1/4,z+3/4,-y+3/4", "-x+1/4,z+1/4,y+1/4", "-x+3/4,-z+3/4,-y+1/4", "x+3/4,-z+1/4,y+3/4", "z+1/4,y+3/4,-x+3/4", "z+3/4,-y+1/4,x+3/4", "-z+1/4,y+1/4,x+1/4", "-z+3/4,-y+3/4,-x+1/4"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,-z", "-y,-x,-z", "y,-x,z", "-y,x,z", "x,z,-y", "-x,z,y", "-x,-z,-y", "x,-z,y", "z,y,-x", "z,-y,x", "-z,y,x", "-z,-y,-x", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "z+1/2,x+1/2,y+1/2", "z+1/2,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y+1/2", "-z+1/2,x+1/2,-y+1/2", "y+1/2,z+1/2,x+1/2", "-y+1/2,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x+1/2", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "y+1/2,-x+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "x+1/2,z+1/2,-y+1/2", "-x+1/2,z+1/2,y+1/2", "-x+1/2,-z+1/2,-y+1/2", "x+1/2,-z+1/2,y+1/2", "z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,x+1/2", "-z+1/2,-y+1/2,-x+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "y+1/4,x+3/4,-z+3/4", "-y+1/4,-x+1/4,-z+1/4", "y+3/4,-x+3/4,z+1/4", "-y+3/4,x+1/4,z+3/4", "x+1/4,z+3/4,-y+3/4", "-x+3/4,z+1/4,y+3/4", "-x+1/4,-z+1/4,-y+1/4", "x+3/4,-z+3/4,y+1/4", "z+1/4,y+3/4,-x+3/4", "z+3/4,-y+3/4,x+1/4", "-z+3/4,y+1/4,x+3/4", "-z+1/4,-y+1/4,-x+1/4"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "y+3/4,x+1/4,-z+1/4", "-y+3/4,-x+3/4,-z+3/4", "y+1/4,-x+1/4,z+3/4", "-y+1/4,x+3/4,z+1/4", "x+3/4,z+1/4,-y+1/4", "-x+1/4,z+3/4,y+1/4", "-x+3/4,-z+3/4,-y+3/4", "x+1/4,-z+1/4,y+3/4", "z+3/4,y+1/4,-x+1/4", "z+1/4,-y+1/4,x+3/4", "-z+1/4,y+3/4,x+1/4", "-z+3/4,-y+3/4,-x+3/4"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "y+3/4,x+1/4,-z+1/4", "-y+3/4,-x+3/4,-z+3/4", "y+1/4,-x+1/4,z+3/4", "-y+1/4,x+3/4,z+1/4", "x+3/4,z+1/4,-y+1/4", "-x+1/4,z+3/4,y+1/4", "-x+3/4,-z+3/4,-y+3/4", "x+1/4,-z+1/4,y+3/4", "z+3/4,y+1/4,-x+1/4", "z+1/4,-y+1/4,x+3/4", "-z+1/4,y+3/4,x+1/4", "-z+3/4,-y+3/4,-x+3/4", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-x+1/2,y,-z", "x,-y,-z+1/2", "z+1/2,x+1/2,y+1/2", "z,-x,-y+1/2", "-z,-x+1/2,y", "-z+1/2,x,-y", "y+1/2,z+1/2,x+1/2", "-y+1/2,z,-x", "y,-z,-x+1/2", "-y,-z+1/2,x", "y+1/4,x+3/4,-z+3/4", "-y+1/4,-x+1/4,-z+1/4", "y+3/4,-x+3/4,z+1/4", "-y+3/4,x+1/4,z+3/4", "x+1/4,z+3/4,-y+3/4", "-x+3/4,z+1/4,y+3/4", "-x+1/4,-z+1/4,-y+1/4", "x+3/4,-z+3/4,y+1/4", "z+1/4,y+3/4,-x+3/4", "z+3/4,-y+3/4,x+1/4", "-z+3/4,y+1/4,x+3/4", "-z+1/4,-y+1/4,-x+1/4"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,z", "-y,-x,z", "y,-x,-z", "-y,x,-z", "x,z,y", "-x,z,-y", "-x,-z,y", "x,-z,-y", "z,y,x", "z,-y,-x", "-z,y,-x", "-z,-y,x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,z", "-y,-x,z", "y,-x,-z", "-y,x,-z", "x,z,y", "-x,z,-y", "-x,-z,y", "x,-z,-y", "z,y,x", "z,-y,-x", "-z,y,-x", "-z,-y,x", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "z,x+1/2,y+1/2", "z,-x+1/2,-y+1/2", "-z,-x+1/2,y+1/2", "-z,x+1/2,-y+1/2", "y,z+1/2,x+1/2", "-y,z+1/2,-x+1/2", "y,-z+1/2,-x+1/2", "-y,-z+1/2,x+1/2", "y,x+1/2,z+1/2", "-y,-x+1/2,z+1/2", "y,-x+1/2,-z+1/2", "-y,x+1/2,-z+1/2", "x,z+1/2,y+1/2", "-x,z+1/2,-y+1/2", "-x,-z+1/2,y+1/2", "x,-z+1/2,-y+1/2", "z,y+1/2,x+1/2", "z,-y+1/2,-x+1/2", "-z,y+1/2,-x+1/2", "-z,-y+1/2,x+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "z+1/2,x,y+1/2", "z+1/2,-x,-y+1/2", "-z+1/2,-x,y+1/2", "-z+1/2,x,-y+1/2", "y+1/2,z,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z,-x+1/2", "-y+1/2,-z,x+1/2", "y+1/2,x,z+1/2", "-y+1/2,-x,z+1/2", "y+1/2,-x,-z+1/2", "-y+1/2,x,-z+1/2", "x+1/2,z,y+1/2", "-x+1/2,z,-y+1/2", "-x+1/2,-z,y+1/2", "x+1/2,-z,-y+1/2", "z+1/2,y,x+1/2", "z+1/2,-y,-x+1/2", "-z+1/2,y,-x+1/2", "-z+1/2,-y,x+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "z+1/2,x+1/2,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x+1/2,y", "-z+1/2,x+1/2,-y", "y+1/2,z+1/2,x", "-y+1/2,z+1/2,-x", "y+1/2,-z+1/2,-x", "-y+1/2,-z+1/2,x", "y+1/2,x+1/2,z", "-y+1/2,-x+1/2,z", "y+1/2,-x+1/2,-z", "-y+1/2,x+1/2,-z", "x+1/2,z+1/2,y", "-x+1/2,z+1/2,-y", "-x+1/2,-z+1/2,y", "x+1/2,-z+1/2,-y", "z+1/2,y+1/2,x", "z+1/2,-y+1/2,-x", "-z+1/2,y+1/2,-x", "-z+1/2,-y+1/2,x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,z", "-y,-x,z", "y,-x,-z", "-y,x,-z", "x,z,y", "-x,z,-y", "-x,-z,y", "x,-z,-y", "z,y,x", "z,-y,-x", "-z,y,-x", "-z,-y,x", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "z+1/2,x+1/2,y+1/2", "z+1/2,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y+1/2", "-z+1/2,x+1/2,-y+1/2", "y+1/2,z+1/2,x+1/2", "-y+1/2,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x+1/2", "y+1/2,x+1/2,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,z+1/2,y+1/2", "-x+1/2,z+1/2,-y+1/2", "-x+1/2,-z+1/2,y+1/2", "x+1/2,-z+1/2,-y+1/2", "z+1/2,y+1/2,x+1/2", "z+1/2,-y+1/2,-x+1/2", "-z+1/2,y+1/2,-x+1/2", "-z+1/2,-y+1/2,x+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y+1/2,x+1/2,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,z+1/2,y+1/2", "-x+1/2,z+1/2,-y+1/2", "-x+1/2,-z+1/2,y+1/2", "x+1/2,-z+1/2,-y+1/2", "z+1/2,y+1/2,x+1/2", "z+1/2,-y+1/2,-x+1/2", "-z+1/2,y+1/2,-x+1/2", "-z+1/2,-y+1/2,x+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y+1/2,x+1/2,z+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,-x+1/2,-z+1/2", "-y+1/2,x+1/2,-z+1/2", "x+1/2,z+1/2,y+1/2", "-x+1/2,z+1/2,-y+1/2", "-x+1/2,-z+1/2,y+1/2", "x+1/2,-z+1/2,-y+1/2", "z+1/2,y+1/2,x+1/2", "z+1/2,-y+1/2,-x+1/2", "-z+1/2,y+1/2,-x+1/2", "-z+1/2,-y+1/2,x+1/2", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "z,x+1/2,y+1/2", "z,-x+1/2,-y+1/2", "-z,-x+1/2,y+1/2", "-z,x+1/2,-y+1/2", "y,z+1/2,x+1/2", "-y,z+1/2,-x+1/2", "y,-z+1/2,-x+1/2", "-y,-z+1/2,x+1/2", "y+1/2,x,z", "-y+1/2,-x,z", "y+1/2,-x,-z", "-y+1/2,x,-z", "x+1/2,z,y", "-x+1/2,z,-y", "-x+1/2,-z,y", "x+1/2,-z,-y", "z+1/2,y,x", "z+1/2,-y,-x", "-z+1/2,y,-x", "-z+1/2,-y,x", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "z+1/2,x,y+1/2", "z+1/2,-x,-y+1/2", "-z+1/2,-x,y+1/2", "-z+1/2,x,-y+1/2", "y+1/2,z,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z,-x+1/2", "-y+1/2,-z,x+1/2", "y,x+1/2,z", "-y,-x+1/2,z", "y,-x+1/2,-z", "-y,x+1/2,-z", "x,z+1/2,y", "-x,z+1/2,-y", "-x,-z+1/2,y", "x,-z+1/2,-y", "z,y+1/2,x", "z,-y+1/2,-x", "-z,y+1/2,-x", "-z,-y+1/2,x", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "z+1/2,x+1/2,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x+1/2,y", "-z+1/2,x+1/2,-y", "y+1/2,z+1/2,x", "-y+1/2,z+1/2,-x", "y+1/2,-z+1/2,-x", "-y+1/2,-z+1/2,x", "y,x,z+1/2", "-y,-x,z+1/2", "y,-x,-z+1/2", "-y,x,-z+1/2", "x,z,y+1/2", "-x,z,-y+1/2", "-x,-z,y+1/2", "x,-z,-y+1/2", "z,y,x+1/2", "z,-y,-x+1/2", "-z,y,-x+1/2", "-z,-y,x+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "y+1/4,x+1/4,z+1/4", "-y+1/4,-x+3/4,z+3/4", "y+3/4,-x+1/4,-z+3/4", "-y+3/4,x+3/4,-z+1/4", "x+1/4,z+1/4,y+1/4", "-x+3/4,z+3/4,-y+1/4", "-x+1/4,-z+3/4,y+3/4", "x+3/4,-z+1/4,-y+3/4", "z+1/4,y+1/4,x+1/4", "z+3/4,-y+1/4,-x+3/4", "-z+3/4,y+3/4,-x+1/4", "-z+1/4,-y+3/4,x+3/4", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-x+1/2,y,-z", "x,-y,-z+1/2", "z+1/2,x+1/2,y+1/2", "z,-x,-y+1/2", "-z,-x+1/2,y", "-z+1/2,x,-y", "y+1/2,z+1/2,x+1/2", "-y+1/2,z,-x", "y,-z,-x+1/2", "-y,-z+1/2,x", "y+3/4,x+3/4,z+3/4", "-y+3/4,-x+1/4,z+1/4", "y+1/4,-x+3/4,-z+1/4", "-y+1/4,x+1/4,-z+3/4", "x+3/4,z+3/4,y+3/4", "-x+1/4,z+1/4,-y+3/4", "-x+3/4,-z+1/4,y+1/4", "x+1/4,-z+3/4,-y+1/4", "z+3/4,y+3/4,x+3/4", "z+1/4,-y+3/4,-x+1/4", "-z+1/4,y+1/4,-x+3/4", "-z+3/4,-y+1/4,x+1/4"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,-z", "-y,-x,-z", "y,-x,z", "-y,x,z", "x,z,-y", "-x,z,y", "-x,-z,-y", "x,-z,y", "z,y,-x", "z,-y,x", "-z,y,x", "-z,-y,-x", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x", "-y,-x,z", "y,x,z", "-y,x,-z", "y,-x,-z", "-x,-z,y", "x,-z,-y", "x,z,y", "-x,z,-y", "-z,-y,x", "-z,y,-x", "z,-y,-x", "z,y,x"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-x+1/2,y,-z+1/2", "x,-y+1/2,-z+1/2", "z,x,y", "z,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y", "-z+1/2,x,-y+1/2", "y,z,x", "-y+1/2,z,-x+1/2", "y,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x", "y,x,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "y,-x+1/2,z", "-y+1/2,x,z", "x,z,-y+1/2", "-x+1/2,z,y", "-x+1/2,-z+1/2,-y+1/2", "x,-z+1/2,y", "z,y,-x+1/2", "z,-y+1/2,x", "-z+1/2,y,x", "-z+1/2,-y+1/2,-x+1/2", "-x,-y,-z", "x+1/2,y+1/2,-z", "x+1/2,-y,z+1/2", "-x,y+1/2,z+1/2", "-z,-x,-y", "-z,x+1/2,y+1/2", "z+1/2,x+1/2,-y", "z+1/2,-x,y+1/2", "-y,-z,-x", "y+1/2,-z,x+1/2", "-y,z+1/2,x+1/2", "y+1/2,z+1/2,-x", "-y,-x,z+1/2", "y+1/2,x+1/2,z+1/2", "-y,x+1/2,-z", "y+1/2,-x,-z", "-x,-z,y+1/2", "x+1/2,-z,-y", "x+1/2,z+1/2,y+1/2", "-x,z+1/2,-y", "-z,-y,x+1/2", "-z,y+1/2,-x", "z+1/2,-y,-x", "z+1/2,y+1/2,x+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "y+1/2,-x+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "x+1/2,z+1/2,-y+1/2", "-x+1/2,z+1/2,y+1/2", "-x+1/2,-z+1/2,-y+1/2", "x+1/2,-z+1/2,y+1/2", "z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,x+1/2", "-z+1/2,-y+1/2,-x+1/2", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2", "-y+1/2,x+1/2,-z+1/2", "y+1/2,-x+1/2,-z+1/2", "-x+1/2,-z+1/2,y+1/2", "x+1/2,-z+1/2,-y+1/2", "x+1/2,z+1/2,y+1/2", "-x+1/2,z+1/2,-y+1/2", "-z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,-x+1/2", "z+1/2,y+1/2,x+1/2"},
			{"x,y,z", "-x+1/2,-y+1/2,z", "-x+1/2,y,-z+1/2", "x,-y+1/2,-z+1/2", "z,x,y", "z,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y", "-z+1/2,x,-y+1/2", "y,z,x", "-y+1/2,z,-x+1/2", "y,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x", "y+1/2,x+1/2,-z", "-y,-x,-z", "y+1/2,-x,z+1/2", "-y,x+1/2,z+1/2", "x+1/2,z+1/2,-y", "-x,z+1/2,y+1/2", "-x,-z,-y", "x+1/2,-z,y+1/2", "z+1/2,y+1/2,-x", "z+1/2,-y,x+1/2", "-z,y+1/2,x+1/2", "-z,-y,-x", "-x,-y,-z", "x+1/2,y+1/2,-z", "x+1/2,-y,z+1/2", "-x,y+1/2,z+1/2", "-z,-x,-y", "-z,x+1/2,y+1/2", "z+1/2,x+1/2,-y", "z+1/2,-x,y+1/2", "-y,-z,-x", "y+1/2,-z,x+1/2", "-y,z+1/2,x+1/2", "y+1/2,z+1/2,-x", "-y+1/2,-x+1/2,z", "y,x,z", "-y+1/2,x,-z+1/2", "y,-x+1/2,-z+1/2", "-x+1/2,-z+1/2,y", "x,-z+1/2,-y+1/2", "x,z,y", "-x+1/2,z,-y+1/2", "-z+1/2,-y+1/2,x", "-z+1/2,y,-x+1/2", "z,-y+1/2,-x+1/2", "z,y,x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,-z", "-y,-x,-z", "y,-x,z", "-y,x,z", "x,z,-y", "-x,z,y", "-x,-z,-y", "x,-z,y", "z,y,-x", "z,-y,x", "-z,y,x", "-z,-y,-x", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x", "-y,-x,z", "y,x,z", "-y,x,-z", "y,-x,-z", "-x,-z,y", "x,-z,-y", "x,z,y", "-x,z,-y", "-z,-y,x", "-z,y,-x", "z,-y,-x", "z,y,x", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "z,x+1/2,y+1/2", "z,-x+1/2,-y+1/2", "-z,-x+1/2,y+1/2", "-z,x+1/2,-y+1/2", "y,z+1/2,x+1/2", "-y,z+1/2,-x+1/2", "y,-z+1/2,-x+1/2", "-y,-z+1/2,x+1/2", "y,x+1/2,-z+1/2", "-y,-x+1/2,-z+1/2", "y,-x+1/2,z+1/2", "-y,x+1/2,z+1/2", "x,z+1/2,-y+1/2", "-x,z+1/2,y+1/2", "-x,-z+1/2,-y+1/2", "x,-z+1/2,y+1/2", "z,y+1/2,-x+1/2", "z,-y+1/2,x+1/2", "-z,y+1/2,x+1/2", "-z,-y+1/2,-x+1/2", "-x,-y+1/2,-z+1/2", "x,y+1/2,-z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2", "-z,-x+1/2,-y+1/2", "-z,x+1/2,y+1/2", "z,x+1/2,-y+1/2", "z,-x+1/2,y+1/2", "-y,-z+1/2,-x+1/2", "y,-z+1/2,x+1/2", "-y,z+1/2,x+1/2", "y,z+1/2,-x+1/2", "-y,-x+1/2,z+1/2", "y,x+1/2,z+1/2", "-y,x+1/2,-z+1/2", "y,-x+1/2,-z+1/2", "-x,-z+1/2,y+1/2", "x,-z+1/2,-y+1/2", "x,z+1/2,y+1/2", "-x,z+1/2,-y+1/2", "-z,-y+1/2,x+1/2", "-z,y+1/2,-x+1/2", "z,-y+1/2,-x+1/2", "z,y+1/2,x+1/2", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "z+1/2,x,y+1/2", "z+1/2,-x,-y+1/2", "-z+1/2,-x,y+1/2", "-z+1/2,x,-y+1/2", "y+1/2,z,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z,-x+1/2", "-y+1/2,-z,x+1/2", "y+1/2,x,-z+1/2", "-y+1/2,-x,-z+1/2", "y+1/2,-x,z+1/2", "-y+1/2,x,z+1/2", "x+1/2,z,-y+1/2", "-x+1/2,z,y+1/2", "-x+1/2,-z,-y+1/2", "x+1/2,-z,y+1/2", "z+1/2,y,-x+1/2", "z+1/2,-y,x+1/2", "-z+1/2,y,x+1/2", "-z+1/2,-y,-x+1/2", "-x+1/2,-y,-z+1/2", "x+1/2,y,-z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y,z+1/2", "-z+1/2,-x,-y+1/2", "-z+1/2,x,y+1/2", "z+1/2,x,-y+1/2", "z+1/2,-x,y+1/2", "-y+1/2,-z,-x+1/2", "y+1/2,-z,x+1/2", "-y+1/2,z,x+1/2", "y+1/2,z,-x+1/2", "-y+1/2,-x,z+1/2", "y+1/2,x,z+1/2", "-y+1/2,x,-z+1/2", "y+1/2,-x,-z+1/2", "-x+1/2,-z,y+1/2", "x+1/2,-z,-y+1/2", "x+1/2,z,y+1/2", "-x+1/2,z,-y+1/2", "-z+1/2,-y,x+1/2", "-z+1/2,y,-x+1/2", "z+1/2,-y,-x+1/2", "z+1/2,y,x+1/2", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "z+1/2,x+1/2,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x+1/2,y", "-z+1/2,x+1/2,-y", "y+1/2,z+1/2,x", "-y+1/2,z+1/2,-x", "y+1/2,-z+1/2,-x", "-y+1/2,-z+1/2,x", "y+1/2,x+1/2,-z", "-y+1/2,-x+1/2,-z", "y+1/2,-x+1/2,z", "-y+1/2,x+1/2,z", "x+1/2,z+1/2,-y", "-x+1/2,z+1/2,y", "-x+1/2,-z+1/2,-y", "x+1/2,-z+1/2,y", "z+1/2,y+1/2,-x", "z+1/2,-y+1/2,x", "-z+1/2,y+1/2,x", "-z+1/2,-y+1/2,-x", "-x+1/2,-y+1/2,-z", "x+1/2,y+1/2,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-z+1/2,-x+1/2,-y", "-z+1/2,x+1/2,y", "z+1/2,x+1/2,-y", "z+1/2,-x+1/2,y", "-y+1/2,-z+1/2,-x", "y+1/2,-z+1/2,x", "-y+1/2,z+1/2,x", "y+1/2,z+1/2,-x", "-y+1/2,-x+1/2,z", "y+1/2,x+1/2,z", "-y+1/2,x+1/2,-z", "y+1/2,-x+1/2,-z", "-x+1/2,-z+1/2,y", "x+1/2,-z+1/2,-y", "x+1/2,z+1/2,y", "-x+1/2,z+1/2,-y", "-z+1/2,-y+1/2,x", "-z+1/2,y+1/2,-x", "z+1/2,-y+1/2,-x", "z+1/2,y+1/2,x"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "y+1/2,-x+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "x+1/2,z+1/2,-y+1/2", "-x+1/2,z+1/2,y+1/2", "-x+1/2,-z+1/2,-y+1/2", "x+1/2,-z+1/2,y+1/2", "z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,x+1/2", "-z+1/2,-y+1/2,-x+1/2", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2", "-y+1/2,x+1/2,-z+1/2", "y+1/2,-x+1/2,-z+1/2", "-x+1/2,-z+1/2,y+1/2", "x+1/2,-z+1/2,-y+1/2", "x+1/2,z+1/2,y+1/2", "-x+1/2,z+1/2,-y+1/2", "-z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,-x+1/2", "z+1/2,y+1/2,x+1/2", "x,y+1/2,z+1/2", "-x,-y+1/2,z+1/2", "-x,y+1/2,-z+1/2", "x,-y+1/2,-z+1/2", "z,x+1/2,y+1/2", "z,-x+1/2,-y+1/2", "-z,-x+1/2,y+1/2", "-z,x+1/2,-y+1/2", "y,z+1/2,x+1/2", "-y,z+1/2,-x+1/2", "y,-z+1/2,-x+1/2", "-y,-z+1/2,x+1/2", "y+1/2,x,-z", "-y+1/2,-x,-z", "y+1/2,-x,z", "-y+1/2,x,z", "x+1/2,z,-y", "-x+1/2,z,y", "-x+1/2,-z,-y", "x+1/2,-z,y", "z+1/2,y,-x", "z+1/2,-y,x", "-z+1/2,y,x", "-z+1/2,-y,-x", "-x,-y+1/2,-z+1/2", "x,y+1/2,-z+1/2", "x,-y+1/2,z+1/2", "-x,y+1/2,z+1/2", "-z,-x+1/2,-y+1/2", "-z,x+1/2,y+1/2", "z,x+1/2,-y+1/2", "z,-x+1/2,y+1/2", "-y,-z+1/2,-x+1/2", "y,-z+1/2,x+1/2", "-y,z+1/2,x+1/2", "y,z+1/2,-x+1/2", "-y+1/2,-x,z", "y+1/2,x,z", "-y+1/2,x,-z", "y+1/2,-x,-z", "-x+1/2,-z,y", "x+1/2,-z,-y", "x+1/2,z,y", "-x+1/2,z,-y", "-z+1/2,-y,x", "-z+1/2,y,-x", "z+1/2,-y,-x", "z+1/2,y,x", "x+1/2,y,z+1/2", "-x+1/2,-y,z+1/2", "-x+1/2,y,-z+1/2", "x+1/2,-y,-z+1/2", "z+1/2,x,y+1/2", "z+1/2,-x,-y+1/2", "-z+1/2,-x,y+1/2", "-z+1/2,x,-y+1/2", "y+1/2,z,x+1/2", "-y+1/2,z,-x+1/2", "y+1/2,-z,-x+1/2", "-y+1/2,-z,x+1/2", "y,x+1/2,-z", "-y,-x+1/2,-z", "y,-x+1/2,z", "-y,x+1/2,z", "x,z+1/2,-y", "-x,z+1/2,y", "-x,-z+1/2,-y", "x,-z+1/2,y", "z,y+1/2,-x", "z,-y+1/2,x", "-z,y+1/2,x", "-z,-y+1/2,-x", "-x+1/2,-y,-z+1/2", "x+1/2,y,-z+1/2", "x+1/2,-y,z+1/2", "-x+1/2,y,z+1/2", "-z+1/2,-x,-y+1/2", "-z+1/2,x,y+1/2", "z+1/2,x,-y+1/2", "z+1/2,-x,y+1/2", "-y+1/2,-z,-x+1/2", "y+1/2,-z,x+1/2", "-y+1/2,z,x+1/2", "y+1/2,z,-x+1/2", "-y,-x+1/2,z", "y,x+1/2,z", "-y,x+1/2,-z", "y,-x+1/2,-z", "-x,-z+1/2,y", "x,-z+1/2,-y", "x,z+1/2,y", "-x,z+1/2,-y", "-z,-y+1/2,x", "-z,y+1/2,-x", "z,-y+1/2,-x", "z,y+1/2,x", "x+1/2,y+1/2,z", "-x+1/2,-y+1/2,z", "-x+1/2,y+1/2,-z", "x+1/2,-y+1/2,-z", "z+1/2,x+1/2,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x+1/2,y", "-z+1/2,x+1/2,-y", "y+1/2,z+1/2,x", "-y+1/2,z+1/2,-x", "y+1/2,-z+1/2,-x", "-y+1/2,-z+1/2,x", "y,x,-z+1/2", "-y,-x,-z+1/2", "y,-x,z+1/2", "-y,x,z+1/2", "x,z,-y+1/2", "-x,z,y+1/2", "-x,-z,-y+1/2", "x,-z,y+1/2", "z,y,-x+1/2", "z,-y,x+1/2", "-z,y,x+1/2", "-z,-y,-x+1/2", "-x+1/2,-y+1/2,-z", "x+1/2,y+1/2,-z", "x+1/2,-y+1/2,z", "-x+1/2,y+1/2,z", "-z+1/2,-x+1/2,-y", "-z+1/2,x+1/2,y", "z+1/2,x+1/2,-y", "z+1/2,-x+1/2,y", "-y+1/2,-z+1/2,-x", "y+1/2,-z+1/2,x", "-y+1/2,z+1/2,x", "y+1/2,z+1/2,-x", "-y,-x,z+1/2", "y,x,z+1/2", "-y,x,-z+1/2", "y,-x,-z+1/2", "-x,-z,y+1/2", "x,-z,-y+1/2", "x,z,y+1/2", "-x,z,-y+1/2", "-z,-y,x+1/2", "-z,y,-x+1/2", "z,-y,-x+1/2", "z,y,x+1/2"},
			{"x,y,z", "-x+3/4,-y+1/4,z+1/2", "-x+1/4,y+1/2,-z+3/4", "x+1/2,-y+3/4,-z+1/4", "z,x,y", "z+1/2,-x+3/4,-y+1/4", "-z+3/4,-x+1/4,y+1/2", "-z+1/4,x+1/2,-y+3/4", "y,z,x", "-y+1/4,z+1/2,-x+3/4", "y+1/2,-z+3/4,-x+1/4", "-y+3/4,-z+1/4,x+1/2", "y+3/4,x+1/4,-z+1/2", "-y,-x,-z", "y+1/4,-x+1/2,z+3/4", "-y+1/2,x+3/4,z+1/4", "x+3/4,z+1/4,-y+1/2", "-x+1/2,z+3/4,y+1/4", "-x,-z,-y", "x+1/4,-z+1/2,y+3/4", "z+3/4,y+1/4,-x+1/2", "z+1/4,-y+1/2,x+3/4", "-z+1/2,y+3/4,x+1/4", "-z,-y,-x", "-x,-y,-z", "x+1/4,y+3/4,-z+1/2", "x+3/4,-y+1/2,z+1/4", "-x+1/2,y+1/4,z+3/4", "-z,-x,-y", "-z+1/2,x+1/4,y+3/4", "z+1/4,x+3/4,-y+1/2", "z+3/4,-x+1/2,y+1/4", "-y,-z,-x", "y+3/4,-z+1/2,x+1/4", "-y+1/2,z+1/4,x+3/4", "y+1/4,z+3/4,-x+1/2", "-y+1/4,-x+3/4,z+1/2", "y,x,z", "-y+3/4,x+1/2,-z+1/4", "y+1/2,-x+1/4,-z+3/4", "-x+1/4,-z+3/4,y+1/2", "x+1/2,-z+1/4,-y+3/4", "x,z,y", "-x+3/4,z+1/2,-y+1/4", "-z+1/4,-y+3/4,x+1/2", "-z+3/4,y+1/2,-x+1/4", "z+1/2,-y+1/4,-x+3/4", "z,y,x", "x,y+1/2,z+1/2", "-x+3/4,-y+3/4,z", "-x+1/4,y,-z+1/4", "x+1/2,-y+1/4,-z+3/4", "z,x+1/2,y+1/2", "z+1/2,-x+1/4,-y+3/4", "-z+3/4,-x+3/4,y", "-z+1/4,x,-y+1/4", "y,z+1/2,x+1/2", "-y+1/4,z,-x+1/4", "y+1/2,-z+1/4,-x+3/4", "-y+3/4,-z+3/4,x", "y+3/4,x+3/4,-z", "-y,-x+1/2,-z+1/2", "y+1/4,-x,z+1/4", "-y+1/2,x+1/4,z+3/4", "x+3/4,z+3/4,-y", "-x+1/2,z+1/4,y+3/4", "-x,-z+1/2,-y+1/2", "x+1/4,-z,y+1/4", "z+3/4,y+3/4,-x", "z+1/4,-y,x+1/4", "-z+1/2,y+1/4,x+3/4", "-z,-y+1/2,-x+1/2", "-x,-y+1/2,-z+1/2", "x+1/4,y+1/4,-z", "x+3/4,-y,z+3/4", "-x+1/2,y+3/4,z+1/4", "-z,-x+1/2,-y+1/2", "-z+1/2,x+3/4,y+1/4", "z+1/4,x+1/4,-y", "z+3/4,-x,y+3/4", "-y,-z+1/2,-x+1/2", "y+3/4,-z,x+3/4", "-y+1/2,z+3/4,x+1/4", "y+1/4,z+1/4,-x", "-y+1/4,-x+1/4,z", "y,x+1/2,z+1/2", "-y+3/4,x,-z+3/4", "y+1/2,-x+3/4,-z+1/4", "-x+1/4,-z+1/4,y", "x+1/2,-z+3/4,-y+1/4", "x,z+1/2,y+1/2", "-x+3/4,z,-y+3/4", "-z+1/4,-y+1/4,x", "-z+3/4,y,-x+3/4", "z+1/2,-y+3/4,-x+1/4", "z,y+1/2,x+1/2", "x+1/2,y,z+1/2", "-x+1/4,-y+1/4,z", "-x+3/4,y+1/2,-z+1/4", "x,-y+3/4,-z+3/4", "z+1/2,x,y+1/2", "z,-x+3/4,-y+3/4", "-z+1/4,-x+1/4,y", "-z+3/4,x+1/2,-y+1/4", "y+1/2,z,x+1/2", "-y+3/4,z+1/2,-x+1/4", "y,-z+3/4,-x+3/4", "-y+1/4,-z+1/4,x", "y+1/4,x+1/4,-z", "-y+1/2,-x,-z+1/2", "y+3/4,-x+1/2,z+1/4", "-y,x+3/4,z+3/4", "x+1/4,z+1/4,-y", "-x,z+3/4,y+3/4", "-x+1/2,-z,-y+1/2", "x+3/4,-z+1/2,y+1/4", "z+1/4,y+1/4,-x", "z+3/4,-y+1/2,x+1/4", "-z,y+3/4,x+3/4", "-z+1/2,-y,-x+1/2", "-x+1/2,-y,-z+1/2", "x+3/4,y+3/4,-z", "x+1/4,-y+1/2,z+3/4", "-x,y+1/4,z+1/4", "-z+1/2,-x,-y+1/2", "-z,x+1/4,y+1/4", "z+3/4,x+3/4,-y", "z+1/4,-x+1/2,y+3/4", "-y+1/2,-z,-x+1/2", "y+1/4,-z+1/2,x+3/4", "-y,z+1/4,x+1/4", "y+3/4,z+3/4,-x", "-y+3/4,-x+3/4,z", "y+1/2,x,z+1/2", "-y+1/4,x+1/2,-z+3/4", "y,-x+1/4,-z+1/4", "-x+3/4,-z+3/4,y", "x,-z+1/4,-y+1/4", "x+1/2,z,y+1/2", "-x+1/4,z+1/2,-y+3/4", "-z+3/4,-y+3/4,x", "-z+1/4,y+1/2,-x+3/4", "z,-y+1/4,-x+1/4", "z+1/2,y,x+1/2", "x+1/2,y+1/2,z", "-x+1/4,-y+3/4,z+1/2", "-x+3/4,y,-z+3/4", "x,-y+1/4,-z+1/4", "z+1/2,x+1/2,y", "z,-x+1/4,-y+1/4", "-z+1/4,-x+3/4,y+1/2", "-z+3/4,x,-y+3/4", "y+1/2,z+1/2,x", "-y+3/4,z,-x+3/4", "y,-z+1/4,-x+1/4", "-y+1/4,-z+3/4,x+1/2", "y+1/4,x+3/4,-z+1/2", "-y+1/2,-x+1/2,-z", "y+3/4,-x,z+3/4", "-y,x+1/4,z+1/4", "x+1/4,z+3/4,-y+1/2", "-x,z+1/4,y+1/4", "-x+1/2,-z+1/2,-y", "x+3/4,-z,y+3/4", "z+1/4,y+3/4,-x+1/2", "z+3/4,-y,x+3/4", "-z,y+1/4,x+1/4", "-z+1/2,-y+1/2,-x", "-x+1/2,-y+1/2,-z", "x+3/4,y+1/4,-z+1/2", "x+1/4,-y,z+1/4", "-x,y+3/4,z+3/4", "-z+1/2,-x+1/2,-y", "-z,x+3/4,y+3/4", "z+3/4,x+1/4,-y+1/2", "z+1/4,-x,y+1/4", "-y+1/2,-z+1/2,-x", "y+1/4,-z,x+1/4", "-y,z+3/4,x+3/4", "y+3/4,z+1/4,-x+1/2", "-y+3/4,-x+1/4,z+1/2", "y+1/2,x+1/2,z", "-y+1/4,x,-z+1/4", "y,-x+3/4,-z+3/4", "-x+3/4,-z+1/4,y+1/2", "x,-z+3/4,-y+3/4", "x+1/2,z+1/2,y", "-x+1/4,z,-y+1/4", "-z+3/4,-y+1/4,x+1/2", "-z+1/4,y,-x+1/4", "z,-y+3/4,-x+3/4", "z+1/2,y+1/2,x"},
			{"x,y,z", "-x+1/4,-y+3/4,z+1/2", "-x+3/4,y+1/2,-z+1/4", "x+1/2,-y+1/4,-z+3/4", "z,x,y", "z+1/2,-x+1/4,-y+3/4", "-z+1/4,-x+3/4,y+1/2", "-z+3/4,x+1/2,-y+1/4", "y,z,x", "-y+3/4,z+1/2,-x+1/4", "y+1/2,-z+1/4,-x+3/4", "-y+1/4,-z+3/4,x+1/2", "y+3/4,x+1/4,-z", "-y+1/2,-x+1/2,-z+1/2", "y+1/4,-x,z+3/4", "-y,x+3/4,z+1/4", "x+3/4,z+1/4,-y", "-x,z+3/4,y+1/4", "-x+1/2,-z+1/2,-y+1/2", "x+1/4,-z,y+3/4", "z+3/4,y+1/4,-x", "z+1/4,-y,x+3/4", "-z,y+3/4,x+1/4", "-z+1/2,-y+1/2,-x+1/2", "-x,-y,-z", "x+3/4,y+1/4,-z+1/2", "x+1/4,-y+1/2,z+3/4", "-x+1/2,y+3/4,z+1/4", "-z,-x,-y", "-z+1/2,x+3/4,y+1/4", "z+3/4,x+1/4,-y+1/2", "z+1/4,-x+1/2,y+3/4", "-y,-z,-x", "y+1/4,-z+1/2,x+3/4", "-y+1/2,z+3/4,x+1/4", "y+3/4,z+1/4,-x+1/2", "-y+1/4,-x+3/4,z", "y+1/2,x+1/2,z+1/2", "-y+3/4,x,-z+1/4", "y,-x+1/4,-z+3/4", "-x+1/4,-z+3/4,y", "x,-z+1/4,-y+3/4", "x+1/2,z+1/2,y+1/2", "-x+3/4,z,-y+1/4", "-z+1/4,-y+3/4,x", "-z+3/4,y,-x+1/4", "z,-y+1/4,-x+3/4", "z+1/2,y+1/2,x+1/2", "x,y+1/2,z+1/2", "-x+1/4,-y+1/4,z", "-x+3/4,y,-z+3/4", "x+1/2,-y+3/4,-z+1/4", "z,x+1/2,y+1/2", "z+1/2,-x+3/4,-y+1/4", "-z+1/4,-x+1/4,y", "-z+3/4,x,-y+3/4", "y,z+1/2,x+1/2", "-y+3/4,z,-x+3/4", "y+1/2,-z+3/4,-x+1/4", "-y+1/4,-z+1/4,x", "y+3/4,x+3/4,-z+1/2", "-y+1/2,-x,-z", "y+1/4,-x+1/2,z+1/4", "-y,x+1/4,z+3/4", "x+3/4,z+3/4,-y+1/2", "-x,z+1/4,y+3/4", "-x+1/2,-z,-y", "x+1/4,-z+1/2,y+1/4", "z+3/4,y+3/4,-x+1/2", "z+1/4,-y+1/2,x+1/4", "-z,y+1/4,x+3/4", "-z+1/2,-y,-x", "-x,-y+1/2,-z+1/2", "x+3/4,y+3/4,-z", "x+1/4,-y,z+1/4", "-x+1/2,y+1/4,z+3/4", "-z,-x+1/2,-y+1/2", "-z+1/2,x+1/4,y+3/4", "z+3/4,x+3/4,-y", "z+1/4,-x,y+1/4", "-y,-z+1/2,-x+1/2", "y+1/4,-z,x+1/4", "-y+1/2,z+1/4,x+3/4", "y+3/4,z+3/4,-x", "-y+1/4,-x+1/4,z+1/2", "y+1/2,x,z", "-y+3/4,x+1/2,-z+3/4", "y,-x+3/4,-z+1/4", "-x+1/4,-z+1/4,y+1/2", "x,-z+3/4,-y+1/4", "x+1/2,z,y", "-x+3/4,z+1/2,-y+3/4", "-z+1/4,-y+1/4,x+1/2", "-z+3/4,y+1/2,-x+3/4", "z,-y+3/4,-x+1/4", "z+1/2,y,x", "x+1/2,y,z+1/2", "-x+3/4,-y+3/4,z", "-x+1/4,y+1/2,-z+3/4", "x,-y+1/4,-z+1/4", "z+1/2,x,y+1/2", "z,-x+1/4,-y+1/4", "-z+3/4,-x+3/4,y", "-z+1/4,x+1/2,-y+3/4", "y+1/2,z,x+1/2", "-y+1/4,z+1/2,-x+3/4", "y,-z+1/4,-x+1/4", "-y+3/4,-z+3/4,x", "y+1/4,x+1/4,-z+1/2", "-y,-x+1/2,-z", "y+3/4,-x,z+1/4", "-y+1/2,x+3/4,z+3/4", "x+1/4,z+1/4,-y+1/2", "-x+1/2,z+3/4,y+3/4", "-x,-z+1/2,-y", "x+3/4,-z,y+1/4", "z+1/4,y+1/4,-x+1/2", "z+3/4,-y,x+1/4", "-z+1/2,y+3/4,x+3/4", "-z,-y+1/2,-x", "-x+1/2,-y,-z+1/2", "x+1/4,y+1/4,-z", "x+3/4,-y+1/2,z+1/4", "-x,y+3/4,z+3/4", "-z+1/2,-x,-y+1/2", "-z,x+3/4,y+3/4", "z+1/4,x+1/4,-y", "z+3/4,-x+1/2,y+1/4", "-y+1/2,-z,-x+1/2", "y+3/4,-z+1/2,x+1/4", "-y,z+3/4,x+3/4", "y+1/4,z+1/4,-x", "-y+3/4,-x+3/4,z+1/2", "y,x+1/2,z", "-y+1/4,x,-z+3/4", "y+1/2,-x+1/4,-z+1/4", "-x+3/4,-z+3/4,y+1/2", "x+1/2,-z+1/4,-y+1/4", "x,z+1/2,y", "-x+1/4,z,-y+3/4", "-z+3/4,-y+3/4,x+1/2", "-z+1/4,y,-x+3/4", "z+1/2,-y+1/4,-x+1/4", "z,y+1/2,x", "x+1/2,y+1/2,z", "-x+3/4,-y+1/4,z+1/2", "-x+1/4,y,-z+1/4", "x,-y+3/4,-z+3/4", "z+1/2,x+1/2,y", "z,-x+3/4,-y+3/4", "-z+3/4,-x+1/4,y+1/2", "-z+1/4,x,-y+1/4", "y+1/2,z+1/2,x", "-y+1/4,z,-x+1/4", "y,-z+3/4,-x+3/4", "-y+3/4,-z+1/4,x+1/2", "y+1/4,x+3/4,-z", "-y,-x,-z+1/2", "y+3/4,-x+1/2,z+3/4", "-y+1/2,x+1/4,z+1/4", "x+1/4,z+3/4,-y", "-x+1/2,z+1/4,y+1/4", "-x,-z,-y+1/2", "x+3/4,-z+1/2,y+3/4", "z+1/4,y+3/4,-x", "z+3/4,-y+1/2,x+3/4", "-z+1/2,y+1/4,x+1/4", "-z,-y,-x+1/2", "-x+1/2,-y+1/2,-z", "x+1/4,y+3/4,-z+1/2", "x+3/4,-y,z+3/4", "-x,y+1/4,z+1/4", "-z+1/2,-x+1/2,-y", "-z,x+1/4,y+1/4", "z+1/4,x+3/4,-y+1/2", "z+3/4,-x,y+3/4", "-y+1/2,-z+1/2,-x", "y+3/4,-z,x+3/4", "-y,z+1/4,x+1/4", "y+1/4,z+3/4,-x+1/2", "-y+3/4,-x+1/4,z", "y,x,z+1/2", "-y+1/4,x+1/2,-z+1/4", "y+1/2,-x+3/4,-z+3/4", "-x+3/4,-z+1/4,y", "x+1/2,-z+3/4,-y+3/4", "x,z,y+1/2", "-x+1/4,z+1/2,-y+1/4", "-z+3/4,-y+1/4,x", "-z+1/4,y+1/2,-x+1/4", "z+1/2,-y+3/4,-x+3/4", "z,y,x+1/2"},
			{"x,y,z", "-x,-y,z", "-x,y,-z", "x,-y,-z", "z,x,y", "z,-x,-y", "-z,-x,y", "-z,x,-y", "y,z,x", "-y,z,-x", "y,-z,-x", "-y,-z,x", "y,x,-z", "-y,-x,-z", "y,-x,z", "-y,x,z", "x,z,-y", "-x,z,y", "-x,-z,-y", "x,-z,y", "z,y,-x", "z,-y,x", "-z,y,x", "-z,-y,-x", "-x,-y,-z", "x,y,-z", "x,-y,z", "-x,y,z", "-z,-x,-y", "-z,x,y", "z,x,-y", "z,-x,y", "-y,-z,-x", "y,-z,x", "-y,z,x", "y,z,-x", "-y,-x,z", "y,x,z", "-y,x,-z", "y,-x,-z", "-x,-z,y", "x,-z,-y", "x,z,y", "-x,z,-y", "-z,-y,x", "-z,y,-x", "z,-y,-x", "z,y,x", "x+1/2,y+1/2,z+1/2", "-x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z+1/2", "z+1/2,x+1/2,y+1/2", "z+1/2,-x+1/2,-y+1/2", "-z+1/2,-x+1/2,y+1/2", "-z+1/2,x+1/2,-y+1/2", "y+1/2,z+1/2,x+1/2", "-y+1/2,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x+1/2", "-y+1/2,-z+1/2,x+1/2", "y+1/2,x+1/2,-z+1/2", "-y+1/2,-x+1/2,-z+1/2", "y+1/2,-x+1/2,z+1/2", "-y+1/2,x+1/2,z+1/2", "x+1/2,z+1/2,-y+1/2", "-x+1/2,z+1/2,y+1/2", "-x+1/2,-z+1/2,-y+1/2", "x+1/2,-z+1/2,y+1/2", "z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,x+1/2", "-z+1/2,-y+1/2,-x+1/2", "-x+1/2,-y+1/2,-z+1/2", "x+1/2,y+1/2,-z+1/2", "x+1/2,-y+1/2,z+1/2", "-x+1/2,y+1/2,z+1/2", "-z+1/2,-x+1/2,-y+1/2", "-z+1/2,x+1/2,y+1/2", "z+1/2,x+1/2,-y+1/2", "z+1/2,-x+1/2,y+1/2", "-y+1/2,-z+1/2,-x+1/2", "y+1/2,-z+1/2,x+1/2", "-y+1/2,z+1/2,x+1/2", "y+1/2,z+1/2,-x+1/2", "-y+1/2,-x+1/2,z+1/2", "y+1/2,x+1/2,z+1/2", "-y+1/2,x+1/2,-z+1/2", "y+1/2,-x+1/2,-z+1/2", "-x+1/2,-z+1/2,y+1/2", "x+1/2,-z+1/2,-y+1/2", "x+1/2,z+1/2,y+1/2", "-x+1/2,z+1/2,-y+1/2", "-z+1/2,-y+1/2,x+1/2", "-z+1/2,y+1/2,-x+1/2", "z+1/2,-y+1/2,-x+1/2", "z+1/2,y+1/2,x+1/2"},
			{"x,y,z", "-x+1/2,-y,z+1/2", "-x,y+1/2,-z+1/2", "x+1/2,-y+1/2,-z", "z,x,y", "z+1/2,-x+1/2,-y", "-z+1/2,-x,y+1/2", "-z,x+1/2,-y+1/2", "y,z,x", "-y,z+1/2,-x+1/2", "y+1/2,-z+1/2,-x", "-y+1/2,-z,x+1/2", "y+3/4,x+1/4,-z+1/4", "-y+3/4,-x+3/4,-z+3/4", "y+1/4,-x+1/4,z+3/4", "-y+1/4,x+3/4,z+1/4", "x+3/4,z+1/4,-y+1/4", "-x+1/4,z+3/4,y+1/4", "-x+3/4,-z+3/4,-y+3/4", "x+1/4,-z+1/4,y+3/4", "z+3/4,y+1/4,-x+1/4", "z+1/4,-y+1/4,x+3/4", "-z+1/4,y+3/4,x+1/4", "-z+3/4,-y+3/4,-x+3/4", "-x,-y,-z", "x+1/2,y,-z+1/2", "x,-y+1/2,z+1/2", "-x+1/2,y+1/2,z", "-z,-x,-y", "-z+1/2,x+1/2,y", "z+1/2,x,-y+1/2", "z,-x+1/2,y+1/2", "-y,-z,-x", "y,-z+1/2,x+1/2", "-y+1/2,z+1/2,x", "y+1/2,z,-x+1/2", "-y+1/4,-x+3/4,z+3/4", "y+1/4,x+1/4,z+1/4", "-y+3/4,x+3/4,-z+1/4", "y+3/4,-x+1/4,-z+3/4", "-x+1/4,-z+3/4,y+3/4", "x+3/4,-z+1/4,-y+3/4", "x+1/4,z+1/4,y+1/4", "-x+3/4,z+3/4,-y+1/4", "-z+1/4,-y+3/4,x+3/4", "-z+3/4,y+3/4,-x+1/4", "z+3/4,-y+1/4,-x+3/4", "z+1/4,y+1/4,x+1/4", "x+1/2,y+1/2,z+1/2", "-x,-y+1/2,z", "-x+1/2,y,-z", "x,-y,-z+1/2", "z+1/2,x+1/2,y+1/2", "z,-x,-y+1/2", "-z,-x+1/2,y", "-z+1/2,x,-y", "y+1/2,z+1/2,x+1/2", "-y+1/2,z,-x", "y,-z,-x+1/2", "-y,-z+1/2,x", "y+1/4,x+3/4,-z+3/4", "-y+1/4,-x+1/4,-z+1/4", "y+3/4,-x+3/4,z+1/4", "-y+3/4,x+1/4,z+3/4", "x+1/4,z+3/4,-y+3/4", "-x+3/4,z+1/4,y+3/4", "-x+1/4,-z+1/4,-y+1/4", "x+3/4,-z+3/4,y+1/4", "z+1/4,y+3/4,-x+3/4", "z+3/4,-y+3/4,x+1/4", "-z+3/4,y+1/4,x+3/4", "-z+1/4,-y+1/4,-x+1/4", "-x+1/2,-y+1/2,-z+1/2", "x,y+1/2,-z", "x+1/2,-y,z", "-x,y,z+1/2", "-z+1/2,-x+1/2,-y+1/2", "-z,x,y+1/2", "z,x+1/2,-y", "z+1/2,-x,y", "-y+1/2,-z+1/2,-x+1/2", "y+1/2,-z,x", "-y,z,x+1/2", "y,z+1/2,-x", "-y+3/4,-x+1/4,z+1/4", "y+3/4,x+3/4,z+3/4", "-y+1/4,x+1/4,-z+3/4", "y+1/4,-x+3/4,-z+1/4", "-x+3/4,-z+1/4,y+1/4", "x+1/4,-z+3/4,-y+1/4", "x+3/4,z+3/4,y+3/4", "-x+1/4,z+1/4,-y+3/4", "-z+3/4,-y+1/4,x+1/4", "-z+1/4,y+1/4,-x+3/4", "z+1/4,-y+3/4,-x+1/4", "z+3/4,y+3/4,x+3/4"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "-x+2/3,-y+1/3,-z+1/3", "y+2/3,-x+y+1/3,-z+1/3", "x-y+2/3,x+1/3,-z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "-x+1/3,-y+2/3,-z+2/3", "y+1/3,-x+y+2/3,-z+2/3", "x-y+1/3,x+2/3,-z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z", "x-y,-y,-z", "-x,-x+y,-z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "y+2/3,x+1/3,-z+1/3", "x-y+2/3,-y+1/3,-z+1/3", "-x+2/3,-x+y+1/3,-z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "y+1/3,x+2/3,-z+2/3", "x-y+1/3,-y+2/3,-z+2/3", "-x+1/3,-x+y+2/3,-z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,z", "-x+y,y,z", "x,x-y,z", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "-y+2/3,-x+1/3,z+1/3", "-x+y+2/3,y+1/3,z+1/3", "x+2/3,x-y+1/3,z+1/3", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "-y+1/3,-x+2/3,z+2/3", "-x+y+1/3,y+2/3,z+2/3", "x+1/3,x-y+2/3,z+2/3"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "-y+2/3,-x+1/3,z+5/6", "-x+y+2/3,y+1/3,z+5/6", "x+2/3,x-y+1/3,z+5/6", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "-y+1/3,-x+2/3,z+1/6", "-x+y+1/3,y+2/3,z+1/6", "x+1/3,x-y+2/3,z+1/6"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "y,x,-z+1/2", "x-y,-y,-z+1/2", "-x,-x+y,-z+1/2", "-x,-y,-z", "y,-x+y,-z", "x-y,x,-z", "-y,-x,z+1/2", "-x+y,y,z+1/2", "x,x-y,z+1/2", "x+2/3,y+1/3,z+1/3", "-y+2/3,x-y+1/3,z+1/3", "-x+y+2/3,-x+1/3,z+1/3", "y+2/3,x+1/3,-z+5/6", "x-y+2/3,-y+1/3,-z+5/6", "-x+2/3,-x+y+1/3,-z+5/6", "-x+2/3,-y+1/3,-z+1/3", "y+2/3,-x+y+1/3,-z+1/3", "x-y+2/3,x+1/3,-z+1/3", "-y+2/3,-x+1/3,z+5/6", "-x+y+2/3,y+1/3,z+5/6", "x+2/3,x-y+1/3,z+5/6", "x+1/3,y+2/3,z+2/3", "-y+1/3,x-y+2/3,z+2/3", "-x+y+1/3,-x+2/3,z+2/3", "y+1/3,x+2/3,-z+1/6", "x-y+1/3,-y+2/3,-z+1/6", "-x+1/3,-x+y+2/3,-z+1/6", "-x+1/3,-y+2/3,-z+2/3", "y+1/3,-x+y+2/3,-z+2/3", "x-y+1/3,x+2/3,-z+2/3", "-y+1/3,-x+2/3,z+1/6", "-x+y+1/3,y+2/3,z+1/6", "x+1/3,x-y+2/3,z+1/6"},
			{"x,y,z", "-y,x-y,z", "-x+y,-x,z", "-x,-y,z", "y,-x+y,z", "x-y,x,z"}
	};
	
	public static void main(String[] args) {
		String sg225hm = "F 4/m -3 2/m";
		System.out.println(sg225hm + "->" + Pattern.compile(" +").matcher(sg225hm).replaceAll(""));
		System.out.println(names[225] + "->" + simplifyLongName(names[225]));
		System.out.println(names[138] + "->" + simplifyLongName(names[138]));
		System.out.println(names[230] + "->" + simplifyLongName(names[230]));
		System.out.println(get("Fm3m").getNumber());
		
	}

	
	
}
	
	
