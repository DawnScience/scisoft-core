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

import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetGeometryMetadata;

class ComponentCylinder extends ComponentGeometry {

	public ComponentCylinder() {
		super();
	}
	
	public ComponentCylinder(ComponentCylinder inCyl) {
		super(inCyl);
	}
	
	public ComponentCylinder(XPDFTargetGeometryMetadata inGeom) {
		super(inGeom);
	}
	
	@Override
	protected ComponentGeometry clone() {
		return new ComponentCylinder(this);
	}

	@Override
	public String getShape() {
		return "cylinder";
	}

}
