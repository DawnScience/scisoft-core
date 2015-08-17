/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

//public because it needs to be visible in the uk...xpdf.operations package
public class XPDFComponentPlate extends XPDFComponentGeometry {

	public XPDFComponentPlate() {
		super();
	}
	
	public XPDFComponentPlate(XPDFComponentPlate inPlate) {
		super(inPlate);
	}
	
	public XPDFComponentPlate(XPDFComponentGeometry inGeom) {
		super(inGeom);
	}
	
	@Override
	protected XPDFComponentGeometry clone() {
		return new XPDFComponentPlate(this);
	}

	@Override
	public String getShape() {
		return "plate";
	}

}
