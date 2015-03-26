/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.IExportOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.RunningAverage;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

public class AverageFastestOperation extends AbstractOperation<EmptyModel, OperationData> implements IExportOperation {

	private RunningAverage average;
	private int fastestDimension = -1;;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.AverageFastestOperation";
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
		average = null;
		fastestDimension = -1;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (average == null) {
			average = new RunningAverage(input);
			if (fastestDimension < 0) fastestDimension = calculateFastestDimension(ssm.getDataDimensions(),ssm.getSubSampledShape());
			if (fastestDimension < 0) throw new OperationException(this, "Cannot average this data, it has no, non-data dimensions!");
		} else {
			average.update(input);
		}
		
		if (isLastSlice(ssm.getSliceInOutput(), ssm.getSubSampledShape(), fastestDimension)) {
			IDataset out = average.getCurrentAverage();
			copyMetadata(input, out);
			out.clearMetadata(SliceFromSeriesMetadata.class);
			average = null;
			SliceFromSeriesMetadata outsmm = ssm.clone();
			outsmm.reducedDimensionToSingular(fastestDimension);

			out.setMetadata(outsmm);
			
			return new OperationData(out);
		}
		
		return null;
	}
	
	private int calculateFastestDimension(int[] dataDims, int[] shape) {
		int[] dd = dataDims.clone();
		Arrays.sort(dd);
		
		for (int i = shape.length-1; i > -1; i--) {
			int key = Arrays.binarySearch(dd, i);
			if (key < 0) return i;
		}
		
		return -1;	
	}
	
	private boolean isLastSlice(Slice[] outputSlice, int[] outputShape, int dim) {
		return (outputSlice[dim].getStart().intValue() == outputShape[dim]-1);
	}

}
