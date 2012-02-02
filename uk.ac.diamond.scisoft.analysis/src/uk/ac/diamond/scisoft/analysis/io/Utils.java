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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.ShortDataset;

/**
 * Utilities class
 */
public class Utils {

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
	public static void readLeInt(InputStream is, IntegerDataset data, int start) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		final byte[] buf = new byte[start + 4 * size];
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = start;
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
		data.setStoredValue("max", amax);
		data.setStoredValue("min", amin);
		data.setStoredValue("hash", hash);
	}

	/**
	 * Read an image of big-endian integers
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readBeInt(InputStream is, IntegerDataset data, int start) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		final byte[] buf = new byte[start + 4 * size];
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = start;
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
		data.setStoredValue("max", amax);
		data.setStoredValue("min", amin);
		data.setStoredValue("hash", hash);
	}

	/**
	 * Read an image of big-endian shorts
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readBeShort(InputStream is, IntegerDataset data, int start) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		byte[] buf = new byte[(2 * size)+start];
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = start; // Byte offset to start of data
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

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue("max", amax);
		data.setStoredValue("min", amin);
		data.setStoredValue("hash", hash);
	}

	/**
	 * Read an image of little-endian shorts
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readLeShort(InputStream is, IntegerDataset data, int start) throws IOException {
		final int size = data.getSize();
		final int[] idata = data.getData();
		byte[] buf = new byte[(2 * size)+start];
		is.read(buf);
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		int pos = start; // Byte offset to start of data
		for (int i = 0; i < size; i++) {
			int value = leInt(buf[pos], buf[pos+1]);
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

		hash = hash*19 + data.getDtype()*17 + data.getElementsPerItem();
		int[] shape = data.getShape();
		int rank = shape.length;
		for (int i = 0; i < rank; i++) {
			hash = hash*17 + shape[i];
		}
		data.setStoredValue("max", amax);
		data.setStoredValue("min", amin);
		data.setStoredValue("hash", hash);
	}

	/**
	 * Read an image of bytes
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readByte(InputStream is, ShortDataset data, int start) throws IOException {
		final int size = data.getSize();
		final short[] idata = data.getData();
		byte[] buf = new byte[(size)+start];
		is.read(buf);
		short amax = Short.MIN_VALUE;
		short amin = Short.MAX_VALUE;
		int hash = 0;
		int pos = start; // Byte offset to start of data
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
		data.setStoredValue("max", amax);
		data.setStoredValue("min", amin);
		data.setStoredValue("hash", hash);
	}


	/**
	 * Read an image of float
	 * @param is
	 * @param data
	 * @param start number of bytes from start of input stream
	 * @throws IOException
	 */
	public static void readFloat(InputStream is, FloatDataset data, int start) throws IOException {
		final int size = data.getSize();
		final float[] fdata = data.getData();
		byte[] buf = new byte[4*(size)+start];
		is.read(buf);
		byte [] bdata = new byte[4];
		float fmax = Float.MIN_VALUE;
		float fmin = Float.MAX_VALUE;
		double hash = 0.0;
		int pos = start; // Byte offset to start of data
		float value;
		for (int i = 0; i < size; i++) {
			bdata[0] = buf[pos+3];
			bdata[1] = buf[pos+2];
			bdata[2] = buf[pos+1];
			bdata[3] = buf[pos];
			value = ByteBuffer.wrap(bdata).getFloat();
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
		data.setStoredValue("max", fmax);
		data.setStoredValue("min", fmin);
		data.setStoredValue("hash", (int)hash);
	}

	/**
	 * Parse a string and try to convert it to the lowest precision Number object
	 * @param text
	 * @return a Number or null
	 */
	public static Number parseValue(String text) {
		try {
			return Byte.parseByte(text);
		} catch (NumberFormatException be) {
			try {
				return Short.parseShort(text);
			} catch (NumberFormatException se) {
				try {
					return Integer.parseInt(text);
				} catch (NumberFormatException ie) {
					try {
						return Long.parseLong(text);
					} catch (NumberFormatException le) {
						try { // nb no float as precision
							return Double.parseDouble(text);
						} catch (NumberFormatException de) {
							SRSLoader.logger.info("Value {} is not a number", text);
						}
					}
				}
			}
		}
		return null;
	}
}
