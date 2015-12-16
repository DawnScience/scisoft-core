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
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

import com.github.tschoonj.xraylib.Xraylib;

/**
 * Calculates the electron cross-sections as a function of angle 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-14
 *
 */
public class XPDFElectronCrossSections {

//	double beamEnergy;
//	Dataset twoTheta;
	private XPDFCoordinates coordinates;
	private Dataset thomson;
	private Dataset kleinNishina;
	private static final double classicalElectronRadius = 1.0;
	private static final double electronMasskeV = 510.998910;//(13)
	private static final double breitDiracPower = 2.0;
	
	/**
	 * Empty constructor
	 */
	public XPDFElectronCrossSections() {
		coordinates = null;
		thomson = null;
		kleinNishina = null;
	}
	
	/**
	 * Copy constructor
	 * @param inXSect
	 * 				Cross-section object to be copied.
	 */
	public XPDFElectronCrossSections(XPDFElectronCrossSections inXSect){
		this.coordinates = (inXSect.coordinates != null) ? inXSect.coordinates : null;
		this.thomson = (inXSect.thomson != null) ? inXSect.thomson : null;
		this.kleinNishina = (inXSect.kleinNishina != null) ? inXSect.kleinNishina : null;
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
		// invalidate the Klein-Nishina value
		kleinNishina = null;
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
		this.kleinNishina = null;
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
		this.kleinNishina = null;
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
	 * Returns the Thomson differential cross section.
	 * @param coords
	 * 				the coordinates object describing the angles of interest
	 * @return
	 * 		the differential Thomson cross-section in barns per atom per steradian
	 */
	public static Dataset getThomsonCrossSection(XPDFCoordinates coords) {
		IndexIterator iter = coords.getTwoTheta().getIterator();
		DoubleDataset jJ = new DoubleDataset(coords.getTwoTheta());
		
		while (iter.hasNext())
			 jJ.setAbs(iter.index, Xraylib.DCSP_Thoms(coords.getTwoTheta().getElementDoubleAbs(iter.index), 0));
		
		return jJ;
	}
	
	/**
	 * Calculates and returns the inelastic electron scattering cross-section.
	 * @return the inelastic electron scattering cross-section at the selected
	 * 			scattering angles, for the beam energy. As parameterized by 
	 * 			Klein and Nishina.
	 */
	public Dataset getKleinNishinaCrossSection() {
		if (this.kleinNishina == null) {
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
			this.kleinNishina = Maths.multiply(0.5*classicalElectronRadius*classicalElectronRadius,
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
		return this.kleinNishina;
	}
	
	/**
	 * Returns the inelastic Klein-Nishina differential cross-section 
	 * @param coords
	 * 				coordinates object describing the angles of interest 
	 * @param beamEnergy
	 * 					energy of the incident photons in keV
	 * @return
	 * 		unpolarized Klein-Nishina differential cross section in barns per atom per steradian 
	 */
	public static Dataset getKleinNishinaCrossSection(XPDFCoordinates coords, double beamEnergy) {
		IndexIterator iter = coords.getTwoTheta().getIterator();
		DoubleDataset oscarYoshio = new DoubleDataset(coords.getTwoTheta());
		while (iter.hasNext())
			oscarYoshio.setAbs(iter.index, Xraylib.DCS_KN(beamEnergy, coords.getTwoTheta().getElementDoubleAbs(iter.index)));
		
		return oscarYoshio;
	}
}
