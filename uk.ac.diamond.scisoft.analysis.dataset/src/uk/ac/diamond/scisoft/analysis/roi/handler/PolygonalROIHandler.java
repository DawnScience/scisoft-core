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

import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.roi.PolygonalROI;

/**
 * Wrapper class for polygon that adds handles for each point
 */
public class PolygonalROIHandler extends ROIHandler {

	/**
	 * Handler for ROIBase
	 * @param roi
	 */
	public PolygonalROIHandler(PolygonalROI roi) {
		super();
		if (roi != null) {
			for (int h = 0, hmax = roi.getSides(); h < hmax; h++) {
				add(-1);
			}
			this.roi = roi;
		}
	}

	/**
	 * @return Returns the roi.
	 */
	@Override
	public PolygonalROI getROI() {
		return (PolygonalROI) roi;
	}

	@Override
	public int getCentreHandle() {
		return -1;
	}

	@Override
	public void setROI(IROI roi) {
		PolygonalROI proi = (PolygonalROI) roi;

		int n = proi.getSides();
		if (n > size()) {
			for (int h = size(); h < n; h++) {
				add(-1);
			}
		}
		this.roi = proi;
	}

	@Override
	public double[] getHandlePoint(int handle, int size) {
		double[] pt = getAnchorPoint(handle, size);
		
		if (pt != null) {
			pt[0] -= size/2;
			pt[1] -= size/2;
		}
		return pt;
	}

	@Override
	public double[] getAnchorPoint(int handle, int size) {
		final PolygonalROI proi = (PolygonalROI) roi;
		double[] pt = null;

		int sides = proi.getSides();
		if (handle < sides) {
			pt = proi.getPoint(handle).getPoint();
		}

		return pt;
	}

	@Override
	public IROI interpretMouseDragging(double[] spt, double[] ept) {
		// TODO Auto-generated method stub
		return null;
	}

}
