/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.XRMCLoader.XRMCMetadata;

public class XRMCLoaderTest {

	static final String testFileName = "testfiles/images/image.xrmc";
	XRMCLoader loader;
	@Before
	public void setUp() {
		loader = new XRMCLoader(testFileName);
	}

	// expected array sizes
	final int nOrder = 2, nEnergy = 1, nX = 40, nY = 40;  
	
	@Test
	public void testLoadFile() {
		
		IDataHolder dh;
		
		try {
			dh = loader.loadFile(); 
		} catch (Exception e) {
			fail("Exception loading file:" + e.toString());
			return;
		}
		
		IDataset data = dh.getDataset(0);
		assertArrayEquals("Error in array shape", new int[]{nOrder, nEnergy, nX, nY}, data.getShape());
		assertEquals("Maximum values differ", 720000., (double) data.max(), 10000.);
	}
	
	@Test
	public void testMetadata() {
		IDataHolder dh;
		try {
			dh = loader.loadFile();
		} catch (Exception e) {
			fail("Exception loading file:" + e.toString());
			return;
		}
		IMetadata meta = dh.getMetadata();
		try {
			assertEquals("Error in number of orders metadata", (int) (double) meta.getMetaValue(XRMCMetadata.NORDER.getName()), nOrder);
			assertEquals("Error in number of columns metadata", (int) (double) meta.getMetaValue(XRMCMetadata.NCOLUMNS.getName()), nX);
			assertEquals("Error in number of rows metadata", (int) (double) meta.getMetaValue(XRMCMetadata.NROWS.getName()), nY);
			assertEquals("Error in pixel x size metadata", (double) meta.getMetaValue(XRMCMetadata.PIXELSIZEX.getName()), 1, 1e-12); // pixels are 1 cm × 1 cm
			assertEquals("Error in pixel y size metadata", (double) meta.getMetaValue(XRMCMetadata.PIXELSIZEY.getName()), 1, 1e-12);
			assertEquals("Error in exposure time metadata", (double) meta.getMetaValue(XRMCMetadata.EXPOSURETIME.getName()), 1.0, 1e-12);
			assertEquals("Error in number of energy bins metadata", (int) (double) meta.getMetaValue(XRMCMetadata.NENERGY.getName()), 1);
			assertEquals("Error in minimum energy metadata", (double) meta.getMetaValue(XRMCMetadata.MINENERGY.getName()), 0., 1e-12);
			assertEquals("Error in maximum energy metadata", (double) meta.getMetaValue(XRMCMetadata.MAXENERGY.getName()), 80., 1e-12);


		} catch(Exception e) {
			fail("Exception reading metadata: " + e.toString());
		}
	
	}

}
