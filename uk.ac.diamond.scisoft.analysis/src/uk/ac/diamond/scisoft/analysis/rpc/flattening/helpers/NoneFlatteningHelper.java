/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
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
