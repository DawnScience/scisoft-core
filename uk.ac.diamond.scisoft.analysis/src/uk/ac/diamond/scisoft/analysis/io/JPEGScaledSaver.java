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

package uk.ac.diamond.scisoft.analysis.io;

/**
 * This class saves a DataHolder as a JPEG image file. The maximum value for each pixel that JPEG is capable of
 * handling is 255. If the value of a measurement exceeds this value then this method is recommended. This method will
 * scale the values of the datasets within a DataHolder to lie within the capabilities of JPEG.
 */
public class JPEGScaledSaver extends JavaImageScaledSaver {

	/**
	 * @param FileName
	 */
	public JPEGScaledSaver(String FileName) {
		super(FileName, "jpeg", 8, true);
	}

	/**
	 * Save as JPEG using given minimum and maximum values
	 * @param FileName
	 * @param min
	 * @param max
	 */
	public JPEGScaledSaver(String FileName, double min, double max) {
		super(FileName, "jpeg", 8, true, min, max);
	}

}
