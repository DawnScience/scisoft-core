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
 * Computational geometry description of geometric primitives via a face and edge list.
 * Primitives must not be degenerated or self-intersect.
 * Such descriptions of primitives are frequently used for triangles and polyhedra
 * to store them on disk for visualization purposes. Although storage efficient,
 * such a description is not well suited for topological and neighborhood queries
 * of especially meshes that are built from primitives.
 * In this case, scientists may need a different view on the primitives which
 * is better represented for instance with a half_edge_data_structure instance.
 * The reason to split thus the geometric description of primitives, sets, and
 * specifically meshes of primitives is to keep the structure simple enough for
 * users without these computational geometry demands but also enable those more
 * computational geometry savy users the storing of the additionally relevant
 * data structure.
 * This is beneficial and superior over NXoff_geometry because for instance a
 * half_edge_data_structure instance can be immediately use to reinstantiate
 * the set without having to recompute the half_edge_structure from the vertex
 * and face-list based representation and thus offer a more efficient route
 * to serve applications where topological and graph-based operations are key.

 */
public class NXcg_face_list_data_structureImpl extends NXobjectImpl implements NXcg_face_list_data_structure {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_face_list_data_structureImpl() {
		super();
	}

	public NXcg_face_list_data_structureImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_face_list_data_structure.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE;
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
	public Dataset getNumber_of_vertices() {
		return getDataset(NX_NUMBER_OF_VERTICES);
	}

	@Override
	public Long getNumber_of_verticesScalar() {
		return getLong(NX_NUMBER_OF_VERTICES);
	}

	@Override
	public DataNode setNumber_of_vertices(IDataset number_of_verticesDataset) {
		return setDataset(NX_NUMBER_OF_VERTICES, number_of_verticesDataset);
	}

	@Override
	public DataNode setNumber_of_verticesScalar(Long number_of_verticesValue) {
		return setField(NX_NUMBER_OF_VERTICES, number_of_verticesValue);
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
	public Dataset getVertex_identifier_offset() {
		return getDataset(NX_VERTEX_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getVertex_identifier_offsetScalar() {
		return getLong(NX_VERTEX_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setVertex_identifier_offset(IDataset vertex_identifier_offsetDataset) {
		return setDataset(NX_VERTEX_IDENTIFIER_OFFSET, vertex_identifier_offsetDataset);
	}

	@Override
	public DataNode setVertex_identifier_offsetScalar(Long vertex_identifier_offsetValue) {
		return setField(NX_VERTEX_IDENTIFIER_OFFSET, vertex_identifier_offsetValue);
	}

	@Override
	public Dataset getEdge_identifier_offset() {
		return getDataset(NX_EDGE_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getEdge_identifier_offsetScalar() {
		return getLong(NX_EDGE_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setEdge_identifier_offset(IDataset edge_identifier_offsetDataset) {
		return setDataset(NX_EDGE_IDENTIFIER_OFFSET, edge_identifier_offsetDataset);
	}

	@Override
	public DataNode setEdge_identifier_offsetScalar(Long edge_identifier_offsetValue) {
		return setField(NX_EDGE_IDENTIFIER_OFFSET, edge_identifier_offsetValue);
	}

	@Override
	public Dataset getFace_identifier_offset() {
		return getDataset(NX_FACE_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getFace_identifier_offsetScalar() {
		return getLong(NX_FACE_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setFace_identifier_offset(IDataset face_identifier_offsetDataset) {
		return setDataset(NX_FACE_IDENTIFIER_OFFSET, face_identifier_offsetDataset);
	}

	@Override
	public DataNode setFace_identifier_offsetScalar(Long face_identifier_offsetValue) {
		return setField(NX_FACE_IDENTIFIER_OFFSET, face_identifier_offsetValue);
	}

	@Override
	public Dataset getVertex_identifier() {
		return getDataset(NX_VERTEX_IDENTIFIER);
	}

	@Override
	public Long getVertex_identifierScalar() {
		return getLong(NX_VERTEX_IDENTIFIER);
	}

	@Override
	public DataNode setVertex_identifier(IDataset vertex_identifierDataset) {
		return setDataset(NX_VERTEX_IDENTIFIER, vertex_identifierDataset);
	}

	@Override
	public DataNode setVertex_identifierScalar(Long vertex_identifierValue) {
		return setField(NX_VERTEX_IDENTIFIER, vertex_identifierValue);
	}

	@Override
	public Dataset getEdge_identifier() {
		return getDataset(NX_EDGE_IDENTIFIER);
	}

	@Override
	public Long getEdge_identifierScalar() {
		return getLong(NX_EDGE_IDENTIFIER);
	}

	@Override
	public DataNode setEdge_identifier(IDataset edge_identifierDataset) {
		return setDataset(NX_EDGE_IDENTIFIER, edge_identifierDataset);
	}

	@Override
	public DataNode setEdge_identifierScalar(Long edge_identifierValue) {
		return setField(NX_EDGE_IDENTIFIER, edge_identifierValue);
	}

	@Override
	public Dataset getFace_identifier() {
		return getDataset(NX_FACE_IDENTIFIER);
	}

	@Override
	public Long getFace_identifierScalar() {
		return getLong(NX_FACE_IDENTIFIER);
	}

	@Override
	public DataNode setFace_identifier(IDataset face_identifierDataset) {
		return setDataset(NX_FACE_IDENTIFIER, face_identifierDataset);
	}

	@Override
	public DataNode setFace_identifierScalar(Long face_identifierValue) {
		return setField(NX_FACE_IDENTIFIER, face_identifierValue);
	}

	@Override
	public Dataset getVertices() {
		return getDataset(NX_VERTICES);
	}

	@Override
	public Number getVerticesScalar() {
		return getNumber(NX_VERTICES);
	}

	@Override
	public DataNode setVertices(IDataset verticesDataset) {
		return setDataset(NX_VERTICES, verticesDataset);
	}

	@Override
	public DataNode setVerticesScalar(Number verticesValue) {
		return setField(NX_VERTICES, verticesValue);
	}

	@Override
	public Dataset getEdges() {
		return getDataset(NX_EDGES);
	}

	@Override
	public Long getEdgesScalar() {
		return getLong(NX_EDGES);
	}

	@Override
	public DataNode setEdges(IDataset edgesDataset) {
		return setDataset(NX_EDGES, edgesDataset);
	}

	@Override
	public DataNode setEdgesScalar(Long edgesValue) {
		return setField(NX_EDGES, edgesValue);
	}

	@Override
	public Dataset getFaces() {
		return getDataset(NX_FACES);
	}

	@Override
	public Long getFacesScalar() {
		return getLong(NX_FACES);
	}

	@Override
	public DataNode setFaces(IDataset facesDataset) {
		return setDataset(NX_FACES, facesDataset);
	}

	@Override
	public DataNode setFacesScalar(Long facesValue) {
		return setField(NX_FACES, facesValue);
	}

	@Override
	public Dataset getVertices_are_unique() {
		return getDataset(NX_VERTICES_ARE_UNIQUE);
	}

	@Override
	public Boolean getVertices_are_uniqueScalar() {
		return getBoolean(NX_VERTICES_ARE_UNIQUE);
	}

	@Override
	public DataNode setVertices_are_unique(IDataset vertices_are_uniqueDataset) {
		return setDataset(NX_VERTICES_ARE_UNIQUE, vertices_are_uniqueDataset);
	}

	@Override
	public DataNode setVertices_are_uniqueScalar(Boolean vertices_are_uniqueValue) {
		return setField(NX_VERTICES_ARE_UNIQUE, vertices_are_uniqueValue);
	}

	@Override
	public Dataset getEdges_are_unique() {
		return getDataset(NX_EDGES_ARE_UNIQUE);
	}

	@Override
	public Boolean getEdges_are_uniqueScalar() {
		return getBoolean(NX_EDGES_ARE_UNIQUE);
	}

	@Override
	public DataNode setEdges_are_unique(IDataset edges_are_uniqueDataset) {
		return setDataset(NX_EDGES_ARE_UNIQUE, edges_are_uniqueDataset);
	}

	@Override
	public DataNode setEdges_are_uniqueScalar(Boolean edges_are_uniqueValue) {
		return setField(NX_EDGES_ARE_UNIQUE, edges_are_uniqueValue);
	}

	@Override
	public Dataset getFaces_are_unique() {
		return getDataset(NX_FACES_ARE_UNIQUE);
	}

	@Override
	public Boolean getFaces_are_uniqueScalar() {
		return getBoolean(NX_FACES_ARE_UNIQUE);
	}

	@Override
	public DataNode setFaces_are_unique(IDataset faces_are_uniqueDataset) {
		return setDataset(NX_FACES_ARE_UNIQUE, faces_are_uniqueDataset);
	}

	@Override
	public DataNode setFaces_are_uniqueScalar(Boolean faces_are_uniqueValue) {
		return setField(NX_FACES_ARE_UNIQUE, faces_are_uniqueValue);
	}

	@Override
	public Dataset getWinding_order() {
		return getDataset(NX_WINDING_ORDER);
	}

	@Override
	public Long getWinding_orderScalar() {
		return getLong(NX_WINDING_ORDER);
	}

	@Override
	public DataNode setWinding_order(IDataset winding_orderDataset) {
		return setDataset(NX_WINDING_ORDER, winding_orderDataset);
	}

	@Override
	public DataNode setWinding_orderScalar(Long winding_orderValue) {
		return setField(NX_WINDING_ORDER, winding_orderValue);
	}

}
