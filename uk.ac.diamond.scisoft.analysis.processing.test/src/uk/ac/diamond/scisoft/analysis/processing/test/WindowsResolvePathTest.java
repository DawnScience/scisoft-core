/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class WindowsResolvePathTest {
	
	@Before
	public void windowsOnly() {
	    org.junit.Assume.assumeTrue(isWindows());
	}

	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("win");
	}
	
	@Test
	public void testResolvePath() {
		
		String relative = "../../differentfile.nxs";
		String base = "C:\\this\\is\\a\\absolute\\location\\file.nxs";
		String result = "C:\\this\\is\\a\\differentfile.nxs";
		
		String p = ProcessingUtils.resolvePath(relative, base);
		
		assertEquals(result, p);
		
		relative = "./differentfile.nxs";
		base = "C:\\this\\is\\a\\absolute\\location\\file.nxs";
		result = "C:\\this\\is\\a\\absolute\\location\\differentfile.nxs";
		
		p = ProcessingUtils.resolvePath(relative, base);
		
		assertEquals(result, p);
		
		relative = "differentfile.nxs";
		base = "C:\\this\\is\\a\\absolute\\location\\file.nxs";
		result = "C:\\this\\is\\a\\absolute\\location\\differentfile.nxs";
		
		p = ProcessingUtils.resolvePath(relative, base);
		
		assertEquals(result, p);
		
		relative = "./onemore/differentfile.nxs";
		base = "C:\\this\\is\\a\\absolute\\location\\file.nxs";
		result = "C:\\this\\is\\a\\absolute\\location\\onemore\\differentfile.nxs";
		
		p = ProcessingUtils.resolvePath(relative, base);
		
		assertEquals(result, p);
		
		relative = "C:\\onemore\\differentfile.nxs";
		base = "\\this\\is\\a\\absolute\\location\\file.nxs";
		
		p = ProcessingUtils.resolvePath(relative, base);
		
		assertEquals(relative, p);
		
	}
	
}
