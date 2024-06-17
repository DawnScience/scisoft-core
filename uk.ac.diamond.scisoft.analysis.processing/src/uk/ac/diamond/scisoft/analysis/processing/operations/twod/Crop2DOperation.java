/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.AbstractCropOperation;

@Atomic
public class Crop2DOperation extends AbstractCropOperation<Crop2DModel> {
	
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

	@Override
	protected double[][] getUserVals(IDataset input) {
		//Get user crop values from the model
		int r = getOutputRank().getRank();
		double[][] userVals = new double[r][2];
		userVals[0][0] = model.getxMin();
		userVals[0][1] = model.getxMax();
		userVals[1][0] = model.getyMin();
		userVals[1][1] = model.getyMax();
		for (int i = 2; i < r; i++) {
			Arrays.fill(userVals[i], Double.NaN);
		}

		return userVals;
	}
}

