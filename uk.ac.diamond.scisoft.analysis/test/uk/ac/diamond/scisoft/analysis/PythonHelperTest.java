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
