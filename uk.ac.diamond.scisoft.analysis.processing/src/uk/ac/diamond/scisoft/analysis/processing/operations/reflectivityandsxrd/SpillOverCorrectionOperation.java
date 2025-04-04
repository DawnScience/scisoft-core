/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd;

import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.GeometricCorrectionModel;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;
import uk.ac.diamond.scisoft.analysis.utils.SimpleUncertaintyPropagationMath;

@Atomic
public class SpillOverCorrectionOperation extends AbstractOperationBase<GeometricCorrectionModel, OperationData>  {

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.SpillOverCorrectionOperation";
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
		// Determine the spill over angle
		// Using the relationship defined in Section 3.2 of 
		// Salah, F., Harzallah, B., and van der Lee, A. (2007). 
		// J. Appl. Crystallogr. 40(5), 813--819. 
		// DOI: 10.1107/S0021889807030403		
		double sampleSize = model.getSampleSize();
		double beamHeight = model.getBeamHeight();
		double sampleSizeErr = model.getSampleSizeErr();
		double beamHeightErr = model.getBeamHeightErr();
		
		double[] ratio = new double[2];
		SimpleUncertaintyPropagationMath.divide(beamHeight, sampleSize, beamHeightErr, sampleSizeErr, ratio);
		double[] thetaSpillOver = new double[2];
		SimpleUncertaintyPropagationMath.arcsin(ratio[0], ratio[1], thetaSpillOver);
		
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
		Dataset two = DatasetFactory.createFromObject(2.);
		Dataset twoSliceAxis = ErrorPropagationUtils.multiplyWithUncertainty(sliceAxis, two);
		
		IDataset correctedData = data;
		if (Math.toRadians(twoSliceAxis.getDouble()) < thetaSpillOver[0]) {
			
			double[] correction = new double[2];
			double twoSliceError = 0;
			twoSliceError = twoSliceAxis.getError();
			SimpleUncertaintyPropagationMath.divide(thetaSpillOver[0], Math.toRadians(sliceAxis.getDouble()), 
					thetaSpillOver[1], Math.toRadians(twoSliceError), correction);
			Dataset correctionD = DatasetFactory.createFromObject(correction[0]);
			Dataset correctionErr = DatasetFactory.createFromObject(correction[1]);			
			correctionD.setErrors(correctionErr);
			correctedData = ErrorPropagationUtils.multiplyWithUncertainty((Dataset) data, correctionD); 
			copyMetadata(data, correctedData);
		}
		return new OperationData(correctedData);
	}
}