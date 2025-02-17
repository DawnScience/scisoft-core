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

package uk.ac.diamond.scisoft.analysis.histogram.functions;

/**
 *  Square map function, takes the square of an input value
 */
public class SquareMapFunction extends AbstractMapFunction {
	@Override
	public String getMapFunctionName() {
		return "x^2";
	}

	@Override
	public double mapFunction(double input) {
		return input*input;
	}
}
