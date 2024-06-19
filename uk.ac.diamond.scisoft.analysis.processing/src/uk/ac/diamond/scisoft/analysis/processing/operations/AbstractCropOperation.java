/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.model.IOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

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
		double[][] userVals = getUserVals(input);
		
		//Return: array of arrays [[min/max][dimension]]
		int[][] indices = new int[2][cropRank];
		
		//Get the axes and also the shape of the data
		int[] dataShape = input.getShape();
		ILazyDataset[] axes = getFirstAxes(input);
		//As the axes are picked up in reverse order (i.e. z,y,x) have to get crop values using (cropRank-1)-dim
		for (int dim = 0; dim < cropRank; dim++) {
			//If no axes come back, use the raw user values
			double[] endVals = userVals[(cropRank-1)-dim];
			if ((axes == null) || (axes[0] == null))  {
				double v = endVals[0];
				indices[0][dim] = Double.isNaN(v) ? 0 : (int)v;
				v = endVals[1];
				indices[1][dim] = Double.isNaN(v) ? dataShape[dim] : (int)v;
			}else {
			//We do have axes, so get the indices of the user values
				double v = endVals[0];
				indices[0][dim] = Double.isNaN(v) ? 0 : getAxisIndex(axes[dim], v);
				v = endVals[1];
				indices[1][dim] = Double.isNaN(v) ? dataShape[dim] : getAxisIndex(axes[dim], v);
			}
			
			if (indices[0][dim] == indices[1][dim]) {
				throw new OperationException(this, "Selected crop range outside axis range");
			}
			
			//Correct for reversed axes/inputs
			int tmp = indices[0][dim];
			if (tmp > indices[1][dim]) {
				indices[0][dim] = indices[1][dim];
				indices[1][dim] = tmp;
			}
		}
		OperationData data = new OperationData(input.getSlice(indices[0], indices[1], null));
		int[] middle = new int[cropRank];
		for (int i = 0; i < cropRank; i++) {
			middle[i] = (indices[0][i] + indices[1][i]) / 2;
		}
		data.setAuxData(ProcessingUtils.createNamedDataset(middle, "crop_centre"));
		return data;
	}
	
	protected int getAxisIndex(ILazyDataset theAxis, Double value) {
		try {
			return Maths.abs(Maths.subtract(theAxis.getSlice((Slice) null), value)).argMin();
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
	}

	/**
	 * Returns user defined crop values from the model.
	 * @return userVals
	 */
	protected abstract double[][] getUserVals(IDataset input);
	
}
