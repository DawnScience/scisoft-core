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
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToDatasetFunction;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.DiffGaussian;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;

/**
 * Fit and align datasets to single side of a Gaussian peak
 */
public class AlignToHalfGaussianPeak implements DatasetToDatasetFunction {

	protected static final Logger logger = LoggerFactory.getLogger(AlignToHalfGaussianPeak.class);
	private boolean useRisingSide = false;
	private boolean forceToPosition = false;
	private double position = 0;
	private double lx = -Double.MAX_VALUE, hx = Double.MAX_VALUE;

	private DiffGaussian dg = new DiffGaussian();
	private boolean resample;

	/**
	 * @param useRisingSide if true fit to the side that rises (as the index increases)
	 */
	public AlignToHalfGaussianPeak(boolean useRisingSide) {
		this.useRisingSide = useRisingSide;
	}

	/**
	 * @param position value to align x
	 */
	public void setAlignPosition(double position) {
		this.position = position;
	}

	/**
	 * @param forceToPosition if true, always align 
	 */
	public void setForceToPosition(boolean forceToPosition) {
		this.forceToPosition = forceToPosition;
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

	public void setResample(boolean resampleX) {
		this.resample = resampleX;
	}

	/**
	 * @param datasets x/y pairs of datasets
	 * @return x/y pairs of datasets where x has been shifted to align data 
	 */
	@Override
	public List<? extends IDataset> value(IDataset... datasets) {
		List<Dataset> result = new ArrayList<>();
		List<Double> iShifts = new ArrayList<>();

		int imax = datasets.length;
		for (int i = 0; i < imax; i += 2) {
			Dataset x = DatasetUtils.convertToDataset(datasets[i]);
			result.add(x);
			Dataset y = DatasetUtils.convertToDataset(datasets[i + 1]);
			result.add(y);
			Monotonicity m = Comparisons.findMonotonicity(x);
			Slice cs = findCroppingSlice(m, x, Math.min(x.getSize(), y.getSize()), lx, hx);
			if (cs == null) {
				logger.warn("Trace {} ignored as it is not strictly monotonic", i / 2); //t.getName());
				iShifts.add(null);
				continue;
			}

			logger.debug("Cropping to {}", cs);
			Dataset cy = y.getSliceView(cs);

			// look for 1st peak in derivative
			Dataset dy = Maths.centralDifference(cy, 0);
			int pos = useRisingSide ? dy.argMax(true) : dy.argMin(true);

			// fit to derivative of Gaussian???
			double c = fitDiffGaussian(dg, dy, cy.max(true).doubleValue()/5., pos);
			iShifts.add(cs.getStart() + c);
		}

		logger.debug("Shifts are {}", iShifts);

		int i = 0;
		double firstIShift = Double.NaN;
		double firstXShift = forceToPosition ? position : Double.NaN;
		Dataset firstX = null;
		if (forceToPosition) {
			if (resample) {
				for (Double s : iShifts) {
					if (s != null && Double.isFinite(s)) {
						firstIShift = s;
						firstX = result.get(i);
						double xShift = Maths.interpolate(firstX, firstIShift);
						logger.debug("Shift x {} by {}", i, xShift);
						firstX = Maths.subtract(firstX, xShift);
						result.set(i, firstX);
						break;
					}
					i += 2;
				}
			}
		} else { // make relative to first plot
			firstX = result.get(i);
			for (Double s : iShifts) {
				if (s != null && Double.isFinite(s)) {
					firstIShift = s;
					Dataset x = result.get(i);
					firstXShift = Maths.interpolate(x, firstIShift);
					i += 2;
					break;
				}
				i += 2;
			}
		}
		logger.debug("First shift is {} (index = {})", firstXShift, firstIShift);

		if (Double.isFinite(firstXShift)) {
			for (; i < imax; i += 2) {
				Double s = iShifts.get(i / 2);
				if (s != null && Double.isFinite(s)) {
					Dataset x = result.get(i);
					if (resample) {
						double delta = s - firstIShift;
						logger.debug("Resampling {} by {}", i + 1, delta);
						if (delta != 0) {
							int iDelta = (int) Math.ceil(delta);
							Slice slice = new Slice(firstX.getSize() - iDelta);
							logger.debug("Slicing aligned data: {}", slice);
							result.set(i, firstX.getSliceView(slice));
							Dataset y = result.get(i + 1);
							// assume x is uniformly spaced (otherwise we need to interpolate by new x values)
							result.set(i + 1, RegisterData1D.shiftData(y, delta).getSliceView(slice));
						}
					} else {
						double delta = Maths.interpolate(x, s) - firstXShift;
						logger.debug("Shifting {} by {}", i, delta);
						result.set(i, Maths.subtract(x, delta));
					}
				}
			}
		}

		return result;
	}

	private static Slice findCroppingSlice(Monotonicity m, Dataset x, int l, double lx, double hx) {
		if (m == Monotonicity.STRICTLY_DECREASING) {
			Slice s = new Slice(l - 1, null, -1);
			x = x.getSliceView(s);
		} else if (m != Monotonicity.STRICTLY_INCREASING) {
			return null;
		}
	
		// slice plot according to x interval
		// it may happen that trace starts (or ends in chosen range)
		int li = 0;
		if (lx > x.getDouble(li)) {
			List<Double> c = DatasetUtils.crossings(x, lx);
			if (c.size() > 0) {
				li = (int) Math.ceil(c.get(0));
			}
		}
	
		int hi = l - 1;
		if (hx < x.getDouble(hi)) {
			List<Double> c = DatasetUtils.crossings(x, hx);
			if (c.size() > 0) {
				hi = (int) Math.floor(c.get(0));
			}
		}
		assert hi > li;
	
		return m == Monotonicity.STRICTLY_INCREASING ? new Slice(li, hi) : new Slice(l - 1 - hi, l - 1 - li);
	}

	private static double fitDiffGaussian(DiffGaussian dg, Dataset dy, double peak, int pos) {
		double hpy = 0.5 * dy.getDouble(pos);
		boolean neg = hpy < 0;
		int max = dy.getSize();

		int hwhm;
		int beg, end;
		if (neg) {
			int pw = pos + 1;
			// find range to fit from peak to half-peak distance
			while (dy.getDouble(pw) < hpy && ++pw < max) {
			}
			if (pw >= max) {
				logger.warn("Could not find closest mid-height point");
				return Double.NaN;
			}
			hwhm = Math.max(2, pw - pos);
			// work out where zero crossing ends
			beg = pos - 1;
			while (dy.getDouble(beg) <= 0 && --beg >= 0) {
			}
			beg++;
			end = Math.min(max, pos + 4*hwhm);
		} else {
			int pw = pos - 1;
			while (dy.getDouble(pw) > hpy && --pw >= 0) {
			}
			if (pw < 0) {
				logger.warn("Could not find closest mid-height point");
				return Double.NaN;
			}
			hwhm = Math.max(2, pos - pw);
			beg = Math.max(0, pos - 4*hwhm);
			end = pos + 1;
			while (dy.getDouble(end) >= 0 && ++end < max) {
			}
		}
		Dataset cdy = dy.getSliceView(new Slice(beg, end));
		logger.info("Using hw of {} in {}:{}", hwhm, beg, end);
		logger.info(cdy.toString(true));
		Dataset cdx = DatasetFactory.createRange(beg, end, 1.0);

		IParameter p = dg.getParameter(0);
		double cen = neg ? beg : end;
		dg.setParameterValues(cen - 0.5, peak, 1e-1/hwhm);
		p.setLimits(cen - 1, cen);
		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.SIMPLEX_NM);
		double residual = Double.POSITIVE_INFINITY;
//		double[] errors = null;
		try {
			opt.optimize(new IDataset[] {cdx}, cdy, dg);
			residual = opt.calculateResidual();
			logger.info("Residual is {}", residual);
//			errors = opt.guessParametersErrors();
		} catch (Exception e) {
			return Double.NaN;
		}

		return dg.getParameterValue(0);
	}

}
