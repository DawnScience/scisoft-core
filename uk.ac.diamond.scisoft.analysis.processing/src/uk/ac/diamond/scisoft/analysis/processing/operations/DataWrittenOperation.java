/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.LongDataset;
import org.eclipse.january.dataset.Slice;

public class DataWrittenOperation extends AbstractOperation<DataWrittenModel, OperationData> {

	private int count = 0;
	private int fastestDimension = -1;
	private int outFrame = 0;
	private int totalCount = 1;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations";
	}
	
	@Override
	public void init(){
		count = 0;
		fastestDimension = -1;
		totalCount = 1;
		
	}

	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		if (model.getnFramesOverwrite() < 0) {
			Dataset ones = DatasetFactory.ones(new int[]{1}, Dataset.INT64);
			ones.setName("key");
			return new OperationData(input, new Serializable[]{ones});
		} else {
			SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
			SliceFromSeriesMetadata ussm = ssm.clone();
			System.out.println(Slice.createString(ussm.getSliceInfo().getSliceInOutput()));
			if (fastestDimension < 0) {
				fastestDimension = ussm.getSliceInfo().calculateFastestDimension();
			}
			
			ussm.getSliceInfo().reducedDimension(fastestDimension, outFrame+1, outFrame);
			
			if (count <= model.getnFramesOverwrite()-2) {
				count++;
			} else {
				outFrame++;
				count = 0;
			}
//			ussm.getSliceInfo().reducedDimension(fastestDimension, endSize, currentStart);
			
			System.out.println(Slice.createString(ussm.getSliceInfo().getSliceInOutput()));
			IDataset out = input.getSliceView();
			out.clearMetadata(SliceFromSeriesMetadata.class);
			out.setMetadata(ussm);
			Dataset ones = DatasetFactory.createFromObject(LongDataset.class,new long[]{totalCount++});
			ones.setName("key");
			return new OperationData(out,ones);

			
			
			
			
			
		}
		
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
	public String getName(){
		return "DataWritten";
	}
}
