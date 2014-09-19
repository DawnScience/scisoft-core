/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.Test;

public class DatLoaderTest {

	
	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testFeKedge_1_15() throws Exception {
	
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/FeKedge_1_15.dat";
		final DataHolder dh = new DatLoader(testfile1).loadFile();
		if (dh.getNames().length!=73) throw new Exception("There should be 73 columns!");
		
		// Test some of the data
		if (dh.getDataset("Energy").getDouble(0)!=6912.0000d) throw new Exception("The first value of energy should be 6912.0000!");
		if (dh.getDataset("Energy").getDouble(488)!=7962.0000d) throw new Exception("The 488 value of energy should be 7962.0000!");
		
		if (dh.getDataset("Element 1").getDouble(0)!=-39259.72d) throw new Exception("The first value of Element 1 should be -39259.72!");
		if (dh.getDataset("Element 1").getDouble(488)!=327272.07d) throw new Exception("The 488 value of energy should be 327272.07!");
	}
	
	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testMoFoil() throws Exception {
	
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat";
		final DataHolder dh = new DatLoader(testfile1).loadFile();
		if (dh.getNames().length!=13) throw new Exception("There should be 13 columns!");

		if (dh.getDataset("Unknown").getDouble(0)!=3556d) throw new Exception("The first value of Unknown should be 3556!");
		if (dh.getDataset("Unknown").getDouble(898)!=1067d) throw new Exception("The 898 value of Unknown should be 1067!");
	}

	
	
	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testMoFoilLoaderFactory() throws Exception {
	
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat";
		final IDataHolder dh = LoaderFactory.getData(testfile1, null);
		if (dh.getNames().length!=13) throw new Exception("There should be 13 columns!");

		if (dh.getDataset("Unknown").getDouble(0)!=3556d) throw new Exception("The first value of Unknown should be 3556!");
		if (dh.getDataset("Unknown").getDouble(898)!=1067d) throw new Exception("The 898 value of Unknown should be 1067!");
	}

	
	/**
	 * This method tests for SciSoft trac #496
	 */
	@Test
	public void testMoFoilLoaderFactoryMeta() throws Exception {
	
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat";
		final IMetadata meta   = LoaderFactory.getMetadata(testfile1, null);
		if (meta.getDataNames().size()!=13) throw new Exception("There should be 13 columns!");

		if (!meta.getDataNames().contains("(13 fluo channels)")) throw new Exception("There should be a (13 fluo channels) column!");
		if (meta.getDataNames().contains("")) throw new Exception("There should be no empty string column!");
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
        if (!names.contains("Unknown1")) throw new Exception("No Unknown1 in meta data!");
        if (!names.contains("Unknown2")) throw new Exception("No Unknown2 in meta data!");
		
        final IDataHolder dh = LoaderFactory.getData(testfile1, null);
        final Map<String,ILazyDataset> data = dh.toLazyMap();
        for (String name : data.keySet()) {
			if (!data.get(name).getName().equals(name)) throw new Exception("DatLoader did not set dataset name correctly!");
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
        if (!names.contains("Column_1")) throw new Exception("No Unknown1 in meta data!");
        if (!names.contains("Column_2")) throw new Exception("No Unknown2 in meta data!");
		
        final IDataHolder dh = LoaderFactory.getData(testfile1, null);
        final Map<String,ILazyDataset> data = dh.toLazyMap();
        for (String name : data.keySet()) {
			if (!data.get(name).getName().equals(name)) throw new Exception("DatLoader did not set dataset name correctly!");
		}
	}

	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new DatLoader("testfiles/gda/analysis/io/DatLoaderTest/MoFoil.dat").loadFile();
		Dataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
	}
}
