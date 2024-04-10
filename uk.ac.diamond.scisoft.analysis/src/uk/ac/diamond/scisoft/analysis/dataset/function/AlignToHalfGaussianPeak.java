/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToNumberFunction;
import org.eclipse.january.dataset.AbstractDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.DiffGaussian;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

/**
 * Fit datasets to single side of a Gaussian peak
 */
public class AlignToHalfGaussianPeak implements DatasetToNumberFunction {

	private static final Logger logger = LoggerFactory.getLogger(AlignToHalfGaussianPeak.class);
	private boolean useRisingSide = false;
	private double lx = -Double.MAX_VALUE;
	private double hx = Double.MAX_VALUE;

	private DiffGaussian dg = new DiffGaussian();

	/**
	 * @param useRisingSide if true fit to the side that rises (as the index increases)
	 */
	public AlignToHalfGaussianPeak(boolean useRisingSide) {
		this.useRisingSide = useRisingSide;
	}

	/**
	 * Set zone where peak is
	 * @param lo lower x value
	 * @param hi upper x value
	 */
	public void setPeakZone(double lo, double hi) {
		if (lo >= hi) {
			throw new IllegalArgumentException("Lower limit must be less than upper limit");
		}
		lx = lo;
		hx = hi;
	}

	/**
	 * @param datasets x/y pairs of datasets
	 * @return fitted positions of derivative of Gaussian (in x values)
	 */
	@Override
	public List<Double> value(IDataset... datasets) {
		List<Double> xPositions = new ArrayList<>(); // positions of derivative of Gaussian in x values

		final int imax = datasets.length;
		for (int i = 0; i < imax; i += 2) {
			Dataset x = DatasetUtils.convertToDataset(datasets[i]);
			Dataset y = DatasetUtils.convertToDataset(datasets[i + 1]);
			Monotonicity m = Comparisons.findMonotonicity(x);
			if (m != Monotonicity.STRICTLY_DECREASING && m != Monotonicity.STRICTLY_INCREASING) {
				logger.warn("Trace {} ignored as it is not strictly monotonic", i);
				xPositions.add(null);
				continue;
			}
			boolean isXInc = m == Monotonicity.STRICTLY_INCREASING;
			Slice cs = findCroppingSlice(isXInc, x, Math.min(x.getSize(), y.getSize()) - 1, lx, hx);

			logger.info("Cropping to {}", cs);
			Dataset cx = x.getSliceView(cs);
			Dataset cy = y.getSliceView(cs);
			if (!isXInc) {
				Slice sf = new Slice(null, null, -1);
				cx = cx.getSliceView(sf);
				cy = cy.getSliceView(sf);
			}

			// look for 1st peak in derivative
			Dataset dy = Maths.derivative(cx, cy, 1);
			int pos = useRisingSide ? dy.argMax(true) : dy.argMin(true);

			// fit to derivative of Gaussian
			double c = fitDiffGaussian(dg, cx, dy, cy.max(true).doubleValue()/5., pos);
			xPositions.add(c);
		}

		logger.debug("Positions are {}", xPositions);
		return xPositions;
	}

	/**
	 * Align data to given x positions
	 * @param resample
	 * @param firstXPos if NaN, then do not align to given value
	 * @param xPositions
	 * @param datasets
	 * @return x/y pairs of datasets where x and/or y have been shifted to align data
	 */
	public static Dataset[] alignToPositions(boolean resample, double firstXPos, List<Double> xPositions, Dataset[] datasets) {
		if (xPositions.size() < 2) {
			return datasets;
		}
		return resample ? resampleToPositions(firstXPos, xPositions, datasets) : shiftToPositions(firstXPos, xPositions, datasets);
	}

	private static Dataset[] resampleToPositions(double alignXPos, List<Double> posn, Dataset[] in) {
		double firstXPos = Double.NaN;
		int ip = 0;
		int imax = posn.size() - 1;
		for (; ip <= imax; ip++) {
			Double p = posn.get(ip);
			if (p != null && Double.isFinite(p)) {
				firstXPos = p;
				break;
			}
		}
		if (Double.isNaN(firstXPos) || ip  >= imax) {
			return in;
		}

		Dataset xo = in[2 * ip];
		double firstStep = xo.getDouble(1) - xo.getDouble();

		Dataset[] result = Arrays.copyOf(in, in.length);
		resampleToPosn(ip++, firstXPos, firstXPos, firstStep, result);

		// shift first
		while (ip <= imax) {
			Double x = posn.get(ip);
			if (x != null && Double.isFinite(x)) {
				resampleToPosn(ip, x, firstXPos, firstStep, result);
			}
			ip++;
		}

		double delta = alignXPos - firstXPos;
		if (Double.isFinite(delta) && delta != 0) {
			for (ip = 0; ip <= imax; ip++) {
				result[2 * ip] = Maths.add(result[2 * ip], delta);
			}
		}

		return result;
	}

	private static void resampleToPosn(int ip, double x, double firstX, double firstStep, Dataset[] result) {
		int ci = 2 * ip;
		Dataset xo = result[ci];
		double xShift = firstX - x;
		double xSteps = xShift / firstStep;
		if (xSteps > 1) {
			xShift -= Math.floor(xSteps) * firstStep;
		}
		double xb = xo.getDouble();
		double s = (x - xb) / firstStep;
		int si = (int) Math.floor(s);
		if (s - si > 1e-15) { // not on sample point
			int size = (int) Math.floor((xo.getDouble(-1) - xb)/ firstStep);
			double sx = x - si * firstStep;
			DoubleDataset xn = DatasetFactory.createRange(size).imultiply(firstStep).iadd(sx);
			Dataset yn = Maths.interpolate(xo, result[ci + 1], xn, null, null);
			xn.iadd(xShift);
			result[ci] = xn;
			result[++ci] = yn;
		} else {
			result[ci] = Maths.add(xo, xShift);
		}
	}

	/**
	 * Shift data by given shifts relative to first X position
	 * @param firstXPos can be NaN to automatically find first X with valid position
	 * @param positions
	 * @param in
	 * @return x/y pairs of datasets where x and/or y have been shifted to align data
	 */
	private static Dataset[] shiftToPositions(double firstXPos, List<Double> positions, Dataset[] in) {
		int i = 0;
		final int imax = in.length;
		Dataset[] result = new Dataset[imax];
		for (Double p : positions) {
			double delta = 0;
			if (p != null && Double.isFinite(p)) {
				if (Double.isNaN(firstXPos)) {
					firstXPos = p;
				} else {
					delta = firstXPos - p;
				}
			}
			if (delta != 0) {
				result[i] = Maths.add(in[i], delta);
			} else {
				result[i] = in[i];
			}
			result[i + 1] = in[i + 1];
			i += 2;
		}
		return result;
	}

	private static Slice findCroppingSlice(boolean isXInc, Dataset x, int max, double lx, double hx) {
		if (!isXInc) {
			Slice s = new Slice(max, null, -1);
			x = x.getSliceView(s);
		}
	
		// slice plot according to x interval
		// it may happen that trace starts (or ends in chosen range)
		int li = 0;
		if (lx > x.getDouble(li)) {
			List<Double> c = DatasetUtils.crossings(x, lx);
			if (!c.isEmpty()) {
				li = (int) Math.ceil(c.get(0));
			}
		}
	
		int hi = max;
		if (hx < x.getDouble(hi)) {
			List<Double> c = DatasetUtils.crossings(x, hx);
			if (!c.isEmpty()) {
				hi = (int) Math.floor(c.get(0));
			}
		}
		assert hi > li;
	
		return isXInc ? new Slice(li, hi) : new Slice(max - hi, max - li);
	}

	/**
	 * @param dg
	 * @param x
	 * @param dy
	 * @param peak
	 * @param pos
	 * @return position of differentiated Gaussian
	 */
	private static double fitDiffGaussian(DiffGaussian dg, Dataset x, Dataset dy, double peak, int pos) {
		double hpy = 0.5 * dy.getDouble(pos);
		int max = dy.getSize() - 1;

		int hwhm;
		int beg;
		int end;
		int pw = pos;
		// find range to fit from peak to half-peak distance
		if (hpy < 0) {
			while (++pw <= max && dy.getDouble(pw) <= hpy);
			if (pw > max) {
				logger.warn("Could not find closest mid-height point");
				return Double.NaN;
			}
			hwhm = Math.max(2, pos - pw);
			// work out where zero crossing ends
			beg = pos;
			while (--beg >= 0 && dy.getDouble(beg) <= 0);
			beg = Math.max(beg, 0);
			end = Math.min(max, pos + 3*hwhm);
		} else {
			while (--pw >= 0 && dy.getDouble(pw) >= hpy);
			if (pw < 0) {
				logger.warn("Could not find closest mid-height point");
				return Double.NaN;
			}
			hwhm = Math.max(2, pos - pw);
			beg = Math.max(0, pos - 3*hwhm);
			end = pos;
			while (++end <= max && dy.getDouble(end) >= 0);
			end = Math.min(end, max);
			if (end < max - 1) { // include crossing
				end++;
			}
		}

		Dataset cdy = dy.getSliceView(new Slice(beg, end));
		logger.info("Using hw of {} in {}:{}", hwhm, beg, end);
		Dataset cdx = x.getSliceView(new Slice(beg, end));

		IParameter p = dg.getParameter(0);
		double step = cdx.getDouble(1) - cdx.getDouble();
		double cx = x.getDouble(pos);
		double width = hwhm*step;
		dg.setParameterValues(cx, peak, 1/(width*width));
		p.setLimits(cdx.getDouble(), x.getDouble(end));
		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.SIMPLEX_NM);
		double residual = Double.POSITIVE_INFINITY;
		try {
			if (logger.isInfoEnabled()) {
				AbstractDataset.setMaxLineLength(250);
				logger.info("Fitting {} over {}", dg, cdx.toString(true));
			}
			opt.optimize(new IDataset[] {cdx}, cdy, dg);
			residual = opt.calculateResidual();
			Dataset fdy = dg.calculateValues(cdx);
			if (logger.isInfoEnabled()) {
				logger.info("Fitted {} to {}", fdy.toString(true), cdy.toString(true));
				logger.info("Parameters are {}", Arrays.toString(dg.getParameterValues()));
				logger.info("Residual is {}", residual);
			}
		} catch (Exception e) {
			return Double.NaN;
		}

		return p.getValue();
	}

}
