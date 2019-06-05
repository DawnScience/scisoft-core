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
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.LocalServiceManager;
import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

@Atomic
public class SubtractWithProcessing extends FrameMathsOperation<SubtractWithProcessingModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractWithProcessing";
	}

	@Override
	protected Dataset getData(IDataset ds) {
		
		Dataset pFrame = data;
		if (pFrame == null) {
			synchronized(this) {
				pFrame = data;
				if (pFrame == null) {
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
							if (datasetName == null || datasetName.isEmpty()) {
								throw new OperationException(this, "Dataset name not set!");
							}
							throw new OperationException(this, "File does not contain: " + datasetName);
						}
					} catch (Exception e) {
						if (e instanceof OperationException) {
							throw (OperationException)e;
						} 
						
						throw new OperationException(this, "Can't load data!", e);
					}

					Dataset processed = om.process(filePath,
							datasetName,
							ds.getFirstMetadata(SliceFromSeriesMetadata.class),model.getStartFrame(),
							model.getEndFrame());
					pFrame = data =  processed.imultiply(model.getScaling());
				}
			}
		}
		
		return pFrame;
	}

	@Override
	protected Dataset performOperation(Dataset input, Dataset other) {
		if (other == null) return input.getSliceView();
		return ErrorPropagationUtils.subtractWithUncertainty(DatasetUtils.convertToDataset(input), other);
	}
}
