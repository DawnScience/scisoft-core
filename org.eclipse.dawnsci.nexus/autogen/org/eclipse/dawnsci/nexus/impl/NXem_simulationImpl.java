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


import org.eclipse.dawnsci.nexus.*;

/**
 * Base class for documenting a simulation of electron beam-matter interaction.

 */
public class NXem_simulationImpl extends NXobjectImpl implements NXem_simulation {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROGRAM,
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_DATA);

	public NXem_simulationImpl() {
		super();
	}

	public NXem_simulationImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXem_simulation.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_EM_SIMULATION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXprogram getProgram() {
		// dataNodeName = NX_PROGRAM
		return getChild("program", NXprogram.class);
	}

	@Override
	public void setProgram(NXprogram programGroup) {
		putChild("program", programGroup);
	}

	@Override
	public NXprogram getProgram(String name) {
		return getChild(name, NXprogram.class);
	}

	@Override
	public void setProgram(String name, NXprogram program) {
		putChild(name, program);
	}

	@Override
	public Map<String, NXprogram> getAllProgram() {
		return getChildren(NXprogram.class);
	}

	@Override
	public void setAllProgram(Map<String, NXprogram> program) {
		setChildren(program);
	}

	@Override
	public NXparameters getParameters() {
		// dataNodeName = NX_PARAMETERS
		return getChild("parameters", NXparameters.class);
	}

	@Override
	public void setParameters(NXparameters parametersGroup) {
		putChild("parameters", parametersGroup);
	}

	@Override
	public NXparameters getParameters(String name) {
		return getChild(name, NXparameters.class);
	}

	@Override
	public void setParameters(String name, NXparameters parameters) {
		putChild(name, parameters);
	}

	@Override
	public Map<String, NXparameters> getAllParameters() {
		return getChildren(NXparameters.class);
	}

	@Override
	public void setAllParameters(Map<String, NXparameters> parameters) {
		setChildren(parameters);
	}

	@Override
	public NXprocess getProcess() {
		// dataNodeName = NX_PROCESS
		return getChild("process", NXprocess.class);
	}

	@Override
	public void setProcess(NXprocess processGroup) {
		putChild("process", processGroup);
	}

	@Override
	public NXprocess getProcess(String name) {
		return getChild(name, NXprocess.class);
	}

	@Override
	public void setProcess(String name, NXprocess process) {
		putChild(name, process);
	}

	@Override
	public Map<String, NXprocess> getAllProcess() {
		return getChildren(NXprocess.class);
	}

	@Override
	public void setAllProcess(Map<String, NXprocess> process) {
		setChildren(process);
	}

	@Override
	public NXdata getData() {
		// dataNodeName = NX_DATA
		return getChild("data", NXdata.class);
	}

	@Override
	public void setData(NXdata dataGroup) {
		putChild("data", dataGroup);
	}

	@Override
	public NXdata getData(String name) {
		return getChild(name, NXdata.class);
	}

	@Override
	public void setData(String name, NXdata data) {
		putChild(name, data);
	}

	@Override
	public Map<String, NXdata> getAllData() {
		return getChildren(NXdata.class);
	}

	@Override
	public void setAllData(Map<String, NXdata> data) {
		setChildren(data);
	}

}
