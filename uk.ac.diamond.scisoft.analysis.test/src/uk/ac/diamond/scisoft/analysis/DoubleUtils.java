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
