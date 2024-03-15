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

import org.eclipse.dawnsci.nexus.*;

/**
 * Computational geometry description of a set of triangles in Euclidean space.

 */
public class NXcg_triangle_setImpl extends NXobjectImpl implements NXcg_triangle_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_HEXAHEDRON_SET);

	public NXcg_triangle_setImpl() {
		super();
	}

	public NXcg_triangle_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_triangle_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_TRIANGLE_SET;
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
	public IDataset getCardinality() {
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
	public IDataset getNumber_of_unique_vertices() {
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
	public IDataset getIdentifier_offset() {
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
	public IDataset getIdentifier() {
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
	public NXcg_face_list_data_structure getTriangles() {
		// dataNodeName = NX_TRIANGLES
		return getChild("triangles", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setTriangles(NXcg_face_list_data_structure trianglesGroup) {
		putChild("triangles", trianglesGroup);
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

	@Override
	public NXcg_unit_normal_set getFace_normal() {
		// dataNodeName = NX_FACE_NORMAL
		return getChild("face_normal", NXcg_unit_normal_set.class);
	}

	@Override
	public void setFace_normal(NXcg_unit_normal_set face_normalGroup) {
		putChild("face_normal", face_normalGroup);
	}

	@Override
	public IDataset getArea() {
		return getDataset(NX_AREA);
	}

	@Override
	public Number getAreaScalar() {
		return getNumber(NX_AREA);
	}

	@Override
	public DataNode setArea(IDataset areaDataset) {
		return setDataset(NX_AREA, areaDataset);
	}

	@Override
	public DataNode setAreaScalar(Number areaValue) {
		return setField(NX_AREA, areaValue);
	}

	@Override
	public IDataset getEdge_length() {
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
	public IDataset getInterior_angle() {
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

	@Override
	public IDataset getCenter() {
		return getDataset(NX_CENTER);
	}

	@Override
	public Number getCenterScalar() {
		return getNumber(NX_CENTER);
	}

	@Override
	public DataNode setCenter(IDataset centerDataset) {
		return setDataset(NX_CENTER, centerDataset);
	}

	@Override
	public DataNode setCenterScalar(Number centerValue) {
		return setField(NX_CENTER, centerValue);
	}

	@Override
	public NXcg_hexahedron_set getBounding_box() {
		// dataNodeName = NX_BOUNDING_BOX
		return getChild("bounding_box", NXcg_hexahedron_set.class);
	}

	@Override
	public void setBounding_box(NXcg_hexahedron_set bounding_boxGroup) {
		putChild("bounding_box", bounding_boxGroup);
	}

}
