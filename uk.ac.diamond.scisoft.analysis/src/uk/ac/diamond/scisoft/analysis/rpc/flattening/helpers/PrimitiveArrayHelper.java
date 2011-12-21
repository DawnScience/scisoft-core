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

import org.apache.commons.lang.ArrayUtils;

import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

abstract public class PrimitiveArrayHelper implements IFlattener<Object> {

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		if (obj instanceof int[]) {
			return ArrayUtils.toObject((int[]) obj);
		} else if (obj instanceof boolean[]) {
			return ArrayUtils.toObject((boolean[]) obj);
		} else if (obj instanceof double[]) {
			return ArrayUtils.toObject((double[]) obj);
		}
		throw new AssertionError();
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof int[] || obj instanceof boolean[] || obj instanceof double[];
	}
}
