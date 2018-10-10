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
 * 
 * @author Tekevwe Kwakpovwe
 * generates text for beam section of gudrun input file
 */

public class Beam {
	
	private static final Logger LOGGER = Logger.getLogger( Beam.class.getName());
	
	private String geometry = "CYLINDRICAL";
	private String tubeCurrent = "*";
	private String kAlpha1 = "*";
	private String kAlpha2 = "*";
	
	private String kBeta = "*";
	private String anodeMaterial ="*";
	private String tubeVoltage = "*";
	private String lowestScatteringAngle = "*";
	
	private String highestScatteringAngle="*";
	private String kBetaFilter = "Na";
	private String bremsstrahlungFile = "*";
	private String scatteringAngleStep="*";
	
	private double[] beamProfileValues = {1.0,1.0};
	private double[] footprint = {0.0,0.0,-1.0};
	private double[] lowAngleCutoff = {0.0,0.0,-1.0};
	private double[] incidentBeamEdges = {-0.035,0.035,-0.7,0.7};
	
	private double[] scatteredBeamEdges = {-0.035,0.035,-0.7,0.7};
	private double absStep = 0.001;
	private double msStep = 0.005;
	private double kAlpha2Intensity = 0.0;
	
	private double kBetaIntensity = 0.0;
	private double bremsstrahlungScatteringAmplitude = 0.0;
	private double targetDensity = 0.0;
	private double targetPenetrationDepth = 0.0;
	
	private double kBetaFilterDensity = 0.0;
	private double kBetaFilterThickness =0.0;
	private double bremsstrahlungPower = 0.0;
	private double detectorCutoff = 0.0;
	
	private double detectorCutoffWidth = 0.0;
	private double angleOffset = 0.0;
	private double wavelength = 0.161669;
	private double thetaThetaScanning = 0.0;
	
	private int nSlices = 100;
	private int corStep = 5;
	private int nBremsstrahlungIterations = 0;
	private int fixedSlits = 0;
		
	public Beam() {
		//Beam objects never needs attributes assigned on declaration
	} 
	
	//Generates the text to place on the file for the beam section for the autogudrun file
	public String generate() {
		LOGGER.log(Level.INFO, "Creating Beam input section...");
		StringBuilder entry = new StringBuilder();

		entry.append("BEAM          {\n\n");
		entry.append(this.geometry + "          Sample geometry\n");
		entry.append((this.beamProfileValues).length + "          Number of beam profile values\n");
		for (double beamProfileValue: this.beamProfileValues) {
			entry.append(beamProfileValue + "  ");
		}
        entry.append("          Beam profile values (Maximum of 50 allowed currently)\n");
        entry.append(this.absStep + "  " + this.msStep + "  " + this.nSlices + "          Step size for absorption and m.s. calculation and no. of slices\n");
        entry.append(this.corStep + "          Step in scattering angle to calculate corrections at: [deg.]          \n");
        entry.append(this.incidentBeamEdges[0] + "  " + this.incidentBeamEdges[1] + "  "+
                     this.incidentBeamEdges[2] + "  " + this.incidentBeamEdges[3] +
          "          Incident beam edges relative to centre of sample [cm]\n"
         );
        		              
        
        entry.append(this.scatteredBeamEdges[0] + "  " +
                     this.scatteredBeamEdges[1] + "  " +
        		     this.scatteredBeamEdges[2] + "  " +
                     this.scatteredBeamEdges[3] + 
          "          Scattered beam edges relative to centre of sample [cm]\n"
         );
        
        entry.append(this.bremsstrahlungFile + "          File containing bremsstrahlung intensity\n");
        entry.append(this.targetDensity + "          Density of target material [gm/cm^3] \n");
        entry.append(this.targetPenetrationDepth + "          Effective target penetration depth [cm] \n");
        entry.append(this.kBetaFilter + "          K-beta filter \n");
        entry.append(this.kBetaFilterDensity + "          K-beta filter density [gm/cm^3] \n");
        entry.append(this.kBetaFilterThickness + "          K-beta filter thickness [cm] \n");
        entry.append(this.bremsstrahlungPower + "          Bremsstrahlung power \n");
        entry.append(this.detectorCutoff + "          Detector cutoff [keV]\n");
        entry.append(this.detectorCutoffWidth + "          Cutoff width [keV] \n");
        entry.append(this.lowestScatteringAngle + "          Lowest scattering angle\n");
        entry.append(this.highestScatteringAngle + "          Highest scattering angle\n");
        entry.append(this.scatteringAngleStep + "          Scattering angle step\n");
        entry.append(this.angleOffset + "          Angle offset [deg.] \n");
        entry.append(this.anodeMaterial + "          Anode material:\n");
        entry.append(this.tubeVoltage + "          Tube voltage [kV]\n");
        entry.append(this.wavelength + "          Wavelength [A]:\n");
        entry.append(this.thetaThetaScanning + "          Theta-theta scanning?\n");
        entry.append(this.fixedSlits + "          Fixed slits?\n");
        entry.append(this.footprint[0] + "  " +
                     this.footprint[1] + "  " +
        		     this.footprint[2] +
          "          Footprint length, sample thickness, and depression (all relative to sample dimension):\n"
        );
        
        entry.append(this.lowAngleCutoff[0] + "  " +
                     this.lowAngleCutoff[1] + "  " +
        		     this.lowAngleCutoff[2] +
          "          Position, width and power for low angle cutoff [deg]: \n"
        );
        
        entry.append(this.tubeCurrent + "          Tube current [mA]\n");
        entry.append(this.kAlpha1 + "          kAlpha1 [A] \n");
        entry.append(this.kAlpha2 + "          kAlpha2 [A] \n");
        entry.append(this.kBeta + "          kBeta [A] \n");
        entry.append(this.kAlpha2Intensity + "  " +
                     this.kBetaIntensity +
          "          kAlpha2 and kBeta relative intensities:  \n"
        );
        
        entry.append(this.bremsstrahlungScatteringAmplitude + "          Bremsstrahlung scattering amplitude\n");
        entry.append(this.nBremsstrahlungIterations + "          No. of bremsstrahlung iterations\n");
        entry.append("\n}");
		return entry.toString();
	}
	

}
