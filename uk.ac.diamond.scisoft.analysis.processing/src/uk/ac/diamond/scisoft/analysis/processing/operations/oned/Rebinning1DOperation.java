/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.processing.operations.oned;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.dawnsci.analysis.api.processing.OperationData;
import org.eclipse.dawnsci.analysis.api.processing.OperationException;
import org.eclipse.dawnsci.analysis.api.processing.OperationRank;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.dataset.operations.AbstractOperation;
import org.eclipse.january.DatasetException;
import org.eclipse.january.IMonitor;
import org.eclipse.january.MetadataException;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DatasetUtils;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.ILazyDataset;
import org.eclipse.january.dataset.IndexIterator;
import org.eclipse.january.dataset.Maths;
import org.eclipse.january.metadata.AxesMetadata;
import org.eclipse.january.metadata.MetadataFactory;

public class Rebinning1DOperation extends AbstractOperation<Rebinning1DModel, OperationData> {

	private int nBins;
	private double start;
	private double stop;
	private Dataset binEdges;
	private PropertyChangeListener listener;
	
	@Override
	public String getId() {
		return "uk.ac.diamond.scisoft.analysis.processing.operations.oned.Rebinning1DOperation";
	}
	
	@Override
	public void init(){
		binEdges = null;
	}
	
	protected OperationData process(IDataset input, IMonitor monitor) throws OperationException {
		
		ILazyDataset[] axes = getFirstAxes(input);
		
		if (axes == null || axes[0] == null) throw new OperationException(this, "Cannot rebin if there is no axis");
		
		Dataset axis;
		try {
		    axis = DatasetUtils.convertToDataset(axes[0].getSlice());
		} catch (DatasetException e) {
			throw new OperationException(this, e);
		}
		
		if (binEdges == null) {
			nBins = model.getNumberOfBins() != null ? model.getNumberOfBins() : axis.getSize(); 
			updateStartStop(axis);
		}

		AxesMetadata axm;
		try {
			axm = MetadataFactory.createMetadata(AxesMetadata.class, 1);
		} catch (MetadataException e) {
			throw new OperationException(this, e);
		}
		Dataset ax = Maths.add(binEdges.getSlice(new int[]{1}, null ,null), binEdges.getSlice(null, new int[]{-1},null));
		ax.idivide(2);
		
		double[] edges = new double[]{binEdges.getElementDoubleAbs(0),binEdges.getElementDoubleAbs(nBins)};
		IDataset rebinned = doRebinning(getMinMaxAxisArray(axis), DatasetUtils.convertToDataset(input), nBins, edges, ax.clone());
		ax.setName(axis.getName());
		axm.setAxis(0, ax);
		rebinned.setMetadata(axm);
		return new OperationData(rebinned);
		
		
	}
	
	private Dataset[] getMinMaxAxisArray(Dataset axis) {
		
		DoubleDataset minD = DatasetFactory.zeros(DoubleDataset.class, axis.getShape());
		DoubleDataset maxD = DatasetFactory.zeros(DoubleDataset.class, axis.getShape());
		boolean reversed = false;
		
		//TODO handle high to low order
		if (axis.getElementDoubleAbs(0) > axis.getElementDoubleAbs(axis.getSize()-1)) {
			reversed = true;
			Dataset axisr = axis.getSlice();
			for (int i = 0; i < axis.getSize(); i++) {
				axisr.set(axis.getObject(i), axis.getSize()-i-1);
			}
			axis = axisr;
		}
		
		
		IndexIterator it = axis.getIterator();
		double min = 0;
		double max = 0;
		double val = 0;
		while (it.hasNext()) {
			
			val = axis.getElementDoubleAbs(it.index);
			
			if (it.index == 0) {
				min = axis.getElementDoubleAbs(it.index+1);
				min = val - (min-val);
			} else {
				min = axis.getElementDoubleAbs(it.index-1);
			}
			
			min = val - (val - min)/2;
			
			if (it.index < axis.getSize()-1) {
				max = axis.getElementDoubleAbs(it.index+1);
			} else {
				max = axis.getElementDoubleAbs(it.index-1);
				max = val + (val - max);
			}
			
			max = val + (max- val)/2;
			
			minD.setAbs(it.index, min);
			maxD.setAbs(it.index, max);
			
		}
		
		if (reversed) {
			DoubleDataset minDr = (DoubleDataset)minD.getSlice();;
			DoubleDataset maxDr = (DoubleDataset)maxD.getSlice();;
			for (int i = 0; i < minDr.getSize(); i++) {
				minDr.set(minD.getObject(i), minD.getSize()-1-i);
				maxDr.set(maxD.getObject(i), maxD.getSize()-1-i);
			}
			minD = minDr;
			maxD = maxDr;
		}
		
		return new Dataset[]{minD,maxD};
	}
	
	
	private IDataset doRebinning(Dataset[] minMaxAxis, Dataset data, int nbins, double[] edges, Dataset ax) {
		
		Dataset d = DatasetUtils.convertToDataset(data);
		Dataset e = d.getErrors();

		final double lo = edges[0];
		final double hi = edges[1];
		final double span = (hi - lo)/nbins;
		DoubleDataset histo = DatasetFactory.zeros(DoubleDataset.class, nbins);
		DoubleDataset intensity = DatasetFactory.zeros(DoubleDataset.class, nbins);
		DoubleDataset error = null;
		double[] eb = null;
		if (e != null) {
			error = DatasetFactory.zeros(DoubleDataset.class, nbins);
			eb = error.getData();
		}

		final double[] h = histo.getData();
		final double[] in = intensity.getData();
		
		//TODO when span <= 0
		
		IndexIterator it = data.getIterator();
		// double[] integrationRange = {ax.getDouble(0), ax.getDouble(-1)};

		while (it.hasNext()) {
			
			//scale if pixel range not fully covered by bin range
			
			double rangeScale = 1;

			double sig = data.getElementDoubleAbs(it.index);
			double aMin = minMaxAxis[0].getElementDoubleAbs(it.index);
			double aMax = minMaxAxis[1].getElementDoubleAbs(it.index);
			
			//scale if all signal not in bin range
			sig *= rangeScale;
			
			if (aMax < lo || aMin > hi) {
				continue;
			}
			
			double minBinExact = (aMin-lo)/span;
			double maxBinExact = (aMax-lo)/span;

			int minBin = (int)minBinExact;
			int maxBin = (int)maxBinExact;
			
			if (minBin == maxBin) {
				h[minBin]++;
				in[minBin] += sig;
				
				if (e!=null) {
					final double std = e.getElementDoubleAbs(it.index)*rangeScale;
					eb[minBin] += (std*std);
				}
				
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
					
					if (e!=null) {
						final double std = e.getElementDoubleAbs(it.index)*iPerPixel;
						eb[i] += (std*std);
					}
				}
				
			}
		}
		
		intensity.idivide(histo);
		DatasetUtils.makeFinite(intensity);
		if (eb != null) intensity.setErrorBuffer(eb);
		intensity.setName(data.getName() + "_integrated");
		
		return intensity;
		
	}

	private void updateStartStop(IDataset axis) {

		double st = model.getMin() != null ? model.getMin() : axis.min().doubleValue();
		double sp = model.getMax() != null ? model.getMax() : axis.max().doubleValue();
		
		double shift = (sp- st)/(2*nBins);
		start = st - shift;
		stop = sp + shift;
		binEdges = DatasetFactory.createLinearSpace(start,stop,nBins+1, Dataset.FLOAT64);
	}

	@Override
	public OperationRank getInputRank() {
		return OperationRank.ONE;
	}

	@Override
	public OperationRank getOutputRank() {
		return OperationRank.ONE;
	}
	
	@Override
	public void setModel(Rebinning1DModel model) {
		
		super.setModel(model);
		if (listener == null) {
			listener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					binEdges = null;
				}
			};
		} else {
			((AbstractOperationModel)this.model).removePropertyChangeListener(listener);
		}
		
		((AbstractOperationModel)this.model).addPropertyChangeListener(listener);
	}

}
