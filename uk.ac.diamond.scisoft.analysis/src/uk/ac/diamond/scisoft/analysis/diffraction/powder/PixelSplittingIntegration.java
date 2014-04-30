package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IndexIterator;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

public class PixelSplittingIntegration extends AbstractPixelIntegration1D {
	
	public PixelSplittingIntegration(IDiffractionMetadata metadata) {
		super(metadata);
	}
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public PixelSplittingIntegration(IDiffractionMetadata metadata, int numBins) {
		super(metadata, numBins);
	}

	@Override
	public List<AbstractDataset> integrate (IDataset dataset) {
		
		int[] shape = dataset.getShape();
		
		//Generate radial and azimuthal look-up arrays as required
		//TODO test shape of axis array
		if (radialArray == null && (radialRange != null || isAzimuthalIntegration())) {
			generateMinMaxRadialArray(dataset.getShape());
		}

		if (azimuthalArray == null && (azimuthalRange != null || !isAzimuthalIntegration())) {
			generateMinMaxAzimuthalArray(qSpace.getDetectorProperties().getBeamCentreCoords(),shape);
		}

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();

//		AbstractDataset mt = mask;
//		AbstractDataset dst = DatasetUtils.convertToAbstractDataset(dataset);
//		AbstractDataset axt = radialArray;

		//check mask and roi
		AbstractDataset mt = mask;
		if (mask != null && !Arrays.equals(mask.getShape(),dataset.getShape())) throw new IllegalArgumentException("Mask shape does not match dataset shape");


		if (roi != null) {
			if (maskRoiCached == null)
				maskRoiCached = mergeMaskAndRoi(dataset.getShape());
			mt = maskRoiCached;
		}


		AbstractDataset d = DatasetUtils.convertToAbstractDataset(dataset);
		AbstractDataset[] a = radialArray;
		AbstractDataset[] r = azimuthalArray;
		double[] integrationRange = azimuthalRange;
		double[] binRange = radialRange;
		
		if (!isAzimuthalIntegration()) {
			a = azimuthalArray;
			r = radialArray;
			integrationRange = radialRange;
			binRange = azimuthalRange;
		}
		if (binEdges == null) {
			binEdges = calculateBins(a,mt,binRange, nbins);
		}

		final double[] edges = binEdges.getData();
		final double lo = edges[0];
		final double hi = edges[nbins];
		final double span = (hi - lo)/nbins;
		DoubleDataset histo = new DoubleDataset(nbins);
		DoubleDataset intensity = new DoubleDataset(nbins);
		final double[] h = histo.getData();
		final double[] in = intensity.getData();
		if (span <= 0) {
			h[0] = dataset.getSize();
			result.add(histo);
			result.add(intensity);
			return result;
		}
		

		IndexIterator tIt = a[0].getIterator();


		while (tIt.hasNext()) {
			
			if (mt != null && !mt.getElementBooleanAbs(tIt.index)) continue;
			
			double rangeScale = 1;
			
			if (integrationRange != null && r != null) {
				double rMin = r[0].getElementDoubleAbs(tIt.index);
				double rMax = r[1].getElementDoubleAbs(tIt.index);
				
				if (rMin > integrationRange[1]) continue;
				if (rMax < integrationRange[0]) continue;
				
				double fullRange = rMax-rMin;
				
				rMin = integrationRange[0] > rMin ? integrationRange[0] : rMin;
				rMax = integrationRange[1] < rMax ? integrationRange[1] : rMax;
				
				double reducedRange = rMax-rMin;
				
				rangeScale = reducedRange/fullRange;
				
			}
			
			double sig = d.getElementDoubleAbs(tIt.index);
			double qMin = a[0].getElementDoubleAbs(tIt.index);
			double qMax = a[1].getElementDoubleAbs(tIt.index);

//			double sig = d.getDouble(pos);
			sig *= rangeScale;

			if (qMax < lo && qMin > hi) {
				continue;
			} 

			//losing something here?

			double minBinExact = (qMin-lo)/span;
			double maxBinExact = (qMax-lo)/span;

			int minBin = (int)minBinExact;
			int maxBin = (int)maxBinExact;

			if (minBin == maxBin) {
				h[minBin]++;
				in[minBin] += sig;
			} else {

				double iPerPixel = 1/(maxBinExact-minBinExact);

				double minFrac = 1-(minBinExact-minBin);
				double maxFrac = maxBinExact-maxBin;

				if (minBin >= 0 && minBin < h.length) {
					h[minBin]+=(iPerPixel*minFrac);
					in[minBin] += (sig*iPerPixel*minFrac);
				}

				if (maxBin < h.length && maxBin >=0) {
					h[maxBin]+=(iPerPixel*maxFrac);
					in[maxBin] += (sig*iPerPixel*maxFrac);
				}


				for (int i = (minBin+1); i < maxBin; i++) {
					if (i >= h.length || i < 0) continue; 
					h[i]+=iPerPixel;
					in[i] += (sig*iPerPixel);
				}
			}
		}

		processAndAddToResult(intensity, histo, result,binRange, dataset.getName());

		return result;
	}
}
