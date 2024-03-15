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
 * Computational geometry description of a set of spheres in Euclidean space.
 * Each sphere can have a different radius.

 */
public class NXcg_sphere_setImpl extends NXobjectImpl implements NXcg_sphere_set {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS);

	public NXcg_sphere_setImpl() {
		super();
	}

	public NXcg_sphere_setImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_sphere_set.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_SPHERE_SET;
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
	public IDataset getRadius() {
		return getDataset(NX_RADIUS);
	}

	@Override
	public Number getRadiusScalar() {
		return getNumber(NX_RADIUS);
	}

	@Override
	public DataNode setRadius(IDataset radiusDataset) {
		return setDataset(NX_RADIUS, radiusDataset);
	}

	@Override
	public DataNode setRadiusScalar(Number radiusValue) {
		return setField(NX_RADIUS, radiusValue);
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
	public IDataset getIs_closed() {
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
