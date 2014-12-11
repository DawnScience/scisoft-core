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

public class XAFSNormalisationModel extends AbstractOperationModel {

	@OperationModelField(hint="Define the pre-edge energy.", label = "Pre-edge energy")
	private double preEdge = 0;
	@OperationModelField(hint="Define the post-edge energy.", label = "Post-edge energy")
	private double postEdge = 0;
	@OperationModelField(hint="Set order of pre-edge polynomial.", label = "Pre-edge polynomial order")
	private int preEdgeOrder = 1;
	@OperationModelField(hint="Set order of post-edge polynomial.", label = "Post-edge polynomial order")
	private int postEdgeOrder = 1;
	
	public double getPreEdge() {
		return preEdge;
	}
	public void setPreEdge(double preEdge) {
		firePropertyChange("preEdge", this.preEdge, this.preEdge = preEdge);
	}
	public double getPostEdge() {
		return postEdge;
	}
	public void setPostEdge(double postEdge) {
		firePropertyChange("postEdge", this.postEdge, this.postEdge = postEdge);
	}
	
	public int getPreEdgeOrder() {
		return preEdgeOrder;
	}
	public void setPreEdgeOrder(int preEdgeOrder) {
		firePropertyChange("preEdgeOrder", this.preEdgeOrder, this.preEdgeOrder = preEdgeOrder);
	}
	public int getPostEdgeOrder() {
		return postEdgeOrder;
	}
	public void setPostEdgeOrder(int postEdgeOrder) {
		firePropertyChange("postEdgeOrder", this.postEdgeOrder, this.postEdgeOrder = postEdgeOrder);
	}
	
}
