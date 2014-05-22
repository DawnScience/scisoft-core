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

import java.io.Serializable;

import uk.ac.diamond.scisoft.analysis.dataset.Activator;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IFittingAlgorithmService;
import uk.ac.diamond.scisoft.analysis.fitting.IConicSectionFitter;

/**
 * An elliptical region of interest which fits the points in a polygonal region of interest
 */
public class EllipticalFitROI extends EllipticalROI implements IFitROI, Serializable {

	private IPolylineROI proi;
	private boolean circleOnly;
	private transient IConicSectionFitter fitter; // The fitter is not serializable, the EllipticalFitROI is.
	private double residual;

	private EllipticalFitROI(double major, double minor, double angle, double ptx, double pty) {
		super(major, minor, angle, ptx, pty);
		residual = 0;
	}

	public EllipticalFitROI(IPolylineROI points) {
		this(points, false);
	}

	public EllipticalFitROI(IPolylineROI points, boolean fitCircle) {
		super(1, 0, 0);
		circleOnly = fitCircle;
		setPoints(points);
	}

	@Override
	public void downsample(double subFactor) {
		super.downsample(subFactor);
		proi.downsample(subFactor);
	}

	@Override
	public EllipticalFitROI copy() {
		EllipticalFitROI c = new EllipticalFitROI(getSemiAxis(0), getSemiAxis(1), getAngle(), getPointX(), getPointY());
		c.proi = proi.copy();
		c.name = name;
		c.plot = plot;
		return c;
	}

	/**
	 * Fit an ellipse to given polyline
	 * @param polyline
	 * @return fitter
	 */
	public static IConicSectionFitter fit(IPolylineROI polyline, final boolean fitCircle) {
		
		IFittingAlgorithmService service = (IFittingAlgorithmService) Activator.getService(IFittingAlgorithmService.class);
		IDataset[] xy = polyline.makeCoordinateDatasets();
		if (fitCircle) {
			IConicSectionFitter f = service.createCircleFitter();
			f.geometricFit(xy[0], xy[1], null);
			return f;
		}

		IConicSectionFitter f = service.createEllipseFitter();
		f.geometricFit(xy[0], xy[1], null);
		return f;
	}

	
	/**
	 * Set points which are then used to fit ellipse
	 * @param points
	 */
	@Override
	public void setPoints(IPolylineROI points) {
		proi = points;
		int n = points.getNumberOfPoints();
		final double[] p;
		if (fitter == null) {
			fitter = fit(points, n < 5 || circleOnly);
			p = fitter.getParameters();
		} else {
			IDataset[] xy = points.makeCoordinateDatasets();
			p = fitter.getParameters();
			if (p.length < 5) {
				p[0] = getSemiAxis(0);
				p[1] = getPointX();
				p[2] = getPointY();
			} else {
				p[0] = getSemiAxis(0);
				p[1] = getSemiAxis(1);
				p[2] = getAngle();
				p[3] = getPointX();
				p[4] = getPointY();
			}
			fitter.geometricFit(xy[0], xy[1], p);
		}
		residual = fitter.getRMS();

		if (p.length < 5) {
			setSemiAxis(0, p[0]);
			setSemiAxis(1, p[0]);
			setAngle(0);
			setPoint(p[1], p[2]);
		} else {
			setSemiAxis(0, p[0]);
			setSemiAxis(1, p[1]);
			setAngle(p[2]);
			setPoint(p[3], p[4]);
		}
		bounds = null;
	}

	/**
	 * @return root mean squared of residuals
	 */
	@Override
	public double getRMS() {
		return residual;
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
	@Override
	public IPolylineROI getPoints() {
		return proi;
	}

}
