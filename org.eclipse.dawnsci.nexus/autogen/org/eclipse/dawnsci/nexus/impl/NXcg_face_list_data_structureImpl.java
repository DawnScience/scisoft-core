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
 * Computational geometry of primitives via a face-and-edge-list data structure.
 * Primitives must neither be degenerated nor self-intersect but can have different
 * properties. A face-and-edge-list-based description of primitives is
 * frequently used for triangles and polyhedra to store them on disk for
 * visualization purposes (see OFF, PLY, VTK, or STL file formats).
 * Although this description is storage efficient, it is not well-suited for
 * topological analyses. In this case using a half-edge data structure is
 * an alternative.
 * Having an own base class for the data structure how primitives are stored is
 * useful to embrace both users with small or detailed specification demands.
 * Indices can be used as identifier and thus names for individual instances.

 */
public class NXcg_face_list_data_structureImpl extends NXcg_primitiveImpl implements NXcg_face_list_data_structure {

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
	public Dataset getIndex_offset_vertex() {
		return getDataset(NX_INDEX_OFFSET_VERTEX);
	}

	@Override
	public Long getIndex_offset_vertexScalar() {
		return getLong(NX_INDEX_OFFSET_VERTEX);
	}

	@Override
	public DataNode setIndex_offset_vertex(IDataset index_offset_vertexDataset) {
		return setDataset(NX_INDEX_OFFSET_VERTEX, index_offset_vertexDataset);
	}

	@Override
	public DataNode setIndex_offset_vertexScalar(Long index_offset_vertexValue) {
		return setField(NX_INDEX_OFFSET_VERTEX, index_offset_vertexValue);
	}

	@Override
	public Dataset getIndex_offset_edge() {
		return getDataset(NX_INDEX_OFFSET_EDGE);
	}

	@Override
	public Long getIndex_offset_edgeScalar() {
		return getLong(NX_INDEX_OFFSET_EDGE);
	}

	@Override
	public DataNode setIndex_offset_edge(IDataset index_offset_edgeDataset) {
		return setDataset(NX_INDEX_OFFSET_EDGE, index_offset_edgeDataset);
	}

	@Override
	public DataNode setIndex_offset_edgeScalar(Long index_offset_edgeValue) {
		return setField(NX_INDEX_OFFSET_EDGE, index_offset_edgeValue);
	}

	@Override
	public Dataset getIndex_offset_face() {
		return getDataset(NX_INDEX_OFFSET_FACE);
	}

	@Override
	public Long getIndex_offset_faceScalar() {
		return getLong(NX_INDEX_OFFSET_FACE);
	}

	@Override
	public DataNode setIndex_offset_face(IDataset index_offset_faceDataset) {
		return setDataset(NX_INDEX_OFFSET_FACE, index_offset_faceDataset);
	}

	@Override
	public DataNode setIndex_offset_faceScalar(Long index_offset_faceValue) {
		return setField(NX_INDEX_OFFSET_FACE, index_offset_faceValue);
	}

	@Override
	public Dataset getIndices_vertex() {
		return getDataset(NX_INDICES_VERTEX);
	}

	@Override
	public Long getIndices_vertexScalar() {
		return getLong(NX_INDICES_VERTEX);
	}

	@Override
	public DataNode setIndices_vertex(IDataset indices_vertexDataset) {
		return setDataset(NX_INDICES_VERTEX, indices_vertexDataset);
	}

	@Override
	public DataNode setIndices_vertexScalar(Long indices_vertexValue) {
		return setField(NX_INDICES_VERTEX, indices_vertexValue);
	}

	@Override
	public Dataset getIndices_edge() {
		return getDataset(NX_INDICES_EDGE);
	}

	@Override
	public Long getIndices_edgeScalar() {
		return getLong(NX_INDICES_EDGE);
	}

	@Override
	public DataNode setIndices_edge(IDataset indices_edgeDataset) {
		return setDataset(NX_INDICES_EDGE, indices_edgeDataset);
	}

	@Override
	public DataNode setIndices_edgeScalar(Long indices_edgeValue) {
		return setField(NX_INDICES_EDGE, indices_edgeValue);
	}

	@Override
	public Dataset getIndices_face() {
		return getDataset(NX_INDICES_FACE);
	}

	@Override
	public Long getIndices_faceScalar() {
		return getLong(NX_INDICES_FACE);
	}

	@Override
	public DataNode setIndices_face(IDataset indices_faceDataset) {
		return setDataset(NX_INDICES_FACE, indices_faceDataset);
	}

	@Override
	public DataNode setIndices_faceScalar(Long indices_faceValue) {
		return setField(NX_INDICES_FACE, indices_faceValue);
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
