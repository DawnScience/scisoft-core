/*-
 * Copyright (c) 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.saxs;


import java.io.IOException;
// Imports from java
import java.net.URL;

// Imports from org.apache
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.eclipse.core.runtime.FileLocator;
// Imports from org.eclipse.core
import org.eclipse.core.runtime.Platform;
import org.eclipse.dawnsci.analysis.api.io.ScanFileHolderException;
// Imports from org.eclipse.dawnsci
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.PlotAdditionalData;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
// Imports from org.eclipse.january
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;
// Imports from org.osgi
import org.osgi.framework.Bundle;

import uk.ac.diamond.scisoft.analysis.io.DataHolder;
import uk.ac.diamond.scisoft.analysis.io.LoaderFactory;


// @author Tim Snow


// The operation for a DAWN process to calculate the intensity scaling value between gathered data from a known standard
// As in: F.Ilavsky G. Long J. Quintana A. Allen & P. Jemian, Metallurgical and Materials Transactions A, 2010, 41, 1151-1158, DOI: 10.1007/s11661-009-9950-x

@PlotAdditionalData(onInput = false, dataName = "Calibrant")

public class AbsoluteIntensityCalibrationOperation extends AbstractOperation<AbsoluteIntensityCalibrationModel, OperationData> {

	
	// Let's give this process an ID tag
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.saxs.AbsoluteIntensityCalibrationOperation";
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


	// Now let's define the main calculation process
	@Override
	public OperationData process(IDataset inputDataset, IMonitor monitor) throws OperationException {
		// We should be returning:
		// 1) The data
		// 2) The calibration lineplot
		// 3) The scaling factor between 1 & 2
		
		
		// First we shall set up some variables for the calibrant
		DoubleDataset calibrantQdataset = null;
		DoubleDataset calibrantIdataset = null;
		
		// and input datasets
		DoubleDataset inputQdataset = null;
		DoubleDataset inputIdataset = null;
		
		// alongside a home for the final scaling factor.
		double absoluteScalingFactor = 0.00;
		double absoluteScalingFactorStdDev = 0.00;
		
		// Next we shall populate the calibrant dataset
		try {
			if (model.getUseInternalCalibrant() == true) {
				// By opening the default file
				Bundle bundle = Platform.getBundle("uk.ac.diamond.scisoft.analysis.processing");
				URL calibrantFileURL = bundle.getEntry("data/GlassyCarbon_T.dat");
				DataHolder calibrantData = (DataHolder) LoaderFactory.getData(FileLocator.resolve(calibrantFileURL).getPath());
				// and extracting out the relevant datasets
				calibrantQdataset = (DoubleDataset) calibrantData.getDataset(0).squeezeEnds();
				calibrantIdataset = (DoubleDataset) calibrantData.getDataset(1).squeezeEnds();
			}
			else {
				// Or by opening a different file
				String filePath = model.getAbsoluteScanFilePath();
				DataHolder calibrantData = (DataHolder) LoaderFactory.getData(filePath);
				// and extracting out the relevant datasets
				calibrantQdataset = (DoubleDataset) calibrantData.getDataset(0).squeezeEnds();
				calibrantIdataset = (DoubleDataset) calibrantData.getDataset(1).squeezeEnds();
			}
		} // Performing error handling where required
		catch (IOException ioError) {
			throw new OperationException(this, ioError);
		} // Throwing the appropriate OperationExceptions
		catch (ScanFileHolderException fileError) {
			throw new OperationException(this, fileError);
		} catch (Exception loaderFactoryError) {
			throw new OperationException(this, loaderFactoryError);
		}

		// Then we shall populate the input datasets
		inputIdataset = (DoubleDataset) inputDataset.getSlice(new Slice()).squeeze();
		try {
			inputQdataset = (DoubleDataset) DatasetUtils.sliceAndConvertLazyDataset(inputDataset.getFirstMetadata(AxesMetadata.class).getAxes()[0]);
		} catch (DatasetException noAxisException) {
			throw new OperationException(this, noAxisException);
		}

		// Then create an interpolator for where we need data but don't have any
		UnivariateInterpolator splineIinterpolator = new SplineInterpolator();
		UnivariateFunction calibrantInterpolator = splineIinterpolator.interpolate((double[])calibrantQdataset.getBuffer(),(double[])calibrantIdataset.getBuffer());
		
		// Now we'll get the radial range
		double[] radialRange = model.getRadialRange();
		
		// Or create our own if need be from the min/max values available
		if (radialRange == null) {
			radialRange = new double[2];
			radialRange[0] = Math.max(calibrantQdataset.min().doubleValue(), inputQdataset.min().doubleValue());
			radialRange[1] = Math.min(calibrantQdataset.max().doubleValue(), inputQdataset.min().doubleValue());
		}
		
		// And then define the minimum and maximum q values and indexes
		double qMin = Math.max(calibrantQdataset.min().doubleValue(), radialRange[0]);
		double qMax = Math.min(calibrantQdataset.max().doubleValue(), radialRange[1]);
		
		int inputQStart = Math.min(inputQdataset.getSize() - 1, DatasetUtils.findIndexGreaterThanOrEqualTo(inputQdataset, qMin));
		int inputQStop = Math.min(inputQdataset.getSize() - 1, DatasetUtils.findIndexGreaterThanOrEqualTo(inputQdataset, qMax));
		
		// Then create a home for the statistics
		SummaryStatistics stats = new SummaryStatistics();
		
		// Then find the correlation between the calibrant and the data
		for (int loopIter = inputQStart; loopIter <= inputQStop; loopIter++) {
			// Get the ratio between the calibrant and real data's values 
			if (inputIdataset.getDouble(loopIter) != 0) {
				stats.addValue(calibrantInterpolator.value(inputQdataset.getDouble(loopIter)) / inputIdataset.getDouble(loopIter));	
			}
		}

		// Then we'll get out the statistics we care about
		absoluteScalingFactor = stats.getMean();
		absoluteScalingFactorStdDev = stats.getStandardDeviation();
		
		// Now we can create a home for the scaling factor and standard deviation
		Dataset absoluteScalingFactorDataset = DatasetFactory.zeros(1);
		Dataset absoluteScalingFactorErrorDataset = DatasetFactory.zeros(1);
		
		// Then fill them with descriptive titles!
		String datasetTitle = "Scaling Factor is " + Double.toString(absoluteScalingFactor);
		absoluteScalingFactorDataset.setName(datasetTitle);
		absoluteScalingFactorDataset.set(absoluteScalingFactor, 0);
		
		String errorDatasetTitle = "Scaling Standard Deviation is " + Double.toString(absoluteScalingFactorStdDev);
		absoluteScalingFactorErrorDataset.setName(errorDatasetTitle);
		absoluteScalingFactorErrorDataset.set(absoluteScalingFactorStdDev, 0);
		
		// Also, sneakily, update the UI
		model.setScalingFactor(absoluteScalingFactor);
		model.setScalingFactorError(absoluteScalingFactorStdDev);
		
		// Now we create a dataset for the calibrant data
		AxesMetadata axisMetadata = null;
		// Which has to be passed through a try/catch statement
		try {
			axisMetadata = MetadataFactory.createMetadata(AxesMetadata.class, 1);
			axisMetadata.addAxis(0, calibrantQdataset);
		} catch (MetadataException metadataError) {
			throw new OperationException(this, metadataError);
		}
		
		// Then shave down, and correct, the output dataset
		DoubleDataset outputIdataset = (DoubleDataset) inputIdataset.getSlice(new Slice(inputQStart, inputQStop));
		outputIdataset = outputIdataset.imultiply(absoluteScalingFactor);

		// Now we can rework the calibrant data for plotting
		IDataset calibrantDataset = DatasetFactory.createFromObject(calibrantIdataset);
		calibrantDataset.setMetadata(axisMetadata);
		calibrantDataset.setName("Calibrant");
		
		// Then we can return EVERYTHING
		return new OperationData(outputIdataset, calibrantDataset, absoluteScalingFactorDataset, absoluteScalingFactorErrorDataset);
	}
}