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

public class CompositeFunctionTest {

	private static final double ABS_TOL = 1e-7;

	@Test
	public void testFunction() {
		CompositeFunction cf = new CompositeFunction();

		AFunction fa = new Cubic();
		fa.setParameterValues(23., -10., 1.2, -5.2);

		AFunction fb = new StraightLine();
		fb.setParameterValues(4.2, -7.5);

		cf.addFunction(fa);
		cf.addFunction(fb);

		Assert.assertEquals(6, cf.getNoOfParameters());

		Assert.assertArrayEquals(new double[] {23., -10., 1.2, -5.2, 4.2, -7.5}, cf.getParameterValues(), ABS_TOL);
		Assert.assertEquals(-23. - 10. - 1.2 - 5.2 - 4.2 - 7.5, cf.val(-1), ABS_TOL);

		Assert.assertEquals(-1, cf.partialDeriv(0, -1), ABS_TOL);
		Assert.assertEquals(1, cf.partialDeriv(1, -1), ABS_TOL);
		Assert.assertEquals(-1, cf.partialDeriv(2, -1), ABS_TOL);
		Assert.assertEquals(1, cf.partialDeriv(3, -1), ABS_TOL);
		Assert.assertEquals(-1, cf.partialDeriv(4, -1), ABS_TOL);
		Assert.assertEquals(1, cf.partialDeriv(5, -1), ABS_TOL);
	}
}
