/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.roi.IParametricROI;
import org.eclipse.dawnsci.analysis.dataset.roi.LinearROI;
import org.eclipse.dawnsci.analysis.dataset.roi.PointROI;
import org.eclipse.dawnsci.analysis.dataset.roi.PolylineROI;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.PositionIterator;
import org.eclipse.january.dataset.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.Fitter;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.roi.ROIProfile;

public class PeakFittingEllipseFinder {

	private static Logger logger = LoggerFactory.getLogger(PeakFittingEllipseFinder.class);
	
	/**
	 * Find a set of points of interests near given ellipse from an image.
	 * <p>
	 * The ellipse is divided into sub-areas and these POIs are considered to
	 * be the locations of maximum pixel intensities found within those sub-areas.
	 * @param originalImage
	 * @param mask (can be null) 
	 * @param ellipse
	 * @param inOut array of elliptical ROIs giving search range
	 * @return polyline ROI
	 */
	public static PolylineROI findPointsOnConic(Dataset originalImage, BooleanDataset mask, IParametricROI ellipse,
			IParametricROI[] inOut, int nPoints, IMonitor mon) {
		
		Dataset image = originalImage; 
		
		if (image.getRank() != 2) {
			logger.error("Dataset must have two dimensions");
			throw new IllegalArgumentException("Dataset must have two dimensions");
		}
		if (mask != null) {
			if(image.isCompatibleWith(mask)) {
				logger.info("Applying mask to image for point finding");
				image = originalImage.clone();// change to working on a clone of the input data when a mask exists
				image.setByBoolean(-1, Comparisons.logicalNot(mask)); // the mask is inverted because of the way the FastMaskTool defines masks	
			}else{
				logger.error("Mask must match image shape");
				throw new IllegalArgumentException("Mask must match image shape");
			}
		}
		
		
		final int[] shape = image.getShape();
		final int h = shape[0];
		final int w = shape[1];
		if (ellipse.containsPoint(-1,-1) && ellipse.containsPoint(-1,h+1) && ellipse.containsPoint(w+1,h+1) && ellipse.containsPoint(w+1,-1)) {
			throw new IllegalArgumentException("Ellipse does not intersect image!");
		}
		
		PolylineROI polyline = new PolylineROI();
		
		
		List<double[]> searchRange = findSuitableSearchRange(ellipse, shape);
		
		if (searchRange.isEmpty()) searchRange.add(new double[]{0, Math.PI*2});
		
		double totalRange = 0;
		for (double[]range : searchRange) totalRange += (range[1]-range[0]);
		
		int count = 1;
		
		while (count < 5 && polyline.getNumberOfPoints() < nPoints *0.75) {
			
			if (mon != null) {
				if (count == 1) mon.subTask("Starting peak fit...");
				else mon.subTask("Not enough points found, continuing search...: " + count);
			}
			
			for (double[] range : searchRange) {
				
				double step = (range[1]-range[0])/nPoints;
				int nPointFrac = (int)((range[1]-range[0])/totalRange*nPoints);
				
				double offset = 1/count*step;
				offset = 0;
				findNumberOfPointsOnEllipse(image, polyline,inOut, new double[]{offset+range[0],range[1]},nPointFrac, mon);
			}
			
			count++;
		}
		
		if (polyline.getNumberOfPoints() < nPoints/2) {
			logger.debug("not find enough points");
			return null;
		}
		
		return polyline;
	}
	
	private static PolylineROI findNumberOfPointsOnEllipse(Dataset image, PolylineROI polyline,
			IParametricROI[] inOut, double[] startStop, int nPoints, IMonitor mon) {
		
		final int[] shape = image.getShape();
		final int h = shape[1];
		final int w = shape[0];
		
		List<PointROI> roiList = new ArrayList<PointROI>();
		List<Gaussian> gaussianList = new ArrayList<Gaussian>();
		
		double step = (startStop[1]-startStop[0])/nPoints;
		
		for (double i = startStop[0]; i < startStop[1]; i+=step) {
			double[] beg = inOut[0].getPoint(i);
			double[] end = inOut[1].getPoint(i);
			
			if (end[0] > h || end[0] < 0 || end[1] > w || end[1] < 0) continue;
			
			LinearROI line = new LinearROI(beg, end);
			Dataset sub = ROIProfile.line(image, line, 1)[0];

			BooleanDataset badVals = Comparisons.lessThan(sub, 0);
			
			if (Comparisons.allTrue(badVals)) continue;
			
			double count = ((Number) badVals.sum()).doubleValue();
			
			if (count > sub.getSize()*0.1) continue;
			
			sub = sub.setByBoolean(Double.NaN, badVals);
			double min = sub.min(true).doubleValue();
			sub = sub.setByBoolean(min, badVals);
			
			Dataset xAx = DatasetFactory.createRange(IntegerDataset.class, sub.getSize());
			
			sub.isubtract(min);
			
			double s = (Double)Stats.median(sub.getSlice(new int[] {0}, new int[] {3}, new int[] {1}));
			double en = (Double)Stats.median(sub.getSlice(new int[] {sub.getSize()-3}, new int[] {sub.getSize()}, new int[] {1}));
			
			double m = (s-en)/(0-sub.getSize()-1);
			double c = s;
			
			Dataset base = Maths.multiply(xAx, m);
			base.iadd(c);
			
			sub.isubtract(base);
			
			if ((Double)sub.mean() < 0) continue;
			Gaussian g = null;
			
			try {
				
				DoubleDataset xData = DatasetFactory.createRange(DoubleDataset.class, sub.getSize());
				int maxPos = sub.maxPos()[0];
				g = new Gaussian(new double[]{maxPos,1,sub.getDouble(maxPos)});
				Fitter.fit(xData, sub, new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT), g);
				
			} catch (Exception e) {
				logger.trace(e.getMessage());
			}
			
			if (g == null) continue;
			
			//lineAngle != i for non-elliptical
			double lineAngle = line.getAngle();
			
			double r = g.getParameter(0).getValue();
			double x = r*Math.cos(lineAngle)+beg[0];
			double y = r*Math.sin(lineAngle)+beg[1];
			roiList.add(new PointROI(x,y));
			gaussianList.add(g);
			
			if (mon != null) {
				mon.worked(1);
				if (mon.isCancelled()) return null;
			}
		}
		
		if (gaussianList.isEmpty()) return polyline;
		
		Dataset heights = DatasetFactory.zeros(gaussianList.size());
		Dataset widths = DatasetFactory.zeros(gaussianList.size());
		
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
//			double ph = gaussianList.get(i).getHeight();
			if (gaussianList.get(i).getHeight() > threshold && pw > lowerw && pw < upperw) {
				polyline.insertPoint(roiList.get(i));
			}
		}
		
		return polyline;
	}
	
	private static List<double[]> findSuitableSearchRange(IParametricROI ellipse, int[] shape) {
	
		List<double[]> angles = new ArrayList<double[]>();
		
		angles.add(ellipse.getHorizontalIntersectionParameters(0));//t
		angles.add(ellipse.getVerticalIntersectionParameters(0));//l
		angles.add(ellipse.getHorizontalIntersectionParameters(shape[0]));//b
		angles.add(ellipse.getVerticalIntersectionParameters(shape[1]));//r
		
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
}
