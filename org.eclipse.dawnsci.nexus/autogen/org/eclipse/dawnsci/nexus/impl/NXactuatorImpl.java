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
 * An actuator used to control an external condition.
 * The condition itself is described in :ref:`NXenvironment`.

 */
public class NXactuatorImpl extends NXcomponentImpl implements NXactuator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PID_CONTROLLER);

	public NXactuatorImpl() {
		super();
	}

	public NXactuatorImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXactuator.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ACTUATOR;
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
	public Dataset getShort_name() {
		return getDataset(NX_SHORT_NAME);
	}

	@Override
	public String getShort_nameScalar() {
		return getString(NX_SHORT_NAME);
	}

	@Override
	public DataNode setShort_name(IDataset short_nameDataset) {
		return setDataset(NX_SHORT_NAME, short_nameDataset);
	}

	@Override
	public DataNode setShort_nameScalar(String short_nameValue) {
		return setString(NX_SHORT_NAME, short_nameValue);
	}

	@Override
	public Dataset getActuation_target() {
		return getDataset(NX_ACTUATION_TARGET);
	}

	@Override
	public String getActuation_targetScalar() {
		return getString(NX_ACTUATION_TARGET);
	}

	@Override
	public DataNode setActuation_target(IDataset actuation_targetDataset) {
		return setDataset(NX_ACTUATION_TARGET, actuation_targetDataset);
	}

	@Override
	public DataNode setActuation_targetScalar(String actuation_targetValue) {
		return setString(NX_ACTUATION_TARGET, actuation_targetValue);
	}

	@Override
	public Dataset getPhysical_quantity() {
		return getDataset(NX_PHYSICAL_QUANTITY);
	}

	@Override
	public String getPhysical_quantityScalar() {
		return getString(NX_PHYSICAL_QUANTITY);
	}

	@Override
	public DataNode setPhysical_quantity(IDataset physical_quantityDataset) {
		return setDataset(NX_PHYSICAL_QUANTITY, physical_quantityDataset);
	}

	@Override
	public DataNode setPhysical_quantityScalar(String physical_quantityValue) {
		return setString(NX_PHYSICAL_QUANTITY, physical_quantityValue);
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
	public Dataset getOutputvalue() {
		return getDataset(NX_OUTPUTVALUE);
	}

	@Override
	public Number getOutputvalueScalar() {
		return getNumber(NX_OUTPUTVALUE);
	}

	@Override
	public DataNode setOutputvalue(IDataset outputvalueDataset) {
		return setDataset(NX_OUTPUTVALUE, outputvalueDataset);
	}

	@Override
	public DataNode setOutputvalueScalar(Number outputvalueValue) {
		return setField(NX_OUTPUTVALUE, outputvalueValue);
	}

	@Override
	public NXpid_controller getPid_controller() {
		// dataNodeName = NX_PID_CONTROLLER
		return getChild("pid_controller", NXpid_controller.class);
	}

	@Override
	public void setPid_controller(NXpid_controller pid_controllerGroup) {
		putChild("pid_controller", pid_controllerGroup);
	}

	@Override
	public NXpid_controller getPid_controller(String name) {
		return getChild(name, NXpid_controller.class);
	}

	@Override
	public void setPid_controller(String name, NXpid_controller pid_controller) {
		putChild(name, pid_controller);
	}

	@Override
	public Map<String, NXpid_controller> getAllPid_controller() {
		return getChildren(NXpid_controller.class);
	}

	@Override
	public void setAllPid_controller(Map<String, NXpid_controller> pid_controller) {
		setChildren(pid_controller);
	}

}
