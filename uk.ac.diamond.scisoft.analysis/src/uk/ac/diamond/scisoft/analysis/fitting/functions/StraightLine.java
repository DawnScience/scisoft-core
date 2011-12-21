/*
 * Copyright 2011 Diamond Light Source Ltd.
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
 * This class implements the function y(x) = m*x + c
 */
public class StraightLine extends AFunction {
	private static String cname = "Linear";

	/**
	 * Basic constructor, not advisable to use.
	 */
	public StraightLine() {
		super(2);
		name = cname;
	}

	public StraightLine(IParameter[] params) {
		super(params);
		name = cname;
	}

	/**
	 * Constructor allowing for careful construction of bounds
	 * 
	 * @param minM
	 *            Minimum value allowed for m
	 * @param maxM
	 *            Maximum value allowed for m
	 * @param minC
	 *            Minimum value allowed for c
	 * @param maxC
	 *            Maximum value allowed for c
	 */
	public StraightLine(double minM, double maxM, double minC, double maxC) {
		super(2);

		getParameter(0).setLowerLimit(minM);
		getParameter(0).setUpperLimit(maxM);
		getParameter(0).setValue((minM + maxM) / 2.0);

		getParameter(1).setLowerLimit(minC);
		getParameter(1).setUpperLimit(maxC);
		getParameter(1).setValue((minC + maxC) / 2.0);

		name = cname;
	}

	double a, b;
	private void calcCachedParameters() {
		a = getParameterValue(0);
		b = getParameterValue(1);

		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];
		return a * position + b;
	}

	@Override
	public String toString() {
		final StringBuilder out = new StringBuilder();
		out.append(String.format("M Has Value %f within the bounds [%f,%f]\n", getParameterValue(0),
				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit()));
		out.append(String.format("C Has Value %f within the bounds [%f,%f]", getParameterValue(1),
				getParameter(1).getLowerLimit(), getParameter(1).getUpperLimit()));
		return out.toString();
	}

	@Override
	public double partialDeriv(int parameter, double... position) {
		switch (parameter) {
		case 0:
			return position[0];
		case 1:
			return 1.0;
		default:
			throw new IndexOutOfBoundsException("Parameter index is out of bounds");
		}
	}
}
