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

public class UnitCellTest {
	@Test
	public void testUnitCell() {
		Vector3d a = new Vector3d(1, 0, 0);
		Vector3d b = new Vector3d(0, 1, 0);
		Vector3d c = new Vector3d(0, 0, 1);
		UnitCell uc = new UnitCell(1);

		Assert.assertEquals(a, uc.getA());
		Assert.assertEquals(b, uc.getB());
		Assert.assertEquals(c, uc.getC());
		Assert.assertEquals(1.0, uc.volume(), 1e-14);
		Assert.assertEquals(1.0, uc.getLengths()[0], 1e-14);
		Assert.assertEquals(1.0, uc.getLengths()[1], 1e-14);
		Assert.assertEquals(1.0, uc.getLengths()[2], 1e-14);
		Assert.assertEquals(90.0, uc.getAngles()[0], 1e-14);
		Assert.assertEquals(90.0, uc.getAngles()[1], 1e-14);
		Assert.assertEquals(90.0, uc.getAngles()[2], 1e-14);

		Vector3d d = new Vector3d(1, Math.sqrt(3), 0);
		uc.setB(d);
		Assert.assertEquals(d, uc.getB());
		Assert.assertEquals(Math.sqrt(3), uc.volume(), 1e-14);
		Assert.assertEquals(1.0, uc.getLengths()[0], 1e-14);
		Assert.assertEquals(2.0, uc.getLengths()[1], 1e-14);
		Assert.assertEquals(1.0, uc.getLengths()[2], 1e-14);
		Assert.assertEquals(90.0, uc.getAngles()[0], 1e-14);
		Assert.assertEquals(90.0, uc.getAngles()[1], 1e-14);
		Assert.assertEquals(60.0, uc.getAngles()[2], 1e-14);

	}
}
