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
 * Computational geometry description of a set of cylinders in Euclidean space.
 * The members of the set can have different size. For each member the position
 * of the center and the height is mandatory. The radius can either be defined
 * in the radius field or by filling both the upper and the lower radius field.
 * The latter case can be used to represent truncated cones.

 */
public class NXcg_cylinder_setImpl extends NXobjectImpl implements NXcg_cylinder_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXcg_cylinder_setImpl() {
		super();
	}

	public NXcg_cylinder_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_cylinder_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_CYLINDER_SET;
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
	public IDataset getHeight() {
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
	public IDataset getRadii() {
		return getDataset(NX_RADII);
	}

	@Override
	public Number getRadiiScalar() {
		return getNumber(NX_RADII);
	}

	@Override
	public DataNode setRadii(IDataset radiiDataset) {
		return setDataset(NX_RADII, radiiDataset);
	}

	@Override
	public DataNode setRadiiScalar(Number radiiValue) {
		return setField(NX_RADII, radiiValue);
	}

	@Override
	public IDataset getUpper_cap_radius() {
		return getDataset(NX_UPPER_CAP_RADIUS);
	}

	@Override
	public Number getUpper_cap_radiusScalar() {
		return getNumber(NX_UPPER_CAP_RADIUS);
	}

	@Override
	public DataNode setUpper_cap_radius(IDataset upper_cap_radiusDataset) {
		return setDataset(NX_UPPER_CAP_RADIUS, upper_cap_radiusDataset);
	}

	@Override
	public DataNode setUpper_cap_radiusScalar(Number upper_cap_radiusValue) {
		return setField(NX_UPPER_CAP_RADIUS, upper_cap_radiusValue);
	}

	@Override
	public IDataset getLower_cap_radius() {
		return getDataset(NX_LOWER_CAP_RADIUS);
	}

	@Override
	public Number getLower_cap_radiusScalar() {
		return getNumber(NX_LOWER_CAP_RADIUS);
	}

	@Override
	public DataNode setLower_cap_radius(IDataset lower_cap_radiusDataset) {
		return setDataset(NX_LOWER_CAP_RADIUS, lower_cap_radiusDataset);
	}

	@Override
	public DataNode setLower_cap_radiusScalar(Number lower_cap_radiusValue) {
		return setField(NX_LOWER_CAP_RADIUS, lower_cap_radiusValue);
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
	public IDataset getVolume() {
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
	public IDataset getLateral_surface_area() {
		return getDataset(NX_LATERAL_SURFACE_AREA);
	}

	@Override
	public Number getLateral_surface_areaScalar() {
		return getNumber(NX_LATERAL_SURFACE_AREA);
	}

	@Override
	public DataNode setLateral_surface_area(IDataset lateral_surface_areaDataset) {
		return setDataset(NX_LATERAL_SURFACE_AREA, lateral_surface_areaDataset);
	}

	@Override
	public DataNode setLateral_surface_areaScalar(Number lateral_surface_areaValue) {
		return setField(NX_LATERAL_SURFACE_AREA, lateral_surface_areaValue);
	}

	@Override
	public IDataset getCap_surface_area() {
		return getDataset(NX_CAP_SURFACE_AREA);
	}

	@Override
	public Number getCap_surface_areaScalar() {
		return getNumber(NX_CAP_SURFACE_AREA);
	}

	@Override
	public DataNode setCap_surface_area(IDataset cap_surface_areaDataset) {
		return setDataset(NX_CAP_SURFACE_AREA, cap_surface_areaDataset);
	}

	@Override
	public DataNode setCap_surface_areaScalar(Number cap_surface_areaValue) {
		return setField(NX_CAP_SURFACE_AREA, cap_surface_areaValue);
	}

	@Override
	public IDataset getSurface_area() {
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

}
