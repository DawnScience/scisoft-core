/*-
 * Copyright © 2010 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.DetectorProperties;
import uk.ac.diamond.scisoft.analysis.diffraction.DiffractionCrystalEnvironment;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.gda.util.number.DoubleUtils;

public class QSpaceTest {
	static double wavelength = 1.4;
	static int[] ishape = new int[] {800, 400};
	static double[] pxdim = new double[] { 0.2, 0.3 };
	static double[] origin = new double[] { 80, 60, 234 };
	private static DetectorProperties detprops;
	private static DiffractionCrystalEnvironment diffexp;
	private static QSpace qspace;

	@BeforeClass
	public static void setUp() {
		Matrix3d orientn = new Matrix3d();
		orientn.setIdentity();
		detprops = new DetectorProperties(new Vector3d(origin), ishape[1], ishape[0],
				pxdim[1], pxdim[0], orientn);
		diffexp = new DiffractionCrystalEnvironment(wavelength);
		qspace = new QSpace(detprops, diffexp);
	}

	@Test
	public void testQShape() {
		Vector3d qa = qspace.getInitialWavevector();
		Assert.assertTrue("q " + qa.length() + " not equal " + (2*Math.PI/wavelength),
				DoubleUtils.equalsWithinTolerance(qa.length(), 2*Math.PI/wavelength, 1e-4));

		// fake a pixel coordinate so 2*sin(theta) = 1 or theta = 30 degrees
		// i.e. equilateral triangle
		int[] bp = detprops.pixelCoords(detprops.getBeamPosition());
//		System.out.println("Beam on detector is " + Arrays.toString(bp));
		double projlen = Math.tan(Math.toRadians(60.))*origin[2];
//		System.out.println("coords are " + (projlen/pxdim[0]) + ", 0");
//		System.out.println("coords are 0, " + (projlen/pxdim[1]));
		qa = qspace.qFromPixelPosition(bp[0] + projlen/pxdim[0], bp[1]);
		Assert.assertTrue("q " + qa.length() + " not equal " + (2*Math.PI/wavelength),
				DoubleUtils.equalsWithinTolerance(qa.length(), 2*Math.PI/wavelength, 1e-4));
		
		qa = qspace.qFromPixelPosition(bp[0], bp[1]+projlen/pxdim[1]);
		Assert.assertTrue("q " + qa.length() + " not equal " + (2*Math.PI/wavelength),
				DoubleUtils.equalsWithinTolerance(qa.length(), 2*Math.PI/wavelength, 1e-4));
	}

	@Test
	public void testPixelPositions() {
		double ttheta = Math.toRadians(11.5);
		double rho = origin[2]*Math.tan(ttheta);
		
		double dphi = 45.;

		for (double phi = 0; phi < 360.; phi += dphi) {
			int x = (int) Math.floor((origin[0] + rho * Math.cos(Math.toRadians(phi))) / pxdim[0]);
			int y = (int) Math.floor((origin[1] + rho * Math.sin(Math.toRadians(phi))) / pxdim[1]);

			Vector3d qa = qspace.qFromPixelPosition(x, y);
			Vector3d ta = new Vector3d();
			qspace.pixelPosition(qa, ta);
//			System.out.println("At " + x + "," + y + ", q: " + qa + "; value = " + (phi+30) + "; at " + ta.x + "," + ta.y);
			Assert.assertEquals("x is wrong", x, ta.x, 1e-6);
			Assert.assertEquals("y is wrong", y, ta.y, 1e-6);
		}
	}

	@Test
	public void testScatteringAngles() {
		double ttheta = Math.toRadians(11.5);
		double rho = origin[2]*Math.tan(ttheta);
		
		double dphi = 45.;

		for (double phi = 0; phi < 360.; phi += dphi) {
			int x = (int) Math.floor((origin[0] + rho * Math.cos(Math.toRadians(phi))) / pxdim[0]);
			int y = (int) Math.floor((origin[1] + rho * Math.sin(Math.toRadians(phi))) / pxdim[1]);

			Vector3d qa = qspace.qFromPixelPosition(x, y);
			double pixdelta = Math.max(pxdim[0], pxdim[1])/origin[2]; // maximum angle subtended by a single pixel
			Assert.assertEquals(ttheta, qspace.scatteringAngle(qa), pixdelta);
		}
	}
}
