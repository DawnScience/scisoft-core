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

import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Stats;
import uk.ac.diamond.scisoft.analysis.roi.CircularROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;
import uk.ac.diamond.scisoft.analysis.roi.PointROI;
import uk.ac.diamond.scisoft.analysis.roi.PolylineROI;

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

	private static final double ARC_LENGTH = 16;
	private static final double RADIAL_FRACTION = 0.03125; // 0.125;
	private static final int MAX_POINTS = 200;

	public static PolylineROI findPOIsNearCircle(AbstractDataset image, CircularROI circle) {
		return findPOIsNearCircle(image, circle, ARC_LENGTH, RADIAL_FRACTION, MAX_POINTS);
	}

	public static PolylineROI findPOIsNearCircle(AbstractDataset image, CircularROI circle, double arcLength, double radialFraction, int maxPoints) {
		return findPOIsNearEllipse(image, new EllipticalROI(circle), arcLength, radialFraction, maxPoints);
	}

	public static PolylineROI findPOIsNearEllipse(AbstractDataset image, EllipticalROI ellipse) {
		return findPOIsNearEllipse(image, ellipse, ARC_LENGTH, RADIAL_FRACTION, MAX_POINTS);
	}

	public static PolylineROI findPOIsNearEllipse(AbstractDataset image, EllipticalROI ellipse, double arcLength, double radialFraction, int maxPoints) {
		if (image.getRank() != 2) {
			throw new IllegalArgumentException("Dataset must have two dimensions");
		}

		final double aj = ellipse.getSemiAxis(0);
		final double an = ellipse.getSemiAxis(1);
		if (an < arcLength) {
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

		// find starting angle which is along shortest spoke
		TreeMap<Double, Double> lengths = new TreeMap<Double, Double>();
		
		lengths.put(Math.hypot(0-yc, 0-xc), Math.atan2(0-yc, 0-xc));
		lengths.put(Math.hypot(h-yc, 0-xc), Math.atan2(h-yc, 0-xc));
		lengths.put(Math.hypot(h-yc, w-xc), Math.atan2(h-yc, w-xc));
		lengths.put(Math.hypot(0-yc, w-xc), Math.atan2(0-yc, w-xc));
		final double pstart = lengths.firstEntry().getValue();

		final double pdelta = arcLength/an; // change in angle
		double rdelta = radialFraction*an; // semi-width of annulus of interest
		if (rdelta < 1) {
			logger.warn("Radial fraction was set too low: setting to {}", 1./an);
			rdelta = 1;
		}
		final double rsj = aj - rdelta;
		final double rej = aj + rdelta;
		final double rsn = an - rdelta;
		final double ren = an + rdelta;

		final int imax = (int) Math.ceil(Math.PI*2./pdelta);
//		final double[] quantiles = Stats.quantile(image, 0.0, 0.1, 0.2, 0.25, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75, 0.8, 0.9, 1.0);
//		System.err.println(Arrays.toString(quantiles));
//		final double threshold = quantiles[10]; // quantiles[6] + 0.5 * (quantiles[9] - quantiles[3]);
		final double threshold = (Double) Stats.median(image) + 0.5 * (Double) Stats.iqr(image);
		logger.debug("Threshold: {}", threshold);

		logger.debug("Major semi-axis = [{}, {}]; {}, {}", new Object[] {rsj, rej, Math.toDegrees(pstart), imax});
		final int[] start = new int[2];
		final int[] stop = new int[2];
		final int[] step = new int[] { 1, 1 };
		HashSet<PointROI> pointSet = new HashSet<PointROI>();
		for (int i = 0; i < imax; i++) {
			double p = pstart + i*pdelta;
			double cp = Math.cos(p);
			double sp = Math.sin(p);
			AbstractDataset sub;
			final int[] beg = new int[] {(int) (yc + rsj * sa * cp + rsn * ca * sp), (int) (xc + rsj * ca* cp - rsn * sa * sp)};
			final int[] end = new int[] {(int) (yc + rej * sa * cp + ren * ca * sp), (int) (xc + rej * ca* cp - ren * sa * sp)};
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

			final int[] pos = sub.maxPos();
//			System.err.printf("Slice: %s, %s has max at %s\n", Arrays.toString(start), Arrays.toString(stop), Arrays.toString(pos));
			pointSet.add(new PointROI(pos[1]+start[1], pos[0]+start[0]));
		}

		PolylineROI polyline = new PolylineROI();
		int pmax = maxPoints;
		for (PointROI p : pointSet) {
			int[] pos = p.getIntPoint();
			double v = image.getDouble(pos[1], pos[0]);
			if (v >= threshold) {
//				System.err.printf("Adding %f %s\n", v, Arrays.toString(pos));
				polyline.insertPoint(p);
				if (--pmax == 0)
					break;
//			} else {
//				System.err.println("Rejected!");
			}
		}
		logger.debug("Used {} of {} pixels", polyline.getNumberOfPoints(), pointSet.size());

		return polyline;
	}

	static class PeakCompare implements Comparator<PointROI> {
		private AbstractDataset image;

		public PeakCompare(AbstractDataset image) {
			this.image = image;
		}

		@Override
		public int compare(PointROI o1, PointROI o2) {
			return (int) Math.signum(image.getDouble(o2.getIntPoint())-image.getDouble(o1.getIntPoint()));
		}
	}
}
