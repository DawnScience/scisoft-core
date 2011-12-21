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

abstract public class FlatteningHelper<T> implements IFlattener<T> {

	final private Class<T> type;

	public FlatteningHelper(Class<T> type) {
		this.type = type;
	}

	@Override
	public abstract Object flatten(Object obj, IRootFlattener rootFlattener);

	@Override
	public abstract T unflatten(Object obj, IRootFlattener rootFlattener);

	@Override
	public boolean canFlatten(Object obj) {
		return type.isAssignableFrom(obj.getClass());
	}

	protected Map<String, Object> getFlattenedOutMap(Class<T> clazz) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(IFlattener.TYPE_KEY, clazz.getCanonicalName());
		return returnMap;

	}

	final protected String getTypeCanonicalName() {
		return type.getCanonicalName();
	}

}
