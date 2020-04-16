/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.ddogleg.solver.PolynomialOps;
import org.ddogleg.solver.PolynomialRoots;
import org.ddogleg.solver.RootFinderType;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.Maths;
import org.ejml.data.Complex64F;

/**
 * Class that wrappers the equation <br>
 * y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n
 */
public class Polynomial extends AFunction {
	private static final String NAME = "Polynomial";
	private static final String DESC = "A polynomial of degree n."
			+ "\n    y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n";
	private transient double[] a;
	private transient int nparams; // actually degree + 1

	/**
	 * Basic constructor, not advisable to use
	 */
	public Polynomial() {
		this(0);
	}

	/**
	 * Make a polynomial of given degree (0 - constant, 1 - linear, 2 - quadratic, etc)
	 * 
	 * @param degree
	 */
	public Polynomial(final int degree) {
		super(degree + 1);
	}

	/**
	 * Make a polynomial with given parameters
	 * 
	 * @param params
	 */
	public Polynomial(double[] params) {
		super(params);
	}

	/**
	 * Make a polynomial with given parameters
	 * 
	 * @param params
	 */
	public Polynomial(IParameter... params) {
		super(params);
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
			throw new IllegalArgumentException("Bound arrays must be of equal length");
		}
		nparams = min.length;
		parameters = new Parameter[nparams];
		a = new double[nparams];

		for (int i = 0; i < nparams; i++) {
			a[i] = 0.5 * (min[i] + max[i]);
			parameters[i] = new Parameter(a[i], min[i], max[i]);
		}

		setNames();
	}

	@Override
	protected void setNames() {
		if (isDirty() && nparams < getNoOfParameters()) {
			nparams = getNoOfParameters();
		}
		String[] paramNames = new String[nparams];
		for (int i = 0; i < nparams; i++) {
			paramNames[i] = "a_" + i;
		}

		setNames(NAME, DESC, paramNames);
	}

	private void calcCachedParameters() {
		if (a == null || a.length != nparams) {
			a = new double[nparams];
		}
		for (int i = 0; i < nparams; i++) {
			a[i] = getParameterValue(i);
		}

		setDirty(false);
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			calcCachedParameters();
		}
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

		it.reset();
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
		Dataset pos = DatasetUtils.convertToDataset(it.getValues()[0]);

		final int n = nparams - 1 - indexOfParameter(parameter);
		switch (n) {
		case 0:
			data.fill(1);
			break;
		case 1:
			data.setSlice(pos);
			break;
		case 2:
			Maths.square(pos, data);
			break;
		default:
			Maths.power(pos, n, data);
			break;
		}
	}

	/**
	 * Create a 2D dataset which contains in each row a coordinate raised to n-th powers.
	 * <p>
	 * This is for solving the linear least squares problem
	 * 
	 * @param coords
	 * @return matrix
	 */
	public DoubleDataset makeMatrix(Dataset coords) {
		final int rows = coords.getSize();
		DoubleDataset matrix = DatasetFactory.zeros(DoubleDataset.class, rows, nparams);

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
	 * 
	 * @param degree
	 */
	public void setDegree(int degree) {
		nparams = degree + 1;
		parameters = createParameters(nparams);
		dirty = true;

		setNames();

		if (parent != null) {
			parent.updateParameters();
		}
	}

	public String getStringEquation() {

		StringBuilder out = new StringBuilder();

		DecimalFormat df = new DecimalFormat("0.#####E0");

		for (int i = nparams - 1; i >= 2; i--) {
			out.append(df.format(parameters[nparams - 1 - i].getValue()));
			out.append(String.format("x^%d + ", i));
		}

		if (nparams >= 2)
			out.append(df.format(parameters[nparams - 2].getValue()) + "x + ");
		if (nparams >= 1)
			out.append(df.format(parameters[nparams - 1].getValue()));

		return out.toString();
	}

	/**
	 * Find all roots
	 * 
	 * @return all roots or null if there is any problem finding the roots
	 */
	public Complex[] findRoots() {
		if (isDirty()) {
			calcCachedParameters();
		}
		return findRoots(a);
	}

	/**
	 * Find all roots
	 * 
	 * @param coeffs
	 * @return all roots or null if there is any problem finding the roots
	 */
	public static Complex[] findRoots(double... coeffs) {
		double[] reverse = coeffs.clone();
		ArrayUtils.reverse(reverse);
		double max = Double.NEGATIVE_INFINITY;
		for (double r : reverse) {
			max = Math.max(max, Math.abs(r));
		}
		for (int i = 0; i < reverse.length; i++) {
			reverse[i] /= max;
		}

		org.ddogleg.solver.Polynomial p = org.ddogleg.solver.Polynomial.wrap(reverse);
		PolynomialRoots rf = PolynomialOps.createRootFinder(p.computeDegree(), RootFinderType.EVD);
		if (rf.process(p)) {
			// reorder to NumPy's roots output
			List<Complex64F> rts = rf.getRoots();
			Complex[] out = new Complex[rts.size()];
			int i = 0;
			for (Complex64F r : rts) {
				out[i++] = new Complex(r.getReal(), r.getImaginary());
			}
			return sort(out);
		}

		return null;
	}

	private static Complex[] sort(Complex[] values) {
		// reorder to NumPy's roots output
		List<Complex> rts = Arrays.asList(values);
		Collections.sort(rts, new Comparator<Complex>() {
			@Override
			public int compare(Complex o1, Complex o2) {
				double a = o1.getReal();
				double b = o2.getReal();

				double u = 10 * Math.ulp(Math.max(Math.abs(a), Math.abs(b)));
				if (Math.abs(a - b) > u)
					return a < b ? -1 : 1;

				a = o1.getImaginary();
				b = o2.getImaginary();
				if (a == b)
					return 0;
				return a < b ? 1 : -1;
			}
		});

		return rts.toArray(new Complex[0]);
	}
}
