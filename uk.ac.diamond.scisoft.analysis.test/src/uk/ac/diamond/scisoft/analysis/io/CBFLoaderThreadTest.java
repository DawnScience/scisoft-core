/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.utils.OSUtils;

/**
 *
 */
public class CBFLoaderThreadTest extends LoaderThreadTestBase {

	
	static String testpath = null;
	static String TestFileFolder;
	
	@BeforeClass
	static public void setUpClass() {
		TestUtils.skipTestIf(OSUtils.isWindowsOS(),
			".CBFLoaderThreadTest skipped, since currently failing on Windows");

		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		TestFileFolder += "CBFLoaderTest/";
		testpath = TestFileFolder;
		if (testpath.matches("^/[a-zA-Z]:.*")) // Windows path
			testpath = testpath.substring(1); // strip leading slash
	}
	
	@Override
	@Test
	public void testInTestThread() throws Exception{
		super.testInTestThread();
	}
	
	@Override
	@Test
	public void testWithTenThreads() {
		try {
			super.testWithNThreads(10);
		} catch (ScanFileHolderException sfhe) {
			if (((sfhe.getCause() instanceof OutOfMemoryError)) || (sfhe.toString().endsWith("Direct buffer memory")))
				System.out.println("Out of memory: this is common and acceptable for this test");
			else
				Assert.fail("Something other than an out of memory exception was thrown: " + sfhe.toString());
		} catch (Exception e) {
			Assert.fail("Loading failed for reasons other than out of memory: " + e.toString());
		}
	}
	
	/**
	 * 
	 * 
	 * @throws Exception if the file could not be loaded
	 */
	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {
		final DataHolder dh  = new CBFLoader(testpath + "F6_1_001.cbf").loadFile();
		final String[] names = dh.getNames();
		assert names.length == 0;
		assert dh.getDataset(0).getSize() == (3072*3072);
	}

}
