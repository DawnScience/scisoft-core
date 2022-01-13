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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.ComplexDoubleDataset;
import org.eclipse.january.dataset.ComplexFloatDataset;
import org.eclipse.january.dataset.CompoundByteDataset;
import org.eclipse.january.dataset.CompoundDoubleDataset;
import org.eclipse.january.dataset.CompoundFloatDataset;
import org.eclipse.january.dataset.CompoundIntegerDataset;
import org.eclipse.january.dataset.CompoundLongDataset;
import org.eclipse.january.dataset.CompoundShortDataset;
import org.eclipse.january.dataset.DTypeUtils;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.InterfaceUtils;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.metadata.IMetadata;
import org.eclipse.january.metadata.Metadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.StatisticsMetadata;

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
	private String dName;
	private int[] shape;
	private int isize;
	
	public RawBinaryLoader() {
		
	}
	
	/**
	 * @param FileName
	 */
	public RawBinaryLoader(String FileName) {
		fileName = FileName;
	}

	@Override
	protected void clearMetadata() {
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

			int dtype = readHeader(fBuffer);

			Class<? extends Dataset> clazz = DTypeUtils.getInterface(dtype);
			if (isize != 1) {
				clazz = InterfaceUtils.getCompoundInterface(clazz);
			}

			while (fBuffer.position() % 4 != 0) { // move past any padding
				fBuffer.get();
			}

			ILazyDataset data;
			if (loadLazily) {
				data = createLazyDataset(new RawBinaryLoader(fileName), dName, clazz, shape);
			} else {
				int tSize = isize;
				for (int j = 0; j < shape.length; j++) {
					tSize *= shape[j];
				}

				data = loadRawDataset(fBuffer, isize, clazz, tSize, shape);
				data.setName(dName);
			}
			fc.close();
			output.addDataset(dName, data);
			if (loadMetadata) {
				metadata = createMetadata();
				output.setMetadata(metadata);
			}
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

	int readHeader(ByteBuffer fBuffer) throws ScanFileHolderException, UnsupportedEncodingException {
		if (RawBinarySaver.getFormatTag() != fBuffer.getInt()) {
			throw new ScanFileHolderException("File does not start with a Diamond format tag " + String.format("%x", fBuffer.getInt()));
		}
		byte dtype = fBuffer.get();
		isize = fBuffer.get();
		if (isize < 0) isize += 256;
		int rank = fBuffer.get();
		if (rank < 0) rank += 256;
		shape = new int[rank];
		for (int j = 0; j < rank; j++) {
			shape[j] = fBuffer.getInt();
		}
		int nlen = fBuffer.getShort();
		if (nlen < 0)
			nlen += 65536;
		byte[] name = new byte[nlen];
		if (nlen > 0)
			fBuffer.get(name);

		if (nlen > 0) {
			dName = new String(name, "UTF-8");
		} else {
			dName = "RAW file";
		}
		return dtype;
	}

	@Override
	public void loadMetadata(IMonitor mon) throws IOException {
		File f = null;
		FileInputStream fi = null;
		try {

			f = new File(fileName);
			fi = new FileInputStream(f);
			FileChannel fc = fi.getChannel();

			MappedByteBuffer fBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size());
			fBuffer.order(ByteOrder.LITTLE_ENDIAN);

			readHeader(fBuffer);
			metadata = createMetadata();
		} catch (Exception ex) {
			if (ex instanceof IOException)
				throw (IOException) ex;
			throw new IOException("There was a problem reading the Raw file", ex);
		} finally {
			if (fi != null)
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private IMetadata createMetadata() {
		IMetadata md = new Metadata();
		md.setFilePath(fileName);
		md.addDataInfo(dName, shape);
		return md;
	}

	/**
	 * Load a binary data set with the following parameters.
	 * 
	 * @param fBuffer
	 *            buffer to load data set from, must by in LITTLE_ENDIAN and current position be the first byte of the
	 *            raw data with the remaining number of bytes being the total size in bytes.
	 * @param isize
	 *            Data item size
	 * @param clazz
	 *            Data interface type of the data to load, see sub-interfaces of {@link Dataset} (e.g. @link
	 *            {@link DoubleDataset})
	 * @param tSize
	 *            Data total size in element size
	 * @param shape
	 *            Shape of the data
	 * @return newly created data set loaded from byte buffer
	 * @throws ScanFileHolderException
	 *             if a detected error is encountered, or wraps an underlying exception
	 */
	public static Dataset loadRawDataset(ByteBuffer fBuffer, int isize, Class<? extends Dataset> clazz, int tSize, int[] shape)
			throws ScanFileHolderException {
		Dataset data = null;
		@SuppressWarnings("rawtypes")
		StatisticsMetadata stats = null;
		int hash = 0;
		double dhash = 0;
		if (BooleanDataset.class.isAssignableFrom(clazz)) {
			BooleanDataset b = DatasetFactory.zeros(BooleanDataset.class, shape);
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
			stats = storeStats(data, maxA ? 1 : 0, minA ? 1 : 0);
		} else if (ByteDataset.class.isAssignableFrom(clazz)) {
			ByteDataset i8 = DatasetFactory.zeros(ByteDataset.class, shape);
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
			stats = storeStats(data, maxB, minB);
		} else if (ShortDataset.class.isAssignableFrom(clazz)) {
			ShortDataset i16 = DatasetFactory.zeros(ShortDataset.class, shape);
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
			stats = storeStats(data, maxS, minS);
		} else if (IntegerDataset.class.isAssignableFrom(clazz)) {
			IntegerDataset i32 = DatasetFactory.zeros(IntegerDataset.class, shape);
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
			stats = storeStats(data, maxI, minI);
		} else if (LongDataset.class.isAssignableFrom(clazz)) {
			LongDataset i64 = DatasetFactory.zeros(LongDataset.class, shape);
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
			stats = storeStats(data, maxL, minL);
		} else if (CompoundByteDataset.class.isAssignableFrom(clazz)) {
			CompoundByteDataset ci8 = DatasetFactory.zeros(isize, CompoundByteDataset.class, shape);
			if (fBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			byte[] dataB = ci8.getData();
			for (int j = 0; j < tSize; j++) {
				byte v = fBuffer.get();
				hash = hash * 19 + v;
				dataB[j] = v;
			}
			data = ci8;
		} else if (CompoundShortDataset.class.isAssignableFrom(clazz)) {
			CompoundShortDataset ci16 = DatasetFactory.zeros(isize, CompoundShortDataset.class, shape);
			ShortBuffer sDataBuffer = fBuffer.asShortBuffer();
			if (sDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + sDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			short[] dataS = ci16.getData();
			for (int j = 0; j < tSize; j++) {
				short v = sDataBuffer.get();
				hash = hash * 19 + v;
				dataS[j] = v;
			}
			data = ci16;
		} else if (CompoundIntegerDataset.class.isAssignableFrom(clazz)) {
			CompoundIntegerDataset ci32 = DatasetFactory.zeros(isize, CompoundIntegerDataset.class, shape);
			IntBuffer iDataBuffer = fBuffer.asIntBuffer();
			if (iDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + iDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			int[] dataI = ci32.getData();
			for (int j = 0; j < tSize; j++) {
				int v = iDataBuffer.get();
				hash = hash * 19 + v;
				dataI[j] = v;
			}
			data = ci32;
		} else if (CompoundLongDataset.class.isAssignableFrom(clazz)) {
			CompoundLongDataset ci64 = DatasetFactory.zeros(isize, CompoundLongDataset.class, shape);
			LongBuffer lDataBuffer = fBuffer.asLongBuffer();
			if (lDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + lDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			long[] dataL = ci64.getData();
			for (int j = 0; j < tSize; j++) {
				long v = lDataBuffer.get();
				hash = hash * 19 + (int) v;
				dataL[j] = v;
			}
			data = ci64;
		} else if (FloatDataset.class.isAssignableFrom(clazz)) {
			FloatBuffer fltDataBuffer = fBuffer.asFloatBuffer();
			FloatDataset f32 = DatasetFactory.zeros(FloatDataset.class, shape);
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
			stats = storeStats(data, maxF, minF);
			hash = (int) dhash;
		} else if (ComplexFloatDataset.class.isAssignableFrom(clazz)) {
			ComplexFloatDataset c64 = DatasetFactory.zeros(ComplexFloatDataset.class, shape);
			FloatBuffer fltDataBuffer = fBuffer.asFloatBuffer();
			if (fltDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fltDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			float[] dataFlt = c64.getData();
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
		} else if (CompoundFloatDataset.class.isAssignableFrom(clazz)) {
			CompoundFloatDataset cf32 = DatasetFactory.zeros(isize, CompoundFloatDataset.class, shape);
			FloatBuffer fltDataBuffer = fBuffer.asFloatBuffer();
			if (fltDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + fltDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			float[] dataFlt = cf32.getData();
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
		} else if (clazz == null || DoubleDataset.class.isAssignableFrom(clazz)) {
			DoubleDataset f64 = DatasetFactory.zeros(DoubleDataset.class, shape);
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
			stats = storeStats(data, maxD, minD);
			hash = (int) dhash;
		} else if (ComplexDoubleDataset.class.isAssignableFrom(clazz)) {
			ComplexDoubleDataset c128 = DatasetFactory.zeros(ComplexDoubleDataset.class, shape);
			DoubleBuffer dblDataBuffer = fBuffer.asDoubleBuffer();
			if (dblDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + dblDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			double[] dataDbl = c128.getData();
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
		} else if (CompoundDoubleDataset.class.isAssignableFrom(clazz)) {
			CompoundDoubleDataset cf64 = DatasetFactory.zeros(isize, CompoundDoubleDataset.class, shape);
			DoubleBuffer dblDataBuffer = fBuffer.asDoubleBuffer();
			if (dblDataBuffer.remaining() != tSize) {
				throw new ScanFileHolderException("Data size, " + dblDataBuffer.remaining()
						+ ", does not match expected, " + tSize);
			}
			double[] dataDbl = cf64.getData();
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
		} else {
			throw new ScanFileHolderException("Dataset type not supported");
		}

		hash = hash*19 + data.getClass().hashCode()*17 + data.getElementsPerItem();
		if (stats == null) {
			try {
				stats = MetadataFactory.createMetadata(StatisticsMetadata.class, data);
			} catch (MetadataException e) {
				throw new ScanFileHolderException("Could not create hash metadata", e);
			}
			data.addMetadata(stats);
		}
		stats.setHash(hash);

		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static StatisticsMetadata storeStats(Dataset data, Number max, Number min) throws ScanFileHolderException {
		StatisticsMetadata<Number> stats = data.getFirstMetadata(StatisticsMetadata.class);
		if (stats == null) {
			try {
				stats = MetadataFactory.createMetadata(StatisticsMetadata.class, data);
			} catch (MetadataException e) {
				throw new ScanFileHolderException("Could not create max/min metadata", e);
			}
		}

		stats.setMaximumMinimumSum(max, min, null);
		data.addMetadata(stats);
		return stats;
	}
}
