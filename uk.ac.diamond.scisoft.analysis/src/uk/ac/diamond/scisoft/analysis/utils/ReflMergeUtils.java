/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

// @author Andrew McCluskey

package uk.ac.diamond.scisoft.analysis.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.january.DatasetException;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.IntegerDataset;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.dataset.Slice;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

import uk.ac.diamond.scisoft.analysis.dataset.function.Interpolation1D;

public class ReflMergeUtils {
	
	private ReflMergeUtils() {
	    // No-op; won't be called
	}	
	
	/**
	 * Assigns an x-axis dataset to a given dataset. 
	 * 
	 * @param y
	 * 			Dataset to gain x-axis
	 * @param x
	 * 			Dataset x-axis
	 * @return Dataset now including x-axis.
	 */
	public static Dataset setXAxis(Dataset y, Dataset x) throws MetadataException {
		AxesMetadata ax = y.getFirstMetadata(AxesMetadata.class);
		if (ax == null) {
			ax = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		}
		ax.setAxis(0, x);
		y.setMetadata(ax);
		return y;
	}
	
	/**
	 * Returns an x-axis dataset from a given dataset. 
	 * 
	 * @param y
	 * 			Dataset to gain x-axis
	 * @return Dataset x-axis.
	 */
	public static Dataset getXAxis(Dataset y) throws DatasetException {
		Dataset x = null;
		AxesMetadata ax = null;
		ax = y.getFirstMetadata(AxesMetadata.class);
		x = DatasetUtils.sliceAndConvertLazyDataset(ax.getAxes()[0]);
		if (x == null) {
			throw new DatasetException("There was a problem returning the x-axis.");
		}
		return x;
	}
	
	private static Dataset listsToXYDataset(List<Double> y, List<Double> x, List<Double> yE, List<Double> xE) throws MetadataException {
		Dataset newYE = null;
		Dataset newXE = null;
		Dataset newY = DatasetFactory.createFromList(y);
		Dataset newX = DatasetFactory.createFromList(x);
		if (!xE.isEmpty()) {
			newXE = DatasetFactory.createFromList(xE);
			newX.setErrors(newXE);
		}
		if (!yE.isEmpty()) {
			newYE = DatasetFactory.createFromList(yE);
			newY.setErrors(newYE);
		}
		return setXAxis(newY, newX);
	}
	
	// This will correct for the inaccuracy in the transmission correction
	// by reducing the difference between two datasets
	public static Dataset[] correctAttenuation(Dataset[] allData) throws DatasetException {
		Dataset[] newData = new Dataset[allData.length];
		Dataset[] newXTemp = new Dataset[allData.length];
		Dataset[] xTemp = new Dataset[allData.length];
		for (int i = 0; i < allData.length; i++) {
			xTemp[i] = getXAxis(allData[i]);
		}
		
		for (int i = 0; i < allData.length - 1; i++) {
	
			double overlap[] = new double[2];
			overlap[0] = xTemp[i+1].getDouble(0);
			overlap[1] = xTemp[i].getDouble(-1);
			
			int index[] = new int[2];
			index[0] = Maths.abs(Maths.subtract(xTemp[i], overlap[0])).argMin(true);
			index[1] = Maths.abs(Maths.subtract(xTemp[i+1], overlap[1])).argMin(true);
			
			// get slices for the target dataset and the one that will change
			Dataset target = allData[i].getSlice(new Slice(index[0], allData[i].getShapeRef()[0], 1));
			Dataset targetX = xTemp[i].getSlice(new Slice(index[0], allData[i].getShapeRef()[0], 1));
			Dataset vary = allData[i+1].getSlice(new Slice(0, index[1]+1, 1));	
			Dataset varyX = xTemp[i+1].getSlice(new Slice(0, index[1]+1, 1));	

					
			// If the length of the overlap on both the varying and target data is greater than two
			// it is possible to use the spline interpolation to match the data
			if (vary.getShapeRef()[0] > 2 && target.getShapeRef()[0] > 2) {
				// The 0.01 is to offer some wiggle room such that an 
				// OutOfRangeException is not thrown
				Dataset range = DatasetFactory.createLinearSpace(DoubleDataset.class, overlap[0]+0.01, overlap[1]-0.01, 10);
				Dataset targetDataset = Interpolation1D.splineInterpolation(targetX, target, range);
				Dataset varyDataset = Interpolation1D.splineInterpolation(varyX, vary, range);
				
				// The normalisation is by the mean of the vary array divided by the target array
				AxesMetadata ax = allData[i+1].getFirstMetadata(AxesMetadata.class);
				allData[i+1] = ErrorPropagationUtils.divideWithUncertainty(allData[i+1], DatasetFactory.createFromObject(Maths.divide(varyDataset, targetDataset).mean(true)));
				allData[i+1].setMetadata(ax);
			} else {
				// If there is not enough data points for a Spline interpolation we use the simpler
				// normalisation is by the mean of the vary array divided by the mean of the target array
				AxesMetadata ax = allData[i+1].getFirstMetadata(AxesMetadata.class);
				allData[i+1] = ErrorPropagationUtils.divideWithUncertainty(allData[i+1], DatasetFactory.createFromObject(Maths.divide(vary.mean(true), target.mean(true))));
				allData[i+1].setMetadata(ax);
			}
			
			// The first and last points (not including the overall first and last points) are then removed
			// to clean the data up a bit
			if (i == 0) {
				newData[i] = allData[i].getSlice(new Slice(0, -1, 1));
				newXTemp[i] = xTemp[i].getSlice(new Slice(0, -1, 1));
				newData[i] = setXAxis(newData[i], newXTemp[i]);
			}

			newData[i+1] = allData[i+1].getSlice(new Slice(1, allData[i+1].getShapeRef()[0], 1));
			newXTemp[i+1] = xTemp[i+1].getSlice(new Slice(1, allData[i+1].getShapeRef()[0], 1));
			newData[i+1] = setXAxis(newData[i+1], newXTemp[i+1]);
		}

		return newData;
	}
	
	public static Dataset concatenate(Dataset[] allData) throws DatasetException {
		
		Dataset[] x = new Dataset[allData.length];
		Dataset[] yE = new Dataset[allData.length];
		Dataset[] xE = new Dataset[allData.length];
		boolean anyYErrors = true;
		boolean anyXErrors = true;
		
		for (int i = 0; i < allData.length; i++) {
			x[i] = getXAxis(allData[i]);
			if (allData[i].hasErrors()) {
				yE[i] = allData[i].getErrors();
			} else { 
				anyYErrors = false;
			}
			if (x[i].hasErrors()) {
				xE[i] = x[i].getErrors();
			} else {
				anyXErrors = false;
			}
		}
		
		Dataset yNew = DatasetUtils.concatenate(allData, 0);
		Dataset xNew = DatasetUtils.concatenate(x, 0);
		
		if (anyXErrors) {
			Dataset xENew = DatasetUtils.concatenate(xE, 0);
			xNew.setErrors(xENew);
		}
		if (anyYErrors) {
			Dataset yENew = DatasetUtils.concatenate(yE, 0);
			yNew.setErrors(yENew);
		}
		
		return setXAxis(yNew, xNew);		
	}
	
	// This will normalise the total externally reflected component to 1
	public static Dataset normaliseTER(Dataset d) throws DatasetException {
		Dataset dX = getXAxis(d);
		IndexIterator it = d.getIterator();
		double rollingSum = 0;
		double previousRollingAverage = 0;
		int counter = 0;
		while (it.hasNext()) {
			rollingSum += d.getDouble(it.index);
			double rollingAverage = 0;
			if (counter == 0) {
				rollingAverage = rollingSum;
				previousRollingAverage = rollingAverage;
			} else {
				rollingAverage = rollingSum / counter;
			}
			double diffPercent = (rollingAverage - previousRollingAverage) / previousRollingAverage;
			// When a percentage difference of the current and previous rolling average is 
			// less than (or equal) -0.05 of the previous rolling average, this is taken as the 
			// end of the total external reflection
			if (diffPercent <= -0.05) {
				break;
			}
			previousRollingAverage = rollingAverage;
			counter += 1;
		}
		int endOfTIR = it.index;
			
		Dataset dSlice = d.getSlice(new Slice(0, endOfTIR, 1));
		d = ErrorPropagationUtils.divideWithUncertainty(d, DatasetFactory.createFromObject(dSlice.mean()));
		AxesMetadata ax = null;
		try {
			ax = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new MetadataException("There has been an error with Metadata creation.", e);
		}
		d.setMetadata(ax);
		return setXAxis(d, dX);
	}
	
	public static Dataset rebinDatapoints(Dataset data) throws DatasetException {
		Dataset dataX = getXAxis(data);
		// A quarter of the width of the distances between the last two datapoints
		// appears to be a suitable value to ensure that the total external 
		// reflection drop off is caught
		double separation = dataX.getDouble(-1) - dataX.getDouble(-2);
		separation /= 4.;
		double bottom = dataX.getDouble(0);
		int size = (int) (((dataX.getDouble(-1) + separation) - (bottom)) / separation); 
		Dataset newY = DatasetFactory.zeros(DoubleDataset.class, size);
		Dataset newX = DatasetFactory.zeros(DoubleDataset.class, size);
		Dataset newYE = DatasetFactory.zeros(DoubleDataset.class, size);
		Dataset newXE = DatasetFactory.zeros(DoubleDataset.class, size);
		Dataset numberOfHits = DatasetFactory.zeros(IntegerDataset.class, size);
		for (int i = 0; i < dataX.getShapeRef()[0]; i++) {
			int index = (int) (((dataX.getDouble(i)) - (bottom)) / separation); 
			
			newY.set(newY.getDouble(index) + data.getDouble(i), index);
			newX.set(newX.getDouble(index) + dataX.getDouble(i), index);
			if (data.hasErrors()) {
				newYE.set(newYE.getDouble(index) + Math.pow(data.getError(i), 2), index);
			}
			if (dataX.hasErrors()) {
				newXE.set(newXE.getDouble(index) + Math.pow(dataX.getError(i), 2), index);
			}
			numberOfHits.set(numberOfHits.getInt(index) + 1, index);
		}
		
		newY = Maths.divide(newY, numberOfHits);
		newX = Maths.divide(newX, numberOfHits);
		newYE = Maths.divide(Maths.sqrt(newYE), numberOfHits);
		newXE = Maths.divide(Maths.sqrt(newXE), numberOfHits);
		
		// Strip out the NaNs and zeros
		List<Double> y = new ArrayList<>(); 
		List<Double> yE = new ArrayList<>(); 
		List<Double> x = new ArrayList<>(); 
		List<Double> xE = new ArrayList<>(); 
		for (int i = 0; i < newY.getShapeRef()[0]; i++) {
			double j = newY.getDouble(i);
			if (!Double.isNaN(j) && Math.abs(j) > 2 * Double.MIN_VALUE) {
				y.add(j);
				x.add(newX.getDouble(i));
				yE.add(newYE.getDouble(i));
				xE.add(newXE.getDouble(i));
			}
		}
				
		return listsToXYDataset(y, x, yE, xE);
	}
}