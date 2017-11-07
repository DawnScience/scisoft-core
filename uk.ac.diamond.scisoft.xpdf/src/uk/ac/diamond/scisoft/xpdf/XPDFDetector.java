/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.Arrays;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.Maths;

public class XPDFDetector {

	private XPDFSubstance substance;
	private double thickness;
	private double solidAngleSubtended;
	// Euler angles of the detector in radians
	protected double[] eulerAngles;

	
	/**
	 * Default constructor.
	 */
	public XPDFDetector() {
		substance = null;
		this.eulerAngles = new double[] {0, 0, 0};
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
		this.eulerAngles = Arrays.copyOf(inTect.eulerAngles, 3);
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
		if (twoTheta.squeeze().getShape().length == 1) {
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

	/**
	 * Gets the solid angle of the detector.
	 * <p>
	 * Gets the solid angle subtended by the detector from the sample. 
	 * @return the total solid angle the detector subtends from the sample.
	 */
	public double getSolidAngle() {
		return solidAngleSubtended;
	}

	/**
	 * 
	 * Sets the solid angle of the detector.
	 * <p>
	 * Sets the solid angle subtended by the detector from the sample,
	 * expressed in steradians. 
	 * @param solidAngleSubtended
	 * 							the total solid angle the detector subtends from the sample.
	 */
	public void setSolidAngle(double solidAngleSubtended) {
		this.solidAngleSubtended = solidAngleSubtended;
	}

	/**
	 *  Sets the Euler angles in radians
	 * @param eulerAngles
	 * 					Euler angles in radians ordered as pitch, yaw, roll.
	 */
	public void setEulerAngles(double[] eulerAngles) {
		this.eulerAngles = Arrays.copyOf(eulerAngles, 3);
	}
	/**
	 *  Sets the individual Euler angles in radians
	 * @param pitch	Pitch angle in radians
	 * @param yaw	Yaw angle in radians
	 * @param roll	Roll angle in radians
	 */
	public void setEulerAngles(double pitch, double yaw, double roll) {
		this.setEulerAngles(new double[] {pitch, yaw, roll});
	}
	/**
	 *  Sets the individual Euler angles in degrees of arc
	 * @param pitch Pitch angle in degrees of arc
	 * @param yaw	Yaw angle in degrees of arc
	 * @param roll  Roll angle in degrees of arc
	 */
	public void setEulerAnglesinDegrees(double pitch, double yaw, double roll) {
		this.setEulerAngles(Math.toRadians(pitch), Math.toRadians(yaw), Math.toRadians(roll));
	}
	/**
	 *  Gets the Euler angles as an array
	 * @return Euler angles as (pitch, yaw, roll) in radians.
	 */
	public double[] getEulerAngles() {
		return Arrays.copyOf(eulerAngles, 3);
	}
	/**
	 *  Gets the Euler angles as an array in degrees of arc
	 * @return Euler angles as (pitch, yaw, roll) in degrees
	 */
	public double[] getEulerAnglesinDegrees() {
		return new double[] {Math.toDegrees(eulerAngles[0]), Math.toDegrees(eulerAngles[1]), Math.toDegrees(eulerAngles[2])};
	}

	
}
