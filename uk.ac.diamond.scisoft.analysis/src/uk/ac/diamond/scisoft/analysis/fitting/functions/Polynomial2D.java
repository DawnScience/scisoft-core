/*-
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.fitting.functions;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.ddogleg.solver.PolynomialOps;
import org.ddogleg.solver.PolynomialRoots;
import org.ddogleg.solver.RootFinderType;
import org.eclipse.dawnsci.analysis.api.fitting.functions.IParameter;
import org.eclipse.january.dataset.Dataset;
import org.eclipse.january.dataset.DatasetFactory;
import org.eclipse.january.dataset.DoubleDataset;
import org.ejml.data.Complex64F;

/**
 * Class that wrappers the equation <br>
 * y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n
 */
public class Polynomial2D extends AFunction {
	private static final String NAME = "Polynomial2D";
	private static final String DESC = "A polynomial of degree n."
			+ "\n    y(x) = a_0 x^n + a_1 x^(n-1) + a_2 x^(n-2) + ... + a_(n-1) x + a_n";
	private transient double[] a;
	private transient int nparams; // actually degree + 1
	private transient int degree;
	private transient int noFunctions;

	/**
	 * Basic constructor, not advisable to use
	 */
	public Polynomial2D() {
		this(0);
	}

	/**
	 * Make a polynomial of given degree (0 - constant, 1 - linear, 2 - quadratic, etc)
	 * @param degree
	 */
	public Polynomial2D(int degree) {
		super((int) (Math.pow((degree+1),2)));
		this.degree = degree;
		nparams = (int) Math.pow((degree+1),2);
		noFunctions = (int) Math.pow((degree+1),2);
		parameters = createParameters(noFunctions);
		
	}

	/**
	 * Make a polynomial with given parameters
	 * @param params
	 */
	public Polynomial2D(double[] params) {
		super(params);
	}

	/**
	 * Make a polynomial with given parameters
	 * @param params
	 */
	public Polynomial2D(IParameter... params) {
		super(params);
	}

	/**
	 * Constructor that allows for the positioning of all the parameter bounds
	 * 
	 * @param min
	 *            minimum boundaries
	 * @param max
	 *            maximum boundaries
	 */
	public Polynomial2D(double[] min, double[] max) {
		super(0);
		if (min.length != max.length) {
			throw new IllegalArgumentException("Bound arrays must be of equal length");
		}
		nparams = min.length;
		parameters = new Parameter[nparams];
		a = new double[nparams];

		for (int i = 0; i < nparams; i++) {
			a[i] = 0.5*(min[i] + max[i]);
			parameters[i] = new Parameter(a[i], min[i], max[i]);
		}

		setNames();
	}

	@Override
	public int getNoOfParameters(){
		return nparams;
	}
	
	
	
	@Override
	protected void setNames() {
		if (isDirty() && noFunctions < getNoOfParameters()) {
			noFunctions = getNoOfParameters();
		}
		String[] paramNames = new String[noFunctions];
		for (int i = 0; i < noFunctions; i++) {
			paramNames[i] = "a_" + i;
		}

		setNames(NAME, DESC, paramNames);
	}

	private void calcCachedParameters() {
		if (a == null || a.length != noFunctions) {
			a = new double[noFunctions];
		}
		for (int i = 0; i < noFunctions; i++) {
			a[i] = getParameterValue(i);
		}

		setDirty(false);
	}
	
	

	@Override
	public double val(double... values) {
		if (isDirty()) {
			calcCachedParameters();
		}
		final double position = values[0];

		double v = a[0];
		for (int i = 1; i < noFunctions; i++) {
			v = v * position + a[i];
		}
		return v;
	}

	@Override
	public void fillWithValues(DoubleDataset data, CoordinatesIterator it) {
		
		double[] d = getParameterValues();
		it.reset();
		double[] coords = it.getCoordinates();
		int i = 0;
		double[] buffer = data.getData();

		while (it.hasNext()) {
			double x = coords[0];
			double y = coords[1];
			double temp = 0;
			for (int j = 0; j < (degree+1); j++) {
				for (int k = 0; k < (degree+1); k++) {
					double v = d[(j*(degree+1)+k)]*Math.pow(x, j)*Math.pow(y, k);
					temp += v;
				}
			}
//			if (temp<0){
//				temp=0;
//			}
			buffer[i++] = temp;	
		}
		
		buffer.toString();
	}
	
	public double[] outputParameters() {
		
		double[] d = getParameterValues();
		
		return d;
	}
	
	
	
	public double[][] jacobian (CoordinatesIterator it){
		
		it.reset();
		double[] coords = it.getCoordinates();
		
		double[][] jacobian = new double[2][(int)(Math.pow((degree+1), 2))];
		
		while (it.hasNext()) {
			double x = coords[0];
			double y = coords[1];
		
			for (int j = 0; j < (degree+1); j++) {
				for (int k = 0; k < (degree+1); k++) {
					
					double v =0;
					double u =0;
					
					v = Math.pow(x, j)*Math.pow(y, k);
					u = Math.pow(x, j)*Math.pow(y, k);
					
					jacobian[0][(j*(degree+1)+k)] = v;
					jacobian[1][(j*(degree+1)+k)] = u;
				}
			}
		}
		
		return jacobian;
	}
	
	public void checkFittingParameters() {
		
		double[] d = getParameterValues();
		
		System.out.println(">>>>>>>>>>>Fitted parameters: <<<<<<<<<");
		
		for (int e=0; e<d.length; e++){
				System.out.println("Parameter d[" +e+"]: "+d[e]+"  ########");
		}
		
		
	}
	
	


	public DoubleDataset getOutputValues0 (double[] d,int[] len, int boundaryBox, int fitPower ) {
				
			DoubleDataset output1 = DatasetFactory.zeros(new int[] {len[1], len[0]});//new DoubleDataset(len[1], len[0]);
			
			for (int k=boundaryBox; k<boundaryBox+len[1]; k++){
				for (int l=boundaryBox; l<boundaryBox+len[0]; l++){
					
					double temp = 0;
					double x = k;
					double y = l;
				
					for (int j = 0; j < (fitPower+1); j++) {
						for (int i = 0; i < (fitPower+1); i++) {
							try{
								double v = d[(j*(fitPower+1)+i)]*Math.pow(x, j)*Math.pow(y, i);
								temp += v;
							}
							catch (ArrayIndexOutOfBoundsException exc){
								
							}
						}
					}
					
					output1.set(temp, k-boundaryBox, l-boundaryBox);
				}
			}
		
			return output1;
	}

	public DoubleDataset getOutputValues1 (int[] len, int boundaryBox, int fitPower ) {
		
		double[] d = getParameterValues();
		
		DoubleDataset output1 = this.getOutputValues0(d,len, boundaryBox, fitPower);
	
		return output1;
	}
	
	
	
//	@Override
//	public double partialDeriv(IParameter parameter, double... position) {
//		if (isDuplicated(parameter))
//			return super.partialDeriv(parameter, position);
//
//		int i = indexOfParameter(parameter);
//		if (i < 0)
//			return 0;
//
//		final double pos = position[0];
//		final int n = nparams - 1 - i;
//		switch (n) {
//		case 0:
//			return 1.0;
//		case 1:
//			return pos;
//		case 2:
//			return pos * pos;
//		default:
//			return Math.pow(pos, n);
//		}
//	}

//	@Override
//	public void fillWithPartialDerivativeValues(IParameter parameter, DoubleDataset data, CoordinatesIterator it) {
//		Dataset pos = DatasetUtils.convertToDataset(it.getValues()[0]);
//
//		final int n = nparams - 1 - indexOfParameter(parameter);
//		switch (n) {
//		case 0:
//			data.fill(1);
//			break;
//		case 1:
//			data.setSlice(pos);
//			break;
//		case 2:
//			Maths.square(pos, data);
//			break;
//		default:
//			Maths.power(pos, n, data);
//			break;
//		}
//	}

	public DoubleDataset makeMatrix(List<Dataset> coords, int degree) {
		
		int noFunctions = (int) Math.pow((degree+1),2);
		final int rows = (coords.get(0)).getShape()[0];
		DoubleDataset designMatrix = DatasetFactory.zeros(new int[] {rows, noFunctions}); 
		
		
		for (int l = 0; l < rows; l++) {
			final double x = (coords.get(0)).getDouble(l);
			final double y = (coords.get(1)).getDouble(l);
			double v = 1.0;
			
			for (int i=0; i<= (degree+1); i++){
				for (int j=0; j<= (degree+1); j++){
					double element = v*Math.pow(x,i)*Math.pow(y,j);
					designMatrix.set(element, l, i*j+j);					
					}
			}
			
		}

			return designMatrix;
	}
	
	
	
	public static double[] makeAArray (int degree){
		
		double[] a = new double[(int) Math.pow((degree+1),2)];
		for (int i=0; i<a.length; i++){
			a[i] =1;
		}
		
		return a;
	}
	
	/**
	 * Create a 2D dataset which contains in each row a coordinate raised to n-th powers.
	 * <p>
	 * This is for solving the linear least squares problem 
	 * @param coords
	 * @return matrix
	 */
	public static DoubleDataset makeDesignMatrix(int[][]coords, int degree, double[] a) {
		final int rows = coords.length;
		int noFunctions = (int) Math.pow((degree+1),2);
		DoubleDataset designMatrix = DatasetFactory.zeros(new int[] {rows, noFunctions});
		
		for (int l =0; l<rows; l++){
			for (int i=0; i<= (degree+1); i++){
				for (int j=0; j<= (degree+1); j++){
					double element = a[l]*Math.pow(coords[l][0],i)*Math.pow(coords[l][1],j);
					designMatrix.set(element, l, i+j);
				}
			}
		}
		
		return designMatrix;
		
	}
	
	public static Dataset evaluateDesignMatrix (DoubleDataset designMatrix){
		
		Dataset z = designMatrix.sum(1);
		
		return z;
	}
	
	public static double calculateChiSquared (Dataset z, double[] values){
		
		double chiSquared = 0;
		for (int i = 0; i<values.length; i++){
			chiSquared = chiSquared + Math.pow((values[i] - z.getDouble(i))/(Math.pow(values[i],0.5)),2);
		}
		return chiSquared;
	}
	
	public static int findIndexOfMinimum (double[] array){
		
		double minimum= array[0];
		int index = 0;
		for (int i = 1; i < array.length; i++) {
		  if ( array[i] < minimum) {
		      minimum = array[i];
		      index = i;
		   }
		}
		return index;
	}
	
	public static double[] outputAArray (int[][]coords, double[] values, int degree, int noLoops, double delta){
		
		double a[] = makeAArray(degree);
		int noFunctions = (int) Math.pow((degree+1),2);
		double[][] parameterSpace = new double[2*noFunctions][];
		double[] chiSquaredArray = new double[2*noFunctions];
		
		for (int loopCounter = 0; loopCounter<noLoops;loopCounter++){
			for (int l =0; l<=noFunctions; l++){
				double[] b =a;
				b[l] = a[l]+delta*a[l];
				DoubleDataset designMatrix = makeDesignMatrix(coords, degree, b);
				chiSquaredArray[l] = calculateChiSquared(designMatrix, values);
				parameterSpace[l]=b;
				
				b[l] = a[l]-delta*a[l];
				designMatrix = makeDesignMatrix(coords, degree, b);
				parameterSpace[l + noFunctions]=b;
				designMatrix = makeDesignMatrix(coords, degree, b);
				chiSquaredArray[l + noFunctions] = calculateChiSquared(designMatrix, values);
				parameterSpace[l + noFunctions]=b;
			}
			
			int minimumChiSquaredIndex = findIndexOfMinimum(chiSquaredArray);
			a = parameterSpace[minimumChiSquaredIndex];
		}
		
		return a;
		
	}
	
	public static DoubleDataset outputMatrix (int[][]coords, double[] values, int degree, int noLoops, double delta){
		
		double[] a = outputAArray(coords, values, degree, noLoops, delta);
		
		Dataset outputDesignMatrix = evaluateDesignMatrix(makeDesignMatrix(coords, degree, a));
		
		DoubleDataset output = DatasetFactory.zeros(new int[] {values.length,3});
		
		for (int k = 0; k<values.length;k++){
			output.set(coords[k][0], k, 0);
			output.set(coords[k][1], k, 1);
			output.set(outputDesignMatrix.getDouble(k), k, 2);
		}
		
		return output;
	}
	
	

	/**
	 * Set the degree after a class instantiation
	 * @param degree
	 */
	public void setDegree(int degree) {
		nparams = (int) Math.pow((degree+1),2);
		noFunctions = (int) Math.pow((degree+1),2);
		parameters = createParameters(noFunctions);
		dirty = true;
		
		setNames();

		if (parent != null) {
			parent.updateParameters();
		}
	}
	
	public String getStringEquation() {
		
		StringBuilder out = new StringBuilder();
		
		DecimalFormat df = new DecimalFormat("0.#####E0");
		
		for (int i = nparams-1; i >= 2; i--) {
			out.append(df.format(parameters[nparams - 1 -i].getValue()));
			out.append(String.format("x^%d + ", i));
		}
		
		if (nparams >= 2)
			out.append(df.format(parameters[nparams-2].getValue()) + "x + ");
		if (nparams >= 1)
			out.append(df.format(parameters[nparams-1].getValue()));
		
		return out.toString();
	}

	/**
	 * Find all roots
	 * @return all roots or null if there is any problem finding the roots
	 */
	public Complex[] findRoots() {
		if (isDirty()) {
			calcCachedParameters();
		}
		return findRoots(a);
	}

	/**
	 * Find all roots
	 * @param coeffs
	 * @return all roots or null if there is any problem finding the roots
	 */
	public static Complex[] findRoots(double... coeffs) {
		double[] reverse = coeffs.clone();
		ArrayUtils.reverse(reverse);
		double max = Double.NEGATIVE_INFINITY;
		for (double r : reverse) {
			max = Math.max(max, Math.abs(r));
		}
		for (int i = 0; i < reverse.length; i++) {
			reverse[i] /= max;
		}

		org.ddogleg.solver.Polynomial p = org.ddogleg.solver.Polynomial.wrap(reverse);
		PolynomialRoots rf = PolynomialOps.createRootFinder(p.computeDegree(), RootFinderType.EVD);
		if (rf.process(p)) {
			// reorder to NumPy's roots output
			List<Complex64F> rts = rf.getRoots();
			Complex[] out = new Complex[rts.size()];
			int i = 0;
			for (Complex64F r : rts) {
				out[i++] = new Complex(r.getReal(), r.getImaginary());
			}
			return sort(out);
		}

		return null;
	}

	private static Complex[] sort(Complex[] values) {
		// reorder to NumPy's roots output
		List<Complex> rts = Arrays.asList(values);
		Collections.sort(rts, new Comparator<Complex>() {
			@Override
			public int compare(Complex o1, Complex o2) {
				double a = o1.getReal();
				double b = o2.getReal();
				
				double u = 10*Math.ulp(Math.max(Math.abs(a), Math.abs(b)));
				if (Math.abs(a - b) > u)
					return a < b ? -1 : 1;

				a = o1.getImaginary();
				b = o2.getImaginary();
				if (a == b)
					return 0;
				return a < b ? 1 : -1;
			}
		});

		return rts.toArray(new Complex[0]);
	}
}

//TEST