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
 * Computer science description of pseudo-random number generator.
 * The purpose of such metadata is to identify if exactly the same sequence
 * can be reproduced, like for a PRNG or not (for a true physically random source).

 */
public class NXcs_prngImpl extends NXobjectImpl implements NXcs_prng {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

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
	public Dataset getProgram() {
		return getDataset(NX_PROGRAM);
	}

	@Override
	public String getProgramScalar() {
		return getString(NX_PROGRAM);
	}

	@Override
	public DataNode setProgram(IDataset programDataset) {
		return setDataset(NX_PROGRAM, programDataset);
	}

	@Override
	public DataNode setProgramScalar(String programValue) {
		return setString(NX_PROGRAM, programValue);
	}

	@Override
	public String getProgramAttributeVersion() {
		return getAttrString(NX_PROGRAM, NX_PROGRAM_ATTRIBUTE_VERSION);
	}

	@Override
	public void setProgramAttributeVersion(String versionValue) {
		setAttribute(NX_PROGRAM, NX_PROGRAM_ATTRIBUTE_VERSION, versionValue);
	}

	@Override
	public Dataset getSeed() {
		return getDataset(NX_SEED);
	}

	@Override
	public Number getSeedScalar() {
		return getNumber(NX_SEED);
	}

	@Override
	public DataNode setSeed(IDataset seedDataset) {
		return setDataset(NX_SEED, seedDataset);
	}

	@Override
	public DataNode setSeedScalar(Number seedValue) {
		return setField(NX_SEED, seedValue);
	}

	@Override
	public Dataset getWarmup() {
		return getDataset(NX_WARMUP);
	}

	@Override
	public Number getWarmupScalar() {
		return getNumber(NX_WARMUP);
	}

	@Override
	public DataNode setWarmup(IDataset warmupDataset) {
		return setDataset(NX_WARMUP, warmupDataset);
	}

	@Override
	public DataNode setWarmupScalar(Number warmupValue) {
		return setField(NX_WARMUP, warmupValue);
	}

}
