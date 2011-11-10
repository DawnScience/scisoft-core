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
