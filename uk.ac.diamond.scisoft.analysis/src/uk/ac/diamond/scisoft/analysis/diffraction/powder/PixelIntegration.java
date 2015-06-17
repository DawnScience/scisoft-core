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
		if (bean.getYAxisArray() != null) {
			r = bean.getYAxisArray()[0];
		}

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
		
		double rMin = 0;
		double rMax = 0;
		
		while (iter.hasNext()) {

			if (m != null && !m.getElementBooleanAbs(iter.index)) continue;

			double rangeScale = 1;

			if (integrationRange != null && r != null) {
				rMin = r[0].getElementDoubleAbs(iter.index);
				rMax = r[1].getElementDoubleAbs(iter.index);

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

			if (qMax < lo || qMin > hi) {
				continue;
			} 

			double minBinExact = (qMin-lo)/span;
			double maxBinExact = (qMax-lo)/span;

			int minBin = (int)minBinExact;
			int maxBin = (int)maxBinExact;

			if (minBin == maxBin) {
				h[minBin]+=rangeScale;
				in[minBin] += (sig*rangeScale);
				
				if (e!=null) {
					final double std = e.getElementDoubleAbs(iter.index)*rangeScale;
					eb[minBin] += (std*std);
				}
				
			} else {

				double range = maxBinExact-minBinExact;

				double minFrac = 1-(minBinExact-minBin);
				double maxFrac = maxBinExact-maxBin;

				for (int i = minBin; i <= maxBin; i++) {
					double modify = rangeScale;
					if (i >= h.length || i < 0) continue;
					if (i == minBin) modify *= minFrac;
					if (i == maxBin) modify *= maxFrac;
					modify /= range;
					h[i]+=modify;
					in[i] += (sig*modify);
					if (e!=null) {
						final double std = e.getElementDoubleAbs(iter.index)*modify;
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
		final double spanQ = (hiQ - loQ)/(bean.getNumberOfBinsXAxis());

		final double loChi = bean.getYBinEdgeMin();
		final double hiChi = bean.getYBinEdgeMax();
		final double spanChi = (hiChi - loChi)/(bean.getNumberOfBinsYAxis());
		
		//TODO early exit if spans are z
		final int nXBins = bean.getNumberOfBinsXAxis();
		final int nYBins = bean.getNumberOfBinsYAxis();

		IntegerDataset histo = (IntegerDataset) DatasetFactory.zeros(new int[]{nYBins,nXBins}, Dataset.INT32);
		DoubleDataset intensity = (DoubleDataset) DatasetFactory.zeros(new int[]{nYBins,nXBins},Dataset.FLOAT64);

		Dataset x = DatasetUtils.convertToDataset(bean.getXAxisArray()[0]);
		Dataset y = DatasetUtils.convertToDataset(bean.getYAxisArray()[0]);
		Dataset b = DatasetUtils.convertToDataset(data);
		Dataset m = DatasetUtils.convertToDataset(mask);
		IndexIterator iter = x.getIterator();

		while (iter.hasNext()) {

			final double valq = x.getElementDoubleAbs(iter.index);
			final double sig = b.getElementDoubleAbs(iter.index);
			final double chi = y.getElementDoubleAbs(iter.index);
			if (m != null && !m.getElementBooleanAbs(iter.index)) {
				continue;
			}

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
				double cIn = intensity.get(chiPos,qPos);
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
		
		final double minX = bean.getXBinEdgeMin();
		final double maxX = bean.getXBinEdgeMax();
		final double spanX = (maxX - minX)/nXBins;

		final double minY = bean.getYBinEdgeMin();
		final double maxY = bean.getYBinEdgeMax();
		final double spanY = (maxY - minY)/nYBins;

		DoubleDataset histo = new DoubleDataset(nYBins, nXBins);
		DoubleDataset intensity = new DoubleDataset(nYBins, nXBins);
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
			double xPixMax = x1.getElementDoubleAbs(iter.index);
			double xPixMin = x0.getElementDoubleAbs(iter.index);
			double yPixMax = y1.getElementDoubleAbs(iter.index);
			double yPixMin = y0.getElementDoubleAbs(iter.index);

			double sig = d.getElementDoubleAbs(iter.index);

			if (xPixMax < minX || xPixMin > maxX) {
				continue;
			} 

			if (yPixMax < minY || yPixMin > maxY) {
				continue;
			}

			double minBinExactX = (xPixMin-minX)/spanX;
			double maxBinExactX = (xPixMax-minX)/spanX;

			double minBinExactY = (yPixMin-minY)/spanY;
			double maxBinExactY = (yPixMax-minY)/spanY;

			double partialScale = 1;
			double iFull = (maxBinExactX-minBinExactX)*(maxBinExactY-minBinExactY);
			
			//Partial pixel if outside of range
			minBinExactX = xPixMin < minX ? 0 : minBinExactX;
			maxBinExactX = xPixMax > maxX ? nXBins : maxBinExactX;
			minBinExactY = yPixMin < minY ? 0 : minBinExactY;
			maxBinExactY = yPixMax > maxY ? nYBins : maxBinExactY;
			
			double iFraction = (maxBinExactX-minBinExactX)*(maxBinExactY-minBinExactY);
			partialScale *= (iFraction/iFull);
			
			int minBinX = (int)minBinExactX;
			int maxBinX= (int)maxBinExactX;
			int minBinY = (int)minBinExactY;
			int maxBinY = (int)maxBinExactY;
			
			double binArea = (maxBinExactX-minBinExactX)*(maxBinExactY-minBinExactY);
			
			double minFracX = 1-(minBinExactX-minBinX);
			double maxFracX = maxBinExactX-maxBinX;
			double minFracY = 1-(minBinExactY-minBinY);
			double maxFracY = maxBinExactY-maxBinY;
			
			for (int i = minBinX ; i <= maxBinX; i++) {
				if (i < 0 || i >= nXBins) continue;
				for (int j = minBinY; j <= maxBinY; j++) {
					if (j < 0 || j >= nYBins) continue;

					int[] setPos = new int[]{j,i};
					double val = histo.get(setPos);

					double modify = partialScale;

					if (i == minBinX && minBinX != maxBinX) modify *= (minFracX);
					if (i == maxBinX && minBinX != maxBinX) modify *= (maxFracX);
					if (j == minBinY && minBinY != maxBinY) modify *= (minFracY);
					if (j == maxBinY && minBinY != maxBinY) modify *= (maxFracY);
					
					if (j == maxBinY && maxBinY == minBinY) modify*=(maxBinExactY-minBinExactY);
					if (j == maxBinX && maxBinX == minBinX) modify*=(maxBinExactX-minBinExactX);
					
					modify /= binArea;
					histo.set(val+modify, setPos);
					double inVal = intensity.get(setPos);
					intensity.set(inVal+sig*modify, setPos);
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

