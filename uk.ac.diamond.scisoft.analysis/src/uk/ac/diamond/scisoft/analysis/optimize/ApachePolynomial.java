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

import org.apache.commons.math.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math.optimization.fitting.PolynomialFitter;
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer;

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
	
	/**
	 * Calculate a polynomial filtered dataset using a specified window size and polynomial order.
	 * 
	 * @param x The abscissa (generally energy/wavelength etc)
	 * @param y The ordinate to be smoothed
	 * @param windowSize The size of window used in the smoothing
	 * @param polyOrder The order of the polynomial fitted to the window
	 * @return result The smoothed data set
	 * @throws Exception 
	 */
	public static AbstractDataset getPolynomialSmoothed(final AbstractDataset x, final AbstractDataset y,int windowSize, int polyOrder) throws Exception{
		//Could probably do with more sanity check on relative size of window vs polynomial but doesnt seem to trip up
		// So we'll see how it goes...
		
		//integer divide window size so window is symmetric around point
		int window = windowSize/2;
		
		PolynomialFitter fitter = new PolynomialFitter(polyOrder, new LevenbergMarquardtOptimizer());
		double dx = x.getDouble(1) - x.getDouble(0); //change in x for edge extrapolation
		int xs =  x.getSize();
		AbstractDataset result = AbstractDataset.zeros(y);
		
		for (int idx = 0; idx < xs; idx++){
			
			fitter.clearObservations();
			
			//Deal with edge cases:
			//In both cases extend x edge by dx required for window size
			//Pad y with first or last value
			for (int idw = -window; idw < window+1; idw++) {
				if (idx+idw < 0)
					fitter.addObservedPoint(1,x.getDouble(0)+(dx*(idx+idw)), y.getDouble(0));
				else if ((idx + idw) > (x.getSize()-1))
					fitter.addObservedPoint(1,x.getDouble(xs-1) + dx*(idx + idw -(xs-1)), y.getDouble(xs-1));
				else
					fitter.addObservedPoint(1,x.getDouble(idx+idw), y.getDouble(idx+idw));
				
			}
			
			PolynomialFunction fitted = new PolynomialFunction(fitter.fit().getCoefficients());
			result.set(fitted.value(x.getDouble(idx)),idx);	
		}
		
		return result;
		
	}
}
