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
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * Calculates the electron cross-sections as a function of angle 
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFElectronCrossSections {

//	double beamEnergy;
//	Dataset twoTheta;
	private XPDFCoordinates coordinates;
	private Dataset thomson;
	private Dataset kleinNishima;
	private static final double classicalElectronRadius = 1.0;
	private static final double electronMasskeV = 510.998910;//(13)
	private static final double breitDiracPower = 2.0;
	
	/**
	 * Empty constructor
	 */
	public XPDFElectronCrossSections() {
		coordinates = null;
		thomson = null;
		kleinNishima = null;
	}
	
	/**
	 * Copy constructor
	 * @param inXSect
	 * 				Cross-section object to be copied.
	 */
	public XPDFElectronCrossSections(XPDFElectronCrossSections inXSect){
		this.coordinates = (inXSect.coordinates != null) ? inXSect.coordinates : null;
		this.thomson = (inXSect.thomson != null) ? inXSect.thomson : null;
		this.kleinNishima = (inXSect.kleinNishima != null) ? inXSect.kleinNishima : null;
	}
	
	/**
	 * Sets the beam energy.
	 * @param beamEnergy
	 * 					The beam energy at which the cross-sections are to be calculated.
	 */
	public void setBeamEnergy(double beamEnergy) {
		if (this.coordinates == null)
			this.coordinates = new XPDFCoordinates();
		this.coordinates.setEnergy(beamEnergy);
		// invalidate the Klein-Nishima value
		kleinNishima = null;
	}
	
	/**
	 * Sets the scattering angles.
	 * @param twoTheta
	 * 				The scattering angles at which the cross-sections are to be calculated.
	 */
	public void setAngles(Dataset twoTheta) {
		if (this.coordinates == null)
			this.coordinates = new XPDFCoordinates();
		this.coordinates.setTwoTheta(twoTheta);
		// invalidate the Thomson and Klein-Nishima values
		this.kleinNishima = null;
		this.thomson = null;
	}
	
	/**
	 * Sets the energy and scattering angles. 
	 * @param coordinates
	 * 					The coordinates object containing the beam energy and
	 * 					the scattering angles.
	 */
	public void setCoordinates(XPDFCoordinates coordinates) {
		this.coordinates = coordinates;
		// invalidate the Thomson and Klein-Nishima values
		this.kleinNishima = null;
		this.thomson = null;
	}
	
	/**
	 * Calculates and returns the elastic Thomson scattering cross-section.
	 * @return the elastic Thomson electron scattering cross-section at the
	 * 			selected scattering angles.
	 */
	public Dataset getThomsonCrossSection() {
		if (this.thomson == null) {
			thomson = Maths.multiply(
					0.5*classicalElectronRadius*classicalElectronRadius, 
					Maths.add(1, Maths.square(Maths.cos(coordinates.getTwoTheta())))
					);
		}
		return this.thomson;
	}
	
	/**
	 * Calculates and returns the inelastic electron scattering cross-section.
	 * @return the inelastic electron scattering cross-section at the selected
	 * 			scattering angles, for the beam energy. As parameterized by 
	 * 			Klein and Nishima.
	 */
	public Dataset getKleinNishimaCrossSection() {
		if (this.kleinNishima == null) {
			double gamma = coordinates.getEnergy()/electronMasskeV;
			Dataset photonEnergyRatio = Maths.divide(
					1.0,
					Maths.add(
							1.0,
							Maths.multiply(
									gamma, 
									Maths.subtract(
											1,
											Maths.cos(coordinates.getTwoTheta())
											)
									)
							)
					);
			this.kleinNishima = Maths.multiply(0.5*classicalElectronRadius*classicalElectronRadius,
					Maths.multiply(
							Maths.power(
									photonEnergyRatio, 
									breitDiracPower),
							Maths.add(
									Maths.add(
											photonEnergyRatio, 
											Maths.divide(1, photonEnergyRatio)),
									Maths.add(
											-1,
											Maths.square(Maths.cos(coordinates.getTwoTheta()))
									)
							)
					)
			);
//							Maths.subtract(
//									Maths.add(
//											photonEnergyRatio, 
//											Maths.divide(1, photonEnergyRatio)),
//									Maths.multiply(
//											2,
//											Maths.multiply(
//													Maths.square(Maths.sin(coordinates.getTwoTheta())),
//													Maths.square(Maths.sin(DoubleDataset.zeros(coordinates.getTwoTheta()))) // The azimuthal scattering angle is zero
//													)
//											)
//									)
//							)
//					);								
		}
		return this.kleinNishima;
	}
}
