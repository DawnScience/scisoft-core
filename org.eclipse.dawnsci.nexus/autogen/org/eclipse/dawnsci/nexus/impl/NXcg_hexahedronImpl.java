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
 * Computational geometry description of a set of hexahedra in Euclidean space.
 * This class can also be used to describe cuboids or cubes, axis-aligned or not.
 * The class represents different access and description levels to offer both
 * applied scientists and computational geometry experts an approach whereby
 * different specific views can be implemented using the same base class:
 * * In the simplest case experimentalists may use this base class to describe
 * the dimensions or size of a specimen. In this case the alignment with axes
 * is not relevant as eventually only the volume of the specimen is of interest.
 * * In many cases, take for example an experiment where a specimen was cut out
 * from a specifically deformed piece of material, the orientation of the
 * specimen's edges with the experiment coordinate system is of high relevance.
 * Examples include knowledge about the specimen edge, whether it is
 * parallel to the rolling, the transverse, or the normal direction.
 * * While the above-mentioned use cases are sufficient to pinpoint the sample
 * within a known laboratory/experiment coordinate system, these descriptions
 * are not detailed enough to specify e.g. a CAD model of the specimen.
 * * Therefore, groups and fields for an additional, computational-geometry-
 * based view of hexahedra is offered to serve additional computational
 * tasks: storage-oriented simple views or detailed topological/graph-based
 * descriptions.
 * Hexahedra are important geometrical primitives, which are among the most
 * frequently used elements in finite element meshing/modeling.
 * As a specialization of the :ref:`NXcg_primitive` base class hexahedra
 * are assumed non-degenerated, closed, and built of polygons that are
 * not self-intersecting.
 * The term hexahedra will be used throughout this base class but includes
 * the special cases cuboid, cube, box, axis-aligned bounding box (AABB),
 * and optimal bounding box (OBB).
 * An axis-aligned bounding box is a common data object in computational science
 * and simulation codes to represent a cuboid whose edges are aligned with the
 * base vectors of a coordinate system. As a part of binary trees, these data
 * objects are important for making time- as well as space-efficient queries
 * of geometric primitives in techniques like kd-trees.
 * An optimal bounding box is a common data object which provides the best
 * tightly fitting box about an arbitrary object. In general, such boxes are
 * rotated. Exact and substantially faster in practice approximate algorithms
 * exist to compute optimal or near optimal bounding boxes for sets of points.

 */
public class NXcg_hexahedronImpl extends NXcg_primitiveImpl implements NXcg_hexahedron {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_UNIT_NORMAL,
		NexusBaseClass.NX_CG_UNIT_NORMAL,
		NexusBaseClass.NX_CG_UNIT_NORMAL,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_FACE_LIST_DATA_STRUCTURE,
		NexusBaseClass.NX_CG_HALF_EDGE_DATA_STRUCTURE);

	public NXcg_hexahedronImpl() {
		super();
	}

	public NXcg_hexahedronImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_hexahedron.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_HEXAHEDRON;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
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
	public Dataset getArea() {
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
	public NXcg_unit_normal getVertex_normal() {
		// dataNodeName = NX_VERTEX_NORMAL
		return getChild("vertex_normal", NXcg_unit_normal.class);
	}

	@Override
	public void setVertex_normal(NXcg_unit_normal vertex_normalGroup) {
		putChild("vertex_normal", vertex_normalGroup);
	}

	@Override
	public NXcg_unit_normal getEdge_normal() {
		// dataNodeName = NX_EDGE_NORMAL
		return getChild("edge_normal", NXcg_unit_normal.class);
	}

	@Override
	public void setEdge_normal(NXcg_unit_normal edge_normalGroup) {
		putChild("edge_normal", edge_normalGroup);
	}

	@Override
	public NXcg_unit_normal getFace_normal() {
		// dataNodeName = NX_FACE_NORMAL
		return getChild("face_normal", NXcg_unit_normal.class);
	}

	@Override
	public void setFace_normal(NXcg_unit_normal face_normalGroup) {
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
	public NXcg_face_list_data_structure getHexahedronid() {
		// dataNodeName = NX_HEXAHEDRONID
		return getChild("hexahedronid", NXcg_face_list_data_structure.class);
	}

	@Override
	public void setHexahedronid(NXcg_face_list_data_structure hexahedronidGroup) {
		putChild("hexahedronid", hexahedronidGroup);
	}

	@Override
	public NXcg_half_edge_data_structure getHexahedron_half_edgeid() {
		// dataNodeName = NX_HEXAHEDRON_HALF_EDGEID
		return getChild("hexahedron_half_edgeid", NXcg_half_edge_data_structure.class);
	}

	@Override
	public void setHexahedron_half_edgeid(NXcg_half_edge_data_structure hexahedron_half_edgeidGroup) {
		putChild("hexahedron_half_edgeid", hexahedron_half_edgeidGroup);
	}

}
