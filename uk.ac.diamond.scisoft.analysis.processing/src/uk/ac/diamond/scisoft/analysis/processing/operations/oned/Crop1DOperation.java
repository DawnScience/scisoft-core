/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

public class Crop1DOperation extends AbstractOperation<Crop1DModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.Crop1DOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		int[] xIndices = new int[2];
		Double[] userXVals = new Double[2];
		int[] minIndices = new int[1];
		int[] maxIndices = new int[1];
		
		userXVals[0] = model.getMin();
		userXVals[1] = model.getMax();
		
		//Get the x-axis (if no axes come back, we'll use the user values)
		ILazyDataset[] axes = getFirstAxes(input);
		if (axes == null) {
			xIndices[0] = (int)userXVals[0].doubleValue();
			xIndices[1] = (int)userXVals[1].doubleValue();
		} else {
			int[] dataShape = input.getShape();
			xIndices = axisCropIndexConverter(userXVals, dataShape[0], axes[0]);
		}
		//Copy axes indices to the indices for slicing
		minIndices[0] = xIndices[0];
		maxIndices[0] = xIndices[1];
		
		return new OperationData(input.getSlice(minIndices, maxIndices, null));
	}
	
	protected int[] axisCropIndexConverter(Double[] userCropRange, int dataDimShape, ILazyDataset theAxis) {
		int[] axisCropIndices = new int[2];
		
		if (theAxis == null) {
			//We have no axis metadata - use the user values
			axisCropIndices[0] = (int)userCropRange[0].doubleValue();
			axisCropIndices[1] = (int)userCropRange[1].doubleValue();
		} else {
			//If one or other crop directions is not given, set index to 0/shape of data
			//Otherwise get the index from the axis
			axisCropIndices[0] = userCropRange[0] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, userCropRange[0]);
			axisCropIndices[1] = userCropRange[1] == null ? dataDimShape : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, userCropRange[1]);
		}
		
		return axisCropIndices;
	}
}
