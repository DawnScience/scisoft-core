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
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetComponentMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

public class XPDFTargetComponent {

	private String name;
	private XPDFComponentForm form;
	private XPDFBeamTrace trace;
	boolean isSample;
	private double c3;
	private Dataset subBak;
	private Dataset calCon;
	private Dataset mulCor;
	
	public XPDFTargetComponent() {
		this.name="";
		this.form=null;
		this.trace=null;
		this.isSample=false;
		this.c3 = 1.0;
		this.subBak = null;
		this.calCon = null;
		this.mulCor = null;
	}

	public XPDFTargetComponent(XPDFTargetComponent inComp) {
		this.name = inComp.name;
		this.form = new XPDFComponentForm(inComp.form);
		this.trace = new XPDFBeamTrace(inComp.trace);
		this.isSample = inComp.isSample;
		this.c3 = inComp.c3;
		this.subBak = inComp.subBak != null ? inComp.subBak : null;
		this.calCon = inComp.calCon != null ? inComp.calCon : null;
		this.mulCor = inComp.mulCor != null ? inComp.mulCor : null;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setForm(XPDFComponentForm form) {
		this.form = form;
	}

	public void setTrace(XPDFBeamTrace trace) {
		this.trace = trace;
	}

	public void setSample(boolean isSample) {
		this.isSample = isSample;
	}

	public String getName() {
		return name;
	}

	public XPDFComponentForm getForm() {
		return form;
	}

	public XPDFBeamTrace getTrace() {
		return trace;
	}

	public boolean isSample() {
		return isSample;
	}

	public void setTraceCounts(IDataset sliceView) {
		trace.setTrace(sliceView);
	}
	
	public void setBackground(XPDFBeamTrace background) {
		subBak =  Maths.subtract(trace.getNormalizedTrace(), background.getNormalizedTrace());
	}
	
	public IDataset getBackgroundSubtractedTrace() {
		return subBak.getSliceView();
	}
	
	public void setCalibrationConstant(double c3) {
		this.c3 = c3;
		calCon = Maths.divide(this.subBak, this.c3);
	}
	
	public IDataset getCalibratedTrace() {
		return calCon.getSliceView();
	}
	
	public void setMultipleScatteringCorrection(IDataset multipleScatteringCorrection) {
		mulCor = Maths.subtract(calCon, multipleScatteringCorrection);
	}

	public IDataset getMultipleScatteringCorrectedTrace() {
		return mulCor;
	}
}
