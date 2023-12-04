/*-
 *******************************************************************************
 * Copyright (c) 2020 Diamond Light Source Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * This file was auto-generated from the NXDL XML definition.
 *******************************************************************************/

package org.eclipse.dawnsci.nexus.impl;

import java.util.Set;
import java.util.EnumSet;
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Metadata for laser-, voltage-, or combined pulsing triggering field evaporation.
 * 
 */
public class NXpulser_apmImpl extends NXobjectImpl implements NXpulser_apm {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SOURCE,
		NexusBaseClass.NX_BEAM);

	public NXpulser_apmImpl() {
		super();
	}

	public NXpulser_apmImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpulser_apm.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PULSER_APM;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}
	

	@Override
	public IDataset getPulse_mode() {
		return getDataset(NX_PULSE_MODE);
	}

	@Override
	public String getPulse_modeScalar() {
		return getString(NX_PULSE_MODE);
	}

	@Override
	public DataNode setPulse_mode(IDataset pulse_modeDataset) {
		return setDataset(NX_PULSE_MODE, pulse_modeDataset);
	}

	@Override
	public DataNode setPulse_modeScalar(String pulse_modeValue) {
		return setString(NX_PULSE_MODE, pulse_modeValue);
	}

	@Override
	public IDataset getPulse_frequency() {
		return getDataset(NX_PULSE_FREQUENCY);
	}

	@Override
	public Number getPulse_frequencyScalar() {
		return getNumber(NX_PULSE_FREQUENCY);
	}

	@Override
	public DataNode setPulse_frequency(IDataset pulse_frequencyDataset) {
		return setDataset(NX_PULSE_FREQUENCY, pulse_frequencyDataset);
	}

	@Override
	public DataNode setPulse_frequencyScalar(Number pulse_frequencyValue) {
		return setField(NX_PULSE_FREQUENCY, pulse_frequencyValue);
	}

	@Override
	public IDataset getPulse_fraction() {
		return getDataset(NX_PULSE_FRACTION);
	}

	@Override
	public Number getPulse_fractionScalar() {
		return getNumber(NX_PULSE_FRACTION);
	}

	@Override
	public DataNode setPulse_fraction(IDataset pulse_fractionDataset) {
		return setDataset(NX_PULSE_FRACTION, pulse_fractionDataset);
	}

	@Override
	public DataNode setPulse_fractionScalar(Number pulse_fractionValue) {
		return setField(NX_PULSE_FRACTION, pulse_fractionValue);
	}

	@Override
	public IDataset getPulsed_voltage() {
		return getDataset(NX_PULSED_VOLTAGE);
	}

	@Override
	public Double getPulsed_voltageScalar() {
		return getDouble(NX_PULSED_VOLTAGE);
	}

	@Override
	public DataNode setPulsed_voltage(IDataset pulsed_voltageDataset) {
		return setDataset(NX_PULSED_VOLTAGE, pulsed_voltageDataset);
	}

	@Override
	public DataNode setPulsed_voltageScalar(Double pulsed_voltageValue) {
		return setField(NX_PULSED_VOLTAGE, pulsed_voltageValue);
	}

	@Override
	public IDataset getStanding_voltage() {
		return getDataset(NX_STANDING_VOLTAGE);
	}

	@Override
	public Double getStanding_voltageScalar() {
		return getDouble(NX_STANDING_VOLTAGE);
	}

	@Override
	public DataNode setStanding_voltage(IDataset standing_voltageDataset) {
		return setDataset(NX_STANDING_VOLTAGE, standing_voltageDataset);
	}

	@Override
	public DataNode setStanding_voltageScalar(Double standing_voltageValue) {
		return setField(NX_STANDING_VOLTAGE, standing_voltageValue);
	}

	@Override
	public NXsource getLaser_gun() {
		// dataNodeName = NX_LASER_GUN
		return getChild("laser_gun", NXsource.class);
	}

	@Override
	public void setLaser_gun(NXsource laser_gunGroup) {
		putChild("laser_gun", laser_gunGroup);
	}
	// Unprocessed group: 
	// Unprocessed group: 

	@Override
	public NXbeam getLaser_beam() {
		// dataNodeName = NX_LASER_BEAM
		return getChild("laser_beam", NXbeam.class);
	}

	@Override
	public void setLaser_beam(NXbeam laser_beamGroup) {
		putChild("laser_beam", laser_beamGroup);
	}
	// Unprocessed group:  pinhole_position
	// Unprocessed group:  spot_position

}
