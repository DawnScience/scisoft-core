/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class SelectedFramesModel extends AbstractOperationModel {
	
	@OperationModelField(hint="Position of first (or only) dataset, leave blank to average all data",label = "First" )
	private Integer startFrame = 0;
	@OperationModelField(hint="Position of end dataset, leave empty to use a single dataset",label = "Last" )
	private Integer endFrame = 1;
	@OperationModelField(hint="Scaling of the multiplying data", label="Scaling")
	private double scaling = 1.0;
	
	public Integer getStartFrame() {
		return startFrame;
	}

	public void setStartFrame(Integer startFrame) {
		firePropertyChange("startFrame", this.startFrame, this.startFrame = startFrame);
	}

	public Integer getEndFrame() {
		return endFrame;
	}

	public void setEndFrame(Integer endFrame) {
		firePropertyChange("endFrame", this.endFrame, this.endFrame = endFrame);
	}

	public double getScaling() {
		return scaling;
	}
	
	public void setScaling(double scaling) {
		firePropertyChange("scaling", this.scaling, this.scaling = scaling);
	}
	
	@OperationModelField(label = "Save subtraction frame?", hint="When checked the corrected frame that was used for the subtraction will be saved in the results file as an auxillary dataset.", fieldPosition = 99)
	private boolean saveCorrectedFrame = false;
	
	public boolean isSaveCorrectedFrame() {
		return saveCorrectedFrame;
	}
}
