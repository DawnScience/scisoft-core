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

package gda.analysis.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.io.DataHolder;

/**
 * Save datasets in a Diamond specific raw format
 * 
 * All this is done in little-endian:
 * 
 * File format tag: 0x0D1A05C1 (4bytes - stands for Diamond Scisoft)
 * Dataset type: -1 for old dataset
 * Item size: (1byte - number of elements per data item)
 * Rank: (1byte)
 * Shape: (rank*4 bytes)
 * Name: (length 2bytes, utf8 string)
 * Pad: to round up header to multiple of 4 bytes
 * Data: (little-endian raw dump)
 *
 */
public class RawBinarySaver implements IFileSaver {

	private String filename = "";
	private static int formatTag = 0xC1051A0D; // 0x0D1A05C1 in big endian

	/**
	 * @return format tag for identifying a Diamond specific raw format
	 */
	public static int getFormatTag() {
		return formatTag;
	}

	/**
	 * Takes the dataset from a scan file holder and save it as our own Diamond Scisoft format
	 * 
	 * @param filename
	 */
	public RawBinarySaver(String filename) {
		this.filename = filename;
	}

	@Override
	public void saveFile(DataHolder dh) throws ScanFileHolderException {
		File f = null;
		for (int i = 0, imax = dh.size(); i < imax; i++) {
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
			DoubleDataset data = (DoubleDataset) dh.getDataset(i).cast(AbstractDataset.FLOAT64);
			byte dtype = -1;
			byte isize = 1;
			String dataName = data.getName();
			byte[] name = null;
			try {
				name = dataName.getBytes("UTF8");
				int nlen = dataName.length() - 2;
				while (name.length > 65535) { // truncate if necessary
					name = dataName.substring(0, nlen).getBytes("UTF8");
					nlen--;
				}
			} catch (UnsupportedEncodingException e1) {
				throw new ScanFileHolderException("Problem dealing with dataset name", e1);
			}

			int[] shape = data.getShape();
			if (shape.length > 255) {
				throw new ScanFileHolderException("Rank exceeds 255!");
			}

			byte rank = (byte) shape.length;
			int hdrSize = 4 + 1 + 1 + 1 + rank*4 + 2 + name.length;
			hdrSize = ((hdrSize+3)/4) * 4; // round up to multiple of 4 bytes
			int dataSize = isize*data.getSize();

			try {
				FileOutputStream fout = new FileOutputStream(f);
				FileChannel fc = fout.getChannel();
				ByteBuffer hdrBuffer = ByteBuffer.allocateDirect(hdrSize);
				hdrBuffer.order(ByteOrder.LITTLE_ENDIAN);
				hdrBuffer.putInt(formatTag);
				hdrBuffer.put(dtype);
				hdrBuffer.put(isize);
				hdrBuffer.put(rank);
				for (int j = 0; j < rank; j++)
					hdrBuffer.putInt(shape[j]);
				hdrBuffer.putShort((short) name.length);
				if (name.length > 0)
					hdrBuffer.put(name);
				while (hdrBuffer.position() < hdrSize)
					hdrBuffer.put((byte) 0);
				hdrBuffer.rewind();
				while (hdrBuffer.hasRemaining())
					fc.write(hdrBuffer);

				ByteBuffer dbBuffer = ByteBuffer.allocateDirect(dataSize*8);
				dbBuffer.order(ByteOrder.LITTLE_ENDIAN);
				DoubleBuffer dataBuffer = dbBuffer.asDoubleBuffer();
				IndexIterator iter = data.getIterator();
				while (iter.hasNext()) {
					dataBuffer.put(data.getElementDoubleAbs(iter.index));
				}
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
