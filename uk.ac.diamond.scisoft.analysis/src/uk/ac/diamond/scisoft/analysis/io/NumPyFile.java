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

import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.ByteDataset;
import org.eclipse.january.dataset.ComplexDoubleDataset;
import org.eclipse.january.dataset.ComplexFloatDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.FloatDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.ShortDataset;

public class NumPyFile {
	/**
	 * Magic number at the head of the file, includes version number as all numpy files are always version 1.0.
	 */
	/*package*/ static byte[] magic = { (byte) 0x93, 'N', 'U', 'M', 'P', 'Y', 0x1, 0x0 };

	/*package*/ static class DataTypeInfo {
		static DataTypeInfo create() {
			return new DataTypeInfo();
		}

		DataTypeInfo setInterface(Class<? extends Dataset> clazz) {
			this.clazz = clazz;
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
		Class<? extends Dataset> clazz;
		int iSize;
		boolean unsigned;
	}

	/*package*/ static Map<String, DataTypeInfo> dataTypeMap = new HashMap<>();
	/*package*/ static Map<Class<? extends Dataset>, DataTypeInfo> numPyTypeMap = new HashMap<>();
	/*package*/ static Map<Class<? extends Dataset>, DataTypeInfo> unsignedNumPyTypeMap = new HashMap<>();
	static {
		List<DataTypeInfo> infos = new ArrayList<DataTypeInfo>();
		// order is important here - unsigned must precede the signed types so the latter overwrite
		// them in the maps
		infos.add(DataTypeInfo.create().setNumPyType("|b1").setInterface(BooleanDataset.class).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("|u1").setInterface(ByteDataset.class).setISize(1).setUnsigned(true));
		infos.add(DataTypeInfo.create().setNumPyType("|i1").setInterface(ByteDataset.class).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<u2").setInterface(ShortDataset.class).setISize(1).setUnsigned(true));
		infos.add(DataTypeInfo.create().setNumPyType("<i2").setInterface(ShortDataset.class).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<u4").setInterface(IntegerDataset.class).setISize(1).setUnsigned(true));
		infos.add(DataTypeInfo.create().setNumPyType("<i4").setInterface(IntegerDataset.class).setISize(1));
		// Unsigned 64 bits cannot be unwrapped so do not set its unsignedness
		infos.add(DataTypeInfo.create().setNumPyType("<u8").setInterface(LongDataset.class).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<i8").setInterface(LongDataset.class).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<f4").setInterface(FloatDataset.class).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<f8").setInterface(DoubleDataset.class).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<c8").setInterface(ComplexFloatDataset.class).setISize(2));
		infos.add(DataTypeInfo.create().setNumPyType("<c16").setInterface(ComplexDoubleDataset.class).setISize(2));
		
		for (DataTypeInfo dataTypeInfo : infos) {
			dataTypeMap.put(dataTypeInfo.numPyType, dataTypeInfo);
			if (dataTypeInfo.unsigned)
				unsignedNumPyTypeMap.put(dataTypeInfo.clazz, dataTypeInfo);
			else
				numPyTypeMap.put(dataTypeInfo.clazz, dataTypeInfo);
		}
	}

}
