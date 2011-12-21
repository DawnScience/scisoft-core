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

import java.util.List;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class ListHelper implements IFlattener<List<Object>> {

	public ListHelper() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		List<Object> l = (List<Object>) obj;
		return rootFlattener.flatten(l.toArray());
	}

	@Override
	public List<Object> unflatten(Object obj, IRootFlattener rootFlattener) {
		throw new AssertionError();
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof List;
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		return false;
	}
}
