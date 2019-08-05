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
import org.apache.commons.math3.distribution.NormalDistribution;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.GeometricCorrectionModel;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;
import uk.ac.diamond.scisoft.analysis.utils.SimpleUncertaintyPropagationMath;
/**
 * This operation performs the area geometric correction for 
 * reflectometry measurements as described by 
 * Gibaud, A. and Vignaud, G. (1993). Acta. Cryst., A49, 642--648.
 * DOI: 10.1107/S0108767392013126
 */
@Atomic
public class AreaCorrectionOperation extends AbstractOperationBase<GeometricCorrectionModel, OperationData> {
	
	@Override 
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.AreaCorrectionOperation";
	}
	
	// We should have two dimensions going in 
	@Override 
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}
	
	// and two coming out
	@Override 
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	// This is the fun bit
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
		
		NormalDistribution footprint  = new NormalDistribution(0, (model.getBeamHeight() / (2 * Math.sqrt(2 * Math.log(2)))));
		double areaCorrection = 2 * (footprint.cumulativeProbability(model.getSampleSize() * Math.sin(Math.toRadians(sliceAxis.getDouble()))));
		double[] thetaRadian = new double[2];
		SimpleUncertaintyPropagationMath.sin(Math.toRadians(sliceAxis.getDouble()), Math.toRadians(sliceAxis.getError()), thetaRadian);
		double[] forCPErr = new double[2];
		SimpleUncertaintyPropagationMath.multiply(model.getSampleSize(), thetaRadian[0], model.getSampleSizeErr(), thetaRadian[1], forCPErr);
		double areaCorrectionErr = 2 * (footprint.cumulativeProbability(forCPErr[1]));
		
		
		Dataset areaCorrectionDataset = DatasetFactory.createFromObject(areaCorrection);
		Dataset areaCorrectionError = DatasetFactory.createFromObject(areaCorrectionErr);
		areaCorrectionDataset.setErrors(areaCorrectionError);
		
		IDataset correctedData = ErrorPropagationUtils.divideWithUncertainty((Dataset) data, areaCorrectionDataset);
		copyMetadata(data, correctedData);
		return new OperationData(correctedData);
	}
}