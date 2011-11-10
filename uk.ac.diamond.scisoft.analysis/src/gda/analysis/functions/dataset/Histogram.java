/*-
 * Copyright © 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package gda.analysis.functions.dataset;

import gda.analysis.DataSet;

import java.util.ArrayList;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;

/**
 * Find histogram of each dataset and return a 1D dataset of bin counts.
 * <p>
 * By default, outliers are ignored.
 */
public class Histogram implements IDataSetFunction {

	
	private int numBuckets;
	private boolean overwriteMinMax = false;
	private boolean ignoreOutliers = true;
	private double min;
	private double max;
	/**
	 * Constructor of the Histogram DataSetFunction
	 * @param numBuckets number of buckets
	 */
	
	public Histogram(int numBuckets)
	{
		this.numBuckets = numBuckets;
		overwriteMinMax = false;
		ignoreOutliers = true;
	}
	
	/**
	 * Constructor of the Histogram DataSetFunction
	 * @param numBuckets number of buckets
	 * @param overwriteMin overwrite default minimum in the DataSet
	 * @param overwriteMax overwrite default maximum in the DataSet
	 */
	
	public Histogram(int numBuckets, double overwriteMin, double overwriteMax)
	{
		this(numBuckets);
		overwriteMinMax = true;
		min = overwriteMin;
		max = overwriteMax;
	}
	
	/**
	 * Constructor of the Histogram DataSetFunction
	 * @param numBuckets number of buckets
	 * @param overwriteMin overwrite default minimum in the DataSet
	 * @param overwriteMax overwrite default maximum in the DataSet
	 * @param ignore should outliers be ignored? (Outliers are values that lie outside the min..max range)
	 */
	
	public Histogram(int numBuckets, double overwriteMin, double overwriteMax, boolean ignore)
	{
		this(numBuckets,overwriteMin,overwriteMax);
		ignoreOutliers = ignore;
	}	
	
	
	@Override
	public List<DataSet> execute(DataSet callingDataSet) {
	    DataSet returnSet = DataSet.zeros(numBuckets);
	    double[] buckets = returnSet.getData();

		double maxValue = callingDataSet.max();
		double minValue = callingDataSet.min();
		if (overwriteMinMax)
		{
			maxValue = max;
			minValue = min;
		}
		double bucketSpan = (maxValue-minValue) / (numBuckets-1);
		IndexIterator iter = callingDataSet.getIterator();
	    while (iter.hasNext())
	    {
	    	boolean insert = true;
	    	double value = callingDataSet.getAbs(iter.index);
	    	if (value < minValue || value > maxValue)
	    	{
	    		if (!ignoreOutliers)
	    		{
	    			value = Math.min(value, maxValue);
	    			value = Math.max(value, minValue);
	    		} else insert = false;
	    	}
	    	if (insert)
	    	{
	    		int bucketNr = (int)((value-minValue) / bucketSpan);
	    		buckets[bucketNr]++;
	    	}
	    }
	    ArrayList<DataSet> result = new ArrayList<DataSet>();
	    result.add(returnSet);
		return result;
	}
	
	/**
	 * Set a new overwrite min
	 * @param newMin
	 */
	public void setMin(double newMin)
	{
		min = newMin;
	}
	
	/**
	 * Set a new overwrite max
	 * @param newMax
	 */
	public void setMax(double newMax)
	{
		max = newMax;
	}

	/**
	 * Set overwrite flag
	 * @param overwrite should min / max in the DataSet be overwritten by custom min/max?
	 */
	public void setOverwriteMinMax(boolean overwrite)
	{
		overwriteMinMax = overwrite;
	}
	
	/**
	 * Set if outliers between min..max should be ignored
	 * @param ignore should outliers be ignored?
	 */

	public void setIgnoreOutliers(boolean ignore)
	{
		ignoreOutliers = ignore;
	}
}
