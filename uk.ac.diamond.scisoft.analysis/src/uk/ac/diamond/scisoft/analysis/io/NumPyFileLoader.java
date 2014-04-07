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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

/**
 * Reads files in npy format as defined here; http://svn.scipy.org/svn/numpy/trunk/doc/neps/npy-format.txt
 */
public class NumPyFileLoader extends AbstractFileLoader {
	transient private static final Logger logger = LoggerFactory.getLogger(NumPyFileLoader.class);
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

			AbstractDataset data = loadDataset(fBuffer);

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

	public static AbstractDataset loadDataset(ByteBuffer fBuffer) throws ScanFileHolderException {
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
				shapeList.add(Integer.parseInt(shapeTupleStrArray[i]));
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

		AbstractDataset data = RawBinaryLoader.loadRawDataset(fBuffer, dtype, isize, tSize, shape);
		if (unsigned)
			data = AbstractDataset.array(data, unsigned);

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
	public static AbstractDataset loadFileHelper(String fileName) throws ScanFileHolderException {
		NumPyFileLoader fileLoader = new NumPyFileLoader(fileName);
		DataHolder dataHolder = fileLoader.loadFile();
		AbstractDataset dataset = dataHolder.getDataset(0);
		return dataset;
	}

}
