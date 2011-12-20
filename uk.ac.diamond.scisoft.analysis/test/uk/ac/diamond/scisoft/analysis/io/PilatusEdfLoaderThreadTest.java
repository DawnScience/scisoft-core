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

import static org.junit.Assert.assertEquals;
import gda.util.TestUtils;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;


public class PilatusEdfLoaderThreadTest extends LoaderThreadTestBase {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/EdfLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(PilatusEdfLoader.class.getCanonicalName());

	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
		
	}
	
	@Override
	@Test
	public void testInTestThread() throws Exception{
		super.testInTestThread();
	}
	
	@Override
	@Test
	public void testWithTenThreads() throws Exception{
		super.testWithTenThreads();
	}

	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {
		
		DataHolder dataHolder = LoaderFactory.getData(testFileFolder+"diff6105.edf", null);
     		
		AbstractDataset data = dataHolder.getDataset("ESRF Pilatus Data");
		assertEquals(data.getDouble(0, 0),      98.0, 0.0);
		assertEquals(data.getDouble(2047, 2047),199.0, 0.0);
	}
}
