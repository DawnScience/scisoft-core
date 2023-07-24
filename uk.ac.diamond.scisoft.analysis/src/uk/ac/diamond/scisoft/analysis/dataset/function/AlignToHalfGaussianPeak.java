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
import org.eclipse.dawnsci.analysis.dataset.impl.function.DatasetToNumberFunction;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Comparisons.Monotonicity;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
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
	private double lx = -Double.MAX_VALUE, hx = Double.MAX_VALUE;

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
	 * @return fitted positions of derivative of Gaussian (in index values)
	 */
	@Override
	public List<Double> value(IDataset... datasets) {
		List<Double> iPositions = new ArrayList<>(); // positions of derivative of Gaussian in index values

		final int imax = datasets.length;
		for (int i = 0; i < imax; i += 2) {
			Dataset x = DatasetUtils.convertToDataset(datasets[i]);
			Dataset y = DatasetUtils.convertToDataset(datasets[i + 1]);
			Monotonicity m = Comparisons.findMonotonicity(x);
			Slice cs = findCroppingSlice(m, x, Math.min(x.getSize(), y.getSize()), lx, hx);
			if (cs == null) {
				logger.warn("Trace {} ignored as it is not strictly monotonic", i); //t.getName());
				iPositions.add(null);
				continue;
			}

			logger.debug("Cropping to {}", cs);
			Dataset cy = y.getSliceView(cs);

			// look for 1st peak in derivative
			Dataset dy = Maths.centralDifference(cy, 0);
			int pos = useRisingSide ? dy.argMax(true) : dy.argMin(true);

			// fit to derivative of Gaussian
			double c = fitDiffGaussian(dg, dy, cy.max(true).doubleValue()/5., pos);
			iPositions.add(cs.getStart() + c);
		}

		logger.debug("Positions are {}", iPositions);
		return iPositions;
	}

	/**
	 * Align data to given positions
	 * @param resample
	 * @param forceToPosition
	 * @param position
	 * @param iPositions
	 * @param datasets
	 * @return x/y pairs of datasets where x and/or y have been shifted to align data
	 */
	public static Dataset[] alignToPositions(boolean resample, boolean forceToPosition, double position, List<Double> iPositions, Dataset... datasets) {
		List<Double> shifts = calculateShifts(resample, forceToPosition, position, iPositions, datasets);

		return shiftData(shifts, datasets);
	}

	/**
	 * Calculate shifts for aligning datasets
	 * @param resample
	 * @param forceToPosition
	 * @param position
	 * @param iPositions
	 * @param datasets
	 * @return x/y pairs of shifts (y shifts are in index space)
	 */
	public static List<Double> calculateShifts(boolean resample, boolean forceToPosition, double position, List<Double> iPositions, Dataset... datasets) {
		List<Double> shifts = new ArrayList<>();
		if (resample) {
			int firstI = 0;
			if (forceToPosition) {
				firstI = calcFirstXShift(shifts, datasets, iPositions);
			} else {
				firstI = calcXRelativeToFirstX(shifts, datasets, iPositions, Double.NaN, true);
			}
			calcYShifts(shifts, iPositions, firstI);
		} else {
			calcXRelativeToFirstX(shifts, datasets, iPositions, forceToPosition ? position : Double.NaN, false);
		}

		System.err.println("Shifts are " + shifts);
		return shifts;
	}

	/**
	 * Shift first X and set all other X to same
	 * @param shifts (should be empty)
	 * @param data
	 * @param iPositions
	 * @return index of first X with valid position
	 */
	private static int calcFirstXShift(List<Double> shifts, Dataset[] data, List<Double> iPositions) {
		Dataset firstX = null;
		int i = 0;
		int firstI = 0;
		for (Double p : iPositions) {
			if (p != null && Double.isFinite(p) && firstX == null) {
				firstX = data[i];
				double xPos = Maths.interpolate(firstX, p);
				logger.debug("Shifting 1st x {} by {}", i, xPos);
				shifts.add(-xPos);
				firstI = i;
			} else {
				shifts.add(null);
			}
			shifts.add(null); // placeholder for y shift
			i += 2;
		}

		return firstI;
	}

	/**
	 * Shift rest (if leaveRest is false) relative to first X position
	 * @param shifts empty list to populate
	 * @param data x/y pairs of 
	 * @param iPositions
	 * @param firstXPos can be null to automatically find first X with valid position
	 * @param leaveRest if true, then do not shift any
	 * @return index of first X with valid position
	 */
	private static int calcXRelativeToFirstX(List<Double> shifts, Dataset[] data, List<Double> iPositions, double firstXPos, boolean leaveRest) {
		int i = 0;
		int firstI = 0;
		for (Double p : iPositions) {
			if (p != null && Double.isFinite(p)) {
				Dataset x = data[i];
				if (Double.isNaN(firstXPos)) {
					firstXPos = Maths.interpolate(x, p);
					shifts.add(null); // leave first unshifted
					firstI = i;
				} else {
					if (leaveRest) {
						shifts.add(null);
					} else {
						double delta = Maths.interpolate(x, p) - firstXPos;
						logger.debug("Shifting x {} by {}", i, delta);
						shifts.add(-delta);
					}
				}
			} else {
				shifts.add(null);
			}
			shifts.add(null); // placeholder for y shift
			i += 2;
		}

		return firstI;
	}

	/**
	 * Calculate shift to resample Y data
	 * @param shifts
	 * @param iPositions
	 * @param firstI
	 */
	private static void calcYShifts(List<Double> shifts, List<Double> iPositions, int firstI) {
		double firstIShift = iPositions.get(firstI);
		for (int i = firstI, imax = shifts.size(); i < imax; i += 2) {
			Double p = iPositions.get(i / 2);
			if (p != null && Double.isFinite(p)) {
				double delta = p - firstIShift;
				if (delta != 0) {
					logger.debug("Resampling {} by {}", i + 1, delta);
					shifts.set(i + 1, delta);
				}
			}
		}
	}

	/**
	 * Shift data by given shifts
	 * @param shifts x/y pairs of shifts
	 * @param datasets
	 * @return x/y pairs of datasets where x and/or y have been shifted to align data
	 */
	public static Dataset[] shiftData(List<Double> shifts, Dataset... datasets) {
		final int imax = datasets.length;
		Dataset[] result = new Dataset[imax];
		double[] firstXShift = {Double.NaN};
		for (int i = 0; i < imax; i += 2) {
			Dataset[] shifted = shiftData(firstXShift, shifts.get(i), shifts.get(i + 1), datasets[i], datasets[i+1]);
			result[i] = shifted[0];
			result[i + 1] = shifted[1];
		}
		return result;
	}

	/**
	 * Shift data by given shifts
	 * @param shiftX x shift
	 * @param shiftY y shift
	 * @param x
	 * @param y
	 * @return x/y pair of datasets where x and/or y have been shifted to align data
	 */
	public static Dataset[] shiftData(double[] firstXShift, Double shiftX, Double shiftY, Dataset x, Dataset y) {
		if (shiftX != null) {
			if (shiftX != 0) {
				x = Maths.add(x, shiftX);
				if (Double.isNaN(firstXShift[0])) {
					firstXShift[0] = shiftX;
				}
			}
		} else {
			if (shiftY != null) { // resample case
				if (shiftY != 0) {
					// assume x is uniformly spaced (otherwise we need to interpolate by new x values)
					y = RegisterData1D.shiftData(y, shiftY);
					if (Double.isFinite(firstXShift[0])) {
						x = Maths.add(x, firstXShift[0]);
					}
				}
			}
		}
		return new Dataset[] {x, y};
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

	/**
	 * TODO allow x dataset too
	 * @param dg
	 * @param dy
	 * @param peak
	 * @param pos
	 * @return position of differentiated Gaussian
	 */
	private static double fitDiffGaussian(DiffGaussian dg, Dataset dy, double peak, int pos) {
		double hpy = 0.5 * dy.getDouble(pos);
		boolean neg = hpy < 0;
		int max = dy.getSize();

		int hwhm;
		int beg, end;
		if (neg) {
			int pw = pos + 1;
			// find range to fit from peak to half-peak distance
			while (pw < max && dy.getDouble(pw) < hpy) {
				pw++;
			}
			if (pw >= max) {
				logger.warn("Could not find closest mid-height point");
				return Double.NaN;
			}
			hwhm = Math.max(2, pw - pos);
			// work out where zero crossing ends
			if (pos <= 0) {
				beg = 0;
			} else {
				beg = pos - 1;
				while (dy.getDouble(beg) <= 0 && --beg >= 0) {
				}
				beg++;
			}
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
