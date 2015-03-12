/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.externaldata;


import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

public class SubtractDataOperation extends SubtractCurrentDataOperation<ExternalDataSelectedFramesModel> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractDataOperation";
	}
	
	protected ILazyDataset getLazyDataset(SliceFromSeriesMetadata ssm){
		return ProcessingUtils.getLazyDataset(this, ((ExternalDataSelectedFramesModel)model).getFilePath(), ((ExternalDataSelectedFramesModel)model).getDatasetName());
	}

	protected boolean subtractOneToOneIfMatch(){
		return true;
	}

	

	


}
