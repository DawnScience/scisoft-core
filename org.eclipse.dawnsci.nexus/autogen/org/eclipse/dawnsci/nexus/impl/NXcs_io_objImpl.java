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
 * Computer science description of a storage object in an input/output system.

 */
public class NXcs_io_objImpl extends NXobjectImpl implements NXcs_io_obj {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_FABRICATION);

	public NXcs_io_objImpl() {
		super();
	}

	public NXcs_io_objImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_io_obj.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_IO_OBJ;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getTechnology() {
		return getDataset(NX_TECHNOLOGY);
	}

	@Override
	public String getTechnologyScalar() {
		return getString(NX_TECHNOLOGY);
	}

	@Override
	public DataNode setTechnology(IDataset technologyDataset) {
		return setDataset(NX_TECHNOLOGY, technologyDataset);
	}

	@Override
	public DataNode setTechnologyScalar(String technologyValue) {
		return setString(NX_TECHNOLOGY, technologyValue);
	}

	@Override
	public IDataset getMax_physical_capacity() {
		return getDataset(NX_MAX_PHYSICAL_CAPACITY);
	}

	@Override
	public Number getMax_physical_capacityScalar() {
		return getNumber(NX_MAX_PHYSICAL_CAPACITY);
	}

	@Override
	public DataNode setMax_physical_capacity(IDataset max_physical_capacityDataset) {
		return setDataset(NX_MAX_PHYSICAL_CAPACITY, max_physical_capacityDataset);
	}

	@Override
	public DataNode setMax_physical_capacityScalar(Number max_physical_capacityValue) {
		return setField(NX_MAX_PHYSICAL_CAPACITY, max_physical_capacityValue);
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
	public NXfabrication getFabrication() {
		// dataNodeName = NX_FABRICATION
		return getChild("fabrication", NXfabrication.class);
	}

	@Override
	public void setFabrication(NXfabrication fabricationGroup) {
		putChild("fabrication", fabricationGroup);
	}

	@Override
	public NXfabrication getFabrication(String name) {
		return getChild(name, NXfabrication.class);
	}

	@Override
	public void setFabrication(String name, NXfabrication fabrication) {
		putChild(name, fabrication);
	}

	@Override
	public Map<String, NXfabrication> getAllFabrication() {
		return getChildren(NXfabrication.class);
	}

	@Override
	public void setAllFabrication(Map<String, NXfabrication> fabrication) {
		setChildren(fabrication);
	}

}
