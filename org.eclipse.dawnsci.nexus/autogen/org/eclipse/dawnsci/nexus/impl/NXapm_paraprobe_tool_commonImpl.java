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

import java.util.Date;
import java.util.Set;
import java.util.EnumSet;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Base class documenting organizational metadata used by all tools of the
 * paraprobe-toolbox.

 */
public class NXapm_paraprobe_tool_commonImpl extends NXobjectImpl implements NXapm_paraprobe_tool_common {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_PROGRAM,
		NexusBaseClass.NX_CS_PROFILING,
		NexusBaseClass.NX_USER,
		NexusBaseClass.NX_COORDINATE_SYSTEM);

	public NXapm_paraprobe_tool_commonImpl() {
		super();
	}

	public NXapm_paraprobe_tool_commonImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_paraprobe_tool_common.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_PARAPROBE_TOOL_COMMON;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getStatus() {
		return getDataset(NX_STATUS);
	}

	@Override
	public String getStatusScalar() {
		return getString(NX_STATUS);
	}

	@Override
	public DataNode setStatus(IDataset statusDataset) {
		return setDataset(NX_STATUS, statusDataset);
	}

	@Override
	public DataNode setStatusScalar(String statusValue) {
		return setString(NX_STATUS, statusValue);
	}

	@Override
	public Dataset getIdentifier_analysis() {
		return getDataset(NX_IDENTIFIER_ANALYSIS);
	}

	@Override
	public Long getIdentifier_analysisScalar() {
		return getLong(NX_IDENTIFIER_ANALYSIS);
	}

	@Override
	public DataNode setIdentifier_analysis(IDataset identifier_analysisDataset) {
		return setDataset(NX_IDENTIFIER_ANALYSIS, identifier_analysisDataset);
	}

	@Override
	public DataNode setIdentifier_analysisScalar(Long identifier_analysisValue) {
		return setField(NX_IDENTIFIER_ANALYSIS, identifier_analysisValue);
	}

	@Override
	public NXnote getConfig() {
		// dataNodeName = NX_CONFIG
		return getChild("config", NXnote.class);
	}

	@Override
	public void setConfig(NXnote configGroup) {
		putChild("config", configGroup);
	}

	@Override
	public NXprogram getProgramid() {
		// dataNodeName = NX_PROGRAMID
		return getChild("programid", NXprogram.class);
	}

	@Override
	public void setProgramid(NXprogram programidGroup) {
		putChild("programid", programidGroup);
	}

	@Override
	public NXcs_profiling getProfiling() {
		// dataNodeName = NX_PROFILING
		return getChild("profiling", NXcs_profiling.class);
	}

	@Override
	public void setProfiling(NXcs_profiling profilingGroup) {
		putChild("profiling", profilingGroup);
	}

	@Override
	public NXuser getUserid() {
		// dataNodeName = NX_USERID
		return getChild("userid", NXuser.class);
	}

	@Override
	public void setUserid(NXuser useridGroup) {
		putChild("userid", useridGroup);
	}

	@Override
	public NXcoordinate_system getNamed_reference_frameid() {
		// dataNodeName = NX_NAMED_REFERENCE_FRAMEID
		return getChild("named_reference_frameid", NXcoordinate_system.class);
	}

	@Override
	public void setNamed_reference_frameid(NXcoordinate_system named_reference_frameidGroup) {
		putChild("named_reference_frameid", named_reference_frameidGroup);
	}

}
