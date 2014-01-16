/*-
 * Copyright 2014 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class FunctionExtensionFactoryPluginTest {

	@Test
	public void testGetFittingFunctionNames() {
		String[] fittingFunctionNames = FunctionExtensionFactory.getFunctionExtensionFactory().getFittingFunctionNames();
		List<String> list = Arrays.asList(fittingFunctionNames);
		// test that we see normal/always available function
		assertTrue(list.contains("Gaussian"));
		// test that we see third-party defined function
		assertTrue(list.contains("Kichwa Function"));
	}

	@Test
	public void testGetFittingFunction() throws CoreException {
		IFunction function = FunctionExtensionFactory.getFunctionExtensionFactory().getFittingFunction("Kichwa Function");
		assertEquals("Kichwa Test Function", function.getName());
	}

}
