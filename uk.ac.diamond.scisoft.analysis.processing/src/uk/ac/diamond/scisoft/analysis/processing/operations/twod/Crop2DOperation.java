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
		//Set the rank of the operation
		int operationRank = 2;
		
		//Get user crop values from the model
		Double[][] userVals = new Double[operationRank][2];
		userVals[0][0] = model.getxMin();
		userVals[0][1] = model.getxMax();
		userVals[1][0] = model.getyMin();
		userVals[1][1] = model.getyMax();
		
		//Do crop and return
		return cropOperation(input, operationRank, userVals);
	}
}

