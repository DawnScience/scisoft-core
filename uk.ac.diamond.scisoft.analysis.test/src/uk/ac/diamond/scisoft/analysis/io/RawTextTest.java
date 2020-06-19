/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

/**
 * set up file paths
 */
public class RawTextTest {
	final static String testFileFolder = "testfiles/images/";
	static String testScratchDirectoryName = null;
	Dataset data;
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
		testScratchDirectoryName = IOTestUtils.setUpTestClass(RawTextTest.class, true);
	}

	/**
	 * @throws ScanFileHolderException
	 * 
	 * Creates a random dataset and tries to save it as ASCII in a file
	 */
	@Test
	public void testSaveFile() throws ScanFileHolderException {
		DataHolder dh = new DataHolder();
		data = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, 576000, range);
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
		Dataset a = dh.getDataset(0);
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
		if (dh == null || dh.getNames().length < 1)
			throw new ScanFileHolderException("");
		Assert.assertEquals("Datasets not equal", a, dh.getDataset(0));
	}
}
