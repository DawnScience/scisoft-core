/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

public abstract class AbstractFramesOperation extends AbstractOperation<EmptyModel, OperationData> implements IExportOperation {
	private Dataset currentData;
	private int count;
	private IFramesOperation framesOperation;

	public AbstractFramesOperation(IFramesOperation framesOperation) {
		this.framesOperation = framesOperation;
	}
	
	@FunctionalInterface
	public interface IFramesOperation {
		public Dataset execute(Dataset oldDataset, Dataset newDataset);
	}
	
	@Override
	public final OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public final OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	@Override
	public void init(){
		currentData = null;
		count = 0;
	}

	@Override
	protected final OperationData process(IDataset input, IMonitor monitor) {
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (currentData == null) {
			currentData = DatasetUtils.convertToDataset(input.getSlice());
		} else {
			currentData = framesOperation.execute(currentData, DatasetUtils.convertToDataset(input));
		}
		
		count++;
		
		if (count == ssm.getTotalSlices()) {
			IDataset out = currentData.getSliceView();
			copyMetadata(input, out);
			out.clearMetadata(SliceFromSeriesMetadata.class);
			currentData = null;
			SliceFromSeriesMetadata outsmm = ssm.clone();
			for (int i = 0; i < ssm.getParent().getRank(); i++) {
				
				if (!outsmm.isDataDimension(i)) outsmm.reducedDimensionToSingular(i);
				
			}
			out.setMetadata(outsmm);
			
			return new OperationData(out);
		}
		
		return null;
	}
	
}
