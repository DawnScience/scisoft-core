/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;


import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.InterpolatorUtils;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

public class SubtractCalibratedWithProcessing extends FrameMathsOperation<SubtractWithProcessingModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractCalibratedWithProcessing";
	}
	
	// Now, how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}
	
	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	@Override
	protected Dataset getData(IDataset ds) {
		OperationMetadata om = ds.getFirstMetadata(OperationMetadata.class);
		String filePath = ((SubtractWithProcessingModel)model).getFilePath();
		String datasetName = null;
		
		if (model.getUseSameDataset() == true) {
			datasetName = getSliceSeriesMetadata(ds).getSourceInfo().getDatasetName();
		} else {
			datasetName = model.getDatasetName();
		}
		
		if (filePath == null) {
			SliceFromSeriesMetadata ssm = ds.getFirstMetadata(SliceFromSeriesMetadata.class);
			filePath = ssm.getFilePath();
		}
		
		try {
			IDataHolder dh = LocalServiceManager.getLoaderService().getData(filePath, null);
			if (!dh.contains(datasetName)){
				return null;
			}
		} catch (Exception e) {
			throw new OperationException(this, "Can't load data!");
		}
		
		Dataset processed = om.process(filePath,
									 datasetName,
									 ds.getFirstMetadata(SliceFromSeriesMetadata.class),model.getStartFrame(),
									 model.getEndFrame());
		return processed.imultiply(model.getScaling());
	}

	@Override
	protected Dataset performOperation(Dataset input, Dataset other) {
		if (other == null) return input.getSliceView();
		
		AxesMetadata xAxisMetadata;
		Dataset inputX;
		Dataset otherX;
		
		// Just in case we don't have an x-axis (as we could do with an x axis)
		try {
			xAxisMetadata = input.getFirstMetadata(AxesMetadata.class);
			inputX = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
			
			xAxisMetadata = other.getFirstMetadata(AxesMetadata.class);
			otherX = DatasetUtils.convertToDataset(xAxisMetadata.getAxis(0)[0].getSlice());
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		
		other = InterpolatorUtils.remap1D(other, otherX, inputX);
		
		return ErrorPropagationUtils.subtractWithUncertainty(input, other);
	}
}
