/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.processing.operations;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IPeak;
import org.eclipse.dawnsci.analysis.api.processing.model.AbstractOperationModel;
import org.eclipse.dawnsci.analysis.api.processing.model.OperationModelField;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;

/**
 * Hacky temp model for fitting.
 * 
 * @author Matthew Gerring
 *
 */
public class FittingModel extends AbstractOperationModel {

	private IDataset xAxis;
	private Class<? extends IPeak> peakClass;
	private Class<? extends IOptimizer> optimizerClass;
	private double quality;
	private long seed;
	
	@OperationModelField(max=10, min=0)
	private int smoothing;
	
	private int numberOfPeaks;
	private double threshold;
	private boolean autostopping;
	private boolean backgrounddominated;

	public FittingModel() {
		
	}

	
	public FittingModel(IDataset xaxis, Class<? extends IPeak> class1,
			Class<? extends IOptimizer> class2, double d, long seed, int smoothing,
			int numpeaks, double threshold, boolean autostopping,
			boolean backgrounddominated) {
		
		this.xAxis = xaxis;
	    this.peakClass      = class1;
	    this.optimizerClass = class2;
	    this.quality = d;
	    this.seed = seed;
	    this.smoothing = smoothing;
	    this.numberOfPeaks = numpeaks;
	    this.threshold = threshold;
	    this.autostopping = autostopping;
	    this.backgrounddominated = backgrounddominated;
	}

	public IDataset getxAxis() {
		return xAxis;
	}

	public void setxAxis(IDataset xAxis) {
		this.xAxis = xAxis;
	}

	public Class<? extends IPeak> getPeak() {
		return peakClass;
	}

	public void setPeak(Class<? extends IPeak> peak) {
		this.peakClass = peak;
	}

	public Class<? extends IOptimizer> getOptimizer() {
		return optimizerClass;
	}

	public void setOptimizer(Class<? extends IOptimizer> optimizer) {
		this.optimizerClass = optimizer;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public int getSmoothing() {
		return smoothing;
	}

	public void setSmoothing(int smoothing) {
		this.smoothing = smoothing;
	}

	public int getNumberOfPeaks() {
		return numberOfPeaks;
	}

	public void setNumberOfPeaks(int numberOfPeaks) {
		this.numberOfPeaks = numberOfPeaks;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public boolean isAutostopping() {
		return autostopping;
	}

	public void setAutostopping(boolean autostopping) {
		this.autostopping = autostopping;
	}

	public boolean isBackgrounddominated() {
		return backgrounddominated;
	}

	public void setBackgrounddominated(boolean backgrounddominated) {
		this.backgrounddominated = backgrounddominated;
	}

	public IOptimizer createOptimizer() throws Exception {
		return optimizerClass.getConstructor(double.class, Long.class).newInstance(quality, seed);
	}

}

