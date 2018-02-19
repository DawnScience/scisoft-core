/*-
 * Copyright 2017 Diamond Light Source Ltd.
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
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.InterpolatorUtils;

// Imports from uk.ac.diamond.scisoft.analysis
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

// Imports from org.slf4j
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UsaxsDataTransposerOperation extends AbstractOperation<EmptyModel, OperationData> {
	
	
	// Then set up a logger
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UsaxsDataTransposerOperation.class);
	
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.UsaxsDataTransposerOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		// Get our x-axis for conversion and our y-axis for later...
		Dataset xAxis = null;
		AxesMetadata xAxisMetadata = null;
		Dataset yAxis = DatasetUtils.convertToDataset(input);
		Dataset output = null;
		
		try {
			xAxisMetadata = input.getFirstMetadata(AxesMetadata.class);
			xAxis = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		xAxis = Maths.abs(xAxis);
		int minValueIndex = xAxis.argMin(null);
		int xAxisLength = xAxis.getSize();
		float halfAxisLength = xAxisLength / 2;
		
		if (minValueIndex > halfAxisLength) {
			Slice lowerSegment = new Slice(0, minValueIndex);
			Slice upperSegment = new Slice(minValueIndex + 1, xAxisLength);
			
			output = AverageAroundZero(yAxis.getSlice(upperSegment), yAxis.getSlice(lowerSegment));
			
		} else {			
			Slice lowerSegment = new Slice(0, minValueIndex - 1);
			Slice upperSegment = new Slice(minValueIndex, xAxisLength);
			
			output = AverageAroundZero(yAxis.getSlice(lowerSegment), yAxis.getSlice(upperSegment));
			
		}
		
		// And return
		return new OperationData(output);
	}
	
	
	private Dataset AverageAroundZero(Dataset shorterDataset, Dataset longerDataset) {
		// First let's create someplaceholders for our axes and output datasets
		AxesMetadata xAxisMetadata = null;
		Dataset shorterX = null;
		Dataset longerX = null;
		
		try {
			xAxisMetadata = shorterDataset.getFirstMetadata(AxesMetadata.class);
			shorterX = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
			shorterX = Maths.abs(shorterX);
			DatasetUtils.sort(shorterX, shorterDataset);
			xAxisMetadata.setAxis(0, shorterX);
			shorterDataset.setMetadata(xAxisMetadata);
			
			xAxisMetadata = longerDataset.getFirstMetadata(AxesMetadata.class);
			longerX = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
			longerX = Maths.abs(longerX);
			DatasetUtils.sort(longerX, longerDataset);
			xAxisMetadata.setAxis(0, longerX);
			longerDataset.setMetadata(xAxisMetadata);
			
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		shorterDataset = InterpolatorUtils.remap1D(shorterDataset, shorterX, longerX);
		
		Dataset output = longerDataset.clone();
		IndexIterator datasetIterator = longerDataset.getIterator();
		Dataset datasetTwo = DatasetFactory.createFromObject(new Double(2.0));
		
		while (datasetIterator.hasNext()) {
			int index = datasetIterator.index;
			if (Double.isNaN(shorterDataset.getDouble(index)) != true) {
				output.set(ErrorPropagationUtils.divideWithUncertainty(ErrorPropagationUtils.addWithUncertainty(shorterDataset.getSlice(new Slice(index, index + 1)), longerDataset.getSlice(new Slice(index, index + 1))), datasetTwo), index);
			}
		}
		
		return output;
	}
}
