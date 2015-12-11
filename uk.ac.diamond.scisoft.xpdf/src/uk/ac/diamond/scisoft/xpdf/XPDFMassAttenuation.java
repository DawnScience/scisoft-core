/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import com.github.tschoonj.xraylib.Xraylib;

/**
 * Calculates the mass attenuation for fixed I15-1 beam energies.
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 *
 */
public final class XPDFMassAttenuation {

	/**
	 * Private constructor
	 */
	private XPDFMassAttenuation() {
	}
	
	/**
	 * Returns the mass attenuation coefficient.
	 * @param energy
	 * 				energy of the attenuated photons in eV.
	 * @param z
	 * 			atomic number of the attenuating atoms.
	 * @return the mass attenuation coefficient in cm²/g 
	 */
	public static double get(double energy, int z) {
		
		//return (new XCOMElement(z)).getAttenuation(energy*1e-3, "total");
		return Xraylib.CS_Total(z, energy*1e-3);
	}
	
	public static double getPhoto(double energy, int z) {
		return Xraylib.CS_Photo(z, energy*1e-3);
	}
}
