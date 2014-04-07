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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ILazyDataset;

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
		final IMetaData meta   = LoaderFactory.getMetaData(testfile1, null);
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
		final IMetaData meta   = LoaderFactory.getMetaData(testfile1, null);
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
		final IMetaData meta   = LoaderFactory.getMetaData(testfile1, null);
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
		AbstractDataset data = loader.getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
	}
}
