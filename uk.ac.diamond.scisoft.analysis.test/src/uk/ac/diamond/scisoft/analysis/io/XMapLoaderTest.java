/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.junit.Test;

/**
 * Deliberately brief testing at the moment, this is more of a run harness
 *
 */
public class XMapLoaderTest {

	/**
	 * Basic test where everything should run as expected
	 * 
	 * @throws Exception if the test fails
	 */
	@Test
	public void testLoadFile() throws Exception {

		String testfile1 = "testfiles/gda/analysis/io/XMapLoaderTest/module1binary_31.zip";
		
		XMapLoader xMapLoader = new XMapLoader(testfile1);

		xMapLoader.loadFile();
	}

	@Test
	public void testLoaderFactory() throws Exception {
		IDataHolder dh = LoaderFactory.getData( "testfiles/gda/analysis/io/XMapLoaderTest/module1binary_31.zip", null);
        if (dh==null || dh.getNames().length<1) throw new Exception();
 	}
}
