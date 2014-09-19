/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io.numpy;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileLoader;

/**
 * Test that unsupported data types are correctly errored on
 */
@RunWith(Parameterized.class)
public class NumPyUnsupportedLoadTypesTest {

	@Parameters
	public static Collection<Object[]> configs() {
		List<Object[]> params = new LinkedList<Object[]>();
		params.add(new Object[] { "'>i4'" }); // wrong endian
// these two do not work with a 64-bit python
//		params.add(new Object[] { "'<f12'" }); // long float
//		params.add(new Object[] { "'<c24'" }); // long complex
		params.add(new Object[] { "'|O4'" }); // objects
		// This list isn't meant to be exhaustive
		return params;
	}

	private String numpyDataType;

	public NumPyUnsupportedLoadTypesTest(String numpyDataType) {
		this.numpyDataType = numpyDataType;
	}

	@Test(expected = ScanFileHolderException.class)
	public void testUnsupportedDataTypes() throws ScanFileHolderException, Exception {
		if (!PythonHelper.enablePythonTests()) {
			// because of expected= can't fall back on default Assume in runPythonScript to ignore this test
			throw new ScanFileHolderException(null);
		}
		
		File loc = NumPyTest.getTempFile();
		StringBuilder script = new StringBuilder();
		script.append("import numpy; ");
		script.append("exp=numpy.array(1, dtype=" + numpyDataType + "); ");
		script.append("numpy.save(r'" + loc.toString() + "', exp)");
		PythonHelper.runPythonScript(script.toString(), true);

		NumPyFileLoader.loadFileHelper(loc.toString());
	}
}
