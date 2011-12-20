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

import gda.analysis.io.ScanFileHolderException;
import gda.util.TestUtils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

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
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(TiffLoaderTest.class.getCanonicalName());
        TestUtils.makeScratchDirectory(testScratchDirectoryName);
	}

	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
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
		DataHolder dh = LoaderFactory.getData(testfile1, null);
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
		AbstractDataset a = AbstractDataset.arange(128 * 128, AbstractDataset.FLOAT32).reshape(128, 128);
		DataHolder d = new DataHolder();
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
		checkDataset(a, in.getDataset(0));
	}

	private void checkDataset(AbstractDataset e, AbstractDataset a) {
		int[] shape = e.getShape();
		Assert.assertArrayEquals("Shape not equal", shape, a.getShape());
		Assert.assertEquals("1st value", e.getDouble(0, 0), a.getDouble(0, 0), 1e-5);
		Assert.assertEquals("last value", e.getDouble(shape[0] - 1, shape[1] - 1),
				a.getDouble(shape[0] - 1, shape[1] - 1), 1e-5);

	}

}
