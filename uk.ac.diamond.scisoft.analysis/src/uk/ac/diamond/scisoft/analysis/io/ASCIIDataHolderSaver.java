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

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.IFileSaver;
import gda.analysis.io.ScanFileHolderException;
import gda.data.nexus.tree.NexusTreeProvider;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.DoubleBuffer;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

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

	private void wl(String msg) throws IOException {
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

	private void writeDataset(String heading, AbstractDataset ds) throws IOException {
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

	@Override
	public void saveFile(DataHolder dh) throws ScanFileHolderException {
		try {
			out = new BufferedWriter(new FileWriter(fileName));
			
			if (dh instanceof NexusTreeProvider) {
				NexusTreeProvider ndh = (NexusTreeProvider) dh;
				if(ndh.getNexusTree() != null) {
					wl(ndh.getNexusTree().toXML(true, false));
					wl(ndh.getNexusTree().toXML(true, true));
					wl(ndh.getNexusTree().toXML(false, false));
					wl(ndh.getNexusTree().toXML(false, true));
				}
			}

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

