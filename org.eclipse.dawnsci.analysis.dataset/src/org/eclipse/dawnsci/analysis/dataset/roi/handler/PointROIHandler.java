/*-
 * Copyright 2022 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.dataset.roi.handler;

import java.util.Arrays;
import org.eclipse.dawnsci.analysis.dataset.roi.PointROI;

public class PointROIHandler extends ROIHandler<PointROI> {
	
	public PointROIHandler(PointROI roi) {
		this.roi = roi;
		list = Arrays.asList(-1);
	}

	@Override
	public double[] getHandlePoint(int handle, int size) {
		return roi.getPoint();
	}

	@Override
	public double[] getAnchorPoint(int handle, int size) {
		return roi.getPoint();
	}

	@Override
	public int getCentreHandle() {
		return 0;
	}

	@Override
	public PointROI interpretMouseDragging(double[] spt, double[] ept) {
		PointROI croi = null; // return null if not a valid event

		switch (status) {
		case RMOVE:
			croi = roi.copy();
			ept[0] -= spt[0];
			ept[1] -= spt[1];
			croi.addPoint(ept);
			break;
		case NONE:
			croi = roi.copy();
			croi.setPoint(ept);
			break;
		case REORIENT:
		case RESIZE:
		case ROTATE:
		case CMOVE:
		case CRMOVE:
			break;
		}

		return croi;
	}

}
