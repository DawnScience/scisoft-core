/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.ScanFileHolderException;
import gda.util.TestUtils;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.gda.util.OSUtils;

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
	
	@Ignore
	@Test
	public void testWithTwentyThreads() {
		try {
			super.testWithNThreads(20);
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
