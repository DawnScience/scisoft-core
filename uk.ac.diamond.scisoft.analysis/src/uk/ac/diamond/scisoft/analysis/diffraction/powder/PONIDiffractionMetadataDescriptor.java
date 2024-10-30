/*-
 * Copyright 2020 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.text.DecimalFormat;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import org.eclipse.dawnsci.analysis.api.diffraction.DetectorProperties;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;

/**
 * Point of Normal Incidence (PONI) file constructor. The specification for the
 * PONI file can be found
 * at @link{https://pyfai.readthedocs.io/en/stable/geometry_conversion.html}
 * 
 * an example PONI file can be found at
 * {@link https://www.silx.org/pub/pyFAI/cookbook/calibration/LaB6_29.4keV.poni}
 * 
 * Important things to note: all distances are specified in units of meters [m]
 * (including wavelength), and all angles are specified in radians.
 * 
 * This class is only intended to use properties derived from the standard
 * detector properties object (and hence cannot be used to create a new detector
 * description)
 * 
 * X1, X2 are up and right when looking down the beam from sample to detector and
 * the image origin is bottom left
 */
public class PONIDiffractionMetadataDescriptor {
	private String detectorName = "Detector";
	private double pixelHeight = 1;
	private double pixelWidth = 1;
	private int detectorRows = 0;
	private int detectorColumns = 0;
	private double[] poni;
	private double distance;
	private double[] angles;
	private double wavelength;
	private static final String LINESEP = System.getProperty("line.separator");
	private static final String HEADER = "#Calibration coverted from DAWN calibration" + LINESEP
			+ "#Note: C-Order, 1 refers to the Y axis, 2 to the X axis" + LINESEP + "poni_version: 2.1" + LINESEP;

	private Matrix3d orientation;

	/**
	 * Transform from coordinates in DLS frame to coordinates in PONI frame
	 */
	public static final Matrix3d DLS2PONI = new Matrix3d(0, 1, 0, -1, 0, 0, 0, 0, 1);

	private static final double MILLIM = 1e-3;


	/**
	 * create a Point of Normal Incidence (PONI) description of the diffraction
	 * geometry from an existing description.
	 * 
	 * @see org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata
	 * 
	 * @param meta object containing all the experimental geometry and
	 * wavelength settings from which the PONI will be constructed
	 */
	public PONIDiffractionMetadataDescriptor(IDiffractionMetadata meta) {
		setFromDiffractionMetadata(meta);
	}

	/**
	 * Construct a Point of Normal Incidence (PONI) description from the
	 * required pyFAI inputs, and a detector properties object (that holds
	 * information for the pixel size and detector size)
	 * 
	 * @param poni1 PONI location in the slow axis (X1) direction
	 * @param poni2 PONI location in the fast axis (X2) direction
	 * @param distance distance [m] from the origin to the PONI.
	 * @param rot1 rot_1 [radians]
	 * @param rot2 rot_2 [radians]
	 * @param rot3 rot_3 [radians]
	 * @param wavelength wavelength of the incident radiation [m]
	 * @param dp
	 * 
	 */
	public PONIDiffractionMetadataDescriptor(double poni1, double poni2, double distance, double rot1, double rot2,
			double rot3, double wavelength, DetectorProperties dp) {
		this.distance = distance;
		this.wavelength = wavelength;
		poni = new double[] { poni1, poni2 };
		angles = new double[] { rot1, rot2, rot3 };
		pixelHeight = dp.getVPxSize() * MILLIM;
		pixelWidth = dp.getHPxSize() * MILLIM;
		detectorRows = dp.getPy();
		detectorColumns = dp.getPx();
		setOrientation(angles);
	}

	/**
	 * Construct a Point of Normal Incidence (PONI) description from the
	 * required pyFAI inputs, and a detector properties object (that holds
	 * information for the pixel size and detector size)
	 * 
	 * @param poni1 PONI location in the slow axis (X1) direction
	 * @param poni2 PONI location in the fast axis (X2) direction
	 * @param distance distance [m] from the origin to the PONI.
	 * @param rot1 rot_1 [radians]
	 * @param rot2 rot_2 [radians]
	 * @param rot3 rot_3 [radians]
	 * @param wavelength wavelength of the incident radiation [m]
	 * @param pixelHeight height [m] (extent in X1) of a single pixel
	 * @param pixelWidth width [m] (extent in X2) of a single pixel
	 * @param detectorRows number of rows of pixels in detector (or equally
	 * number of pixels in slow X1 axis)
	 * @param detectorColumns number of columns of pixels in detector (or
	 * equally number of pixels in fast X2 axis)
	 */
	public PONIDiffractionMetadataDescriptor(double poni1, double poni2, double distance, double rot1, double rot2,
			double rot3, double wavelength, double pixelHeight, double pixelWidth, int detectorRows,
			int detectorColumns) {
		this.poni = new double[] { poni1, poni2 };
		this.distance = distance;
		this.angles = new double[] { rot1, rot2, rot3 };
		this.wavelength = wavelength;
		this.pixelHeight = pixelHeight;
		this.pixelWidth = pixelWidth;
		this.detectorRows = detectorRows;
		this.detectorColumns = detectorColumns;
		setOrientation(angles);
	}

	/**
	 * 
	 * @return The Point of Normal Incidence (PONI) defined in the pyFAI
	 * detector coordinate system in units of [m].
	 */
	public double[] getPoni() {
		return this.poni;
	}

	/**
	 * Detector distance in pyFAI is defined as the distance, specified in [m],
	 * at which a ray from the origin, moving in the direction of the normal to
	 * the detector plane, will intersect the detector plane.
	 * 
	 * @return The distance as defined by pyFAI of the detector in [m]
	 */
	public double getDistance() {
		return this.distance;
	}

	/**
	 * get the rotation angles in [radians] that specify the detectors
	 * orientation in the pyFAI reference frame.
	 * 
	 * For orientation specification
	 * see @link{https://www.silx.org/doc/pyFAI/latest/geometry_conversion.html}.
	 * 
	 * @return array containing the three angles
	 */
	public double[] getRotations() {
		return this.angles;
	}

	/**
	 * 
	 * @return the wavelength, units of [m], of the incident radiation.
	 */
	public double getWavelength() {
		return this.wavelength;
	}

	private void setFromDiffractionMetadata(IDiffractionMetadata meta) {
		DetectorProperties dp = meta.getDetector2DProperties();
		this.wavelength = meta.getDiffractionCrystalEnvironment().getWavelength() * 1e-10;
		pixelHeight = dp.getVPxSize() * MILLIM;
		pixelWidth = dp.getHPxSize() * MILLIM;
		detectorRows = dp.getPy();
		detectorColumns = dp.getPx();
		Vector3d cp = dp.getClosestPoint();
		double[] coords = dp.pixelPreciseCoords(cp);
		poni = new double[] {(dp.getPy() - coords[1]) * pixelHeight, coords[0] * pixelWidth};
		distance = cp.length() * MILLIM;

		Matrix3d invOrientation = new Matrix3d(dp.getOrientation());
		invOrientation.transpose();
		santise(invOrientation);
		angles = calculateEulerPONI(invOrientation);
		orientation = createOrientationFromEulerPyFAI(angles);
	}

	private static void santise(Matrix3d m) {
		double scale = m.getScale();
		double min = Math.ulp(scale);
		for (int i = 0; i < 3; i++) {
			double t;
			t = Math.abs(m.getElement(i, 0));
			if (t > 0 && t <= min) {
				m.setElement(i, 0, 0);
			}
			t = Math.abs(m.getElement(i, 1));
			if (t > 0 && t <= min) {
				m.setElement(i, 1, 0);
			}
			t = Math.abs(m.getElement(i, 2));
			if (t > 0 && t <= min) {
				m.setElement(i, 2, 0);
			}
		}
	}

	/**
	 * 
	 * @return String representation of the PONI file.
	 */
	public String getStringDescription() {
		StringBuilder outBuilder = new StringBuilder(HEADER);
		DecimalFormat decimal = new DecimalFormat("0.#######E00");
		outBuilder.append(String.format("Detector: %s%n", detectorName));
		outBuilder.append(String.format("Detector_config: {\"pixel1\": %s, \"pixel2\": %s, \"max_shape\": [%d, %d], \"orientation\": 2}%n",
				decimal.format(pixelHeight), decimal.format(pixelWidth), detectorRows, detectorColumns));
		// this might need an empty spline_file entry
		outBuilder.append(String.format("Distance: %s%n", distance));
		outBuilder.append(String.format("Poni1: %s%nPoni2: %s%n", decimal.format(poni[0]), decimal.format(poni[1])));
		outBuilder.append(String.format("Rot1: %s%nRot2: %s%nRot3: %s%n", decimal.format(angles[0]),
				decimal.format(angles[1]), decimal.format(angles[2])));
		outBuilder.append(String.format("Wavelength: %s%n", decimal.format(wavelength)));
		return outBuilder.toString();
	}

	/**
	 * Get the position of a point specified in [m] in the detector frame as a
	 * point in the pyFAI laboratory reference frame.
	 * 
	 * @param detectorPosition position in the pyFAI detector frame
	 * 
	 * @return vector to the point in the pyFAI lab reference system
	 */
	public Vector3d getLabPositionFromDetectorPosition(Vector3d detectorPosition) {
		Vector3d offsetArray = new Vector3d(-poni[0], -poni[1], distance);
		Vector3d out = new Vector3d(detectorPosition);
		out.add(offsetArray);
		orientation.transform(out);
		return out;
	}

	/**
	 * get position in terms of horizontal and vertical pixel coordinates in the
	 * pyFAI lab space
	 * 
	 * @param horiz horizontal coordinate (fast-axis position). Units [pixels]
	 * @param vert vertical coordinate (slow-axis position). Units [pixels]
	 * @return pixel position in [m] defined in the pyFAI lab coordinate space
	 */
	public Vector3d getPixelPosition(double horiz, double vert) {
		// convert from fast-slow to pyFAI detector reference frame
		Vector3d pixelVector = new Vector3d(vert * pixelHeight, horiz * pixelWidth, 0);

		return getLabPositionFromDetectorPosition(pixelVector);
	}

	/**
	 * In PyFAI lab frame (+x upwards, +z along beam)
	 *
	 * @param angles (in radians)
	 * @return active orientation transformation
	 */
	public static Matrix3d createOrientationFromEulerPyFAI(double... angles) {
		Matrix3d m = new Matrix3d();
		Matrix3d mPF = new Matrix3d();
		// Z(r3) Y(-r2) X(-r1)
		mPF.rotZ(angles[2]);
		m.rotY(-angles[1]);
		mPF.mul(m);
		m.rotX(-angles[0]);
		mPF.mul(m);
		return mPF;
	}

	/**
	 * Set orientation by using three PONI angles
	 * 
	 * @param angles
	 */
	public void setOrientation(double... angles) {
		orientation = createOrientationFromEulerPyFAI(angles);
	}

	/**
	 * Calculate Euler angles
	 * @param m active transformation for PyFAI frame
	 * @return angles in radians
	 */
	public static double[] calculateEulerPyFAI(Matrix3d m) {
		// from Z(r3) Y(-r2) X(-r1) in PONI frame
		double r1;
		double r3;
		double s2 = m.getM20();

		if (Math.abs(s2) < 1) {
			r1 = Math.atan2(-m.getM21(), m.getM22());
			r3 = Math.atan2(m.getM10(), m.getM00());
		} else {
			// degenerate when s2=+/- 1, so choose r3 = 0
			r1 = Math.atan2(m.getM12(), -s2*m.getM02());
			r3 = 0;
		}
		return new double[] { r1, Math.asin(s2), r3 };
	}

	/**
	 * In DLS lab frame (+y upwards, +z along beam)
	 * 
	 * @param angles
	 * @return active orientation transformation
	 */
	public static Matrix3d createOrientationFromEulerPONI(double... angles) {
		Matrix3d m = new Matrix3d();
		Matrix3d mP = new Matrix3d();
		// Z(r3) X(r2) Y(-r1)
		mP.rotZ(angles[2]);
		m.rotX(angles[1]);
		mP.mul(m);
		m.rotY(-angles[0]);
		mP.mul(m);
		return mP;
	}

	/**
	 * Calculate Euler angles
	 * @param m active transformation for DLS frame
	 * @return angles in radians
	 */
	public static double[] calculateEulerPONI(Matrix3d m) {
		// from Z(r3) X(r2) Y(-r1) in DLS frame
		double r1;
		double r3;
		double s2 = m.getM21();

		if (Math.abs(s2) < 1) {
			r1 = Math.atan2(m.getM20(), m.getM22());
			r3 = Math.atan2(-m.getM01(), m.getM11());
		} else {
			// degenerate when s2=+/- 1, so choose r3 = 0
			r1 = Math.atan2(-m.getM02(), m.getM00());
			r3 = 0;
		}

		return new double[] { r1, Math.asin(s2), r3 };
	}
}
