/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;

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
		assertEquals("Wrong number of columns!", 73, dh.getNames().length);

		// Test some of the data
		assertEquals("The zeroth value of energy is incorrect", 6912.0000d, dh.getDataset("Energy").getDouble(0), 1e-4);
		assertEquals("The 488th value of energy is incorrect", 7962.0000d, dh.getDataset("Energy").getDouble(488), 1e-4);

		assertEquals("The zeroth value of Element 1 is incorrect", -39259.72d, dh.getDataset("Element 1").getDouble(0), 1e-2);
		assertEquals("The 488th value of Element 1 is incorrect", 327272.07d, dh.getDataset("Element 1").getDouble(488), 1e-2);
	}
}
