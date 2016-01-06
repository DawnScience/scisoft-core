/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

public class XPDFDetector {

	private XPDFSubstance substance;
	private double thickness;
	private double solidAngleSubtended;
	
	/**
	 * Default constructor.
	 */
	public XPDFDetector() {
		substance = null;
	}
	
	/**
	 * Copy constructor.
	 * @param inTect
	 * 				detector data to be copied.
	 */
	public XPDFDetector(XPDFDetector inTect) {
		this.substance = (inTect.substance != null) ? new XPDFSubstance(inTect.substance) : null;
		this.thickness = inTect.thickness;
		this.solidAngleSubtended = inTect.solidAngleSubtended;
	}

	/**
	 * Applies a detector correction to a Dataset.
	 * @param data
	 * 			the data to be corrected
	 * @param twoTheta
	 * 			the Dataset of angles over which the data is to be corrected
	 * @param beamEnergy
	 * 			the beam energy at which the correction is to take place
	 * @return the corrected data
	 */
	public Dataset applyTransmissionCorrection(Dataset data, Dataset twoTheta, double beamEnergy){
		return Maths.multiply(data, getTransmissionCorrection(twoTheta, beamEnergy));
	}

	
	/**
	 * Returns a multiplicative detector correction.
	 * @param twoTheta
	 * 			the Dataset of angles over which the data is to be corrected
	 * @param beamEnergy
	 * 			the beam energy at which the correction is to take place
	 * @return the corrected data
	 */
	public Dataset getTransmissionCorrection(Dataset twoTheta, double beamEnergy){
	
		final double mu = substance.getPhotoionizationCoefficient(beamEnergy);
		Dataset transmission = null;
		if (twoTheta.getShape().length == 1) {
			transmission = calculateTransmission(mu, twoTheta);
		} else {
			transmission = (new XPDFScaled2DCalculation(4096) {
				@Override
				protected Dataset calculateTwoTheta(Dataset twoThetaLow) {
					return calculateTransmission(mu, twoThetaLow);
				}
			}).runTwoTheta(twoTheta);
		}
		return transmission;
	}
		
	private Dataset calculateTransmission(double mu, Dataset twoTheta) {
		return Maths.subtract(
				1,
				Maths.exp(
						Maths.divide(
								-mu*thickness,
								Maths.cos(twoTheta)
						)
				)
		);
	}
	
	/**
	 * Sets the substance of which the detector is made.
	 * @param substance
	 * 				An XPDFSubstance class describing the material from which
	 * 				the detector is made.
	 */
	public void setSubstance(XPDFSubstance substance) {
		this.substance = substance;
	}

	/**
	 * Sets the thickness of the detector in mm.
	 * @param thickness
	 * 				thickness of the detector, measured in mm.
	 */
	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public double getSolidAngle() {
		return solidAngleSubtended;
	}

	public void setSolidAngle(double solidAngleSubtended) {
		this.solidAngleSubtended = solidAngleSubtended;
	}

}
