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
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.RunningAverage;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

public class AverageSectionsOfFastestOperation extends AbstractOperation<AverageSectionsOfFastestModel, OperationData> {

	private RunningAverage average;
	private int fastestDimension = -1;
	int currentInAverage = 0;
	int currentStart = 0;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.AverageSectionsOfFastestOperation";
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
		currentInAverage = 0;
		currentStart = 0;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (fastestDimension < 0) fastestDimension = calculateFastestDimension(ssm.getDataDimensions(),ssm.getSubSampledShape());
		if (fastestDimension < 0) throw new OperationException(this, "Cannot average this data, it has no, non-data dimensions!");
		
		int outs = ssm.getSliceInfo().getSubSampledShape()[fastestDimension];
		
		int start = model.getStart() == null ? 0 : model.getStart();
		int stop = model.getStop() == null ? outs : model.getStop();
		int number = model.getNumber() == null ? outs : model.getNumber();
		
		if ((stop - start) % number != 0) {
			int n = (stop - start) / number;
			stop = start +  n*number;
		}
		
		int dataStart = ssm.getSliceInOutput()[fastestDimension].getStart();
		
		int size = ssm.getSubSampledShape()[fastestDimension];
		
		int endSize = (size - stop) + start + ((stop-start)/number);
		 
		
		if (average == null) {
			
			if (dataStart < start || dataStart >= stop) {
				SliceFromSeriesMetadata ussm = ssm.clone();
				ussm.getSliceInfo().reducedDimension(fastestDimension, endSize, currentStart);
				IDataset out = input.getSliceView();
				out.clearMetadata(SliceFromSeriesMetadata.class);
				out.setMetadata(ussm);
				currentStart++;
				return new OperationData(out);

			}
			
			average = new RunningAverage(input);
			currentInAverage++;

		} else {
			average.update(input);
			currentInAverage++;
		}
		
		if (currentInAverage == number) {
			SliceFromSeriesMetadata ussm = ssm.clone();
			ussm.getSliceInfo().reducedDimension(fastestDimension, endSize, currentStart);
			currentInAverage = 0;
			currentStart++;
			Dataset out = average.getCurrentAverage();
			average = null;
			out.clearMetadata(SliceFromSeriesMetadata.class);
			out.setMetadata(ussm);
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
}
