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
 * Computer science description of pseudo-random number generator.
 * The purpose of this base class is to identify if exactly the same sequence can be
 * reproduced, like for a PRNG or not, like for a true physically random source.

 */
public class NXcs_prngImpl extends NXobjectImpl implements NXcs_prng {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_PROGRAM);

	public NXcs_prngImpl() {
		super();
	}

	public NXcs_prngImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcs_prng.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CS_PRNG;
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
	public Dataset getSeed() {
		return getDataset(NX_SEED);
	}

	@Override
	public Long getSeedScalar() {
		return getLong(NX_SEED);
	}

	@Override
	public DataNode setSeed(IDataset seedDataset) {
		return setDataset(NX_SEED, seedDataset);
	}

	@Override
	public DataNode setSeedScalar(Long seedValue) {
		return setField(NX_SEED, seedValue);
	}

	@Override
	public Dataset getWarmup() {
		return getDataset(NX_WARMUP);
	}

	@Override
	public Long getWarmupScalar() {
		return getLong(NX_WARMUP);
	}

	@Override
	public DataNode setWarmup(IDataset warmupDataset) {
		return setDataset(NX_WARMUP, warmupDataset);
	}

	@Override
	public DataNode setWarmupScalar(Long warmupValue) {
		return setField(NX_WARMUP, warmupValue);
	}

}
