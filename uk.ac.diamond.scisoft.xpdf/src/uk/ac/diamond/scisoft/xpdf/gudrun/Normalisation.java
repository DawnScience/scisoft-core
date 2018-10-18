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
 * generates text for normalisation section of gudrun input file
 *
 */

public class Normalisation {
	
	private static final Logger LOGGER = Logger.getLogger( Normalisation.class.getName());
	
	private double azimuthalAngle = 0.0;
	private double overlapFactor = 0.0;
	private int divideBy = 0;
	private int powerBreitDirac = 2;
	private int normKroghMoe = 1;
	
	public Normalisation() {
		//Normalisation does not need attributes assigned on declaration
	}	
	public Normalisation(double azA, double ovF, int div, int pBD, int nKM) {
		this.azimuthalAngle = azA;
		this.overlapFactor = ovF;
		this.divideBy = div;
		this.powerBreitDirac = pBD;
		this.normKroghMoe = nKM;
		
//----the setter methods----------------------------------------------------------
	}
	public void setAzimuthalAngle(double azA) {
		this.azimuthalAngle = azA;
	}
	public void setOverlapFactor(double ovF) {
		this.overlapFactor = ovF;
	}
	public void setDivideBy(int div) {
		this.divideBy = div;
	}
	public void setPowerBreitDirac(int pBD) {
		this.powerBreitDirac = pBD;
	}
	public void setNormKroghMoe(int nKM) {
		this.normKroghMoe = nKM;
	}
	
//---------------------------------------------------------------------------------
	
	//Generates the text to place on the file for the normalisation section for the autogudrun file
	public String generate() {
		LOGGER.log(Level.INFO, "Creating Beam input section...");
		StringBuilder entry = new StringBuilder(); 
		
		entry.append("NORMALISATION          {\n\n");
        entry.append(this.azimuthalAngle + "          Azimuthal angle of detector above scattering plane:\n");
        entry.append(this.divideBy + "          Divide by <F>^2?\n");
        entry.append(this.powerBreitDirac + "          Power for Breit-Dirac factor (2 -3)\n");
        entry.append(this.normKroghMoe + "          Krogh-Moe & Norman normalisation\n");
        entry.append(this.overlapFactor + "          Overlap factor\n");
        entry.append("\n}");
		return entry.toString();
	}
}

