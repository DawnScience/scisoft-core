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
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.dataset.Slice;
import uk.ac.diamond.scisoft.analysis.dataset.function.DatasetToDatasetFunction;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

public class PixelSplittingIntegration extends AbstractPixelIntegration {

	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public PixelSplittingIntegration(IDiffractionMetadata metadata, int numBins) {
		super(metadata, numBins);
	}

	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
			return null;

		if (radialArray == null) {

			if (qSpace == null) return null;

			int[] shape = datasets[0].getShape();

			//make one larger to know range of q for lower and right hand edges
			shape[0]++;
			shape[1]++;
			
			generateRadialArray(shape, false);

		}

		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		for (IDataset ds : datasets) {
			
			AbstractDataset mt = mask;
			AbstractDataset dst = DatasetUtils.convertToAbstractDataset(ds);
			AbstractDataset axt = radialArray;
			
			
			if (roi != null) {
				if (maskRoiCached == null)
					maskRoiCached = mergeMaskAndRoi(ds.getShape());
				mt = maskRoiCached;
			}
			
			
			if (radialBins == null) {
				calculateBins(axt,mt);
			}
			
			final double[] edges = radialBins.getData();
			final double lo = edges[0];
			final double hi = edges[nbins];
			final double span = (hi - lo)/nbins;
			DoubleDataset histo = new DoubleDataset(nbins);
			DoubleDataset intensity = new DoubleDataset(nbins);
			final double[] h = histo.getData();
			final double[] in = intensity.getData();
			if (span <= 0) {
				h[0] = ds.getSize();
				result.add(histo);
				result.add(radialBins);
				continue;
			}

//			AbstractDataset a = DatasetUtils.convertToAbstractDataset(axisArray);
//			AbstractDataset b = DatasetUtils.convertToAbstractDataset(ds);
			PositionIterator iter = dst.getPositionIterator();

			int[] pos = iter.getPos();
			int[] posStop = pos.clone();

			while (iter.hasNext()) {
				
				if (mt != null && !mt.getBoolean(pos)) continue;
				
				posStop[0] = pos[0]+2;
				posStop[1] = pos[1]+2;
				AbstractDataset qrange = axt.getSlice(pos, posStop, null);

				final double qMax = (Double)qrange.max();
				final double qMin = (Double)qrange.min();

				final double sig = dst.getDouble(pos);

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

			processAndAddToResult(intensity, histo, result, ds.getName());
			
		}

		return result;
	}
}
