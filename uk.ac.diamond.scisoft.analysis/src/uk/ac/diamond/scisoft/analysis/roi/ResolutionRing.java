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

import java.io.Serializable;

import org.eclipse.swt.graphics.Color;

/**
 * Class for Resolution rings in the diffraction viewer.
 */
public class ResolutionRing implements Serializable {

	private double resolution;
	private boolean visible;
	private java.awt.Color colour;
	private boolean ice;
	private boolean evenSpaced;
	private boolean standard;

	/**
	 * @param resolution in Angstroms
	 */
	public ResolutionRing(double resolution) {
		this(resolution, true, java.awt.Color.ORANGE, false, false, false);
	}

	/**
	 * @param resolution in Angstroms
	 * @param visible
	 * @param colour
	 * @param ice if true, then ring is an ice ring
	 * @param evenSpacedRings
	 */
	public ResolutionRing(double resolution, boolean visible, Color colour, boolean ice, boolean evenSpacedRings) {
		this(resolution, visible, colour, ice, evenSpacedRings, false);
	}

	/**
	 * @param resolution in Angstroms
	 * @param visible
	 * @param colour
	 * @param ice if true, then ring is an ice ring
	 * @param evenSpacedRings
	 * @param standard
	 */
	public ResolutionRing(double resolution, boolean visible, Color colour, boolean ice, boolean evenSpacedRings,
			boolean standard) {
		this(resolution, visible, new java.awt.Color(colour.getRed(), colour.getGreen(), colour.getBlue()), ice,
				evenSpacedRings, standard);
	}

	private ResolutionRing(double resolution, boolean visible, java.awt.Color colour, boolean ice, boolean evenSpacedRings,boolean standard) {
		this.resolution = resolution;
		this.visible = visible;
		this.colour = colour;
		this.setIce(ice);
		this.setEvenSpaced(evenSpacedRings);
		this.setStandard(standard);
	}

	public void setVisible(boolean visable) {
		this.visible = visable;
	}

	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param resolution
	 *            The resolution in Angstroms to set.
	 */
	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	/**
	 * @return Returns the resolution in Angstroms
	 */
	public double getResolution() {
		return resolution;
	}

	public Color getColour() {
		return new Color(null, colour.getRed(), colour.getGreen(), colour.getBlue());
	}

	public java.awt.Color getAWTColour() {
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
