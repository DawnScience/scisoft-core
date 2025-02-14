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
 * Computational geometry description of the marching cubes algorithm.
 * Documenting which specific version was used can help to understand how robust
 * the results are with respect to the topology of the triangulation.

 */
public class NXcg_marching_cubesImpl extends NXobjectImpl implements NXcg_marching_cubes {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_GRID);

	public NXcg_marching_cubesImpl() {
		super();
	}

	public NXcg_marching_cubesImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_marching_cubes.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_MARCHING_CUBES;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public NXcg_grid getGrid() {
		// dataNodeName = NX_GRID
		return getChild("grid", NXcg_grid.class);
	}

	@Override
	public void setGrid(NXcg_grid gridGroup) {
		putChild("grid", gridGroup);
	}

	@Override
	public Dataset getImplementation() {
		return getDataset(NX_IMPLEMENTATION);
	}

	@Override
	public String getImplementationScalar() {
		return getString(NX_IMPLEMENTATION);
	}

	@Override
	public DataNode setImplementation(IDataset implementationDataset) {
		return setDataset(NX_IMPLEMENTATION, implementationDataset);
	}

	@Override
	public DataNode setImplementationScalar(String implementationValue) {
		return setString(NX_IMPLEMENTATION, implementationValue);
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

}
