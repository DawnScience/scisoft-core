/*-
 * Copyright © 2010 Diamond Light Source Ltd.
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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

/**
 * Class that wrappers the equation <br>
 * y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n
 */
public class Polynomial extends AFunction {
	private static String cname = "Polynomial";

	final double[] a;
	final int nparams; // actually degree + 1

	/**
	 * Basic constructor, not advisable to use
	 */
	public Polynomial() {
		super(1);
		a = new double[1];
		nparams = 1;
		name = cname;
	}

	/**
	 * Make a polynomial of given degree (0 - constant, 1 - linear, 2 - quadratic, etc)
	 * @param degree
	 */
	public Polynomial(final int degree) {
		super(degree + 1);
		a = new double[degree + 1];
		nparams = degree + 1;
		name = cname;
	}

	/**
	 * Make a polynomial with given parameters
	 * @param params
	 */
	public Polynomial(double[] params) {
		super(params);
		a = new double[params.length];
		nparams = params.length;
		name = cname;
	}

	/**
	 * Make a polynomial with given parameters
	 * @param params
	 */
	public Polynomial(Parameter[] params) {
		super(params);
		a = new double[params.length];
		nparams = params.length;
		name = cname;
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 * 
	 * @param min
	 *            minimum boundaries
	 * @param max
	 *            maximum boundaries
	 */
	public Polynomial(double[] min, double[] max) {
		super(0);
		if (min.length != max.length) {
			throw new IllegalArgumentException("");
		}
		nparams = min.length;
		parameters = new Parameter[nparams];

		for (int i = 0; i < nparams; i++) {
			parameters[i] = new Parameter(0.5*(min[i] + max[i]), min[i], max[i]);
		}

		a = new double[nparams];
		name = cname;
	}

	private void calcCachedParameters() {
		for (int i = 0; i < nparams; i++)
			a[i] = getParameterValue(i);

		markParametersClean();
	}

	@Override
	public double val(double... values) {
		if (areParametersDirty())
			calcCachedParameters();

		final double position = values[0];

		double v = a[0];
		for (int i = 1; i < nparams; i++) {
			v = v * position + a[i];
		}
		return v;
	}

	@Override
	public void disp() {
		// FIXME
//		StringBuilder out = new StringBuilder();
//
//		for (int i = 0; i < nparams; i++) {
//			out.append(String.format("Parameter %d has value %f within the bounds [%f,%f]\n", i, getParameterValue(i), 
//					getParameter(i).getLowerLimit(), getParameter(i).getUpperLimit()));
//		}
//
//		TerminalPrinter.print(out.toString());
	}

	@Override
	public double partialDeriv(int parameter, double... position) {
		if (parameter < 0 || parameter >= nparams)
			throw new IndexOutOfBoundsException("Parameter index is out of bounds");

		final double pos = position[0];

		final int n = nparams - 1 - parameter;
		switch (n) {
		case 0:
			return 1.0;
		case 1:
			return pos;
		case 2:
			return pos * pos;
		default:
			return Math.pow(pos, n);
		}
	}

	/**
	 * Create a 2D dataset which contains in each row a coordinate raised to n-th powers.
	 * <p>
	 * This is for solving the linear least squares problem 
	 * @param coords
	 * @return matrix
	 */
	public DoubleDataset makeMatrix(AbstractDataset coords) {
		final int rows = coords.getSize();
		DoubleDataset matrix = new DoubleDataset(rows, nparams);

		for (int i = 0; i < rows; i++) {
			final double x = coords.getDouble(i);
			double v = 1.0;
			for (int j = nparams - 1; j >= 0; j--) {
				matrix.setItem(v, i, j);
				v *= x;
			}
		}

		return matrix;
	}
}
