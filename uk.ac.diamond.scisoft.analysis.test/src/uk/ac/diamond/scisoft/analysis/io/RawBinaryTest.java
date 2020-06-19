/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.ShortDataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

/**
 *
 */
public class RawBinaryTest {

	static String testScratchDirectoryName = null;
	static String testpath = null;
	static double testValue1, testValue2;
	Dataset data;
	int sizex = 240, sizey = 240, range = sizex * sizey;

	/**
	 * Creates an empty directory for use by test code.
	 * 
	 * @throws Exception
	 *             if the directory is not created
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = IOTestUtils.setUpTestClass(RawBinaryTest.class, true);
	}

	/* @throws ScanFileHolderException
	 * 
	 * Creates a random dataset and tries to save it as ASCII in a file
	 */
	@Test
	public void test2DSaveFile() throws ScanFileHolderException {
		String filePath2D = "test2DSaveFile.raw";
		DataHolder dh = new DataHolder();
		data = DatasetFactory.createLinearSpace(DoubleDataset.class, 0, range, range);
		data.setShape(sizex, sizey);
		data.setName("test 2D");
		testValue1 = data.getDouble(sizex-1, 0);
		testValue2 = data.getDouble(10, 10);
		try {
			dh.addDataset("testing data", data);
			new RawBinarySaver(testScratchDirectoryName + filePath2D).saveFile(dh);
		} catch (Exception e) {
			throw new ScanFileHolderException(
					"Problem testing rawOutput class", e);
		}

		try {
			dh = new RawBinaryLoader(testScratchDirectoryName + filePath2D).loadFile();
			Dataset data = dh.getDataset(0);
			assertTrue(data instanceof DoubleDataset);
			assertEquals(data.getSize(), range);
			assertEquals(data.getName(), "test 2D");
			assertEquals(data.getShape().length, 2);
			assertEquals(data.getShape()[0], sizey);
			assertEquals(data.getShape()[1], sizex);
			assertEquals(data.getDouble(sizex-1, 0), testValue1, 0.01);
			assertEquals(data.getDouble(10, 10), testValue2, 0.01);
		} catch (Exception e) {
			throw new ScanFileHolderException("Problem testing rawOutput class",e);
		}

	}

	/* @throws ScanFileHolderException
	 * 
	 * Creates a random dataset and tries to save it as ASCII in a file
	 */
	@Test
	public void test1DSaveFile() throws ScanFileHolderException {
		String filePath1D = "test1DSaveFile.raw";
		DataHolder dh = new DataHolder();
		data = DatasetFactory.createLinearSpace(FloatDataset.class, 0, range, range);
		data.setName("test 1D");
		try {
			dh.addDataset("testing data", data);
			new RawBinarySaver(testScratchDirectoryName + filePath1D).saveFile(dh);
		} catch (Exception e) {
			throw new ScanFileHolderException(
					"Problem testing rawOutput class", e);
		}

		try {
			dh = new RawBinaryLoader(testScratchDirectoryName + filePath1D).loadFile();
			Dataset data = dh.getDataset(0);
			assertTrue("Interface", data instanceof FloatDataset);
			assertEquals(data.getSize(), range);
			assertEquals(data.getName(), "test 1D");
			assertEquals(data.getShape().length, 1);
			assertEquals(data.getShape()[0], range);
		} catch (Exception e) {
			throw new ScanFileHolderException("Problem testing rawOutput class",e);
		}
	}

	@Test
	public void testLoaderFactory() throws Exception {
		String filePath1D = "testLoaderFactory.raw";
		IDataHolder dh = new DataHolder();
		data = DatasetFactory.createLinearSpace(ShortDataset.class, 0, range, range);
		data.setName("test factory");
		try {
			dh.addDataset("testing data", data);
			new RawBinarySaver(testScratchDirectoryName + filePath1D).saveFile(dh);
		} catch (Exception e) {
			throw new ScanFileHolderException(
					"Problem testing rawOutput class", e);
		}

		dh = LoaderFactory.getData(testScratchDirectoryName + filePath1D, null);
		if (dh==null || dh.getNames().length<1) throw new Exception();
		IDataset data = dh.getDataset(0);
		assertTrue(InterfaceUtils.getInterface(data).equals(ShortDataset.class));
		assertEquals(data.getSize(), range);
		assertEquals(data.getName(), "test factory");
		assertEquals(data.getShape().length, 1);
		assertEquals(data.getShape()[0], range);
	}

}
