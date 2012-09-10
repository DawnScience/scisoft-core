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

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;

/**
 * Class that wrappers the equation <br>
 * y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n
 */
public class Polynomial extends AFunction {
	private static String cname = "Polynomial";
	private static String cdescription = "y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n";
	final double[] a;
	private String[] paramNames;
	final int nparams; // actually degree + 1
	/**
	 * Basic constructor, not advisable to use
	 */
	public Polynomial() {
		super(1);
		a = new double[1];
		nparams = 1;
		name = cname;
		description = cdescription;
		fillParameters(a);
		paramNames = new String[nparams];
		for(int i=0; i<paramNames.length; i++){
			paramNames[i] = "Parameter"+i;
			setParameterName(paramNames[i], i);
		}
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
		description = cdescription;
		fillParameters(a);
		paramNames = new String[nparams];
		for(int i=0; i<paramNames.length; i++){
			paramNames[i] = "Parameter"+i;
			setParameterName(paramNames[i], i);
		}
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
		description = cdescription;
		fillParameters(a);
		paramNames = new String[nparams];
		for(int i=0; i<paramNames.length; i++){
			paramNames[i] = "Parameter"+i;
			setParameterName(paramNames[i], i);
		}
	}

	/**
	 * Make a polynomial with given parameters
	 * @param params
	 */
	public Polynomial(IParameter[] params) {
		super(params);
		a = new double[params.length];
		nparams = params.length;
		name = cname;
		description = cdescription;
		fillParameters(a);
		paramNames = new String[nparams];
		for(int i=0; i<paramNames.length; i++){
			paramNames[i] = "Parameter"+i;
			setParameterName(paramNames[i], i);
		}
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
		description = cdescription;
		fillParameters(a);
		paramNames = new String[nparams];
		for(int i=0; i<paramNames.length; i++){
			paramNames[i] = "Parameter"+i;
			setParameterName(paramNames[i], i);
		}
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
	public String toString() {
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < nparams; i++) {
			out.append(String.format("Parameter %d has value %f within the bounds [%f,%f]\n", i, getParameterValue(i), 
					getParameter(i).getLowerLimit(), getParameter(i).getUpperLimit()));
		}
		return out.substring(0, out.length() - 1);
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
