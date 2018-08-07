/*
 * Copyright (c) 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.xpdf.xrmc;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;

public class XRMCDetector extends XRMCFile {

	Matrix4d transformToLab;

	static final double POSITION_TO_PIXELSIZE = 1000.0;
	static final double PIXELSIZE_SCALE = 1e-6; // Pixel size in µm
	static final double XRMC_SCALE = 1e-2; // XRMC scales values in cm
	static final double POSITION_SCALE = 1e-3; // Position is in mm
	static final double METRE_SCALE = 1.0; // scale of metres
	
	static final long serialVersionUID = 0x4880c2e482c5f8bdL;
	
	/**
	 * Creates a new Detector class, based on the given file
	 * @param fileName
	 * 				file path at which the source file can be found
	 */
	public XRMCDetector(String fileName) {
		super(fileName);
		transformToLab = null;
	}
	
	public XRMCDetector(String[] fileText) {
		super(fileText);
		transformToLab = null;
	}
	
	public XRMCDetector(XRMCDetector src) {
		super(src);
		transformToLab = null;
	}
	
	/**
	 * Checks that what the XRMCDatReader has ingested is an XRMC detector file.
	 * @return true if the structure in the reader matches an XRMC detector file
	 */
	public boolean isDetectorFile() {
		return isValidFile();
	}

	@Override
	protected String getDeviceName() {
		return "detectorarray";
	}
	
	/**
	 * Returns the number of pixels in each dimension of the detector.
	 * @return the integral number of pixels in the detector array.
	 */
	public int[] getNPixels() {
		String pixelString = getValue("NPixels");
		String[] tokens = pixelString.split("\\s+");
		int[] nxny = new int[2];
		nxny[0] = Integer.parseInt(tokens[0]);
		nxny[1] = Integer.parseInt(tokens[1]);

		return nxny;
	}
	
	/**
	 * Returns the size of the pixel in each dimension in μm.
	 * @return The size of the detector pixels in μm.
	 */
	public double[] getPixelSize() {
		// an extra multiplication by 10 000 to go from cm to μm
		return getParseAndScaleValues("PixelSize", XRMC_SCALE/PIXELSIZE_SCALE);
	}
	
	/**
	 * Returns the centre of the detector, positioned in mm
	 * @return coordinates of the screen in mm, relative to the origin. 
	 */
	public double[] getDetectorPosition() {
		return getParseAndScaleValues("X", XRMC_SCALE/POSITION_SCALE);
	}

	public double[] getDetectorNormal() {
		double[] uk = getAndParseValues("uk");
		normalize(uk);
		return uk;
	}
	
	public double[] getDetectorXVector() {
		double[] ui = getAndParseValues("ui");
		normalize(ui);
		return ui;
	}
	
	public int getNBins() {
		// Assume there are not more than 2^53 energies
		return (int) getAndParseValues("NBins")[0];
	}
	
	public double getEmin() {
		return Double.parseDouble(getValue("Emin"));
	}
	
	public double getEmax() {
		return Double.parseDouble(getValue("Emax"));
	}

	/**
	 * Returns the solid angle subtended by the detector.
	 * @return the solid angle subtended by the detector (steradians).
	 */
	public double getSolidAngle() {
		// Generate the corner vectors for the detector
		//   Generate the basis vectors for the screen in the lab coordinate system
		Maths3d xScreen = new Maths3d(this.getDetectorXVector());
		Maths3d zScreen = new Maths3d(this.getDetectorNormal());
		Maths3d yScreen = zScreen.cross(xScreen);
		
		// screen centre in mm
		Maths3d screenCentre = new Maths3d(getDetectorPosition());
		// in metres
		screenCentre = screenCentre.times(METRE_SCALE/POSITION_SCALE);

		// half lengths of the screen in metres
		double xHalfLength = this.getNPixels()[0] * this.getPixelSize()[0] / (METRE_SCALE/PIXELSIZE_SCALE) / 2;
		double yHalfLength = this.getNPixels()[1] * this.getPixelSize()[1] / (METRE_SCALE/PIXELSIZE_SCALE) / 2;
		
		Maths3d mm = screenCentre.minus(xScreen.times(xHalfLength)).minus(yScreen.times(yHalfLength));
		Maths3d pm = screenCentre.plus(xScreen.times(xHalfLength)).minus(yScreen.times(yHalfLength));
		Maths3d mp = screenCentre.minus(xScreen.times(xHalfLength)).plus(yScreen.times(yHalfLength));
		Maths3d pp = screenCentre.plus(xScreen.times(xHalfLength)).plus(yScreen.times(yHalfLength));
		
		return
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, pp.value, mp.value) +
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, mp.value, mm.value) +
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, mm.value, pm.value) +
				DetectorProperties.calculatePlaneTriangleSolidAngle(screenCentre.value, pm.value, pp.value);
	}
	
	/**
	 * Returns the euler angles of the detector relative to the beam.
	 * @return Euler angles in radians ordered as pitch, yaw, roll.
	 */
	public double[] getEulerAngles() {
		Maths3d xScreen = new Maths3d(this.getDetectorXVector());
		Maths3d zScreen = new Maths3d(this.getDetectorNormal());
		Maths3d yScreen = zScreen.cross(xScreen);

		// Use elements of the full rotation matrix to get the
		// trigonometric functions of the Euler angles.
		
		double sinYaw = zScreen.get().x;
		double yaw = Math.asin(sinYaw);
		
		double pitch = Math.atan2(-zScreen.get().y, zScreen.get().z);
		// When the screen is exactly face on, the pitch will here be 
		// π/2, because of the definitions of the screen and lab
		// coordinate systems.  
		pitch -= Math.PI/2;
		
		double roll = Math.atan2(-yScreen.get().x, xScreen.get().x);
		
		return new double[] {pitch, yaw, roll};
	}
	
	/*
	 * Coordinate frames
	 * 
	 * XRMC lab frame.
	 * A three-dimensional, right-handed orthogonal Cartesian coordinate system
	 * The origin is centred on the beam-sample interaction region
	 * x is horizontal, perpendicular to the beam
	 * y is parallel to the beam, +ve downstream
	 * z is vertically upwards
	 * 
	 * Detector frame
	 * A two-dimensional Cartesian coordinate system
	 * The origin is the top left of the detector (facing in the side of the detector that receives the radiation)
	 * x is horizontal
	 * y is vertical
	 */
	
	/**
	 * Returns the XRMC lab coordinates of a given point on the detector.
	 * <p>
	 * Returns the position (in metres) in the XRMC lab coordinate frame. The 
	 * input is a position on the detector in units of pixels. The origin is 
	 * the top left of the top left pixel. The centre of the top left pixel is 
	 * (0.5, 0.5).
	 */
	public Vector3d labFromPixel(Vector2d x) {
		Vector2d d = new Vector2d(getPixelSize());
		d.scale(PIXELSIZE_SCALE/METRE_SCALE);
		Vector4d h = new Vector4d(d.x*x.x, -d.y*x.y, 0.0, 1.0);
		getTransform().transform(h);
		return new Vector3d(h.x, h.y, h.z);
	}
	
	/**
	 * Returns the scattering angles (γ,δ) for a given pixel
	 * @param x
	 * 			the location on the screen in units of pixels, with the origin
	 * 			at the top left of the top left pixel.
	 * @param beamUi
	 * 				unit vector of the perpendicular reference direction for
	 * 				the beam. Often the direction of strongest polarization.
	 * @param beamUk
	 * 				unit vector in the direction of propagation of the beam
	 * @return (γ,δ) scattering angles in radians.
	 */
	public Vector2d anglesFromPixel(Vector2d x, Vector3d beamUi, Vector3d beamUk) {
		return anglesFromPixel(x, beamUi, beamUk, false);
	}

	/**
	 * Returns the polar scattering angles (φ,2θ) for a given pixel.
	 * @param x
	 * 			the location on the screen in units of pixels, with the origin
	 * 			at the top left of the top left pixel.
	 * @param beamUi
	 * 				unit vector of the perpendicular reference direction for
	 * 				the beam. Often the direction of strongest polarization.
	 * @param beamUk
	 * 				unit vector in the direction of propagation of the beam
	 * @return (φ,2θ) scattering angles in radians.
	 */
	public Vector2d polarAnglesFromPixel(Vector2d x, Vector3d beamUi, Vector3d beamUk) {
		return anglesFromPixel(x, beamUi, beamUk, true);
	}
	
	/**
	 * Returns the polar (φ,2θ) or Cartesian (γ,δ) scattering angles for a
	 * given pixel.
	 * @param x
	 * 			the location on the screen in units of pixels, with the origin
	 * 			at the top left of the top left pixel.
	 * @param beamUi
	 * 				unit vector of the perpendicular reference direction for
	 * 				the beam. Often the direction of strongest polarization.
	 * @param beamUk
	 * 				unit vector in the direction of propagation of the beam
	 * @param polar
	 * 				if true, return the polar scattering angles (φ,2θ), else 
	 * 				return the Cartesian scattering angles (γ,δ)
	 * @return
	 */
	public Vector2d anglesFromPixel(Vector2d x, Vector3d beamUi, Vector3d beamUk, boolean polar) {
		// Vector from the origin to the pixel
		Vector3d k = labFromPixel(x);
		// components of k relative to the beam coordinates
		double ki = beamUi.dot(k);
		double kj = new Maths3d(beamUk).cross(beamUi).dot(k);
		double kk = beamUk.dot(k);
		
		Vector3d kDash = new Maths3d(beamUi).times(ki).plus(new Maths3d(beamUk).times(kk)).get();
		
		
		Vector2d result = null;
		if (polar) {
			double phi = Math.atan2(kj, ki);
			double tth = Math.acos(kk/kDash.length());
			result = new Vector2d(phi, tth);
		} else {
			double gamma = Math.atan2(ki, kk);
			double delta = Math.asin(kj/k.length());
			result = new Vector2d(gamma, delta);
		}
		return result;
	}

	/**
	 * Returns the detector coordinates of a given point in the lab frame.
	 * <p>
	 * The point in the lab frame is transformed into the detectors xyz frame,
	 * where x & y are the fast and slow pixel directions, and z is in the
	 * direction of the detector *anti*-normal. The point is then projected
	 * into the detector plane by zeroing the z component. The output value is
	 * in fractional pixels, with the origin being the top left of the top left
	 * pixel.  
	 */
	public Vector2d pixelFromLab(Vector3d x) {
		Vector4d h = new Vector4d(x.x, x.y, x.z, 1.0);
		Matrix4d inverseTransform = new Matrix4d();
		inverseTransform.invert(getTransform());
		inverseTransform.transform(h);
		Vector2d d = new Vector2d(getPixelSize());
		d.scale(PIXELSIZE_SCALE/METRE_SCALE);
		return new Vector2d(h.x/d.x, h.y/d.y);
	}

	/**
	 * Sets the transformation matrix from the detector coordinates to the XRMC lab coordinates x_lab = M x_det
	 */
	private Matrix4d getTransform() {
		if (transformToLab == null) {
			transformToLab = createTransformToLab();
		}		
		return transformToLab;
	}
	
	private Matrix4d createTransformToLab( ) {

		// Get the data to construct the transformation matrix in metres (where units are used)
		Vector3d centreOffset  = new Vector3d(getDetectorPosition());
		centreOffset.scale(POSITION_SCALE/METRE_SCALE);
		// Detector x (row) direction
		Vector3d e1 = new Vector3d(getDetectorXVector());
		// Detector z (normal) direction
		Vector3d e3 = new Vector3d(getDetectorNormal());
		
		// Rotation from detector coordinates to lab coordinates
		e1.normalize();
		e3.normalize();
		Vector3d e2 = new Vector3d();
		e2.cross(e3, e1); // This is antiparallel to the along-column vector

		// ROtation only part of the transformation
		Matrix3d rotateDetectorToAligned = new Matrix3d();
		rotateDetectorToAligned.setColumn(0, e1);
		rotateDetectorToAligned.setColumn(1, e2);
		rotateDetectorToAligned.setColumn(2, e3);
		
		// Transformation from the centred detector coordinates to the lab coordinates
		Vector3d moveCentreToLab = new Vector3d(getDetectorPosition());
		moveCentreToLab.scale(POSITION_SCALE/METRE_SCALE);
		Matrix4d transformCentreToLab = new Matrix4d(rotateDetectorToAligned, moveCentreToLab, 1.0);
		
		// Transform a position in metres (not pixels) from the top left origin
		// of the detector into the lab frame 
		int[] nPx = getNPixels();
		double[] szPx = getPixelSize();
		// Note that the detector right-handed coordinate system 
		Vector3d originToCentre = new Vector3d(nPx[0]*szPx[0]/2/(METRE_SCALE/PIXELSIZE_SCALE), -nPx[1]*szPx[1]/2/(METRE_SCALE/PIXELSIZE_SCALE), 0.);

		rotateDetectorToAligned.transform(originToCentre);

		Matrix3d id3d = new Matrix3d();
		id3d.setIdentity();
		//negate originToCentre to get the correct transform direction
		originToCentre.negate();
		Matrix4d originToCentre4d = new Matrix4d(id3d, originToCentre, 1.0);
		
		Matrix4d transformToLabLocal = new Matrix4d();
		transformToLabLocal.mul(originToCentre4d, transformCentreToLab);

		return transformToLabLocal;

	}
	
	// Normalize a double array in-place
	private void normalize(double[] x) {
		double squareSum = 0.;
		for (double xi : x)
			squareSum += xi*xi;
		
		double normer = 1./Math.sqrt(squareSum);
		for (int i = 0; i < x.length; i++)
			x[i] *= normer;
	}
}
