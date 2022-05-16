/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.RGBByteDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

/**
 * Test for JPEGLoader and JPEGSaver
 */
public class JPEGTest {
	static String testScratchDirectoryName = null;
	Dataset data;
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
		testScratchDirectoryName = IOTestUtils.setUpTestClass(JPEGTest.class, true);
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
		data = DatasetFactory.createLinearSpace(ShortDataset.class, 0, 255, range);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);

		DataHolder dhb = new JPEGLoader(testScratchDirectoryName + filePath).loadFile();

		Dataset x = dhb.getDataset(0);
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
		data = DatasetFactory.createLinearSpace(ShortDataset.class, 0, 255, range);
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
		data = DatasetFactory.createLinearSpace(ShortDataset.class, 0, 255, range);
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
		data = DatasetFactory.createLinearSpace(IntegerDataset.class, 0, 250000, range);
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
		data = DatasetFactory.createLinearSpace(ShortDataset.class, 0, 255, range);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGSaver(testScratchDirectoryName + filePath).saveFile(dha);
		DataHolder dhb = new JPEGLoader(testScratchDirectoryName + filePath).loadFile();

		Dataset x = dhb.getDataset(0);
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
		data = DatasetFactory.createLinearSpace(FloatDataset.class, 0, 250000, range);
		data.setShape(sizex, sizey);
		dha.addDataset("testing data", data);
		new JPEGScaledSaver(testScratchDirectoryName + filePath).saveFile(dha);
		DataHolder dhb = new JPEGLoader(testScratchDirectoryName + filePath).loadFile();

		Dataset x = DatasetUtils.norm(data);
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
		data = DatasetFactory.createLinearSpace(FloatDataset.class, 0, 250000, range);
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
		data = DatasetFactory.createLinearSpace(ByteDataset.class, 0, 126, range);
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

	final static String TestFileFolder = "testfiles/images/";

	/**
	 * This method load a coloured JPEG from a fixed location
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testColourSupport() throws Exception {
		JPEGLoader loader = new JPEGLoader(TestFileFolder + "testrgb.jpg");
		DataHolder dha = loader.loadFile();
		Dataset c = dha.getDataset(0);
		assertTrue(c instanceof RGBByteDataset);

		loader.setLoadAllLazily(false);
		DataHolder dhb = loader.loadFile();
		Dataset d = dhb.getDataset(0);
		TestUtils.assertDatasetEquals(c, d);
	}
}
