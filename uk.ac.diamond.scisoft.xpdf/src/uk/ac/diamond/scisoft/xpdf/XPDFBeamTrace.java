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
import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * Beam trace data for XPDF processing
 * 
 * @author Timothy Spain
 *
 */
//public because it needs to be visible in the uk...xpdf.operations package
public class XPDFBeamTrace {

	IDataset trace;
	double countingTime;
	double monitorRelativeFlux;

	public XPDFBeamTrace() {
		countingTime = 1.0;
		monitorRelativeFlux = 1.0;
		trace = null;
	}
	
	public XPDFBeamTrace(XPDFBeamTrace inTrace) {
		this.countingTime = inTrace.countingTime;
		this.monitorRelativeFlux = inTrace.monitorRelativeFlux;
		this.trace = (inTrace.trace == null) ? null : inTrace.trace.getSliceView();
	}
	
	@Override
	protected XPDFBeamTrace clone() {
		return new XPDFBeamTrace(this);
	}

	public IDataset getTrace() {
		return (trace != null) ? trace.getSlice() : null;
	}

	public void setTrace(IDataset trace) {
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
	
	public IDataset getNormalizedTrace() {
		return Maths.divide(trace.getSliceView(), this.countingTime*this.monitorRelativeFlux);
	}
	
	public IDataset getBackgroundSubtractedTrace(XPDFBeamTrace background) {
		return Maths.subtract(getNormalizedTrace(), background.getNormalizedTrace());
		
	}
}
