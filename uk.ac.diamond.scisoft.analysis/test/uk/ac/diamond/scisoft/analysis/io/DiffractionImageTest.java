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

import gda.util.TestUtils;

import javax.vecmath.Vector3d;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

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
		IMetaData metadata = dh.getDataset(0).getMetadata();
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
	@Ignore("This is being ignored intil the MAR loader has been refactored to use the new metadata, 10 Nov 11")
	public void testMAR() throws Exception {
		DataHolder dh = new MARLoader(testfile2).loadFile();
		IMetaData metadata = dh.getDataset(0).getMetadata();
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
		IMetaData metadata = dh.getDataset(0).getMetadata();
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
}
