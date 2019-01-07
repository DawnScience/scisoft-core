/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.plotting.api;

import org.eclipse.dawnsci.plotting.api.axis.IAxis;
import org.eclipse.dawnsci.plotting.api.region.IRegion;
import org.eclipse.dawnsci.plotting.api.trace.ITrace;

/**
 * Class to hold information relating to a location in the plot
 * <p>
 * This includes the x,y coordinates on the primary axis, a trace, a region or an axis.
 */
public class PlotLocationInfo {

	public static final String ID = "PLOTLOCATIONBEAN";
	public static final String PLOTTINGSYSTEM = "PLOTACTIONSYSTEM";

	private IPlottingSystem<?> system;
	private double x;
	private double y;
	private IRegion region;
	private ITrace trace;
	private IAxis axis;

	public PlotLocationInfo(IPlottingSystem<?> system, double x, double y, IRegion region, ITrace trace, IAxis axis) {
		this.system = system;
		this.x = x;
		this.y = y;
		this.region = region;
		this.trace = trace;
		this.axis = axis;
	}

	public IPlottingSystem<?> getSystem() {
		return system;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public IRegion getRegion() {
		return region;
	}

	public ITrace getTrace() {
		return trace;
	}

	public IAxis getAxis() {
		return axis;
	}

}
