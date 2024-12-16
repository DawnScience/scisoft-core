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

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.MatrixUtils;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.crystallography.ReciprocalCell.Ortho_Convention;

public class ReciprocalCellTest {

	private final static double TOLERANCE = 1e-14;
	private final static double WIDE_TOLERANCE = 1e-5;

	@Test
	public void testUnitCell() {
		Vector3d a = new Vector3d(1, 0, 0);
		Vector3d b = new Vector3d(0, 1, 0);
		Vector3d c = new Vector3d(0, 0, 1);
		UnitCell uc = new UnitCell(1);
		ReciprocalCell rc = new ReciprocalCell(uc);

		assertEquals(a, rc.getA());
		assertEquals(b, rc.getB());
		assertEquals(c, rc.getC());
		assertEquals(1.0, rc.volume(), TOLERANCE);
		assertEquals(1.0, rc.getLength(0), TOLERANCE);
		assertEquals(1.0, rc.getLength(1), TOLERANCE);
		assertEquals(1.0, rc.getLength(2), TOLERANCE);
		assertEquals(90.0, rc.getAngle(0), TOLERANCE);
		assertEquals(90.0, rc.getAngle(1), TOLERANCE);
		assertEquals(90.0, rc.getAngle(2), TOLERANCE);

		Vector3d d = new Vector3d(1, Math.sqrt(3), 0);
		d.scale(0.5);
		uc.setB(d);
		rc = new ReciprocalCell(uc);
		assertEquals(2.0/Math.sqrt(3), rc.volume(), TOLERANCE);
		assertEquals(2.0/Math.sqrt(3), rc.getLengths()[0], TOLERANCE);
		assertEquals(2.0/Math.sqrt(3), rc.getLengths()[1], TOLERANCE);
		assertEquals(1.0, rc.getLength(2), TOLERANCE);
		assertEquals(90.0, rc.getAngle(0), TOLERANCE);
		assertEquals(90.0, rc.getAngle(1), TOLERANCE);
		assertEquals(120.0, rc.getAngle(2), 1e-7);
	}

	@Test
	public void testHexagonalUnitCell() {
		UnitCell uc = new UnitCell(2, 2, 4.5, 90, 90, 120);

		ReciprocalCell rc = new ReciprocalCell(uc);
		double oovol = 1./(4.5*Math.sqrt(3)*2);
		Vector3d a = new Vector3d(2, 0, 0);
		double cosAng = Math.cos(Math.toRadians(30));
		Vector3d b = new Vector3d(-1, 2*cosAng, 0);
		Vector3d c = new Vector3d(0, 0, 4.5);
		Vector3d cross = new Vector3d();
		cross.cross(b, c);
		cross.scale(oovol);
		assertTrue(cross.epsilonEquals(rc.getA(), TOLERANCE));
		cross.cross(c, a);
		cross.scale(oovol);
		assertTrue(cross.epsilonEquals(rc.getB(), TOLERANCE));
		cross.cross(a, b);
		cross.scale(oovol);
		assertTrue(cross.epsilonEquals(rc.getC(), TOLERANCE));

		assertEquals(oovol, rc.volume(), TOLERANCE);
		assertEquals(90.0, rc.getAngle(0), TOLERANCE);
		assertEquals(90.0, rc.getAngle(1), TOLERANCE);
		assertEquals(60.0, rc.getAngle(2), 1e4*TOLERANCE);
	}

	/*
Using cctbx
from cctbx import uctbx
xtal_params = {'cubic':(1.54, 1.54, 1.54, 90, 90, 90),
'orthorhombic':(1.41421, 1.41421, 1.00000, 90, 90, 90),
'triclinic':(7.51, 7.73, 7.00, 106.0, 113.5, 99.5),
'monoclinic':(4.229, 6.931, 7.862, 90, 99.61, 90),
'hexagonal':(2.8836000, 2.8836000, 7.0633000,90, 90, 120),
}
import math
two_pi = math.pi*2
for n, p in xtal_params.items():
    uc = uctbx.unit_cell(p)
    print(n, [two_pi*f for f in uc.fractionalization_matrix()])
cubic
 [4.079990459207523, -2.4982736282101165e-16, -2.4982736282101165e-16,
 0.0, 4.079990459207523, -6.5611250679162e-16,
 0.0, 0.0, 4.079990459207523]
orthorhombic
 [4.442894129711702, -2.720488037451001e-16, -2.720488037451e-16,
 0.0, 4.442894129711702, -7.1447186801047565e-16,
 0.0, 0.0, 6.283185307179586]
triclinic
 [0.8366425176004776, 0.14000594176371142, 0.4499278072919872,
 0.0, 0.8241337151260324, 0.33597762068467213,
 0.0, 0.0, 1.0569882410717926]
monoclinic
 [1.4857378357010134, -9.097520424316813e-17, 0.25156048134672043,
 0.0, 0.9065337335419977, -1.4578174296048114e-16,
 0.0, 0.0, 0.8105587281236747]
hexagonal
 [2.178937892627128, 1.2580103788557475, -3.3572496416395595e-16,
 0.0, 2.516020757711496, -4.04606996753243e-16,
 0.0, 0.0, 0.8895537931532834]
	 */
	@Test
	public void testCubicCell() {
		UnitCell uc = new UnitCell(1.54, 1.54, 1.54, 90, 90, 90);
		double[] om = {4.07999, 0, 0, 0, 4.07999, 0, 0, 0, 4.07999};
		checkOrthogonalization(uc, Ortho_Convention.DEFAULT, om);
		checkOrthogonalization(uc, Ortho_Convention.BUSING_LEVY, om);
		checkOrthogonalization(uc, Ortho_Convention.PDB, om);
	}


	@Test
	public void testOrthorhombicCell() {
		UnitCell uc = new UnitCell(1.41421, 1.41421, 1.00000, 90, 90, 90);
		double[] om = {4.44288, 0, 0, 0, 4.44288, 0, 0, 0, 6.28319};
		checkOrthogonalization(uc, Ortho_Convention.DEFAULT, om);
		checkOrthogonalization(uc, Ortho_Convention.BUSING_LEVY, om);
		checkOrthogonalization(uc, Ortho_Convention.PDB, om);
	}

	@Test
	public void testHexagonalCell() {
		UnitCell uc = new UnitCell(2.8836000, 2.8836000, 7.0633000, 90, 90, 120);

		checkOrthogonalization(uc, Ortho_Convention.DEFAULT, 2.178937892627128, 0, 0,
				1.2580103788557475, 2.516020757711496, 0, 0, 0, 0.8895537931532834);
		checkOrthogonalization(uc, Ortho_Convention.BUSING_LEVY, 2.516020757711496, 1.2580103788557475, 0,
				0, 2.178937892627128, 0, 0, 0, 0.8895537931532834);
		checkOrthogonalization(uc, Ortho_Convention.PDB, 2.178937892627128, 1.2580103788557475, 0,
				0, 2.516020757711496, 0, 0, 0, 0.8895537931532834);
	}

	@Test
	public void testTriclinicCell() {
		UnitCell uc = new UnitCell(7.51, 7.73, 7.00, 106.0, 113.5, 99.5); // Dalyite
		checkOrthogonalization(uc, Ortho_Convention.BUSING_LEVY, 0.96021, 0.27759, 0.49527, 0, 0.84559, 0.25738, 0, 0, 0.89760);
		checkOrthogonalization(uc, Ortho_Convention.PDB, 0.8366425176004776, 0.14000594176371142, 0.4499278072919872,
				0, 0.8241337151260324, 0.33597762068467213, 0, 0, 1.0569882410717926);
	}

	@Test
	public void testMonoclinicCell() {
		UnitCell uc = new UnitCell(4.229, 6.931, 7.862, 90, 99.61, 90); // Acanthite
		checkOrthogonalization(uc, Ortho_Convention.BUSING_LEVY, 1.50688, 0, 0.13532, 0, 0.90653, 0, 0, 0, 0.79918);
		checkOrthogonalization(uc, Ortho_Convention.PDB, 1.4857378357010134, 0, 0.25156048134672043,
				0.0, 0.9065337335419977, 0, -0, 0, 0.8105587281236747);
	}

	void checkOrthogonalization(UnitCell uc, Ortho_Convention conv, double... expected) {
		ReciprocalCell rc = new ReciprocalCell(uc, conv);
		Matrix3d b = rc.orthogonalization();
		b.mul(2*Math.PI); // extra 2*pi factor as DiffCalc does this
		Matrix3d e = new Matrix3d(expected);
		MatrixUtils.isClose(e, b, WIDE_TOLERANCE, WIDE_TOLERANCE);
	}
}
