/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.gudrun;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tekevwe Kwakpovwe
 * generates text for the sample bakground section of gudrun input file
 *
 */
public class SampleBackground {
	
	private static final Logger LOGGER = Logger.getLogger( SampleBackground.class.getName());
	
	private String[] filenames;
	private double sampleBackgroundFactor = 1;
	private double i0 = 1;
	private double dataFactor = 1;
	public SampleBackground() {
		
	}
	
	public SampleBackground(String[] f, int s, int d) {
		this.filenames = f;
		this.sampleBackgroundFactor = s;
		this.dataFactor = d;
		
	}
	
	//----setters for the class-------------------------------------------------------
	
	public void setFileNames(String[] filenames) {
		this.filenames = filenames;
	}
	
	public void setSBF(double s) {
		this.sampleBackgroundFactor = s;
	}
	
	public void setDataFactor(double d) {
		this.dataFactor = d;
	}
	
	public double getBckgrFac() {
		return this.sampleBackgroundFactor;
	}
	
	public double getDataFactor() {
		return this.dataFactor;
	}
	
	public void seti0(double newi0) {
		this.i0 = newi0;
	}
	public double getSBF() {
		return this.sampleBackgroundFactor;
	}
	
	public double geti0() {
		return this.i0;
	}
	
	//---------------------------------------------------------------------------------
	//Generates the text to place on the file for the sample background section for the autogudrun file
	
	public String generate() {
		
		LOGGER.log(Level.INFO, "Creating Sample Background input section...");
		StringBuilder entry = new StringBuilder();
		entry.append("SAMPLE BACKGROUND          {\n\n");
        entry.append((this.filenames).length + "            Number of  files\n");
        for (String filename : this.filenames) {
            entry.append(filename + "          SAMPLE BACKGROUND data files\n");
        }
        entry.append(this.sampleBackgroundFactor + "          Sample background factor \n");
        for (String filename : this.filenames) {
            entry.append(this.dataFactor + "          Data factor\n");
        }
        for (String filename : this.filenames) {
            entry.append("0          Exclude scans\n");
        }
        entry.append("\n}");
		return entry.toString();
	}
	
	public String[] getFileNames() {
		return this.filenames;
	}
}
