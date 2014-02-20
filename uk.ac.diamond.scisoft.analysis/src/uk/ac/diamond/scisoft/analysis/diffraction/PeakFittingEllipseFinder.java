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
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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
import uk.ac.diamond.scisoft.analysis.monitor.IMonitor;
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
			double innerDelta, double outerDelta,int nPoints, IMonitor mon) {
		if (image.getRank() != 2) {
			logger.error("Dataset must have two dimensions");
			throw new IllegalArgumentException("Dataset must have two dimensions");
		}
		if (mask != null && !image.isCompatibleWith(mask)) {
			logger.error("Mask must match image shape");
			throw new IllegalArgumentException("Mask must match image shape");
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
		
		
		List<double[]> searchRange = findSuitableSearchRange(ellipse, shape);
		
		if (searchRange.isEmpty()) searchRange.add(new double[]{0, Math.PI*2});
		
		double totalRange = 0;
		for (double[]range : searchRange) totalRange += (range[1]-range[0]);
		
		int count = 1;
		
		while (count < 5 && polyline.getNumberOfPoints() < nPoints *0.75) {
			for (double[] range : searchRange) {
				
				double step = (range[1]-range[0])/nPoints;
				int nPointFrac = (int)((range[1]-range[0])/totalRange*nPoints);
				
				double offset = 1/count*step;
				
				findNumberOfPointsOnEllipse(image, polyline, inner, outer, offset+range[0],range[1],nPointFrac, mon);
			}
			
			count++;
		}
		
		if (polyline.getNumberOfPoints() < nPoints/2) {
			logger.debug("not find enough points");
			return null;
		}
		
		return polyline;
	}
	
	private static PolylineROI findNumberOfPointsOnEllipse(AbstractDataset image, PolylineROI polyline,
			EllipticalROI inner, EllipticalROI outer, double start, double stop, int nPoints, IMonitor mon) {
		
		final int[] shape = image.getShape();
		final int h = shape[1];
		final int w = shape[0];
		
		double range = outer.getSemiAxis(0)-inner.getSemiAxis(0);
		
		double ang = inner.getAngle();
		
		List<PointROI> roiList = new ArrayList<PointROI>();
		List<Gaussian> gaussianList = new ArrayList<Gaussian>();
		
		double step = (stop-start)/nPoints;
		
		for (double i = start; i < stop; i+=step) {
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
			
			if (mon != null) {
				if (mon.isCancelled()) return null;
			}
		}
		
		AbstractDataset heights = AbstractDataset.zeros(new int[] {gaussianList.size()}, AbstractDataset.FLOAT64);
		AbstractDataset widths = AbstractDataset.zeros(new int[] {gaussianList.size()}, AbstractDataset.FLOAT64);
		
		for (int i = 0; i < gaussianList.size(); i++) {
			heights.set(gaussianList.get(i).getHeight(), i);
			widths.set(gaussianList.get(i).getFWHM(), i);
		}
		
		
		double med = (Double)Stats.median(widths);
		double mad = (Double)Stats.median(Maths.abs(Maths.subtract(widths, med)));
		
		double upperw = med + mad*3;
		double lowerw = med - mad*3;
//		double threshold = (Double) heights.mean() - iqr > 0 ? (Double) heights.mean() - iqr : (Double) heights.mean();
		double threshold = 0;
		
		for (int i = 0; i < gaussianList.size(); i++) {
			double pw = gaussianList.get(i).getFWHM();
			double ph = gaussianList.get(i).getHeight();
			if (gaussianList.get(i).getHeight() > threshold && pw > lowerw && pw < upperw) {
				polyline.insertPoint(roiList.get(i));
			}
		}
		
		return polyline;
	}
	
	private static List<double[]> findSuitableSearchRange(EllipticalROI ellipse, int[] shape) {
	
		List<double[]> angles = new ArrayList<double[]>();
		
		angles.add(findDetectorEllipseInterceptX(0,ellipse));//t
		angles.add(findDetectorEllipseInterceptY(0,ellipse));//l
		angles.add(findDetectorEllipseInterceptX(shape[0],ellipse));//b
		angles.add(findDetectorEllipseInterceptY(shape[1],ellipse));//r
		
		List<Double> all = new ArrayList<Double>();		
		for (double[] angle : angles) {
			if (angle != null) {
				all.add(angle[0]);
				all.add(angle[1]);
			}
		}
		
		List<double[]> startStop = new ArrayList<double[]>();
		
		if (all.isEmpty()) return startStop;
		
		Collections.sort(all);
		
		for (int i = -1; i < all.size(); i++) {
			
			double current = 0;
			
			if (i != -1) current = all.get(i);
			
			double next = 2*Math.PI;
			
			if (i+1 != all.size()) next = all.get(i+1);
			
			double a = (next-current)/2+current;
			double[] point = ellipse.getPoint(a);
			
			if (point[0] < 0 || point[0] > shape[1] || point[1] < 0 || point[1] > shape[0]) continue;
			
			startStop.add(new double[] {current, next});

			
		}
		
		return startStop;
	}
	
	
	private static double[] findDetectorEllipseInterceptX(double x1, EllipticalROI roi) {
		//XAxis
		
		double xCor = x1-roi.getPointY();
		
		double ca = Math.cos(roi.getAngle());
		double sa = Math.sin(roi.getAngle());
		double ca2 = Math.pow(ca, 2);
		double sa2 = Math.pow(sa, 2);
		double a2 = Math.pow(roi.getSemiAxis(0), 2);
		double b2 = Math.pow(roi.getSemiAxis(1), 2);
		
		double a = ca2/a2+sa2/b2;
		double b = -2*ca*sa*(1/a2-1/b2)*xCor;
		double c = ((sa2/a2 + ca2/b2)*Math.pow(xCor, 2))-1;
		
		double quad = Math.pow(b, 2) - 4*a*c;
		
		if (quad < 0) return null;
		
		double[] out = new double[2];
		
		double yP = (-b + Math.sqrt(quad))/(2*a);
		double yM = (-b - Math.sqrt(quad))/(2*a);
		
		out[0] = Math.atan2(roi.getSemiAxis(0)*(ca*xCor- sa*yP), roi.getSemiAxis(1)*(ca*yP + sa*xCor));
		out[1] = Math.atan2(roi.getSemiAxis(0)*(ca*xCor - sa*yM), roi.getSemiAxis(1)*(ca*yM + sa*xCor));
		
		if (out[0] < 0) out[0] = 2*Math.PI + out[0];
		if (out[1] < 0) out[1] = 2*Math.PI + out[1];
		
		return out;
	}
	
	private static double[] findDetectorEllipseInterceptY(double y1, EllipticalROI roi) {
		
		double yCor = y1-roi.getPointX();
		
		double ca = Math.cos(roi.getAngle());
		double sa = Math.sin(roi.getAngle());
		double ca2 = Math.pow(ca, 2);
		double sa2 = Math.pow(sa, 2);
		double a2 = Math.pow(roi.getSemiAxis(0), 2);
		double b2 = Math.pow(roi.getSemiAxis(1), 2);
		
		double a = sa2/a2+ca2/b2;
		double b = -2*ca*sa*(1/a2-1/b2)*yCor;
		double c = ((ca2/a2 + sa2/b2)*Math.pow(yCor, 2))-1;
		
		double quad = Math.pow(b, 2) - 4*a*c;
		
		if (quad < 0) return null;
		 
		double[] out = new double[2];

		double xP = (-b + Math.sqrt(quad))/(2*a);
		double xM = (-b - Math.sqrt(quad))/(2*a);
		
		out[0] = Math.atan2(roi.getSemiAxis(0)*(ca*xP - sa*yCor), roi.getSemiAxis(1)*(ca*yCor + sa*xP));
		out[1] = Math.atan2(roi.getSemiAxis(0)*(ca*xM - sa*yCor), roi.getSemiAxis(1)*(ca*yCor + sa*xM));
		
		
		if (out[0] < 0) out[0] = 2*Math.PI + out[0];
		if (out[1] < 0) out[1] = 2*Math.PI + out[1];
		
		return out;
	}
	
}
