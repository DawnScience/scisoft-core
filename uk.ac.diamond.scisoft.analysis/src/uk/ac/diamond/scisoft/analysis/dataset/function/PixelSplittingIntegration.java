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
import uk.ac.diamond.scisoft.analysis.dataset.Maths;
import uk.ac.diamond.scisoft.analysis.dataset.PositionIterator;
import uk.ac.diamond.scisoft.analysis.dataset.function.DatasetToDatasetFunction;
import uk.ac.diamond.scisoft.analysis.diffraction.QSpace;

public class PixelSplittingIntegration implements DatasetToDatasetFunction {
	
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
	public PixelSplittingIntegration(QSpace qSpace, int numBins) {
		this.qSpace = qSpace;
		nbins = numBins;
		
	}

	@Override
	public List<AbstractDataset> value(IDataset... datasets) {
		if (datasets.length == 0)
		return null;
	
	if (qArray == null) {
		
		if (qSpace == null) return null;

		int[] shape = datasets[0].getShape();
		
		//make one larger to know range of q for lower and right hand edges
		shape[0]++;
		shape[1]++;
		
		qArray = AbstractDataset.zeros(shape, AbstractDataset.FLOAT64);
		
		PositionIterator iter = qArray.getPositionIterator();
		int[] pos = iter.getPos();

		while (iter.hasNext()) {
			qArray.set(qSpace.qFromPixelPosition(pos[1], pos[0]).length(), pos);
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
		DoubleDataset histo = new DoubleDataset(nbins);
		DoubleDataset intensity = new DoubleDataset(nbins);
		final double[] h = histo.getData();
		final double[] in = intensity.getData();
		if (span <= 0) {
			h[0] = ds.getSize();
			result.add(histo);
			result.add(bins);
			continue;
		}

		AbstractDataset a = DatasetUtils.convertToAbstractDataset(qArray);
		AbstractDataset b = DatasetUtils.convertToAbstractDataset(ds);
		PositionIterator iter = b.getPositionIterator();
		
		int[] pos = iter.getPos();
		int[] posStop = pos.clone();

		while (iter.hasNext()) {
			
			posStop[0] = pos[0]+2;
			posStop[1] = pos[1]+2;
			AbstractDataset qrange = a.getSlice(pos, posStop, null);
			
			final double qMax = (Double)qrange.max();
			final double qMin = (Double)qrange.min();
			
			final double sig = b.getDouble(pos);
			
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
				
				if (minBin >= 0) {
					h[minBin]+=(iPerPixel*minFrac);
					in[minBin] += (sig*iPerPixel*minFrac);
				}
				
				if (maxBin < h.length) {
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
		
		AbstractDataset axis = Maths.add(bins.getSlice(new int[]{1}, null ,null), bins.getSlice(null, new int[]{-1},null));
		axis.idivide(2);
		
		result.add(axis);
		result.add(Maths.divide(intensity, histo));
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
