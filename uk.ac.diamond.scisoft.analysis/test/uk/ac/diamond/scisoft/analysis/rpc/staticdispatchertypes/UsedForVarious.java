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

package uk.ac.diamond.scisoft.analysis.rpc.staticdispatchertypes;

import java.io.IOException;

import org.junit.Assert;

@SuppressWarnings("unused")
public class UsedForVarious {

	public static int callCheckedException(int o) throws IOException {
		throw new IOException();
	}

	public static int callUnCheckedException(int o) {
		throw new UnsupportedOperationException();
	}

	public int nonStaticMethod(int o) {
		return 0;
	}

	public String similarSignatureStaticAndNonStatic(int o) {
		return "non-static";
	}

	public static String similarSignatureStaticAndNonStatic(Integer o) {
		return "static";
	}

	public static Class<Object> overloaded(Object o) {
		return Object.class;
	}

	public static Class<Integer> overloaded(Integer o) {
		return Integer.class;
	}
	
	public static Object callObjectWithNull(Object o) {
		Assert.assertNull(o);
		return "callObjectWithNull";
	}
	
	public static Object callNullReturn(int o) {
		Assert.assertEquals(0, o);
		return null;
	}
	
	public static Object callNullReturn(String o) {
		Assert.assertEquals("arg", o);
		return null;
	}
	
	public static Class<String> typednone(String o) {
		return String.class;
	}

	public static Class<Integer> typednone(Integer o) {
		return Integer.class;
	}
	
}
