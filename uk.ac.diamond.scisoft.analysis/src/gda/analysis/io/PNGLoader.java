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
 * This class loads a PNG image file into a DataHolder.
 * @deprecated {@link uk.ac.diamond.scisoft.analysis.io.PNGLoader}
 */
@Deprecated
public class PNGLoader extends uk.ac.diamond.scisoft.analysis.io.PNGLoader {

	/**
	 * @param fileName
	 */
	public PNGLoader(String fileName) {
		this(fileName, false);
	}

	/**
	 * @param fileName
	 * @param convertToGrey
	 */
	public PNGLoader(String fileName, boolean convertToGrey) {
		super(fileName, convertToGrey);
	}

}
