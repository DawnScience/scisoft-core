/*-
 * Copyright 2013 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.roi;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.fitting.CircleFitter;
import uk.ac.diamond.scisoft.analysis.fitting.IConicSectionFitter;

/**
 * A circular region of interest which fits the points in a polygonal region of interest
 */
public class CircularFitROI extends CircularROI {

	private PolylineROI proi;
	private IConicSectionFitter fitter;

	private CircularFitROI(double radius, double ptx, double pty) {
		super(radius, ptx, pty);
	}

	public CircularFitROI(PolylineROI points) {
		super(1, 0, 0);
		setPoints(points);
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		proi.downsample(subFactor);
	}

	@Override
	public CircularFitROI copy() {
		CircularFitROI croi = new CircularFitROI(getRadius(), getPointX(), getPointY());
		croi.proi = proi.copy();
		croi.setPlot(plot);
		return croi;
	}

	/**
	 * Fit a circle to given polygon
	 * @param polyline
	 * @return fitter
	 */
	public static IConicSectionFitter fit(PolylineROI polyline) {
		AbstractDataset[] xy = polyline.makeCoordinateDatasets();

		CircleFitter f = new CircleFitter();
		f.geometricFit(xy[0], xy[1], null);
		return f;
	}

	/**
	 * Set points which are then used to fit circle
	 * @param points
	 */
	public void setPoints(PolylineROI points) {
		proi = points;
		if (fitter == null) {
			fitter = fit(points);
		} else {
			AbstractDataset[] xy = points.makeCoordinateDatasets();
			fitter.geometricFit(xy[0], xy[1], fitter.getParameters());
		}
		final double[] p = fitter.getParameters();

		setRadius(p[0]);
		setPoint(p[1], p[2]);
	}

	/**
	 * @return fitter used
	 */
	public IConicSectionFitter getFitter() {
		return fitter;
	}

	/**
	 * @return points in polygon for fitting
	 */
	public PolylineROI getPoints() {
		return proi;
	}
}
