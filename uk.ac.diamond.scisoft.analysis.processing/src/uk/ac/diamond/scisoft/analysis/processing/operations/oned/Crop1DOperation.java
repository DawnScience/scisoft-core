/*-
 * Copyright 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.AbstractOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;

public class Crop1DOperation extends AbstractOperation<Crop1DModel, OperationData> {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.Crop1DOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		int[] startIndex;
		int[] endIndex;
		
		Double startVal = model.getStart();
		Double endVal = model.getEnd();
		
		// Get the x-axis
		ILazyDataset[] axes = getFirstAxes(input);
		if ((axes == null) || (axes[0] == null))  {
			// We're plotting against axis-less data
			startIndex = new int[]{(int) startVal.doubleValue()};
			endIndex = new int[]{(int) endVal.doubleValue()};
		}
		else {
			ILazyDataset theAxis = axes[0];
			if ((startVal == null) && (endVal == null)){
				startIndex = null;
				endIndex = null;
			}else if (startVal == null) {
				startIndex = null;
				endIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, endVal)};
				
			} else if (endVal == null) {
				startIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, startVal)};
				endIndex = null;
			} else {
				if (endVal <= startVal) {
					throw new OperationException(this, "Beginning of range is at or after end");
				}else if (startVal>= endVal){
					throw new OperationException(this, "End of range is at or before beginning");
				}
				startIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, startVal)};
				endIndex = new int[]{DatasetUtils.findIndexGreaterThanOrEqualTo((Dataset) theAxis, endVal)};
			}
		}
			
		return new OperationData(input.getSlice(startIndex, endIndex, null));
	}


}
