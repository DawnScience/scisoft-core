/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.AbstractCropOperation;

@Atomic
public class Crop1DOperation extends AbstractCropOperation<Crop1DModel> {

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

	@Override
	protected double[][] getUserVals(IDataset input) {
		//Get user crop values from the model
		int r = getOutputRank().getRank();
		double[][] userVals = new double[r][2];
		userVals[0][0] = model.getMin();
		userVals[0][1] = model.getMax();
		for (int i = 1; i < r; i++) {
			Arrays.fill(userVals[i], Double.NaN);
		}
		
		return userVals;
	}
	
	
}
