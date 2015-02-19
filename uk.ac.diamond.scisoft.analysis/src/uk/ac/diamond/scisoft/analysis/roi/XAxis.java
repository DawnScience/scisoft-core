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
	ANGLE ("2theta / deg", new FromANGLEConversionStrategy()) ,
	PIXEL ("Pixel Number", new FromPIXELConversionStrategy()) ,
	Q("Q-space", new FromQConversionStrategy()) ,
	RESOLUTION ("d-space", new FromRESOLUTIONConversionStrategy()) ,;
	
	
	private final String axisLabel;
	private AbstractXAxisConversionStrategy conversionStrategy;
	XAxis(String axisLabel, AbstractXAxisConversionStrategy strategy) {
		this.axisLabel = axisLabel;
		setXAxisConversionStrategy(strategy);
	}
	public String getXAxisLabel() {
		return axisLabel;
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
	
	public double convertToRESOLUTION(double initVal, Double lambda) throws Exception{
		return conversionStrategy.toRESOLUTION(initVal, lambda);
	}
}
