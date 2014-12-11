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
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class XAFSEDEPostEdgeBackgroundModel extends AbstractOperationModel {

	@OperationModelField(hint="Define the polynomial order.", label = "Post-edge polynomial order")
	private int polyOrder = 2;
	@OperationModelField(label = "K-weight")
	private int kw = 2;
	private int offset = 30;
	private int step = 40;
	
	public int getPolyOrder() {
		return polyOrder;
	}
	public void setPolyOrder(int polyOrder) {
		firePropertyChange("polyOrder", this.polyOrder, this.polyOrder = polyOrder);
	}
	public int getKw() {
		return kw;
	}
	public void setKw(int kw) {
		firePropertyChange("kw", this.kw, this.kw = kw);
	}
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		firePropertyChange("offset", this.offset, this.offset = offset);
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
		firePropertyChange("step", this.step, this.step = step);
	}
	
}
