/*
 * Copyright 2012 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

public class Fit2DMaskLoaderTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/Fit2dLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(Fit2DMaskLoaderTest.class.getCanonicalName());
	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
		
	}
	
	@Test
	public void testReadMask1()  throws Exception {
		
		final String path = testFileFolder+"/fit2d.msk";
		DataHolder dataHolder = LoaderFactory.getData(path, null);
		
		AbstractDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(3072,shape[0], 0.0);
		assertEquals(3072,shape[1], 0.0);
		assertEquals(true,data.getBoolean(0,0));
		assertEquals(true, data.getBoolean(1023, 1023));
		assertEquals(false, data.getBoolean(2047, 2047));
	}
	
	@Test
	public void testReadMask2()  throws Exception {
		
		final String path = testFileFolder+"/fit2d2.msk";
		DataHolder dataHolder = LoaderFactory.getData(path, null);
		
		AbstractDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(2048,shape[0], 0.0);
		assertEquals(2048,shape[1], 0.0);
		assertEquals(true,data.getBoolean(0,0));
		assertEquals(false, data.getBoolean(80, 80));
		assertEquals(true, data.getBoolean(2047, 2047));
	}

}
