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

public class ReciprocalCellTest {
	@Test
	public void testUnitCell() {
		Vector3d a = new Vector3d(1, 0, 0);
		Vector3d b = new Vector3d(0, 1, 0);
		Vector3d c = new Vector3d(0, 0, 1);
		UnitCell uc = new UnitCell(1);
		ReciprocalCell rc = new ReciprocalCell(uc);

		Assert.assertEquals(a, rc.getA());
		Assert.assertEquals(b, rc.getB());
		Assert.assertEquals(c, rc.getC());
		Assert.assertEquals(1.0, rc.volume(), 1e-14);
		Assert.assertEquals(1.0, rc.getLengths()[0], 1e-14);
		Assert.assertEquals(1.0, rc.getLengths()[1], 1e-14);
		Assert.assertEquals(1.0, rc.getLengths()[2], 1e-14);
		Assert.assertEquals(90.0, rc.getAngles()[0], 1e-14);
		Assert.assertEquals(90.0, rc.getAngles()[1], 1e-14);
		Assert.assertEquals(90.0, rc.getAngles()[2], 1e-14);

		Vector3d d = new Vector3d(1, Math.sqrt(3), 0);
		d.scale(0.5);
		uc.setB(d);
		rc = new ReciprocalCell(uc);
		Assert.assertEquals(2.0/Math.sqrt(3), rc.volume(), 1e-14);
		Assert.assertEquals(2.0/Math.sqrt(3), rc.getLengths()[0], 1e-14);
		Assert.assertEquals(2.0/Math.sqrt(3), rc.getLengths()[1], 1e-14);
		Assert.assertEquals(1.0, rc.getLengths()[2], 1e-14);
		Assert.assertEquals(90.0, rc.getAngles()[0], 1e-14);
		Assert.assertEquals(90.0, rc.getAngles()[1], 1e-14);
		Assert.assertEquals(120.0, rc.getAngles()[2], 1e-7);
	}
}
