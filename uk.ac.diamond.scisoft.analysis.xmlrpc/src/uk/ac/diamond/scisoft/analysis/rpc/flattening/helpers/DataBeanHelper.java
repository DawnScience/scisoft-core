/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.rpc.flattening.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.plotserver.DataBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DataSetWithAxisInformation;
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

		List<DataSetWithAxisInformation> data = new ArrayList<DataSetWithAxisInformation>();
		for (Object object : dataGeneric) {
			data.add((DataSetWithAxisInformation) object);
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
