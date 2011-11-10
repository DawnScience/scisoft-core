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
 * This class loads a JPEG image file
 */
public class JPEGLoader extends JavaImageLoader {

	public JPEGLoader() {
		this(null, false);
	}
	/**
	 * @param FileName
	 */
	public JPEGLoader(String FileName) {
		this(FileName, false);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 */
	public JPEGLoader(String FileName, boolean convertToGrey) {
		super(FileName, "jpeg", convertToGrey);
	}

	/**
	 * @param FileName
	 * @param convertToGrey
	 * @param keepBitWidth
	 */
	public JPEGLoader(String FileName, boolean convertToGrey, boolean keepBitWidth) {
		super(FileName, "jpeg", convertToGrey, keepBitWidth);
	}
	
}