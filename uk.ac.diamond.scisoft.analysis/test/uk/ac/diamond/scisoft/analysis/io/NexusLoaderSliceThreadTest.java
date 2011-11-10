/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

public class NexusLoaderSliceThreadTest extends LoaderThreadTestBase {
	
	private static String filename = System.getProperty("GDALargeTestFilesLocation")+"/NexusUITest/DCT_201006-good.h5";

	private SliceObject sliceObject;
	
	@Before
	public void createSliceObject() {
		sliceObject = new SliceObject();
		sliceObject.setName("NXdata.data");
		sliceObject.setPath(filename);
		sliceObject.setSliceStart(new int[]{0, 0, 1});
		sliceObject.setSliceStop(new int[]{61, 171, 2});
	}
	
	@Override
	@Test
	public void testInTestThread() throws Exception{
		super.testInTestThread();
	}
	
	@Override
	@Test
	@Ignore("2010/11/05 Test ignored since not consistently passing in Hudson")
	public void testWithTenThreads() throws Exception{
		super.testWithTenThreads();
	}

	@Test
	@Ignore("2010/11/05 Test ignored since not consistently passing in Hudson")
	public void testWithNThreads() throws Exception{
		super.testWithNThreads(100);
	}

	
	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {
		
		Assert.assertTrue(new File(filename).canRead());
        
		final SliceObject currentSlice = sliceObject.clone();
		currentSlice.setSliceStart(new int[]{0, 0, threadIndex+10});
		currentSlice.setSliceStop(new int[]{61, 171, threadIndex+11});

		final AbstractDataset slice = LoaderFactory.getSlice(currentSlice, null);
		Assert.assertTrue(slice.getSize()==(61*171));
	}
}
