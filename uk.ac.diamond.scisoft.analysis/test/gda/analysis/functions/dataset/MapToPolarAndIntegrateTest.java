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
public class MapToPolarAndIntegrateTest extends TestCase {
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
	public void testMapToPolarAndIntegrate() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(250,250,50.,0.,200.,45.); // eighth of annulus
		List<DataSet> pd = d.exec(mp);

		Sum s = new Sum();
		List<DataSet> dsets = pd.get(0).exec(s);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).get(0), answer*5e-3); // within 0.5% accuracy

		assertEquals(answer, pd.get(1).sum(), answer*5e-3); // within 0.5% accuracy

		assertEquals(answer, pd.get(2).sum(), answer*5e-3); // within 0.5% accuracy
		assertEquals(answer, pd.get(3).sum(), answer*5e-3); // within 0.5% accuracy
	}

	/**
	 * test clipping
	 */
	@Test
	public void testMapToPolarAndIntegrate2() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(360,360,50.,0.,200.,45.); // eighth of annulus
		List<DataSet> dsets = d.exec(mp);
		DataSet intp = dsets.get(0);
		DataSet intr = dsets.get(1);

		double answer = 140.*140./2 - Math.PI*(50.*50.)/8.;
		assertEquals(answer, intp.sum(), answer*10e-3); // within 1% accuracy
		assertEquals(answer, intr.sum(), answer*10e-3); // within 1% accuracy
	}

	/**
	 * test over branch cut
	 */
	@Test
	public void testMapToPolarAndIntegrate3() {
		MapToPolarAndIntegrate mp = new MapToPolarAndIntegrate(250,250,50.,22.5,200.,-22.5); // eighth of annulus
		List<DataSet> pd = d.exec(mp);

		Sum s = new Sum();
		List<DataSet> dsets = pd.get(0).exec(s);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).get(0), answer*5e-3); // within 0.5% accuracy

		assertEquals(answer, pd.get(1).sum(), answer*5e-3); // within 0.5% accuracy

		assertEquals(answer, pd.get(2).sum(), answer*5e-3); // within 0.5% accuracy
		assertEquals(answer, pd.get(3).sum(), answer*5e-3); // within 0.5% accuracy
	}

}
