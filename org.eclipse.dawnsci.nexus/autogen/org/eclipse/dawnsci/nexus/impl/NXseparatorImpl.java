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
 * definition for an electrostatic separator.
 * 
 */
public class NXseparatorImpl extends NXobjectImpl implements NXseparator {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG,
		NexusBaseClass.NX_LOG);

	public NXseparatorImpl() {
		super();
	}

	public NXseparatorImpl(final long oid) {
		super(oid);
	}
	
	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXseparator.class;
	}
	
	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_SEPARATOR;
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
	public IDataset getSet_bfield_current() {
		return getDataset(NX_SET_BFIELD_CURRENT);
	}

	@Override
	public Double getSet_bfield_currentScalar() {
		return getDouble(NX_SET_BFIELD_CURRENT);
	}

	@Override
	public DataNode setSet_bfield_current(IDataset set_bfield_currentDataset) {
		return setDataset(NX_SET_BFIELD_CURRENT, set_bfield_currentDataset);
	}

	@Override
	public DataNode setSet_bfield_currentScalar(Double set_bfield_currentValue) {
		return setField(NX_SET_BFIELD_CURRENT, set_bfield_currentValue);
	}

	@Override
	public NXlog getRead_bfield_current() {
		// dataNodeName = NX_READ_BFIELD_CURRENT
		return getChild("read_bfield_current", NXlog.class);
	}

	@Override
	public void setRead_bfield_current(NXlog read_bfield_currentGroup) {
		putChild("read_bfield_current", read_bfield_currentGroup);
	}

	@Override
	public NXlog getRead_bfield_voltage() {
		// dataNodeName = NX_READ_BFIELD_VOLTAGE
		return getChild("read_bfield_voltage", NXlog.class);
	}

	@Override
	public void setRead_bfield_voltage(NXlog read_bfield_voltageGroup) {
		putChild("read_bfield_voltage", read_bfield_voltageGroup);
	}

	@Override
	public IDataset getSet_efield_voltage() {
		return getDataset(NX_SET_EFIELD_VOLTAGE);
	}

	@Override
	public Double getSet_efield_voltageScalar() {
		return getDouble(NX_SET_EFIELD_VOLTAGE);
	}

	@Override
	public DataNode setSet_efield_voltage(IDataset set_efield_voltageDataset) {
		return setDataset(NX_SET_EFIELD_VOLTAGE, set_efield_voltageDataset);
	}

	@Override
	public DataNode setSet_efield_voltageScalar(Double set_efield_voltageValue) {
		return setField(NX_SET_EFIELD_VOLTAGE, set_efield_voltageValue);
	}

	@Override
	public NXlog getRead_efield_current() {
		// dataNodeName = NX_READ_EFIELD_CURRENT
		return getChild("read_efield_current", NXlog.class);
	}

	@Override
	public void setRead_efield_current(NXlog read_efield_currentGroup) {
		putChild("read_efield_current", read_efield_currentGroup);
	}

	@Override
	public NXlog getRead_efield_voltage() {
		// dataNodeName = NX_READ_EFIELD_VOLTAGE
		return getChild("read_efield_voltage", NXlog.class);
	}

	@Override
	public void setRead_efield_voltage(NXlog read_efield_voltageGroup) {
		putChild("read_efield_voltage", read_efield_voltageGroup);
	}

}
