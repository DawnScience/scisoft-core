/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.functions;

import gda.analysis.TerminalPrinter;

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

	public StraightLine(Parameter[] params) {
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
	public void disp() {

		String out = String.format("M Has Value %f within the bounds [%f,%f]", getParameterValue(0),
				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit());
		TerminalPrinter.print(out);

		out = String.format("C Has Value %f within the bounds [%f,%f]", getParameterValue(1),
				getParameter(1).getLowerLimit(), getParameter(1).getUpperLimit());
		TerminalPrinter.print(out);
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
