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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.dataset.impl.AbstractDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.FloatDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.ShortDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class
 */
public class Utils {
	protected static final Logger logger = LoggerFactory.getLogger(Utils.class);

	/**
	 * @param b
	 * @return an integer from bytes specified in little endian order
	 */
	public static int leInt(int... b) {
		int a = 0;
		for (int i = b.length - 1; i >= 0; i--) {
			a <<= 8;
			a |= (b[i] & 0xff);
		}
		return a;
	}

	/**
	 * @param b1
	 * @param b2
	 * @return an integer from bytes specified in little endian order
	 */
	public static int leInt(int b1, int b2) {
		return ((b2 & 0xff) << 8) | (b1 & 0xff);
	}

	/**
	 * Sign extended conversion
	 * @param b1
	 * @param b2
	 * @return an integer from bytes specified in little endian order
	 */
	public static int leIntSE(int b1, int b2) {
		return (b2 << 8) | (b1 & 0xff);
	}

	/**
	 * @param b1
	 * @param b2
	 * @param b3
	 * @param b4
	 * @return an integer from bytes specified in little endian order
	 */
	public static int leInt(int b1, int b2, int b3, int b4) {
		return ((b4 & 0xff) << 24)| ((b3 & 0xff) << 16) | ((b2 & 0xff) << 8) | (b1 & 0xff);
	}

	/**
	 * @param b
	 * @return an integer from bytes specified in big endian order
	 */
	public static int beInt(int... b) {
		int a = 0;
		for (int i = 0; i < b.length; i++) {
			a <<= 8;
			a |= (b[i] & 0xff);
		}
		return a;
	}

	/**
	 * @param b1
	 * @param b2
	 * @return an integer from bytes specified in big endian order
	 */
	public static int beInt(int b1, int b2) {
		return ((b1 & 0xff) << 8) | (b2 & 0xff);
	}

	/**
	 * Sign extended conversion
	 * @param b1
	 * @param b2
	 * @return an integer from bytes specified in big endian order
	 */
	public static int beIntSE(int b1, int b2) {
		return (b1 << 8) | (b2 & 0xff);
	}

	/**
	 * @param b1
	 * @param b2
	 * @param b3
	 * @param b4
	 * @return an integer from bytes specified in big endian order
	 */
	public static int beInt(int b1, int b2, int b3, int b4) {
		return ((b1 & 0xff) << 24)| ((b2 & 0xff) << 16) | ((b3 & 0xff) << 8) | (b4 & 0xff);
	}

	/**
	 * @param is
	 *            input stream
	 * @return a little endian 4-byte integer read from stream
	 * @throws IOException
	 */
	public static int readLeInt(InputStream is) throws IOException {
		int a = is.read();
		int b = is.read();
		int c = is.read();
		int d = is.read();
		return leInt(a, b, c, d);
	}

	/**
	 * @param is
	 *            input stream
	 * @return a big endian 4-byte integer read from stream
	 * @throws IOException
	 */
	public static int readBeInt(InputStream is) throws IOException {
		int a = is.read();
		int b = is.read();
		int c = is.read();
		int d = is.read();
		return beInt(a, b, c, d);
	}

	/**
	 * @param is
	 *            input stream
	 * @return a little endian 2-byte integer read from stream
	 * @throws IOException
	 */
	public static int readLeShort(InputStream is) throws IOException {
		int a = is.read();
		int b = is.read();
		return leInt(a, b);
	}

	/**
	 * @param is
	 *            input stream
	 * @return a big endian 2-byte integer read from stream
	 * @throws IOException
	 */
	public static int readBeShort(InputStream is) throws IOException {
		int a = is.read();
		int b = is.read();
		return beInt(a, b);
	}

	/**
	 * @param os
	 *            output stream
	 * @param val
	 *            little endian integer to write out
	 * @throws IOException
	 */
	public static void writeLeInt(OutputStream os, int val) throws IOException {
		byte[] b = { (byte) (val & 0xff), (byte) ((val >> 8) & 0xff), (byte) ((val >> 16) & 0xff),
				(byte) ((val >> 24) & 0xff) };
		os.write(b);
	}

	/**
	 * @param os
	 *            output stream
	 * @param val
	 *            little endian integer to write out
	 * @throws IOException
	 */
	public static void writeBeInt(OutputStream os, int val) throws IOException {
		byte[] b = { (byte) ((val >> 24) & 0xff), (byte) ((val >> 16) & 0xff), (byte) ((val >> 8) & 0xff),
				(byte) (val & 0xff) };
		os.write(b);
	}

	/**
	 * Read an image of little-endian integers
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readLeInt(InputStream is, IntegerDataset data, long start) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		final byte[] buf = new byte[4 * size];
		is.skip(start);
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = 0;
		for (int i = 0; i < size; i++) {
			int value = leInt(buf[pos], buf[pos + 1], buf[pos + 2], buf[pos + 3]);
			hash = (hash * 19 + value);
			idata[i] = value;
			if (value > amax) {
				amax = value;
			}
			if (value < amin) {
				amin = value;
			}
			pos += 4;
		}

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_MAX, amax);
		data.setStoredValue(AbstractDataset.STORE_MIN, amin);
		data.setStoredValue(AbstractDataset.STORE_HASH, hash);
	}

	/**
	 * Read an image of big-endian integers
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readBeInt(InputStream is, IntegerDataset data, long start) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		final byte[] buf = new byte[4 * size];
		is.skip(start);
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = 0;
		for (int i = 0; i < size; i++) {
			int value = beInt(buf[pos], buf[pos + 1], buf[pos + 2], buf[pos + 3]);
			hash = (hash * 19 + value);
			idata[i] = value;
			if (value > amax) {
				amax = value;
			}
			if (value < amin) {
				amin = value;
			}
			pos += 4;
		}

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_MAX, amax);
		data.setStoredValue(AbstractDataset.STORE_MIN, amin);
		data.setStoredValue(AbstractDataset.STORE_HASH, hash);
	}

	/**
	 * Read an image of big-endian shorts
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @param signed if true, shorts are sign-extended into integers
	 * @throws IOException
	 */
	public static void readBeShort(InputStream is, IntegerDataset data, long start, boolean signed) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		byte[] buf = new byte[2 * size];
		is.skip(start);
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = 0; // Byte offset to start of data
		if (signed) {
			for (int i = 0; i < size; i++) {
				int value = beIntSE(buf[pos], buf[pos+1]);
				hash = (hash * 19 + value);
				idata[i] = value;
				if (value > amax) {
					amax = value;
				}
				if (value < amin) {
					amin = value;
				}
				pos += 2;
			}
		} else {
			for (int i = 0; i < size; i++) {
				int value = beInt(buf[pos], buf[pos+1]);
				hash = (hash * 19 + value);
				idata[i] = value;
				if (value > amax) {
					amax = value;
				}
				if (value < amin) {
					amin = value;
				}
				pos += 2;
			}
		}

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_MAX, amax);
		data.setStoredValue(AbstractDataset.STORE_MIN, amin);
		data.setStoredValue(AbstractDataset.STORE_HASH, hash);
	}

	/**
	 * Read an image of little-endian shorts
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @param signed if true, shorts are sign-extended into integers
	 * @throws IOException
	 */
	public static void readLeShort(InputStream is, IntegerDataset data, long start, boolean signed) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		byte[] buf = new byte[2 * size];
		is.skip(start);
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = 0; // Byte offset to start of data
		if (signed) {
			for (int i = 0; i < size; i++) {
				int value = leIntSE(buf[pos], buf[pos + 1]);
				hash = (hash * 19 + value);
				idata[i] = value;
				if (value > amax) {
					amax = value;
				}
				if (value < amin) {
					amin = value;
				}
				pos += 2;
			}
		} else {
			for (int i = 0; i < size; i++) {
				int value = leInt(buf[pos], buf[pos + 1]);
				hash = (hash * 19 + value);
				idata[i] = value;
				if (value > amax) {
					amax = value;
				}
				if (value < amin) {
					amin = value;
				}
				pos += 2;
			}
		}
		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_MAX, amax);
		data.setStoredValue(AbstractDataset.STORE_MIN, amin);
		data.setStoredValue(AbstractDataset.STORE_HASH, hash);
	}

	/**
	 * Read an image of bytes
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readByte(InputStream is, ShortDataset data, long start) throws IOException {
		final int size = data.getSize();
		final short[] idata = data.getData();
		byte[] buf = new byte[size];
		is.skip(start);
		is.read(buf);
		short amax = Short.MIN_VALUE;
		short amin = Short.MAX_VALUE;
		int hash = 0;
		int pos = 0; // Byte offset to start of data
		for (int i = 0; i < size; i++) {
			short value = (short) (buf[pos] & 0xff);
			hash = (hash * 19 + value);
			idata[i] = value;
			if (value > amax) {
				amax = value;
			}
			if (value < amin) {
				amin = value;
			}
			pos += 1;
		}

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_MAX, amax);
		data.setStoredValue(AbstractDataset.STORE_MIN, amin);
		data.setStoredValue(AbstractDataset.STORE_HASH, hash);
	}

	/**
	 * Read an image of little-endian floats
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readLeFloat(InputStream is, FloatDataset data, long start) throws IOException {
		readFloat(is, data, start, ByteOrder.LITTLE_ENDIAN);
	}

	/**
	 * Read an image of big-endian floats
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readBeFloat(InputStream is, FloatDataset data, long start) throws IOException {
		readFloat(is, data, start, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * Read an image of floats, while specifying the endianness
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @param byte_order the byte order of the data
	 * @throws IOException
	 */
	public static void readFloat(InputStream is, FloatDataset data, long start, ByteOrder byte_order) throws IOException {
		final int size = data.getSize();
		final float[] fdata = data.getData();
		byte[] buf = new byte[4*size];
		is.skip(start);
		is.read(buf);
		byte [] bdata = new byte[4];
		float fmax = Float.MIN_VALUE;
		float fmin = Float.MAX_VALUE;
		double hash = 0.0;
		int pos = 0; // Byte offset to start of data
		float value;
		for (int i = 0; i < size; i++) {
			bdata[0] = buf[pos+0];
			bdata[1] = buf[pos+1];
			bdata[2] = buf[pos+2];
			bdata[3] = buf[pos+3];
			ByteBuffer byte_buffer = ByteBuffer.wrap(bdata);
			byte_buffer.order(byte_order);
			value = byte_buffer.getFloat();
			hash = (hash * 19 + value);
			fdata[i] = value;
			if (value > fmax) {
				fmax = value;
			}
			if (value < fmin) {
				fmin = value;
			}
			pos += 4;
		}

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue(AbstractDataset.STORE_MAX, fmax);
		data.setStoredValue(AbstractDataset.STORE_MIN, fmin);
		data.setStoredValue(AbstractDataset.STORE_HASH, (int)hash);
	}
	
	/**
	 * Read an image of little-endian floats
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readFloat(InputStream is, FloatDataset data, long start) throws IOException {
		readFloat(is, data, start, ByteOrder.LITTLE_ENDIAN);
	}

	private static final Pattern EXP_REGEX = Pattern.compile("[eE]");

	/**
	 * Faster parse for double
	 * @param number
	 * @return double value
	 */
	public static final double parseDouble(final String number) {
		if (number==null) return Double.NaN;
		
//		int offset = number.toLowerCase().indexOf('e');
//		if (offset<0) { // faster than parseDouble
		if (EXP_REGEX.matcher(number).matches()) {
			BigDecimal base = new BigDecimal(number);
			return base.scaleByPowerOfTen(0).doubleValue();// faster
		}
		return Double.parseDouble(number); // slow
	}

	/**
	 * Parse a string and try to convert it to the lowest precision Number object
	 * @param text
	 * @return a Number or null
	 */
	public static Number parseValue(String text) {
		try {
			BigInteger base = new BigInteger(text);
			int size = base.bitLength();
			if (size > 63) {
				try {
					return parseDouble(text);
				} catch (NumberFormatException de) {
					logger.info("Value {} is not a number", text);
				}
			} else if (size > 31) {
				return base.longValue();
			} else if (size > 15) {
				return base.intValue();
			} else if (size > 7) {
				return base.shortValue();
			} else {
				return base.byteValue();
			}
		} catch (Throwable be) {
			try { // nb no float as precision
				return parseDouble(text);
			} catch (NumberFormatException de) {
				logger.info("Value {} is not a number", text);
			}
		}
		return null;
	}

	/**
	 * Get string from byte array by assuming the encoding is ASCII
	 * @param bytes
	 * @param pos
	 * @param length
	 * @return string
	 * @throws UnsupportedEncodingException
	 */
	public static String getString(byte[] bytes, int pos, int length) throws UnsupportedEncodingException {
		byte[] t = new byte[length];
		System.arraycopy(bytes, pos, t, 0, length);
		return new String(t, "US-ASCII"); 
	}

	/**
	 * Get string from byte array by assuming the encoding is ASCII
	 * @param bytes
	 * @return string
	 * @throws UnsupportedEncodingException
	 */
	public static String getString(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, "US-ASCII"); 
	}

	/**
	 * Loads a list of files (through a string array) and returns a list of IDataset
	 * 
	 * @param data
	 *            output of data loaded (Optional)
	 * @param filePaths
	 *            file paths of files to be loaded
	 * @return data loaded
	 * @throws Exception
	 */
	public static List<IDataset> loadData(List<IDataset> data, String[] filePaths) throws Exception {
		if (data == null)
			data = new ArrayList<IDataset>(filePaths.length);
		for (int i = 0; i < filePaths.length; i++) {
			IDataHolder holder = null;
			holder = LoaderFactory.getData(filePaths[i]);
			File file = new File(filePaths[i]);
			String filename = file.getName();
			ILazyDataset lazy = holder.getLazyDataset(0);
			int[] shape = lazy.getShape();
			if (shape[0] > 1 && lazy.getRank() == 3) { // 3d dataset
				for (int j = 0; j < shape[0]; j++) {
					IDataset dataset = lazy.getSlice(
							new Slice(j, shape[0], shape[1])).squeeze();
					data.add(dataset);
				}
			} else { // if each single image is loaded separately (2d)
				IDataset dataset = lazy.getSlice(new Slice());
				if (dataset.getName() == null || dataset.getName().equals("")) {
					dataset.setName(filename);
				}
				data.add(dataset);
			}
		}
		return data;
	}
}
