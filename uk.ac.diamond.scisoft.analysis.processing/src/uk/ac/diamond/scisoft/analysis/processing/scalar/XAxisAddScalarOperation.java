/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.scalar;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.scalar.ScalarUncertaintyModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.ErrorPropagationUtils;

@Atomic
public class XAxisAddScalarOperation extends AbstractOperation<ScalarUncertaintyModel, OperationData>  {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.scalar.XAxisAddScalarOperation";
	}
	
	
	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}
	
	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	@Override
	protected OperationData process(IDataset data, IMonitor monitor) {
		
		ILazyDataset[] ax = getFirstAxes(data);
		if (ax == null || ax.length == 0 || ax[0] == null) throw new OperationException(this, "No axes found!");
		
		// Somewhere to put the new x-axis then check the x-axis exists
		Dataset xAxis;
		try {
			xAxis = DatasetUtils.sliceAndConvertLazyDataset(ax[0]);
		} catch (DatasetException xAxisError) {
			throw new OperationException(this, xAxisError);
		}
		Dataset scalar = DatasetFactory.createFromObject(model.getValue());
		scalar.setErrors(model.getError());
		
		// Add the desired value to the x-axis
		xAxis = ErrorPropagationUtils.addWithUncertainty(xAxis, scalar);
		
		// Assign the relevant metadata
		AxesMetadata xAxisMetadata = data.getFirstMetadata(AxesMetadata.class);
		xAxisMetadata.setAxis(0, xAxis);
		data.setMetadata(xAxisMetadata);
		
		return new OperationData(data);
	}
}
