/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

public class SubtractExternalFrameOperation<T extends ExternalDataSelectedFramesModel> extends FrameMathsOperation<ExternalDataSelectedFramesModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractExternalFrameOperation";
	}
	
	@Override
	protected Dataset getData(IDataset ds) {
		String path = ((ExternalDataSelectedFramesModel)model).getFilePath();
		String dsName = ((ExternalDataSelectedFramesModel)model).getDatasetName();
		
		Dataset d = null;
		
		if (model.getStartFrame() == null && model.getEndFrame() == null){
			d = DataUtils.getExternalFrameMatching(ds, model.getStartFrame(),model.getEndFrame(), path,dsName,this);
		}
		
		if (d != null) return d;
		
		Dataset lFrame = data;
		if (lFrame == null) {
			synchronized(this) {
				lFrame = data;
				if (lFrame == null) {
					lFrame = data = DataUtils.getExternalFrameAverage(ds, model.getStartFrame(),
							model.getEndFrame(), path,dsName,this);
				}
			}
		}
		
		return lFrame;
	}

	@Override
	protected Dataset performOperation(Dataset input, Dataset other) {
		return ErrorPropagationUtils.subtractWithUncertainty(DatasetUtils.convertToDataset(input), other);
	}
}
