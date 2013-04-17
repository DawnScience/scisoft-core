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

import gda.analysis.io.ScanFileHolderException;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.PythonHelper;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.NumPyFileSaver;

/**
 * This tests special cases not covered by the other tests which simply sweeps data types
 */
public class NumPySpecialsTest {

	@Test(expected = ScanFileHolderException.class)
	public void testExceptionOnRankOver255() throws Exception {
		int[] shape = new int[500];
		for (int i = 0; i < shape.length; i++) {
			shape[i] = 1;
		}
		AbstractDataset ds = AbstractDataset.ones(shape, AbstractDataset.FLOAT64);
		NumPyTest.saveNumPyFile(ds, NumPyTest.getTempFile(), false);
	}

	@Test(expected = ScanFileHolderException.class)
	public void testExceptionOnEmptyFile() throws Exception {
		AbstractDataset ds = AbstractDataset.ones(new int[] { 2, 3 }, AbstractDataset.FLOAT64);
		final DataHolder dh = new DataHolder();
		dh.addDataset("", ds);
		new NumPyFileSaver("").saveFile(dh);
	}

	@Test(expected = ScanFileHolderException.class)
	public void testExceptionOnNullFile() throws Exception {
		AbstractDataset ds = AbstractDataset.ones(new int[] { 2, 3 }, AbstractDataset.FLOAT64);
		final DataHolder dh = new DataHolder();
		dh.addDataset("", ds);
		new NumPyFileSaver(null).saveFile(dh);
	}

	@Test
	public void testSaveMultipleFiles() throws Exception {
		AbstractDataset ds1 = AbstractDataset.zeros(new int[] { 20 }, AbstractDataset.FLOAT64);
		AbstractDataset ds2 = AbstractDataset.ones(new int[] { 20 }, AbstractDataset.FLOAT64);
		final DataHolder dh = new DataHolder();
		dh.addDataset("", ds1);
		dh.addDataset("", ds2);

		// Determine file names for each of the two data sets
		File fileName = NumPyTest.getTempFile();
		String fileString = fileName.toString();
		String baseName = fileString.substring(0, fileString.length() - ".npy".length());
		File file1 = new File(baseName + "00001.npy");
		File file2 = new File(baseName + "00002.npy");

		// Make sure that the files we should be creating don't already exist
		Assert.assertFalse(file1.exists());
		Assert.assertFalse(file2.exists());

		new NumPyFileSaver(fileString).saveFile(dh);

		// Make sure they do exist now and schedule them for deletion
		Assert.assertTrue(file1.exists() && file1.canRead());
		Assert.assertTrue(file2.exists() && file2.canRead());
		file1.deleteOnExit();
		file2.deleteOnExit();

		// Check the files load correctly in python
		verifySave(0, file1);
		verifySave(1, file2);
	}

	private void verifySave(int value, File file1) throws Exception {
		StringBuilder script1 = new StringBuilder();
		script1.append("import numpy; ");
		script1.append("exp=numpy.array([" + value + "]*20, dtype=numpy.float64); ");
		script1.append("act=numpy.load(r'" + file1.toString() + "');");
		script1.append(NumPyTest.PYTHON_NUMPY_PRINT_MATCHES);
		String pythonStdout1 = PythonHelper.runPythonScript(script1.toString(), false);
		Assert.assertTrue(Boolean.parseBoolean(pythonStdout1.trim()));
	}

}
