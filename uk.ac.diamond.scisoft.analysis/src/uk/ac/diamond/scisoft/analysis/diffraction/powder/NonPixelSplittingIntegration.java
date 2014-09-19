/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.IDiffractionMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;

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
	 */
	public NonPixelSplittingIntegration(IDiffractionMetadata metadata) {
		super(metadata);
	}
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public NonPixelSplittingIntegration(IDiffractionMetadata metadata, int numBins) {
		super(metadata, numBins);
		
	}
	
	/**
	 * @param dataset input dataset
	 * @return a list of 1D datasets which are histograms
	 */
	@Override
	public List<Dataset> integrate(IDataset dataset) {
		//Generate radial and azimuthal look-up arrays as required
		//TODO test shape of axis array
		if (radialArray == null && (radialRange != null || isAzimuthalIntegration())) {
			generateRadialArray(dataset.getShape(), true);
		}
		
		if (azimuthalArray == null && (azimuthalRange != null || !isAzimuthalIntegration())) {
			generateAzimuthalArray(qSpace.getDetectorProperties().getBeamCentreCoords(), dataset.getShape());
		}
		
		List<Dataset> result = new ArrayList<Dataset>();

		//check mask and roi
		Dataset mt = mask;
		if (mask != null && !Arrays.equals(mask.getShape(),dataset.getShape())) throw new IllegalArgumentException("Mask shape does not match dataset shape");

		if (roi != null) {
			if (maskRoiCached == null)
				maskRoiCached = mergeMaskAndRoi(dataset.getShape());
			mt = maskRoiCached;
		}
		
		Dataset d = DatasetUtils.convertToDataset(dataset);
		Dataset a = radialArray != null ? radialArray[0] : null;
		Dataset r = azimuthalArray != null ? azimuthalArray[0] : null;
		double[] integrationRange = azimuthalRange;
		double[] binRange = radialRange;
		
		if (!isAzimuthalIntegration()) {
			a = azimuthalArray[0];
			r = radialArray != null ? radialArray[0] : null;
			integrationRange = radialRange;
			binRange = azimuthalRange;
		}
		
		if (binEdges == null) {
			binEdges = calculateBins(new Dataset[] {a},mt,binRange, nbins);
		}

		
		//TODO make more generic for azimuthal vs radial integration
		final double[] edges = binEdges.getData();
		final double lo = edges[0];
		final double hi = edges[nbins];
		final double span = (hi - lo)/nbins;
		IntegerDataset histo = new IntegerDataset(nbins);
		DoubleDataset intensity = new DoubleDataset(nbins);
		final int[] h = histo.getData();
		final double[] in = intensity.getData();
		if (span <= 0 || a == null) {
			h[0] = dataset.getSize();
			result.add(histo);
			result.add(intensity);
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
		
		processAndAddToResult(intensity, histo, result, binRange, dataset.getName());

		
		return result;
	}
	
}
