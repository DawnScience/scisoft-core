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

import uk.ac.diamond.scisoft.analysis.processing.AbstractCropOperation;

public class Crop1DOperation extends AbstractCropOperation<Crop1DModel, OperationData> {

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
		int[] minIndices = new int[1];
		int[] maxIndices = new int[1];
		
		//Get user crop values.
		Double[] userXVals = new Double[]{model.getMin(), model.getMax()};
		
		//Get the x-axis (if no axes come back, we'll use the user values)
		ILazyDataset[] axes = getFirstAxes(input);
		if (axes == null) {
			for (int i = 0; i < 2; i++) {
				xIndices[i] = (int)userXVals[i].doubleValue();
			}
		} else {
			int[] dataShape = input.getShape();
			xIndices = axisCropIndexConverter(userXVals, dataShape[0], axes[0]);
		}
		//Copy axes indices to the indices for slicing
		minIndices[0] = xIndices[0];
		maxIndices[0] = xIndices[1];
		
		return new OperationData(input.getSlice(minIndices, maxIndices, null));
	}
}
