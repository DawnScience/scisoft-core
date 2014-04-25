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

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

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
public class NonPixelSplittingIntegration extends AbstractPixelIntegration1D {
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public NonPixelSplittingIntegration(IDiffractionMetadata metadata, int numBins) {
		super(metadata, numBins);
		
	}

	public List<AbstractDataset> testIntegrateName(AbstractDataset dataset) {
		return integrate(dataset);
	}
	
	/**
	 * @param dataset input dataset
	 * @return a list of 1D datasets which are histograms
	 */
	@Override
	public List<AbstractDataset> integrate(IDataset dataset) {
		//Generate radial and azimuthal look-up arrays as required
		//TODO test shape of axis array
		if (radialArray == null && (radialRange != null || isAzimuthalIntegration())) {
			generateRadialArray(dataset.getShape(), true);
		}
		
		if (azimuthalArray == null && (azimuthalRange != null || !isAzimuthalIntegration())) {
			generateAzimuthalArray(qSpace.getDetectorProperties().getBeamCentreCoords(), dataset.getShape(),true);
		}
		
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

		//check mask and roi
		AbstractDataset mt = mask;
		if (mask != null && !Arrays.equals(mask.getShape(),dataset.getShape())) throw new IllegalArgumentException("Mask shape does not match dataset shape");

		if (roi != null) {
			if (maskRoiCached == null)
				maskRoiCached = mergeMaskAndRoi(dataset.getShape());

			mt = maskRoiCached;
		}
		
		AbstractDataset d = DatasetUtils.convertToAbstractDataset(dataset);
		AbstractDataset a = radialArray[0];
		AbstractDataset r = azimuthalArray != null ? azimuthalArray[0] : null;
		double[] integrationRange = azimuthalRange;
		double[] binRange = radialRange;
		
		if (!isAzimuthalIntegration()) {
			a = azimuthalArray[0];
			r = radialArray != null ? radialArray[0] : null;
			integrationRange = radialRange;
			binRange = azimuthalRange;
		}
		
		if (binArray == null) {
			binArray = calculateBins(new AbstractDataset[] {a},mt,binRange);
		}

		
		//TODO make more generic for azimuthal vs radial integration
		final double[] edges = binArray.getData();
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
			result.add(binArray);
			return result;
		}

		//iterate over dataset, binning values per pixel
		IndexIterator iter = a.getIterator();
		
		while (iter.hasNext()) {
			final double val = a.getElementDoubleAbs(iter.index);
			final double sig = d.getElementDoubleAbs(iter.index);
			
			if (mt != null && !mt.getElementBooleanAbs(iter.index)) continue;
			
			if (integrationRange != null && r != null) {
				final double ra = r.getElementDoubleAbs(iter.index);
				if (ra > integrationRange[1] || ra < integrationRange[0]) continue;
			}

			if (val < lo || val > hi) {
				continue;
			}

			if(((int) ((val-lo)/span))<h.length){
				h[(int) ((val-lo)/span)]++;
				in[(int) ((val-lo)/span)] += sig;
			}
		}
		
		processAndAddToResult(intensity, histo, result, dataset.getName());
		
		return result;
	}
	
}
