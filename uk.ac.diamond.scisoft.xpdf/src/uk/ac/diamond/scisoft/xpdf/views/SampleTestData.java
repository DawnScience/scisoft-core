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

public class SampleTestData {

	public static XPDFSampleParameters createTestSample(String name) {
		XPDFSampleParameters sample  = new XPDFSampleParameters();
		
		switch (name) {
		case("Barium Titanate"): {
			sample.setName("Barium Titanate");
			List<XPDFPhase> phases = new ArrayList<XPDFPhase>();
			phases.add(createTestPhase("BTO"));
			phases.add(createTestPhase("Calcium titanate"));
			sample.setPhases(phases);
			sample.setComposition("Ba0.9Ca0.1TiO3"); // Should be "Ba0.9Ca0.1TiO3"
			sample.setDensity(3.71);
			sample.setAsSample();
			break;
		}
		case("Rutile"): {
			sample.setName("Rutile");
			List<XPDFPhase> phases = new ArrayList<XPDFPhase>();
			phases.add(createTestPhase("TiO2"));
			sample.setPhases(phases);
			sample.setComposition("TiO2");
			sample.setDensity(6.67);
			sample.setAsSample();
			break;
		}
		case("Quartz Capillary"): {
			sample.setName("Quartz Capillary");
			sample.setAsContainer();
			List<XPDFPhase> phases = new ArrayList<XPDFPhase>();
			phases.add(createTestPhase("cSiO2"));
			sample.setPhases(phases);
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
			List<XPDFPhase> phases = new ArrayList<XPDFPhase>();
			phases.add(createTestPhase("ceria"));
			sample.setPhases(phases);
			sample.setComposition("CeO2");
			sample.setDensity(7.65);
			break;
		}
		default:
		}
		
		return sample;
	}
	
	
	public static XPDFPhase createTestPhase(String name) {
		XPDFPhase phase = new XPDFPhase();
		
		switch (name) {
		case ("BTO"): { 
			phase.setName("BTO");
			phase.setSpaceGroupIndex(99); // P4mm
			phase.addComment("barium titanate");
			break;
		}
		case ("Calcium titanate"): {
			phase.setName("calcium titanate");
			phase.setSpaceGroupIndex(62); // Pnma
			break;
		}
		case ("TiO2"): {
			phase.setName("TiO2");
			phase.setSpaceGroupIndex(123);  // P4mmm
			phase.addComment("titanium dioxide");
			break;
		}
		case ("cSiO2"): {
			phase.setName("cSiO2");
			phase.setSpaceGroupIndex(32); // Pba2
			phase.addComment("Crystalline silica,");
			phase.addComment("also known as quartz");
			break;
		}
		case ("Crown Glass"): {
			phase.setName("Crown glass");
			phase.setSpaceGroupIndex(0); // was glass really the best example?
			break;
		}
		case ("Flint Glass"): {
			phase.setName("Flint glass");
			phase.setSpaceGroupIndex(0);
			break;
		}
		case ("ceria"): {
			phase.setName("ceria");
			phase.setSpaceGroupIndex(225); //Fm-3m
			phase.addComment("I love you, ceria!");
			break;
		}
		default:
		}
		
		
		return phase;
	}
	
}
