/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi.handler;

import uk.ac.diamond.scisoft.analysis.roi.CircularROI;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;
import uk.ac.diamond.scisoft.analysis.roi.HyperbolicROI;
import uk.ac.diamond.scisoft.analysis.roi.IParametricROI;
import uk.ac.diamond.scisoft.analysis.roi.OrientableROIBase;
import uk.ac.diamond.scisoft.analysis.roi.ParabolicROI;

/**
 * Wrapper class for a IParametricROI that adds handles. If the ROI is closed then it has five handles
 * otherwise it is open (i.e. unbounded) and has four handles (as one is considered to be at infinity).
 * An open parametric ROI usually has its infinity at the parameter of its orientation angle.
 * 
 * Handle 0 is focus (or centre), 1 is periapsis, 2 is c/w of 1 at the latus rectum or minor axis
 * intersection points, 3 is a/c of 1 at the other intersection point, and 4 is apoapsis if closed.
 */
public class ParametricROIHandler<T extends IParametricROI> extends ROIHandler<T> {
	/**
	 * Number of handle areas
	 */
	private final static int NHANDLE = 5;
	private boolean closed;

	/**
	 * Handler for RectangularROI
	 * @param roi
	 */
	public ParametricROIHandler(T roi, boolean isClosed) {
		super();
		this.roi = roi;
		closed = isClosed;
		for (int h = 0; h < NHANDLE; h++) {
			add(-1);
		}
		if (!closed) {
			remove(NHANDLE - 1);
		}
	}

	@Override
	public int getCentreHandle() {
		return 0;
	}

	private static final double FAR = 1e3;

	@Override
	public double[] getHandlePoint(int handle, int size) {
		double m = (roi.getStartParameter(FAR) + roi.getEndParameter(FAR)) * 0.5;
		double[] pt = null;

		switch (handle) {
		case 0:
			pt = roi.getPoint();
			break;
		case 1:
			pt = roi.getPoint(m);
			break;
		case 2:
			pt = roi.getPoint(m*0.5);
			break;
		case 3:
			pt = roi.getPoint(m*1.5);
			break;
		case 4:
			if (closed)
				pt = roi.getPoint(0);
			break;
		}
		return pt;
	}

	@Override
	public double[] getAnchorPoint(int handle, int size) {
		double m = (roi.getStartParameter(FAR) + roi.getEndParameter(FAR)) * 0.5;
		double[] pt = null;

		switch (handle) {
		case 0:
			pt = roi.getPoint();
			break;
		case 1:
			pt = roi.getPoint(m);
			break;
		case 2:
			pt = roi.getPoint(m*0.5);
			break;
		case 3:
			pt = roi.getPoint(m*1.5);
			break;
		case 4:
			if (closed)
				pt = roi.getPoint(0);
			break;
		}
		return pt;
	}

	/**
	 * @param spt starting point 
	 * @param pt 
	 * @return resized ROI
	 */
	@SuppressWarnings("unchecked")
	public T resize(double[] spt, double[] pt) {
		T rroi = null;

		if (!closed && handle == 4)
			return rroi;

		rroi = (T) roi.copy();
		pt[0] -= spt[0];
		pt[1] -= spt[1];

		switch (handle) {
		case -1: // new definition
			rroi.setPoint(spt);
			break;
		case 0: // focus/centre
			rroi.addPoint(pt);
			break;
		case 1: // periapsis
			if (rroi instanceof CircularROI) {
				if (pt[0] == 0)
					break;
				CircularROI lroi = (CircularROI) rroi;
				// work out perpendicular displacement
				double r = lroi.getRadius();
				r -= pt[0];
				if (r < 0)
					r = 0;
				lroi.setRadius(r);
			} else if (rroi instanceof OrientableROIBase) {
				// work out perpendicular displacement
				double a = rroi.getAngle();
				double d = pt[0] * Math.cos(a) + pt[1] * Math.sin(a);
				if (d == 0)
					break;

				if (rroi instanceof EllipticalROI) {
					EllipticalROI lroi = (EllipticalROI) rroi;
					double r = lroi.getSemiAxis(0) - d;
					if (r < 0)
						r = 0;
					lroi.setSemiAxis(0, r);
				} else if (rroi instanceof ParabolicROI) {
					ParabolicROI lroi = (ParabolicROI) rroi;
					double p = lroi.getFocalParameter() - d;
					if (p < 0)
						p = 0;
					lroi.setFocalParameter(p);
				} else if (rroi instanceof HyperbolicROI) {
					HyperbolicROI lroi = (HyperbolicROI) rroi;
					double l = lroi.getSemilatusRectum();
					double e = lroi.getEccentricity();
					double x = l / (1 + e) - d;
					e = l / x - 1;
					if (e < 1)
						e = 1;
					lroi.setEccentricity(e);
				}
			}
			break;
		case 2:
			if (rroi instanceof CircularROI) {
				if (pt[1] == 0)
					break;
				CircularROI lroi = (CircularROI) rroi;
				double r = lroi.getRadius();
				r += pt[1];
				if (r < 0)
					r = 0;
				lroi.setRadius(r);
			} else if (rroi instanceof OrientableROIBase) {
				// work out perpendicular displacement
				double a = rroi.getAngle();
				double d = -pt[0] * Math.sin(a) + pt[1] * Math.cos(a);
				if (d == 0)
					break;

				if (rroi instanceof EllipticalROI) {
					EllipticalROI lroi = (EllipticalROI) rroi;
					// work out perpendicular displacement
					double r = lroi.getSemiAxis(1) + d;
					if (r < 0)
						r = 0;
					lroi.setSemiAxis(1, r);
				} else if (rroi instanceof ParabolicROI) {
					ParabolicROI lroi = (ParabolicROI) rroi;
					double p = lroi.getFocalParameter() + 0.5 * d;
					if (p < 0)
						p = 0;
					lroi.setFocalParameter(p);
				} else if (rroi instanceof HyperbolicROI) {
					HyperbolicROI lroi = (HyperbolicROI) rroi;
					double l = lroi.getSemilatusRectum() + d;
					if (l < 0)
						l = 0;
					lroi.setSemilatusRectum(l);
				}
			}
			break;
		case 3:
			if (rroi instanceof CircularROI) {
				if (pt[1] == 0)
					break;
				CircularROI lroi = (CircularROI) rroi;
				double r = lroi.getRadius();
				r -= pt[1];
				if (r < 0)
					r = 0;
				lroi.setRadius(r);
			} else if (rroi instanceof OrientableROIBase) {
				// work out perpendicular displacement
				double a = rroi.getAngle();
				double d = -pt[0] * Math.sin(a) + pt[1] * Math.cos(a);
				if (d == 0)
					break;

				if (rroi instanceof EllipticalROI) {
					EllipticalROI lroi = (EllipticalROI) rroi;
					// work out perpendicular displacement
					double r = lroi.getSemiAxis(1) - d;
					if (r < 0)
						r = 0;
					lroi.setSemiAxis(1, r);
				} else if (rroi instanceof ParabolicROI) {
					ParabolicROI lroi = (ParabolicROI) rroi;
					double p = lroi.getFocalParameter() - 0.5 * d;
					if (p < 0)
						p = 0;
					lroi.setFocalParameter(p);
				} else if (rroi instanceof HyperbolicROI) {
					HyperbolicROI lroi = (HyperbolicROI) rroi;
					double l = lroi.getSemilatusRectum() - d;
					if (l < 0)
						l = 0;
					lroi.setSemilatusRectum(l);
				}
			}
			break;
		case 4:
			if (closed) {
				if (rroi instanceof CircularROI) {
					if (pt[0] == 0)
						break;
					CircularROI lroi = (CircularROI) rroi;
					double r = lroi.getRadius();
					r += pt[0];
					if (r < 0)
						r = 0;
					lroi.setRadius(r);
				} else if (rroi instanceof EllipticalROI) {
					EllipticalROI lroi = (EllipticalROI) rroi;
					// work out perpendicular displacement
					double a = lroi.getAngle();
					double d = pt[0] * Math.cos(a) + pt[1] * Math.sin(a);
					if (d == 0)
						break;
					double r = lroi.getSemiAxis(0) + d;
					if (r < 0)
						r = 0;
					lroi.setSemiAxis(0, r);
				}
			}
			break;
		default:
			break;
		}

		return rroi;
	}

	/**
	 * @param pt
	 * @return reoriented ROI
	 */
	public T reorient(@SuppressWarnings("unused") double[] pt) {
		return null; // TODO
	}

	@SuppressWarnings("unchecked")
	@Override
	public T interpretMouseDragging(double[] spt, double[] ept) {
		T croi = null; // return null if not a valid event

		switch (status) {
		case RMOVE:
			croi = (T) roi.copy();
			ept[0] -= spt[0];
			ept[1] -= spt[1];
			croi.addPoint(ept);
			break;
		case NONE:
			croi = (T) roi.copy();
//			croi.setEndPoint(ept);
			break;
		case REORIENT:
			croi = reorient(ept);
			break;
		case RESIZE:
			croi = resize(spt, ept);
			break;
		case ROTATE:
//			croi = (T) roi.copy();
//			double ang = croi.getAngleRelativeToMidPoint(ept);
//			double[] mpt = croi.getMidPoint();
//			croi.setAngle(ang);
//			croi.setMidPoint(mpt);
			break;
		case CMOVE:
			break;
		case CRMOVE:
			break;
		}

		if (croi == null) {
			throw new UnsupportedOperationException("Not implemented yet");
		}

		return croi;
	}
}
