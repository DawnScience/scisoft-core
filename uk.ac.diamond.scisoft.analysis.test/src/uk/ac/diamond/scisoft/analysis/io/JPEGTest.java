/*
 * Copyright 2011 Diamond Light Source Ltd.
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

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Comparisons;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Test for JPEGLoader and JPEGSaver
 */
public class JPEGTest {
	static String testScratchDirectoryName = null;
	AbstractDataset data;
	DataHolder dh = new DataHolder();
	DataHolder dhLoad = new DataHolder();
	int sizex = 500, sizey = 500, range = sizex * sizey;
	double abserr = 2.0; // maximum permitted absolute error (remember that JPEGs are lossy)

	/**
	 * Creates an empty directory for use by test code.
	 * 
	 * @throws Exception if the test fails
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(JPEGTest.class.getCanonicalName());
		TestUtils.makeScratchDirectory(testScratchDirectoryName);
	}

	/**
	 * Test Loader
	 */
	public JPEGTest() {
	}

	/**
	 * This method takes a data set that has been created above and saves it as a JPEG file, then loads and verifies it.
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testSaveFile() throws Exception {
		String filePath = "testSaveFile.jpg";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 255, range, Dataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);

		DataHolder dhb = new JPEGLoader(testScratchDirectoryName + filePath).loadFile();

		AbstractDataset x = dhb.getDataset(0);
		Number min = x.min();
		Number ptp = x.peakToPeak();
		x = Maths.multiply(Maths.subtract(x, min), data.max().doubleValue()/ptp.doubleValue());

		assertTrue("Saved and loaded datasets are not equal", Comparisons.allTrue(Comparisons.almostEqualTo(x, data, 1e-5, 2)));
	}

	/**
	 * Test to see if the saver can save and reload using LoaderFactory.
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testLoaderFactory() throws Exception {
		String filePath = "testLoaderFactory.jpg";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 255, range, Dataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);

		final IDataHolder dh = LoaderFactory.getData(testScratchDirectoryName + filePath, null);
		if (dh==null || dh.getNames().length<1) throw new Exception();
	}

	/**
	 * Test to see if the saver can save many images
	 * 
	 * @throws ScanFileHolderException
	 */
	@Test
	public void manyImages() throws ScanFileHolderException {
		String filePath = "manyImages.jpg";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 255, range, Dataset.INT16);
		data.setShape(sizex, sizey);
		for (int i = 0; i < 5; i++) {
			dha.addDataset("testing data " + i, data);
		}
		new JPEGSaver(testScratchDirectoryName + "ManyImages" + filePath).saveFile(dha);
	}

	/**
	 * Test to see if the saver can save many scaled images
	 * 
	 * @throws ScanFileHolderException
	 */
	@Test
	public void manyImagesScaled() throws ScanFileHolderException {
		String filePath = "manyImagesScaled.jpg";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 250000, range, Dataset.INT32);
		data.setShape(sizex, sizey);
		for (int i = 0; i < 5; i++) {
			dha.addDataset("testing data " + i, data);
		}
		new JPEGScaledSaver(testScratchDirectoryName + "ManyImagesScaled" + filePath).saveFile(dha);
	}

	/**
	 * Test loading and saving for unscaled data
	 * 
	 * @throws ScanFileHolderException
	 * @throws ScanFileHolderException
	 */
	@Test
	public void compareDataset() throws ScanFileHolderException {
		String filePath = "compareDataset.jpg";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 255, range, Dataset.INT16);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGSaver(testScratchDirectoryName + filePath).saveFile(dha);
		DataHolder dhb = new JPEGLoader(testScratchDirectoryName + filePath).loadFile();

		AbstractDataset x = dhb.getDataset(0);
		Number min = x.min();
		Number ptp = x.peakToPeak();
		x = Maths.multiply(Maths.subtract(x, min), data.max().doubleValue()/ptp.doubleValue());

		assertTrue("Saved and loaded datasets are not equal", Comparisons.allTrue(Comparisons.almostEqualTo(x, data, 1e-5, 2)));
	}

	/**
	 * Compares loaded and saved data set (scaled)
	 * 
	 * @throws ScanFileHolderException
	 */
	@Test
	public void compareScaledDataset() throws ScanFileHolderException {
		String filePath = "compareScaledDataset.jpg";
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 250000, range, Dataset.FLOAT32);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);
		DataHolder dhb = new JPEGLoader(testScratchDirectoryName + filePath).loadFile();

		AbstractDataset x = DatasetUtils.norm(data);
		x.imultiply(255);

		assertTrue("Saved and loaded datasets are not equal", Comparisons.allTrue(Comparisons.almostEqualTo(x, dhb.getDataset(0), 1e-5, 2)));
	}

	/**
	 * Test to see if the loader and saver can deal with a file name without suffix
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void noEnding() throws Exception {
		DataHolder dha = new DataHolder();
		data = DatasetUtils.linSpace(0, 250000, range, Dataset.FLOAT32);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGScaledSaver(testScratchDirectoryName + "noEnding.jpg").saveFile(dha);

		dh = new JPEGLoader(testScratchDirectoryName + "noEnding").loadFile();
		new JPEGScaledSaver(testScratchDirectoryName + "noEnding1").saveFile(dh);
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
		data = DatasetUtils.linSpace(0, 126, range, Dataset.INT8);
		data.setShape(sizex, sizey);
		DataHolder dha = new DataHolder();
		dha.addDataset("testing data", data);
		new JPEGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);
		new JPEGSaver(testScratchDirectoryName + filePath1).saveFile(dha);
		dh = new JPEGLoader(testScratchDirectoryName + filePath).loadFile();
		dh  = new JPEGLoader(testScratchDirectoryName + filePath1).loadFile();
	}

	/**
	 * Test to see what happens when the file is missing
	 * 
	 * @throws ScanFileHolderException if the file couldn't be loaded
	 */
	@Test
	public void testNoFile() throws ScanFileHolderException {
		try {
			dh = new JPEGLoader(testScratchDirectoryName + "NoFile.jpeg").loadFile();
		} catch (ScanFileHolderException e) {
			if (!(e.getCause() instanceof FileNotFoundException))
				throw e;
		}

	}
}
