/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

public abstract class AbstractCropOperation<T extends IOperationModel, D extends OperationData> extends AbstractOperation<T, D> {
	/**
	 * Take the user's selected range and determine if axis or raw values should be used.
	 * Thus, set the indices to either the the raw values, shape of data or axis indices.
	 * @param userCropRange
	 * @param dataDimShape
	 * @param theAxis
	 * @return axisCropIndices
	 */
	protected static int[] axisCropIndexConverter(Double[] userCropRange, int dataDimShape, ILazyDataset theAxis) {
		int[] axisCropIndices = new int[2];
		
		if (theAxis == null) {
			//We have no axis metadata - use the user values
			axisCropIndices[0] = userCropRange[0] == null ? 0 : (int)userCropRange[0].doubleValue();
			axisCropIndices[1] = userCropRange[1] == null ? dataDimShape :(int)userCropRange[1].doubleValue();
		} else {
			//If one or other crop directions is not given, set index to 0/shape of data
			//Otherwise get the index from the axis
			axisCropIndices[0] = userCropRange[0] == null ? 0 : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, userCropRange[0]);
			axisCropIndices[1] = userCropRange[1] == null ? dataDimShape : DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, userCropRange[1]);
		}
		
		return axisCropIndices;
	}
	
	protected OperationData cropOperation(IDataset input, int cropRank, Double[][] userVals){
		//Return: array of arrays [[min/max][dimension]]
		int[][] indices = new int[2][cropRank];
		
		//Get the axes and also the shape of the data
		int[] dataShape = input.getShape();
		ILazyDataset[] axes = getFirstAxes(input);
		for (int dim = 0; dim < cropRank; dim++) {
			//If no axes come back, use the raw user values
			if ((axes == null) || (axes[0] == null))  {
				indices[0][dim] = userVals[dim][0] == null ? 0 : (int)userVals[dim][0].doubleValue();
				indices[1][dim] = userVals[dim][1] == null ? dataShape[dim] : (int)userVals[dim][1].doubleValue();
			}else {
			//We do have axes, so get the indices of the user values
				indices[0][dim] = userVals[dim][0] == null ? 0 :  DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) axes[dim], userVals[dim][0]);
				indices[1][dim] = userVals[dim][1] == null ? dataShape[dim] :  DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) axes[dim], userVals[dim][1]);
			}
		}
		return new OperationData(input.getSlice(indices[0], indices[1], null));
	}
}
