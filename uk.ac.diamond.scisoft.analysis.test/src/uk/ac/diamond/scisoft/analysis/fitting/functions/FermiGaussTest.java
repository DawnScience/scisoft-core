/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.junit.Assert;
import org.junit.Test;

public class FermiGaussTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		AFunction f = new FermiGauss();
		Assert.assertEquals(6, f.getNoOfParameters());
		f.setParameterValues(23., 110., 2.5, -0.5, -5.2, 0);
		Assert.assertArrayEquals(new double[] {23., 110., 2.5, -0.5, -5.2, 0}, f.getParameterValues(), ABS_TOL);

		Assert.assertEquals((2.5*0 - 0.5)/2. - 5.2, f.val(23.), ABS_TOL);

		double w = 110 * Math.log(2)*FermiGauss.K2EV_CONVERSION_FACTOR;
		Assert.assertEquals((2.5*w - 0.5) / 3 - 5.2, f.val(23. + w), ABS_TOL);
		Assert.assertEquals((-2.5*w - 0.5) / 1.5 - 5.2, f.val(23. - w), ABS_TOL);

		DoubleDataset xd = new DoubleDataset(new double[] {23. - w, 23, 23. + 2 * w});
		DoubleDataset fx;
		fx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {(-2.5*w - 0.5)/1.5 - 5.2, (2.5*0 - 0.5)/2. - 5.2,
				(2.5*2*w - 0.5)/5 - 5.2}, fx.getData(), ABS_TOL);

		f.setParameterValues(23., 110., 2.5, -0.5, -5.2, 1);
		fx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {((-2.5*w - 0.5)/1.5 - 5.2)*Math.exp(-5.685e-3),
				((2.5*0 - 0.5)/2. - 5.2)*Math.exp(-3.816e-3),
				((2.5*2*w - 0.5)/5 - 5.2)*Math.exp(9.81e-3)}, fx.getData(), 100*ABS_TOL);

		f.setParameterValues(23., 110., 2.5*2, -0.5*2, -5.2*2, 1);
		fx = f.calculateValues(xd);
		Assert.assertArrayEquals(new double[] {2*((-2.5*w - 0.5)/1.5 - 5.2)*Math.exp(-5.685e-3),
				2*((2.5*0 - 0.5)/2. - 5.2)*Math.exp(-3.816e-3),
				2*((2.5*2*w - 0.5)/5 - 5.2)*Math.exp(9.81e-3)}, fx.getData(), 200*ABS_TOL);
	}
}
