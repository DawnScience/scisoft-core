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

import java.lang.reflect.Array;
import java.util.Arrays;

import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.roi.IROI;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IFlattener;
import uk.ac.diamond.scisoft.analysis.rpc.flattening.IRootFlattener;

public class ObjectArrayHelper implements IFlattener<Object[]> {

	public ObjectArrayHelper() {
	}

	@Override
	public Object flatten(Object obj, IRootFlattener rootFlattener) {
		Object[] in = (Object[]) obj;
		Object[] out = new Object[in.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = rootFlattener.flatten(in[i]);
		}
		return out;
	}

	@Override
	public Object[] unflatten(Object obj, IRootFlattener rootFlattener) {
		Object[] inObjArray = (Object[]) obj;
		Object[] unflattened = new Object[inObjArray.length];

		// If all objects are the same class, unflatten to an array of that type
		Class<?> clazz = null;
		boolean canSpecializeUnflattenClazz = true;
		Class<?> superClazz = null;
		boolean canSpecializeUnflattenSuperClazz = true;
		for (int i = 0; i < unflattened.length; i++) {
			unflattened[i] = rootFlattener.unflatten(inObjArray[i]);
			if (unflattened[i] == null) {
				// ok, keep going
			} else if (superClazz != null && !superClazz.isAssignableFrom(unflattened[i].getClass())) {
				canSpecializeUnflattenSuperClazz = false;
			} else if (superClazz == null) {
				if (unflattened[i] instanceof IDataset) {
					superClazz = IDataset.class;
				} else if (unflattened[i] instanceof IROI) {
					superClazz = IROI.class;
				} else {
					canSpecializeUnflattenSuperClazz = false;
				}
			}

			if (unflattened[i] == null) {
				// ok, keep going
			} else if (clazz != null && !clazz.equals(unflattened[i].getClass())) {
				canSpecializeUnflattenClazz = false;
			} else if (clazz == null) {
				clazz = unflattened[i].getClass();

			}
		}

		if (canSpecializeUnflattenClazz && clazz != null) {
			canSpecializeUnflattenSuperClazz = true;
			superClazz = clazz;
		}
		if (canSpecializeUnflattenSuperClazz && superClazz != null) {
			@SuppressWarnings("unchecked")
			Class<? extends Object[]> arrayClazz = (Class<? extends Object[]>) Array.newInstance(superClazz, 0)
					.getClass();
			return Arrays.copyOf(unflattened, unflattened.length, arrayClazz);
		}
		return unflattened;
	}

	@Override
	public boolean canFlatten(Object obj) {
		return obj instanceof Object[];
	}

	@Override
	public boolean canUnFlatten(Object obj) {
		return canFlatten(obj);
	}
}
