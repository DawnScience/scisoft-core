/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.DatasetException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Slice;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

/**
 * Tests the ADSC image loader with file in TestFiles
 */
public class TiffLoaderTest {
	static String testfile1 = null;
	static String testfile2 = null;
	static String testfile3 = null;
	private static String testScratchDirectoryName;

	/**
	 * @throws Exception 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = IOTestUtils.setUpTestClass(TiffLoaderTest.class, true);
	}

	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		TestFileFolder += "PilatusTiffLoaderTest/";
		testfile1 = TestFileFolder + "fcell_H_8GPa_20keV_18000s_0173.tif";
		testfile2 = TestFileFolder + "ipp16.TIF";
		testfile3 = TestFileFolder + "crl-bestfocus1.tif";
	}

	/**
	 * Test Loading
	 * 
	 * @throws ScanFileHolderException
	 *             if the test fails
	 */
	@Test
	public void testLoadFile() throws ScanFileHolderException {
		new TIFFImageLoader(testfile1).loadFile();
	}

	@Test
	public void testLoaderFactory() throws Exception {
		IDataHolder dh = LoaderFactory.getData(testfile1, null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
 	}

	/**
	 * Test Loading
	 * 
	 * @throws ScanFileHolderException
	 *             if the test fails
	 */
	@Test
	public void testLoadFile12Bit() throws ScanFileHolderException {
		new TIFFImageLoader(testfile2).loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws ScanFileHolderException
	 *             if the test fails
	 */
	@Test
	public void testLoadFile16Bit() throws ScanFileHolderException {
		new TIFFImageLoader(testfile3).loadFile();
	}

	@Test
	public void testTruncatedFile() {
		try {
			DataHolder dh = new TIFFImageLoader("testfiles/images/test-trunc.tiff").loadFile();
			System.err.println(dh.size());
			Assert.fail("Should have thrown an exception");
		} catch (ScanFileHolderException e) {
//			e.printStackTrace();
		}
	}

	@Test
	public void testNullFile() {
		try {
			new TIFFImageLoader("testfiles/images/null.dat").loadFile();
			Assert.fail("Should have thrown an exception");
		} catch (ScanFileHolderException e) {
//			e.printStackTrace();
		}
	}

	@Test
	public void testSaveFile() throws ScanFileHolderException {
		Dataset a = DatasetFactory.createRange(FloatDataset.class, 128 * 128).reshape(128, 128);
		DataHolder d = new DataHolder();
		a.idivide(10000);
		d.addDataset("a", a);

		String oname = testScratchDirectoryName + "a.tif";
		TIFFImageSaver s;
		DataHolder in;

		s = new TIFFImageSaver(oname, true);
		s.saveFile(d);

		in = new TIFFImageLoader(oname).loadFile();
		checkDataset(a, in.getDataset(0));

		s = new TIFFImageSaver(oname);
		s.saveFile(d);

		in = new TIFFImageLoader(oname).loadFile();
		checkDataset(a.cast(IntegerDataset.class), in.getDataset(0));
	}

	@Test
	public void testStackedFile() throws DatasetException, ScanFileHolderException {
		ILazyDataset image = new TIFFImageLoader(TestFileFolder + "untitled1020.TIF").loadFile().getLazyDataset(0);
		Assert.assertArrayEquals("Shape not equal", new int[] {3, 2048, 2048}, image.getShape());

		IDataset d = image.getSlice(new Slice(1));
		Assert.assertArrayEquals("Shape not equal", new int[] {1, 2048, 2048}, d.getShape());
		Assert.assertEquals("Type is int32", Integer.class, d.getElementClass());

		d = image.getSlice(new Slice(1, 3), new Slice(1));
		Assert.assertArrayEquals("Shape not equal", new int[] {2, 1, 2048}, d.getShape());
		Assert.assertEquals("Type is int32", Integer.class, d.getElementClass());

		d = image.getSlice(new Slice(1, 3), new Slice(null, null, 4), new Slice(2, 25));
		Assert.assertArrayEquals("Shape not equal", new int[] {2, 512, 23}, d.getShape());
		Assert.assertEquals("Type is int32", Integer.class, d.getElementClass());
	}

	@Test
	public void testCachedLazyStackedFile() throws Exception {
		String file = TestFileFolder + "untitled1020.TIF";
		LoaderFactory.clear(file);
		LoaderFactory.cacheData(LoaderFactory.getData(file, false, true, true, null));
		IFileLoader loader = new TIFFImageLoader(file);
		ILazyDataset image = loader.loadFile().getLazyDataset(0);
		Assert.assertArrayEquals("Shape not equal", new int[] {3, 2048, 2048}, image.getShape());

		IDataset d = image.getSlice(new Slice(1));
		Assert.assertArrayEquals("Shape not equal", new int[] {1, 2048, 2048}, d.getShape());
		Assert.assertEquals("Type is int32", Integer.class, d.getElementClass());
	}

	private void checkDataset(Dataset e, Dataset a) {
		int[] shape = e.getShape();
		Assert.assertArrayEquals("Shape not equal", shape, a.getShape());
		Assert.assertEquals("1st value", e.getDouble(0, 0), a.getDouble(0, 0), 1e-5);
		Assert.assertEquals("last value", e.getDouble(shape[0] - 1, shape[1] - 1),
				a.getDouble(shape[0] - 1, shape[1] - 1), 1e-5);
		TestUtils.assertDatasetEquals(e, a, 1e-14, 1e-14);
	}

}
