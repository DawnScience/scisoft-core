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
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

/**
 * Copy of Histogram class as base for nonpixelsplitting integration
 * 
 * Find histogram of each dataset and return pairs of 1D integer dataset of bin counts
 * and 1D double dataset of bin edges (including rightmost edge).
 * <p>
 * By default, outliers are ignored.
 */
public class NonPixelSplittingIntegration extends AbstractPixelIntegration {
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public NonPixelSplittingIntegration(IDiffractionMetadata metadata, int numBins) {
		super(metadata, numBins);
		
	}
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 * @param lower minimum value of histogram range
	 * @param upper maximum value of histogram range
	 */
	public NonPixelSplittingIntegration(IDiffractionMetadata metadata, int numBins, double lower, double upper)
	{
		super(metadata, numBins, lower, upper);
	}

	/**
	 * @param datasets input datasets
	 * @return a list of 1D datasets which are histograms
	 */
	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;
		
		//TODOtest shape of axis array
		if (radialArray == null) {
			generateRadialArray(datasets[0].getShape(), true);
		}
		
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		for (IDataset ds : datasets) {
			
			AbstractDataset mt = mask;
			if (mask != null && !Arrays.equals(mask.getShape(),ds.getShape())) throw new IllegalArgumentException("Mask shape does not match dataset shape");
			
			AbstractDataset d = DatasetUtils.convertToAbstractDataset(ds);
			AbstractDataset a = radialArray;
			
			
			if (roi != null) {
				if (maskRoiCached == null)
					maskRoiCached = mergeMaskAndRoi(ds.getShape());
				
				mt = maskRoiCached;
			}
			
			if (radialBins == null) {
				calculateBins(a,mt);
			}
			final double[] edges = radialBins.getData();
			final double lo = edges[0];
			final double hi = edges[nbins];
			final double span = (hi - lo)/nbins;
			IntegerDataset histo = new IntegerDataset(nbins);
			DoubleDataset intensity = new DoubleDataset(nbins);
			final int[] h = histo.getData();
			final double[] in = intensity.getData();
			if (span <= 0) {
				h[0] = a.getSize();
				result.add(histo);
				result.add(radialBins);
				continue;
			}

			IndexIterator iter = a.getIterator();

			while (iter.hasNext()) {
				final double val = a.getElementDoubleAbs(iter.index);
				final double sig = d.getElementDoubleAbs(iter.index);
				if (mt != null && !mt.getElementBooleanAbs(iter.index)) continue;
				
				if (val < lo || val > hi) {
					continue;
				}
				
				if(((int) ((val-lo)/span))<h.length){
					h[(int) ((val-lo)/span)]++;
					in[(int) ((val-lo)/span)] += sig;
				}
			}
			
			processAndAddToResult(intensity, histo, result, ds.getName());
		}

		return result;
	}
	
}
