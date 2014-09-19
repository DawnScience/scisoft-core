/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;

import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DatasetWithAxisInformation;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class DataBeanHelper extends MapFlatteningHelper<DataBean> {

	public static final String DATA = "data";
	public static final String AXISDATA = "axisData";

	public DataBeanHelper() {
		super(DataBean.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataBean unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		DataBean outBean = new DataBean();
		Object[] dataGeneric = (Object[]) rootFlattener.unflatten(thisMap.get(DATA));
		Map<Object, Object> axisDataGeneric = (Map<Object, Object>) rootFlattener.unflatten(thisMap.get(AXISDATA));

		List<DatasetWithAxisInformation> data = new ArrayList<DatasetWithAxisInformation>();
		for (Object object : dataGeneric) {
			data.add((DatasetWithAxisInformation) object);
		}

		Map<String, Dataset> axisData = new HashMap<String, Dataset>();
		for (Entry<Object, Object> entry : axisDataGeneric.entrySet()) {
			axisData.put((String) entry.getKey(), (Dataset) entry.getValue());
		}

		outBean.setData(data);
		outBean.setAxisData(axisData);

		return outBean;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		DataBean dataBean = (DataBean) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(DATA, rootFlattener.flatten(dataBean.getData()));
		outMap.put(AXISDATA, rootFlattener.flatten(dataBean.getAxisData()));
		return outMap;
	}

}
