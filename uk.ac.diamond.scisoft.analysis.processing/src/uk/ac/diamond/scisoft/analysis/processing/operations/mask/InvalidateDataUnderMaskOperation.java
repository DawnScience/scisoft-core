/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.mask;


import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.metadata.MaskMetadata;


@Atomic
public class InvalidateDataUnderMaskOperation extends AbstractOperation<InvalidateDataUnderMaskModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.mask.InvalidateDataUnderMaskOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		Dataset mask = DatasetUtils.convertToDataset(getFirstMask(input));
		Dataset out = DatasetUtils.convertToDataset(input.getSlice());
		
		if (mask == null) {
			return new OperationData(input);
		}
		
		// Implement the user's choice of invalidation strategy
		switch(model.getInvalidation()) {
			case ZEROS:	out.setByBoolean(0.00, Comparisons.logicalNot(mask));
						break;
						
			case NANS:	if (!out.hasFloatingPointElements()) out = DatasetUtils.cast(input, Dataset.FLOAT64);
						out.setByBoolean(Double.NaN, Comparisons.logicalNot(mask));
						break;
						
			default:	throw new OperationException(this, "Invalid value picked, please pick zero or NaN.");
		}
		
		out.setName("mask_invalidated");
		
		copyMetadata(input, out);
		
		out.clearMetadata(MaskMetadata.class);
		
		return new OperationData(out);
	}
}