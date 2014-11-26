/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.Assert;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class MARImageLoaderThreadTest extends LoaderThreadTestBase{
	
	private static String TestFileFolder;

	private static String testfile1, testfile2, testfile3, testfile4;

	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if (TestFileFolder == null) {
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		TestFileFolder += "MARImageTest/";
		testfile1 = TestFileFolder + "in1187_sample1.mccd";
		testfile2 = TestFileFolder + "mar225_001.mccd";
		testfile3 = TestFileFolder + "mar165_001.mccd";
		testfile4 = TestFileFolder + "ins2-foc_MS_2_001.mccd";
	}

	@Override
	@Test
	public void testInTestThread() throws Exception{
		super.testInTestThread();
	}
		
	@Test
	public void testWithTwentyThreads() {
		String hostname;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException e) {
			hostname = "";
		}
		if (hostname.startsWith("p99-ws100")) {
			TestUtils.skipTest(
			this.getClass().getCanonicalName() + ".testWithTwentyThreads skipped, since test takes forever on our under-powered Ubuntu test box (" + hostname + ")");
		}
		try {
			super.testWithNThreads(20);
		} catch (ScanFileHolderException sfhe) {
			if (!(sfhe.getCause() instanceof OutOfMemoryError))
				Assert.fail("Something other than an out of memory exception was thrown.");
			System.out.println("This test is expected to throw an exception as the loaders run out of memory");
		} catch (Exception e) {
			Assert.fail("Loading failed for reasons other than out of memory");
		}
	}

	
	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception{
		@SuppressWarnings("unused")
		IDataHolder dh = LoaderFactory.getData(testfile1, null);
        dh = LoaderFactory.getData(testfile2, null);
        dh = LoaderFactory.getData(testfile3, null);
        dh = LoaderFactory.getData(testfile4, null);
	}
}
