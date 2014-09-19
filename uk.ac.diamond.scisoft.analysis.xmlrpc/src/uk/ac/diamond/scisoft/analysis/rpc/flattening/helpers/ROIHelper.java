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

abstract public class ROIHelper<T> extends MapFlatteningHelper<T> {

	public ROIHelper(Class<T> type) {
		super(type);
	}

	public static final String NAME = "name";
	public static final String SPT = "spt";
	public static final String PLOT = "plot";

	public Map<String, Object> flatten(IROI roi, String typeName, IRootFlattener rootFlattener) {
		Map<String, Object> outMap = createMap(typeName);
		outMap.put(NAME, rootFlattener.flatten(roi.getName()));
		outMap.put(SPT, rootFlattener.flatten(roi.getPointRef()));
		outMap.put(PLOT, rootFlattener.flatten(roi.isPlot()));
		return outMap;
	}
}
