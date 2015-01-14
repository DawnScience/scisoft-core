/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class SelectedFramesModel extends ExternalDataModel {
	
	@OperationModelField(hint="Position of first (or only) dataset, leave blank to average all data",label = "First:" )
	private Integer startFrame = null;
	@OperationModelField(hint="Position of end dataset, leave empty to use a single dataset",label = "Last:" )
	private Integer endFrame = null;
	
	public Integer getStartFrame() {
		return startFrame;
	}

	public void setStartFrame(int startFrame) {
		firePropertyChange("startFrame", this.startFrame, this.startFrame = startFrame);
	}

	public Integer getEndFrame() {
		return endFrame;
	}

	public void setEndFrame(Integer endFrame) {
		firePropertyChange("endFrame", this.endFrame, this.endFrame = endFrame);
	}

}
