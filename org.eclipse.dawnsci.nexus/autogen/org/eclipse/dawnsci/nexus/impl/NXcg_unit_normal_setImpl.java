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
 * Computational geometry description of a set of (oriented) unit normal vectors.

 */
public class NXcg_unit_normal_setImpl extends NXobjectImpl implements NXcg_unit_normal_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_unit_normal_setImpl() {
		super();
	}

	public NXcg_unit_normal_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_unit_normal_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_UNIT_NORMAL_SET;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDimensionality() {
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
	public Dataset getCardinality() {
		return getDataset(NX_CARDINALITY);
	}

	@Override
	public Long getCardinalityScalar() {
		return getLong(NX_CARDINALITY);
	}

	@Override
	public DataNode setCardinality(IDataset cardinalityDataset) {
		return setDataset(NX_CARDINALITY, cardinalityDataset);
	}

	@Override
	public DataNode setCardinalityScalar(Long cardinalityValue) {
		return setField(NX_CARDINALITY, cardinalityValue);
	}

	@Override
	public Dataset getNormals() {
		return getDataset(NX_NORMALS);
	}

	@Override
	public Double getNormalsScalar() {
		return getDouble(NX_NORMALS);
	}

	@Override
	public DataNode setNormals(IDataset normalsDataset) {
		return setDataset(NX_NORMALS, normalsDataset);
	}

	@Override
	public DataNode setNormalsScalar(Double normalsValue) {
		return setField(NX_NORMALS, normalsValue);
	}

	@Override
	public Dataset getOrientation() {
		return getDataset(NX_ORIENTATION);
	}

	@Override
	public Long getOrientationScalar() {
		return getLong(NX_ORIENTATION);
	}

	@Override
	public DataNode setOrientation(IDataset orientationDataset) {
		return setDataset(NX_ORIENTATION, orientationDataset);
	}

	@Override
	public DataNode setOrientationScalar(Long orientationValue) {
		return setField(NX_ORIENTATION, orientationValue);
	}

}
