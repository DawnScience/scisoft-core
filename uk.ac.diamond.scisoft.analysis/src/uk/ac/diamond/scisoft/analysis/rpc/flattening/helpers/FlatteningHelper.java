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
