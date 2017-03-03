/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations;


// Import org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;

// Import org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;

//Import uk.ac.diamond.scisoft
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;


//
// @ Author: Tim Snow
//
// A processing operation to pull through additional data into a results file
//
public class DataPullthroughOperation extends AbstractOperationBase<DataPullthroughModel, OperationData> {
	
	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.DataPullthroughOperation";
	}
	
	
	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationData execute(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// Get the appropriate dataset from the file
		IDataset auxData;
		// If it exists that is...
		try {
			auxData = LoaderFactory.getDataSet(getSliceSeriesMetadata(inputDataset).getSourceInfo().getFilePath(), model.getDatasetPath(), null);
		} catch (Exception e) {
			throw new OperationException(this, "The axis: " + model.getDatasetPath() + " is not present in this file!");
		}

		// Now let's get ready to return the dataset
		OperationData toReturn = new OperationData(inputDataset);
		// With the additional dataset from the source
		toReturn.setAuxData(auxData);
		// Then return it!
		return toReturn;
	}
}
