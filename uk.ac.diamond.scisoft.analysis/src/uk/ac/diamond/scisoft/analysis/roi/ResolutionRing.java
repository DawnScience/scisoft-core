/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.roi;

import java.awt.Color;
import java.io.Serializable;

/**
 * Class for Resolution rings in the diffraction viewer.
 */
public class ResolutionRing implements Serializable {

	private double wavelength;
	private boolean visible;
	private Color colour;
	private boolean ice;
	private boolean evenSpaced;
	private boolean standard;

	/**
	 * @param wavelength in Angstroms
	 * @param visible
	 * @param colour
	 * @param ice
	 *            flag to indicate whether the ring is set to be an ice ring
	 */
	public ResolutionRing(double wavelength, boolean visible, Color colour, boolean ice, boolean evenSpacedRings) {
		this.wavelength = wavelength;
		this.visible = visible;
		this.colour = colour;
		this.setIce(ice);
		this.setEvenSpaced(evenSpacedRings);
		this.setStandard(false);
		}
	public ResolutionRing(double wavelength, boolean visible, org.eclipse.swt.graphics.Color colour, boolean ice, boolean evenSpacedRings, boolean standard) {
		this.wavelength = wavelength;
		this.visible = visible;
		this.colour = new Color(colour.getRed(), colour.getGreen(), colour.getBlue());
		this.setIce(ice);
		this.setEvenSpaced(evenSpacedRings);
		this.setStandard(standard);
		}
	public ResolutionRing(double wavelength, boolean visible, Color colour, boolean ice, boolean evenSpacedRings,boolean standard) {
		this.wavelength = wavelength;
		this.visible = visible;
		this.colour = colour;
		this.setIce(ice);
		this.setEvenSpaced(evenSpacedRings);
		this.setStandard(standard);
	}
	public ResolutionRing(double wavelength, boolean visible, Color colour) {
		this.wavelength = wavelength;
		this.visible = visible;
		this.colour = colour;
		this.setIce(false);
		this.setEvenSpaced(false);
		this.setStandard(false);
	}

	public ResolutionRing(double wavelength) {
		this.wavelength = wavelength;
		this.visible = true;
		this.colour = Color.ORANGE;
		this.setStandard(false);
	}

	public void setVisible(boolean visable) {
		this.visible = visable;
	}

	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param wavelength
	 *            The wavelength in Angstroms to set.
	 */
	public void setWavelength(double wavelength) {
		this.wavelength = wavelength;
	}

	/**
	 * @return Returns the wavelength in Angstroms
	 */
	public double getWavelength() {
		return wavelength;
	}

	/**
	 * @param colour
	 *            The colour to set.
	 */
	public void setColour(Color colour) {
		this.colour = colour;
	}

	/**
	 * @return Returns the colour.
	 */
	public Color getColour() {
		return colour;
	}
	
	public org.eclipse.swt.graphics.Color getSWTColour() {
		return new org.eclipse.swt.graphics.Color(null, colour.getRed(), colour.getGreen(), colour.getBlue());
	}

	public void setIce(boolean ice) {
		this.ice = ice;
	}

	public boolean isIce() {
		return ice;
	}

	public void setEvenSpaced(boolean evenSpaced) {
		this.evenSpaced = evenSpaced;
	}

	public boolean isEvenSpaced() {
		return evenSpaced;
	}
	/**
	 * @param standard The standard to set.
	 */
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
	/**
	 * @return Returns the standard.
	 */
	public boolean isStandard() {
		return standard;
	}
}
