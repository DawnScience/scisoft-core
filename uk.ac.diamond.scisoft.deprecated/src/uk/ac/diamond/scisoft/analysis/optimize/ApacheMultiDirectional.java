/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import uk.ac.diamond.daq.util.logging.deprecation.DeprecationLogger;

/**
 * Use {@link ApacheOptimizer} with {@link ApacheOptimizer.Optimizer#SIMPLEX_MD}
 */
@Deprecated(since="Dawn 1.11")
public class ApacheMultiDirectional extends ApacheOptimizer {
	
	private static final DeprecationLogger logger = DeprecationLogger.getLogger(ApacheMultiDirectional.class);

	public ApacheMultiDirectional() {
		super(Optimizer.SIMPLEX_MD);
		logger.deprecatedClass(null, "uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer"
				+ " with uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer.SIMPLEX_MD");
	}
}
