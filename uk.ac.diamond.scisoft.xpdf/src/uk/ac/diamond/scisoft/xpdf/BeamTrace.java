/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;
//TODO: Move back to uk.ac.diamond.scisoft.xpdf once the NPEs are solved

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTraceMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * Beam trace data for XPDF processing
 * 
 * @author Timothy Spain
 *
 */
class BeamTrace {

	ILazyDataset trace;
	double countingTime;
	double monitorRelativeFlux;

	public BeamTrace() {
		countingTime = 0.0;
		monitorRelativeFlux = 0.0;
		trace = null;
	}
	
	public BeamTrace(BeamTrace inTrace) {
		this.countingTime = inTrace.countingTime;
		this.monitorRelativeFlux = inTrace.monitorRelativeFlux;
		this.trace = (inTrace.trace == null) ? null : inTrace.trace.getSliceView();
	}

	public BeamTrace(XPDFTraceMetadata inTrace) {
		this.countingTime = inTrace.getCountingTime();
		this.monitorRelativeFlux = inTrace.getMonitorRelativeFlux();
		this.trace = (inTrace.getTrace() == null) ? null : inTrace.getTrace().getSliceView();
	}
	
	@Override
	protected BeamTrace clone() {
		return new BeamTrace(this);
	}

	public ILazyDataset getTrace() {
		return trace.getSliceView();
	}

	public void setTrace(ILazyDataset trace) {
		this.trace = trace;
	}

	public ILazyDataset getNormalizedTrace() {
		return Maths.divide(trace.getSliceView(), this.countingTime*this.monitorRelativeFlux);
	}
	
//	public double getCountingTime() {
//		return countingTime;
//	}

	public void setCountingTime(double countingTime) {
		this.countingTime = countingTime;
	}

//	public double getMonitorRelativeFlux() {
//		return monitorRelativeFlux;
//	}

	public void setMonitorRelativeFlux(double monitorRelativeFlux) {
		this.monitorRelativeFlux = monitorRelativeFlux;
	}
	
}
