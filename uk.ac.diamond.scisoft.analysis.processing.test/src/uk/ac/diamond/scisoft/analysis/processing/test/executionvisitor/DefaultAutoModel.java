/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.executionvisitor;

import org.eclipse.dawnsci.analysis.api.processing.model.FileType;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;

/**
 * Test model that has fields which are automatically set by an operation
 */
public class DefaultAutoModel extends Junk1DModel {

	@OperationModelField(file = FileType.EXISTING_FILE)
	private String file = null;

	private IRectangularROI roiA = null;

	private Double value = null;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public IRectangularROI getRoiA() {
		return roiA;
	}

	public void setRoiA(IRectangularROI roiA) {
		this.roiA = roiA;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
