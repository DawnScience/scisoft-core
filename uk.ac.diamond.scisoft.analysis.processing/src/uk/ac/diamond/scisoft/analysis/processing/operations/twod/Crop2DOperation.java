/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.twod;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

public class Crop2DOperation extends AbstractOperation<Crop2DModel, OperationData> {
	
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
	
	protected OperationData process(IDataset input, IMonitor monitor) {
		int[] xMinIndex;
		int[] xMaxIndex;
		int[] yMinIndex;
		int[] yMaxIndex;
		
		Double xMinVal = model.getxMin();
		Double xMaxVal = model.getxMax();
		Double yMinVal = model.getyMin();
		Double yMaxVal = model.getyMax();
		
		//Get axes data from dataset and see if X & Y axes exist
		ILazyDataset[] axes = getFirstAxes(input);
		
		ILazyDataset theXAxis = axes[0];
		ILazyDataset theYAxis = axes[1];
		
		xMinIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, xMinVal)};
		xMaxIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, xMaxVal)};
		
		yMinIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, yMinVal)};
		yMaxIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theXAxis, yMaxVal)};
		
		}
	}
}
