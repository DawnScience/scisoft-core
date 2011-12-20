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

package uk.ac.diamond.scisoft.analysis.io;

/**
 * This class saves a DataHolder as a PNG image file. The maximum value for each pixel that PNG is capable of
 * handling is 65535. If the value of a measurement exceeds this value then this method is recommended. This method will
 * scale the values of the datasets within a DataHolder to lie within the capabilities of PNG.
 */
public class PNGScaledSaver extends JavaImageScaledSaver {

	/**
	 * @param FileName
	 */
	public PNGScaledSaver(String FileName) {
		super(FileName, "png", 16, true);
	}

	/**
	 * Save as PNG using given minimum and maximum values
	 * @param FileName
	 * @param min
	 * @param max
	 */
	public PNGScaledSaver(String FileName, double min, double max) {
		super(FileName, "png", 16, true, min, max);
	}
}
