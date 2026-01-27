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
 * A description of a feedback system in terms of the settings of a proportional-integral-derivative (PID) controller.
 * Automated control of a physical quantity is often achieved by connecting the output of a sensor to an actuator
 * (e.g. using a thermocouple to monitor the effect of a heater and influence the power provided to it). The physical
 * quantity being operated on is typically referred to as the "Process Variable", with the desired value being the
 * "Setpoint" (which may vary as a function of time) and the "Error Value" is the time-varying function of the difference
 * between the Setpoint value and the concurrent measurement of the Process Variable (Error Value = Setpoint - Process Variable).
 * A PID controller calculates an output value for use as an input signal to an actuator via the weighted sum of four terms:
 * * Proportional: the current Error Value
 * * Integral: the integral of the Error Value function
 * * Derivative: the first derivative of the Error Value function
 * * Feed Forward: A model of the physical system (optional)
 * The weightings of these terms are given by the corresponding constants:
 * * K_p
 * * K_i
 * * K_d
 * * K_ff
 * A classic PID controller only implements the P, I and D terms and the values of the K_p, K_i and K_d constants are sufficient to fully
 * describe the behavior of the feedback system implemented by such a PID controller. The inclusion of a Feed Forward term in a feedback system
 * is a modern adaptation that aids optimization of the automated control. It is not present in all PID controllers, but it is also not uncommon.
 * Note that the ``NXpid_controller`` is designed to be a child object of the actuator that its output is connected to. The parent object
 * representing the actuator is likely to be represented by an ``NXactuator`` or ``NXpositioner`` base class, but there is a wide variety
 * of possible applications for PID controllers.

 */
public class NXpid_controllerImpl extends NXcomponentImpl implements NXpid_controller {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_SENSOR);

	public NXpid_controllerImpl() {
		super();
	}

	public NXpid_controllerImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpid_controller.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PID_CONTROLLER;
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
	public Dataset getK_p() {
		return getDataset(NX_K_P);
	}

	@Override
	public Number getK_pScalar() {
		return getNumber(NX_K_P);
	}

	@Override
	public DataNode setK_p(IDataset k_pDataset) {
		return setDataset(NX_K_P, k_pDataset);
	}

	@Override
	public DataNode setK_pScalar(Number k_pValue) {
		return setField(NX_K_P, k_pValue);
	}

	@Override
	public Dataset getK_i() {
		return getDataset(NX_K_I);
	}

	@Override
	public Number getK_iScalar() {
		return getNumber(NX_K_I);
	}

	@Override
	public DataNode setK_i(IDataset k_iDataset) {
		return setDataset(NX_K_I, k_iDataset);
	}

	@Override
	public DataNode setK_iScalar(Number k_iValue) {
		return setField(NX_K_I, k_iValue);
	}

	@Override
	public Dataset getK_d() {
		return getDataset(NX_K_D);
	}

	@Override
	public Number getK_dScalar() {
		return getNumber(NX_K_D);
	}

	@Override
	public DataNode setK_d(IDataset k_dDataset) {
		return setDataset(NX_K_D, k_dDataset);
	}

	@Override
	public DataNode setK_dScalar(Number k_dValue) {
		return setField(NX_K_D, k_dValue);
	}

	@Override
	public Dataset getK_ff() {
		return getDataset(NX_K_FF);
	}

	@Override
	public Number getK_ffScalar() {
		return getNumber(NX_K_FF);
	}

	@Override
	public DataNode setK_ff(IDataset k_ffDataset) {
		return setDataset(NX_K_FF, k_ffDataset);
	}

	@Override
	public DataNode setK_ffScalar(Number k_ffValue) {
		return setField(NX_K_FF, k_ffValue);
	}

	@Override
	public Dataset getFeed_forward_model() {
		return getDataset(NX_FEED_FORWARD_MODEL);
	}

	@Override
	public String getFeed_forward_modelScalar() {
		return getString(NX_FEED_FORWARD_MODEL);
	}

	@Override
	public DataNode setFeed_forward_model(IDataset feed_forward_modelDataset) {
		return setDataset(NX_FEED_FORWARD_MODEL, feed_forward_modelDataset);
	}

	@Override
	public DataNode setFeed_forward_modelScalar(String feed_forward_modelValue) {
		return setString(NX_FEED_FORWARD_MODEL, feed_forward_modelValue);
	}

	@Override
	public Dataset getControl_action() {
		return getDataset(NX_CONTROL_ACTION);
	}

	@Override
	public String getControl_actionScalar() {
		return getString(NX_CONTROL_ACTION);
	}

	@Override
	public DataNode setControl_action(IDataset control_actionDataset) {
		return setDataset(NX_CONTROL_ACTION, control_actionDataset);
	}

	@Override
	public DataNode setControl_actionScalar(String control_actionValue) {
		return setString(NX_CONTROL_ACTION, control_actionValue);
	}

}
