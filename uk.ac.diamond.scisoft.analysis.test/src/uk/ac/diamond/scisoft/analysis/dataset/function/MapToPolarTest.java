/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetFactory;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 *
 */
public class MapToPolarTest extends TestCase {
	Dataset d = DatasetFactory.zeros(new int[] {500,500}, Dataset.FLOAT32);

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
		Dataset pd = mp.value(d).get(0);
		
		Sum s = new Sum();
		List<Number> dsets = s.value(pd);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).doubleValue(), answer*2e-3); // within 0.2% accuracy
	}

	/**
	 * test clipping
	 */
	@Test
	public void testMapToPolar2() {
		MapToPolar mp = new MapToPolar(360,360,50.,0.,200.,45.); // eighth of annulus
		List<? extends Dataset> dsets = mp.value(d);
		Dataset pd = dsets.get(0);
		Dataset upd = dsets.get(1); // new return with unit array

		Integrate2D int2d = new Integrate2D();
		dsets = int2d.value(pd);
		Dataset intp = dsets.get(0);
		Dataset intr = dsets.get(1);

		dsets = int2d.value(upd);
		intp = Maths.divide(intp, dsets.get(0));
		intr = Maths.divide(intr, dsets.get(1));

		Sum s = new Sum();
		List<Number> osets = s.value(pd);
		double answer = 140.*140./2 - Math.PI*(50.*50.)/8.;
		assertEquals(answer, osets.get(0).doubleValue(), answer*2e-3); // within 0.2% accuracy
	}

	/**
	 * test over branch cut
	 */
	@Test
	public void testMapToPolar3() {
		MapToPolar mp = new MapToPolar(250,250,50.,22.5,200.,-22.5); // eighth of annulus
		Dataset pd = mp.value(d).get(0);
		
		Sum s = new Sum();
		List<Number> dsets = s.value(pd);
		double answer = Math.PI*(200.*200. - 50.*50.)/8.;
		assertEquals(answer, dsets.get(0).doubleValue(), answer*2e-3); // within 0.2% accuracy
	}


}
