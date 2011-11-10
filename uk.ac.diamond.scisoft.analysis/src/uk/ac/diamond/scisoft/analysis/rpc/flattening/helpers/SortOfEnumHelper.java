/*-
 * Copyright © 2011 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
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