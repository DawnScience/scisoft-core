/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.junit.Assert;
import org.junit.Test;

public class OffsetTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new Offset();
		Assert.assertEquals(1, f.getNoOfParameters());
		f.setParameterValues(23.);
		Assert.assertArrayEquals(new double[] {23.}, f.getParameterValues(), ABS_TOL);
		Assert.assertEquals(23., f.val(), ABS_TOL);

		Dataset xd = DatasetFactory.createFromObject(new double[] {-1, 0, 2});
		DoubleDataset dx;
		dx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {23., 23., 23.}, dx.getData(), ABS_TOL);

		dx = f.calculatePartialDerivativeValues(f.getParameter(0), xd);
		Assert.assertArrayEquals(new double[] {1, 1, 1}, dx.getData(), ABS_TOL);

		Assert.assertEquals(1, f.partialDeriv(f.getParameter(0), -1), ABS_TOL);
	}
}
