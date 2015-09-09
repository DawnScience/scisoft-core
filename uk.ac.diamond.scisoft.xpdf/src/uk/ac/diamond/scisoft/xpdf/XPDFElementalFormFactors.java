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

public class XPDFElementalFormFactors {

	
	private static Dataset genericFormFactor(double[] a, double[] b, double c, Dataset x) {
		final int nTerms = 5;
		Dataset formFactor = DoubleDataset.zeros(x);
		Dataset xsq = Maths.square(x);
		for (int i = 0; i < nTerms; i++)
			formFactor.iadd(Maths.multiply(a[i], Maths.exp(Maths.multiply(-b[i], xsq))));
		
		formFactor.iadd(c);
		return formFactor;
	}
	
	public static Dataset Sofx(int z, Dataset x) {
		 return genericFormFactor(XPDFSofx.getA(z), XPDFSofx.getB(z), XPDFSofx.getC(z), x);
	}
	
	public static Dataset fofx(int z, Dataset x) {
		 return genericFormFactor(XPDFfofx.getA(z), XPDFfofx.getB(z), XPDFfofx.getC(z), x);
	}

}
