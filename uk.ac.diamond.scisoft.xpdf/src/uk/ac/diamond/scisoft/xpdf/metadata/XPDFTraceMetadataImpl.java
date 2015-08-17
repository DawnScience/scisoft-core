/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.metadata;

import org.eclipse.dawnsci.analysis.api.dataset.ILazyDataset;
import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTrace;

public class XPDFTraceMetadataImpl implements XPDFTrace {

	// Remove these if I ever work out how best to include the metadata in the dataset itself 
	ILazyDataset trace;
	double countingTime;
	double monitorRelativeFlux;
	
	public XPDFTraceMetadataImpl() {
		countingTime = 0.0;
		monitorRelativeFlux = 0.0;
		trace = null;
	}

	public XPDFTraceMetadataImpl(XPDFTraceMetadataImpl intrace) {
		this.countingTime = intrace.countingTime;
		this.monitorRelativeFlux = intrace.monitorRelativeFlux;
		this.trace = (intrace.trace == null) ? null : intrace.trace.getSliceView();
	}
	
	@Override
	public MetadataType clone() {
		return new XPDFTraceMetadataImpl(this);
	}

	public void setTrace(ILazyDataset trace) {
		this.trace = trace;
	}

	public void setCountingTime(double countingTime) {
		this.countingTime = countingTime;
	}

	public void setMonitorRelativeFlux(double monitorRelativeFlux) {
		this.monitorRelativeFlux = monitorRelativeFlux;
	}

	@Override
	public double getCountingTime() {
		return this.countingTime;
	}

	@Override
	public double getMonitorRelativeFlux() {
		return this.monitorRelativeFlux;
	}

	@Override
	public ILazyDataset getTrace() {
		return trace;
	}

}
