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
 * This class loads a PNG image file
 */
public class PNGLoader extends JavaImageLoader {

	public PNGLoader() {
		this(null, false);
	}
	/**
	 * @param FileName
	 */
	public PNGLoader(String FileName) {
		this(FileName, false);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 */
	public PNGLoader(String FileName, boolean convertToGrey) {
		super(FileName, "png", convertToGrey);
	}
	
	/**
	 * @param FileName
	 * @param convertToGrey
	 * @param keepBitWidth
	 */
	public PNGLoader(String FileName, boolean convertToGrey, boolean keepBitWidth) {
		super(FileName, "png", convertToGrey, keepBitWidth);
	}
}
