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
 * Computer science description of a set of computing nodes.

 */
public class NXcs_computerImpl extends NXobjectImpl implements NXcs_computer {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CS_CPU,
		NexusBaseClass.NX_CS_GPU,
		NexusBaseClass.NX_CS_MM_SYS,
		NexusBaseClass.NX_CS_IO_SYS);

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
	public IDataset getOperating_system() {
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
	public IDataset getUuid() {
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
	public NXcs_cpu getCs_cpu() {
		// dataNodeName = NX_CS_CPU
		return getChild("cs_cpu", NXcs_cpu.class);
	}

	@Override
	public void setCs_cpu(NXcs_cpu cs_cpuGroup) {
		putChild("cs_cpu", cs_cpuGroup);
	}

	@Override
	public NXcs_cpu getCs_cpu(String name) {
		return getChild(name, NXcs_cpu.class);
	}

	@Override
	public void setCs_cpu(String name, NXcs_cpu cs_cpu) {
		putChild(name, cs_cpu);
	}

	@Override
	public Map<String, NXcs_cpu> getAllCs_cpu() {
		return getChildren(NXcs_cpu.class);
	}

	@Override
	public void setAllCs_cpu(Map<String, NXcs_cpu> cs_cpu) {
		setChildren(cs_cpu);
	}

	@Override
	public NXcs_gpu getCs_gpu() {
		// dataNodeName = NX_CS_GPU
		return getChild("cs_gpu", NXcs_gpu.class);
	}

	@Override
	public void setCs_gpu(NXcs_gpu cs_gpuGroup) {
		putChild("cs_gpu", cs_gpuGroup);
	}

	@Override
	public NXcs_gpu getCs_gpu(String name) {
		return getChild(name, NXcs_gpu.class);
	}

	@Override
	public void setCs_gpu(String name, NXcs_gpu cs_gpu) {
		putChild(name, cs_gpu);
	}

	@Override
	public Map<String, NXcs_gpu> getAllCs_gpu() {
		return getChildren(NXcs_gpu.class);
	}

	@Override
	public void setAllCs_gpu(Map<String, NXcs_gpu> cs_gpu) {
		setChildren(cs_gpu);
	}

	@Override
	public NXcs_mm_sys getCs_mm_sys() {
		// dataNodeName = NX_CS_MM_SYS
		return getChild("cs_mm_sys", NXcs_mm_sys.class);
	}

	@Override
	public void setCs_mm_sys(NXcs_mm_sys cs_mm_sysGroup) {
		putChild("cs_mm_sys", cs_mm_sysGroup);
	}

	@Override
	public NXcs_mm_sys getCs_mm_sys(String name) {
		return getChild(name, NXcs_mm_sys.class);
	}

	@Override
	public void setCs_mm_sys(String name, NXcs_mm_sys cs_mm_sys) {
		putChild(name, cs_mm_sys);
	}

	@Override
	public Map<String, NXcs_mm_sys> getAllCs_mm_sys() {
		return getChildren(NXcs_mm_sys.class);
	}

	@Override
	public void setAllCs_mm_sys(Map<String, NXcs_mm_sys> cs_mm_sys) {
		setChildren(cs_mm_sys);
	}

	@Override
	public NXcs_io_sys getCs_io_sys() {
		// dataNodeName = NX_CS_IO_SYS
		return getChild("cs_io_sys", NXcs_io_sys.class);
	}

	@Override
	public void setCs_io_sys(NXcs_io_sys cs_io_sysGroup) {
		putChild("cs_io_sys", cs_io_sysGroup);
	}

	@Override
	public NXcs_io_sys getCs_io_sys(String name) {
		return getChild(name, NXcs_io_sys.class);
	}

	@Override
	public void setCs_io_sys(String name, NXcs_io_sys cs_io_sys) {
		putChild(name, cs_io_sys);
	}

	@Override
	public Map<String, NXcs_io_sys> getAllCs_io_sys() {
		return getChildren(NXcs_io_sys.class);
	}

	@Override
	public void setAllCs_io_sys(Map<String, NXcs_io_sys> cs_io_sys) {
		setChildren(cs_io_sys);
	}

}
