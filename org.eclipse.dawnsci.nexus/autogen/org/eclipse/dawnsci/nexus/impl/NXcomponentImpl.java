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
 * Base class for components of an instrument - real ones or simulated ones.

 */
public class NXcomponentImpl extends NXobjectImpl implements NXcomponent {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_FABRICATION,
		NexusBaseClass.NX_PROGRAM,
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXcomponentImpl() {
		super();
	}

	public NXcomponentImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcomponent.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_COMPONENT;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getApplied() {
		return getDataset(NX_APPLIED);
	}

	@Override
	public Boolean getAppliedScalar() {
		return getBoolean(NX_APPLIED);
	}

	@Override
	public DataNode setApplied(IDataset appliedDataset) {
		return setDataset(NX_APPLIED, appliedDataset);
	}

	@Override
	public DataNode setAppliedScalar(Boolean appliedValue) {
		return setField(NX_APPLIED, appliedValue);
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
	public Dataset getDescription() {
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
	public Dataset getInputs() {
		return getDataset(NX_INPUTS);
	}

	@Override
	public String getInputsScalar() {
		return getString(NX_INPUTS);
	}

	@Override
	public DataNode setInputs(IDataset inputsDataset) {
		return setDataset(NX_INPUTS, inputsDataset);
	}

	@Override
	public DataNode setInputsScalar(String inputsValue) {
		return setString(NX_INPUTS, inputsValue);
	}

	@Override
	public Dataset getOutputs() {
		return getDataset(NX_OUTPUTS);
	}

	@Override
	public String getOutputsScalar() {
		return getString(NX_OUTPUTS);
	}

	@Override
	public DataNode setOutputs(IDataset outputsDataset) {
		return setDataset(NX_OUTPUTS, outputsDataset);
	}

	@Override
	public DataNode setOutputsScalar(String outputsValue) {
		return setString(NX_OUTPUTS, outputsValue);
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
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
	}

	@Override
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

}
