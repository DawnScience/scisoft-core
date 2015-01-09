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
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;

public abstract class AbstractCropOperation<T extends IOperationModel> extends AbstractOperation<T, OperationData> {
	/**
	 * Take the user's selected range and determine if axis or raw values should be used.
	 * Thus, set the indices to either the the raw values, shape of data or axis indices.
	 * If user values null return the a slice with the same shape.
	 * @param input
	 * @param monitor
	 * @throws OperationException
	 * @return OperationData (slice)
	 */
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		//Determine the operation rank
		int cropRank = getOutputRank().getRank();
		
		//Get the user crop limits from the model
		Double[][] userVals = getUserVals();
		
		//Return: array of arrays [[min/max][dimension]]
		int[][] indices = new int[2][cropRank];
		
		//Get the axes and also the shape of the data
		int[] dataShape = input.getShape();
		ILazyDataset[] axes = getFirstAxes(input);
		//As the axes are picked up in reverse order (i.e. z,y,x) have to get crop values using (cropRank-1)-dim
		for (int dim = 0; dim < cropRank; dim++) {
			//If no axes come back, use the raw user values
			if ((axes == null) || (axes[0] == null))  {
				indices[0][dim] = userVals[(cropRank-1)-dim][0] == null ? 0 : (int)userVals[(cropRank-1)-dim][0].doubleValue();
				indices[1][dim] = userVals[(cropRank-1)-dim][1] == null ? dataShape[dim] : (int)userVals[(cropRank-1)-dim][1].doubleValue();
			}else {
			//We do have axes, so get the indices of the user values
				indices[0][dim] = userVals[(cropRank-1)-dim][0] == null ? 0 : getAxisIndex(axes[dim], userVals[(cropRank-1)-dim][0]);
				indices[1][dim] = userVals[(cropRank-1)-dim][1] == null ? dataShape[dim] : getAxisIndex(axes[dim], userVals[(cropRank-1)-dim][1]);
			}
			
			if (indices[0][dim] == indices[1][dim]) {
				throw new OperationException(this, "Selected crop range outside axis range");
			}
			
			//Correct for reversed axes/inputs
			if (indices[0][dim] > indices[1][dim]) {
				int tmp = indices[0][dim];
				indices[0][dim] = indices[1][dim];
				indices[1][dim] = tmp;
			}
		}
		
		return new OperationData(input.getSlice(indices[0], indices[1], null));
	}
	
	protected int getAxisIndex(ILazyDataset theAxis, Double value) {

		return Maths.abs(Maths.subtract(theAxis.getSlice((Slice)null), value)).argMin();
		}
	
	/**
	 * Returns user defined crop values from the model.
	 * @return userVals
	 */
	protected abstract Double[][] getUserVals();
	
}
