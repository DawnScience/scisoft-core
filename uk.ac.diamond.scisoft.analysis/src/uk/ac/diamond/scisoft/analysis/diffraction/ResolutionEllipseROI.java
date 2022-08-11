/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.diffraction;

import org.eclipse.dawnsci.analysis.api.roi.IPolylineROI;
import org.eclipse.dawnsci.analysis.dataset.roi.EllipticalROI;

/**
 *
 */
public class ResolutionEllipseROI extends EllipticalROI {
	private static final long serialVersionUID = 356527860808765390L;

	double resolution = 0;
	IPolylineROI points;

	public ResolutionEllipseROI(EllipticalROI eroi, double dSpace) {
		this.setSemiAxes(eroi.getSemiAxes());
		this.setAngle(eroi.getAngle());
		this.setPoint(eroi.getPoint());
		resolution = dSpace;
	}

	/**
	 * Copy constructor
	 * @param orig
	 */
	public ResolutionEllipseROI(ResolutionEllipseROI orig) {
		super(orig);
		points = orig.points == null ? null : orig.points.copy();
		resolution = orig.resolution;
	}

	@Override
	public ResolutionEllipseROI copy() {
		return new ResolutionEllipseROI(this);
	}

	/**
	 * Set d space in angstrom associated with ellipse
	 * @return resolution
	 */
	public double getResolution() {
		return resolution;
	}

	
	/**
	 * Set d space in angstrom associated with ellipse
	 * @param resolution
	 */
	public void setResolution(double resolution) {
		this.resolution = resolution;
	}
	
	public void setPoints(IPolylineROI points) {
		this.points = points;
	}
	
	public IPolylineROI getPoints() {
		return points;
	}
}
