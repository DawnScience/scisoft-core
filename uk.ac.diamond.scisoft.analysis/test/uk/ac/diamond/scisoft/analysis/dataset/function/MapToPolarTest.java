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
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 *
 */
public class MapToPolarTest extends TestCase {
	AbstractDataset d = AbstractDataset.zeros(new int[] {500,500}, AbstractDataset.FLOAT32);

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
	public void testMapToPolar() {
		MapToPolar mp = new MapToPolar(250,250,50.,0.,200.,45.); // eighth of annulus
		AbstractDataset pd = mp.value(d).get(0);
		
		Sum s = new Sum();
		List<Number> dsets = s.value(pd);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).doubleValue(), answer*5e-3); // within 0.5% accuracy
	}

	/**
	 * test clipping
	 */
	@Test
	public void testMapToPolar2() {
		MapToPolar mp = new MapToPolar(360,360,50.,0.,200.,45.); // eighth of annulus
		List<AbstractDataset> dsets = mp.value(d);
		AbstractDataset pd = dsets.get(0);
		AbstractDataset upd = dsets.get(1); // new return with unit array

		Integrate2D int2d = new Integrate2D();
		dsets = int2d.value(pd);
		AbstractDataset intp = dsets.get(0);
		AbstractDataset intr = dsets.get(1);

		dsets = int2d.value(upd);
		intp = Maths.divide(intp, dsets.get(0));
		intr = Maths.divide(intr, dsets.get(1));

		Sum s = new Sum();
		List<Number> osets = s.value(pd);
//		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		double answer = Math.PI*(200.*200.)/8.;
		answer = 140.*140./2 - Math.PI*(50.*50.)/8.;
		assertEquals(answer, osets.get(0).doubleValue(), answer*10e-3); // within 1% accuracy
	}

	/**
	 * test over branch cut
	 */
	@Test
	public void testMapToPolar3() {
		MapToPolar mp = new MapToPolar(250,250,50.,22.5,200.,-22.5); // eighth of annulus
		AbstractDataset pd = mp.value(d).get(0);
		
		Sum s = new Sum();
		List<Number> dsets = s.value(pd);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).doubleValue(), answer*5e-3); // within 0.5% accuracy
	}


}
