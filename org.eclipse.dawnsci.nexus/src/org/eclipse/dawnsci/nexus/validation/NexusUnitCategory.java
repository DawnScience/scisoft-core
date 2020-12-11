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

import java.util.Arrays;

import javax.measure.Unit;

import si.uom.SI;
import tec.units.indriya.AbstractUnit;

/**
 * An enumeration of unit types.
 * Note: this class is derived from the file {@code nxdlType.xdl} which can be found on GitHub
 * <a href="https://github.com/nexusformat/definitions/blob/master/nxdlTypes.xsd">here</a>.
 * Generated documentation from that file can be found in the Nexus manual
 * <a href="https://manual.nexusformat.org/nxdl-types.html#unit-categories-allowed-in-nxdl-specifications">here</a>.
 * The file is hand-written, rather than auto-generated from that file using XSLT. 
 * TODO: should we try to do this, see https://jira.diamond.ac.uk/browse/DAQ-3300
 * @author Matthew Dickie
 */
public enum NexusUnitCategory {
	
	NX_ANGLE(SI.RADIAN),
	
	NX_ANY(null) {

		@Override
		public boolean isCompatible(Unit<?> unit) {
			return true; // all units are accepted
		}
		
	},
	
	NX_AREA(SI.SQUARE_METRE),
	
	NX_CHARGE(SI.COULOMB),
	
	NX_CROSS_SECTION(SI.SQUARE_METRE), // alias of NX_AREA
	
	NX_CURRENT(SI.AMPERE),
	
	NX_DIMENSIONLESS(AbstractUnit.ONE), // TODO is this right?
	
	/**
	 * Emmittance, a length * angle, e.g. metre-radians
	 */
	NX_EMITTANCE(SI.METRE.multiply(SI.RADIAN)),
	
	NX_ENERGY(SI.JOULE),
	
	/**
	 * some number (e.g. photons) per square meter per second.
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
	
	NX_PULSES(AbstractUnit.ONE), // TODO is this right?
	
	NX_SCATTERING_LENGTH_DENSITY(SI.METRE.divide(SI.CUBIC_METRE)), // definition gives example m/m^3 
	
	NX_SOLID_ANGLE(SI.STERADIAN),
	
	NX_TEMPERATURE(SI.KELVIN),
	
	NX_TIME(SI.SECOND),
	
	/**
	 * Alias to {@link #NX_TIME}
	 */
	NX_TIME_OF_FLIGHT(SI.SECOND),
	
	/**
	 * From https://manual.nexusformat.org/nxdl-types.html:
	 * units of the specified transformation
	 * could be any of these: NX_LENGTH, NX_ANGLE, or NX_UNITLESS
	 * Note: this unit type is not specified in application definition, but it is specified in the
	 * NXtransformations base class definition.
	 */
	NX_TRANSFORMATION(null) {

		@Override
		public boolean isCompatible(Unit<?> unit) {
			return Arrays.asList(NX_LENGTH, NX_ANGLE, NX_UNITLESS).stream().anyMatch(cat -> cat.isCompatible(unit));
		}
		
	},
	
	NX_UNITLESS(null) {

		@Override
		public boolean isCompatible(Unit<?> unit) {
			return false; // no unit attribute is valid in this case
		}
		
	},
	
	NX_VOLTAGE(SI.VOLT),
	
	NX_VOLUME(SI.CUBIC_METRE),
	
	NX_WAVELENGTH(SI.METRE), // example is angstrom
	
	NX_WAVENUMBER(SI.METRE.inverse()); // example is 1/nm or 1/angstrom
	
	private Unit<?> standardUnit;
	
	private NexusUnitCategory(Unit<?> unit) {
		// make sure we have the standard unit
		if (unit != null) {
			standardUnit = unit.getSystemUnit();
		}
	}
	
	public boolean isCompatible(Unit<?> unit) {
		return unit.getSystemUnit().equals(standardUnit);
	}

}
