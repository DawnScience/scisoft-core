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
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Test;

/**
 *
 */
public class SumTest extends TestCase {

	/**
	 * 
	 */
	@Test
	public void testExecute() {
		double[] x = {1., 2., 3., 4., 5.};
		Dataset d = new DoubleDataset(x);
		Sum s = new Sum();
		List<Number> dsets = s.value(d);

		assertEquals(15., dsets.get(0).doubleValue(), 1e-8);
	}

}
