/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.xpdf;

import java.util.Map;

import org.eclipse.dawnsci.analysis.dataset.impl.Dataset;
import org.eclipse.dawnsci.analysis.dataset.impl.Maths;

public class XPDFCalibration {

	
	public static double KroghMoeSum(Map<Integer, Integer> composition, double numberDensity, double electronOverlap) {
		
		double KMSum = 0.0;
		int atomSum = 0;
		
		for (Map.Entry<Integer, Integer> iZN1 : composition.entrySet()) {
			atomSum += iZN1.getValue();
			for (Map.Entry<Integer, Integer> iZN2 : composition.entrySet()) {
				KMSum += iZN1.getValue()*iZN2.getValue()*(iZN1.getKey()*iZN2.getKey() - electronOverlap);
			}
		}
		KMSum /= (atomSum*atomSum);
		return 2*Math.PI*Math.PI*numberDensity*KMSum;
	}
	
	public static double QQIntegral(Dataset q, Dataset a, Dataset b) {
		double dq = q.getDouble(1) - q.getDouble(0);
		double integral = quadrateDataset(Maths.multiply(Maths.square(q), Maths.divide(a, b)));
		return dq*integral;
	}
	
	// TODO: a more sophisticated quadrature formula than the rectangle rule
	private static double quadrateDataset(Dataset integrand) {
		return (double) integrand.sum();
	}
}
