/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.fitting.functions;

import org.eclipse.january.dataset.IDataset;

/**
 * Reference implementation for a function to be accessible in jexl function expression
 */
public class JexlFunctionConnector {
	
	/**
	 * Wrapper for standard peak functions
	 * 
	 * @param x
	 * @param p
	 * @param w
	 * @param a
	 * @return gaussian
	 */
	public static IDataset Gaussian(IDataset x, double p, double w, double a) {
		Gaussian g = new Gaussian(p, w, a);
		return g.calculateValues(x);
	}
	
	public static IDataset Lorentzian(IDataset x, double p, double w, double a) {
		Lorentzian l = new Lorentzian(p, w, a);
		return l.calculateValues(x);
	}
	
	public static IDataset PseudoVoigt(IDataset x, double p, double lw, double gw, double a, double mix) {
		PseudoVoigt pv = new PseudoVoigt(p, lw, a, gw, mix);
		return pv.calculateValues(x);
	}
	
	public static IDataset PearsonVII(IDataset x, double p, double w, double a, double power) {
		PearsonVII p7 = new PearsonVII(p,w,a,power);
		return p7.calculateValues(x);
	}

}
