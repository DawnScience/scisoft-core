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
 * Component of an electron analyzer that deflects the paths of electrons. This includes electrostatic and electromagnetic deflectors.

 */
public class NXdeflectorImpl extends NXcomponentImpl implements NXdeflector {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXdeflectorImpl() {
		super();
	}

	public NXdeflectorImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXdeflector.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_DEFLECTOR;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public Dataset getVoltage() {
		return getDataset(NX_VOLTAGE);
	}

	@Override
	public Number getVoltageScalar() {
		return getNumber(NX_VOLTAGE);
	}

	@Override
	public DataNode setVoltage(IDataset voltageDataset) {
		return setDataset(NX_VOLTAGE, voltageDataset);
	}

	@Override
	public DataNode setVoltageScalar(Number voltageValue) {
		return setField(NX_VOLTAGE, voltageValue);
	}

	@Override
	public Dataset getCurrent() {
		return getDataset(NX_CURRENT);
	}

	@Override
	public Number getCurrentScalar() {
		return getNumber(NX_CURRENT);
	}

	@Override
	public DataNode setCurrent(IDataset currentDataset) {
		return setDataset(NX_CURRENT, currentDataset);
	}

	@Override
	public DataNode setCurrentScalar(Number currentValue) {
		return setField(NX_CURRENT, currentValue);
	}

	@Override
	public Dataset getOffset_x() {
		return getDataset(NX_OFFSET_X);
	}

	@Override
	public Number getOffset_xScalar() {
		return getNumber(NX_OFFSET_X);
	}

	@Override
	public DataNode setOffset_x(IDataset offset_xDataset) {
		return setDataset(NX_OFFSET_X, offset_xDataset);
	}

	@Override
	public DataNode setOffset_xScalar(Number offset_xValue) {
		return setField(NX_OFFSET_X, offset_xValue);
	}

	@Override
	public Dataset getOffset_y() {
		return getDataset(NX_OFFSET_Y);
	}

	@Override
	public Number getOffset_yScalar() {
		return getNumber(NX_OFFSET_Y);
	}

	@Override
	public DataNode setOffset_y(IDataset offset_yDataset) {
		return setDataset(NX_OFFSET_Y, offset_yDataset);
	}

	@Override
	public DataNode setOffset_yScalar(Number offset_yValue) {
		return setField(NX_OFFSET_Y, offset_yValue);
	}

}
