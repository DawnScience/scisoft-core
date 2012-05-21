/*-
 * Copyright 2012 Diamond Light Source Ltd.
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

import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.fitting.EllipseFitter;

/**
 * An elliptical region of interest which fits the points in a polygonal region of interest
 */
public class EllipticalFitROI extends EllipticalROI {

	private PolygonalROI proi;

	public EllipticalFitROI(PolygonalROI points) {
		super(1, 0, 0);
		setPoints(points);
	}

	/**
	 * Fit an ellipse to given polygon
	 * @return ellipse parameters
	 */
	private static double[] fitEllipse(PolygonalROI polygon) {
		int n = polygon.getSides();
		double[] x = new double[n];
		double[] y = new double[n];
		int i = 0;
		for (ROIBase r : polygon) {
			x[i] = r.getPointX();
			y[i] = r.getPointY();
			i++;
		}

		DoubleDataset dx = new DoubleDataset(x);
		DoubleDataset dy = new DoubleDataset(y);
		EllipseFitter f = new EllipseFitter();
		f.geometricFit(dx, dy, null);
		return f.getParameters();
	}

	/**
	 * Set points which are then used to fit ellipse
	 * @param points
	 */
	public void setPoints(PolygonalROI points) {
		proi = points;
		final double[] p = fitEllipse(points);

		setSemiAxis(0, p[0]);
		setSemiAxis(1, p[1]);
		setAngle(p[2]);
		setPoint(p[3], p[4]);
	}

	/**
	 * @return points in polygon for fitting
	 */
	public PolygonalROI getPoints() {
		return proi;
	}

}
