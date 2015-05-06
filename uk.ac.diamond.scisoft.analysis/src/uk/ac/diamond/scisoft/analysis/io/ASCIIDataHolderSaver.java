/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.DoubleBuffer;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

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
		out.flush();
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
		double[] data = ((DoubleDataset) DatasetUtils.cast(ds, Dataset.FLOAT64)).getData();
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

