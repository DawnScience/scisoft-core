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
