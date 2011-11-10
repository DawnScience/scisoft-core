/*-
 * Copyright © 2011 Diamond Light Source Ltd.
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
 * This class saves a DataHolder as a TIFF image file.
 */
public class TIFFImageSaver extends JavaImageSaver {

	/**
	 * @param filename
	 */
	public TIFFImageSaver(String filename) {
		this(filename, false);
	}

	/**
	 * @param filename
	 * @param numBits
	 */
	public TIFFImageSaver(String filename, int numBits) {
		super(filename, "tiff", numBits, true);
	}

	/**
	 * @param filename
	 * @param numBits
	 * @param asUnsigned
	 */
	public TIFFImageSaver(String filename, int numBits, boolean asUnsigned) {
		super(filename, "tiff", numBits, asUnsigned);
	}

	/**
	 * @param filename
	 * @param asFloat
	 */
	public TIFFImageSaver(String filename, boolean asFloat) {
		super(filename, "tiff", asFloat ? 33 : 16, true);
	}
}
