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
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

public class SubtractInternalStringExternalData extends FrameMathsOperation<InternalStringExternalDataModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractInternalStringExternalData";
	}

	@Override
	protected Dataset getData(IDataset ds) {
		OperationMetadata om = ds.getFirstMetadata(OperationMetadata.class);
		SliceFromSeriesMetadata ssm = ds.getFirstMetadata(SliceFromSeriesMetadata.class);
		String datasetName = ssm.getDatasetName();
		String filePath = null;
		
		IDataset datasetContainingFilePath = ProcessingUtils.getDataset(this, ssm.getFilePath(), model.getFilePathDataset());
		
		if (datasetContainingFilePath != null) {
			filePath = DatasetUtils.convertToDataset(datasetContainingFilePath).getString();
		}
				
		if (filePath == null) {
			throw new OperationException(this, "Can't load data!");
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
		return ErrorPropagationUtils.subtractWithUncertainty(DatasetUtils.convertToDataset(input), other);
	}
}
