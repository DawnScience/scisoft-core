/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.optimize;

//import org.apache.commons.math3.FunctionEvaluationException;


/**
 * Class which wraps the Apache Commons Conjugate Gradient fitting routine
 * and makes it compatible with the scisoft fitting routines
 */
@Deprecated
public class ApacheConjugateGradient extends ApacheOptimizer {
	public ApacheConjugateGradient() {
		super(Optimizer.CONJUGATE_GRADIENT);
	}
}
