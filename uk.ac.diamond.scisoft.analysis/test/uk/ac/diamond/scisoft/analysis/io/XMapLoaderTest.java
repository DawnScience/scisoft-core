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

import org.junit.Test;

/**
 * Deliberately brief testing at the moment, this is more of a run harness
 *
 */
public class XMapLoaderTest {

	/**
	 * Basic test where everything should run as expected
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testLoadFile() throws Exception {

		String testfile1 = "testfiles/gda/analysis/io/XMapLoaderTest/module1binary_31.zip";
		
		XMapLoader xMapLoader = new XMapLoader(testfile1);

		xMapLoader.loadFile();
	}

	@Test
	public void testLoaderFactory() throws Exception {
		DataHolder dh = LoaderFactory.getData( "testfiles/gda/analysis/io/XMapLoaderTest/module1binary_31.zip", null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
 	}
}
