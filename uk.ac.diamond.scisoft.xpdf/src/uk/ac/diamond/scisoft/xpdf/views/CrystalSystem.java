/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

import java.util.ArrayList;
import java.util.List;

class CrystalSystem {

	private int number;
	private static CrystalSystem[] systems;
	static final int nSystems = 8; // 7 crystal systems, plus rhombohedral space groups with a rhombohedral basis
	
	public static CrystalSystem get(int systemOrdinal) {
		if (systems == null || systems.length < nSystems) {
			generateSystems();
		}
		return systems[systemOrdinal];
	}
		
	public String getName() {
		return (number >= 0 && number < nSystems) ? names[number] : "Unknown";
	}
	
	public int getOrdinal() {
		return number;
	}
	
	public static String[] getNames() {
		return names;
	}
	
	public List<XPDFSpaceGroup> getGroups() {
		List<XPDFSpaceGroup> groupList = new ArrayList<XPDFSpaceGroup>();
		for (int group = lowestGroups[number]; group <= highestGroups[number]; group++)
			groupList.add(XPDFSpaceGroup.get(group));
		return groupList;
	}
	
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
