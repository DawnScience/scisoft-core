/*-
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

import java.text.DecimalFormat;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractCompoundDataset;
import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;

/**
 * Class that wrappers the equation <br>
 * y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n
 */
public class Polynomial extends AFunction {
	private static final String NAME = "Polynomial";
	private static final String DESC = "y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n";
	double[] a;
	private String[] paramNames;
	int nparams; // actually degree + 1

	/**
	 * Basic constructor, not advisable to use
	 */
	public Polynomial() {
		super(1);
		nparams = 1;
		a = new double[nparams];
		fillParameters(a);

		setNames();
	}

	/**
	 * Make a polynomial of given degree (0 - constant, 1 - linear, 2 - quadratic, etc)
	 * @param degree
	 */
	public Polynomial(final int degree) {
		super(degree + 1);
		nparams = degree + 1;
		a = new double[nparams];
		fillParameters(a);

		setNames();
	}

	/**
	 * Make a polynomial with given parameters
	 * @param params
	 */
	public Polynomial(double[] params) {
		super(params);
		nparams = params.length;
		a = new double[nparams];
		fillParameters(a);

		setNames();
	}

	/**
	 * Make a polynomial with given parameters
	 * @param params
	 */
	public Polynomial(IParameter... params) {
		super(params);
		nparams = params.length;
		a = new double[nparams];
		fillParameters(a);

		setNames();
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
		fillParameters(a);

		setNames();
	}

	private void setNames() {
		name = NAME;
		description = DESC;
		for (int i = 0; i < paramNames.length; i++) {
			IParameter p = getParameter(i);
			p.setName(paramNames[i]);
		}
	}

	@Override
	protected void fillParameters(double... params) {
		super.fillParameters(params);
		paramNames = new String[nparams];
		for (int i = 0; i < paramNames.length; i++) {
			paramNames[i] = "Parameter" + i;
		}
	}

	private void calcCachedParameters() {
		for (int i = 0; i < nparams; i++)
			a[i] = getParameterValue(i);

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty())
			calcCachedParameters();

		final double position = values[0];

		double v = a[0];
		for (int i = 1; i < nparams; i++) {
			v = v * position + a[i];
		}
		return v;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty())
			calcCachedParameters();

		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			double v = a[0];
			double p = coords[0];
			for (int j = 1; j < nparams; j++) {
				v = v * p + a[j];
			}
			buffer[i++] = v;
		}
	}

	@Override
	public double partialDeriv(IParameter parameter, double... position) {
		if (isDuplicated(parameter))
			return super.partialDeriv(parameter, position);

		int i = indexOfParameter(parameter);
		if (i < 0)
			return 0;

		final double pos = position[0];
		final int n = nparams - 1 - i;
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


	@Override
	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
		if (isDuplicated(parameter)) {
			super.fillWithPartialDerivativeValues(parameter, data, it);
			return;
		}

		int i = indexOfParameter(parameter);
		if (i < 0)
			return;

		AbstractDataset pos = DatasetUtils.convertToAbstractDataset(it.getValues()[0]);
		if (pos instanceof AbstractCompoundDataset) {
			pos = ((AbstractCompoundDataset) pos).asNonCompoundDataset();
		}

		final int n = nparams - 1 - i;

		switch (n) {
		case 0:
			data.fill(1);
			break;
		case 1:
			data.fill(pos);
			break;
		case 2:
			data.fill(Maths.square(pos));
			break;
		default:
			data.fill(Maths.power(pos, n));
			break;
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

	/**
	 * Set the degree after a class instantiation
	 * @param degree
	 */
	public void setDegree(int degree) {
		parameters = new Parameter[degree + 1];
		for (int i = 0; i < degree + 1; i++) {
			parameters[i] = new Parameter();
		}
		a = new double[degree + 1];
		nparams = degree + 1;
		name = NAME;
		description = DESC;
		fillParameters(a);
	}
	
	public String getStringEquation(){
		
		StringBuilder out = new StringBuilder();
		
		DecimalFormat df = new DecimalFormat("0.#####E0");
		
		for (int i = nparams-1; i >= 2; i--) {
			out.append(df.format(parameters[nparams - 1 -i].getValue()));
			out.append(String.format("x^%d + ", i));
		}
		
		if (nparams >= 2)
			out.append(df.format(parameters[nparams-2].getValue()) + "x + ");
		if (nparams >= 1)
			out.append(df.format(parameters[nparams-1].getValue()));
		
		return out.toString();
	}
}
