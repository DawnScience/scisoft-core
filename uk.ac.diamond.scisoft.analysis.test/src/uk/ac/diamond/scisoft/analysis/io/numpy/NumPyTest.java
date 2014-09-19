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
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileLoader;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver;

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
	protected static void saveNumPyFile(Dataset ds, File loc, boolean unsigned) throws ScanFileHolderException {
		final DataHolder dh = new DataHolder();
		dh.addDataset("", ds);
		new NumPyFileSaver(loc.toString(), unsigned).saveFile(dh);
	}

	static int[][] shapesToTest = { { 1 }, { 100 }, { 1000000 }, { 10, 10 }, { 5, 6, 7, 8 } };
	static Object[][] types = new Object[][] { { "'|b1'", Dataset.BOOL }, { "'|i1'", Dataset.INT8 },
		{ "'<i2'", Dataset.INT16 }, { "'<i4'", Dataset.INT32 }, { "'<i8'", Dataset.INT64 },
		{ "'|u1'", Dataset.INT8, true }, { "'<u2'", Dataset.INT16, true }, { "'<u4'", Dataset.INT32, true },
		{ "'<f4'", Dataset.FLOAT32 }, { "'<f8'", Dataset.FLOAT64 },
		{ "'<c8'", Dataset.COMPLEX64 }, { "'<c16'", Dataset.COMPLEX128 },};

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
				case Dataset.FLOAT32:
				case Dataset.FLOAT64:
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

	private Dataset createDataset() {
		final Dataset ds;
		if (abstractDatasetDataType != Dataset.BOOL) {
			ds = DatasetFactory.createRange(len, abstractDatasetDataType);
		} else {
			// creates an array of all False, so make two entries True if the array is big enough
			boolean[] boolarr = new boolean[len];
			if (len > 0)
				boolarr[0] = true;
			if (len > 100)
				boolarr[100] = true;
			ds = DatasetFactory.createFromObject(boolarr, abstractDatasetDataType);
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
		if (abstractDatasetDataType != Dataset.BOOL) {
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
		Dataset loadedFile = NumPyFileLoader.loadFileHelper(loc.toString());
		Dataset ds = createDataset();
		if (unsigned)
			ds = DatasetFactory.createFromObject(ds, unsigned);
		Assert.assertEquals(toString(), ds, loadedFile);
	}

	// This test writes an abstract data set with NumPyFileSaver and runs a short python script
	// to load it and check it is as expected
	@Test
	public void testSave() throws Exception {
		Dataset ds = createDataset();
		File loc = getTempFile();
		saveNumPyFile(ds, loc, unsigned);

		String script = createNumPyArray(" act=numpy.load(r'" + loc.toString() + "');" + PYTHON_NUMPY_PRINT_MATCHES);
		String pythonStdout = PythonHelper.runPythonScript(script, false);
		Assert.assertTrue(toString(), Boolean.parseBoolean(pythonStdout.trim()));
	}

	// Test we can load what we just saved
	@Test
	public void testSaveAndLoad() throws Exception {
		Dataset exp = createDataset();
		File loc = getTempFile();
		saveNumPyFile(exp, loc, unsigned);

		Dataset act = NumPyFileLoader.loadFileHelper(loc.toString());

		if (unsigned)
			exp = DatasetFactory.createFromObject(exp, unsigned);
		Assert.assertEquals(toString(), exp, act);
	}

}
