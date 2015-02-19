/*-
 * Copyright 2015 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.roi;

public class FromRESOLUTIONConversionStrategy extends AbstractXAxisConversionStrategy {

	@Override
	public double toANGLE(double initVal, Double lambda) throws Exception {
		Double thRadians = Math.asin(lambda/(2*initVal));
		return calcTwoThetaInDegrees(thRadians);
	}

	@Override
	public double toPIXEL(double initVal) throws Exception {
		throw new Exception("Unimplemented method.");
	}

	@Override
	public double toQ(double initVal, Double lambda) throws Exception {
		return (2*Math.PI)/initVal;
	}

	@Override
	public double toRESOLUTION(double initVal, Double lambda) throws Exception {
		return initVal; //Do nothing
	}
}
