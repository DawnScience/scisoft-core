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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.TestUtils;


public class PilatusEdfLoaderThreadTest extends LoaderThreadTestBase {
	
	static String testScratchDirectoryName = null;
	final static String testFileFolder = "testfiles/gda/analysis/io/EdfLoaderTest/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testScratchDirectoryName = TestUtils.generateDirectorynameFromClassname(PilatusEdfLoader.class.getCanonicalName());

	    TestUtils.makeScratchDirectory(testScratchDirectoryName);
		
	}
	
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

		IDataHolder dataHolder = LoaderFactory.getData(testFileFolder + "diff6105.edf", null);

		IDataset data = dataHolder.getDataset(PilatusEdfLoader.DATA_NAME);
		assertEquals(data.getDouble(0, 0), 98.0, 0.0);
		assertEquals(data.getDouble(2047, 2047), 199.0, 0.0);
	}
}
