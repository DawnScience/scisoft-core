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
 * @deprecated use {@link RawBinarySaver}
 */
@Deprecated
public class RawBinaryOutput extends RawBinarySaver {
	
	/**
	 * Takes the dataset from a scan file holder which is hopefully
	 * array of doubles and output them as a width X height array 
	 * called 'filename'.raw to binary (Java byte order)
	 * The out line of the file is first 4 bytes (int) number of dimensions
	 * then length of each dimensions 4 bytes (int)
	 * Data block as double (8 bytes)  
	 * <p>
	 * <b>Deprecated</b> - use RawBinarySaver instead
	 * @param filename
	 */
	@Deprecated
	public RawBinaryOutput(String filename) {
		super(filename);
	}

}
