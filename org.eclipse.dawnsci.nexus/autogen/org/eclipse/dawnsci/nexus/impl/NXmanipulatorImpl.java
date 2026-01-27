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
 * Base class to describe the use of manipulators and sample stages.

 */
public class NXmanipulatorImpl extends NXcomponentImpl implements NXmanipulator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_ACTUATOR,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_ACTUATOR,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_ACTUATOR,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_ACTUATOR,
		NexusBaseClass.NX_SENSOR,
		NexusBaseClass.NX_POSITIONER);

	public NXmanipulatorImpl() {
		super();
	}

	public NXmanipulatorImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXmanipulator.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_MANIPULATOR;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getName() {
		return getDataset(NX_NAME);
	}

	@Override
	public String getNameScalar() {
		return getString(NX_NAME);
	}

	@Override
	public DataNode setName(IDataset nameDataset) {
		return setDataset(NX_NAME, nameDataset);
	}

	@Override
	public DataNode setNameScalar(String nameValue) {
		return setString(NX_NAME, nameValue);
	}

	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
	}

	@Override
	public Dataset getType() {
		return getDataset(NX_TYPE);
	}

	@Override
	public String getTypeScalar() {
		return getString(NX_TYPE);
	}

	@Override
	public DataNode setType(IDataset typeDataset) {
		return setDataset(NX_TYPE, typeDataset);
	}

	@Override
	public DataNode setTypeScalar(String typeValue) {
		return setString(NX_TYPE, typeValue);
	}

	@Override
	public NXactuator getCryostat() {
		// dataNodeName = NX_CRYOSTAT
		return getChild("cryostat", NXactuator.class);
	}

	@Override
	public void setCryostat(NXactuator cryostatGroup) {
		putChild("cryostat", cryostatGroup);
	}
	// Unprocessed group:

	@Override
	public NXsensor getTemperature_sensor() {
		// dataNodeName = NX_TEMPERATURE_SENSOR
		return getChild("temperature_sensor", NXsensor.class);
	}

	@Override
	public void setTemperature_sensor(NXsensor temperature_sensorGroup) {
		putChild("temperature_sensor", temperature_sensorGroup);
	}
	// Unprocessed group: value_log

	@Override
	public NXactuator getSample_heater() {
		// dataNodeName = NX_SAMPLE_HEATER
		return getChild("sample_heater", NXactuator.class);
	}

	@Override
	public void setSample_heater(NXactuator sample_heaterGroup) {
		putChild("sample_heater", sample_heaterGroup);
	}
	// Unprocessed group: output_heater_power_log
	// Unprocessed group:

	@Override
	public NXsensor getDrain_current_ammeter() {
		// dataNodeName = NX_DRAIN_CURRENT_AMMETER
		return getChild("drain_current_ammeter", NXsensor.class);
	}

	@Override
	public void setDrain_current_ammeter(NXsensor drain_current_ammeterGroup) {
		putChild("drain_current_ammeter", drain_current_ammeterGroup);
	}
	// Unprocessed group: value_log

	@Override
	public NXactuator getSample_bias_potentiostat() {
		// dataNodeName = NX_SAMPLE_BIAS_POTENTIOSTAT
		return getChild("sample_bias_potentiostat", NXactuator.class);
	}

	@Override
	public void setSample_bias_potentiostat(NXactuator sample_bias_potentiostatGroup) {
		putChild("sample_bias_potentiostat", sample_bias_potentiostatGroup);
	}
	// Unprocessed group:

	@Override
	public NXsensor getSample_bias_voltmeter() {
		// dataNodeName = NX_SAMPLE_BIAS_VOLTMETER
		return getChild("sample_bias_voltmeter", NXsensor.class);
	}

	@Override
	public void setSample_bias_voltmeter(NXsensor sample_bias_voltmeterGroup) {
		putChild("sample_bias_voltmeter", sample_bias_voltmeterGroup);
	}
	// Unprocessed group: value_log

	@Override
	public NXactuator getActuator() {
		// dataNodeName = NX_ACTUATOR
		return getChild("actuator", NXactuator.class);
	}

	@Override
	public void setActuator(NXactuator actuatorGroup) {
		putChild("actuator", actuatorGroup);
	}

	@Override
	public NXactuator getActuator(String name) {
		return getChild(name, NXactuator.class);
	}

	@Override
	public void setActuator(String name, NXactuator actuator) {
		putChild(name, actuator);
	}

	@Override
	public Map<String, NXactuator> getAllActuator() {
		return getChildren(NXactuator.class);
	}

	@Override
	public void setAllActuator(Map<String, NXactuator> actuator) {
		setChildren(actuator);
	}

	@Override
	public NXsensor getSensor() {
		// dataNodeName = NX_SENSOR
		return getChild("sensor", NXsensor.class);
	}

	@Override
	public void setSensor(NXsensor sensorGroup) {
		putChild("sensor", sensorGroup);
	}

	@Override
	public NXsensor getSensor(String name) {
		return getChild(name, NXsensor.class);
	}

	@Override
	public void setSensor(String name, NXsensor sensor) {
		putChild(name, sensor);
	}

	@Override
	public Map<String, NXsensor> getAllSensor() {
		return getChildren(NXsensor.class);
	}

	@Override
	public void setAllSensor(Map<String, NXsensor> sensor) {
		setChildren(sensor);
	}

	@Override
	public NXpositioner getPositioner() {
		// dataNodeName = NX_POSITIONER
		return getChild("positioner", NXpositioner.class);
	}

	@Override
	public void setPositioner(NXpositioner positionerGroup) {
		putChild("positioner", positionerGroup);
	}

	@Override
	public NXpositioner getPositioner(String name) {
		return getChild(name, NXpositioner.class);
	}

	@Override
	public void setPositioner(String name, NXpositioner positioner) {
		putChild(name, positioner);
	}

	@Override
	public Map<String, NXpositioner> getAllPositioner() {
		return getChildren(NXpositioner.class);
	}

	@Override
	public void setAllPositioner(Map<String, NXpositioner> positioner) {
		setChildren(positioner);
	}

}
