/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.internaldata.MultiplyInternalMatrixOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

@Atomic
public class MultiplyExternalMatrixOperation<T extends ExternalDataModel> extends MultiplyInternalMatrixOperation<T> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.MultiplyExternalMatrixOperation";
	}

	@Override
	protected IDataset getDatasetFromModel(IDataset input) {
		return ProcessingUtils.getDataset(this, model.getFilePath(), model.getDatasetName());
	}
	
}
