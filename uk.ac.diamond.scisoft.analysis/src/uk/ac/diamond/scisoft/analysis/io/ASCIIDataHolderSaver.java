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

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.DoubleBuffer;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Example class for dumping multidimensional data and meta data
 */
public class ASCIIDataHolderSaver implements IFileSaver {
	final String fileName;
	private Writer out = null;

	/**
	 * @param fileName
	 */
	public ASCIIDataHolderSaver(String fileName) {
		if (fileName == null || fileName.isEmpty())
			throw new IllegalArgumentException("Filename is null or empty");
		this.fileName = fileName;
	}

	protected void wl(String msg) throws IOException {
		w(msg + "\n");
	}

	private void w(String msg) throws IOException {
		out.write(msg);
	}

	private void writeData(int[] shape, DoubleBuffer db) throws IOException {
		if (shape.length == 0) {
			throw new IllegalArgumentException("shape.length == 0");
		}
		if (shape.length == 1) {
			// write out data and exit
			w(Double.toString(db.get()));
			for (int index = 1; index < shape[0]; index++) {
				w("," + Double.toString(db.get()));
			}
			wl("");
			return;
		}
		for (int index = 0; index < shape[shape.length - 1]; index++) {
			int[] inner_shape = new int[shape.length - 1];
			for (int i = 0; i < inner_shape.length; i++) {
				inner_shape[i] = shape[i];
			}
			writeData(inner_shape, db);
		}
	}

	private void writeDataset(String heading, IDataset ds) throws IOException {
		wl("Dataset," + heading + "," + ds.getName());
		int[] shape = ds.getShape();
		for (int d : shape) {
			wl(Integer.toString(d));
		}
		// create a reversed dimension array to successive gets from the db are in the correct order
		int[] inner_shape = new int[shape.length];
		for (int i = 0; i < inner_shape.length; i++) {
			inner_shape[i] = shape[(inner_shape.length - 1) - i];
		}
		double[] data = ((DoubleDataset) DatasetUtils.cast(ds, AbstractDataset.FLOAT64)).getData();
		DoubleBuffer db = DoubleBuffer.wrap(data, 0, data.length);
		writeData(inner_shape, db);
	}

	@SuppressWarnings("unused")
	protected void writeMetadata(IDataHolder dh) throws IOException {
		// do nothing but can be overridden
	}

	@Override
	public void saveFile(IDataHolder dh) throws ScanFileHolderException {
		try {
			out = new BufferedWriter(new FileWriter(fileName));

			writeMetadata(dh);
			String[] headings = dh.getNames();
			wl("NumberOfDatasets," + headings.length);
			for (String h : headings) {
				writeDataset(h, dh.getDataset(h));
			}
			out.close();

		} catch (Exception e) {
			throw new ScanFileHolderException("Error in AsciiScanFileHolderSaver.saveFile for " + fileName, e);
		}
	}
}

