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
 * Base class for reporting the description of a computer

 */
public class NXcs_computerImpl extends NXobjectImpl implements NXcs_computer {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CS_PROCESSOR,
		NexusBaseClass.NX_CS_MEMORY,
		NexusBaseClass.NX_CS_STORAGE);

	public NXcs_computerImpl() {
		super();
	}

	public NXcs_computerImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_computer.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_COMPUTER;
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
	public Dataset getOperating_system() {
		return getDataset(NX_OPERATING_SYSTEM);
	}

	@Override
	public String getOperating_systemScalar() {
		return getString(NX_OPERATING_SYSTEM);
	}

	@Override
	public DataNode setOperating_system(IDataset operating_systemDataset) {
		return setDataset(NX_OPERATING_SYSTEM, operating_systemDataset);
	}

	@Override
	public DataNode setOperating_systemScalar(String operating_systemValue) {
		return setString(NX_OPERATING_SYSTEM, operating_systemValue);
	}

	@Override
	public String getOperating_systemAttributeVersion() {
		return getAttrString(NX_OPERATING_SYSTEM, NX_OPERATING_SYSTEM_ATTRIBUTE_VERSION);
	}

	@Override
	public void setOperating_systemAttributeVersion(String versionValue) {
		setAttribute(NX_OPERATING_SYSTEM, NX_OPERATING_SYSTEM_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public Dataset getUuid() {
		return getDataset(NX_UUID);
	}

	@Override
	public String getUuidScalar() {
		return getString(NX_UUID);
	}

	@Override
	public DataNode setUuid(IDataset uuidDataset) {
		return setDataset(NX_UUID, uuidDataset);
	}

	@Override
	public DataNode setUuidScalar(String uuidValue) {
		return setString(NX_UUID, uuidValue);
	}

	@Override
	public NXcs_processor getProcessorid() {
		// dataNodeName = NX_PROCESSORID
		return getChild("processorid", NXcs_processor.class);
	}

	@Override
	public void setProcessorid(NXcs_processor processoridGroup) {
		putChild("processorid", processoridGroup);
	}

	@Override
	public NXcs_memory getMemoryid() {
		// dataNodeName = NX_MEMORYID
		return getChild("memoryid", NXcs_memory.class);
	}

	@Override
	public void setMemoryid(NXcs_memory memoryidGroup) {
		putChild("memoryid", memoryidGroup);
	}

	@Override
	public NXcs_storage getStorageid() {
		// dataNodeName = NX_STORAGEID
		return getChild("storageid", NXcs_storage.class);
	}

	@Override
	public void setStorageid(NXcs_storage storageidGroup) {
		putChild("storageid", storageidGroup);
	}

}
