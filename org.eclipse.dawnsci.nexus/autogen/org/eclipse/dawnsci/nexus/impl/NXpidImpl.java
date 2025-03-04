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
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Contains the settings of a PID controller.

 */
public class NXpidImpl extends NXobjectImpl implements NXpid {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SENSOR);

	public NXpidImpl() {
		super();
	}

	public NXpidImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpid.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PID;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public NXsensor getPv_sensor() {
		// dataNodeName = NX_PV_SENSOR
		return getChild("pv_sensor", NXsensor.class);
	}

	@Override
	public void setPv_sensor(NXsensor pv_sensorGroup) {
		putChild("pv_sensor", pv_sensorGroup);
	}
	// Unprocessed group: value_log

	@Override
	public Dataset getSetpoint() {
		return getDataset(NX_SETPOINT);
	}

	@Override
	public Double getSetpointScalar() {
		return getDouble(NX_SETPOINT);
	}

	@Override
	public DataNode setSetpoint(IDataset setpointDataset) {
		return setDataset(NX_SETPOINT, setpointDataset);
	}

	@Override
	public DataNode setSetpointScalar(Double setpointValue) {
		return setField(NX_SETPOINT, setpointValue);
	}

	@Override
	public Dataset getK_p_value() {
		return getDataset(NX_K_P_VALUE);
	}

	@Override
	public Number getK_p_valueScalar() {
		return getNumber(NX_K_P_VALUE);
	}

	@Override
	public DataNode setK_p_value(IDataset k_p_valueDataset) {
		return setDataset(NX_K_P_VALUE, k_p_valueDataset);
	}

	@Override
	public DataNode setK_p_valueScalar(Number k_p_valueValue) {
		return setField(NX_K_P_VALUE, k_p_valueValue);
	}

	@Override
	public Dataset getK_i_value() {
		return getDataset(NX_K_I_VALUE);
	}

	@Override
	public Number getK_i_valueScalar() {
		return getNumber(NX_K_I_VALUE);
	}

	@Override
	public DataNode setK_i_value(IDataset k_i_valueDataset) {
		return setDataset(NX_K_I_VALUE, k_i_valueDataset);
	}

	@Override
	public DataNode setK_i_valueScalar(Number k_i_valueValue) {
		return setField(NX_K_I_VALUE, k_i_valueValue);
	}

	@Override
	public Dataset getK_d_value() {
		return getDataset(NX_K_D_VALUE);
	}

	@Override
	public Number getK_d_valueScalar() {
		return getNumber(NX_K_D_VALUE);
	}

	@Override
	public DataNode setK_d_value(IDataset k_d_valueDataset) {
		return setDataset(NX_K_D_VALUE, k_d_valueDataset);
	}

	@Override
	public DataNode setK_d_valueScalar(Number k_d_valueValue) {
		return setField(NX_K_D_VALUE, k_d_valueValue);
	}

}
