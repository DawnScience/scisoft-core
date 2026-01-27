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
 * Base class for the configuration and results of a reconstruction algorithm.
 * Generating a tomographic reconstruction of the specimen uses selected and
 * calibrated ion hit positions, the evaporation sequence, and voltage curve data.
 * Very often scientists use own software scripts according to published procedures,
 * so-called reconstruction protocols.

 */
public class NXapm_reconstructionImpl extends NXprocessImpl implements NXapm_reconstruction {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROGRAM,
		NexusBaseClass.NX_NOTE,
		NexusBaseClass.NX_PARAMETERS,
		NexusBaseClass.NX_PROCESS,
		NexusBaseClass.NX_COLLECTION);

	public NXapm_reconstructionImpl() {
		super();
	}

	public NXapm_reconstructionImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXapm_reconstruction.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_APM_RECONSTRUCTION;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXprogram getNXProgram() {
		// dataNodeName = NX_PROGRAM
		return getChild("program", NXprogram.class);
	}

	@Override
	public void setNXProgram(NXprogram programGroup) {
		putChild("program", programGroup);
	}

	@Override
	public NXprogram getNXProgram(String name) {
		return getChild(name, NXprogram.class);
	}

	@Override
	public void setNXProgram(String name, NXprogram program) {
		putChild(name, program);
	}

	@Override
	public Map<String, NXprogram> getAllNXProgram() {
		return getChildren(NXprogram.class);
	}

	@Override
	public void setAllNXProgram(Map<String, NXprogram> program) {
		setChildren(program);
	}

	@Override
	public NXnote getNote() {
		// dataNodeName = NX_NOTE
		return getChild("note", NXnote.class);
	}

	@Override
	public void setNote(NXnote noteGroup) {
		putChild("note", noteGroup);
	}

	@Override
	public NXnote getNote(String name) {
		return getChild(name, NXnote.class);
	}

	@Override
	public void setNote(String name, NXnote note) {
		putChild(name, note);
	}

	@Override
	public Map<String, NXnote> getAllNote() {
		return getChildren(NXnote.class);
	}

	@Override
	public void setAllNote(Map<String, NXnote> note) {
		setChildren(note);
	}

	@Override
	public NXparameters getConfig() {
		// dataNodeName = NX_CONFIG
		return getChild("config", NXparameters.class);
	}

	@Override
	public void setConfig(NXparameters configGroup) {
		putChild("config", configGroup);
	}

	@Override
	public Dataset getReconstructed_positions() {
		return getDataset(NX_RECONSTRUCTED_POSITIONS);
	}

	@Override
	public Double getReconstructed_positionsScalar() {
		return getDouble(NX_RECONSTRUCTED_POSITIONS);
	}

	@Override
	public DataNode setReconstructed_positions(IDataset reconstructed_positionsDataset) {
		return setDataset(NX_RECONSTRUCTED_POSITIONS, reconstructed_positionsDataset);
	}

	@Override
	public DataNode setReconstructed_positionsScalar(Double reconstructed_positionsValue) {
		return setField(NX_RECONSTRUCTED_POSITIONS, reconstructed_positionsValue);
	}

	@Override
	public String getReconstructed_positionsAttributeDepends_on() {
		return getAttrString(NX_RECONSTRUCTED_POSITIONS, NX_RECONSTRUCTED_POSITIONS_ATTRIBUTE_DEPENDS_ON);
	}

	@Override
	public void setReconstructed_positionsAttributeDepends_on(String depends_onValue) {
		setAttribute(NX_RECONSTRUCTED_POSITIONS, NX_RECONSTRUCTED_POSITIONS_ATTRIBUTE_DEPENDS_ON, depends_onValue);
	}

	@Override
	public Dataset getQuality() {
		return getDataset(NX_QUALITY);
	}

	@Override
	public String getQualityScalar() {
		return getString(NX_QUALITY);
	}

	@Override
	public DataNode setQuality(IDataset qualityDataset) {
		return setDataset(NX_QUALITY, qualityDataset);
	}

	@Override
	public DataNode setQualityScalar(String qualityValue) {
		return setString(NX_QUALITY, qualityValue);
	}

	@Override
	public NXprocess getNaive_discretization() {
		// dataNodeName = NX_NAIVE_DISCRETIZATION
		return getChild("naive_discretization", NXprocess.class);
	}

	@Override
	public void setNaive_discretization(NXprocess naive_discretizationGroup) {
		putChild("naive_discretization", naive_discretizationGroup);
	}
	// Unprocessed group:
	// Unprocessed group:

	@Override
	public Dataset getVolume() {
		return getDataset(NX_VOLUME);
	}

	@Override
	public Double getVolumeScalar() {
		return getDouble(NX_VOLUME);
	}

	@Override
	public DataNode setVolume(IDataset volumeDataset) {
		return setDataset(NX_VOLUME, volumeDataset);
	}

	@Override
	public DataNode setVolumeScalar(Double volumeValue) {
		return setField(NX_VOLUME, volumeValue);
	}

	@Override
	public Dataset getField_of_view() {
		return getDataset(NX_FIELD_OF_VIEW);
	}

	@Override
	public Double getField_of_viewScalar() {
		return getDouble(NX_FIELD_OF_VIEW);
	}

	@Override
	public DataNode setField_of_view(IDataset field_of_viewDataset) {
		return setDataset(NX_FIELD_OF_VIEW, field_of_viewDataset);
	}

	@Override
	public DataNode setField_of_viewScalar(Double field_of_viewValue) {
		return setField(NX_FIELD_OF_VIEW, field_of_viewValue);
	}

	@Override
	public NXcollection getObb() {
		// dataNodeName = NX_OBB
		return getChild("obb", NXcollection.class);
	}

	@Override
	public void setObb(NXcollection obbGroup) {
		putChild("obb", obbGroup);
	}

}
