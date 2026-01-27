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
 * Computational geometry description of a set of primitives in Euclidean space.
 * Primitives must neither be degenerated nor self-intersect.
 * Individual primitives can differ in their properties (e.g. size, shape, rotation).

 */
public class NXcg_primitiveImpl extends NXobjectImpl implements NXcg_primitive {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_UNIT_NORMAL,
		NexusBaseClass.NX_CG_UNIT_NORMAL,
		NexusBaseClass.NX_CG_UNIT_NORMAL);

	public NXcg_primitiveImpl() {
		super();
	}

	public NXcg_primitiveImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_primitive.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_PRIMITIVE;
	}

	@Override
	public Set<NexusBaseClass> getPermittedChildGroupClasses() {
		return PERMITTED_CHILD_GROUP_CLASSES;
	}


	@Override
	public Dataset getDepends_on() {
		return getDataset(NX_DEPENDS_ON);
	}

	@Override
	public String getDepends_onScalar() {
		return getString(NX_DEPENDS_ON);
	}

	@Override
	public DataNode setDepends_on(IDataset depends_onDataset) {
		return setDataset(NX_DEPENDS_ON, depends_onDataset);
	}

	@Override
	public DataNode setDepends_onScalar(String depends_onValue) {
		return setString(NX_DEPENDS_ON, depends_onValue);
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
	public Dataset getIndex_offset() {
		return getDataset(NX_INDEX_OFFSET);
	}

	@Override
	public Long getIndex_offsetScalar() {
		return getLong(NX_INDEX_OFFSET);
	}

	@Override
	public DataNode setIndex_offset(IDataset index_offsetDataset) {
		return setDataset(NX_INDEX_OFFSET, index_offsetDataset);
	}

	@Override
	public DataNode setIndex_offsetScalar(Long index_offsetValue) {
		return setField(NX_INDEX_OFFSET, index_offsetValue);
	}

	@Override
	public Dataset getIndices() {
		return getDataset(NX_INDICES);
	}

	@Override
	public Long getIndicesScalar() {
		return getLong(NX_INDICES);
	}

	@Override
	public DataNode setIndices(IDataset indicesDataset) {
		return setDataset(NX_INDICES, indicesDataset);
	}

	@Override
	public DataNode setIndicesScalar(Long indicesValue) {
		return setField(NX_INDICES, indicesValue);
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
	public Dataset getIs_center_of_mass() {
		return getDataset(NX_IS_CENTER_OF_MASS);
	}

	@Override
	public Boolean getIs_center_of_massScalar() {
		return getBoolean(NX_IS_CENTER_OF_MASS);
	}

	@Override
	public DataNode setIs_center_of_mass(IDataset is_center_of_massDataset) {
		return setDataset(NX_IS_CENTER_OF_MASS, is_center_of_massDataset);
	}

	@Override
	public DataNode setIs_center_of_massScalar(Boolean is_center_of_massValue) {
		return setField(NX_IS_CENTER_OF_MASS, is_center_of_massValue);
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
	public Dataset getOrientation() {
		return getDataset(NX_ORIENTATION);
	}

	@Override
	public Number getOrientationScalar() {
		return getNumber(NX_ORIENTATION);
	}

	@Override
	public DataNode setOrientation(IDataset orientationDataset) {
		return setDataset(NX_ORIENTATION, orientationDataset);
	}

	@Override
	public DataNode setOrientationScalar(Number orientationValue) {
		return setField(NX_ORIENTATION, orientationValue);
	}

	@Override
	public Dataset getIs_mesh() {
		return getDataset(NX_IS_MESH);
	}

	@Override
	public Boolean getIs_meshScalar() {
		return getBoolean(NX_IS_MESH);
	}

	@Override
	public DataNode setIs_mesh(IDataset is_meshDataset) {
		return setDataset(NX_IS_MESH, is_meshDataset);
	}

	@Override
	public DataNode setIs_meshScalar(Boolean is_meshValue) {
		return setField(NX_IS_MESH, is_meshValue);
	}

	@Override
	public Dataset getIs_triangle_mesh() {
		return getDataset(NX_IS_TRIANGLE_MESH);
	}

	@Override
	public Boolean getIs_triangle_meshScalar() {
		return getBoolean(NX_IS_TRIANGLE_MESH);
	}

	@Override
	public DataNode setIs_triangle_mesh(IDataset is_triangle_meshDataset) {
		return setDataset(NX_IS_TRIANGLE_MESH, is_triangle_meshDataset);
	}

	@Override
	public DataNode setIs_triangle_meshScalar(Boolean is_triangle_meshValue) {
		return setField(NX_IS_TRIANGLE_MESH, is_triangle_meshValue);
	}

	@Override
	public Dataset getIs_surface_mesh() {
		return getDataset(NX_IS_SURFACE_MESH);
	}

	@Override
	public Boolean getIs_surface_meshScalar() {
		return getBoolean(NX_IS_SURFACE_MESH);
	}

	@Override
	public DataNode setIs_surface_mesh(IDataset is_surface_meshDataset) {
		return setDataset(NX_IS_SURFACE_MESH, is_surface_meshDataset);
	}

	@Override
	public DataNode setIs_surface_meshScalar(Boolean is_surface_meshValue) {
		return setField(NX_IS_SURFACE_MESH, is_surface_meshValue);
	}

	@Override
	public Dataset getIs_geodesic_mesh() {
		return getDataset(NX_IS_GEODESIC_MESH);
	}

	@Override
	public Boolean getIs_geodesic_meshScalar() {
		return getBoolean(NX_IS_GEODESIC_MESH);
	}

	@Override
	public DataNode setIs_geodesic_mesh(IDataset is_geodesic_meshDataset) {
		return setDataset(NX_IS_GEODESIC_MESH, is_geodesic_meshDataset);
	}

	@Override
	public DataNode setIs_geodesic_meshScalar(Boolean is_geodesic_meshValue) {
		return setField(NX_IS_GEODESIC_MESH, is_geodesic_meshValue);
	}

	@Override
	public Dataset getDescription() {
		return getDataset(NX_DESCRIPTION);
	}

	@Override
	public String getDescriptionScalar() {
		return getString(NX_DESCRIPTION);
	}

	@Override
	public DataNode setDescription(IDataset descriptionDataset) {
		return setDataset(NX_DESCRIPTION, descriptionDataset);
	}

	@Override
	public DataNode setDescriptionScalar(String descriptionValue) {
		return setString(NX_DESCRIPTION, descriptionValue);
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

}
