/*
 * Copyright (c) 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package uk.ac.diamond.scisoft.analysis.crystallography;

/**
 * A mathematics class to solve the Birch-Murnaghan equation of state for
 * either p as a function of v/v0 (direct evaluation) or v/v0 as a function of
 * p (Newton's method)
 * @author Timothy Spain, timothy.spain@diamond.ac.uk
 *
 */

public class BirchMurnaghanSolver {

	// In the below, x is the linear unit cell size as a function of pressure x = (V/V0)^(1/3)
	private static double term1(double x, double bulkModulus) {
		return 3./2 * bulkModulus * (Math.pow(x, -7.) - Math.pow(x, -5.));
	}
	private static double dTerm1_dx(double x, double bulkModulus) {
		return 3./2. * bulkModulus * (-7. * Math.pow(x, 8.) + 5. * Math.pow(x,  6.));
	}
	
	private static double term2(double x, double bulkModulus_p) {
		return ( 1 + 3./4.*(bulkModulus_p - 4)*(Math.pow(x, -2.) - 1));
	}

	private static double dTerm2_dx(double x, double bulkModulus_p) {
		return 3./4. * (bulkModulus_p - 4) * -2. * Math.pow(x, -3.);
	}
	
	private static double fBirchMurnaghan(double x, double bulkModulus, double bulkModulus_p) {
		return  term1(x, bulkModulus) * term2(x, bulkModulus_p);
	}
	
	private static double dBirchMurnaghan(double x, double bulkModulus, double bulkModulus_p) {
		return term1(x, bulkModulus) * dTerm2_dx(x, bulkModulus_p) + dTerm1_dx(x, bulkModulus) * term2(x, bulkModulus_p);
	}

	/**
	 * Solves the Birch-Murnaghan equation of state for linear scale (x/x0) as a function of pressure
	 * @param pressure
	 * 				input pressure
	 * @param bulkModulus
	 * 					material bulk modulus (same units)
	 * @param bulkModulusDerivative
	 * 								material first derivative of the bulk modulus with respect to pressure (dimensionless)
	 * @return
	 * 		ratio of the length at the given pressure to the length at zero pressure
	 */
	public static double birchMurnaghanLinear(double pressure, double bulkModulus, double bulkModulusDerivative) {
		// Solve the Birch-Murnaghan equation of state to get the linear ratio at the current pressure
		final double error = 1e-6;
		double x1, x2 = 1;
		
		do {
			x1 = x2;
			x2 = x1 - (fBirchMurnaghan(x1, bulkModulus, bulkModulusDerivative) - pressure) / dBirchMurnaghan(x1, bulkModulus, bulkModulusDerivative);
		} while (Math.abs(x2/x1 - 1) > error);
		
		return x2;
	}
	
	/**
	 * Solves the Birch-Murnaghan equation of state for volume ratio (V/V0) as a function of pressure
	 * @param pressure
	 * 				input pressure
	 * @param bulkModulus
	 * 					material bulk modulus (same units)
	 * @param bulkModulusDerivative
	 * 								material first derivative of the bulk modulus with respect to pressure (dimensionless)
	 * @return
	 * 		ratio of the volume at the given pressure to the volume at zero pressure
	 */
	public static double birchMurnaghanVolume(double pressure, double bulkModulus, double bulkModulusDerivative) {
		return Math.pow(birchMurnaghanLinear(pressure, bulkModulus, bulkModulusDerivative), 3);
	}
	
	
	/**
	 * Evaluates the Birch-Murnaghan equation of state to get pressure as a function of V/V0
	 * @param v_v0
	 * 			ratio of current volume to volume at zero pressure
	 * @param bulkModulus
	 * 					bulk modulus of the material
	 * @param bulkModulusDerivative
	 * 							first derivative of the bulk modulus with respect to pressure of the material
	 * @return pressure required to achieve the requested volume (same units as the bulk modulus parameter).
	 */
	public static double birchMurnaghanPressure(double v_v0, double bulkModulus, double bulkModulusDerivative) {
		double x = Math.pow(v_v0, -1./3.);
		return fBirchMurnaghan(x, bulkModulus, bulkModulusDerivative);
	}

	
}
