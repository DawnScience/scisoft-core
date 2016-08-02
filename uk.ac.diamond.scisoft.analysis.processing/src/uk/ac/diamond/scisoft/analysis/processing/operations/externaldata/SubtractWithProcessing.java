/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;


import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.metadata.OperationMetadata;
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

public class SubtractWithProcessing extends FrameMathsOperation<ExternalDataSelectedFramesModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractWithProcessing";
	}

	@Override
	protected Dataset getData(IDataset ds) {
		OperationMetadata om = ds.getFirstMetadata(OperationMetadata.class);
		Dataset processed = om.process(((ExternalDataSelectedFramesModel)model).getFilePath(),
									 ((ExternalDataSelectedFramesModel)model).getDatasetName(),
									 ds.getFirstMetadata(SliceFromSeriesMetadata.class),model.getStartFrame(),
									 model.getEndFrame());
		return processed.imultiply(model.getScaling());
	}

	@Override
	protected Dataset performOperation(Dataset input, Dataset other) {
		return ErrorPropagationUtils.subtractWithUncertainty(DatasetUtils.convertToDataset(input), other);
	}
}
