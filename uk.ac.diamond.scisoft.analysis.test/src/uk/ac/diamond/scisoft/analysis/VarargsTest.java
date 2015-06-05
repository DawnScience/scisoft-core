/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Check compile-time method resolution is working
 */
public class VarargsTest {

	public String method(final int i) {
		return "Single " + i;
	}

	public String method(final int i, final int j) {
		return "Double " + i + ", " + j;
	}

	public String method(final int... i) {
		return "Multiple " + Arrays.toString(i);
	}

	@Test
	public void testMethods() {
		Assert.assertEquals("Single 1", method(1));
		Assert.assertEquals("Double 1, 2", method(1, 2));
		Assert.assertEquals("Multiple [1, 2, 3]", method(1, 2, 3));
		Assert.assertEquals("Multiple null", method(null));
	}
}
