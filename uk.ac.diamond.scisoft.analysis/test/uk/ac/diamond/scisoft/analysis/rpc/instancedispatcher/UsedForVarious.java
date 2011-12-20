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

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;

import java.io.IOException;

import org.junit.Assert;

@SuppressWarnings("unused")
public class UsedForVarious {

	public int callCheckedException(int o) throws IOException {
		throw new IOException();
	}

	public int callUnCheckedException(int o) {
		throw new UnsupportedOperationException();
	}

	public static int staticMethod(int o) {
		return 0;
	}

	public String similarSignatureStaticAndNonStatic(int o) {
		return "non-static";
	}

	public static String similarSignatureStaticAndNonStatic(Integer o) {
		return "static";
	}

	public Class<Object> overloaded(Object o) {
		return Object.class;
	}

	public Class<Integer> overloaded(Integer o) {
		return Integer.class;
	}

	public Object callObjectWithNull(Object o) {
		Assert.assertNull(o);
		return "callObjectWithNull";
	}

	public Object callNullReturn(int o) {
		Assert.assertEquals(0, o);
		return null;
	}

	public Object callNullReturn(String o) {
		Assert.assertEquals("arg", o);
		return null;
	}

	public Class<String> typednone(String o) {
		return String.class;
	}

	public Class<Integer> typednone(Integer o) {
		return Integer.class;
	}
}
