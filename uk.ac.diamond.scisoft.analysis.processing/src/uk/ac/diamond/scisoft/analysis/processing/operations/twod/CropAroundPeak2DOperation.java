/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.AbstractCropOperation;

@Atomic
public class CropAroundPeak2DOperation extends AbstractCropOperation<CropAroundPeak2DModel> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.twod.CropAroundPeak2DOperation";
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
		int[] peakEstimate = input.maxPos(true);
	
		double xTightness = (model.getXBoxLength() - 2.) / 2.;
		double yTightness = (model.getYBoxLength() - 2.) / 2.;
		int r = getOutputRank().getRank();
		double[][] userVals = new double[r][2];
	
		userVals[0][0] = peakEstimate[1] - xTightness;
		userVals[0][1] = peakEstimate[1] + xTightness + 2;
	
		userVals[1][0] = peakEstimate[0] - yTightness;
		userVals[1][1] = peakEstimate[0] + yTightness + 2;
		for (int i = 2; i < r; i++) {
			Arrays.fill(userVals[i], Double.NaN);
		}
			
		return userVals;
	}
}