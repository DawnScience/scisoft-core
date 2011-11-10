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

import gda.analysis.io.ScanFileHolderException;
import gda.util.TestUtils;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class PilatusTiffLoaderTest {
	static String testfile1 = null;
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		testfile1 = TestFileFolder + "PilatusTiffLoaderTest/p678450.tif"; // 100k
	}

	/**
	 * Test Loading
	 * 
	 * @throws ScanFileHolderException if the file cannot be loaded
	 */
	@Test
	public void testLoadFile() throws ScanFileHolderException {
		new PilatusTiffLoader(testfile1).loadFile();
	}

}

