/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class FluxCorrectionModel extends AbstractOperationModel {

	@OperationModelField(label="Path to normalisation file", hint = "Path to normalisation file",file = FileType.EXISTING_FILE)
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		firePropertyChange("path", this.path, this.path= path);
	}
	
}
