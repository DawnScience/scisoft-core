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
import org.eclipse.dawnsci.analysis.api.metadata.XPDFTargetGeometryMetadata;

public abstract class XPDFTargetAbstractGeometryMetadataImpl implements XPDFTargetGeometryMetadata {

	double inner_r, outer_r;
	boolean is_upstream, is_downstream;
	
	
	
	public XPDFTargetAbstractGeometryMetadataImpl() {
		this.inner_r = 0.0;
		this.outer_r = 0.0;
		this.is_upstream = false;
		this.is_downstream = false;
	}

	public XPDFTargetAbstractGeometryMetadataImpl(XPDFTargetAbstractGeometryMetadataImpl ingeom) {
		this.inner_r = ingeom.inner_r;
		this.outer_r = ingeom.outer_r;
		this.is_upstream = ingeom.is_upstream;
		this.is_downstream = ingeom.is_downstream;
	}

	@Override
	abstract public MetadataType clone();

	public void setDistances(double inIn, double inOut) {
		this.inner_r = inIn;
		this.outer_r = inOut;
	}

	public void setStreamality(boolean is_up, boolean is_down) {
		this.is_upstream = is_up;
		this.is_downstream = is_down;
	}
	
	@Override
	abstract public String getShape();

	@Override
	public double[] getDistances() {
		double[] distances = new double[2];
		distances[0] = inner_r;
		distances[1] = outer_r;
		
		return distances;
	}

	@Override
	public boolean[] getStreamality() {
		boolean[] strity = new boolean[2];
		strity[0] = is_upstream;
		strity[1] = is_downstream;
		
		return strity;
	}

}
