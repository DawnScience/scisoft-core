/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import static org.junit.Assert.assertEquals;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.asserts.TestUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Random;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.IMetadata;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.IOTestUtils;

public class ColumnTextLoaderSaverTest {
	private static String testScratchDirectoryName;

	@BeforeClass
	static public void setUpClass() throws Exception {
		Random.seed(1239127l);
		testScratchDirectoryName = IOTestUtils.setUpTestClass(ColumnTextLoaderSaverTest.class, true);
	}

	@Test
	public void test1D() throws ScanFileHolderException {
		DataHolder dh = new DataHolder();
		DoubleDataset data = Random.randn(256, 16);

		int[] shape = data.getShapeRef();
		for (int i = 0; i < shape[1]; i++) {
			String n = String.format("col-%d", i);
			dh.addDataset(n, data.getSlice((Slice) null, new Slice(i, i+1, 1)).squeezeEnds());
		}

		testWriteRead(dh, shape, "same", ".dat", '\t');
		testWriteRead(dh, shape, "same", ".csv", ',');
	}

	private void testWriteRead(DataHolder dh, int[] shape, String prefix, String ext, char delimiter) throws ScanFileHolderException {
		String fName = testScratchDirectoryName + prefix + ext;
		ColumnTextSaver saver = new ColumnTextSaver(fName);
		saver.setDelimiter(delimiter);
		saver.saveFile(dh);

		ColumnTextLoader loader = new ColumnTextLoader(fName);
		loader.setDelimiter(delimiter);
		DataHolder ldh = loader.loadFile();
		assertEquals(shape[1], ldh.size());

		for (int i = 0; i < shape[1]; i++) {
			Dataset e = dh.getDataset(i);
			Dataset a = ldh.getDataset(i);
			assertEquals(String.format("col-%d", i), a.getName());
			TestUtils.assertDatasetEquals(e, a, 1e-8, 1e-7);
		}

		// test with cell format at higher precision
		fName = testScratchDirectoryName + prefix + "-17" + ext;
		saver = new ColumnTextSaver(fName);
		saver.setDelimiter(delimiter);
		saver.setCellFormat("%.17g");
		saver.saveFile(dh);

		loader.setFile(fName);
		ldh = loader.loadFile();
		assertEquals(shape[1], ldh.size());

		for (int i = 0; i < shape[1]; i++) {
			Dataset e = dh.getDataset(i);
			Dataset a = DatasetUtils.convertToDataset(ldh.getDataset(i));
			assertEquals(String.format("col-%d", i), a.getName());
			TestUtils.assertDatasetEquals(e, a, 1e-17, 1e-16);
		}
	}

	@Test
	public void testRaggedColumns() throws ScanFileHolderException {
		DataHolder dh = new DataHolder();
		DoubleDataset data = Random.randn(256, 16);
		int[] shape = data.getShapeRef();

		// ragged columns
		for (int i = 0; i < shape[1]; i++) {
			String n = String.format("col-%d", i);
			dh.addDataset(n, data.getSlice(new Slice(-(i+1)), new Slice(i, i+1, 1)).squeezeEnds());
		}

		testWriteRead(dh, shape, "ragged_col", ".dat", '\t');
		testWriteRead(dh, shape, "ragged_col", ".csv", ',');
	}

	/**
	 * This method tests for correct number of datasets when there is a , in the dataset names
	 */
	@Test
	public void testSingleColumn() throws Exception {
		final String testfile1 = "testfiles/gda/analysis/io/DatLoaderTest/single.dat";
		DataHolder loader = new ColumnTextLoader(testfile1).loadFile();
		final IMetadata meta   = loader.getMetadata();
		assertEquals("Wrong number of columns!", 1, meta.getDataNames().size());
		Dataset dataset = loader.getDataset(0);
		assertEquals(dataset.getSize(), 11);
		assertEquals(dataset.getDouble(),-180.00, 0.000001);
	}

	@Test
	public void testCreateRow() {
		ColumnTextSaver saver = new ColumnTextSaver("blah");
		saver.setDelimiter(ColumnTextSaver.COMMA);

		assertEquals("", saver.createRow());
		assertEquals("hello,world", saver.createRow("hello", "world"));
		assertEquals("hello,world,\", again\"", saver.createRow("hello", "world", ", again"));
	}
}
