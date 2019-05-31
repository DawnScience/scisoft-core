/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.SingularMatrixException;
import org.eclipse.dawnsci.analysis.api.processing.Atomic;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.EmptyModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.plotting.api.metadata.Plot1DMetadata;
import org.eclipse.dawnsci.plotting.api.metadata.Plot1DMetadataImpl;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.PointStyle;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.GaussianND;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;

@Atomic
public class GaussianBackgroundSubtractionOperation extends AbstractOperation<EmptyModel, OperationData>  {
	
	private static transient final Logger logger = LoggerFactory.getLogger(GaussianBackgroundSubtractionOperation.class);
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.GaussianBackgroundSubtractionOperation";
	}
	
	
	// Before we start, let's make sure we know how many dimensions of data are going in...
	// This is a background subtraction to act on either one or two dimensional datasets therefore 
	// the rank can be of any shape
	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}
	
	// And it should output to the same shape
	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
	
	// This is the processing step where the dimension is determined and the appropriate fitting 
	// is performed
	@Override
	protected OperationData process(IDataset data, IMonitor monitor) {
		OperationData subtractedData = null;	
		if (data.getRank() == 1) {
			subtractedData = fit1DGaussianOffset(data);
		} else if (data.getRank() == 2) {
			subtractedData = fit2DGaussianOffset(data);
		} else {
			logger.error("The dataset should be either one or two dimensional.");
		}
		return subtractedData;
	}
	
	// Fitting for a 1D dataset
	private OperationData fit1DGaussianOffset (IDataset data) {
		// Assess if an axis is present and if it isn't make one
		// in the event of no defined axis the created one will be 
		// from 0 to the length of the data
		ILazyDataset[] ax = getFirstAxes(data);
		if (ax == null) {
			ax = new ILazyDataset[data.getRank()];
		}
		Dataset[] axes = getAxes(ax, data);
		
		Add gaussWithOffsetFit = getCombinedFunctions(data);
		
		optimisation(axes, data, gaussWithOffsetFit);
		
		// ParameterValue[3] is the offset variable
		IDataset subtractedData = Maths.subtract(data, gaussWithOffsetFit.getParameterValues()[3]);
		copyMetadata(data, subtractedData);
		removeNegatives(subtractedData);

		OperationDataForDisplay returnDataWithDisplay = new OperationDataForDisplay();
		OperationLog log = new OperationLog();
				
		IDataset fineAxis = DatasetFactory.createLinearSpace(DoubleDataset.class, axes[0].min(true).doubleValue(), axes[0].max(true).doubleValue(), 1000);
		
		IDataset gaussianWithOffsetShape = gaussWithOffsetFit.calculateValues(fineAxis);
		AxesMetadata xAxisMetadata;

		try {
			xAxisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);

		} catch (MetadataException xAxisError) {
			throw new OperationException(this, xAxisError.getMessage());
		}

		xAxisMetadata.setAxis(0, fineAxis);
		gaussianWithOffsetShape.setMetadata(xAxisMetadata);

		List<IDataset> displayData = new ArrayList<>();
		IDataset dataClone = data.clone();
		dataClone.setName("Unsubtracted Data");
		gaussianWithOffsetShape.setName("Gaussian Best Fit");
		Plot1DMetadataImpl pm = new Plot1DMetadataImpl(Plot1DMetadata.LineStyle.DASHED, 1, PointStyle.CIRCLE, 3);
		dataClone.addMetadata(pm);
		displayData.add(dataClone);		
		Plot1DMetadataImpl pm2 = new Plot1DMetadataImpl(Plot1DMetadata.LineStyle.SOLID, 2, PointStyle.NONE, 0);
		gaussianWithOffsetShape.setMetadata(pm2);
		displayData.add(gaussianWithOffsetShape);
		
		log.append("A 1D Gaussian function with an x-axis offset");
		log.append("has been fit to the data.\n");
		log.append("The parameters are as follows:");
		log.append("Peak position = %E", gaussWithOffsetFit.getParameterValues()[0]);
		log.append("Full-width half maximum = %E", gaussWithOffsetFit.getParameterValues()[1]);
		log.append("Peak area = %E", gaussWithOffsetFit.getParameterValues()[2]);
		log.append("x-Axis offset = %E", gaussWithOffsetFit.getParameterValues()[3]);
		log.append("This offset has then been subtracted from the data.");
		
		Dataset peakPosDataset = DatasetFactory.createFromObject(gaussWithOffsetFit.getParameterValues()[0], 1);
		peakPosDataset.setName("Peak Position of Gaussian Fit");
		Dataset fwhmDataset = DatasetFactory.createFromObject(gaussWithOffsetFit.getParameterValues()[1], 1);
		fwhmDataset.setName("Full width half maximum of Gaussian Fit");
		Dataset peakAreaDataset = DatasetFactory.createFromObject(gaussWithOffsetFit.getParameterValues()[2], 1);
		peakAreaDataset.setName("Peak Area of Gaussian Fit");
		Dataset offsetDataset = DatasetFactory.createFromObject(gaussWithOffsetFit.getParameterValues()[3], 1);
		offsetDataset.setName("x-Axis Offset of Gaussian Fit");
		
		returnDataWithDisplay.setShowSeparately(true);
		returnDataWithDisplay.setLog(log);
		returnDataWithDisplay.setData(subtractedData);
		returnDataWithDisplay.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		returnDataWithDisplay.setAuxData(peakPosDataset, fwhmDataset, peakAreaDataset, offsetDataset);
				
		return returnDataWithDisplay;
	}
	
	// Fitting for a 2D dataset
	private OperationData fit2DGaussianOffset(IDataset data) {
		// Assess if an x- and/or y-axis is present and if they aren't make them
		// in the event of no defined axis the created one will be 
		// from 0 to the length of the data in the appropriate dimension
		ILazyDataset[] ax = getFirstAxes(data);
		Dataset[] axes = getAxes(ax, data);
		
		// Get the Gaussian and Offset functions linked together
		Add gaussWithOffsetFit = getCombinedFunctions(data);
		
		optimisation(axes, data, gaussWithOffsetFit);
		
		IDataset subtractedData = Maths.subtract(data, gaussWithOffsetFit.getParameterValues()[6]);
		
		removeNegatives(subtractedData);
		
		return new OperationData(subtractedData);
	}
	
	private Dataset[] getAxes(ILazyDataset[] ax, IDataset data) {
		Dataset[] axes = new Dataset[data.getRank()];
		for (int i = 0; i < data.getRank(); i++) {
			if (ax[i] == null) {
				axes[i] = DatasetFactory.createRange(data.getShape()[i]);
			} else {
				try {
					axes[i] = DatasetUtils.sliceAndConvertLazyDataset(ax[i]);
				} catch (DatasetException e) {
					throw new OperationException(this, e);
				}
			}
		}
		return axes;
	}
	
	// Combining the Gaussian(ND) and Offset functions using the Add method
	// It isn't easy to generalise this code therefore there is long if
	private Add getCombinedFunctions(IDataset data) {
		if (data.getShape().length == 1) {
			// Set some maximum and minimum values for things
			double maxA = data.max(true).doubleValue() * 2;
			double mins = (double) data.maxPos(true)[0] - 20.;
			double maxs = (double) data.maxPos(true)[0] + 20.;

			// Set up the Gaussian and offset fitting functions
			Gaussian gaussFit = new Gaussian(mins, maxs, 50, maxA);
			Offset offsetFit = new Offset(0, data.max(true).doubleValue());
			offsetFit.setParameterValues(0);
			
			// Then link the two fitting functions together
			Add gaussWithOffsetFit = new Add();
			gaussWithOffsetFit.addFunction(gaussFit);
			gaussWithOffsetFit.addFunction(offsetFit);
			
			return gaussWithOffsetFit;
		} else {
			// Set some maximum and minimum values for things
			double maxV = (double) data.max(true).doubleValue() * 2;
			double[] mins = {data.maxPos(true)[0]-20, data.maxPos(true)[1]-20};
			double[] maxs = {data.maxPos(true)[0]+20, data.maxPos(true)[1]+20};
			
			// Set up the Gaussian and offset fitting functions
			GaussianND gaussFit = new GaussianND(maxV, mins, maxs, 50);
			gaussFit.setSigmaLimits(0, 100);
			Offset offsetFit = new Offset(0, data.max(true).doubleValue());
			offsetFit.setParameterValues(0);
			
			// Then link the two fitting functions together
			Add gaussWithOffsetFit = new Add();
			gaussWithOffsetFit.addFunction(gaussFit);
			gaussWithOffsetFit.addFunction(offsetFit);
			
			return gaussWithOffsetFit;
		}
	}
	
	// Powell optimiser appeared to be the most robust for the 2D fitting operations
	private void optimisation(Dataset[] axes, IDataset data, Add function) {
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.POWELL);
			opt.optimize(axes, data, function);
		} catch (SingularMatrixException e) {
			logger.error("The ftting covariance matrix is singular");
		} catch (Exception fittingError) {
			logger.error("Exception performing 2D Gaussian fit with offset in GaussianBackgroundSubtractionOperation(): {}", fittingError);
		} 
	}
	
	// Negative data is *hopefully* only due to noise and therefore
	// we can reset these values to zero
	private void removeNegatives(IDataset data) {
		for (int i = 0; i < data.getShape()[0]; i++) {
			if (data.getShape().length == 2) {
				for (int j = 0; j < data.getShape()[1]; j++) {
					if (data.getDouble(i, j) < 0) {
						data.set(0, i, j);
					}
				}	
			} else {
				if (data.getDouble(i) < 0) {
					data.set(0, i);
				}
			}
		}
	}
}
