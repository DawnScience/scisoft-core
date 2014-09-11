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

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;

/**
 * Object to hold return data from an IOperation.
 */
public class OperationData {

	private IDataset data;
	private Serializable[] auxData;

	public IDataset getData() {
		return data;
	}
	
	public OperationData() {
        this(null, null);
	}
	public OperationData(IDataset data) {
		this(data, (Serializable) null);
	}

	public OperationData(IDataset data, Serializable... aux) {
		this.data = data;
		this.auxData = aux;
	}

	public void setData(IDataset data) {
		this.data = data;
	}

	public Serializable[] getAuxData() {
		return auxData;
	}

	public void setAuxData(Serializable... auxData) {
		this.auxData = auxData;
	}

}
