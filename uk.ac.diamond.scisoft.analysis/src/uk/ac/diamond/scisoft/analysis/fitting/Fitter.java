/*
 * Copyright 2011 Diamond Light Source Ltd.
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

package uk.ac.diamond.scisoft.analysis.fitting;

import uk.ac.diamond.scisoft.analysis.dataset.AbstractDataset;
import uk.ac.diamond.scisoft.analysis.dataset.IDataset;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheConjugateGradient;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheMultiDirectional;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheNelderMead;
import uk.ac.diamond.scisoft.analysis.optimize.ApachePolynomial;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.GradientDescent;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.LeastSquares;
import uk.ac.diamond.scisoft.analysis.optimize.NelderMead;
import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.IFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;

public class Fitter {
	
	private static final double simplexQuality = 1e-6;
	public static double quality = 1e-4; 
	public static Long seed = null; 

	public static void simplexFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) throws Exception {
		simplexFit(simplexQuality, coords, yAxis, function);
	}
	
	public static void simplexFit(final double quality, final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) throws Exception {
		
		NelderMead nm = new NelderMead(quality);
		
		nm.optimize(coords, yAxis, function);	
	}
		
	public static void ApacheNelderMeadFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) throws Exception {
		
		ApacheNelderMead anm = new ApacheNelderMead();
		
		anm.optimize(coords, yAxis, function);	
	}	
	
	public static void ApacheMultiDirectionFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) throws Exception {
		
		ApacheMultiDirectional amd = new ApacheMultiDirectional();
		
		amd.optimize(coords, yAxis, function);	
	}
		
	public static void ApacheConjugateGradientFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) throws Exception {
		
		ApacheConjugateGradient acg = new ApacheConjugateGradient();
		
		acg.optimize(coords, yAxis, function);	
	}

	public static void GDFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) throws Exception {
		GDFit(quality, coords, yAxis, function);
	}
	
	public static void GDFit(final double quality, final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) throws Exception {
		
		GradientDescent gd = new GradientDescent(quality);
		
		gd.optimize(coords, yAxis, function);	
	}
	
	
	/**
	 * Genetic algorithm fitter
	 * @param coords
	 * @param yAxis
	 * @param function
	 */
	public static void geneticFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) {
		geneticFit(quality, coords, yAxis, function);
	}

	/**
	 * Genetic algorithm fitter
	 * @param quality
	 * @param coords
	 * @param yAxis
	 * @param function
	 */
	public static void geneticFit(final double quality, final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) {
	
		GeneticAlg ga = seed == null ? new GeneticAlg(quality) : new GeneticAlg(quality, seed); 

		ga.optimize(coords, yAxis, function);
	}

	/**
	 * Linear least squares fitter
	 * @param coords
	 * @param yAxis
	 * @param function
	 */
	public static void llsqFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final IFunction function) {
		LeastSquares lsq = new LeastSquares(0); 
	
		lsq.optimize(coords, yAxis, function);
	}

	/**
	 * Polynomial fitter
	 * @param coords
	 * @param yAxis
	 * @param rcond relative condition number used to limit singular values to use (try 1e-15)
	 * @param degree of polynomial
	 * @throws Exception 
	 */
	public static Polynomial polyFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final double rcond, final int degree) throws Exception {
		Polynomial polynomial = new Polynomial(degree);
		polyFit(coords, yAxis, rcond, polynomial);
		return polynomial;
	}

	/**
	 * Polynomial fitter
	 * @param coords
	 * @param yAxis
	 * @param rcond relative condition number used to limit singular values to use (try 1e-15)
	 * @param polynomial
	 * @throws Exception 
	 */
	public static void polyFit(final AbstractDataset[] coords, final AbstractDataset yAxis, final double rcond, final Polynomial polynomial) throws Exception {
		//determine polynomial order from number of parameters in polynomial
		int polyOrder = polynomial.getNoOfParameters()-1;
		
		double[] values = ApachePolynomial.polynomialFit(coords[0], yAxis, polyOrder);
		
		//the polynomial object expects the coefficient array to be in the reverse order to the Apache answer 
		double[] flipped = values.clone();
		for (int i = 0; i < flipped.length; i++)
			flipped[flipped.length-1-i] = values[i];
		
		polynomial.setParameterValues(flipped);
	}

	/**
	 * This function takes a pair of datasets and some other inputs, and then
	 * fits the function specified using the method specified.
	 * 
	 * @param xAxis
	 *            The dataset containing all the x values of the data
	 * @param yAxis
	 *            The dataset containing all the y values of the data
	 * @param optimizer
	 *            The optimiser which implements IOptimizer, which is to be used
	 * @param functions
	 *            A list of functions which inherit from AFunction which are
	 *            used to make up the function to be fit.
	 * @throws Exception 
	 */
	public static CompositeFunction fit(IDataset xAxis, IDataset yAxis,
			                         IOptimizer optimizer, AFunction... functions) throws Exception {

		CompositeFunction comp = new CompositeFunction();
		IDataset[] coords = new IDataset[] {xAxis};

		for (int i = 0; i < functions.length; i++) {
			comp.addFunction(functions[i]);
		}

		// call the optimisation routine
		optimizer.optimize(coords, yAxis, comp);

		return comp;
	}
}

