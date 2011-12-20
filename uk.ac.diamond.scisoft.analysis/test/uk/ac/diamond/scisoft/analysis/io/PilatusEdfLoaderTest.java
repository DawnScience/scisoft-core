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

import static org.junit.Assert.assertEquals;
import gda.util.TestUtils;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;


public class PilatusEdfLoaderTest {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/EdfLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(PilatusEdfLoader.class.getCanonicalName());

	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
		
	}
	
	@Test
	public void testUnsignedShortFiles()  throws Exception {
		testShortFile(System.getProperty("GDALargeTestFilesLocation")+"/EDFLoaderTest/billeA_4201_EF_XRD_5000.edf");
		testShortFile(System.getProperty("GDALargeTestFilesLocation")+"/EDFLoaderTest/billeA_4201_EF_XRD_5873.edf");
	}
	
	private void testShortFile(final String filePath) throws Exception {
		
		DataHolder dataHolder = LoaderFactory.getData(filePath, null);		 		
	    if (dataHolder.getMap().size()!=1) throw new Exception("Should only be one data set");

	    final AbstractDataset set = (AbstractDataset)dataHolder.getMap().values().iterator().next();
	    final int[] shape = set.getShape();
	    if (shape[0]!=2048) throw new Exception("Wrong size of dimension 0, should be 2048!");
	    if (shape[1]!=2048) throw new Exception("Wrong size of dimension 1, should be 2048!");
	    
	    
	}
	
	@Test
	public void testBoundsOnIntegerDataSet() throws Exception {
		
		DataHolder dataHolder = LoaderFactory.getData(testFileFolder+"diff6105.edf", null);
     		
		IntegerDataset dataSet = (IntegerDataset)dataHolder.getDataset("ESRF Pilatus Data");
		final int min = dataSet.min().intValue();
		final int max = dataSet.max().intValue();
		
		final int[] data  = dataSet.getData();
		int realMin = Integer.MAX_VALUE;
		int realMax = Integer.MIN_VALUE;
		for (int i = 0; i < data.length; i++) {
			realMin = Math.min(realMin, data[i]);
			realMax = Math.max(realMax, data[i]);
		}
		
		if (min!=realMin) throw new Exception("Maths incorrect in IntegerDataset incorrect minimum calculated!");
		if (max!=realMax) throw new Exception("Maths incorrect in IntegerDataset incorrect maximum calculated!");
	}
	
	@Test
	public void testUseLoaderFactory() throws Exception {
		
		DataHolder dataHolder = LoaderFactory.getData(testFileFolder+"diff6105.edf", null);
     		
		AbstractDataset data = dataHolder.getDataset("ESRF Pilatus Data");
		assertEquals(data.getDouble(0, 0),      98, 0.0);
		assertEquals(data.getDouble(2047, 2047), 199, 0.0);
	}
	
	
	@Test
	public void testUseMetaDataLoaderFactory() throws Exception {
		
		IMetaData meta = LoaderFactory.getMetaData(testFileFolder+"diff6105.edf", null);
     		
		assertEquals(meta.getMetaValue("HeaderID"), "EH:000001:000000:000000");			
		assertEquals(meta.getMetaValue("Image"), "1");			
		assertEquals(meta.getMetaValue("ByteOrder"), "LowByteFirst");			
		assertEquals(meta.getMetaValue("DataType"), "UnsignedShort");			
		assertEquals(meta.getMetaValue("Dim_1"), "2048");			
		assertEquals(meta.getMetaValue("Dim_2"), "2048");			
		assertEquals(meta.getMetaValue("Size"), "8388608");			
		assertEquals(meta.getMetaValue("time"), "Thu Jun 18 04:24:25 2009");			
		assertEquals(meta.getMetaValue("count_time"), "Na");
		assertEquals(meta.getMetaValue("title"), "ESPIA FRELON Image 6105");			
		assertEquals(meta.getMetaValue("run"), "6105");
	}


	@Test
	public void loadDiff6105() throws Exception {
		
		PilatusEdfLoader edfLoader = new PilatusEdfLoader(testFileFolder+"diff6105.edf");
		DataHolder dataHolder = edfLoader.loadFile();
     
		assertEquals(edfLoader.getMetaData().getMetaValue("HeaderID"), "EH:000001:000000:000000");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Image"), "1");			
		assertEquals(edfLoader.getMetaData().getMetaValue("ByteOrder"), "LowByteFirst");			
		assertEquals(edfLoader.getMetaData().getMetaValue("DataType"), "UnsignedShort");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Dim_1"), "2048");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Dim_2"), "2048");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Size"), "8388608");			
		assertEquals(edfLoader.getMetaData().getMetaValue("time"), "Thu Jun 18 04:24:25 2009");			
		assertEquals(edfLoader.getMetaData().getMetaValue("count_time"), "Na");
		assertEquals(edfLoader.getMetaData().getMetaValue("title"), "ESPIA FRELON Image 6105");			
		assertEquals(edfLoader.getMetaData().getMetaValue("run"), "6105");
		
		AbstractDataset data = dataHolder.getDataset("ESRF Pilatus Data");
		assertEquals(data.getDouble(0, 0),      98.0, 0.0);
		assertEquals(data.getDouble(2047, 2047),199.0, 0.0);
	}
	
	@Test
	public void load() throws Exception {
		PilatusEdfLoader edfLoader = new PilatusEdfLoader(testFileFolder+"pilatus300k.edf");

		DataHolder dataHolder = edfLoader.loadFile();
		assertEquals(edfLoader.getMetaData().getMetaValue("HeaderID"), "EH:000001:000000:000000");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Image"), "1");			
		assertEquals(edfLoader.getMetaData().getMetaValue("VersionNumber"), "0.10");			
		assertEquals(edfLoader.getMetaData().getMetaValue("ByteOrder"), "LowByteFirst");			
		assertEquals(edfLoader.getMetaData().getMetaValue("DataType"), "SignedInteger");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Dim_1"), "1475");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Dim_2"), "195");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Size"), "1150500");			
		assertEquals(edfLoader.getMetaData().getMetaValue("Date"), "Wed Jun 16 19:14:09 2010");			
		assertEquals(edfLoader.getMetaData().getMetaValue("count_time"), "1.000000000");
		assertEquals(edfLoader.getMetaData().getMetaValue("title"), "# Pixel_size 172e-6 m x 172e-6 m");			
		assertEquals(edfLoader.getMetaData().getMetaValue("run"), "0");

		AbstractDataset data = dataHolder.getDataset("ESRF Pilatus Data");
		// Check the first 5 data points
		assertEquals(data.getDouble(0, 0), 38.0, 0.0);
		assertEquals(data.getDouble(0, 1), 38.0, 0.0);
		assertEquals(data.getDouble(0, 2), 34.0, 0.0);
		assertEquals(data.getDouble(0, 3), 36.0, 0.0);
		assertEquals(data.getDouble(0, 4), 30.0, 0.0);
		// Check the last 5 data points
		assertEquals(data.getDouble(194, 1470), 223.0, 0.0);
		assertEquals(data.getDouble(194, 1471), 212.0, 0.0);
		assertEquals(data.getDouble(194, 1472), 179.0, 0.0);
		assertEquals(data.getDouble(194, 1473), 191.0, 0.0);
		assertEquals(data.getDouble(194, 1474), 229.0, 0.0);
		
	}
}
