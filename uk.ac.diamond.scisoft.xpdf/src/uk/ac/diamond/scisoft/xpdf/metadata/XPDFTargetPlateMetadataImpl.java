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

public class XPDFTargetPlateMetadataImpl extends XPDFTargetAbstractGeometryMetadataImpl {

	public XPDFTargetPlateMetadataImpl(XPDFTargetPlateMetadataImpl inplate) {
		this.inner_r = inplate.inner_r;
		this.outer_r = inplate.outer_r;
		this.is_upstream = inplate.is_upstream;
		this.is_downstream = inplate.is_downstream;
	}

	public XPDFTargetPlateMetadataImpl() {
		this.inner_r = 0.0;
		this.outer_r = 0.0;
		this.is_upstream = false;
		this.is_downstream = false;
	}

	@Override
	public MetadataType clone() {
		return new XPDFTargetPlateMetadataImpl(this);
	}

	@Override
	public String getShape() {
		return "plate";
	}

}
