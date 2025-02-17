/*
 * Copyright (c) 2012 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package uk.ac.diamond.scisoft.analysis.crystallography;

import javax.measure.Quantity;
import javax.measure.Unit;

import tec.units.indriya.unit.Units;

public interface ScatteringVectorOverDistance extends Quantity<ScatteringVectorOverDistance> {
	@SuppressWarnings("unchecked")
	public final static Unit<ScatteringVectorOverDistance> UNIT = (Unit<ScatteringVectorOverDistance>) Units.METRE.inverse().pow(2);
}
