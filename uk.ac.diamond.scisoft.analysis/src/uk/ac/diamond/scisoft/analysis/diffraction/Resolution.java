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

import java.util.ArrayList;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;


/**
 * Utility class to calculate various aspects of resolution from crystallographic images
 */

public class Resolution {

	/**
	 * Takes the position of peaks along a line drawn on an diffraction pattern and returns the distance in real space
	 * between reflections.
	 * 
	 * @param peaks
	 *            array of x,y coordinates of the reflection in pixels from the origin at the top left of the detector.
	 *            There must be an even number of values for each peak
	 * @param detector
	 *            DetectorOrientation object containing position, orientation and properties of the detector
	 * @param diffExp
	 *            DiffractionExperiment object containing information about the diffraction experiment including the
	 *            wavelength
	 * @return array of inter-peak distances (d spacing) in angstroms
	 * @throws Exception
	 */
	public static double[] peakDistances(int[] peaks, DetectorProperties detector, DiffractionCrystalEnvironment diffExp)
			throws Exception {
		if (peaks.length % 2 != 0) {
			throw new IllegalArgumentException("The number of xy values does not match the number of points selected");
		}
		double[] peakDouble = new double[peaks.length];
		for (int i = 0; i < peaks.length; i += 2) {
			peakDouble[i] = peaks[i];
			peakDouble[i + 1] = peaks[i + 1];
		}
		return peakDistances(peakDouble, detector, diffExp);
	}

	/**
	 * @param peaks
	 *            An array of x,y values of peaks on the detector in pixels. There must be an even number of values for
	 *            each peak
	 * @param detector
	 *            DetectorOrientation Object containing position, orientation and properties of the detector
	 * @param diffExp
	 *            DiffractionExperiment object containing information about the diffraction experiment including the
	 *            wavelength
	 * @return array of inter-peak distances (d spacing) in angstroms
	 * @throws Exception
	 *             thrown when an odd number of pixels get passed to method
	 */
	public static double[] peakDistances(double[] peaks, DetectorProperties detector,
			DiffractionCrystalEnvironment diffExp) throws Exception {

		if (peaks.length % 2 != 0) {
			throw new IllegalArgumentException("The number of xy values does not match the number of points selected");
		}

		ArrayList<double[]> pixels = new ArrayList<double[]>();
		double[] temp;
		pixels.add(new double[] { peaks[0], peaks[1] });
		for (int i = 2; i < peaks.length; i += 2) {
			temp = new double[] { peaks[i], peaks[i + 1] };
			boolean unique = true;
			for (double[] px : pixels) {
				if (temp[0] == px[0] && temp[1] == px[1]) {
					unique = false;
					break;
				}
			}
			if (unique) {
				pixels.add(temp);
			}
		}
		int imax = pixels.size() - 1;
		Vector3d q = new Vector3d();
		QSpace qspace = new QSpace(detector, diffExp);
		double[] distances = new double[imax];
		for (int i = 0; i < imax; i++) {
			double[] px1 = pixels.get(i);
			double[] px2 = pixels.get(i + 1);
			q.sub(qspace.qFromPixelPosition(px1[0], px1[1]), qspace.qFromPixelPosition(px2[0], px2[1]));
			distances[i] = 2 * Math.PI / q.length();
		}
		return distances;
	}

	// public static double[] resolutionRingElipse(DetectorOrientation detector, DiffractionCrystalEnvironment diffexp,
	// double angstrom) {
	//
	// Vector3d beamVector = new Vector3d();
	// beamVector = detector.getBeamVector();
	//
	// Vector3d origin = new Vector3d();
	// origin = detector.getOrigin();
	//
	// Vector3d detectorNormal = new Vector3d();
	// detectorNormal = detector.detectorNormal();
	//
	// double Rlength = origin.dot(detectorNormal) / beamVector.dot(detectorNormal);
	// // scale beam vector to detector surface
	// beamVector.scale(Rlength);
	//
	// Vector3d u = new Vector3d();
	// u.cross(detectorNormal, beamVector);
	// Vector3d v = new Vector3d();
	// v.cross(u, beamVector);
	//
	// // angle between beam vector and detector normal
	// double delta = beamVector.angle(beamVector);
	// double theta = math.asin(diffexp.getWavelength() / (2 * angstrom));
	//
	// double sTheta = math.sin(theta);
	// double sDelta = math.sin(delta);
	// double cTheta = math.cos(theta);
	// double cDelta = math.cos(delta);
	//
	// // double t = ((beamVector.length() * sTheta) / sDelta)
	// // * (((sDelta * sTheta) + (cTheta * cDelta)) / (cTheta * cTheta) - (sDelta * sDelta));
	//
	// Vector3d majorSemiAxis = new Vector3d(v);
	// double a = beamVector.length() * ((cDelta * sTheta * cTheta) / ((cTheta * cTheta) - (sDelta * sDelta)));
	// majorSemiAxis.scale(a);
	//
	// Vector3d minorSemiAxis = new Vector3d(u);
	// double b = beamVector.length()
	// * (math.tan(theta) / math.sqrt(1 - ((math.tan(theta) * math.tan(theta)) * (math.tan(delta) * math
	// .tan(delta)))));
	// minorSemiAxis.scale(b);
	//
	// double[] out = new double[2];
	// out[0] = majorSemiAxis.length();
	// out[1] = minorSemiAxis.length();
	// return out; // something useful
	// }

	/**
	 * This method calculates the pixel positions on the detector of a resolution ring specified by the d spacing
	 * specified in angstrom
	 * 
	 * @param detector
	 *            Class describing the detector in space
	 * @param diffexp
	 *            Class describing the diffraction experiment
	 * @param angstrom
	 *            resolution in Angstrom
	 * @return Array of pixel values indicating where the resolution ring is on the detector. If the raw misses the
	 *         detector then the xy value will be Integer.MAX_VALUE
	 **/
	@Deprecated
	public static int[] resolutionRingRaytrace(DetectorProperties detector, DiffractionCrystalEnvironment diffexp,
			double angstrom) {

		double Rlength, tempAnglex, tempAngley, xComponent, yComponent;
		int numConeRays = 720;
		// calculate the cone angle and the initial vector when the vector in in
		// the yz plane
		double theta = Math.asin(diffexp.getWavelength() / (2 * angstrom));
		Vector3d ringVector = new Vector3d(0, Math.sin(theta), Math.cos(theta));
		ringVector.normalize();

		int counter = 0;
		int[] pixelValues = new int[numConeRays * 2];

		// Correct for beam vector
		Vector3d beam = detector.getBeamVector();
		Vector3d correctbeam = new Vector3d();
		correctbeam.sub(beam, new Vector3d(0, 0, 1));

		Matrix3d zRotate = new Matrix3d();
		zRotate.setIdentity();
		// Vector describing the point on the detector where the 'ray' hits
		// relative to the detector origin
		Vector3d detectorVec = new Vector3d();
		Vector3d detectorNorm = detector.getNormal();
		Vector3d detectorOrigin = detector.getOrigin();

		for (int i = 0; i < numConeRays; i++) {

			// rotate to produce a cone
			Vector3d coneRay = new Vector3d(ringVector);
			zRotate.rotZ(((2 * Math.PI) / numConeRays) * i);
			zRotate.transform(coneRay);

			if (correctbeam.length() != 0) {
				coneRay.add(correctbeam); // correct for beam vector
			}

			Rlength = detectorOrigin.dot(detectorNorm) / coneRay.dot(detectorNorm);
			coneRay.scale(Rlength);
			// calculate the pixel position
			detectorVec.sub(coneRay, detectorOrigin);
			tempAnglex = detectorVec.angle(detector.getHorizontalVector());
			xComponent = detectorVec.length() * Math.cos(tempAnglex);
			tempAngley = detectorVec.angle(detector.getVerticalVector());
			yComponent = detectorVec.length() * Math.cos(tempAngley);

			if (xComponent <= detector.getDetectorSizeH() && yComponent <= detector.getDetectorSizeV()
					&& xComponent > 0 && yComponent > 0) {

				pixelValues[counter++] = (int) (xComponent / detector.getHPxSize());
				pixelValues[counter++] = (int) (yComponent / detector.getVPxSize());
			} else {
				pixelValues[counter++] = Integer.MAX_VALUE;
				pixelValues[counter++] = Integer.MAX_VALUE;
			}
		}
		return pixelValues;
	}

	/**
	 * Stopgap. calculate a resolution ring conditional on the detector being normal to the beam vector. This will be
	 * replaced with an ellipse pending Implementation of ellipse plotting.
	 * 
	 * @param detector
	 * @param difExp
	 * @param d
	 * @return radius on the resolution ring in PIXELS
	 */
	public static double circularResolutionRingRadius(DetectorProperties detector,
			DiffractionCrystalEnvironment difExp, double d) {
		double theta = 2*(Math.asin(difExp.getWavelength() / (2 * d)));
		Vector3d radiusVector = new Vector3d(0, Math.sin(theta), Math.cos(theta));
		Vector3d beam = new Vector3d(detector.getBeamVector());
		Vector3d normal = detector.getNormal();
		
		// scale vectors
		radiusVector.scale(detector.getOrigin().dot(normal) / radiusVector.dot(normal));
		beam.scale(detector.getOrigin().dot(normal) / beam.dot(normal));

		radiusVector.sub(beam);
		return radiusVector.length() / detector.getVPxSize();
	}


	/**
	 * Calculates the vector between pixels on the detector.
	 * 
	 * @param det
	 *            Detector Object describing the orientation of the detector
	 * @param pixel1x
	 * @param pixel1y
	 * @param pixel2x
	 * @param pixel2y
	 * @return pixelDistance distance between pixels in real space
	 */
	public static Vector3d pixelToPixelVector(DetectorProperties det, int pixel1x, int pixel1y, int pixel2x, int pixel2y) {
		Vector3d pixelVector = new Vector3d();
		pixelVector.sub(det.pixelPosition(pixel2x, pixel2y), det.pixelPosition(pixel1x, pixel1y));
		return pixelVector;
	}

	// /**
	// * calculated the d spacing between 2 pixels
	// *
	// * @param det
	// * Detector Object describing the orientation of the detector
	// * @param difExp
	// * Diffraction experiment describing the experiment
	// *
	// * @param pixel1x
	// * @param pixel1y
	// * @param pixel2x
	// * @param pixel2y
	// * @return d spacing
	// */
	// // use q space class
	// @Deprecated
	// public static double dSpacingBetweenPixels(DetectorProperties det,
	// DiffractionCrystalEnvironment difExp, int pixel1x, int pixel1y,
	// int pixel2x, int pixel2y) {
	// if (pixel1x == pixel2x && pixel1y == pixel2y) {
	// throw new IllegalArgumentException("The pixel values were identical");
	// }
	// double dSpacing = dSpacingBetweenScatteringVectors(det, difExp,
	// scatteredVector(det, pixel1x, pixel1y), scatteredVector(det,
	// pixel2x, pixel2y));
	// return dSpacing;
	// }

	// /**
	// * Calculates a vector between 2 scattering vectors
	// *
	// * @param det
	// * Class describing the detector in space
	// * @param difExp
	// * Class describing the Diffraction Experiment
	// *
	// * @param ScatteringVector1
	// * @param ScatteringVector2
	// * @return distance between hkl plane in angstrom
	// */
	// // use q space class
	// @Deprecated
	// public static double dSpacingBetweenScatteringVectors(
	// DetectorProperties det, DiffractionCrystalEnvironment difExp,
	// Vector3d ScatteringVector1, Vector3d ScatteringVector2) {
	// double invLambda = 1 / difExp.getWavelength();
	// if (ScatteringVector1.length() != invLambda) {
	// ScatteringVector1 = scaleScatteredVector(difExp, ScatteringVector1);
	// }
	// if (ScatteringVector2.length() != invLambda) {
	// ScatteringVector2 = scaleScatteredVector(difExp, ScatteringVector2);
	// }
	// Vector3d scaledbeamVector = scaleScatteredVector(difExp, det
	// .getBeamVector());
	// // Calculate vector from reciprocal space origin to reciprocal position
	// // on Ewald sphere
	// ScatteringVector1.sub(scaledbeamVector);
	// ScatteringVector2.sub(scaledbeamVector);
	// // calculate distance between points
	// ScatteringVector1.sub(ScatteringVector2);
	// double dSpacing = 1 / ScatteringVector1.length();
	// //System.out.println("Vector :"+ ScatteringVector1.toString());
	// return dSpacing;
	// }

	// /**
	// * Scales a scattering vector such that it has 1/lambda length. This will
	// * measure the distance from the real space origin to reciprocal
	// *
	// * @param difExp
	// * Class describing the diffraction experiment
	// *
	// * @param scatteredVector
	// * vector from sample to pixel on detector
	// * @return Scaled scattering vector
	// */
	// // use q space class
	// @Deprecated
	// public static Vector3d scaleScatteredVector(DiffractionCrystalEnvironment difExp,
	// Vector3d scatteredVector) {
	// double scale = difExp.getWavelength() / scatteredVector.length();
	// scatteredVector.scale(scale);
	// return scatteredVector;
	// }
}
