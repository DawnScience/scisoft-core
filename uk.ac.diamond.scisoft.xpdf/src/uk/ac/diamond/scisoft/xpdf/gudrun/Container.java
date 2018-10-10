/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.gudrun;

/**
 * @author Tekevwe Kwakpovwe
 * generates text for the container section of gudrun input file
 *
 */

import com.github.tschoonj.xraylib.Xraylib;
import com.github.tschoonj.xraylib.compoundData;


public class Container {
	
	private String[] filenames;
	private String name;
	private String composition; 
	
	private double innerRadii;
	private double outerRadii;
	private double density = 2.2;
	
	private int height = 5;
	private int tweak = 1;
	private int dataFactor = 1;
	
	/**
	 * Constructor method for the container class
	 */
	private Container() {
		//Container objects never needs attributes assigned on declaration
	} 
		
	
	// ---------------------------- setter methods ---------------------------
	public void setFileNames(String[] filenames) {
		this.filenames = filenames; 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCompostition(String composition) {
		this.composition = composition;
	}
	
	public void setInnerRadii(double innerRadii) {
		this.innerRadii = innerRadii;
	}
	
	public void setOuterRadii(double outerRadii) {
		this.outerRadii = outerRadii;
	}
	
	public void setDensity(double density) {
		this.density = density;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setTweak(int tweak) {
		this.tweak = tweak;
	}
	
	//Generates the text to place on the file for the container section for the autogudrun file
	public String generate() {
		
		StringBuilder entry = new StringBuilder();
		entry.append("CONTAINER "+ this.name + "          {\n\n");
		entry.append((this.filenames).length + "            Number of  files\n");
		for (String filename: this.filenames ) {
			entry.append(filename + "          CONTAINER " + this.name + " data files\n");
		}
		compoundData atomicComp = Xraylib.CompoundParser(this.composition);
		for (int i = 0; i < atomicComp.nElements ; i++) {
	        entry.append(Xraylib.AtomicNumberToSymbol(atomicComp.Elements[i]) +
	            	  "  " + Xraylib.AtomicNumberToSymbol(atomicComp.Elements[i]) +
	            	  "  " + atomicComp.nAtoms[i] +
        		     "  0.0  0.0          Sample atomic composition\\n");
		}
		
		entry.append("*  0  0  0  0          * 0 0 0 0 to specify end of composition input\n");
		entry.append("SameAsBeam          Geometry\n");
		entry.append(this.innerRadii + "  "+ this.outerRadii + "          Inner and outer radii (cm)\n");
		entry.append(this.height + "          Sample height (cm)\n");
		entry.append(this.density + "          Density Units:  gm/cm^3?\n");
		entry.append("TABLES          Total cross section source\n");
		entry.append(this.tweak + "          Container tweak factor\n");
        for (String filename :this.filenames) {
        	entry.append(this.dataFactor + "          Data factor\n");
        }
            
        for (String filename : this.filenames) {
            entry.append("0          Exclude scans\n");
        }
		return entry.toString();
	}

}
