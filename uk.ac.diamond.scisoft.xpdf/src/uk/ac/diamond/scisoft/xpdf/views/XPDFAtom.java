/*
 * Copyright 2016 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf.views;

public class XPDFAtom {

	private double[] position;
	private double occupancy;
	private int atomicNumber;
	// non-equilibrium parameters

	public void XPDFAtom(int atomicNumber) {
		position = new double[3];
		this.atomicNumber = atomicNumber;
		// initialize non-equilibrium 
	}
	
	public void setPosition(double x, double y, double z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}
	
	public void setPosition(double[] r) {
		this.setPosition(r[0], r[1], r[2]);
	}
	
	public void setOccupancy(double occupancy) {
		this.occupancy = occupancy;
	}
	
	
	
}
