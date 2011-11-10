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

import uk.ac.diamond.scisoft.analysis.io.PNGScaledSaver;

/**
 * This class saves a DataHolder as a PNG image file. The maximum value for each pixel that PNG is capable of
 * handling is 65535. If the value of a measurement exceeds this value then use PNGScaledSaver instead.
 * @deprecated use {@link PNGScaledSaver}
 */
@Deprecated
public class PNGSaver extends uk.ac.diamond.scisoft.analysis.io.PNGSaver {

	/**
	 * @param fileName
	 */
	public PNGSaver(String fileName) {
		super(fileName);
	}

}
