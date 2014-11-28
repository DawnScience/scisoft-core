/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.*;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
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
	public void lazyLoadLoaderFactory()  throws Exception {
		
		IDataHolder dataHolder = LoaderFactory.getData(testFileFolder+file, true, true, true, null);

		ILazyDataset lazy = dataHolder.getLazyDataset("Portable Grey Map");
		assertArrayEquals(new int[] {1024, 1024}, lazy.getShape());
		
		IDataset data = lazy.getSlice();
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
