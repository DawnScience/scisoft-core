/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.functions;

public interface IPeak extends IFunction {
	/**
	 * Returns the peak position for that peak
	 * @return peak position
	 */
	public double getPosition();
		
	/**
	 * Returns the full width half maximum of a peak
	 * @return FWHM
	 */
	public double getFWHM();
	
	/**
	 * Returns the area under the peak
	 * @return area under peak
	 */
	public double getArea();
	
	/**
	 * @return the height of the peak ( y at getPosition() )  
	 */
	public double getHeight();

}
