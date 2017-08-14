/*
 * Copyright (c) 2014 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math3.analysis.integration.IterativeLegendreGaussIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.util.MathUtils;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.metadata.AxesMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.CinaderOrientationModel;
import uk.ac.diamond.scisoft.analysis.processing.operations.saxs.CinaderOrientationModel.NumberOfSymmetryFolds;


@Atomic
public class CinaderOrientationOperation extends AbstractOperation<CinaderOrientationModel, OperationData> {

	// Our private variables for this class
	private static final int INTEGRATION_POINTS = 1000000;
	private final static Logger logger = LoggerFactory.getLogger("Cinader Orientation Log");
			
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.CinaderOrientationOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationData process(IDataset slice, IMonitor monitor) throws OperationException {
		Dataset data = DatasetUtils.convertToDataset(slice);
		
		Dataset inputAxis = null;
		try {
			List<AxesMetadata> axes = slice.getMetadata(AxesMetadata.class);
			if (axes != null) {
				inputAxis = DatasetUtils.sliceAndConvertLazyDataset(axes.get(0).getAxes()[0]); //assume q is first axis
			}
		} catch (Exception e) {
			throw new OperationException(this, e);
		}
		
		Object[] myobj;
		Dataset axis = null;

		axis = inputAxis.clone().squeeze();
		NumberOfSymmetryFolds symmetryFoldsEnum = model.getFoldsOfSymmetry();
		int symmetryFolds = symmetryFoldsEnum.getFoldsOfSymmetry();
		myobj = this.process(data.getBuffer(), axis.getBuffer(), data.getShape(), symmetryFolds);
		
		float[] mydata = (float[]) myobj[0];
		float[] myangle = (float[]) myobj[1];
		float[] myvector = (float[]) myobj[2];

		Dataset resultData = DatasetFactory.createFromObject(mydata, mydata.length);
		resultData.setName("Data");
		Dataset resultAngle = DatasetFactory.createFromObject(myangle, myangle.length);
		resultAngle.setName("Angle");
		Dataset resultVector = DatasetFactory.createFromObject(myvector, myvector.length);
		resultVector.setName("Vector");
		return new OperationData(slice, new Serializable[]{resultData, resultAngle, resultVector});
	}
	
	private Object[] process(Serializable buffer, Serializable axis, final int[] dimensions, final int symmetryFolds) {
		
		double[] parentaxis = (double[]) ConvertUtils.convert(axis, double[].class);
		float[] parentdata = (float[]) ConvertUtils.convert(buffer, float[].class);
		
		int size = dimensions[dimensions.length - 1];
		double[] myaxis = new double[size];
		double[] mydata = new double[size];
		double[] cos2data = new double[size];
		double[] sin2data = new double[size];
		double[] sincosdata = new double[size];
		double correctionValue = 0.00;
		
		// Inspect first element and prepare a correction value if it's not zero
		if (parentaxis[0] < 0.00) {
			correctionValue = Math.abs(parentaxis[0]);
		} else if (parentaxis[0] > 0.00) {
			correctionValue = parentaxis[0];
		}
		
		for (int i = 0; i < parentaxis.length; i++) {
			myaxis[i] = Math.toRadians(parentaxis[i] + correctionValue) * symmetryFolds;
			mydata[i] = parentdata[i];
			
			cos2data[i] = (Math.pow(Math.cos(myaxis[i]), 2) * parentdata[i]);
			sin2data[i] = (Math.pow(Math.sin(myaxis[i]), 2) * parentdata[i]);
			sincosdata[i] = (Math.sin(myaxis[i]) * Math.cos(myaxis[i]) * parentdata[i]);
		}
		
		UnivariateInterpolator interpolator = new SplineInterpolator();
		UnivariateFunction function = interpolator.interpolate(myaxis, mydata);
		UnivariateFunction cos2Function = interpolator.interpolate(myaxis, cos2data);
		UnivariateFunction sin2Function = interpolator.interpolate(myaxis, sin2data);
		UnivariateFunction sincosFunction = interpolator.interpolate(myaxis, sincosdata);
		
		UnivariateIntegrator integrator = new IterativeLegendreGaussIntegrator(15,
				BaseAbstractUnivariateIntegrator.DEFAULT_RELATIVE_ACCURACY,
				BaseAbstractUnivariateIntegrator.DEFAULT_ABSOLUTE_ACCURACY);

		try {
			float cos2mean = (float) integrator.integrate(INTEGRATION_POINTS, cos2Function, myaxis[0], myaxis[myaxis.length - 1]);
			float sin2mean = (float) integrator.integrate(INTEGRATION_POINTS, sin2Function, myaxis[0], myaxis[myaxis.length - 1]);
			float sincosmean = (float) integrator.integrate(INTEGRATION_POINTS, sincosFunction, myaxis[0], myaxis[myaxis.length - 1]);
			float norm = (float) integrator.integrate(INTEGRATION_POINTS, function, myaxis[0], myaxis[myaxis.length - 1]);
			
			cos2mean /= norm;
			sin2mean /= norm;
			sincosmean /= norm;
			
			float result =  (float) Math.sqrt(Math.pow(cos2mean - sin2mean, 2) + Math.pow(2d * sincosmean, 2));
			double angle = Math.atan2(2d * sincosmean, cos2mean - sin2mean) / symmetryFolds;
			
			Object[] output = new Object[] {
					new float[] { result },
					new float[] { (float) Math.abs(Math.toDegrees(angle)) },
					new float[] { (float) (result * Math.cos(angle)),  (float) (result * Math.sin(angle)) },
					}; 
			
			return output;
			
		} catch (TooManyEvaluationsException e) {
			logger.warn("Too many evaluations:" + e);
			return new Object[] { new float[] { Float.NaN }, new float[] { Float.NaN }, new float[] { Float.NaN } };
		} catch (MaxCountExceededException e) {
			logger.warn("Max count exceeded:" + e);
			return new Object[] { new float[] { Float.NaN }, new float[] { Float.NaN }, new float[] { Float.NaN } };
		}
	}
}