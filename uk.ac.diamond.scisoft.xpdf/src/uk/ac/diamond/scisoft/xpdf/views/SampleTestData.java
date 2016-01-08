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
			{
				XPDFPhase newPhase = new XPDFPhase();
				newPhase.setName("BTO");
				newPhase.addComment("barium titanate");
				phases.add(newPhase);
				newPhase = new XPDFPhase();
				newPhase.setName("Calcium titanate");
				phases.add(newPhase);
			}
			sample.setPhases(phases);
			sample.setComposition("Ba0.9Ca0.1TiO3"); // Should be "Ba0.9Ca0.1TiO3"
			sample.setDensity(3.71);
			sample.setAsSample();
			break;
		}
		case("Rutile"): {
			sample.setName("Rutile");
			List<XPDFPhase> phases = new ArrayList<XPDFPhase>();

			XPDFPhase newPhase = new XPDFPhase();
			newPhase.setName("TiO2");
			newPhase.addComment("tianium dioxide");
			phases.add(newPhase);

				sample.setPhases(phases);
			sample.setComposition("TiO2");
			sample.setDensity(6.67);
			sample.setAsSample();
			break;
		}
		case("Quartz Capillary"): {
			sample.setName("Quartz Capillary");
			sample.setAsContainer();
			//		cap.setPhases(new ArrayList<String>(Arrays.asList(new String[] {"cSiO₂"})));
			List<XPDFPhase> phases = new ArrayList<XPDFPhase>();
			for (String phaseName : new String[] {"cSiO2"}) {
				XPDFPhase newPhase = new XPDFPhase();
				newPhase.setName(phaseName);
				newPhase.addComment("Crystalline silica,");
				newPhase.addComment("also known as quartz");
				phases.add(newPhase);
			}
			sample.setPhases(phases);
			sample.setComposition("SiO2");
			sample.setDensity(2.65);
			sample.setPackingFraction(1.0);
			sample.setShape("Cylinder");
			sample.setDimensions(0.15, 0.16);
		}
		default:
		}
		
		return sample;
	}
	
}
