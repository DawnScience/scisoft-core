/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

public class SimpleUncertaintyPropagationMath {
	
	public static void add(double a, double b, double ae, double be, double[] out) {
		out[0] = a + b;
		out[1] = Math.hypot(ae, be);
	}
	
	public static void subtract(double a, double b, double ae, double be, double[] out) {
		out[0] = a - b;
		out[1] = Math.hypot(ae, be);
	}
	
	public static void multiply(double a, double b, double ae, double be, double[] out) {
		out[0] = a * b;
		out[1] = Math.hypot(ae*b, be*a);
	}
	
	public static void multiply(double a, double b, double ae, double[] out) {
		out[0] = a * b;
		out[1] = Math.abs(ae)*b;
	}
	
	public static void divide(double a, double b, double ae, double be, double[] out) {
		out[0] = a/b;
		out[1] = Math.hypot(ae/b, be*a/(b*b));
	}
	
	public static void divide(double a, double b, double ae, double[] out) {
		out[0] = a/b;
		out[1] = Math.abs(ae)/b;
	}
	
	public static void arcsin(double a, double ae, double[] out) {
		out[0] = Math.asin(a);
		out[1] = ae / (Math.sqrt(1 - (a * a)));
	}

}
