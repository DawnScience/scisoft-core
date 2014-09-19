/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.apache.commons.lang.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.IExtendedMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

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
		testfile1 = "/ADSCImageTest/F6_1_001.img";
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
		
		final IDataHolder dh = LoaderFactory.getData(TestFileFolder + testfile1, null);
		if (!dh.getNames()[0].equals("ADSC Image")) throw new Exception();
	}
	
	@Test
	public void testMetaDataDate() throws Exception{
		DataHolder loader = new ADSCImageLoader(TestFileFolder + testfile1).loadFile();
		Dataset data = loader.getDataset(0);
		IExtendedMetadata metadata = (IExtendedMetadata) data.getMetadata();
		Date date = metadata.getCreation();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
		try {
			Assert.assertTrue(sdf.parse((String)metadata.getMetaValue("DATE")).equals(date));
		} catch (Exception e) {
			throw new Exception("Could not parse the date from the header", e);
		}
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
		Dataset data = loader.getDataset(0);
		IExtendedMetadata metadata = (IExtendedMetadata) data.getMetadata();
		System.out.println("File path is "+metadata.getFullPath());
		System.out.println("File size is "+metadata.getFileSize()+ " in bytes");
		System.out.println("File last modified "+metadata.getLastModified().toString());
		System.out.println("File owner is "+metadata.getFileOwner());
	}
	
	@Test
	public void testSerializability() throws Exception {
		DataHolder loader = new ADSCImageLoader(TestFileFolder + testfile1).loadFile();
		Dataset data;
		IDiffractionMetadata md, cmd;
		data = loader.getDataset(0);
		md = (IDiffractionMetadata) data.getMetadata();
		
		// test cloning
		cmd = md.clone();
		Assert.assertEquals("Metadata", md.getDiffractionCrystalEnvironment(), cmd.getDiffractionCrystalEnvironment());
		Assert.assertEquals("Metadata", md.getDetector2DProperties(), cmd.getDetector2DProperties());

		// test cloning serialization
		cmd = (IDiffractionMetadata) SerializationUtils.clone(md);
		Assert.assertEquals("Metadata", md.getDiffractionCrystalEnvironment(), cmd.getDiffractionCrystalEnvironment());
		Assert.assertEquals("Metadata", md.getDetector2DProperties(), cmd.getDetector2DProperties());
	}

	@Test
	public void testLoadWithMissingMetadata() throws Exception {
		DataHolder loader = new ADSCImageLoader(TestFileFolder + File.separator + "ADSCImageTest"+File.separator+"F6-invalidmd.img").loadFile();
		Assert.assertEquals("Metadata", null, loader.getMetadata());
		Dataset data = loader.getDataset(0);
		Assert.assertEquals("Data", 3072, data.getShapeRef()[0]);
		Assert.assertEquals("Data", 3072, data.getShapeRef()[1]);
	}
}
