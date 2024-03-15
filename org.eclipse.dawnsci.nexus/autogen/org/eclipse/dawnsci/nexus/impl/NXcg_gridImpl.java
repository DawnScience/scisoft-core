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
 * Computational geometry description of a Wigner-Seitz cell grid in Euclidean space.
 * Frequently convenient three-dimensional grids with cubic cells are used.
 * Exemplar applications are spectral-solver based crystal plasticity
 * and stencil methods like phase-field or cellular automata.

 */
public class NXcg_gridImpl extends NXobjectImpl implements NXcg_grid {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_TRANSFORMATIONS,
		NexusBaseClass.NX_CG_POLYHEDRON_SET);

	public NXcg_gridImpl() {
		super();
	}

	public NXcg_gridImpl(final long oid) {
		super(oid);
	}

	@Override
	public Class<? extends NXobject> getNXclass() {
		return NXcg_grid.class;
	}

	@Override
	public NexusBaseClass getNexusBaseClass() {
		return NexusBaseClass.NX_CG_GRID;
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
	public IDataset getOrigin() {
		return getDataset(NX_ORIGIN);
	}

	@Override
	public Number getOriginScalar() {
		return getNumber(NX_ORIGIN);
	}

	@Override
	public DataNode setOrigin(IDataset originDataset) {
		return setDataset(NX_ORIGIN, originDataset);
	}

	@Override
	public DataNode setOriginScalar(Number originValue) {
		return setField(NX_ORIGIN, originValue);
	}

	@Override
	public IDataset getSymmetry() {
		return getDataset(NX_SYMMETRY);
	}

	@Override
	public String getSymmetryScalar() {
		return getString(NX_SYMMETRY);
	}

	@Override
	public DataNode setSymmetry(IDataset symmetryDataset) {
		return setDataset(NX_SYMMETRY, symmetryDataset);
	}

	@Override
	public DataNode setSymmetryScalar(String symmetryValue) {
		return setString(NX_SYMMETRY, symmetryValue);
	}

	@Override
	public IDataset getCell_dimensions() {
		return getDataset(NX_CELL_DIMENSIONS);
	}

	@Override
	public Number getCell_dimensionsScalar() {
		return getNumber(NX_CELL_DIMENSIONS);
	}

	@Override
	public DataNode setCell_dimensions(IDataset cell_dimensionsDataset) {
		return setDataset(NX_CELL_DIMENSIONS, cell_dimensionsDataset);
	}

	@Override
	public DataNode setCell_dimensionsScalar(Number cell_dimensionsValue) {
		return setField(NX_CELL_DIMENSIONS, cell_dimensionsValue);
	}

	@Override
	public IDataset getExtent() {
		return getDataset(NX_EXTENT);
	}

	@Override
	public Long getExtentScalar() {
		return getLong(NX_EXTENT);
	}

	@Override
	public DataNode setExtent(IDataset extentDataset) {
		return setDataset(NX_EXTENT, extentDataset);
	}

	@Override
	public DataNode setExtentScalar(Long extentValue) {
		return setField(NX_EXTENT, extentValue);
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
	public IDataset getCoordinate() {
		return getDataset(NX_COORDINATE);
	}

	@Override
	public Long getCoordinateScalar() {
		return getLong(NX_COORDINATE);
	}

	@Override
	public DataNode setCoordinate(IDataset coordinateDataset) {
		return setDataset(NX_COORDINATE, coordinateDataset);
	}

	@Override
	public DataNode setCoordinateScalar(Long coordinateValue) {
		return setField(NX_COORDINATE, coordinateValue);
	}

	@Override
	public NXcg_polyhedron_set getBounding_box() {
		// dataNodeName = NX_BOUNDING_BOX
		return getChild("bounding_box", NXcg_polyhedron_set.class);
	}

	@Override
	public void setBounding_box(NXcg_polyhedron_set bounding_boxGroup) {
		putChild("bounding_box", bounding_boxGroup);
	}

	@Override
	public IDataset getNumber_of_boundaries() {
		return getDataset(NX_NUMBER_OF_BOUNDARIES);
	}

	@Override
	public Long getNumber_of_boundariesScalar() {
		return getLong(NX_NUMBER_OF_BOUNDARIES);
	}

	@Override
	public DataNode setNumber_of_boundaries(IDataset number_of_boundariesDataset) {
		return setDataset(NX_NUMBER_OF_BOUNDARIES, number_of_boundariesDataset);
	}

	@Override
	public DataNode setNumber_of_boundariesScalar(Long number_of_boundariesValue) {
		return setField(NX_NUMBER_OF_BOUNDARIES, number_of_boundariesValue);
	}

	@Override
	public IDataset getBoundaries() {
		return getDataset(NX_BOUNDARIES);
	}

	@Override
	public String getBoundariesScalar() {
		return getString(NX_BOUNDARIES);
	}

	@Override
	public DataNode setBoundaries(IDataset boundariesDataset) {
		return setDataset(NX_BOUNDARIES, boundariesDataset);
	}

	@Override
	public DataNode setBoundariesScalar(String boundariesValue) {
		return setString(NX_BOUNDARIES, boundariesValue);
	}

	@Override
	public IDataset getBoundary_conditions() {
		return getDataset(NX_BOUNDARY_CONDITIONS);
	}

	@Override
	public Long getBoundary_conditionsScalar() {
		return getLong(NX_BOUNDARY_CONDITIONS);
	}

	@Override
	public DataNode setBoundary_conditions(IDataset boundary_conditionsDataset) {
		return setDataset(NX_BOUNDARY_CONDITIONS, boundary_conditionsDataset);
	}

	@Override
	public DataNode setBoundary_conditionsScalar(Long boundary_conditionsValue) {
		return setField(NX_BOUNDARY_CONDITIONS, boundary_conditionsValue);
	}

}
