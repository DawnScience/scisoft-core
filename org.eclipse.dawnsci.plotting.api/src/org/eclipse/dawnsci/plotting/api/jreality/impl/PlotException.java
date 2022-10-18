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

package org.eclipse.dawnsci.plotting.api.jreality.impl;

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;
/**
 *
 */
@Deprecated(since="at least 2015")
public class PlotException extends Exception {

	private static final DeprecationLogger logger = DeprecationLogger.getLogger(PlotException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlotException(String failureMessage) {
		super(failureMessage);
		logger.deprecatedClass();
	}
}
