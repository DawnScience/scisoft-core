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

import java.util.Date;
import java.util.GregorianCalendar;

import gda.util.TestUtils;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

/**
 * Tests the ADSC image loader with file in TestFiles
 */
public class ADSCImageTest {
	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
	}

	static String testfile1 = null;

	@BeforeClass
	public static void setUpBeforeClass() {
		testfile1 = "ADSCImageTest/F6_1_001.img";
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoadFile() throws Exception {
		new ADSCImageLoader(TestFileFolder + testfile1).loadFile();
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoaderFactory() throws Exception {
		
		final DataHolder dh = LoaderFactory.getData(TestFileFolder + testfile1, null);
		if (!dh.getNames()[0].equals("ADSC Image")) throw new Exception();
	}
	
	@Test
	public void testMetaDataDate() throws Exception{
		DataHolder loader = new ADSCImageLoader(TestFileFolder + testfile1).loadFile();
		AbstractDataset data = loader.getDataset(0);
		IExtendedMetadata metadata = (IExtendedMetadata) data.getMetadata();
		Date date = metadata.getCreation();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		String[] parsedTime = gc.getTime().toString().split(" ");
		String[] rawTime = ((String)metadata.getMetaValue("DATE")).split(" ");
		Assert.assertTrue(parsedTime[0].equalsIgnoreCase(rawTime[0]));
		Assert.assertTrue(parsedTime[1].equalsIgnoreCase(rawTime[1]));
		Assert.assertTrue(parsedTime[2].equalsIgnoreCase(rawTime[2]));
		Assert.assertTrue(parsedTime[3].equalsIgnoreCase(rawTime[3]));
		Assert.assertTrue(parsedTime[5].equalsIgnoreCase(rawTime[4])); // This is because the date in GregorianCalendar contains GMT and the ADSC doesn't
	}
	
	@Test
	public void testFileParsedMetaData() throws Exception{
		DataHolder loader = new ADSCImageLoader(TestFileFolder + testfile1).loadFile();
		AbstractDataset data = loader.getDataset(0);
		IExtendedMetadata metadata = (IExtendedMetadata) data.getMetadata();
		System.out.println("File path is "+metadata.getFullPath());
		System.out.println("File size is "+metadata.getFileSize()+ " in bytes");
		System.out.println("File last modified "+metadata.getLastModified().toString());
		System.out.println("File owner is "+metadata.getFileOwner());
	}
	
	
}
