/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;


import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import uk.ac.diamond.scisoft.analysis.processing.operations.internaldata.InternalChannelAverageFrameModel;


// @author: Tim Snow (tim.snow@diamond.ac.uk)


public class ExternalChannelAverageFrameModel extends InternalChannelAverageFrameModel {
	
	
	@OperationModelField(label = "File Path", hint = "Path to the file containing the dataset the values to average and apply", file = FileType.EXISTING_FILE, fieldPosition = 1)
	private String filePath = "";
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		firePropertyChange("filePath", this.filePath, this.filePath = filePath);
	}
}
