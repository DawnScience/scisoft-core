/*-
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.utils;

import org.junit.Test;

import org.junit.Assert;

public class SimpleUncertaintlyPropagationMathsTest {

	
	private static final double x1 = 14.4;
	private static final double xe1 = 0.3;
	private static final double x2 = 9.3;
	private static final double xe2 = 0.2;
	private static final double x3 = 0.146;
	private static final double xe3 = 0.0124;
	
	
	@Test
	public void addTest() {
		
		double[] out = new double[2];
		
		SimpleUncertaintyPropagationMath.add(x1, x2, xe1, xe2, out);
		
		Assert.assertEquals(x1+x2, out[0], 1e-7);
		Assert.assertEquals(Math.hypot(xe1, xe2), out[1], 1e-7);
		
	}
	
	@Test
	public void subtractTest() {
		
		double[] out = new double[2];
		
		SimpleUncertaintyPropagationMath.subtract(x1, x2, xe1, xe2, out);
		
		Assert.assertEquals(x1-x2, out[0], 1e-7);
		Assert.assertEquals(Math.hypot(xe1, xe2), out[1], 1e-7);

	}
	
	@Test
	public void multiplyTest() {

		double[] out = new double[2];
		
		SimpleUncertaintyPropagationMath.multiply(x1, x2, xe1, xe2, out);
		
		Assert.assertEquals(x1*x2, out[0], 1e-7);
		Assert.assertEquals(Math.abs(out[0])*Math.hypot(xe1/x1, xe2/x2), out[1], 1e-7);
		
	}
	
	@Test
	public void multiplyConstantTest() {
		
		double[] out = new double[2];
		
		SimpleUncertaintyPropagationMath.multiply(x1, x2, xe1, out);
		
		Assert.assertEquals(x1*x2, out[0], 1e-7);
		Assert.assertEquals(xe1*x2, out[1], 1e-7);
	}
	
	@Test
	public void divideTest() {

		double[] out = new double[2];
		
		SimpleUncertaintyPropagationMath.divide(x1, x2, xe1, xe2, out);
		
		Assert.assertEquals(x1/x2, out[0], 1e-7);
		Assert.assertEquals(Math.abs(out[0])*Math.hypot(xe1/x1, xe2/x2), out[1], 1e-7);
		
	}
	
	@Test
	public void divideConstantTest() {

		double[] out = new double[2];
		
		SimpleUncertaintyPropagationMath.divide(x1, x2, xe1, out);
		
		Assert.assertEquals(x1/x2, out[0], 1e-7);
		Assert.assertEquals(xe1/Math.abs(x2), out[1], 1e-7);
		
	}
	
	@Test 
	public void arcsinTest() {
	
		double[] out = new double[2];
	
		SimpleUncertaintyPropagationMath.arcsin(x3, xe3, out);
		Assert.assertEquals(Math.asin(x3), out[0], 1e-7);
		Assert.assertEquals(0.012534310273722801, out[1], 1e-7);
	}	
}
