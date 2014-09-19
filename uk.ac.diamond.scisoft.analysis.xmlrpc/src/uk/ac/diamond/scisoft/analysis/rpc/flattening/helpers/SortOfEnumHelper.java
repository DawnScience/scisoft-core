/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
