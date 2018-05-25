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
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.BooleanIterator;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
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
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.processing.metadata.FitMetadata;
import uk.ac.diamond.scisoft.analysis.processing.operations.MetadataUtils;
import uk.ac.diamond.scisoft.analysis.processing.operations.backgroundsubtraction.SubtractFittedBackgroundOperation;
import uk.ac.diamond.scisoft.analysis.processing.operations.rixs.RixsBaseModel.ENERGY_DIRECTION;
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

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROCESS_NAME = "RIXS elastic line reduction";
	/**
	 * Suffix for processed file
	 */
	public static final String SUFFIX = "elastic_line";

	private double residual;
	private Dataset position;
	private boolean useSpectrum = true;
	private Dataset output;

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

	}

	@Override
	void initializeProcess(IDataset original) {
		log.append("Elastic Line Reduction");
		log.append("======================");

		// get position
		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);

		ILazyDataset ld = smd.getParent();
		AxesMetadata amd = ld.getFirstMetadata(AxesMetadata.class);
		positionName = amd.getAxis(0)[0].getName();
		try {
			position = DatasetUtils.convertToDataset(smd.getMatchingSlice(amd.getAxis(0)[0])).squeeze(true);
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
		log.append("At frame %d/%d", si.getSliceNumber(), smax);

		// TODO make this live-friendly by show result per frame
		// needs to give fake results for first slice
		if (si.isLastSlice()) {
			summaryData.clear();
			if (smax != 1) { // display when there is only one image
				displayData.clear();
			}
//				odd.setAuxData();
			double[] dispersion = new double[roiMax];
			for (int r = 0; r < roiMax; r++) {
//					if (goodPosition[r].size() <= 2) {
//						log.append("Not enough good lines (%d) found for ROI %d", goodPosition[r].size(), r);
//						continue;
//					}

				if (goodPosition[r].size() == 0) {
					log.append("No lines found for ROI %d", r);
					continue;
				}
				List<?>[] coords;
				if (useSpectrum) {
					coords = fitSpectraPeakPositions(r);
				} else {
					coords = new List<?>[] {goodPosition[r], goodIntercept[r]};
				}

				double[] res = fitIntercepts(r, coords);
				dispersion[r] = 1./res[1];
				log.append("Dispersion is %g for residual %g", dispersion[r], res[0]);
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

		Dataset[] coords;
		if (model.getDelta() != 0) {
			int minPoints = model.getMinPoints();
			if (model.getDelta() == 1) {
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
			int ymax = original.getShape()[model.getEnergyIndex()];
			
			BooleanDataset mask;
			try {
				mask = fitStraightLine(getStraightLine(r), ymax, minPoints, model.getMaxDev(), coords[0], coords[1]);
			} catch (OperationException e) {
				createInvalidOperationData(r, e);
				if (r == 0) {
					coords[0].setName("row0");
					coords[1].setName("col0");
					generateFitForDisplay(null, coords[0], coords[1], null, false);
				}
				return original;
			}
	
			coords[0] = coords[0].getByBoolean(mask);
			coords[1] = coords[1].getByBoolean(mask);

			if (r == 0) {
				coords[0].setName("row0");
				coords[1].setName("col0");
				generateFitForDisplay(getStraightLine(0), coords[0], coords[1], "line_0_fit", false);
			}
		} else {
			minimizeFWHMForSpectrum(r, in);
		}
		StraightLine line = getStraightLine(r);
		processFit(r, in, line.getParameterValue(0), line.getParameterValue(1), residual);

		return original;
	}

	private void minimizeFWHMForSpectrum(int r, Dataset in) {
		Dataset s = sumImageAlongSlope(in, 0, false);
//		s.setName("raw_spectrum");
//		auxData.add(s);

		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);

		// assume max is where the peak to be sharpened is located
		double w = findFWHM(opt, peak, DatasetFactory.createRange(s.getSize()), s);
		int pos = (int) Math.round(peak.getParameterValue(0));
		int del = Math.max(s.getSize()/8, (int) Math.ceil(30 * w));
		// work out interval and make some aux data of FWHMs
		Slice slice = new Slice(Math.max(0, pos - del), Math.min(s.getSize(), pos + del));
		log.append("Raw spectrum has max at %d with FWHM of %g", pos, w);
		log.append("Slice interval for peak is %s", slice);

		Dataset sin = in.getSliceView((Slice) null, slice);
		Dataset sx = DatasetFactory.createRange(DoubleDataset.class, slice.getStart(), slice.getStop(), 1);
//		sin.setName("subimage");
//		auxData.add(sin);

		FWHMForSpectrum fn = new FWHMForSpectrum(opt, sx, sin, peak);
		IParameter param = fn.getParameter(0);
		int n = 33;
		Dataset slopes = DatasetFactory.createLinearSpace(DoubleDataset.class, -16, 16, n).idivide(16*16);
		slopes.setName("slope");
		Dataset summed = DatasetFactory.zeros(n, sin.getShapeRef()[1]);
		DoubleDataset fwhm = DatasetFactory.zeros(n);
		for (int i = 0; i < n; i++) {
			double x = slopes.getDouble(i);
			Dataset ns = sumImageAlongSlope(sin, x, false);
			fwhm.setItem(findFWHM(opt, peak, sx, ns), i);
			summed.setSlice(ns, new Slice(i, i+1));
		}

		int pmin = fwhm.argMin(true);
		log.append("Minimal FWHM at slope of %g ", slopes.getDouble(pmin));
		fwhm.setName("tilted_profile_width_" + r);
		MetadataUtils.setAxes(fwhm, slopes);
		auxData.add(fwhm);
		if (r == 0) {
			displayData.add(fwhm);
		}
		summed.setName("tilted_profile_" + r);
		MetadataUtils.setAxes(summed, slopes);
		auxData.add(summed);

		// crop limits to turning point of minimum
		Dataset diff = Maths.derivative(DatasetFactory.createRange(n), fwhm, 3);
		List<Double> cs = DatasetUtils.crossings(diff, 0);
		log.append("Zero crossings of FWHM derivative: %s", cs);
		int imax = cs.size() - 1;
		if (imax < 0) {
			throw new OperationException(this, "No turning points found in FWHM");
		}
		int i = 0;
		for (; i <= imax && Math.round(cs.get(i)) < pmin; i++);
		int imin = 0;
		if (i > 0) {
			imin = (int) Math.ceil(cs.get(i - 1));
		} else {
			imin = 0;
		}
		for (; i <= imax && Math.round(cs.get(i)) < pmin; i++);
		imax = (int) Math.floor(cs.get(Math.min(imax, i)));
		if (imax < pmin) {
			imax = n - 1;
		}

		// minimize FWHM
		param.setLimits(slopes.getDouble(imin), slopes.getDouble(imax));
		param.setValue(slopes.getDouble(pmin));
		log.append("Initial parameter settings: %g [%g, %g]", param.getValue(), param.getLowerLimit(), param.getUpperLimit());
		IOptimizer optimizer = new ApacheOptimizer(Optimizer.SIMPLEX_NM);
		try {
			optimizer.optimize(true, fn);
			StraightLine line = getStraightLine(r);
			line.getParameter(0).setValue(param.getValue());
			line.getParameter(1).setValue(peak.getParameterValue(0));
			log.append("Optimized minimal FWHM at slope of %g", param.getValue());
//			System.err.printf("Optimized minimal FWHM at slope of %g\n", param.getValue());
		} catch (Exception e) {
			log.append("Error minimizing FWHM for peak in spectrum: %s", e.getMessage());
			throw new OperationException(this, "Error minimizing FWHM for peak in spectrum", e);
		}
	}

	private class FWHMForSpectrum extends AFunction {
		private static final long serialVersionUID = 5751477494591603033L;
		final Dataset image;
		private IFunction peak;
		private Dataset rx;
		private ApacheOptimizer opt;

		public FWHMForSpectrum(ApacheOptimizer opt, Dataset rx, Dataset image, IFunction peak) {
			super(1);
			this.opt = opt;
			this.image = image;
			this.peak = peak;
			this.rx = rx;
		}

		@Override
		public double val(double... values) {
			double x = getParameterValue(0);
			Dataset ns = sumImageAlongSlope(image, x, false);
			return findFWHM(opt, peak, rx, ns);
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
	 * @param peak
	 * @param x dataset
	 * @param y dataset
	 * @return FWHM
	 */
	public static double findFWHM(ApacheOptimizer opt, IFunction peak, Dataset x, Dataset y) {
		initializeFunctionParameters(null, peak, x, y);
//		double res = Double.POSITIVE_INFINITY;
		try {
			fitFunction(null, opt, "Exception for FWHM fit in optimizer", null, peak, x, y, null);
//			System.err.println("Peak fit is " + peak + " with residual " + res);
		} catch (Exception e) {
			return Double.POSITIVE_INFINITY;
		}
		return peak.getParameterValue(1);
	}

	private void processFit(int i, Dataset in, double m, double c, double r) {
		if (Double.isFinite(c)) {
			goodPosition[i].add(position.getDouble());
			goodIntercept[i].add(c);

			if (useSpectrum && in != null) {
				Dataset spectrum = makeSpectrum(in, getStraightLine(i).getParameterValue(0), model.isClipSpectra());
				spectrum.setName(ES_PREFIX + i);
//				auxData.add(spectrum); // put in summary data instead
				goodSpectra[i].add(spectrum);
				posSlope[i].add(m > 0);
//				if (i == 0) { // cannot do this as it interferes with writing output
//					output = spectrum;
//				}
			}
		}

		auxData.add(ProcessingUtils.createNamedDataset(m, LINE_GRADIENT_FORMAT, i).reshape(1));
		auxData.add(ProcessingUtils.createNamedDataset(c, LINE_INTERCEPT_FORMAT, i));
		auxData.add(ProcessingUtils.createNamedDataset(r, LINE_RESIDUAL_FORMAT, i));
	}

	public final static String LINE_INTERCEPT_FORMAT = "line_%d_c";
	public final static String LINE_GRADIENT_FORMAT = "line_%d_m";
	public final static String LINE_RESIDUAL_FORMAT = "line_%d_residual";

	/**
	 * Fit to intercepts of elastic lines
	 * @param r
	 * @param coords
	 * @return residual and gradient
	 */
	private double[] fitIntercepts(int r, List<?>[] coords) {
		if (coords[0].size() <= 2) {
			return new double[] {Double.NaN, Double.NaN};
		}

		Dataset energy = DatasetFactory.createFromList(coords[0]);
		Dataset intercept = DatasetFactory.createFromList(coords[1]);
		String name;
		if (model.getEnergyDirection() == ENERGY_DIRECTION.SLOW) {
			energy.setName("energy_" + r);
			intercept.setName("Intercept");
			name = "energy_fit_" + r;
		} else {
			energy.setName("Energy");
			intercept.setName("intercept_" + r);
			name = "intercept_fit_" + r;
		}
		double smax = 2*Math.abs(intercept.peakToPeak().doubleValue()) / Math.abs(energy.peakToPeak().doubleValue());
		StraightLine iLine = new StraightLine(-smax, smax, -Double.MAX_VALUE, Double.MAX_VALUE);

		double res = fitFunction("Exception for intercept fit to find dispersion", iLine, energy, intercept, null);
		generateFitForDisplay(iLine, energy, intercept, name, model.getEnergyDirection() == ENERGY_DIRECTION.SLOW);
		return new double[] {res, iLine.getParameterValue(0)};
	}

	private List<?>[] fitSpectraPeakPositions(int r) {
//		Gaussian peak = new Gaussian();
		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

		List<Double> bPosition = new ArrayList<>();
		List<Double> gPosition = new ArrayList<>();
		List<Double> gPosn = new ArrayList<>();
		List<Double> gFWHM = new ArrayList<>();
		List<Double> positions = goodPosition[r];

		int ns = goodSpectra[r].size();
		Dataset spectrum = null;
		Dataset bSpectrum = null;
		Dataset gSpectrum = null;
		Dataset gSpectrumFit = null;

		// as clipped spectra have differing sizes, we need to find common sections
		int minSize = Integer.MAX_VALUE;
		for (int i = 0; i < ns; i++) {
			minSize = Math.min(minSize, goodSpectra[r].get(i).getSize());
		}
		DoubleDataset x = null;
		if (ns > 0) {
			x = DatasetFactory.createRange(minSize);
			x.setName("Energy Index");
		}
		int[] minShape = new int[] {1, minSize};
		for (int i = 0; i < ns; i++) {
			spectrum = goodSpectra[r].get(i);
			int size = spectrum.getSize();
			if (size > minSize) {
				Slice cs = posSlope[r].get(i) ? new Slice(minSize) : new Slice(size - minSize, null);
				spectrum = spectrum.getSliceView(cs);
			}
			log.append("Fitting elastic peak: %d/%d", i, ns);
			initializeFunctionParameters(this, peak, x, spectrum);
			double res = Double.POSITIVE_INFINITY;
			try {
				res = fitFunction("Exception for elastic peak fit", peak, x, spectrum, null);
				System.err.println("Peak " + i + " fit is " + peak + " with residual " + res);
				if (Double.isFinite(res)) {
					res = Double.POSITIVE_INFINITY;
					// isolate peak and fit again (to reduce influence of other spikes)
					double posn = peak.getParameterValue(0);
					double width = model.getPeakFittingFactor() * peak.getParameterValue(1);
					Slice slice = new Slice(Math.max(0, (int) Math.floor(posn-width)), Math.min(minSize, (int) Math.floor(posn+width)));
					IParameter p = peak.getParameter(3); // reset offset
					p.setValue(-0.5*peak.val(posn));
					res = fitFunction("Exception for slice of elastic peak fit", peak, x.getSliceView(slice), spectrum.getSliceView(slice), null);
					System.err.println("Refit is " + peak + " with residual " + res + " in " + slice);
					if (!Double.isFinite(res)) {
						log.append("Fitting elastic peak: refit FAILED");
					}
					gPosition.add(positions.get(i));
					gPosn.add(peak.getParameterValue(0));
					gFWHM.add(peak.getParameterValue(1));
					DoubleDataset fit = peak.calculateValues(x);
					if (gSpectrum == null) {
						gSpectrum = spectrum.clone().reshape(minShape);
						gSpectrumFit = fit.reshape(minShape);
					} else {
						
						gSpectrum = DatasetUtils.concatenate(new IDataset[] {gSpectrum, spectrum.reshape(minShape)}, 0);
						gSpectrumFit = DatasetUtils.concatenate(new IDataset[] {gSpectrumFit, fit.reshape(minShape)}, 0);
					}
				}
			} catch (Exception e) {
			}
			if (!Double.isFinite(res)) {
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
			MetadataUtils.setAxes(gSpectrum, ge);
			summaryData.add(gSpectrum);
			gSpectrumFit.setName(ESF_PREFIX + r);
			MetadataUtils.setAxes(gSpectrumFit, ge);
			summaryData.add(gSpectrumFit);
			Dataset gf = DatasetFactory.createFromList(gFWHM);
			gf.setName(ESFWHM_PREFIX + r);
			MetadataUtils.setAxes(gf, ge);
			summaryData.add(gf);
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

	protected void generateFitForDisplay(IFunction f, Dataset x, Dataset d, String name, boolean transpose) {
		if (transpose) {
			MetadataUtils.setAxes(x, d);
			displayData.add(x);
		} else {
			MetadataUtils.setAxes(d, x);
			displayData.add(d);
		}
		if (f == null) {
			return;
		}

		Dataset fit = DatasetUtils.convertToDataset(f.calculateValues(x));
		if (transpose) {
			Dataset xf = x.clone();
			xf.setName(name);
			fit.setName(d.getName());
			MetadataUtils.setAxes(xf, fit);
			displayData.add(xf);
		} else {
			fit.setName(name);
			MetadataUtils.setAxes(fit, x);
			displayData.add(fit);
		}
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
		initializeFunctionParameters(this, peak, x, v);
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
				col.setAbs(i, peak.getParameterValue(0));
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

		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
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
			throw new OperationException(op, excMessage, fittingError);
		}

		if (errors == null) {
			throw new OperationException(op, excMessage);
		}
		if (llog != null) {
			llog.append("Fitted function: residual = %g\n%s", residual, f);
			llog.append("Peak is %g cf %g", f.val(f.getParameter(0).getValue()), v.max().doubleValue());
		}
		return residual;
	}

	protected static void initializeFunctionParameters(IOperation<?,?> op, IFunction pdf, Dataset x, Dataset v) {
		IParameter p = pdf.getParameter(0);
		double max = v.max(true).doubleValue();
		if (max == 0) {
			throw new OperationException(op, "Cannot fit to data with maximum value of zero");
		}

		int pmax = v.argMax(true); // position of maximum
		// find base line
		List<Dataset> hs = createHistogram(v);
		int pos = hs.get(0).maxPos(true)[0];
		double base = hs.get(1).getDouble(pos);
		double fw = SubtractFittedBackgroundOperation.findFWHMPostMax(x, v, base);
		if (Double.isNaN(fw)) {
			fw = v.stdDeviation();
		}
		p.setLimits(Math.max(pmax - 5 * fw, 0), Math.min(pmax + 5 * fw, v.getSize()));
		p.setValue(pmax);

		p = pdf.getParameter(1);
		p.setLimits(1, 2*fw);
		p.setValue(fw);

		p = pdf.getParameter(2);
		// estimate area
		double t = ((Number) v.sum(true)).doubleValue();
		p.setValue(t);
		p.setLimits(0, Math.max(2* t, fw * max));

		if (pdf.getNoOfParameters() > 3) {
			p = pdf.getParameter(3);
			// width of base line noise PDF
			double bd = SubtractFittedBackgroundOperation.findFWHMPostMax(hs.get(1).getSliceView(new Slice(-1)), hs.get(0), 0);
			p.setValue(base);
//			p.setLimits(-Double.MAX_VALUE, Double.MAX_VALUE);
			p.setLimits(-Double.MAX_VALUE, base + bd);
		}
	}

	static List<Dataset> createHistogram(Dataset in) {
		double min = Math.floor(SubtractFittedBackgroundOperation.findPositiveMin(in));
		double max = Math.ceil(in.max(true).doubleValue());
		IntegerDataset bins = DatasetFactory.createRange(IntegerDataset.class, min, max+1, 100);

		Histogram histo = new Histogram(bins);
		return histo.value(in);
	}

}
