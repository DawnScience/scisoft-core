/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

public class Crop2DOperation extends AbstractOperation<Crop2DModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.Crop2DOperation";
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}
	
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) {
		int[] minIndices = new int[2];
		int[] maxIndices = new int[2];
		
		Double[] minVal = new Double[]{model.getxMin(), model.getyMin()};
		Double[] maxVal = new Double[]{model.getxMax(), model.getyMax()};
		
		//Get axes data from dataset and see if X & Y axes exist
		ILazyDataset[] axes = getFirstAxes(input);
		
		if ((axes == null) || ((axes[0] == null ) && (axes[1] == null))) { //Check whether axes == null could occur
			// There are no axis metadata. Given values are crop values.
			minIndices[0] = minVal[0] == null ? 0 : (int) minVal[0].doubleValue();
			maxIndices[0] = maxVal[0] == null ? 0 : (int) maxVal[0].doubleValue();
			minIndices[1] = minVal[1] == null ? 0 : (int) minVal[1].doubleValue();
			maxIndices[1] = maxVal[1] == null ? 0 : (int) maxVal[1].doubleValue();
		} else if (axes[0] == null) {
			//If there is no X-axis metadata, given values are crop values...
			minIndices[0] = (int) minVal[0].doubleValue();
			maxIndices[0] = (int) maxVal[0].doubleValue();
			
			//... and get Dataset indices of non-null crop values for the Y-axis 
			ILazyDataset theYAxis = axes[1];
			minIndices[1] = minVal[1] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theYAxis, minVal[1]);
			maxIndices[1] = maxVal[1] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theYAxis, maxVal[1]);
		} else if (axes[1] == null) {
			//Get Dataset indices of non-null crop values for X-axis...
			ILazyDataset theXAxis = axes[0];
			minIndices[0] = minVal[0] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, minVal[0]);
			maxIndices[0] = maxVal[0] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, maxVal[0]);
			
			//... and get set given value to Y-crop values
			minIndices[1] = (int) minVal[0].doubleValue();
			maxIndices[1] = (int) maxVal[0].doubleValue();
		} else {
			//Axes are non-null...
			ILazyDataset theXAxis = axes[0];
			ILazyDataset theYAxis = axes[1];
			
			// ...get indices of non-null crop values for X- and Y-axes
			minIndices[0] = minVal[0] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, minVal[0]);
			maxIndices[0] = maxVal[0] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, maxVal[0]);
			minIndices[1] = minVal[1] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theYAxis, minVal[1]);
			maxIndices[1] = maxVal[1] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theYAxis, maxVal[1]);
		}
		return new OperationData(input.getSlice(minIndices, maxIndices, null));
		
	}
}

