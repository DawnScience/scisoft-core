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

import org.eclipse.dawnsci.analysis.api.roi.IROI;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

abstract public class ROIHelper<T extends IROI> extends MapFlatteningHelper<T> {

	public ROIHelper(Class<T> type) {
		super(type);
	}

	private static final String NAME = "name";
	private static final String SPT = "spt";
	private static final String PLOT = "plot";
	private static final String FIXED = "fixed";

	public Map<String, Object> flatten(IROI roi, String typeName, IRootFlattener rootFlattener) {
		Map<String, Object> outMap = createMap(typeName);
		outMap.put(NAME, rootFlattener.flatten(roi.getName()));
		outMap.put(SPT, rootFlattener.flatten(roi.getPointRef()));
		outMap.put(PLOT, rootFlattener.flatten(roi.isPlot()));
		outMap.put(FIXED, rootFlattener.flatten(roi.isFixed()));
		return outMap;
	}

	@Override
	public T unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		T roiOut = createInstance();
		roiOut.setName((String) rootFlattener.unflatten(inMap.get(ROIHelper.NAME)));
		roiOut.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiOut.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		roiOut.setFixed((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.FIXED)));

		return roiOut;
	}
}
