/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.nexuswriter;

/**
 * A class to load test data for the XPDF unit cell editor.
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */
class SampleTestData {

	/**
	 * Creates and returns a example {@link XPDFSampleParameters}.
	 * <p>
	 * Returns a sample object, bases on matching one of the given names:
	 * <ul>
	 * <li> Barium Titanate </li>
	 * <li> Rutile </li>
	 * <li> Quartz Capillary </li>
	 * <li> ceria </li>
	 * </ul>
	 * @param name
	 * 			name of the sample to create
	 * @return
	 * 		the object of the created sample.
	 */
	public static XPDFSampleParameters createTestSample(String name) {
		XPDFSampleParameters sample  = new XPDFSampleParameters();
		
		switch (name) {
		case("Barium Titanate"): {
			sample.setName("Barium Titanate");
//			sample.addPhase(createTestPhase("BTO"), 0.9);
//			sample.addPhase(createTestPhase("Calcium titanate"), 0.1);
//			sample.setComposition("Ba0.9Ca0.1TiO3"); // Should be "Ba0.9Ca0.1TiO3"
			sample.addPhase(createTestPhase("BTO"));
			sample.setComposition("BaTiO3");
			sample.setDensity(3.71);
			sample.setAsSample();
			break;
		}
		case("Rutile"): {
			sample.setName("Rutile");
			sample.addPhase(createTestPhase("TiO2"));
			sample.setComposition("TiO2");
			sample.setDensity(6.67);
			sample.setAsSample();
			break;
		}
		case("Quartz Capillary"): {
			sample.setName("Quartz Capillary");
			sample.setAsContainer();
			sample.addPhase(createTestPhase("cSiO2"));
			sample.setComposition("SiO2");
			sample.setDensity(2.65);
			sample.setPackingFraction(1.0);
			sample.setShape("Cylinder");
			sample.setDimensions(0.5, 0.51);
			break;
		}
		case("Quartz Capillary 0p3mm"): {
			sample.setName("Quartz Capillary");
			sample.setAsContainer();
			sample.addPhase(createTestPhase("cSiO2"));
			sample.setComposition("SiO2");
			sample.setDensity(2.65);
			sample.setPackingFraction(1.0);
			sample.setShape("Cylinder");
			sample.setDimensions(0.15, 0.16);
			break;
		}
		case("ceria"): {
			sample.setName("Ceria SRM");
			sample.setAsSample();
			sample.addPhase(createTestPhase("ceria"));
			sample.setComposition("CeO2");
			sample.setDensity(7.65);
			break;
		}
		case ("palladate") : {
			sample.setName("potassium hexachloropalladate");
			sample.setAsSample();
			sample.addPhase(createTestPhase("palladate"));
			sample.setComposition("K2PdCl6");
			sample.setDensity(2.89);
			sample.setShape("Cylinder");
			sample.setDimensions(0.0, 0.39);
		}
		case ("vitrex") : {
			sample.setName("vitrex");
			sample.setAsContainer();
			sample.addPhase(createTestPhase("vitrex"));
			sample.setComposition("SiO2");
			sample.setDensity(2.23);
			sample.setShape("cylinder");
			sample.setDimensions(1.99, 2.00);
			sample.setPackingFraction(1.0);
		}
		case ("bcto") : {
			sample.setName("Barium Calcium Titanate");
			sample.addPhase(createTestPhase("BTO"), 0.9);
			sample.addPhase(createTestPhase("Calcium titanate"), 0.1);
			sample.setComposition("Ba0.9Ca0.1TiO3"); // Should be "Ba0.9Ca0.1TiO3"
			sample.setDensity(3.71);
			sample.setAsSample();
			break;
		}
		
		default:
		}
		
		return sample;
	}
	
	
	/**
	 * Creates and returns a example {@link XPDFPhase}.
	 * <p>
	 * Returns a phase object, bases on matching one of the given names:
	 * <ul>
	 * <li> BTO </li>
	 * <li> Calcium titanate </li>
	 * <li> TiO2 </li>
	 * <li> cSiO2 </li>
	 * <li> Crown Glass </li>
	 * <li> Flint Glass </li>
	 * <li> ceria </li>
	 * <li> microcline </li>
	 * <li> cryolite </li>
	 * <li> ilmenite </li>
	 * </ul>
	 * @param name
	 * 			name of the sample to create
	 * @return
	 * 		the object of the created sample.
	 */
	public static XPDFPhase createTestPhase(String name) {
		XPDFPhase phase = new XPDFPhase();
		
		switch (name) {
		case ("BTO"): { 
			phase.setName("BTO");
			phase.setSpaceGroup(99); // P4mm
			phase.setUnitCellLengths(3.9945, 3.9945, 4.0296);
			// Tetragonal, 90°, 90°, 90°
			phase.addComment("barium titanate");
			phase.addAtom(new XPDFAtom("ba", 56, 1.0, new double[] {0.0,  0.0, 0.0}, "a"));
			phase.addAtom(new XPDFAtom("ti", 22, 1.0, new double[] {0.5,  0.5, 0.52}, "b"));
			phase.addAtom(new XPDFAtom("ox1", 8, 1.0, new double[] {0.5,  0.5, 0.0}, "b"));
			phase.addAtom(new XPDFAtom("ox2", 8, 1.0, new double[] {0.5,  0.0, 0.5}, "c"));

			break;
		}
		case ("Calcium titanate"): {
			phase.setName("calcium titanate");
			phase.setSpaceGroup(62); // Pnma
			phase.setUnitCellLengths(5.447, 7.654, 5.388);
			// Orthorhombic, 90°, 90°, 90°
			phase.addComment("perovskite");
			break;
		}
		case ("TiO2"): {
			phase.setName("TiO2");
			phase.setSpaceGroup(136);  // P42/mmm
			phase.setUnitCellLengths(4.5939, 4.5939, 2.9588);
			// Tetragonal, 90°, 90°, 90°
			phase.addComment("titanium dioxide");
			phase.addComment("or rutile");
			break;
		}
		case ("cSiO2"): {
			phase.setName("cSiO2");
			phase.setSpaceGroup(152); // P3121
			phase.setUnitCellLength(0, 4.9139);
			phase.setUnitCellLength(2, 5.4056);
			// Trigonal, 90°, 90°, 120°
			phase.addComment("Crystalline silica,");
			phase.addComment("also known as quartz");
			phase.addAtom(new XPDFAtom("si", 14, 1.0, new double[] {0.46970, 0.00000, 0.33333}, "a"));
			phase.addAtom(new XPDFAtom("ox", 8, 1.0, new double[] {0.41330, 0.26720, 0.77550}, "c")); // coordinates incorrect
			break;
		}
		case ("Crown Glass"): {
			phase.setName("Crown glass");
			phase.setForm(XPDFPhaseForm.get(XPDFPhaseForm.Forms.GLASSY));
			break;
		}
		case ("Flint Glass"): {
			phase.setName("Flint glass");
			phase.setForm(XPDFPhaseForm.get(XPDFPhaseForm.Forms.GLASSY));
			break;
		}
		case ("ceria"): {
			phase.setName("ceria");
			phase.setSpaceGroup(225); //Fm-3m
			phase.setUnitCellLength(0, 5.41165);
			// Cubic, 90°, 90°, 90°
			phase.addComment("I love you, ceria!");
			phase.addAtom(new XPDFAtom("ox", 8, 1.0, new double[] {0.25,  0.25, 0.25}, "c"));
			phase.addAtom(new XPDFAtom("ce", 58, 1.0, new double[] {0.0,  0.0, 0.0}, "a"));
			break;
		}
		case ("microcline"): {
			phase.setName("microcline");
			phase.setSpaceGroup(2); // P-1
			phase.setUnitCellLengths(8.5784, 12.96, 7.2112);
			phase.setUnitCellAngles(90.3, 116.05, 89); // Triclinic
			phase.addComment("bright blue");
			break;
		}
		case ("cryolite"): {
			phase.setName("cryolite");
			phase.setSpaceGroup(10); // P2/m
			phase.setUnitCellLengths(7.7564, 5.5959, 5.4024);
			phase.setUnitCellAngle(1, 90.18); // Monoclinic
			break;
		}
		case ("ilmenite"): {
			phase.setName("ilmenite");
			phase.setSpaceGroup(XPDFSpaceGroup.get(148).asRhombohedral());
			//phase.setUnitCellLengths(5.08854, 0, 14.0924); // Hexagonal setting axes
			phase.setUnitCellLength(0, 5.54051);
			phase.setUnitCellAngle(0, 54.6726);
			phase.addComment("ferrous titanate");
			// Like haematite, but with titanium. This is the rhombohedral setting
			phase.addAtom(new XPDFAtom("Fe1", 26, 1.0, new double[] {0.333, 0.333, 0.333}, "c"));
//			phase.addAtom(new XPDFAtom("Fe2", 26, 1.0, new double[] {0.667, 0.667, 0.667}));
			phase.addAtom(new XPDFAtom("Ti1", 22, 1.0, new double[] {0.167, 0.167, 0.167}, "c"));
//			phase.addAtom(new XPDFAtom("Ti2", 22, 1.0, new double[] {0.833, 0.833, 0.833}));
			phase.addAtom(new XPDFAtom("O1", 8, 1.0, new double[] {0.583, 0.917, 0.250}, "f"));
//			phase.addAtom(new XPDFAtom("O2", 8, 1.0, new double[] {0.917, 0.250, 0.583}));
//			phase.addAtom(new XPDFAtom("O3", 8, 1.0, new double[] {0.250, 0.583, 0.917}));
//			phase.addAtom(new XPDFAtom("O4", 8, 1.0, new double[] {-0.583, -0.917, -0.250}));
//			phase.addAtom(new XPDFAtom("O5", 8, 1.0, new double[] {-0.917, -0.250, -0.583}));
//			phase.addAtom(new XPDFAtom("O6", 8, 1.0, new double[] {-0.250, -0.583, -0.917}));
			break;
		}
		case ("beryllium") : {
			phase.setName("beryllium");
			phase.setSpaceGroup(194); // P63/mmc, hexagonal
			phase.setUnitCellLength(0, 2.29);
			phase.setUnitCellLength(2, 3.58);
			phase.addComment("elemental hcp beryllium");
//			phase.addAtom(new XPDFAtom("Be1", 4, 1.0, new double[] {0,0,0}, "a"));
			break;
		}
		case ("palladate") : {
			phase.setName("potassium hexachloropalladate");
//			phase.setName("ceria");
//			phase.setSpaceGroup(225); //Fm-3m
//			phase.setUnitCellLength(0, 5.41165);
//			// Cubic, 90°, 90°, 90°
//			phase.addComment("I love you, ceria!");
//			phase.addAtom(new XPDFAtom("ox", 8, 1.0, new double[] {0.25,  0.25, 0.25}, "c"));
//			phase.addAtom(new XPDFAtom("ce", 58, 1.0, new double[] {0.0,  0.0, 0.0}, "a"));
		}
		case ("vitrex") : {
			phase.setName("vitrex");
			phase.setSpaceGroup(152); // P3121
			phase.setUnitCellLength(0, 4.9139);
			phase.setUnitCellLength(2, 5.4056);
			// Trigonal, 90°, 90°, 120°
			phase.addComment("branded quartz");
			phase.addAtom(new XPDFAtom("si", 14, 1.0, new double[] {0.46970, 0.00000, 0.33333}, "a"));
			phase.addAtom(new XPDFAtom("ox", 8, 1.0, new double[] {0.41330, 0.26720, 0.77550}, "c")); // coordinates incorrect
			break;
		}
		default:
		}
		
		
		return phase;
	}
	
}
