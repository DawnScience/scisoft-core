/*-
 * Copyright 2012 Diamond Light Source Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.diamond.scisoft.analysis.optimize;

import org.apache.commons.math.optimization.fitting.PolynomialFitter;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;
import org.apache.commons.math.analysis.polynomials.PolynomialFunction;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;

public class ApachePolynomial {
	
	public static double[] polynomialFit(AbstractDataset x, AbstractDataset y, int polyOrder) throws Exception {
		
		PolynomialFitter fitter = new PolynomialFitter(polyOrder, new LevenbergMarquardtOptimizer());
		
		for (int i = 0; i < y.getSize(); i++) {
			fitter.addObservedPoint(1,x.getDouble(i),y.getDouble(i));
		}
		
		PolynomialFunction fitted = fitter.fit();
		return fitted.getCoefficients();
		
	}
}
