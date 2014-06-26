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

import junit.framework.Assert;

import org.apache.commons.lang.SerializationUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

/**
 * RubyLoaderTest Class
 */
public class CrysalisLoaderTest {
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		TestFileFolder += "/CrysalisLoaderTest/";
	}

	/**
	 * Testing loading a file into a sfh
	 * 
	 * @throws Exception if the file couldn't be loaded
	 */
	@Test
	public void testLoadFile() throws Exception {
		new CrysalisLoader(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img").loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoaderFactory() throws Exception {
		if (LoaderFactory.getData(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img", null) == null) throw new Exception();
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new CrysalisLoader(TestFileFolder + "ccd_direct_0deg_1000ms_1.img_1_uncomp.img").loadFile();
		AbstractDataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
	}
}
