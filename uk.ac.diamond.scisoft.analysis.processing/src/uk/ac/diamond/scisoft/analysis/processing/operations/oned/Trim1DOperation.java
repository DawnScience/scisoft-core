/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;


// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;


// imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.MetadataFactory;


// This plugin is designed to trim out sections of data from within the dataset
// @ author: Tim Snow
//
public class Trim1DOperation extends AbstractOperation<Trim1DModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.Trim1DOperation";
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
	public OperationData process(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// Get the data as a double dataset
		IDataset someData = inputDataset.getSliceView().squeeze();

		// Get the axis and other metadata from the input dataset
		ILazyDataset[] axes = getFirstAxes(inputDataset);
		SliceFromSeriesMetadata inputMetadata = getSliceSeriesMetadata(someData);
		IDataset inputError = inputDataset.getError();

		// Work out the size of the trim required
		double startTrim = model.getMin();
		double endTrim = model.getMax();
		boolean preserveLength = model.getPreserveDataLength();
		
		// Set up some placeholders for the output
		IDataset inputAxis = null;
		IDataset outputAxis = null;
		IDataset outputError = null;
		IDataset outputDataset = null;
		
		// Find out the size of the dataset to start with
		int dataLength = inputDataset.getSize();

		// Try to assign the input axis data, if there isn't any we'll create a linear space of values
		try {
			inputAxis = axes[0].getSlice((Slice) null);
		} catch (DatasetException noAxes) {
			inputAxis = DatasetFactory.createLinearSpace(0, dataLength, dataLength, Dataset.INT);
		}
		
		// Find the start and end array indices
		int startIndex = Maths.abs(Maths.subtract(inputAxis, startTrim)).argMin();
		int endIndex = Maths.abs(Maths.subtract(inputAxis, endTrim)).argMin();
		
		// First, if we wish to preserve the length
		if (preserveLength == true) {
			// We clone the dataset and axis values
			outputDataset = inputDataset.clone();
			outputAxis = inputAxis.clone();
			outputError = inputError.clone();
			for (int loopIter = startIndex; loopIter < endIndex; loopIter ++) {
				// And fill the relevant elements with zero
				outputDataset.set(Double.NaN, loopIter);

			}
		} // However if we are removing a section of the dataset
		else {
			// We work out it's new length and create the shorter datasets
			int newDataLength = dataLength - (endIndex - startIndex);
			outputDataset = DatasetFactory.zeros(newDataLength);
			outputAxis = DatasetFactory.zeros(newDataLength);
			outputError = DatasetFactory.zeros(newDataLength);
			
			// Then First part of the data and axis values
			for (int loopIter = 0; loopIter < startIndex; loopIter ++) {
				outputDataset.set(inputDataset.getDouble(loopIter), loopIter);
				outputAxis.set(inputAxis.getDouble(loopIter), loopIter);
				outputError.set(inputError.getDouble(loopIter), loopIter);
			}
			
			// followed by the section after the trim
			for (int loopIter = endIndex; loopIter < dataLength; loopIter ++) {
				int newIndex = (loopIter - endIndex) + startIndex;
				outputDataset.set(inputDataset.getDouble(loopIter), newIndex);
				outputAxis.set(inputAxis.getDouble(loopIter), newIndex);
				outputError.set(inputError.getDouble(loopIter), newIndex);
			}		
		}

		// Finally we enter the input metadata and axis values back into the operation data
		// Starting by creating a new home for the AxesMetadata
		AxesMetadata axisValues = null;
		
		try {
			// Creating the metadata class and setting it with the axis values
			axisValues = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			axisValues.setAxis(0, outputAxis);			
		} catch (MetadataException e) {
			// Raising an error if required
			System.out.println("We have a problem");
		}
		
		// Then sticking back in all the previous data
		outputDataset.setMetadata(inputMetadata);
		outputDataset.setMetadata(axisValues);
		outputDataset.setError(outputError);
		
		// Create the return variable
		OperationData toReturn = new OperationData();
		
		// Fill it
		toReturn.setData(outputDataset);

		// And then return it		
		return toReturn;	
	}	
}