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
 * Computational geometry description of a polyhedra in Euclidean space.
 * Polyhedra, also so-called cells (especially in the convex of tessellations),
 * here described have to be all non-degenerated, closed, built of and thus
 * built out of not-self-intersecting polygon meshes. Polyhedra may make contact,
 * so that this base class can be used for a future description of tessellations.
 * For more complicated manifolds and especially for polyhedra with holes, users
 * are advised to check if their particular needs are described by creating
 * (eventually customized) instances of an NXcg_polygon_set, which can be
 * extended for the description of piecewise-linear complexes.

 */
public class NXcg_polyhedron_setImpl extends NXobjectImpl implements NXcg_polyhedron_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE);

	public NXcg_polyhedron_setImpl() {
		super();
	}

	public NXcg_polyhedron_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_polyhedron_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_POLYHEDRON_SET;
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
	public Dataset getVolume() {
		return getDataset(NX_VOLUME);
	}

	@Override
	public Number getVolumeScalar() {
		return getNumber(NX_VOLUME);
	}

	@Override
	public DataNode setVolume(IDataset volumeDataset) {
		return setDataset(NX_VOLUME, volumeDataset);
	}

	@Override
	public DataNode setVolumeScalar(Number volumeValue) {
		return setField(NX_VOLUME, volumeValue);
	}

	@Override
	public Dataset getCenter() {
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
	public Dataset getSurface_area() {
		return getDataset(NX_SURFACE_AREA);
	}

	@Override
	public Number getSurface_areaScalar() {
		return getNumber(NX_SURFACE_AREA);
	}

	@Override
	public DataNode setSurface_area(IDataset surface_areaDataset) {
		return setDataset(NX_SURFACE_AREA, surface_areaDataset);
	}

	@Override
	public DataNode setSurface_areaScalar(Number surface_areaValue) {
		return setField(NX_SURFACE_AREA, surface_areaValue);
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
	public NXcg_face_list_data_structure getPolyhedra() {
		// dataNodeName = NX_POLYHEDRA
		return getChild("polyhedra", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setPolyhedra(NXcg_face_list_data_structure polyhedraGroup) {
		putChild("polyhedra", polyhedraGroup);
	}

	@Override
	public NXcg_face_list_data_structure getPolyhedron() {
		// dataNodeName = NX_POLYHEDRON
		return getChild("polyhedron", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setPolyhedron(NXcg_face_list_data_structure polyhedronGroup) {
		putChild("polyhedron", polyhedronGroup);
	}

	@Override
	public NXcg_half_edge_data_structure getPolyhedron_half_edge() {
		// dataNodeName = NX_POLYHEDRON_HALF_EDGE
		return getChild("polyhedron_half_edge", NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setPolyhedron_half_edge(NXcg_half_edge_data_structure polyhedron_half_edgeGroup) {
		putChild("polyhedron_half_edge", polyhedron_half_edgeGroup);
	}

}
