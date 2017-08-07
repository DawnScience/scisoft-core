/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import java.util.Arrays;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.roi.ROISliceUtils;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.SliceND;

public class MaxAverageRegionRatioOperation extends AbstractOperation<XRangeModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.MaxAverageRegionRatioOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] firstAxes = getFirstAxes(input);
		IDataset axis = null;
		
		if (firstAxes == null || firstAxes[0] == null) {
			axis = DatasetFactory.createRange(IntegerDataset.class, input.getSize());
		} else {
			try {
				axis = firstAxes[0].getSlice();
			} catch (DatasetException e) {
				throw new OperationException(this, e);
			}
		}
		
		double[] integrationRange = model.getRange();
		int[] indexes = new int[2];
		if (integrationRange != null) {
			integrationRange = integrationRange.clone();
			Arrays.sort(integrationRange);
			indexes = new int[2];
			indexes[0]= ROISliceUtils.findPositionOfClosestValueInAxis(axis, integrationRange[0]);
			indexes[1]= ROISliceUtils.findPositionOfClosestValueInAxis(axis, integrationRange[1]);
			Arrays.sort(indexes);
		} else {
			indexes[0] = 0;
			indexes[1] = axis.getSize()-1;
		}
		
		SliceND s = new SliceND(input.getShape());
		s.setSlice(0, indexes[0], indexes[1], 1);
		
		Dataset slice = DatasetUtils.convertToDataset(input.getSlice(s));
		double max = slice.max(true).doubleValue();
		double mean = (Double)slice.mean(true);
		
		Dataset aux = DatasetFactory.createFromObject(new double[]{max/mean});
		aux.setName("max-mean-ratio");
		aux.squeeze();
		
		
		
		return new OperationData(input, aux);
	}

}
