/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.metadata.IDiffractionMetadata;
import uk.ac.diamond.scisoft.analysis.metadata.IMetadata;

/**
 */
public class MAR345LoaderTest {
	static String TestFileFolder;

	static String testfile;

	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		TestFileFolder += "/MAR3450LoaderTest/";
		testfile = TestFileFolder + "image_130.mar3450";
	}

	@Test
	public void testLoaderFactory() throws Exception {
		IDataHolder dh = LoaderFactory.getData(testfile, null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
        		
		assertTrue(dh.getName(0).contains(AbstractFileLoader.DEF_IMAGE_NAME));
		
		IDataset data = dh.getDataset(0);
		checkImage(data);
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception
	 *             if the test fails
	 */
	@Test
	public void testLoadFile() throws Exception {
		DataHolder dh = new MAR345Loader(testfile).loadFile();

		Dataset image = dh.getDataset(0);
		checkImage(image);
	}

	private void checkImage(IDataset image) {
		Assert.assertEquals(2, image.getRank());
		Assert.assertEquals(3450, image.getShape()[0]);
		Assert.assertEquals(3450, image.getShape()[1]);
		Assert.assertEquals(0, image.getInt(0, 0));
		Assert.assertEquals(0, image.getInt(0, 100));
		Assert.assertEquals(0, image.getInt(0, 1000));
		Assert.assertEquals(0, image.getInt(1, 10));
		Assert.assertEquals(0, image.getInt(100, 100));
		Assert.assertEquals(0, image.getInt(3449, 3449));

		Assert.assertEquals(94, image.getInt(0, 1676));
		Assert.assertEquals(82, image.getInt(107, 1124));
		Assert.assertEquals(106, image.getInt(214, 891));
		Assert.assertEquals(70, image.getInt(321, 721));
		Assert.assertEquals(82, image.getInt(428, 586));
		Assert.assertEquals(108, image.getInt(534, 2887));
		Assert.assertEquals(183, image.getInt(641, 1738));
		Assert.assertEquals(142, image.getInt(748, 589));
		Assert.assertEquals(146, image.getInt(854, 2890));
		Assert.assertEquals(250, image.getInt(961, 1741));

		IMetadata md = image.getMetadata();
		Assert.assertTrue(md instanceof IDiffractionMetadata);
		DiffractionCrystalEnvironment env = ((IDiffractionMetadata) md).getDiffractionCrystalEnvironment();
		Assert.assertEquals(1.54179, env.getWavelength(), 1e-5);
		DetectorProperties det = ((IDiffractionMetadata) md).getDetector2DProperties();
		Assert.assertEquals(0, det.getDetectorDistance(), 1e-15);
	}
}
