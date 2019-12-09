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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.exception.OutOfRangeException;
import org.eclipse.dawnsci.analysis.api.io.IDataHolder;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperationBase;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;
import uk.ac.diamond.scisoft.analysis.utils.ErrorPropagationUtils;

public class DCDQNormalisationOperation extends AbstractOperationBase<DCDQNormalisationModel, OperationData> implements PropertyChangeListener  {
	
	private PolynomialSplineFunction pSF;
	private Double averageIntensity;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.reflectivityandsxrd.DCDQNormalisationOperation";
	}
	
	@Override
	public final OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public final OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	@Override
	public OperationData execute(IDataset input, IMonitor monitor) {
		
		if (pSF == null) {
			pSF = initialiseInterpolator();
		}
		
		AxesMetadata axesMetadata = input.getFirstMetadata(AxesMetadata.class);
		if (axesMetadata == null) throw new OperationException(this, "No metadata found!");
		
		ILazyDataset [] qAxis = axesMetadata.getAxes();
		Dataset qSlice;
		try {
			qSlice = DatasetUtils.sliceAndConvertLazyDataset(qAxis[0]);
		} catch (DatasetException sliceAxisError) {
			throw new OperationException(this, sliceAxisError);
		}
		
		// The intensity is can be in the region of 1e8, therefore
		// in order to avoid the situation where we are normalising by a 
		// particularly massive number we divide by the average intensity here		
		double qValue = qSlice.getDouble();
		double divisor;
		try {
			divisor = pSF.value(qValue) / averageIntensity;
		} catch (OutOfRangeException outRangeError) {
			// If the minimum q collected is less than the minimum in the 
			// normalisation then just assume that the divisor will be 
			// the same
			divisor = pSF.value(pSF.getKnots()[0]) / averageIntensity;
		}
		
		IDataset output = ErrorPropagationUtils.divideWithUncertainty(DatasetUtils.convertToDataset(input), DatasetFactory.createFromObject(divisor));
		copyMetadata(input, output);
		
		return new OperationData(output);
	}
	
	
	private PolynomialSplineFunction initialiseInterpolator() {
		String filePath = model.getFilePath();
		
		IDataHolder dh;
		try {
			dh = LoaderFactory.getData(filePath);			
		} catch (Exception e) {
			throw new OperationException(this, "The defined file could not be loaded.", e);
		}
		
		DoubleDataset adc2Dataset;
		try {
			adc2Dataset = DatasetUtils.cast(DoubleDataset.class, dh.getDataset(model.getDatasetIName()));
		} catch (Exception e) {
			throw new OperationException(this, "No dataset named " + model.getDatasetIName() + " found in the file.", e);
		}
		
		double[] q;
		try {
			q = DatasetUtils.cast(DoubleDataset.class, dh.getDataset(model.getDatasetQName())).getData();
		} catch (Exception e) {
			throw new OperationException(this, "No dataset named " + model.getDatasetQName() + " found in the file.", e);
		}
		
		double[] adc2 = adc2Dataset.getData();
		
		
		averageIntensity = ((Number)adc2Dataset.mean(true)).doubleValue();
			
		SplineInterpolator iterpolator = new SplineInterpolator();
		
		return iterpolator.interpolate(q, adc2);
	}
	
	@Override
	public void setModel(DCDQNormalisationModel model) {
		super.setModel(model);
		model.addPropertyChangeListener(this);
		pSF = null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		pSF = null;
	}
}