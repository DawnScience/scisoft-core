/*
 * Copyright © 2011 Diamond Light Source Ltd.
 * Contact :  ScientificSoftware@diamond.ac.uk
 * 
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.FloatDataset;

/**
 *
 */
public class MakeMaskTest extends TestCase {

	/**
	 * 
	 */
	@Test
	public void testExecute() {
		float[] x = {1.f, 2.f, 3.f, 4.f, 5.f};
		double[] y = {0, 1, 1, 0, 0};
		AbstractDataset d = new FloatDataset(x, null);
		MakeMask m = new MakeMask(1.2, 3.5);
		List<AbstractDataset> dsets = m.value(d);

		for (int i = 0; i < y.length; i++) {
			assertEquals(y[i], dsets.get(0).getDouble(i), 1e-8);
		}
	}

}
