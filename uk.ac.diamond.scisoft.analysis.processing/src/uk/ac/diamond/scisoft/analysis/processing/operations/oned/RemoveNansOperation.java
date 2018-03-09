/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.oned;


import javax.print.attribute.standard.OutputDeviceAssigned;

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
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.DatasetFactory;

// Importing the logger!
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@author Tim Snow


public class RemoveNansOperation extends AbstractOperation<EmptyModel, OperationData> {
	
	
	// First, set up a logger
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RemoveNansOperation.class);
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.RemoveNansOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) {
		// First a few things need to be declared
		Dataset inputDataset = DatasetUtils.convertToDataset(input);
		Dataset nanBooleans = DatasetFactory.zeros(BooleanDataset.class, inputDataset.getSize());
		IndexIterator datasetIterator = inputDataset.getIterator();
		AxesMetadata xAxisMetadata = null;
		Dataset inputAxis = null;
		int nanCounter = 0;
		
		// Now let's get the primary axis, which will also need a judicious trimming...
		try {
			xAxisMetadata = input.getFirstMetadata(AxesMetadata.class);
			inputAxis = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		// Now we can populate the boolean dataset
		while (datasetIterator.hasNext()) {
			int index = datasetIterator.index;
			
			if (Double.isNaN(inputDataset.getElementDoubleAbs(index))) {
				nanBooleans.set(true, index);
				nanCounter ++;
			}
		}
		
		// Now let's work out how long the output dataset needs to be and set up some bits
		Dataset outputDataset = DatasetFactory.zeros(inputDataset.getClass(), inputDataset.getSize() - nanCounter);
		Dataset outputErrors = DatasetFactory.zeros(inputDataset.getClass(), inputDataset.getSize() - nanCounter);
		Dataset outputAxis = DatasetFactory.zeros(inputAxis.getClass(), inputDataset.getSize() - nanCounter);
		datasetIterator = nanBooleans.getIterator();
		int outputIndex = 0;
		
		// Now we can populate the output dataset
		while (datasetIterator.hasNext()) {
			int index = datasetIterator.index;
			
			if (!nanBooleans.getBoolean(index)) {
				outputDataset.set(inputDataset.getObject(index), outputIndex);
				outputErrors.set(outputDataset.getError(index), outputIndex);
				
				outputAxis.set(inputAxis.getObject(index), outputIndex);
				outputIndex ++;
			}
		}
		
		// Copy all the metadata back alongside our new, shorter, primary axis
		copyMetadata(input, outputDataset);
		copyMetadata(inputAxis, outputAxis);
		outputAxis.setName(inputAxis.getName());
		
		xAxisMetadata.setAxis(0, outputAxis);
		outputDataset.setMetadata(xAxisMetadata);
		outputDataset.setErrors(outputErrors);
		
		// and return it!
		return new OperationData(outputDataset);
	}
}
