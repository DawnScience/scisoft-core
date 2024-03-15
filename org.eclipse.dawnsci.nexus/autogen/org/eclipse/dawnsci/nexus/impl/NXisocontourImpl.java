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

import org.eclipse.dawnsci.nexus.*;

/**
 * Computational geometry description of isocontouring/phase-fields in Euclidean space.
 * Iso-contouring algorithms such as MarchingCubes and others are frequently
 * used to segment d-dimensional regions into regions where intensities are
 * lower or higher than a threshold value, the so-called isovalue.
 * Frequently in computational materials science phase-field methods are
 * used which generate data on discretized grids. Isocontour algorithms
 * are often used in such context to pinpoint the locations of microstructural
 * features from this implicit phase-field-variable-based description.
 * One of the key intentions of this base class is to provide a starting point
 * for scientists from the phase-field community (condensed matter physicists,
 * and materials engineers) to incentivize that also phase-field simulation
 * data could be described with NeXus, provided base classes such as the this one
 * get further extend according to the liking of the phase-field community.

 */
public class NXisocontourImpl extends NXobjectImpl implements NXisocontour {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_GRID);

	public NXisocontourImpl() {
		super();
	}

	public NXisocontourImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXisocontour.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_ISOCONTOUR;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public IDataset getDimensionality() {
		return getDataset(NX_DIMENSIONALITY);
	}

	@Override
	public Long getDimensionalityScalar() {
		return getLong(NX_DIMENSIONALITY);
	}

	@Override
	public DataNode setDimensionality(IDataset dimensionalityDataset) {
		return setDataset(NX_DIMENSIONALITY, dimensionalityDataset);
	}

	@Override
	public DataNode setDimensionalityScalar(Long dimensionalityValue) {
		return setField(NX_DIMENSIONALITY, dimensionalityValue);
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
	public IDataset getIsovalue() {
		return getDataset(NX_ISOVALUE);
	}

	@Override
	public Number getIsovalueScalar() {
		return getNumber(NX_ISOVALUE);
	}

	@Override
	public DataNode setIsovalue(IDataset isovalueDataset) {
		return setDataset(NX_ISOVALUE, isovalueDataset);
	}

	@Override
	public DataNode setIsovalueScalar(Number isovalueValue) {
		return setField(NX_ISOVALUE, isovalueValue);
	}

}
