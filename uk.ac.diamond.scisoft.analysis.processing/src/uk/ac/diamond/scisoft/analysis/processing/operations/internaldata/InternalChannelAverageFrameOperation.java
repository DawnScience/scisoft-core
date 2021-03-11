/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */


package uk.ac.diamond.scisoft.analysis.processing.operations.internaldata;


import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;

import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.ExternalChannelAverageFrameModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.externaldata.ExternalChannelAverageFrameOperation;


// @author: Tim Snow (tim.snow@diamond.ac.uk)


public class InternalChannelAverageFrameOperation extends AbstractOperation<InternalChannelAverageFrameModel, OperationData> {


	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.internaldata.InternalChannelAverageFrameOperation";
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
		// Let's be sneaky and *actually* use the external dataset version of this processing step
		ExternalChannelAverageFrameModel subModel = new ExternalChannelAverageFrameModel();
		ExternalChannelAverageFrameOperation subOperation = new ExternalChannelAverageFrameOperation();
		
		subModel.setAveragingDirection(model.getAveragingDirection());
		subModel.setChannelIndex(model.getChannelIndex());
		subModel.setDatasetName(model.getDatasetName());
		subModel.setFilePath(inputDataset.getFirstMetadata(SliceFromSeriesMetadata.class).getFilePath());
		subModel.setMathematicalOperator(model.getMathematicalOperator());
		subModel.setScaling(model.getScaling());
		subOperation.setModel(subModel);

		return subOperation.process(inputDataset, monitor);
	}
}
