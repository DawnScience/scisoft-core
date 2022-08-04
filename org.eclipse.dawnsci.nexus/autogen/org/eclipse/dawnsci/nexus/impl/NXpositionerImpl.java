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
import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * A generic positioner such as a motor or piezo-electric transducer.
 * 
 */
public class NXpositionerImpl extends NXobjectImpl implements NXpositioner {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXpositionerImpl() {
		super();
	}

	public NXpositionerImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpositioner.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_POSITIONER;
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
	public IDataset getValue() {
		return getDataset(NX_VALUE);
	}

	@Override
	public Number getValueScalar() {
		return getNumber(NX_VALUE);
	}

	@Override
	public DataNode setValue(IDataset valueDataset) {
		return setDataset(NX_VALUE, valueDataset);
	}

	@Override
	public DataNode setValueScalar(Number valueValue) {
		return setField(NX_VALUE, valueValue);
	}

	@Override
	public IDataset getRaw_value() {
		return getDataset(NX_RAW_VALUE);
	}

	@Override
	public Number getRaw_valueScalar() {
		return getNumber(NX_RAW_VALUE);
	}

	@Override
	public DataNode setRaw_value(IDataset raw_valueDataset) {
		return setDataset(NX_RAW_VALUE, raw_valueDataset);
	}

	@Override
	public DataNode setRaw_valueScalar(Number raw_valueValue) {
		return setField(NX_RAW_VALUE, raw_valueValue);
	}

	@Override
	public IDataset getTarget_value() {
		return getDataset(NX_TARGET_VALUE);
	}

	@Override
	public Number getTarget_valueScalar() {
		return getNumber(NX_TARGET_VALUE);
	}

	@Override
	public DataNode setTarget_value(IDataset target_valueDataset) {
		return setDataset(NX_TARGET_VALUE, target_valueDataset);
	}

	@Override
	public DataNode setTarget_valueScalar(Number target_valueValue) {
		return setField(NX_TARGET_VALUE, target_valueValue);
	}

	@Override
	public IDataset getTolerance() {
		return getDataset(NX_TOLERANCE);
	}

	@Override
	public Number getToleranceScalar() {
		return getNumber(NX_TOLERANCE);
	}

	@Override
	public DataNode setTolerance(IDataset toleranceDataset) {
		return setDataset(NX_TOLERANCE, toleranceDataset);
	}

	@Override
	public DataNode setToleranceScalar(Number toleranceValue) {
		return setField(NX_TOLERANCE, toleranceValue);
	}

	@Override
	public IDataset getSoft_limit_min() {
		return getDataset(NX_SOFT_LIMIT_MIN);
	}

	@Override
	public Number getSoft_limit_minScalar() {
		return getNumber(NX_SOFT_LIMIT_MIN);
	}

	@Override
	public DataNode setSoft_limit_min(IDataset soft_limit_minDataset) {
		return setDataset(NX_SOFT_LIMIT_MIN, soft_limit_minDataset);
	}

	@Override
	public DataNode setSoft_limit_minScalar(Number soft_limit_minValue) {
		return setField(NX_SOFT_LIMIT_MIN, soft_limit_minValue);
	}

	@Override
	public IDataset getSoft_limit_max() {
		return getDataset(NX_SOFT_LIMIT_MAX);
	}

	@Override
	public Number getSoft_limit_maxScalar() {
		return getNumber(NX_SOFT_LIMIT_MAX);
	}

	@Override
	public DataNode setSoft_limit_max(IDataset soft_limit_maxDataset) {
		return setDataset(NX_SOFT_LIMIT_MAX, soft_limit_maxDataset);
	}

	@Override
	public DataNode setSoft_limit_maxScalar(Number soft_limit_maxValue) {
		return setField(NX_SOFT_LIMIT_MAX, soft_limit_maxValue);
	}

	@Override
	public IDataset getVelocity() {
		return getDataset(NX_VELOCITY);
	}

	@Override
	public Number getVelocityScalar() {
		return getNumber(NX_VELOCITY);
	}

	@Override
	public DataNode setVelocity(IDataset velocityDataset) {
		return setDataset(NX_VELOCITY, velocityDataset);
	}

	@Override
	public DataNode setVelocityScalar(Number velocityValue) {
		return setField(NX_VELOCITY, velocityValue);
	}

	@Override
	public IDataset getAcceleration_time() {
		return getDataset(NX_ACCELERATION_TIME);
	}

	@Override
	public Number getAcceleration_timeScalar() {
		return getNumber(NX_ACCELERATION_TIME);
	}

	@Override
	public DataNode setAcceleration_time(IDataset acceleration_timeDataset) {
		return setDataset(NX_ACCELERATION_TIME, acceleration_timeDataset);
	}

	@Override
	public DataNode setAcceleration_timeScalar(Number acceleration_timeValue) {
		return setField(NX_ACCELERATION_TIME, acceleration_timeValue);
	}

	@Override
	public IDataset getController_record() {
		return getDataset(NX_CONTROLLER_RECORD);
	}

	@Override
	public String getController_recordScalar() {
		return getString(NX_CONTROLLER_RECORD);
	}

	@Override
	public DataNode setController_record(IDataset controller_recordDataset) {
		return setDataset(NX_CONTROLLER_RECORD, controller_recordDataset);
	}

	@Override
	public DataNode setController_recordScalar(String controller_recordValue) {
		return setString(NX_CONTROLLER_RECORD, controller_recordValue);
	}

	@Override
	public String getAttributeDefault() {
		return getAttrString(null, NX_ATTRIBUTE_DEFAULT);
	}

	@Override
	public void setAttributeDefault(String defaultValue) {
		setAttribute(null, NX_ATTRIBUTE_DEFAULT, defaultValue);
	}

}
