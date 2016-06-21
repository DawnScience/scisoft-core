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
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;
import uk.ac.diamond.scisoft.analysis.utils.OSUtils;

import static org.junit.Assume.assumeTrue;

/**
 *
 */
public class CBFLoaderThreadTest extends LoaderThreadTestBase {

	
	static String testpath = null;
	static String TestFileFolder;
	
	@BeforeClass
	static public void setUpClass() {
		skipTestIf(OSUtils.isWindowsOS(),
			".CBFLoaderThreadTest skipped, since currently failing on Windows");

		TestFileFolder = IOTestUtils.getGDALargeTestFilesLocation();
		TestFileFolder += "CBFLoaderTest/";
		testpath = TestFileFolder;
		if (testpath.matches("^/[a-zA-Z]:.*")) // Windows path
			testpath = testpath.substring(1); // strip leading slash
	}

	/**
	 * Utility function to skip a JUnit test if the specified condition is true.
	 * If called from a method annotated with @Test, and condition is true, the @Test method will halt and be ignored (skipped).
	 * If called from a method annotated with @Before or @BeforeClass, all @Test methods of the class are ignored (skipped).
	 * 
	 * Existing test runners (we're talking JUnit 4.5 and Ant 1.7.1, as bundled with Eclipse 3.5.1, don't have the concept of a
	 * skipped test (tests are classified as either a pass or fail). Tests that fail an assumption are reported as passed.
	 * 
	 * Internally, a failing assumption throws an AssumptionViolatedException (in JUnit 4,5; this may have changed in later releases).
	 * 
	 * @param condition - boolean specifying whether the test method or test class is to be skipped
	 * @param reason - explanation of why the test is skipped
	 */
	private static void skipTestIf(boolean condition, String reason) {
		if (condition) {
			System.out.println("JUnit test skipped: " + reason);
			assumeTrue(false);
		}
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
