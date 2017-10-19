/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.operations;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.processing.operations.powder.AzimuthalPixelIntegrationOperation;
import uk.ac.diamond.scisoft.xpdf.metadata.XPDFMetadata;

/**
 * An operation to perform azimuthal integration while preserving the {@link XPDFMetadata} metadata associated with the data. 
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 * @param <T> a subclass of{@link AzimuthalPixalIntegrationModel}.
 */
public class XPDFAzimuthalIntegrationOperation extends AbstractOperation<XPDFAzimuthalIntegrationModel, OperationData>{

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.xpdf.operations.XPDFAzimuthalIntegration";
	}
	
	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// get the XPDF metadata
		XPDFMetadata xMeta = input.getFirstMetadata(XPDFMetadata.class);
		
		// create the Operation that will do the processing
		AzimuthalPixelIntegrationOperation<XPDFAzimuthalIntegrationInternalModel> internalOperation = 
				new AzimuthalPixelIntegrationOperation<XPDFAzimuthalIntegrationInternalModel>();
		
		// create the fake model that will supply the parameters to the internal Operation
		XPDFAzimuthalIntegrationInternalModel internalModel = 
				new XPDFAzimuthalIntegrationInternalModel(
						model.getAzimuthalRange(),
						model.getRadialRange(),
						model.isPixelSplitting(),
						model.getNumberOfBins());
		
		internalOperation.setModel(internalModel);
		
		// Resize the data to be more like it was in the execute() method
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		// Get the dimensions in the original data
		int[] dataDims = ssm.getDataDimensions();
		int maxDimension = Collections.max(Arrays.asList(ArrayUtils.toObject(dataDims)));
		int[] newShape = new int[maxDimension+1];
		int[] oldShape = input.getShape();
		// All elements of the new shape are 1
		Arrays.fill(newShape, 1);
		// except those that are in the data
		for (int dim = 0; dim < oldShape.length; dim++)
			newShape[dataDims[dim]] = oldShape[dim]; 
		
		IDataset reshapedInput = input.clone();
		// The reshaped data is now (hopefully) the same shape as input was in this.execute()
		reshapedInput.resize(newShape);
		
		OperationData output = internalOperation.execute(reshapedInput, monitor);
		
		// Set the axis coordinate to q
		if (xMeta != null) {
			xMeta.getSampleTrace().setAxisAngle(false);
		}
		
		output.getData().addMetadata(xMeta);

		return output;
		
//		OperationData superResult = super.process(input, monitor);
//
//		// The downstream processing relies on metadata to tell if the axis is 2θ or q
//		if (xMeta != null) {
//			
//			if (model.getAxisType() != XAxis.ANGLE)
//				xMeta.getSample().getTrace().setAxisAngle(false);
//		
//			superResult.getData().setMetadata(xMeta);
//		}
//		return superResult;
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.TWO;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
}
