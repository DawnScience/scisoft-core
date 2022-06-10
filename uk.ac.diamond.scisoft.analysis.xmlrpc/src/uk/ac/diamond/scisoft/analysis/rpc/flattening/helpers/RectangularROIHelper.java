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

import org.eclipse.dawnsci.analysis.dataset.roi.RectangularROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class RectangularROIHelper extends ROIHelper<RectangularROI> {
	private static final String LEN = "len";
	private static final String ANG = "ang";
	private static final String CLIPPING_COMPENSATION = "clippingCompensation";

	public RectangularROIHelper() {
		super(RectangularROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		RectangularROI roi = (RectangularROI) obj;
		Map<String, Object> outMap = super.flatten(roi, RectangularROI.class.getCanonicalName(), rootFlattener);
		outMap.put(LEN, rootFlattener.flatten(roi.getLengths()));
		outMap.put(ANG, rootFlattener.flatten(roi.getAngle()));
		outMap.put(CLIPPING_COMPENSATION, rootFlattener.flatten(roi.isClippingCompensation()));
		return outMap;
	}

	@Override
	public RectangularROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		RectangularROI roiOut = super.unflatten(inMap, rootFlattener);
		roiOut.setLengths((double[]) rootFlattener.unflatten(inMap.get(LEN)));
		roiOut.setAngle((Double) rootFlattener.unflatten(inMap.get(ANG)));
		roiOut.setClippingCompensation((Boolean) rootFlattener.unflatten(inMap.get(CLIPPING_COMPENSATION)));

		return roiOut;
	}
}
