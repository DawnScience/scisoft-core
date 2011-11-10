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
public class CentroidNDTest extends TestCase {
	DataSet d = new DataSet(100,60);

	@Override
	public void setUp() {
		d.fill(1.);
	}

	/**
	 * 
	 */
	@Test
	public void testCentroidND() {
		CentroidND cen = new CentroidND();
		ArrayList<DataSet> csets = (ArrayList<DataSet>) d.exec(cen);
		DataSet cd = csets.get(0);
		assertEquals("Centroid test, y", 50., cd.get(0), 1e-8);
		assertEquals("Centroid test, x", 30., cd.get(1), 1e-8);
	}
}

	