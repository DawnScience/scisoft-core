/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.operations;

import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ImportXRMCMetadataModel extends AbstractOperationModel {

	@OperationModelField(hint="Path to XRMC input file", label = "XRMC input file path")
	private String xrmcInputFilePath = "";
	
	public String getXrmcInputFilePath() {
		return xrmcInputFilePath;
	}
	public void setXrmcInputFilePath(String path) {
		firePropertyChange("xrmcInputFilePath", this.xrmcInputFilePath, this.xrmcInputFilePath = path);
	}
}
