/*-
 *******************************************************************************
 * Copyright (c) 2011, 2016 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Matthew Gerring - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.dawnsci.nexus.validation;

import javax.measure.Unit;

import si.uom.SI;
import tec.units.indriya.AbstractUnit;

public enum NexusUnitCategory {
	
	NX_ANGLE(SI.RADIAN),
	
	// TODO: check standard unit for this unit category (perhaps any units are ok?)
	NX_ANY(AbstractUnit.ONE),
	
	NX_AREA(SI.SQUARE_METRE),
	
	NX_CHARGE(SI.COULOMB),
	
	NX_CROSS_SECTION(SI.SQUARE_METRE),
	
	NX_CURRENT(SI.AMPERE),
	
	NX_DIMENSIONLESS(AbstractUnit.ONE),
	
	/**
	 * Emmittance, a length * angle, e.g. metre-radians
	 */
	NX_EMITTANCE(SI.METRE.multiply(SI.RADIAN)),
	
	NX_ENERGY(SI.JOULE),
	
	/**
	 * TODO: what unit of flux to use?
	 * The nexus format documentation gives the example: s-1 cm-2,
	 */
	NX_FLUX(SI.SECOND.inverse().divide(SI.SQUARE_METRE)),
	
	NX_FREQUENCY(SI.HERTZ),
	
	NX_LENGTH(SI.METRE),
	
	NX_MASS(SI.KILOGRAM),
	
	NX_MASS_DENSITY(SI.KILOGRAM.divide(SI.CUBIC_METRE)),
	
	NX_MOLECULAR_WEIGHT(SI.KILOGRAM.divide(SI.MOLE)),
	
	/**
	 * Alias to {@link #NX_TIME}
	 */
	NX_PERIOD(SI.SECOND),
	
	NX_PER_AREA(SI.SQUARE_METRE.inverse()),
	
	NX_PER_LENGTH(SI.METRE.inverse()),
	
	NX_POWER(SI.WATT),
	
	NX_PRESSURE(SI.PASCAL),
	
	/**
	 * Alias to NX_NUMBER
	 * TODO check unit for this category - could use sub-interface of Dimensionless
	 */
	NX_PULSES(AbstractUnit.ONE),
	
	NX_SCATTERING_LENGTH_DENSITY(SI.SQUARE_METRE.inverse()),
	
	NX_SOLID_ANGLE(SI.STERADIAN),
	
	NX_TEMPERATURE(SI.KELVIN),
	
	NX_TIME(SI.SECOND),
	
	/**
	 * Alias to {@link #NX_TIME}
	 */
	NX_TIME_OF_FLIGHT(SI.SECOND),
	
	/**
	 * TODO, what units for unitless? could we use a subinterface of Dimensionless?
	 */
	NX_UNITLESS(null),
	
	NX_VOLTAGE(SI.VOLT),
	
	NX_VOLUME(SI.CUBIC_METRE),
	
	NX_WAVELENGTH(SI.METRE),
	
	NX_WAVENUMBER(SI.METRE.inverse());
	
	private Unit<?> standardUnit;
	
	private NexusUnitCategory(Unit<?> unit) {
		// make sure we have the standard unit
		standardUnit = unit.getSystemUnit();
	}
	
	public boolean isCompatible(Unit<?> unit) {
		return unit.getSystemUnit().equals(standardUnit);
	}

}
