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
 * Subtract two functions
 */
public class Subtract extends ABinaryOperator implements IOperator {
	private static final String cname = "Subtract";

	public Subtract() {
		super();
		name = cname;
	}


	@Override
	public double val(double... values) {
		
		double y = fa == null ? 0 : fa.val(values);
		y -= fb == null ? 0 : fb.val(values);

		return y;
	}

	@Override
	public double partialDeriv(int index, double... values) throws IndexOutOfBoundsException {
		IParameter p = getParameter(index);
		double d = fa == null ? 0 : fa.partialDeriv(p, values);

		if (fb != null) {
			d -= fb.partialDeriv(p, values);
		}

		return d;
	}
}
