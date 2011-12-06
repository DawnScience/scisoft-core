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


/**
 * Class that wrappers the equation <br>
 * y(x) = ax^3 + bx^2 + cx + d
 */
public class Cubic extends AFunction {
	private static String cname = "Cubic";

	/**
	 * Basic constructor, not advisable to use
	 */
	public Cubic() {
		super(4);
		name = cname;
	}

	public Cubic(Parameter[] params) {
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
	 * @param minD
	 *            minimum boundary for the D parameter
	 * @param maxD
	 *            maximum boundary for the D parameter
	 */
	public Cubic(double minA, double maxA, double minB, double maxB, double minC, double maxC, double minD, double maxD) {
		super(4);

		getParameter(0).setLimits(minA,maxA);
		getParameter(0).setValue((minA + maxA) / 2.0);

		getParameter(1).setLimits(minB,maxB);
		getParameter(1).setValue((minB + maxB) / 2.0);

		getParameter(2).setLimits(minC,maxC);
		getParameter(2).setValue((minC + maxC) / 2.0);

		getParameter(3).setLimits(minD,maxD);
		getParameter(3).setValue((minD + maxD) / 2.0);

		name = cname;
	}

	double a, b, c, d;
	private void calcCachedParameters() {
		a = getParameterValue(0);
		b = getParameterValue(1);
		c = getParameterValue(2);
		d = getParameterValue(3);

		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		double position = values[0];
		return a * position * position * position + b * position * position + c * position + d;
	}

	@Override
	public void disp() {
// FIXME
//		String out = String.format("A Has Value %f within the bounds [%f,%f]", getParameterValue(0), 
//				getParameter(0).getLowerLimit(), getParameter(0).getUpperLimit());
//		TerminalPrinter.print(out);
//
//		out = String.format("B Has Value %f within the bounds [%f,%f]", getParameterValue(1),
//				getParameter(1).getLowerLimit(), getParameter(1).getUpperLimit());
//		TerminalPrinter.print(out);
//
//		out = String.format("C Has Value %f within the bounds [%f,%f]", getParameterValue(2),
//				getParameter(2).getLowerLimit(), getParameter(2).getUpperLimit());
//		TerminalPrinter.print(out);
//
//		out = String.format("D Has Value %f within the bounds [%f,%f]", getParameterValue(3),
//				getParameter(3).getLowerLimit(), getParameter(3).getUpperLimit());
//		TerminalPrinter.print(out);
	}

	@Override
	public double partialDeriv(int parameter, double... position) {
		final double pos = position[0];
		switch (parameter) {
		case 0:
			return pos * pos * pos;
		case 1:
			return pos * pos;
		case 2:
			return pos;
		case 3:
			return 1.0;
		default:
			throw new IndexOutOfBoundsException("Parameter index is out of bounds");
		}
	}
}
