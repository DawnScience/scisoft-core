/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


 package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;

 
// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;


// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;

// A method to calculate the absolute scatter
// From: Small-angle scattering of x-rays, A. Guinier & G. Fournet, Wiley & Sons, London, 1955 pp 75-81.
public class InvariantOperation extends AbstractOperation<EmptyModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.Invariant";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationData process(IDataset originalDataset, IMonitor monitor) throws OperationException {
		// Remove a dimension of the dataset and get the axes attached to it too
		IDataset dataDataset = originalDataset.squeeze();
		IDataset axesDataset = (IDataset) dataDataset.getFirstMetadata(AxesMetadata.class).getAxes()[0];
		
		// Get the length of the dataset
		int dataLength = dataDataset.getSize();
		
		// We're also going to need a home for the result
		//IDataset outputDataset = originalDataset.clone();
		double invariantValue = 0.00;
		
		// We're going to need an axis, so if one doesn't already exist, let's create one
		if (axesDataset == null) {
			axesDataset = DatasetFactory.createLinearSpace(0, dataLength, dataLength, Dataset.FLOAT);
		}
		
		for (int loopIter = 0; loopIter < dataLength; loopIter ++) {
			invariantValue += Math.pow(axesDataset.getDouble(loopIter), 2) * dataDataset.getDouble(loopIter);
		}

		// Now we can create a home for the invariant
		Dataset invariantValueDataset = DatasetFactory.zeros(1);

		// Give it a name
		invariantValueDataset.setName("Invariant Value");
		// and stick in the required value
		invariantValueDataset.set(invariantValue, 0);

		// Now create a dataset to return
		OperationData toReturn = new OperationData();
		
		// Fill it with the required values
		toReturn.setData(originalDataset);
		toReturn.setAuxData(invariantValueDataset);

		// and return it!
		return toReturn;
	}
}
