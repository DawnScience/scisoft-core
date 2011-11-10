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
import gda.analysis.utils.DatasetMaths;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

/**
 *
 */
public class MapToPolarTest extends TestCase {
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
	public void testMapToPolar() {
		MapToPolar mp = new MapToPolar(250,250,50.,0.,200.,45.); // eighth of annulus
		DataSet pd = d.exec(mp).get(0);
		
		Sum s = new Sum();
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) pd.exec(s);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).get(0), answer*5e-3); // within 0.5% accuracy
	}

	/**
	 * test clipping
	 */
	@Test
	public void testMapToPolar2() {
		MapToPolar mp = new MapToPolar(360,360,50.,0.,200.,45.); // eighth of annulus
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) d.exec(mp);
		DataSet pd = dsets.get(0);
		DataSet upd = dsets.get(1); // new return with unit array

		Integrate2D int2d = new Integrate2D();
		dsets = (ArrayList<DataSet>) pd.exec(int2d);
		DataSet intp = dsets.get(0);
		DataSet intr = dsets.get(1);

		dsets = (ArrayList<DataSet>) upd.exec(int2d);
		intp = DatasetMaths.divide(intp, dsets.get(0));
		intr = DatasetMaths.divide(intr, dsets.get(1));

		Sum s = new Sum();
		dsets = (ArrayList<DataSet>) pd.exec(s);
		double answer = 140.*140./2 - Math.PI*(50.*50.)/8.;
		assertEquals(answer, dsets.get(0).get(0), answer*10e-3); // within 1% accuracy
	}

	/**
	 * test over branch cut
	 */
	@Test
	public void testMapToPolar3() {
		MapToPolar mp = new MapToPolar(250,250,50.,22.5,200.,-22.5); // eighth of annulus
		DataSet pd = d.exec(mp).get(0);
		
		Sum s = new Sum();
		ArrayList<DataSet> dsets = (ArrayList<DataSet>) pd.exec(s);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).get(0), answer*5e-3); // within 0.5% accuracy
	}


}
