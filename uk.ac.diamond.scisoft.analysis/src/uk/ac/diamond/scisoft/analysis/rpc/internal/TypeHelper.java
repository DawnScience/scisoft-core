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

package uk.ac.diamond.scisoft.analysis.rpc.internal;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to help map primitive and boxed types.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Primitive_wrapper_class">Primitive_wrapper_class</a>
 */
public class TypeHelper {
	private static Map<Class<?>, Class<?>> mapPrimitiveToBoxed;

	static {
		// Add all 8 primitive types to the map
		mapPrimitiveToBoxed = new HashMap<Class<?>, Class<?>>();
		mapPrimitiveToBoxed.put(Boolean.TYPE, Boolean.class);
		mapPrimitiveToBoxed.put(Byte.TYPE, Byte.class);
		mapPrimitiveToBoxed.put(Character.TYPE, Character.class);
		mapPrimitiveToBoxed.put(Double.TYPE, Double.class);
		mapPrimitiveToBoxed.put(Float.TYPE, Float.class);
		mapPrimitiveToBoxed.put(Integer.TYPE, Integer.class);
		mapPrimitiveToBoxed.put(Long.TYPE, Long.class);
		mapPrimitiveToBoxed.put(Short.TYPE, Short.class);
	}

	/**
	 * @param boxedClass
	 * @param primitiveClass
	 * @return true if primitiveClass represents the primitive version of boxedClass
	 */
	public static boolean isBoxed(Class<? extends Object> boxedClass, Class<?> primitiveClass) {
		Class<?> clazz = mapPrimitiveToBoxed.get(primitiveClass);
		if (clazz == null) {
			// Not a primitive type
			return false;
		}
		return clazz.equals(boxedClass);
	}

	public static boolean isPrimitive(Class<?> clazz) {
		return mapPrimitiveToBoxed.containsKey(clazz);
	}

}
