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
 * Point of Normal Incidence (PONI) file constructor. The specification
 * for the PONI file can be found
 * at @link{https://www.silx.org/doc/pyFAI/latest/geometry_conversion.html}
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
 */
public class PONIDiffractionMetadataDescriptor {
	private DetectorProperties dp;
	private String detectorName = "Detector";
	private double[] poni;
	private double distance;
	private double[] angles;
	private double wavelength;
	private static final String LINESEP = System.getProperty("line.separator");
	private static final String HEADER = "#Calibration coverted from DAWN calibration" + LINESEP
			+ "#Note: C-Order, 1 refers to the Y axis, 2 to the X axis" + LINESEP + "poni_version: 2" + LINESEP;

	// matrix to transform a vector in the pyFAI detector reference frame (NOT Lab
	// frame) to DLS detector frame
	private static final Matrix3d fixedDectorReferenceTransform = new Matrix3d(0, -1, 0, -1, 0, 0, 0, 0, 1);

	// matrix to transform a vector in DLS lab reference frame to pyFAI lab
	// reference frame.
	private static final Matrix3d fixedLabReferenceTransform = new Matrix3d(0, 1, 0, -1, 0, 0, 0, 0, 1);
	private Matrix3d invOrientation;

	/**
	 * create a Point of Normal Incidence (PONI) description of the diffraction
	 * geometry from an existing description.
	 * 
	 * @see org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata
	 * 
	 * @param meta
	 *            object containing all the experimental geometry and wavelength
	 *            settings from which the PONI will be constructed
	 */
	public PONIDiffractionMetadataDescriptor(IDiffractionMetadata meta) {
		setFromDiffractionMetadata(meta);
	}

	/**
	 * Construct a Point of Normal Incidence (PONI) description from the required
	 * pyFAI inputs, and a detector properties object (that holds information for
	 * the pixel size and detector size)
	 * 
	 * @param poni1
	 *            PONI location in the slow axis (X1) direction
	 * @param poni2
	 *            PONI location in the fast axis (X2) direction
	 * @param distance
	 *            distance [m] from the origin to the PONI.
	 * @param rot1
	 *            rot_1 [radians]
	 * @param rot2
	 *            rot_2 [radians]
	 * @param rot3
	 *            rot_3 [radians]
	 * @param wavelength
	 *            wavelength of the incident radiation [m]
	 * @param dp
	 * 
	 */
	public PONIDiffractionMetadataDescriptor(double poni1, double poni2, double distance, double rot1, double rot2,
			double rot3, double wavelength, DetectorProperties dp) {
		this.poni = new double[] { poni1, poni2 };
		this.distance = distance;
		this.angles = new double[] { rot1, rot2, rot3 };
		this.wavelength = wavelength;
		this.dp = dp;

	}

	/**
	 * 
	 * @return The Point of Normal Incidence (PONI) defined in the pyFAI detector
	 *         coordinate system in units of [m].
	 */
	public double[] getPoni() {
		return this.poni;
	}

	/**
	 * Detector distance in pyFAI is defined as the distance, specified in [m], at
	 * which a ray from the origin, moving in the direction of the normal to the
	 * detector plane, will intersect the detector plane.
	 * 
	 * @return The distance as defined by pyFAI of the detector in [m]
	 */
	public double getDistance() {
		return this.distance;
	}

	/**
	 * get the rotation angles in [radians] that specify the detectors orientation
	 * in the pyFAI reference frame.
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
		this.dp = meta.getDetector2DProperties();
		this.wavelength = meta.getDiffractionCrystalEnvironment().getWavelength() * 1e-10;

		Matrix3d ori = dp.getOrientation();
		Vector3d normal = new Vector3d(dp.getNormal());
		Vector3d detectorOrigin = dp.pixelPosition(0, 0);

		normal.negate(); // need the outward normal
		double interceptDistance = detectorOrigin.dot(normal);
		normal.scale(interceptDistance);
		Vector3d poniVector = new Vector3d();

		// Calculate the vector pointing from the DLS detector origin to the PONI in the
		// DLS lab system
		// and put the vector into the DLS detector reference frame
		poniVector.sub(normal, detectorOrigin);
		ori.transform(poniVector);

		// The PONI has now been fully described in the standard DLS detector system
		// which
		// has its X1 axis pointing along the descending column direction (within a row,
		// anti-parallel to the fast-axis)
		// and its X2 axis pointing in the direction of descending rows (within a
		// column, anti-parallel to the slow-axis).
		// The PONI standard uses X1 to point in the direction of the slow-axis, and the
		// X2 to point in the fast axis direction.
		// The conversion is encoded in the static field fixedDetectorReferenceTransform
		fixedDectorReferenceTransform.transform(poniVector);
		poniVector.scale(1e-3); // PONI to be assigned in [m]

		this.poni = new double[] { poniVector.getX(), poniVector.getY() };

		// get the passive form of the detectors orientation matrix
		Matrix3d invOriDLS = new Matrix3d();
		invOriDLS.transpose(ori);

		// get the orientation in DLS Lab reference frame
		invOriDLS.mul(fixedDectorReferenceTransform);

		// transform the orientation from the DLS lab system to the pyFAI lab system
		this.invOrientation = new Matrix3d();
		invOrientation.mul(fixedLabReferenceTransform, invOriDLS);

		santise(invOrientation);

		// from invOrientation we determine the required angles
		double sr2 = invOrientation.getM02();
		double cr2 = Math.sqrt(1. - (sr2 * sr2));

		double r1;
		double r2;// these angles correspond to rot1, rot2, rot3 of the pyFAI spec.
		double r3;

		if (cr2 != 0.) {
			r3 = Math.atan2(invOrientation.getM10(), invOrientation.getM00());
			r1 = Math.atan2(-invOrientation.getM21(), invOrientation.getM22());
		} else {
			r3 = 0;
			r1 = Math.atan2(invOrientation.getM01(), invOrientation.getM11());
		}
		r2 = Math.asin(sr2);

		this.angles = new double[] { r1, r2, r3 };
		this.distance = interceptDistance * 1e-3; // convert to [m]

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
		outBuilder.append(String.format("Detector_config: {\"pixel1\": %s, \"pixel2\": %s, \"max_shape\": [%d, %d]}%n",
				decimal.format(dp.getVPxSize() * 1e-3), decimal.format(dp.getHPxSize() * 1e-3), dp.getPy(),
				dp.getPx())); // this might need an empty spline_file entry
		outBuilder.append(String.format("Distance: %s%n", distance));
		outBuilder.append(String.format("Poni1: %s%nPoni2: %s%n", decimal.format(poni[0]), decimal.format(poni[1])));
		outBuilder.append(String.format("Rot1: %s%nRot2: %s%nRot3: %s%n", decimal.format(angles[0]),
				decimal.format(angles[1]), decimal.format(angles[2])));
		outBuilder.append(String.format("Wavelength: %s%n", decimal.format(wavelength)));
		return outBuilder.toString();

	}

	/**
	 * Get the position of a point specified in [m] in the detector frame as a point
	 * in the pyFAI laboratory reference frame.
	 * 
	 * @param detectorPosition
	 *            position in the pyFAI detector frame
	 * 
	 * @return vector to the point in the pyFAI lab reference system
	 */
	public Vector3d getLabPositionFromDetectorPosition(Vector3d detectorPosition) {
		Vector3d offsetArray = new Vector3d(-poni[0], -poni[1], distance);
		Vector3d out = new Vector3d(detectorPosition);
		out.add(offsetArray);
		invOrientation.transform(out);
		return out;

	}

	/**
	 * get position in terms of horizontal and vertical pixel coordinates in the
	 * pyFAI lab space
	 * 
	 * @param horiz
	 *            horizontal coordinate (fast-axis position). Units [pixels]
	 * @param vert
	 *            vertical coordinate (slow-axis position). Units [pixels]
	 * @return pixel position in [m] defined in the pyFAI lab coordinate space
	 */
	public Vector3d getPixelPosition(double horiz, double vert) {
		// convert from fast-slow to pyFAI detector reference frame
		Vector3d pixelVector = new Vector3d(vert * dp.getVPxSize(), horiz * dp.getHPxSize(), 0);

		return getLabPositionFromDetectorPosition(pixelVector);
	}

}
