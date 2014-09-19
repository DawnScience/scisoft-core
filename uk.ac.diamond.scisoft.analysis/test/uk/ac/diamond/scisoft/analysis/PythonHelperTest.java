/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
