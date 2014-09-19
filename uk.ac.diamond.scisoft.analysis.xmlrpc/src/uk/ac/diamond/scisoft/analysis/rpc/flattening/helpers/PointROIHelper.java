/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.roi.PointROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class PointROIHelper extends ROIHelper<PointROI> {
	public PointROIHelper() {
		super(PointROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		return super.flatten((PointROI) obj, PointROI.class.getCanonicalName(), rootFlattener);
	}

	@Override
	public PointROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		PointROI roi = new PointROI();
		roi.setName((String) rootFlattener.unflatten(inMap.get(ROIHelper.NAME)));
		roi.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roi.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		return roi;
	}
}
