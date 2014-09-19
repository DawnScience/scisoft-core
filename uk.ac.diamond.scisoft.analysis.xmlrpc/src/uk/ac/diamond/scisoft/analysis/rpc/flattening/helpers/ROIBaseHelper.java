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

import org.eclipse.dawnsci.analysis.dataset.roi.ROIBase;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class ROIBaseHelper extends ROIHelper<ROIBase> {
	public ROIBaseHelper() {
		super(ROIBase.class);
	}

	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattener) {
		return super.flatten((ROIBase) obj, ROIBase.class.getCanonicalName(), rootFlattener);
	}

	@Override
	public ROIBase unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		ROIBase roiBase = new ROIBase();
		roiBase.setName((String) rootFlattener.unflatten(inMap.get(ROIHelper.NAME)));
		roiBase.setPoint((double[]) rootFlattener.unflatten(inMap.get(ROIHelper.SPT)));
		roiBase.setPlot((Boolean) rootFlattener.unflatten(inMap.get(ROIHelper.PLOT)));
		return roiBase;
	}
}
