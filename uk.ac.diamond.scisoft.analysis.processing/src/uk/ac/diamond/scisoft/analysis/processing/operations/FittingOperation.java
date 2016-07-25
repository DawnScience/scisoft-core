/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import java.io.Serializable;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;

public class FittingOperation extends AbstractOperationBase<FittingModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.fittingOperation";
	}


	@Override
	public OperationData execute(IDataset data, IMonitor monitor) throws OperationException {
		
		try {
			Dataset d = DatasetUtils.convertToDataset(data);
			if (d.getRank() != 1) {
				d = d.getSliceView().squeeze(true);
			}
			List<CompositeFunction> fittedPeakList = Generic1DFitter.fitPeakFunctions(DatasetUtils.convertToDataset(model.getxAxis()), 
					                                                                  d, 
					                                                                  model.getPeak(), model.createOptimizer(),
					                                                                  model.getSmoothing(), model.getNumberOfPeaks(),
					                                                                  model.getThreshold(), 
					                                                                  model.isAutostopping(), model.isBackgrounddominated(), monitor);
			
	        // Same original data but with some fitted peaks added to auxillary data.
			return new OperationData(d, (Serializable)fittedPeakList);
		} catch (Exception ne) {
			throw new OperationException(this, ne);
		}
	}
	
	public OperationRank getInputRank() {
		return OperationRank.ONE; // XY data
	}
	public OperationRank getOutputRank() {
		return OperationRank.ZERO; 
	}

}
