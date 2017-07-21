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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.metadata.IMetadata;
import org.junit.Before;
//import org.junit.Before;
import org.junit.Test;

public class JCPDSLoaderTest {

	private DataHolder dh;	
	
	@Before
	public void setUp() {
		JCPDSLoader loadOTron = new JCPDSLoader("testfiles/diamond.jcpds");
		
		dh = null;
		try {
			dh = loadOTron.loadFile();
		} catch (ScanFileHolderException sFHE) {
			fail("Exception in reading file: " + sFHE);
			return;
		}
	}

	@Test
	public void testData() {
		
		Dataset d = dh.getDataset("d");
		
		assertEquals(5, d.getSize());
		
		assertEquals(2.0600, d.getDouble(0), 5e-5);
		assertEquals(27., dh.getDataset("i").getDouble(1), 5e-1);
		assertEquals(3., dh.getDataset("h").getDouble(2), 5e-1);
		assertEquals(0., dh.getDataset("k").getDouble(3), 5e-1);
		assertEquals(1., dh.getDataset("l").getDouble(4), 5e-1);
		
	}
	
	@Test
	public void testMetadata() {

		IMetadata metadata = dh.getMetadata();
		Collection<String> metaNames = new HashSet<String>();
		try {
		metaNames = metadata.getMetaNames();
		} catch (MetadataException mE) {
			fail("MetadataException " + mE.toString());
		}
		assertTrue(metaNames.contains("K0"));
		assertTrue(metaNames.contains("K0P"));
		assertTrue(metaNames.contains("SYMMETRY"));
		assertTrue(metaNames.contains("A"));
		assertTrue(metaNames.contains("ALPHAT"));
		
		try {
			assertEquals(metadata.getMetaValue("K0"), "999.000");
			assertEquals(metadata.getMetaValue("K0P"), "1.00000");
			assertEquals(metadata.getMetaValue("SYMMETRY"), "CUBIC");
			assertEquals(metadata.getMetaValue("A"), "3.56670");
			assertEquals(metadata.getMetaValue("ALPHAT"), "0.000000");
		} catch (MetadataException mE) {
			fail("MetadataException " + mE.toString());
		}
		
	}

}
