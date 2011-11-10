/*-
 * Copyright © 2009 Diamond Light Source Ltd.
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

package gda.analysis.functions.dataset;

import gda.analysis.DataSet;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

/**
 *
 */
public class SumTest extends TestCase {

	/**
	 * 
	 */
	@Test
	public void testExecute() {
		double[] x = {1., 2., 3., 4., 5.};
		DataSet d = new DataSet(x);
		Sum s = new Sum();
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) d.exec(s);

		assertEquals(15., dsets.get(0).get(0), 1e-8);
	}

}
