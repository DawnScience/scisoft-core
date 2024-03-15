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
 * Computational geometry description of alpha shapes or wrappings to primitives.
 * For details see:
 * * https://dx.doi.org/10.1109/TIT.1983.1056714 for 2D,
 * * https://dx.doi.org/10.1145/174462.156635 for 3D,
 * * https://dl.acm.org/doi/10.5555/871114 for weighted, and
 * * https://doc.cgal.org/latest/Alpha_shapes_3 for 3D implementation
 * * https://doc.cgal.org/latest/Manual/packages.html#PkgAlphaWrap3 for 3D wrap
 * in CGAL, the Computational Geometry Algorithms Library.
 * As a starting point, we follow the conventions of the CGAL library.

 */
public class NXcg_alpha_complexImpl extends NXobjectImpl implements NXcg_alpha_complex {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_POINT_SET,
		NexusBaseClass.NX_CG_TRIANGLE_SET,
		NexusBaseClass.NX_CG_TRIANGLE_SET,
		NexusBaseClass.NX_CG_TETRAHEDRON_SET);

	public NXcg_alpha_complexImpl() {
		super();
	}

	public NXcg_alpha_complexImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_alpha_complex.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_ALPHA_COMPLEX;
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
	public IDataset getType() {
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
	public IDataset getMode() {
		return getDataset(NX_MODE);
	}

	@Override
	public String getModeScalar() {
		return getString(NX_MODE);
	}

	@Override
	public DataNode setMode(IDataset modeDataset) {
		return setDataset(NX_MODE, modeDataset);
	}

	@Override
	public DataNode setModeScalar(String modeValue) {
		return setString(NX_MODE, modeValue);
	}

	@Override
	public IDataset getAlpha() {
		return getDataset(NX_ALPHA);
	}

	@Override
	public Number getAlphaScalar() {
		return getNumber(NX_ALPHA);
	}

	@Override
	public DataNode setAlpha(IDataset alphaDataset) {
		return setDataset(NX_ALPHA, alphaDataset);
	}

	@Override
	public DataNode setAlphaScalar(Number alphaValue) {
		return setField(NX_ALPHA, alphaValue);
	}

	@Override
	public IDataset getOffset() {
		return getDataset(NX_OFFSET);
	}

	@Override
	public Number getOffsetScalar() {
		return getNumber(NX_OFFSET);
	}

	@Override
	public DataNode setOffset(IDataset offsetDataset) {
		return setDataset(NX_OFFSET, offsetDataset);
	}

	@Override
	public DataNode setOffsetScalar(Number offsetValue) {
		return setField(NX_OFFSET, offsetValue);
	}

	@Override
	public NXcg_point_set getPoint_set() {
		// dataNodeName = NX_POINT_SET
		return getChild("point_set", NXcg_point_set.class);
	}

	@Override
	public void setPoint_set(NXcg_point_set point_setGroup) {
		putChild("point_set", point_setGroup);
	}

	@Override
	public NXcg_triangle_set getTriangle_set() {
		// dataNodeName = NX_TRIANGLE_SET
		return getChild("triangle_set", NXcg_triangle_set.class);
	}

	@Override
	public void setTriangle_set(NXcg_triangle_set triangle_setGroup) {
		putChild("triangle_set", triangle_setGroup);
	}

	@Override
	public NXcg_triangle_set getTriangulation() {
		// dataNodeName = NX_TRIANGULATION
		return getChild("triangulation", NXcg_triangle_set.class);
	}

	@Override
	public void setTriangulation(NXcg_triangle_set triangulationGroup) {
		putChild("triangulation", triangulationGroup);
	}

	@Override
	public NXcg_tetrahedron_set getInterior_cells() {
		// dataNodeName = NX_INTERIOR_CELLS
		return getChild("interior_cells", NXcg_tetrahedron_set.class);
	}

	@Override
	public void setInterior_cells(NXcg_tetrahedron_set interior_cellsGroup) {
		putChild("interior_cells", interior_cellsGroup);
	}

}
