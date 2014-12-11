/*
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.processing.operations.spectroscopy;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class XAFSFFTModel extends AbstractOperationModel {

	private boolean oversample = true;
	private boolean filter = false;
	
	public boolean isOversample() {
		return oversample;
	}
	public void setOversample(boolean oversample) {
		firePropertyChange("oversample", this.oversample, this.oversample = oversample);
	}
	public boolean isFilter() {
		return filter;
	}
	public void setFilter(boolean filter) {
		firePropertyChange("filter", this.filter, this.filter = filter);
		this.filter = filter;
	}
	
}
