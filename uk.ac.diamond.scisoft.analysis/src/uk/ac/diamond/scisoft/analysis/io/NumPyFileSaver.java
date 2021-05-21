/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.io.IFileSaver;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.io.NumPyFile.DataTypeInfo;

/**
 * Writes files in npy format as defined here; http://svn.scipy.org/svn/numpy/trunk/doc/neps/npy-format.txt
 */
public class NumPyFileSaver implements IFileSaver {

	private String filename = "";
	protected boolean unsigned = false;

	/**
	 * Takes the dataset from a scan file holder and save it in npy format
	 * 
	 * @param filename
	 */
	public NumPyFileSaver(String filename) {
		this.filename = filename;
	}

	/**
	 * Takes the dataset from a scan file holder and save it in npy format
	 * 
	 * @param filename
	 * @param unsigned
	 */
	public NumPyFileSaver(String filename, boolean unsigned) {
		this.filename = filename;
		this.unsigned = unsigned;
	}

	@Override
	public void saveFile(IDataHolder dh) throws ScanFileHolderException {
		File f = null;
		final int imax = dh.size();
		for (int i = 0; i < imax; i++) {
			try {
				String name = null;
				String end = null;
				if (imax == 1) {
					name = filename;
				} else {
					try {
						name = filename.substring(0, (filename.lastIndexOf(".")));
						end = filename.substring(filename.lastIndexOf("."));
					} catch (Exception e) {
						name = filename;
					}

					NumberFormat format = new DecimalFormat("00000");
					name = name + format.format(i + 1) + end;
				}

				f = new File(name);
			} catch (Exception e) {
				throw new ScanFileHolderException("Error saving file '" + filename + "'", e);
			}

			IDataset dataset = dh.getDataset(i);
			if (dataset == null) {
				throw new ScanFileHolderException("Dataset null at index " + i + " unsupported");
			}
			Dataset sdata = DatasetUtils.convertToDataset(dataset);
			Class<? extends Dataset> clazz = sdata.getClass();
			DataTypeInfo dataTypeInfo;
			dataTypeInfo = unsigned ? NumPyFile.unsignedNumPyTypeMap.get(clazz) : NumPyFile.numPyTypeMap.get(clazz);
			if (dataTypeInfo == null) { // ignore unsigned flag if not found
				dataTypeInfo = NumPyFile.numPyTypeMap.get(clazz);
			}
			if (dataTypeInfo == null) {
				throw new ScanFileHolderException("Unsupported data types for NumPy File Saver");
			}

			int is = sdata.getElementsPerItem();
			if (is > 255) {
				throw new ScanFileHolderException("Number of elements in each item exceeds allowed maximum of 255");
			}
			if (unsigned) {
				clazz = dataTypeInfo.clazz;
				sdata = sdata.cast(clazz);
			}

			byte isize = (byte) is;

			int[] shape = sdata.getShape();
			if (shape.length > 255) {
				throw new ScanFileHolderException("Rank exceeds 255!");
			}
			StringBuilder shapeTuple = new StringBuilder();
			for (int j = 0; j < shape.length; j++) {
				shapeTuple.append(shape[j]);
				shapeTuple.append(", ");
			}
			if (shapeTuple.length() > 0) {
				shapeTuple.deleteCharAt(shapeTuple.length() - 1); // remove final space
				if (shape.length > 1) {
					shapeTuple.deleteCharAt(shapeTuple.length() - 1); // remove final comma
				}
			}

			// format looks like this, and always in this order: 
			// {'descr': '<i4', 'fortran_order': False, 'shape': (100,), }
			// or:
			// {'descr': '<i4', 'fortran_order': False, 'shape': (100, 100), }
			StringBuilder formatBuilder = new StringBuilder();
			formatBuilder.append("{'descr': '");
			formatBuilder.append(dataTypeInfo.numPyType); // e.g. <i4
			formatBuilder.append("', 'fortran_order': False, 'shape': (");
			formatBuilder.append(shapeTuple); // e.g. 100, or 100, 100
			formatBuilder.append("), }");
			/* header size */
			int hdrSize = formatBuilder.length() + NumPyFile.magic.length + 2 + 1; // +1 for the newline
			// header is padded with spaces and exactly one newline to reach multiple of 16 bytes
			int padding_required = 16 - (hdrSize % 16);
			hdrSize += padding_required;
			while (padding_required-- > 0) {
				formatBuilder.append(' ');
			}
			formatBuilder.append('\n');

			byte[] formatBytes;
			try {
				formatBytes = formatBuilder.toString().getBytes("US-ASCII");
			} catch (UnsupportedEncodingException e) {
				throw new ScanFileHolderException("Impossible error, US-ASCII is always available?", e);
			}

			try {
				FileOutputStream fout = new FileOutputStream(f);
				FileChannel fc = fout.getChannel();
				ByteBuffer hdrBuffer = ByteBuffer.allocateDirect(hdrSize);
				hdrBuffer.order(ByteOrder.LITTLE_ENDIAN);
				for (int j = 0; j < NumPyFile.magic.length; j++) {
					hdrBuffer.put(NumPyFile.magic[j]);
				}
				hdrBuffer.putShort((short) formatBytes.length);
				for (int j = 0; j < formatBytes.length; j++) {
					hdrBuffer.put(formatBytes[j]);
				}
				hdrBuffer.rewind();
				while (hdrBuffer.hasRemaining())
					fc.write(hdrBuffer);

				ByteBuffer dbBuffer = RawBinarySaver.saveRawDataset(isize, clazz, sdata);
				dbBuffer.rewind();
				while (dbBuffer.hasRemaining())
					fc.write(dbBuffer);
				fc.close();
				fout.close();
			} catch (Exception e) {
				throw new ScanFileHolderException("Error saving file '" + filename + "'", e);
			}
		}
	}

}
