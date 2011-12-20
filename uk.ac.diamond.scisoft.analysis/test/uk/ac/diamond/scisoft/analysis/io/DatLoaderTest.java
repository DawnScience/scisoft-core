/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

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
		final DataHolder dh = LoaderFactory.getData(testfile1, null);
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
		
        final DataHolder dh = LoaderFactory.getData(testfile1, null);
        final Map<String,ILazyDataset> data = dh.getMap();
        for (String name : data.keySet()) {
			if (!data.get(name).getName().equals(name)) throw new Exception("DatLoader did not set dataset name correctly!");
		}
	}
}
