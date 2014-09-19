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

import org.eclipse.dawnsci.analysis.dataset.roi.CircularROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class CircularROIHelper extends ROIHelper<CircularROI> {
	public static final String RAD = "rad";

	public CircularROIHelper() {
		super(CircularROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		CircularROI roi = (CircularROI) obj;
		Map<String, Object> outMap = super.flatten(roi, CircularROI.class.getCanonicalName(), rootFlattener);
		outMap.put(RAD, rootFlattener.flatten(roi.getRadius()));
		return outMap;
	}

	@Override
	public CircularROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		CircularROI roiOut = new CircularROI();
		roiOut.setName((String) rootFlattener.unflatten(inMap.get(ROIHelper.NAME)));
		roiOut.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiOut.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		roiOut.setRadius((Double) rootFlattener.unflatten(inMap.get(RAD)));

		return roiOut;
	}
}
