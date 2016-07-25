/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;

import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

@Atomic
public class SubtractCurrentDataOperation extends FrameMathsOperation<SelectedFramesModel> {

	protected Dataset subtrahend;
	protected PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.SubtractCurrentDataOperation";
	}

	@Override
	protected Dataset getData(IDataset ds) {
		Dataset lFrame = data;
		if (lFrame == null) {
			synchronized(this) {
				lFrame = data;
				if (lFrame == null) {
					lFrame = data = DataUtils.getInternalFrameAverage(ds, model.getStartFrame(), model.getEndFrame());
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
