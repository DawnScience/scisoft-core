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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Metadata for laser- and/or voltage-pulsing in atom probe microscopy.

 */
public class NXpulser_apmImpl extends NXobjectImpl implements NXpulser_apm {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_FABRICATION,
		NexusBaseClass.NX_SOURCE);

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
	public NXfabrication getFabrication() {
		// dataNodeName = NX_FABRICATION
		return getChild("fabrication", NXfabrication.class);
	}

	@Override
	public void setFabrication(NXfabrication fabricationGroup) {
		putChild("fabrication", fabricationGroup);
	}

	@Override
	public NXfabrication getFabrication(String name) {
		return getChild(name, NXfabrication.class);
	}

	@Override
	public void setFabrication(String name, NXfabrication fabrication) {
		putChild(name, fabrication);
	}

	@Override
	public Map<String, NXfabrication> getAllFabrication() {
		return getChildren(NXfabrication.class);
	}

	@Override
	public void setAllFabrication(Map<String, NXfabrication> fabrication) {
		setChildren(fabrication);
	}

	@Override
	public Dataset getPulse_mode() {
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
	public Dataset getPulse_frequency() {
		return getDataset(NX_PULSE_FREQUENCY);
	}

	@Override
	public Double getPulse_frequencyScalar() {
		return getDouble(NX_PULSE_FREQUENCY);
	}

	@Override
	public DataNode setPulse_frequency(IDataset pulse_frequencyDataset) {
		return setDataset(NX_PULSE_FREQUENCY, pulse_frequencyDataset);
	}

	@Override
	public DataNode setPulse_frequencyScalar(Double pulse_frequencyValue) {
		return setField(NX_PULSE_FREQUENCY, pulse_frequencyValue);
	}

	@Override
	public Dataset getPulse_fraction() {
		return getDataset(NX_PULSE_FRACTION);
	}

	@Override
	public Double getPulse_fractionScalar() {
		return getDouble(NX_PULSE_FRACTION);
	}

	@Override
	public DataNode setPulse_fraction(IDataset pulse_fractionDataset) {
		return setDataset(NX_PULSE_FRACTION, pulse_fractionDataset);
	}

	@Override
	public DataNode setPulse_fractionScalar(Double pulse_fractionValue) {
		return setField(NX_PULSE_FRACTION, pulse_fractionValue);
	}

	@Override
	public Dataset getPulsed_voltage() {
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
	public Dataset getPulse_number() {
		return getDataset(NX_PULSE_NUMBER);
	}

	@Override
	public Long getPulse_numberScalar() {
		return getLong(NX_PULSE_NUMBER);
	}

	@Override
	public DataNode setPulse_number(IDataset pulse_numberDataset) {
		return setDataset(NX_PULSE_NUMBER, pulse_numberDataset);
	}

	@Override
	public DataNode setPulse_numberScalar(Long pulse_numberValue) {
		return setField(NX_PULSE_NUMBER, pulse_numberValue);
	}

	@Override
	public Dataset getStanding_voltage() {
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
	public NXsource getSource() {
		// dataNodeName = NX_SOURCE
		return getChild("source", NXsource.class);
	}

	@Override
	public void setSource(NXsource sourceGroup) {
		putChild("source", sourceGroup);
	}

	@Override
	public NXsource getSource(String name) {
		return getChild(name, NXsource.class);
	}

	@Override
	public void setSource(String name, NXsource source) {
		putChild(name, source);
	}

	@Override
	public Map<String, NXsource> getAllSource() {
		return getChildren(NXsource.class);
	}

	@Override
	public void setAllSource(Map<String, NXsource> source) {
		setChildren(source);
	}
	// Unprocessed group:
	// Unprocessed group:
	// Unprocessed group:

}
