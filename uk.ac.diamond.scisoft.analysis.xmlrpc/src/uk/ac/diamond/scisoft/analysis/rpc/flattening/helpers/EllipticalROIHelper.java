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

import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class EllipticalROIHelper extends ROIHelper<EllipticalROI> {
	private static final String SAXIS = "saxis";
	private static final String ANG = "ang";

	public EllipticalROIHelper() {
		super(EllipticalROI.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		EllipticalROI roi = (EllipticalROI) obj;
		Map<String, Object> outMap = super.flatten(roi, EllipticalROI.class.getCanonicalName(), rootFlattener);
		outMap.put(SAXIS, rootFlattener.flatten(roi.getSemiAxes()));
		outMap.put(ANG, rootFlattener.flatten(roi.getAngle()));
		return outMap;
	}

	@Override
	public EllipticalROI unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		EllipticalROI roiOut = super.unflatten(inMap, rootFlattener);
		roiOut.setSemiAxes((double[]) rootFlattener.unflatten(inMap.get(SAXIS)));
		roiOut.setAngle((Double) rootFlattener.unflatten(inMap.get(ANG)));

		return roiOut;
	}
}
