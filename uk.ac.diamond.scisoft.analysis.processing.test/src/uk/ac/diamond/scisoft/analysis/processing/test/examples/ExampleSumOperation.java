/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.examples;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.monitor.IMonitor;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.EmptyModel;

/**
 * Simple example of an operation which sums all the images in a data series
 */
public class ExampleSumOperation extends AbstractOperation<EmptyModel, OperationData> {

	private Dataset sum;
	private ILazyDataset parent;
	private int counter;
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		//get series metadata (will not be null), to check we are from the same parent
		//and whether we have hit the final image
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		
		if (parent != ssm.getParent()) {
			parent = ssm.getParent();
			sum = null;
			counter = 0;
		}
		
		if (sum == null) {
			sum = DatasetUtils.convertToDataset(input.clone());
		} else {
			sum.iadd(input);
		}
		counter++;
		
		if (counter == ssm.getTotalSlices()) {
			IDataset out = sum;
			sum = null;
			counter = 0;
			SliceFromSeriesMetadata outsmm = ssm.clone();
			for (int i = 0; i < ssm.getParent().getRank(); i++) {
				if (!outsmm.isDataDimension(i)) outsmm.reducedDimensionToSingular(i);
			}
			out.setMetadata(outsmm);
			return new OperationData(out);
		}
		
		return null;
	}
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.test.examples.ExampleSumOperation";
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
