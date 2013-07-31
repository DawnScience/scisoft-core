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
