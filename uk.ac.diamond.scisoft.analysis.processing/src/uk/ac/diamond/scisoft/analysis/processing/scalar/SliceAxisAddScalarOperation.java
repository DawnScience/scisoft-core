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
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SourceInformation;
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
public class SliceAxisAddScalarOperation extends AbstractOperationBase<ScalarUncertaintyModel, OperationData>  {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.scalar.SliceAxisAddScalarOperation";
	}
	
	
	// Before we start, let's make sure we know how many dimensions of data are going in...
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}
	
	// ...and out
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	@Override
	public OperationData execute(IDataset data, IMonitor monitor) {
		// Get the data from the slice axis
		SliceFromSeriesMetadata ssm = data.getFirstMetadata(SliceFromSeriesMetadata.class);
		if (ssm == null ) throw new OperationException(this, "No metadata found!");

		ILazyDataset ssmParent = ssm.getParent();
		AxesMetadata ssmParentAxesMetadata = ssmParent.getFirstMetadata(AxesMetadata.class);
		ILazyDataset [] ssmParentAxes = ssmParentAxesMetadata.getAxes();
		Dataset sliceAxis;
		try {
			sliceAxis = DatasetUtils.sliceAndConvertLazyDataset(ssmParentAxes[0]);
		} catch (DatasetException sliceAxisError) {
			throw new OperationException(this, sliceAxisError);
		}
		
		// Get the value and uncertainty to be added to it
		Dataset scalar = DatasetFactory.createFromObject(model.getValue());
		scalar.setErrors(model.getError());
		
		// Add the desired value to the slice axis
		sliceAxis = ErrorPropagationUtils.addWithUncertainty(sliceAxis, scalar);
		
		// Reconstruct everything that was taken down before
		ssmParentAxesMetadata.setAxis(0, sliceAxis);
		ssmParent.setMetadata(ssmParentAxesMetadata);
		SourceInformation si = new SourceInformation(ssm.getFilePath(), ssm.getDatasetName(), ssmParent);
		SliceFromSeriesMetadata ssm2 = new SliceFromSeriesMetadata(si, ssm.getSliceInfo());
		data.setMetadata(ssm2);
		
		return new OperationData(data);
	}
}