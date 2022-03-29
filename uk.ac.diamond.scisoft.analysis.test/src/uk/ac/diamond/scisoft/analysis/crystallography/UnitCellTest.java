/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.vecmath.Vector3d;

import org.junit.Test;

public class UnitCellTest {

	private final static double TOLERANCE = 1e-14;

	@Test
	public void testUnitCell() {
		Vector3d a = new Vector3d(1, 0, 0);
		Vector3d b = new Vector3d(0, 1, 0);
		Vector3d c = new Vector3d(0, 0, 1);
		UnitCell uc = new UnitCell(1);

		assertEquals(a, uc.getA());
		assertEquals(b, uc.getB());
		assertEquals(c, uc.getC());
		assertEquals(1.0, uc.volume(), TOLERANCE);
		assertEquals(1.0, uc.getLength(0), TOLERANCE);
		assertEquals(1.0, uc.getLength(1), TOLERANCE);
		assertEquals(1.0, uc.getLength(2), TOLERANCE);
		assertEquals(90.0, uc.getAngle(0), TOLERANCE);
		assertEquals(90.0, uc.getAngle(1), TOLERANCE);
		assertEquals(90.0, uc.getAngle(2), TOLERANCE);

		Vector3d d = new Vector3d(1, Math.sqrt(3), 0);
		uc.setB(d);
		assertEquals(d, uc.getB());
		assertEquals(Math.sqrt(3), uc.volume(), TOLERANCE);
		assertEquals(1.0, uc.getLength(0), TOLERANCE);
		assertEquals(2.0, uc.getLength(1), TOLERANCE);
		assertEquals(1.0, uc.getLength(2), TOLERANCE);
		assertEquals(90.0, uc.getAngle(0), TOLERANCE);
		assertEquals(90.0, uc.getAngle(1), TOLERANCE);
		assertEquals(60.0, uc.getAngle(2), TOLERANCE);
	}

	@Test
	public void testHexagonalUnitCell() {
		UnitCell uc = new UnitCell(new double[] {2, 2, 4.5}, new double[] {90, 90, 120});
		Vector3d a = new Vector3d(2, 0, 0);
		double cosAng = Math.cos(Math.toRadians(30));
		Vector3d b = new Vector3d(-1, 2*cosAng, 0);
		Vector3d c = new Vector3d(0, 0, 4.5);

		assertTrue(a.epsilonEquals(uc.getA(), TOLERANCE));
		assertTrue(b.epsilonEquals(uc.getB(), TOLERANCE));
		assertTrue(c.epsilonEquals(uc.getC(), TOLERANCE));
		double vol = 4.5*Math.sqrt(3)*2;
		Vector3d bc = new Vector3d();
		bc.cross(b, c);
		assertEquals(a.dot(bc), uc.volume(), TOLERANCE);
		assertEquals(vol, uc.volume(), TOLERANCE);
		assertEquals(2.0, uc.getLength(0), TOLERANCE);
		assertEquals(2.0, uc.getLength(1), TOLERANCE);
		assertEquals(4.5, uc.getLength(2), TOLERANCE);
		assertEquals(90.0, uc.getAngle(0), TOLERANCE);
		assertEquals(90.0, uc.getAngle(1), TOLERANCE);
		assertEquals(120.0, uc.getAngle(2), TOLERANCE);
	}
}
