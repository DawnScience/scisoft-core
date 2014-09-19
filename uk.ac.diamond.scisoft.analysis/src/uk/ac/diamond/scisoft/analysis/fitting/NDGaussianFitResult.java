/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;

public class NDGaussianFitResult {

	private double[] pos = null;
	private double[] fwhm = null;
	private double[] area = null;
	private double[] background = null;
	
	public NDGaussianFitResult(AFunction... results) {
		pos = new double[results.length];
		fwhm = new double[results.length];
		area = new double[results.length];
		background = new double[results.length];
		
		for (int i = 0; i < results.length; i++) {
			pos[i] = results[i].getParameterValue(0);
			fwhm[i] = results[i].getParameterValue(1);
			area[i] = results[i].getParameterValue(2);
			background[i] = results[i].getParameterValue(3);
		}
	}

	public double[] getPos() {
		return pos;
	}

	public double[] getFwhm() {
		return fwhm;
	}

	public double[] getArea() {
		return area;
	}

	public double[] getBackground() {
		return background;
	}
	
	

}
