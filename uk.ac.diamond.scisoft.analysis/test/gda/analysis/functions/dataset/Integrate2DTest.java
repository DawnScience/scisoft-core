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
public class Integrate2DTest extends TestCase {
	DataSet d = DataSet.arange(9.);

	/**
	 */
	@Override
	public void setUp() {
		d.setShape(3, 3);
	}

	/**
	 */
	@Test
	public void testIntegrate2D() {
		Integrate2D ia = new Integrate2D();
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) d.exec(ia);
		assertEquals(3., dsets.get(0).get(0), 1e-8);
		assertEquals(12., dsets.get(0).get(1), 1e-8);
		assertEquals(21., dsets.get(0).get(2), 1e-8);
		assertEquals(9., dsets.get(1).get(0), 1e-8);
		assertEquals(12., dsets.get(1).get(1), 1e-8);
		assertEquals(15., dsets.get(1).get(2), 1e-8);
	}

	/**
	 */
	@Test
	public void testIntegrate2DIntIntIntInt() {
		Integrate2D ia = new Integrate2D(0, 0, 1, 1);
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) d.exec(ia);
		assertEquals(1., dsets.get(0).get(0), 1e-8);
		assertEquals(7., dsets.get(0).get(1), 1e-8);
		assertEquals(3., dsets.get(1).get(0), 1e-8);
		assertEquals(5., dsets.get(1).get(1), 1e-8);
	}

}
