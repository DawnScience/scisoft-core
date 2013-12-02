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

/**
 * Divide two functions
 */
public class Divide extends ABinaryOperator implements IOperator {
	private static final String cname = "Divide";

	public Divide() {
		super();
		name = cname;
	}

	@Override
	public double val(double... values) {
		
		double y = fa == null ? 0 : fa.val(values);
		y /= fb == null ? 0 : fb.val(values);

		return y;
	}

	@Override
	public double partialDeriv(int index, double... values) throws IndexOutOfBoundsException {
		IParameter p = getParameter(index);
		double d = 0;

		if (fa == null) {
			if (fb == null) {
				d = Double.NaN;
			}
		} else {
			double da = fa.partialDeriv(p, values);
			if (fb == null) {
				d = da / 0;
			} else {
				double b = fb.val(values);
				d =  da * b;
				d -= fa.val(values) * fb.partialDeriv(p, values);
				d /= b*b;
			}
		}
		return d;
	}
}
