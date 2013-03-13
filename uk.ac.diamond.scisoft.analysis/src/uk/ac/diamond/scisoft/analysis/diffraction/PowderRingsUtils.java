/*-
 * Copyright 2013 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import javax.measure.unit.SI;

import org.apache.commons.math.ConvergenceException;
import org.apache.commons.math.FunctionEvaluationException;
import org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction;
import org.apache.commons.math.analysis.MultivariateMatrixFunction;
import org.apache.commons.math.optimization.VectorialPointValuePair;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uncommons.maths.combinatorics.CombinationGenerator;

import uk.ac.diamond.scisoft.analysis.crystallography.HKL;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Comparisons;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Stats;
import uk.ac.diamond.scisoft.analysis.fitting.Generic1DFitter;
import uk.ac.diamond.scisoft.analysis.fitting.IConicSectionFitFunction;
import uk.ac.diamond.scisoft.analysis.fitting.IConicSectionFitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IdentifiedPeak;
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
import uk.ac.diamond.scisoft.analysis.roi.CircularROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalFitROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;
import uk.ac.diamond.scisoft.analysis.roi.PointROI;
import uk.ac.diamond.scisoft.analysis.roi.PolylineROI;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;
import uk.ac.diamond.scisoft.analysis.roi.RectangularROI;

/**
 * Utilities to fit powder rings
 */
public class PowderRingsUtils {
	private static Logger logger = LoggerFactory.getLogger(PowderRingsUtils.class);

//	/**
//	 * Given two (fitted) rings, which have parallel major axes, calculate detector distance
//	 * and beam angle relative to detector normal if angles are known
//	 * @param innerAxis     length on major axis of inner ring
//	 * @param firstGap      gap between outer and inner ring
//	 * @param secondGap     gap between inner and outer ring
//	 * @param innerTwoTheta scattering angle for inner ring
//	 * @param outerTwoTheta scattering angle for outer ring
//	 */
//	public static DetectorProperties calculateDetectorProperties(double innerAxis, double firstGap, double secondGap, double innerTwoTheta, double outerTwoTheta) {
//
//		DetectorProperties prop = new DetectorProperties();
//		return prop;
//	}

	/*
	 * Given circle in image
	 *  - generate radii/spokes so they are spaced a few pixels apart on circle
	 *  - find peak centres on spokes near circle if within image
	 *  - fit ellipse to those centres
	 *  - repeat for larger ellipses by scanning along longest spoke
	 */

	private static final double ARC_LENGTH = 8;
	private static final double RADIAL_DELTA = 8;
	private static final double RADIAL_MIN = 5*RADIAL_DELTA;
	private static final int MAX_POINTS = 200;

	private static final int PEAK_SMOOTHING = 3;
	private static final double MAX_FWHM_FACTOR = 2;
	private static final double RING_SEPARATION = 4;

	public static PolylineROI findPOIsNearCircle(IMonitor mon, AbstractDataset image, BooleanDataset mask, CircularROI circle) {
		return findPOIsNearCircle(mon, image, mask, circle, ARC_LENGTH, RADIAL_DELTA, MAX_POINTS);
	}

	public static PolylineROI findPOIsNearCircle(IMonitor mon, AbstractDataset image, BooleanDataset mask, CircularROI circle,
			double arcLength, double radialDelta, int maxPoints) {
		return findPOIsNearEllipse(mon, image, mask, new EllipticalROI(circle), arcLength, radialDelta, maxPoints);
	}

	public static PolylineROI findPOIsNearEllipse(IMonitor mon, AbstractDataset image, BooleanDataset mask, EllipticalROI ellipse) {
		return findPOIsNearEllipse(mon, image, mask, ellipse, ARC_LENGTH, RADIAL_DELTA, MAX_POINTS);
	}

	/**
	 * Find a set of points of interests near given ellipse from an image.
	 * <p>
	 * The ellipse is divided into sub-areas and these POIs are considered to
	 * be the locations of maximum pixel intensities found within those sub-areas.
	 * @param mon
	 * @param image
	 * @param mask (can be null)
	 * @param ellipse
	 * @param arcLength step size along arc in pixels
	 * @param radialDelta +/- value to define area to search
	 * @param maxPoints maximum number of points to return ( 
	 * @return polyline ROI
	 */
	public static PolylineROI findPOIsNearEllipse(IMonitor mon, AbstractDataset image, BooleanDataset mask, EllipticalROI ellipse,
			double arcLength, double radialDelta, int maxPoints) {
		if (image.getRank() != 2) {
			logger.error("Dataset must have two dimensions");
			throw new IllegalArgumentException("Dataset must have two dimensions");
		}
		if (mask != null && !image.isCompatibleWith(mask)) {
			logger.error("Mask must match image shape");
			throw new IllegalArgumentException("Mask must match image shape");
		}

		final double aj = ellipse.getSemiAxis(0);
		final double an = ellipse.getSemiAxis(1);
		if (an < arcLength) {
			logger.error("Ellipse/circle is too small");
			throw new IllegalArgumentException("Ellipse/circle is too small");
		}

		final double xc = ellipse.getPointX();
		final double yc = ellipse.getPointY();
		final double ang = ellipse.getAngle();
		final double ca = Math.cos(ang);
		final double sa = Math.sin(ang);
		final int[] shape = image.getShape();
		final int h = shape[0];
		final int w = shape[1];

		final double pdelta = arcLength / aj; // change in angle
		double rdelta = radialDelta; // semi-width of annulus of interest
		if (rdelta < 1) {
			logger.warn("Radial delta was set too low: setting to 1");
			rdelta = 1;
		}
		final double rsj = aj - rdelta;
		final double rej = aj + rdelta;
		final double rsn = an - rdelta;
		final double ren = an + rdelta;

		final int imax = (int) Math.ceil(Math.PI * 2. / pdelta);

		logger.debug("Major semi-axis = [{}, {}]; {}", new Object[] { rsj, rej, imax });
		final int[] start = new int[2];
		final int[] stop = new int[2];
		final int[] step = new int[] { 1, 1 };
		HashSet<PointROI> pointSet = new HashSet<PointROI>();
		for (int i = 0; i < imax; i++) {
			double p = i * pdelta;
			double cp = Math.cos(p);
			double sp = Math.sin(p);
			AbstractDataset sub;
			final int[] beg = new int[] { (int) (yc + rsj * sa * cp + rsn * ca * sp),
					(int) (xc + rsj * ca * cp - rsn * sa * sp) };
			final int[] end = new int[] { (int) (yc + rej * sa * cp + ren * ca * sp),
					(int) (xc + rej * ca * cp - ren * sa * sp) };
			start[0] = Math.max(0, Math.min(beg[0], end[0]));
			stop[0] = Math.min(h, Math.max(beg[0], end[0]));
			if (start[0] == stop[0]) {
				if (stop[0] == h) {
					start[0]--;
				} else {
					stop[0]++;
				}
			} else if (start[0] > stop[0] || start[0] >= h) {
				continue;
			}
			start[1] = Math.max(0, Math.min(beg[1], end[1]));
			stop[1] = Math.min(w, Math.max(beg[1], end[1]));
			if (start[1] == stop[1]) {
				if (stop[1] == w) {
					start[1]--;
				} else {
					stop[1]++;
				}
			} else if (start[1] > stop[1] || start[1] >= w) {
				continue;
			}
			sub = image.getSlice(start, stop, step);

			int[] pos = sub.maxPos();
			if (mask != null) {
				pos[0] += start[0];
				pos[1] += start[1];

				if (mask.get(pos)) {
					AbstractDataset sorted = DatasetUtils.sort(sub.flatten(), null);
					int l = sorted.getSize() - 1;
					do {
						double x = sorted.getElementDoubleAbs(l);
						pos = sub.getNDPosition(DatasetUtils.findIndexEqualTo(sub, x));
						pos[0] += start[0];
						pos[1] += start[1];
					} while (!mask.get(pos) && --l >= 0);
					if (l < 0) {
						logger.warn("Could not find unmasked value for slice!");
					} else {
						pointSet.add(new PointROI(pos[1], pos[0]));
					}
				}
			} else {
//			System.err.printf("Slice: %s, %s has max at %s\n", Arrays.toString(start), Arrays.toString(stop), Arrays.toString(pos));
				pointSet.add(new PointROI(pos[1]+start[1], pos[0]+start[0]));
			}

			if (mon != null)
				mon.worked(1);
		}

		// analyse pixel values
		int n = pointSet.size();
		double[] values = new double[n];
		int i = 0;
		for (PointROI p : pointSet) {
			int[] pos = p.getIntPoint();
			values[i++] = image.getDouble(pos[1], pos[0]);
		}

		DoubleDataset pixels = new DoubleDataset(values);
		System.err.println(pixels);

		// threshold with population stats from maxima
		logger.debug("Stats: {} {} {} {}", new Object[] {pixels.min(), pixels.mean(), pixels.max(),
				Arrays.toString(Stats.quantile(pixels, 0.25, 0.5, 0.75))});

		double threshold;
		if (n > maxPoints) {
			threshold = Stats.quantile(pixels, 1 - maxPoints/(double) n);
			logger.debug("Threshold: {} setting for highest {}", threshold, maxPoints);
		} else {
			threshold = (Double) pixels.mean() - 2 * (Double) Stats.iqr(pixels);
			logger.debug("Threshold: {} setting by mean - 2IQR", threshold);
		}

		PolylineROI polyline = new PolylineROI();
		if (threshold > (Double) pixels.min()) {
			for (PointROI p : pointSet) {
				int[] pos = p.getIntPoint();
				double v = image.getDouble(pos[1], pos[0]);
				if (v >= threshold) {
//					System.err.printf("Adding %f %s\n", v, Arrays.toString(pos));
					polyline.insertPoint(p);
//				} else {
//					System.err.println("Rejecting " + p + " = " + v);
				}
			}
		} else {
			for (PointROI p : pointSet) {
				polyline.insertPoint(p);
			}
		}
		if (mon != null)
			mon.worked(polyline.getNumberOfPoints());

		logger.debug("Used {} of {} pixels", polyline.getNumberOfPoints(), pointSet.size());

		return polyline;
	}

	public static EllipticalFitROI fitAndTrimOutliers(IMonitor mon, PolylineROI points, boolean circleOnly) {
		return fitAndTrimOutliers(mon, points, RADIAL_DELTA, circleOnly);
	}

	/**
	 * Fit an ellipse to given points, trim points if they fall outside given distance
	 * and re-fit
	 * @param points
	 * @param trimDelta trim distance
	 * @param circleOnly if true, then fit a circle
	 * @return fitted ellipse
	 */
	public static EllipticalFitROI fitAndTrimOutliers(IMonitor mon, PolylineROI points, double trimDelta, boolean circleOnly) {
		try {
			EllipticalFitROI efroi = new EllipticalFitROI(points, circleOnly);

			PolylineROI cpts = points;
			int n = cpts.getNumberOfPoints();
			IConicSectionFitter f = efroi.getFitter();
			IConicSectionFitFunction fn = f.getFitFunction(null, null);

			AbstractDataset d = fn.calcDistanceSquared(f.getParameters());

			// find outliers
			double h = trimDelta * trimDelta;
			double ds = d.max().doubleValue();
			logger.debug("Range: [0, {}] cf [{}, {}, {}]", new Object[] { h, d.min(), d.mean(), d.max() });
			if (ds < h)
				return efroi;

			BooleanDataset b = Comparisons.lessThanOrEqualTo(d, h);
			BooleanIterator it = d.getBooleanIterator(b, true);
			PolylineROI npts = new PolylineROI();
			while (it.hasNext()) {
				npts.insertPoint(cpts.getPoint(it.index));
			}
			int m = npts.getNumberOfPoints();
			if (m < n) {
				logger.debug("Found some outliers: {}/{}", n - m, n);
				efroi.setPoints(npts);
				if (mon != null)
					mon.worked(m);
			}

			return efroi;
		} catch (Exception e) {
			logger.error("Problem with trimming: {}", e);
			throw new IllegalArgumentException("Problem!: ", e);
		}
	}

	/**
	 * Find other ellipses from given ellipse and image.
	 * <p>
	 * This is done by looking at the box profile along spokes from the
	 * given centre and finding peaks. Then the distance out to those peaks is used
	 * to search for more POIs and so more ellipses
	 * @param image
	 * @param mask (can be null)
	 * @param roi initial ellipse
	 * @return list of ellipses
	 */
	public static List<EllipticalROI> findOtherEllipses(IMonitor mon, AbstractDataset image, BooleanDataset mask, EllipticalROI roi) {
		return findOtherEllipses(mon, image, mask, roi, RADIAL_MIN, RADIAL_DELTA, ARC_LENGTH, RADIAL_DELTA, MAX_POINTS);
	}

	/**
	 * Find other ellipses from given ellipse and image.
	 * <p>
	 * This is done by looking at the box profile along spokes from the
	 * given centre and finding peaks. Then the distance out to those peaks is used
	 * to search for more POIs and so more ellipses
	 * @param image
	 * @param mask (can be null)
	 * @param roi initial ellipse
	 * @param radialMin
	 * @param radialDelta
	 * @param arcLength
	 * @param trimDelta
	 * @param maxPoints
	 * @return list of ellipses
	 */
	public static List<EllipticalROI> findOtherEllipses(IMonitor mon, AbstractDataset image, BooleanDataset mask, EllipticalROI roi,
			double radialMin, double radialDelta, double arcLength, double trimDelta, int maxPoints) {
		if (image.getRank() != 2) {
			logger.error("Dataset must have two dimensions");
			throw new IllegalArgumentException("Dataset must have two dimensions");
		}
		if (mask != null && !image.isCompatibleWith(mask)) {
			logger.error("Mask must match image shape");
			throw new IllegalArgumentException("Mask must match image shape");
		}

		// explore all corners
		final int[] shape = image.getShape();
		final int h = shape[0];
		final int w = shape[1];
		double[] ec = roi.getPoint();
		TreeSet<Double> majors = new TreeSet<Double>();

		// TODO farm this out across several threads
		findMajorAxes(mon, majors, image, mask, roi, radialMin, radialDelta, ec, 0 - ec[0], 0 - ec[1]); // TL
		findMajorAxes(mon, majors, image, mask, roi, radialMin, radialDelta, ec, w - ec[0], 0 - ec[1]); // TR
		findMajorAxes(mon, majors, image, mask, roi, radialMin, radialDelta, ec, w - ec[0], h - ec[1]); // BR
		findMajorAxes(mon, majors, image, mask, roi, radialMin, radialDelta, ec, 0 - ec[0], h - ec[1]); // BL

		// and finally find POIs
		List<EllipticalROI> ells = new ArrayList<EllipticalROI>();
		double major = roi.getSemiAxis(0);
		double aspect = roi.getSemiAxis(0)/roi.getSemiAxis(1);
		double last = Double.NEGATIVE_INFINITY;
		for (double a : majors) {
			System.err.println("Current " + a + ", last " + last);
			if (Math.abs(a - last) < RING_SEPARATION) { // omit close rings
				last = a;
				System.err.println("Dropped as too close");
				continue;
			}
			if (Math.abs(a - major) < RING_SEPARATION) {
				last = major;
				System.err.println("Add original");
				ells.add(roi);
			} else {
				EllipticalROI er = new EllipticalROI(a, a/aspect, roi.getAngle(), ec[0], ec[1]);
				try {
					PolylineROI polyline = findPOIsNearEllipse(mon, image, mask, er, arcLength, radialDelta, maxPoints);
					if (polyline.getNumberOfPoints() > 2) {
						er = fitAndTrimOutliers(mon, polyline, trimDelta, roi.isCircular());
						if (Math.abs(er.getSemiAxis(0) - last) < RING_SEPARATION) { // omit close rings
							last = a;
							System.err.println("Dropped as fit is too close");
							continue;
						}
						double[] c = er.getPointRef();
						if (Math.hypot(c[0] - ec[0], c[1] - ec[1]) > radialDelta) {
							last = a; // omit fits with far-off centres
							System.err.println("Dropped as centre is far-off");
							continue;
						}
						last = er.getSemiAxis(0);
						ells.add(er);
					} else {
						logger.warn("Could not find enough points at {}", er);
					}
				} catch (IllegalArgumentException e) {
					logger.debug("Problem with {}", er, e);
					last = a;
				}
				if (mon != null)
					mon.worked(1);
			}
		}

		return ells;
	}

	/**
	 * Find major axes by looking along thick line given by relative coordinates to centre for
	 * maximum intensity values
	 * @param mon
	 * @param axes
	 * @param image
	 * @param mask
	 * @param roi
	 * @param radialMin
	 * @param radialDelta
	 * @param centre
	 * @param dx
	 * @param dy
	 */
	private static void findMajorAxes(IMonitor mon, TreeSet<Double> axes, AbstractDataset image, AbstractDataset mask, EllipticalROI roi, double radialMin, double radialDelta, double[] centre, double dx, double dy) {
		RectangularROI rroi = new RectangularROI();
		rroi.setPoint(centre);
		rroi.setAngle(Math.atan2(dy, dx));
		rroi.setLengths(Math.hypot(dx, dy) - radialMin, radialDelta);
		rroi.translate(0, -0.5); //rroi.getPoint(0, -0.5));
		rroi.setClippingCompensation(true);
		AbstractDataset profile = ROIProfile.maxInBox(image, mask, rroi)[0];
		List<IdentifiedPeak> peaks = Generic1DFitter.findPeaks(AbstractDataset.arange(profile.getSize(), AbstractDataset.INT), profile, PEAK_SMOOTHING);
		if (mon != null)
			mon.worked(profile.getSize());

		DescriptiveStatistics stats = new DescriptiveStatistics();
		for (IdentifiedPeak p : peaks) {
			if (p.getPos() < radialMin) {
				continue;
			}
			stats.addValue(p.getArea());
			System.err.printf("P %f A %f W %f H %f\n", p.getPos(), p.getArea(), p.getFWHM(), p.getHeight());
		}
		
		double area = stats.getMean() + 0.5*(stats.getPercentile(75) - stats.getPercentile(25));
		logger.debug("Area: {}", stats);
		logger.debug("Minimum threshold: {}", area);

		double majorFactor = roi.getSemiAxis(0)/roi.getDistance(rroi.getAngle());
		double maxFWHM = MAX_FWHM_FACTOR*radialDelta;
		for (IdentifiedPeak p : peaks) {
			double l = p.getPos();
			if (l < radialMin) {
				continue;
			}
			System.err.println(p);
			// filter on area and FWHM
			if (p.getFWHM() > maxFWHM) {
				continue;
			}
			if (p.getArea() < area) {
				break;
			}
			axes.add(l*majorFactor);
		}
		if (mon != null)
			mon.worked(peaks.size());

	}

	static QSpaceFitFunction createQFitFunction(List<EllipticalROI> ellipses, double pix, int n) {
		double[] known1 = new double[n];
		double[] known2 = new double[n];

		double bestAngle = 0;
		double min = Double.POSITIVE_INFINITY;
		for (int i = 0; i < n; i++) {
			EllipticalROI e = ellipses.get(i);
			if (e instanceof EllipticalFitROI) {
				double rms = ((EllipticalFitROI) e).getRMS();
				if (rms < min) {
					min = rms;
					bestAngle = e.getAngle();
				}
			}
			double a = e.getSemiAxis(0);
			double rs = a/e.getSemiAxis(1);
			a *= pix;
			rs = 1./(rs*rs);
			known1[i] = a*rs; // in mm
			known2[i] = rs;
		}
		return new QSpaceFitFunction(known1, known2, bestAngle);
	}

	/**
	 * @param mon
	 * @param detector
	 * @param env
	 * @param ellipses
	 * @param spacings a list of possible spacings
	 * @return q-space
	 */
	public static QSpace fitToQSpace(IMonitor mon, DetectorProperties detector, DiffractionCrystalEnvironment env, List<EllipticalROI> ellipses, List<HKL> spacings) {
		double[] init = new double[] {env.getWavelength()*1e-7, detector.getDetectorDistance(), Math.sin(detector.getTiltAngle())};
		logger.debug("Init: {}", init);
		int n = ellipses.size();

		double dmin = spacings.get(0).getD().doubleValue(SI.MILLIMETRE);
		{
			double rmin = init[1] * Math.tan(2.0 * Math.asin(0.5 * init[0] / dmin)) / detector.getVPxSize();
			int l = 0;
			for (EllipticalROI e : ellipses) {
				if (e.getSemiAxis(0) >= rmin)
					break;
				l++;
			}

			if (l >= n) {
				throw new IllegalArgumentException("Maybe all rings are too small!");
			}
			if (l > 0) {
				logger.debug("Discarding first {} rings", l);
				ellipses = ellipses.subList(l, n);
				n = ellipses.size();
			}
		}
		

		if (n > spacings.size()) {
			logger.warn("The number of d-spacings ({}) should be greater than or equal to {}: using inner rings", spacings.size(), n);
			n = spacings.size();
		}
		logger.debug("Using {} rings:", n);
		{
			for (EllipticalROI e : ellipses)
				logger.debug("    {}", e);
		}

		QSpaceFitFunction f = createQFitFunction(ellipses, detector.getVPxSize(), n);
		if (mon != null)
			mon.worked(1);

		// set up a combination generator for all (but first spacing)
		List<Double> s = new ArrayList<Double>();
		for (int i = 0, imax = spacings.size(); i < imax; i++ ) {
			HKL d = spacings.get(i);
			s.add(d.getD().doubleValue(SI.MILLIMETRE));
		}
		CombinationGenerator<Double> gen = new CombinationGenerator<Double>(s, n);

		double min = Double.POSITIVE_INFINITY;
		LevenbergMarquardtOptimizer opt = new LevenbergMarquardtOptimizer();
//		opt.setMaxEvaluations(2000);
		double[] params = null;
		List<Double> fSpacings = null;

		for (List<Double> list : gen) { // find combination that minimizes residuals
//			list.add(0, dmin);
			f.setSpacings(list);

			try {
				VectorialPointValuePair result = opt.optimize(f, f.getTarget(), f.getWeight(), init);

//				logger.info("Q-space fit: rms = {}, x^2 = {}", opt.getRMS(), opt.getChiSquare());
				double res = opt.getRMS();
				if (res < min) {
					min = res;
					params = result.getPoint();
					fSpacings = list;
				}
			} catch (FunctionEvaluationException e) {
				logger.error("Function evaluation problem", e);
				// cannot happen
			} catch (IllegalArgumentException e) {
				logger.error("Start point has wrong dimension", e);
				// should not happen!
			} catch (ConvergenceException e) {
				logger.error("Convergence problem: max iterations ({}) exceeded", opt.getMaxIterations());
				// ignore
//				throw new IllegalArgumentException("Problem with optimizer converging");
			}
			if (mon != null)
				mon.worked(10);
		}

		if (fSpacings == null || params == null) {
			logger.warn("Problem with fitting - as could not find a single fit!");
			return null;
		}

		double wlen = params[0]*1e7;
		double tilt = Math.toDegrees(Math.asin(params[2]));
		logger.debug("Parameters: w {}, D {}, e {} (min {})", new Object[] {wlen, params[1], tilt, min});
		logger.debug("Spacings used: {}", fSpacings);
		f.setSpacings(fSpacings);
		logger.debug("Residual values for {}: {}", f.getRMS(params), f.value);

		DetectorProperties nDetector = new DetectorProperties(detector);
		nDetector.setDetectorDistance(params[1]);
		nDetector.setNormalAnglesInDegrees(tilt, 0, f.getAngle());
		DiffractionCrystalEnvironment nEnv = new DiffractionCrystalEnvironment(wlen);
		return new QSpace(nDetector, nEnv);
	}

	/**
	 * LS function uses parameters: wavelength (mm), perpendicular distance (mm), sine of tilt angle
	 */
	static class QSpaceFitFunction implements DifferentiableMultivariateVectorialFunction {

		private double[] target;
		private double[] spacing; // in mm
		private double[] value;

		private int n;
		private int twoN;
		private double[][] jac;
		private double[] weight;
		private double angle;

		private static final double EXTRA_WEIGHT = 10; // for distance

		public QSpaceFitFunction(double[] known1, double[] known2, double bestAngle) {
			n = known1.length;
			twoN = 2 * n;
			angle = bestAngle;
			target = new double[twoN];
			weight = new double[twoN];
			for (int i = 0, j = 0; i < n; i++) {
				double k = known1[i];
				target[j] = k;
				weight[j++] = EXTRA_WEIGHT/(k*k); // make residual similar magnitude to known2's
				target[j] = known2[i];
				weight[j++] = 1;
			}
			spacing = new double[n];
			value = new double[twoN];
			jac = new double[twoN][3];
		}

		public double getAngle() {
			return angle;
		}

		public double[] getWeight() {
			return weight;
		}

		public double[] getTarget() {
			return target;
		}

		/**
		 * @param spacings (in mm)
		 */
		public void setSpacings(List<Double> spacings) {
			for (int i = 0; i < n; i++) {
				spacing[i] = spacings.get(i);
			}
		}

		/**
		 * @param arg wavelength (mm), perpendicular distance (mm), sine of tilt angle
		 * @return values
		 */
		@Override
		public double[] value(double[] arg) throws FunctionEvaluationException, IllegalArgumentException {
			calcValues(arg);
			return value;
		}

		private void calcValues(double[] arg) {
			double wlen = arg[0];
			double dist = arg[1];
			double setas = arg[2] * arg[2];

			for (int i = 0, j = 0; i < n; i++) {
				double si = 0.5*wlen/spacing[i];
				double x = 0.5 - si*si;
				double ti = Math.sqrt(0.5 + x)*si/x;
				value[j++] = ti*dist;
				value[j++] = 1 - setas*(1+ti*ti);
			}
		}

		private void calcResidualValues(double[] arg) {
			double wlen = arg[0];
			double dist = arg[1];
			double setas = arg[2] * arg[2];

			for (int i = 0, j = 0; i < n; i++) {
				double si = 0.5*wlen/spacing[i];
				double x = 0.5 - si*si;
				double ti = Math.sqrt(0.5 + x)*si/x;
				double t = ti*dist - target[j];
				value[j] = t*t*weight[j];
				j++;
				t = 1 - setas*(1+ti*ti) - target[j];
				value[j] = t*t*weight[j];
				j++;
			}
		}

		/**
		 * @param arg wavelength (mm), perpendicular distance (mm), sine of tilt angle
		 * @return root mean square of residuals
		 */
		public double getRMS(double[] arg) {
			calcResidualValues(arg);
			double s = 0;
			for (int i = 0; i < twoN; i++) {
				s += value[i];
			}
			return s/twoN;
		}

		@Override
		public MultivariateMatrixFunction jacobian() {
			return new MultivariateMatrixFunction() {
				@Override
				public double[][] value(double[] point) throws FunctionEvaluationException, IllegalArgumentException {
					calcJacobian(point);
					return jac;
				}
			};
		}

		private void calcJacobian(double[] arg) {
			double wlen = arg[0];
			double hdist = 0.5 * arg[1];
			double hseta = arg[2];
			double setas = hseta * hseta;
			hseta *= 0.5;

			for (int i = 0, j = 0; i < n; i++) {
				double otdi = 0.5 / spacing[i];
				double si = wlen * otdi;
				double x = 0.5 - si * si;
				double ti = Math.sqrt(0.5 + x) * si / x;
				double fx = otdi / x;

				jac[j][0] = hdist * ti * fx;
				jac[j][1] = ti;
				jac[j][2] = 0;
				j++;

				double gx = 1 / (x * x);
				jac[j][0] = -fx * setas * si * gx;
				jac[j][1] = 0;
				jac[j][2] = -hseta * gx;
				j++;
			}
		}
	}
}
