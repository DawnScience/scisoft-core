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

public class XPDFTargetCylinderMetadataImpl extends XPDFTargetAbstractGeometryMetadataImpl {
		
	public XPDFTargetCylinderMetadataImpl() {
		super();
	}

	public XPDFTargetCylinderMetadataImpl(XPDFTargetCylinderMetadataImpl incyl) {
		this.inner_r = incyl.inner_r;
		this.outer_r = incyl.outer_r;
		this.is_upstream = incyl.is_upstream;
		this.is_downstream = incyl.is_downstream;
	}

	@Override
	public MetadataType clone() {
		return new XPDFTargetCylinderMetadataImpl(this);
	}

	@Override
	public String getShape() {
		return "cylinder";
	}

}
