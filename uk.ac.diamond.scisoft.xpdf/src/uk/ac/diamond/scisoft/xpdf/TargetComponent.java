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

import org.eclipse.dawnsci.analysis.api.dataset.IDataset;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetComponentMetadata;
import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

class TargetComponent {

	private String name;
	private ComponentForm form;
	private BeamTrace trace;
	boolean isSample;
	private double c3;
	private Dataset subBak;
	private Dataset calCon;
	private Dataset mulCor;
	
	public TargetComponent() {
		this.name="";
		this.form=null;
		this.trace=null;
		this.isSample=false;
		this.c3 = 1.0;
		this.subBak = null;
		this.calCon = null;
		this.mulCor = null;
	}

	public TargetComponent(TargetComponent inComp) {
		this.name = inComp.name;
		this.form = new ComponentForm(inComp.form);
		this.trace = new BeamTrace(inComp.trace);
		this.isSample = inComp.isSample;
		this.c3 = inComp.c3;
		this.subBak = inComp.subBak.getSliceView();
		this.calCon = inComp.calCon.getSliceView();
		this.mulCor = inComp.mulCor.getSliceView();
	}
	
	public TargetComponent(XPDFTargetComponentMetadata inComp) {
		this.name = inComp.getName();
		this.form = new ComponentForm(inComp.getForm());
		this.trace = new BeamTrace(inComp.getTrace());
		this.isSample = inComp.isSample();
		this.c3 = 1.0;
		this.subBak= null;
		this.calCon = null;
		this.mulCor = null;
	}

	public String getName() {
		return name;
	}

	public ComponentForm getForm() {
		return form;
	}

	public BeamTrace getTrace() {
		return trace;
	}

	public boolean isSample() {
		return isSample;
	}

	public void setTraceCounts(IDataset sliceView) {
		trace.setTrace(sliceView);
	}
	
	public void setBackground(BeamTrace background) {
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
