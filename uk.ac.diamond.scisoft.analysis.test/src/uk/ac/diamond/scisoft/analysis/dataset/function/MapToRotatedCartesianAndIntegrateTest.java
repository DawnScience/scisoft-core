/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.dataset.function;

import junit.framework.TestCase;

import org.eclipse.dawnsci.analysis.dataset.impl.BooleanDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.junit.Test;

/**
 *
 */
public class MapToRotatedCartesianAndIntegrateTest extends TestCase {
	Dataset d = DatasetFactory.zeros(new int[] {500, 500}, Dataset.FLOAT64);

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
	public void testMapToRotatedCartesianAndIntegrate() {
		MapToRotatedCartesianAndIntegrate mp = new MapToRotatedCartesianAndIntegrate(100,70,50,30,45.);
		Dataset pd = mp.value(d).get(0);

		double answer = 50.*30;
		assertEquals(answer, ((Number) pd.sum()).doubleValue(), answer*1e-4); // within 0.01% accuracy
	}

	/**
	 * 
	 */
	@Test
	public void testMapToRotatedCartesianAndIntegrateMasked() {
		MapToRotatedCartesianAndIntegrate mp = new MapToRotatedCartesianAndIntegrate(100,70,50,30,45.);
		BooleanDataset m = new BooleanDataset(d.getShape());
		m.fill(true);
		mp.setMask(m);
		Dataset pd = mp.value(d).get(0);

		double answer = 50.*30;
		assertEquals(answer, ((Number) pd.sum()).doubleValue(), answer*1e-4); // within 0.01% accuracy

		m.fill(false);
		mp.setMask(m);
		pd = mp.value(d).get(0);

		assertEquals(0, ((Number) pd.sum()).doubleValue(), 1e-4); // within 0.01% accuracy

		m.fill(true);
		m.setSlice(false, new int[] {0, 108}, null, null);
		pd = mp.value(d).get(0);

		answer = 0.5*50.*30;
		assertEquals(answer, ((Number) pd.sum()).doubleValue(), answer*3e-2); // within 3% accuracy
	}
}
