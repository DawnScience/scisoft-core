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

import org.junit.Assert;
import org.junit.Test;

public class PseudoVoigtTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		IFunction f = new PseudoVoigt();
		Assert.assertEquals(5, f.getNoOfParameters());
		f.setParameterValues(23., 1.2, 2., 5.3, 0.6);
		Assert.assertArrayEquals(new double[] {23., 1.2, 2., 5.3, 0.6}, f.getParameterValues(), ABS_TOL);

		double sigma = 1.2 / Math.sqrt(8. * Math.log(2.));
		double y = (1. - 0.6) * 5.3 / Math.PI;
		y += 0.6 * 5.3 / Math.sqrt(2.0 * Math.PI) / sigma;

		Assert.assertEquals(y, f.val(23.), ABS_TOL);
	}
}
