/*-
 *******************************************************************************
 * Copyright (c) 2011, 2014 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.dawnsci.plotting.api.jreality.overlay.objects;

import org.eclipse.dawnsci.plotting.api.jreality.overlay.Overlay2DProvider;
import org.eclipse.dawnsci.plotting.api.jreality.overlay.OverlayProvider;

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;

/**
 *
 */
@Deprecated(since="at least 2015")
public class CircleSectorObject extends OverlayObject {

	private static final DeprecationLogger logger = DeprecationLogger.getLogger(CircleSectorObject.class);
	private double cx, cy;
	private double inRadius = 1.0;
	private double outRadius = 2.0;
	private double startAngle = 0;
	private double endAngle = 270.0;
	
	public CircleSectorObject(int primID, OverlayProvider provider) {
		super(primID, provider);
		logger.deprecatedClass();
	}
	
	public void setCircleSectorPostion(double cx, double cy) {
		this.cx = cx;
		this.cy = cy;
	}
	
	public void setCircleSectorRadii(double innerRadius, double outerRadius) {
		this.inRadius = innerRadius;
		this.outRadius = outerRadius;
	}
	
	public void setCircleSectorAngles(double startAngle, double endAngle) {
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}

	@Override
	public void draw() {
		if (provider instanceof Overlay2DProvider)
			((Overlay2DProvider)provider).drawSector(primID, cx, cy, inRadius, outRadius, startAngle, endAngle);
	}	
}
