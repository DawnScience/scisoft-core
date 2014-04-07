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

package uk.ac.diamond.scisoft.analysis.io.emulated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.IDataHolder;
import uk.ac.diamond.scisoft.analysis.io.IFileLoader;
import uk.ac.diamond.scisoft.analysis.io.ScanFileHolderException;
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
