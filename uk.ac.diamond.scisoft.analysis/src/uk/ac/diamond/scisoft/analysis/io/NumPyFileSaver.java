/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import gda.analysis.io.IFileSaver;
import gda.analysis.io.ScanFileHolderException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.io.NumPyFile.DataTypeInfo;

/**
 * Writes files in npy format as defined here; http://svn.scipy.org/svn/numpy/trunk/doc/neps/npy-format.txt
 */
public class NumPyFileSaver implements IFileSaver {

	private String filename = "";

	/**
	 * Takes the dataset from a scan file holder and save it as our own Diamond Scisoft format
	 * 
	 * @param filename
	 */
	public NumPyFileSaver(String filename) {
		this.filename = filename;
	}

	@Override
	public void saveFile(DataHolder dh) throws ScanFileHolderException {
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

			AbstractDataset dataset = dh.getDataset(i);
			if (dataset == null) {
				throw new ScanFileHolderException("Dataset null at index " + i + " unsupported");
			}
			AbstractDataset sdata = DatasetUtils.convertToAbstractDataset(dataset);
			int dtype = sdata.getDtype();
			DataTypeInfo dataTypeInfo = NumPyFile.numPyTypeMap.get(dtype);
			if (dataTypeInfo == null) {
				throw new ScanFileHolderException("Unsupported data types for NumPy File Saver");
			}

			int is = sdata.getElementsPerItem();
			if (is > 255) {
				throw new ScanFileHolderException("Number of elements in each item exceeds allowed maximum of 255");
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
			shapeTuple.deleteCharAt(shapeTuple.length() - 1); // remove final space
			if (shape.length > 1) {
				shapeTuple.deleteCharAt(shapeTuple.length() - 1); // remove final comma
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
			int hdrSize = formatBuilder.length() + NumPyFile.magic.length + 2 /* header size */;
			hdrSize = ((hdrSize + 15) / 16) * 16; // round up header length to multiple of 16
			while (formatBuilder.length() + NumPyFile.magic.length + 2 < hdrSize) {
				formatBuilder.append(' ');
			}

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

				ByteBuffer dbBuffer = RawBinarySaver.saveRawDataset(sdata, dtype, isize);
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
