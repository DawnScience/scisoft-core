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

package uk.ac.diamond.scisoft.analysis.rpc.flattening;

import uk.ac.diamond.scisoft.analysis.rpc.AbstractAnalysisRpcGenericDispatcher;
import uk.ac.diamond.scisoft.analysis.rpc.internal.TypeHelper;

/**
 * A wrapper type for <code>null</code> (on Java) or <code>None</code> (on Python)
 * <p>
 * While null/None is supported directly there is one case where knowing the intended type of the null is important,
 * when dealing with overloaded methods.
 * <p>
 * In Java if a class has these two overloaded methods:
 * 
 * <pre>
 * void func(String s);
 * 
 * void func(Integer i);
 * </pre>
 * 
 * and you called them the compiler would determine the correct version based on the compile time types. As RPC has to
 * rely on only run time types when determining which method to call, a TypedNone can be used to distinguish to achieve
 * the equivalent to having written in Java:
 * 
 * <pre>
 * o.func((String) null);
 * o.func((Integer) null);
 * </pre>
 * <p>
 * TypedNone's are immutable.
 * 
 * @see AbstractAnalysisRpcGenericDispatcher
 */
public class TypedNone {
	private Class<? extends Object> clazz;

	/**
	 * Construct a new TypedNone with the intended data type the None represents.
	 * 
	 * @param clazz
	 *            class type of type null may not be <code>null</code> or a primitive class type (e.g. cannot be
	 *            {@link Integer#TYPE})
	 */
	public TypedNone(Class<? extends Object> clazz) {
		if (clazz == null)
			throw new NullPointerException();
		if (TypeHelper.isPrimitive(clazz))
			throw new IllegalArgumentException("Cannot cast from null to primitive type");
		this.clazz = clazz;
	}

	/**
	 * Construct a new TypedNone with the intended data type the None represents.
	 * 
	 * @param className
	 *            the name of the class suitable for passing to {@link Class#forName(String)}
	 * @throws ClassNotFoundException
	 *             if className is invalid
	 */
	public TypedNone(String className) throws ClassNotFoundException {
		Class<? extends Object> clazz = Class.forName(className);
		if (TypeHelper.isPrimitive(clazz))
			throw new IllegalArgumentException("Cannot cast from null to primitive type");
		this.clazz = clazz;
	}

	/**
	 * Return the data type represented by the TypedNone
	 * 
	 * @return data type
	 */
	public Class<? extends Object> getType() {
		return clazz;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof TypedNone) {
			TypedNone typed = (TypedNone) obj;
			return clazz.equals(typed.clazz);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 7 + clazz.hashCode();
	}

	@Override
	public String toString() {
		return "(" + clazz.getCanonicalName() + ")null";
	}
}
