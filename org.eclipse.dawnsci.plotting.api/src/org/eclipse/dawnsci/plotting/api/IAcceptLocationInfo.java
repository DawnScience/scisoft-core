/*-
 * Copyright 2019 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.plotting.api;

/**
 * Interface to mark classes capable of using {@link PlotLocationInfo}
 * <p>
 * Used with jface actions in the plot popup menu extension point
 * 
 */
public interface IAcceptLocationInfo {

	void setLocationInfo(PlotLocationInfo bean);
}
