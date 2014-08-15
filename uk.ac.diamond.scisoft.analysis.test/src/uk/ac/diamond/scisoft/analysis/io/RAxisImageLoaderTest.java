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

import org.apache.commons.lang.SerializationUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;

public class RAxisImageLoaderTest {

	static String TestFileFolder;
	static String filename;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		filename = TestFileFolder+"/RigakuLoaderTest/4_05_screen_0001.osc";
	}
	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoadFile() throws Exception {
		new RAxisImageLoader(filename).loadFile();
	}

	
	@Test
	public void testLoaderFactory() throws Exception {
		
		final IDataHolder dh = LoaderFactory.getData(filename, null);
		if (!dh.getNames()[0].equals("RAxis Image")) throw new Exception();
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new RAxisImageLoader(filename).loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
	}
}

