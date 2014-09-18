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

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;


public class PgmLoaderTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/PGMLoaderTest/";
	private String file = "image0001.pgm";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(PilatusEdfLoader.class.getCanonicalName());
	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
	}

	@Test
	public void load()  throws Exception {
		
		PgmLoader pgmLoader = new PgmLoader(testFileFolder+file);

		DataHolder dataHolder = pgmLoader.loadFile();
		assertEquals(pgmLoader.getHeaderValue("MagicNumber"), "P5");			
		assertEquals(pgmLoader.getHeaderValue("Width"), "1024");			
		assertEquals(pgmLoader.getHeaderValue("Height"), "1024");			
		assertEquals(pgmLoader.getHeaderValue("Maxval"), "65535");			

		Dataset data = dataHolder.getDataset("Portable Grey Map");
		// Check the first data point
		assertEquals(data.getDouble(0, 0), 0.0, 0.0);
		// Check the middle data point
		assertEquals(data.getDouble(512, 511), 15104.0, 0.0);			
		// Check the last data point
		assertEquals(data.getDouble(1023, 1023), 0.0, 0.0);
	}
	
	@Test
	public void loadLoaderFactory()  throws Exception {
		
		IDataHolder dataHolder = LoaderFactory.getData(testFileFolder+file, null);

		IDataset data = dataHolder.getDataset("Portable Grey Map");
		// Check the first data point
		assertEquals(data.getDouble(0, 0), 0.0, 0.0);
		// Check the middle data point
		assertEquals(data.getDouble(512, 511), 15104.0, 0.0);			
		// Check the last data point
		assertEquals(data.getDouble(1023, 1023), 0.0, 0.0);

	}

	@Test
	public void loadLoaderFactoryMetaData()  throws Exception {
		
		IMetadata meta = LoaderFactory.getMetadata(testFileFolder+file, null);
		assertEquals(meta.getMetaValue("MagicNumber"), "P5");			
		assertEquals(meta.getMetaValue("Width"), "1024");			
		assertEquals(meta.getMetaValue("Height"), "1024");			
		assertEquals(meta.getMetaValue("Maxval"), "65535");			

	}

}
