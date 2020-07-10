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
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.ShortDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.MetadataFactory;
import org.eclipse.january.metadata.StatisticsMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities class
 */
public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

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

	private static int getByte(InputStream is) throws IOException {
		int b = is.read();
		if (b == -1) {
			throw new IOException("Unexpected EOF when reading byte in stream");
		}
		return b;
	}

	/**
	 * @param is
	 *            input stream
	 * @return a little endian 4-byte integer read from stream
	 * @throws IOException
	 */
	public static int readLeInt(InputStream is) throws IOException {
		int a = getByte(is);
		int b = getByte(is);
		int c = getByte(is);
		int d = getByte(is);
		return leInt(a, b, c, d);
	}

	/**
	 * @param is
	 *            input stream
	 * @return a big endian 4-byte integer read from stream
	 * @throws IOException
	 */
	public static int readBeInt(InputStream is) throws IOException {
		int a = getByte(is);
		int b = getByte(is);
		int c = getByte(is);
		int d = getByte(is);
		return beInt(a, b, c, d);
	}

	/**
	 * @param is
	 *            input stream
	 * @return a little endian 2-byte integer read from stream
	 * @throws IOException
	 */
	public static int readLeShort(InputStream is) throws IOException {
		int a = getByte(is);
		int b = getByte(is);
		return leInt(a, b);
	}

	/**
	 * @param is
	 *            input stream
	 * @return a big endian 2-byte integer read from stream
	 * @throws IOException
	 */
	public static int readBeShort(InputStream is) throws IOException {
		int a = getByte(is);
		int b = getByte(is);
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

		storeStats(data, amax, amin, hash);
	}

	@SuppressWarnings("unchecked")
	private static void storeStats(Dataset data, Number max, Number min, int hash) {
		StatisticsMetadata<Number> stats = data.getFirstMetadata(StatisticsMetadata.class);
		if (stats == null) {
			try {
				stats = MetadataFactory.createMetadata(StatisticsMetadata.class, data);
			} catch (MetadataException e) {
				logger.error("Could not create max/min metadata", e);
				return;
			}
		}

		stats.setMaximumMinimumSum(max, min, null);
		stats.setHash(hash*19 + data.getClass().hashCode()*17 + data.getElementsPerItem());
		data.addMetadata(stats);
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

		storeStats(data, amax, amin, hash);
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

		storeStats(data, amax, amin, hash);
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

		storeStats(data, amax, amin, hash);
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

		storeStats(data, amax, amin, hash);
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
	 * @param byteOrder the byte order of the data
	 * @throws IOException
	 */
	private static void readFloat(InputStream is, FloatDataset data, long start, ByteOrder byteOrder) throws IOException {
		final int size = data.getSize();
		final float[] fdata = data.getData();
		byte[] buf = new byte[4*size];
		is.skip(start);
		is.read(buf);
		byte[] bdata = new byte[4];
		ByteBuffer byteBuffer = ByteBuffer.wrap(bdata);
		byteBuffer.order(byteOrder);
		float fmax = Float.MIN_VALUE;
		float fmin = Float.MAX_VALUE;
		double hash = 0.0;
		int pos = 0; // Byte offset to start of data
		float value;
		for (int i = 0; i < size; i++) {
			bdata[0] = buf[pos + 0];
			bdata[1] = buf[pos + 1];
			bdata[2] = buf[pos + 2];
			bdata[3] = buf[pos + 3];
			value = byteBuffer.getFloat(0);
			hash = hash * 19 + Float.floatToRawIntBits(value);
			fdata[i] = value;
			if (value > fmax) {
				fmax = value;
			}
			if (value < fmin) {
				fmin = value;
			}
			pos += 4;
		}

		storeStats(data, fmax, fmin, (int) hash);
	}

	/**
	 * Regular expression for any floating point number
	 */
	public static final String FLOATING_POINT_NUMBER = "[-+]?(?:(?:[0-9]+\\.?[0-9]*)|(?:\\.[0-9]+))(?:[eE][-+]?[0-9]+)?";

	private static final Pattern FLOAT_POINT_REGEX = Pattern.compile(FLOATING_POINT_NUMBER);

	/**
	 * @param text
	 * @return true if it can be a number
	 */
	public static final boolean isNumber(String text) {
		return FLOAT_POINT_REGEX.matcher(text).matches();
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
		return loadData(data, filePaths, null);
	}

	/**
	 * Loads a list of files (through a string array) and returns a list of IDataset
	 * 
	 * @param data
	 *            output of data loaded (Optional)
	 * @param filePaths
	 *            file paths of files to be loaded
	 * @param dataname
	 *            name of data to load in case multiple data is available
	 * @return data loaded
	 * @throws Exception
	 */
	public static List<IDataset> loadData(List<IDataset> data, String[] filePaths, String dataname) throws Exception {
		if (data == null)
			data = new ArrayList<IDataset>(filePaths.length);
		for (int i = 0; i < filePaths.length; i++) {
			IDataHolder holder = null;
			holder = LoaderFactory.getData(filePaths[i]);
			File file = new File(filePaths[i]);
			String filename = file.getName();
			ILazyDataset lazy = null;
			if (dataname != null) {
				lazy = holder.getLazyDataset(dataname);
			} else {
				lazy = holder.getLazyDataset(0);
			}
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

	static Dataset createDataset(RandomAccessFile raf, int[] shape, boolean keepBitWidth) throws IOException {
		Dataset data;
	
		// read in all the data at once for speed.
	
		byte[] read = new byte[shape[0] * shape[1] * 2];
		raf.read(read);
	
		// and put it into the dataset
		data = DatasetFactory.zeros(IntegerDataset.class, shape);
		int[] databuf = ((IntegerDataset) data).getData();
		int amax = Integer.MIN_VALUE;
		int amin = Integer.MAX_VALUE;
		int hash = 0;
		for (int i = 0, j = 0; i < databuf.length; i++, j += 2) {
			int value = leInt(read[j], read[j + 1]);
			hash = (hash * 19 + value);
			databuf[i] = value;
			if (value > amax) {
				amax = value;
			}
			if (value < amin) {
				amin = value;
			}
		}
	
		if (keepBitWidth||amax < (1 << 15)) {
			data = DatasetUtils.cast(ShortDataset.class, data);
		}

		storeStats(data, amax, amin, hash);
	
		return data;
	}

	private static final String DLS_PREFIX = "/dls/";
	private static final String DLS_DATA_WIN_DIR = "\\\\data.diamond.ac.uk";
	private static final String DLS_SCIENCE_PREFIX = "/dls/science/";
	private static final String DLS_SCIENCE_WIN_DIR = "\\\\dls-science\\science";
	private static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows");

	/**
	 * Translate Diamond specific file paths to their Windows equivalent on Windows
	 * @param filePath
	 * @return Windows file path if on Windows and starts with Diamond absolute paths
	 */
	public static String translateDLSFilePath(String filePath) {
		return translateDLSFilePath(filePath, IS_WINDOWS);
	}

	static String translateDLSFilePath(String filePath, boolean isWindows) {
		if (isWindows) {
			if (filePath == null) {
				return null;
			} else if (filePath.startsWith(DLS_SCIENCE_PREFIX)) {
				return new File(DLS_SCIENCE_WIN_DIR, filePath.substring(DLS_SCIENCE_PREFIX.length())).getPath();
			} else if (filePath.startsWith(DLS_PREFIX)) {
				return new File(DLS_DATA_WIN_DIR, filePath.substring(DLS_PREFIX.length())).getPath();
			}
		}
		return filePath;
	}

	/**
	 * Find external file by checking parent's directory
	 * @param logger
	 * @param parent
	 * @param ePath external file path
	 * @return absolute file path or null if not found
	 */
	public static String findExternalFilePath(final Logger logger, final String parent, String ePath) {
		if (ePath == null) {
			return null;
		}

		boolean exists = false;
		ePath = translateDLSFilePath(ePath);
		File ef = new File(ePath);
		if (logger != null) {
			logger.trace("Looking for external file {}", ePath);
		}
		if (!ef.isAbsolute()) {
			exists = ef.exists();
			if (!exists) { // use directory of linking file
				if (logger != null) {
					logger.trace("Could not find external relative file, now trying in {}", parent);
				}
				File ref = new File(parent, ef.getName());
				exists = ref.exists();
				if (!exists) {
					// append to directory of linking file
					File ref2 = new File(parent, ePath);
					if (!ref2.equals(ref)) {
						if (logger != null) {
							logger.trace("Could not find external relative file, finally trying {}", ref2);
						}
						ref = ref2;
					}
				}
				ePath = ref.getAbsolutePath();
				ef = ref;
			}
		} else {
			// first try to find in directory of current file
			File ref = new File(parent, ef.getName());
			exists = ref.exists();
			if (exists) {
				ePath = ref.getAbsolutePath();
				ef = ref;
				if (logger != null) {
					logger.trace("Found external file {} in {}", ef.getName(), parent);
				}
			}
		}

		if (!exists) {
			exists = ef.exists();
			ePath = ef.getAbsolutePath();
			if (exists) {
				if (logger != null) {
					logger.trace("Finally found external file {}", ePath);
				}
			} else {
				ePath = null;
			}
		}
		return ePath;
	}

	/**
	 * Find external file by checking parent's directory
	 * @param parent
	 * @param ePath external file path
	 * @return absolute file path or null if not found
	 */
	static String findExternalFilePath(final String parent, String ePath) {
		return findExternalFilePath(null, parent, ePath);
	}

	/**
	 * Translate path to Unix path if on Windows
	 * @param path
	 * @return Unix file path
	 */
	public static String translateToUnixPath(final String path) {
		return translateToUnixPath(path, IS_WINDOWS);
	}

	static String translateToUnixPath(final String path, boolean isWindows) {
		if (path == null) {
			return null;
		}
		return isWindows ? path.replace('\\', '/') : path;
	}
}
