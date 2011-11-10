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

import org.junit.Test;

/**
 * Deliberately brief testing at the moment, this is more of a run harness
 *
 */
public class XMapLoaderTest {

	/**
	 * Basic test where everything should run as expected
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testLoadFile() throws Exception {

		String testfile1 = "testfiles/gda/analysis/io/XMapLoaderTest/module1binary_31.zip";
		
		XMapLoader xMapLoader = new XMapLoader(testfile1);

		xMapLoader.loadFile();
	}

	@Test
	public void testLoaderFactory() throws Exception {
		DataHolder dh = LoaderFactory.getData( "testfiles/gda/analysis/io/XMapLoaderTest/module1binary_31.zip", null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
 	}
}
