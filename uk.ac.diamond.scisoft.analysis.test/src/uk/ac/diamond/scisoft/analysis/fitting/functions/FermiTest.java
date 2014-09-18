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
