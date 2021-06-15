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
import tec.units.indriya.unit.Units;

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
	
	NX_ANGLE(Units.RADIAN),
	
	NX_ANY(null) {

		@Override
		public boolean isCompatible(Unit<?> unit) {
			return true; // all units are accepted
		}
		
		public boolean isRequired() {
			return false;
		}
		
	},
	
	NX_AREA(Units.SQUARE_METRE),
	
	NX_CHARGE(Units.COULOMB),
	
	NX_CROSS_SECTION(Units.SQUARE_METRE), // alias of NX_AREA
	
	NX_CURRENT(Units.AMPERE),
	
	NX_DIMENSIONLESS(AbstractUnit.ONE), // TODO is this right?
	
	/**
	 * Emmittance, a length * angle, e.g. metre-radians
	 */
	NX_EMITTANCE(Units.METRE.multiply(Units.RADIAN)),
	
	NX_ENERGY(Units.JOULE),
	
	/**
	 * some number (e.g. photons) per square meter per second.
	 */
	NX_FLUX(Units.SECOND.inverse().divide(Units.SQUARE_METRE)),
	
	NX_FREQUENCY(Units.HERTZ),
	
	NX_LENGTH(Units.METRE),
	
	NX_MASS(Units.KILOGRAM),
	
	NX_MASS_DENSITY(Units.KILOGRAM.divide(Units.CUBIC_METRE)),
	
	NX_MOLECULAR_WEIGHT(Units.KILOGRAM.divide(Units.MOLE)),
	
	/**
	 * Alias to {@link #NX_TIME}
	 */
	NX_PERIOD(Units.SECOND),
	
	NX_PER_AREA(Units.SQUARE_METRE.inverse()),
	
	NX_PER_LENGTH(Units.METRE.inverse()),
	
	NX_POWER(Units.WATT),
	
	NX_PRESSURE(Units.PASCAL),
	
	NX_PULSES(AbstractUnit.ONE), // TODO is this right?
	
	NX_SCATTERING_LENGTH_DENSITY(Units.METRE.divide(Units.CUBIC_METRE)), // definition gives example m/m^3 
	
	NX_SOLID_ANGLE(Units.STERADIAN),
	
	NX_TEMPERATURE(Units.KELVIN),
	
	NX_TIME(Units.SECOND),
	
	/**
	 * Alias to {@link #NX_TIME}
	 */
	NX_TIME_OF_FLIGHT(Units.SECOND),
	
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
			// Note: we should validate according to the unit category according to the value of the
			// 'transformations_type' attribute of the NXtransformations group. See DAQ-3588
			return Arrays.asList(NX_LENGTH, NX_ANGLE, NX_UNITLESS).stream().anyMatch(cat -> cat.isCompatible(unit));
		}
		
		public boolean isRequired() {
			// units not required for NX_UNITLESS case
			return false;
		}
		
	},
	
	NX_UNITLESS(null) {

		@Override
		public boolean isCompatible(Unit<?> unit) {
			return false; // no unit attribute is valid in this case
		}
		
		public boolean isRequired() {
			return false;
		}
		
	},
	
	NX_VOLTAGE(Units.VOLT),
	
	NX_VOLUME(Units.CUBIC_METRE),
	
	NX_WAVELENGTH(Units.METRE), // example is angstrom
	
	NX_WAVENUMBER(SI.RECIPROCAL_METRE); // example is 1/nm or 1/angstrom
	
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
	
	public boolean isRequired() {
		return true;
	}

}
