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
	static final int nSystems = 7;
	
	public static CrystalSystem get(int systemOrdinal) {
		if (systems == null || systems.length <= nSystems+1) {
			generateSystems();
		}
		return systems[systemOrdinal];
	}
		
	public String getName() {
		return (number >= 0 && number <= nSystems) ? names[number] : "Unknown";
	}
	
	public List<XPDFSpaceGroup> getGroups() {
		List<XPDFSpaceGroup> groupList = new ArrayList<XPDFSpaceGroup>(highestGroups[number] - lowestGroups[number] + 1);
		for (int group = lowestGroups[number]; group <= highestGroups[number]+1; group++)
			groupList.add(group, XPDFSpaceGroup.get(group));
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
		return fixedAxes[number];
	}
	
	private static void generateSystems() {
		systems = new CrystalSystem[nSystems+1]; // 7 crystal systems, and amorphous at 0
		for (int iSystem = 0; iSystem <= nSystems ; iSystem++) {
			CrystalSystem system = new CrystalSystem();
			system.number = iSystem;
			systems[iSystem] = system;
		}
	}
	
	private final static int[] lowestGroups = {0, 1, 3, 16, 75, 143, 168, 195};
	private final static int[] highestGroups = {0, 2, 15, 74, 142, 167, 194, 230};
	private final static String[] names = {"Undefined", "Triclinic", "Monoclinic", "Orthorhombic",
		"Tetragonal", "Trigonal", "Hexagonal", "Cubic"};
	private final static int[] nFreeAxes = {0, 3, 3, 3, 2, 1, 2, 1};
	private final static int[][] fixedAxes = {
		new int[] {-1, -1, -1},
		new int[] {-1, -2, -3},
		new int[] {90, -2, 90},
		new int[] {90, 90, 90},
		new int[] {90, 90, 90},
		new int[] {-1, -1, -1},
		new int[] {90, 90, 120},
		new int[] {90, 90, 90}};
		
}
