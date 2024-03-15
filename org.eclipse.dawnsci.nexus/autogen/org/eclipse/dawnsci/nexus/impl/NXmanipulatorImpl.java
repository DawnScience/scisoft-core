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
 * Extension of NXpositioner to include fields to describe the use of manipulators
 * in photoemission experiments.

 */
public class NXmanipulatorImpl extends NXobjectImpl implements NXmanipulator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_POSITIONER,
		NexusBaseClass.NX_TRANSFORMATIONS);

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
	public IDataset getName() {
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
	public IDataset getDescription() {
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
	public IDataset getType() {
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
	public IDataset getCryocoolant() {
		return getDataset(NX_CRYOCOOLANT);
	}

	@Override
	public Boolean getCryocoolantScalar() {
		return getBoolean(NX_CRYOCOOLANT);
	}

	@Override
	public DataNode setCryocoolant(IDataset cryocoolantDataset) {
		return setDataset(NX_CRYOCOOLANT, cryocoolantDataset);
	}

	@Override
	public DataNode setCryocoolantScalar(Boolean cryocoolantValue) {
		return setField(NX_CRYOCOOLANT, cryocoolantValue);
	}

	@Override
	public IDataset getCryostat_temperature() {
		return getDataset(NX_CRYOSTAT_TEMPERATURE);
	}

	@Override
	public Double getCryostat_temperatureScalar() {
		return getDouble(NX_CRYOSTAT_TEMPERATURE);
	}

	@Override
	public DataNode setCryostat_temperature(IDataset cryostat_temperatureDataset) {
		return setDataset(NX_CRYOSTAT_TEMPERATURE, cryostat_temperatureDataset);
	}

	@Override
	public DataNode setCryostat_temperatureScalar(Double cryostat_temperatureValue) {
		return setField(NX_CRYOSTAT_TEMPERATURE, cryostat_temperatureValue);
	}

	@Override
	public IDataset getHeater_power() {
		return getDataset(NX_HEATER_POWER);
	}

	@Override
	public Double getHeater_powerScalar() {
		return getDouble(NX_HEATER_POWER);
	}

	@Override
	public DataNode setHeater_power(IDataset heater_powerDataset) {
		return setDataset(NX_HEATER_POWER, heater_powerDataset);
	}

	@Override
	public DataNode setHeater_powerScalar(Double heater_powerValue) {
		return setField(NX_HEATER_POWER, heater_powerValue);
	}

	@Override
	public IDataset getSample_temperature() {
		return getDataset(NX_SAMPLE_TEMPERATURE);
	}

	@Override
	public Double getSample_temperatureScalar() {
		return getDouble(NX_SAMPLE_TEMPERATURE);
	}

	@Override
	public DataNode setSample_temperature(IDataset sample_temperatureDataset) {
		return setDataset(NX_SAMPLE_TEMPERATURE, sample_temperatureDataset);
	}

	@Override
	public DataNode setSample_temperatureScalar(Double sample_temperatureValue) {
		return setField(NX_SAMPLE_TEMPERATURE, sample_temperatureValue);
	}

	@Override
	public IDataset getDrain_current() {
		return getDataset(NX_DRAIN_CURRENT);
	}

	@Override
	public Double getDrain_currentScalar() {
		return getDouble(NX_DRAIN_CURRENT);
	}

	@Override
	public DataNode setDrain_current(IDataset drain_currentDataset) {
		return setDataset(NX_DRAIN_CURRENT, drain_currentDataset);
	}

	@Override
	public DataNode setDrain_currentScalar(Double drain_currentValue) {
		return setField(NX_DRAIN_CURRENT, drain_currentValue);
	}

	@Override
	public IDataset getSample_bias() {
		return getDataset(NX_SAMPLE_BIAS);
	}

	@Override
	public Double getSample_biasScalar() {
		return getDouble(NX_SAMPLE_BIAS);
	}

	@Override
	public DataNode setSample_bias(IDataset sample_biasDataset) {
		return setDataset(NX_SAMPLE_BIAS, sample_biasDataset);
	}

	@Override
	public DataNode setSample_biasScalar(Double sample_biasValue) {
		return setField(NX_SAMPLE_BIAS, sample_biasValue);
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

	@Override
	public IDataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

}
