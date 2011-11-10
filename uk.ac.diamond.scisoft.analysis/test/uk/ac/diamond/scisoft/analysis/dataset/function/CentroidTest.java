/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;

/**
 *
 */
public class CentroidTest extends TestCase {
	AbstractDataset d;

	@Override
	public void setUp() {
		d = new IntegerDataset(100,60);
		d.fill(1);
	}

	/**
	 * 
	 */
	@Test
	public void testCentroid() {
		Centroid cen = new Centroid();
		List<Double> csets = cen.value(d);
		assertEquals("Centroid test, y", 50., csets.get(0), 1e-8);
		assertEquals("Centroid test, x", 30., csets.get(1), 1e-8);
	}
}

	