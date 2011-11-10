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
 * This class will read the MAR 300 image file type
 * 
 * This class is a sub class of the JavaImageLoader class. This reads the image
 * data. The following method initially reads the default tiff header while the
 * following reads the MAR specific headers. All information is read into a
 * hashmap as string object pairs. The MAR header is read as a stream of bytes
 * the value of which is determined in a C header file. Most values are 32bit ints.
 * @deprecated use {@link uk.ac.diamond.scisoft.analysis.io.MARLoader}
 */
@Deprecated
public class MARLoader extends uk.ac.diamond.scisoft.analysis.io.MARLoader {
	/**
	 * @param fileName
	 */
	public MARLoader(String fileName) {
		super(fileName);
	}
}
