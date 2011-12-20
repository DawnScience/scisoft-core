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

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileLoader;

/**
 * Test that NaN is properly loaded and stored
 */
@RunWith(Parameterized.class)
public class NumPyNanTest {

	@Parameters
	public static Collection<Object[]> configs() {
		List<Object[]> params = new LinkedList<Object[]>();
		params.add(new Object[] { "'<f4'", AbstractDataset.FLOAT32});
		params.add(new Object[] { "'<f8'", AbstractDataset.FLOAT64});
		return params;
	}

	private String numpyDataType;
	private int dtype;

	public NumPyNanTest(String numpyDataType, int dtype) {
		this.numpyDataType = numpyDataType;
		this.dtype = dtype;
	}

	@Test
	public void testLoad() throws Exception {
		File loc = NumPyTest.getTempFile();
		StringBuilder script = new StringBuilder();
		script.append("import numpy; ");
		script.append("exp=numpy.array([float('NaN')]*2, dtype=" + numpyDataType + "); ");
		script.append("numpy.save(r'" + loc.toString() + "', exp);");
		PythonHelper.runPythonScript(script.toString(), true);

		AbstractDataset loadedFile = NumPyFileLoader.loadFileHelper(loc.toString());
		Assert.assertTrue(ArrayUtils.isEquals(new int[] {2}, loadedFile.getShape()));
		Assert.assertEquals(dtype, loadedFile.getDtype());
		Assert.assertTrue(Double.isNaN(loadedFile.getDouble(0)));
		Assert.assertTrue(Double.isNaN(loadedFile.getDouble(1)));
	}

	@Test
	public void testSave() throws Exception {
		AbstractDataset ds = AbstractDataset.array(new double[] {Double.NaN, Double.NaN}, dtype);
		File loc = NumPyTest.getTempFile();
		NumPyTest.saveNumPyFile(ds, loc);
		StringBuilder script = new StringBuilder();
		script.append("import numpy; ");
		script.append("exp=numpy.array([float('NaN')]*2, dtype=" + numpyDataType + "); ");
		script.append("act=numpy.load(r'" + loc.toString() + "');");
		script.append("print(exp.dtype==act.dtype and exp.shape==act.shape and numpy.isnan(act[0]) and numpy.isnan(act[1]))");
		String pythonStdout = PythonHelper.runPythonScript(script.toString(), false);
		Assert.assertTrue(Boolean.parseBoolean(pythonStdout.trim()));
		
	}

}
