/*-
 * Copyright 2018 Diamond Light Source Ltd.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.eclipse.dawnsci.analysis.api.unit;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Dimensionless;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;

import si.uom.SI;
import si.uom.quantity.Action;
import tec.units.indriya.AbstractQuantity;
import tec.units.indriya.quantity.NumberQuantity;
import tec.units.indriya.quantity.Quantities;
import tec.units.indriya.unit.BaseUnit;
import tec.units.indriya.unit.MetricPrefix;
import tec.units.indriya.unit.Units;

/**
 * Handy methods for handling units
 */
public class UnitUtils {

	/**
	 * Planck constant, h, in J s (81 in last digits or 1.2e-8 relative)
	 * <p>
	 * See CODATA 2014, https://physics.nist.gov/cuu/Constants/index.html
	 */
	public static final double PLANCK_CONSTANT = 6.626070040e-34;

	/**
	 * Speed of light in vacuum, c, m s^-1 (exact)
	 * <p>
	 * See CODATA 2014, https://physics.nist.gov/cuu/Constants/index.html
	 */
	public static final double SPEED_OF_LIGHT = 299792458.;

	/**
	 * Planck constant, see {{@link #PLANCK_CONSTANT}
	 */
	public static final Quantity<Action> h = UnitUtils.getQuantity(PLANCK_CONSTANT, SI.JOULE_SECOND);

	/**
	 * Speed of light, see {{@link #SPEED_OF_LIGHT}
	 */
	public static final Quantity<Speed> c = UnitUtils.getQuantity(SPEED_OF_LIGHT, Units.METRE_PER_SECOND);

	public static final Unit<Length> MILLIMETRE = MetricPrefix.MILLI(Units.METRE);

	public static final Unit<Length> CENTIMETRE = MetricPrefix.CENTI(Units.METRE);

	public static final Unit<Length> MICROMETRE = MetricPrefix.MICRO(Units.METRE);

	public static final Unit<Length> NANOMETRE = MetricPrefix.NANO(Units.METRE);

	public static final Unit<Dimensionless> PIXEL = new BaseUnit<>("px", "pixel");

	/**
	 * Distance between centre of one pixel and centre of the next pixel
	 */
	public static final Unit<Length> PIXEL_PITCH = new BaseUnit<>("px", "pixel");

	public static final <Q extends Quantity<Q>> double convert(Quantity<Q> q, Unit<Q> u) {
		return q.to(u).getValue().doubleValue();
	}

	public static final double convertToMM(Quantity<Length> l) {
		return convert(l, MILLIMETRE);
	}

	/**
	 * Typed version of {@link Quantities#getQuantity(Number, Unit)}
	 * @param value
	 * @param unit
	 * @return quantity
	 */
	@SuppressWarnings("unchecked")
	public static final <Q extends Quantity<Q>> Q getQuantity(Number value, Unit<Q> unit) {
		return (Q) Quantities.getQuantity(value, unit);
	}

	/**
	 * Copy a quantity
	 * @param q
	 * @return copy
	 */
	@SuppressWarnings("unchecked")
	public static final <Q extends Quantity<Q>> Q copy(Quantity<Q> q) {
		return (Q) Quantities.getQuantity(q.getValue(), q.getUnit());
	}

	/**
	 * Set a persistence delegate to given encoder to aid serialization of quantities
	 * @param enc encoder
	 */
	public static final void addUnitPersistenceDelegate(XMLEncoder enc) {
		DefaultPersistenceDelegate delegate = new DefaultPersistenceDelegate() {
			@Override
			protected Expression instantiate(Object oldInstance, Encoder out) {
				return new Expression(oldInstance, Quantities.class, "getQuantity", new Object[] { oldInstance.toString() });
			}
			@Override
			protected boolean mutatesTo(Object oldInstance, Object newInstance) {
				return newInstance != null && AbstractQuantity.class.isAssignableFrom(newInstance.getClass());
			}
		};

		enc.setPersistenceDelegate(Quantities.getQuantity(2.54, CENTIMETRE).getClass(), delegate);
		enc.setPersistenceDelegate(NumberQuantity.class, delegate);
	}

	public static void main(String[] argv) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder xmlEncoder = new XMLEncoder(baos);
		addUnitPersistenceDelegate(xmlEncoder);
		xmlEncoder.writeObject(Quantities.getQuantity(25.4, CENTIMETRE));
		xmlEncoder.close();

		System.out.println(baos);
	}
}
