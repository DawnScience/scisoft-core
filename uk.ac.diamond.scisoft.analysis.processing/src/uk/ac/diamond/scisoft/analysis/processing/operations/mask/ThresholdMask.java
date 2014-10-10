/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations.mask;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MaskMetadata;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.PositionIterator;

import uk.ac.diamond.scisoft.analysis.metadata.MaskMetadataImpl;

public class ThresholdMask extends AbstractOperation<ThresholdMaskModel, OperationData> {

	@Override
    public String getName() {
		return "Threshold Mask";
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.thresholdMask";
	}

	@Override
	public OperationData execute(IDataset slice, IMonitor monitor) throws OperationException {
		
		Dataset data = (Dataset)slice;
		if (data.getRank() != 2) {
			data = data.getView().squeeze(true);
		}
		IDataset mask = DatasetUtils.convertToDataset(getFirstMask(data));
		
		if (mask == null) mask = BooleanDataset.ones(data.getShape());
		
		if (!isCompatible(data.getShape(), mask.getShape())) {
			throw new OperationException(this, "Mask is incorrect shape!");
		}
		
		try {
			Double upper  = (Double)model.get("Upper");
			if (upper==null) upper = Double.MAX_VALUE;
			
			Double lower  = (Double)model.get("Lower");
			if (lower==null) lower = -Double.MAX_VALUE;
			
			// TODO A fork/join or Java8 lambda would do this operation faster...
			PositionIterator it = new PositionIterator(mask.getShape());
			while (it.hasNext()) {
								
				int[] pos = it.getPos();
				if (data.getDouble(pos)>upper || data.getDouble(pos)<lower) {
					mask.set(false, pos);
				}
			}
			
			MaskMetadata mm = new MaskMetadataImpl(mask);
			data.setMetadata(mm);
			
			return new OperationData(data);

		} catch (Exception ne) {
			throw new OperationException(this, ne);
		}
	}

	protected static boolean isCompatible(final int[] ashape, final int[] bshape) {

		List<Integer> alist = new ArrayList<Integer>();

		for (int a : ashape) {
			if (a > 1) alist.add(a);
		}

		final int imax = alist.size();
		int i = 0;
		for (int b : bshape) {
			if (b == 1)
				continue;
			if (i >= imax || b != alist.get(i++))
				return false;
		}

		return i == imax;
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
