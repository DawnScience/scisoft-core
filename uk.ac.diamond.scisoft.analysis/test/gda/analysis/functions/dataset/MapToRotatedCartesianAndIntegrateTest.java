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

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

/**
 *
 */
public class MapToRotatedCartesianAndIntegrateTest extends TestCase {
	DataSet d = new DataSet(500,500);

	/**
	 */
	@Override
	public void setUp() {
		d.fill(1.);
	}

	/**
	 * 
	 */
	@Test
	public void testMapToRotatedCartesianAndIntegrate() {
		MapToRotatedCartesianAndIntegrate mp = new MapToRotatedCartesianAndIntegrate(100,70,50,30,45.);
		List<DataSet> pd = d.exec(mp);

		Sum s = new Sum();
		List<DataSet> dsets = pd.get(0).exec(s);
		double answer = 50.*30;
		assertEquals(answer, dsets.get(0).get(0), answer*1e-4); // within 0.01% accuracy

		dsets = pd.get(1).exec(s);
		assertEquals(answer, dsets.get(0).get(0), answer*1e-4); // within 0.01% accuracy

		assertEquals(answer, pd.get(2).sum(), answer*1e-4); // within 0.01% accuracy
		assertEquals(answer, pd.get(3).sum(), answer*1e-4); // within 0.01% accuracy

	}

}
