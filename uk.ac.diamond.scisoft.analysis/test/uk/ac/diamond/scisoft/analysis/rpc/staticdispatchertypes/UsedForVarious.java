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
