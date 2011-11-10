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

import uk.ac.diamond.scisoft.analysis.io.ASCIIDataHolderSaver;

/**
 * Example class for dumping multidimensional data and meta data
 * @deprecated use {@link ASCIIDataHolderSaver}
 */
@Deprecated
public class AsciiScanFileHolderSaver extends uk.ac.diamond.scisoft.analysis.io.ASCIIDataHolderSaver {
	/**
	 * @param fileName
	 */
	public AsciiScanFileHolderSaver(String fileName) {
		super(fileName);
	}
}

