/*
 * Copyright (c) 2012, 2017 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.measure.Quantity;
import javax.measure.unit.ProductUnit;
import javax.measure.Unit;

import si.uom.SI;

public interface ScatteringVectorOverDistance extends Quantity {
	
    public final static Unit<ScatteringVectorOverDistance> UNIT 
         = new ProductUnit<ScatteringVectorOverDistance>(Unit.ONE.divide(SI.METRE.pow(2)));
}