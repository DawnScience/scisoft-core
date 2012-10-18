/*
 * Copyright 2012 Diamond Light Source Ltd.
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
import uk.ac.diamond.scisoft.analysis.roi.SectorROI;

/**
 * Wrapper class for a SectorROI that adds handles
 */
public class SectorROIHandler extends ROIHandler {
	/**
	 * Number of handle areas
	 */
	private final static int NHANDLE = 10;

	/**
	 * Handler for SectorROI
	 * 
	 * @param roi
	 */
	public SectorROIHandler(SectorROI roi) {
		super();
		for (int h = 0; h < NHANDLE; h++) {
			add(-1);
		}
		this.roi = roi;
	}

	/**
	 * @return Returns the roi.
	 */
	@Override
	public SectorROI getROI() {
		return (SectorROI) roi;
	}

	@Override
	public double[] getHandlePoint(int handle, int size) {
		return null;
	}

	/**
	 * @param handle
	 * @param size
	 * @param dphi 
	 * @return handle point in polar coords
	 */
	public double[] getSectorPoint(int handle, int size, double dphi) {
		SectorROI oroi = (SectorROI) roi;
		double[] pt = new double[2];

		switch (handle) {
		case 0:
			pt[0] = oroi.getRadius(0);
			pt[1] = oroi.getAngle(0);
			break;
		case 1:
			pt[0] = oroi.getRadius(0);
			pt[1] = 0.5 * (oroi.getAngle(0) + oroi.getAngle(1) - dphi);
			break;
		case 2:
			pt[0] = oroi.getRadius(0);
			pt[1] = oroi.getAngle(1) - dphi;
			break;
		case 3:
			pt[0] = 0.5 * (oroi.getRadius(0) + oroi.getRadius(1) - size);
			pt[1] = oroi.getAngle(0);
			break;
		case 4:
			pt[0] = 0.5 * (oroi.getRadius(0) + oroi.getRadius(1) - size);
			pt[1] = 0.5 * (oroi.getAngle(0) + oroi.getAngle(1) - dphi);
			break;
		case 5:
			pt[0] = 0.5 * (oroi.getRadius(0) + oroi.getRadius(1) - size);
			pt[1] = oroi.getAngle(1) - dphi;
			break;
		case 6:
			pt[0] = oroi.getRadius(1) - size;
			pt[1] = oroi.getAngle(0);
			break;
		case 7:
			pt[0] = oroi.getRadius(1) - size;
			pt[1] = 0.5 * (oroi.getAngle(0) + oroi.getAngle(1) - dphi);
			break;
		case 8:
			pt[0] = oroi.getRadius(1) - size;
			pt[1] = oroi.getAngle(1) - dphi;
			break;
		case 9:
			break;
		}
		return pt;
	}

	@SuppressWarnings("null")
	@Override
	public double[] getAnchorPoint(int handle, int size) {
		SectorROI oroi = (SectorROI) roi;
		double[] pt = new double[2];
		double[] cpt = roi.getPointRef();
		SectorCoords sc = null;

		switch (handle) {
		case 0:
			sc = new SectorCoords(oroi.getRadius(0), oroi.getAngle(0), false, false);
			break;
		case 1:
			sc = new SectorCoords(oroi.getRadius(0), 0.5 * (oroi.getAngle(0) + oroi.getAngle(1)), false, false);
			break;
		case 2:
			sc = new SectorCoords(oroi.getRadius(0), oroi.getAngle(1), false, false);
			break;
		case 3:
			sc = new SectorCoords(0.5 * (oroi.getRadius(0) + oroi.getRadius(1)), oroi.getAngle(0), false, false);
			break;
		case 4:
			sc = new SectorCoords(0.5 * (oroi.getRadius(0) + oroi.getRadius(1)),
					0.5 * (oroi.getAngle(0) + oroi.getAngle(1)), false, false);
			break;
		case 5:
			sc = new SectorCoords(0.5 * (oroi.getRadius(0) + oroi.getRadius(1)), oroi.getAngle(1), false, false);
			break;
		case 6:
			sc = new SectorCoords(oroi.getRadius(1), oroi.getAngle(0), false, false);
			break;
		case 7:
			sc = new SectorCoords(oroi.getRadius(1), 0.5 * (oroi.getAngle(0) + oroi.getAngle(1)), false, false);
			break;
		case 8:
			sc = new SectorCoords(oroi.getRadius(1), oroi.getAngle(1), false, false);
			break;
		case 9:
			return cpt;
		}
		pt[0] = cpt[0] + sc.getCartesian()[0];
		pt[1] = cpt[1] + sc.getCartesian()[1];
		return pt;
	}

	/**
	 * @param spt
	 *            start point
	 * @param ept
	 *            end point
	 * @return resized ROI
	 */
	public SectorROI resize(double[] spt, double[] ept) {
		SectorROI sroi = null;

		if (handle == 4)
			return sroi;
		sroi = (SectorROI) roi.copy();

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
			sroi.setAngles(spt[1], ept[1]);
			break;
		case 0:
			sroi.addRadius(0, ept[0] - spt[0]);
			sroi.addAngle(0, ept[1] - spt[1]);
			break;
		case 1:
			sroi.addRadius(0, ept[0] - spt[0]);
			break;
		case 2:
			sroi.addRadius(0, ept[0] - spt[0]);
			sroi.addAngle(1, ept[1] - spt[1]);
			break;
		case 3:
			sroi.addAngle(0, ept[1] - spt[1]);
			break;
		case 5:
			sroi.addAngle(1, ept[1] - spt[1]);
			break;
		case 6:
			sroi.addRadius(1, ept[0] - spt[0]);
			sroi.addAngle(0, ept[1] - spt[1]);
			break;
		case 7:
			sroi.addRadius(1, ept[0] - spt[0]);
			break;
		case 8:
			sroi.addRadius(1, ept[0] - spt[0]);
			sroi.addAngle(1, ept[1] - spt[1]);
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
	public SectorROI crmove(double[] spt, double[] ept) {
		SectorROI sroi = null;

		if (handle == 4)
			return sroi;
		sroi = (SectorROI) roi.copy();

		switch (handle) {
		case 0: case 2: case 6: case 8:
			sroi.addRadii(ept[0] - spt[0]);
			sroi.addAngles(ept[1] - spt[1]);
			break;
		case 1: case 7:
			sroi.addRadii(ept[0] - spt[0]);
			break;
		case 3: case 5:
			sroi.addAngles(ept[1] - spt[1]);
			break;
		}
		return sroi;
	}

	@Override
	public SectorROI interpretMouseDragging(int[] cpt, int[] pt) {
		SectorROI croi = null; // return null if not a valid event
		SectorROI sroi = (SectorROI) roi;

		final double[] spt = sroi.getPointRef();
		SectorCoords ssc = null;
		SectorCoords esc = null;
		double[] sp = null;
		double[] ep = null;

		switch (status) {
		case CMOVE:
			croi = sroi.copy();
			pt[0] -= cpt[0];
			pt[1] -= cpt[1];
			croi.addPoint(pt);
			break;
		case RMOVE:
			croi = sroi.copy();
			ssc = new SectorCoords(spt, cpt);
			esc = new SectorCoords(spt, pt);
			sp = ssc.getPolarRadians();
			ep = esc.getPolarRadians();
			croi.addRadii(ep[0] - sp[0]);
			croi.addAngles(ep[1] - sp[1]);
			break;
		case NONE:
			croi = sroi.copy();
			break;
		case RESIZE:
			ssc = new SectorCoords(spt, cpt);
			esc = new SectorCoords(spt, pt);
			sp = ssc.getPolarRadians();
			ep = esc.getPolarRadians();
			croi = resize(sp, ep);
			break;
		case ROTATE:
			croi = sroi.copy();
			ssc = new SectorCoords(spt, cpt);
			esc = new SectorCoords(spt, pt);
			sp = ssc.getPolarRadians();
			ep = esc.getPolarRadians();
			croi.addAngles(ep[1] - sp[1]);
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
