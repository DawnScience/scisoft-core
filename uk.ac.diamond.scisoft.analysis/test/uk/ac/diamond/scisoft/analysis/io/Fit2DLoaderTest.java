/*
 * Copyright 2011 Diamond Light Source Ltd.
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

import gda.util.TestUtils;

import org.junit.BeforeClass;

// TODO FIXME Loader not functional as yet
public class Fit2DLoaderTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/EdfLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(PilatusEdfLoader.class.getCanonicalName());

	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
		
	}
	
	//@Test
	public void testSimpleParse()  throws Exception {
		
		final String path = testFileFolder+"/BAp4_background2.f2d";
		final Fit2DLoader loader = new Fit2DLoader(path);
		
		final DataHolder h = loader.loadFile();
	}

}
