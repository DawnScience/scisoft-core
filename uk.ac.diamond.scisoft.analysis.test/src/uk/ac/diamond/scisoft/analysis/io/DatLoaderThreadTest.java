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

import org.junit.Test;

public class DatLoaderThreadTest extends LoaderThreadTestBase{

	@Override
	@Test
	public void testInTestThread() throws Exception{
		super.testInTestThread();
	}
	
	@Override
	@Test
	public void testWithTenThreads() throws Exception{
		super.testWithTenThreads();
	}

	@Override
	public void doTestOfDataSet(int threadIndex) throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/FeKedge_1_15.dat";
		final DataHolder dh = new DatLoader(testfile1).loadFile();
		if (dh.getNames().length!=73) throw new Exception("There should be 73 columns!");
		
		// Test some of the data
		if (dh.getDataset("Energy").getDouble(0)!=6912.0000d) throw new Exception("The first value of energy should be 6912.0000!");
		if (dh.getDataset("Energy").getDouble(488)!=7962.0000d) throw new Exception("The 488 value of energy should be 7962.0000!");
		
		if (dh.getDataset("Element 1").getDouble(0)!=-39259.72d) throw new Exception("The first value of Element 1 should be -39259.72!");
		if (dh.getDataset("Element 1").getDouble(488)!=327272.07d) throw new Exception("The 488 value of energy should be 327272.07!");

	}
}
