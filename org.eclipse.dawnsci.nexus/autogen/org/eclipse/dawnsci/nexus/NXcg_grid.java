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

package org.eclipse.dawnsci.nexus;

import java.util.Map;

import org.eclipse.dawnsci.analysis.api.tree.DataNode;

import org.eclipse.january.dataset.IDataset;

/**
 * Computational geometry description of a Wigner-Seitz cell grid in Euclidean space.
 * Frequently convenient three-dimensional grids with cubic cells are used.
 * Exemplar applications are spectral-solver based crystal plasticity
 * and stencil methods like phase-field or cellular automata.
 * <p><b>Symbols:</b>
 * The symbols used in the schema to specify e.g. dimensions of arrays.<ul>
 * <li><b>d</b>
 * The dimensionality of the grid.</li>
 * <li><b>c</b>
 * The cardinality or total number of cells/grid points.</li>
 * <li><b>n_b</b>
 * Number of boundaries of the bounding box or primitive to the grid.</li></ul></p>
 *
 */
public interface NXcg_grid extends NXobject {

	public static final String NX_DIMENSIONALITY = "dimensionality";
	public static final String NX_CARDINALITY = "cardinality";
	public static final String NX_ORIGIN = "origin";
	public static final String NX_SYMMETRY = "symmetry";
	public static final String NX_CELL_DIMENSIONS = "cell_dimensions";
	public static final String NX_EXTENT = "extent";
	public static final String NX_IDENTIFIER_OFFSET = "identifier_offset";
	public static final String NX_IDENTIFIER = "identifier";
	public static final String NX_POSITION = "position";
	public static final String NX_COORDINATE = "coordinate";
	public static final String NX_NUMBER_OF_BOUNDARIES = "number_of_boundaries";
	public static final String NX_BOUNDARIES = "boundaries";
	public static final String NX_BOUNDARY_CONDITIONS = "boundary_conditions";
	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getDimensionality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionalityDataset the dimensionalityDataset
	 */
	public DataNode setDimensionality(IDataset dimensionalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getDimensionalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>1</b> </li>
	 * <li><b>2</b> </li>
	 * <li><b>3</b> </li></ul></p>
	 * </p>
	 *
	 * @param dimensionality the dimensionality
	 */
	public DataNode setDimensionalityScalar(Long dimensionalityValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCardinality();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinalityDataset the cardinalityDataset
	 */
	public DataNode setCardinality(IDataset cardinalityDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCardinalityScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param cardinality the cardinality
	 */
	public DataNode setCardinalityScalar(Long cardinalityValue);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getOrigin();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param originDataset the originDataset
	 */
	public DataNode setOrigin(IDataset originDataset);

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getOriginScalar();

	/**
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param origin the origin
	 */
	public DataNode setOriginScalar(Number originValue);

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getSymmetry();

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param symmetryDataset the symmetryDataset
	 */
	public DataNode setSymmetry(IDataset symmetryDataset);

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getSymmetryScalar();

	/**
	 * The symmetry of the lattice defining the shape of the unit cell.
	 * <p>
	 * <p><b>Enumeration:</b><ul>
	 * <li><b>cubic</b> </li></ul></p>
	 * </p>
	 *
	 * @param symmetry the symmetry
	 */
	public DataNode setSymmetryScalar(String symmetryValue);

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCell_dimensions();

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param cell_dimensionsDataset the cell_dimensionsDataset
	 */
	public DataNode setCell_dimensions(IDataset cell_dimensionsDataset);

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getCell_dimensionsScalar();

	/**
	 * The unit cell dimensions using crystallographic notation.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param cell_dimensions the cell_dimensions
	 */
	public DataNode setCell_dimensionsScalar(Number cell_dimensionsValue);

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells, or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getExtent();

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells, or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param extentDataset the extentDataset
	 */
	public DataNode setExtent(IDataset extentDataset);

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells, or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getExtentScalar();

	/**
	 * Number of unit cells along each of the d unit vectors.
	 * The total number of cells, or grid points has to be the cardinality.
	 * If the grid has an irregular number of grid positions in each direction,
	 * as it could be for instance the case of a grid where all grid points
	 * outside some masking primitive are removed, this extent field should
	 * not be used. Instead use the coordinate field.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: d;
	 * </p>
	 *
	 * @param extent the extent
	 */
	public DataNode setExtentScalar(Long extentValue);

	/**
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.
	 *
	 * @return  the value.
	 */
	public NXtransformations getTransformations();

	/**
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.
	 *
	 * @param transformationsGroup the transformationsGroup
	 */
	public void setTransformations(NXtransformations transformationsGroup);

	/**
	 * Get a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @param name  the name of the node.
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public NXtransformations getTransformations(String name);

	/**
	 * Set a NXtransformations node by name:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @param name the name of the node
	 * @param transformations the value to set
	 */
	public void setTransformations(String name, NXtransformations transformations);

	/**
	 * Get all NXtransformations nodes:
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @return  a map from node names to the NXtransformations for that node.
	 */
	public Map<String, NXtransformations> getAllTransformations();

	/**
	 * Set multiple child nodes of a particular type.
	 * <ul>
	 * <li>
	 * Reference to or definition of a coordinate system with
	 * which the positions and directions are interpretable.</li>
	 * </ul>
	 *
	 * @param transformations the child nodes to add
	 */

	public void setAllTransformations(Map<String, NXtransformations> transformations);


	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for cells. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier_offset();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for cells. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offsetDataset the identifier_offsetDataset
	 */
	public DataNode setIdentifier_offset(IDataset identifier_offsetDataset);

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for cells. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifier_offsetScalar();

	/**
	 * Integer which specifies the first index to be used for distinguishing
	 * identifiers for cells. Identifiers are defined either implicitly
	 * or explicitly. For implicit indexing the identifiers are defined on the
	 * interval [identifier_offset, identifier_offset+c-1].
	 * For explicit indexing the identifier array has to be defined.
	 * The identifier_offset field can for example be used to communicate if the
	 * identifiers are expected to start from 1 (referred to as Fortran-/Matlab-)
	 * or from 0 (referred to as C-, Python-style index notation) respectively.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param identifier_offset the identifier_offset
	 */
	public DataNode setIdentifier_offsetScalar(Long identifier_offsetValue);

	/**
	 * Integer used to distinguish cells for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getIdentifier();

	/**
	 * Integer used to distinguish cells for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifierDataset the identifierDataset
	 */
	public DataNode setIdentifier(IDataset identifierDataset);

	/**
	 * Integer used to distinguish cells for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getIdentifierScalar();

	/**
	 * Integer used to distinguish cells for explicit indexing.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: c;
	 * </p>
	 *
	 * @param identifier the identifier
	 */
	public DataNode setIdentifierScalar(Long identifierValue);

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getPosition();

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param positionDataset the positionDataset
	 */
	public DataNode setPosition(IDataset positionDataset);

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Number getPositionScalar();

	/**
	 * Position of each cell in Euclidean space.
	 * <p>
	 * <b>Type:</b> NX_NUMBER
	 * <b>Units:</b> NX_LENGTH
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param position the position
	 */
	public DataNode setPositionScalar(Number positionValue);

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getCoordinate();

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param coordinateDataset the coordinateDataset
	 */
	public DataNode setCoordinate(IDataset coordinateDataset);

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getCoordinateScalar();

	/**
	 * Coordinate of each cell with respect to the discrete grid.
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_DIMENSIONLESS
	 * <b>Dimensions:</b> 1: c; 2: d;
	 * </p>
	 *
	 * @param coordinate the coordinate
	 */
	public DataNode setCoordinateScalar(Long coordinateValue);

	/**
	 * A tight bounding box or sphere or bounding primitive about the grid.
	 *
	 * @return  the value.
	 */
	public NXcg_polyhedron_set getBounding_box();

	/**
	 * A tight bounding box or sphere or bounding primitive about the grid.
	 *
	 * @param bounding_boxGroup the bounding_boxGroup
	 */
	public void setBounding_box(NXcg_polyhedron_set bounding_boxGroup);

	/**
	 * How many distinct boundaries are distinguished?
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getNumber_of_boundaries();

	/**
	 * How many distinct boundaries are distinguished?
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_boundariesDataset the number_of_boundariesDataset
	 */
	public DataNode setNumber_of_boundaries(IDataset number_of_boundariesDataset);

	/**
	 * How many distinct boundaries are distinguished?
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getNumber_of_boundariesScalar();

	/**
	 * How many distinct boundaries are distinguished?
	 * Most grids discretize a cubic or cuboidal region. In this case
	 * six sides can be distinguished, each making an own boundary.
	 * <p>
	 * <b>Type:</b> NX_POSINT
	 * <b>Units:</b> NX_UNITLESS
	 * </p>
	 *
	 * @param number_of_boundaries the number_of_boundaries
	 */
	public DataNode setNumber_of_boundariesScalar(Long number_of_boundariesValue);

	/**
	 * Name of domain boundaries of the simulation box/ROI e.g. left, right,
	 * front, back, bottom, top.
	 * <p>
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getBoundaries();

	/**
	 * Name of domain boundaries of the simulation box/ROI e.g. left, right,
	 * front, back, bottom, top.
	 * <p>
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundariesDataset the boundariesDataset
	 */
	public DataNode setBoundaries(IDataset boundariesDataset);

	/**
	 * Name of domain boundaries of the simulation box/ROI e.g. left, right,
	 * front, back, bottom, top.
	 * <p>
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public String getBoundariesScalar();

	/**
	 * Name of domain boundaries of the simulation box/ROI e.g. left, right,
	 * front, back, bottom, top.
	 * <p>
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundaries the boundaries
	 */
	public DataNode setBoundariesScalar(String boundariesValue);

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public IDataset getBoundary_conditions();

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundary_conditionsDataset the boundary_conditionsDataset
	 */
	public DataNode setBoundary_conditions(IDataset boundary_conditionsDataset);

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @return  the value.
	 */
	public Long getBoundary_conditionsScalar();

	/**
	 * The boundary conditions for each boundary:
	 * 0 - undefined
	 * 1 - open
	 * 2 - periodic
	 * 3 - mirror
	 * 4 - von Neumann
	 * 5 - Dirichlet
	 * <p>
	 * <b>Type:</b> NX_INT
	 * <b>Units:</b> NX_UNITLESS
	 * <b>Dimensions:</b> 1: n_b;
	 * </p>
	 *
	 * @param boundary_conditions the boundary_conditions
	 */
	public DataNode setBoundary_conditionsScalar(Long boundary_conditionsValue);

}
