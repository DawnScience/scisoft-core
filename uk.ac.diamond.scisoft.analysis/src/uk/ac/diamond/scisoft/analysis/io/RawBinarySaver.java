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

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ByteDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ComplexDoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ComplexFloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LongDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;

/**
 * Save datasets in a Diamond specific raw format
 * 
 * All this is done in little-endian:
 * 
 * File format tag: 0x0D1A05C1 when read as a 32-bit word in big-endian (4bytes - stands for Diamond Scisoft)
 * Dataset type: (1byte) (0 - bool, 1 - int8, 2 - int16, 3 - int32, 4 - int64, 5 - float32,
 *                        6 - float64, 7 - complex64, 8 - complex128)
 *               array datasets supported but with single element dataset types
 * Item size: (1 unsigned byte - number of elements per data item)
 * Rank: (1 unsigned byte)
 * Shape: (rank*4-byte unsigned word)
 * Name: (length in bytes as 2-byte unsigned word, utf8 string)
 * Pad: to round up header to multiple of 4 bytes
 * Data: (little-endian raw dump, row major order)
 *
 */
public class RawBinarySaver implements IFileSaver {

	private String filename = "";
	private static int formatTag = 0xC1051A0D; // this is 0x0D1A05C1 in big endian

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

			Dataset sdata = DatasetUtils.convertToDataset(dh.getDataset(i));
			int dtype = sdata.getDtype();
			switch (dtype) {
			case Dataset.ARRAYINT8:
				dtype = Dataset.INT8;
				break;
			case Dataset.ARRAYINT16:
				dtype = Dataset.INT16;
				break;
			case Dataset.ARRAYINT32:
				dtype = Dataset.INT32;
				break;
			case Dataset.ARRAYINT64:
				dtype = Dataset.INT64;
				break;
			case Dataset.ARRAYFLOAT32:
				dtype = Dataset.FLOAT32;
				break;
			case Dataset.ARRAYFLOAT64:
				dtype = Dataset.FLOAT64;
				break;
			}

			int is = sdata.getElementsPerItem();
			if (is > 255) {
				throw new ScanFileHolderException("Number of elements in each item exceeds allowed maximum of 255");
			}
			byte isize = (byte) is;
			String dataName = sdata.getName();
			byte[] name = null;
			try {
				name = dataName.getBytes("UTF-8");
				int nlen = dataName.length() - 2;
				while (name.length > 65535) { // truncate if necessary
					name = dataName.substring(0, nlen).getBytes("UTF8");
					nlen--;
				}
			} catch (UnsupportedEncodingException e1) {
				throw new ScanFileHolderException("Problem dealing with dataset name", e1);
			}

			int[] shape = sdata.getShape();
			if (shape.length > 255) {
				throw new ScanFileHolderException("Rank exceeds 255!");
			}

			byte rank = (byte) shape.length;
			int hdrSize = 4 + 1 + 1 + 1 + rank*4 + 2 + name.length;
			hdrSize = ((hdrSize+3)/4) * 4; // round up to multiple of 4 bytes

			try {
				FileOutputStream fout = new FileOutputStream(f);
				FileChannel fc = fout.getChannel();
				ByteBuffer hdrBuffer = ByteBuffer.allocateDirect(hdrSize);
				hdrBuffer.order(ByteOrder.LITTLE_ENDIAN);
				hdrBuffer.putInt(formatTag);
				hdrBuffer.put((byte) dtype);
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

				ByteBuffer dbBuffer = saveRawDataset(sdata, dtype, isize);
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

	/**
	 * Saves the dataset to the ByteBuffer provided in a raw format, ie no headers
	 * @param sdata Dataset to save
	 * @param dtype Dataset type for save purpose
	 * @param isize Each entry item size
	 * @return the allocated ByteBuffer where the data is saved
	 */
	public static ByteBuffer saveRawDataset(Dataset sdata, int dtype, byte isize) {
		ByteBuffer dbBuffer = ByteBuffer.allocateDirect(sdata.getNbytes());
		dbBuffer.order(ByteOrder.LITTLE_ENDIAN);

		switch (dtype) {
		case Dataset.BOOL:
			BooleanDataset b = (BooleanDataset) sdata;
			boolean[] dataBln = b.getData();
			IndexIterator iter = b.getIterator();
			while (iter.hasNext()) {
				dbBuffer.put((byte) (dataBln[iter.index] ? 1 : 0));
			}
			break;
		case Dataset.INT8:
			ByteDataset i8 = (ByteDataset) sdata;
			byte[] dataB = i8.getData();
			iter = i8.getIterator();
			if (isize == 1) {
				while (iter.hasNext()) {
					dbBuffer.put(dataB[iter.index]);
				}
			} else {
				while (iter.hasNext()) {
					for (int j = 0; j < isize; j++)
						dbBuffer.put(dataB[iter.index+j]);
				}
			}
			break;
		case Dataset.INT16:
			ShortDataset i16 = (ShortDataset) sdata;
			ShortBuffer sDataBuffer = dbBuffer.asShortBuffer();
			short[] dataS = i16.getData();
			iter = i16.getIterator();
			if (isize == 1) {
				while (iter.hasNext()) {
					sDataBuffer.put(dataS[iter.index]);
				}
			} else {
				while (iter.hasNext()) {
					for (int j = 0; j < isize; j++)
						sDataBuffer.put(dataS[iter.index+j]);
				}
			}
			break;
		case Dataset.INT32:
			IntegerDataset i32 = (IntegerDataset) sdata;
			IntBuffer iDataBuffer = dbBuffer.asIntBuffer();
			int[] dataI = i32.getData();
			iter = i32.getIterator();
			if (isize == 1) {
				while (iter.hasNext()) {
					iDataBuffer.put(dataI[iter.index]);
				}
			} else {
				while (iter.hasNext()) {
					for (int j = 0; j < isize; j++)
						iDataBuffer.put(dataI[iter.index+j]);
				}
			}
			break;
		case Dataset.INT64:
			LongDataset i64 = (LongDataset) sdata;
			LongBuffer lDataBuffer = dbBuffer.asLongBuffer();
			long[] dataL = i64.getData();
			iter = i64.getIterator();
			if (isize == 1) {
				while (iter.hasNext()) {
					lDataBuffer.put(dataL[iter.index]);
				}
			} else {
				while (iter.hasNext()) {
					for (int j = 0; j < isize; j++)
						lDataBuffer.put(dataL[iter.index+j]);
				}
			}
			break;
		case Dataset.FLOAT32:
			FloatDataset f32 = (FloatDataset) sdata;
			FloatBuffer fDataBuffer = dbBuffer.asFloatBuffer();
			float[] dataFlt = f32.getData();
			iter = f32.getIterator();
			if (isize == 1) {
				while (iter.hasNext()) {
					fDataBuffer.put(dataFlt[iter.index]);
				}
			} else {
				while (iter.hasNext()) {
					for (int j = 0; j < isize; j++)
						fDataBuffer.put(dataFlt[iter.index+j]);
				}
			}
			break;
		case Dataset.FLOAT64:
			DoubleDataset f64 = (DoubleDataset) sdata;
			DoubleBuffer dataBuffer = dbBuffer.asDoubleBuffer();
			double[] dataDbl = f64.getData();
			iter = f64.getIterator();
			if (isize == 1) {
				while (iter.hasNext()) {
					dataBuffer.put(dataDbl[iter.index]);
				}
			} else {
				while (iter.hasNext()) {
					for (int j = 0; j < isize; j++)
						dataBuffer.put(dataDbl[iter.index+j]);
				}
			}
			break;
		case Dataset.COMPLEX64:
			ComplexFloatDataset c64 = (ComplexFloatDataset) sdata;
			fDataBuffer = dbBuffer.asFloatBuffer();
			dataFlt = c64.getData();
			iter = c64.getIterator();
			while (iter.hasNext()) {
				fDataBuffer.put(dataFlt[iter.index]);
				fDataBuffer.put(dataFlt[iter.index+1]);
			}
			break;
		case Dataset.COMPLEX128:
			ComplexDoubleDataset c128 = (ComplexDoubleDataset) sdata;
			dataBuffer = dbBuffer.asDoubleBuffer();
			dataDbl = c128.getData();
			iter = c128.getIterator();
			while (iter.hasNext()) {
				dataBuffer.put(dataDbl[iter.index]);
				dataBuffer.put(dataDbl[iter.index+1]);
			}
			break;
		}
		return dbBuffer;
	}
}
