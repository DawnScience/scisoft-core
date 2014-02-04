/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.dataset.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Comparisons;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.dataset.function.DatasetToDatasetFunction;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

/**
 * Copy of Histogram class as base for nonpixelsplitting integration
 * 
 * Find histogram of each dataset and return pairs of 1D integer dataset of bin counts
 * and 1D double dataset of bin edges (including rightmost edge).
 * <p>
 * By default, outliers are ignored.
 */
public class NonPixelSplittingIntegration implements DatasetToDatasetFunction {
	private int nbins;
	private Double min = null;
	private Double max = null;
	private DoubleDataset bins = null;
	private AbstractDataset qArray;
	private QSpace qSpace = null;

	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public NonPixelSplittingIntegration(QSpace qSpace, int numBins) {
		this.qSpace = qSpace;
		nbins = numBins;
		
	}
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public NonPixelSplittingIntegration(AbstractDataset qArray, int numBins)
	{
		nbins = numBins;
		this.qArray = qArray;
	}
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 * @param lower minimum value of histogram range
	 * @param upper maximum value of histogram range
	 */
	public NonPixelSplittingIntegration(AbstractDataset qArray, int numBins, double lower, double upper)
	{
		this(qArray, numBins);
		min = lower;
		max = upper;
		if (min > max) {
			throw new IllegalArgumentException("Given lower bound was higher than upper bound");
		}

		bins = (DoubleDataset) DatasetUtils.linSpace(min, max, nbins + 1, AbstractDataset.FLOAT64);
	}

	/**
	 * @param datasets input datasets
	 * @return a list of 1D datasets which are histograms
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;
		
		if (qArray == null) {
			
			if (qSpace == null) return null;

			qArray = AbstractDataset.zeros(DatasetUtils.convertToAbstractDataset(datasets[0]), AbstractDataset.FLOAT64);
			
			PositionIterator iter = qArray.getPositionIterator();
			int[] pos = iter.getPos();

			while (iter.hasNext()) {
				qArray.set(qSpace.qFromPixelPosition(pos[1]+0.5, pos[0]+0.5).length(), pos);
			}
		}
		
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		for (IDataset ds : datasets) {
			if (bins == null) {
				bins = (DoubleDataset) DatasetUtils.linSpace(qArray.min().doubleValue(), qArray.max().doubleValue(), nbins + 1, AbstractDataset.FLOAT64);
			}
			final double[] edges = bins.getData();
			final double lo = edges[0];
			final double hi = edges[nbins];
			final double span = (hi - lo)/nbins;
			IntegerDataset histo = new IntegerDataset(nbins);
			DoubleDataset intensity = new DoubleDataset(nbins);
			final int[] h = histo.getData();
			final double[] in = intensity.getData();
			if (span <= 0) {
				h[0] = qArray.getSize();
				result.add(histo);
				result.add(bins);
				continue;
			}

			AbstractDataset a = DatasetUtils.convertToAbstractDataset(qArray);
			AbstractDataset b = DatasetUtils.convertToAbstractDataset(ds);
			IndexIterator iter = a.getIterator();

			while (iter.hasNext()) {
				final double val = a.getElementDoubleAbs(iter.index);
				final double sig = b.getElementDoubleAbs(iter.index);
				if (val < lo && val > hi) {
					continue;
				} else {
					if(((int) ((val-lo)/span))<h.length){
						h[(int) ((val-lo)/span)]++;
						in[(int) ((val-lo)/span)] += sig;
					}
				}
			}
			
			AbstractDataset axis = Maths.add(bins.getSlice(new int[]{1}, null ,null), bins.getSlice(null, new int[]{-1},null));
			axis.idivide(2);
			
			axis.setName("q");
			
			result.add(axis);
			AbstractDataset out = Maths.divide(intensity, histo);
			out.setName(ds.getName() + "_integrated");
			result.add(out);
		}

		return result;
	}

	/**
	 * Set minimum and maximum edges of histogram bins
	 * @param min
	 * @param max
	 */
	public void setMinMax(double min, double max) {
		this.min = min;
		this.max = max;
		bins = (DoubleDataset) DatasetUtils.linSpace(min, max, nbins + 1, AbstractDataset.FLOAT64);		
	}
	
}
