/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;


import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

/**
 *
 */
public class CBFLoaderTest {

	static String testpath = null;
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		TestFileFolder += "CBFLoaderTest/";
		testpath = TestFileFolder;
		if (testpath.matches("^/[a-zA-Z]:.*")) // Windows path
			testpath = testpath.substring(1); // strip leading slash
	}

	static String testfile1 = null;

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExistingFile() throws Exception {
		new CBFLoader(testpath + "F6_1_001.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile1() throws Exception {
		new CBFLoader(testpath + "adscconverted_flat_orig.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile2() throws Exception {
		new CBFLoader(testpath + "converted_flat_orig.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile3() throws Exception {
		new CBFLoader(testpath + "mb_LP_1_001_orig.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile4() throws Exception {
		new CBFLoader(testpath + "F6_1_001_xReverse.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile5() throws Exception {
		new CBFLoader(testpath + "F6_1_001_yReverse.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile6() throws Exception {
		new CBFLoader(testpath + "F6_1_001_bothReverse.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile7() throws Exception {
		new CBFLoader(testpath + "F6_1_001_transpose.cbf").loadFile();
	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile8() throws Exception {
		new CBFLoader(testpath + "xtal5e_1_0010.cbf").loadFile();
	}
	
	
	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
//	@Test
//	public void testExampleFile81() throws Exception {
//		ScanFileHolder sfhLoad = new ScanFileHolder();
//		sfhLoad.load(new CBFLoader(testpath + "ins_c_9_1999.cbf"));
//	}

	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Test
	public void testExampleFile9() throws Exception {
		new CBFLoader(testpath + "insulin_pilatus6mconverted_orig.cbf").loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoaderFactory() throws Exception {
		
		if (LoaderFactory.getData(testpath + "F6_1_001_bothReverse.cbf", null)==null) throw new Exception();
		if (LoaderFactory.getData(testpath + "xtal5e_1_0010.cbf", null)==null) throw new Exception();
		if (LoaderFactory.getData(testpath + "insulin_pilatus6mconverted_orig.cbf", null)==null) throw new Exception();
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new CBFLoader(testpath + "xtal5e_1_0010.cbf").loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getFirstMetadata(IMetadata.class));
	}

	private int[] iterateAllOld(int xLength, int yLength, boolean isRowsX, boolean xIncreasing, boolean yIncreasing) {
		int index = 0;
		int position = 0;
		int numPixels = xLength*yLength;
		int[] array = new int[numPixels];
		for (int j = 0; j < yLength; j++) {
			for (int i = 0; i < xLength; i++) {
				if (isRowsX) {
					if (xIncreasing) {
						if (!yIncreasing) { // note that image in GDA is plotted so Y increases from top to bottom
							index = position; // top left x increasing (same as j*xLength + i)
						} else {
							index = ((yLength - 1 - j) * xLength + i); // bottom left
						}
					} else {
						if (!yIncreasing) {
							index = j * xLength + (xLength - 1 - i); // top right x decreasing
						} else {
							index = numPixels - position - 1; // bottom right
						}
					}
				} else {
					if (xIncreasing) {
						if (!yIncreasing) {
							index = i * yLength + j;
						} else {
							index = (xLength - 1 - i) * yLength + j;
						}
					} else {
						if (!yIncreasing) {
							index = i * yLength + (yLength - 1 - j);
						} else {
							index = (xLength - 1 - i) * yLength + (yLength - 1 - j);
						}
					}
				}
				array[index] = position++;
			}
		}
		return array;
	}

	private int[] iterateAllNew(int xLength, int yLength, boolean isRowsX, boolean xIncreasing, boolean yIncreasing) {
		int numPixels = xLength*yLength;
		int[] array = new int[numPixels];

		int stride1; // stride is change in position on n-th dim
		int stride2;
		int start;  // start is offset in position

		if (!isRowsX) { // swap row and column directions
			boolean b = yIncreasing;
			yIncreasing = !xIncreasing;
			xIncreasing = !b;
		}

		if (!yIncreasing) { // note that image in GDA is plotted so Y increases from top to bottom
			stride1 = xLength;
			start = 0;
		} else {
			stride1 = -xLength;
			start = xLength*yLength - xLength;
		}

		if (xIncreasing) {
			stride2 = 1;
		} else {
			stride2 = -1;
			start += xLength - 1;
		}

		int rows;
		int cols;
		int rstep;
		int cstep;

		if (isRowsX) {
			rows = yLength;
			cols = xLength;
			rstep = stride1;
			cstep = stride2;
		} else {
			rows = xLength;
			cols = yLength;
			rstep = stride2;
			cstep = stride1;
		}

		int index = 0;
		int lposition = start;
		for (int j = 0; j < rows; j++) {
			int position = lposition;
			for (int i = 0; i < cols; i++) {
				array[index++] = position;
				position += cstep;
			}
			lposition += rstep;
		}
		return array;
	}

	@Test
	public void testIteration() {
		int x = 7;
		int y = 3;
		int[] oArray, nArray;

		boolean[] values = new boolean[] {true, false};

		for (boolean a : values) {
			a = !a;
			for (boolean b : values) {
				for (boolean c : values) {
					c = !c;
					oArray = iterateAllOld(x, y, a, b, c);
//					System.err.printf("%b,%b,%b: %s\n", a, b, c, Arrays.toString(oArray));
					nArray = iterateAllNew(x, y, a, b, c);
//					System.err.printf("%b,%b,%b: %s\n", a, b, c, Arrays.toString(nArray));
					for (int i = 0; i < x*y; i++) {
						Assert.assertEquals("Arrays (" + a + "," + b + "," + c + ") do not match at " + i, oArray[i], nArray[i]);
					}
				}
			}
		}
	}
}
