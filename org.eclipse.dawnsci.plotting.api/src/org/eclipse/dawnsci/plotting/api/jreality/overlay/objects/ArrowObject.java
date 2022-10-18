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
public class ArrowObject extends OverlayObject {

	private static final DeprecationLogger logger = DeprecationLogger.getLogger(ArrowObject.class);
	private double sx,sy,ex,ey;
	private double arrowPos = 1.0;
	
	public ArrowObject(int primID, OverlayProvider provider) {
		super(primID, provider);
		logger.deprecatedClass();
	}

	public void setLinePoints(double sx,  double sy, double ex, double ey) {
		this.sx = sx;
		this.ex = ex;
		this.sy = sy;
		this.ey = ey;
	}
	
	public void setLineStart(double sx, double sy) {
		this.sx = sx;
		this.sy = sy;
	}
	
	public void setLineEnd(double ex, double ey) {
		this.ex = ex;
		this.ey = ey;
	}
	
	public void setArrowPos(double arrowPos) {
		this.arrowPos = arrowPos;
	}

	@Override
	public void draw() {
		if (provider instanceof Overlay2DProvider) {
			((Overlay2DProvider)provider).drawArrow(primID, sx, sy, ex, ey,arrowPos);
		}
	}
}


