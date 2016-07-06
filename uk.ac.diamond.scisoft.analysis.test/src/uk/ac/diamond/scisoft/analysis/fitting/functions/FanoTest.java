/*-
 * Copyright (c) 2015 Diamond Light Source Ltd.
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

public class FanoTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new Fano();
		Assert.assertEquals(4, f.getNoOfParameters());
		f.setParameterValues(23., 5., 1.2, 4);
		Assert.assertArrayEquals(new double[] {23., 5., 1.2, 4}, f.getParameterValues(), ABS_TOL);

		double hr = 1.2*4*4.;
		Assert.assertEquals(hr, f.val(23.), ABS_TOL);

		double w = 5./2;
		double ha = 1.2*(4.-1)*(4.-1)/2.;
		double hb = 1.2*(4.+1)*(4.+1)/2.;
		Assert.assertEquals(ha, f.val(23. - w), ABS_TOL);
		Assert.assertEquals(hb, f.val(23. + w), ABS_TOL);

		Dataset xd = DatasetFactory.createFromObject(new double[] {23. - w, 23, 23. + w});
		DoubleDataset dx;
		dx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {ha, hr, hb}, dx.getData(), ABS_TOL);
	}
}
