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
import java.util.List;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.Dataset;
import uk.ac.diamond.scisoft.analysis.dataset.DatasetUtils;
import uk.ac.diamond.scisoft.analysis.dataset.DoubleDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IntegerDataset;
import uk.ac.diamond.scisoft.analysis.io.IDiffractionMetadata;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

/**
 * Copy of Histogram class as base for nonpixelsplitting integration
 * 
 * Find histogram of each dataset and return pairs of 1D integer dataset of bin counts
 * and 1D double dataset of bin edges (including rightmost edge).
 * <p>
 * By default, outliers are ignored.
 */
public class NonPixelSplittingIntegrationGPU extends AbstractPixelIntegration {
	
	/**
	 * Constructor of the Histogram
	 * @param numBins number of bins
	 */
	public NonPixelSplittingIntegrationGPU(IDiffractionMetadata metadata, int numBins) {
		super(metadata, numBins);
		
	}
	
	/**
	 * @param dataset input dataset
	 * @return a list of 1D datasets which are histograms
	 */
	@Override
	public List<AbstractDataset> integrate(IDataset dataset) {
		
		if (radialArray == null) {
			
			generateRadialArray(dataset.getShape(), true);
			
		}
		
		List<AbstractDataset> result = new ArrayList<AbstractDataset>();
		if (binEdges == null) {
			binEdges = (DoubleDataset) DatasetUtils.linSpace(radialArray[0].min().doubleValue(), radialArray[0].max().doubleValue(), nbins + 1, Dataset.FLOAT64);
		}
		final double[] edges = binEdges.getData();
		final double lo = edges[0];
		final double hi = edges[nbins];
		final double span = (hi - lo)/nbins;
		IntegerDataset histo = new IntegerDataset(nbins);
		DoubleDataset intensity = new DoubleDataset(nbins);
		final int[] h = histo.getData();
		final double[] in = intensity.getData();
		if (span <= 0) {
			h[0] = radialArray[0].getSize();
			result.add(histo);
			result.add(binEdges);
			return result;
		}

		AbstractDataset a = DatasetUtils.convertToAbstractDataset(radialArray[0]);
		AbstractDataset b = DatasetUtils.convertToAbstractDataset(dataset);

		Range range = Range.create(a.getSize()); 

		// Kernel copies primitive arrays to the GPU for us.

		/**
		 * This is not a serious go at speeding up using GPU. This is because the
		 * maths is simple and the memory copy large, therefore it will not give a 
		 * speed up. This is just to show Jake how to use the Kernel object.
		 * 
		 * In practice this particular loop is better speeded up with a fork join or
		 * with Java8 lambda run on a parallel stream.
		 * 
		 * You will need to install a GPU driver to have this run on the GPU. Also the
		 * NonPixelSplittingKernel might not compile properly, check the output messages
		 * when the test runs.
		 */
		NonPixelSplittingKernel kernel = new NonPixelSplittingKernel();
		kernel.setLow(lo);
		kernel.setHi(hi);
		kernel.setSpan(span);
		kernel.setA((double[])a.getBuffer());
		kernel.setB((float[])b.getBuffer());
		kernel.setIntensity(in);
		kernel.setHisto(h);

		kernel.execute(range);

		processAndAddToResult(new DoubleDataset(kernel.getIntensity(), nbins), 
				new IntegerDataset(kernel.getHisto(), nbins), result,null,  dataset.getName());

		return result;
	}
	
    private class NonPixelSplittingKernel extends Kernel {
    	
       	private double[] a;
      	private float[] b;
     	private double[] intensity;
     	private int[] histo;
      	private double lo, hi, span;

		public int[] getHisto() {
			return histo;
		}

		public void setHisto(int[] histo) {
			this.histo = histo;
		}

		public double[] getIntensity() {
			return intensity;
		}

		public void setIntensity(double[] intensity) {
			this.intensity = intensity;
		}

		public void setLow(double low) {
			this.lo = low;
		}

		public void setHi(double hi) {
			this.hi = hi;
		}
		public void setSpan(double span) {
			this.span = span;
		}

		public void setA(double[] a) {
			this.a = a;
		}

		public void setB(float[] b) {
			this.b = b;
		}

		@Override
		public void run() {
			int i     = getGlobalId();
			final double val = a[i];
			final double sig = b[i];
			if (val < lo && val > hi) {
				return;
			}
			if(((int) ((val-lo)/span))<histo.length){
				histo[(int) ((val-lo)/span)]++;
				intensity[(int) ((val-lo)/span)] += sig;
			}
			
		}
    	
    }
	
}
