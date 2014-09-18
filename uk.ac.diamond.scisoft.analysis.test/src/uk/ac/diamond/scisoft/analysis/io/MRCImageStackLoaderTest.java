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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;

/**
 */
public class MRCImageStackLoaderTest {
	static String TestFileFolder;

	static String testfile;

	@BeforeClass
	static public void setUpClass() {
		TestFileFolder = TestUtils.getGDALargeTestFilesLocation();
		if( TestFileFolder == null){
			Assert.fail("TestUtils.getGDALargeTestFilesLocation() returned null - test aborted");
		}
		TestFileFolder += "/MRCImageStackLoaderTest/";
		testfile = TestFileFolder + "May10_15.48.32.mrc";
	}

	@Test
	public void testLoaderFactory() throws Exception {
		IDataHolder dh = LoaderFactory.getData(testfile, null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
        		
		assertTrue(dh.getName(0).contains(AbstractFileLoader.STACK_NAME));

		ILazyDataset image = dh.getLazyDataset(0);
		checkImage(image);
	}

	/**
	 * Test Loading
	 * 
	 * @throws Exception
	 *             if the test fails
	 */
	@Test
	public void testLoadFile() throws Exception {
		DataHolder dh = new MRCImageStackLoader(testfile).loadFile();

		ILazyDataset image = dh.getLazyDataset(0);
		checkImage(image);
	}

	private void checkImage(ILazyDataset image) { // 3838, 3710, 40
		Assert.assertEquals(3, image.getRank());
		Assert.assertEquals(40, image.getShape()[0]);
		Assert.assertEquals(3710, image.getShape()[1]);
		Assert.assertEquals(3838, image.getShape()[2]);

		IDataset subImage;
		subImage = image.getSlice(new Slice(1), null, null);
		Assert.assertEquals(3, subImage.getRank());
		Assert.assertEquals(1, subImage.getShape()[0]);
		Assert.assertEquals(3710, subImage.getShape()[1]);
		Assert.assertEquals(3838, subImage.getShape()[2]);

		subImage = image.getSlice((Slice) null, new Slice(1), null);
		Assert.assertEquals(40, subImage.getShape()[0]);
		Assert.assertEquals(1, subImage.getShape()[1]);
		Assert.assertEquals(3838, subImage.getShape()[2]);

		subImage = image.getSlice((Slice) null, null, new Slice(1));
		Assert.assertEquals(40, subImage.getShape()[0]);
		Assert.assertEquals(3710, subImage.getShape()[1]);
		Assert.assertEquals(1, subImage.getShape()[2]);

		subImage = image.getSlice(new Slice(1), null, new Slice(null, null, 2));
		Assert.assertEquals(1, subImage.getShape()[0]);
		Assert.assertEquals(3710, subImage.getShape()[1]);
		Assert.assertEquals(3838/2, subImage.getShape()[2]);
	}
}
