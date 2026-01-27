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
 * Computational geometry description of a half-edge data structure.
 * Such a data structure can be used to efficiently circulate around faces
 * and iterate over vertices of a planar graph. The data structure is also
 * known as a doubly connected edge list.
 * Indices can be used as identifier and thus names for individual instances.

 */
public class NXcg_half_edge_data_structureImpl extends NXcg_primitiveImpl implements NXcg_half_edge_data_structure {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.noneOf(NexusBaseClass.class);

	public NXcg_half_edge_data_structureImpl() {
		super();
	}

	public NXcg_half_edge_data_structureImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_half_edge_data_structure.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE;
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
	public Dataset getPosition() {
		return getDataset(NX_POSITION);
	}

	@Override
	public Number getPositionScalar() {
		return getNumber(NX_POSITION);
	}

	@Override
	public DataNode setPosition(IDataset positionDataset) {
		return setDataset(NX_POSITION, positionDataset);
	}

	@Override
	public DataNode setPositionScalar(Number positionValue) {
		return setField(NX_POSITION, positionValue);
	}

	@Override
	public Dataset getVertex_incident_half_edge() {
		return getDataset(NX_VERTEX_INCIDENT_HALF_EDGE);
	}

	@Override
	public Long getVertex_incident_half_edgeScalar() {
		return getLong(NX_VERTEX_INCIDENT_HALF_EDGE);
	}

	@Override
	public DataNode setVertex_incident_half_edge(IDataset vertex_incident_half_edgeDataset) {
		return setDataset(NX_VERTEX_INCIDENT_HALF_EDGE, vertex_incident_half_edgeDataset);
	}

	@Override
	public DataNode setVertex_incident_half_edgeScalar(Long vertex_incident_half_edgeValue) {
		return setField(NX_VERTEX_INCIDENT_HALF_EDGE, vertex_incident_half_edgeValue);
	}

	@Override
	public Dataset getFace_half_edge() {
		return getDataset(NX_FACE_HALF_EDGE);
	}

	@Override
	public Long getFace_half_edgeScalar() {
		return getLong(NX_FACE_HALF_EDGE);
	}

	@Override
	public DataNode setFace_half_edge(IDataset face_half_edgeDataset) {
		return setDataset(NX_FACE_HALF_EDGE, face_half_edgeDataset);
	}

	@Override
	public DataNode setFace_half_edgeScalar(Long face_half_edgeValue) {
		return setField(NX_FACE_HALF_EDGE, face_half_edgeValue);
	}

	@Override
	public Dataset getHalf_edge_vertex_origin() {
		return getDataset(NX_HALF_EDGE_VERTEX_ORIGIN);
	}

	@Override
	public Long getHalf_edge_vertex_originScalar() {
		return getLong(NX_HALF_EDGE_VERTEX_ORIGIN);
	}

	@Override
	public DataNode setHalf_edge_vertex_origin(IDataset half_edge_vertex_originDataset) {
		return setDataset(NX_HALF_EDGE_VERTEX_ORIGIN, half_edge_vertex_originDataset);
	}

	@Override
	public DataNode setHalf_edge_vertex_originScalar(Long half_edge_vertex_originValue) {
		return setField(NX_HALF_EDGE_VERTEX_ORIGIN, half_edge_vertex_originValue);
	}

	@Override
	public Dataset getHalf_edge_twin() {
		return getDataset(NX_HALF_EDGE_TWIN);
	}

	@Override
	public Long getHalf_edge_twinScalar() {
		return getLong(NX_HALF_EDGE_TWIN);
	}

	@Override
	public DataNode setHalf_edge_twin(IDataset half_edge_twinDataset) {
		return setDataset(NX_HALF_EDGE_TWIN, half_edge_twinDataset);
	}

	@Override
	public DataNode setHalf_edge_twinScalar(Long half_edge_twinValue) {
		return setField(NX_HALF_EDGE_TWIN, half_edge_twinValue);
	}

	@Override
	public Dataset getHalf_edge_incident_face() {
		return getDataset(NX_HALF_EDGE_INCIDENT_FACE);
	}

	@Override
	public Long getHalf_edge_incident_faceScalar() {
		return getLong(NX_HALF_EDGE_INCIDENT_FACE);
	}

	@Override
	public DataNode setHalf_edge_incident_face(IDataset half_edge_incident_faceDataset) {
		return setDataset(NX_HALF_EDGE_INCIDENT_FACE, half_edge_incident_faceDataset);
	}

	@Override
	public DataNode setHalf_edge_incident_faceScalar(Long half_edge_incident_faceValue) {
		return setField(NX_HALF_EDGE_INCIDENT_FACE, half_edge_incident_faceValue);
	}

	@Override
	public Dataset getHalf_edge_next() {
		return getDataset(NX_HALF_EDGE_NEXT);
	}

	@Override
	public Long getHalf_edge_nextScalar() {
		return getLong(NX_HALF_EDGE_NEXT);
	}

	@Override
	public DataNode setHalf_edge_next(IDataset half_edge_nextDataset) {
		return setDataset(NX_HALF_EDGE_NEXT, half_edge_nextDataset);
	}

	@Override
	public DataNode setHalf_edge_nextScalar(Long half_edge_nextValue) {
		return setField(NX_HALF_EDGE_NEXT, half_edge_nextValue);
	}

	@Override
	public Dataset getHalf_edge_prev() {
		return getDataset(NX_HALF_EDGE_PREV);
	}

	@Override
	public Long getHalf_edge_prevScalar() {
		return getLong(NX_HALF_EDGE_PREV);
	}

	@Override
	public DataNode setHalf_edge_prev(IDataset half_edge_prevDataset) {
		return setDataset(NX_HALF_EDGE_PREV, half_edge_prevDataset);
	}

	@Override
	public DataNode setHalf_edge_prevScalar(Long half_edge_prevValue) {
		return setField(NX_HALF_EDGE_PREV, half_edge_prevValue);
	}

	@Override
	public Dataset getWeinberg_vector() {
		return getDataset(NX_WEINBERG_VECTOR);
	}

	@Override
	public String getWeinberg_vectorScalar() {
		return getString(NX_WEINBERG_VECTOR);
	}

	@Override
	public DataNode setWeinberg_vector(IDataset weinberg_vectorDataset) {
		return setDataset(NX_WEINBERG_VECTOR, weinberg_vectorDataset);
	}

	@Override
	public DataNode setWeinberg_vectorScalar(String weinberg_vectorValue) {
		return setString(NX_WEINBERG_VECTOR, weinberg_vectorValue);
	}

}
