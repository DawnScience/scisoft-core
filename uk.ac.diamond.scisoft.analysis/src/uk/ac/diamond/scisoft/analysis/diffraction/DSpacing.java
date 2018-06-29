/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.api.roi.IOrientableROI;
import org.eclipse.dawnsci.analysis.api.roi.IParametricROI;
import org.eclipse.dawnsci.analysis.api.roi.IROI;
import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalFitROI;
import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROI;
import org.eclipse.dawnsci.analysis.dataset.roi.HyperbolicROI;
import org.eclipse.dawnsci.analysis.dataset.roi.ParabolicROI;
import org.eclipse.dawnsci.analysis.dataset.roi.PolylineROI;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.IDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.uom.NonSI;
import uk.ac.diamond.scisoft.analysis.crystallography.CalibrantSpacing;
import uk.ac.diamond.scisoft.analysis.crystallography.HKL;

/**
 * Utility class to hold methods that calculate or use d-spacings
 */
public class DSpacing {

	private static Logger logger = LoggerFactory.getLogger(DSpacing.class);

	/**
	 * Calculate d-spacings from given positions of Bragg diffraction spots
	 * @param detector
	 * @param diffExp
	 * @param pos
	 *            An array of x,y positions of spots on the detector in pixels. There must be an even number of values
	 * @return array of inter-spot distances (d spacing) in angstroms
	 */
	public static double[] dSpacingsFromPixelCoords(DetectorProperties detector, DiffractionCrystalEnvironment diffExp,
			int... pos) {
		double[] dpos = new double[pos.length];
		for (int i = 0; i < pos.length; i++)
			dpos[i] = pos[i];
		return dSpacingsFromPixelCoords(detector, diffExp, dpos);
	}

	static class Pair {
		final double x;
		final double y;

		public Pair(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair other = (Pair) obj;
				if (this == other)
					return true;

				return Double.doubleToRawLongBits(x) == Double.doubleToRawLongBits(other.x) &&
						Double.doubleToRawLongBits(y) == Double.doubleToRawLongBits(other.y);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return (int) (Double.doubleToRawLongBits(x) * 17 + Double.doubleToRawLongBits(y));
		}

		@Override
		public String toString() {
			return String.format("(%f, %f)", x, y);
		}
	}

	/**
	 * Calculate d-spacings from given positions of Bragg diffraction spots
	 * @param detector
	 * @param diffExp
	 * @param pos
	 *            An array of x,y positions of spots on the detector in pixels. There must be an even number of values
	 * @return array of inter-spot distances (d spacing) in angstroms
	 */
	public static double[] dSpacingsFromPixelCoords(DetectorProperties detector, DiffractionCrystalEnvironment diffExp,
			double... pos) {
		if (pos.length % 2 != 0) {
			throw new IllegalArgumentException("The number of values must be even");
		}

		// unique-fy coords
		Set<Pair> coords = new LinkedHashSet<Pair>();
		for (int i = 0; i < pos.length; i += 2) {
			Pair p = new Pair(pos[i], pos[i+1]);
			coords.add(p);
		}

		Vector3d q = new Vector3d();
		QSpace qspace = new QSpace(detector, diffExp);
		double[] spacings = new double[coords.size()-1];
		Iterator<Pair> it = coords.iterator();
		Pair p2 = it.next();
		int i = 0;
		while (it.hasNext()) {
			Pair p1 = p2;
			p2 = it.next();
			q.sub(qspace.qFromPixelPosition(p1.x, p1.y), qspace.qFromPixelPosition(p2.x, p2.y));
			spacings[i++] = 2 * Math.PI / q.length();
		}
		return spacings;
	}

	/**
	 * Calculate radius of circle assuming the detector is normal to the beam vector
	 * 
	 * @param detector
	 * @param diffExp
	 * @param dSpacing
	 * @return radius of circle in PIXELS
	 */
	public static double radiusFromDSpacing(DetectorProperties detector, DiffractionCrystalEnvironment diffExp,
			double dSpacing) {
		double alpha = coneAngleFromDSpacing(diffExp, dSpacing);
		return (detector.getDetectorDistance() * Math.tan(alpha))/detector.getVPxSize();
	}

	/**
	 * Calculate a conic section
	 * @param detector
	 * @param diffExp
	 * @param dSpacing (in Angstroms)
	 * @return roi
	 */
	public static IROI conicFromDSpacing(DetectorProperties detector, DiffractionCrystalEnvironment diffExp,
			double dSpacing) {
		return conicFromDSpacing(detector, diffExp.getWavelength(), dSpacing);
	}

	/**
	 * Calculate cone semi-angle
	 * @param diffExp
	 * @param dSpacing (in Angstroms)
	 * @return semi-angle
	 */
	public static double coneAngleFromDSpacing(DiffractionCrystalEnvironment diffExp, double dSpacing) {
		return coneAngleFromDSpacing(diffExp.getWavelength(), dSpacing);
	}

	/**
	 * Calculate cone semi-angle
	 * @param wavelength (in same units as d-spacing)
	 * @param dSpacing (in same units as wavelength)
	 * @return semi-angle
	 */
	public static double coneAngleFromDSpacing(double wavelength, double dSpacing) {
		double s = 0.5 * wavelength / dSpacing;
		if (s > 1) {
			throw new IllegalArgumentException("Wavelength cannot be greater than 2 * dSpacing");
		}
		return 2 * Math.asin(s);
	}

	/**
	 * Calculate a conic section
	 * @param detector
	 * @param wavelength (in same units as d-spacing)
	 * @param dSpacing (in same units as wavelength)
	 * @return roi
	 */
	public static IROI conicFromDSpacing(DetectorProperties detector, double wavelength,
			double dSpacing) {
		double alpha = coneAngleFromDSpacing(wavelength, dSpacing);
		return conicFromAngle(detector, alpha);
	}

	/**
	 * Calculate a conic section
	 * @param detector
	 * @param alpha semi-angle (in radians)
	 * @return roi
	 */
	public static IROI conicFromAngle(DetectorProperties detector, double alpha) {
		IROI[] rois = conicsFromAngles(detector, alpha);
		return rois != null && rois.length > 0 ? rois[0] : null;
	}

	/**
	 * Calculate a conic section
	 * @param detector
	 * @param alpha semi-angle (in radians)
	 * @return roi
	 */
	@Deprecated
	static IROI oldConicFromAngle(DetectorProperties detector, double alpha) {
		final Vector3d normal = detector.getNormal();

		Vector3d major = new Vector3d();
		Vector3d minor = new Vector3d();
		minor.cross(normal, detector.getBeamVector());
		double se = minor.length();
		double ce = Math.sqrt(1. - se*se);
		if (se == 0) {
			major.set(-1, 0, 0);
		} else {
			minor.normalize();
			major.cross(minor, normal);
		}

		Vector3d intersect = null;
		double r = 0;
		try {
			intersect = detector.getBeamCentrePosition();
			r = intersect.length();
		} catch (IllegalStateException e) {
			throw new UnsupportedOperationException("Cannot handle parabolic case yet");
		}

		double sa = Math.sin(alpha);
		double ca = Math.cos(alpha);

		if (ca*ce - sa*se < 1e-15) {
			throw new UnsupportedOperationException("Part of cone does not intersect detector plane");
		}

		Vector3d row = detector.getPixelRow();
		Vector3d col = detector.getPixelColumn();
		double angle = Math.atan2(major.dot(col), major.dot(row));
		if (se != 0) {
			double x = r*se*sa*sa/(ca*ca - se*se);
			major.scale(x/major.length());
			intersect.add(major);
		}
		Vector3d centre = new Vector3d();
		detector.pixelCoords(intersect, centre);

		r /= detector.getVPxSize();
		EllipticalROI eroi;
		if (se != 0) {
			double denom = ca*ca - se*se;
			if (denom <= 0) { // if alpha >= 90 - eta
				return null; // then parabolic or hyperbolic cases
			}
			double a = r*ce*sa*ca/denom;
			double b = r*ce*sa/Math.sqrt(denom);
			eroi = new EllipticalROI(a, b, angle, centre.x, centre.y);
		} else {
			double a = r*sa/ca;
			eroi = new EllipticalROI(a, centre.x, centre.y);
			eroi.setAngle(angle);
		}

		return eroi;
	}

	/**
	 * Calculate conic sections
	 * @param detector
	 * @param alphas semi-angles (in radians)
	 * @return conic ROIs (can be null or contain nulls)
	 */
	public static IROI[] conicsFromAngles(DetectorProperties detector, double... alphas) {
		double distance = detector.getDetectorDistance();
		if (distance < 0) {
			logger.warn("Detector is behind origin!");
			return null;
		} else if (distance == 0) {
			// TODO three degenerate cases (point, line, line pair)
			logger.warn("Origin is on plane of detector!");
			return null;
		}

		final Vector3d normal = detector.getNormal();
		final Vector3d beam = detector.getBeamVector();

		Vector3d major = new Vector3d();
		Vector3d minor = new Vector3d();

		minor.cross(normal, beam);
		double ce = -normal.dot(beam);
		if (ce < 0)
			return null;
		double sse = 1 - ce*ce;
		double se = Math.sqrt(sse);
		if (sse == 0) {
			major.set(-1, 0, 0);
		} else {
			minor.normalize();
			major.cross(minor, normal);
			major.normalize();
		}
		Vector3d row = detector.getPixelRow();
		Vector3d col = detector.getPixelColumn();
		double angle = Math.atan2(major.dot(col), major.dot(row));

		IROI[] rois = new IROI[alphas.length];
		Vector3d centre = new Vector3d();
		double pixel = detector.getVPxSize();
		for (int i = 0; i < rois.length; i++) {
			double alpha = alphas[i];
			if (Double.isNaN(alpha) || alpha >= 0.5*Math.PI) {
				continue;
			}
			double sa = Math.sin(alpha);
			double ca = Math.cos(alpha);
			double denom = ca * ca - sse;

			IOrientableROI roi = null;
			double o;
			double f = Math.abs(denom);
			if (f < 10*Math.ulp(1d)) {
				// parabolic
				double p = distance * sa / (2 * ca);
				ParabolicROI proi = new ParabolicROI();
				roi = proi;
				proi.setFocalParameter(p / pixel);
				o = distance * ca / (2*sa);
			} else {
				f = 1. / f;
				if (denom > 0) {
					// circular/elliptical
					double a = distance * sa * ca * f / pixel;
					double b = distance * sa * Math.sqrt(f) / pixel;
					EllipticalROI eroi = new EllipticalROI();
					roi = eroi;
					if (se == 0) {
						b = a;
					}
					eroi.setSemiAxes(new double[] { a, b });
					o = distance * se * ce * f;
				} else {
					// hyperbolic
					HyperbolicROI hroi = new HyperbolicROI();
					roi = hroi;
					double l = distance * sa / (ca * pixel);
					double e = se / ca;
					hroi.setEccentricity(e);
					hroi.setSemilatusRectum(l);
					o = distance * (sa - ce) * se * f;
				}
			}
			roi.setAngle(angle);

			Vector3d point = detector.getClosestPoint();
			if (o != 0) {
				point.scaleAdd(o, major, point);
			}

			detector.pixelCoords(point, centre);
			roi.setPoint(centre.x, centre.y);
			rois[i] = roi;
//			logger.debug("Ang {}: {} [{}], d: {}, r: {}", i, Math.toDegrees(alpha), 90-Math.toDegrees(alpha), distance, roi);
//			double sample = ((roi instanceof EllipticalROI) && ((EllipticalROI) roi).isCircular() ) ? 0 : Math.PI;
//			logger.debug("Pt: {}", ((IParametricROI) roi).getPoint(sample));
		}

		return rois;
	}
	
	public static List<IROI> getResolutionRings(IDiffractionMetadata metadata, CalibrantSpacing cs) {
		return getResolutionRings(metadata, cs.getHKLs());
	}
	
	
	public static List<IROI> getResolutionRings(IDiffractionMetadata metadata, List<HKL> hkls) {
		
		List<IROI> rois = new ArrayList<IROI>(hkls.size());
		
		for (HKL hkl : hkls) {
			DetectorProperties detprop = metadata.getDetector2DProperties();
			DiffractionCrystalEnvironment diffenv = metadata.getDiffractionCrystalEnvironment();
			try {
				IROI roi = DSpacing.conicFromDSpacing(detprop, diffenv, hkl.getD().to(NonSI.ANGSTROM).getValue().doubleValue());
				rois.add(roi);
			} catch ( Exception e) {
				rois.add(null);
			}
		}
		
		return rois;
	}
	
	public static IROI runConicPeakFit(final IMonitor monitor, Dataset image, IParametricROI roi, IParametricROI[] innerOuter, int nPoints) {
		
		if (roi == null)
			return null;

		if (monitor != null) monitor.subTask("Find POIs near initial ellipse");
		PolylineROI points;
		if (monitor != null) monitor.subTask("Fit POIs");
		
		points = PeakFittingEllipseFinder.findPointsOnConic(image, null,roi, innerOuter,nPoints, monitor);
		
		if (monitor != null && monitor.isCancelled())
			return null;
		
		if (points == null) return null;
		
		if (roi instanceof EllipticalROI) {
			if (points.getNumberOfPoints() < 3) {
				throw new IllegalArgumentException("Could not find enough points to trim");
			}

			if (monitor != null) monitor.subTask("Trim POIs");
			EllipticalFitROI efroi = PowderRingsUtils.fitAndTrimOutliers(monitor, points, 5, false);
			logger.debug("Found {}...", efroi);
			if (monitor != null) monitor.subTask("");
			
			EllipticalFitROI cfroi = PowderRingsUtils.fitAndTrimOutliers(null, points, 2, true);
			
			
			double dma = efroi.getSemiAxis(0)-cfroi.getSemiAxis(0);
			double dmi = efroi.getSemiAxis(1)-cfroi.getSemiAxis(0);
			
			double crms = Math.sqrt((dma*dma + dmi*dmi)/2);
			double rms = efroi.getRMS();
			
			if (crms < rms) {
				efroi = cfroi;
				logger.warn("SWITCHING TO CIRCLE - RMS SEMIAX-RADIUS {} < FIT RMS {}",crms,rms);
			}
			
			return efroi;
		}
		
		return points;
	}
	
	public static IROI fitParametricROI(List<IROI> resROIs, IParametricROI r, IDataset image, int i, int minSpacing, int nPoints, int maxSize, IMonitor monitor) {
		
		IParametricROI[] inOut = getInnerAndOuterRangeROIs(resROIs, r,i,minSpacing, maxSize);
		
		if (inOut == null) return null;
		
		return DSpacing.runConicPeakFit(monitor,DatasetUtils.convertToDataset(image), r,inOut,nPoints);
	}
	
	private static IParametricROI[] getInnerAndOuterRangeROIs(List<IROI> resROIs, IParametricROI r, int i, int minSpacing, int maxSize) {
		IParametricROI[] inOut = new IParametricROI[2];
		//TODO min spacing for non-elliptical
		//TODO include parabolic case
		if (r instanceof HyperbolicROI) {
			HyperbolicROI h = (HyperbolicROI)r;
			double slr = h.getSemilatusRectum();
			
			if (i != 0) {
				
				if (resROIs.get(i-1) instanceof HyperbolicROI) {
					HyperbolicROI inner =  (HyperbolicROI)resROIs.get(i-1);
					double sd = (slr-inner.getSemilatusRectum())/4;
					sd = sd > maxSize ? maxSize : sd;
					double semi = slr - sd;
					double px = h.getPointX() - (h.getPointX() - inner.getPointX())/4;
					double py = h.getPointY() - (h.getPointY() - inner.getPointY())/4;
					inOut[0] = new HyperbolicROI(semi, h.getEccentricity(), h.getAngle(), px, py);
				}
			}
			
			if (i < resROIs.size()-1) {
				if (resROIs.get(i+1) instanceof HyperbolicROI) {
					HyperbolicROI outer =  (HyperbolicROI)resROIs.get(i+1);
					double sd = (outer.getSemilatusRectum()-slr)/4;
					sd = sd > maxSize ? maxSize : sd;
					double pxd = (outer.getPointX() - h.getPointX())/4;
					double pyd = (outer.getPointY() - h.getPointY())/4;
					inOut[1] = new HyperbolicROI(h.getSemilatusRectum()+sd,
							h.getEccentricity(), h.getAngle(), h.getPointX()+pxd, h.getPointY()+pyd);
					
					
					if (inOut[0] == null) {
						inOut[0] = new HyperbolicROI(h.getSemilatusRectum()-sd,
								outer.getEccentricity(), outer.getAngle(), h.getPointX()-pxd, h.getPointY()-pyd);
					}
				}
			}
			
			
		} else if (r instanceof EllipticalROI) {
			EllipticalROI e = (EllipticalROI) r;
			double major = e.getSemiAxis(0);
			
			double deltalow = major > maxSize ? maxSize : major;
			double deltahigh = maxSize;
			
			if (i != 0) {
				
				if (resROIs.get(i-1) instanceof EllipticalROI) {
					deltalow = 0.5*(major - ((EllipticalROI)resROIs.get(i-1)).getSemiAxis(0));
					deltalow = deltalow > maxSize ? maxSize : deltalow;
				}
			}
			
			if (i < resROIs.size()-1) {
				if (resROIs.get(i+1) instanceof EllipticalROI) {
				deltahigh = 0.5*(((EllipticalROI)resROIs.get(i+1)).getSemiAxis(0) - major);
				deltahigh = deltahigh > maxSize ? maxSize : deltahigh;
				}
			}
			
			if (deltalow < minSpacing || deltahigh < minSpacing) return null;
			
			EllipticalROI in = e.copy();
			in.setSemiAxis(0, e.getSemiAxis(0)-deltalow);
			in.setSemiAxis(1, e.getSemiAxis(1)-deltalow);
			inOut[0] = in;
			
			EllipticalROI out = e.copy();
			out.setSemiAxis(0, e.getSemiAxis(0)+deltahigh);
			out.setSemiAxis(1, e.getSemiAxis(1)+deltahigh);
			inOut[1] = out;
		}
		
		if (inOut[0] == null || inOut[1] == null) return null;
		
		return inOut;
	}
}
