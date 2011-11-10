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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gda.analysis.io.ScanFileHolderException;
import gda.util.TestUtils;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Comparisons;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Test for PNGLoader and PNGSaver
 */
public class PNGTest {
	final static String TestFileFolder = "testfiles/images/";
	static String testScratchDirectoryName = null;
	AbstractDataset data;
	DataHolder dh = new DataHolder();
	int sizex = 500, sizey = 500, range = sizex * sizey;

	/**
	 * Creates an empty directory for use by test code.
	 * 
	 * @throws Exception if setup fails
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(PNGTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
	}

	/**
	 * Test Loader
	 */
	public PNGTest() {
	}

	/**
	 * This method takes a data set that has been created above and saves it as a .png file, then loads and verifies it
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testSaveFile() throws Exception {
		String filePath = "testSaveFile.png";
		DataHolder dha = new DataHolder();
		AbstractDataset loadData;
		data = DatasetUtils.linSpace(0, 32000, range, AbstractDataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new PNGSaver(testScratchDirectoryName + filePath).saveFile(dha);

		DataHolder dhb = new PNGLoader(testScratchDirectoryName + filePath, false, true).loadFile();
		short[] outData = (short[]) data.getBuffer();
		loadData = dhb.getDataset(0);
		short[] inData = (short[]) loadData.getBuffer();
		assertEquals(outData.length, inData.length);
		for (int i = 0; i < outData.length; i++) {
			assertEquals("" + i, outData[i], inData[i]);
		}
	}

	@Test
	public void testLoaderFactory() throws Exception {
		String filePath = "testLoaderFactory.png";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 32000, range, AbstractDataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new PNGSaver(testScratchDirectoryName + filePath).saveFile(dha);

		final DataHolder dh = LoaderFactory.getData(testScratchDirectoryName + filePath, null);
		if (dh==null || dh.getNames().length<1) throw new Exception();
	}

	/**
	 * This method load a PNG from a fixed location
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testPNGLoader2() throws Exception {
		new PNGLoader(TestFileFolder + "test.png", true).loadFile();
	}

	/**
	 * Test to see if the saver can save many images
	 * 
	 * @throws ScanFileHolderException
	 */
	@Test
	public void manyImages() throws ScanFileHolderException {
		String filePath = "manyImages.png";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 32000, range, AbstractDataset.INT16);
		data.setShape(sizex, sizey);
		for (int i = 0; i < 5; i++) {
			dha.addDataset("testing data " + i, data);
		}
		new PNGSaver(testScratchDirectoryName + filePath).saveFile(dha);
	}

	/**
	 * Test to see if the saver can save many scaled images
	 * 
	 * @throws ScanFileHolderException
	 */
	@Test
	public void manyImagesScaled() throws ScanFileHolderException {
		String filePath = "manyImagesScaled.png";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 250000, range, AbstractDataset.INT32);
		data.setShape(sizex, sizey);
		for (int i = 0; i < 5; i++) {
			dha.addDataset("testing data " + i, data);
		}
		new PNGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);
	}

	/**
	 * Test loading and saving for unscaled data
	 * 
	 * @throws ScanFileHolderException
	 */
	@Test
	public void compareDataset() throws ScanFileHolderException {
		String filePath = "compareDataset.png";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 32000, range, AbstractDataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new PNGSaver(testScratchDirectoryName + filePath).saveFile(dha);
		DataHolder dhb = new PNGLoader(testScratchDirectoryName + filePath, false, true).loadFile();

		AbstractDataset loadData = dhb.getDataset(0);

		assertTrue("Saved and loaded datasets are not equal", Comparisons.allTrue(Comparisons.equalTo(loadData, data)));
	}

	/**
	 * Compare loaded and saved dataset (scaled)
	 * 
	 * @throws ScanFileHolderException
	 */
	@Test
	public void compareScaledDataset() throws ScanFileHolderException {
		String filePath = "compareScaledDataset.png";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 250000, range, AbstractDataset.INT32);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new PNGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);
		DataHolder dhb = new PNGLoader(testScratchDirectoryName + filePath, false, false).loadFile();

		AbstractDataset x = dhb.getDataset(0);
		Number min = x.min();
		Number ptp = x.peakToPeak();
		x = Maths.multiply(Maths.subtract(x, min), data.max().doubleValue()/ptp.doubleValue());

		assertTrue("Saved and loaded datasets are not equal", Comparisons.allTrue(Comparisons.almostEqualTo(x, data, 1e-5, 4)));
	}

	/**
	 * Test to see if the loader and saver can deal with a file name without suffix
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void noEnding() throws Exception {
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 32000, range, AbstractDataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new PNGSaver(testScratchDirectoryName + "noEnding.png").saveFile(dha);

		dh = new PNGLoader(testScratchDirectoryName + "noEnding").loadFile();
		assertTrue("Saved and loaded datasets are not equal", Comparisons.allTrue(Comparisons.equalTo(dh.getDataset(0), data)));
		new PNGScaledSaver(testScratchDirectoryName + "noEnding1").saveFile(dh);
		dh = new PNGLoader(testScratchDirectoryName + "noEnding1").loadFile();
	}

	/**
	 * Test to see if the loader and saver can deal with a file with an incorrect file name
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void incorrectEnding() throws Exception {
		String filePath = "incorrectEnding.txt";
		String filePath1 = "incorrectEnding1.txt";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 32000, range, AbstractDataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new PNGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);
		new PNGSaver(testScratchDirectoryName + filePath1).saveFile(dha);
		new PNGLoader(testScratchDirectoryName + filePath).loadFile();
		new PNGLoader(testScratchDirectoryName + filePath1).loadFile();
	}

	/**
	 * Test to see what happens when the file is missing
	 * 
	 * @throws ScanFileHolderException if the test fails
	 */
	@Test
	public void testNoFile() throws ScanFileHolderException {
		try {
			new PNGLoader(testScratchDirectoryName + "NoFile.png").loadFile();
		} catch (ScanFileHolderException e) {
			if (!(e.getCause() instanceof FileNotFoundException))
				throw e;
		}
	}
}
