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
 * Basically an implementation of a simple cubic spline calculator
 */
public class CubicSpline extends AFunction {
	private static String cname = "CubicSpline";

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
		name = cname;
	}
	
	public CubicSpline(IParameter... params) {
		super(params);
		name = cname;
	}

	/**
	 * Constructor for use with Global Optimisers
	 * @param xpoints The x positions for the control points
	 * @param ystartpoints the start y positions for the control points
	 * @param deviation the amount the optimiser can go from the specified y value
	 */
	public CubicSpline(double[] xpoints, double[] ystartpoints, double deviation) {
		super(ystartpoints);
		for(int i = 0; i < parameters.length; i++) {
			parameters[i].setLimits(parameters[i].getValue()-deviation, parameters[i].getValue()+deviation);
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
	
	
	protected boolean changed() {
		if(y == null) {
			return true;
		}
		
		//check the values to see if they have changed 
		for(int i = 0; i < y.length; i++) {
			if(parameters[i].getValue() != y[i]) {
				return true;
			}
		}
		return false;
	}
	
	protected double[] getParameterDoubleList() {
		double[] result = new double[parameters.length];
		for(int i = 0; i < parameters.length; i++) {
			result[i] = parameters[i].getValue();
		}
		return result;
	}
	
	@Override
	public double val(double... values) {
		
		if(changed()) {
			// build the spline
			generateSpline(x, getParameterDoubleList());
		}
		return evaluateSpline(values[0]);		
	}

}
