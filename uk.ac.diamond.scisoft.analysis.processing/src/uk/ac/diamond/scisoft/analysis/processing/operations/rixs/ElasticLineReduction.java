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
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationDataForDisplay;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceInformation;
import org.eclipse.january.IMonitor;
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
import uk.ac.diamond.scisoft.analysis.fitting.functions.Add;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
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
	private boolean isImageGood = false;

	protected List<Double>[] goodPosition = new List[] {new ArrayList<>(), new ArrayList<>()}; 
	protected List<Double>[] goodIntercept = new List[] {new ArrayList<>(), new ArrayList<>()};
	private List<Dataset>[] goodSpectra = new List[] {new ArrayList<>(), new ArrayList<>()};

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	@Override
	public String getFilenameSuffix() {
		return SUFFIX;
	}

	@Override
	void updateFromModel() {
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
		addAuxData(r, Double.NaN, Double.NaN, Double.NaN);
	}

	private void addDisplayData(int i, Dataset[] coords) {
		coords[0].setName(String.format("row%d", i));
		coords[1].setName(String.format("col%d", i));
		generateFitForDisplay(getStraightLine(i), coords[0], coords[1], String.format("line_%d_fit", i), false);
	}

	@Override
	protected void resetProcess(IDataset original) {
		goodPosition[0].clear();
		goodPosition[1].clear();
		goodIntercept[0].clear();
		goodIntercept[1].clear();
		goodSpectra[0].clear();
		goodSpectra[1].clear();

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
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
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

		Dataset out = null;

		if (si != null) {
			int smax = si.getTotalSlices();
			log.append("At frame %d/%d", si.getSliceNumber(), smax);

			// TODO make this live-friendly by show result per frame
			// needs to give fake results for first slice
			if (si.getSliceNumber() == smax - 1) {
				summaryData.clear();
				displayData.clear();
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

				out = ProcessingUtils.createNamedDataset(dispersion, "energy_dispersion").reshape(1, roiMax);
				copyMetadata(input, out);
				out.clearMetadata(AxesMetadata.class);
				out.clearMetadata(SliceFromSeriesMetadata.class);
				SliceFromSeriesMetadata outssm = ssm.clone();
				for (int i = 0, imax = ssm.getParent().getRank(); i < imax; i++) {
					if (!outssm.isDataDimension(i)) {
						outssm.reducedDimensionToSingular(i);
					}
				}
				out.setMetadata(outssm);

				if (displayData.size() > 0) {
					for (IDataset d : displayData) {
						summaryData.add(d);
					}
				}
			}
		}

		OperationDataForDisplay odd;
		if (od instanceof OperationDataForDisplay) {
			odd = (OperationDataForDisplay) od;
		} else {
			odd = new OperationDataForDisplay(od.getData());
		}
		odd.setShowSeparately(true);
		odd.setLog(log);
		odd.setData(out);
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
		System.err.println(sum / countsPerPhoton);
		log.append("Number of photons, estimated: %g", sum / countsPerPhoton);
		if (sum < requiredPhotons) {
			createInvalidOperationData(r, new OperationException(this, "Not enough signal for elastic line"));
			return original;
		}

		Dataset[] coords;
		if (model.getDelta() == 1) {
			coords = processPerRowMax(in);
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
			mask = fitStraightLine(r, model.getDelta() == 1, ymax, coords[0], coords[1]);
		} catch (OperationException e) {
			createInvalidOperationData(r, e);
			return original;
		}

		coords[0] = coords[0].getByBoolean(mask);
		coords[1] = coords[1].getByBoolean(mask);
		
		addDisplayData(r, coords);

		StraightLine line = getStraightLine(r);
		addAuxData(r, line.getParameterValue(0), line.getParameterValue(1), residual);

		if (useSpectrum) { // use spectrum fitted with Gaussian for calibration fit
			Dataset[] result = makeSpectrum(r, in);
			Dataset spectrum = result[1];
			spectrum.setName("elastic_spectrum_" + r);
//			auxData.add(spectrum); // put in summary data instead

			if (isImageGood) {
				goodSpectra[r].add(spectrum);
			}
		}

		return original;
	}

	private void addAuxData(int i, double m, double c, double r) {
		if (Double.isFinite(c)) {
			goodPosition[i].add(position.getDouble());
			goodIntercept[i].add(c);
		}
	
		auxData.add(addPositionAxis(ProcessingUtils.createNamedDataset(m, "line_%d_m", i)));
		auxData.add(addPositionAxis(ProcessingUtils.createNamedDataset(c, "line_%d_c", i)));
		auxData.add(addPositionAxis(ProcessingUtils.createNamedDataset(r, "residual_%d", i)));

		if (useSpectrum) {
			isImageGood = Double.isFinite(c);
		}
	}

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

		double res = fitFunction(iLine, energy, intercept, null);
		generateFitForDisplay(iLine, energy, intercept, name, model.getEnergyDirection() == ENERGY_DIRECTION.SLOW);
		return new double[] {res, iLine.getParameterValue(0)};
	}

	private List<?>[] fitSpectraPeakPositions(int r) {
//		Gaussian peak = new Gaussian();
		Add peak = new Add();
		peak.addFunction(new Gaussian());
		peak.addFunction(new Offset());

		List<Double> gEnergy = new ArrayList<>();
		List<Double> gPosn = new ArrayList<>();
		List<Double> gFWHM = new ArrayList<>();
		List<Double> energy = goodPosition[r];

		int ns = goodSpectra[r].size();
		Dataset spectrum = null;
		Dataset gSpectrum = null;
		Dataset gSpectrumFit = null;
		int size = 0;
		DoubleDataset x = null;
		if (ns > 0) {
			size = goodSpectra[r].get(0).getSize();
			x = DatasetFactory.createRange(size);
			x.setName("Energy Index");
		}
		for (int i = 0; i < ns; i++) {
			spectrum = goodSpectra[r].get(i);
			log.append("Fitting elastic peak: %d/%d", i, ns);
			initializeFunctionParameters(peak, spectrum);
			double res = Double.POSITIVE_INFINITY;
			try {
				res = fitFunction(peak, x, spectrum, null);
				System.err.println("Peak " + i + " fit is " + peak + " with residual " + res);
				if (Double.isFinite(res)) {
					gEnergy.add(energy.get(i));
					gPosn.add(peak.getParameterValue(0));
					gFWHM.add(peak.getParameterValue(1));
					DoubleDataset fit = peak.calculateValues(x);
					if (gSpectrum == null) {
						gSpectrum = spectrum.clone().reshape(1, size);
						gSpectrumFit = fit.reshape(1, size);
					} else {
						gSpectrum = DatasetUtils.concatenate(new IDataset[] {gSpectrum, spectrum.reshape(1, size)}, 0);
						gSpectrumFit = DatasetUtils.concatenate(new IDataset[] {gSpectrumFit, fit.reshape(1, size)}, 0);
					}
				}
			} catch (Exception e) {
			}
			if (!Double.isFinite(res)) {
				log.append("Fitting elastic peak: FAILED");
			}
		}

		Dataset ge = DatasetFactory.createFromList(gEnergy);
		ge.setName("Scan energy");
		gSpectrum.setName("elastic_spectrum_" + r);
		ProcessingUtils.setAxes(gSpectrum, ge);
		summaryData.add(gSpectrum);
		gSpectrumFit.setName("elastic_spectrum_fit_" + r);
		ProcessingUtils.setAxes(gSpectrumFit, ge);
		summaryData.add(gSpectrumFit);
		Dataset gf = DatasetFactory.createFromList(gFWHM);
		gf.setName("elastic_spectrum_fwhm_" + r);
		ProcessingUtils.setAxes(gf, ge);
		summaryData.add(gf);

		return new List<?>[] {gEnergy, gPosn};
	}

	protected void generateFitForDisplay(IFunction f, Dataset x, Dataset d, String name, boolean transpose) {
		Dataset fit = DatasetUtils.convertToDataset(f.calculateValues(x));
		if (transpose) {
			Dataset xf = x.clone();
			xf.setName(name);
			fit.setName(d.getName());
			ProcessingUtils.setAxes(xf, fit);
			ProcessingUtils.setAxes(x, d);
			displayData.add(x);
			displayData.add(xf);
		} else {
			fit.setName(name);
			ProcessingUtils.setAxes(fit, x);
			ProcessingUtils.setAxes(d, x);
			displayData.add(d);
			displayData.add(fit);
		}
	}

	// FIXME this is wasteful as these axis datasets are replicated for each
	// aux data
	// TODO fix NexusFileExecutionVisitor to automatically link any axis datasets
	// from the SSFMD#getParent()'s axes metadata
	private Dataset addPositionAxis(Dataset y) {
		ProcessingUtils.setAxes(y, position);
		return y;
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
			c.setItem(max.getInt(it.index), i++);
		}

		log.append("Cols stats: mean = %g; outliers at %s", ((Number) c.mean()).doubleValue(), Arrays.toString(Stats.outlierValues(c, 5, 95, peaks/10)));
		Histogram histo = new Histogram(64);
		log.append(histo.value(c).get(0).toString(true));
		log.append(histo.value(c).get(1).toString(true));

		return new Dataset[] {r, c};
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
		initializeFunctionParameters(peak, v);
		log.append("Initial peak:\n%s", peak);

		// fit entire image so as to initialise per summed row fits
		fitFunction(peak, x, v, null);

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
				fitFunction(peak, xSlice, t, null);
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
		allStrips.setName("strip_" + r);
		ProcessingUtils.setAxes(allStrips, null, xSlice);
		auxData.add(allStrips);

		return new Dataset[] {row, col};
	}

	private BooleanDataset fitStraightLine(int r, boolean useMaxFactor, int ymax, Dataset x, Dataset y) {
		log.append("\nFitting straight line");
		StraightLine line = getStraightLine(r);
		residual = Double.POSITIVE_INFINITY;
		Dataset diff;
		double dev = Double.POSITIVE_INFINITY;
		BooleanDataset mask = null;

		do {
			line.setParameterValues(0, ymax/2);
			double cr = fitFunction(line, x, y, mask);
			if (cr > 1.5*residual) { // allow for variation in residual trends
				throw new OperationException(this, "Discarding outliers made straight line fit worse");
			}
			residual = cr;

			diff = Maths.subtract(line.calculateValues(x), y);
			double cdev = diff.stdDeviation(true, true);
			if (cdev >= dev) { // ensure more outliers are found by making limits tighter
				dev = Math.max(model.getMaxDev(), dev/1.25);
			} else {
				dev = cdev;
			}
			log.append("Finding inliers with limit of %g", dev);
			BooleanDataset omask = Comparisons.withinRange(diff, -dev, dev);
			int i = ((Number) omask.sum()).intValue();
			log.append("Outliers found: %d/%d", x.getSize() - i, x.getSize());
			if (i < model.getMinPoints()) {
				throw new OperationException(this, "Too few points left to fit straight line: " + i + " from " + x.getSize());
			}
			mask = omask;
		} while (dev > model.getMaxDev());

		return mask;
	}

	protected double fitFunction(IFunction f, Dataset x, Dataset v, Dataset m) {
		if (m != null) {
			x = x.getByBoolean(m);
			v = v.getByBoolean(m);
		}
		double residual = Double.POSITIVE_INFINITY;
		double[] errors = null;
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {x}, v, f);
			residual = opt.calculateResidual();
			errors = opt.guessParametersErrors();
		} catch (Exception fittingError) {
			throw new OperationException(this, "Exception performing fit in ElasticLineReduction()", fittingError);
		}

		if (errors == null) {
			throw new OperationException(this, "Exception performing fit in ElasticLineReduction()");
		}
		log.append("Fitted function: residual = %g\n%s", residual, f);
		log.append("Peak is %g cf %g", f.val(f.getParameter(0).getValue()), v.max().doubleValue());
		return residual;
	}

	protected void initializeFunctionParameters(IFunction pdf, Dataset v) {
		IParameter p = pdf.getParameter(0);
		if (v.max(true).doubleValue() == 0) {
			throw new OperationException(this, "Cannot fit to data with maximum value of zero");
		}

		int pmax = v.argMax(true); // position of maximum
		double std = v.stdDeviation();
		p.setLimits(Math.max(pmax - 10 * std, 0), Math.min(pmax + 10 * std, v.getSize()));
		p.setValue(pmax);

		p = pdf.getParameter(1);
		p.setLimits(1, 2*std);
		p.setValue(std);

		p = pdf.getParameter(2);
		// estimate area
		double t = ((Number) v.sum(true)).doubleValue();
		p.setValue(t);
		double hm = v.max(true).doubleValue();
		p.setLimits(0, 2*std * hm);

		if (pdf.getNoOfParameters() > 3) {
			p = pdf.getParameter(3);
//			p.setValue(1e-5);
			p.setLimits(0, Double.MAX_VALUE);
		}
	}
}
