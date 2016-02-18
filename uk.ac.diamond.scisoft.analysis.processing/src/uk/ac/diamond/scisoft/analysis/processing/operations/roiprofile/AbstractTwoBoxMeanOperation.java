/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.ROIUtils;
import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public abstract class AbstractTwoBoxMeanOperation<T extends TwoBoxModel> extends AbstractOperation<TwoBoxModel, OperationData> {

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.TWO;
	}

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		RectangularROI box1 = model.getBox1();
		RectangularROI box2 = model.getBox2();
		Dataset mask = null;
		MaskMetadata mmd = input.getFirstMetadata(MaskMetadata.class);
		if (mmd != null && mmd.getMask() != null) mask = DatasetUtils.convertToDataset(mmd.getMask().getSlice());
		
		Dataset data = DatasetUtils.convertToDataset(input);
		
		Dataset[] b1 = ROIProfile.boxMean(data, mask, box1, false);
		Dataset[] b2 = ROIProfile.boxMean(data, mask, box2, false);
		
		Dataset out = result(b1[0].getDouble(0), b2[0].getDouble(0)); 
		
		return new OperationData(input, out);
	}
	
	abstract protected Dataset result(double mean1, double mean2);

}
