/*-
 * Copyright 2021 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Planck;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.processing.operations.PyrometryModel.OperationToDisplay;
import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;


public class PyrometryOperation extends AbstractOperation<PyrometryModel, OperationData> {

	private Dataset planckXSlice;
	private Dataset planckYSlice;
	private Dataset wienXSlice;
	private Dataset wienYSlice;

	private Dataset xAxis = null;

	private double _a = Planck.kB / Planck.h / Planck.c;
	private double _b = 2 * Math.PI * Planck.h * Planck.c * Planck.c;

	private static final Logger logger = LoggerFactory.getLogger(PyrometryOperation.class);

	@Override
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {

		// find which slice we're in, and get the same slice of the required dataset

		// user must supply a spectrum dataset
		if (model.getSpectrumDataset() == null) throw new OperationException(this, "Dataset to analyse must be set");

		SliceFromSeriesMetadata ssm = input.getFirstMetadata(SliceFromSeriesMetadata.class);

		Dataset spectrum = null;
		IDataset lz_spectra = null;

		try {
			lz_spectra = ProcessingUtils.getDataset(this, ssm.getFilePath(), model.getSpectrumDataset() );
		} catch (OperationException e) {
			throw new OperationException(this, "cannot locate spectrum dataset in file");
		}

		try {
			spectrum = DatasetUtils.sliceAndConvertLazyDataset(ssm.getMatchingSlice(lz_spectra)).squeeze();
		} catch (DatasetException e) {
			throw new OperationException(this, "cannot slice dataset");
		}


		// we'll need this for the inversion slice later on
		final int len = spectrum.getShapeRef()[0];

		try {
			IDataset lz_wavelength = ProcessingUtils.getDataset(this, ssm.getFilePath(), model.getWavelengthDataset() );
			xAxis = Maths.multiply(DatasetUtils.sliceAndConvertLazyDataset(lz_wavelength), 1e-9);
		} catch (OperationException oe) {
			throw new OperationException(this, "could not access spectrum x axis");
		} catch (DatasetException de) {
			throw new OperationException(this, "error converting x axis");
		}


		// Load the calibration from the specified file
		String path = model.getCalibrationFilePath();
		String dsName = model.getCalibrationDataset();

		if (path == null || path.isEmpty()) throw new OperationException(this,"Calibration file path must be set");
		if (dsName == null || dsName.isEmpty()) throw new OperationException(this,"Calibration file dataset must be set");

		ILazyDataset lz_cal = ProcessingUtils.getLazyDataset(this, path, dsName);


		// need Planck function for normalisation
		AFunction f = new Planck();
		f.setParameterValues(1., model.getCalibrationTemperature());
		DoubleDataset ideal = f.calculateValues(xAxis);


		// calibrate and normalise the input data
		Dataset calibrated = null;
		try {
			calibrated = Maths.divide(spectrum, DatasetUtils.sliceAndConvertLazyDataset(lz_cal)).imultiply(ideal);
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}


		// get the interval the user is interested in fitting
		double[] wavelengthRangeNM = model.getWavelengthRange();

		// user types in nm, need to convert to m
		double[] wavelengthRange = {0.0,1.0};
		for (int i = 0; i < wavelengthRange.length; i++) {
			wavelengthRange[i] = wavelengthRangeNM[i] / 1e9;
		}

		// create logpane object to annotate output in gui (note this is not the logger)
		OperationLog logpane = new OperationLog();

		//////////////////////////////////////////////////////////////////////////////
		// Planck Function Fitting

		// prepare the slicing
		Dataset[] slices = prepareDataForFitting(xAxis, calibrated, wavelengthRange);
		planckXSlice = slices[0];
		planckYSlice = slices[1];

		// Perform the fitting
		Planck planckFit = fitPlanckFunction();

		// Extract out the fitting parameters
		double planckEmissivity = planckFit.getParameterValue(0);
		double planckTemperature = planckFit.getParameterValue(1);

		// Assuming there were nice numbers, regenerate from the x-axis
		Dataset planckFittedYaxis;
		if (Double.isFinite(planckEmissivity) && Double.isFinite(planckTemperature)) {
			planckFittedYaxis = planckFit.calculateValues(planckXSlice);
		} else {
			planckFittedYaxis = DatasetFactory.zeros(planckYSlice);
		}



		// log the results to the logpane
		double rSquared = calculateRSquared(planckYSlice, planckFittedYaxis);
		logpane.append("Planck Function Fitting -- R² = %.4f",rSquared );
		logpane.append("Emissivity (e) = %.2f", planckEmissivity);
		logpane.append("Temperature (T) = %.2f K" , planckTemperature);
		logpane.append("");

		// also the logger
		logger.debug("Planck T:{}, e:{}, R²:{} ",planckTemperature,planckEmissivity,rSquared);


		//////////////////////////////////////////////////////////////////////////////
		// Wien Function Fitting

		// Wien function fits inverse wavelength as x axis
		Slice inversion = new Slice(len, 0, -1);
		Dataset inverseWavelength = Maths.reciprocal(xAxis).getSlice(inversion);

		// Convert Planck to Wien form
		Dataset wiened = convertToWien(xAxis, calibrated).getSlice(inversion);
		double[] inverseWavelengthRange = {1./wavelengthRange[1], 1./wavelengthRange[0]};

		slices = prepareDataForFitting(inverseWavelength, wiened, inverseWavelengthRange);

		wienXSlice = slices[0];
		wienYSlice = slices[1];

		// Perform the fitting
		StraightLine linearFit = fitWienFunction();

		// extract the temperature
		double wienTemperature = 1. / linearFit.getParameterValue(0);


		// Assuming there were nice numbers, regenerate from the x-axis
		Dataset wienFittedYaxis;
		if (Double.isFinite(wienTemperature)) {
			wienFittedYaxis = linearFit.calculateValues(wienXSlice);
		} else {
			wienFittedYaxis = DatasetFactory.zeros(wienYSlice);
		}


		// log that
		rSquared = calculateRSquared(wienYSlice, wienFittedYaxis);
		logpane.append("Wien Function Fitting -- R² = %.4f", rSquared);
		logpane.append("Temperature (T) = %.2f K" , wienTemperature);
		logpane.append("");

		// also the logger
		logger.debug("Wien T:{}, R²:{} ",wienTemperature, rSquared);


		//////////////////////////////////////////////////////////////////////////////
		// Two Colour Fitting
		// T(lambda) = (1/lambda - 1/lambda+dlambda) / Wien(lambda) Wien(lambda+dlambda)

		// get deltalambda from model
		double deltaLambda = model.getDeltaLambda();

		// convert to closest number of bins
		int nDeltaLambdaBins = (int) Math.round(deltaLambda / (planckXSlice.getDouble(1) - planckXSlice.getDouble(0)) / 1e9);
		int nPoints = planckXSlice.getSize();
		if (nDeltaLambdaBins > nPoints) {
			throw new OperationException(this, "Δλ is too large for wavelength range");
		}

		int nWindows = nPoints - nDeltaLambdaBins;
		logger.debug("performing STCP with {} windows", nWindows);

		// loop through data and do calc for each point
		DoubleDataset twoColourTemperatures = DatasetFactory.zeros(nWindows);

		for (int i = 0; i < nWindows; i++) {
			int j = i + nDeltaLambdaBins;
			double f1 = planckXSlice.getDouble(i);
			double f2 = planckXSlice.getDouble(j);
			double i1 = convertToWien(f1, planckYSlice.getDouble(i));
			double i2 = convertToWien(f2, planckYSlice.getDouble(j));
			twoColourTemperatures.set(Math.abs((1/f2 - 1/f1) / (i2 - i1)), i);
		}


		// Now take a histogram of twoColourTemperatures; 12 bins, range = Plank Temperature ± 20%
		Histogram twoColourHistogram  = new Histogram(12, 0.8 * planckTemperature, 1.2 * planckTemperature);
		List<Dataset> histogramResults = twoColourHistogram.value(twoColourTemperatures);

		// fit a Gaussian to the histogram
		Slice histogramSlice = new Slice(0, 12);
		Dataset histogramBins = histogramResults.get(1).getSlice(histogramSlice);

		// shift the bin edges by half a bin width to make fitting work
		double histogramHalfBinWidth = (histogramBins.getDouble(1) - histogramBins.getDouble(0)) / 2;
		histogramBins = Maths.add(histogramBins, histogramHalfBinWidth);

		Dataset histogramCounts = histogramResults.get(0);
		histogramCounts.setName("STCP counts");


		// Set up a place to place the fitting parameters
		Gaussian gaussianFit = new Gaussian();
		gaussianFit.setParameterValues(planckTemperature, 50, 1);


		// Try to do the fitting of the histogram with a gaussian
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {histogramBins}, histogramCounts, gaussianFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing STCP fit in PyrometryOperation(): " + fittingError.toString());
			throw new OperationException(this, "error performing gaussian fit" + fittingError.toString());
		}

		gaussianFit.setName("STCP");


		// get the parameters out of the gaussianFit object
		double twoColourTemperature = gaussianFit.getParameterValue(0);
		double twoColourFwhm = gaussianFit.getParameterValue(1);
		double twoColourArea = gaussianFit.getParameterValue(2);
		double twoColourStdDev = histogramCounts.stdDeviation();

		Dataset fittedGaussian;
		if (Double.isFinite(twoColourTemperature) && Double.isFinite(twoColourFwhm) && Double.isFinite(twoColourArea)) {
			fittedGaussian = gaussianFit.calculateValues(histogramBins);
		} else {
			fittedGaussian = DatasetFactory.zeros(histogramBins);
		}

		fittedGaussian.setName("Gaussian Fit");


		// add two colour outputs to log
		rSquared = calculateRSquared(histogramCounts, fittedGaussian);
		logpane.append("Sliding Two Colour Pyrometry -- R² = %.4f", rSquared);
		logpane.append("Temperature (T) = %.2f K" , twoColourTemperature);
		logpane.append("Temperature Standard Deviation = %.2f", twoColourStdDev);
		logpane.append("Temperature Gaussian Half Width = %.2f K", twoColourFwhm);
		logpane.append("Temperature Gaussian Sigma = %.2f K", twoColourFwhm/2.355); // 2 root 2 ln 2

		// also the logger
		logger.debug("STCP T:{}, σ:{}, R²:{}",wienTemperature, twoColourFwhm/2.355, rSquared);

		//////////////////////////////////////////////////////////////////////////////
		// Formulate the outputs

		// Create an operation data object but also displaying the values of interest
		OperationDataForDisplay returnDataWithDisplay = new OperationDataForDisplay();
		planckFittedYaxis.setName("Planck Fit");
		wienFittedYaxis.setName("Wien Fit");
		planckYSlice.setName("Planck Observed");
		wienYSlice.setName("Wien Observed");
		histogramBins.setName("Temperature Bin");
		MetadataUtils.setAxes(planckFittedYaxis, Maths.multiply(planckXSlice, 1e9));
		MetadataUtils.setAxes(wienFittedYaxis, wienXSlice);
		MetadataUtils.setAxes(this, calibrated, xAxis);
		MetadataUtils.setAxes(planckYSlice, Maths.multiply(planckXSlice,1e9)); // want to display in nm
		MetadataUtils.setAxes(wienYSlice, wienXSlice);
		MetadataUtils.setAxes(histogramCounts, histogramBins);
		MetadataUtils.setAxes(fittedGaussian, histogramBins);


		// The output data to display in the plot
		List<IDataset> displayData = new ArrayList<>();
		OperationToDisplay figureChoice = model.getOperationSelected();

		switch (figureChoice) {
		case PLANCK:		displayData.add(planckYSlice);
		displayData.add(planckFittedYaxis);
		break;
		case WIEN:			displayData.add(wienYSlice);
		displayData.add(wienFittedYaxis);
		break;
		case TWOCOLOUR:		displayData.add(histogramCounts);
		displayData.add(fittedGaussian);
		break;
		default:			OperationException unexpectedSelectionException = new OperationException(this,"unexpected figure choice");
		throw unexpectedSelectionException;
		}


		// Then set up the operation data object, getting it ready to return everything
		returnDataWithDisplay.setShowSeparately(true);
		returnDataWithDisplay.setLog(logpane);
		returnDataWithDisplay.setData(input);
		returnDataWithDisplay.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));

		Dataset planckTemperatureDataset = DatasetFactory.createFromObject(planckTemperature);
		planckTemperatureDataset.setName("Planck Temperature");
		Dataset wienTemperatureDataset = DatasetFactory.createFromObject(wienTemperature);
		wienTemperatureDataset.setName("Wien Temperature");
		Dataset twoColourTemperatureDataset = DatasetFactory.createFromObject(twoColourTemperature);
		twoColourTemperatureDataset.setName("Two Colour Temperature");
		Dataset twoColourFwhmDataset = DatasetFactory.createFromObject(twoColourFwhm);
		twoColourFwhmDataset.setName("Two Colour FWHM");
		Dataset twoColourStdDevDataset = DatasetFactory.createFromObject(twoColourStdDev);
		twoColourStdDevDataset.setName("Two Colour StdDev");


		// add the various auxiliary results to the return object
		returnDataWithDisplay.setAuxData(planckTemperatureDataset
				, wienTemperatureDataset
				, twoColourTemperatureDataset
				, twoColourStdDevDataset
				, twoColourFwhmDataset
				);

		return returnDataWithDisplay;
	}

	public Dataset[] prepareDataForFitting(Dataset xData, Dataset yData, double[] regionOfInterest) {
		// Create some placeholders
		int startIndex = 0;
		int endIndex = 0;

		// Just to make sure the indexing is right, lowest number first
		if (regionOfInterest[0] < regionOfInterest[1]) {
			startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xData, regionOfInterest[0]);
			endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xData, regionOfInterest[1]);
		} // Or we handle for this
		else {
			startIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xData, regionOfInterest[1]);
			endIndex = DatasetUtils.findIndexGreaterThanOrEqualTo(xData, regionOfInterest[0]);
		}

		// Slice the datasets down to the size of interest
		Slice sliceOfInterest = new Slice(startIndex, endIndex, 1);
		xData.getSlice(sliceOfInterest);
		yData.getSlice(sliceOfInterest);

		// return a list of Datasets
		return new Dataset[]{xData.getSlice(sliceOfInterest), yData.getSlice(sliceOfInterest)};

	}


	public Planck fitPlanckFunction() {

		// Set up a place to place the fitting parameters
		Planck planckFit = new Planck();
		planckFit.setParameterValues(1,3000);


		// Try to do the fitting on the new processed slices
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {planckXSlice}, planckYSlice, planckFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing Planck fit in PyrometryOperation(): " + fittingError.toString());
			throw new OperationException(this, "error performing gaussian fit" + fittingError.toString());
		}

		// Then return it
		return planckFit;
	}

	public StraightLine fitWienFunction() {

		// Set up a place to place the fitting parameters
		StraightLine straightLineFit = new StraightLine();
		straightLineFit.setParameterValues(5e-4, 500);

		// Try to do the fitting on the new processed slices
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {wienXSlice}, wienYSlice, straightLineFit);
		} catch (Exception fittingError) {
			System.err.println("Exception performing Wien fit in PyrometryOperation(): " + fittingError.toString());
			throw new OperationException(this, "error performing gaussian fit" + fittingError.toString());
		}

		// Then return it
		return straightLineFit;
	}

	public double convertToWien(double xAxis, double yAxis) {
		// xAxis is wavelength, yaxis is intensity
		// wiened = k / h / c * log(2 * pi * h * c**2 / wavelength**5) / intensity)
		// split constants into those outside of log (_a) and those inside (_b)

		double _c = Math.pow(xAxis, 5.0) * yAxis;

		return _a * Math.log(_b / _c);
	}

	public Dataset convertToWien(Dataset xAxis, Dataset yAxis) {
		// xAxis is wavelength, yaxis is intensity
		// wiened = k / h / c * log(2 * pi * h * c**2 / wavelength**5) / intensity)
		// split constants into those outside of log (_a) and those inside (_b)

		Dataset _c = Maths.multiply(Maths.power(xAxis, 5), yAxis);

		return Maths.multiply(_a, Maths.log(Maths.divide(_b, _c)));
	}


	public double calculateRSquared(Dataset observed, Dataset modelled) {
		// a simple calculation of coefficient of determination
		double residual = observed.residual(modelled);
		double mean = observed.mean(0).getDouble();
		double total = observed.residual(mean);

		return 1-(residual/total);
	}

	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.PyrometryOperation";
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ANY;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}
}
