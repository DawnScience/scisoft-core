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

package uk.ac.diamond.scisoft.analysis.io.numpy;

import java.io.File;
import java.io.IOException;
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
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileLoader;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver;
import uk.ac.diamond.scisoft.analysis.io.ScanFileHolderException;

@RunWith(Parameterized.class)
public class NumPyTest {

	protected static final String PYTHON_NUMPY_PRINT_MATCHES = "print(exp.dtype==act.dtype and isinstance((exp==act), numpy.ndarray) and (exp==act).all())";

	/**
	 * Return a self deleting temp file
	 * 
	 * @return temp file
	 * @throws IOException
	 */
	protected static File getTempFile() throws IOException {
		File loc = File.createTempFile("scisoft", ".npy");
		loc.deleteOnExit();
		return loc;
	}

	/**
	 * Wraps a dataset in a dataholder and saves it to the specified location
	 * 
	 * @param ds
	 *            to save
	 * @param loc
	 *            to save to
	 * @throws ScanFileHolderException
	 */
	protected static void saveNumPyFile(AbstractDataset ds, File loc, boolean unsigned) throws ScanFileHolderException {
		final DataHolder dh = new DataHolder();
		dh.addDataset("", ds);
		new NumPyFileSaver(loc.toString(), unsigned).saveFile(dh);
	}

	static int[][] shapesToTest = { { 1 }, { 100 }, { 1000000 }, { 10, 10 }, { 5, 6, 7, 8 } };
	static Object[][] types = new Object[][] { { "'|b1'", AbstractDataset.BOOL }, { "'|i1'", AbstractDataset.INT8 },
		{ "'<i2'", AbstractDataset.INT16 }, { "'<i4'", AbstractDataset.INT32 }, { "'<i8'", AbstractDataset.INT64 },
		{ "'|u1'", AbstractDataset.INT8, true }, { "'<u2'", AbstractDataset.INT16, true }, { "'<u4'", AbstractDataset.INT32, true },
		{ "'<f4'", AbstractDataset.FLOAT32 }, { "'<f8'", AbstractDataset.FLOAT64 },
		{ "'<c8'", AbstractDataset.COMPLEX64 }, { "'<c16'", AbstractDataset.COMPLEX128 },};

	@Parameters
	public static Collection<Object[]> configs() {
		List<Object[]> params = new LinkedList<Object[]>();
		int index = 0;
		for (int i = 0; i < types.length; i++) {
			Object[] type = types[i];
			boolean unsigned = type.length > 2;
			for (int j = 0; j < shapesToTest.length; j++) {
				params.add(new Object[] { index++, type[0], type[1], shapesToTest[j], false, unsigned });
				switch ((Integer) type[1]) {
				case AbstractDataset.FLOAT32:
				case AbstractDataset.FLOAT64:
					// Add some Inf values
					params.add(new Object[] { index++, type[0], type[1], shapesToTest[j], true, unsigned });
				}
			}
		}
		return params;
	}

	private int index = 0;
	private String numpyDataType;
	private int abstractDatasetDataType;
	private int[] shape;
	private String shapeStr;
	private int len;
	private boolean addInf;
	private boolean unsigned;

	public NumPyTest(int index, String numpyDataType, int abstractDatasetDataType, int[] shape, boolean addInf, boolean unsigned) {
		this.index = index;
		this.numpyDataType = numpyDataType;
		this.abstractDatasetDataType = abstractDatasetDataType;
		this.shape = shape;
		this.addInf = addInf;
		this.unsigned = unsigned;
		this.len = 1;
		for (int i = 0; i < shape.length; i++) {
			this.len *= shape[i];
		}
		this.shapeStr = ArrayUtils.toString(shape);
		this.shapeStr = this.shapeStr.substring(1, shapeStr.length() - 1);
		// System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return String.format("TEST %d: numpyType=%s datasetType=%d len=%d shape=%s", index, numpyDataType,
				abstractDatasetDataType, len, ArrayUtils.toString(shape));
	}

	private AbstractDataset createDataset() {
		final AbstractDataset ds;
		if (abstractDatasetDataType != AbstractDataset.BOOL) {
			ds = AbstractDataset.arange(len, abstractDatasetDataType);
		} else {
			// creates an array of all False, so make two entries True if the array is big enough
			boolean[] boolarr = new boolean[len];
			if (len > 0)
				boolarr[0] = true;
			if (len > 100)
				boolarr[100] = true;
			ds = AbstractDataset.array(boolarr, abstractDatasetDataType);
		}
		if (addInf && len > 3) {
			ds.set(Double.POSITIVE_INFINITY, 2);
			ds.set(Double.NEGATIVE_INFINITY, 3);
		}
		ds.setShape(shape);
		return ds;
	}

	private String createNumPyArray(String postCommands) {
		StringBuilder script = new StringBuilder();
		script.append("import numpy; ");
		if (abstractDatasetDataType != AbstractDataset.BOOL) {
			script.append("exp=numpy.arange(" + len + ", dtype=" + numpyDataType + "); ");
		} else {
			script.append("exp=numpy.array([False] * " + len + ", dtype=" + numpyDataType + "); ");
			if (len > 0)
				script.append("exp[0]=True; ");
			if (len > 100)
				script.append("exp[100]=True; ");
		}
		if (addInf && len > 3) {
			script.append("exp[2]=float('Inf'); ");
			script.append("exp[3]=-float('Inf'); ");
		}
		script.append("exp.shape=" + shapeStr + "; ");
		script.append(postCommands);
		return script.toString();
	}

	// This test writes a numpy array with a small python script and then uses NumPyFileLoader to load it, and then make
	// sure it is equals to a newly created abstract dataset
	@Test
	public void testLoad() throws Exception {
		File loc = getTempFile();
		String script = createNumPyArray(" numpy.save(r'" + loc.toString() + "', exp)");
		PythonHelper.runPythonScript(script, true);
		AbstractDataset loadedFile = NumPyFileLoader.loadFileHelper(loc.toString());
		AbstractDataset ds = createDataset();
		if (unsigned)
			ds = AbstractDataset.array(ds, unsigned);
		Assert.assertEquals(ds, loadedFile);
	}

	// This test writes an abstract data set with NumPyFileSaver and runs a short python script
	// to load it and check it is as expected
	@Test
	public void testSave() throws Exception {
		AbstractDataset ds = createDataset();
		File loc = getTempFile();
		saveNumPyFile(ds, loc, unsigned);

		String script = createNumPyArray(" act=numpy.load(r'" + loc.toString() + "');" + PYTHON_NUMPY_PRINT_MATCHES);
		String pythonStdout = PythonHelper.runPythonScript(script, false);
		Assert.assertTrue(Boolean.parseBoolean(pythonStdout.trim()));
	}

	// Test we can load what we just saved
	@Test
	public void testSaveAndLoad() throws Exception {
		AbstractDataset exp = createDataset();
		File loc = getTempFile();
		saveNumPyFile(exp, loc, unsigned);

		AbstractDataset act = NumPyFileLoader.loadFileHelper(loc.toString());

		if (unsigned)
			exp = AbstractDataset.array(exp, unsigned);
		Assert.assertEquals(exp, act);
	}

}
