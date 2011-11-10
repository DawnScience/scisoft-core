/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

import static org.junit.Assert.assertEquals;
import gda.util.TestUtils;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;


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

		AbstractDataset data = dataHolder.getDataset("Portable Grey Map");
		// Check the first data point
		assertEquals(data.getDouble(0, 0), 0.0, 0.0);
		// Check the middle data point
		assertEquals(data.getDouble(512, 511), 15104.0, 0.0);			
		// Check the last data point
		assertEquals(data.getDouble(1023, 1023), 0.0, 0.0);
	}
	
	@Test
	public void loadLoaderFactory()  throws Exception {
		
		DataHolder dataHolder = LoaderFactory.getData(testFileFolder+file, null);

		AbstractDataset data = dataHolder.getDataset("Portable Grey Map");
		// Check the first data point
		assertEquals(data.getDouble(0, 0), 0.0, 0.0);
		// Check the middle data point
		assertEquals(data.getDouble(512, 511), 15104.0, 0.0);			
		// Check the last data point
		assertEquals(data.getDouble(1023, 1023), 0.0, 0.0);

	}

	@Test
	public void loadLoaderFactoryMetaData()  throws Exception {
		
		IMetaData meta = LoaderFactory.getMetaData(testFileFolder+file, null);
		assertEquals(meta.getMetaValue("MagicNumber"), "P5");			
		assertEquals(meta.getMetaValue("Width"), "1024");			
		assertEquals(meta.getMetaValue("Height"), "1024");			
		assertEquals(meta.getMetaValue("Maxval"), "65535");			

	}

}
