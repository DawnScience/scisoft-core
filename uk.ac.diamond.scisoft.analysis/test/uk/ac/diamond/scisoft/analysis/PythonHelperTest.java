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

package uk.ac.diamond.scisoft.analysis;


import org.junit.Assert;
import org.junit.Test;

public class PythonHelperTest {

	// A quick test of the test
	@Test
	public void testParseArray() {
		Assert.assertArrayEquals(new String[0], PythonHelper.parseArray("[]"));
		Assert.assertArrayEquals(new String[] {"one"}, PythonHelper.parseArray("['one']"));
		Assert.assertArrayEquals(new String[] {"one", "two"}, PythonHelper.parseArray("['one', 'two']"));
		Assert.assertArrayEquals(new String[] {"one one", "two two"}, PythonHelper.parseArray("['one one', 'two two']"));
	}

}
