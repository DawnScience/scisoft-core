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

import org.eclipse.dawnsci.analysis.dataset.roi.LinearROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class LinearROIHelper extends ROIHelper<LinearROI> {
	private static final String LEN = "len";
	private static final String ANG = "ang";
	private static final String CROSS_HAIR = "crossHair";

	public LinearROIHelper() {
		super(LinearROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		LinearROI roi = (LinearROI) obj;
		Map<String, Object> outMap = super.flatten(roi, LinearROI.class.getCanonicalName(), rootFlattener);
		outMap.put(LEN, rootFlattener.flatten(roi.getLength()));
		outMap.put(ANG, rootFlattener.flatten(roi.getAngle()));
		outMap.put(CROSS_HAIR, rootFlattener.flatten(roi.isCrossHair()));

		return outMap;
	}

	@Override
	public LinearROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		LinearROI roiOut = super.unflatten(inMap, rootFlattener);
		roiOut.setLength((Double) rootFlattener.unflatten(inMap.get(LEN)));
		roiOut.setAngle((Double) rootFlattener.unflatten(inMap.get(ANG)));
		roiOut.setCrossHair((Boolean) rootFlattener.unflatten(inMap.get(CROSS_HAIR)));
		return roiOut;
	}
}
