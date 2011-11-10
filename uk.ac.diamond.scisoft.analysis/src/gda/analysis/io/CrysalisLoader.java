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
 * Basic class that loads in data from the Oxford Instruments Ruby detector
 * @deprecated use {@link uk.ac.diamond.scisoft.analysis.io.CrysalisLoader}
 */
@Deprecated
public class CrysalisLoader extends uk.ac.diamond.scisoft.analysis.io.CrysalisLoader {

	/**
	 * @param fileName
	 */
	public CrysalisLoader(String fileName) {
		super(fileName);
	}
}
