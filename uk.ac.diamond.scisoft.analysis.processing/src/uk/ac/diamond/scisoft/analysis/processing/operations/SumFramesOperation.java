/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

public class SumFramesOperation extends AbstractOperation<EmptyModel, OperationData> {

	private Dataset sum;
	private int count;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.SumFramesOperation";
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
	public void init(){
		sum = null;
		count = 0;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (sum == null) {
			sum = DatasetUtils.convertToDataset(input.getSlice());
		} else {
			sum.iadd(input);
		}
		
		count++;
		
		if (count == ssm.getTotalSlices()) {
			IDataset out = sum.getSliceView();
			copyMetadata(input, out);
			out.clearMetadata(SliceFromSeriesMetadata.class);
			sum = null;
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
