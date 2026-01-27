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
 * Computational geometry description of a set of triangles.

 */
public class NXcg_triangleImpl extends NXcg_primitiveImpl implements NXcg_triangle {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE);

	public NXcg_triangleImpl() {
		super();
	}

	public NXcg_triangleImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_triangle.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_TRIANGLE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getNumber_of_unique_vertices() {
		return getDataset(NX_NUMBER_OF_UNIQUE_VERTICES);
	}

	@Override
	public Long getNumber_of_unique_verticesScalar() {
		return getLong(NX_NUMBER_OF_UNIQUE_VERTICES);
	}

	@Override
	public DataNode setNumber_of_unique_vertices(IDataset number_of_unique_verticesDataset) {
		return setDataset(NX_NUMBER_OF_UNIQUE_VERTICES, number_of_unique_verticesDataset);
	}

	@Override
	public DataNode setNumber_of_unique_verticesScalar(Long number_of_unique_verticesValue) {
		return setField(NX_NUMBER_OF_UNIQUE_VERTICES, number_of_unique_verticesValue);
	}

	@Override
	public NXcg_face_list_data_structure getTriangles() {
		// dataNodeName = NX_TRIANGLES
		return getChild("triangles", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setTriangles(NXcg_face_list_data_structure trianglesGroup) {
		putChild("triangles", trianglesGroup);
	}

	@Override
	public NXcg_face_list_data_structure getTriangleid() {
		// dataNodeName = NX_TRIANGLEID
		return getChild("triangleid", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setTriangleid(NXcg_face_list_data_structure triangleidGroup) {
		putChild("triangleid", triangleidGroup);
	}

	@Override
	public Dataset getEdge_length() {
		return getDataset(NX_EDGE_LENGTH);
	}

	@Override
	public Number getEdge_lengthScalar() {
		return getNumber(NX_EDGE_LENGTH);
	}

	@Override
	public DataNode setEdge_length(IDataset edge_lengthDataset) {
		return setDataset(NX_EDGE_LENGTH, edge_lengthDataset);
	}

	@Override
	public DataNode setEdge_lengthScalar(Number edge_lengthValue) {
		return setField(NX_EDGE_LENGTH, edge_lengthValue);
	}

	@Override
	public Dataset getInterior_angle() {
		return getDataset(NX_INTERIOR_ANGLE);
	}

	@Override
	public Number getInterior_angleScalar() {
		return getNumber(NX_INTERIOR_ANGLE);
	}

	@Override
	public DataNode setInterior_angle(IDataset interior_angleDataset) {
		return setDataset(NX_INTERIOR_ANGLE, interior_angleDataset);
	}

	@Override
	public DataNode setInterior_angleScalar(Number interior_angleValue) {
		return setField(NX_INTERIOR_ANGLE, interior_angleValue);
	}

}
