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
 * definition for a quadrupole magnet.
 * 
 */
public class NXquadrupole_magnetImpl extends NXobjectImpl implements NXquadrupole_magnet {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG);

	public NXquadrupole_magnetImpl() {
		super();
	}

	public NXquadrupole_magnetImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXquadrupole_magnet.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_QUADRUPOLE_MAGNET;
	}
	
	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public IDataset getBeamline_distance() {
		return getDataset(NX_BEAMLINE_DISTANCE);
	}

	@Override
	public Double getBeamline_distanceScalar() {
		return getDouble(NX_BEAMLINE_DISTANCE);
	}

	@Override
	public DataNode setBeamline_distance(IDataset beamline_distanceDataset) {
		return setDataset(NX_BEAMLINE_DISTANCE, beamline_distanceDataset);
	}

	@Override
	public DataNode setBeamline_distanceScalar(Double beamline_distanceValue) {
		return setField(NX_BEAMLINE_DISTANCE, beamline_distanceValue);
	}

	@Override
	public IDataset getSet_current() {
		return getDataset(NX_SET_CURRENT);
	}

	@Override
	public Double getSet_currentScalar() {
		return getDouble(NX_SET_CURRENT);
	}

	@Override
	public DataNode setSet_current(IDataset set_currentDataset) {
		return setDataset(NX_SET_CURRENT, set_currentDataset);
	}

	@Override
	public DataNode setSet_currentScalar(Double set_currentValue) {
		return setField(NX_SET_CURRENT, set_currentValue);
	}

	@Override
	public NXlog getRead_current() {
		// dataNodeName = NX_READ_CURRENT
		return getChild("read_current", NXlog.class);
	}

	@Override
	public void setRead_current(NXlog read_currentGroup) {
		putChild("read_current", read_currentGroup);
	}

	@Override
	public NXlog getRead_voltage() {
		// dataNodeName = NX_READ_VOLTAGE
		return getChild("read_voltage", NXlog.class);
	}

	@Override
	public void setRead_voltage(NXlog read_voltageGroup) {
		putChild("read_voltage", read_voltageGroup);
	}

}
