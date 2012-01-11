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

import org.junit.Ignore;
import org.junit.Test;

public class NexusLoaderThreadTest extends LoaderThreadTestBase {

	private static String filename = "testfiles/gda/analysis/io/NexusLoaderTest/FeKedge_1_15.nxs";

	@Override
	@Test
	public void testInTestThread() throws Exception {
		super.testInTestThread();
	}

	@Test
	public void testWithThreads() throws Exception {
		super.testWithNThreads(2);
	}

	@Override
	@Test
	@Ignore("2012/01/10 Test ignored since not consistently passing in Hudson")
	public void testWithTenThreads() throws Exception {
		super.testWithTenThreads();
	}

	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {

		Assert.assertTrue(new File(filename).canRead());

		final DataHolder dh = LoaderFactory.getData(filename, null);
		Assert.assertTrue(dh.getLazyDataset("/entry1/counterTimer01/lnI0It").getSize() == 489);
	}
}
