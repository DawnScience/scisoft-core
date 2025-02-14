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
import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;
import org.eclipse.january.dataset.Dataset;

import org.eclipse.dawnsci.nexus.*;

/**
 * Computational geometry description of a set of polylines in Euclidean space.
 * Each polyline is built from a sequence of vertices (points with identifiers).
 * Each polyline must have a start and an end point.
 * The sequence describes the positive traversal along the polyline when walking
 * from the start vertex to the end/last vertex.

 */
public class NXcg_polyline_setImpl extends NXobjectImpl implements NXcg_polyline_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET);

	public NXcg_polyline_setImpl() {
		super();
	}

	public NXcg_polyline_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_polyline_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_POLYLINE_SET;
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
	public Dataset getNumber_of_total_vertices() {
		return getDataset(NX_NUMBER_OF_TOTAL_VERTICES);
	}

	@Override
	public Long getNumber_of_total_verticesScalar() {
		return getLong(NX_NUMBER_OF_TOTAL_VERTICES);
	}

	@Override
	public DataNode setNumber_of_total_vertices(IDataset number_of_total_verticesDataset) {
		return setDataset(NX_NUMBER_OF_TOTAL_VERTICES, number_of_total_verticesDataset);
	}

	@Override
	public DataNode setNumber_of_total_verticesScalar(Long number_of_total_verticesValue) {
		return setField(NX_NUMBER_OF_TOTAL_VERTICES, number_of_total_verticesValue);
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
	public NXtransformations getTransformations() {
		// dataNodeName = NX_TRANSFORMATIONS
		return getChild("transformations", NXtransformations.class);
	}

	@Override
	public void setTransformations(NXtransformations transformationsGroup) {
		putChild("transformations", transformationsGroup);
	}

	@Override
	public NXtransformations getTransformations(String name) {
		return getChild(name, NXtransformations.class);
	}

	@Override
	public void setTransformations(String name, NXtransformations transformations) {
		putChild(name, transformations);
	}

	@Override
	public Map<String, NXtransformations> getAllTransformations() {
		return getChildren(NXtransformations.class);
	}

	@Override
	public void setAllTransformations(Map<String, NXtransformations> transformations) {
		setChildren(transformations);
	}

	@Override
	public Dataset getIdentifier_offset() {
		return getDataset(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public Long getIdentifier_offsetScalar() {
		return getLong(NX_IDENTIFIER_OFFSET);
	}

	@Override
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset) {
		return setDataset(NX_IDENTIFIER_OFFSET, identifier_offsetDataset);
	}

	@Override
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue) {
		return setField(NX_IDENTIFIER_OFFSET, identifier_offsetValue);
	}

	@Override
	public Dataset getIdentifier() {
		return getDataset(NX_IDENTIFIER);
	}

	@Override
	public Long getIdentifierScalar() {
		return getLong(NX_IDENTIFIER);
	}

	@Override
	public DataNode setIdentifier(IDataset identifierDataset) {
		return setDataset(NX_IDENTIFIER, identifierDataset);
	}

	@Override
	public DataNode setIdentifierScalar(Long identifierValue) {
		return setField(NX_IDENTIFIER, identifierValue);
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
	public Dataset getPolylines() {
		return getDataset(NX_POLYLINES);
	}

	@Override
	public Long getPolylinesScalar() {
		return getLong(NX_POLYLINES);
	}

	@Override
	public DataNode setPolylines(IDataset polylinesDataset) {
		return setDataset(NX_POLYLINES, polylinesDataset);
	}

	@Override
	public DataNode setPolylinesScalar(Long polylinesValue) {
		return setField(NX_POLYLINES, polylinesValue);
	}

	@Override
	public Dataset getLength() {
		return getDataset(NX_LENGTH);
	}

	@Override
	public Number getLengthScalar() {
		return getNumber(NX_LENGTH);
	}

	@Override
	public DataNode setLength(IDataset lengthDataset) {
		return setDataset(NX_LENGTH, lengthDataset);
	}

	@Override
	public DataNode setLengthScalar(Number lengthValue) {
		return setField(NX_LENGTH, lengthValue);
	}

	@Override
	public Dataset getIs_closed() {
		return getDataset(NX_IS_CLOSED);
	}

	@Override
	public Boolean getIs_closedScalar() {
		return getBoolean(NX_IS_CLOSED);
	}

	@Override
	public DataNode setIs_closed(IDataset is_closedDataset) {
		return setDataset(NX_IS_CLOSED, is_closedDataset);
	}

	@Override
	public DataNode setIs_closedScalar(Boolean is_closedValue) {
		return setField(NX_IS_CLOSED, is_closedValue);
	}

	@Override
	public NXcg_unit_normal_set getVertex_normal() {
		// dataNodeName = NX_VERTEX_NORMAL
		return getChild("vertex_normal", NXcg_unit_normal_set.class);
	}

	@Override
	public void setVertex_normal(NXcg_unit_normal_set vertex_normalGroup) {
		putChild("vertex_normal", vertex_normalGroup);
	}

	@Override
	public NXcg_unit_normal_set getEdge_normal() {
		// dataNodeName = NX_EDGE_NORMAL
		return getChild("edge_normal", NXcg_unit_normal_set.class);
	}

	@Override
	public void setEdge_normal(NXcg_unit_normal_set edge_normalGroup) {
		putChild("edge_normal", edge_normalGroup);
	}

}
