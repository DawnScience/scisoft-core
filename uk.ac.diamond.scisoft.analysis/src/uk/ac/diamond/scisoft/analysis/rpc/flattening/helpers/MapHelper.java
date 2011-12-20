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
import java.util.Map.Entry;
import java.util.Set;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

@SuppressWarnings("rawtypes")
public class MapHelper extends MapFlatteningHelper<Map> {

	public static final String KEYS = "keys";
	public static final String VALUES = "values";

	public MapHelper() {
		super(Map.class);
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		Map<?, ?> inMap = (Map<?, ?>) obj;

		Object[] keys = new Object[inMap.size()];
		Object[] values = new Object[inMap.size()];

		int i = 0;
		for (Object entryObj : (Set<?>) inMap.entrySet()) {
			Entry<?, ?> entry = (Entry<?, ?>) entryObj;
			keys[i] = rootFlattener.flatten(entry.getKey());
			values[i] = rootFlattener.flatten(entry.getValue());
			i++;
		}

		Map<String, Object> outMap = createMap(getTypeCanonicalName());
		outMap.put(KEYS, keys);
		outMap.put(VALUES, values);
		return outMap;
	}

	@Override
	public Map<?, ?> unflatten(Map<?, ?> inMap, IRootFlattener rootFlattener) {
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		Object[] keys = (Object[]) inMap.get(KEYS);
		Object[] values = (Object[]) inMap.get(VALUES);
		if (keys.length != values.length) {
			throw new UnsupportedOperationException("Cannot unflatten when keys and values are different lengths");
		}
		for (int i = 0; i < keys.length; i++) {
			returnMap.put(rootFlattener.unflatten(keys[i]), rootFlattener.unflatten(values[i]));
		}
		return returnMap;
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof Map;
	}
}
