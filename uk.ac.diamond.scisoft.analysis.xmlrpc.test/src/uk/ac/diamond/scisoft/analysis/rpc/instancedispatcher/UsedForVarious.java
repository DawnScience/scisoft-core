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

package uk.ac.diamond.scisoft.analysis.rpc.instancedispatcher;

import java.io.IOException;

import org.junit.Assert;

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
