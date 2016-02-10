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
import java.util.HashMap;
import java.util.Map;

class XPDFSpaceGroup {

	private int number;
	private static XPDFSpaceGroup[] groups;
	static final int[] rhombGroups = {146, 148, 155, 160, 161, 166, 167};
	static final int nGroupsFedorov = 230;
	static final int nGroups = nGroupsFedorov + rhombGroups.length;
	private static Map<Integer, Integer> hexagonalRhombohedralGroupMap = null;
	
	private XPDFSpaceGroup() {
	}
	
	public static XPDFSpaceGroup get(int groupNumber) {
		if (groups == null) {
			generateGroups();
		}
		// Watch out for off by 1 errors
		return groups[groupNumber];
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getName() {
		return names[number];
	}
	
	public String getShortName() {
		return shortNames[number];
	}
	
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
	
	private static void generateGroups() {
		groups = new XPDFSpaceGroup[nGroups+1];
		for (int newGroup = 0; newGroup <= nGroups; newGroup++) {
			XPDFSpaceGroup group = new XPDFSpaceGroup();
			group.number = newGroup;
			groups[newGroup] = group;
		}
		
		// Also generate the mapping between rhombohedral and hexagonal groups
		hexagonalRhombohedralGroupMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < nGroups-nGroupsFedorov; i++) {
			hexagonalRhombohedralGroupMap.put(rhombGroups[i], nGroupsFedorov+i);
			hexagonalRhombohedralGroupMap.put(nGroupsFedorov+i, rhombGroups[i]);
		}
	}
	
	/**
	 * Returns the rhombohedral equivalent space group.
	 * <p>
	 * If this is the space group has a rhombohedral lattice, then return the
	 * pseudo-space group representing its rhombohedral form. Otherwise, return this.
	 * @return
	 * 		the rhombohedral equivalent psuedo-space group, if it exists, else this.
	 */
	public XPDFSpaceGroup asRhombohedral() {
		if (Arrays.asList(rhombGroups).contains(this.number)) {
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
		if (this.number > nGroupsFedorov) {
			return groups[hexagonalRhombohedralGroupMap.get(number)];
		}
		return this;
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

}
	
	
