/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

public class NumPyFile {
	/**
	 * Magic number at the head of the file, includes version number as all numpy files are always version 1.0.
	 */
	/*package*/ static byte[] magic = { (byte) 0x93, 'N', 'U', 'M', 'P', 'Y', 0x1, 0x0 };

	/*package*/ static class DataTypeInfo {
		static DataTypeInfo create() {
			return new DataTypeInfo();
		}

		DataTypeInfo setDType(int dType) {
			this.dType = dType;
			return this;
		}

		DataTypeInfo setNumPyType(String numPyType) {
			this.numPyType = numPyType;
			return this;
		}

		DataTypeInfo setISize(int iSize) {
			this.iSize = iSize;
			return this;
		}

		DataTypeInfo setUnsigned(boolean isUnsigned) {
			unsigned = isUnsigned;
			return this;
		}

		void setShape(int[] shape) {
			this.shape = shape;
		}

		int[] getShape() {
			return shape;
		}

		int[] shape;
		String numPyType;
		int dType;
		int iSize;
		boolean unsigned;
	}

	/*package*/ static Map<String, DataTypeInfo> dataTypeMap = new HashMap<String, DataTypeInfo>();
	/*package*/ static Map<Integer, DataTypeInfo> numPyTypeMap = new HashMap<Integer, DataTypeInfo>();
	/*package*/ static Map<Integer, DataTypeInfo> unsignedNumPyTypeMap = new HashMap<Integer, DataTypeInfo>();
	static {
		List<DataTypeInfo> infos = new ArrayList<DataTypeInfo>();
		// order is important here - unsigned must precede the signed types so the latter overwrite
		// them in the maps
		infos.add(DataTypeInfo.create().setNumPyType("|b1").setDType(Dataset.BOOL).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("|u1").setDType(Dataset.INT8).setISize(1).setUnsigned(true));
		infos.add(DataTypeInfo.create().setNumPyType("|i1").setDType(Dataset.INT8).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<u2").setDType(Dataset.INT16).setISize(1).setUnsigned(true));
		infos.add(DataTypeInfo.create().setNumPyType("<i2").setDType(Dataset.INT16).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<u4").setDType(Dataset.INT32).setISize(1).setUnsigned(true));
		infos.add(DataTypeInfo.create().setNumPyType("<i4").setDType(Dataset.INT32).setISize(1));
		// Unsigned 64 bits cannot be unwrapped so do not set its unsignedness
		infos.add(DataTypeInfo.create().setNumPyType("<u8").setDType(Dataset.INT64).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<i8").setDType(Dataset.INT64).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<f4").setDType(Dataset.FLOAT32).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<f8").setDType(Dataset.FLOAT64).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<c8").setDType(Dataset.COMPLEX64).setISize(2));
		infos.add(DataTypeInfo.create().setNumPyType("<c16").setDType(Dataset.COMPLEX128).setISize(2));
		
		for (DataTypeInfo dataTypeInfo : infos) {
			dataTypeMap.put(dataTypeInfo.numPyType, dataTypeInfo);
			if (dataTypeInfo.unsigned)
				unsignedNumPyTypeMap.put(dataTypeInfo.dType, dataTypeInfo);
			else
				numPyTypeMap.put(dataTypeInfo.dType, dataTypeInfo);
		}
	}

}
