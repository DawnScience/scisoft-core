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

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

abstract public class SortOfEnumHelper<T> extends MapFlatteningHelper<T> {
	public SortOfEnumHelper(Class<T> type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> flatten(Object obj, IRootFlattener rootFlattenerj) {
		T typedObj = (T) obj;
		Map<String, Object> outMap = super.createMap(getTypeCanonicalName());
		outMap.put(CONTENT, typedObj.toString());
		return outMap;
	}

	@Override
	public abstract T unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener);

}
