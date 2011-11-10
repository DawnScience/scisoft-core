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
 * Load datasets in a Diamond specific raw format
 * 
 * All this is done in little-endian:
 * 
 * File format tag: 0x0D1A05C1 (4bytes - stands for Diamond Scisoft)
 * Dataset type: (1byte) -1 for old dataset
 * Item size: (1byte - number of elements per data item)
 * Rank: (1byte)
 * Shape: (rank*4 bytes)
 * Name: (length 2bytes, utf8 string)
 * Data: (little-endian raw dump)
 *
 *@deprecated use {@link uk.ac.diamond.scisoft.analysis.io.RawBinaryLoader}
 */
@Deprecated
public class RawBinaryLoader extends uk.ac.diamond.scisoft.analysis.io.RawBinaryLoader {

	public RawBinaryLoader() {
	}

	/**
	 * @param FileName
	 */
	public RawBinaryLoader(String FileName) {
		super(FileName);
	}

}
