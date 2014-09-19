/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.vecmath.Vector3d;

import org.junit.Assert;
import org.junit.Test;

public class LatticeCellTest {

	@Test
	public void testCell() {
		Vector3d a = new Vector3d(1, 0, 0);
		Vector3d b = new Vector3d(0, 1, 0);
		Vector3d c = new Vector3d(0, 0, 1);
		LatticeCell lc = new LatticeCell(a, b, c);

		Assert.assertEquals(a, lc.getA());
		Assert.assertEquals(b, lc.getB());
		Assert.assertEquals(c, lc.getC());
		Assert.assertEquals(1.0, lc.volume(), 1e-14);

		Vector3d d = new Vector3d(1, 2, 0);
		lc.setB(d);
		Assert.assertEquals(d, lc.getB());
		Assert.assertEquals(2.0, lc.volume(), 1e-14);
	}
}
