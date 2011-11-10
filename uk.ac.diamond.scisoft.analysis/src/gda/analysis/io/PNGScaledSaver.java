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

package gda.analysis.io;

/**
 * This class saves a DataHolder as a PNG image file. The maximum value for each pixel that PNG is capable of
 * handling is 65535. If the value of a measurement exceeds this value then this method is recommended. This method will
 * scale the values of the datasets within a ScanFileHolder to lie within the capabilities of PNG.
 * @deprecated {@link uk.ac.diamond.scisoft.analysis.io.PNGScaledSaver}
 */
@Deprecated
public class PNGScaledSaver extends uk.ac.diamond.scisoft.analysis.io.PNGScaledSaver {

	/**
	 * @param fileName
	 */
	public PNGScaledSaver(String fileName) {
		super(fileName);
	}

	/**
	 * Save as PNG using given minimum and maximum values
	 * @param fileName
	 * @param min
	 * @param max
	 */
	public PNGScaledSaver(String fileName, double min, double max) {
		super(fileName, min, max);
	}
}
