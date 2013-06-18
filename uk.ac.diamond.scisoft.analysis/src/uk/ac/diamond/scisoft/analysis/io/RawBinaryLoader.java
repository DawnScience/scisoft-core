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

import gda.analysis.io.ScanFileHolderException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ByteDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ComplexDoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ComplexFloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundByteDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundDoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundFloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundIntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundLongDataset;
import uk.ac.diamond.scisoft.analysis.dataset.CompoundShortDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.LongDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;

/**
 * Load datasets in a Diamond specific raw format
 * 
 * All this is done in little-endian:
 * 
 * File format tag: 0x0D1A05C1 (4bytes - stands for Diamond Scisoft)
 * Dataset type: (1byte) (0 - bool, 1 - int8, 2 - int16, 3 - int32, 4 - int64, 5 - float32,
 *                        6 - float64, 7 - complex64, 8 - complex128)
 *               -1 - old dataset, array datasets supported but with single element dataset types
 * Item size: (1 unsigned byte - number of elements per data item)
 * Rank: (1 unsigned byte)
 * Shape: (rank*4-byte unsigned word)
 * Name: (length in bytes as 2-byte unsigned word, utf8 string)
 * Data: (little-endian raw dump, row major order)
 *
 */
public class RawBinaryLoader extends AbstractFileLoader {
	private String fileName;
	
	public RawBinaryLoader() {
		
	}
	
	/**
	 * @param FileName
	 */
	public RawBinaryLoader(String FileName) {
		fileName = FileName;
	}
	
	public void setFile(final String fileName) {
		this.fileName = fileName;
	}

	@Override
	public DataHolder loadFile() throws ScanFileHolderException {

		DataHolder output = new DataHolder();
		File f = null;
		FileInputStream fi = null;
		try {

			f = new File(fileName);
			fi = new FileInputStream(f);
			FileChannel fc = fi.getChannel();

			MappedByteBuffer fBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size());
			fBuffer.order(ByteOrder.LITTLE_ENDIAN);

			if (RawBinarySaver.getFormatTag() != fBuffer.getInt()) {
				throw new ScanFileHolderException("File does not start with a Diamond format tag " + String.format("%x", fBuffer.getInt()));
			}
			int dtype = fBuffer.get();
			int isize = fBuffer.get();
			if (isize < 0) isize += 256;
			int rank = fBuffer.get();
			if (rank < 0) rank += 256;
			int tSize = isize;
			int[] shape = new int[rank];
			for (int j = 0; j < rank; j++) {
				shape[j] = fBuffer.getInt();
				tSize *= shape[j];
			}
			int nlen = fBuffer.getShort();
			if (nlen < 0)
				nlen += 65536;
			byte[] name = new byte[nlen];
			if (nlen > 0)
				fBuffer.get(name);
			while (fBuffer.position() % 4 != 0) // move past any padding
				fBuffer.get();

			if (isize != 1) {
				switch (dtype) {
				case AbstractDataset.INT8:
					dtype = AbstractDataset.ARRAYINT8;
					break;
				case AbstractDataset.INT16:
					dtype = AbstractDataset.ARRAYINT16;
					break;
				case AbstractDataset.INT32:
					dtype = AbstractDataset.ARRAYINT32;
					break;
				case AbstractDataset.INT64:
					dtype = AbstractDataset.ARRAYINT64;
					break;
				case AbstractDataset.FLOAT32:
					dtype = AbstractDataset.ARRAYFLOAT32;
					break;
				case AbstractDataset.FLOAT64:
					dtype = AbstractDataset.ARRAYFLOAT64;
					break;
				}
			}

			AbstractDataset data = loadRawDataset(fBuffer, dtype, isize, tSize, shape);
			fc.close();

			if (nlen > 0) {
				String dName = new String(name, "UTF-8");
				data.setName(dName);
			    output.addDataset(dName, data);
			} else {
			    output.addDataset("RAW file", data);				
			}
		    data.setDirty();
		} catch (Exception ex) {
			if (ex instanceof ScanFileHolderException)
				throw (ScanFileHolderException) ex;
			throw new ScanFileHolderException("There was a problem reading the Raw file", ex);			
		} finally {
			if (fi != null)
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return output;
	}

	/**
	 * Load a binary data set with the following parameters.
	 * 
	 * @param fBuffer
	 *            buffer to load data set from, must by in LITTLE_ENDIAN and current position be the first byte of the
	 *            raw data with the remaining number of bytes being the total size in bytes.
	 * @param dtype
	 *            Data type of the data to load, see {@link AbstractDataset} for list of types (e.g. @link
	 *            {@link AbstractDataset#FLOAT64}
	 * @param isize
	 *            Data item size
	 * @param tSize
	 *            Data total size in element size
	 * @param shape
	 *            Shape of the data
	 * @return newly created data set loaded from byte buffer
	 * @throws ScanFileHolderException
	 *             if a detected error is encountered, or wraps an underlying exception
	 */
	public static AbstractDataset loadRawDataset(ByteBuffer fBuffer, int dtype, int isize, int tSize, int[] shape)
			throws ScanFileHolderException {
		AbstractDataset data = null;
		int hash = 0;
		double dhash = 0;
		switch (dtype) {
		case AbstractDataset.BOOL:
			BooleanDataset b = new BooleanDataset(shape);
			if (fBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			boolean[] dataBln = b.getData();
			boolean maxA = false;
			boolean minA = true;
			for (int j = 0; j < tSize; j++) {
				boolean v = fBuffer.get() != 0;
				hash = hash * 19 + (v ? 0 : 1);
				dataBln[j] =  v;
				if (!maxA && v)
					maxA = true;
				if (minA && !v)
					minA = false;
			}
			data = b;
			data.setStoredValue(AbstractDataset.STORE_MAX, maxA);
			data.setStoredValue(AbstractDataset.STORE_MIN, minA);
			break;
		case AbstractDataset.INT8:
			ByteDataset i8 = new ByteDataset(shape);
			if (fBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			byte[] dataB = i8.getData();
			byte maxB = Byte.MIN_VALUE;
			byte minB = Byte.MAX_VALUE;
			for (int j = 0; j < tSize; j++) {
				byte v = fBuffer.get();
				hash = hash * 19 + v;
				dataB[j] = v;
				if (v > maxB)
					maxB = v;
				if (v < minB)
					minB = v;
			}
			data = i8;
			data.setStoredValue(AbstractDataset.STORE_MAX, maxB);
			data.setStoredValue(AbstractDataset.STORE_MIN, minB);
			break;
		case AbstractDataset.INT16:
			ShortDataset i16 = new ShortDataset(shape);
			ShortBuffer sDataBuffer = fBuffer.asShortBuffer();
			if (sDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + sDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			short[] dataS = i16.getData();
			short maxS = Short.MIN_VALUE;
			short minS = Short.MAX_VALUE;
			for (int j = 0; j < tSize; j++) {
				short v = sDataBuffer.get();
				hash = hash * 19 + v;
				dataS[j] = v;
				if (v > maxS)
					maxS = v;
				if (v < minS)
					minS = v;
			}
			data = i16;
			data.setStoredValue(AbstractDataset.STORE_MAX, maxS);
			data.setStoredValue(AbstractDataset.STORE_MIN, minS);
			break;
		case AbstractDataset.INT32:
			IntegerDataset i32 = new IntegerDataset(shape);
			IntBuffer iDataBuffer = fBuffer.asIntBuffer();
			if (iDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + iDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			int[] dataI = i32.getData();
			int maxI = Integer.MIN_VALUE;
			int minI = Integer.MAX_VALUE;
			for (int j = 0; j < tSize; j++) {
				int v = iDataBuffer.get();
				hash = hash * 19 + v;
				dataI[j] = v;
				if (v > maxI)
					maxI = v;
				if (v < minI)
					minI = v;
			}
			data = i32;
			data.setStoredValue(AbstractDataset.STORE_MAX, maxI);
			data.setStoredValue(AbstractDataset.STORE_MIN, minI);
			break;
		case AbstractDataset.INT64:
			LongDataset i64 = new LongDataset(shape);
			LongBuffer lDataBuffer = fBuffer.asLongBuffer();
			if (lDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + lDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			long[] dataL = i64.getData();
			long maxL = Long.MIN_VALUE;
			long minL = Long.MAX_VALUE;
			for (int j = 0; j < tSize; j++) {
				long v = lDataBuffer.get();
				hash = hash * 19 + (int) v;
				dataL[j] = v;
				if (v > maxL)
					maxL = v;
				if (v < minL)
					minL = v;
			}
			data = i64;
			data.setStoredValue(AbstractDataset.STORE_MAX, maxL);
			data.setStoredValue(AbstractDataset.STORE_MIN, minL);
			break;
		case AbstractDataset.ARRAYINT8:
			CompoundByteDataset ci8 = new CompoundByteDataset(isize, shape);
			if (fBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataB = ci8.getData();
			for (int j = 0; j < tSize; j++) {
				byte v = fBuffer.get();
				hash = hash * 19 + v;
				dataB[j] = v;
			}
			data = ci8;
			break;
		case AbstractDataset.ARRAYINT16:
			CompoundShortDataset ci16 = new CompoundShortDataset(isize, shape);
			sDataBuffer = fBuffer.asShortBuffer();
			if (sDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + sDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataS = ci16.getData();
			for (int j = 0; j < tSize; j++) {
				short v = sDataBuffer.get();
				hash = hash * 19 + v;
				dataS[j] = v;
			}
			data = ci16;
			break;
		case AbstractDataset.ARRAYINT32:
			CompoundIntegerDataset ci32 = new CompoundIntegerDataset(isize, shape);
			iDataBuffer = fBuffer.asIntBuffer();
			if (iDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + iDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataI = ci32.getData();
			for (int j = 0; j < tSize; j++) {
				int v = iDataBuffer.get();
				hash = hash * 19 + v;
				dataI[j] = v;
			}
			data = ci32;
			break;
		case AbstractDataset.ARRAYINT64:
			CompoundLongDataset ci64 = new CompoundLongDataset(isize, shape);
			lDataBuffer = fBuffer.asLongBuffer();
			if (lDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + lDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataL = ci64.getData();
			for (int j = 0; j < tSize; j++) {
				long v = lDataBuffer.get();
				hash = hash * 19 + (int) v;
				dataL[j] = v;
			}
			data = ci64;
			break;
		case AbstractDataset.FLOAT32:
			FloatBuffer fltDataBuffer = fBuffer.asFloatBuffer();
			FloatDataset f32 = new FloatDataset(shape);
			if (fltDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fltDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			float[] dataFlt = f32.getData();
			float maxF = -Float.MAX_VALUE;
			float minF = Float.MAX_VALUE;
			for (int j = 0; j < tSize; j++) {
				float v = fltDataBuffer.get();
				if (Float.isInfinite(v) || Float.isNaN(v))
					dhash = (dhash * 19) % Integer.MAX_VALUE;
				else
					dhash = (dhash * 19 + v) % Integer.MAX_VALUE;

				dataFlt[j] = v;
				if (Float.isNaN(maxF)) {
					maxF = v;
				}
				if (Float.isNaN(minF)) {
					minF = v;
				}
				if (v > maxF)
					maxF = v;
				if (v < minF)
					minF = v;
			}
			data = f32;
			data.setStoredValue(AbstractDataset.STORE_MAX, maxF);
			data.setStoredValue(AbstractDataset.STORE_MIN, minF);
			hash = (int) dhash;
			break;
		case AbstractDataset.ARRAYFLOAT32:
			CompoundFloatDataset cf32 = new CompoundFloatDataset(isize, shape);
			fltDataBuffer = fBuffer.asFloatBuffer();
			if (fltDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fltDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataFlt = cf32.getData();
			for (int j = 0; j < tSize; j++) {
				float v = fltDataBuffer.get();
				if (Float.isInfinite(v) || Float.isNaN(v))
					dhash = (dhash * 19) % Integer.MAX_VALUE;
				else
					dhash = (dhash * 19 + v) % Integer.MAX_VALUE;

				dataFlt[j] = v;
			}
			data = cf32;
			hash = (int) dhash;
			break;
		case AbstractDataset.COMPLEX64:
			ComplexFloatDataset c64 = new ComplexFloatDataset(shape);
			fltDataBuffer = fBuffer.asFloatBuffer();
			if (fltDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fltDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataFlt = c64.getData();
			dhash = 0;
			for (int j = 0; j < tSize; j++) {
				float v = fltDataBuffer.get();
				if (Float.isInfinite(v) || Float.isNaN(v))
					dhash = (dhash * 19) % Integer.MAX_VALUE;
				else
					dhash = (dhash * 19 + v) % Integer.MAX_VALUE;

				dataFlt[j] = v;
			}
			data = c64;
			hash = (int) dhash;
			break;
		case -1: // old dataset
		case AbstractDataset.FLOAT64:
			DoubleDataset f64 = new DoubleDataset(shape);
			DoubleBuffer dblDataBuffer = fBuffer.asDoubleBuffer();
			if (dblDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + dblDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			double[] dataDbl = f64.getData();
			double maxD = -Double.MAX_VALUE;
			double minD = Double.MAX_VALUE;

			for (int j = 0; j < tSize; j++) {
				double v = dblDataBuffer.get();
				if (Double.isInfinite(v) || Double.isNaN(v))
					dhash = (dhash * 19) % Integer.MAX_VALUE;
				else
					dhash = (dhash * 19 + v) % Integer.MAX_VALUE;

				dataDbl[j] = v;
				if (Double.isNaN(maxD)) {
					maxD = v;
				}
				if (Double.isNaN(minD)) {
					minD = v;
				}
				if (v > maxD)
					maxD = v;
				if (v < minD)
					minD = v;
			}
			data = f64;
			data.setStoredValue(AbstractDataset.STORE_MAX, maxD);
			data.setStoredValue(AbstractDataset.STORE_MIN, minD);
			hash = (int) dhash;
			break;
		case AbstractDataset.ARRAYFLOAT64:
			CompoundDoubleDataset cf64 = new CompoundDoubleDataset(isize, shape);
			dblDataBuffer = fBuffer.asDoubleBuffer();
			if (dblDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + dblDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataDbl = cf64.getData();
			for (int j = 0; j < tSize; j++) {
				double v = dblDataBuffer.get();
				if (Double.isInfinite(v) || Double.isNaN(v))
					dhash = (dhash * 19) % Integer.MAX_VALUE;
				else
					dhash = (dhash * 19 + v) % Integer.MAX_VALUE;

				dataDbl[j] = v;
			}
			data = cf64;
			hash = (int) dhash;
			break;
		case AbstractDataset.COMPLEX128:
			ComplexDoubleDataset c128 = new ComplexDoubleDataset(shape);
			dblDataBuffer = fBuffer.asDoubleBuffer();
			if (dblDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + dblDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			dataDbl = c128.getData();
			for (int j = 0; j < tSize; j++) {
				double v = dblDataBuffer.get();
				if (Double.isInfinite(v) || Double.isNaN(v))
					dhash = (dhash * 19) % Integer.MAX_VALUE;
				else
					dhash = (dhash * 19 + v) % Integer.MAX_VALUE;

				dataDbl[j] = v;
			}
			data = c128;
			hash = (int) dhash;
			break;
		default:
			throw new ScanFileHolderException("Dataset type not supported");				
		}

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_HASH, hash);
		return data;
	}
}
