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
 * Computational geometry description of a set of hexahedra in Euclidean space.
 * The hexahedra do not have to be connected, can have different size,
 * can intersect, and be rotated.
 * This class can also be used to describe cuboids or cubes, axis-aligned or not.
 * The class represents different access and description levels to offer both
 * applied scientists and computational geometry experts to use the same
 * base class but rather their specific view on the data:
 * * Most simple many experimentalists wish to communicate dimensions/the size
 * of specimens. In this case the alignment with axes is not relevant as
 * eventually the only relevant information to convey is the volume of the
 * specimen.
 * * In many cases, take for instance an experiment where a specimen was taken
 * from a specifically deformed piece of material, e.g. cold-rolled,
 * channel-die deformed, the orientation of the specimen edges with the
 * experiment coordinate system can be of very high relevance.
 * Examples include to know which specimen edge is parallel to the rolling,
 * the transverse, or the normal direction.
 * * Sufficient to pinpoint the sample and laboratory/experiment coordinate
 * system, the above-mentioned descriptions are not detailed enough though
 * to create a CAD model of the specimen.
 * * Therefore, groups and fields for an additional, computational-geometry-
 * based view of the hexahedra is offered which serve different computational
 * tasks: storage-oriented simple views or detailed topological/graph-based
 * descriptions.
 * Hexahedra are important geometrical primitives, which are among the most
 * frequently used elements in finite element meshing/modeling.
 * Hexahedra have to be non-degenerated, closed, and built of polygons
 * which are not self-intersecting.
 * The term hexahedra will be used throughout this base class but includes
 * the especially in engineering and more commonly used special cases,
 * cuboid, cube, box, axis-aligned bounding box (AABB), optimal bounding
 * box (OBB).
 * An axis-aligned bounding box is a common data object in
 * computational science and codes to represent a cuboid whose edges
 * are aligned with a coordinate system. As a part of binary trees these
 * are important data objects for time as well as space efficient queries
 * of geometric primitives in techniques like kd-trees.
 * An optimal bounding box is a common data object which provides the best
 * tight fitting box about an arbitrary object. In general such boxes are
 * rotated. Exact and substantially faster in practice approximate algorithms
 * exist for computing optimal or near optimal bounding boxes for point sets.

 */
public class NXcg_hexahedron_setImpl extends NXobjectImpl implements NXcg_hexahedron_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_ORIENTATION_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_UNIT_NORMAL_SET,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE);

	public NXcg_hexahedron_setImpl() {
		super();
	}

	public NXcg_hexahedron_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_hexahedron_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_HEXAHEDRON_SET;
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
	public Dataset getShape() {
		return getDataset(NX_SHAPE);
	}

	@Override
	public Number getShapeScalar() {
		return getNumber(NX_SHAPE);
	}

	@Override
	public DataNode setShape(IDataset shapeDataset) {
		return setDataset(NX_SHAPE, shapeDataset);
	}

	@Override
	public DataNode setShapeScalar(Number shapeValue) {
		return setField(NX_SHAPE, shapeValue);
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
	public Dataset getWidth() {
		return getDataset(NX_WIDTH);
	}

	@Override
	public Number getWidthScalar() {
		return getNumber(NX_WIDTH);
	}

	@Override
	public DataNode setWidth(IDataset widthDataset) {
		return setDataset(NX_WIDTH, widthDataset);
	}

	@Override
	public DataNode setWidthScalar(Number widthValue) {
		return setField(NX_WIDTH, widthValue);
	}

	@Override
	public Dataset getHeight() {
		return getDataset(NX_HEIGHT);
	}

	@Override
	public Number getHeightScalar() {
		return getNumber(NX_HEIGHT);
	}

	@Override
	public DataNode setHeight(IDataset heightDataset) {
		return setDataset(NX_HEIGHT, heightDataset);
	}

	@Override
	public DataNode setHeightScalar(Number heightValue) {
		return setField(NX_HEIGHT, heightValue);
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
	public Dataset getIs_box() {
		return getDataset(NX_IS_BOX);
	}

	@Override
	public Boolean getIs_boxScalar() {
		return getBoolean(NX_IS_BOX);
	}

	@Override
	public DataNode setIs_box(IDataset is_boxDataset) {
		return setDataset(NX_IS_BOX, is_boxDataset);
	}

	@Override
	public DataNode setIs_boxScalar(Boolean is_boxValue) {
		return setField(NX_IS_BOX, is_boxValue);
	}

	@Override
	public Dataset getIs_axis_aligned() {
		return getDataset(NX_IS_AXIS_ALIGNED);
	}

	@Override
	public Boolean getIs_axis_alignedScalar() {
		return getBoolean(NX_IS_AXIS_ALIGNED);
	}

	@Override
	public DataNode setIs_axis_aligned(IDataset is_axis_alignedDataset) {
		return setDataset(NX_IS_AXIS_ALIGNED, is_axis_alignedDataset);
	}

	@Override
	public DataNode setIs_axis_alignedScalar(Boolean is_axis_alignedValue) {
		return setField(NX_IS_AXIS_ALIGNED, is_axis_alignedValue);
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
	public NXorientation_set getOrientation() {
		// dataNodeName = NX_ORIENTATION
		return getChild("orientation", NXorientation_set.class);
	}

	@Override
	public void setOrientation(NXorientation_set orientationGroup) {
		putChild("orientation", orientationGroup);
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
	public NXcg_face_list_data_structure getHexahedra() {
		// dataNodeName = NX_HEXAHEDRA
		return getChild("hexahedra", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setHexahedra(NXcg_face_list_data_structure hexahedraGroup) {
		putChild("hexahedra", hexahedraGroup);
	}

	@Override
	public NXcg_face_list_data_structure getHexahedron() {
		// dataNodeName = NX_HEXAHEDRON
		return getChild("hexahedron", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setHexahedron(NXcg_face_list_data_structure hexahedronGroup) {
		putChild("hexahedron", hexahedronGroup);
	}

	@Override
	public NXcg_half_edge_data_structure getHexahedron_half_edge() {
		// dataNodeName = NX_HEXAHEDRON_HALF_EDGE
		return getChild("hexahedron_half_edge", NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setHexahedron_half_edge(NXcg_half_edge_data_structure hexahedron_half_edgeGroup) {
		putChild("hexahedron_half_edge", hexahedron_half_edgeGroup);
	}

}
