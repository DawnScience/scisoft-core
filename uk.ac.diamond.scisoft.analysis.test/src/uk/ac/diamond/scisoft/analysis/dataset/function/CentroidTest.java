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
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.function.Centroid;
import org.junit.Test;

/**
 *
 */
public class CentroidTest extends TestCase {
	Dataset d;

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

	
