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
import gda.util.TestUtils;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

public class Fit2DLoaderTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/Fit2dLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(Fit2DLoaderTest.class.getCanonicalName());

	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
		
	}
	
	@Test
	public void testF2dMetaLoader()  throws Exception {
		
		IMetaData meta = LoaderFactory.getMetaData(testFileFolder+"test1.f2d", null);
 		
		assertEquals(meta.getMetaValue("Dim_1"), "2048");
		assertEquals(meta.getMetaValue("Dim_2"), "2048");
	}
	
	@Test
	public void testF2dDataLoader()  throws Exception {
		
		final String path = testFileFolder+"/test1.f2d";
		DataHolder dataHolder = LoaderFactory.getData(path, null);
 		
		AbstractDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(2048,shape[0], 0.0);
		assertEquals(2048,shape[1], 0.0);
		assertEquals(0.0,data.getDouble(0,0), 0.0);
		assertEquals(2572.0, data.getDouble(1023, 1023), 0.0);
		assertEquals(0.0, data.getDouble(2047, 2047), 0.0);
	}

}
