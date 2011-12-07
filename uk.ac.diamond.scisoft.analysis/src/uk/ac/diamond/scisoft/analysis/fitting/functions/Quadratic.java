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

package uk.ac.diamond.scisoft.analysis.fitting.functions;


/**
 * Class that wrappers the function y(x) = ax^2 + bx + c
 */
public class Quadratic extends AFunction {
	private static String cname = "Quadratic";

	/**
	 * Basic constructor, not advisable to use
	 */
	public Quadratic() {
		super(3);
		name = cname;
	}

	public Quadratic(IParameter[] params) {
		super(params);
		name = cname;
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 * 
	 * @param minA
	 *            minimum boundary for the A parameter
	 * @param maxA
	 *            maximum boundary for the A parameter
	 * @param minB
	 *            minimum boundary for the B parameter
	 * @param maxB
	 *            maximum boundary for the B parameter
	 * @param minC
	 *            minimum boundary for the C parameter
	 * @param maxC
	 *            maximum boundary for the C parameter
	 */
	public Quadratic(double minA, double maxA, double minB, double maxB, double minC, double maxC) {
		super(3);

		getParameter(0).setLowerLimit(minA);
		getParameter(0).setUpperLimit(maxA);
		getParameter(0).setValue((minA + maxA) / 2.0);

		getParameter(1).setLowerLimit(minB);
		getParameter(1).setUpperLimit(maxB);
		getParameter(1).setValue((minB + maxB) / 2.0);

		getParameter(2).setLowerLimit(minC);
		getParameter(2).setUpperLimit(maxC);
		getParameter(2).setValue((minC + maxC) / 2.0);

		name = cname;
	}
	
	/**
	 * A very simple constructor which just specifies the values, not the bounds
	 * @param Params
	 */
	public Quadratic(double[] Params) {
		super(Params);

		name = cname;
	}

	double a, b, c;
	private void calcCachedParameters() {
		a = getParameterValue(0);
		b = getParameterValue(1);
		c = getParameterValue(2);

		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];
		return a * position * position + b * position + c;
	}

	@Override
	public String toString() {
		final StringBuilder out = new StringBuilder();
		out.append(String.format("A Has Value %f within the bounds [%f,%f]\n", getParameterValue(0),
				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit()));
		out.append(String.format("B Has Value %f within the bounds [%f,%f]\n", getParameterValue(1),
				getParameter(1).getLowerLimit(), getParameter(1).getUpperLimit()));
		out.append(String.format("C Has Value %f within the bounds [%f,%f]", getParameterValue(2),
				getParameter(2).getLowerLimit(), getParameter(2).getUpperLimit()));
		return out.toString();
	}

	@Override
	public double partialDeriv(int parameter, double... position) {
		final double pos = position[0];
		switch (parameter) {
		case 0:
			return pos * pos;
		case 1:
			return pos;
		case 2:
			return 1.0;
		default:
			throw new IndexOutOfBoundsException("Parameter index is out of bounds");
		}
	}
}
