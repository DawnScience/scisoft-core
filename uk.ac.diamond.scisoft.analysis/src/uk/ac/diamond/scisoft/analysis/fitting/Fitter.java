/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting;

import org.eclipse.dawnsci.analysis.api.fitting.functions.IFunction;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DoubleDataset;
import org.eclipse.january.dataset.IDataset;

import uk.ac.diamond.scisoft.analysis.fitting.functions.AFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.CompositeFunction;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Gaussian;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Offset;
import uk.ac.diamond.scisoft.analysis.fitting.functions.Polynomial;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApacheOptimizer.Optimizer;
import uk.ac.diamond.scisoft.analysis.optimize.ApachePolynomial;
import uk.ac.diamond.scisoft.analysis.optimize.GeneticAlg;
import uk.ac.diamond.scisoft.analysis.optimize.GradientDescent;
import uk.ac.diamond.scisoft.analysis.optimize.IOptimizer;
import uk.ac.diamond.scisoft.analysis.optimize.LeastSquares;
import uk.ac.diamond.scisoft.analysis.optimize.NelderMead;

public class Fitter {
	
	private static final double simplexQuality = 1e-6;
	public static double quality = 1e-4; 
	public static Long seed = null; 

	public static void simplexFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		simplexFit(simplexQuality, coords, yAxis, null, function);
	}

	public static void simplexFit(final double quality, final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		NelderMead opt = new NelderMead(quality);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}

	public static void simplexFit(final double quality, final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		simplexFit(quality, coords, yAxis, null, function);
	}

	/**
	 * Run the Apache Nelder Mead fitter
	 * @param coords
	 * @param yAxis
	 * @param weight used to multiply squared differences to calculate residuals
	 * @param function
	 * @throws Exception
	 */
	public static void ApacheNelderMeadFit(final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.SIMPLEX_NM);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}

	public static void ApacheNelderMeadFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		ApacheNelderMeadFit(coords, yAxis, null, function);
	}

	public static void ApacheMultiDirectionFit(final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.SIMPLEX_MD);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}

	public static void ApacheMultiDirectionFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		ApacheMultiDirectionFit(coords, yAxis, null, function);
	}

	public static void ApacheConjugateGradientFit(final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.CONJUGATE_GRADIENT);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}

	public static void ApacheConjugateGradientFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		ApacheConjugateGradientFit(coords, yAxis, null, function);
	}

	public static void ApacheLevenbergMarquardtFit(final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		ApacheOptimizer opt = new ApacheOptimizer(Optimizer.LEVENBERG_MARQUARDT);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}
	
	public static void ApacheLevenbergMarquardtFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		ApacheLevenbergMarquardtFit(coords, yAxis, null, function);
	}
	
	public static void GDFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		GDFit(quality, coords, yAxis, null, function);
	}

	public static void GDFit(final double quality, final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		GradientDescent opt = new GradientDescent(quality);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}

	public static void GDFit(final double quality, final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		GDFit(quality, coords, yAxis, null, function);
	}

	/**
	 * Genetic algorithm fitter
	 * @param coords
	 * @param yAxis
	 * @param function
	 * @throws Exception 
	 */
	public static void geneticFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		geneticFit(quality, coords, yAxis, null, function);
	}

	/**
	 * Genetic algorithm fitter
	 * @param quality
	 * @param coords
	 * @param yAxis
	 * @param weight used to multiply squared differences to calculate residuals
	 * @param function
	 * @throws Exception 
	 */
	public static void geneticFit(final double quality, final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		GeneticAlg opt = new GeneticAlg(quality, seed);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}

	public static void geneticFit(final double quality, final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		geneticFit(quality, coords, yAxis, null, function);
	}

	/**
	 * Linear least squares fitter
	 * @param coords
	 * @param yAxis
	 * @param function
	 * @throws Exception 
	 */
	public static void llsqFit(final Dataset[] coords, final Dataset yAxis, final Dataset weight, final IFunction function) throws Exception {
		LeastSquares opt = new LeastSquares(0);
		if (weight != null) {
			opt.setWeight(weight.cast(DoubleDataset.class));
		}
		opt.optimize(coords, yAxis, function);
	}

	public static void llsqFit(final Dataset[] coords, final Dataset yAxis, final IFunction function) throws Exception {
		llsqFit(coords, yAxis, null, function);
	}

	/**
	 * Polynomial fitter
	 * @param coords
	 * @param yAxis
	 * @param rcond relative condition number used to limit singular values to use (try 1e-15)
	 * @param degree of polynomial
	 */
	public static Polynomial polyFit(final Dataset[] coords, final Dataset yAxis, final double rcond, final int degree) {
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
	 */
	public static void polyFit(final Dataset[] coords, final Dataset yAxis, @SuppressWarnings("unused") final double rcond, final Polynomial polynomial) {
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
			                         IOptimizer optimizer, IFunction... functions) throws Exception {

		CompositeFunction comp = new CompositeFunction();
		IDataset[] coords = new IDataset[] {xAxis};

		for (int i = 0; i < functions.length; i++) {
			comp.addFunction(functions[i]);
		}

		// call the optimisation routine
		optimizer.optimize(coords, yAxis, comp);

		return comp;
	}
	
	public static AFunction GaussianFit(Dataset data, Dataset axis) {
		
		Gaussian gauss = new Gaussian(axis.min().doubleValue(), 
				axis.max().doubleValue(), 
				axis.peakToPeak().doubleValue(), 
				Math.abs(axis.peakToPeak().doubleValue() * data.peakToPeak().doubleValue()));
		Offset offset = new Offset(data.min().doubleValue(), data.max().doubleValue());
		CompositeFunction comp = new CompositeFunction();
		comp.addFunction(gauss);
		comp.addFunction(offset);
		
		try {
			geneticFit(new Dataset[] {axis}, data, comp);
		} catch (Exception e) {
		}
		return comp;
	}
	
	public static NDGaussianFitResult NDGaussianSimpleFit(Dataset data, Dataset... axis) {
		if (data.getRank() != axis.length) {
			//TODO make this better
			throw new IllegalArgumentException("Incorrect number of Axis");
		}
		int dims = axis.length;
		
		// first resolve the problem into n 1D problems
		AFunction[] results = new AFunction[dims];
		for (int i = 0; i < dims; i++) {
			Dataset flattened = data;
			for (int j = 0; j < dims-1; j++) {
				if (j < i) {
					flattened = flattened.sum(0);
				} else {
					flattened = flattened.sum(1);
				}
			}
			results[i] = GaussianFit(flattened, axis[i]);
		}
		
		return new NDGaussianFitResult(results);
	}
}

