/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

public class DoubleUtils {

	/**
	 * Test if two numbers are equal.
	 * @param foo
	 * @param bar
	 * @param tolerance
	 * @return true if foo equals bar within tolerance
	 */
	public static boolean equalsWithinTolerance(Number foo, Number bar, Number tolerance) {
		final double a = foo.doubleValue();
		final double b = bar.doubleValue();
		final double t = tolerance.doubleValue();	
		return t>=Math.abs(a-b);
	}
	
	/**
	 * Test if two numbers are equal within an absolute or relative tolerance whichever is larger.
	 * The relative tolerance is given by a percentage and calculated from the absolute maximum of the input numbers.
	 * @param foo
	 * @param bar
	 * @param tolerance
	 * @param percentage
	 * @return true if foo equals bar within tolerance
	 */
	public static boolean equalsWithinTolerances(Number foo, Number bar, Number tolerance, Number percentage) {
		final double a = foo.doubleValue();
		final double b = bar.doubleValue();
		final double t = tolerance.doubleValue();
		final double p = percentage.doubleValue();

		double r = p * Math.max(Math.abs(a), Math.abs(b)) / 100.; // relative tolerance
		if (r > t)
			return r >= Math.abs(a - b);
		return t >= Math.abs(a - b);
	}

	public static void main(String[] args) {
		System.out.println(DoubleUtils.equalsWithinTolerance(10,11,2));
		System.out.println(DoubleUtils.equalsWithinTolerance(10,11,1));
		System.out.println(DoubleUtils.equalsWithinTolerance(10,10.9,1));
		System.out.println(DoubleUtils.equalsWithinTolerance(10.99,10.98,0.02));
		System.out.println(DoubleUtils.equalsWithinTolerance(10.99,10.97,0.02));
		System.out.println(DoubleUtils.equalsWithinTolerance(10.99,10.96,0.02));

		System.out.println(DoubleUtils.equalsWithinTolerances(10.99, 10.96, 0.02, 2.));
		System.out.println(DoubleUtils.equalsWithinTolerances(10.99, 10.96, 0.02, 0.1));

	}
}
