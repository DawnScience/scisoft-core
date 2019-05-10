/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.processing.IOperation;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationLog;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.dawnsci.plotting.api.metadata.Plot1DMetadata;
import org.eclipse.dawnsci.plotting.api.metadata.Plot1DMetadataImpl;
import org.eclipse.dawnsci.plotting.api.trace.ILineTrace.PointStyle;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.BooleanIterator;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.ShapeUtils;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.Stats;
import org.eclipse.january.metadata.AxesMetadata;

import uk.ac.diamond.scisoft.analysis.dataset.function.Histogram;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CoordinatesIterator;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.processing.metadata.FitMetadata;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractFittedBackgroundOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Find and fit the RIXS elastic line image to a straight line and if scan then
 * calibrate energy dispersion
 * <p>
 * Records line fit parameters, peak FWHM as auxiliary data and also, energy dispersion if
 * can calibrate scan
 */
public class ElasticLineReduction extends RixsBaseOperation<ElasticLineReductionModel> {

	public static final String ES_PREFIX = "elastic_spectrum_";
	public static final String ESF_PREFIX = ES_PREFIX + "fit_";
	public static final String ESFWHM_PREFIX = ES_PREFIX + "fwhm_";
	public static final String ESAREA_PREFIX = ES_PREFIX + "area_";
	public static final String ESHEIGHT_PREFIX = ES_PREFIX + "height_";

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "RIXS elastic line reduction";
	/**
	 * Suffix for processed file
	 */
	public static final String SUFFIX = "elastic_line";

	private static int counter = -1;
	private List<IDataset> countedData = null; // new ArrayList<IDataset>();

	private double residual;
	private Dataset position; // scan position
	private boolean useSpectrum = true;
	private Dataset output;

	protected List<Double>[] allSlope = new List[] {new ArrayList<>(), new ArrayList<>()};
	protected List<Double>[] allPosition = new List[] {new ArrayList<>(), new ArrayList<>()};
	protected List<Double>[] allResidual = new List[] {new ArrayList<>(), new ArrayList<>()};
	protected List<Double>[] goodPosition = new List[] {new ArrayList<>(), new ArrayList<>()};
	protected List<Double>[] goodIntercept = new List[] {new ArrayList<>(), new ArrayList<>()};
	private List<Dataset>[] goodSpectra = new List[] {new ArrayList<>(), new ArrayList<>()};
	private List<Boolean>[] posSlope = new List[] {new ArrayList<>(), new ArrayList<>()};
	private String positionName;

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	@Override
	public String getFilenameSuffix() {
		return SUFFIX;
	}

	@Override
	void updateFromModel(boolean throwEx) {
		updateROICount();
		for (int r = 0; r < roiMax; r++) {
			if (lines[r] == null) {
				StraightLine l = lines[r] = new StraightLine();
				l.getParameter(0).setLimits(-model.getMaxSlope(), model.getMaxSlope());
			}
		}
	}

	private void createInvalidOperationData(int r, Exception e) {
		log.append("Operation halted!");
		log.append("%s", e);
		processFit(r, null, Double.NaN, Double.NaN, Double.NaN);
	}

	@Override
	protected void resetProcess(IDataset original) {
		allPosition[0].clear();
		allPosition[1].clear();
		allSlope[0].clear();
		allSlope[1].clear();
		allResidual[0].clear();
		allResidual[1].clear();
		goodPosition[0].clear();
		goodPosition[1].clear();
		goodIntercept[0].clear();
		goodIntercept[1].clear();
		goodSpectra[0].clear();
		goodSpectra[1].clear();
		posSlope[0].clear();
		posSlope[1].clear();

		// update lines parameters
		int[] shape = original.getShape();
		for (int r = 0; r < roiMax; r++) {
			IParameter intercept = lines[r].getParameter(1);
			intercept.setLimits(0, shape[model.getEnergyIndex()]); // FIXME decide which to set
			intercept.setUpperLimit(shape[0]);
		}

		counter = -1;
		if (countedData != null) {
			countedData.clear();
		}
	}

	@Override
	void initializeProcess(IDataset original) {
		log.append("Elastic Line Reduction");
		log.append("======================");

		// get position
		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);

		ILazyDataset ld = smd.getParent();
		AxesMetadata amd = ld.getFirstMetadata(AxesMetadata.class);
		ILazyDataset[] firstAxes = amd == null ? null : amd.getAxis(0);
		if (firstAxes == null || firstAxes.length == 0 || firstAxes[0] == null) {
			return;
		}
		positionName = firstAxes[0].getName();
		try {
			position = DatasetUtils.convertToDataset(smd.getMatchingSlice(firstAxes[0])).squeeze(true);
			log.append("Current position: %s", position.toString(true));
		} catch (Exception e) { // TODO remove NexusUtils
			// single point scans are incorrectly dealt with
			log.append("Could not get position: %s", e);
			int[] shape = smd.getSliceInfo().getInputSliceWithoutDataDimensions().getSourceShape();
			position = DatasetFactory.createRange(ShapeUtils.calcSize(shape)).reshape(shape);
		}
		if (position.getRank() == 0) {
			position.setShape(1);
		}
	}

	@Override
	boolean skipFrame(int size, int frame) {
		return false;
	}

	@Override
	public OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		output = null;
		OperationData od = super.process(input, monitor);
		IDataset data = od.getData();
		if (data != null) {
			SliceFromSeriesMetadata ssm = data.getFirstMetadata(SliceFromSeriesMetadata.class);
			data = DatasetFactory.zeros(1, 1);
			if (ssm != null) {
				data.setMetadata(ssm);
			}
			od.setData(data); // save storage space
		}

		// hold state in this object's super class then
		// then process when a running count matches 
		SliceFromSeriesMetadata ssm = getSliceSeriesMetadata(input);
		SliceInformation si = ssm.getSliceInfo();

		// aggregate aux data???
		int smax = si.getTotalSlices();
		log.appendSuccess("At frame %d/%d", si.getSliceNumber(), smax);

		// TODO make this live-friendly by show result per frame
		// needs to give fake results for first slice
		if (si.isLastSlice()) {
			summaryData.clear();

			double[] dispersion = new double[roiMax];
			for (int r = 0; r < roiMax; r++) {
//					if (goodPosition[r].size() <= 2) {
//						log.append("Not enough good lines (%d) found for ROI %d", goodPosition[r].size(), r);
//						continue;
//					}

				if (goodPosition[r].size() == 0) {
					log.appendFailure("No lines found for ROI %d", r);
					continue;
				}
				List<?>[] coords;
				if (useSpectrum) {
					coords = fitSpectraPeakPositions(r);
				} else {
					coords = new List<?>[] {goodPosition[r], goodIntercept[r]};
				}

				double aveM = 0;
				double goodC = Double.NaN;
				int n = 0;
				List<Double> slopes = allSlope[r];
				List<Double> posns = allPosition[r];
				for (int i = 0, imax = slopes.size(); i < imax; i++) {
					Double a = slopes.get(i);
					if (Double.isFinite(a)) {
						n++;
						aveM += a;
					}
					if (Double.isNaN(goodC)) {
						a = posns.get(i);
						if (Double.isFinite(a)) {
							goodC = a;
						}
					}
				}

				auxData.add(ProcessingUtils.createNamedDataset(aveM/n, LINE_GRADIENT_FORMAT, r).reshape(1));
				auxData.add(ProcessingUtils.createNamedDataset(goodC, LINE_INTERCEPT_FORMAT, r));

				Dataset posData = DatasetFactory.createFromList(coords[0]);
				posData.setName(positionName);
				if (n > 1) {
					Dataset d = ProcessingUtils.createNamedDataset((Serializable) slopes, LINE_GRADIENT_FORMAT, r);
					MetadataUtils.setAxes(d, posData);
					summaryData.add(d);
					d = ProcessingUtils.createNamedDataset((Serializable) posns, LINE_INTERCEPT_FORMAT, r);
					MetadataUtils.setAxes(d, posData);
					summaryData.add(d);
				}
				summaryData.add(ProcessingUtils.createNamedDataset((Serializable) allResidual[r], LINE_RESIDUAL_FORMAT, r));

				if (positionName.contains("energy")) {
					if (smax != 1) { // display when there is only one image
						displayData.clear();
					}

					double[] res = fitIntercepts(r, posData.getView(false), coords[1]);
					if (res == null) {
						dispersion[r] = Double.NaN;
						log.appendFailure("No energy change so cannot find dispersion");
					} else {
						dispersion[r] = 1./res[1];
						log.appendSuccess("Dispersion is %g for residual %g", dispersion[r], res[0]);
					}
				}
			}

			output = ProcessingUtils.createNamedDataset(dispersion, "energy_dispersion").reshape(1, roiMax);
			copyMetadata(input, output);
			output.clearMetadata(AxesMetadata.class);
			output.clearMetadata(SliceFromSeriesMetadata.class);
			SliceFromSeriesMetadata outssm = ssm.clone();
			for (int i = 0, imax = ssm.getParent().getRank(); i < imax; i++) {
				if (!outssm.isDataDimension(i)) {
					outssm.reducedDimensionToSingular(i);
				}
			}
			output.setMetadata(outssm);
		}

		OperationDataForDisplay odd;
		if (od instanceof OperationDataForDisplay) {
			odd = (OperationDataForDisplay) od;
		} else {
			odd = new OperationDataForDisplay(od.getData());
		}
		odd.setShowSeparately(true);
		odd.setLog(log);
		odd.setData(output);
		odd.setDisplayData(displayData.toArray(new IDataset[displayData.size()]));
		odd.setAuxData(auxData.toArray(new Serializable[auxData.size()]));
		odd.setSummaryData(summaryData.toArray(new Serializable[summaryData.size()]));

		return odd;
	}

	@Override
	IDataset processImageRegion(int r, IDataset original, Dataset in) {
		double requiredPhotons = countsPerPhoton * model.getMinPhotons(); // count per photon

		// check if image has sufficient signal: anything less than 100 photons is insufficient
		double sum = ((Number) in.sum()).doubleValue();
		log.append("Number of photons, estimated: %g", sum / countsPerPhoton);
		if (sum < requiredPhotons) {
			createInvalidOperationData(r, new OperationException(this, "Not enough signal for elastic line"));
			return original;
		}

		Double slope = model.getSlopeOverride();
		if (slope != null) {
			findIntercept(r, in, slope);
		} else {
			int delta = model.getDelta();
			int[] shape = original.getShape();
			if (delta != 0) {
				extractPointsAndFitLine(r, shape, in, delta, true);
			} else {
				minimizeFWHMForSpectrum(r, shape, in);
			}
		}
		StraightLine line = getStraightLine(r);
		processFit(r, in, line.getParameterValue(STRAIGHT_LINE_M), line.getParameterValue(STRAIGHT_LINE_C), residual);
		return original;
	}

	private void findIntercept(int r, Dataset in, double slope) {
		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
		Dataset s = sumImageAlongSlope(in, slope, false);
		DoubleDataset x = DatasetFactory.createRange(s.getSize());
		double factor = model.getPeakFittingFactor();
		double w = findFWHM(countedData, opt, peak, factor, x, s);
		StraightLine line = getStraightLine(r);
		line.getParameter(STRAIGHT_LINE_M).setValue(slope);
		line.getParameter(STRAIGHT_LINE_C).setValue(peak.getParameterValue(SPECTRA_PEAK_POSN));
		log.append("Override with slope (%g) gives width = %g and line %s", slope, w, line);
		residual = 0;
	}

	private void extractPointsAndFitLine(int r, int[] shape, Dataset in, int delta, boolean display) {
		Dataset[] coords = null;

		try {
			int minPoints = model.getMinPoints();
			if (delta == 1) {
				coords = processPerRowMax(in);
				minPoints = Math.max(minPoints, coords[0].getSize() / 20); // override to use at least 5%
				log.append("Using %d as minimum points", minPoints);
			} else {
				coords = processBySummedRows(r, in);
			}

			// shift coords
			coords[0].iadd(offset[0]);
			coords[1].iadd(offset[1]);

			// fit, prune and refit
			int ymax = shape[model.getEnergyIndex()];
			BooleanDataset mask;
			mask = fitStraightLine(getStraightLine(r), ymax, minPoints, model.getMaxDev(), coords[0], coords[1]);
			if (display && r == 0) {
				coords[0] = coords[0].getByBoolean(mask);
				coords[1] = coords[1].getByBoolean(mask);
				coords[0].setName("row0");
				coords[1].setName("col0");
				Plot1DMetadataImpl pm = new Plot1DMetadataImpl(Plot1DMetadata.LineStyle.NONE, 0, PointStyle.CIRCLE, 4);
				pm.setPlotTitle("Slope fitting");
				pm.setLegendEntry("Elastic maxima");
				pm.setXAxisName("y");
				pm.setYAxisName("x");
				coords[1].addMetadata(pm);
				generateFitForDisplayAndSummary(getStraightLine(0), coords[0], coords[1], "line_0_fit");
			}
		} catch (OperationException e) {
			createInvalidOperationData(r, e);
			if (display && r == 0) {
				coords[0].setName("row0");
				coords[1].setName("col0");
				generateFitForDisplayAndSummary(null, coords[0], coords[1], null);
			}
		}
	}

	private static boolean USE_EVAL = false;

	private void minimizeFWHMForSpectrum(int r, int[] shape, Dataset in) {
		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);

		Dataset s = sumImageAlongSlope(in, 0, false);
		DoubleDataset x = DatasetFactory.createRange(s.getSize());
		double factor = model.getPeakFittingFactor();
		double w = findFWHM(countedData, opt, peak, factor, x, s);
		int pos = (int) Math.round(peak.getParameterValue(SPECTRA_PEAK_POSN));

		// work out interval and make some aux data of FWHMs
		Slice slice = getCropSlice(x, pos, factor * w * 4);
		log.append("Raw spectrum has max at %d with FWHM of %g", pos, w);
		log.append("Slice interval for peak is %s", slice);

		Dataset sin = in.getSliceView((Slice) null, slice);
		Dataset sx = x.getSliceView(slice);
//		sin.setName("subimage");
//		auxData.add(sin);

		FWHMForSpectrum fn = new FWHMForSpectrum(opt, sx, sin, peak, factor);
		IParameter slope = fn.getParameter(SPECTRAL_SLOPE);

		if (USE_EVAL) {
			setSlopeFitParametersByEval(r, peak, opt, factor, sin, sx, slope);
		} else {
			setSlopeFitParametersByFittingLine(r, peak, opt, factor, sin, sx, slope, shape, sin);
		}

		log.append("Initial parameter settings: %g [%g, %g]", slope.getValue(), slope.getLowerLimit(), slope.getUpperLimit());
		ApacheOptimizer optimizer = new ApacheOptimizer(Optimizer.SIMPLEX_NM);
		try {
			optimizer.optimize(true, fn);
			StraightLine line = getStraightLine(r);
			line.getParameter(STRAIGHT_LINE_M).setValue(slope.getValue());
			line.getParameter(STRAIGHT_LINE_C).setValue(peak.getParameterValue(SPECTRA_PEAK_POSN));

			residual = peak.residual(true, sumImageAlongSlope(sin, slope.getValue(), false), null, sx);
			log.appendSuccess("Optimized minimal FWHM (%g) at slope of %g", peak.getParameterValue(SPECTRA_PEAK_WIDTH), slope.getValue());
		} catch (Exception e) {
			log.appendFailure("Error minimizing FWHM for peak in spectrum: %s", e.getMessage());
			throw new OperationException(this, "Error minimizing FWHM for peak in spectrum", e);
		}
	}

	private void setSlopeFitParametersByEval(int r, Add peak, ApacheOptimizer opt, double factor, Dataset sin, Dataset sx,
			IParameter slope) {
		int n = 16;
		Dataset slopes = DatasetFactory.createRange(2*n+1);
		slopes.isubtract(n+1).imultiply(model.getMaxSlope()/n);
		slopes.setName("slope");
		Dataset summed = DatasetFactory.zeros(n, sin.getShapeRef()[1]);
		Dataset fitted = DatasetFactory.zeros(summed);
		DoubleDataset fwhm = DatasetFactory.zeros(n);
		fwhm.setName("fwhm");
		for (int i = 0; i < n; i++) {
			double m = slopes.getDouble(i);
			Dataset ns = sumImageAlongSlope(sin, m, false);
			fwhm.setItem(findFWHM(countedData, opt, peak, factor, sx, ns), i);
			Slice nSlice = new Slice(i, i+1);
			summed.setSlice(ns, nSlice);
			fitted.setSlice(peak.calculateValues(sx), nSlice);
		}

		if (countedData != null) {
			String prefix = String.format("%04d_", ++counter);
			Dataset tf = fwhm.getView(false);
			tf.setName(prefix + "tilted_fwhm");
			countedData.add(tf);
			auxData.addAll(countedData);
		}

		int pmin = fwhm.argMin(true);
		log.append("Minimal FWHM at slope of %g ", slopes.getDouble(pmin));
		fwhm.setName("tilted_profile_width_" + r);
		MetadataUtils.setAxes(fwhm, slopes);
		auxData.add(fwhm.getView(true));
		if (r == 0) {
			displayData.add(fwhm);
		}
		summed.setName("tilted_profile_" + r);
		MetadataUtils.setAxes(summed, slopes);
		auxData.add(summed);
		fitted.setName("tilted_profile_fit_" + r);
		MetadataUtils.setAxes(fitted, slopes);
		auxData.add(fitted);

		// crop limits to turning point of minimum
		Dataset diff = Maths.derivative(DatasetFactory.createRange(n), fwhm, 3);
		log.append("FWHM derivative: %s", diff.toString(true));
		int imin = pmin;
		double last = diff.getDouble(imin);
		while (--imin > 1) {
			double d = diff.getDouble(imin);
			if (d >= last) {
				imin++;
				break;
			}
			last = d;
		}
		int imax = diff.getSize();
		if (imax < 0) {
			throw new OperationException(this, "No turning points found in FWHM");
		}
		last = diff.getDouble(pmin);
		for (int i = pmin + 1; i < imax; i++) {
			double d = diff.getDouble(i);
			if (d <= last) {
				imax = i;
				break;
			}
			last = d;
		}

		slope.setLimits(slopes.getDouble(imin), slopes.getDouble(imax));
		slope.setValue(slopes.getDouble(pmin));
	}

	private void setSlopeFitParametersByFittingLine(int r, Add peak, ApacheOptimizer opt, double factor, Dataset sin, Dataset sx,
			IParameter slope, int[] shape, Dataset in) {
		extractPointsAndFitLine(r, shape, in, 1, false);
		StraightLine line = getStraightLine(r);

		double s = line.getParameterValue(STRAIGHT_LINE_M);
		Dataset ns = sumImageAlongSlope(sin, s, false);
		double w = findFWHM(null, opt, peak, factor, sx, ns);

		// find slope limits around fitted line
		List<Double> slopes = new ArrayList<>();
		List<Double> fwhm = new ArrayList<>();
		double d = Math.min((s == 0 ? model.getMaxSlope() : Math.abs(s)), 1./64)/8;
		double min = s;
		double last;
		double tw = w;
		do {
			slopes.add(0, min);
			fwhm.add(0, tw);
			last = tw;
			min -= d;
			ns = sumImageAlongSlope(sin, min, false);
			tw = findFWHM(null, opt, peak, factor, sx, ns);
		} while (tw >= last);

		double max = s;
		tw = w;
		do {
			last = tw;
			max += d;
			ns = sumImageAlongSlope(sin, max, false);
			tw = findFWHM(null, opt, peak, factor, sx, ns);
			slopes.add(max);
			fwhm.add(tw);
		} while (tw >= last);

		fwhm.remove(fwhm.size() - 1);
		Dataset widths = DatasetFactory.createFromList(fwhm);
		widths.setName("fwhm");
		MetadataUtils.setAxes(widths, DatasetFactory.createFromList(slopes));
		displayData.add(widths);
		slope.setLimits(min, max);
		slope.setValue(s);
	}

	private static final int SPECTRAL_SLOPE = 0;

	private class FWHMForSpectrum extends AFunction {
		private static final long serialVersionUID = 5751477494591603033L;
		final Dataset image;
		private IFunction peak;
		private Dataset rx;
		private double factor;
		private ApacheOptimizer opt;

		public FWHMForSpectrum(ApacheOptimizer opt, Dataset rx, Dataset image, IFunction peak, double factor) {
			super(1);
			this.opt = opt;
			this.image = image;
			this.peak = peak;
			this.rx = rx;
			this.factor = factor;
		}

		@Override
		public double val(double... values) {
			double x = getParameterValue(SPECTRAL_SLOPE);
			Dataset ns = sumImageAlongSlope(image, x, false);
			double f = findFWHM(null, opt, peak, factor, rx, ns);
			return f;
		}

		@Override
		protected void setNames() {
			setNames("FWHM", "Full-width half maximum when summing along given slope", "slope");
		}

		@Override
		public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
			double f = val();
			it.reset();
			int i = 0;
			double[] buffer = data.getData();
			while (it.hasNext()) {
				buffer[i++] = f;
			}
		}
	}

	/**
	 * Find full-width at half-maximum from fitting a peak
	 * @param savedData
	 * @param opt
	 * @param peak
	 * @param factor 
	 * @param x dataset
	 * @param y dataset
	 * @return FWHM
	 */
	public static double findFWHM(List<IDataset> savedData, ApacheOptimizer opt, IFunction peak, double factor, Dataset x, Dataset y) {
		initializeSpectrumFunctionParameters(null, peak, x, y);

		Slice s = getCropSlice(x, peak.getParameterValue(SPECTRA_PEAK_POSN), factor * peak.getParameterValue(SPECTRA_PEAK_WIDTH));
		x = x.getSliceView(s);
		y = y.getSliceView(s);

		try {
			fitFunction(null, opt, "Exception for FWHM fit in optimizer", null, peak, x, y, null);
		} catch (Exception ex) {
			return Double.POSITIVE_INFINITY;
		}

		if (savedData != null) {
			String prefix = String.format("%03d_", ++counter);
			y.setName(prefix + "tilted_data");
			savedData.add(y);
			IDataset ny = peak.calculateValues(x);
			ny.setName(prefix + "tilted_fit");
			savedData.add(ny);
		}
		return peak.getParameterValue(SPECTRA_PEAK_WIDTH);
	}

	// crop data to just peak to avoid clipping artefact influencing fit
	private static Slice getCropSlice(Dataset x, double posn, double width) {
		Monotonicity m = Comparisons.findMonotonicity(x);
		boolean isIncreasing = m.equals(Monotonicity.NONDECREASING) || m.equals(Monotonicity.STRICTLY_INCREASING);
		int p = x.getNDPosition(isIncreasing ? DatasetUtils.findIndexGreaterThanOrEqualTo(x, posn) : DatasetUtils.findIndexLessThan(x, posn))[0];
		if (!isIncreasing) {
			width = -width;
		}
		double xl = x.getDouble(p) - width;
		int b = p;
		while (--b > 0) {
			if (x.getDouble(b) < xl) {
				break;
			}
		}
		xl = x.getDouble(p) + width;
		int e = p;
		int size = x.getSize() - 1;
		while (++e < size) {
			if (x.getDouble(e) > xl) {
				break;
			}
		}
		return new Slice(b, e);
	}

	private void processFit(int i, Dataset in, double m, double c, double r) {
		allSlope[i].add(m);
		allPosition[i].add(c);
		allResidual[i].add(r);
		if (Double.isFinite(c)) {
			goodPosition[i].add(position == null ? 0 : position.getDouble());
			goodIntercept[i].add(c);

			if (useSpectrum && in != null) {
				Dataset spectrum = makeSpectrum(in, m, model.isClipSpectra());
				spectrum.setName(ES_PREFIX + i);
				goodSpectra[i].add(spectrum);
				posSlope[i].add(m > 0);
			}
		}
	}

	public final static String LINE_GRADIENT_FORMAT  = "line_%d_m";
	public final static String LINE_INTERCEPT_FORMAT = "line_%d_c";
	public final static String LINE_RESIDUAL_FORMAT = "line_%d_residual";

	public final static Pattern LINE_REGEXP = Pattern.compile("line_(\\d+)_([mc])");

	/**
	 * Fit to intercepts of elastic lines
	 * @param r
	 * @param energy 
	 * @param coords
	 * @return residual and gradient or null if energy does not change
	 */
	private double[] fitIntercepts(int r, Dataset energy, List<?> coords) {
		if (coords.size() <= 2) {
			return null;
		}

		Dataset intercept = DatasetFactory.createFromList(coords);
		String name = "intercept_fit_" + r;
		energy.setName("Energy");
		intercept.setName("intercept_" + r);

		double ePTP = energy.peakToPeak().doubleValue();
		if (ePTP == 0) {
			MetadataUtils.setAxes(intercept, energy);
			summaryData.add(intercept);
			return null;
		}

		double grad = intercept.peakToPeak().doubleValue() / ePTP;
		double gMax = 2*Math.abs(grad);
		StraightLine iLine = new StraightLine(-gMax, gMax, -Double.MAX_VALUE, Double.MAX_VALUE);
		iLine.setParameterValues(grad, 0);

		double res = fitFunction(this, new ApacheOptimizer(Optimizer.SIMPLEX_NM), "Exception for intercept fit to find dispersion", log, iLine, energy, intercept, null);
		generateFitForDisplayAndSummary(iLine, energy, intercept, name);
		return new double[] {res, iLine.getParameterValue(STRAIGHT_LINE_M)};
	}

	private static final int SPECTRA_PEAK_POSN = 0;
	private static final int SPECTRA_PEAK_WIDTH = 1;
	private static final int SPECTRA_PEAK_AREA = 2;
	private static final int SPECTRA_PEAK_OFFSET = 3;

	private List<?>[] fitSpectraPeakPositions(int r) {
		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

		List<Double> bPosition = new ArrayList<>();
		List<Double> gPosition = new ArrayList<>();
		List<Double> gPosn = new ArrayList<>();
		List<Double> gFWHM = new ArrayList<>();
		List<Double> gArea = new ArrayList<>();
		List<Double> positions = goodPosition[r];

		List<Dataset> gSpectra = goodSpectra[r];
		int ns = gSpectra.size();
		Dataset spectrum = null;
		Dataset bSpectrum = null;
		Dataset gAxis = null;
		Dataset gSpectrum = null;
		Dataset gSpectrumFit = null;

		// as clipped spectra have differing sizes, we need to find common sections
		int minSize = Integer.MAX_VALUE;
		if (model.isClipSpectra()) {
			for (int i = 0; i < ns; i++) {
				minSize = Math.min(minSize, gSpectra.get(i).getSize());
			}
		} else if (ns > 0) {
			minSize = gSpectra.get(0).getSize();
		}
		DoubleDataset x = null;
		if (ns > 0) {
			x = DatasetFactory.createRange(minSize);
			x.setName("Energy Index");
		}
		int[] minShape = new int[] {1, minSize};
		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
		for (int i = 0; i < ns; i++) {
			spectrum = gSpectra.get(i);
			Dataset tx = x;
			AxesMetadata am = spectrum.getFirstMetadata(AxesMetadata.class);
			if (am != null) {
				try {
					tx = DatasetUtils.sliceAndConvertLazyDataset(am.getAxes()[0]);
				} catch (DatasetException e1) {
					// do nothing
				}
			}
			int size = spectrum.getSize();
			if (size > minSize) {
				Slice cs = posSlope[r].get(i) ? new Slice(minSize) : new Slice(size - minSize, null);
				spectrum = spectrum.getSliceView(cs);
				tx = tx.getSliceView(cs);
			}
			log.append("Fitting elastic peak: %d/%d", i, ns);

			findFWHM(null, opt, peak, model.getPeakFittingFactor(), tx, spectrum);
			double res = opt.calculateResidual();
			System.err.println("Peak " + i + " fit is " + peak + " with residual " + res);
			if (Double.isFinite(res)) {
				gPosition.add(positions.get(i));
				gPosn.add(peak.getParameterValue(SPECTRA_PEAK_POSN));
				gFWHM.add(peak.getParameterValue(SPECTRA_PEAK_WIDTH));
				gArea.add(peak.getParameterValue(SPECTRA_PEAK_AREA));
				DoubleDataset fit = peak.calculateValues(tx);
				if (gSpectrum == null) {
					if (tx != x) {
						gAxis = tx.clone().reshape(minShape);
					}
					gSpectrum = spectrum.clone().reshape(minShape);
					gSpectrum.clearMetadata(AxesMetadata.class);
					gSpectrumFit = fit.reshape(minShape);
				} else {
					if (gAxis != null) {
						gAxis = DatasetUtils.concatenate(new IDataset[] {gAxis, tx.reshape(minShape)}, 0);
					}
					gSpectrum = DatasetUtils.concatenate(new IDataset[] {gSpectrum, spectrum.reshape(minShape)}, 0);
					gSpectrumFit = DatasetUtils.concatenate(new IDataset[] {gSpectrumFit, fit.reshape(minShape)}, 0);
				}
			} else {
				log.append("Fitting elastic peak: FAILED");
				bPosition.add(positions.get(i));
				if (bSpectrum == null) {
					bSpectrum = spectrum.clone().reshape(minShape);
				} else {
					bSpectrum = DatasetUtils.concatenate(new IDataset[] {bSpectrum, spectrum.reshape(minShape)}, 0);
				}
			}
		}

		if (gPosition.size() > 0) {
			Dataset ge = DatasetFactory.createFromList(gPosition);
			ge.setName(positionName);
			gSpectrum.setName(ES_PREFIX + r);
			if (gAxis != null) {
				gAxis.setName("Pixels");
			}
			MetadataUtils.setAxes(gSpectrum, ge, gAxis);
			summaryData.add(gSpectrum);
			gSpectrumFit.setName(ESF_PREFIX + r);
			MetadataUtils.setAxes(gSpectrumFit, ge, gAxis);
			summaryData.add(gSpectrumFit);
			Dataset gf = DatasetFactory.createFromList(gFWHM);
			gf.setName(ESFWHM_PREFIX + r);
			MetadataUtils.setAxes(gf, ge);
			summaryData.add(gf);
			Dataset ga = DatasetFactory.createFromList(gArea);
			ga.setName(ESAREA_PREFIX + r);
			MetadataUtils.setAxes(ga, ge);
			summaryData.add(ga);

			List<Double> gHeight = new ArrayList<>();
			for (int i = 0, imax = ga.getSize(); i < imax; i++) {
				gHeight.add(Gaussian.calcHeight(ga.getDouble(i), gf.getDouble(i)));
			}
			Dataset gh = DatasetFactory.createFromList(gHeight);
			gh.setName(ESHEIGHT_PREFIX + r);
			MetadataUtils.setAxes(gh, ge);
			summaryData.add(gh);
		}
		if (bPosition.size() > 0) {
			Dataset be = DatasetFactory.createFromList(bPosition);
			be.setName(positionName);
			bSpectrum.setName("bad_elastic_spectrum_" + r);
			MetadataUtils.setAxes(bSpectrum, be);
			summaryData.add(bSpectrum);
		}

		return new List<?>[] {gPosition, gPosn};
	}

	protected void generateFitForDisplayAndSummary(IFunction f, Dataset x, Dataset d, String name) {
		MetadataUtils.setAxes(d, x);
		displayData.add(d.getView(false));
		summaryData.add(d);
		if (f == null) {
			return;
		}

		Dataset fit = DatasetUtils.convertToDataset(f.calculateValues(x));
		fit.setName(name);
		MetadataUtils.setAxes(fit, x);
		summaryData.add(fit);
		fit = fit.getView(false);

		// set plot title again as more lines will overwrite it
		Plot1DMetadata pm = d.getFirstMetadata(Plot1DMetadata.class);
		String pt = pm == null ? null : pm.getPlotTitle();
		if (pt != null) {
			pm = new Plot1DMetadataImpl();
			pm.setPlotTitle(pt);
			fit.setMetadata(pm);
		}
		displayData.add(fit);
	}

	/**
	 * Get coords of maximum pixel for each row
	 * @param image
	 * @return coords
	 */
	private Dataset[] processPerRowMax(Dataset image) {
		log.append("Process each line");
		Dataset max = image.argMax(1, true);
		BooleanDataset mask = Comparisons.greaterThan(image.max(1), 0);
		int peaks = ((Number) mask.sum()).intValue();
		log.append("Non-zero max are %d cf non-zero maxpos = %d", peaks, Comparisons.greaterThan(max, 0).sum());
		BooleanIterator it = max.getBooleanIterator(mask);
		IntegerDataset r = DatasetFactory.zeros(IntegerDataset.class, peaks);
		IntegerDataset c = DatasetFactory.zeros(IntegerDataset.class, peaks);

		int i = 0;
		while (it.hasNext()) {
			r.setItem(it.index, i);
			c.setItem((int) max.getElementLongAbs(it.index), i++);
		}

		if (canPruneMax(image)) {
			Dataset v = DatasetFactory.zeros(image.getClass(), peaks);
			for (int j = 0; j < peaks; j++) {
				v.setObjectAbs(j, image.getObject(j, c.get(j)));
			}

			int lo = 65;
			int hi = model.isUseCutoff() ? 100 : 98; // to eliminate cosmic ray zingers
			double[] qs = Stats.quantile(v, lo/100., hi/100.);
			log.append("Using value cutoffs at %d%% (%g) and at %d%% (%g)", lo, qs[0], hi, qs[1]);
			mask = Comparisons.withinRange(v, qs[0], qs[1]);

			r = (IntegerDataset) r.getByBoolean(mask);
			c = (IntegerDataset) c.getByBoolean(mask);
			log.append("Using %d of %d points", r.getSize(), peaks);
		}

//		log.append("Cols stats: mean = %g; outliers at %s", ((Number) c.mean()).doubleValue(), Arrays.toString(Stats.outlierValues(c, 5, 95, peaks/10)));

		return new Dataset[] {r, c};
	}

	private boolean canPruneMax(Dataset image) {
		try {
			List<FitMetadata> fms = image.getMetadata(FitMetadata.class);
			if (fms != null) {
				for (FitMetadata fm : fms) {
					if (fm.getOperationClass().equals(SubtractFittedBackgroundOperation.class)) {
						return false;
					}
				}
			}
		} catch (MetadataException e) {
		}

		return true;
	}

	private int[] stripSizes = new int[] {-1, -1};

	/**
	 * Sum rows to form approximate form of elastic spectrum then fit to find maxima
	 * and use them as coords
	 * @param r 
	 * @param image
	 * @return coords
	 */
	private Dataset[] processBySummedRows(int r, Dataset image) {
		log.append("Process summed lines");
		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

		int[] shape = image.getShapeRef();
		int xmax = shape[1];
		DoubleDataset x = DatasetFactory.createRange(xmax);
		x.setName("Energy Index");

		Dataset v = image.sum(0);
		log.append("Sum %s/%s", v.sum(), v.toString(true));
		initializeSpectrumFunctionParameters(this, peak, x, v);
		log.append("Initial peak:\n%s", peak);

		// fit entire image so as to initialise per summed row fits
		fitFunction("Exception for image profile fit", peak, x, v, null);

//		generateFitForDisplay(peak, x, v);
		double[] ip = peak.getParameterValues();
		int mean = (int) Math.floor(ip[0]);
		int width = Math.max(3, (int) (3 * Math.ceil(ip[1])));
		Slice interval = new Slice(Math.max(0, mean - width), Math.min(xmax, mean + width + 1));
		int stripSize = stripSizes[r];
		if (stripSize < 0) { // save first strip size
			stripSize = stripSizes[r] = interval.getNumSteps();
		}

		log.append("Using line slice %s for finding peak", interval);
		int delta = model.getDelta();
		int rows = shape[0];
		int strips = rows/delta;
		ip[2] /= strips; // adjust parameters for area and offset
		ip[3] /= strips;
		DoubleDataset row = DatasetFactory.createRange(DoubleDataset.class, strips).imultiply(delta).iadd(delta/2.0);
		DoubleDataset col = DatasetFactory.zeros(DoubleDataset.class, strips);
		Dataset xSlice = x.getSliceView(interval);
		Dataset allStrips = null;
		for (int i = 0, j = 0; i < strips; i++, j += delta) {
			Slice s = new Slice(j, j + delta);
			log.append("Summing %s", s);
			peak.setParameterValues(ip); // reset initial parameter values
			Dataset t = image.getSliceView(s, interval).sum(0);
			if (allStrips == null) {
				allStrips = t.reshape(1, t.getSize());
			} else {
				allStrips = DatasetUtils.concatenate(new IDataset[] {allStrips, t.reshape(1, t.getSize())}, 0);
			}

			try {
				fitFunction("Exception for summed rows fit", peak, xSlice, t, null);
				col.setAbs(i, peak.getParameterValue(SPECTRA_PEAK_POSN));
			} catch (Exception e) {
				col.setAbs(i, Double.NaN);
			}

//			if (i == 0) { // TODO add ROI number
//				t.setName("peak_" + i);
//				generateFitForDisplay(peak, xSlice, t, "peak_fit_" + i);
//			}
		}

		int diff = interval.getNumSteps() - stripSize;
		if (diff > 0) {
			// need to crop strips when saved as they can have different sizes
			allStrips = allStrips.getSliceView(null, new Slice(diff, null));
		}
		allStrips.setName("subprofile_" + r);
		MetadataUtils.setAxes(allStrips, null, xSlice);
		auxData.add(allStrips);

		return new Dataset[] {row, col};
	}

	private BooleanDataset fitStraightLine(StraightLine line, int ymax, int minPoints, double maxDev, Dataset x, Dataset y) {
		log.append("\nFitting straight line");
		residual = Double.POSITIVE_INFINITY;
		Dataset diff;
		double dev = Double.POSITIVE_INFINITY;
		BooleanDataset mask = null;

		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.SIMPLEX_MD);
		do {
			line.setParameterValues(0, ymax/2);
			double cr = fitFunction(this, opt, "Exception in straight line fit", log, line, x, y, mask);
			if (cr > 1.5*residual) { // allow for variation in residual trends
				throw new OperationException(this, "Discarding outliers made straight line fit worse");
			}
			residual = cr;

			diff = Maths.subtract(line.calculateValues(x), y);
			double cdev = diff.stdDeviation(true, true);
			if (cdev >= dev) { // ensure more outliers are found by making limits tighter
				dev = Math.max(maxDev, dev/1.25);
			} else {
				dev = cdev;
			}
			log.append("Finding inliers with limit of %g", dev);
			BooleanDataset omask = Comparisons.withinRange(diff, -dev, dev);
			int i = ((Number) omask.sum()).intValue();
			log.append("Outliers found: %d/%d", x.getSize() - i, x.getSize());
			if (i < minPoints) {
				throw new OperationException(this, "Too few points left to fit straight line: " + i + " from " + x.getSize());
			}
			mask = omask;
		} while (dev > maxDev);

		log.appendSuccess("Final fitted line is %s", line);
		return mask;
	}

	protected double fitFunction(String excMessage, IFunction f, Dataset x, Dataset v, Dataset m) {
		return fitFunction(this, new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT), excMessage, log, f, x, v, m);
	}

	public static double fitFunction(IOperation<?, ?> op, ApacheOptimizer opt, String excMessage, OperationLog llog, IFunction f, Dataset x, Dataset v, Dataset m) {
		if (m != null) {
			x = x.getByBoolean(m);
			v = v.getByBoolean(m);
		}
		double residual = Double.POSITIVE_INFINITY;
		double[] errors = null;

		try {
			opt.optimize(new Dataset[] {x}, v, f);
			residual = opt.calculateResidual();
			errors = opt.guessParametersErrors();
		} catch (Exception fittingError) {
			System.err.println("Error: " + f);
			throw new OperationException(op, excMessage, fittingError);
		}

		if (opt.hasErrors() && errors == null) {
			throw new OperationException(op, excMessage);
		}
		if (llog != null) {
			llog.append("Fitted function: residual = %g\n%s", residual, f);
			llog.append("Peak is %g cf %g", f.val(f.getParameter(0).getValue()), v.max().doubleValue());
		}
		return residual;
	}

	protected static void initializeSpectrumFunctionParameters(IOperation<?,?> op, IFunction pdf, Dataset x, Dataset v) {
		IParameter p = pdf.getParameter(SPECTRA_PEAK_POSN);
		double max = v.max(true).doubleValue();
		if (max == 0) {
			throw new OperationException(op, "Cannot fit to data with maximum value of zero");
		}

		double pmax = x.getDouble(v.argMax(true)); // position of maximum
		// find base line
		List<Dataset> hs = createHistogram(v);
		int pos = hs.get(0).maxPos(true)[0];
		double base = hs.get(1).getDouble(pos);
		double fw = SubtractFittedBackgroundOperation.findFWHMPostMax(x, v, base);
		if (Double.isNaN(fw)) {
			fw = v.stdDeviation();
		}
		p.setLimits(Math.max(pmax - 5 * fw, x.min(true).doubleValue()), Math.min(pmax + 5 * fw, x.max(true).doubleValue()));
		p.setValue(pmax);

		p = pdf.getParameter(SPECTRA_PEAK_WIDTH);
		p.setLimits(1, 2*fw);
		p.setValue(fw);

		p = pdf.getParameter(SPECTRA_PEAK_AREA);
		// estimate area
		double t = calcAreaAboveBase(v, base);
		p.setValue(t);
		p.setLimits(0, Math.max(2* t, fw * max));

		if (pdf.getNoOfParameters() > SPECTRA_PEAK_OFFSET) {
			p = pdf.getParameter(SPECTRA_PEAK_OFFSET);
			// width of base line noise PDF
			double bd = SubtractFittedBackgroundOperation.findFWHMPostMax(hs.get(1).getSliceView(new Slice(-1)), hs.get(0), 0);
			p.setValue(base);
			p.setLimits(-Double.MAX_VALUE, base + bd);
		}
	}

	private static double calcAreaAboveBase(Dataset v, double base) {
		double sum = 0;
		IndexIterator it = v.getIterator();
		while (it.hasNext()) {
			double x = v.getElementDoubleAbs(it.index) - base;
			if (x > 0) {
				sum += x;
			}
		}
		return sum;
	}

	private static double findPositiveMin(Dataset d) {
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

	private static List<Dataset> createHistogram(Dataset in) {
		double min = Math.floor(findPositiveMin(in));
		double max = Math.ceil(in.max(true).doubleValue());
		IntegerDataset bins = DatasetFactory.createRange(IntegerDataset.class, min, max+1, 100);

		Histogram histo = new Histogram(bins);
		return histo.value(in);
	}
}
