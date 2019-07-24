/*
 * Copyright (c) 2012, 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;


@Atomic
public class InterallySaveMaskOperation extends AbstractOperation<EmptyModel, OperationData> {

	// Let's give this process an ID tag
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.mask.InterallySaveMaskOperation";
	}


	// Before we start, let's make sure we know how many dimensions of data are going in...
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}


	// Now let's define the processing step
	public OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		// Try to get ahold of the mask first
		ILazyDataset lazyMask = getFirstMask(input);
		IDataset mask = null;
		
		if (lazyMask != null) {
			try {
				mask = lazyMask.getSlice().squeeze();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		
		// Finally, we can create a new OperationData object with the correct datasets
		OperationData operationDataset = new OperationData();
		operationDataset.setData(input);
		operationDataset.setAuxData(mask);
		return operationDataset;
	}
}
