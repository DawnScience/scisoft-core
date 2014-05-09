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

import uk.ac.diamond.scisoft.analysis.coords.SectorCoords;
import uk.ac.diamond.scisoft.analysis.roi.RingROI;

/**
 * Wrapper class for a RingROI that adds handles
 */
public class RingROIHandler extends ROIHandler<RingROI> {
	/**
	 * Number of handle areas
	 */
	private final static int NHANDLE = 3;

	/**
	 * Handler for SectorROI
	 * 
	 * @param roi
	 */
	public RingROIHandler(RingROI roi) {
		super();
		for (int h = 0; h < NHANDLE; h++) {
			add(-1);
		}
		this.roi = roi;
	}

	@Override
	public int getCentreHandle() {
		return NHANDLE - 1;
	}

	@Override
	public void configureDragging(int handle, HandleStatus dragStatus) {
		// re-interpret wrong statuses
		if (handle == NHANDLE-1 && dragStatus == HandleStatus.RMOVE) {
			// not co-centre of arcs
			dragStatus = HandleStatus.CMOVE;
		}
		super.configureDragging(handle, dragStatus);
	}

	@Override
	public double[] getHandlePoint(int handle, int size) {
		return null;
	}

	private static final double RIGHT_ANGLE = Math.PI / 2;

	@Override
	public double[] getAnchorPoint(int handle, int size) {
		double[] pt = new double[2];
		double[] cpt = roi.getPointRef();
		double[] rpt = null;

		switch (handle) {
		case 0:
			rpt = SectorCoords.convertFromPolarRadians(roi.getRadius(0), RIGHT_ANGLE);
			break;
		case 1:
			rpt = SectorCoords.convertFromPolarRadians(roi.getRadius(1), RIGHT_ANGLE);
			break;
		case 2:
			return cpt;
		default:
			return pt;
		}
		pt[0] = cpt[0] + rpt[0];
		pt[1] = cpt[1] + rpt[1];
		return pt;
	}

	/**
	 * @param spt
	 *            start point
	 * @param ept
	 *            end point
	 * @return resized ROI
	 */
	public RingROI resize(double[] spt, double[] ept) {
		RingROI sroi = null;

		sroi = roi.copy();

		switch (handle) {
		case -1: // new definition
			double t;
			if (spt[0] > ept[0]) {
				t = spt[0];
				spt[0] = ept[0];
				ept[0] = t;
			}
			if (spt[1] > ept[1]) {
				t = spt[1];
				spt[1] = ept[1];
				ept[1] = t;
			}
			sroi.setRadii(spt[0], ept[0]);
			break;
		case 0:
			sroi.addRadius(0, ept[0] - spt[0]);
			break;
		case 1:
			sroi.addRadius(1, ept[0] - spt[0]);
			break;
		case 2:
			break;
		}
		return sroi;
	}

	/**
	 * Constrained ROI move
	 * @param spt
	 * @param ept
	 * @return moved ROI
	 */
	public RingROI crmove(double[] spt, double[] ept) {
		RingROI sroi = null;

		if (handle == 4)
			return sroi;
		sroi = roi.copy();

		switch (handle) {
		case 0:
			sroi.addRadii(ept[0] - spt[0]);
			break;
		case 1:
			sroi.addRadii(ept[0] - spt[0]);
			break;
		}
		return sroi;
	}

	@Override
	public RingROI interpretMouseDragging(double[] cpt, double[] pt) {
		RingROI croi = null; // return null if not a valid event

		final double[] spt = roi.getPointRef();
		SectorCoords ssc = null;
		SectorCoords esc = null;
		double[] sp = null;
		double[] ep = null;

		switch (status) {
		case CMOVE:
			croi = roi.copy();
			pt[0] -= cpt[0];
			pt[1] -= cpt[1];
			croi.addPoint(pt);
			break;
		case RMOVE:
			croi = roi.copy();
			ssc = new SectorCoords(spt, cpt);
			esc = new SectorCoords(spt, pt);
			sp = ssc.getPolarRadians();
			ep = esc.getPolarRadians();
			croi.addRadii(ep[0] - sp[0]);
			break;
		case NONE:
			croi = roi.copy();
			break;
		case RESIZE:
			ssc = new SectorCoords(spt, cpt);
			esc = new SectorCoords(spt, pt);
			sp = ssc.getPolarRadians();
			ep = esc.getPolarRadians();
			croi = resize(sp, ep);
			break;
		case ROTATE:
			croi = roi.copy();
			ssc = new SectorCoords(spt, cpt);
			esc = new SectorCoords(spt, pt);
			sp = ssc.getPolarRadians();
			ep = esc.getPolarRadians();
			break;
		case CRMOVE:
			ssc = new SectorCoords(spt, cpt);
			esc = new SectorCoords(spt, pt);
			sp = ssc.getPolarRadians();
			ep = esc.getPolarRadians();
			croi = crmove(sp, ep);
			break;
		case REORIENT:
			break;
		}
		return croi;
	}
}
