/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.IMetadata;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatLoaderTest {

	@BeforeClass
	public static void setupClass() {
		LoaderFactory.setTestingForLoader("dat", null);
	}

	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testFeKedge_1_15() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/FeKedge_1_15.dat";
		final DataHolder dh = new DatLoader(testfile1).loadFile();
		assertEquals("Wrong number of columns!", 73, dh.getNames().length);
		
		// Test some of the data
		assertEquals("The zeroth value of energy is incorrect", 6912.0000d, dh.getDataset("Energy").getDouble(0), 1e-4);
		assertEquals("The 488th value of energy is incorrect", 7962.0000d, dh.getDataset("Energy").getDouble(488), 1e-4);
		
		assertEquals("The zeroth value of Element 1 is incorrect", -39259.72d, dh.getDataset("Element 1").getDouble(0), 1e-2);
		assertEquals("The 488th value of Element 1 is incorrect", 327272.07d, dh.getDataset("Element 1").getDouble(488), 1e-2);
	}

	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testMoFoil() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat";
		final DataHolder dh = new DatLoader(testfile1).loadFile();
		assertEquals("Wrong number of columns!", 13, dh.getNames().length);

		assertEquals("The zeroth value of Unknown is incorrect", 3556d, dh.getDataset("Unknown").getDouble(0), 1e-4);
		assertEquals("The 898th value of Unknown is incorrect", 1067d, dh.getDataset("Unknown").getDouble(898), 1e-4);
	}

	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testMoFoilLoaderFactory() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat";
		final IDataHolder dh = LoaderFactory.getData(testfile1, null);

		assertEquals("Wrong number of columns!", 13, dh.getNames().length);

		assertEquals("The zeroth value of Unknown is incorrect", 3556d, dh.getDataset("Unknown").getDouble(0), 1e-4);
		assertEquals("The 898th value of Unknown is incorrect", 1067d, dh.getDataset("Unknown").getDouble(898), 1e-4);
	}

	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testMoFoilLoaderFactoryMeta() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat";
		final IMetadata meta   = LoaderFactory.getMetadata(testfile1, null);
		assertEquals("Wrong number of columns!", 13, meta.getDataNames().size());

		assertTrue("There should be a (13 fluo channels) column!", meta.getDataNames().contains("(13 fluo channels)"));
		assertFalse("There should be no empty string column!", meta.getDataNames().contains(""));
	}


	/**
	 * File with data but without a header line. Therefore the 
	 * code has to work 
	 * @throws Exception 
	 */
	@Test
	public void testHorribleId143DatFile() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/bsa_013_01.dat";
		final IMetadata meta   = LoaderFactory.getMetadata(testfile1, null);
		final Collection<String> names = meta.getDataNames();

		assertTrue("No Unknown1 in meta data!", names.contains("Unknown1"));
		assertTrue("No Unknown2 in meta data!", names.contains("Unknown2"));

		final IDataHolder dh = LoaderFactory.getData(testfile1, null);
		final Map<String, ILazyDataset> data = dh.toLazyMap();
		for (String name : data.keySet()) {
			assertEquals("DatLoader did not set dataset name correctly!", name, data.get(name).getName());
		}
	}

	/**
	 * File with data but without a header line. Therefore the 
	 * code has to work 
	 * @throws Exception 
	 */
	@Test
	public void testPlainDatFile() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/noheader.dat";
		final IMetadata meta   = LoaderFactory.getMetadata(testfile1, null);
		final Collection<String> names = meta.getDataNames();
		assertTrue("No Column_1 in meta data!", names.contains("Column_1"));
		assertTrue("No Column_2 in meta data!", names.contains("Column_2"));

		final IDataHolder dh = LoaderFactory.getData(testfile1, null);
		final Map<String, ILazyDataset> data = dh.toLazyMap();
		for (String name : data.keySet()) {
			assertEquals("DatLoader did not set dataset name correctly!", name, data.get(name).getName());
		}
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new DatLoader("testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat").loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getFirstMetadata(IMetadata.class));
	}
	
	/**
	 * This method tests for correct number of datasets when there is a , in the dataset names
	 */
	@Test
	public void testBadDatasetNames() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/bad_names.dat";
		DataHolder loader = new DatLoader(testfile1).loadFile();
		final IMetadata meta   = loader.getMetadata();
		assertEquals("Wrong number of columns!", 4, meta.getDataNames().size());
	}
	
	/**
	 * This method tests for correct number of datasets when there is a , in the dataset names
	 */
	@Test
	public void testSingleColumn() throws Exception {
		
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/single.dat";
		DataHolder loader = new DatLoader(testfile1).loadFile();
		final IMetadata meta   = loader.getMetadata();
		assertEquals("Wrong number of columns!", 1, meta.getDataNames().size());
		Dataset dataset = loader.getDataset(0);
		assertEquals(dataset.getSize(), 11);
		assertEquals(dataset.getDouble(),-180.00, 0.000001);
		
	}
}
