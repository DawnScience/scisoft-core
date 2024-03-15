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
 * Computational geeometry description of a half-edge data structure.
 * Such a data structure can be used to efficiently circulate around faces
 * and iterate over vertices of a planar graph.

 */
public class NXcg_half_edge_data_structureImpl extends NXobjectImpl implements NXcg_half_edge_data_structure {

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
	public IDataset getNumber_of_vertices() {
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
	public IDataset getNumber_of_faces() {
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
	public IDataset getNumber_of_half_edges() {
		return getDataset(NX_NUMBER_OF_HALF_EDGES);
	}

	@Override
	public Long getNumber_of_half_edgesScalar() {
		return getLong(NX_NUMBER_OF_HALF_EDGES);
	}

	@Override
	public DataNode setNumber_of_half_edges(IDataset number_of_half_edgesDataset) {
		return setDataset(NX_NUMBER_OF_HALF_EDGES, number_of_half_edgesDataset);
	}

	@Override
	public DataNode setNumber_of_half_edgesScalar(Long number_of_half_edgesValue) {
		return setField(NX_NUMBER_OF_HALF_EDGES, number_of_half_edgesValue);
	}

	@Override
	public IDataset getVertex_identifier_offset() {
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
	public IDataset getFace_identifier_offset() {
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
	public IDataset getHalf_edge_identifier_offset() {
		return getDataset(NX_HALF_EDGE_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getHalf_edge_identifier_offsetScalar() {
		return getLong(NX_HALF_EDGE_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setHalf_edge_identifier_offset(IDataset half_edge_identifier_offsetDataset) {
		return setDataset(NX_HALF_EDGE_IDENTIFIER_OFFSET, half_edge_identifier_offsetDataset);
	}

	@Override
	public DataNode setHalf_edge_identifier_offsetScalar(Long half_edge_identifier_offsetValue) {
		return setField(NX_HALF_EDGE_IDENTIFIER_OFFSET, half_edge_identifier_offsetValue);
	}

	@Override
	public IDataset getPosition() {
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
	public IDataset getVertex_incident_half_edge() {
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
	public IDataset getFace_half_edge() {
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
	public IDataset getHalf_edge_vertex_origin() {
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
	public IDataset getHalf_edge_twin() {
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
	public IDataset getHalf_edge_incident_face() {
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
	public IDataset getHalf_edge_next() {
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
	public IDataset getHalf_edge_prev() {
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
	public IDataset getWeinberg_vector() {
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
