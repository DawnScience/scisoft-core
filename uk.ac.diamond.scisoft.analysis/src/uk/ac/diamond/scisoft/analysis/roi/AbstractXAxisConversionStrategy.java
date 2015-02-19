/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.roi;

public abstract class AbstractXAxisConversionStrategy {
	
	//Operations to convert from between each of the existing axis types in the XAxis enum
	public abstract double toANGLE(double initVal, Double lambda) throws Exception;
	public abstract double toPIXEL(double initVal) throws Exception;
	public abstract double toQ(double initVal, Double lambda) throws Exception;
	public abstract double toRESOLUTION(double initVal, Double lambda) throws Exception;
	
	/**
	 * Calculate value of theta in radians from a given two theta value in degrees
	 * @param tthVal
	 * @return theta (radians)
	 */
	protected double calcThetaInRadians(double tthVal) {
		return Math.toRadians(tthVal/2);
	}
	
	/**
	 * Calculate value of two theta in degrees fomr a given theta value in radians
	 * @param thRadians
	 * @return two theta (degrees)
	 */
	protected double calcTwoThetaInDegrees(double thRadians) {
		return 2*Math.toDegrees(thRadians);
	}

}
