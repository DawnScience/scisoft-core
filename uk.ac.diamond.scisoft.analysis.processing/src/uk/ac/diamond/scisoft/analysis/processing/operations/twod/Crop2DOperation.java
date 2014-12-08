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

import uk.ac.diamond.scisoft.analysis.processing.AbstractCropOperation;

public class Crop2DOperation extends AbstractCropOperation<Crop2DModel, OperationData> {
	
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
		int[] xIndices = new int[2];
		int[] yIndices = new int[2];
		int[] minIndices = new int[2];
		int[] maxIndices = new int[2];
		
		//Get user crop values.
		Double[] userXVals = new Double[]{model.getxMin(), model.getxMax()};
		Double[] userYVals = new Double[]{model.getyMin(), model.getyMax()};
		
		//Get axes data from dataset and see if X & Y axes exist
		ILazyDataset[] axes = getFirstAxes(input);
		if (axes == null) {
			for (int i = 0; i < 2; i++) {
				xIndices[i] = (int)userXVals[i].doubleValue();
				yIndices[i] = (int)userYVals[i].doubleValue();
			}
		} else {
			int[] dataShape = input.getShape();
			xIndices = axisCropIndexConverter(userXVals, dataShape[0], axes[0]);
			yIndices = axisCropIndexConverter(userYVals, dataShape[1], axes[1]);
		}
		minIndices[0] = xIndices[0];
		minIndices[1] = yIndices[0];
		maxIndices[0] = xIndices[1];
		maxIndices[1] = yIndices[1];

		return new OperationData(input.getSlice(minIndices, maxIndices, null));
		
	}
}

