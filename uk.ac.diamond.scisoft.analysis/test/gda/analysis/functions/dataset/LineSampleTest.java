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
public class LineSampleTest extends TestCase {
	DataSet d = new DataSet(100,100);

	@Override
	public void setUp() {
		d.fill(1.);
	}


	/**
	 * 
	 */
	@Test
	public void testLineSample() {
		LineSample ls = new LineSample(12, 75, 63, 24, 1.);
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) d.exec(ls);
		DataSet dls = dsets.get(0);

		for (int i=0; i<dls.getSize(); i++) {
			assertEquals("Line sample test, " + i + " = " + dls.get(i), 1., dls.get(i), 1e-8);
		}
	}
}

	