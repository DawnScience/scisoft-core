/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
