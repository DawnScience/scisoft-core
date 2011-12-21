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
import uk.ac.diamond.scisoft.analysis.rpc.flattening.TypedNone;

public class NoneFlatteningHelper implements IFlattener<Object> {

	static final String TYPE_NAME = "__None__";
	static final String TYPED_NONE_TYPE = "typedNoneType";
	static final String NULL = "null";

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put(IFlattener.TYPE_KEY, TYPE_NAME);
		if (obj == null) {
			outMap.put(TYPED_NONE_TYPE, NULL);
		} else {
			TypedNone tn = (TypedNone) obj;
			outMap.put(TYPED_NONE_TYPE, tn.getType().getCanonicalName());
		}
		return outMap;
	}

	@Override
	public Object unflatten(Object obj, IRootFlattener rootFlattener) {
		if (obj == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		String className = (String) (((Map<String, Object>) obj).get(TYPED_NONE_TYPE));
		if (className != null && !className.equals(NULL)) {
			try {
				TypedNone typedNone = new TypedNone(className);
				return typedNone;
			} catch (ClassNotFoundException e) {
				// Do nothing, just return an Object null
			}
		}
		return null;
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj == null || obj instanceof TypedNone;
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Map<?, ?>) {
			Map<?, ?> thisMap = (Map<?, ?>) obj;
			return TYPE_NAME.equals(thisMap.get(IFlattener.TYPE_KEY));
		}

		return false;
	}
}
