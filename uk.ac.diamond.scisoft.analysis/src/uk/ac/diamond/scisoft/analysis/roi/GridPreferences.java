/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.roi;

import java.io.Serializable;

/**
 * Stores the preferences used in the GridProfile Side Plot View
 * To be used along side a GridROI
 */
public class GridPreferences implements Serializable {
	/**
	 * The spacing of the grid on the X axis, in pixels per mm
	 */
	private double gridScanResolutionX;
	/**
	 * The spacing of the grid on the Y axis, in pixels per mm
	 */
	private double gridScanResolutionY;
	/**
	 * The centre of the beamline on the X axis in pixels
	 */
	private double gridScanBeamlinePosX;
	/**
	 * The centre of the beamline on the X axis in pixels
	 */
	private double gridScanBeamlinePosY;
	
	/**
	 * @param gridScanResolutionX The spacing of the grid on the X axis, in pixels per mm
	 * @param gridScanResolutionY The spacing of the grid on the Y axis, in pixels per mm
	 * @param gridScanBeamlinePosX the centre of the beamline on the X axis in pixels
	 * @param gridScanBeamlinePosY the centre of the beamline on the Y axis in pixels
	 */
	public GridPreferences(double gridScanResolutionX, double gridScanResolutionY, double gridScanBeamlinePosX,
			double gridScanBeamlinePosY) {
		this.gridScanResolutionX = gridScanResolutionX;
		this.gridScanResolutionY = gridScanResolutionY;
		this.gridScanBeamlinePosX = gridScanBeamlinePosX;
		this.gridScanBeamlinePosY = gridScanBeamlinePosY;
	}
	
	/**
	 * Copy constructor
	 * @param toCopy GridPreferences to make a copy of
	 */
	public GridPreferences(GridPreferences toCopy) {
		this(toCopy.gridScanResolutionX, toCopy.gridScanResolutionY, toCopy.gridScanBeamlinePosX,
				toCopy.gridScanBeamlinePosY);
	}

	/**
	 * Default constructor, equivalent to calling:
	 * 	GridPreferences (10000.0, 10000.0, 0, 0)
	 * Which is 1 micron == 1 pixel and centre in the top left of the image
	 */
	public GridPreferences() {
		this(10000.0, 10000.0, 0, 0);
	}

	/**
	 * @return Returns the spacing of the grid on the X axis, in pixels per mm
	 */
	public double getResolutionX() {
		return gridScanResolutionX;
	}
	/**
	 * @param gridScanResolutionX The spacing of the grid on the X axis, in pixels per mm
	 */
	public void setResolutionX(double gridScanResolutionX) {
		this.gridScanResolutionX = gridScanResolutionX;
	}
	/**
	 * @return Returns the spacing of the grid on the Y axis, in pixels per mm
	 */
	public double getResolutionY() {
		return gridScanResolutionY;
	}
	/**
	 * @param gridScanResolutionY The spacing of the grid on the Y axis, in pixels per mm
	 */
	public void setResolutionY(double gridScanResolutionY) {
		this.gridScanResolutionY = gridScanResolutionY;
	}
	/**
	 * @return Returns the centre of the beamline on the X axis in pixels
	 */
	public double getBeamlinePosX() {
		return gridScanBeamlinePosX;
	}
	/**
	 * @param gridScanBeamlinePosX the centre of the beamline on the X axis in pixels
	 */
	public void setBeamlinePosX(double gridScanBeamlinePosX) {
		this.gridScanBeamlinePosX = gridScanBeamlinePosX;
	}
	/**
	 * @return Returns the centre of the beamline on the Y axis in pixels
	 */
	public double getBeamlinePosY() {
		return gridScanBeamlinePosY;
	}
	/**
	 * @param gridScanBeamlinePosY the centre of the beamline on the Y axis in pixels
	 */
	public void setBeamlinePosY(double gridScanBeamlinePosY) {
		this.gridScanBeamlinePosY = gridScanBeamlinePosY;
	}
	
	/**
	 * Returns the number of x pixels per 1 um from preference store
	 * @return number of x pixels per micron
	 */
	public double getXPixelsPerMicron() {
		return getResolutionX() / 1000;
	}

	/**
	 * Returns the number of y pixels per 1 um from preference store
	 * @return number of y pixels per micron
	 */
	public double getYPixelsPerMicron() {
		return getResolutionY() / 1000;
	}	
	
	/** 
	 * Converts a coordinate X point from microns to pixels
	 * @param pixels
	 * @return micron location of given pixel location
	 */
	public double getXMicronsFromPixelsCoord(double pixels) {
		return (pixels - getBeamlinePosX()) / getXPixelsPerMicron();
	}
	
	/**
	 * Converts a coordinate Y point from microns to pixels
	 * @param pixels
	 * @return micron location of given pixel location
	 */
	public double getYMicronsFromPixelsCoord(double pixels) {
		return (pixels - getBeamlinePosY()) / getYPixelsPerMicron() ;
	}
	
	/**
	 * Converts a coordinate X point from pixels to microns
	 * @param microns
	 * @return pixel location of given micron location
	 */
	public double getXPixelsFromMicronsCoord(double microns) {
		return microns * getXPixelsPerMicron() + getBeamlinePosX();
	}
	
	/**
	 * Converts a coordinate X point from pixels to microns
	 * @param microns
	 * @return pixel location of given micron location
	 */
	public double getYPixelsFromMicronsCoord(double microns) {
		return microns * getYPixelsPerMicron() + getBeamlinePosY();
	}

	
	/** 
	 * Converts a length X point from microns to pixels
	 * @param pixels
	 * @return micron location of given pixel location
	 */
	public double getXMicronsFromPixelsLen(double pixels) {
		return pixels / getXPixelsPerMicron();
	}
	
	/**
	 * Converts a length Y point from microns to pixels
	 * @param pixels
	 * @return micron location of given pixel location
	 */
	public double getYMicronsFromPixelsLen(double pixels) {
		return pixels / getYPixelsPerMicron();
	}
	
	/**
	 * Converts a length X point from pixels to microns
	 * @param microns
	 * @return pixel location of given micron location
	 */
	public double getXPixelsFromMicronsLen(double microns) {
		return microns * getXPixelsPerMicron();
	}
	
	/**
	 * Converts a length X point from pixels to microns
	 * @param microns
	 * @return pixel location of given micron location
	 */
	public double getYPixelsFromMicronsLen(double microns) {
		return microns * getYPixelsPerMicron();
	}	
}