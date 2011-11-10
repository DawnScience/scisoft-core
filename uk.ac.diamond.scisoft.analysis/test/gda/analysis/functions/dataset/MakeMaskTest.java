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
public class MakeMaskTest extends TestCase {

	/**
	 * 
	 */
	@Test
	public void testExecute() {
		double[] x = {1., 2., 3., 4., 5.};
		double[] y = {0, 1, 1, 0, 0};
		DataSet d = new DataSet(x);
		MakeMask m = new MakeMask(1.2, 3.5);
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) d.exec(m);

		for (int i = 0; i < y.length; i++) {
			assertEquals(y[i], dsets.get(0).get(i), 1e-8);
		}
	}

}
