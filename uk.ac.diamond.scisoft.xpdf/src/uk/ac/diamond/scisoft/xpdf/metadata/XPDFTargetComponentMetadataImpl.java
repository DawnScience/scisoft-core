/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.metadata;

import org.eclipse.dawnsci.analysis.api.metadata.MetadataType;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetComponentMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetFormMetadata;
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTraceMetadata;

public class XPDFTargetComponentMetadataImpl implements XPDFTargetComponentMetadata {

	private String name;
	private XPDFTargetFormMetadata form;
	private XPDFTraceMetadata trace;
	private boolean isSample;
	
	public XPDFTargetComponentMetadataImpl() {
		this.name="";
		this.form=null;
		this.trace=null;
		this.isSample=false;
	}

	public XPDFTargetComponentMetadataImpl(XPDFTargetComponentMetadataImpl incont) {
		this.name = incont.name;
		this.form = (XPDFTargetFormMetadata) incont.form.clone();
		this.trace = (XPDFTraceMetadata) incont.trace.clone();
		this.isSample=incont.isSample;
	}

	@Override
	public MetadataType clone() {
		return new XPDFTargetComponentMetadataImpl(this);
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setForm(XPDFTargetFormMetadata form) {
		this.form = (XPDFTargetFormMetadata) form.clone();
	}

	public void setTrace(XPDFTraceMetadata traceMeta) {
		this.trace = (XPDFTraceMetadata) traceMeta.clone();
	}

	public void setSample(boolean isSample) {
		this.isSample = isSample;
	}
	
	@Override
	public XPDFTargetFormMetadata getForm() {
		return (XPDFTargetFormMetadata) this.form.clone();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public XPDFTraceMetadata getTrace() {
		return (XPDFTraceMetadata) this.trace.clone();
	}

	@Override
	public boolean isSample() {
		return this.isSample;
	}


}
