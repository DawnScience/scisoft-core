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

import gda.util.TestUtils;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.io.CBFLoader;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;

/**
 * set up file paths
 */
public class RawOutputTest {
	static String testScratchDirectoryName = null;
	static String testpath = null;
	AbstractDataset data;
	int sizex = 2400, sizey = 2400, range = sizex * sizey;
	String filePath = "TestData.dat";
	static String TestFileFolder;

	/**
	 * Creates an empty directory for use by test code.
	 * 
	 * @throws Exception
	 *             if the directory is not created
	 */
	@BeforeClass
	static public void setUpClass() throws Exception {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		testScratchDirectoryName = TestUtils
				.generateDirectorynameFromClassname(RawOutputTest.class
						.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
//		testpath = CBFLoaderTest.class.getResource("testfiles/").toURI()
//				.getPath();
//		if (testpath.matches("^/[a-zA-Z]:.*")) // Windows path
//			testpath = testpath.substring(1); // strip leading slash
		testpath = TestFileFolder + "CBFLoaderTest/";
	}

	/**
	 * @throws ScanFileHolderException
	 * 
	 * Creates a random dataset and tries to save it as ASCII in a file
	 */
	@Test
	public void testSaveFile() throws ScanFileHolderException {
		DataHolder dh = new DataHolder();
		data = DatasetUtils.linSpace(0, 5760000, range, AbstractDataset.FLOAT64);
		data.setShape(sizex, sizey);
		try {
			dh.addDataset("testing data", data);
			new RawOutput(testScratchDirectoryName + filePath).saveFile(dh); //
		} catch (Exception e) {
			throw new ScanFileHolderException(
					"Problem testing rawOutput class", e);
		}
	}

	/**
	 * @throws ScanFileHolderException
	 */
	@Test
	public void testCBFFile() throws ScanFileHolderException {
		DataHolder dh = new CBFLoader(testpath + "F6_1_001.cbf").loadFile();
		try {
			new RawOutput(testScratchDirectoryName + "F6_1_001.dat").saveFile(dh);
		} catch (Exception e) {
			throw new ScanFileHolderException(
					"Problem testing rawOutput class", e);
		}
	}
	
	@Test
	public void testLoaderFactory() throws Exception {
		DataHolder dh = LoaderFactory.getData(testpath + "F6_1_001.cbf", null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
 	}

}
