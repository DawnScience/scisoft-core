/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * Beam trace data for XPDF processing
 * 
 * @author Timothy Spain
 *
 */
//public because it needs to be visible in the uk...xpdf.operations package
public class XPDFBeamTrace {

	Dataset trace;
	double countingTime;
	double monitorRelativeFlux;
	boolean isNormalized, isBackgroundSubtracted;
	
	
	public XPDFBeamTrace() {
		countingTime = 1.0;
		monitorRelativeFlux = 1.0;
		trace = null;
		isNormalized = false;
		isBackgroundSubtracted = false;
	}
	
	public XPDFBeamTrace(XPDFBeamTrace inTrace) {
		this.countingTime = inTrace.countingTime;
		this.monitorRelativeFlux = inTrace.monitorRelativeFlux;
		this.trace = (inTrace.trace == null) ? null : inTrace.trace.getSliceView();
		this.isNormalized = inTrace.isNormalized;
		this.isBackgroundSubtracted = inTrace.isBackgroundSubtracted;
	}
	
	@Override
	protected XPDFBeamTrace clone() {
		return new XPDFBeamTrace(this);
	}

	public Dataset getTrace() {
		return (trace != null) ? trace : null;
	}

	public void setTrace(Dataset trace) {
		this.trace = trace;
	}

	public double getCountingTime() {
		return countingTime;
	}

	public void setCountingTime(double countingTime) {
		this.countingTime = countingTime;
	}

	public double getMonitorRelativeFlux() {
		return monitorRelativeFlux;
	}

	public void setMonitorRelativeFlux(double monitorRelativeFlux) {
		this.monitorRelativeFlux = monitorRelativeFlux;
	}
	
	public void normalizeTrace() {
		if (trace != null)
			trace = Maths.divide(trace, this.countingTime*this.monitorRelativeFlux);
		isNormalized = true;
	}
	
	public IDataset getNormalizedTrace() {
		if (isNormalized) {
			return trace;
		} else {
			return (trace != null) ? Maths.divide(trace, this.countingTime*this.monitorRelativeFlux) : null;
		}
	}
	
	public boolean isNormalized() {
		return this.isNormalized;
	}
	
	public void subtractBackground(XPDFBeamTrace background) {
		if (trace != null)
			trace = Maths.subtract(this.getNormalizedTrace(), background.getNormalizedTrace());
		isBackgroundSubtracted = true;
	}
	
	public boolean isBackgroundSubtracted() {
		return this.isBackgroundSubtracted;
	}
	
	public IDataset getBackgroundSubtractedTrace(XPDFBeamTrace background) {
		if (isBackgroundSubtracted) {
			return trace;
		} else {
			return (trace == null) ? null : Maths.subtract(getNormalizedTrace(), background.getNormalizedTrace());
		}
	}

}
