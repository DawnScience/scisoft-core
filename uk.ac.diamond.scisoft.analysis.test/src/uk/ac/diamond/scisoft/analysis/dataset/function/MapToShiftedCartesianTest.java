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
import org.junit.Test;

/**
 *
 */
public class MapToShiftedCartesianTest extends TestCase {
	Dataset d = DatasetFactory.zeros(new int[] {10, 20}, Dataset.FLOAT64);

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
	public void testMapToShiftedCartesian() {
		MapToShiftedCartesian mp = new MapToShiftedCartesian(5.3,7.6);
		Dataset pd = mp.value(d).get(0);

		Sum s = new Sum();
		List<Number> dsets = s.value(pd);
		double answer = (10.-5.3)*(20. - 7.6);
		assertEquals(answer, dsets.get(0).doubleValue(), answer*1e-4); // within 0.01% accuracy
	}

}
