/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.test.operations.twod;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.diffraction.DiffractionCrystalEnvironment;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.junit.Test;

import uk.ac.diamond.scisoft.analysis.diffraction.powder.PONIDiffractionMetadataDescriptor;
import uk.ac.diamond.scisoft.analysis.io.DiffractionMetadata;

public class DiffractionMetadataConversionTest {

	private IDiffractionMetadata meta;
	double yawDegrees;

	public DiffractionMetadataConversionTest() {
		setYawedMetadata();
	}

	@Test
	public void PONIExportConversionLabPixelPositionAssignmentTest() {
		PONIDiffractionMetadataDescriptor poni = new PONIDiffractionMetadataDescriptor(meta);
		// for yawed metadata the origin in pyFAI should be
		double yawRad = Math.toRadians(yawDegrees);
		Vector3d pyFAIOrigin = new Vector3d(-50., -50 * Math.cos(yawRad), 300 + (50 * Math.sin(yawRad)));
		pyFAIOrigin.scale(1e-3); // convert to metres
		Vector3d actualOrigin = poni.getPixelPosition(0, 0);

		double prec = Math.ulp(actualOrigin.length());
		assertTrue("Error in expected origin", pyFAIOrigin.epsilonEquals(actualOrigin, prec));
	}

	@Test
	public void PONIExportConversionLabDetectorPositionAssignmentTest() {
		PONIDiffractionMetadataDescriptor poni = new PONIDiffractionMetadataDescriptor(meta);
		Vector3d beamCentrePyFAI = new Vector3d(0., 0., 300);
		beamCentrePyFAI.scale(1e-3); // convert to metres
		Vector3d detectorCoordinates = new Vector3d(50., 50., 0.);
		detectorCoordinates.scale(1e-3);// convert to m
		Vector3d beamCentre = poni.getLabPositionFromDetectorPosition(detectorCoordinates);
		double prec = Math.ulp(beamCentre.length());
		assertTrue("Error in expected beam centre", beamCentre.epsilonEquals(beamCentrePyFAI, prec));
	}

	@Test
	public void PONIExportConversionTest() {
		PONIDiffractionMetadataDescriptor poni = new PONIDiffractionMetadataDescriptor(meta);
		assertArrayEquals("Error in expected assigned PONI values",
				new double[] { 50. * 1e-3, (300. / Math.sqrt(2) + 50) * 1e-3 }, poni.getPoni(), 1e-6);

		double[] angles = poni.getRotations();
		assertEquals("Error in expected rot1", yawDegrees, Math.toDegrees(angles[0]), 1e-5);
		assertEquals("Error in expected rot2", 0, angles[1], 1e-5);
		assertEquals("Error in expected rot3", 0, angles[2], 1e-5);

		assertEquals("error in assinging wavelength", meta.getDiffractionCrystalEnvironment().getWavelength(),
				poni.getWavelength() * 1e10, 1e-4);
	}

	private void setYawedMetadata() {
		double pXSize = 0.1;
		int[] detectorSize = new int[] { 1000, 1000 };

		double[] beamCentre = new double[] { 500., 500. };
		Vector3d origin = new Vector3d(0., 0., 300.);
		Matrix3d ori = new Matrix3d();
		ori.setIdentity();

		DetectorProperties dp = new DetectorProperties(origin, detectorSize[0], detectorSize[1], pXSize, pXSize, ori);
		yawDegrees = 45.;
		dp.setNormalAnglesInDegrees(yawDegrees, 0., 0.);
		dp.setBeamCentreCoords(beamCentre);
		// now detector is tilted to (1, 0, -1) and its origin at (50/sqrt(2), 50, 300+50/sqrt(2))
		// closest point aka PONI is at (150, 0, 150)
		double xo = beamCentre[0] * pXSize / Math.sqrt(2);
		assertTrue(new Vector3d(xo, beamCentre[1] * pXSize, origin.z + xo).epsilonEquals(dp.getOrigin(), 1e-10));

		meta = new DiffractionMetadata("", dp, new DiffractionCrystalEnvironment(0.444));
	}

}
