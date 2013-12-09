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

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.BooleanDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Comparisons;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.Stats;
import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.roi.EllipticalROI;
import uk.ac.diamond.scisoft.analysis.roi.LinearROI;
import uk.ac.diamond.scisoft.analysis.roi.PointROI;
import uk.ac.diamond.scisoft.analysis.roi.PolylineROI;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public class PeakFittingEllipseFinder {

	private static Logger logger = LoggerFactory.getLogger(PeakFittingEllipseFinder.class);
	/**
	 * Find a set of points of interests near given ellipse from an image.
	 * <p>
	 * The ellipse is divided into sub-areas and these POIs are considered to
	 * be the locations of maximum pixel intensities found within those sub-areas.
	 * @param image
	 * @param mask (can be null)
	 * @param ellipse
	 * @param innerDelta inner search range
	 * @param outerDelta outer search range
	 * @return polyline ROI
	 */
	public static PolylineROI findPointsOnEllipse(AbstractDataset image, BooleanDataset mask, EllipticalROI ellipse,
			double innerDelta, double outerDelta) {
		if (image.getRank() != 2) {
			logger.error("Dataset must have two dimensions");
			throw new IllegalArgumentException("Dataset must have two dimensions");
		}
		if (mask != null && !image.isCompatibleWith(mask)) {
			logger.error("Mask must match image shape");
			throw new IllegalArgumentException("Mask must match image shape");
		}
		
		if (innerDelta < 10 || outerDelta < 10) {
			logger.debug("Gap between ellipses too small!");
			return null;
		}
		
		final int[] shape = image.getShape();
		final int h = shape[0];
		final int w = shape[1];
		if (ellipse.containsPoint(-1,-1) && ellipse.containsPoint(-1,h+1) && ellipse.containsPoint(w+1,h+1) && ellipse.containsPoint(w+1,-1)) {
			throw new IllegalArgumentException("Ellipse does not intersect image!");
		}
		
		EllipticalROI inner = ellipse.copy();
		inner.setSemiAxis(0, ellipse.getSemiAxis(0)-innerDelta);
		inner.setSemiAxis(1, ellipse.getSemiAxis(1)-innerDelta);
		
		EllipticalROI outer = ellipse.copy();
		outer.setSemiAxis(0, ellipse.getSemiAxis(0)+outerDelta);
		outer.setSemiAxis(1, ellipse.getSemiAxis(1)+outerDelta);
		
		PolylineROI polyline = new PolylineROI();
		
		find64PointsOnEllipse(image, polyline, inner, outer, 0);
		
		if (polyline.getNumberOfPoints() < 100) {
			find64PointsOnEllipse(image, polyline, inner, outer, Math.PI/256);
			if (polyline.getNumberOfPoints() < 150)  {
				find64PointsOnEllipse(image, polyline, inner, outer, 3*Math.PI/256);
				if (polyline.getNumberOfPoints() < 150) {
					find64PointsOnEllipse(image, polyline, inner, outer, 3*Math.PI/512);
				}
				
			}
			
		}
		
		if (polyline.getNumberOfPoints() < 75) {
			logger.debug("not enough points");
			return null;
		}
		
		return polyline;
	}
	
	private static PolylineROI find64PointsOnEllipse(AbstractDataset image, PolylineROI polyline, EllipticalROI inner, EllipticalROI outer, double start) {
		
		final int[] shape = image.getShape();
		final int h = shape[0];
		final int w = shape[1];
		
		double range = outer.getSemiAxis(0)-inner.getSemiAxis(0);
		
		double ang = inner.getAngle();
		
		List<PointROI> roiList = new ArrayList<PointROI>();
		List<Gaussian> gaussianList = new ArrayList<Gaussian>();
		
		for (double i = start; i < (start + Math.PI*2); i+=(Math.PI/128)) {
			double[] beg = inner.getPoint(i);
			double[] end = outer.getPoint(i);
			
			if (end[0] > h || end[0] < 0 || end[1] > w || end[1] < 0) continue;
			
			LinearROI line = new LinearROI(beg, end);
			AbstractDataset sub = ROIProfile.line(image, line, 1)[0];

			BooleanDataset badVals = Comparisons.lessThanOrEqualTo(sub, 0);
			
			if (Comparisons.allTrue(badVals)) continue;
			
			double count = (Double)badVals.sum();
			
			if (count > range*0.1) continue;
			
			sub = sub.setByBoolean(Double.NaN, badVals);
			double min = sub.min(true).doubleValue();
			sub = sub.setByBoolean(min, badVals);
			
			AbstractDataset xAx = AbstractDataset.arange(sub.getSize(), AbstractDataset.INT32);
			
			sub.isubtract(min);
			
			double s = (Double)Stats.median(sub.getSlice(new int[] {0}, new int[] {3}, new int[] {1}));
			double en = (Double)Stats.median(sub.getSlice(new int[] {sub.getSize()-5}, new int[] {sub.getSize()-1}, new int[] {1}));
			
			double m = (s-en)/(0-sub.getSize()-1);
			double c = s;
			
			AbstractDataset base = Maths.multiply(xAx, m);
			base.iadd(c);
			
			sub.isubtract(base);
			
			if ((Double)sub.mean() < 0) continue;
			Gaussian g = null;
			
			try {
				
				DoubleDataset xData = DoubleDataset.arange(sub.getSize());
				int maxPos = sub.maxPos()[0];
				g = new Gaussian(new double[]{maxPos,1,sub.getDouble(maxPos)});
				Fitter.ApacheNelderMeadFit(new AbstractDataset[]{xData}, sub, g);
				
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
			
			if (g == null) continue;
			
			double r = g.getParameter(0).getValue();
			double x = r*Math.cos(i+ang)+beg[0];
			double y = r*Math.sin(i+ang)+beg[1];
			roiList.add(new PointROI(x,y));
			gaussianList.add(g);
		}
		
		AbstractDataset heights = AbstractDataset.zeros(new int[] {gaussianList.size()}, AbstractDataset.FLOAT64);
		
		for (int i = 0; i < gaussianList.size(); i++) {
			heights.set(gaussianList.get(i).getHeight(), i);
		}
		
		double iqr = (Double)Stats.iqr(heights);
		double threshold = (Double) heights.mean() - 2*iqr > 0 ? (Double) heights.mean() - 2*iqr : (Double) heights.mean();
		
		for (int i = 0; i < gaussianList.size(); i++) {
			if (gaussianList.get(i).getHeight() > threshold) {
				polyline.insertPoint(roiList.get(i));
			}
		}
		
		return polyline;
	}
	
}
