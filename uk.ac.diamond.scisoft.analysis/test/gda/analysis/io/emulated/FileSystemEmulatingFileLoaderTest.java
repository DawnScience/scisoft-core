/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.io.emulated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gda.analysis.io.IFileLoader;
import gda.analysis.io.ScanFileHolderException;
import gda.analysis.io.emulated.FileSystemEmulatingFileLoader;

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
		DataHolder result = fileSystemEmulatingFileLoader.loadFile();
		long dt = System.currentTimeMillis() - t;
		System.out.println(dt);
		assertTrue((dt<1100) && (dt>998));  /* 1000 is theoretically the lower bound, but accept 999 also as this is seen occasionally */
		assertEquals(loadDataHolder, result);
	}
	
}
