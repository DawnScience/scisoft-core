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
import org.eclipse.dawnsci.analysis.dataset.impl.FloatDataset;
import org.junit.Test;

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
		Dataset d = new FloatDataset(x, null);
		MakeMask m = new MakeMask(1.2, 3.5);
		List<? extends Dataset> dsets = m.value(d);

		for (int i = 0; i < y.length; i++) {
			assertEquals(y[i], dsets.get(0).getDouble(i), 1e-8);
		}
	}

}
