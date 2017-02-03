/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.filter;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;

public class FilterXYbyRatioOperation extends AbstractOperation<FilterXYbyRatioModel, OperationData> {

	private int nextIndex = 0;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.filter.FilterXYbyRatioOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	@Override
	public void init(){
		nextIndex = 0;
	}
	

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] firstAxes = getFirstAxes(input);
		IDataset axis = null;
		
		if (firstAxes == null || firstAxes[0] == null) {
			axis = DatasetFactory.createRange(input.getSize(), Dataset.INT32);
		} else {
			try {
				axis = firstAxes[0].getSlice();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		
		double num = getIntegratedValue(axis, input, model.getNumeratorRange());
		double denom = getIntegratedValue(axis, input, model.getDenominatorRange());
		
		double ratio = num/denom;
		
		boolean above = ratio >= model.getThreshold();
		
		if ((above && !model.isFilterAboveThreshold()) || (!above && model.isFilterAboveThreshold())) {
			
			SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);
			Dataset index = DatasetFactory.createFromObject(ssm.getSliceInfo().getSliceNumber());
			
			if (ssm.getSliceInfo().getSliceNumber() != nextIndex) {
				ssm.getSliceInfo().reducedDimension(ssm.getSliceInfo().calculateFastestDimension(), nextIndex+1, nextIndex);
			} 
			
			
			index.setName("index");
			
			nextIndex++;
			
			return new OperationData(input,index);
		}
		
		return null;
		
	}
	
	private double getIntegratedValue(IDataset axis, IDataset input, double[] integrationRange){
		
		int[] indexes = new int[2];
		if (integrationRange != null) {
			integrationRange = integrationRange.clone();
			Arrays.sort(integrationRange);
			indexes = new int[2];
			indexes[0]= ROISliceUtils.findPositionOfClosestValueInAxis(axis, integrationRange[0]);
			indexes[1]= ROISliceUtils.findPositionOfClosestValueInAxis(axis, integrationRange[1]);
			Arrays.sort(indexes);
		} else {
			indexes[0] = 0;
			indexes[1] = axis.getSize()-1;
		}
		
		
		double out = 0;
		
		for (int i = indexes[0]; i < indexes[1]; i++) {
			
			double val = (input.getDouble(i)+input.getDouble(i+1))/2;
			double tmp = Math.abs((axis.getDouble(i+1)-axis.getDouble(i)));
			val = val * tmp;
			
			out += val;
			
		}
		
		return out;
	}

}
