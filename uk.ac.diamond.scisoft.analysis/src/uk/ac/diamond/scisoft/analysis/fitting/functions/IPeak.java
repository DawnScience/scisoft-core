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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

/**
 * A unimodal function (i.e. has one peak)
 */
public interface IPeak extends IFunction {

	/**
	 * Returns the peak position for that peak
	 * 
	 * @return peak position
	 */
	public double getPosition();

	/**
	 * Returns the full width half maximum of a peak
	 * 
	 * @return FWHM
	 */
	public double getFWHM();

	/**
	 * Returns the area under the peak
	 * 
	 * @return area under peak
	 */
	public double getArea();

	/**
	 * @return the height of the peak ( y at getPosition() )
	 */
	public double getHeight();

}
