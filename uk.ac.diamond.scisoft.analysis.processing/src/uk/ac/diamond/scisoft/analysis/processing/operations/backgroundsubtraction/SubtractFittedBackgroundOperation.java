/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction;

import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.SliceND;

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.processing.metadata.FitMetadataImpl;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractFittedBackgroundModel.BackgroundPixelPDF;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Subtract a background from the input dataset. The background is determined by fitting to a function
 */
public class SubtractFittedBackgroundOperation extends AbstractImageSubtractionOperation<SubtractFittedBackgroundModel> {

	private IFunction pdf;
	private double residual;
	private Dataset x;
	private Dataset h;
	private Dataset fit;
	private double threshold;
	private OperationLog log = new OperationLog();

	@Override
	public String getFilenameSuffix() {
		return "subtract_fitted_bg";
	}

	@Override
	public String getId() {
		return getClass().getName();
	}

	public static double findPositiveMin(Dataset d) {
		IndexIterator it = d.getIterator();
		double min = Double.POSITIVE_INFINITY;
		while (it.hasNext()) {
			double x = d.getElementDoubleAbs(it.index);
			if (x > 0 && x < min) {
				min = x;
			}
		}
		return min;
	}

	public static List<Dataset> createHistogram(Dataset in, boolean positive) {
		double min = positive ? findPositiveMin(in) : in.min(true).doubleValue();
		double max = in.max(true).doubleValue();
		int nbin = (int) Math.ceil(max - min);
		if (nbin == 0) {
			return null;
		}

		Dataset bins;
		if (in.hasFloatingPointElements() || nbin > SubtractFittedBackgroundModel.HISTOGRAM_MAX_BINS) {
			bins = DatasetFactory.createLinearSpace(DoubleDataset.class, min, max, nbin);
		} else {
			bins = DatasetFactory.createRange(IntegerDataset.class, min, max+1, 1);
		}

		Histogram histo = new Histogram(bins);
		return histo.value(in);
	}

	protected double calculateThreshold(Dataset in) throws OperationException {
		List<Dataset> hs = createHistogram(in, model.isPositiveOnly());
		if (hs == null) {
			return Double.NaN;
		}
		h = hs.get(0);
		h.setName("Histogram counts");
		x = hs.get(1).getSliceView(new Slice(-1));
		x.setName("Intensity values");
		pdf = prepareBackgroundPDF(x, h, model.getBackgroundPDF());

		// extract indexes
		IParameter p = pdf.getParameter(0);
		int b = (int) p.getLowerLimit();
		int e = (int) p.getUpperLimit();
		double cx = p.getValue();
		int c = (int) cx - 1;
		p.setLimits(x.getDouble(c) - 0.5, x.getDouble(c + 2)); // set narrow range for fitting pdf position
		p.setValue(cx);
		SliceND slice = new SliceND(h.getShapeRef(), new Slice(b, e + 1, 1));

		residual = fitFunction(this, pdf, x.getSliceView(slice), h.getSliceView(slice));
		log.append("\nFitted PDF in %s: residual = %g\n%s", slice, residual, pdf);

		fit = DatasetUtils.convertToDataset(pdf.calculateValues(x));
		fit.setName("Background fit");

		// find where given ratio occurs and is past peak position
		List<Double> cs = DatasetUtils.crossings(x, Maths.dividez(h, fit), model.getRatio());
		double thr = -1;
		for (Double d : cs) {
			if (d > cx) {
				thr = d;
				break;
			}
		}

		if (thr < 0) {
			throw new OperationException(this, "Failed to find a threshold");
		}
		return thr;
	}

	// this calculates a background to subtract from input
	@Override
	protected Dataset getImage(IDataset input) throws OperationException {
		Dataset in = DatasetUtils.convertToDataset(input);
		double thr = calculateThreshold(in);
		threshold = thr;
		if (Double.isNaN(thr)) {
			double min = in.min(true).doubleValue();
			log.append("No range in data. All finite values are %g", min);
			return DatasetFactory.createFromObject(min);
		}

		if (!in.hasFloatingPointElements()) {
			int ithr = (int) Math.floor(thr);
			log.append("Threshold = %d", ithr);
			return DatasetUtils.select(Comparisons.lessThan(in, ithr), in, ithr);
		}

		log.append("Threshold = %d", threshold);
		return DatasetUtils.select(Comparisons.lessThan(in, thr), in, thr);
	}

	static double fitFunction(IOperation<?, ?> op, IFunction fun, Dataset xf, IDataset hf) {
		double residual = Double.POSITIVE_INFINITY;
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {xf}, hf, fun);
			residual = opt.calculateResidual();
		} catch (Exception fittingError) {
			throw new OperationException(op, "Exception performing fit", fittingError);
		}
		return residual;
	}

	@Override
	public OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		log.clear();
		log.append("Subtract Fitted Background");
		log.append("==========================");

		OperationData op = super.process(input, monitor);

		// add fit parameters metadata to dataset to pass down chain
		OperationDataForDisplay od = new OperationDataForDisplay(op.getData());
		od.setShowSeparately(true);
		od.setLog(log);

		if (h != null) {
			setDisplayData(od, x, h, fit);
	
			// switch to use display data and record fit parameters as [1]-shape datasets
			// need two plots...
			od.setAuxData(ProcessingUtils.createNamedDataset(threshold, "threshold"),
					ProcessingUtils.createNamedDataset(pdf.getParameterValue(0), "pdf_posn"),
					ProcessingUtils.createNamedDataset(pdf.getParameterValue(1), "pdf_fwhm"),
					ProcessingUtils.createNamedDataset(pdf.getParameterValue(2), "pdf_area"),
					ProcessingUtils.createNamedDataset(residual, "residual"));
	
			od.getData().addMetadata(new FitMetadataImpl().setFitFunction(pdf));
		}

		return od;
	}

	// make display datasets here to be recorded in file
	public static void setDisplayData(OperationDataForDisplay od, Dataset x, Dataset h, Dataset fit) {
		Dataset a = Maths.log10(h);
		a.setName("Log10 of " + h.getName());
		Dataset b = Maths.log10(fit);
		b.setName("Log10 of " + fit.getName());
		// clamp values to above zero for nicer display 
		b.setByBoolean(0, Comparisons.lessThan(b, 0));
		MetadataUtils.setAxes(a, x);
		MetadataUtils.setAxes(b, x);
		od.setDisplayData(a, b);
	}

	private IFunction prepareBackgroundPDF(Dataset x, Dataset h, BackgroundPixelPDF pdfType) {
		IFunction pdf = null;
		switch (pdfType) {
		default:
		case Gaussian:
			log.append("Using Gaussian PDF");
			pdf = new Gaussian();
		}

		initializeFunctionParameters(x, h, pdf);
		log.append("Initial PDF:\n%s", pdf);
		return pdf;
	}

	private static void initializeFunctionParameters(Dataset x, Dataset h, IFunction pdf) {
		IParameter p = pdf.getParameter(0);
		double xb = x.getDouble(0);
		double dx = x.getDouble(1) - xb;
		int pMax = x.getInt(h.maxPos(true)); // position of maximum

		int pBeg = pMax - 1; // only fit from just before peak as background peak can be unresolved
		int pEnd = pMax;
		double hm = h.max(true).doubleValue();
		double hr = 1e-3 * hm; // set right threshold to determine fit interval
		for (int max = h.getSize() - 1; pEnd < max && h.getDouble(++pEnd) > hr;);
		p.setValue(pMax); // set these to indexes to allow slicing
		p.setLimits(pBeg, pEnd);

		p = pdf.getParameter(1);
		// estimate FWHM from crossings at HM and finding the crossing that is less than max x
		double xr = findFWHMPostMax(x, h, 0);
		p.setLimits(dx, 2*xr);
		p.setValue(xr);

		p = pdf.getParameter(2);
		// estimate area
		double t = dx * ((Number) h.sum(true)).doubleValue();
		p.setValue(t);
		p.setLimits(dx * hm, 2*xr * hm);
	}

	/**
	 * Find FWHM from values of y after maximum
	 * @param x
	 * @param y
	 * @return FWHM or NaN if not found
	 */
	public static double findFWHMPostMax(Dataset x, Dataset y, double base) {
		double ym = y.max(true).doubleValue();
		int pos = y.maxPos(true)[0];
		SliceND slice = new SliceND(x.getShapeRef(), new Slice(pos, null));
		List<Double> cs = DatasetUtils.crossings(x.getSliceView(slice), y.getSliceView(slice), (ym + base)*0.5);
		return cs.size() > 0 ? (cs.get(0) - x.getDouble(pos)) * 2 : Double.NaN;
	}
}
