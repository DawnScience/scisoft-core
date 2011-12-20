/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.util.TestUtils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RAxisImageLoaderTest {

	static String TestFileFolder;
	static String filename;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		filename = TestFileFolder+"RigakuLoaderTest/4_05_screen_0001.osc";
	}
	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoadFile() throws Exception {
		new RAxisImageLoader(filename).loadFile();
	}

	
	@Test
	public void testLoaderFactory() throws Exception {
		
		final DataHolder dh = LoaderFactory.getData(filename, null);
		if (!dh.getNames()[0].equals("RAxis Image")) throw new Exception();
	}
	
}

