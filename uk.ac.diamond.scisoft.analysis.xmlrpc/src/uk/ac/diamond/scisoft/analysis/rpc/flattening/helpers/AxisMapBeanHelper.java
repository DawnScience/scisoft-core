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

import uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class AxisMapBeanHelper extends MapFlatteningHelper<AxisMapBean> {

	public static final String AXIS_ID = "axisID";
	public static final String AXIS_NAMES = "axisNames";

	public AxisMapBeanHelper() {
		super(AxisMapBean.class);
	}

	@Override
	public AxisMapBean unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		AxisMapBean outBean = new AxisMapBean();
		Object[] objIds = (Object[]) thisMap.get(AXIS_ID);
		String[] ids = new String[objIds.length];
		System.arraycopy(objIds, 0, ids, 0, objIds.length);
		outBean.setAxisID(ids);
		Object[] objNames = (Object[]) rootFlattener.unflatten(thisMap.get(AXIS_NAMES));
		if (objNames != null) {
			String[] names = new String[objNames.length];
			System.arraycopy(objNames, 0, names, 0, objNames.length);
			outBean.setAxisNames(names);
		}
		return outBean;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		AxisMapBean thisAxisMap = (AxisMapBean) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(AXIS_ID, rootFlattener.flatten(thisAxisMap.getAxisID()));
		outMap.put(AXIS_NAMES, rootFlattener.flatten(thisAxisMap.getAxisNames()));
		return outMap;
	}
}
