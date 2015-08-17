/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetGeometryMetadata;

// public because it needs to be visible in the uk...xpdf.operations package
public abstract class XPDFComponentGeometry {

	// Larger and smaller measurements of the component
	double inner_r, outer_r;
	// Is this component up/down stream of the sample. The sample component is 
	// neither upstream nor downstream of itself 
	boolean is_upstream, is_downstream;

	public XPDFComponentGeometry() {
		this.inner_r = 0.0;
		this.outer_r = 0.0;
		this.is_upstream = false;
		this.is_downstream = false;
	}

	public XPDFComponentGeometry(XPDFComponentGeometry inGeom) {
		this.inner_r = inGeom.inner_r;
		this.outer_r = inGeom.outer_r;
		this.is_upstream = inGeom.is_upstream;
		this.is_downstream = inGeom.is_downstream;
	}

	public XPDFComponentGeometry(XPDFTargetGeometryMetadata inGeom) {
		double[] distances = inGeom.getDistances();
		inner_r = distances[0];
		outer_r = distances[1];
		boolean[] streamality = inGeom.getStreamality();
		is_upstream = streamality[0];
		is_downstream = streamality[1];
	}
	
	@Override
	protected abstract XPDFComponentGeometry clone();

	public void setDistances(double inIn, double inOut) {
		this.inner_r = inIn;
		this.outer_r = inOut;
	}

	public void setStreamality(boolean is_up, boolean is_down) {
		this.is_upstream = is_up;
		this.is_downstream = is_down;
	}

	abstract public String getShape();
	
	public double[] getDistances() {
		double[] distances = new double[2];
		distances[0] = inner_r;
		distances[1] = outer_r;
		
		return distances;
	}
	
	public boolean[] getStreamality() {
		boolean[] strity = new boolean[2];
		strity[0] = is_upstream;
		strity[1] = is_downstream;
		
		return strity;
	}

}
