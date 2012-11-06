/*
 * Copyright 2011 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.ArrayList;

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
}
