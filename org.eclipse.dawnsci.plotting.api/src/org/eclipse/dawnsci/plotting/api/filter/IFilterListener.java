/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.plotting.api.filter;

import java.util.EventListener;

public interface IFilterListener extends EventListener {

	/**
	 * Called after the filter can run
	 * @param evt
	 */
	public void filterApplied(FilterEvent evt);
	/**
	 * Called after the filter can run
	 * @param evt
	 */
	public void filterReset(FilterEvent evt);
}