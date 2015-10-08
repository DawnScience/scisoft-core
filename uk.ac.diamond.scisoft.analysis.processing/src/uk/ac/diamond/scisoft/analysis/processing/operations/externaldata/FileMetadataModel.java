/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;

public class FileMetadataModel extends AbstractOperationModel {

	private String metadataName;

	public String getMetadataName() {
		return metadataName;
	}

	public void setMetadataName(String metadataName) {
		firePropertyChange("metadataName", this.metadataName, this.metadataName = metadataName);
	}
	
	
}
