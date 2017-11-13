/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.internaldata;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.DataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.FrameMathsOperation;

public abstract class AbstractInternalFrameOperation extends FrameMathsOperation<InternalDataSelectedFramesModel> {

	@Override
	public void init() {
		data = null;
	}
	
	@Override
	protected Dataset getData(IDataset ds) {
		String dsName = model.getDatasetName();
		
		Dataset d = null;
		
		if (model.getStartFrame() == null && model.getEndFrame() == null){
			d = DataUtils.getInternalFrameMatching(ds, model.getStartFrame(),model.getEndFrame(), dsName,this);
		}
		
		if (d != null) return d;
		
		Dataset lFrame = data;
		if (lFrame == null) {
			synchronized(this) {
				lFrame = data = DataUtils.getInternalFrameAverage(ds, model.getStartFrame(),
						model.getEndFrame(), dsName, this); 
			}
		}
		
		return lFrame;
	}
}
