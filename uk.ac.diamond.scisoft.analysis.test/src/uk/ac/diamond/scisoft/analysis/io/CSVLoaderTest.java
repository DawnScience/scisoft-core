/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;

import org.eclipse.january.dataset.IDataset;
import org.junit.Test;

public class CSVLoaderTest {
	
	@Test
	public void testEPICScsv() throws Exception {
	
		final String testfile1 = "testfiles/scan.csv";
		final DataHolder dh = new CSVLoader(testfile1).loadFile();
		if (dh.getNames().length!=3) throw new Exception("There should be 3 columns!");
		
		String[] names = dh.getNames();
		
		assertEquals(names[0], "BL04J-AL-SLITS-02:TOP.VAL");
		assertEquals(names[1], "Points");
		assertEquals(names[2], "BL04J-EA-STK-03:IAMP4:I");
				
		IDataset d0 = dh.getLazyDataset(names[0]).getSlice();
		double v0 = d0.getDouble(0);
		double v1 = d0.getDouble(d0.getSize()-1);
		assertEquals(2.5, v0,0.0000000000001);
		assertEquals(-1.5, v1,0.0000000000001);
		assertEquals(80, d0.getSize());
		
	}
	
	@Test
	public void testHeadercsv() throws Exception {
		
		final String testfile1 = "testfiles/iris.csv";
		final DataHolder dh = new CSVLoader(testfile1).loadFile();
		if (dh.getNames().length!=4) throw new Exception("There should be 4 columns!");
		
		String[] names = dh.getNames();
		
		for (int i = 0 ; i < names.length; i++) {
			assertEquals("col"+(i+1), names[i]);
		}
				
		IDataset d0 = dh.getLazyDataset(names[0]).getSlice();
		double v0 = d0.getDouble(0);
		double v1 = d0.getDouble(d0.getSize()-1);
		assertEquals(5.1, v0,0.0000000000001);
		assertEquals(5.9, v1,0.0000000000001);
		assertEquals(150, d0.getSize());
		
	}
	
	@Test
	public void testNoHeadercsv() throws Exception {
	
		final String testfile1 = "testfiles/test.csv";
		final DataHolder dh = new CSVLoader(testfile1).loadFile();
		if (dh.getNames().length!=2) throw new Exception("There should be 2 columns!");
		
		String[] names = dh.getNames();
				
		IDataset d0 = dh.getLazyDataset(names[0]).getSlice();
		double v0 = d0.getDouble(0);
		double v1 = d0.getDouble(d0.getSize()-1);
		assertEquals(1, v0,0.0000000000001);
		assertEquals(7, v1,0.0000000000001);
		assertEquals(6, d0.getSize());
		
	}

}
