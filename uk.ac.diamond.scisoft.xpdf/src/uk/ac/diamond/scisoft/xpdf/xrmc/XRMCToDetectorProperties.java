/*-
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.xrmc;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;

public class XRMCToDetectorProperties {

	// Empty ctor
	private XRMCToDetectorProperties() {
		
	}
	
	public static DetectorProperties get(XRMCDetector det, XRMCSource src) {
		Vector3d originXRMC = det.labFromPixel(new Vector2d(0, 0)); // top left of the top left pixel: DetectorProperties origin, in XRMC lab frame
		//		Vector3d originDP = new Vector3d(-originXRMC.x, originXRMC.z, originXRMC.y); // origin, Detector Properties frame
		Vector3d eulerXYZ = new Vector3d(det.getEulerAngles());

		// Get the image size of the xrmc data
		int[] shape = det.getNPixels();
		int nx = shape[0], ny = shape[1];
		Vector2d pixelSpacing = new Vector2d(det.getPixelSize());
		pixelSpacing.scale(1e-3);

		Vector3d beamVector = new Vector3d(src.getUK());
		Vector3d beamUi = new Vector3d(src.getUI());
		Vector3d detectorUi = new Vector3d(det.getDetectorXVector());
		Vector3d detectorUk = new Vector3d(det.getDetectorNormal());
		
		return calculateDetectorProperties(nx, ny, originXRMC, beamVector, eulerXYZ, pixelSpacing, detectorUi, detectorUk, beamUi);
	}

	/**
	 * Creates a new @link{DetectorProperties} object based on the passed properties.
	 * <p>
	 * A static utility method.
	 * @param nx
	 * 			Number of pixels in the x direction
	 * @param ny
	 * 			Number of pixels in the y direction
	 * @param origin
	 * 				Origin vector of the detector, relative to the sample
	 * @param beamVector
	 * 					Direction vector of the beam
	 * @param eulerAngles
	 * 					Euler angles of the detector, relative to the aligned detector orientation
	 * @param pixelSpacing
	 * 					Two element @link{Dataset} containing the pixel distances in x, y, measured in mm
	 * @param detectorUi
	 * 					Along-row direction unit vector
	 * @param detectorUk
	 * 					Detector normal, in the detecting direction
	 * @param beamUi
	 * 				Reference direction orthogonal to the beam wavevector. Direction of polarization, for example.
	 * @return the newly constructed @link{DetectorProperties} object
	 */
	public static DetectorProperties calculateDetectorProperties(int nx, int ny, Vector3d origin, Vector3d beamVector, Vector3d eulerAngles, Vector2d pixelSpacing, Vector3d detectorUi, Vector3d detectorUk, Vector3d beamUi) {
		// Assume orthogonal, normalized ui, uk
		Vector3d detectorUj = new Vector3d();
		detectorUj.cross(detectorUk, detectorUi);
		Vector3d row0 = new Vector3d(detectorUi);
		row0.negate();
		Vector3d row1 = new Vector3d(detectorUj);
		Vector3d row2 = new Vector3d(detectorUk);
		row2.negate();
		
		Matrix3d alignment = new Matrix3d();
		alignment.setRow(0, row0);
		alignment.setRow(1, row1);
		alignment.setRow(2, row2);

		// But detector properties assumes a beam along +ve z, which may not be true. Create the matrix to transform to this coordinate system
		Vector3d beamUk = beamVector;
		row0 = beamUi;
		row1 = new Vector3d();
		row1.cross(beamUk, beamUi);
		row2 = beamUk;
		
		// This is the matrix to transform from detProp
		Matrix3d toDetProp = new Matrix3d();
		toDetProp.setRow(0, row0);
		toDetProp.setRow(1, row1);
		toDetProp.setRow(2, row2);
		
		Vector3d originDP = new Vector3d(origin);
		originDP.scale(1000.); //convert from metres to millimetres 
		
		Matrix3d alignmentDP = new Matrix3d(alignment);
		alignmentDP.mul(toDetProp);
		
		return new DetectorProperties(originDP, beamVector, ny, nx, pixelSpacing.y, pixelSpacing.x, alignment);

	}
}
