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
 * Device to reduce an atmosphere to a controlled pressure.

 */
public class NXpumpImpl extends NXcomponentImpl implements NXpump {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXpumpImpl() {
		super();
	}

	public NXpumpImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXpump.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_PUMP;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDesign() {
		return getDataset(NX_DESIGN);
	}

	@Override
	public String getDesignScalar() {
		return getString(NX_DESIGN);
	}

	@Override
	public DataNode setDesign(IDataset designDataset) {
		return setDataset(NX_DESIGN, designDataset);
	}

	@Override
	public DataNode setDesignScalar(String designValue) {
		return setString(NX_DESIGN, designValue);
	}

	@Override
	public Dataset getBase_pressure() {
		return getDataset(NX_BASE_PRESSURE);
	}

	@Override
	public Double getBase_pressureScalar() {
		return getDouble(NX_BASE_PRESSURE);
	}

	@Override
	public DataNode setBase_pressure(IDataset base_pressureDataset) {
		return setDataset(NX_BASE_PRESSURE, base_pressureDataset);
	}

	@Override
	public DataNode setBase_pressureScalar(Double base_pressureValue) {
		return setField(NX_BASE_PRESSURE, base_pressureValue);
	}

	@Override
	public Dataset getMedium() {
		return getDataset(NX_MEDIUM);
	}

	@Override
	public String getMediumScalar() {
		return getString(NX_MEDIUM);
	}

	@Override
	public DataNode setMedium(IDataset mediumDataset) {
		return setDataset(NX_MEDIUM, mediumDataset);
	}

	@Override
	public DataNode setMediumScalar(String mediumValue) {
		return setString(NX_MEDIUM, mediumValue);
	}

}
