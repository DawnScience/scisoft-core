/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Assert;
import org.junit.Test;

public class FermiTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new Fermi();
		Assert.assertEquals(4, f.getNoOfParameters());
		f.setParameterValues(23., 110., 1.2, -5.2);
		Assert.assertArrayEquals(new double[] {23., 110., 1.2, -5.2}, f.getParameterValues(), ABS_TOL);

		Assert.assertEquals(1.2 / 2. - 5.2, f.val(23.), ABS_TOL);

		double w = 110 * Math.log(2);
		Assert.assertEquals(1.2 / 3 - 5.2, f.val(23. + w), ABS_TOL);
		Assert.assertEquals(1.2 / 1.5 - 5.2, f.val(23. - w), ABS_TOL);

		DoubleDataset xd = new DoubleDataset(new double[] {23. - w, 23, 23. + 2 * w});
		DoubleDataset dx;
		dx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {1.2/1.5 - 5.2, 1.2/2 - 5.2, 1.2/5 - 5.2}, dx.getData(), ABS_TOL);
	}
}
