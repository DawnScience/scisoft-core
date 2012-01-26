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
 * Object used to encapulate reference to ExternalFile from HDF5
 */
public class ExternalFiles {
	int[] shape;
	String[] files;

	public String getAsText() {
		if (files == null)
			return "";
		StringBuilder sBuilder = new StringBuilder();
		boolean addCR = false;
		for (String filename : files) {
			if (addCR) {
				sBuilder.append("\n");
			}
			sBuilder.append(filename);
			addCR = true;
		}
		return sBuilder.toString();
	}
}