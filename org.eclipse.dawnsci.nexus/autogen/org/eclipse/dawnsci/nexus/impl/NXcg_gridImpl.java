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
 * Computational geometry description of a grid of Wigner-Seitz cells in Euclidean space.
 * Three-dimensional grids with cubic cells are if not the most frequently used
 * example of such grids. Numerical methods and models that use grids are used
 * in many cases in the natural sciences and engineering disciplines. Examples are
 * discretizations in space and time used for phase-field, cellular automata, or Monte Carlo
 * modeling.

 */
public class NXcg_gridImpl extends NXcg_primitiveImpl implements NXcg_grid {

	private static final long serialVersionUID = 1L;  // no state in this class, so always compatible


	public static final Set<NexusBaseClass> PERMITTED_CHILD_GROUP_CLASSES = EnumSet.of(
		NexusBaseClass.NX_CG_POLYHEDRON);

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
	public Dataset getOrigin() {
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
	public Dataset getSymmetry() {
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
	public Dataset getCell_dimensions() {
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
	public Dataset getExtent() {
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
	public Dataset getPosition() {
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
	public Dataset getCoordinate() {
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
	public NXcg_polyhedron getBounding_box() {
		// dataNodeName = NX_BOUNDING_BOX
		return getChild("bounding_box", NXcg_polyhedron.class);
	}

	@Override
	public void setBounding_box(NXcg_polyhedron bounding_boxGroup) {
		putChild("bounding_box", bounding_boxGroup);
	}

	@Override
	public Dataset getNumber_of_boundaries() {
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
	public Dataset getBoundaries() {
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
	public Dataset getBoundary_conditions() {
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

	@Override
	public Dataset getSurface_reconstruction() {
		return getDataset(NX_SURFACE_RECONSTRUCTION);
	}

	@Override
	public String getSurface_reconstructionScalar() {
		return getString(NX_SURFACE_RECONSTRUCTION);
	}

	@Override
	public DataNode setSurface_reconstruction(IDataset surface_reconstructionDataset) {
		return setDataset(NX_SURFACE_RECONSTRUCTION, surface_reconstructionDataset);
	}

	@Override
	public DataNode setSurface_reconstructionScalar(String surface_reconstructionValue) {
		return setString(NX_SURFACE_RECONSTRUCTION, surface_reconstructionValue);
	}

}
