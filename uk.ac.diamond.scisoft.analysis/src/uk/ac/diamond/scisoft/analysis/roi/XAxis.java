/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.roi;

public enum XAxis {
	ANGLE (0, "2theta / deg", new FromANGLEConversionStrategy()) ,
	PIXEL (1, "Pixel Number", new FromPIXELConversionStrategy()) ,
	Q(2, "Q-space (inverse Angstrom)", new FromQConversionStrategy()) ,
	Qnm(3, "Q-space (inverse nanometre)", new FromQnmConversionStrategy()) ,
	Qm(4, "Q-space (inverse metre)", new FromQmConversionStrategy()) ,
	RESOLUTION (5, "d-space", new FromRESOLUTIONConversionStrategy()) ,;
	
	
	private final String axisLabel;
	private final int axisStrategyInteger;
	
	private AbstractXAxisConversionStrategy conversionStrategy;
	XAxis(int axisStrategyInteger, String axisLabel, AbstractXAxisConversionStrategy strategy) {
		this.axisLabel = axisLabel;
		this.axisStrategyInteger = axisStrategyInteger;
		setXAxisConversionStrategy(strategy);
	}
	public String getXAxisLabel() {
		return axisLabel;
	}
	
	@Override
	public String toString() {
		switch (this.axisStrategyInteger) {
			case 0 :	return String.format("Angle (degrees)");
			case 1 :	return String.format("Pixel number (pixels)");
			case 2 :	return String.format("Q (inverse Angstroms)");
			case 3 :	return String.format("Q (inverse nanometers)");
			case 4 :	return String.format("Q (inverse meters)");
			case 5 :	return String.format("d-spacing (Angstrom)");
			default:	return String.format("Error!");
		}
	}
	
	private void setXAxisConversionStrategy(AbstractXAxisConversionStrategy strategy) {
		conversionStrategy = strategy;
	}
	
	public double convertToANGLE(double initVal, Double lambda) throws Exception{
		return conversionStrategy.toANGLE(initVal, lambda);
	}
	
	public double convertToPIXEL(double initVal, Double lambda) throws Exception{
		return conversionStrategy.toPIXEL(initVal);
	}
	
	public double convertToQ(double initVal, Double lambda) throws Exception{
		return conversionStrategy.toQ(initVal, lambda);
	}
	
	public double convertToQnm(double initVal, Double lambda) throws Exception{
		return conversionStrategy.toQnm(initVal, lambda);
	}
	
	public double convertToRESOLUTION(double initVal, Double lambda) throws Exception{
		return conversionStrategy.toRESOLUTION(initVal, lambda);
	}
}
