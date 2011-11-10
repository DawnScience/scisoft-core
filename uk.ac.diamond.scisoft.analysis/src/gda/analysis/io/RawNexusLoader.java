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
 * File loader for RAW NeXus files. <br/>
 * Usage:<br/>
 * <code>>>> from gda.analysis.io import *<br/>
 * >>> dataholder = RawNexusLoader("/path/to/file/filename.nxs").loadFile() </code>
 * @deprecated use {@link uk.ac.diamond.scisoft.analysis.io.RawNexusLoader}
 */
@Deprecated
public class RawNexusLoader extends uk.ac.diamond.scisoft.analysis.io.RawNexusLoader {

	/**
	 * @param fileName
	 */
	public RawNexusLoader(String fileName) {
		super(fileName);
	}
}
