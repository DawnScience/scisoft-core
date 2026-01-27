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
 * Computational geometry description of a set of polyhedra in Euclidean space.
 * Polyhedra or so-called cells (especially in the convex of tessellations) are
 * constructed from polygon meshes. Polyhedra may make contact to allow a usage
 * of this base class for a description of tessellations.
 * For the description of more complicated manifolds and especially for polyhedra
 * with holes, users are advised to check if their particular needs are described
 * by creating customized instances of an :ref:`NXcg_polygon`.

 */
public class NXcg_polyhedronImpl extends NXcg_primitiveImpl implements NXcg_polyhedron {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE);

	public NXcg_polyhedronImpl() {
		super();
	}

	public NXcg_polyhedronImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_polyhedron.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_POLYHEDRON;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getNumber_of_faces() {
		return getDataset(NX_NUMBER_OF_FACES);
	}

	@Override
	public Long getNumber_of_facesScalar() {
		return getLong(NX_NUMBER_OF_FACES);
	}

	@Override
	public DataNode setNumber_of_faces(IDataset number_of_facesDataset) {
		return setDataset(NX_NUMBER_OF_FACES, number_of_facesDataset);
	}

	@Override
	public DataNode setNumber_of_facesScalar(Long number_of_facesValue) {
		return setField(NX_NUMBER_OF_FACES, number_of_facesValue);
	}

	@Override
	public Dataset getFace_area() {
		return getDataset(NX_FACE_AREA);
	}

	@Override
	public Number getFace_areaScalar() {
		return getNumber(NX_FACE_AREA);
	}

	@Override
	public DataNode setFace_area(IDataset face_areaDataset) {
		return setDataset(NX_FACE_AREA, face_areaDataset);
	}

	@Override
	public DataNode setFace_areaScalar(Number face_areaValue) {
		return setField(NX_FACE_AREA, face_areaValue);
	}

	@Override
	public Dataset getNumber_of_edges() {
		return getDataset(NX_NUMBER_OF_EDGES);
	}

	@Override
	public Long getNumber_of_edgesScalar() {
		return getLong(NX_NUMBER_OF_EDGES);
	}

	@Override
	public DataNode setNumber_of_edges(IDataset number_of_edgesDataset) {
		return setDataset(NX_NUMBER_OF_EDGES, number_of_edgesDataset);
	}

	@Override
	public DataNode setNumber_of_edgesScalar(Long number_of_edgesValue) {
		return setField(NX_NUMBER_OF_EDGES, number_of_edgesValue);
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
	public NXcg_face_list_data_structure getPolyhedra() {
		// dataNodeName = NX_POLYHEDRA
		return getChild("polyhedra", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setPolyhedra(NXcg_face_list_data_structure polyhedraGroup) {
		putChild("polyhedra", polyhedraGroup);
	}

	@Override
	public NXcg_face_list_data_structure getPolyhedronid() {
		// dataNodeName = NX_POLYHEDRONID
		return getChild("polyhedronid", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setPolyhedronid(NXcg_face_list_data_structure polyhedronidGroup) {
		putChild("polyhedronid", polyhedronidGroup);
	}

	@Override
	public NXcg_half_edge_data_structure getPolyhedron_half_edgeid() {
		// dataNodeName = NX_POLYHEDRON_HALF_EDGEID
		return getChild("polyhedron_half_edgeid", NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setPolyhedron_half_edgeid(NXcg_half_edge_data_structure polyhedron_half_edgeidGroup) {
		putChild("polyhedron_half_edgeid", polyhedron_half_edgeidGroup);
	}

}
