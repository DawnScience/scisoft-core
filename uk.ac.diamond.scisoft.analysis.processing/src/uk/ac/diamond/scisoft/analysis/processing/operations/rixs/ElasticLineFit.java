/*-
 * Copyright 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.rixs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.dataset.slicer.SliceFromSeriesMetadata;
import org.eclipse.january.DatasetException;
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
import uk.ac.diamond.scisoft.analysis.processing.operations.utils.ProcessingUtils;

/**
 * Find and fit the RIXS elastic line image to a straight line so other image
 * <p>
 * Returns line fit parameters and also peak FWHM as auxiliary data
 */
public class ElasticLineFit extends RixsBaseOperation<ElasticLineFitModel> {

	/**
	 * Auxiliary subentry. This must match the name field defined in the plugin extension
	 */
	public static final String PROC_NAME = "RIXS elastic line fit";

	private double residual;
	private Dataset position;

	protected List<Double>[] goodPosition = new List[] {new ArrayList<>(), new ArrayList<>()}; 
	protected List<Double>[] goodIntercept = new List[] {new ArrayList<>(), new ArrayList<>()};

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.SAME;
	}

	@Override
	public String getFilenameSuffix() {
		return "elastic_line_fit";
	}

	@Override
	void initializeProcess(IDataset original) {
		log.append("Elastic Line Fit");
		log.append("================");

		// get position
		SliceFromSeriesMetadata smd = original.getFirstMetadata(SliceFromSeriesMetadata.class);
		if (smd.getSliceInfo().getSliceNumber() == 1) {
			goodPosition[0].clear();
			goodPosition[1].clear();
			goodIntercept[0].clear();
			goodIntercept[1].clear();
		}

		ILazyDataset ld = smd.getParent();
		AxesMetadata amd = ld.getFirstMetadata(AxesMetadata.class);
		try {
			position = DatasetUtils.convertToDataset(smd.getMatchingSlice(amd.getAxis(0)[0])).squeeze(true);
			log.append("Current position: %s", position.toString(true));
		} catch (DatasetException e1) {
		}
	}

	@Override
	IDataset processImageRegion(int r, IDataset original, Dataset in) {
		double requiredPhotons = countsPerPhoton * model.getMinPhotons(); // count per photon

		// check if image has sufficient signal: anything less than 100 photons is insufficient
		if (((Number) in.sum()).doubleValue() < requiredPhotons) {
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

		return original;
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
		return od;
	}

	private void createInvalidOperationData(int r, Exception e) {
		log.append("Operation halted!");
		log.append("%s", e);
		addAuxData(r, Double.NaN, Double.NaN, Double.NaN);
	}

	protected void addAuxData(int i, double m, double c, double r) {
		if (Double.isFinite(c)) {
			goodPosition[i].add(position.getDouble());
			goodIntercept[i].add(c);
		}

		auxData.add(addPositionAxis(ProcessingUtils.createNamedDataset(m, "line_%d_m", i)));
		auxData.add(addPositionAxis(ProcessingUtils.createNamedDataset(c, "line_%d_c", i)));
		auxData.add(addPositionAxis(ProcessingUtils.createNamedDataset(r, "residual_%d", i)));
	}

	private void addDisplayData(int i, Dataset[] coords) {
		coords[0].setName(String.format("row%d", i));
		coords[1].setName(String.format("col%d", i));
		generateFitForDisplay(getStraightLine(i), coords[0], coords[1], String.format("line_%d_fit", i));
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

	protected void generateFitForDisplay(IFunction f, Dataset x, Dataset d, String name) {
		IDataset fit = f.calculateValues(x);
		fit.setName(name);
		ProcessingUtils.setAxes(fit, x);
		ProcessingUtils.setAxes(d, x);
		displayData.add(d);
		displayData.add(fit);
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
		fitFunction(peak, x, v);

//		generateFitForDisplay(peak, x, v);
		double[] ip = peak.getParameterValues();
		int mean = (int) Math.floor(ip[0]);
		int width = Math.max(3, (int) (3 * Math.ceil(ip[1])));
		Slice interval = new Slice(Math.max(0, mean - width), Math.min(xmax, mean + width + 1));
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
				fitFunction(peak, xSlice, t);
				col.setAbs(i, peak.getParameterValue(0));
			} catch (Exception e) {
				col.setAbs(i, Double.NaN);
			}

//			if (i == 0) { // TODO add ROI number
//				t.setName("peak_" + i);
//				generateFitForDisplay(peak, xSlice, t, "peak_fit_" + i);
//			}
		}

		allStrips.setName("strip_" + r);
		ProcessingUtils.setAxes(allStrips, null, xSlice);
		auxData.add(allStrips);

		return new Dataset[] {row, col};
	}

	private BooleanDataset fitStraightLine(int r, boolean useMaxFactor, int ymax, Dataset x, Dataset y) {
		log.append("\nFitting straight line");
		StraightLine line = getStraightLine(r);
		if (line == null) {
			line = new StraightLine(-model.getMaxSlope(), model.getMaxSlope(), 0, ymax);
			lines[r] = line;
		}
		residual = Double.POSITIVE_INFINITY;
		Dataset diff;
		double dev = Double.POSITIVE_INFINITY;
		BooleanDataset mask = null;

		do {
			line.setParameterValues(0, ymax/2);
			double cr = fitFunction(line, x, y, mask);
			if (cr > residual) {
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
				throw new OperationException(this, "Too few points left to fit straight line: " + (x.getSize() - i));
			}
			mask = omask;
		} while (dev > model.getMaxDev());

		return mask;
	}

	protected double fitFunction(IFunction f, Dataset x, Dataset v) {
		return fitFunction(f, x, v, null);
	}

	private double fitFunction(IFunction f, Dataset x, Dataset v, Dataset m) {
		if (m != null) {
			x = x.getByBoolean(m);
			v = v.getByBoolean(m);
		}
		double residual = Double.POSITIVE_INFINITY;
		try {
			ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
			opt.optimize(new Dataset[] {x}, v, f);
			residual = opt.calculateResidual();
		} catch (Exception fittingError) {
			throw new OperationException(this, "Exception performing fit in ElasticLineFit()", fittingError);
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
