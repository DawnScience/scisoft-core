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
// A processing operation to pull through additional axes
//
public class AxisPullthroughOperation extends AbstractOperationBase<AxisPullthroughModel, OperationData> {
	
	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.AxisPullthroughOperation";
	}
	
	
	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}


	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	@Override
	public OperationData execute(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// Get the appropriate dataset from the file
		IDataset axisDataset;
		// If it exists that is...
		try {
			axisDataset = LoaderFactory.getDataSet(getSliceSeriesMetadata(inputDataset).getSourceInfo().getFilePath(), model.getAxisPath(), null);
		} catch (Exception e) {
			throw new OperationException(this, "The axis: " + model.getAxisPath() + " is not present in this file!");
		}
		// And name it
		axisDataset.setName(model.getAxisPath());

		// Make a copy of the dataset and it's metadata for output
		IDataset outputDataset = inputDataset.clone();
		copyMetadata(inputDataset, outputDataset);
		
		// Retrieve the current axes from the dataset passed to us
		AxesMetadata axesMetadata;
		// If any exist...
		try {
			axesMetadata = outputDataset.getMetadata(AxesMetadata.class).get(0);
		} catch (Exception e) {
			throw new OperationException(this, "Cannot find appropriate Axes in the data file");
		}
		
		// Work out the shape and dimensionality of the axis/axes 
		int[] datasetShape = axisDataset.getShape();
		int datasetDimensionality = datasetShape.length;

		// Add them in an appropriate matter, doing some double checking where necessary 
		if (datasetDimensionality == 1 && datasetDimensionality != 0){
			axesMetadata.addAxis(0, axisDataset);
		} else {
			try {
				axesMetadata.addAxis(0, axisDataset, datasetShape);
			} catch (Exception datasetShapeError) {
				throw new OperationException(this, "The axis to pull through is of a different dimensionality and/or shape");
			}
		}

		// Add this metadata to the output dataset
		outputDataset.setMetadata(axesMetadata);
		// Then return it!
		return new OperationData(outputDataset);
	}
}
