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

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.plotserver.AxisMapBean;
import uk.ac.diamond.scisoft.analysis.plotserver.DataSetWithAxisInformation;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class DataSetWithAxisInformationHelper extends MapFlatteningHelper<DataSetWithAxisInformation> {

	public static final String DATA = "data";
	public static final String AXISMAP = "axisMap";

	public DataSetWithAxisInformationHelper() {
		super(DataSetWithAxisInformation.class);
	}

	@Override
	public DataSetWithAxisInformation unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener) {
		DataSetWithAxisInformation out = new DataSetWithAxisInformation();
		Dataset data = (Dataset) rootFlattener.unflatten(thisMap.get(DATA));
		AxisMapBean axisMap = (AxisMapBean) rootFlattener.unflatten(thisMap.get(AXISMAP));

		out.setData(data);
		out.setAxisMap(axisMap);

		return out;
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		DataSetWithAxisInformation in = (DataSetWithAxisInformation) obj;
		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(DATA, rootFlattener.flatten(in.getData()));
		outMap.put(AXISMAP, rootFlattener.flatten(in.getAxisMap()));
		return outMap;
	}

}
