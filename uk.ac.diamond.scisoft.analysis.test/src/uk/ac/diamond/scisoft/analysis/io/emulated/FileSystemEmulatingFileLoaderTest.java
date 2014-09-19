/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.emulated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileLoader;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
public class FileSystemEmulatingFileLoaderTest {
	
	private IFileLoader mockLoader;
	private DataHolder loadDataHolder;
	private FileSystemEmulatingFileLoader fileSystemEmulatingFileLoader;

	@Before 
	public void setUp() throws ScanFileHolderException {
		mockLoader = mock(IFileLoader.class);
		loadDataHolder = mock(DataHolder.class);
		when(mockLoader.loadFile()).thenReturn(loadDataHolder);
		
	}
	
	@Test
	public void testEmulatedLatency() throws ScanFileHolderException {
		fileSystemEmulatingFileLoader = new FileSystemEmulatingFileLoader(mockLoader, 1000, 0);
		verifyResultAndDelayWas1s();
	}

	
	@Test
	public void testEmulatedFileloadTime() throws ScanFileHolderException {
		fileSystemEmulatingFileLoader = new FileSystemEmulatingFileLoader(mockLoader, 0, 1000);
		verifyResultAndDelayWas1s();
	}
	
	@Test
	public void testEmulatedFileloadTimeToShort() throws ScanFileHolderException {
		fileSystemEmulatingFileLoader = new FileSystemEmulatingFileLoader(mockLoader, 1000, 0);
		verifyResultAndDelayWas1s();
	}

	private void verifyResultAndDelayWas1s()
	throws ScanFileHolderException {
		long t = System.currentTimeMillis();
		IDataHolder result = fileSystemEmulatingFileLoader.loadFile();
		long dt = System.currentTimeMillis() - t;
		System.out.println(dt);
		assertTrue((dt<1100) && (dt>998));  /* 1000 is theoretically the lower bound, but accept 999 also as this is seen occasionally */
		assertEquals(loadDataHolder, result);
	}
	
}
