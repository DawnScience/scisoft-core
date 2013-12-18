/*-
 * Copyright (c) 2013 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.persistence.bean.roi;

public class LinearROIBean extends ROIBean {

	private double len;    // length
	private double ang;    // angle in radians
	private double[] endPoint;

	public LinearROIBean() {
		type = "LinearROI";
	}

	public double getLength() {
		return len;
	}
	public void setLength(double len) {
		this.len = len;
	}
	public double getAngle() {
		return ang;
	}
	public void setAngle(double ang) {
		this.ang = ang;
	}

	public double[] getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(double[] endPoint) {
		this.endPoint = endPoint;
	}


}
