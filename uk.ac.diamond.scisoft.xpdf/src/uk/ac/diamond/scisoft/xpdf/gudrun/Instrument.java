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
 * generates text for instrument section of gudrun input file
 *
 */

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Instrument {
	
	private static final Logger LOGGER = Logger.getLogger( Instrument.class.getName());
	
	private String instrumentName = "XPDF";
	private String inputFileDirectory = System.getProperty("user.dir") ;
	private String dataFileDirectory= System.getProperty("user.dir") ;
	private String dataFileType = "xy";
	private String xrayCrossSecFile = "/dls_sw/apps/gudrun/Gudrun4_hyp/StartupFiles/Xray/CrossSec_XCOM.txt ";
	private String xrayFormFactorFile = "/dls_sw/apps/gudrun/Gudrun4_hyp/StartupFiles/Xray/f0_WaasKirf.txt";
	private String xrayComptonScatFile = "/dls_sw/apps/gudrun/Gudrun4_hyp/StartupFiles/Xray/CrossSec_Compton_Balyuzi.txt";
	private double qMin = 0.5;
	private double qMax = 30;
	private double qStep = 0.01;
	private double rMax = 50;
	private double rStep = 0.02;
	
	public Instrument() {
		//Instrument objects never needs attributes assigned on declaration
	}
	
	//---A bunch of setters ---------------------------------------------------
	
	public void setName(String inN) {
		this.instrumentName = inN;
	}
	
	public void setInDirectory(File iFD) {
		this.inputFileDirectory = iFD.toString();
	}
	
	public void setDataFileDirectory(File dfd) {
		this.dataFileDirectory = dfd.toString();
	}
	
	public void setInDirectory(String iFD) {
		this.inputFileDirectory = iFD;
	}
	
	public void setDataFileDirectory(String dfd) {
		this.dataFileDirectory = dfd;
	}
	
	public void setDataFileType(String dft) {
		this.dataFileType = dft;
	}
	
	public void setXrayCSS(String xcs) {
		this.xrayCrossSecFile = xcs;
	}
	
	public void setXrayFFF(String xff) {
		this.xrayFormFactorFile = xff;
	}
	
	public void setXrayCSF(String xcf) {
		this.xrayComptonScatFile = xcf;
	}
	public void setQMin(double qMi) {
		this.qMin = qMi;
	}

	
	public void setQMax(double qMa) {
		this.qMax = qMa;
	}
	
	public void setRstep(double rSt) {
		this.rStep = rSt;
	}
	
	//----- Function to generate the text saved onto the file for the Instrument --------
	
	public String generate() {
		LOGGER.log(Level.INFO, "Creating instrument input section...");
		
		StringBuilder entry = new StringBuilder();
        entry.append("INSTRUMENT          {\n\n");
        entry.append(this.instrumentName + "          Instrument name\n");
        entry.append(this.inputFileDirectory + "          Gudrun input file directory:\n");
        entry.append(this.dataFileDirectory + "          Data file directory\n");
        entry.append(this.dataFileType + "          Data file type\n");
        entry.append(this.xrayCrossSecFile + "          X-ray cross sections file\n");
        entry.append(this.xrayFormFactorFile + "          X-ray form factor file\n");
        entry.append(this.xrayComptonScatFile + "          X-ray Compton scattering file\n");
        entry.append(this.qMin + "  " + this.qMax + "  " + this.qStep + "          Q-range [1/A] for final DCS\n");
        entry.append(this.rMax + "  " + this.rStep + "          r-max and r-step for final g(r)\n");
        entry.append("\n}");
		return entry.toString();
	}

}
