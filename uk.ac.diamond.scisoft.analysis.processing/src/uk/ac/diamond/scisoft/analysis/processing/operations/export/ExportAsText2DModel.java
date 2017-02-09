/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.export;

import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;

public class ExportAsText2DModel extends ExportAsText1DModel {

	@OperationModelField(label = "Split Columns", hint = "Split columns into separate text f")
	private boolean splitColumns = false;

	public boolean isSplitColumns() {
		return splitColumns;
	}

	public void setSplitColumns(boolean splitColumns) {
		firePropertyChange("splitColumns", this.splitColumns, this.splitColumns = splitColumns);
	}

}
