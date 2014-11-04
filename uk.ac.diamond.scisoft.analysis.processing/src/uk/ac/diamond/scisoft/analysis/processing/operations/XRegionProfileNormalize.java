/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.Slice;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

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
		int xStart;
		int xEnd;
		int smoothing;
		try {
			xStart = (int)model.get("xStart");
			xEnd = (int)model.get("xEnd");
			smoothing = (int)model.get("smoothing");
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		
		Dataset region = DatasetUtils.convertToDataset(input.getSlice(new Slice[] {null, new Slice(xStart, xEnd, 1)}));
		Dataset regionProfile = region.sum(1);
		
		Dataset smoothedProfile = regionProfile;
		
		try {
			Dataset xAxis = DoubleDataset.createRange(regionProfile.getShape()[0]);
			smoothedProfile = ApachePolynomial.getPolynomialSmoothed(xAxis, regionProfile, smoothing, 3);
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
