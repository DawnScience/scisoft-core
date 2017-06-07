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
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.spi.ServiceProvider;

import tec.units.ri.unit.Units;

public interface ScatteringVector {

	final static Quantity<Length> length = ServiceProvider.current().getQuantityFactory(Length.class).create(1, Units.METRE);
	public final static Quantity<?> QUANTITY = length.inverse();
	public final static Unit<?> UNIT = length.inverse().getUnit();
}
