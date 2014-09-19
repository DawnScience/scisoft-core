/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import javax.vecmath.Vector3d;

import junit.framework.Assert;

import org.apache.commons.lang.SerializationUtils;
import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.IMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

public class DiffractionImageTest {
	static String testfile1 = null;
	static String testfile2 = null;
	static String testfile3 = null;

	static String TestFileFolder;
	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		testfile1 = TestFileFolder+"ADSCImageTest/F6_1_001.img";
		testfile2 = TestFileFolder+"MARImageTest/mar225_001.mccd";
		testfile3 = TestFileFolder+"CBFLoaderTest/xtal5e_1_0010.cbf";
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testLoaderFactory() throws Exception {
		
		if (LoaderFactory.getData(testfile1, null) == null) throw new Exception();
		if (LoaderFactory.getData(testfile2, null) == null) throw new Exception();
		if (LoaderFactory.getData(testfile3, null) == null) throw new Exception();
	}

	DetectorProperties detprops;
	DiffractionCrystalEnvironment dce;
	
	/**
	 * Test Loading
	 * 
	 * @throws Exception if the loading fails
	 */
	@Test
	public void testMetadata() throws Exception {
		DataHolder dh = new ADSCImageLoader(testfile1).loadFile();
		IMetadata metadata = dh.getDataset(0).getMetadata();
		if(metadata instanceof IDiffractionMetadata){
			detprops = ((IDiffractionMetadata)metadata).getDetector2DProperties();
			dce = ((IDiffractionMetadata)metadata).getDiffractionCrystalEnvironment();
		}
		double detSizeX = detprops.getDetectorSizeH();
		double detSizeY = detprops.getDetectorSizeV();
		double diagDetSize = Math.sqrt((detSizeX * detSizeX)
				+ (detSizeY * detSizeY));
		int[] detectorCorners = { 0, 0, 0, detprops.getPx(), detprops.getPy(), 0, detprops.getPx(), detprops.getPy() };
		System.out.println("ADSC "+detSizeX +" pix size "+detprops.getHPxSize() +" num pix "+ detprops.getPx());

		Vector3d px1topx4 = new Vector3d();
		px1topx4.sub(detprops.pixelPosition(detectorCorners[0], detectorCorners[1]),detprops.pixelPosition(detprops.getPx(), detprops.getPy()));
		Assert.assertEquals(diagDetSize, px1topx4.length(), 0.00001);
	}

	@Test
	public void testMAR() throws Exception {
		DataHolder dh = new MARLoader(testfile2).loadFile();
		IMetadata metadata = dh.getDataset(0).getMetadata();
		if(metadata instanceof IDiffractionMetadata){
			detprops = ((IDiffractionMetadata)metadata).getDetector2DProperties();
			dce = ((IDiffractionMetadata)metadata).getDiffractionCrystalEnvironment();
		}
		double detSizeX = detprops.getDetectorSizeH();
		double detSizeY = detprops.getDetectorSizeV();
		System.out.println("MAR "+detSizeX +" pix size "+detprops.getHPxSize() +" num pix "+ detprops.getPx());
		double diagDetSize = Math.sqrt((detSizeX * detSizeX)
				+ (detSizeY * detSizeY));
		int[] detectorCorners = { 0, 0, 0, detprops.getPx(), detprops.getPy(), 0, detprops.getPx(), detprops.getPy() };
		Vector3d px1topx4 = new Vector3d();
		px1topx4.sub(detprops.pixelPosition(detectorCorners[0], detectorCorners[1]),detprops.pixelPosition(detprops.getPx(), detprops.getPy()));
		Assert.assertEquals(diagDetSize, px1topx4.length(), 0.00001);
	}

	@Test
	public void testminiCBF() throws Exception {
		DataHolder dh = new CBFLoader(testfile3).loadFile();
		IMetadata metadata = dh.getDataset(0).getMetadata();
		if(metadata instanceof IDiffractionMetadata){
			detprops = ((IDiffractionMetadata)metadata).getDetector2DProperties();
			dce = ((IDiffractionMetadata)metadata).getDiffractionCrystalEnvironment();
		}		
		double detSizeX = detprops.getDetectorSizeH();
		double detSizeY = detprops.getDetectorSizeV();
		System.out.println("mini cbf "+detSizeX +" pix size "+detprops.getHPxSize() +" num pix "+ detprops.getPx());
		double diagDetSize = Math.sqrt((detSizeX * detSizeX)
				+ (detSizeY * detSizeY));
		int[] detectorCorners = { 0, 0, 0, detprops.getPx(), detprops.getPy(), 0, detprops.getPx(), detprops.getPy() };
		Vector3d px1topx4 = new Vector3d();
		px1topx4.sub(detprops.pixelPosition(detectorCorners[0], detectorCorners[1]),detprops.pixelPosition(detprops.getPx(), detprops.getPy()));
		Assert.assertEquals(diagDetSize, px1topx4.length(), 0.00001);
	}

	@Test
	public void testSerializability() throws Exception {
		Dataset data;
		data = new ADSCImageLoader(testfile1).loadFile().getDataset(0);
		SerializationUtils.serialize(data.getMetadata());

		data = new MARLoader(testfile2).loadFile().getDataset(0);
		SerializationUtils.serialize(data.getMetadata());

		data = new CBFLoader(testfile3).loadFile().getDataset(0);
		SerializationUtils.serialize(data.getMetadata());
}

}
