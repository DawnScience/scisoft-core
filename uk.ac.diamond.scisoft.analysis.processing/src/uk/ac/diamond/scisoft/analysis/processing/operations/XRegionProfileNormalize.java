/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.optimize.ApachePolynomial;

public class XRegionProfileNormalize extends AbstractOperation<XRegionProfileNormalizeModel, OperationData> {

	public enum DataType {
		DATA, GOLD
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.XRegionProfileNormalize";
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor)
			throws OperationException {
		// Get the Region to work with
		AxesMetadata axesMetadata;
		ILazyDataset energyAxis = null;
		if (model.getxRange() == null || model.getxRange().length != 2) throw new OperationException(this,"X range must contain 2 values");
		try {
			axesMetadata = input.getMetadata(AxesMetadata.class).get(0);
			energyAxis = axesMetadata.getAxis(1)[0];
		} catch (Exception e) {
			//throw new OperationException(this, "Cannot find appropriate Axes in the data file");
		}
		
		if (energyAxis == null) {
			// default to the pixel values
			energyAxis = DatasetFactory.createRange(IntegerDataset.class, input.getShape()[1]);
		}
		Dataset eDataset;
		try {
			eDataset = DatasetUtils.convertToDataset(energyAxis.getSlice());
		} catch (DatasetException e1) {
			throw new OperationException(this, e1);
		}
		int minPos = Maths.abs(Maths.subtract(eDataset, model.getxRange()[0])).argMin();
		int maxPos = Maths.abs(Maths.subtract(eDataset, model.getxRange()[1])).argMin();
		
		if (minPos == maxPos) {
			throw new OperationException(this, "Select a range inside the X axis");
		}
		if (minPos > maxPos) {
			int tmp = minPos;
			minPos = maxPos;
			maxPos = tmp;
		}
		
		
		Dataset region = DatasetUtils.convertToDataset(input.getSlice(new Slice[] {null, new Slice(minPos, maxPos, 1)}));
		Dataset regionProfile = region.sum(1);
		
		Dataset smoothedProfile = regionProfile;
		
		try {
			Dataset xAxis = DatasetFactory.createRange(DoubleDataset.class, regionProfile.getShape()[0]);
			smoothedProfile = ApachePolynomial.getPolynomialSmoothed(xAxis, regionProfile, model.getSmoothing(), 3);
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		
		smoothedProfile.setShape(smoothedProfile.getShape()[0], 1);
		smoothedProfile.setName("SmoothedProfile");
		
		Dataset result = Maths.divide(input, smoothedProfile);
		result.setName("NormalizedData");
		
		copyMetadata(input, result);
		
		OperationData opData = new OperationData(result);
		opData.setAuxData(smoothedProfile);
		
		return opData;
	}
	
	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

}
