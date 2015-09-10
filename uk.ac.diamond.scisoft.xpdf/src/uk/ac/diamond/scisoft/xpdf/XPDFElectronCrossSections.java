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

public class XPDFElectronCrossSections {

//	double beamEnergy;
//	Dataset twoTheta;
	XPDFCoordinates coordinates;
	Dataset thomson;
	Dataset kleinNishima;
	static final double classicalElectronRadius = 1.0;
	static final double electronMasskeV = 510.998910;//(13)
	static final double breitDiracPower = 2.0;
	
	public XPDFElectronCrossSections() {
		coordinates = null;
		thomson = null;
		kleinNishima = null;
	}
	
	public XPDFElectronCrossSections(XPDFElectronCrossSections inXSect){
		this.coordinates = (inXSect.coordinates != null) ? inXSect.coordinates : null;
		this.thomson = (inXSect.thomson != null) ? inXSect.thomson : null;
		this.kleinNishima = (inXSect.kleinNishima != null) ? inXSect.kleinNishima : null;
	}
	
	public void setBeamEnergy(double beamEnergy) {
		if (this.coordinates == null)
			this.coordinates = new XPDFCoordinates();
		this.coordinates.setEnergy(beamEnergy);
		// invalidate the Klein-Nishima value
		kleinNishima = null;
	}
	
	public void setAngles(Dataset twoTheta) {
		if (this.coordinates == null)
			this.coordinates = new XPDFCoordinates();
		this.coordinates.setTwoTheta(twoTheta);
		// invalidate the Thomson and Klein-Nishima values
		this.kleinNishima = null;
		this.thomson = null;
	}
	
	public void setCoordinates(XPDFCoordinates coordinates) {
		this.coordinates = coordinates;
		// invalidate the Thomson and Klein-Nishima values
		this.kleinNishima = null;
		this.thomson = null;
	}
	
	public Dataset getThomsonCrossSection() {
		if (this.thomson == null) {
			thomson = Maths.multiply(
					0.5*classicalElectronRadius*classicalElectronRadius, 
					Maths.add(1, Maths.square(Maths.cos(coordinates.getTwoTheta())))
					);
		}
		return this.thomson;
	}
	
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
							Maths.subtract(
									Maths.add(
											photonEnergyRatio, 
											Maths.divide(1, photonEnergyRatio)),
									Maths.multiply(2, Maths.square(Maths.sin(coordinates.getTwoTheta())))
									)
							)
					);								
		}
		return this.kleinNishima;
	}
}
