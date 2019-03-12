/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.gudrun;

import com.github.tschoonj.xraylib.Xraylib;
import com.github.tschoonj.xraylib.compoundData;

/**
 * 
 * @author Tekevwe Kwakpovwe
 * A Class to add the sample information to the autogudrun text file
 * contains variables that represent all the possible attributes of the sample
 */

public class Sample {
	
	private String[] fileNames;
	private String name;
	private String composition;
    private String geometry="SameAsBeam";
    private String density = "1.0";
    private double innerRadii= 0.0;
    private double outerRadii = 0.0585;
    private double kAlpha2 = 0.0;
    private double kBeta = 0.0;
    private double tweak = 2.0;
    private double lorchWidth = 0.1;
    private double polarisation = -1.0;
    private double dataFactor;
    private double minRadius = 1.0;
    private int height = 5;
    private int topHatWidth = 4;
    private int nIterations = 5;
    private int multiScattering = 1;
    private int compton = 1;
    private int bremsstrahlung = 0;
    private int nBremsstrahlungIter = 0;
    private int broadPower = 0;
    
    /**
     * constructor for Sample class
     */
	public Sample() {
		//For creating the sample
	}
	
	//Getters
	public String[] getFileNames() {
		return this.fileNames;
	}
	
	//Setters 
	public void setFileNames(String[] fNa) {
		this.fileNames = fNa;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public void setComposition(String cpn) {
		this.composition = cpn;
	}
	
	public void setGeometry(String geo) {
		this.geometry = geo;
	}
	
	public void setInnerRadii(double inR) {
		this.innerRadii = inR;
	}
	
	public void setOuterRadii(double ouR) {
		this.outerRadii = ouR;
	}
	
	public void setKAlpha2(double kAl) {
		this.kAlpha2 = kAl;
	}
	
	public void setKBeta(double kBe) {
		this.kBeta = kBe;
	}
	
	public void setDensity(String den) {
		this.density = den;
	}
	
	public void setTweak(double twe) {
		this.tweak = twe;
	}
	
	public void setLorchWidth(double lor) {
		this.lorchWidth = lor;
	}
	
	public void setPolarisation(double pol) {
		this.polarisation = pol;
	}
	
	public void setDataFactor(double dat) {
		this.dataFactor = dat;
	}
	
	public void setHeight(int hei) {
		this.height = hei;
	}
	
	public void setTopHatWidth(int top) {
		this.topHatWidth = top;
	}
	
	public void setMinRadius(int miR) {
		this.minRadius = miR;
	}
	
	public void setNIterations(int nIt) {
		this.nIterations = nIt;
	}
	
	public void setMultiScattering(int mul) {
		this.multiScattering = mul;
	}
	
	public void setCompton(int com) {
		this.compton = com;
	}
	
	public void setBremsstrahlung(int bre) {
		this.bremsstrahlung = bre;
	}
	
	public void setNBI(int nBi) {
		this.nBremsstrahlungIter = nBi;
	}

	public void setBroadPower(int bro) {
		this.broadPower = bro;
	}
	
	//getters
	public String getName() {
		return this.name;
	}
	
	/**
	 * Generates the text to be placed on the autogudrun file
	 * @return: A string across multiple lines to represent the Sample section of the gudrun input file
	 */
	public String generate() { 
		StringBuilder entry = new StringBuilder();
		entry.append("SAMPLE " + (this.name) + "          {\n\n");
		
        entry.append(((this.fileNames).length) + "            Number of  files\n");
        for (String fileName : this.fileNames) {
            entry.append(fileName + "          SAMPLE " + this.name +" data files\n");
        }
        entry.append("1          Force calculation of sample corrections?\n");
        
        
        //Following section is the body of what is written on the file under the Sample header
		compoundData atomicComp = Xraylib.CompoundParser(this.composition.trim());
		for (int i = 0; i < atomicComp.nElements ; i++) {
        entry.append(Xraylib.AtomicNumberToSymbol(atomicComp.Elements[i]) +
        	  "  " + Xraylib.AtomicNumberToSymbol(atomicComp.Elements[i]) +
        	  "  " + atomicComp.nAtoms[i] +
        		"  0.0  0.0          Sample atomic composition\n");
		}
        entry.append("*  0  0  0  0          * 0 0 0 0 to specify end of composition input\n");
        entry.append(this.geometry + "          Geometry\n");
        entry.append(this.innerRadii + "  " +this.outerRadii + "          Inner and outer radii (cm)\n");
        entry.append(this.height + "          Sample height (cm)\n");
        entry.append(this.density + "          Density Units:  gm/cm^3?\n");
        entry.append("TABLES          Total cross section source\n");
        entry.append(this.tweak + "          Tweak factor\n");
        entry.append(this.topHatWidth + "          Top hat width (1/A) for cleaning up Fourier Transform\n");
        entry.append(this.minRadius + "          Minimum radius for Fourier Transform [A]\n");
        entry.append(this.lorchWidth + "          Width of broadening in r-space [A]\n");
        entry.append("0  0          0   0          to finish specifying wavelength range of resonance\n");
        entry.append("0.0  0.0  1.0          Exponential amplitude, decay [ ] and stretch\n");
        entry.append("1          Sample calibration factor\n");
        entry.append(this.nIterations + "          No. of iterations\n");
        entry.append("0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0           Fluorescence levels\n");
        entry.append(this.multiScattering + "          Factor to modify multiple scattering (0 - 1)\n");
        entry.append(this.polarisation + "          Incident beam polarization factor (-1 -> +1)\n");
        entry.append(this.compton + "          Factor for Compton scattering\n");
        entry.append(this.bremsstrahlung + "          Bremsstrahlung scattering amplitude\n");
        entry.append(this.nBremsstrahlungIter + "          No. of bremsstrahlung iterations\n");
        entry.append(this.broadPower + "          Broadening power\n");
        entry.append(this.kAlpha2 + "  " + this.kBeta + "          kAlpha2 and kBeta relative intensities:  \n");
        for (String filename : this.fileNames) {
            entry.append(this.dataFactor + "          Data factor\n");
        }
        entry.append("1          Analyse this sample? \n");
        for (String filename : this.fileNames) {
            entry.append("0          Exclude scans\n");
        }
        entry.append("\n}");
        
        return entry.toString();
	}

}
