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
 * Store normal vector information as properties of primitives.
 * Use only only as a child of an instance of :ref:`NXcg_primitive`
 * so that this instance acts as the parent to define a context.

 */
public class NXcg_unit_normalImpl extends NXobjectImpl implements NXcg_unit_normal {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_unit_normalImpl() {
		super();
	}

	public NXcg_unit_normalImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_unit_normal.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_UNIT_NORMAL;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getNormals() {
		return getDataset(NX_NORMALS);
	}

	@Override
	public Number getNormalsScalar() {
		return getNumber(NX_NORMALS);
	}

	@Override
	public DataNode setNormals(IDataset normalsDataset) {
		return setDataset(NX_NORMALS, normalsDataset);
	}

	@Override
	public DataNode setNormalsScalar(Number normalsValue) {
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
