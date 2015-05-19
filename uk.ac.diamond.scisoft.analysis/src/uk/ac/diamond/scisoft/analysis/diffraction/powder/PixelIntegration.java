/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction.powder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetFactory;
import org.eclipse.dawnsci.analysis.dataset.impl.DatasetUtils;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.FloatDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.IndexIterator;
import org.eclipse.dawnsci.analysis.dataset.impl.IntegerDataset;

public class PixelIntegration {

	public static List<Dataset> integrate(IDataset data, IDataset mask, IPixelIntegrationCache bean) {
		
		if (bean.isTo1D()) {
			if (bean.isPixelSplitting()) return pixelSplitting1D(data, mask, bean);
			return nonPixelSplitting1D(data, mask, bean);
		} 
		
		if (bean.isPixelSplitting()) return pixelSplitting2D(data, mask, bean);
		return nonPixelSplitting2D(data, mask, bean);
		
	}
	
	private static List<Dataset> nonPixelSplitting1D(IDataset data, IDataset mask, IPixelIntegrationCache bean) {
		
		List<Dataset> result = new ArrayList<Dataset>();
		
		Dataset d = DatasetUtils.convertToDataset(data);
		Dataset e = d.getError();
		
		int nbins = bean.getNumberOfBinsXAxis();
		
		final double lo = bean.getXBinEdgeMin();
		final double hi = bean.getXBinEdgeMax();
		final double span = (hi - lo)/bean.getNumberOfBinsXAxis();
		IntegerDataset histo = new IntegerDataset(nbins);
		DoubleDataset intensity = new DoubleDataset(nbins);
		DoubleDataset error = null;
		double[] eb = null;
		if (e != null) {
			error = new DoubleDataset(nbins);
			eb = error.getData();
		}
		
		final int[] h = histo.getData();
		final double[] in = intensity.getData();
		
		Dataset a = bean.getXAxisArray()[0];
		
		if (span <= 0 || a == null) {
			h[0] = data.getSize();
			result.add(histo);
			result.add(intensity);
			return result;
		}
		
		double[] integrationRange = bean.getYAxisRange();
		Dataset m = DatasetUtils.convertToDataset(mask);
		Dataset r =  null;
		if (bean.getYAxisArray() != null) r = bean.getYAxisArray()[0];

		//iterate over dataset, binning values per pixel
		IndexIterator iter = a.getIterator();

		while (iter.hasNext()) {
			final double val = a.getElementDoubleAbs(iter.index);
			final double sig = d.getElementDoubleAbs(iter.index);
			
			if (m != null && !m.getElementBooleanAbs(iter.index)) continue;
			
			if (integrationRange != null && r != null) {
				final double ra = r.getElementDoubleAbs(iter.index);
				if (ra > integrationRange[1] || ra < integrationRange[0]) continue;
			}

			if (val < lo || val > hi) {
				continue;
			}

			int p = (int) ((val-lo)/span);
			
			if(p < h.length){
				h[p]++;
				in[p] += sig;
				if (e!=null) {
					final double std = e.getElementDoubleAbs(iter.index);
					eb[p] += (std*std);
				}
			}
		}
		
		if (eb != null) intensity.setErrorBuffer(eb);
		
		intensity.setName(data.getName() + "_integrated");
		
		processAndAddToResult(intensity, histo, result, bean,false);
		
		return result;
		
	}
	
	private static List<Dataset> pixelSplitting1D(IDataset data, IDataset mask, IPixelIntegrationCache bean){
		
		List<Dataset> result = new ArrayList<Dataset>();
		
		Dataset d = DatasetUtils.convertToDataset(data);
		Dataset e = d.getError();
		
		int nbins = bean.getNumberOfBinsXAxis();
		
		final double lo = bean.getXBinEdgeMin();
		final double hi = bean.getXBinEdgeMax();
		final double span = (hi - lo)/bean.getNumberOfBinsXAxis();
		DoubleDataset histo = new DoubleDataset(nbins);
		DoubleDataset intensity = new DoubleDataset(nbins);
		DoubleDataset error = null;
		double[] eb = null;
		if (e != null) {
			error = new DoubleDataset(nbins);
			eb = error.getData();
		}
		
		final double[] h = histo.getData();
		final double[] in = intensity.getData();
		
		Dataset[] a= bean.getXAxisArray();
		
		if (span <= 0 || a == null) {
			h[0] = data.getSize();
			result.add(histo);
			result.add(intensity);
			return result;
		}
		
		double[] integrationRange = bean.getYAxisRange();
		Dataset[] r = bean.getYAxisArray();
		Dataset m = DatasetUtils.convertToDataset(mask);

		//iterate over dataset, binning values per pixel
		IndexIterator iter = a[0].getIterator();
		
		while (iter.hasNext()) {

			if (m != null && !m.getElementBooleanAbs(iter.index)) continue;

			double rangeScale = 1;

			if (integrationRange != null && r != null) {
				double rMin = r[0].getElementDoubleAbs(iter.index);
				double rMax = r[1].getElementDoubleAbs(iter.index);

				if (rMin > integrationRange[1]) continue;
				if (rMax < integrationRange[0]) continue;

				double fullRange = rMax-rMin;

				rMin = integrationRange[0] > rMin ? integrationRange[0] : rMin;
				rMax = integrationRange[1] < rMax ? integrationRange[1] : rMax;

				double reducedRange = rMax-rMin;

				rangeScale = reducedRange/fullRange;

			}

			double sig = d.getElementDoubleAbs(iter.index);
			double qMin = a[0].getElementDoubleAbs(iter.index);
			double qMax = a[1].getElementDoubleAbs(iter.index);

			//			double sig = d.getDouble(pos);
			sig *= rangeScale;

			if (qMax < lo || qMin > hi) {
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
				
				if (e!=null) {
					final double std = e.getElementDoubleAbs(iter.index);
					eb[minBin] += (std*std);
				}
				
			} else {

				double iPerPixel = 1/(maxBinExact-minBinExact);

				double minFrac = 1-(minBinExact-minBin);
				double maxFrac = maxBinExact-maxBin;

				if (minBin >= 0 && minBin < h.length) {
					h[minBin]+=(iPerPixel*minFrac);
					in[minBin] += (sig*iPerPixel*minFrac);
					
					if (e!=null) {
						final double std = e.getElementDoubleAbs(iter.index)*iPerPixel*minFrac;
						eb[minBin] += (std*std);
					}
				}

				if (maxBin < h.length && maxBin >=0) {
					h[maxBin]+=(iPerPixel*maxFrac);
					in[maxBin] += (sig*iPerPixel*maxFrac);
					
					if (e!=null) {
						final double std = e.getElementDoubleAbs(iter.index)*iPerPixel*maxFrac;
						eb[maxBin] += (std*std);
					}
				}


				for (int i = (minBin+1); i < maxBin; i++) {
					if (i >= h.length || i < 0) continue; 
					h[i]+=iPerPixel;
					in[i] += (sig*iPerPixel);
					if (e!=null) {
						final double std = e.getElementDoubleAbs(iter.index)*iPerPixel;
						eb[i] += (std*std);
					}
				}
			}
		}
		
		if (eb != null) intensity.setErrorBuffer(eb);
		
		processAndAddToResult(intensity, histo, result, bean,false);
		
		return result;
	}
	
	private static List<Dataset> nonPixelSplitting2D(IDataset data, IDataset mask, IPixelIntegrationCache bean) {

		List<Dataset> result = new ArrayList<Dataset>();
		
		final double loQ = bean.getXBinEdgeMin();
		final double hiQ = bean.getXBinEdgeMax();
		final double spanQ = (hiQ - loQ)/bean.getNumberOfBinsXAxis();

		final double loChi = bean.getYBinEdgeMin();
		final double hiChi = bean.getYBinEdgeMax();
		final double spanChi = (hiChi - loChi)/bean.getNumberOfBinsYAxis();

		//TODO early exit if spans are z
		final int nXBins = bean.getNumberOfBinsXAxis();
		final int nYBins = bean.getNumberOfBinsYAxis();

		IntegerDataset histo = (IntegerDataset) DatasetFactory.zeros(new int[]{nYBins,nXBins}, Dataset.INT32);
		FloatDataset intensity = (FloatDataset) DatasetFactory.zeros(new int[]{nYBins,nXBins},Dataset.FLOAT32);

		Dataset x = DatasetUtils.convertToDataset(bean.getXAxisArray()[0]);
		Dataset y = DatasetUtils.convertToDataset(bean.getYAxisArray()[0]);
		Dataset b = DatasetUtils.convertToDataset(data);
		Dataset m = DatasetUtils.convertToDataset(mask);
		IndexIterator iter = x.getIterator();

		while (iter.hasNext()) {

			final double valq = x.getElementDoubleAbs(iter.index);
			final double sig = b.getElementDoubleAbs(iter.index);
			final double chi = y.getElementDoubleAbs(iter.index);
			if (m != null && !m.getElementBooleanAbs(iter.index)) continue;

			if (valq < loQ || valq > hiQ) {
				continue;
			}

			if (chi < loChi || chi > hiChi) {
				continue;
			}

			int qPos = (int) ((valq-loQ)/spanQ);
			int chiPos = (int) ((chi-loChi)/spanChi);

			if(qPos<nXBins && chiPos<nYBins){
				int cNum = histo.get(chiPos,qPos);
				float cIn = intensity.get(chiPos,qPos);
				histo.set(cNum+1, chiPos,qPos);
				intensity.set(cIn+sig, chiPos,qPos);
			}

		}

		processAndAddToResult(intensity, histo, result,bean, true);

		return result;
		
	}
	
	
	private static List<Dataset> pixelSplitting2D(IDataset data, IDataset mask, IPixelIntegrationCache bean) {
		
		List<Dataset> result = new ArrayList<Dataset>();
		
		final int nXBins = bean.getNumberOfBinsXAxis();
		final int nYBins = bean.getNumberOfBinsYAxis();
		
		final double loQ = bean.getXBinEdgeMin();
		final double hiQ = bean.getXBinEdgeMax();
		final double spanQ = (hiQ - loQ)/nXBins;

		final double loChi = bean.getYBinEdgeMin();
		final double hiChi = bean.getYBinEdgeMax();
		final double spanChi = (hiChi - loChi)/nYBins;

		FloatDataset histo = new FloatDataset(nYBins, nXBins);
		FloatDataset intensity = new FloatDataset(nYBins, nXBins);
		//			final double[] h = histo.getData();
		//			final double[] in = intensity.getData();
		//			if (spanQ <= 0) {
		//				h[0] = ds.getSize();
		//				result.add(histo);
		//				result.add(bins);
		//				continue;
		//			}

		Dataset x0 = bean.getXAxisArray()[0];
		Dataset x1 = bean.getXAxisArray()[1];
		Dataset y0 = bean.getYAxisArray()[0];
		Dataset y1 = bean.getYAxisArray()[1];
		Dataset d = DatasetUtils.convertToDataset(data);
		
		Dataset m = DatasetUtils.convertToDataset(mask);
		
		IndexIterator iter = x0.getIterator();

		while (iter.hasNext()) {

			if (m != null && !m.getElementBooleanAbs(iter.index)) continue;
			final double qMax = x1.getElementDoubleAbs(iter.index);
			final double qMin = x0.getElementDoubleAbs(iter.index);
			final double chiMax = y1.getElementDoubleAbs(iter.index);
			final double chiMin = y0.getElementDoubleAbs(iter.index);

			final double sig = d.getElementDoubleAbs(iter.index);

			if (qMax < loQ || qMin > hiQ) {
				continue;
			} 

			if (chiMax < loChi || chiMin > hiChi) {
				continue;
			} 

			//losing something here? is flooring (int cast best?)

			double minBinExactQ = (qMin-loQ)/spanQ;
			double maxBinExactQ = (qMax-loQ)/spanQ;
			int minBinQ = (int)minBinExactQ;
			int maxBinQ = (int)maxBinExactQ;

			double minBinExactChi = (chiMin-loChi)/spanChi;
			double maxBinExactChi = (chiMax-loChi)/spanChi;
			int minBinChi = (int)minBinExactChi;
			int maxBinChi = (int)maxBinExactChi;

			//FIXME potentially may need to deal with azimuthal arrays with discontinuities (+180/-180 degress etc)

			double iPerPixel = 1/((maxBinExactQ-minBinExactQ)*(maxBinExactChi-minBinExactChi));
			double minFracQ = 1-(minBinExactQ-minBinQ);
			double maxFracQ = maxBinExactQ-maxBinQ;
			double minFracChi = 1-(minBinExactChi-minBinChi);
			double maxFracChi = maxBinExactChi-maxBinChi;

			for (int i = minBinQ ; i <= maxBinQ; i++) {
				if (i < 0 || i >= nXBins) continue;
				for (int j = minBinChi; j <= maxBinChi; j++) {
					if (j < 0 || j >= nYBins) continue;

					int[] setPos = new int[]{j,i};
					double val = histo.get(setPos);

					double modify = 1;

					if (i == minBinQ && minBinQ != maxBinQ) modify *= minFracQ;
					if (i == maxBinQ && minBinQ != maxBinQ) modify *= maxFracQ;
					if (i == minBinChi && minBinChi != maxBinChi) modify *= minFracChi;
					if (i == maxBinChi && minBinChi != maxBinChi) modify *= maxFracChi;

					histo.set(val+iPerPixel*modify, setPos);
					double inVal = intensity.get(setPos);
					intensity.set(inVal+sig*iPerPixel*modify, setPos);
				}

			}
		}

		processAndAddToResult(intensity, histo, result, bean, true);
		
		return result;
	}

	
	private static void processAndAddToResult(Dataset intensity, Dataset histo, List<Dataset> result, IPixelIntegrationCache bean, boolean is2d) {
		
		Dataset error = intensity.getError();
		
		if (error != null) {
			error.idivide(histo);
			DatasetUtils.makeFinite(error);
		}


		Dataset axis = bean.getXAxis();

		intensity.idivide(histo);
		DatasetUtils.makeFinite(intensity);


		result.add(axis);
		result.add(intensity);
		if (is2d) result.add(bean.getYAxis());


		result.get(1).setError(error);
		
	}
}

