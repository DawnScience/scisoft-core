/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.roi.IPolylineROI;
import org.eclipse.dawnsci.analysis.api.roi.IRectangularROI;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.IDataset;

public class PolygonIntegrationOperation extends AbstractOperation<PolygonIntegrationModel, OperationData> {
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.roiprofile.PolygonIntegrationOperation";
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
	protected OperationData process(IDataset input, IMonitor monitor) {
		IPolylineROI regionOfInterest = (IPolylineROI) model.getRegionOfInterest();
		if (regionOfInterest == null) 
			throw new OperationException(this, "Please define a polygon using the live mode dialog!");
		
		IRectangularROI bounds = regionOfInterest.getBounds();
		int[] intPoint = bounds.getIntPoint();
		int[] intLengths = bounds.getIntLengths();
		
		if (intPoint[0] < 0 || intPoint[0] + intLengths[0] >= input.getShape()[1] ||
				intPoint[1] < 0 || intPoint[1] + intLengths[1] >= input.getShape()[0])
			throw new OperationException(this, "Polygon is out of bounds!");

		double sum = 0.0;
		int monitorCounter = 0;
	
		BooleanDataset mask = DatasetFactory.zeros(BooleanDataset.class, input.getShape());
		
		for (int i = intPoint[0] ; i < intPoint[0] + intLengths[0] ; i++) {
			for (int j = intPoint[1] ; j < intPoint[1] + intLengths[1] ; j++) {
				if (regionOfInterest.containsPoint(i, j)) {
					sum += input.getDouble(j, i);
					mask.setItem(true, j, i);
				}
				if (monitorCounter++ % 50 == 0 && monitor != null && monitor.isCancelled()) {
					throw new OperationCanceledException("Sum aborted");
				}
			}
		}

		
		Dataset s = DatasetFactory.createFromObject(sum);
		s.setName("sum");
		
		OperationDataForDisplay odd = new OperationDataForDisplay(input, s);
		odd.setDisplayData(mask);
		
		return odd;
	}
}
