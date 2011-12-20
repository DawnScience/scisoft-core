/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io.numpy;

import gda.analysis.io.ScanFileHolderException;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
		params.add(new Object[] { "'<f12'" }); // long float
		params.add(new Object[] { "'<c24'" }); // long complex
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
