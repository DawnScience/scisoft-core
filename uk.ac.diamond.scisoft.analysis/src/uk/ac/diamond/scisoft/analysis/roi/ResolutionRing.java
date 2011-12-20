/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
