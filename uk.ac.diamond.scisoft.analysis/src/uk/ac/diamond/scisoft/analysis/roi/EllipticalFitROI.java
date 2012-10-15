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

	private PolylineROI proi;

	private EllipticalFitROI(double major, double minor, double angle, double ptx, double pty) {
		super(major, minor, angle, ptx, pty);
	}

	public EllipticalFitROI(PolylineROI points) {
		super(1, 0, 0);
		setPoints(points);
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		proi.downsample(subFactor);
	}

	@Override
	public EllipticalFitROI copy() {
		EllipticalFitROI croi = new EllipticalFitROI(getSemiAxis(0), getSemiAxis(1), getAngle(), getPointX(), getPointY());
		croi.proi = proi.copy();
		croi.setPlot(plot);
		return croi;
	}

	/**
	 * Fit an ellipse to given polygon
	 * @return ellipse parameters
	 */
	private static double[] fitEllipse(final int n, final Iterable<PointROI> polygon) {
		double[] x = new double[n];
		double[] y = new double[n];
		int i = 0;
		for (PointROI r : polygon) {
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
	public void setPoints(PolylineROI points) {
		proi = points;
		final double[] p = fitEllipse(points.getNumberOfPoints(), points);

		setSemiAxis(0, p[0]);
		setSemiAxis(1, p[1]);
		setAngle(p[2]);
		setPoint(p[3], p[4]);
	}

	/**
	 * @return points in polygon for fitting
	 */
	public PolylineROI getPoints() {
		return proi;
	}

}
