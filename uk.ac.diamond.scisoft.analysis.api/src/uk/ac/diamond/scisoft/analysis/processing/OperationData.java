/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.processing;

import java.io.Serializable;
import java.util.Arrays;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Object to hold data and parameters for execution of operations in
 * a pipeline.
 * 
 * TODO - should this just be a DataMessageComponent?
 * 
 */
public class OperationData {

	private IDataset[]      data;
	private IDataset        mask;
	private Serializable[]  auxData;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(auxData);
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((mask == null) ? 0 : mask.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationData other = (OperationData) obj;
		if (!Arrays.equals(auxData, other.auxData))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (mask == null) {
			if (other.mask != null)
				return false;
		} else if (!mask.equals(other.mask))
			return false;
		return true;
	}
	public IDataset getData() {
		return getData(0);
	}
	public IDataset getData(int index) {
		return data[index];
	}
	public void setData(IDataset... data) {
		this.data = data;
	}
	public Serializable[] getAuxData() {
		return auxData;
	}
	public void setAuxData(Serializable... auxData) {
		this.auxData = auxData;
	}
	public OperationData(IDataset data) {
		this(data, (Serializable)null);
	}
	public OperationData(IDataset... data) {
		this.data = data;
	}
	public OperationData(IDataset data, Serializable... aux) {
		this.data    = new IDataset[]{data};
		this.auxData = aux;
	}
	public IDataset getMask() {
		return mask;
	}
	public void setMask(IDataset mask) {
		this.mask = mask;
	}

}
