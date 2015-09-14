/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.DoubleDataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

/**
 * Returns the elemental form factors form eleastic and inelastic scattering.
 * @author Timothy Spain (rkl37156) timothy.spain@diamond.ac.uk
 *
 */
public class XPDFElementalFormFactors {

	/**
	 * Calculates a form factor using the sum of 5 Gaussian model.
	 * <p>
	 * Calculates a form factor, using the sum of 5 Gaussians model used in
	 * both Waasmeier & Kirfel (1994) and Balyuzi (1975).
	 * @param a
	 * 		exponential scaling multiplier.
	 * @param b
	 * 		exponential factor.
	 * @param c
	 * 		additive constant.
	 * @param x
	 * 		sin 2θ/λ parameter for which the form factors are desired.
	 * @return the calculated form factor
	 */
	private static Dataset genericFormFactor(double[] a, double[] b, double c, Dataset x) {
		final int nTerms = 5;
		Dataset formFactor = DoubleDataset.zeros(x);
		Dataset xsq = Maths.square(x);
		for (int i = 0; i < nTerms; i++)
			formFactor.iadd(Maths.multiply(a[i], Maths.exp(Maths.multiply(-b[i], xsq))));
		
		formFactor.iadd(c);
		return formFactor;
	}
	
	/**
	 * Gets the inelastic scattering form factor. 
	 * @param z
	 * 			atomic number of the element.
	 * @param x
	 * 			sin 2θ/λ parameter for which the form factor is desired.
	 * @return inelastic (incoherent) scattering form factor.
	 */
	public static Dataset Sofx(int z, Dataset x) {
		 return genericFormFactor(XPDFSofx.getA(z), XPDFSofx.getB(z), XPDFSofx.getC(z), x);
	}
	
	/**
	 * Gets the elastic scattering form factor. 
	 * @param z
	 * 			atomic number of the element.
	 * @param x
	 * 			sin 2θ/λ parameter for which the form factor is desired.
	 * @return elastic scattering form factor.
	 */
	public static Dataset fofx(int z, Dataset x) {
		 return genericFormFactor(XPDFfofx.getA(z), XPDFfofx.getB(z), XPDFfofx.getC(z), x);
	}

}
