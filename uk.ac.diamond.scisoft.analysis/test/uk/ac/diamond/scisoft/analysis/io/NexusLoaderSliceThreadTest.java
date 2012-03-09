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

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

public class NexusLoaderSliceThreadTest extends LoaderThreadTestBase {

	private static String filename = System.getProperty("GDALargeTestFilesLocation") + "NexusUITest/DCT_201006-good.h5";

	private SliceObject sliceObject;

	@Before
	public void createSliceObject() {
		sliceObject = new SliceObject();
		sliceObject.setName("/RawDCT/NXdata/data");
		sliceObject.setPath(filename);
		sliceObject.setSliceStart(new int[] { 0, 0, 1 });
		sliceObject.setSliceStop(new int[] { 61, 171, 2 });
	}

	@Override
	@Test
	public void testInTestThread() throws Exception {
		super.testInTestThread();
	}

	@Override
	@Test
	public void testWithTenThreads() throws Exception {
		super.testWithTenThreads();
	}

	@Test
	public void testWithNThreads() throws Exception {
		super.testWithNThreads(60);
	}

	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {

		Assert.assertTrue(new File(filename).canRead());

		final SliceObject currentSlice = sliceObject.clone();
		currentSlice.setSliceStart(new int[] { 0, 0, threadIndex + 10 });
		currentSlice.setSliceStop(new int[] { 61, 171, threadIndex + 11 });

		final AbstractDataset slice = LoaderFactory.getSlice(currentSlice, null);
		Assert.assertTrue(slice.getSize() == (61 * 171));
	}
}
