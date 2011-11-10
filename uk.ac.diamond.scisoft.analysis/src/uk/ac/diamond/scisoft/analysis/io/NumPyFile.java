/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

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

		String numPyType;
		int dType;
		int iSize;
	}

	/*package*/ static Map<String, DataTypeInfo> dataTypeMap = new HashMap<String, DataTypeInfo>();
	/*package*/ static Map<Integer, DataTypeInfo> numPyTypeMap = new HashMap<Integer, DataTypeInfo>();
	static {
		Set<DataTypeInfo> infos = new HashSet<DataTypeInfo>();
		infos.add(DataTypeInfo.create().setNumPyType("|b1").setDType(AbstractDataset.BOOL).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("|i1").setNumPyType("|i1").setDType(AbstractDataset.INT8).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<i2").setDType(AbstractDataset.INT16).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<i4").setDType(AbstractDataset.INT32).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<i8").setDType(AbstractDataset.INT64).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<f4").setDType(AbstractDataset.FLOAT32).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<f8").setDType(AbstractDataset.FLOAT64).setISize(1));
		infos.add(DataTypeInfo.create().setNumPyType("<c8").setDType(AbstractDataset.COMPLEX64).setISize(2));
		infos.add(DataTypeInfo.create().setNumPyType("<c16").setDType(AbstractDataset.COMPLEX128).setISize(2));
		
		for (DataTypeInfo dataTypeInfo : infos) {
			dataTypeMap.put(dataTypeInfo.numPyType, dataTypeInfo);
			numPyTypeMap.put(dataTypeInfo.dType, dataTypeInfo);
		}
	}

}
