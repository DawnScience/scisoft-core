/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IPeak;
import org.eclipse.january.IMonitor;
import org.eclipse.january.dataset.BooleanDataset;
import org.eclipse.january.dataset.Comparisons;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.Maths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IdentifiedPeak;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.StraightLine;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

public class Generic1DFitter implements Serializable {
	private static final long serialVersionUID = -8837947420560616887L;

	private static int DEFAULT_SMOOTHING = 3;
	private static double DEFAULT_ACCURACY = 0.0001;
	private static IOptimizer DEFAULT_OPTIMISER = new GeneticAlg(DEFAULT_ACCURACY);
	private static double EPSILON = 1E-5;
	private static boolean FIT_LINEAR_BASELINE = false;
	private static final Logger logger = LoggerFactory.getLogger(Generic1DFitter.class);
	
	/*TODO This class needs a tidy up to:
	 * 1) Remove some of the wrapper classes
	 * 2) Move from peakClass extending IPeak to extending IPeak
	 */

	/**
	 * This method fits peaks to a dataset describing the y values at specified x values. The CompositeFunction specified
	 * will be returned in the list of FittedPeaks. numPeaks is the maximum number of peaks that will be fitted.
	 * 
	 * @param xdata - the x values that of the measurements given in ydata
	 * @param ydata - the y values corresponding to the x values given
	 * @param peakClass - A class that obeys the IPeak interface
	 * @param numPeaks - The maximum number of peaks that are fitted.
	 * @return list of FittedPeaks - an object that contain the fitted IPeak objects and the corresponding objects that
	 *         describe the region of the data where the peak was found
	 */
	
	public static List<CompositeFunction> fitPeakFunctions(Dataset xdata, Dataset ydata, Class<? extends IPeak> peakClass, int numPeaks) {
		int tempSmoothing = (int) (xdata.getSize() * 0.01);
		int smoothing;
		if (tempSmoothing > DEFAULT_SMOOTHING) {
			smoothing = tempSmoothing;
		} else {
			smoothing = DEFAULT_SMOOTHING;
		}
		
		return fitPeakFunctions(xdata, ydata, peakClass, DEFAULT_OPTIMISER, smoothing, numPeaks);
	}
	/**
	 * Identical method to the above, but returns list of IPeak instead of CompositeFunction (different name to tell apart)
	 */
	public static List<IPeak> fitPeaks(Dataset xdata, Dataset ydata, Class<? extends IPeak> peakClass, int numPeaks) {
		
		return getPeaks(fitPeakFunctions(xdata, ydata, peakClass, numPeaks));
	}

	/**
	 * This method fits peaks to a dataset describing the y values at specified x values. The IPeak function specified
	 * will be returned in the list of FittedPeaks. numPeaks is the maximum number of peaks that will be fitted. The
	 * method to fit the peaks can be specified provided that the method obeys the IOptimizer interface. The smoothing
	 * is the value given when calculating the differential of the data being fitted.
	 * 
	 * @param xdata
	 *            - the x values of the measurements given in ydata
	 * @param ydata
	 *            - the y values corresponding to the x values given
	 * @param peakClass
	 *            - A class that obeys the IPeak interface
	 * @param optimiser
	 *            - An optimisation function that obeys the IOptimizer interface
	 * @param smoothing
	 *            - The value applied to the differential of the dataset before searching for peaks
	 * @param numPeaks
	 *            - The maximum number of peaks that are fitted.
	 * @return list of FittedPeaks - an object that contain the fitted IPeak objects and the corresponding objects that
	 *         describe the region of the data where the peak was found
	 */
	public static List<CompositeFunction> fitPeakFunctions(Dataset xdata, Dataset ydata, Class<? extends IPeak> peakClass,
			IOptimizer optimiser, int smoothing, int numPeaks) {
		return fitPeakFunctions(xdata, ydata, peakClass, optimiser, smoothing, numPeaks, 0.0, false, false);
	}

	/**
	 * This method fits peaks to a dataset describing the y values at specified x values. The IPeak function specified
	 * will be returned in the list of FittedPeaks. numPeaks is the maximum number of peaks that will be fitted. The
	 * method to fit the peaks can be specified provided that the method obeys the IOptimizer interface. The smoothing
	 * is the value given when calculating the differential of the data being fitted. Stopping. Autostopping will stop
	 * the fitting when the threshold is met of the indicated measure. For example, if the threshold is set to 0.10 and
	 * the largest peak is has an area of 100 then the routing will continue to run until the next peak being fitted as
	 * an area of less that 10. At this point the routine will stop and the results from the peaks found will be
	 * displayed.
	 * 
	 * @param xdata
	 *            - the x values that of the measurements given in ydata
	 * @param ydata
	 *            - the y values corresponding to the x values given
	 * @param peakClass
	 *            - A function that obeys the IPeak interface
	 * @param optimiser
	 *            - An optimisation function that obeys the IOptimizer interface
	 * @param smoothing
	 *            - The value applied to the differential of the dataset before searching for peaks
	 * @param numPeaks
	 *            - The maximum number of peaks that are fitted.
	 * @param threshold
	 *            - The stop point for auto stopping
	 * @param autoStopping
	 *            - Boolean to select auto stopping
	 * @param heightMeasure
	 *            - Boolean - true if height is the stopping measure and false if it is area.
	 * @return list of FittedPeaks
	 */
	public static List<CompositeFunction> fitPeakFunctions(Dataset xdata, Dataset ydata, Class<? extends IPeak> peakClass,
			IOptimizer optimiser, int smoothing, int numPeaks, double threshold, boolean autoStopping,
			boolean heightMeasure) {
		return fitPeakFunctions(xdata, ydata, peakClass, optimiser, smoothing, numPeaks, threshold, autoStopping, heightMeasure, null);
	}

	/**
	 * This method fits peaks to a dataset describing the y values at specified x values. The IPeak function specified
	 * will be returned in the list of FittedPeaks. numPeaks is the maximum number of peaks that will be fitted. The
	 * method to fit the peaks can be specified provided that the method obeys the IOptimizer interface. The smoothing
	 * is the value given when calculating the differential of the data being fitted. Stopping. Autostopping will stop
	 * the fitting when the threshold is met of the indicated measure. For example, if the threshold is set to 0.10 and
	 * the largest peak is has an area of 100 then the routing will continue to run until the next peak being fitted as
	 * an area of less that 10. At this point the routine will stop and the results from the peaks found will be
	 * displayed.
	 * 
	 * @param xdata
	 *            - the x values that of the measurements given in ydata
	 * @param ydata
	 *            - the y values corresponding to the x values given
	 * @param peakClass
	 *            - A function that obeys the IPeak interface
	 * @param optimiser
	 *            - An optimisation function that obeys the IOptimizer interface
	 * @param smoothing
	 *            - The value applied to the differential of the dataset before searching for peaks
	 * @param numPeaks
	 *            - The maximum number of peaks that are fitted.
	 * @param threshold
	 *            - The stop point for auto stopping
	 * @param autoStopping
	 *            - Boolean to select auto stopping
	 * @param heightMeasure
	 *            - Boolean - true if height is the stopping measure and false if it is area.
	 * @param monitor 
	 * 			  - IAnalysisMonitor - instance of IAnalysisMonitor class allowing jobs to be stopped
	 * @return list of FittedPeaks or null if job is stopped
	 */
	public static List<CompositeFunction> fitPeakFunctions(Dataset xdata, Dataset ydata, Class<? extends IPeak> peakClass,
			IOptimizer optimiser, int smoothing, int numPeaks, double threshold, boolean autoStopping,
			boolean heightMeasure, IMonitor monitor) {

		return fitPeakFunctions((List<IdentifiedPeak>)null, xdata, ydata, peakClass, optimiser, smoothing, numPeaks,
				threshold, autoStopping, heightMeasure, monitor);
	}
	
	/**
	 * 
	 * @param peaks -  may be null if new peaks are required.
	 * @param xdata
	 * @param ydata
	 * @param peakClass
	 * @param optimiser
	 * @param smoothing
	 * @param numPeaks
	 * @param threshold
	 * @param autoStopping
	 * @param heightMeasure
	 * @param monitor
	 * @return list of peaks
	 */
	public static List<CompositeFunction> fitPeakFunctions(List<IdentifiedPeak> peaks, Dataset xdata, Dataset ydata, Class<? extends IPeak> peakClass,
			IOptimizer optimiser, int smoothing, int numPeaks, double threshold, boolean autoStopping,
			boolean heightMeasure, IMonitor monitor) {

		if (peaks==null) {
			peaks = parseDataDerivative(xdata, ydata, smoothing);
		}
		if (peaks == null || peaks.size() <= 0) {
			logger.error("No peaks found");
			return null;
		}

		List<CompositeFunction> fittedPeaks = fitFunction(peaks, peakClass, xdata, ydata, optimiser, numPeaks, threshold,
				autoStopping, heightMeasure, monitor, FIT_LINEAR_BASELINE);

		return fittedPeaks;
	}
	
	
	/**
	 * Yet another wrapper method. This gives access to fitting the linear background
	 */
	public static List<CompositeFunction> fitPeakFunctions(List<IdentifiedPeak> peaks, Dataset xdata, Dataset ydata, Class<? extends IPeak> peakClass,
			IOptimizer optimiser, int smoothing, int numPeaks, double threshold, boolean autoStopping,
			boolean heightMeasure, IMonitor monitor, boolean fitLinearBaseline) {
		
		if (peaks==null) {
			peaks = parseDataDerivative(xdata, ydata, smoothing);
		}
		if (peaks == null || peaks.size() <= 0) {
			logger.error("No peaks found");
			return null;
		}
		return fitFunction(peaks, peakClass, xdata, ydata, optimiser, numPeaks, threshold,
				autoStopping, heightMeasure, monitor, fitLinearBaseline);
	}

	/**
	 * This is the class which actually does the fitting. Do not make public.
	 */
	private static List<CompositeFunction> fitFunction(List<IdentifiedPeak> initialPeaks, Class<? extends IPeak> peakClass, Dataset xData,
			Dataset ydata, IOptimizer optimiser, int numPeaks, double threshold, boolean autoStopping,
			boolean heightMeasure, IMonitor monitor, boolean fitLinearBaseline) {

		ArrayList<CompositeFunction> peaks = new ArrayList<CompositeFunction>();
		if (numPeaks == 0) {
			numPeaks = initialPeaks.size();
		}
		if (numPeaks < 0) {
			numPeaks = xData.getSize();
		}
		int fittedPeaks = 0;

		for (IdentifiedPeak iniPeak : initialPeaks) {
			if (fittedPeaks++ >= numPeaks) {
				break;
			}
			if (monitor != null) {
				monitor.worked(1);
				if (monitor.isCancelled()) {
					return peaks;
				}
			}

			int[] start = { iniPeak.getIndexOfDatasetAtMinPos() };
			int[] stop = { iniPeak.getIndexOfDatasetAtMaxPos() + 1 };
			int[] step = { 1 };
			
			if (xData.getSize() > 2 && xData.getDouble(0) > xData.getDouble(1)) {
				start[0] = xData.getSize() - start[0] -1;
				stop[0] = xData.getSize() - stop[0];
				
				if (start[0] > stop[0]) {
					int tmp = start[0];
					start[0] = stop[0];
					stop[0] = tmp;
				}
			}
			
			Dataset y = ydata.getSlice(start, stop, step);
			Dataset x = xData.getSlice(start, stop, step);

			AFunction baseline = null;
			try {
				
				if (fitLinearBaseline) {
					double initm = (y.getDouble(0) - y.getDouble(-1))/(x.getDouble(0) - x.getDouble(-1));
					double initc = y.getDouble(0) - initm * x.getDouble(0);
					double stepx = Math.abs(x.getDouble(1) - x.getDouble(0));
					double maxC = y.peakToPeak().doubleValue() / stepx;
					double maxY = y.max().doubleValue();
					baseline = new StraightLine(-maxC, maxC, initc - maxY, initc + maxY);
					baseline.setParameterValues(initm,initc);
				} else {
					double lowOffset = y.min().doubleValue();
					double highOffset = (Double) y.mean();
					baseline = new Offset(lowOffset, highOffset);
				}

				Constructor<? extends IPeak> ctor = peakClass.getConstructor(IdentifiedPeak.class);
				IPeak localPeak = ctor.newInstance(iniPeak);
				CompositeFunction comp = new CompositeFunction();
				comp.addFunction(localPeak);
				comp.addFunction(baseline);
				optimiser.optimize(new Dataset[] { x }, y, comp);

				peaks.add(comp);
			} catch (IllegalArgumentException e1) {
				logger.error("There was a problem optimising the peak", e1);
			} catch (InstantiationException e1) {
				logger.error("The function could not be created for fitting", e1);
			} catch (NoSuchMethodException e1) {
				logger.error("The peak function could not be created.", e1);
			} catch (IllegalAccessException e1) {
				logger.error("The function could not be created for fitting", e1);
			} catch (InvocationTargetException e1) {
				logger.error("The function could not be created for fitting", e1);
			} catch (Exception e) {
				logger.error("There was a problem creating the optimizer.", e);
			}
		}

		if (autoStopping) {
			if (heightMeasure) {
				Collections.sort(peaks, new Comparator<CompositeFunction>() {

					@Override
					public int compare(CompositeFunction o1, CompositeFunction o2) {
						return (int) Math.signum(((IPeak)o1.getFunction(0)).getHeight() - ((IPeak)o2.getFunction(0)).getHeight());
					}
				});
				IPeak p = (IPeak) peaks.get(0).getFunction(0);
				double t = p.getHeight() * threshold;
				for (int i = 1; i < peaks.size(); i++) {
					p = (IPeak) peaks.get(i).getFunction(0);
					if (p.getHeight() < t)
						return peaks.subList(0, i);
				}
			} else {
				Collections.sort(peaks, new Comparator<CompositeFunction>() {

					@Override
					public int compare(CompositeFunction o1, CompositeFunction o2) {
						return (int) Math.signum(((IPeak)o1.getFunction(0)).getArea() - ((IPeak)o2.getFunction(0)).getArea());
					}
				});

				IPeak p = (IPeak) peaks.get(0).getFunction(0);
				double t = p.getArea() * threshold;
				for (int i = 1; i < peaks.size(); i++) {
					p = (IPeak) peaks.get(i).getFunction(0);
					if (p.getArea() < t)
						return peaks.subList(0, i);
				}
			}
		}

		return peaks;
	}

	/**
	 * Find peaks in data
	 * @param xdata
	 * @param ydata
	 * @param smoothing
	 * @return list of identified peaks
	 */
	public static List<IdentifiedPeak> findPeaks(Dataset xdata, Dataset ydata, int smoothing) {
		return parseDataDerivative(xdata, ydata, smoothing);
	}

	public static List<IdentifiedPeak> parseDataDerivative(Dataset xdata, Dataset ydata, int smooth) {
		boolean verbose = true;
		ArrayList<IdentifiedPeak> peaks = new ArrayList<IdentifiedPeak>();

		if (xdata.getSize() > 1 
				&& xdata.getDouble(0) > xdata.getDouble(1) &&
				xdata.getSize() == ydata.getSize()) {
			
			xdata = xdata.getSlice(null, null, new int[]{-1});
			ydata = ydata.getSlice(null, null, new int[]{-1});
		}

		// slightly less arbitrary scale for minimum height of peaks
		final double scale = Math.max(Math.abs(ydata.min(true).doubleValue()), Math.abs(ydata.max(true).doubleValue())) * EPSILON;

		Dataset data = Maths.derivative(xdata, ydata, smooth+1);
		int backPos, forwardPos;
		double backTotal, forwardTotal;
		double backValue, forwardValue;
		for (int i = 0, imax = data.getSize() - 1; i < imax; i++) {
			backPos = i;
			backValue = data.getElementDoubleAbs(backPos);
			forwardPos = backPos + 1;
			forwardValue = data.getElementDoubleAbs(forwardPos);
			if (!(backValue >= 0 && forwardValue < 0)) {
				continue;
			}

			// found zero crossing from positive to negative (maximum)
			// now, work out left and right height differences from local minima or edge
			backTotal = 0;
			// get the backwards points
			while (backPos > 0) {
				if (backValue >= 0) {
					backTotal += backValue;
					backPos -= 1;
					backValue = data.getElementDoubleAbs(backPos);
				} else {
					break;
				}
			}

			// get the forward points
			forwardTotal = 0;
			while (forwardPos < imax) {
				if (forwardValue <= 0) {
					forwardTotal -= forwardValue;
					forwardPos += 1;
					forwardValue = data.getElementDoubleAbs(forwardPos);
				} else {
					break;
				}
			}
			// this means a peak has been found
			if (Math.min(backTotal, forwardTotal) > scale) {
				int[] start = { backPos };
				int[] stop = { forwardPos };
				int[] step = { 1 };
				Dataset slicedXData = xdata.getSlice(start, stop, step);
				Dataset slicedYData = ydata.getSlice(start, stop, step);
				List<Double> crossings = DatasetUtils.crossings(slicedXData, slicedYData, slicedYData.max()
						.doubleValue() / 2);
				if (crossings.size() <= 1) {
					crossings.clear();
					crossings.add((double) backPos);
					crossings.add((double) forwardPos);
				}

				IdentifiedPeak newPeak = new IdentifiedPeak(xdata.getElementDoubleAbs(i), xdata.getElementDoubleAbs(backPos),
						xdata.getElementDoubleAbs(forwardPos), Math.min(backTotal, forwardTotal),
						slicedYData.peakToPeak().doubleValue(), backPos, forwardPos, crossings);
				if (verbose) {
					System.out.printf("Back (%g, %g); Forward (%g, %g); Peak pos %g, height %g, area %g, sum of area %g\n",
							xdata.getElementDoubleAbs(backPos), ydata.getElementDoubleAbs(backPos),
							xdata.getElementDoubleAbs(forwardPos), ydata.getElementDoubleAbs(forwardPos),
							xdata.getElementDoubleAbs(i), slicedYData.max(), Math.min(backTotal, forwardTotal), backTotal + forwardTotal);
				}
				peaks.add(newPeak);
			}
		}
		Collections.sort(peaks, new Compare());

		if (verbose && peaks.size() <= 0) {
			System.err.println("No Peaks Found!!");
		}
		return peaks;
	}

	private static class Compare implements Comparator<IdentifiedPeak> {

		@Override
		public int compare(IdentifiedPeak o1, IdentifiedPeak o2) {
			return (int) Math.signum(o2.getArea() - o1.getArea());
		}

	}

	/**
	 * Extracts the peaks from the CompositeFunction's
	 * @param fitPeakFunctions
	 * @return List<IPeak>
	 */
	private static List<IPeak> getPeaks(List<CompositeFunction> fitPeakFunctions) {
		if (fitPeakFunctions == null)
			return null;
		if (fitPeakFunctions.isEmpty())
			return Collections.emptyList();
		final List<IPeak> ret = new ArrayList<IPeak>(fitPeakFunctions.size());
		for (CompositeFunction function : fitPeakFunctions)
			ret.add(function.getPeak(0));
		return ret;
	}

	/**
	 * Select values in x and y according to if x lies in given range
	 * @param x
	 * @param y - may be null
	 * @param startValue
	 * @param endValue
	 * @return x and y in range
	 */
	public static Dataset[] selectInRange(Dataset x, Dataset y, final double startValue, final double endValue) {
		BooleanDataset allowed = Comparisons.withinRange(x, startValue, endValue);
		if (Comparisons.allTrue(allowed)) {
			return y == null ? new Dataset[] {x} : new Dataset[] {x, y};
		}
		Dataset xs = x.getByBoolean(allowed);
		return y == null ? new Dataset[] {xs} : new Dataset[] {xs, y.getByBoolean(allowed)};
	}
}
