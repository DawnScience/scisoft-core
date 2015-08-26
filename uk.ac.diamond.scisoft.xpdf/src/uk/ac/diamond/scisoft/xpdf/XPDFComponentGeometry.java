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
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

// public because it needs to be visible in the uk...xpdf.operations package
public abstract class XPDFComponentGeometry {

	// Larger and smaller measurements of the component
	double rInner, rOuter;
	// Is this component up/down stream of the sample. The sample component is
	// neither upstream nor downstream of itself
	boolean isUpstream, isDownstream;
	
	int gridSize;

	public XPDFComponentGeometry() {
		this.rInner = 0.0;
		this.rOuter = 0.0;
		this.isUpstream = false;
		this.isDownstream = false;
		this.gridSize = 4096;
	}

	public XPDFComponentGeometry(XPDFComponentGeometry inGeom) {
		this.rInner = inGeom.rInner;
		this.rOuter = inGeom.rOuter;
		this.isUpstream = inGeom.isUpstream;
		this.isDownstream = inGeom.isDownstream;
		this.gridSize = inGeom.gridSize;
	}

	@Override
	protected abstract XPDFComponentGeometry clone();

	public void setDistances(double inIn, double inOut) {
		this.rInner = inIn;
		this.rOuter = inOut;
	}

	public void setStreamality(boolean is_up, boolean is_down) {
		this.isUpstream = is_up;
		this.isDownstream = is_down;
	}

	abstract public String getShape();

	public double[] getDistances() {
		double[] distances = new double[2];
		distances[0] = rInner;
		distances[1] = rOuter;

		return distances;
	}

	public boolean[] getStreamality() {
		boolean[] strity = new boolean[2];
		strity[0] = isUpstream;
		strity[1] = isDownstream;

		return strity;
	}

	// Total number of gridpoints to use in calculating the absorption coefficients
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
	
	// return the volume of this component illuminated by the beam geometry
	// encapsulated in beamData
	public abstract double getIlluminatedVolume(XPDFBeamData beamData);

	/*
	 * Path length of the object upstream of the sample, as measured at the
	 * point (x, y) in the beam. x is in the direction measured as beam width, y
	 * in the direction measured as beam height.
	 */
	public abstract Dataset getUpstreamPathLength(Dataset x, Dataset y, Dataset z);

	/*
	 * Path length of the object downstream of the sample, as measured at the
	 * point x,y,z in the beam. x,y are as above, whereas z is the depth into
	 * the target assembly in the direction of the beam, with zero at the
	 * sample.
	 * 
	 * The path length is measured in the direction γ, δ, where γ is the
	 * vertical scattering angle and δ is the horizontal scattering angle.
	 */
	 public abstract Dataset getDownstreamPathLength(Dataset x, Dataset y,
	 Dataset z, double gamma, double delta);

	/*
	 * Given an attenuator geometry and its attenuation coefficient (in mm^-1
	 * ??), as well as its existence up- and downstream, calculate the
	 * absorption map for the horizontal and vertical scattering angles given in
	 * the input datasets.
	 *	
 	 * The absorption is measured in the scattering direction γ, δ, where γ is the
	 * vertical scattering angle and δ is the horizontal scattering angle. These angles are provided as 2 dimensional Datasets, not necessarily forming a regular grid
	 */

	public abstract Dataset calculateAbsorptionCorrections(Dataset gamma, Dataset delta,
			XPDFComponentGeometry attenuatorGeometry, double attenuationCoefficient,
			XPDFBeamData beamData,
			boolean doUpstreamAbsorption, boolean doDownstreamAbsorption);
	
	
}
