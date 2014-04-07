/*
 * Copyright 2012 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;

/**
 * set up file paths
 */
public class RawTextTest {
	final static String testFileFolder = "testfiles/images/";
	static String testScratchDirectoryName = null;
	AbstractDataset data;
	int sizex = 240, sizey = 240, range = sizex * sizey;
	String filePath = "TestData.dat";

	/**
	 * Creates an empty directory for use by test code.
	 * 
	 * @throws Exception
	 *             if the directory is not created
	 */
	@BeforeClass
	static public void setUpClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(RawTextTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
	}

	/**
	 * @throws ScanFileHolderException
	 * 
	 * Creates a random dataset and tries to save it as ASCII in a file
	 */
	@Test
	public void testSaveFile() throws ScanFileHolderException {
		DataHolder dh = new DataHolder();
		data = DatasetUtils.linSpace(0, 576000, range, AbstractDataset.FLOAT64);
		data.setShape(sizex, sizey);
		try {
			dh.addDataset("testing data", data);
			new RawTextSaver(testScratchDirectoryName + filePath).saveFile(dh);
		} catch (Exception e) {
			throw new ScanFileHolderException(
					"Problem testing RawText class", e);
		}
	}

	/**
	 * @throws ScanFileHolderException
	 */
	@Test
	public void testLoadFile() throws ScanFileHolderException {
		DataHolder dh = new PNGLoader(testFileFolder + "test.png").loadFile();
		AbstractDataset a = dh.getDataset(0);
		try {
			new RawTextSaver(testScratchDirectoryName + "test.txt").saveFile(dh);
		} catch (Exception e) {
			throw new ScanFileHolderException(
					"Problem testing RawText class", e);
		}

		try {
			dh = new RawTextLoader(testScratchDirectoryName + "test.txt").loadFile();
		} catch (Exception e) {
			throw new ScanFileHolderException("Problem testing rawOutput class", e);
		}
        if (dh==null || dh.getNames().length<1) throw new ScanFileHolderException("");
        Assert.assertEquals("Datasets not equal", a, dh.getDataset(0));
	}
}
