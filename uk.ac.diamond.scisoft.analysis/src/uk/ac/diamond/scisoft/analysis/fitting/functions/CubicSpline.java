/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;

/**
 * Basically an implementation of a simple cubic spline calculator
 */
public class CubicSpline extends AFunction {
	private static String NAME = "CubicSpline";

	double[] a = null;
	double[] b = null;
	double[] c = null;
	double[] d = null;

	double[] x = null;
	double[] y = null;

	public CubicSpline() {
		this(4);
	}

	public CubicSpline(int numberOfParameters) {
		super(numberOfParameters);
		name = NAME;
	}

	public CubicSpline(IParameter... params) {
		super(params);
		name = NAME;
	}

	/**
	 * Constructor for use with Global Optimisers
	 * @param xpoints The x positions for the control points
	 * @param ystartpoints the start y positions for the control points
	 * @param deviation the amount the optimiser can go from the specified y value
	 */
	public CubicSpline(double[] xpoints, double[] ystartpoints, double deviation) {
		super(ystartpoints);
		name = NAME;
		for(int i = 0; i < parameters.length; i++) {
			IParameter p = parameters[i];
			p.setLimits(p.getValue() - deviation, p.getValue() + deviation);
		}
		x = xpoints;
	}

	/**
	 * Constructor for normal use
	 * @param xpoints The x positions for the control points
	 * @param ystartpoints the start y positions for the control points
	 */
	public CubicSpline(double[] xpoints, double[] ystartpoints) {
		super(ystartpoints);
		name = NAME;
		x = xpoints;
	}

	protected void generateSpline(double[] newx, double[] newy) throws IllegalArgumentException {
		x = newx;
		y = newy;

		int n = 0;
		if (x.length == y.length) {
			n = x.length-1;
		} else {
			throw new IllegalArgumentException("x and y arrays are not the same length");
		}

		double[] h = new double[n];
		double[] bb = new double[n];

		for (int i = 0; i < n; i++) {
			h[i] = x[i+1]-x[i];
			bb[i] = (y[i+1]-y[i])/h[i];
		}

		double[] u = new double[n];
		double[] v = new double[n];

		u[1] = 2.0*(h[0]+h[1]);
		v[1] = 6.0*(bb[1]-bb[0]);

		for (int i = 2; i < n; i++) {
			u[i] = 2.0*(h[i-1]+h[i]) - (h[i-1]*h[i-1])/u[i-1];
			v[i] = 6.0*(bb[i]-bb[i-1]) - (h[i-1]*v[i-1])/u[i-1];
		}
		
		double[] z = new double[n+1];
		z[n] = 0;
		for(int i = n-1; i > 0; i--) {
			z[i] = (v[i] - h[i]*z[i+1])/u[i];
		}

		// now calculate the parameters
		a = new double[n];
		b = new double[n];
		c = new double[n];
		d = new double[n];

		for (int i = 0; i < n; i++) {
			a[i] = y[i];
			b[i] = (-h[i]/6.0)*z[i+1] - (h[i]/3.0)*z[i] + (y[i+1]-y[i])/h[i];
			c[i] = z[i]/2.0;
			d[i] = (z[i+1]-z[i])/(6.0*h[i]);
		}

		setDirty(false);
	}

	protected int getRegion(double xvalue) {
		// get the region
		for (int i = 0; i < x.length-1; i++) {
			if (xvalue < x[i+1]) {
				return i;
			}
		}
		return x.length-2;
	}

	protected double evaluateSpline(double xvalue) {
		int i = getRegion(xvalue);

		double dx = (xvalue - x[i]);
		double result = a[i] + dx*(b[i]+(dx*(c[i]+(dx*d[i]))));

		return result;
	}

	@Override
	public boolean isDirty() {
		if (super.isDirty())
			return true;

		//check the values to see if they have changed 
		for (int i = 0; i < y.length; i++) {
			if (parameters[i].getValue() != y[i]) {
				setDirty(true);
				return true;
			}
		}
		return false;
	}

	@Override
	public double val(double... values) {
		if (isDirty()) {
			// build the spline
			generateSpline(x, getParameterValues());
		}

		return evaluateSpline(values[0]);
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		if (isDirty()) {
			// build the spline
			generateSpline(x, getParameterValues());
		}

		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();
		while (it.hasNext()) {
			buffer[i++] = evaluateSpline(coords[0]);
		}
	}
}
