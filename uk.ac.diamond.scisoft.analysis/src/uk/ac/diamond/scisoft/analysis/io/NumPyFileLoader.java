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
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;

import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads files in npy format as defined here; http://svn.scipy.org/svn/numpy/trunk/doc/neps/npy-format.txt
 */
public class NumPyFileLoader extends AbstractFileLoader {
	private static final Logger logger = LoggerFactory.getLogger(NumPyFileLoader.class);
	private String fileName;

	public NumPyFileLoader() {

	}

	/**
	 * @param fileName
	 */
	public NumPyFileLoader(String fileName) {
		this.fileName = fileName;
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

			ByteBuffer fBuffer;
			FileChannel fc = null; // use on Non-windows only
			if (System.getProperty("os.name").contains("Windows")) {
				// This is a workaround for bug 4715154, see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4715154
				// Can't use fc.map, so load the whole file in a byte array instead
				// For small files it is likely that this alternative method is faster anyway, the problem is on
				// big files that temporarily a large amount of extra memory is needed.
				long fileSizeLong = f.length();
				if (fileSizeLong > Integer.MAX_VALUE)
					throw new IOException("File too big " + f.getName());
				int fileSize = (int) fileSizeLong;
				byte[] bytes = new byte[fileSize];
				int offset = 0;
				int count = 0;
				while (offset < fileSize) {
					count = fi.read(bytes, offset, fileSize - offset);
					if (count >= 0)
						offset += count;
					else
						throw new IOException("Can't read file " + f.getName());
				}
				fBuffer = ByteBuffer.wrap(bytes);
			} else {
				fc = fi.getChannel();
				fBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size());
			}

			Dataset data = loadDataset(fBuffer);

			if (fc != null)
				fc.close();

			output.addDataset("NumPy file", data);
			data.setDirty();
		} catch (Exception ex) {
			if (ex instanceof ScanFileHolderException)
				throw (ScanFileHolderException) ex;
			throw new ScanFileHolderException("There was a problem reading the NumPy file", ex);
		} finally {
			if (fi != null)
				try {
					fi.close();
				} catch (IOException e) {
					logger.error("Exception when closing file", e);
				}
		}
		return output;
	}

	public static Dataset loadDataset(ByteBuffer fBuffer) throws ScanFileHolderException {
		fBuffer.order(ByteOrder.LITTLE_ENDIAN);

		for (int i = 0; i < NumPyFile.magic.length; i++) {
			byte b = fBuffer.get();
			if (NumPyFile.magic[i] != b) {
				throw new ScanFileHolderException("File does not start npy magic number/version");
			}
		}

		short header_len = fBuffer.getShort();
		byte[] formatBytes = new byte[header_len];
		fBuffer.get(formatBytes);
		String format;
		try {
			format = new String(formatBytes, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			throw new ScanFileHolderException("Impossible error, US-ASCII is always available?", e);
		}

		// parse format
		// format looks like this, and always in this order:
		// {'descr': '<i4', 'fortran_order': False, 'shape': (100,), }
		// or:
		// {'descr': '<i4', 'fortran_order': False, 'shape': (100, 100), }
		String[] kvs = format.split(", ", 3);

		String[] descrPair = kvs[0].split(": ");
		String description = descrPair[1].substring(1, descrPair[1].length() - 1);

		String[] forOrdPair = kvs[1].split(": ");
		Boolean fortran_order = Boolean.parseBoolean(forOrdPair[1]);

		String[] shapePair = kvs[2].split(": ");
		String shapeTupleStr = shapePair[1].substring(1, shapePair[1].lastIndexOf(')'));
		String[] shapeTupleStrArray = shapeTupleStr.split(", ?");
		ArrayList<Integer> shapeList = new ArrayList<Integer>();
		if (shapeTupleStrArray.length == 1 && "".equals(shapeTupleStrArray[0])) {
			shapeList.add(1);
		} else {
			for (int i = 0; i < shapeTupleStrArray.length; i++) {
				shapeList.add(Integer.parseInt(shapeTupleStrArray[i].replace("L", "")));
			}
		}
		int rank = shapeList.size();
		int[] shape = new int[rank];
		for (int j = 0; j < rank; j++) {
			shape[j] = shapeList.get(j);
		}

		if (fortran_order) {
			throw new ScanFileHolderException("Only Non-fortran order is supported");
		}

		// Figure out the Data Set type from the description string
		NumPyFile.DataTypeInfo dataTypeInfo = NumPyFile.dataTypeMap.get(description);
		if (dataTypeInfo == null) {
			throw new ScanFileHolderException("Unknown/unsupported data type description: " + description);
		}
		int dtype = dataTypeInfo.dType;
		int isize = dataTypeInfo.iSize;
		boolean unsigned = dataTypeInfo.unsigned;
		int tSize = isize;
		for (int j = 0; j < rank; j++) {
			tSize *= shape[j];
		}

		Dataset data = RawBinaryLoader.loadRawDataset(fBuffer, dtype, isize, tSize, shape);
		if (unsigned)
			data = DatasetFactory.createFromObject(data, unsigned);

		return data;
	}

	/**
	 * Load a NumPy file and return its contained DataSet.
	 * <p>
	 * Provided as a convenience method
	 * 
	 * @param fileName
	 * @return loaded IDataset
	 * @throws ScanFileHolderException
	 *             if the file failed to load as a NumPy file
	 */
	public static Dataset loadFileHelper(String fileName) throws ScanFileHolderException {
		NumPyFileLoader fileLoader = new NumPyFileLoader(fileName);
		DataHolder dataHolder = fileLoader.loadFile();
		Dataset dataset = dataHolder.getDataset(0);
		return dataset;
	}

}
