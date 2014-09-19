/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;
import org.junit.Test;

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
