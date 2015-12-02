/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * Beam trace data for XPDF processing
 * 
 * @author Timothy Spain timothy.spain@diamond.ac.uk
 * @since 2015-09-11
 *
 */
//public because it needs to be visible in the uk...xpdf.operations package
public class XPDFBeamTrace {

	private Dataset trace;
	private double countingTime;
	private double monitorRelativeFlux;
	private boolean isNormalized, isBackgroundSubtracted;
	private boolean isAxisAngle;
	
	
	/**
	 * Empty constructor to create an empty beam.
	 */
	public XPDFBeamTrace() {
		countingTime = 1.0;
		monitorRelativeFlux = 1.0;
		trace = null;
		isNormalized = false;
		isBackgroundSubtracted = false;
		isAxisAngle = true;
	}
	
	/**
	 * Copy constructor.
	 * @param inTrace
	 * 				The XPDFBeamTrace object to be copied.
	 */
	public XPDFBeamTrace(XPDFBeamTrace inTrace) {
		this.countingTime = inTrace.countingTime;
		this.monitorRelativeFlux = inTrace.monitorRelativeFlux;
		this.trace = (inTrace.trace == null) ? null : inTrace.trace.getSliceView();
		this.isNormalized = inTrace.isNormalized;
		this.isBackgroundSubtracted = inTrace.isBackgroundSubtracted;
		this.isAxisAngle = inTrace.isAxisAngle;
	}
	
	/**
	 * clone method, uses the copy constructor.
	 */
	@Override
	protected XPDFBeamTrace clone() {
		return new XPDFBeamTrace(this);
	}

	/**
	 * Getter for the trace Dataset.
	 * @return the Dataset associated with this trace.
	 */
	public Dataset getTrace() {
		return (trace != null) ? trace : null;
	}

	/**
	 * Setter for the trace Dataset.
	 * @param trace
	 * 				The trace which this object is holding
	 */
	public void setTrace(Dataset trace) {
		this.trace = trace;
	}

	/**
	 * Getter for the counting time of the experiment that produced the trace.
	 * @return the counting time in seconds.
	 */
	public double getCountingTime() {
		return countingTime;
	}

	/**
	 * Setter for the trace counting time. 
	 * @param countingTime
	 * 					The counting time needed to produced the trace data in seconds.
	 */
	public void setCountingTime(double countingTime) {
		this.countingTime = countingTime;
	}

	/**
	 * Getter for the flux of the beam relative to the flux monitor.
	 * @return the ratio of monitor to experiment fluxes.
	 */
	public double getMonitorRelativeFlux() {
		return monitorRelativeFlux;
	}

	/**
	 * Setter for the flux of the beam relative to the flux monitor.
	 * @param monitorRelativeFlux
	 * 							the ratio of monitor to experiment fluxes.
	 */
	public void setMonitorRelativeFlux(double monitorRelativeFlux) {
		this.monitorRelativeFlux = monitorRelativeFlux;
	}
	
	/**
	 * Normalize the trace contained, and note this in the appropriate isNormalized boolean
	 */
	public void normalizeTrace() {
		if (trace != null) {
			Dataset traceErrors = (trace.getError() != null) ? trace.getError() : null;
			trace = getNormalizedTrace();
			// Normalize the errors, too
			if (traceErrors != null)
				trace.setError(Maths.divide(traceErrors, this.countingTime*this.monitorRelativeFlux));
		}
		isNormalized = true;
	}
	
	/**
	 * Return the normalized trace.
	 * <p>
	 * If the trace is not previously normalized, then do so. Thence, return the answer.
	 * @return the Dataset of the normalized trace.
	 */
	public Dataset getNormalizedTrace() {
		if (isNormalized) {
			return trace;
		} else {
			return (trace != null) ? Maths.divide(trace, this.countingTime*this.monitorRelativeFlux) : null;
		}
	}
	
	/**
	 * Check if the trace it provides is normalized.
	 * @return if the trace is normalized.
	 */
	public boolean isNormalized() {
		return this.isNormalized;
	}
	
	/**
	 * Subtract the background from the trace.
	 * <p>
	 * Given a background Dataset, subtract this from the trace currently contained.
	 * @param background
	 * 					the Dataset of the data to be subtracted.
	 */
	public void subtractBackground(XPDFBeamTrace background) {
		if (trace != null) {
			Dataset traceErrors = (trace.getError() != null) ? trace.getError() : null;
			trace = getBackgroundSubtractedTrace(background);
			if (traceErrors != null) {
				Dataset subErrors = (background.getNormalizedTrace().getError() != null) ?
						Maths.sqrt(Maths.add(Maths.square(traceErrors), Maths.square(background.getNormalizedTrace().getError()))) :
							traceErrors;
						trace.setError(subErrors);
			}
		}
		isBackgroundSubtracted = true;
	}
	
	/**
	 * Check if the background has been subtracted from this trace.
	 * @return if the background has been subtracted from this trace.
	 */
	public boolean isBackgroundSubtracted() {
		return this.isBackgroundSubtracted;
	}
	
	/**
	 * Get the background subtracted trace.
	 * <p>
	 * Return the trace Dataset with the provided background subtracted from it.
	 * @param background
	 * 					The background to subtract.
	 * @return the trace with the background subtracted.
	 */
	public Dataset getBackgroundSubtractedTrace(XPDFBeamTrace background) {
		if (isBackgroundSubtracted) {
			return trace;
		} else {
			return (trace == null) ? null : Maths.subtract(getNormalizedTrace(), background.getNormalizedTrace());
		}
	}

	/**
	 * Getter for whether the independent variable is angle.
	 * @return true if the x axis is scattering angle.
	 */
	public boolean isAxisAngle() {
		return isAxisAngle;
	}

	/**
	 * Setter for whether the independent variable is angle.
	 * @param isAxisAngle
	 * 					is the independent variable of the trace angle rather
	 * 					than momentum transfer.
	 */
	public void setAxisAngle(boolean isAxisAngle) {
		this.isAxisAngle = isAxisAngle;
	}

}
