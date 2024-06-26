/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;


import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.SliceND;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;


import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;


// @author: Tim Snow (tim.snow@diamond.ac.uk)


public class ExternalChannelAverageFrameOperation extends AbstractOperation<ExternalChannelAverageFrameModel, OperationData> {


	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.ExternalChannelAverageFrameOperation";
	}


	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}


	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}


	@Override
	public OperationData process(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// Checks to start with
		SliceFromSeriesMetadata sliceMetadata = inputDataset.getFirstMetadata(SliceFromSeriesMetadata.class);
		ILazyDataset lazyOperatorDataset = ProcessingUtils.getLazyDataset(this, model.getFilePath(), model.getDatasetName());
		Dataset operatorDataset;
		
		try {
			operatorDataset = DatasetUtils.convertToDataset(lazyOperatorDataset.getSlice(sliceMetadata.getSliceFromInput())).squeeze();
		} catch (DatasetException e) {
			throw new OperationException(this, "The dataset requested returned no data");
		}

		int[] frameShape = operatorDataset.getShape();
		
		if (frameShape.length != 2) {
			throw new OperationException(this, "Dataset specified to average is not two dimensional");
		}
		
		// Now to get the values to average over
		int[] sliceStart, sliceStop, sliceStep;
		int arrayIndex = model.getAveragingDirection().getInt();
		
		if (arrayIndex == 0) {
			sliceStart = new int[] {0, model.getChannelIndex()};
			sliceStop = new int[] {frameShape[0], model.getChannelIndex() + 1};
			sliceStep = new int[] {1, 1};
		} else {
			sliceStart = new int[] {model.getChannelIndex(), 0};
			sliceStop = new int[] {model.getChannelIndex() + 1, frameShape[1]};
			sliceStep = new int[] {1, 1};
		}

		IDataset operatorSlice = operatorDataset.getSlice(new SliceND(frameShape, sliceStart, sliceStop, sliceStep));
		Dataset averagingData = DatasetUtils.convertToDataset(operatorSlice);
		averagingData = averagingData.squeezeEnds();
				
		if (averagingData.getSize() == 0) {
			throw new OperationException(this, "Channel specified yielded an empty dataset, is it within range?");
		}
		
		// Then do the averaging and create the average value dataset with its uncertainty attached
		Dataset averageValue = averagingData.mean(0, null);
		double standardErrorOfTheMean = averagingData.stdDeviation() / Math.sqrt(averagingData.getSize());
		averageValue.setErrors(standardErrorOfTheMean);

		// Manipulate the inputDataset
		Dataset outputDataset = DatasetUtils.convertToDataset(inputDataset);
		
		switch (model.getMathematicalOperator()) {
			case ADD:		outputDataset = ErrorPropagationUtils.addWithUncertainty(outputDataset, averageValue);
							break;
			case SUBTRACT:	outputDataset = ErrorPropagationUtils.subtractWithUncertainty(outputDataset, averageValue);
							break;
			case DIVIDE:	outputDataset = ErrorPropagationUtils.divideWithUncertainty(outputDataset, averageValue);
							break;
			case MULTIPLY:	outputDataset = ErrorPropagationUtils.multiplyWithUncertainty(outputDataset, averageValue);
							break;
			default:		throw new OperationException(this, "The mathematical operator selected is not supported.");
		}
		
		// Finally, return the result
		copyMetadata(inputDataset, outputDataset);
		return new OperationData(outputDataset);
	}
}
