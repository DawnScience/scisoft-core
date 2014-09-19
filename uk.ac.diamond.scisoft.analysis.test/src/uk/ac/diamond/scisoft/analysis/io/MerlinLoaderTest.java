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
import org.junit.Test;

public class MerlinLoaderTest {
	
	final static String testFileFolder = "testfiles/gda/analysis/io/merlin/";
	
	@Test
	public void testMerlinDataLoader()  throws Exception {
		
		final String path = testFileFolder+"default1.mib";
		IDataHolder dataHolder = LoaderFactory.getData(path, null);
 		
		IDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(512,shape[0], 0.0);
		assertEquals(512,shape[1], 0.0);
		assertEquals(4095,data.max().intValue(), 0.0);
	}

	@Test
	public void testMerlinDataLoaderMultiFile()  throws Exception {
		
		final String path = testFileFolder+"multi.mib";
		IDataHolder dataHolder = LoaderFactory.getData(path, null);
 		
		IDataset data = dataHolder.getDataset(0);
		int[] shape = data.getShape();
		assertEquals(10,shape[0], 0.0);
		assertEquals(515,shape[1], 0.0);
		assertEquals(515,shape[2], 0.0);
		assertEquals(2862,data.max().intValue(), 0.0);
	}
	
}
