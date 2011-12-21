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

import java.util.HashMap;
import java.util.Map;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

abstract public class MapFlatteningHelper<T> extends FlatteningHelper<T> {
	protected static final String CONTENT = "content";

	public MapFlatteningHelper(Class<T> type) {
		super(type);
	}

	public abstract T unflatten(Map<?, ?> thisMap, IRootFlattener rootFlattener);

	public Map<String, Object> createMap(String typeName) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put(IFlattener.TYPE_KEY, typeName);
		return outMap;
	}

	@Override
	public T unflatten(Object obj, IRootFlattener rootFlattener) {
		return unflatten((Map<?, ?>) obj, rootFlattener);
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		if (obj instanceof Map<?, ?>) {
			Map<?, ?> thisMap = (Map<?, ?>) obj;
			return getTypeCanonicalName().equals(thisMap.get(IFlattener.TYPE_KEY));
		}

		return false;
	}
}
