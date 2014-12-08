/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing;

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
