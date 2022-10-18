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
 * Use {@link ApacheOptimizer} with {@link ApacheOptimizer.Optimizer#CONJUGATE_GRADIENT}
 */
@Deprecated(since="Dawn 1.11")
public class ApacheConjugateGradient extends ApacheOptimizer {
	
	private static final DeprecationLogger logger = DeprecationLogger.getLogger(ApacheConjugateGradient.class);
	public ApacheConjugateGradient() {
		super(Optimizer.CONJUGATE_GRADIENT);
		logger.deprecatedClass(null, "uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer"
				+ " with uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer.CONJUGATE_GRADIENT");
	}
}
